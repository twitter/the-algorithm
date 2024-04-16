package com.tw ter.ho _m xer.product.scored_t ets.response_transfor r

 mport com.tw ter.ho _m xer.marshaller.t  l nes.Top cContextFunct onal yTypeUnmarshaller
 mport com.tw ter.ho _m xer.model.Ho Features.AncestorsFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Author dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Author sBlueVer f edFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Author sCreatorFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Author sGoldVer f edFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Author sGrayVer f edFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Author sLegacyVer f edFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Cac dCand dateP pel ne dent f erFeature
 mport com.tw ter.ho _m xer.model.Ho Features.D rectedAtUser dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Exclus veConversat onAuthor dFeature
 mport com.tw ter.ho _m xer.model.Ho Features. nNetworkFeature
 mport com.tw ter.ho _m xer.model.Ho Features. nReplyToT et dFeature
 mport com.tw ter.ho _m xer.model.Ho Features. nReplyToUser dFeature
 mport com.tw ter.ho _m xer.model.Ho Features. sReadFromCac Feature
 mport com.tw ter.ho _m xer.model.Ho Features. sRet etFeature
 mport com.tw ter.ho _m xer.model.Ho Features.LastScoredT  stampMsFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Perspect veF lteredL kedByUser dsFeature
 mport com.tw ter.ho _m xer.model.Ho Features.QuotedT et dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.QuotedUser dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.SGSVal dFollo dByUser dsFeature
 mport com.tw ter.ho _m xer.model.Ho Features.SGSVal dL kedByUser dsFeature
 mport com.tw ter.ho _m xer.model.Ho Features.ScoreFeature
 mport com.tw ter.ho _m xer.model.Ho Features.S ceT et dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.S ceUser dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.StreamToKafkaFeature
 mport com.tw ter.ho _m xer.model.Ho Features.SuggestTypeFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Top cContextFunct onal yTypeFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Top c dSoc alContextFeature
 mport com.tw ter.ho _m xer.model.Ho Features.T etUrlsFeature
 mport com.tw ter.ho _m xer.model.Ho Features.  ghtedModelScoreFeature
 mport com.tw ter.ho _m xer.{thr ftscala => hmt}
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateFeatureTransfor r
 mport com.tw ter.product_m xer.core.model.common. dent f er.Transfor r dent f er

object Cac dScoredT etsResponseFeatureTransfor r
    extends Cand dateFeatureTransfor r[hmt.ScoredT et] {

  overr de val  dent f er: Transfor r dent f er =
    Transfor r dent f er("Cac dScoredT etsResponse")

  overr de val features: Set[Feature[_, _]] = Set(
    AncestorsFeature,
    Author dFeature,
    Author sBlueVer f edFeature,
    Author sCreatorFeature,
    Author sGoldVer f edFeature,
    Author sGrayVer f edFeature,
    Author sLegacyVer f edFeature,
    Cac dCand dateP pel ne dent f erFeature,
    D rectedAtUser dFeature,
    Exclus veConversat onAuthor dFeature,
     nNetworkFeature,
     nReplyToT et dFeature,
     nReplyToUser dFeature,
     sReadFromCac Feature,
     sRet etFeature,
    LastScoredT  stampMsFeature,
    Perspect veF lteredL kedByUser dsFeature,
    QuotedT et dFeature,
    QuotedUser dFeature,
    SGSVal dFollo dByUser dsFeature,
    SGSVal dL kedByUser dsFeature,
    ScoreFeature,
    S ceT et dFeature,
    S ceUser dFeature,
    StreamToKafkaFeature,
    SuggestTypeFeature,
    Top cContextFunct onal yTypeFeature,
    Top c dSoc alContextFeature,
    T etUrlsFeature,
      ghtedModelScoreFeature
  )

  overr de def transform(cand date: hmt.ScoredT et): FeatureMap =
    FeatureMapBu lder()
      .add(AncestorsFeature, cand date.ancestors.getOrElse(Seq.empty))
      .add(Author dFeature, So (cand date.author d))
      .add(Author sBlueVer f edFeature, cand date.author tadata.ex sts(_.blueVer f ed))
      .add(Author sGoldVer f edFeature, cand date.author tadata.ex sts(_.goldVer f ed))
      .add(Author sGrayVer f edFeature, cand date.author tadata.ex sts(_.grayVer f ed))
      .add(Author sLegacyVer f edFeature, cand date.author tadata.ex sts(_.legacyVer f ed))
      .add(Author sCreatorFeature, cand date.author tadata.ex sts(_.creator))
      .add(Cac dCand dateP pel ne dent f erFeature, cand date.cand dateP pel ne dent f er)
      .add(D rectedAtUser dFeature, cand date.d rectedAtUser d)
      .add(Exclus veConversat onAuthor dFeature, cand date.exclus veConversat onAuthor d)
      .add( nNetworkFeature, cand date. nNetwork.getOrElse(true))
      .add( nReplyToT et dFeature, cand date. nReplyToT et d)
      .add( nReplyToUser dFeature, cand date. nReplyToUser d)
      .add( sReadFromCac Feature, true)
      .add( sRet etFeature, cand date.s ceT et d. sDef ned)
      .add(LastScoredT  stampMsFeature, cand date.lastScoredT  stampMs)
      .add(
        Perspect veF lteredL kedByUser dsFeature,
        cand date.perspect veF lteredL kedByUser ds.getOrElse(Seq.empty))
      .add(QuotedT et dFeature, cand date.quotedT et d)
      .add(QuotedUser dFeature, cand date.quotedUser d)
      .add(ScoreFeature, cand date.score)
      .add(SGSVal dL kedByUser dsFeature, cand date.sgsVal dL kedByUser ds.getOrElse(Seq.empty))
      .add(
        SGSVal dFollo dByUser dsFeature,
        cand date.sgsVal dFollo dByUser ds.getOrElse(Seq.empty))
      .add(S ceT et dFeature, cand date.s ceT et d)
      .add(S ceUser dFeature, cand date.s ceUser d)
      .add(StreamToKafkaFeature, false)
      .add(SuggestTypeFeature, cand date.suggestType)
      .add(
        Top cContextFunct onal yTypeFeature,
        cand date.top cFunct onal yType.map(Top cContextFunct onal yTypeUnmarshaller(_)))
      .add(Top c dSoc alContextFeature, cand date.top c d)
      .add(T etUrlsFeature, cand date.t etUrls.getOrElse(Seq.empty))
      .add(  ghtedModelScoreFeature, cand date.score)
      .bu ld()
}
