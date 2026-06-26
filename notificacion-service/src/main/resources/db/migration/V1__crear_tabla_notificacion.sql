CREATE TABLE notifications (
                               id         BIGINT AUTO_INCREMENT PRIMARY KEY,
                               user_id    BIGINT      NOT NULL,
                               message    VARCHAR(255) NOT NULL,
                               type       VARCHAR(50)  NOT NULL,
                               is_read    BOOLEAN      NOT NULL DEFAULT FALSE,
                               created_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP
);