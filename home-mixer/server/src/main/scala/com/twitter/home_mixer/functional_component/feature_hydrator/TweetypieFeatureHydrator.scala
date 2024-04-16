package com.tw ter.ho _m xer.funct onal_component.feature_hydrator

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.ho _m xer.model.Ho Features.Author dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Exclus veConversat onAuthor dFeature
 mport com.tw ter.ho _m xer.model.Ho Features. nNetworkFeature
 mport com.tw ter.ho _m xer.model.Ho Features. nReplyToT et dFeature
 mport com.tw ter.ho _m xer.model.Ho Features. sHydratedFeature
 mport com.tw ter.ho _m xer.model.Ho Features. sNsfwFeature
 mport com.tw ter.ho _m xer.model.Ho Features. sRet etFeature
 mport com.tw ter.ho _m xer.model.Ho Features.QuotedT etDroppedFeature
 mport com.tw ter.ho _m xer.model.Ho Features.QuotedT et dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.QuotedUser dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.S ceT et dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.S ceUser dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.T etLanguageFeature
 mport com.tw ter.ho _m xer.model.Ho Features.T etTextFeature
 mport com.tw ter.ho _m xer.model.request.Follow ngProduct
 mport com.tw ter.ho _m xer.model.request.For Product
 mport com.tw ter.ho _m xer.model.request.L stT etsProduct
 mport com.tw ter.ho _m xer.model.request.ScoredT etsProduct
 mport com.tw ter.ho _m xer.model.request.Subscr bedProduct
 mport com.tw ter.ho _m xer.ut l.t etyp e.RequestF elds
 mport com.tw ter.product_m xer.component_l brary.feature_hydrator.cand date.t et_ s_nsfw. sNsfw
 mport com.tw ter.product_m xer.component_l brary.feature_hydrator.cand date.t et_v s b l y_reason.V s b l yReason
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.Cand dateFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.spam.rtf.{thr ftscala => rtf}
 mport com.tw ter.st ch.St ch
 mport com.tw ter.st ch.t etyp e.{T etyP e => T etyp eSt chCl ent}
 mport com.tw ter.t etyp e.{thr ftscala => tp}
 mport com.tw ter.ut l.logg ng.Logg ng
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class T etyp eFeatureHydrator @ nject() (
  t etyp eSt chCl ent: T etyp eSt chCl ent,
  statsRece ver: StatsRece ver)
    extends Cand dateFeatureHydrator[P pel neQuery, T etCand date]
    w h Logg ng {

  overr de val  dent f er: FeatureHydrator dent f er = FeatureHydrator dent f er("T etyp e")

  overr de val features: Set[Feature[_, _]] = Set(
    Author dFeature,
    Exclus veConversat onAuthor dFeature,
     nReplyToT et dFeature,
     sHydratedFeature,
     sNsfw,
     sNsfwFeature,
     sRet etFeature,
    QuotedT etDroppedFeature,
    QuotedT et dFeature,
    QuotedUser dFeature,
    S ceT et dFeature,
    S ceUser dFeature,
    T etTextFeature,
    T etLanguageFeature,
    V s b l yReason
  )

  pr vate val DefaultFeatureMap = FeatureMapBu lder()
    .add( sHydratedFeature, false)
    .add( sNsfw, None)
    .add( sNsfwFeature, false)
    .add(QuotedT etDroppedFeature, false)
    .add(T etTextFeature, None)
    .add(V s b l yReason, None)
    .bu ld()

  overr de def apply(
    query: P pel neQuery,
    cand date: T etCand date,
    ex st ngFeatures: FeatureMap
  ): St ch[FeatureMap] = {
    val safetyLevel = query.product match {
      case Follow ngProduct => rtf.SafetyLevel.T  l neHo Latest
      case For Product =>
        val  nNetwork = ex st ngFeatures.getOrElse( nNetworkFeature, true)
         f ( nNetwork) rtf.SafetyLevel.T  l neHo  else rtf.SafetyLevel.T  l neHo Recom ndat ons
      case ScoredT etsProduct => rtf.SafetyLevel.T  l neHo 
      case L stT etsProduct => rtf.SafetyLevel.T  l neL sts
      case Subscr bedProduct => rtf.SafetyLevel.T  l neHo Subscr bed
      case unknown => throw new UnsupportedOperat onExcept on(s"Unknown product: $unknown")
    }

    val t etF eldsOpt ons = tp.GetT etF eldsOpt ons(
      t et ncludes = RequestF elds.T etTPHydrat onF elds,
       ncludeRet etedT et = true,
       ncludeQuotedT et = true,
      v s b l yPol cy = tp.T etV s b l yPol cy.UserV s ble,
      safetyLevel = So (safetyLevel),
      forUser d = query.getOpt onalUser d
    )

    val exclus veAuthor dOpt =
      ex st ngFeatures.getOrElse(Exclus veConversat onAuthor dFeature, None)

    t etyp eSt chCl ent.getT etF elds(t et d = cand date. d, opt ons = t etF eldsOpt ons).map {
      case tp.GetT etF eldsResult(_, tp.T etF eldsResultState.Found(found), quoteOpt, _) =>
        val coreData = found.t et.coreData
        val  sNsfwAdm n = coreData.ex sts(_.nsfwAdm n)
        val  sNsfwUser = coreData.ex sts(_.nsfwUser)

        val quotedT etDropped = quoteOpt.ex sts {
          case _: tp.T etF eldsResultState.F ltered => true
          case _: tp.T etF eldsResultState.NotFound => true
          case _ => false
        }
        val quotedT et sNsfw = quoteOpt.ex sts {
          case quoteT et: tp.T etF eldsResultState.Found =>
            quoteT et.found.t et.coreData.ex sts(data => data.nsfwAdm n || data.nsfwUser)
          case _ => false
        }

        val s ceT et sNsfw =
          found.ret etedT et.ex sts(_.coreData.ex sts(data => data.nsfwAdm n || data.nsfwUser))

        val t etText = coreData.map(_.text)
        val t etLanguage = found.t et.language.map(_.language)

        val t etAuthor d = coreData.map(_.user d)
        val  nReplyToT et d = coreData.flatMap(_.reply.flatMap(_. nReplyToStatus d))
        val ret etedT et d = found.ret etedT et.map(_. d)
        val quotedT et d = quoteOpt.flatMap {
          case quoteT et: tp.T etF eldsResultState.Found =>
            So (quoteT et.found.t et. d)
          case _ => None
        }

        val ret etedT etUser d = found.ret etedT et.flatMap(_.coreData).map(_.user d)
        val quotedT etUser d = quoteOpt.flatMap {
          case quoteT et: tp.T etF eldsResultState.Found =>
            quoteT et.found.t et.coreData.map(_.user d)
          case _ => None
        }

        val  sNsfw =  sNsfwAdm n ||  sNsfwUser || s ceT et sNsfw || quotedT et sNsfw

        FeatureMapBu lder()
          .add(Author dFeature, t etAuthor d)
          .add(Exclus veConversat onAuthor dFeature, exclus veAuthor dOpt)
          .add( nReplyToT et dFeature,  nReplyToT et d)
          .add( sHydratedFeature, true)
          .add( sNsfw, So ( sNsfw))
          .add( sNsfwFeature,  sNsfw)
          .add( sRet etFeature, ret etedT et d. sDef ned)
          .add(QuotedT etDroppedFeature, quotedT etDropped)
          .add(QuotedT et dFeature, quotedT et d)
          .add(QuotedUser dFeature, quotedT etUser d)
          .add(S ceT et dFeature, ret etedT et d)
          .add(S ceUser dFeature, ret etedT etUser d)
          .add(T etLanguageFeature, t etLanguage)
          .add(T etTextFeature, t etText)
          .add(V s b l yReason, found.suppressReason)
          .bu ld()

      //  f no t et result found, return default and pre-ex st ng features
      case _ =>
        DefaultFeatureMap ++ FeatureMapBu lder()
          .add(Author dFeature, ex st ngFeatures.getOrElse(Author dFeature, None))
          .add(Exclus veConversat onAuthor dFeature, exclus veAuthor dOpt)
          .add( nReplyToT et dFeature, ex st ngFeatures.getOrElse( nReplyToT et dFeature, None))
          .add( sRet etFeature, ex st ngFeatures.getOrElse( sRet etFeature, false))
          .add(QuotedT et dFeature, ex st ngFeatures.getOrElse(QuotedT et dFeature, None))
          .add(QuotedUser dFeature, ex st ngFeatures.getOrElse(QuotedUser dFeature, None))
          .add(S ceT et dFeature, ex st ngFeatures.getOrElse(S ceT et dFeature, None))
          .add(S ceUser dFeature, ex st ngFeatures.getOrElse(S ceUser dFeature, None))
          .add(T etLanguageFeature, ex st ngFeatures.getOrElse(T etLanguageFeature, None))
          .bu ld()
    }
  }
}
