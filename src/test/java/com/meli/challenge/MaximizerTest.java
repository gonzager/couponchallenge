package com.meli.challenge;

import com.meli.challenge.domain.ItemPrice;
import com.meli.challenge.process.Maximizer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
@SpringBootTest
@ActiveProfiles("test")
public class MaximizerTest {
    @Autowired
    private Maximizer maximizer;
    @Test
    void  processItems() {

        List<ItemPrice> maximized_items = maximizer.process(
            Arrays.asList(
                    new ItemPrice("MLA1",new BigDecimal(100),"active"),
                    new ItemPrice("MLA2",new BigDecimal(210), "active"),
                    new ItemPrice("MLA3",new BigDecimal(260), "active"),
                    new ItemPrice("MLA4",new BigDecimal(80), "active"),
                    new ItemPrice("MLA5",new BigDecimal(90), "active")
            ),new BigDecimal(500));

        List<String> maximized_ids = maximized_items.stream().map(i -> i.getId()).collect(Collectors.toList());
        assertTrue(
                maximized_ids.containsAll(Arrays.asList("MLA1", "MLA2", "MLA4", "MLA5"))
        );
    }

    @Test
    void  processAmount() {

        List<ItemPrice> maximized_items = maximizer.process(
                Arrays.asList(
                        new ItemPrice("MLA1",new BigDecimal(100),"active"),
                        new ItemPrice("MLA2",new BigDecimal(210), "active"),
                        new ItemPrice("MLA3",new BigDecimal(260), "active"),
                        new ItemPrice("MLA4",new BigDecimal(80), "active"),
                        new ItemPrice("MLA5",new BigDecimal(90), "active")
                ),new BigDecimal(500));

        BigDecimal amount = maximized_items.stream().reduce(new BigDecimal(0), (a,b)-> a.add(b.getPrice()),BigDecimal::add);

        assertEquals(new BigDecimal(480), amount);
    }

}
