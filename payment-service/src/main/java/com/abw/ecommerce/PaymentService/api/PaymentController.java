package com.abw.ecommerce.PaymentService.api;

import com.abw.ecommerce.PaymentService.dto.OrderDto;
import com.abw.ecommerce.PaymentService.dto.PaymentDto;
import com.abw.ecommerce.PaymentService.http.HeaderGenerator;
import com.abw.ecommerce.PaymentService.service.PaymentService;
import com.abw.ecommerce.PaymentService.service.impl.PaymentServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
@Slf4j
@RequiredArgsConstructor
public class PaymentController {

    @Autowired
    private final PaymentService paymentService;

    @Autowired
    private final PaymentServiceImpl paymentServiceImpl;

    @Autowired
    private final HeaderGenerator headerGenerator;

    @Operation(summary = "Get all payment", description = "Retrieve a list of all payment.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payments retrieved successfully"),
            @ApiResponse(responseCode = "204", description = "No content")
    })
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Mono<ResponseEntity<List<PaymentDto>>> findAll() {
        log.info("*** PaymentDto List, controller; fetch all categories *");
        return paymentService.findAll()
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.ok(Collections.emptyList()));
    }

    @Operation(summary = "Get all payments with paging", description = "Retrieve a paginated list of all payments.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payments retrieved successfully"),
            @ApiResponse(responseCode = "204", description = "No content")
    })
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Mono<ResponseEntity<Page<PaymentDto>>> findAll(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size,
                                                          @RequestParam(defaultValue = "paymentId") String sortBy,
                                                          @RequestParam(defaultValue = "asc") String sortOrder) {
        return paymentService.findAll(page, size, sortBy, sortOrder)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get payment by ID", description = "Retrieve cart information based on the provided ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payment retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Payment not found")
    })
    @GetMapping("/{paymentId}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<Mono<PaymentDto>> findById(@PathVariable("paymentId")
                                                     @NotBlank(message = "Input must not be blank")
                                                     @Valid final String paymentId) {
        log.info("*** PaymentDto, resource; fetch cart by id *");
        return ResponseEntity.ok(paymentService.findById(Integer.parseInt(paymentId)));
    }


    @GetMapping("/getOrder/{orderId}")
    public ResponseEntity<Mono<OrderDto>> getOrderDto(@PathVariable("orderId") final Integer orderId) {
        return ResponseEntity.ok(paymentServiceImpl.getOrderDto(orderId));
    }


    @Operation(summary = "Save payment", description = "Save a new payment.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payment saved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    public Mono<ResponseEntity<PaymentDto>> save(@RequestBody
                                                 @NotNull(message = "Input must not be NULL!")
                                                 @Valid final PaymentDto paymentDto) {
        log.info("*** PaymentDto, resource; save payments *");
        return paymentService.save(paymentDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    @Operation(summary = "Update payment", description = "Update an existing payment.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payment updated successfully"),
            @ApiResponse(responseCode = "404", description = "Payment not found")
    })
    @PutMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Mono<ResponseEntity<PaymentDto>> update(@RequestBody
                                                   @NotNull(message = "Input must not be NULL")
                                                   @Valid final PaymentDto paymentDto) {
        log.info("*** CartDto, resource; update cart *");
        return paymentService.update(paymentDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


    @Operation(summary = "Update payment by ID", description = "Update an existing cart based on the provided ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payment updated successfully"),
            @ApiResponse(responseCode = "404", description = "Payment not found")
    })
    @PutMapping("/{paymentId}")
    @PreAuthorize("hasAuthority('USER')")
    public Mono<ResponseEntity<PaymentDto>> update(@PathVariable("paymentId")
                                                   @NotBlank(message = "Input must not be blank")
                                                   @Valid final Integer paymentId,
                                                   @RequestBody
                                                   @NotNull(message = "Input must not be NULL")
                                                   @Valid final PaymentDto paymentDto) {
        log.info("*** PaymentDto, resource; update cart with paymentId *");
        return paymentService.update(paymentId, paymentDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete payment by ID", description = "Delete a cart based on the provided ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payment deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Payment not found")
    })
    @DeleteMapping("/{paymentId}")
    @PreAuthorize("hasAuthority('USER')")
    public Mono<ResponseEntity<Boolean>> deleteById(@PathVariable("paymentId") final Integer paymentId) {
        log.info("*** Boolean, resource; delete payment by id *");
        return paymentService.deleteById(paymentId)
                .thenReturn(ResponseEntity.ok(true))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).body(false));
    }

}