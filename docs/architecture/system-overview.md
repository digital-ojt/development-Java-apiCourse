# システム設計書

## 概要

ドローン在庫管理システムのアーキテクチャとシステム設計に関するドキュメントです。

## システム構成

### 全体構成

#### システム間の関係

```
 外部システム                    本システム                   データ層
┌─────────────┐
│ 購買システム  │ ──────[部品入荷情報]─────┐
│             │ ──────[入荷取消情報]─────┤
└─────────────┘                        │
                                       │
┌─────────────┐                        │
│生産管理システム│ ─────[製造指示情報]──────┤
│             │ ─────[製品入庫情報]──────┤
└─────────────┘                        │
                                       │
┌─────────────┐                        │
│販売管理システム│ ──────[出荷指示情報]─────┤
│             │ ──────[出荷取消情報]─────┤
└─────────────┘                        │
                                       ▼
                              ┌──────────────┐
                              │              │
                              │ 在庫管理システム │ ◄─────┐
                              │  (本システム)  │        │
                              │              │        │
                              └──────┬───────┘        │
                                     │                │
                              [在庫情報提供]            │
                                     │                │
                                     ▼                │
                          ┌─────────────────┐         │
                          │各システムへ情報配信│         │
                          └─────────────────┘         │
                                                      ▼
                                            ┌─────────────┐
                                            │ データベース  │
                                            │  (MySQL)   │
                                            └─────────────┘
```

#### システム内部構成

```
┌─────────────────────────────────────────────────────────┐
│                   在庫管理システム                        │
├─────────────────────────────────────────────────────────┤
│                                                       │
│  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐  │
│  │ フロントエンド │    │   API層     │    │  データ層   │  │
│  │             │    │             │    │             │  │
│  │• React SPA  │◄──►│• REST API   │◄──►│• MySQL DB   │  │
│  │• JWT認証     │    │• Spring Boot│    │• JPA/ORM    │  │
│  │• UI/UX      │    │• 認証・認可   │    │• トランザクション│  │
│  └─────────────┘    └─────────────┘    └─────────────┘  │
│                                                       │
│  ┌─────────────────────────────────────────────────────┐  │
│  │                  ビジネス機能                       │  │
│  │                                                   │  │
│  │ ┌──────────┐ ┌──────────┐ ┌──────────┐ ┌──────────┐ │  │
│  │ │部品在庫管理│ │製品在庫管理│ │アラート管理│ │履歴管理  │ │  │
│  │ │          │ │          │ │          │ │          │ │  │
│  │ │•入荷処理  │ │•製造入庫  │ │•在庫監視  │ │•操作ログ  │ │  │
│  │ │•製造出庫  │ │•出荷処理  │ │•アラート  │ │•入出庫    │ │  │
│  │ │•在庫参照  │ │•在庫参照  │ │•通知機能  │ │•入出荷    │ │  │
│  │ └──────────┘ └──────────┘ └──────────┘ └──────────┘ │  │
│  │                                                   │  │
│  │ ┌─────────────┐ ┌─────────────┐ ┌─────────────┐     │  │
│  │ │部品カテゴリ管理│ │製品カテゴリ管理│ │センター情報管理│     │  │
│  │ │             │ │             │ │             │     │  │
│  │ │•カテゴリ登録  │ │•カテゴリ登録  │ │•センター登録  │     │  │
│  │ │•階層管理     │ │•用途別分類    │ │•拠点情報管理  │     │  │
│  │ │•検索・参照    │ │•検索・参照    │ │•検索・参照   │     │  │
│  │ └─────────────┘ └─────────────┘ └─────────────┘     │  │
│  └─────────────────────────────────────────────────────┘  │
│                                                       │
│  ┌─────────────────────────────────────────────────────┐  │
│  │                 バッチ処理・連携                     │  │
│  │                                                   │  │
│  │ • 日次在庫監視バッチ（安全在庫・過剰在庫チェック）      │  │
│  │ • 外部システム連携（購買・生産・販売システム）         │  │
│  │ • データアーカイブ（履歴データ管理）                  │  │
│  └─────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────┘
```

---

## 📚 関連ドキュメント

- [業務要件書](../requirements/business-requirements.md)
- [機能要件書](../requirements/functional-requirements.md)
- [システム概要書](./system-overview.md)
- [データ設計書](./data-design.md)
- [API 仕様書](../../api/docs/swagger/)

---
