CREATE TABLE favorites (
                           id         BIGINT AUTO_INCREMENT PRIMARY KEY,
                           user_id    INTEGER  NOT NULL,
                           pokemon_id INTEGER  NOT NULL,
                           created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           UNIQUE KEY unique_favorite (user_id, pokemon_id)
);