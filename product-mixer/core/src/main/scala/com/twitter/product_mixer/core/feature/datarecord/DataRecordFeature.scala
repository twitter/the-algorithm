package com.tw ter.product_m xer.core.feature.datarecord

 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.product_m xer.core.feature.Feature

/**
 * A DataRecord supported feature m x n for enabl ng convers ons from Product M xer Features
 * to DataRecords. W n us ng Feature Store features, t   s pre-conf gured for t  custo r
 * under t  hood. For non-Feature Store features, custo rs must m x  n e  r [[DataRecordFeature]]
 * for requ red features, or [[DataRecordOpt onalFeature]] for opt onal features, as  ll as m x ng
 *  n a correspond ng [[DataRecordCompat ble]] for t  r feature type.
 * @tparam Ent y T  type of ent y that t  feature works w h. T  could be a User, T et,
 *                Query, etc.
 * @tparam Value T  type of t  value of t  feature.
 */
sealed tra  BaseDataRecordFeature[-Ent y, Value] extends Feature[Ent y, Value]

pr vate[product_m xer] abstract class FeatureStoreDataRecordFeature[-Ent y, Value]
    extends BaseDataRecordFeature[Ent y, Value]

/**
 * Feature  n a DataRecord for a requ red feature value; t  correspond ng feature w ll always be
 * ava lable  n t  bu lt DataRecord.
 */
tra  DataRecordFeature[-Ent y, Value] extends BaseDataRecordFeature[Ent y, Value] {
  self: DataRecordCompat ble[Value] =>
}

/**
 * Feature  n a DataRecord for an opt onal feature value; t  correspond ng feature w ll only
 * ever be set  n a DataRecord  f t  value  n t  feature map  s def ned (So (V)).
 */
tra  DataRecordOpt onalFeature[-Ent y, Value]
    extends BaseDataRecordFeature[Ent y, Opt on[Value]] {
  self: DataRecordCompat ble[Value] =>
}

/**
 * An ent re DataRecord as a feature. T   s useful w n t re  s an ex st ng DataRecord that
 * should be used as a whole  nstead of as  nd v dual [[DataRecordFeature]]s for example.
 */
tra  DataRecord nAFeature[-Ent y] extends BaseDataRecordFeature[Ent y, DataRecord]
