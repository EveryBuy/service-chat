CREATE TABLE IF NOT EXISTS file_url(
                                      id         BIGSERIAL PRIMARY KEY,
                                      chat_id BIGINT NOT NULL,
                                      created_at TIMESTAMP WITH TIME ZONE,
                                      user_id BIGINT NOT NULL,
                                      file_url VARCHAR(1000),
                                      CONSTRAINT fk_room_message
                                          FOREIGN KEY (chat_id) REFERENCES chat(id)
);