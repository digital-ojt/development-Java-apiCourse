# ドローン在庫管理システム データ設計書

## 📋 文書概要

| 項目       | 内容                                  |
| ---------- | ------------------------------------- |
| 文書名     | ドローン在庫管理システム データ設計書 |
| 作成日     | 2024 年 12 月 15 日                   |
| 最終更新日 | 2024 年 12 月 15 日                   |
| バージョン | 2.0.0                                 |
| 作成者     | Development Team                      |
| 承認者     | Database Architect                    |

## 🎯 対象読者

- データベースアーキテクト
- バックエンド開発チーム
- システムアーキテクト
- インフラエンジニア

---

## 1. データ設計概要

### 1.1 設計方針

#### 1.1.1 設計原則

- **業務分離**: 部品在庫管理と製品在庫管理の明確な分離
- **プロセス対応**: 購買・製造・販売の各プロセスに対応したデータ構造
- **トレーサビリティ**: 部品から製品までの完全な追跡可能性
- **拡張性**: 外部システム連携に対応可能な設計
- **整合性**: 参照整合性とビジネスルールの保証

#### 1.1.2 業務プロセスとデータモデルの対応

| 業務プロセス     | 対象データ | 主要テーブル                              |
| ---------------- | ---------- | ----------------------------------------- |
| 部品購買・入荷   | 部品在庫   | parts_stock, parts_warehouse_history      |
| 製造（部品出庫） | 部品在庫   | parts_stock, parts_warehouse_history      |
| 製造（製品入庫） | 製品在庫   | products_stock, products_shipping_history |
| 製品出荷         | 製品在庫   | products_stock, products_shipping_history |

#### 1.1.3 命名規則

- **テーブル名**: スネークケース、複数形（例：`parts_stock`）
- **カラム名**: スネークケース（例：`created_at`）
- **主キー**: `id`（AUTO_INCREMENT）
- **外部キー**: `参照テーブル名_id`（例：`category_id`）
- **インデックス**: `idx_テーブル名_カラム名`

### 1.2 データベース概要

#### 1.2.1 データベース基本情報

- **DBMS**: MySQL 8.0
- **文字セット**: utf8mb4
- **照合順序**: utf8mb4_unicode_ci

#### 1.2.2 データベース構成

- **マスター系**: 管理者情報、カテゴリ情報、センター情報
- **部品系**: 部品在庫、部品カテゴリ、部品入出庫履歴
- **製品系**: 製品在庫、製品カテゴリ、製品入出荷履歴
- **ログ系**: 操作ログ

---

## 2. ER 図（概念データモデル）

### 2.1 概要 ER 図

```
外部システム連携                    管理者・システム
┌─────────────────┐              ┌─────────────────┐
│  購買システム     │              │   AdminInfo     │
│ (部品入荷情報)    │              │   (管理者情報)    │
└─────────────────┘              ├─────────────────┤
         │                       │ id (PK)         │
         │ 連携                   │ username        │
         ▼                       │ password_hash   │
┌─────────────────┐              │ email           │
│生産管理システム   │              │ role            │
│(製造指示・完了)   │              │ created_at      │
└─────────────────┘              │ updated_at      │
         │                       └─────────────────┘
         │ 連携                           │
         ▼                               │ 1:N
┌─────────────────┐                     ▼
│ 販売管理システム  │            ┌─────────────────┐
│  (出荷指示)      │            │ OperationLog    │
└─────────────────┘            │ (操作ログ)       │
                              ├─────────────────┤
                              │ id (PK)         │
                              │ admin_id (FK)   │
                              │ operation_type  │
                              │ target_table    │
                              │ old_value       │
                              │ new_value       │
                              │ created_at      │
                              └─────────────────┘

センター・カテゴリ管理
┌─────────────────┐       ┌─────────────────┐       ┌─────────────────┐
│   CenterInfo    │       │ PartsCategory   │       │ProductsCategory │
│  (センター情報)   │       │ (部品カテゴリ)    │       │ (製品カテゴリ)    │
├─────────────────┤       ├─────────────────┤       ├─────────────────┤
│ id (PK)         │       │ id (PK)         │       │ id (PK)         │
│ center_code     │       │ category_code   │       │ category_code   │
│ center_name     │       │ category_name   │       │ category_name   │
│ location        │       │ description     │       │ description     │
│ manager_name    │       │ created_at      │       │ created_at      │
│ created_at      │       │ updated_at      │       │ updated_at      │
│ updated_at      │       └─────────────────┘       └─────────────────┘
└─────────────────┘                │                         │
         │                         │ 1:N                     │ 1:N
         │ 1:N                     ▼                         ▼
         │              ┌─────────────────┐       ┌─────────────────┐
         │              │   PartsStock    │       │  ProductsStock  │
         │              │   (部品在庫)     │       │   (製品在庫)     │
         │              ├─────────────────┤       ├─────────────────┤
         │              │ id (PK)         │       │ id (PK)         │
         │              │ stock_code      │       │ stock_code      │
         │              │ stock_name      │       │ stock_name      │
         │              │ category_id(FK) │       │ category_id(FK) │
         │              │ center_id (FK)  │       │ center_id (FK)  │
         │              │ current_stock   │       │ current_stock   │
         │              │ safety_stock    │       │ safety_stock    │
         │              │ max_stock       │       │ max_stock       │
         │              │ unit            │       │ unit            │
         │              │ unit_cost       │       │ unit_cost       │
         │              │ created_at      │       │ created_at      │
         │              │ updated_at      │       │ updated_at      │
         │              └─────────────────┘       └─────────────────┘
         │                       │                         │
         │                       │ 1:N                     │ 1:N
         │                       ▼                         ▼
         │      ┌─────────────────────────┐    ┌─────────────────────────┐
         │      │ PartsWarehouseHistory   │    │ProductsShippingHistory  │
         │      │   (部品入出庫履歴)     　  │    │   (製品入出荷履歴)       │
         │      ├─────────────────────────┤    ├─────────────────────────┤
         │      │ id (PK)                │    │ id (PK)                │
         │      │ parts_stock_id (FK)    │    │ products_stock_id (FK) │
         │      │ operation_type         │    │ operation_type         │
         │      │ reference_system       │    │ reference_system       │
         │      │ reference_no           │    │ reference_no           │
         │      │ quantity_change        │    │ quantity_change        │
         │      │ stock_before           │    │ stock_before           │
         │      │ stock_after            │    │ stock_after            │
         │      │ supplier_code          │    │ customer_code          │
         │      │ lot_number             │    │ manufacturing_lot      │
         │      │ operator_id            │    │ operator_id            │
         │      │ created_at             │    │ created_at             │
         │      └─────────────────────────┘    └─────────────────────────┘
         │
         └────────────────┬─────────────────────────┬─────────────────
                         │                         │
                         ▼                         ▼
            （外部キー参照）              （外部キー参照）
```

### 2.2 業務プロセス対応

#### 2.2.1 部品購買・入荷プロセス

```
購買システム → PartsStock (在庫増加) → PartsWarehouseHistory (入荷記録)
```

#### 2.2.2 製造プロセス

```
生産管理システム → PartsStock (部品出庫) → PartsWarehouseHistory (出庫記録)
                ↓
               ProductsStock (製品入庫) → ProductsShippingHistory (入庫記録)
```

#### 2.2.3 製品出荷プロセス

```
販売管理システム → ProductsStock (在庫減少) → ProductsShippingHistory (出荷記録)
```

---

## 3. テーブル定義

### 3.1 マスター系テーブル

#### 3.1.1 管理者情報テーブル（admin_infos）

```sql
CREATE TABLE admin_infos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE COMMENT 'ログインユーザー名',
    password_hash VARCHAR(255) NOT NULL COMMENT 'ハッシュ化パスワード',
    email VARCHAR(100) NOT NULL UNIQUE COMMENT 'メールアドレス',
    role ENUM('ADMIN', 'OPERATOR', 'VIEWER') NOT NULL DEFAULT 'VIEWER' COMMENT '権限レベル',
    is_active BOOLEAN NOT NULL DEFAULT TRUE COMMENT 'アクティブフラグ',
    last_login_at TIMESTAMP NULL COMMENT '最終ログイン日時',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日時',

    INDEX idx_admin_infos_username (username),
    INDEX idx_admin_infos_email (email),
    INDEX idx_admin_infos_role (role),
    INDEX idx_admin_infos_is_active (is_active)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='管理者情報';
```

#### 3.1.2 在庫センター情報テーブル（center_infos）

```sql
CREATE TABLE center_infos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    center_code VARCHAR(20) NOT NULL UNIQUE COMMENT 'センターコード',
    center_name VARCHAR(100) NOT NULL COMMENT 'センター名',
    center_type ENUM('MAIN', 'SUB', 'VIRTUAL') NOT NULL DEFAULT 'MAIN' COMMENT 'センター種別',
    location VARCHAR(100) NOT NULL COMMENT '所在地',
    address TEXT NOT NULL COMMENT '住所',
    postal_code VARCHAR(10) NULL COMMENT '郵便番号',
    phone_number VARCHAR(20) NULL COMMENT '電話番号',
    manager_name VARCHAR(50) NOT NULL COMMENT '管理者名',
    manager_email VARCHAR(100) NULL COMMENT '管理者メールアドレス',
    capacity_limit INT NULL COMMENT '保管容量上限',
    is_active BOOLEAN NOT NULL DEFAULT TRUE COMMENT 'アクティブフラグ',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日時',

    INDEX idx_center_infos_code (center_code),
    INDEX idx_center_infos_type (center_type),
    INDEX idx_center_infos_location (location),
    INDEX idx_center_infos_active (is_active)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='在庫センター情報';
```

### 3.2 部品系テーブル

#### 3.2.1 部品カテゴリテーブル（parts_categories）

```sql
CREATE TABLE parts_categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category_code VARCHAR(20) NOT NULL UNIQUE COMMENT 'カテゴリコード',
    category_name VARCHAR(100) NOT NULL COMMENT 'カテゴリ名',
    parent_category_id BIGINT NULL COMMENT '親カテゴリID（階層構造）',
    level TINYINT NOT NULL DEFAULT 1 COMMENT '階層レベル',
    sort_order INT NOT NULL DEFAULT 0 COMMENT '表示順序',
    description TEXT NULL COMMENT '説明',
    is_active BOOLEAN NOT NULL DEFAULT TRUE COMMENT 'アクティブフラグ',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日時',

    INDEX idx_parts_categories_code (category_code),
    INDEX idx_parts_categories_parent (parent_category_id),
    INDEX idx_parts_categories_level (level),
    INDEX idx_parts_categories_sort (sort_order),
    INDEX idx_parts_categories_active (is_active),

    FOREIGN KEY fk_parts_category_parent (parent_category_id)
        REFERENCES parts_categories(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='部品カテゴリ情報';
```

#### 3.2.2 部品在庫テーブル（parts_stock）

```sql
CREATE TABLE parts_stock (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    stock_code VARCHAR(30) NOT NULL UNIQUE COMMENT '部品在庫コード',
    stock_name VARCHAR(200) NOT NULL COMMENT '部品名',
    category_id BIGINT NOT NULL COMMENT '部品カテゴリID',
    center_id BIGINT NOT NULL COMMENT 'センターID',
    current_stock INT NOT NULL DEFAULT 0 COMMENT '現在在庫数',
    safety_stock INT NOT NULL DEFAULT 0 COMMENT '安全在庫数',
    max_stock INT NOT NULL DEFAULT 999999 COMMENT '最大在庫数',
    reorder_point INT NOT NULL DEFAULT 0 COMMENT '発注点',
    unit VARCHAR(10) NOT NULL DEFAULT '個' COMMENT '単位',
    unit_cost DECIMAL(12,2) NOT NULL DEFAULT 0.00 COMMENT '単価',
    standard_cost DECIMAL(12,2) NULL COMMENT '標準原価',
    supplier_code VARCHAR(20) NULL COMMENT '主要仕入先コード',
    supplier_name VARCHAR(100) NULL COMMENT '主要仕入先名',
    lead_time_days INT NULL COMMENT 'リードタイム（日数）',
    part_number VARCHAR(50) NULL COMMENT '部品番号',
    manufacturer VARCHAR(100) NULL COMMENT 'メーカー名',
    specifications TEXT NULL COMMENT '仕様',
    shelf_location VARCHAR(50) NULL COMMENT '棚番・保管場所',
    is_active BOOLEAN NOT NULL DEFAULT TRUE COMMENT 'アクティブフラグ',
    last_receipt_date DATE NULL COMMENT '最終入荷日',
    last_issue_date DATE NULL COMMENT '最終出庫日',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日時',

    INDEX idx_parts_stock_code (stock_code),
    INDEX idx_parts_stock_name (stock_name),
    INDEX idx_parts_stock_category (category_id),
    INDEX idx_parts_stock_center (center_id),
    INDEX idx_parts_stock_current_stock (current_stock),
    INDEX idx_parts_stock_safety_stock (safety_stock),
    INDEX idx_parts_stock_active (is_active),
    INDEX idx_parts_stock_supplier (supplier_code),
    INDEX idx_parts_stock_part_number (part_number),
    INDEX idx_parts_stock_composite (category_id, center_id, is_active),
    INDEX idx_parts_stock_alert (current_stock, safety_stock, max_stock),

    FOREIGN KEY fk_parts_stock_category (category_id)
        REFERENCES parts_categories(id) ON DELETE RESTRICT,
    FOREIGN KEY fk_parts_stock_center (center_id)
        REFERENCES center_infos(id) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='部品在庫情報';
```

#### 3.2.3 部品入出庫履歴テーブル（parts_warehouse_history）

```sql
CREATE TABLE parts_warehouse_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    parts_stock_id BIGINT NOT NULL COMMENT '部品在庫ID',
    operation_type ENUM('RECEIPT', 'RECEIPT_CANCEL', 'ISSUE', 'ISSUE_CANCEL', 'ADJUSTMENT', 'TRANSFER') NOT NULL COMMENT '操作種別',
    reference_system ENUM('PURCHASE', 'PRODUCTION', 'MANUAL', 'SYSTEM') NOT NULL COMMENT '参照システム',
    reference_no VARCHAR(50) NOT NULL COMMENT '参照番号（発注番号、製造指示番号等）',
    quantity_change INT NOT NULL COMMENT '数量変動（正:入庫、負:出庫）',
    stock_before INT NOT NULL COMMENT '変動前在庫数',
    stock_after INT NOT NULL COMMENT '変動後在庫数',
    unit_cost DECIMAL(12,2) NULL COMMENT '単価',
    total_amount DECIMAL(15,2) NULL COMMENT '金額',
    lot_number VARCHAR(50) NULL COMMENT 'ロット番号',
    expiry_date DATE NULL COMMENT '有効期限',
    supplier_code VARCHAR(20) NULL COMMENT '仕入先コード',
    supplier_name VARCHAR(100) NULL COMMENT '仕入先名',
    manufacturing_lot VARCHAR(50) NULL COMMENT '製造ロット番号（出庫時）',
    remarks TEXT NULL COMMENT '備考',
    operator_id BIGINT NOT NULL COMMENT '操作者ID',
    operation_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作日時',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',

    INDEX idx_parts_warehouse_history_stock (parts_stock_id),
    INDEX idx_parts_warehouse_history_operation (operation_type),
    INDEX idx_parts_warehouse_history_system (reference_system),
    INDEX idx_parts_warehouse_history_reference (reference_no),
    INDEX idx_parts_warehouse_history_date (operation_date),
    INDEX idx_parts_warehouse_history_operator (operator_id),
    INDEX idx_parts_warehouse_history_lot (lot_number),
    INDEX idx_parts_warehouse_history_supplier (supplier_code),
    INDEX idx_parts_warehouse_history_composite (parts_stock_id, operation_date DESC),

    FOREIGN KEY fk_parts_warehouse_history_stock (parts_stock_id)
        REFERENCES parts_stock(id) ON DELETE RESTRICT,
    FOREIGN KEY fk_parts_warehouse_history_operator (operator_id)
        REFERENCES admin_infos(id) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='部品入出庫履歴'
PARTITION BY RANGE (YEAR(operation_date)) (
    PARTITION p2024 VALUES LESS THAN (2025),
    PARTITION p2025 VALUES LESS THAN (2026),
    PARTITION p_future VALUES LESS THAN MAXVALUE
);
```

### 3.3 製品系テーブル

#### 3.3.1 製品カテゴリテーブル（products_categories）

```sql
CREATE TABLE products_categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category_code VARCHAR(20) NOT NULL UNIQUE COMMENT 'カテゴリコード',
    category_name VARCHAR(100) NOT NULL COMMENT 'カテゴリ名',
    parent_category_id BIGINT NULL COMMENT '親カテゴリID（階層構造）',
    level TINYINT NOT NULL DEFAULT 1 COMMENT '階層レベル',
    sort_order INT NOT NULL DEFAULT 0 COMMENT '表示順序',
    description TEXT NULL COMMENT '説明',
    is_active BOOLEAN NOT NULL DEFAULT TRUE COMMENT 'アクティブフラグ',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日時',

    INDEX idx_products_categories_code (category_code),
    INDEX idx_products_categories_parent (parent_category_id),
    INDEX idx_products_categories_level (level),
    INDEX idx_products_categories_sort (sort_order),
    INDEX idx_products_categories_active (is_active),

    FOREIGN KEY fk_products_category_parent (parent_category_id)
        REFERENCES products_categories(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='製品カテゴリ情報';
```

#### 3.3.2 製品在庫テーブル（products_stock）

```sql
CREATE TABLE products_stock (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    stock_code VARCHAR(30) NOT NULL UNIQUE COMMENT '製品在庫コード',
    stock_name VARCHAR(200) NOT NULL COMMENT '製品名',
    category_id BIGINT NOT NULL COMMENT '製品カテゴリID',
    center_id BIGINT NOT NULL COMMENT 'センターID',
    current_stock INT NOT NULL DEFAULT 0 COMMENT '現在在庫数',
    safety_stock INT NOT NULL DEFAULT 0 COMMENT '安全在庫数',
    max_stock INT NOT NULL DEFAULT 999999 COMMENT '最大在庫数',
    reorder_point INT NOT NULL DEFAULT 0 COMMENT '発注点',
    unit VARCHAR(10) NOT NULL DEFAULT '台' COMMENT '単位',
    unit_cost DECIMAL(12,2) NOT NULL DEFAULT 0.00 COMMENT '単価',
    standard_cost DECIMAL(12,2) NULL COMMENT '標準原価',
    product_model VARCHAR(50) NULL COMMENT '製品モデル',
    product_version VARCHAR(20) NULL COMMENT '製品バージョン',
    warranty_period_months INT NULL COMMENT '保証期間（月数）',
    weight_kg DECIMAL(8,2) NULL COMMENT '重量（kg）',
    dimensions VARCHAR(100) NULL COMMENT '寸法（W×D×H）',
    color VARCHAR(50) NULL COMMENT 'カラー',
    specifications TEXT NULL COMMENT '仕様',
    shelf_location VARCHAR(50) NULL COMMENT '棚番・保管場所',
    is_active BOOLEAN NOT NULL DEFAULT TRUE COMMENT 'アクティブフラグ',
    last_production_date DATE NULL COMMENT '最終製造日',
    last_shipment_date DATE NULL COMMENT '最終出荷日',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日時',

    INDEX idx_products_stock_code (stock_code),
    INDEX idx_products_stock_name (stock_name),
    INDEX idx_products_stock_category (category_id),
    INDEX idx_products_stock_center (center_id),
    INDEX idx_products_stock_current_stock (current_stock),
    INDEX idx_products_stock_safety_stock (safety_stock),
    INDEX idx_products_stock_active (is_active),
    INDEX idx_products_stock_model (product_model),
    INDEX idx_products_stock_composite (category_id, center_id, is_active),
    INDEX idx_products_stock_alert (current_stock, safety_stock, max_stock),

    FOREIGN KEY fk_products_stock_category (category_id)
        REFERENCES products_categories(id) ON DELETE RESTRICT,
    FOREIGN KEY fk_products_stock_center (center_id)
        REFERENCES center_infos(id) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='製品在庫情報';
```

#### 3.3.3 製品入出荷履歴テーブル（products_shipping_history）

```sql
CREATE TABLE products_shipping_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    products_stock_id BIGINT NOT NULL COMMENT '製品在庫ID',
    operation_type ENUM('PRODUCTION_IN', 'PRODUCTION_CANCEL', 'SHIPMENT', 'SHIPMENT_CANCEL', 'ADJUSTMENT', 'TRANSFER') NOT NULL COMMENT '操作種別',
    reference_system ENUM('PRODUCTION', 'SALES', 'MANUAL', 'SYSTEM') NOT NULL COMMENT '参照システム',
    reference_no VARCHAR(50) NOT NULL COMMENT '参照番号（製造番号、出荷指示番号等）',
    quantity_change INT NOT NULL COMMENT '数量変動（正:入庫、負:出荷）',
    stock_before INT NOT NULL COMMENT '変動前在庫数',
    stock_after INT NOT NULL COMMENT '変動後在庫数',
    unit_cost DECIMAL(12,2) NULL COMMENT '単価',
    total_amount DECIMAL(15,2) NULL COMMENT '金額',
    manufacturing_lot VARCHAR(50) NULL COMMENT '製造ロット番号',
    serial_numbers TEXT NULL COMMENT 'シリアル番号（JSON配列）',
    quality_check_result ENUM('PASS', 'FAIL', 'PENDING') NULL COMMENT '品質検査結果',
    customer_code VARCHAR(20) NULL COMMENT '顧客コード',
    customer_name VARCHAR(100) NULL COMMENT '顧客名',
    delivery_address TEXT NULL COMMENT '配送先住所',
    expected_delivery_date DATE NULL COMMENT '配送予定日',
    actual_delivery_date DATE NULL COMMENT '実配送日',
    remarks TEXT NULL COMMENT '備考',
    operator_id BIGINT NOT NULL COMMENT '操作者ID',
    operation_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作日時',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',

    INDEX idx_products_shipping_history_stock (products_stock_id),
    INDEX idx_products_shipping_history_operation (operation_type),
    INDEX idx_products_shipping_history_system (reference_system),
    INDEX idx_products_shipping_history_reference (reference_no),
    INDEX idx_products_shipping_history_date (operation_date),
    INDEX idx_products_shipping_history_operator (operator_id),
    INDEX idx_products_shipping_history_lot (manufacturing_lot),
    INDEX idx_products_shipping_history_customer (customer_code),
    INDEX idx_products_shipping_history_composite (products_stock_id, operation_date DESC),

    FOREIGN KEY fk_products_shipping_history_stock (products_stock_id)
        REFERENCES products_stock(id) ON DELETE RESTRICT,
    FOREIGN KEY fk_products_shipping_history_operator (operator_id)
        REFERENCES admin_infos(id) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='製品入出荷履歴'
PARTITION BY RANGE (YEAR(operation_date)) (
    PARTITION p2024 VALUES LESS THAN (2025),
    PARTITION p2025 VALUES LESS THAN (2026),
    PARTITION p_future VALUES LESS THAN MAXVALUE
);
```

### 3.4 ログ系テーブル

#### 3.4.1 システム操作ログテーブル（operation_logs）

```sql
CREATE TABLE operation_logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    admin_id BIGINT NULL COMMENT '管理者ID',
    operation_type VARCHAR(50) NOT NULL COMMENT '操作種別',
    target_table VARCHAR(50) NOT NULL COMMENT '対象テーブル名',
    target_id BIGINT NULL COMMENT '対象レコードID',
    action ENUM('CREATE', 'READ', 'UPDATE', 'DELETE', 'LOGIN', 'LOGOUT') NOT NULL COMMENT 'アクション',
    old_values JSON NULL COMMENT '変更前の値（JSON形式）',
    new_values JSON NULL COMMENT '変更後の値（JSON形式）',
    ip_address VARCHAR(45) NOT NULL COMMENT 'IPアドレス（IPv6対応）',
    user_agent TEXT NULL COMMENT 'ユーザーエージェント',
    session_id VARCHAR(100) NULL COMMENT 'セッションID',
    request_id VARCHAR(100) NULL COMMENT 'リクエストID',
    processing_time_ms INT NULL COMMENT '処理時間（ミリ秒）',
    result_status ENUM('SUCCESS', 'FAILURE', 'ERROR') NOT NULL COMMENT '実行結果',
    error_message TEXT NULL COMMENT 'エラーメッセージ',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',

    INDEX idx_operation_logs_admin (admin_id),
    INDEX idx_operation_logs_operation (operation_type),
    INDEX idx_operation_logs_table (target_table),
    INDEX idx_operation_logs_action (action),
    INDEX idx_operation_logs_date (created_at),
    INDEX idx_operation_logs_ip (ip_address),
    INDEX idx_operation_logs_status (result_status),
    INDEX idx_operation_logs_composite (admin_id, created_at DESC),

    FOREIGN KEY fk_log_admin (admin_id)
        REFERENCES admin_infos(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='システム操作ログ'
PARTITION BY RANGE (TO_DAYS(created_at)) (
    PARTITION p202412 VALUES LESS THAN (TO_DAYS('2025-01-01')),
    PARTITION p202501 VALUES LESS THAN (TO_DAYS('2025-02-01')),
    PARTITION p_future VALUES LESS THAN MAXVALUE
);
```

---

## 4. ビジネスルール・制約

### 4.1 在庫データ制約

#### 4.1.1 部品在庫制約

```sql
-- 部品在庫数は非負値
ALTER TABLE parts_stock ADD CONSTRAINT chk_parts_current_stock_positive
    CHECK (current_stock >= 0);

-- 安全在庫は最大在庫以下
ALTER TABLE parts_stock ADD CONSTRAINT chk_parts_safety_max_stock
    CHECK (safety_stock <= max_stock);

-- 単価は正値
ALTER TABLE parts_stock ADD CONSTRAINT chk_parts_unit_cost_positive
    CHECK (unit_cost >= 0);
```

#### 4.1.2 製品在庫制約

```sql
-- 製品在庫数は非負値
ALTER TABLE products_stock ADD CONSTRAINT chk_products_current_stock_positive
    CHECK (current_stock >= 0);

-- 安全在庫は最大在庫以下
ALTER TABLE products_stock ADD CONSTRAINT chk_products_safety_max_stock
    CHECK (safety_stock <= max_stock);

-- 単価は正値
ALTER TABLE products_stock ADD CONSTRAINT chk_products_unit_cost_positive
    CHECK (unit_cost >= 0);
```

### 4.2 業務ルールビュー

#### 4.2.1 部品在庫アラートビュー

```sql
CREATE VIEW parts_stock_alert_view AS
SELECT
    p.id,
    p.stock_code,
    p.stock_name,
    p.current_stock,
    p.safety_stock,
    p.max_stock,
    c.center_name,
    cat.category_name,
    CASE
        WHEN p.current_stock = 0 THEN 'OUT_OF_STOCK'
        WHEN p.current_stock < p.safety_stock THEN 'LOW_STOCK'
        WHEN p.current_stock > p.max_stock THEN 'OVERSTOCKED'
        ELSE 'NORMAL'
    END as alert_status,
    CASE
        WHEN p.current_stock = 0 THEN '欠品'
        WHEN p.current_stock < p.safety_stock THEN '在庫不足'
        WHEN p.current_stock > p.max_stock THEN '過剰在庫'
        ELSE '正常'
    END as alert_message
FROM parts_stock p
JOIN center_infos c ON p.center_id = c.id
JOIN parts_categories cat ON p.category_id = cat.id
WHERE p.is_active = TRUE;
```

#### 4.2.2 製品在庫アラートビュー

```sql
CREATE VIEW products_stock_alert_view AS
SELECT
    p.id,
    p.stock_code,
    p.stock_name,
    p.current_stock,
    p.safety_stock,
    p.max_stock,
    c.center_name,
    cat.category_name,
    CASE
        WHEN p.current_stock = 0 THEN 'OUT_OF_STOCK'
        WHEN p.current_stock < p.safety_stock THEN 'LOW_STOCK'
        WHEN p.current_stock > p.max_stock THEN 'OVERSTOCKED'
        ELSE 'NORMAL'
    END as alert_status,
    CASE
        WHEN p.current_stock = 0 THEN '欠品'
        WHEN p.current_stock < p.safety_stock THEN '在庫不足'
        WHEN p.current_stock > p.max_stock THEN '過剰在庫'
        ELSE '正常'
    END as alert_message
FROM products_stock p
JOIN center_infos c ON p.center_id = c.id
JOIN products_categories cat ON p.category_id = cat.id
WHERE p.is_active = TRUE;
```

### 4.3 外部システム連携制約

#### 4.3.1 部品入出庫処理制約

```sql
-- 部品出庫時の在庫不足チェック
ALTER TABLE parts_warehouse_history ADD CONSTRAINT chk_parts_sufficient_stock
    CHECK (
        operation_type IN ('RECEIPT', 'ADJUSTMENT') OR
        stock_after >= 0
    );
```

#### 4.3.2 製品入出荷処理制約

```sql
-- 製品出荷時の在庫不足チェック
ALTER TABLE products_shipping_history ADD CONSTRAINT chk_products_sufficient_stock
    CHECK (
        operation_type IN ('PRODUCTION_IN', 'ADJUSTMENT') OR
        stock_after >= 0
    );
```

---

## 5. インデックス最適化

### 5.1 部品系パフォーマンス最適化

```sql
-- 部品検索の複合インデックス
CREATE INDEX idx_parts_stock_search
ON parts_stock (category_id, center_id, is_active, stock_name);

-- 部品アラート用インデックス
CREATE INDEX idx_parts_stock_alert
ON parts_stock (is_active, current_stock, safety_stock, max_stock);

-- 部品履歴検索の複合インデックス
CREATE INDEX idx_parts_history_search
ON parts_warehouse_history (parts_stock_id, operation_date DESC, operation_type);
```

### 5.2 製品系パフォーマンス最適化

```sql
-- 製品検索の複合インデックス
CREATE INDEX idx_products_stock_search
ON products_stock (category_id, center_id, is_active, stock_name);

-- 製品アラート用インデックス
CREATE INDEX idx_products_stock_alert
ON products_stock (is_active, current_stock, safety_stock, max_stock);

-- 製品履歴検索の複合インデックス
CREATE INDEX idx_products_history_search
ON products_shipping_history (products_stock_id, operation_date DESC, operation_type);
```

---

## 6. セキュリティ・監査

### 6.1 データベースユーザー権限

```sql
-- アプリケーション用ユーザー
CREATE USER 'drone_app'@'%' IDENTIFIED BY 'strong_password';
GRANT SELECT, INSERT, UPDATE, DELETE ON drone_inventory.* TO 'drone_app'@'%';

-- 読み取り専用ユーザー（レポート用）
CREATE USER 'drone_readonly'@'%' IDENTIFIED BY 'readonly_password';
GRANT SELECT ON drone_inventory.* TO 'drone_readonly'@'%';
```

### 6.2 監査証跡

#### 6.2.1 在庫変動の完全記録

- 全ての在庫変動は履歴テーブルに記録
- 外部システム連携情報も含めた完全なトレーサビリティ
- 操作者・操作日時・参照システムの記録

#### 6.2.2 アクセスログ管理

- 全システム操作のログ記録
- 不正アクセス・異常操作の検知
- 内部統制・監査対応

---

## 7. 主要な改訂点

### 7.1 要件・UI 設計対応

#### 7.1.1 部品・製品の分離

- **旧設計**: `stock_infos` で部品・製品を統合管理
- **新設計**: `parts_stock` と `products_stock` で分離管理
- **理由**: UI 設計で `/parts-stock` と `/products-stock` が別画面として設計されており、業務プロセスも明確に分離されているため

#### 7.1.2 カテゴリの分離

- **旧設計**: `category_infos` で部品・製品カテゴリを統合管理
- **新設計**: `parts_categories` と `products_categories` で分離管理
- **理由**: UI 設計で `/parts-categories` と `/products-categories` が別画面として設計されているため

#### 7.1.3 履歴の分離

- **旧設計**: `stock_histories` で全ての在庫変動を統合管理
- **新設計**: `parts_warehouse_history`（入出庫履歴）と `products_shipping_history`（入出荷履歴）で分離管理
- **理由**: 業務プロセスが明確に分離され、UI でも別画面として管理されるため

### 7.2 外部システム連携対応

#### 7.2.1 業務プロセス追跡

- **参照システム**: `reference_system` で購買・生産・販売システムを明確に区別
- **参照番号**: `reference_no` で外部システムの伝票番号・指示番号を記録
- **トレーサビリティ**: 製造ロット・シリアル番号による完全な追跡機能

#### 7.2.2 データ項目の充実

- **部品系**: 仕入先情報、リードタイム、部品番号、メーカー情報の追加
- **製品系**: 製品モデル、バージョン、保証期間、重量・寸法、品質検査結果の追加
- **履歴系**: 出荷先情報、配送日程、品質情報の追加

### 7.3 パフォーマンス・運用対応

#### 7.3.1 インデックス最適化

- **検索パフォーマンス**: カテゴリ・センター・アクティブ状態での複合インデックス
- **アラート機能**: 在庫レベル監視用の専用インデックス
- **履歴検索**: 在庫 ID・日付・操作種別での高速検索インデックス

#### 7.3.2 パーティショニング

- **履歴テーブル**: 年次パーティショニングによる検索パフォーマンス向上
- **ログテーブル**: 月次パーティショニングによる管理効率化

---

## 📋 承認履歴

| バージョン | 更新日     | 更新者           | 承認者             | 更新内容                                                                    |
| ---------- | ---------- | ---------------- | ------------------ | --------------------------------------------------------------------------- |
| 2.0.0      | 2024-12-15 | Development Team | Database Architect | 要件・UI 設計に基づく大幅改訂（部品・製品分離、入出庫・入出荷履歴分離など） |

---

## 📚 関連ドキュメント

- [機能要件書](../requirements/functional-requirements.md)
- [UI 設計書](./ui-design.md)
- [システム概要書](./system-overview.md)
- [API 仕様書](../../api/docs/swagger/)
- [API 実装状況](../../api/docs/API_STATUS.md)
