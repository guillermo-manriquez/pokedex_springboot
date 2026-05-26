CREATE TABLE Pokedex (
        id_pokedex INT PRIMARY KEY,
        nombre_pokedex VARCHAR(50) NOT NULL,
        generacion VARCHAR(50) NOT NULL
);

CREATE TABLE Pokemon (
        id_pokemon INT PRIMARY KEY,
        id_pokedex INT NOT NULL,

        nombre VARCHAR(50) NOT NULL,
        imagen_url TEXT,
        descripcion VARCHAR(500),

        altura INT,
        peso INT,

        hp INT,
        ataque INT,
        defensa INT,
        velocidad INT,
        ataque_sp INT,
        defensa_sp INT,

        experiencia_base INT,

        forma VARCHAR(50),

        tipo1 VARCHAR(50) NOT NULL,
        tipo2 VARCHAR(50),

        habilidad1 VARCHAR(50) NOT NULL,
        habilidad2 VARCHAR(50),
        habilidad_oculta VARCHAR(50),

        FOREIGN KEY (id_pokedex) REFERENCES Pokedex(id_pokedex)
);