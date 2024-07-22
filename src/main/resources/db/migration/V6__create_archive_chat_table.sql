CREATE TABLE IF NOT EXISTS archive_chat
(
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT,
    chat_id BIGINT
);