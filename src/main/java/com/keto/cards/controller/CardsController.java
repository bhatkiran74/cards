package com.keto.cards.controller;

import com.keto.cards.services.ICardsService;
import com.keto.cards.utils.constants.CardsConstants;
import com.keto.cards.utils.dto.ErrorResponseDto;
import com.keto.cards.utils.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 * CardsController.java
 * Author: Kiransing bhat
 * Description: This class implements CardsController
 **/
@Tag(name = "Cards Management", description = "Endpoints for managing cards")
@RestController
@RequestMapping(path = "/api/cards", produces = {MediaType.APPLICATION_JSON_VALUE})
public class CardsController {

    @Autowired
    private ICardsService iCardsService;

    /**
     * Endpoint to create a new card based on the provided mobile number.
     *
     * @param mobileNumber The mobile number associated with the customer for whom the card is being created.
     * @return ResponseEntity with status 201 (Created) and a response body containing status and message.
     */
    @Operation(summary = "Create a new card", description = "Creates a new card based on the provided mobile number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Account created successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)) })
    })
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createCard(@RequestParam String mobileNumber){
        iCardsService.createCard(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(CardsConstants.STATUS_201,CardsConstants.MESSAGE_201));
    }
}
