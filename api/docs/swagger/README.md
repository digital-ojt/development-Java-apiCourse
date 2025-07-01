# ドローン在庫管理API - モジュール別設計書

このディレクトリには、ドローン在庫管理APIをモジュール別に分割したSwagger設計書が格納されています。

## 📂 ファイル構成

### 共通コンポーネント
- **`shared-components.yaml`** - 全モジュールで使用する共通のスキーマ、レスポンス、セキュリティ定義

### 機能別モジュール

#### 認証モジュール
- **`auth-api.yaml`** - 認証画面関連API
  - ✅ ログイン機能
  - ✅ アカウント登録機能
  - 🚧 アカウント更新機能（今後実装予定）

#### カテゴリ管理モジュール
- **`parts-category-management-api.yaml`** - 部品カテゴリ管理画面関連API
  - ✅ 部品カテゴリ一覧取得
  - 🚧 部品カテゴリ追加・更新・削除（今後実装予定）

- **`product-category-management-api.yaml`** - 製品カテゴリ管理画面関連API
  - 🚧 製品カテゴリ一覧取得（今後実装予定）
  - 🚧 製品カテゴリ追加・更新・削除（今後実装予定）

#### 在庫管理モジュール
- **`parts-stock-management-api.yaml`** - 部品在庫管理画面関連API
  - ✅ 部品在庫一覧取得
  - ✅ カテゴリー別部品在庫取得
  - ✅ 部品在庫検索（詳細条件対応）
  - 🚧 部品在庫追加・更新・削除（今後実装予定）

- **`product-stock-management-api.yaml`** - 製品在庫管理画面関連API
  - 🚧 製品在庫一覧取得（今後実装予定）
  - 🚧 製品在庫検索・追加・更新・削除（今後実装予定）

#### センター情報管理モジュール
- **`center-info-management-api.yaml`** - 在庫センター情報管理画面関連API
  - 🚧 センター情報一覧取得・検索（今後実装予定）
  - 🚧 センター情報追加・更新・削除（今後実装予定）

## 📋 実装状況

### ✅ 実装済み
- **認証API**: ログイン、アカウント登録
- **部品カテゴリAPI**: 一覧取得
- **部品在庫API**: 一覧取得、カテゴリー別取得、検索機能

### 🚧 未実装機能
- **認証API**: アカウント更新、トークン検証
- **カテゴリ管理API**: 部品・製品カテゴリのCRUD操作
- **在庫管理API**: 部品・製品在庫のCRUD操作、一括更新
- **センター情報管理API**: 全機能（一覧取得・検索・CRUD操作）

詳細な実装状況については [API実装状況一覧](../API_STATUS.md) をご覧ください。

## 🔧 設計書の閲覧方法

### 1. オンライン表示（推奨）
[Swagger Editor](https://editor.swagger.io/)でYAMLファイルを開いて表示

```bash
# 例：認証APIの設計書を表示
# 1. https://editor.swagger.io/ を開く
# 2. File > Import file で auth-api.yaml を選択
# 3. または内容をコピー&ペーストして表示
```

### 2. ローカル環境での表示

#### Docker環境での表示
```bash
# リポジトリルートから実行
cd api/docs/swagger/modules

# 認証APIの例
docker run -p 8081:8080 -e SWAGGER_JSON=/app/auth-api.yaml \
  -v ${PWD}:/app swaggerapi/swagger-ui

# ブラウザで http://localhost:8081 を開く
```

#### Node.js環境での表示
```bash
# swagger-ui-serverをインストール
npm install -g swagger-ui-serve

# 設計書を表示（例：認証API）
swagger-ui-serve auth-api.yaml

# 自動的にブラウザが開きます
```

### 3. VS Code拡張機能での表示

**Swagger Viewer**拡張機能を使用：
1. VS CodeでYAMLファイルを開く
2. `Ctrl+Shift+P` > "Swagger: Preview current file"を実行
3. プレビューペインで設計書を表示

## 📖 共通コンポーネント参照について

各モジュールの設計書は`shared-components.yaml`の共通コンポーネントを参照しています。

```yaml
# 参照例
schema:
  $ref: './shared-components.yaml#/components/schemas/ErrorResponse'
```

オンライン環境で閲覧する際は、共通コンポーネントファイルも一緒にインポートするか、統合版の設計書（`../drone-inventory-api.yaml`）を使用してください。

## 🎯 使用場面別推奨設計書

### 開発チーム別の作業分担時
- **認証チーム**: `auth-api.yaml`
- **カテゴリ管理チーム**: `parts-category-management-api.yaml`, `product-category-management-api.yaml`
- **在庫管理チーム**: `parts-stock-management-api.yaml`, `product-stock-management-api.yaml`
- **センター管理チーム**: `center-info-management-api.yaml`

### 画面別の開発時
- **ログイン画面**: `auth-api.yaml`
- **部品管理画面**: `parts-category-management-api.yaml` + `parts-stock-management-api.yaml`
- **製品管理画面**: `product-category-management-api.yaml` + `product-stock-management-api.yaml`
- **センター管理画面**: `center-info-management-api.yaml`

### API実装の順序検討時
1. **フェーズ1**: `auth-api.yaml` + `parts-category-management-api.yaml` + `parts-stock-management-api.yaml`
2. **フェーズ2**: `product-category-management-api.yaml` + `product-stock-management-api.yaml`  
3. **フェーズ3**: `center-info-management-api.yaml`

## 🔗 関連ファイル

- **統合版設計書**: `../drone-inventory-api.yaml`（全モジュールを統合した完全版）
- **プロジェクト概要**: `../../../../README.md`
- **実装コード**: `../../../DroneInventorySystemApi/`

## 📝 メンテナンス方針

1. **共通コンポーネント**は`shared-components.yaml`に集約
2. **実装状況**は各ファイルのdescriptionで明記（✅実装済み、🚧今後実装予定）
3. **API仕様変更**時は関連する全モジュールファイルを更新
4. **新機能追加**時は適切なモジュールファイルに追加、必要に応じて新モジュール作成

## 📧 お問い合わせ

設計書に関するご質問やご要望は開発チームまでお気軽にお声がけください。

---

**最終更新**: 2024年12月15日  
**バージョン**: 1.0.0 