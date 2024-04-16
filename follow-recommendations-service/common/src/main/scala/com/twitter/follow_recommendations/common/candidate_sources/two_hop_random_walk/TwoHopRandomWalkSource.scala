package com.tw ter.follow_recom ndat ons.common.cand date_s ces.two_hop_random_walk

 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.base.StratoFetc rW hUn V ewS ce
 mport com.tw ter.follow_recom ndat ons.common.constants.Gu ceNa dConstants
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter. rm .model.Algor hm
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.strato.cl ent.Fetc r
 mport com.tw ter.wtf.cand date.thr ftscala.{Cand dateSeq => TCand dateSeq}
 mport javax. nject. nject
 mport javax. nject.Na d
 mport javax. nject.S ngleton

@S ngleton
class TwoHopRandomWalkS ce @ nject() (
  @Na d(Gu ceNa dConstants.TWO_HOP_RANDOM_WALK_FETCHER) fetc r: Fetc r[
    Long,
    Un ,
    TCand dateSeq
  ]) extends StratoFetc rW hUn V ewS ce[Long, TCand dateSeq](
      fetc r,
      TwoHopRandomWalkS ce. dent f er) {

  overr de def map(targetUser d: Long, tCand dateSeq: TCand dateSeq): Seq[Cand dateUser] =
    TwoHopRandomWalkS ce.map(targetUser d, tCand dateSeq)

}

object TwoHopRandomWalkS ce {
  def map(targetUser d: Long, tCand dateSeq: TCand dateSeq): Seq[Cand dateUser] = {
    tCand dateSeq.cand dates
      .sortBy(-_.score)
      .map { tCand date =>
        Cand dateUser( d = tCand date.user d, score = So (tCand date.score))
      }
  }

  val  dent f er: Cand dateS ce dent f er =
    Cand dateS ce dent f er(Algor hm.TwoHopRandomWalk.toStr ng)
}
