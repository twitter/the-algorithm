package com.tw ter.follow_recom ndat ons.common.cand date_s ces.stp

 mport com.google. nject.S ngleton
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.base.Soc alProofEnforcedCand dateS ce
 mport com.tw ter.follow_recom ndat ons.common.transforms.mod fy_soc al_proof.Mod fySoc alProof
 mport com.tw ter. rm .model.Algor hm
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport javax. nject. nject

@S ngleton
class Soc alProofEnforcedOffl neStrongT ePred ct onS ce @ nject() (
  offl neStrongT ePred ct onS ce: Offl neStrongT ePred ct onS ce,
  mod fySoc alProof: Mod fySoc alProof,
  statsRece ver: StatsRece ver)
    extends Soc alProofEnforcedCand dateS ce(
      offl neStrongT ePred ct onS ce,
      mod fySoc alProof,
      Soc alProofEnforcedOffl neStrongT ePred ct onS ce.M nNumSoc alProofsRequ red,
      Soc alProofEnforcedOffl neStrongT ePred ct onS ce. dent f er,
      statsRece ver)

object Soc alProofEnforcedOffl neStrongT ePred ct onS ce {
  val  dent f er = Cand dateS ce dent f er(
    Algor hm.StrongT ePred ct onRecW hSoc alProof.toStr ng)

  val M nNumSoc alProofsRequ red = 1
}
