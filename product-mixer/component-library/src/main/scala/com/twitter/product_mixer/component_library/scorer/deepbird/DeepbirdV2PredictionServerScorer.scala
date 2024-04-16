package com.tw ter.product_m xer.component_l brary.scorer.deepb rd

 mport com.tw ter.cortex.deepb rd.{thr ftjava => t}
 mport com.tw ter.ml.pred ct on_serv ce.BatchPred ct onRequest
 mport com.tw ter.ml.pred ct on_serv ce.BatchPred ct onResponse
 mport com.tw ter.product_m xer.component_l brary.scorer.common.ModelSelector
 mport com.tw ter.product_m xer.core.feature.datarecord.BaseDataRecordFeature
 mport com.tw ter.product_m xer.core.feature.featuremap.datarecord.FeaturesScope
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common. dent f er.Scorer dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.ut l.Future

/**
 * Conf gurable Scorer that calls any Deepb rd Pred ct on Serv ce thr ft.
 * @param  dent f er Un que  dent f er for t  scorer
 * @param pred ct onServ ce T  Pred ct on Thr ft Serv ce
 * @param modelSelector Model  D Selector to dec de wh ch model to select, can also be represented
 *                        as an anonymous funct on: { query: Query => So ("Ex") }
 * @param queryFeatures T  Query Features to convert and pass to t  deepb rd model.
 * @param cand dateFeatures T  Cand date Features to convert and pass to t  deepb rd model.
 * @param resultFeatures T  Cand date features returned by t  model.
 * @tparam Query Type of p pel ne query.
 * @tparam Cand date Type of cand dates to score.
 * @tparam QueryFeatures type of t  query level features consu d by t  scorer.
 * @tparam Cand dateFeatures type of t  cand date level features consu d by t  scorer.
 * @tparam ResultFeatures type of t  cand date level features returned by t  scorer.
 */
case class Deepb rdV2Pred ct onServerScorer[
  Query <: P pel neQuery,
  Cand date <: Un versalNoun[Any],
  QueryFeatures <: BaseDataRecordFeature[Query, _],
  Cand dateFeatures <: BaseDataRecordFeature[Cand date, _],
  ResultFeatures <: BaseDataRecordFeature[Cand date, _]
](
  overr de val  dent f er: Scorer dent f er,
  pred ct onServ ce: t.Deepb rdPred ct onServ ce.Serv ceToCl ent,
  modelSelector: ModelSelector[Query],
  queryFeatures: FeaturesScope[QueryFeatures],
  cand dateFeatures: FeaturesScope[Cand dateFeatures],
  resultFeatures: Set[ResultFeatures])
    extends BaseDeepb rdV2Scorer[
      Query,
      Cand date,
      QueryFeatures,
      Cand dateFeatures,
      ResultFeatures
    ]( dent f er, modelSelector, queryFeatures, cand dateFeatures, resultFeatures) {

  overr de def getBatchPred ct ons(
    request: BatchPred ct onRequest,
    modelSelector: t.ModelSelector
  ): Future[BatchPred ct onResponse] =
    pred ct onServ ce.batchPred ctFromModel(request, modelSelector)
}
