package com.tw ter.follow_recom ndat ons.common.feature_hydrat on.adapters

 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.ml.ap .Feature
 mport com.tw ter.ml.ap .Feature.Cont nuous
 mport com.tw ter.ml.ap .FeatureContext
 mport com.tw ter.ml.ap . RecordOneToOneAdapter
 mport com.tw ter.ml.ap .ut l.FDsl._
 mport com.tw ter.ml.featurestore.catalog.features.custo r_j ney.PostNuxAlgor hmFeatures
 mport com.tw ter.ml.featurestore.catalog.features.custo r_j ney.PostNuxAlgor hm dAggregateFeatureGroup
 mport com.tw ter.ml.featurestore.catalog.features.custo r_j ney.PostNuxAlgor hmTypeAggregateFeatureGroup
 mport scala.collect on.JavaConverters._

object PostNuxAlgor hm dAdapter extends PostNuxAlgor hmAdapter {
  overr de val PostNuxAlgor hmFeatureGroup: PostNuxAlgor hmFeatures =
    PostNuxAlgor hm dAggregateFeatureGroup

  // To keep t  length of feature na s reasonable,   remove t  pref x added by FeatureStore.
  overr de val FeatureStorePref x: Str ng =
    "wtf_algor hm_ d.custo r_j ney.post_nux_algor hm_ d_aggregate_feature_group."
}

object PostNuxAlgor hmTypeAdapter extends PostNuxAlgor hmAdapter {
  overr de val PostNuxAlgor hmFeatureGroup: PostNuxAlgor hmFeatures =
    PostNuxAlgor hmTypeAggregateFeatureGroup

  // To keep t  length of feature na s reasonable,   remove t  pref x added by FeatureStore.
  overr de val FeatureStorePref x: Str ng =
    "wtf_algor hm_type.custo r_j ney.post_nux_algor hm_type_aggregate_feature_group."
}

tra  PostNuxAlgor hmAdapter extends  RecordOneToOneAdapter[DataRecord] {

  val PostNuxAlgor hmFeatureGroup: PostNuxAlgor hmFeatures

  // T  str ng that  s attac d to t  feature na  w n    s fetc d from feature store.
  val FeatureStorePref x: Str ng

  /**
   *
   * T  stores transfor d aggregate features for PostNux algor hm aggregate features. T 
   * transformat on  re  s log-rat o, w re rat o  s t  raw value d v ded by # of  mpress ons.
   */
  case class Transfor dAlgor hmFeatures(
    rat oLog: Cont nuous) {
    def getFeatures: Seq[Cont nuous] = Seq(rat oLog)
  }

  pr vate def applyFeatureStorePref x(feature: Cont nuous) = new Cont nuous(
    s"$FeatureStorePref x${feature.getFeatureNa }")

  // T  l st of  nput features W TH t  pref x ass gned to t m by FeatureStore.
  lazy val all nputFeatures: Seq[Seq[Cont nuous]] = Seq(
    PostNuxAlgor hmFeatureGroup.Aggregate7DayFeatures.map(applyFeatureStorePref x),
    PostNuxAlgor hmFeatureGroup.Aggregate30DayFeatures.map(applyFeatureStorePref x)
  )

  // T   s a l st of t  features W THOUT t  pref x ass gned to t m by FeatureStore.
  lazy val outputBaseFeatureNa s: Seq[Seq[Cont nuous]] = Seq(
    PostNuxAlgor hmFeatureGroup.Aggregate7DayFeatures,
    PostNuxAlgor hmFeatureGroup.Aggregate30DayFeatures
  )

  //   use backend  mpress on to calculate rat o values.
  lazy val rat oDenom nators: Seq[Cont nuous] = Seq(
    applyFeatureStorePref x(PostNuxAlgor hmFeatureGroup.Backend mpress ons7Days),
    applyFeatureStorePref x(PostNuxAlgor hmFeatureGroup.Backend mpress ons30Days)
  )

  /**
   * A mapp ng from an or g nal feature's  D to t  correspond ng set of transfor d features.
   * T   s used to compute t  transfor d features for each of t  or g nal ones.
   */
  pr vate lazy val Transfor dFeaturesMap: Map[Cont nuous, Transfor dAlgor hmFeatures] =
    outputBaseFeatureNa s.flatten.map { feature =>
      (
        // T   nput feature would have t  FeatureStore pref x attac d to  .
        new Cont nuous(s"$FeatureStorePref x${feature.getFeatureNa }"),
        //   don't keep t  FeatureStore pref x to keep t  length of feature na s reasonable.
        Transfor dAlgor hmFeatures(
          new Cont nuous(s"${feature.getFeatureNa }-rat o-log")
        ))
    }.toMap

  /**
   * G ven a denom nator, number of  mpress ons, t  funct on returns anot r funct on that adds
   * transfor d features (log1p and rat o) of an  nput feature to a DataRecord.
   */
  pr vate def addTransfor dFeaturesToDataRecordFunc(
    or g nalDr: DataRecord,
    num mpress ons: Double,
  ): (DataRecord, Cont nuous) => DataRecord = { (record: DataRecord, feature: Cont nuous) =>
    {
      Opt on(or g nalDr.getFeatureValue(feature)) foreach { featureValue =>
        Transfor dFeaturesMap.get(feature).foreach { transfor dFeatures =>
          record.setFeatureValue(
            transfor dFeatures.rat oLog,
            //   don't use log1p  re s nce t  values are rat os and add ng 1 to t  _rat o_ would
            // lead to logar hm of values bet en 1 and 2, essent ally mak ng all values t  sa .
            math.log((featureValue + 1) / num mpress ons)
          )
        }
      }
      record
    }
  }

  /**
   * @param record: T   nput record whose PostNuxAlgor hm aggregates are to be transfor d.
   * @return t   nput [[DataRecord]] w h transfor d aggregates added.
   */
  overr de def adaptToDataRecord(record: DataRecord): DataRecord = {
     f (record.cont nuousFeatures == null) {
      // T re are no base features ava lable, and  nce no transformat ons.
      record
    } else {

      /**
       * T  `foldLeft` below goes through pa rs of (1) Feature groups, such as those calculated over
       * 7 days or 30 days, and (2) t  number of  mpress ons for each of t se groups, wh ch  s t 
       * denom nator w n rat o  s calculated.
       */
      rat oDenom nators
        .z p(all nputFeatures).foldLeft( /*  n  al empty DataRecord */ record)(
          (
            /* DataRecord w h transfor d features up to  re */ transfor dRecord,
            /* A tuple w h t  denom nator (# mpress ons) and features to be transfor d */ num mpress onsAndFeatures
          ) => {
            val (num mpress onsFeature, features) = num mpress onsAndFeatures
            Opt on(record.getFeatureValue(num mpress onsFeature)) match {
              case So (num mpress ons)  f num mpress ons > 0.0 =>
                /**
                 * W h t  number of  mpress ons f xed,   generate a funct on that adds log-rat o
                 * for each feature  n t  current [[DataRecord]]. T  `foldLeft` goes through all
                 * such features and appl es that funct on wh le updat ng t  kept DataRecord.
                 */
                features.foldLeft(transfor dRecord)(
                  addTransfor dFeaturesToDataRecordFunc(record, num mpress ons))
              case _ =>
                transfor dRecord
            }
          })
    }
  }

  def getFeatures: Seq[Feature[_]] = Transfor dFeaturesMap.values.flatMap(_.getFeatures).toSeq

  overr de def getFeatureContext: FeatureContext =
    new FeatureContext()
      .addFeatures(t .getFeatures.asJava)
}
