package ro.uvt.info.dw.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TimeSeriesDataController.class)
public class TimeSeriesDataControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void givenTimeSeriesParameters_whenGetTimeSeriesData_thenReturnJsonArray() throws Exception {
        final String assetId = "test asset";
        final String tsdId = "test tsd";
        final LocalDate startDate = LocalDate.of(2019, 4, 1);
        final LocalDate endDate   = LocalDate.of(2019, 4, 15);

        mvc.perform(get("/api/tsData")
            .param("assetId", assetId)
            .param("tsDefinitionId", tsdId)
            .param("startBusinessDate", startDate.toString())
            .param("endBusinessDate", endDate.toString())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.tsData.assetId", is(assetId)))
            .andExpect(jsonPath("$.tsData.tsDefinitionId", is(tsdId)))
            .andExpect(jsonPath("$.tsData.records", hasSize((int)DAYS.between(startDate, endDate))))
            .andExpect(jsonPath("$.attributes", is((Object)null)));
    }
}
