package com.tw ter.product_m xer.component_l brary.f lter

 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lter
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lterResult
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common. dent f er.F lter dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch

/**
 * Pred cate wh ch w ll be appl ed to each cand date. True  nd cates that t  cand date w ll be
 * @tparam Cand date - t  type of t  cand date
 */
tra  ShouldKeepCand date[Cand date] {
  def apply(cand date: Cand date): Boolean
}

object Pred cateF lter {

  /**
   * Bu lds a s mple F lter out of a pred cate funct on from t  cand date to a boolean. For clar y,
   *   recom nd  nclud ng t  na  of t  shouldKeepCand date para ter.
   *
   *  {{{
   *  F lter.fromPred cate(
   *    F lter dent f er("So F lter"),
   *    shouldKeepCand date = { cand date: UserCand date => cand date. d % 2 == 0L }
   *  )
   *  }}}
   *
   * @param  dent f er A F lter dent f er for t  new f lter
   * @param shouldKeepCand date A pred cate funct on from t  cand date. Cand dates w ll be kept
   *                            w n t  funct on returns True.
   */
  def fromPred cate[Cand date <: Un versalNoun[Any]](
     dent f er: F lter dent f er,
    shouldKeepCand date: ShouldKeepCand date[Cand date]
  ): F lter[P pel neQuery, Cand date] = {
    val   =  dent f er

    new F lter[P pel neQuery, Cand date] {
      overr de val  dent f er: F lter dent f er =  

      /**
       * F lter t  l st of cand dates
       *
       * @return a F lterResult  nclud ng both t  l st of kept cand date and t  l st of removed cand dates
       */
      overr de def apply(
        query: P pel neQuery,
        cand dates: Seq[Cand dateW hFeatures[Cand date]]
      ): St ch[F lterResult[Cand date]] = {
        val (keptCand dates, removedCand dates) = cand dates.map(_.cand date).part  on {
          f lterCand date =>
            shouldKeepCand date(f lterCand date)
        }

        St ch.value(F lterResult(kept = keptCand dates, removed = removedCand dates))
      }
    }
  }
}
