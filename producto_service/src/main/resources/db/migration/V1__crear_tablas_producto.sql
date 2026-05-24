CREATE TABLE clasificacion_producto (
    id_clasificacion INT AUTO_INCREMENT PRIMARY KEY,
    nombre_categoria VARCHAR(50) NOT NULL
);

CREATE TABLE producto (
    id_producto INT AUTO_INCREMENT PRIMARY KEY,
    id_clasificacion INT NOT NULL,
    id_pokemon INT,
    nombre_producto VARCHAR(150) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10,2) NOT NULL,
    stock INT DEFAULT 0,
    CONSTRAINT fk_producto_clasificacion
        FOREIGN KEY (id_clasificacion)
            REFERENCES clasificacion_producto(id_clasificacion)
);