package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.adapters.author_features

 mport com.tw ter.ho _m xer.ut l.DataRecordUt l
 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.ml.ap .Feature
 mport com.tw ter.ml.ap .FeatureContext
 mport com.tw ter.ml.ap .ut l.CompactDataRecordConverter
 mport com.tw ter.ml.ap .ut l.FDsl._
 mport com.tw ter.t  l nes.author_features.v1.{thr ftjava => af}
 mport com.tw ter.t  l nes.pred ct on.common.adapters.T  l nesAdapterBase
 mport com.tw ter.t  l nes.pred ct on.common.aggregates.T  l nesAggregat onConf g
 mport com.tw ter.t  l nes.pred ct on.features.user_ alth.User althFeatures
 mport scala.collect on.JavaConverters._

object AuthorFeaturesAdapter extends T  l nesAdapterBase[af.AuthorFeatures] {

  pr vate val Pref x = "or g nal_author.t  l nes.or g nal_author_aggregates."

  pr vate val typedAggregateGroups =
    T  l nesAggregat onConf g.or g nalAuthorAggregatesV1.bu ldTypedAggregateGroups()

  pr vate val aggregateFeaturesRena Map: Map[Feature[_], Feature[_]] =
    typedAggregateGroups.map(_.outputFeaturesToRena dOutputFeatures(Pref x)).reduce(_ ++ _)

  pr vate val pref xedOr g nalAuthorAggregateFeatures =
    typedAggregateGroups.flatMap(_.allOutputFeatures).map { feature =>
      aggregateFeaturesRena Map.getOrElse(feature, feature)
    }

  pr vate val authorFeatures = pref xedOr g nalAuthorAggregateFeatures ++ Seq(
    User althFeatures.AuthorState,
    User althFeatures.NumAuthorFollo rs,
    User althFeatures.NumAuthorConnectDays,
    User althFeatures.NumAuthorConnect
  )

  pr vate val aggregateFeatureContext: FeatureContext =
    new FeatureContext(typedAggregateGroups.flatMap(_.allOutputFeatures).asJava)

  pr vate lazy val pref xedAggregateFeatureContext: FeatureContext =
    new FeatureContext(pref xedOr g nalAuthorAggregateFeatures.asJava)

  overr de val getFeatureContext: FeatureContext = new FeatureContext(authorFeatures: _*)

  overr de val commonFeatures: Set[Feature[_]] = Set.empty

  pr vate val compactDataRecordConverter = new CompactDataRecordConverter()

  overr de def adaptToDataRecords(
    authorFeatures: af.AuthorFeatures
  ): java.ut l.L st[DataRecord] = {
    val dataRecord =
       f (authorFeatures.aggregates != null) {
        val or g nalAuthorAggregatesDataRecord =
          compactDataRecordConverter.compactDataRecordToDataRecord(authorFeatures.aggregates)

        DataRecordUt l.applyRena (
          or g nalAuthorAggregatesDataRecord,
          aggregateFeatureContext,
          pref xedAggregateFeatureContext,
          aggregateFeaturesRena Map)
      } else new DataRecord

     f (authorFeatures.user_ alth != null) {
      val user alth = authorFeatures.user_ alth

       f (user alth.user_state != null) {
        dataRecord.setFeatureValue(
          User althFeatures.AuthorState,
          user alth.user_state.getValue.toLong
        )
      }

      dataRecord.setFeatureValue(
        User althFeatures.NumAuthorFollo rs,
        user alth.num_follo rs.toDouble
      )

      dataRecord.setFeatureValue(
        User althFeatures.NumAuthorConnectDays,
        user alth.num_connect_days.toDouble
      )

      dataRecord.setFeatureValue(
        User althFeatures.NumAuthorConnect,
        user alth.num_connect.toDouble
      )
    }

    L st(dataRecord).asJava
  }
}
