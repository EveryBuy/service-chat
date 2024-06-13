CREATE TABLE IF NOT EXISTS black_list
(
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT,
    blocked_user_id BIGINT
);
