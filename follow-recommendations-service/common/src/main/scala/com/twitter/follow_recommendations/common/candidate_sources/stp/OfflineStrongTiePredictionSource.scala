package com.tw ter.follow_recom ndat ons.common.cand date_s ces.stp

 mport com.google. nject.S ngleton
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.stp.Offl neStpS ceParams.UseDenserPm Matr x
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter. rm .model.Algor hm
 mport com.tw ter.product_m xer.component_l brary.model.cand date.UserCand date
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.ut l.logg ng.Logg ng
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasCl entContext
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .HasParams
 mport javax. nject. nject

object Offl neStpScore extends Feature[UserCand date, Opt on[Double]]

/**
 * Ma n s ce for strong-t e-pred ct on cand dates generated offl ne.
 */
@S ngleton
class Offl neStrongT ePred ct onS ce @ nject() (
  offl neStpS ceW hLegacyPm Matr x: Offl neStpS ceW hLegacyPm Matr x,
  offl neStpS ceW hDensePm Matr x: Offl neStpS ceW hDensePm Matr x)
    extends Cand dateS ce[HasParams w h HasCl entContext, Cand dateUser]
    w h Logg ng {
  overr de val  dent f er: Cand dateS ce dent f er = Offl neStrongT ePred ct onS ce. dent f er

  overr de def apply(request: HasParams w h HasCl entContext): St ch[Seq[Cand dateUser]] = {
     f (request.params(UseDenserPm Matr x)) {
      logger. nfo("Us ng dense PM  matr x.")
      offl neStpS ceW hDensePm Matr x(request)
    } else {
      logger. nfo("Us ng legacy PM  matr x.")
      offl neStpS ceW hLegacyPm Matr x(request)
    }
  }
}

object Offl neStrongT ePred ct onS ce {
  val  dent f er: Cand dateS ce dent f er =
    Cand dateS ce dent f er(Algor hm.StrongT ePred ct onRec.toStr ng)
}
