package com.tw ter.follow_recom ndat ons.common.cand date_s ces.base

 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.cl ent.Fetc r

abstract class StratoFetc rS ce[K, U, V](
  fetc r: Fetc r[K, U, V],
  v ew: U,
  overr de val  dent f er: Cand dateS ce dent f er)
    extends Cand dateS ce[K, Cand dateUser] {

  def map(user: K, v: V): Seq[Cand dateUser]

  overr de def apply(target: K): St ch[Seq[Cand dateUser]] = {
    fetc r
      .fetch(target, v ew)
      .map { result =>
        result.v
          .map { cand dates => map(target, cand dates) }
          .getOrElse(N l)
          .map(_.w hCand dateS ce( dent f er))
      }
  }
}
