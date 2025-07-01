# ドローン在庫管理システム ドキュメント説明

本ディレクトリには、ドローン在庫管理システムの全体的なドキュメントが格納されています。

## 📁 ディレクトリ構成

```
docs/                                    # 全体統括ドキュメント
├── README.md                            # ドキュメント全体の概要、構成、読み方ガイド
├── requirements/                        # 要件定義フェーズのドキュメント群
│   ├── business-requirements.md         # 業務要件書：課題、目的、業務フロー、業務プロセス、ステークホルダー
│   ├── functional-requirements.md       # 機能要件書：ユーザーストーリーや機能一覧、ユースケース定義など
│   └── non-functional-requirements.md   # 非機能要件書：セキュリティ・パフォーマンス・可用性などの要求事項
└── architecture/                        # システム全体の基本設計
    ├── system-overview.md               # システム概要書：全体構成図、サブシステム関係
    ├── data-design.md                   # データ設計書：ER図、テーブル定義、データフロー
    ├── security.md                      # セキュリティ設計書：認証・認可、ログポリシー
    └── operation-design.md              # 運用設計書：バックアップ、障害対応、監視

api/                                     # APIサブシステム
├── docs/                                # API技術ドキュメント
│   ├── README.md                        # API ドキュメント概要
│   ├── architecture/                    # API アーキテクチャ設計
│   │   ├── system-overview.md           # API システム概要書：API構成図、コンポーネント図
│   │   ├── interface-design.md          # API インタフェース設計書：エンドポイント設計
│   │   └── security-design.md           # API セキュリティ設計書：JWT認証、認可設計
│   ├── swagger/                         # API 仕様書（OpenAPI）
│   │   ├── README.md                    # Swagger ドキュメント説明
│   │   ├── modules/                     # API モジュール別仕様
│   │   │   ├── ~.yaml
│   │   │   └── ~.yaml
│   └── detail/                          # API 詳細設計書
│       ├── README.md                    # 詳細設計書概要
│       ├── business-logic/              # ビジネスロジック詳細設計
│       │   ├── authentication-logic.md  # 認証処理詳細設計
│       │   ├── inventory-logic.md       # 在庫管理ロジック詳細設計
│       │   ├── category-logic.md        # カテゴリ管理ロジック詳細設計
│       │   └── validation-logic.md      # バリデーションロジック詳細設計
│       ├── data-access/                 # データアクセス詳細設計
│       │   ├── repository-design.md     # リポジトリパターン設計
│       │   ├── transaction-design.md    # トランザクション設計
│       │   └── query-optimization.md    # クエリ最適化設計
│       └── api-flow/                    # API処理フロー詳細設計
│           ├── login-flow.md            # ログイン処理フロー
│           ├── inventory-crud-flow.md   # 在庫CRUD処理フロー
│           ├── category-crud-flow.md    # カテゴリCRUD処理フロー
│           └── error-handling-flow.md   # エラーハンドリングフロー

batch/                                   # バッチサブシステム
├── docs/                                # バッチ技術ドキュメント
│   ├── README.md                        # バッチ ドキュメント概要
│   ├── architecture/                    # バッチ アーキテクチャ設計
│   │   ├── system-overview.md           # バッチ システム概要書：バッチ処理構成図
│   │   ├── data-flow-diagram.md         # データフローダイアグラム（DFD）
│   │   ├── job-design.md                # ジョブ設計書：バッチジョブ定義、スケジュール
│   │   └── error-handling-design.md     # エラーハンドリング設計書：例外処理、リトライ
│   └── job-specs/                       # ジョブ仕様書
│       ├── inventory-sync-job.md        # 在庫同期ジョブ仕様
│       ├── report-generation-job.md     # レポート生成ジョブ仕様
│       └── data-cleanup-job.md          # データクリーンアップジョブ仕様
└── src/                                 # バッチ実装

web/                                     # WEBサブシステム
├── docs/                                # WEB技術ドキュメント
│   ├── README.md                        # WEB ドキュメント概要
│   ├── architecture/                    # WEB アーキテクチャ設計
│   │   ├── system-overview.md           # WEB システム概要書：フロントエンド構成図
│   │   ├── screen-design.md             # 画面設計書：画面遷移図、UI/UXデザイン
│   │   ├── component-design.md          # コンポーネント設計書：再利用可能コンポーネント
│   │   └── state-management-design.md   # 状態管理設計書：データフロー、状態管理
│   ├── screen-specs/                    # 画面仕様書
│   │   ├── login-screen.md              # ログイン画面仕様
│   │   ├── dashboard-screen.md          # ダッシュボード画面仕様
│   │   ├── inventory-management-screen.md # 在庫管理画面仕様
│   │   └── report-screen.md             # レポート画面仕様
│   └── style-guide/                     # スタイルガイド
│       ├── ui-components.md             # UIコンポーネントガイド
│       └── design-tokens.md             # デザイントークン（色、フォント、スペース）
└── src/                                 # WEB実装
```

## 🎯 対象読者

### 要件定義書 (`requirements/`)

- 要件定義ドキュメント
- **業務要件**: プロジェクトマネージャー、ビジネスアナリスト、ステークホルダー
- **機能要件**: プロジェクトマネージャー、開発チーム、テストチーム
- **非機能要件**: アーキテクト、開発リーダー、インフラエンジニア

### 基本設計書 (`architecture/`)

- 基本設計ドキュメント
- **システム概要書**: アーキテクト、開発リーダー、プロジェクトマネージャー
- **データ設計書**: データベースエンジニア、バックエンド開発チーム
- **インタフェース設計書**: アーキテクト、開発チーム、外部システム連携担当者
- **セキュリティ設計書**: セキュリティエンジニア、アーキテクト、開発リーダー、インフラエンジニア
- **運用設計書**: インフラエンジニア、運用担当者、システム管理者、プロジェクトマネージャー

### API 仕様書 (`../api/docs/swagger/`)

- **API 設計書**: 開発チーム、テストチーム、外部システム連携担当者

### 詳細設計書 (`../api/docs/detail/`)

- **処理詳細設計**: バックエンド開発チーム、コードレビュアー、保守担当者

## ⚠️ 注意事項

### ドキュメント更新ルール

- **要件変更時**: `requirements/` 配下の該当ドキュメントを更新
- **基本設計変更時**: `architecture/` 配下の該当ドキュメントを更新
- **API 変更時**: `../api/docs/swagger/` 配下の該当仕様書を更新

#### API 関連ドキュメントの管理場所について

- API に関する技術的なドキュメントは全て `../api/docs/` 配下に集約
- **API 仕様書**（OpenAPI/Swagger）は **`../api/docs/swagger/`** 配下で管理
- **詳細設計書**（処理詳細・ビジネスロジック）は **`../api/docs/detail/`** 配下で管理
- **API 実装状況**は **`../api/docs/API_STATUS.md`** で確認可能
- **API テスト**は **`../api/api_test/`** 配下で管理
