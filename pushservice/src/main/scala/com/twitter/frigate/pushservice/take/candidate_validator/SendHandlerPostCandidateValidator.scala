package com.tw ter.fr gate.pushserv ce.take.cand date_val dator

 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.conf g.Conf g
 mport com.tw ter.fr gate.pushserv ce.take.pred cates.cand date_map.SendHandlerCand datePred catesMap
 mport com.tw ter. rm .pred cate.Pred cate
 mport com.tw ter.ut l.Future

class SendHandlerPostCand dateVal dator(overr de val conf g: Conf g) extends Cand dateVal dator {

  overr de protected val cand datePred catesMap =
    SendHandlerCand datePred catesMap.postCand datePred cates(conf g)

  pr vate val sendHandlerPostCand dateVal datorStats =
    statsRece ver.counter("sendHandlerPostCand dateVal dator_stats")

  overr de def val dateCand date[C <: PushCand date](cand date: C): Future[Opt on[Pred cate[C]]] = {
    val cand datePred cates = getCRTPred cates(cand date.commonRecType)
    val pred cates = cand datePred cates ++ postPred cates

    sendHandlerPostCand dateVal datorStats. ncr()

    executeConcurrentPred cates(cand date, pred cates)
      .map(_. adOpt on)
  }
}
