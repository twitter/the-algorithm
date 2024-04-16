package com.tw ter.follow_recom ndat ons.common.transforms.mod fy_soc al_proof

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.base.GatedTransform
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasCl entContext
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .HasParams
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class RemoveAccountProofTransform @ nject() (statsRece ver: StatsRece ver)
    extends GatedTransform[HasCl entContext w h HasParams, Cand dateUser] {

  pr vate val stats = statsRece ver.scope(t .getClass.getS mpleNa )
  pr vate val removedProofsCounter = stats.counter("num_removed_proofs")

  overr de def transform(
    target: HasCl entContext w h HasParams,
     ems: Seq[Cand dateUser]
  ): St ch[Seq[Cand dateUser]] =
    St ch.value( ems.map { cand date =>
      removedProofsCounter. ncr()
      cand date.copy(reason = None)
    })
}
