ALTER TABLE user
DROP CONSTRAINT usuario_usuario_status_check;

ALTER TABLE user
ALTER COLUMN usuario_status SET DEFAULT 'DISPONIVEL';

ALTER TABLE user
ADD CONSTRAINT usuario_usuario_status_check
CHECK (usuario_status IN ('EM CAMPO', 'DISPONIVEL', 'DESLIGADO'));