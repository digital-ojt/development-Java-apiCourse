# 🏗️ 設計ドキュメント

## 📋 概要

このディレクトリには、**ドローン在庫管理システム（DroneInventorySystem）** の設計フェーズで作成された設計書群が含まれています。

---

## 📁 ディレクトリ構成

### 🏛️ [アーキテクチャ設計（architecture/）](./architecture/)

- **目的**: システム全体のアーキテクチャ設計書
- **対象読者**: システムアーキテクト、全開発チーム、インフラエンジニア
- **構成**:
  - 📄 **[system-overview.md](./architecture/system-overview.md)**: システム全体概要
  - 📄 **[data-design.md](./architecture/data-design.md)**: データ設計・データベース設計
  - 📄 **[operation-design.md](./architecture/operation-design.md)**: 運用設計・監視設計

### 📋 [要件定義（requirements/）](./requirements/)

- **目的**: システムの要件定義書群
- **対象読者**: プロジェクトマネージャー、ビジネスアナリスト、全開発チーム
- **構成**:
  - 📄 **[business-requirements.md](./requirements/business-requirements.md)**: ビジネス要件定義
  - 📄 **[functional-requirements.md](./requirements/functional-requirements.md)**: 機能要件定義
  - 📄 **[non-functional-requirements.md](./requirements/non-functional-requirements.md)**: 非機能要件定義

---

## 🔗 関連ディレクトリ

### 💻 [API 実装（../api/）](../api/)

- **目的**: API の実装プログラムと API 関連ドキュメント
- **対象読者**: バックエンドエンジニア、フロントエンドエンジニア
- **構成**:
  - 📄 **[../api/README.md](../api/README.md)**: API 実装概要
  - 📁 **[../api/docs/](../api/docs/)**: API 関連ドキュメント
    - 📄 **[interface-design.md](../api/docs/architecture/interface-design.md)**: API インターフェース設計書
    - 📄 **[system-overview.md](../api/docs/architecture/system-overview.md)**: API システム概要
    - 📁 **[swagger/](../api/docs/swagger/)**: OpenAPI 仕様書

---

## 🎯 設計書の関係性

```
┌─────────────────┐    ┌─────────────────┐
│   Requirements  │    │   Architecture  │
│   (要件定義)     │───►│   (設計)        │
└─────────────────┘    └─────────────────┘
                                │
                                ▼
                    ┌─────────────────┐
                    │  Implementation │
                    │  (../api/)      │
                    └─────────────────┘
```

---

## 📖 設計書閲覧ガイド

### 🔰 初回読者向け

1. **[要件定義](./requirements/)** でシステムの目的と要件を理解
2. **[アーキテクチャ設計](./architecture/)** でシステム設計を理解
3. **[API ドキュメント](../api/docs/)** で実装仕様を確認

### 👨‍💻 開発者向け

- **プロジェクト開始時**: `requirements/` → `architecture/` → `../api/docs/`
- **API 開発者**: `requirements/functional-requirements.md` → `../api/docs/architecture/interface-design.md`
- **データベース設計**: `architecture/data-design.md`

### 🔧 保守・運用者向け

- **システム運用**: `architecture/operation-design.md`
- **データベース保守**: `architecture/data-design.md`
- **API 監視**: `../api/docs/architecture/interface-design.md`

---

## 📂 プロジェクト全体構成

```
development-Java-apiCourse/
├── docs/                     # 設計ドキュメント（このディレクトリ）
│   ├── architecture/         # アーキテクチャ設計書
│   ├── requirements/         # 要件定義書
│   └── README.md            # このファイル
└── api/                     # API 実装
    ├── docs/                # API 関連ドキュメント
    ├── src/                 # ソースコード（予定）
    ├── tests/               # テストコード（予定）
    └── README.md           # API 実装概要
```

---

## 📌 更新履歴

| 日付       | バージョン | 更新内容                   | 更新者     |
| ---------- | ---------- | -------------------------- | ---------- |
| 2024/XX/XX | 1.1.0      | プロジェクト構造変更に対応 | 開発チーム |
| 2024/XX/XX | 1.0.0      | 初版作成                   | 開発チーム |

---

**💡 設計書に関する質問やフィードバックは、開発チームまでお気軽にお声がけください。**
