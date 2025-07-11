
DROP TABLE IF EXISTS images CASCADE;


CREATE TABLE images (
    id UUID PRIMARY KEY,
    image_path VARCHAR(255) NOT NULL,
    image_type VARCHAR(100)
);

ALTER TABLE product DROP CONSTRAINT IF EXISTS fk_product_image;
ALTER TABLE product DROP COLUMN IF EXISTS image_id;

ALTER TABLE product ADD COLUMN image_id UUID;
ALTER TABLE product ADD CONSTRAINT fk_product_image
    FOREIGN KEY(image_id) REFERENCES images(id);