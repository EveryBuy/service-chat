ALTER TABLE chat
    ADD COLUMN section VARCHAR(20)
        CHECK (section IN ('BUY', 'SELL'));