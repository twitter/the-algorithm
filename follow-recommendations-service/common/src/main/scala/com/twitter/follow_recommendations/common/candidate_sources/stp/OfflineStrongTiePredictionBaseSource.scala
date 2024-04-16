package com.tw ter.follow_recom ndat ons.common.cand date_s ces.stp

 mport com.tw ter.follow_recom ndat ons.common.models.AccountProof
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.FollowProof
 mport com.tw ter.follow_recom ndat ons.common.models.Reason
 mport com.tw ter. rm .stp.thr ftscala.STPResult
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasCl entContext
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.cl ent.Fetc r
 mport com.tw ter.t  l nes.conf gap .HasParams

/** Base class that all var ants of   offl ne stp dataset can extend. Assu s t  sa  STPResult
 *  value  n t  key and converts t  result  nto t  necessary  nternal model.
 */
abstract class Offl neStrongT ePred ct onBaseS ce(
  fetc r: Fetc r[Long, Un , STPResult])
    extends Cand dateS ce[HasParams w h HasCl entContext, Cand dateUser] {

  def fetch(
    target: Long,
  ): St ch[Seq[Cand dateUser]] = {
    fetc r
      .fetch(target)
      .map { result =>
        result.v
          .map { cand dates => Offl neStrongT ePred ct onBaseS ce.map(target, cand dates) }
          .getOrElse(N l)
          .map(_.w hCand dateS ce( dent f er))
      }
  }

  overr de def apply(request: HasParams w h HasCl entContext): St ch[Seq[Cand dateUser]] = {
    request.getOpt onalUser d.map(fetch).getOrElse(St ch.N l)
  }
}

object Offl neStrongT ePred ct onBaseS ce {
  def map(target: Long, cand dates: STPResult): Seq[Cand dateUser] = {
    for {
      cand date <- cand dates.strongT eUsers.sortBy(-_.score)
    } y eld Cand dateUser(
       d = cand date.user d,
      score = So (cand date.score),
      reason = So (
        Reason(
          So (
            AccountProof(
              followProof = cand date.soc alProof.map(proof => FollowProof(proof, proof.s ze))
            )
          )
        )
      )
    )
  }
}
