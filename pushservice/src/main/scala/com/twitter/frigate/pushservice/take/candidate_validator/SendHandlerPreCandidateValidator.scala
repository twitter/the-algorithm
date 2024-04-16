package com.tw ter.fr gate.pushserv ce.take.cand date_val dator

 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.conf g.Conf g
 mport com.tw ter.fr gate.pushserv ce.take.pred cates.cand date_map.SendHandlerCand datePred catesMap
 mport com.tw ter. rm .pred cate.Pred cate
 mport com.tw ter.ut l.Future

class SendHandlerPreCand dateVal dator(overr de val conf g: Conf g) extends Cand dateVal dator {

  overr de protected val cand datePred catesMap =
    SendHandlerCand datePred catesMap.preCand datePred cates(conf g)

  pr vate val sendHandlerPreCand dateVal datorStats =
    statsRece ver.counter("sendHandlerPreCand dateVal dator_stats")

  overr de def val dateCand date[C <: PushCand date](cand date: C): Future[Opt on[Pred cate[C]]] = {
    val cand datePred cates = getCRTPred cates(cand date.commonRecType)
    val pred cates = sendHandlerPrePred cates ++ cand datePred cates

    sendHandlerPreCand dateVal datorStats. ncr()
    executeSequent alPred cates(cand date, pred cates)
  }
}
