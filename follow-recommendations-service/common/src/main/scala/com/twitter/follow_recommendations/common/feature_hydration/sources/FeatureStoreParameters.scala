package com.tw ter.follow_recom ndat ons.common.feature_hydrat on.s ces

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.ml.featurestore.catalog.datasets.core.UserMob leSdkDataset
 mport com.tw ter.ml.featurestore.catalog.datasets.core.Users ceEnt yDataset
 mport com.tw ter.ml.featurestore.catalog.datasets.custo r_j ney.PostNuxAlgor hm dAggregateDataset
 mport com.tw ter.ml.featurestore.catalog.datasets.custo r_j ney.PostNuxAlgor hmTypeAggregateDataset
 mport com.tw ter.ml.featurestore.catalog.datasets.mag crecs.Not f cat onSummar esEnt yDataset
 mport com.tw ter.ml.featurestore.catalog.datasets.onboard ng. tr cCenterUserCount ngFeaturesDataset
 mport com.tw ter.ml.featurestore.catalog.datasets.onboard ng.UserWtfAlgor hmAggregateFeaturesDataset
 mport com.tw ter.ml.featurestore.catalog.datasets.onboard ng.WhoToFollowPostNuxFeaturesDataset
 mport com.tw ter.ml.featurestore.catalog.datasets.rux.UserRecentReact vat onT  Dataset
 mport com.tw ter.ml.featurestore.catalog.datasets.t  l nes.AuthorFeaturesEnt yDataset
 mport com.tw ter.ml.featurestore.l b.dataset.DatasetParams
 mport com.tw ter.ml.featurestore.l b.dataset.onl ne.Batch ngPol cy
 mport com.tw ter.ml.featurestore.l b.params.FeatureStoreParams
 mport com.tw ter.strato.opcontext.Attr but on.ManhattanApp d
 mport com.tw ter.strato.opcontext.ServeW h n

object FeatureStorePara ters {

  pr vate val FeatureServ ceBatchS ze = 100

  val featureStoreParams = FeatureStoreParams(
    global = DatasetParams(
      serveW h n = So (ServeW h n(durat on = 240.m ll s, roundTr pAllowance = None)),
      attr but ons = Seq(
        ManhattanApp d("o ga", "wtf_ mpress on_store"),
        ManhattanApp d("at na", "wtf_at na"),
        ManhattanApp d("starbuck", "wtf_starbuck"),
        ManhattanApp d("apollo", "wtf_apollo")
      ),
      batch ngPol cy = So (Batch ngPol cy. solated(FeatureServ ceBatchS ze))
    ),
    perDataset = Map(
       tr cCenterUserCount ngFeaturesDataset. d ->
        DatasetParams(
          stratoSuff x = So ("onboard ng"),
          batch ngPol cy = So (Batch ngPol cy. solated(200))
        ),
      Users ceEnt yDataset. d ->
        DatasetParams(
          stratoSuff x = So ("onboard ng")
        ),
      WhoToFollowPostNuxFeaturesDataset. d ->
        DatasetParams(
          stratoSuff x = So ("onboard ng"),
          batch ngPol cy = So (Batch ngPol cy. solated(200))
        ),
      AuthorFeaturesEnt yDataset. d ->
        DatasetParams(
          stratoSuff x = So ("onboard ng"),
          batch ngPol cy = So (Batch ngPol cy. solated(10))
        ),
      UserRecentReact vat onT  Dataset. d -> DatasetParams(
        stratoSuff x =
          None // removed due to low h  rate.   should use a negat ve cac   n t  future
      ),
      UserWtfAlgor hmAggregateFeaturesDataset. d -> DatasetParams(
        stratoSuff x = None
      ),
      Not f cat onSummar esEnt yDataset. d -> DatasetParams(
        stratoSuff x = So ("onboard ng"),
        serveW h n = So (ServeW h n(durat on = 45.m ll s, roundTr pAllowance = None)),
        batch ngPol cy = So (Batch ngPol cy. solated(10))
      ),
      UserMob leSdkDataset. d -> DatasetParams(
        stratoSuff x = So ("onboard ng")
      ),
      PostNuxAlgor hm dAggregateDataset. d -> DatasetParams(
        stratoSuff x = So ("onboard ng")
      ),
      PostNuxAlgor hmTypeAggregateDataset. d -> DatasetParams(
        stratoSuff x = So ("onboard ng")
      ),
    ),
    enableFeatureGenerat onStats = true
  )
}
