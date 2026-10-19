package com.example.floodmonitor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class StationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void postStationStoresItAndReturnsCreatedResource() throws Exception {
        mockMvc.perform(post("/api/v1/stations")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "id": "client-id",
                      "stationName": "Station Krems",
                      "timestamp": "2026-10-19T10:00:00",
                      "waterLevel": 2.1,
                      "waterTemperature": 9.4,
                      "waterflow": 5.2,
                      "unit": "m"
                    }
                    """))
            .andExpect(status().isCreated())
            .andExpect(header().string("Location", "http://localhost/api/v1/stations/6"))
            .andExpect(jsonPath("$.id", is("6")))
            .andExpect(jsonPath("$.stationName", is("Station Krems")));

        mockMvc.perform(get("/api/v1/stations/6"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.stationName", is("Station Krems")));

        mockMvc.perform(get("/api/v1/stations"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[5].stationName", is("Station Krems")));

        mockMvc.perform(get("/api/v1/stations/count"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", is(6)));
    }
}
