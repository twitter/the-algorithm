package com.tw ter.product_m xer.component_l brary.s de_effect. tr cs

 mport com.tw ter.product_m xer.component_l brary.model.cand date.BaseT etCand date
 mport com.tw ter.product_m xer.component_l brary.model.cand date.BaseUserCand date
 mport com.tw ter.product_m xer.component_l brary.s de_effect. tr cs.Cand date tr cFunct on.getCountForType
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.common.presentat on. emCand dateW hDeta ls

/**
 * Funct on to extract nu r cal  tr c value from [[Cand dateW hDeta ls]].
 * T  Cand date tr cFunct on w ll be appl ed on all [[Cand dateW hDeta ls]]  nstances  n t 
 * cand dateSelect on from t  Recom ndat onP pel ne.
 */
tra  Cand date tr cFunct on {
  def apply(cand dateW hDeta ls: Cand dateW hDeta ls): Long
}

object Cand date tr cFunct on {

  pr vate val defaultCountOnePf: Part alFunct on[Cand dateW hDeta ls, Long] = {
    case _ => 0L
  }

  /**
   * Count t  occurrences of a certa n cand date type from [[Cand dateW hDeta ls]].
   */
  def getCountForType(
    cand dateW hDeta ls: Cand dateW hDeta ls,
    countOnePf: Part alFunct on[Cand dateW hDeta ls, Long]
  ): Long = {
    (countOnePf orElse defaultCountOnePf)(cand dateW hDeta ls)
  }
}

object DefaultServedT etsSumFunct on extends Cand date tr cFunct on {
  overr de def apply(cand dateW hDeta ls: Cand dateW hDeta ls): Long =
    getCountForType(
      cand dateW hDeta ls,
      {
        case  em:  emCand dateW hDeta ls =>
           em.cand date match {
            case _: BaseT etCand date => 1L
            case _ => 0L
          }
      })
}

object DefaultServedUsersSumFunct on extends Cand date tr cFunct on {
  overr de def apply(cand dateW hDeta ls: Cand dateW hDeta ls): Long =
    getCountForType(
      cand dateW hDeta ls,
      {
        case  em:  emCand dateW hDeta ls =>
           em.cand date match {
            case _: BaseUserCand date => 1L
            case _ => 0L
          }
      })
}
