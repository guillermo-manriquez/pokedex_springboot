package com.pokedex.notificacion_service.Service;

import com.pokedex.notificacion_service.Dto.NotificacionRequest;
import com.pokedex.notificacion_service.Dto.NotificacionResponse;
import com.pokedex.notificacion_service.Entity.Notificacion;
import com.pokedex.notificacion_service.Repository.NotificacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificacionService {

    private final NotificacionRepository notificacionRepository;

    public void createNotification(NotificacionRequest request) {
        Notificacion notificacion = new Notificacion();
        notificacion.setUserId(request.getUserId());
        notificacion.setMessage(request.getMessage());
        notificacion.setType(request.getType());
        notificacionRepository.save(notificacion);
    }

    public List<NotificacionResponse> getMyNotifications(Integer userId) {
        return notificacionRepository.findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(n -> new NotificacionResponse(
                        n.getId(),
                        n.getMessage(),
                        n.getType(),
                        n.getIsRead(),
                        n.getCreatedAt()
                ))
                .toList();
    }

    public void markAsRead(Long notificationId) {
        Notificacion notification = notificacionRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notificacion no encontrada"));
        notification.setIsRead(true);
        notificacionRepository.save(notification);
    }


}
