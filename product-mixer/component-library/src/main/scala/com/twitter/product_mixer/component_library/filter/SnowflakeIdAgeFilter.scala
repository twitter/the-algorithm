package com.tw ter.product_m xer.component_l brary.f lter

 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lter
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lterResult
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common. dent f er.F lter dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.snowflake. d.Snowflake d
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .Param
 mport com.tw ter.ut l.Durat on

/**
 * @param maxAgeParam Feature Sw ch conf gurable for conven ence
 * @tparam Cand date T  type of t  cand dates
 */
case class Snowflake dAgeF lter[Cand date <: Un versalNoun[Long]](
  maxAgeParam: Param[Durat on])
    extends F lter[P pel neQuery, Cand date] {

  overr de val  dent f er: F lter dent f er = F lter dent f er("Snowflake dAge")

  overr de def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[Cand date]]
  ): St ch[F lterResult[Cand date]] = {
    val maxAge = query.params(maxAgeParam)

    val (keptCand dates, removedCand dates) = cand dates
      .map(_.cand date)
      .part  on { f lterCand date =>
        Snowflake d.t  From dOpt(f lterCand date. d) match {
          case So (creat onT  ) =>
            query.queryT  .s nce(creat onT  ) <= maxAge
          case _ => false
        }
      }

    St ch.value(F lterResult(kept = keptCand dates, removed = removedCand dates))
  }
}
