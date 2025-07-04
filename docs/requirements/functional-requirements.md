# ドローン在庫管理システム 機能要件書

## 📋 文書概要

| 項目       | 内容                                |
| ---------- | ----------------------------------- |
| 文書名     | ドローン在庫管理システム 機能要件書 |
| 作成日     | 2024 年 12 月 15 日                 |
| 最終更新日 | 2024 年 12 月 15 日                 |
| バージョン | 1.0.0                               |
| 作成者     | Development Team                    |
| 承認者     | Project Manager                     |

## 🎯 対象 s

- システムアーキテクト
- 開発チーム（フロントエンド・バックエンド）
- テストエンジニア
- システムインテグレーター
- ビジネスアナリスト

---

## 1. 機能要件の基本方針

### 1.1 ビジネス要件との関連性

本機能要件書は、業務要件書で定義された以下のビジネス目的を実現するための機能を定義する：

#### ビジネス目的と機能要件の対応

| No  | ビジネス目的             | 実現する主要機能領域                                 |
| --- | ------------------------ | ---------------------------------------------------- |
| 1   | **在庫の一元管理**       | 部品・製品在庫管理機能、在庫センター管理機能         |
| 2   | **カスタマイズ生産対応** | 部品カテゴリ管理機能、製品カテゴリ管理機能           |
| 3   | **製造プロセス最適化**   | 在庫アラート機能、履歴管理機能、外部システム連携機能 |
| 4   | **コスト削減**           | 在庫レベル管理機能、適正在庫アラート機能             |

### 1.2 システム機能の価値定義

各機能は以下の価値実現を目的とする：

- **業務効率化**: 在庫確認時間 95%削減（30 分 →1.5 分）
- **コスト削減**: 在庫保管コスト 20%削減、欠品機会損失 30%削減
- **品質向上**: データ正確性向上（95%→99.5%）、完全トレーサビリティ確保

---

## 2. ビジネスプロセス対応機能

### 2.1 部品購買・入荷プロセス支援機能

#### 2.1.1 **WHY**: なぜこの機能が必要か

- **課題**: 部品の入荷管理が手作業で非効率、入荷情報の記録漏れ・ミスが発生
- **ビジネス価値**: 入荷プロセスの自動化により、在庫精度向上と作業時間短縮を実現

#### 2.1.2 **WHAT**: 何を実現するか

##### 部品入荷登録機能

- **目的**: 購買システムからの入荷情報を受信し、部品在庫を正確に更新
- **実現価値**: 手動入力ミス排除、リアルタイム在庫更新
- **主要処理**:
  - 入荷数量の在庫加算
  - 入荷履歴の自動記録
  - 在庫レベルチェック（安全在庫・最大在庫との比較）

##### 部品入荷取消機能

- **目的**: 購買キャンセル時の在庫・履歴データの整合性確保
- **実現価値**: データ整合性保持、正確な在庫状況把握
- **主要処理**:
  - 取消数量の在庫減算
  - 入荷履歴のキャンセル記録
  - 関連アラートの状態更新

### 2.2 製品製造プロセス支援機能

#### 2.2.1 **WHY**: なぜこの機能が必要か

- **課題**: 製造に使用する部品の出庫管理が不透明、製品完成時の入庫処理が手作業
- **ビジネス価値**: 製造プロセスの可視化と自動化により、生産効率向上とトレーサビリティ確保

#### 2.2.2 **WHAT**: 何を実現するか

##### 製造用部品出庫機能

- **目的**: 生産管理システムからの製造指示に基づく部品出庫の自動処理
- **実現価値**: 製造に必要な部品の確実な確保、在庫消費の正確な記録
- **主要処理**:
  - 製造計画に基づく必要部品の特定
  - 部品在庫の減算処理
  - 出庫履歴の記録（製造ロット単位）

##### 製品入庫登録機能

- **目的**: 製造完了・品質検査合格品の製品在庫への登録
- **実現価値**: 販売可能製品の正確な在庫管理、製造トレーサビリティ確保
- **主要処理**:
  - 完成品数量の製品在庫加算
  - 使用部品情報の記録（BOM 追跡）
  - 製品入庫履歴の記録

##### 製造取消処理機能

- **目的**: 製造計画変更・中止時の在庫復旧処理
- **実現価値**: 計画変更への柔軟対応、在庫データの正確性維持
- **主要処理**:
  - 使用予定部品の在庫復旧
  - 関連履歴のキャンセル記録
  - 製品在庫の調整

### 2.3 製品出荷プロセス支援機能

#### 2.3.1 **WHY**: なぜこの機能が必要か

- **課題**: 製品出荷時の在庫減算処理が手作業、出荷可能数量の確認に時間がかかる
- **ビジネス価値**: 出荷プロセスの効率化と正確性向上、顧客への迅速な対応実現

#### 2.3.2 **WHAT**: 何を実現するか

##### 製品出荷処理機能

- **目的**: 販売管理システムからの出荷指示に基づく製品在庫の減算処理
- **実現価値**: 正確な在庫管理、出荷ミス防止、出荷履歴の完全記録
- **主要処理**:
  - 出荷可能数量の事前チェック
  - 製品在庫の減算処理
  - 出荷履歴の詳細記録

##### 出荷キャンセル処理機能

- **目的**: 出荷キャンセル時の製品在庫復旧と履歴整合性確保
- **実現価値**: キャンセル対応の迅速化、在庫データの正確性維持

---

## 3. 在庫最適化支援機能

### 3.1 在庫アラート管理機能

#### 3.1.1 **WHY**: なぜこの機能が必要か

- **課題**: 在庫不足・過剰の発見が遅れ、欠品や保管コスト増加が発生
- **ビジネス価値**: 適正在庫維持により、欠品機会損失 30%削減と保管コスト 20%削減を実現

#### 3.1.2 **WHAT**: 何を実現するか

##### 自動アラート生成機能

- **目的**: 在庫レベルの自動監視と早期警告
- **実現価値**:
  - 欠品リスクの事前回避
  - 過剰在庫による保管コスト抑制
  - 購買・生産計画の最適化支援
- **アラート種別**:
  - 不足アラート：現在在庫 < 安全在庫
  - 過剰アラート：現在在庫 > 最大在庫
  - 欠品アラート：現在在庫 = 0

##### アラート管理・解決機能

- **目的**: アラート状況の一元管理と対応状況の追跡
- **実現価値**: 対応漏れ防止、継続的な在庫改善
- **主要処理**:
  - アラート一覧表示・検索機能
  - 対応状況の更新・記録
  - アラート解決履歴の管理

### 3.2 在庫レベル管理機能

#### 3.2.1 **WHY**: なぜこの機能が必要か

- **課題**: 適正在庫レベルの設定・管理が困難、センター別の在庫最適化ができていない
- **ビジネス価値**: 各センター・品目別の最適在庫により、全体的なコスト削減と効率向上

#### 3.2.2 **WHAT**: 何を実現するか

##### 在庫レベル設定機能

- **目的**: センター・品目別の安全在庫・最大在庫レベルの個別設定
- **実現価値**: きめ細かい在庫管理、地域・需要特性に応じた最適化
- **設定項目**:
  - 安全在庫レベル（品目・センター別）
  - 最大在庫レベル（保管容量・回転率考慮）
  - アラート閾値（段階的警告レベル）

---

## 4. カテゴリ・マスタ管理機能

### 4.1 部品・製品カテゴリ管理機能

#### 4.1.1 **WHY**: なぜこの機能が必要か

- **課題**: カスタマイズ生産に対応する部品・製品の分類管理が不十分
- **ビジネス価値**: 多様な部品組み合わせによるカスタマイズ生産を効率的にサポート

#### 4.1.2 **WHAT**: 何を実現するか

##### 階層型カテゴリ管理機能

- **目的**: 部品・製品の論理的分類による管理効率化
- **実現価値**:
  - カスタマイズ生産での部品選択効率化
  - 在庫分析・レポート作成の高度化
  - 類似品の代替検討支援

##### カテゴリ別在庫分析機能

- **目的**: カテゴリ単位での在庫状況・回転率分析
- **実現価値**: カテゴリ別の購買戦略立案、在庫適正化推進

### 4.2 在庫センター管理機能

#### 4.2.1 **WHY**: なぜこの機能が必要か

- **課題**: 複数拠点の在庫を一元管理できていない、センター間の在庫移動が非効率
- **ビジネス価値**: 全社在庫の最適配置により、物流コスト削減と顧客対応力向上

#### 4.2.2 **WHAT**: 何を実現するか

##### センター情報管理機能

- **目的**: 各在庫センターの基本情報・特性の一元管理
- **実現価値**: 地域別需要への最適対応、物流効率化

##### センター別在庫管理機能

- **目的**: センター単位での独立した在庫管理と統合報告
- **実現価値**: 拠点別最適化と全社最適化の両立

---

## 5. 履歴・トレーサビリティ機能

### 5.1 在庫変動履歴管理機能

#### 5.1.1 **WHY**: なぜこの機能が必要か

- **課題**: 品質問題発生時の原因特定・影響範囲把握が困難、監査対応が非効率
- **ビジネス価値**: 完全なトレーサビリティにより品質保証強化と監査対応効率化

#### 5.1.2 **WHAT**: 何を実現するか

##### 入荷・出荷履歴管理機能

- **目的**: 全ての在庫変動の詳細記録と追跡可能性確保
- **実現価値**:
  - 品質問題時の迅速な原因特定
  - リコール時の影響範囲特定
  - 監査・内部統制対応の効率化

##### 製造トレーサビリティ機能

- **目的**: 製品に使用された部品の完全な追跡記録
- **実現価値**:
  - 製品単位での使用部品履歴確認
  - 部品単位での使用先製品確認
  - 品質改善のためのデータ分析基盤

### 5.2 操作履歴管理機能

#### 5.2.1 **WHY**: なぜこの機能が必要か

- **課題**: システム操作の証跡管理が不十分、不正操作の検知が困難
- **ビジネス価値**: 操作の透明性確保と内部統制強化により、企業リスク低減

#### 5.2.2 **WHAT**: 何を実現するか

##### 操作ログ記録機能

- **目的**: 全システム操作の詳細記録と分析
- **実現価値**:
  - 不正操作の検知・防止
  - 操作ミス時の原因特定
  - 内部統制・コンプライアンス対応

---

## 6. 外部システム連携機能

### 6.1 購買システム連携機能

#### 6.1.1 **WHY**: なぜこの機能が必要か

- **課題**: 購買情報の手動転記によるミス・遅延が発生
- **ビジネス価値**: 自動連携により作業効率 95%向上とデータ精度向上

#### 6.1.2 **WHAT**: 何を実現するか

- 入荷情報の自動受信・在庫更新
- 在庫情報の購買システムへの提供
- 発注推奨情報の自動送信

### 6.2 生産管理システム連携機能

#### 6.2.1 **WHY**: なぜこの機能が必要か

- **課題**: 製造計画と在庫状況の不整合により生産効率が低下
- **ビジネス価値**: リアルタイム連携により製造プロセス最適化

#### 6.2.2 **WHAT**: 何を実現するか

- 製造指示に基づく部品出庫処理
- 製造完了情報の受信・製品在庫更新
- 在庫状況の製造計画への反映

### 6.3 販売管理システム連携機能

#### 6.3.1 **WHY**: なぜこの機能が必要か

- **課題**: 販売可能在庫の確認に時間がかかり、顧客対応が遅延
- **ビジネス価値**: 即座の在庫確認により顧客満足度向上

#### 6.3.2 **WHAT**: 何を実現するか

- 出荷指示に基づく製品在庫減算
- 販売可能在庫情報のリアルタイム提供
- 製品在庫状況の販売計画への反映

---

## 7. 認証・セキュリティ機能

### 7.1 管理者認証機能

#### 7.1.1 **WHY**: なぜこの機能が必要か

- **課題**: 在庫情報は企業機密であり、不正アクセスは事業損失に直結
- **ビジネス価値**: 厳格なアクセス制御により企業機密保護と信頼性確保

#### 7.1.2 **WHAT**: 何を実現するか

##### JWT 認証機能

- **目的**: トークンベースの安全な認証システム
- **実現価値**:
  - セキュアな API アクセス制御
  - セッション管理の効率化
  - 不正アクセス防止

##### 権限管理機能

- **目的**: 役割に応じた適切なアクセス権限制御
- **実現価値**:
  - 必要最小限のアクセス権付与
  - 誤操作リスクの低減
  - 内部統制強化

---

## 📋 承認履歴

| バージョン | 更新日     | 更新者           | 承認者          | 更新内容                                                 |
| ---------- | ---------- | ---------------- | --------------- | -------------------------------------------------------- |
| 1.0.0      | 2024-12-15 | Development Team | Project Manager | ビジネス要件に基づく機能要件定義（WHY・WHAT 重視の改訂） |

---

## 📚 関連ドキュメント

- [業務要件書](./business-requirements.md)
- [非機能要件書](./non-functional-requirements.md)
- [システム概要書](../architecture/system-overview.md)
- [API 仕様書](../../api/docs/swagger/)
- [API 実装状況](../../api/docs/API_STATUS.md)

---
