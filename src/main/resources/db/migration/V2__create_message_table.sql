CREATE TABLE IF NOT EXISTS message(
    id         BIGSERIAL PRIMARY KEY,
    text VARCHAR(1000),
    chat_id BIGINT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE,
    user_id BIGINT NOT NULL,
    image_url VARCHAR(1000),
    file_url VARCHAR(1000),
    CONSTRAINT fk_room_message
        FOREIGN KEY (chat_id) REFERENCES chat(id)
);