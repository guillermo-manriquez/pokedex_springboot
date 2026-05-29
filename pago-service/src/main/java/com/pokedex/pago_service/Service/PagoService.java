package com.pokedex.pago_service.Service;

import com.pokedex.pago_service.Dto.PagoRequest;
import com.pokedex.pago_service.Dto.PagoResponse;
import com.pokedex.pago_service.Model.Pago;
import com.pokedex.pago_service.Repository.PagoRepository;
import com.pokedex.pago_service.client.NotificacionRequest;
import com.pokedex.pago_service.client.NotificacionServiceClient;
import com.pokedex.pago_service.client.OrdenClient;
import com.pokedex.pago_service.Dto.OrdenDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PagoService {

    private final PagoRepository paymentRepository;
    private final NotificacionServiceClient notificacionServiceClient;
    private final OrdenClient ordenClient;

    public PagoResponse processPayment(PagoRequest request) {

        log.info("Iniciando proceso de pago | orderId={} | userId={}",
                request.getOrderId(), request.getUserId());

        try {
            OrdenDTO orden = ordenClient.getOrdenById(request.getOrderId());

            if (orden == null) {
                log.warn("Orden no encontrada: {}", request.getOrderId());
                throw new RuntimeException("Orden no existe");
            }

            if (!orden.getIdUsuario().equals(request.getUserId())) {
                log.warn("Usuario no coincide | requestUser={} | ordenUser={}",
                        request.getUserId(), orden.getIdUsuario());
                throw new RuntimeException("Usuario no corresponde a la orden");
            }

            Pago payment = new Pago();
            payment.setOrderId(request.getOrderId());
            payment.setUserId(request.getUserId());
            payment.setAmount(request.getAmount());
            payment.setCurrency(request.getCurrency() != null ? request.getCurrency() : "CLP");
            payment.setPaymentMethod(request.getPaymentMethod());
            payment.setStatus(Pago.PaymentStatus.PENDING);

            paymentRepository.save(payment);
            log.info("Pago creado en estado PENDING | paymentId={}", payment.getId());

            boolean success = simulatePayment(request.getCardNumber());

            if (success) {
                payment.setStatus(Pago.PaymentStatus.COMPLETED);
                paymentRepository.save(payment);

                log.info("Pago COMPLETADO | paymentId={}", payment.getId());

                notificacionServiceClient.createNotificacion(
                        new NotificacionRequest(
                                payment.getUserId(),
                                "Tu pago fue realizado exitosamente",
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

                log.warn("Pago FALLIDO | paymentId={}", payment.getId());

                return new PagoResponse(
                        payment.getId(),
                        payment.getOrderId(),
                        payment.getAmount(),
                        "FAILED",
                        "Pago fallido - tarjeta rechazada"
                );
            }

        } catch (Exception e) {
            log.error("Error en processPayment | orderId={} | error={}",
                    request.getOrderId(), e.getMessage(), e);

            throw new RuntimeException("Error procesando pago: " + e.getMessage());
        }
    }

    public PagoResponse getPaymentByOrderId(Integer orderId) {
        try {
            log.info("Buscando pago por orderId={}", orderId);

            Pago payment = paymentRepository.findByOrderId(orderId)
                    .orElseThrow(() -> new RuntimeException("Pago no encontrado"));

            return new PagoResponse(
                    payment.getId(),
                    payment.getOrderId(),
                    payment.getAmount(),
                    payment.getStatus().name(),
                    "OK"
            );

        } catch (Exception e) {
            log.error("Error al buscar pago por orderId={} | error={}", orderId, e.getMessage());
            throw new RuntimeException("Error obteniendo pago");
        }
    }

    private boolean simulatePayment(String cardNumber) {
        if (cardNumber == null) return true;
        return !cardNumber.endsWith("0000");
    }
}