# Drone Inventory System API

## 概要
ドローンの在庫一覧表示ためのAPI。

## バックエンド

### ディレクトリ構成
DroneInventorySystemApi/
├── config/
│   └── SecurityConfig.java
├── consts/
│   └── Operation.java
├── controllers/
│   ├── CategoryController.java
│   └── StockController.java
├── dtos/
│   ├── CategoryDto.java
│   └── StockDto.java
├── entities/
│   ├── CategoryEntity.java
│   └── StockEntity.java
├── exceptions/
│   ├── NotFoundException.java
│   └── BadRequestException.java
├── models/
│   ├── Category.java
│   └── Stock.java
├── services/
│   ├── CategoryService.java
│   └── StockService.java
├── repositories/
│   ├── CategoryRepository.java
│   └── StockRepository.java
├── Application.java
└── application.properties

### ファイルの説明

- **config/SecurityConfig.java**: セキュリティ設定を定義するファイル。
- **consts/Operation.java**: 操作ログの定義
- **controllers/CategoryController.java**: カテゴリー情報に関するAPIエンドポイントを提供するコントローラー。
- **controllers/StockController.java**: 在庫情報に関するAPIエンドポイントを提供するコントローラー。
- **dtos/CategoryDto.java**: カテゴリー情報のデータ転送オブジェクト（DTO）を定義するファイル。
- **dtos/StockDto.java**: 在庫情報のデータ転送オブジェクト（DTO）を定義するファイル。
- **entities/CategoryEntity.java**: カテゴリーエンティティを定義するファイル。データベーステーブルに対応する。
- **entities/StockEntity.java**: 在庫エンティティを定義するファイル。データベーステーブルに対応する。
- **exceptions/NotFoundException.java**: リソースが見つからない場合にスローされる例外を定義するファイル。
- **exceptions/BadRequestException.java**: 不正なリクエストが行われた場合にスローされる例外を定義するファイル。
- **models/Category.java**: カテゴリーモデルを定義するファイル。カテゴリーのID、名前、説明などのプロパティを含む。
- **models/Stock.java**: 在庫モデルを定義するファイル。在庫のID、カテゴリーID、名前、数量などのプロパティを含む。
- **services/CategoryService.java**: カテゴリー情報のビジネスロジックを提供するサービス。カテゴリーの取得、追加、更新、削除などのメソッドを含む。
- **services/StockService.java**: 在庫情報のビジネスロジックを提供するサービス。在庫の取得、追加、更新、削除などのメソッドを含む。
- **repositories/CategoryRepository.java**: カテゴリーエンティティのリポジトリを定義するファイル。
- **repositories/StockRepository.java**: 在庫エンティティのリポジトリを定義するファイル。
- **Application.java**: アプリケーションのエントリーポイント。`main` メソッドを含み、アプリケーションの起動を行う。
- **application.properties**: アプリケーションの設定ファイル。

## フロントエンド

### ディレクトリ構成
api_front/
├── css/
│   ├── sb-admin-2.css
│   └── sb-admin-2.min.css
├── img/
├── index.html
└── js/
    └── script.js

### ファイルの説明

- **css/sb-admin-2.css**: 管理ダッシュボードのスタイルシート。
- **css/sb-admin-2.min.css**: 圧縮版の管理ダッシュボードのスタイルシート。
- **img/**: 画像ファイルを格納するディレクトリ。
- **index.html**: 在庫情報の表示ページ。
- **js/script.js**: 在庫情報を取得して表示するためのJavaScriptファイル。

### エンドポイント

#### カテゴリー情報の取得と表示
- **URL**: `/api/categoryinfo/init`
- **メソッド**: GET
- **説明**: カテゴリー情報を取得し、テーブル形式で表示します。

#### 在庫情報の取得と表示
- **URL**: `/api/stockinfo/init`
- **メソッド**: GET
- **説明**: 在庫情報を取得し、テーブル形式で表示します。

#### カテゴリー選択による名前情報の絞り込み
- **URL**: `/api/stockinfo/category`
- **メソッド**: GET
- **説明**: 選択されたカテゴリーに基づいて名前情報を取得し、セレクトボックスを更新します。

#### 在庫情報の検索
- **URL**: `/api/stockinfo/search`
- **メソッド**: GET
- **説明**: 検索条件に基づいて在庫情報を取得し、テーブル形式で表示します。
- **パラメータ**:
  - `categoryId` (オプション): カテゴリーID
  - `name` (オプション): 名前
  - `amountMin` (オプション): 最小個数
  - `amountMax` (オプション): 最大個数

## APIテスト

### ディレクトリ構成
api_test/
├── category_info.http
├── index.html
└── js/
    └── script.js
├── stock_info.http

### ファイルの説明

- **category_info.http**: カテゴリー情報APIのテストリクエストを含むHTTPファイル。
- **index.html**: カテゴリーリストの表示ページ。
- **js/script.js**: カテゴリー情報を取得して表示するためのJavaScriptファイル。
- **stock_info.http**: 在庫情報APIのテストリクエストを含むHTTPファイル。

### エンドポイント

#### カテゴリー情報の取得
- **URL**: `/api/categoryinfo/init`
- **メソッド**: GET
- **説明**: カテゴリー情報を取得し、テーブル形式で表示します。

#### 在庫情報の取得
- **URL**: `/api/stockinfo/init`
- **メソッド**: GET
- **説明**: すべての在庫情報を取得します。
