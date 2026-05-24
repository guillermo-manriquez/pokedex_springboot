CREATE TABLE user_profiles (
                               id           BIGINT AUTO_INCREMENT PRIMARY KEY,
                               user_id      BIGINT       NOT NULL UNIQUE,
                               username     VARCHAR(50)  NOT NULL UNIQUE,
                               display_name VARCHAR(100),
                               created_at   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP
);