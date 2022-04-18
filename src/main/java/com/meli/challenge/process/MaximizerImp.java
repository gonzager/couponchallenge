package com.meli.challenge.process;

import com.meli.challenge.domain.ItemPrice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class MaximizerImp implements Maximizer{
    @Override
    public List<ItemPrice> process(List<ItemPrice> items, BigDecimal amount) {
        return processKnapSack(
                amount.intValue(),
                (int) items.size(),
                items.stream().map(e-> e.getPrice().setScale(0,RoundingMode.CEILING).intValue()).collect(Collectors.toList()),
                items.stream().map(e-> e.getPrice().setScale(0, RoundingMode.CEILING).intValue()).collect(Collectors.toList()),
                items
        );
    }

    private List<ItemPrice> processKnapSack(int tope, int size, List<Integer> wt, List<Integer> val, List<ItemPrice> ids )
    {
        int i, w;
        List<ItemPrice> items = new ArrayList<>();

        // Creo la matriz para recorrerla
        int[][] K = new int[size + 1][tope + 1];
        for (i = 0; i <= size; i++) {
            for (w = 0; w <= tope; w++) {
                if (i == 0 || w == 0)
                    K[i][w] = 0;
                else if (wt.get(i - 1) <= w)
                    K[i][w] = Math.max(val.get(i - 1) + K[i - 1][w - wt.get(i - 1)], K[i - 1][w]);
                else
                    K[i][w] = K[i - 1][w];
            }
        }

        // stores the result of Knapsack
        int res = K[size][tope];
        log.debug("Valor optimizado encontrado: {}", res);

        w = tope;
        for (i = size; i > 0 && res > 0; i--) {
            if (res != K[i - 1][w])
            {
                items.add(new ItemPrice(ids.get(i - 1).getId(), ids.get(i - 1).getPrice()));
                log.debug("Item: {} amount: {}",ids.get(i - 1).getId(), ids.get(i - 1).getPrice() );
                res = res - val.get(i - 1);
                w = w - wt.get(i - 1);
            }
        }
        return items;
    }

}
