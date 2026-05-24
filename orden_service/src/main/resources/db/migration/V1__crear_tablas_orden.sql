CREATE TABLE Orden (
    id_orden INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL, -- sin FK
    fecha_orden TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado_orden VARCHAR(20) DEFAULT 'Pendiente',
    total_monto DECIMAL(12, 2)
);

CREATE TABLE Orden_Item (
    id_orden_item INT AUTO_INCREMENT PRIMARY KEY,
    id_orden INT NOT NULL,
    id_producto INT NOT NULL, -- sin FK
    cantidad INT NOT NULL,
    precio_historico DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (id_orden) REFERENCES Orden(id_orden)
);
