package com.tw ter.fr gate.pushserv ce.take.cand date_val dator

 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.conf g.Conf g
 mport com.tw ter.fr gate.pushserv ce.take.pred cates.cand date_map.Cand datePred catesMap
 mport com.tw ter. rm .pred cate.Pred cate
 mport com.tw ter.ut l.Future

class RFPHCand dateVal dator(overr de val conf g: Conf g) extends Cand dateVal dator {
  pr vate val rFPHCand dateVal datorStats = statsRece ver.scope(t .getClass.getS mpleNa )
  pr vate val concurrentPred cateCount = rFPHCand dateVal datorStats.counter("concurrent")
  pr vate val sequent alPred cateCount = rFPHCand dateVal datorStats.counter("sequent al")

  overr de protected val cand datePred catesMap = Cand datePred catesMap(conf g)

  overr de def val dateCand date[C <: PushCand date](cand date: C): Future[Opt on[Pred cate[C]]] = {
    val cand datePred cates = getCRTPred cates(cand date.commonRecType)
    val pred cates = rfphPrePred cates ++ cand datePred cates ++ postPred cates
     f (cand date.target. sEma lUser) {
      concurrentPred cateCount. ncr()
      executeConcurrentPred cates(cand date, pred cates).map(_. adOpt on)
    } else {
      sequent alPred cateCount. ncr()
      executeSequent alPred cates(cand date, pred cates)
    }
  }
}
