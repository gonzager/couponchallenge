package com.meli.challenge.process;

import com.meli.challenge.domain.ItemPrice;

import java.math.BigDecimal;
import java.util.List;

public interface Maximizer {
    List<ItemPrice> process(List<ItemPrice> items, BigDecimal amount);
}
