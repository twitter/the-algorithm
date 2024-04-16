package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator

 mport com.tw ter.ho _m xer.model.Ho Features.AncestorsFeature
 mport com.tw ter.ho _m xer.model.Ho Features. nReplyToT et dFeature
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.Cand dateFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.ut l.OffloadFuturePools
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etconvosvc.t et_ancestor.{thr ftscala => ta}
 mport com.tw ter.t etconvosvc.{thr ftscala => tcs}
 mport com.tw ter.ut l.Future
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class AncestorFeatureHydrator @ nject() (
  conversat onServ ceCl ent: tcs.Conversat onServ ce. thodPerEndpo nt)
    extends Cand dateFeatureHydrator[P pel neQuery, T etCand date] {

  overr de val  dent f er: FeatureHydrator dent f er = FeatureHydrator dent f er("Ancestor")

  overr de val features: Set[Feature[_, _]] = Set(AncestorsFeature)

  pr vate val DefaultFeatureMap = FeatureMapBu lder().add(AncestorsFeature, Seq.empty).bu ld()

  overr de def apply(
    query: P pel neQuery,
    cand date: T etCand date,
    ex st ngFeatures: FeatureMap
  ): St ch[FeatureMap] = OffloadFuturePools.offloadFuture {
     f (ex st ngFeatures.getOrElse( nReplyToT et dFeature, None). sDef ned) {
      val ancestorsRequest = tcs.GetAncestorsRequest(Seq(cand date. d))
      conversat onServ ceCl ent.getAncestors(ancestorsRequest).map { getAncestorsResponse =>
        val ancestors = getAncestorsResponse.ancestors. adOpt on
          .collect {
            case tcs.T etAncestorsResult.T etAncestors(ancestorsResult)
                 f ancestorsResult.nonEmpty =>
              ancestorsResult. ad.ancestors ++ getTruncatedRootT et(ancestorsResult. ad)
          }.getOrElse(Seq.empty)

        FeatureMapBu lder().add(AncestorsFeature, ancestors).bu ld()
      }
    } else Future.value(DefaultFeatureMap)
  }

  pr vate def getTruncatedRootT et(
    ancestors: ta.T etAncestors,
  ): Opt on[ta.T etAncestor] = {
    ancestors.conversat onRootAuthor d.collect {
      case rootAuthor d
           f ancestors.state == ta.ReplyState.Part al &&
            ancestors.ancestors.last.t et d != ancestors.conversat on d =>
        ta.T etAncestor(ancestors.conversat on d, rootAuthor d)
    }
  }
}
