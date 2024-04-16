package com.tw ter.t  l nes.pred ct on.common.aggregates.real_t  

 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.ml.featurestore.catalog.datasets.mag crecs.UserFeaturesDataset
 mport com.tw ter.ml.featurestore.catalog.datasets.geo.GeoUserLocat onDataset
 mport com.tw ter.ml.featurestore.l b.dataset.DatasetParams
 mport com.tw ter.ml.featurestore.l b.export.strato.FeatureStoreAppNa s
 mport com.tw ter.ml.featurestore.l b.onl ne.FeatureStoreCl ent
 mport com.tw ter.ml.featurestore.l b.params.FeatureStoreParams
 mport com.tw ter.strato.cl ent.{Cl ent, Strato}
 mport com.tw ter.strato.opcontext.Attr but on.ManhattanApp d
 mport com.tw ter.ut l.Durat on

pr vate[real_t  ] object FeatureStoreUt ls {
  pr vate def mkStratoCl ent(serv ce dent f er: Serv ce dent f er): Cl ent =
    Strato.cl ent
      .w hMutualTls(serv ce dent f er)
      .w hRequestT  out(Durat on.fromM ll seconds(50))
      .bu ld()

  pr vate val featureStoreParams: FeatureStoreParams =
    FeatureStoreParams(
      perDataset = Map(
        UserFeaturesDataset. d ->
          DatasetParams(
            stratoSuff x = So (FeatureStoreAppNa s.T  l nes),
            attr but ons = Seq(ManhattanApp d("at na", "t  l nes_aggregates_v2_features_by_user"))
          ),
        GeoUserLocat onDataset. d ->
          DatasetParams(
            attr but ons = Seq(ManhattanApp d("starbuck", "t  l nes_geo_features_by_user"))
          )
      )
    )

  def mkFeatureStoreCl ent(
    serv ce dent f er: Serv ce dent f er,
    statsRece ver: StatsRece ver
  ): FeatureStoreCl ent = {
    com.tw ter.server. n () // necessary  n order to use W lyNS path

    val stratoCl ent: Cl ent = mkStratoCl ent(serv ce dent f er)
    val featureStoreCl ent: FeatureStoreCl ent = FeatureStoreCl ent(
      featureSet =
        UserFeaturesAdapter.UserFeaturesSet ++ AuthorFeaturesAdapter.UserFeaturesSet ++ T etFeaturesAdapter.T etFeaturesSet,
      cl ent = stratoCl ent,
      statsRece ver = statsRece ver,
      featureStoreParams = featureStoreParams
    )
    featureStoreCl ent
  }
}
