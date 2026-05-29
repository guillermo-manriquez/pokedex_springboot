CREATE TABLE orden (
                       id_orden INT AUTO_INCREMENT PRIMARY KEY,
                       id_carrito INT NOT NULL,
                       id_usuario BIGINT NOT NULL,
                       fecha_orden TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       estado_orden VARCHAR(20) DEFAULT 'PENDIENTE',
                       total_monto DECIMAL(12, 2)
);

CREATE TABLE orden_item (
                            id_orden_item INT AUTO_INCREMENT PRIMARY KEY,
                            id_orden INT NOT NULL,
                            id_producto INT NOT NULL,
                            cantidad INT NOT NULL,
                            precio_historico DECIMAL(10, 2) NOT NULL,
                            FOREIGN KEY (id_orden) REFERENCES orden(id_orden)
);