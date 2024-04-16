package com.tw ter.follow_recom ndat ons.common.cand date_s ces.stp

 mport com.tw ter.cortex.deepb rd.runt  .pred ct on_eng ne.TensorflowPred ct onEng ne
 mport com.tw ter.follow_recom ndat ons.common.constants.Gu ceNa dConstants
 mport com.tw ter.ml.ap .Feature.Cont nuous
 mport com.tw ter.ml.ap .ut l.SR chDataRecord
 mport com.tw ter.ml.pred ct on_serv ce.Pred ct onRequest
 mport com.tw ter.st ch.St ch
 mport com.tw ter.wtf.scald ng.jobs.strong_t e_pred ct on.STPRecord
 mport com.tw ter.wtf.scald ng.jobs.strong_t e_pred ct on.STPRecordAdapter
 mport javax. nject. nject
 mport javax. nject.Na d
 mport javax. nject.S ngleton

/**
 * STP ML ranker tra ned us ng DeepB rdV2
 */
@S ngleton
class Dbv2StpScorer @ nject() (
  @Na d(Gu ceNa dConstants.STP_DBV2_SCORER) tfPred ct onEng ne: TensorflowPred ct onEng ne) {
  def getScoredResponse(record: STPRecord): St ch[Opt on[Double]] = {
    val request: Pred ct onRequest = new Pred ct onRequest(
      STPRecordAdapter.adaptToDataRecord(record))
    val responseSt ch = St ch.callFuture(tfPred ct onEng ne.getPred ct on(request))
    responseSt ch.map { response =>
      val r chDr = SR chDataRecord(response.getPred ct on)
      r chDr.getFeatureValueOpt(new Cont nuous("output"))
    }
  }
}
