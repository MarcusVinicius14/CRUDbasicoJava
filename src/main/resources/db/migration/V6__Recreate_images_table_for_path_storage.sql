-- Remove a tabela de imagens antiga, se existir, para recriá-la com a estrutura correta.
DROP TABLE IF EXISTS images CASCADE;

-- Cria a nova tabela 'images' para armazenar o CAMINHO dos arquivos.
CREATE TABLE images (
    id UUID PRIMARY KEY,
    image_path VARCHAR(255) NOT NULL,
    image_type VARCHAR(100)
);

-- Garante que a tabela 'products' tenha a coluna de chave estrangeira.
-- Se a coluna já existe de uma migração anterior, este comando pode falhar.
-- É mais seguro remover a restrição e a coluna antes de adicioná-las novamente,
-- para garantir um estado limpo.

-- Remove a restrição e a coluna antigas para garantir que possamos recriá-las corretamente
ALTER TABLE product DROP CONSTRAINT IF EXISTS fk_product_image;
ALTER TABLE product DROP COLUMN IF EXISTS image_id;

-- Adiciona a coluna e a restrição de chave estrangeira novamente
ALTER TABLE product ADD COLUMN image_id UUID;
ALTER TABLE product ADD CONSTRAINT fk_product_image
    FOREIGN KEY(image_id) REFERENCES images(id);