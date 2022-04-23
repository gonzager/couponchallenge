package com.meli.challenge;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.challenge.domain.Coupon;
import com.meli.challenge.repository.FavouriteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.math.BigDecimal;
import java.util.Arrays;

import static org.hamcrest.Matchers.*;

@SpringBootTest

@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Counpon Controller Test")
public class CouponControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FavouriteRepository favouriteRepository;

    @Test
    void oneItemsFavoriteOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/coupon")
                        .content(asJsonString(new Coupon(Arrays.asList("MLA922245925"),new BigDecimal(50000))))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.items_ids", hasSize(1))
                );
    }

    @Test
    void oneItemsNoFavoriteOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/coupon")
                        .content(asJsonString(new Coupon(Arrays.asList("MLA922245925"),new BigDecimal(1.0))))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(0.00))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items_ids", hasSize(0))
                );
    }

    @Test
    void badRequestEmpty() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/coupon")
                        .content(asJsonString(new Coupon(Arrays.asList(),new BigDecimal(0))))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
