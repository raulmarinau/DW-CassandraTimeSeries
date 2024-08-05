package ro.uvt.info.dw.loader;

import com.jimmoores.quandl.DataSetRequest;
import com.jimmoores.quandl.Row;
import com.jimmoores.quandl.TabularResult;
import com.jimmoores.quandl.classic.ClassicQuandlSession;
import org.springframework.context.ApplicationContext;
import ro.uvt.info.dw.mirror.Asset;
import ro.uvt.info.dw.mirror.Attribute;
import ro.uvt.info.dw.mirror.TimeSeriesData;
import ro.uvt.info.dw.mirror.TimeSeriesDefinition;
import ro.uvt.info.dw.repository.AssetsRepository;
import ro.uvt.info.dw.repository.AttributesRepository;
import ro.uvt.info.dw.repository.TimeSeriesDataRepository;
import ro.uvt.info.dw.repository.TimeSeriesDefinitionsRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class QuandlLoader {

    public static void load(ApplicationContext ctx, String symbol) {
        ClassicQuandlSession session = ClassicQuandlSession.create();
        TabularResult tabularResult = session.getDataSet(
                DataSetRequest.Builder.of(symbol).build());
        System.out.println(tabularResult.toPrettyPrintedString());
        System.out.println(tabularResult.getHeaderDefinition());

        // ----------------- asset
        AssetsRepository assetsRepo = ctx.getBean(AssetsRepository.class);
        if(assetsRepo.findAllById(symbol).isEmpty()) {
            Asset asset = new Asset().id(symbol).systemTime(new Date());
            assetsRepo.save(asset);
        }
        // ----------------- asset end

        // ----------------- attributes
        Set<String> attributes = new HashSet<String>();
        AttributesRepository attributesRepo = ctx.getBean(AttributesRepository.class);

// Create attributes
        for(String columnName : tabularResult.getHeaderDefinition().getColumnNames()) {
// Create and store an attribute with name = columnName
// if and only if it does not already exist in the database
            String id = "quandl_" + columnName;
            attributes.add(columnName);

            if(attributesRepo.findAllById(id).isEmpty()) {
                Attribute attribute = new Attribute()
                        .id(id)
                        .name(columnName)
                        .systemTime(new Date())
                        .publisher("Quandl")
                        .type(1);

                attributesRepo.save(attribute);
            }
        }
        // ----------------- attributes end

        // ----------------- time series definition
// Create the time series definition iff it does not already exist in the database
        TimeSeriesDefinitionsRepository timeSeriesDefinitionsRepo
                = ctx.getBean(TimeSeriesDefinitionsRepository.class);

        if(timeSeriesDefinitionsRepo.findAllById(symbol).isEmpty()){
            TimeSeriesDefinition tsd = new TimeSeriesDefinition()
                    .id(symbol)
                    .systemTime(new Date())
                    .publisher("Quandl")
                    .type(1)
                    .attributes(attributes);

            timeSeriesDefinitionsRepo.save(tsd);
        }
        // ----------------- time series definition end

        // ----------------- time series data
// Populate time series data
        TimeSeriesDataRepository timeSeriesDataRepo =
                ctx.getBean(TimeSeriesDataRepository.class);
        for(final Row row : tabularResult) {
// Create a new record in TimeSeriesData table
            TimeSeriesData tsData = new TimeSeriesData()
                    .assetId(symbol)
                    .timeSeriesDefinitionId(symbol)
                    .systemTime(new Date());
            for(final String attribute: attributes) {
                if(attribute.equals("Date")) {
                    LocalDate localDate = row.getLocalDate(attribute);
                    Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                    tsData.businessDate(date);
                    tsData.businessDateYear(date.getYear());
                }
                else {
                    Double value = row.getDouble(attribute);
                    tsData.valuesDouble().put(attribute, value);
                }
            }
            //
            timeSeriesDataRepo.save(tsData);
        }
        // ----------------- time series data end
    }

}
