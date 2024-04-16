package com.tw ter.follow_recom ndat ons.common.cand date_s ces.s ms

 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.base.StratoFetc rS ce
 mport com.tw ter.follow_recom ndat ons.common.models.AccountProof
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.Reason
 mport com.tw ter.follow_recom ndat ons.common.models.S m larToProof
 mport com.tw ter. rm .cand date.thr ftscala.Cand dates
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.strato.cl ent.Fetc r

abstract class StratoBasedS msCand dateS ce[U](
  fetc r: Fetc r[Long, U, Cand dates],
  v ew: U,
  overr de val  dent f er: Cand dateS ce dent f er)
    extends StratoFetc rS ce[Long, U, Cand dates](fetc r, v ew,  dent f er) {

  overr de def map(target: Long, cand dates: Cand dates): Seq[Cand dateUser] =
    StratoBasedS msCand dateS ce.map(target, cand dates)
}

object StratoBasedS msCand dateS ce {
  def map(target: Long, cand dates: Cand dates): Seq[Cand dateUser] = {
    for {
      cand date <- cand dates.cand dates
    } y eld Cand dateUser(
       d = cand date.user d,
      score = So (cand date.score),
      reason = So (
        Reason(
          So (
            AccountProof(
              s m larToProof = So (S m larToProof(Seq(target)))
            )
          )
        )
      )
    )
  }
}
