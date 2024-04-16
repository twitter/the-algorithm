package com.tw ter.ho _m xer.product.for_ .feature_hydrator

 mport com.tw ter.ho _m xer.model.Ho Features.Author dFeature
 mport com.tw ter.ho _m xer.model.Ho Features. sHydratedFeature
 mport com.tw ter.ho _m xer.model.Ho Features.T etTextFeature
 mport com.tw ter.product_m xer.component_l brary.model.cand date.BaseT etCand date
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.Cand dateFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.spam.rtf.{thr ftscala => rtf}
 mport com.tw ter.st ch.St ch
 mport com.tw ter.st ch.t etyp e.{T etyP e => T etyp eSt chCl ent}
 mport com.tw ter.t etyp e.{thr ftscala => TP}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class T etPrev ewT etyp eCand dateFeatureHydrator @ nject() (
  t etyp eSt chCl ent: T etyp eSt chCl ent)
    extends Cand dateFeatureHydrator[P pel neQuery, BaseT etCand date] {

  pr vate val CoreT etF elds: Set[TP.T et nclude] = Set[TP.T et nclude](
    TP.T et nclude.T etF eld d(TP.T et. dF eld. d),
    TP.T et nclude.T etF eld d(TP.T et.CoreDataF eld. d)
  )

  pr vate val DefaultFeatureMap = FeatureMapBu lder()
    .add(T etTextFeature, None)
    .add( sHydratedFeature, false)
    .add(Author dFeature, None)
    .bu ld()

  overr de val features: Set[Feature[_, _]] =
    Set(T etTextFeature,  sHydratedFeature, Author dFeature)

  overr de val  dent f er: FeatureHydrator dent f er =
    FeatureHydrator dent f er("T etPrev ewT etyp e")

  overr de def apply(
    query: P pel neQuery,
    cand date: BaseT etCand date,
    ex st ngFeatures: FeatureMap
  ): St ch[FeatureMap] = {
    t etyp eSt chCl ent
      .getT etF elds(
        t et d = cand date. d,
        opt ons = TP.GetT etF eldsOpt ons(
          t et ncludes = CoreT etF elds,
           ncludeRet etedT et = false,
           ncludeQuotedT et = false,
          v s b l yPol cy = TP.T etV s b l yPol cy.UserV s ble,
          safetyLevel = So (rtf.SafetyLevel.T  l neHo T etPrev ew),
          forUser d = query.getOpt onalUser d
        )
      ).map {
        case TP.GetT etF eldsResult(_, TP.T etF eldsResultState.Found(found), quoteOpt, _) =>
          val t etText = found.t et.coreData.map(_.text)
          FeatureMapBu lder()
            .add(T etTextFeature, t etText)
            .add( sHydratedFeature, true)
            .add(Author dFeature, found.t et.coreData.map(_.user d))
            .bu ld()
        //  f no t et result found, return default features
        case _ =>
          DefaultFeatureMap
      }

  }
}
