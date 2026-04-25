-- 1. Remove a constraint antiga
ALTER TABLE vehicle
DROP CONSTRAINT viatura_viatura_status_check;

-- 2. Altera o valor padrão
ALTER TABLE vehicle
ALTER COLUMN viatura_status SET DEFAULT 'DISPONIVEL';

-- 3. Cria a nova constraint
ALTER TABLE vehicle
ADD CONSTRAINT viatura_viatura_status_check
CHECK (viatura_status IN ('DISPONIVEL', 'MANUTENCAO', 'EM USO', 'DESATIVADA'));