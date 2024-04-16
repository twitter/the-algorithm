package com.tw ter.follow_recom ndat ons.common.cand date_s ces.stp

 mport com.google. nject.S ngleton
 mport com.tw ter. rm .model.Algor hm
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.strato.generated.cl ent.onboard ng.userrecs.MutualFollowExpans onCl entColumn
 mport javax. nject. nject

/**
 * A s ce that f nds t  mutual follows of one's mutual follows that one  sn't follow ng already.
 */
@S ngleton
class Offl neMutualFollowExpans onS ce @ nject() (
  column: MutualFollowExpans onCl entColumn)
    extends Offl neStrongT ePred ct onBaseS ce(column.fetc r) {
  overr de val  dent f er: Cand dateS ce dent f er =
    Offl neMutualFollowExpans onS ce. dent f er
}

object Offl neMutualFollowExpans onS ce {
  val  dent f er: Cand dateS ce dent f er =
    Cand dateS ce dent f er(Algor hm.MutualFollowExpans on.toStr ng)
}
