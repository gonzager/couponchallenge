package com.meli.challenge.controller;

import com.meli.challenge.domain.Coupon;
import com.meli.challenge.domain.Favourite;
import com.meli.challenge.exception.ErrorDetail;
import com.meli.challenge.service.CouponService;
import com.meli.challenge.service.FavouriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/coupon")
@Slf4j
public class CouponController {

    @Autowired
    CouponService couponService;

    @Autowired
    FavouriteService favouriteService;

    @Operation(summary = "Dada una lista de item_ids y un monto total, recupera una lista de ítems que maximiza lo gastado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ítems que debería comprar el usuario",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Coupon.class)) }),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetail.class)) }),
            @ApiResponse(responseCode = "500", description = "Error Interno del procesamiento del servidor",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<Coupon> getCoupon(@Valid @RequestBody Coupon coupon){
        return new ResponseEntity<>(couponService.getMaximizedCoupon(coupon), HttpStatus.OK);
    }


    @Operation(summary = "Retorna el top 5 de ítems mas favoriteados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ítems mas favoriteados",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Favourite.class)) }),
            @ApiResponse(responseCode = "500", description = "Error Interno en el procesamiento del servidor",
                    content = @Content)
    })
    @GetMapping("/stats")
    public ResponseEntity<List<Favourite>> getStats(){
        return new ResponseEntity<List<Favourite>>(favouriteService.findTop5ByOrderByQuantityDesc(),  HttpStatus.OK);
    }

}
