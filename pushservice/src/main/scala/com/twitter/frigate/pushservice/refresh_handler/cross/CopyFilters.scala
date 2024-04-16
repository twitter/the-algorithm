package com.tw ter.fr gate.pushserv ce.refresh_handler.cross

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base._
 mport com.tw ter.fr gate.common.ut l.MRPushCopy
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.RawCand date
 mport com.tw ter. rm .pred cate.Pred cate
 mport com.tw ter.ut l.Future

pr vate[cross] class CopyF lters(statsRece ver: StatsRece ver) {

  pr vate val copyPred cates = new CopyPred cates(statsRece ver.scope("copy_pred cate"))

  def execute(rawCand date: RawCand date, pushCop es: Seq[MRPushCopy]): Future[Seq[MRPushCopy]] = {
    val cand dateCopyPa rs: Seq[Cand dateCopyPa r] =
      pushCop es.map(Cand dateCopyPa r(rawCand date, _))

    val compos ePred cate: Pred cate[Cand dateCopyPa r] = rawCand date match {
      case _: F1F rstDegree | _: OutOfNetworkT etCand date | _: EventCand date |
          _: Top cProofT etCand date | _: L stPushCand date | _:  rm  nterestBasedUserFollow |
          _: UserFollowW houtSoc alContextCand date | _: D scoverTw terCand date |
          _: TopT et mpress onsCand date | _: TrendT etCand date |
          _: Subscr bedSearchT etCand date | _: D gestCand date =>
        copyPred cates.alwaysTruePred cate

      case _: Soc alContextAct ons => copyPred cates.d splaySoc alContextPred cate

      case _ => copyPred cates.unrecogn zedCand datePred cate // block unrecogn sed cand dates
    }

    // apply pred cate to all [[MRPushCopy]] objects
    val f lterResults: Future[Seq[Boolean]] = compos ePred cate(cand dateCopyPa rs)
    f lterResults.map { results: Seq[Boolean] =>
      val seqBu lder = Seq.newBu lder[MRPushCopy]
      results.z p(pushCop es).foreach {
        case (result, pushCopy) =>  f (result) seqBu lder += pushCopy
      }
      seqBu lder.result()
    }
  }
}
