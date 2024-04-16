package com.tw ter.ho _m xer.funct onal_component.decorator.urt.bu lder

 mport com.tw ter.ho _m xer.model.Ho Features.Conversat onModuleFocalT et dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Conversat onModule dFeature
 mport com.tw ter.ho _m xer.param.Ho GlobalParams.EnableSoc alContextParam
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.soc al_context.BaseSoc alContextBu lder
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Soc alContext
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
case class Ho T etSoc alContextBu lder @ nject() (
  l kedBySoc alContextBu lder: L kedBySoc alContextBu lder,
  l stsSoc alContextBu lder: L stsSoc alContextBu lder,
  follo dBySoc alContextBu lder: Follo dBySoc alContextBu lder,
  top cSoc alContextBu lder: Top cSoc alContextBu lder,
  extendedReplySoc alContextBu lder: ExtendedReplySoc alContextBu lder,
  rece vedReplySoc alContextBu lder: Rece vedReplySoc alContextBu lder,
  popularV deoSoc alContextBu lder: PopularV deoSoc alContextBu lder,
  popular nY AreaSoc alContextBu lder: Popular nY AreaSoc alContextBu lder)
    extends BaseSoc alContextBu lder[P pel neQuery, T etCand date] {

  def apply(
    query: P pel neQuery,
    cand date: T etCand date,
    features: FeatureMap
  ): Opt on[Soc alContext] = {
     f (query.params(EnableSoc alContextParam)) {
      features.getOrElse(Conversat onModuleFocalT et dFeature, None) match {
        case None =>
          l kedBySoc alContextBu lder(query, cand date, features)
            .orElse(follo dBySoc alContextBu lder(query, cand date, features))
            .orElse(top cSoc alContextBu lder(query, cand date, features))
            .orElse(popularV deoSoc alContextBu lder(query, cand date, features))
            .orElse(l stsSoc alContextBu lder(query, cand date, features))
            .orElse(popular nY AreaSoc alContextBu lder(query, cand date, features))
        case So (_) =>
          val conversat on d = features.getOrElse(Conversat onModule dFeature, None)
          // Only hydrate t  soc al context  nto t  root t et  n a conversat on module
           f (conversat on d.conta ns(cand date. d)) {
            extendedReplySoc alContextBu lder(query, cand date, features)
              .orElse(rece vedReplySoc alContextBu lder(query, cand date, features))
          } else None
      }
    } else None
  }
}
