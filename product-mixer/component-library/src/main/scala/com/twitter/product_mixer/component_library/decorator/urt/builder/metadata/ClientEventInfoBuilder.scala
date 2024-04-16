package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. tadata

 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Cl entEvent nfo
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseCl entEvent nfoBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseCl entEventDeta lsBu lder

/**
 * Sets t  [[Cl entEvent nfo]] w h t  `component` f eld set to [[component]]
 * @see  [[http://go/cl ent-events]]
 */
case class Cl entEvent nfoBu lder[-Query <: P pel neQuery, Cand date <: Un versalNoun[Any]](
  component: Str ng,
  deta lsBu lder: Opt on[BaseCl entEventDeta lsBu lder[Query, Cand date]] = None)
    extends BaseCl entEvent nfoBu lder[Query, Cand date] {

  overr de def apply(
    query: Query,
    cand date: Cand date,
    cand dateFeatures: FeatureMap,
    ele nt: Opt on[Str ng]
  ): Opt on[Cl entEvent nfo] =
    So (
      Cl entEvent nfo(
        component = So (component),
        ele nt = ele nt,
        deta ls = deta lsBu lder.flatMap(_.apply(query, cand date, cand dateFeatures)),
        act on = None,
        ent yToken = None)
    )
}

/**
 *  n rare cases   m ght not want to send cl ent event  nfo. For
 * example, t  m ght be set already on t  cl ent for so  legacy
 * t  l nes.
 */
object EmptyCl entEvent nfoBu lder
    extends BaseCl entEvent nfoBu lder[P pel neQuery, Un versalNoun[Any]] {
  overr de def apply(
    query: P pel neQuery,
    cand date: Un versalNoun[Any],
    cand dateFeatures: FeatureMap,
    ele nt: Opt on[Str ng]
  ): Opt on[Cl entEvent nfo] = None
}
