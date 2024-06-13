CREATE TABLE IF NOT EXISTS chat
(
    id         BIGSERIAL PRIMARY KEY,
    ad_id      BIGINT NOT NULL,
    buyer_id   BIGINT NOT NULL,
    seller_id  BIGINT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE
);