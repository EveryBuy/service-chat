CREATE TABLE IF NOT EXISTS favorite_chat
(
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT,
    chat_id BIGINT
);