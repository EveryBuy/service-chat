ALTER TABLE favorite_chat
    ADD CONSTRAINT fk_favorite_chat
        FOREIGN KEY (chat_id)
            REFERENCES chat(id);