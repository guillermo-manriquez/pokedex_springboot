CREATE TABLE lista_pokemon (
    id_lista INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    nombre_lista VARCHAR(100) NOT NULL,
    descripcion VARCHAR(300),
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE lista_pokemon_detalle (
    id_detalle INT AUTO_INCREMENT PRIMARY KEY,
    id_lista INT NOT NULL,
    id_pokemon INT NOT NULL,
    fecha_agregado DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_lista)
        REFERENCES lista_pokemon(id_lista)
);
