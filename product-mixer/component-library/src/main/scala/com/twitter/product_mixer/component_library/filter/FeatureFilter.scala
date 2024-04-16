package com.tw ter.product_m xer.component_l brary.f lter

 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lter
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lterResult
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common. dent f er.F lter dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch

object FeatureF lter {

  /**
   * Bu lds a F lter us ng t  Feature na  as t  F lter dent f er
   *
   * @see [[FeatureF lter.fromFeature( dent f er, feature)]]
   */
  def fromFeature[Cand date <: Un versalNoun[Any]](
    feature: Feature[Cand date, Boolean]
  ): F lter[P pel neQuery, Cand date] =
    FeatureF lter.fromFeature(F lter dent f er(feature.toStr ng), feature)

  /**
   * Bu lds a F lter that keeps cand dates w n t  prov ded Boolean Feature  s present and True.
   *  f t  Feature  s m ss ng or False, t  cand date  s removed.
   *
   *  {{{
   *  F lter.fromFeature(
   *    F lter dent f er("So F lter"),
   *    feature = So Feature
   *  )
   *  }}}
   *
   * @param  dent f er A F lter dent f er for t  new f lter
   * @param feature A feature of [Cand date, Boolean] type used to determ ne w t r Cand dates w ll be kept
   *                            w n t  feature  s present and true ot rw se t y w ll be removed.
   */
  def fromFeature[Cand date <: Un versalNoun[Any]](
     dent f er: F lter dent f er,
    feature: Feature[Cand date, Boolean]
  ): F lter[P pel neQuery, Cand date] = {
    val   =  dent f er

    new F lter[P pel neQuery, Cand date] {
      overr de val  dent f er: F lter dent f er =  

      overr de def apply(
        query: P pel neQuery,
        cand dates: Seq[Cand dateW hFeatures[Cand date]]
      ): St ch[F lterResult[Cand date]] = {
        val (keptCand dates, removedCand dates) = cand dates.part  on { f lterCand date =>
          f lterCand date.features.getOrElse(feature, false)
        }

        St ch.value(
          F lterResult(
            kept = keptCand dates.map(_.cand date),
            removed = removedCand dates.map(_.cand date)))
      }
    }
  }
}
