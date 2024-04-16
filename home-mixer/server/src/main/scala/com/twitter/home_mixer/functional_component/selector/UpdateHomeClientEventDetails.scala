package com.tw ter.ho _m xer.funct onal_component.selector

 mport com.tw ter.ho _m xer.funct onal_component.decorator.bu lder.Ho Cl entEventDeta lsBu lder
 mport com.tw ter.ho _m xer.model.Ho Features.AncestorsFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Conversat onModule2D splayedT etsFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Conversat onModuleHasGapFeature
 mport com.tw ter.ho _m xer.model.Ho Features.HasRandomT etFeature
 mport com.tw ter.ho _m xer.model.Ho Features. sRandomT etAboveFeature
 mport com.tw ter.ho _m xer.model.Ho Features. sRandomT etFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Pos  onFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Served nConversat onModuleFeature
 mport com.tw ter.ho _m xer.model.Ho Features.ServedS zeFeature
 mport com.tw ter.product_m xer.component_l brary.model.presentat on.urt.Urt emPresentat on
 mport com.tw ter.product_m xer.component_l brary.model.presentat on.urt.UrtModulePresentat on
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.common.Cand dateScope
 mport com.tw ter.product_m xer.core.funct onal_component.common.Spec f cP pel nes
 mport com.tw ter.product_m xer.core.funct onal_component.selector.Selector
 mport com.tw ter.product_m xer.core.funct onal_component.selector.SelectorResult
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.common.presentat on. emCand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.common.presentat on.ModuleCand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.t et.T et em
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

/**
 * Bu lds ser al zed t et type  tr cs controller data and updates Cl ent Event Deta ls
 * and Cand date Presentat ons w h t   nfo.
 *
 * Currently only updates presentat on of  em Cand dates. T  needs to be updated
 * w n modules are added.
 *
 * T   s  mple nted as a Selector  nstead of a Decorator  n t  Cand date P pel ne
 * because   need to add controller data that looks at t  f nal t  l ne as a whole
 * (e.g. served s ze, f nal cand date pos  ons).
 *
 * @param cand dateP pel nes - only cand dates from t  spec f ed p pel ne w ll be updated
 */
case class UpdateHo Cl entEventDeta ls(cand dateP pel nes: Set[Cand dateP pel ne dent f er])
    extends Selector[P pel neQuery] {

  overr de val p pel neScope: Cand dateScope = Spec f cP pel nes(cand dateP pel nes)

  pr vate val deta lsBu lder = Ho Cl entEventDeta lsBu lder()

  overr de def apply(
    query: P pel neQuery,
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    result: Seq[Cand dateW hDeta ls]
  ): SelectorResult = {
    val selectedCand dates = result.f lter(p pel neScope.conta ns)

    val randomT etsByPos  on = result
      .map(_.features.getOrElse( sRandomT etFeature, false))
      .z pW h ndex.map(_.swap).toMap

    val resultFeatures = FeatureMapBu lder()
      .add(ServedS zeFeature, So (selectedCand dates.s ze))
      .add(HasRandomT etFeature, randomT etsByPos  on.values erator.conta ns(true))
      .bu ld()

    val updatedResult = result.z pW h ndex.map {
      case ( em @  emCand dateW hDeta ls(cand date, _, _), pos  on)
           f p pel neScope.conta ns( em) =>
        val resultCand dateFeatures = FeatureMapBu lder()
          .add(Pos  onFeature, So (pos  on))
          .add( sRandomT etAboveFeature, randomT etsByPos  on.getOrElse(pos  on - 1, false))
          .bu ld()

        update emPresentat on(query,  em, resultFeatures, resultCand dateFeatures)

      case (module @ ModuleCand dateW hDeta ls(cand dates, presentat on, features), pos  on)
           f p pel neScope.conta ns(module) =>
        val resultCand dateFeatures = FeatureMapBu lder()
          .add(Pos  onFeature, So (pos  on))
          .add( sRandomT etAboveFeature, randomT etsByPos  on.getOrElse(pos  on - 1, false))
          .add(Served nConversat onModuleFeature, true)
          .add(Conversat onModule2D splayedT etsFeature, module.cand dates.s ze == 2)
          .add(
            Conversat onModuleHasGapFeature,
            module.cand dates.last.features.getOrElse(AncestorsFeature, Seq.empty).s ze > 2)
          .bu ld()

        val updated emCand dates =
          cand dates.map(update emPresentat on(query, _, resultFeatures, resultCand dateFeatures))

        val updatedCand dateFeatures = features ++ resultFeatures ++ resultCand dateFeatures

        val updatedPresentat on = presentat on.map {
          case urtModule @ UrtModulePresentat on(t  l neModule) =>
            val cl entEventDeta ls =
              deta lsBu lder(
                query,
                cand dates.last.cand date,
                query.features.get ++ updatedCand dateFeatures)
            val updatedCl entEvent nfo =
              t  l neModule.cl entEvent nfo.map(_.copy(deta ls = cl entEventDeta ls))
            val updatedT  l neModule =
              t  l neModule.copy(cl entEvent nfo = updatedCl entEvent nfo)
            urtModule.copy(t  l neModule = updatedT  l neModule)
        }

        module.copy(
          cand dates = updated emCand dates,
          presentat on = updatedPresentat on,
          features = updatedCand dateFeatures
        )

      case (any, pos  on) => any
    }

    SelectorResult(rema n ngCand dates = rema n ngCand dates, result = updatedResult)
  }

  pr vate def update emPresentat on(
    query: P pel neQuery,
     em:  emCand dateW hDeta ls,
    resultCand dateFeatures: FeatureMap,
    resultFeatures: FeatureMap,
  ):  emCand dateW hDeta ls = {
    val updated emCand dateFeatures =  em.features ++ resultFeatures ++ resultCand dateFeatures

    val updatedPresentat on =  em.presentat on.map {
      case urt em @ Urt emPresentat on(t  l ne em: T et em, _) =>
        val cl entEventDeta ls =
          deta lsBu lder(query,  em.cand date, query.features.get ++ updated emCand dateFeatures)
        val updatedCl entEvent nfo =
          t  l ne em.cl entEvent nfo.map(_.copy(deta ls = cl entEventDeta ls))
        val updatedT  l ne em = t  l ne em.copy(cl entEvent nfo = updatedCl entEvent nfo)
        urt em.copy(t  l ne em = updatedT  l ne em)
      case any => any
    }
     em.copy(presentat on = updatedPresentat on, features = updated emCand dateFeatures)
  }
}
