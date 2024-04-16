package com.tw ter.t  l nes.pred ct on.common.aggregates

 mport com.tw ter.ml.ap .constant.SharedFeatures.T MESTAMP
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work.Offl neAggregateS ce
 mport com.tw ter.t  l nes.pred ct on.features.p_ho _latest.Ho LatestUserAggregatesFeatures
 mport t  l nes.data_process ng.ad_hoc.recap.data_record_preparat on.RecapDataRecordsAggM n malJavaDataset

/**
 * Any update  re should be  n sync w h [[T  l nesFeatureGroups]] and [[AggM n malDataRecordGeneratorJob]].
 */
object T  l nesAggregat onS ces {

  /**
   * T   s t  recap data records after post-process ng  n [[GenerateRecapAggM n malDataRecordsJob]]
   */
  val t  l nesDa lyRecapM n malS ce = Offl neAggregateS ce(
    na  = "t  l nes_da ly_recap",
    t  stampFeature = T MESTAMP,
    dalDataSet = So (RecapDataRecordsAggM n malJavaDataset),
    scald ngSuff xType = So ("dal"),
    w hVal dat on = true
  )
  val t  l nesDa lyTw terW deS ce = Offl neAggregateS ce(
    na  = "t  l nes_da ly_tw ter_w de",
    t  stampFeature = T MESTAMP,
    scald ngHdfsPath = So ("/user/t  l nes/processed/suggests/recap/tw ter_w de_data_records"),
    scald ngSuff xType = So ("da ly"),
    w hVal dat on = true
  )

  val t  l nesDa lyL stT  l neS ce = Offl neAggregateS ce(
    na  = "t  l nes_da ly_l st_t  l ne",
    t  stampFeature = T MESTAMP,
    scald ngHdfsPath = So ("/user/t  l nes/processed/suggests/recap/all_features/l st"),
    scald ngSuff xType = So ("h ly"),
    w hVal dat on = true
  )

  val t  l nesDa lyHo LatestS ce = Offl neAggregateS ce(
    na  = "t  l nes_da ly_ho _latest",
    t  stampFeature = Ho LatestUserAggregatesFeatures.AGGREGATE_T MESTAMP_MS,
    scald ngHdfsPath = So ("/user/t  l nes/processed/p_ho _latest/user_aggregates"),
    scald ngSuff xType = So ("da ly")
  )
}
