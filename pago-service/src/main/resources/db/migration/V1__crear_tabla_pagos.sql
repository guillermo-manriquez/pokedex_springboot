CREATE TABLE payments (
                          id              BIGINT AUTO_INCREMENT PRIMARY KEY,
                          orden_id        INTEGER        NOT NULL,
                          user_id         INTEGER        NOT NULL,
                          cantidad          DECIMAL(10,2)  NOT NULL,
                          moneda        VARCHAR(10)    NOT NULL DEFAULT 'CLP',
                          estado          VARCHAR(20)    NOT NULL DEFAULT 'PENDING',
                          metodo_pago  VARCHAR(30)    NOT NULL,
                          created_at      DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP
);