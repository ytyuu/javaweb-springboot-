-- 创建人物出场表
CREATE TABLE IF NOT EXISTS character_appearance (
    id INT AUTO_INCREMENT PRIMARY KEY,
    character_name VARCHAR(100) NOT NULL COMMENT '人物名',
    appearance_chapters INT NOT NULL COMMENT '出场章数',
    force_name VARCHAR(50) COMMENT '所属势力'
);

-- 创建势力出场表
CREATE TABLE IF NOT EXISTS force_appearance (
    id INT AUTO_INCREMENT PRIMARY KEY,
    chapter_range VARCHAR(50) NOT NULL COMMENT '章数区间',
    wei_count INT DEFAULT 0 COMMENT '魏出场次数',
    shu_count INT DEFAULT 0 COMMENT '蜀出场次数',
    wu_count INT DEFAULT 0 COMMENT '吴出场次数',
    qun_count INT DEFAULT 0 COMMENT '群雄出场次数'
);

-- 创建边表
CREATE TABLE IF NOT EXISTS edges (
    id INT AUTO_INCREMENT PRIMARY KEY,
    src VARCHAR(100) NOT NULL COMMENT '源节点',
    dst VARCHAR(100) NOT NULL COMMENT '目标节点',
    weight INT NOT NULL COMMENT '权重'
);

-- 创建人物生命线表
CREATE TABLE IF NOT EXISTS characters (
  name VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  start INT NULL DEFAULT NULL,
  end INT NULL DEFAULT NULL,
  lifelong INT NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- 插入示例数据
INSERT INTO character_appearance (character_name, appearance_chapters, force_name) VALUES
('刘备', 68, '蜀'),
('关羽', 65, '蜀'),
('张飞', 58, '蜀'),
('曹操', 70, '魏'),
('孙权', 55, '吴'),
('诸葛亮', 60, '蜀'),
('周瑜', 45, '吴'),
('司马懿', 50, '魏');

INSERT INTO force_appearance (chapter_range, wei_count, shu_count, wu_count, qun_count) VALUES
('1-10', 8, 5, 6, 2),
('11-20', 12, 10, 8, 5),
('21-30', 18, 15, 12, 8),
('31-40', 25, 20, 18, 12),
('41-50', 30, 25, 22, 10),
('51-60', 28, 22, 20, 8),
('61-70', 20, 18, 15, 5);

-- 插入人物生命线数据
INSERT INTO characters VALUES ('关羽', 1, 97, 96);
INSERT INTO characters VALUES ('刘备', 1, 119, 118);
INSERT INTO characters VALUES ('司马懿', 39, 119, 80);
INSERT INTO characters VALUES ('吕布', 3, 60, 57);
INSERT INTO characters VALUES ('周瑜', 15, 75, 60);
INSERT INTO characters VALUES ('夏侯惇', 5, 91, 86);
INSERT INTO characters VALUES ('夏侯渊', 5, 116, 111);
INSERT INTO characters VALUES ('孙权', 7, 110, 103);

-- 插入边的示例数据
INSERT INTO edges (src, dst, weight) VALUES
('刘备', '关羽', 5),
('刘备', '张飞', 5),
('关羽', '张飞', 4),
('刘备', '诸葛亮', 6),
('曹操', '司马懿', 5),
('曹操', '夏侯惇', 4),
('孙权', '周瑜', 5),
('刘备', '赵云', 4),
('曹操', '张辽', 3);