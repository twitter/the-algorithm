package com.tw ter.product_m xer.core.feature.featuremap.datarecord

 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.ml.ap .FeatureContext
 mport com.tw ter.ml.ap .ut l.SR chDataRecord
 mport scala.collect on.JavaConverters._
 mport com.tw ter.product_m xer.core.feature.datarecord.BaseDataRecordFeature
 mport com.tw ter.product_m xer.core.feature.datarecord.DataRecordCompat ble
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featurestorev1.FeatureStoreV1Feature
 mport com.tw ter.product_m xer.core.feature.featurestorev1.featurevalue.FeatureStoreV1ResponseFeature

/**
 * FeaturesScope for def n ng what features should be  ncluded  n a DataRecord from a FeatureMap.
 * W re poss ble, prefer [[Spec f cFeatures]].   fa ls loudly on m ss ng features wh ch can  lp
 *  dent fy program r error, but can be complex to manage for mult -phase hydrators.
 */
sealed tra  FeaturesScope[+DRFeature <: BaseDataRecordFeature[_, _]] {
  def getNonFeatureStoreDataRecordFeatures(featureMap: FeatureMap): Seq[DRFeature]

  /**
   * Because Feature Store features aren't d rect features  n t  FeatureMap and  nstead l ve
   * aggregated  n a DataRecord  n   Feature Map,   need to  nterface w h t  underly ng Data
   * Record  nstead. e.g. for t  `AllFeatures` case,   won't know what all FStore ProM x Features
   *   have  n a FeatureMap just by loop ng through features & need to just return t  DataRecord.
   */
  def getFeatureStoreFeaturesDataRecord(featureMap: FeatureMap): SR chDataRecord
}

/**
 * Use all DataRecord features on a FeatureMap to output a DataRecord.
 */
case class AllFeatures[-Ent y]() extends FeaturesScope[BaseDataRecordFeature[Ent y, _]] {
  overr de def getNonFeatureStoreDataRecordFeatures(
    featureMap: FeatureMap
  ): Seq[BaseDataRecordFeature[Ent y, _]] = {

    /**
     * See [[com.tw ter.product_m xer.core.benchmark.FeatureMapBenchmark]]
     *
     * `toSeq``  s a no-op, `v ew`` makes later compos  ons lazy. Currently   only perform a `forEach`
     * on t  result but `v ew`  re has no performance  mpact but protects us  f   acc dentally add
     * more compos  ons  n t  m ddle.
     *
     * Feature Store features aren't  n t  FeatureMap so t  w ll only ever return t  non-FStore Features.
     */
    featureMap.getFeatures.toSeq.v ew.collect {
      case feature: BaseDataRecordFeature[Ent y, _] => feature
    }
  }

  // Get t  ent re underly ng DataRecord  f ava lable.
  overr de def getFeatureStoreFeaturesDataRecord(
    featureMap: FeatureMap
  ): SR chDataRecord =  f (featureMap.getFeatures.conta ns(FeatureStoreV1ResponseFeature)) {
    // Note,   do not copy over t  feature context because JR chDataRecord w ll enforce that
    // all features are  n t  FeatureContext wh ch   do not know at  n  t  , and  's pr cey
    // to compute at run t  .
    SR chDataRecord(featureMap.get(FeatureStoreV1ResponseFeature).r chDataRecord.getRecord)
  } else {
    SR chDataRecord(new DataRecord())
  }
}

/**
 * Bu ld a DataRecord w h only t  g ven features from t  FeatureMap used. M ss ng features
 * w ll fa l loudly.
 * @param features t  spec f c features to  nclude  n t  DataRecord.
 */
case class Spec f cFeatures[DRFeature <: BaseDataRecordFeature[_, _]](
  features: Set[DRFeature])
    extends FeaturesScope[DRFeature] {

  pr vate val featuresForContext = features.collect {
    case featureStoreFeatures: FeatureStoreV1Feature[_, _, _, _] =>
      featureStoreFeatures.boundFeature.mlAp Feature
    case dataRecordCompat ble: DataRecordCompat ble[_] => dataRecordCompat ble.mlFeature
  }.asJava

  pr vate val featureContext = new FeatureContext(featuresForContext)

  pr vate val fsFeatures = features
    .collect {
      case featureStoreV1Feature: FeatureStoreV1Feature[_, _, _, _] =>
        featureStoreV1Feature
    }

  // S nce  's poss ble a custo r w ll pass feature store features  n t  DR Feature l st, let's
  // part  on t m out to only return non-FS ones  n getFeatures. See [[FeaturesScope]] com nt.
  pr vate val nonFsFeatures: Seq[DRFeature] = features.flatMap {
    case _: FeatureStoreV1Feature[_, _, _, _] =>
      None
    case ot rFeature => So (ot rFeature)
  }.toSeq

  overr de def getNonFeatureStoreDataRecordFeatures(
    featureMap: FeatureMap
  ): Seq[DRFeature] = nonFsFeatures

  overr de def getFeatureStoreFeaturesDataRecord(
    featureMap: FeatureMap
  ): SR chDataRecord =
     f (fsFeatures.nonEmpty && featureMap.getFeatures.conta ns(FeatureStoreV1ResponseFeature)) {
      // Return a DataRecord only w h t  expl c ly requested features set.
      val r chDataRecord = SR chDataRecord(new DataRecord(), featureContext)
      val ex st ngDataRecord = featureMap.get(FeatureStoreV1ResponseFeature).r chDataRecord
      fsFeatures.foreach { feature =>
        r chDataRecord.setFeatureValue(
          feature.boundFeature.mlAp Feature,
          ex st ngDataRecord.getFeatureValue(feature.boundFeature.mlAp Feature))
      }
      r chDataRecord
    } else {
      SR chDataRecord(new DataRecord())
    }
}

/**
 * Bu ld a DataRecord w h every feature ava lable  n a FeatureMap except for t  ones prov ded.
 * @param featuresToExclude t  features to be excluded  n t  DataRecord.
 */
case class AllExceptFeatures(
  featuresToExclude: Set[BaseDataRecordFeature[_, _]])
    extends FeaturesScope[BaseDataRecordFeature[_, _]] {

  pr vate val fsFeatures = featuresToExclude
    .collect {
      case featureStoreV1Feature: FeatureStoreV1Feature[_, _, _, _] =>
        featureStoreV1Feature
    }

  overr de def getNonFeatureStoreDataRecordFeatures(
    featureMap: FeatureMap
  ): Seq[BaseDataRecordFeature[_, _]] =
    featureMap.getFeatures
      .collect {
        case feature: BaseDataRecordFeature[_, _] => feature
      }.f lterNot(featuresToExclude.conta ns).toSeq

  overr de def getFeatureStoreFeaturesDataRecord(
    featureMap: FeatureMap
  ): SR chDataRecord =  f (featureMap.getFeatures.conta ns(FeatureStoreV1ResponseFeature)) {
    // Return a data record only w h t  expl c ly requested features set. Do t  by copy ng
    // t  ex st ng one and remov ng t  features  n t  denyl st.
    // Note,   do not copy over t  feature context because JR chDataRecord w ll enforce that
    // all features are  n t  FeatureContext wh ch   do not know at  n  t  , and  's pr cey
    // to compute at run t  .
    val r chDataRecord = SR chDataRecord(
      featureMap.get(FeatureStoreV1ResponseFeature).r chDataRecord.getRecord.deepCopy())
    fsFeatures.foreach { feature =>
      r chDataRecord.clearFeature(feature.boundFeature.mlAp Feature)
    }
    r chDataRecord
  } else {
    SR chDataRecord(new DataRecord())
  }
}
