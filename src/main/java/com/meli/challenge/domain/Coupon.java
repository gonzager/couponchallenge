package com.meli.challenge.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coupon {
    @NotEmpty( message = "La lista de items en mandatoria")
    private List<String> items_ids;
    @Positive( message = "El amount debe ser mayor a 0")
    private BigDecimal amount;

}
