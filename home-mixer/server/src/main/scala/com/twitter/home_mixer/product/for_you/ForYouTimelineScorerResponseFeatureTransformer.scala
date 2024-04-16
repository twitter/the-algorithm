package com.tw ter.ho _m xer.product.for_ 

 mport com.tw ter.t etconvosvc.t et_ancestor.{thr ftscala => ta}
 mport com.tw ter.ho _m xer.model.Ho Features._
 mport com.tw ter. d aserv ces.commons.t et d a.{thr ftscala => mt}
 mport com.tw ter.product_m xer.component_l brary.cand date_s ce.t  l ne_scorer.ScoredT etCand dateW hFocalT et
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateFeatureTransfor r
 mport com.tw ter.product_m xer.core.model.common. dent f er.Transfor r dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Bas cTop cContextFunct onal yType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.RecW hEducat onTop cContextFunct onal yType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Recom ndat onTop cContextFunct onal yType
 mport com.tw ter.search.common.constants.thr ftjava.Thr ftLanguage
 mport com.tw ter.search.common.ut l.lang.Thr ftLanguageUt l
 mport com.tw ter.snowflake. d.Snowflake d
 mport com.tw ter.t  l nem xer. nject on.model.cand date.Aud oSpace taData
 mport com.tw ter.t  l nes.conversat on_features.{thr ftscala => cvt}
 mport com.tw ter.t  l nescorer.common.scoredt etcand date.{thr ftscala => stc}
 mport com.tw ter.t  l neserv ce.suggests.{thr ftscala => tls}

object For T  l neScorerResponseFeatureTransfor r
    extends Cand dateFeatureTransfor r[ScoredT etCand dateW hFocalT et] {

  overr de val  dent f er: Transfor r dent f er =
    Transfor r dent f er("For T  l neScorerResponse")

  overr de val features: Set[Feature[_, _]] = Set(
    AncestorsFeature,
    Aud oSpace taDataFeature,
    Author dFeature,
    Author sBlueVer f edFeature,
    Author sCreatorFeature,
    Author sGoldVer f edFeature,
    Author sGrayVer f edFeature,
    Author sLegacyVer f edFeature,
    AuthoredByContextualUserFeature,
    Cand dateS ce dFeature,
    Conversat onFeature,
    Conversat onModuleFocalT et dFeature,
    Conversat onModule dFeature,
    D rectedAtUser dFeature,
    Earlyb rdFeature,
    Ent yTokenFeature,
    Exclus veConversat onAuthor dFeature,
    Favor edByUser dsFeature,
    Follo dByUser dsFeature,
    Top c dSoc alContextFeature,
    Top cContextFunct onal yTypeFeature,
    From nNetworkS ceFeature,
    FullScor ngSucceededFeature,
    HasD splayedTextFeature,
     nNetworkFeature,
     nReplyToT et dFeature,
     sAncestorCand dateFeature,
     sExtendedReplyFeature,
     sRandomT etFeature,
     sReadFromCac Feature,
     sRet etFeature,
     sRet etedReplyFeature,
    NonSelfFavor edByUser dsFeature,
    Num magesFeature,
    Or g nalT etCreat onT  FromSnowflakeFeature,
    Pred ct onRequest dFeature,
    QuotedT et dFeature,
    ScoreFeature,
    S mclustersT etTopKClustersW hScoresFeature,
    S ceT et dFeature,
    S ceUser dFeature,
    StreamToKafkaFeature,
    SuggestTypeFeature,
    T etLanguageFeature,
    V deoDurat onMsFeature,
  )

  // Convert language code to  SO 639-3 format
  pr vate def getLanguage SOFormatByValue(languageCodeValue:  nt): Str ng =
    Thr ftLanguageUt l.getLanguageCodeOf(Thr ftLanguage.f ndByValue(languageCodeValue))

  overr de def transform(
    cand dateW hFocalT et: ScoredT etCand dateW hFocalT et
  ): FeatureMap = {
    val cand date: stc.v1.ScoredT etCand date = cand dateW hFocalT et.cand date
    val focalT et d = cand dateW hFocalT et.focalT et dOpt

    val or g nalT et d = cand date.s ceT et d.getOrElse(cand date.t et d)
    val t etFeatures = cand date.t etFeaturesMap.flatMap(_.get(or g nalT et d))
    val earlyb rdFeatures = t etFeatures.flatMap(_.recapFeatures.flatMap(_.t etFeatures))
    val d rectedAtUser s nF rstDegree =
      earlyb rdFeatures.flatMap(_.d rectedAtUser d s nF rstDegree)
    val  sReply = cand date. nReplyToT et d.nonEmpty
    val  sRet et = cand date. sRet et.getOrElse(false)
    val  s nNetwork = cand date. s nNetwork.getOrElse(true)
    val conversat onFeatures = cand date.conversat onFeatures.flatMap {
      case cvt.Conversat onFeatures.V1(cand date) => So (cand date)
      case _ => None
    }
    val num mages = cand date. d a taData
      .map(
        _.count( d aEnt y =>
           d aEnt y. d a nfo.ex sts(_. s nstanceOf[mt. d a nfo. mage nfo]) ||
             d aEnt y. d a nfo. sEmpty))
    val has mage = earlyb rdFeatures.ex sts(_.has mage)
    val hasV deo = earlyb rdFeatures.ex sts(_.hasV deo)
    val hasCard = earlyb rdFeatures.ex sts(_.hasCard)
    val hasQuote = earlyb rdFeatures.ex sts(_.hasQuote.conta ns(true))
    val hasD splayedText = earlyb rdFeatures.ex sts(_.t etLength.ex sts(length => {
      val num d a = Seq(hasV deo, (has mage || hasCard), hasQuote).count(b => b)
      val tcoLengthsPlusSpaces = 23 * num d a + ( f (num d a > 0) num d a - 1 else 0)
      length > tcoLengthsPlusSpaces
    }))
    val suggestType = cand date.overr deSuggestType.orElse(So (tls.SuggestType.Undef ned))

    val top cSoc alProof tadataOpt = cand date.ent yData.flatMap(_.top cSoc alProof tadata)
    val top c dSoc alContextOpt = top cSoc alProof tadataOpt.map(_.top c d)
    val top cContextFunct onal yTypeOpt =
      top cSoc alProof tadataOpt.map(_.top cContextFunct onal yType).collect {
        case stc.v1.Top cContextFunct onal yType.Bas c => Bas cTop cContextFunct onal yType
        case stc.v1.Top cContextFunct onal yType.Recom ndat on =>
          Recom ndat onTop cContextFunct onal yType
        case stc.v1.Top cContextFunct onal yType.RecW hEducat on =>
          RecW hEducat onTop cContextFunct onal yType
      }

    FeatureMapBu lder()
      .add(
        AncestorsFeature,
        cand date.ancestors
          .getOrElse(Seq.empty)
          .map(ancestor => ta.T etAncestor(ancestor.t et d, ancestor.user d.getOrElse(0L))))
      .add(
        Aud oSpace taDataFeature,
        cand date.aud oSpace taDatal st.map(_. ad).map(Aud oSpace taData.fromThr ft))
      .add(Author dFeature, So (cand date.author d))
      .add(Author sBlueVer f edFeature, cand date.author sBlueVer f ed.getOrElse(false))
      .add(
        Author sCreatorFeature,
        cand date.author sCreator.getOrElse(false)
      )
      .add(Author sGoldVer f edFeature, cand date.author sGoldVer f ed.getOrElse(false))
      .add(Author sGrayVer f edFeature, cand date.author sGrayVer f ed.getOrElse(false))
      .add(Author sLegacyVer f edFeature, cand date.author sLegacyVer f ed.getOrElse(false))
      .add(
        AuthoredByContextualUserFeature,
        cand date.v e r d.conta ns(cand date.author d) ||
          cand date.v e r d.ex sts(cand date.s ceUser d.conta ns))
      .add(Cand dateS ce dFeature, cand date.cand dateT etS ce d)
      .add(Conversat onFeature, conversat onFeatures)
      .add(Conversat onModule dFeature, cand date.conversat on d)
      .add(Conversat onModuleFocalT et dFeature, focalT et d)
      .add(D rectedAtUser dFeature, cand date.d rectedAtUser d)
      .add(Earlyb rdFeature, earlyb rdFeatures)
      // T   s temporary, w ll need to be updated w h t  encoded str ng.
      .add(Ent yTokenFeature, So ("test_Ent yTokenFor "))
      .add(Exclus veConversat onAuthor dFeature, cand date.exclus veConversat onAuthor d)
      .add(Favor edByUser dsFeature, cand date.favor edByUser ds.getOrElse(Seq.empty))
      .add(Follo dByUser dsFeature, cand date.follo dByUser ds.getOrElse(Seq.empty))
      .add(Top c dSoc alContextFeature, top c dSoc alContextOpt)
      .add(Top cContextFunct onal yTypeFeature, top cContextFunct onal yTypeOpt)
      .add(FullScor ngSucceededFeature, cand date.fullScor ngSucceeded.getOrElse(false))
      .add(HasD splayedTextFeature, hasD splayedText)
      .add( nNetworkFeature, cand date. s nNetwork.getOrElse(true))
      .add( nReplyToT et dFeature, cand date. nReplyToT et d)
      .add( sAncestorCand dateFeature, cand date. sAncestorCand date.getOrElse(false))
      .add(
         sExtendedReplyFeature,
         s nNetwork &&  sReply && ! sRet et && d rectedAtUser s nF rstDegree.conta ns(false))
      .add(From nNetworkS ceFeature, cand date. s nNetwork.getOrElse(true))
      .add( sRandomT etFeature, cand date. sRandomT et.getOrElse(false))
      .add( sReadFromCac Feature, cand date. sReadFromCac .getOrElse(false))
      .add( sRet etFeature, cand date. sRet et.getOrElse(false))
      .add( sRet etedReplyFeature,  sReply &&  sRet et)
      .add(
        NonSelfFavor edByUser dsFeature,
        cand date.favor edByUser ds.getOrElse(Seq.empty).f lterNot(_ == cand date.author d))
      .add(Num magesFeature, num mages)
      .add(
        Or g nalT etCreat onT  FromSnowflakeFeature,
        Snowflake d.t  From dOpt(or g nalT et d))
      .add(Pred ct onRequest dFeature, cand date.pred ct onRequest d)
      .add(ScoreFeature, So (cand date.score))
      .add(
        S mclustersT etTopKClustersW hScoresFeature,
        cand date.s mclustersT etTopKClustersW hScores.map(_.toMap).getOrElse(Map.empty))
      .add(
        StreamToKafkaFeature,
        cand date.pred ct onRequest d.nonEmpty && cand date.fullScor ngSucceeded.getOrElse(false))
      .add(S ceT et dFeature, cand date.s ceT et d)
      .add(S ceUser dFeature, cand date.s ceUser d)
      .add(SuggestTypeFeature, suggestType)
      .add(QuotedT et dFeature, cand date.quotedT et d)
      .add(
        T etLanguageFeature,
        earlyb rdFeatures.flatMap(_.language.map(_.value)).map(getLanguage SOFormatByValue))
      .add(V deoDurat onMsFeature, earlyb rdFeatures.flatMap(_.v deoDurat onMs))
      .bu ld()
  }
}
