package com.tw ter.product_m xer.component_l brary.selector

 mport com.tw ter.product_m xer.core.funct onal_component.common.Cand dateScope
 mport com.tw ter.product_m xer.core.funct onal_component.common.Spec f cP pel nes
 mport com.tw ter.product_m xer.core.funct onal_component.selector.Selector
 mport com.tw ter.product_m xer.core.funct onal_component.selector.SelectorResult
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

/**
 * L m  cand dates to t  f rst cand date s ce  n t  prov ded orthogonalCand dateP pel nes
 * seq that has cand dates  n t  cand date pool. For t  subsequent cand date s ces  n t  seq,
 * remove t  r cand dates from t  cand date pool.
 *
 * @example  f [[orthogonalCand dateP pel nes]]  s `Seq(D, A, C)`, and t  rema n ng cand dates
 * component  dent f ers are `Seq(A, A, A, B, B, C, C, D, D, D)`, t n `Seq(B, B, D, D, D)` w ll rema n
 *  n t  cand date pool.
 *
 * @example  f [[orthogonalCand dateP pel nes]]  s `Seq(D, A, C)`, and t  rema n ng cand dates
 * component  dent f ers are `Seq(A, A, A, B, B, C, C)`, t n `Seq(A, A, A, B, B)` w ll rema n
 *  n t  cand date pool.
 */
case class DropOrthogonalCand dates(
  orthogonalCand dateP pel nes: Seq[Cand dateP pel ne dent f er])
    extends Selector[P pel neQuery] {

  overr de val p pel neScope: Cand dateScope =
    Spec f cP pel nes(orthogonalCand dateP pel nes.toSet)

  overr de def apply(
    query: P pel neQuery,
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    result: Seq[Cand dateW hDeta ls]
  ): SelectorResult = {
    val f rstMatch ngOrthogonalS ceOpt = orthogonalCand dateP pel nes
      .f nd { orthogonalCand dateP pel ne =>
        rema n ngCand dates.ex sts(_.s ce == orthogonalCand dateP pel ne)
      }

    val rema n ngCand datesL m ed = f rstMatch ngOrthogonalS ceOpt match {
      case So (f rstMatch ngOrthogonalS ce) =>
        val subsequentOrthogonalS ces =
          orthogonalCand dateP pel nes.toSet - f rstMatch ngOrthogonalS ce

        rema n ngCand dates.f lterNot { cand date =>
          subsequentOrthogonalS ces.conta ns(cand date.s ce)
        }
      case None => rema n ngCand dates
    }

    SelectorResult(rema n ngCand dates = rema n ngCand datesL m ed, result = result)
  }
}
