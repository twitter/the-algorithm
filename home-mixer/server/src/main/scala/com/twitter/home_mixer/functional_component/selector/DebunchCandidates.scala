package com.tw ter.ho _m xer.funct onal_component.selector

 mport com.tw ter.ho _m xer.funct onal_component.selector.DebunchCand dates.Tra l ngT etsM nS ze
 mport com.tw ter.ho _m xer.funct onal_component.selector.DebunchCand dates.Tra l ngT etsPort onToKeep
 mport com.tw ter.ho _m xer.model.Ho Features.GetNe rFeature
 mport com.tw ter.product_m xer.core.funct onal_component.common.Cand dateScope
 mport com.tw ter.product_m xer.core.funct onal_component.common.Cand dateScope.Part  onedCand dates
 mport com.tw ter.product_m xer.core.funct onal_component.selector.Selector
 mport com.tw ter.product_m xer.core.funct onal_component.selector.SelectorResult
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

tra  MustDebunch {
  def apply(cand date: Cand dateW hDeta ls): Boolean
}

object DebunchCand dates {
  val Tra l ngT etsM nS ze = 5
  val Tra l ngT etsPort onToKeep = 0.1
}

/**
 * T  selector rearranges t  cand dates to only allow bunc s of s ze [[maxBunchS ze]], w re a
 * bunch  s a consecut ve sequence of cand dates that  et [[mustDebunch]].
 */
case class DebunchCand dates(
  overr de val p pel neScope: Cand dateScope,
  mustDebunch: MustDebunch,
  maxBunchS ze:  nt)
    extends Selector[P pel neQuery] {

  overr de def apply(
    query: P pel neQuery,
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    result: Seq[Cand dateW hDeta ls]
  ): SelectorResult = {
    val Part  onedCand dates(selectedCand dates, ot rCand dates) =
      p pel neScope.part  on(rema n ngCand dates)
    val mutableCand dates = collect on.mutable.L stBuffer(selectedCand dates: _*)

    var cand datePo nter = 0
    var nonDebunchPo nter = 0
    var bunchS ze = 0
    var f nalNonDebunch = -1

    wh le (cand datePo nter < mutableCand dates.s ze) {
       f (mustDebunch(mutableCand dates(cand datePo nter))) bunchS ze += 1
      else {
        bunchS ze = 0
        f nalNonDebunch = cand datePo nter
      }

       f (bunchS ze > maxBunchS ze) {
        nonDebunchPo nter = Math.max(cand datePo nter, nonDebunchPo nter)
        wh le (nonDebunchPo nter < mutableCand dates.s ze &&
          mustDebunch(mutableCand dates(nonDebunchPo nter))) {
          nonDebunchPo nter += 1
        }
         f (nonDebunchPo nter == mutableCand dates.s ze)
          cand datePo nter = mutableCand dates.s ze
        else {
          val nextNonDebunch = mutableCand dates(nonDebunchPo nter)
          mutableCand dates.remove(nonDebunchPo nter)
          mutableCand dates. nsert(cand datePo nter, nextNonDebunch)
          bunchS ze = 0
          f nalNonDebunch = cand datePo nter
        }
      }

      cand datePo nter += 1
    }

    val debunc dCand dates =  f (query.features.ex sts(_.getOrElse(GetNe rFeature, false))) {
      val tra l ngT etsS ze = mutableCand dates.s ze - f nalNonDebunch - 1
      val keepCand dates = f nalNonDebunch + 1 +
        Math.max(Tra l ngT etsM nS ze, Tra l ngT etsPort onToKeep * tra l ngT etsS ze).to nt
      mutableCand dates.toL st.take(keepCand dates)
    } else mutableCand dates.toL st

    val updatedCand dates = ot rCand dates ++ debunc dCand dates
    SelectorResult(rema n ngCand dates = updatedCand dates, result = result)
  }
}
