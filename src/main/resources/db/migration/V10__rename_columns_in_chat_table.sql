
ALTER TABLE chat
    RENAME COLUMN buyer_id TO initiator_id;

ALTER TABLE chat
    RENAME COLUMN seller_id TO ad_owner_id;