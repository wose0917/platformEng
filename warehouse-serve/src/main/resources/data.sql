-- 插入仓库数据
INSERT INTO warehouses (name, location, capacity, used_capacity, manager, contact, phone, status, created_by, create_time, update_time)
VALUES 
('主仓库', '北京市朝阳区', 1000, 300, '张三', '李四', '13800138000', 'ACTIVE', 'admin', NOW(), NOW()),
('备用仓库', '北京市海淀区', 500, 100, '王五', '赵六', '13900139000', 'ACTIVE', 'admin', NOW(), NOW()),
('临时仓库', '北京市丰台区', 300, 50, '孙七', '周八', '13700137000', 'MAINTENANCE', 'admin', NOW(), NOW());

-- 插入基础数据
INSERT INTO base_data (code, name, type, description, enabled, sort_order, created_by, create_time, update_time)
VALUES 
('WH-STATUS-ACTIVE', '正常', 'WAREHOUSE_STATUS', '仓库状态-正常', true, 1, 'admin', NOW(), NOW()),
('WH-STATUS-MAINTENANCE', '维护中', 'WAREHOUSE_STATUS', '仓库状态-维护中', true, 2, 'admin', NOW(), NOW()),
('WH-STATUS-INACTIVE', '停用', 'WAREHOUSE_STATUS', '仓库状态-停用', true, 3, 'admin', NOW(), NOW()),
('INB-STATUS-DRAFT', '草稿', 'INBOUND_STATUS', '入库单状态-草稿', true, 1, 'admin', NOW(), NOW()),
('INB-STATUS-SUBMITTED', '已提交', 'INBOUND_STATUS', '入库单状态-已提交', true, 2, 'admin', NOW(), NOW()),
('INB-STATUS-PROCESSING', '处理中', 'INBOUND_STATUS', '入库单状态-处理中', true, 3, 'admin', NOW(), NOW()),
('INB-STATUS-COMPLETED', '已完成', 'INBOUND_STATUS', '入库单状态-已完成', true, 4, 'admin', NOW(), NOW());

-- 插入入库单数据
INSERT INTO inbounds (warehouse_id, inbound_number, supplier, inbound_date, approval_status, status, created_by, create_time, update_time)
VALUES 
(1, 'INB20240301001', '供应商A', NOW(), 'APPROVED', 'COMPLETED', 'admin', NOW(), NOW()),
(1, 'INB20240301002', '供应商B', NOW(), 'PENDING', 'SUBMITTED', 'admin', NOW(), NOW()),
(2, 'INB20240301003', '供应商C', NOW(), 'APPROVED', 'PROCESSING', 'admin', NOW(), NOW());

-- 插入入库记录数据
INSERT INTO inbound_records (inbound_id, warehouse_id, product_name, product_code, quantity, supplier, record_time, operator, status)
VALUES 
(1, 1, '笔记本电脑', 'LAPTOP001', 10, '供应商A', NOW(), 'admin', 'COMPLETED'),
(1, 1, '显示器', 'MONITOR001', 20, '供应商A', NOW(), 'admin', 'COMPLETED'),
(2, 1, '键盘', 'KEYBOARD001', 50, '供应商B', NOW(), 'admin', 'PENDING'),
(3, 2, '鼠标', 'MOUSE001', 100, '供应商C', NOW(), 'admin', 'COMPLETED'); 