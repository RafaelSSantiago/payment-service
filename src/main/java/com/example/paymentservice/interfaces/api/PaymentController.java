package com.example.paymentservice.interfaces.api;

import com.example.paymentservice.domain.application.dto.CreatePaymentCommand;
import com.example.paymentservice.domain.application.dto.PaymentView;
import com.example.paymentservice.domain.application.usecase.PaymentApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentApplicationService service;

    public PaymentController(PaymentApplicationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> create(@Valid @RequestBody CreatePaymentRequest req){
        var view = service.create(new CreatePaymentCommand(
                req.userId(),
                req.method(),
                req.amount(),
                req.currency(),
                req.description(),
                req.expiresAt(),
                req.metadata()
        ));
        var response = new PaymentResponse(
                view.id(), view.status(), view.method(),
                view.amount(), view.currency(), view.description(), view.createdAt()
        );

        return ResponseEntity.created(URI.create("/payments/" + view.id())).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> get(@PathVariable String id){
        System.out.println("Fetching payment with ID: " + id);
        PaymentView view = service.get(id);
        System.out.println(view);
        return ResponseEntity.ok(new PaymentResponse(
                view.id(), view.status(), view.method(),
                view.amount(), view.currency(), view.description(), view.createdAt()
        ));
    }

    @PostMapping("/{id}/success")
    public ResponseEntity<PaymentResponse> markSuccess(
            @PathVariable String id,
            @Valid @RequestBody MarkSuccessRequest req,
            @RequestHeader(value = "Idempotency-key", required = false) String idempotencyKey) {
       var view = service.markSucess(new com.example.paymentservice.domain.application.dto.MarkPaymentSuccessCommand(
               id, req.externalId()
       ));
       var response = new PaymentResponse(
               view.id(), view.status(), view.method(), view.amount(), view.currency(), view.description(), view.createdAt()
       );
       return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/fail")
    public ResponseEntity<PaymentResponse> markFailed(
            @PathVariable String id,
            @Valid @RequestBody MarkFailedRequest req,
            @RequestHeader(value = "Idempotency-Key", required = false) String idempotencyKey) {
        var view = service.markFailed(new com.example.paymentservice.domain.application.dto.MarkPaymentFailedCommand(id, req.failuredCode()));

        var response = new PaymentResponse(view.id(), view.status(), view.method(), view.amount(), view.currency(), view.description(), view.createdAt());
        return ResponseEntity.ok(response);
    }
}
