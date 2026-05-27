package com.pokedex.pago_service.Service;

import com.pokedex.pago_service.Dto.PagoRequest;
import com.pokedex.pago_service.Dto.PagoResponse;
import com.pokedex.pago_service.Model.Pago;
import com.pokedex.pago_service.Repository.PagoRepository;
import com.pokedex.pago_service.client.NotificacionRequest;
import com.pokedex.pago_service.client.NotificacionServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class PagoService {

    private final PagoRepository paymentRepository;
    private final NotificacionServiceClient notificacionServiceClient;


    public PagoResponse processPayment(PagoRequest request) {
        Pago payment = new Pago();
        payment.setOrderId(request.getOrderId());
        payment.setUserId(request.getUserId());
        payment.setAmount(request.getAmount());
        payment.setCurrency(request.getCurrency() != null ? request.getCurrency() : "CLP");
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setStatus(Pago.PaymentStatus.PENDING);

        paymentRepository.save(payment);

        boolean success = simulatePayment(request.getCardNumber());

        if (success) {
            payment.setStatus(Pago.PaymentStatus.COMPLETED);
            paymentRepository.save(payment);

            notificacionServiceClient.createNotificacion(
                    new NotificacionRequest(
                            payment.getUserId(),
                            "Tu pago fue realizado existosamente",
                            "PAYMENT_SUCCESS"
                    )
            );


            return new PagoResponse(
                    payment.getId(),
                    payment.getOrderId(),
                    payment.getAmount(),
                    "COMPLETED",
                    "Pago procesado exitosamente"
            );
        } else {
            payment.setStatus(Pago.PaymentStatus.FAILED);
            paymentRepository.save(payment);
            return new PagoResponse(
                    payment.getId(),
                    payment.getOrderId(),
                    payment.getAmount(),
                    "FAILED",
                    "Pago fallido - tarjeta rechazada"
            );
        }
    }

    public PagoResponse getPaymentByOrderId(Integer orderId) {
        Pago payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));
        return new PagoResponse(
                payment.getId(),
                payment.getOrderId(),
                payment.getAmount(),
                payment.getStatus().name(),
                "OK"
        );
    }

    private boolean simulatePayment(String cardNumber) {
        if (cardNumber == null) return true;
        return !cardNumber.endsWith("0000");
    }
}
