BEGIN;
INSERT INTO users(name, mail)
VALUES
('山田太郎', 'taro@yamada.com'),
('鈴木一郎', 'ichiro@suzuki.us'),
('田中花子', 'hanako@tanaka.jp'),
('大空翼', 'tsubasa@ozora.esp'),
('孫悟空', 'goku@son.com'),
('石川五右衛門', 'goemon@ishikawa.org'),
('次元大助', 'daisuke@jigen.org');
COMMIT;

BEGIN;
INSERT INTO tel(kind, number, users_id)
VALUES
('自宅', '0123456789', 1),

('会社', '0987654321', 1),

('携帯', '09012345678', 1),

('会社', '0123456789', 3),

('携帯', '08088887777', 5),

('自宅', '0234560987', 6),

('携帯', '07077772222', 4),

('会社', '0333334444', 4);
COMMIT;
