package ro.uvt.info.dw.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.uvt.info.dw.mirror.TimeSeriesData;
import ro.uvt.info.dw.repository.TimeSeriesDataRepository;

import java.time.LocalDate;

@RestController
@RequestMapping(path = "/api/tsData", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TimeSeriesDataController {

    private final TimeSeriesDataRepository tsDataRepo;

    /**
     * Usage example
     *  curl "http://localhost:8080/api/tsData?assetId=WIKI/AAPL&tsDefinitionId=QUANDL.WIKI&startBusinessDate=2019-01-01&endBusinessDate=2019-01-20"
     *
     * Note: This implementation echos the request parameters as a JSON response
     *
     * @param assetId the id of the asset
     * @param tsDefinitionId the id of the time series definition
     * @param startBusinessDate business date start
     * @param endBusinessDate business date end (only time series records in (startBusinessDate, endBusinessDate] range will be returnes)
     * @param includeAttributes whether attributes definitions are returned (optional, default value = false)
     * @return an array of TimeSeries records
     */
    @GetMapping
    public ResponseEntity<?> getTimeSeriesData(
        @RequestParam("assetId") String assetId,
        @RequestParam("tsDefinitionId") String tsDefinitionId,
        @RequestParam("startBusinessDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startBusinessDate,
        @RequestParam("endBusinessDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endBusinessDate,
        @RequestParam(name = "includeAttributes", defaultValue = "false") boolean includeAttributes
    ) {
        // Validate input parameters: startBusinessDate <= endBusinessDate
        if (startBusinessDate.isAfter(endBusinessDate)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("startBusinessDate newer than endBusinessDate");
        }

        // needs to be improved for startBusinessDate <= businessDate <= endBusinessDate
        // can no longer update the database so stuck here with a 0 :(
        Iterable<TimeSeriesData> response = tsDataRepo
                .findAllByAssetIdAndTimeSeriesDefinitionIdAndBusinessDateYear(assetId, tsDefinitionId, 0);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
