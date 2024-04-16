package com.tw ter.ho _m xer.funct onal_component.decorator.bu lder

 mport com.tw ter.ho _m xer.model.Ho Features.Ent yTokenFeature
 mport com.tw ter.ho _m xer.model.Ho Features.SuggestTypeFeature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseCl entEventDeta lsBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseCl entEvent nfoBu lder
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Cl entEvent nfo
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.t  l nes. nject on.scr be. nject onScr beUt l

/**
 * Sets t  [[Cl entEvent nfo]] w h t  `component` f eld set to t  Suggest Type ass gned to each cand date
 */
case class Ho Cl entEvent nfoBu lder[Query <: P pel neQuery, Cand date <: Un versalNoun[Any]](
  deta lsBu lder: Opt on[BaseCl entEventDeta lsBu lder[Query, Cand date]] = None)
    extends BaseCl entEvent nfoBu lder[Query, Cand date] {

  overr de def apply(
    query: Query,
    cand date: Cand date,
    cand dateFeatures: FeatureMap,
    ele nt: Opt on[Str ng]
  ): Opt on[Cl entEvent nfo] = {
    val suggestType = cand dateFeatures
      .getOrElse(SuggestTypeFeature, None)
      .getOrElse(throw new UnsupportedOperat onExcept on(s"No SuggestType was set"))

    So (
      Cl entEvent nfo(
        component =  nject onScr beUt l.scr beComponent(suggestType),
        ele nt = ele nt,
        deta ls = deta lsBu lder.flatMap(_.apply(query, cand date, cand dateFeatures)),
        act on = None,
        /**
         * A backend ent y encoded by t  Cl ent Ent  es Encod ng L brary.
         * Placeholder str ng for now
         */
        ent yToken = cand dateFeatures.getOrElse(Ent yTokenFeature, None)
      )
    )
  }
}
