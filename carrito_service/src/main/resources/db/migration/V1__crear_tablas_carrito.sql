CREATE TABLE carrito (
    id_carrito INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario BIGINT NOT NULL, -- sin FK
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE carrito_item (
    id_carrito_item INT AUTO_INCREMENT PRIMARY KEY,
    id_carrito INT NOT NULL,
    id_producto INT NOT NULL, -- sin FK
    cantidad INT DEFAULT 1,
    FOREIGN KEY (id_carrito) REFERENCES Carrito(id_carrito)
);
