package com.tw ter.fr gate.pushserv ce.pred cate

 mport com.tw ter.abuse.detect on.scor ng.thr ftscala.Model
 mport com.tw ter.abuse.detect on.scor ng.thr ftscala.T etScor ngRequest
 mport com.tw ter.abuse.detect on.scor ng.thr ftscala.T etScor ngResponse
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base.T etCand date
 mport com.tw ter.fr gate.common.rec_types.RecTypes
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.params.PushFeatureSw chParams
 mport com.tw ter.fr gate.pushserv ce.params.PushParams
 mport com.tw ter.fr gate.pushserv ce.ut l.Cand dateUt l
 mport com.tw ter. rm .pred cate.Na dPred cate
 mport com.tw ter. rm .pred cate.Pred cate
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.ut l.Future

object PNegMult modalPred cates {

  def  althS gnalScorePNegMult modalPred cate(
    t et althScoreStore: ReadableStore[T etScor ngRequest, T etScor ngResponse]
  )(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[PushCand date w h T etCand date] = {
    val na  = "pneg_mult modal_pred cate"
    val statsScope = stats.scope(na )
    val oonCand datesCounter = statsScope.counter("oon_cand dates")
    val nonEmptyModelScoreCounter = statsScope.counter("non_empty_model_score")
    val bucketedCounter = statsScope.counter("bucketed_oon_cand dates")
    val f lteredCounter = statsScope.counter("f ltered_oon_cand dates")

    Pred cate
      .fromAsync { cand date: PushCand date w h T etCand date =>
        val target = cand date.target
        val crt = cand date.commonRecType
        val  sOonCand date = RecTypes. sOutOfNetworkT etRecType(crt) ||
          RecTypes.outOfNetworkTop cT etTypes.conta ns(crt)

        lazy val enablePNegMult modalPred cateParam =
          target.params(PushFeatureSw chParams.EnablePNegMult modalPred cateParam)
        lazy val pNegMult modalPred cateModelThresholdParam =
          target.params(PushFeatureSw chParams.PNegMult modalPred cateModelThresholdParam)
        lazy val pNegMult modalPred cateBucketThresholdParam =
          target.params(PushFeatureSw chParams.PNegMult modalPred cateBucketThresholdParam)
        val pNegMult modalEnabledForF1T ets =
          target.params(PushParams.EnablePnegMult modalPred ct onForF1T ets)

         f (Cand dateUt l.shouldApply althQual yF lters(
            cand date) && ( sOonCand date || pNegMult modalEnabledForF1T ets) && enablePNegMult modalPred cateParam) {

          val pNegMult modalRequest = T etScor ngRequest(cand date.t et d, Model.PNegMult modal)
          t et althScoreStore.get(pNegMult modalRequest).map {
            case So (t etScor ngResponse) =>
              nonEmptyModelScoreCounter. ncr()

              val pNegMult modalScore = 1.0 - t etScor ngResponse.score

              cand date
                .cac ExternalScore("PNegMult modalScore", Future.value(So (pNegMult modalScore)))

               f ( sOonCand date) {
                oonCand datesCounter. ncr()

                 f (pNegMult modalScore > pNegMult modalPred cateBucketThresholdParam) {
                  bucketedCounter. ncr()
                   f (pNegMult modalScore > pNegMult modalPred cateModelThresholdParam) {
                    f lteredCounter. ncr()
                    false
                  } else true
                } else true
              } else {
                true
              }
            case _ => true
          }
        } else {
          Future.True
        }
      }
      .w hStats(stats.scope(na ))
      .w hNa (na )
  }
}
