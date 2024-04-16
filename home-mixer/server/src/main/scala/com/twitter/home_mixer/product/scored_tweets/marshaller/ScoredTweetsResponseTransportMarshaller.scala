package com.tw ter.ho _m xer.product.scored_t ets.marshaller

 mport com.tw ter.ho _m xer.model.Ho Features._
 mport com.tw ter.ho _m xer.product.scored_t ets.model.ScoredT etsResponse
 mport com.tw ter.ho _m xer.{thr ftscala => t}
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.TransportMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.Top cContextFunct onal yTypeMarshaller
 mport com.tw ter.product_m xer.core.model.common. dent f er.TransportMarshaller dent f er

/**
 * Marshall t  doma n model  nto   transport (Thr ft) model.
 */
object ScoredT etsResponseTransportMarshaller
    extends TransportMarshaller[ScoredT etsResponse, t.ScoredT etsResponse] {

  overr de val  dent f er: TransportMarshaller dent f er =
    TransportMarshaller dent f er("ScoredT etsResponse")

  overr de def apply( nput: ScoredT etsResponse): t.ScoredT etsResponse = {
    val scoredT ets =  nput.scoredT ets.map { t et =>
      mkScoredT et(t et.cand date dLong, t et.features)
    }
    t.ScoredT etsResponse(scoredT ets)
  }

  pr vate def mkScoredT et(t et d: Long, features: FeatureMap): t.ScoredT et = {
    val top cFunct onal yType = features
      .getOrElse(Top cContextFunct onal yTypeFeature, None)
      .map(Top cContextFunct onal yTypeMarshaller(_))

    t.ScoredT et(
      t et d = t et d,
      author d = features.get(Author dFeature).get,
      score = features.get(ScoreFeature),
      suggestType = features.get(SuggestTypeFeature),
      s ceT et d = features.getOrElse(S ceT et dFeature, None),
      s ceUser d = features.getOrElse(S ceUser dFeature, None),
      quotedT et d = features.getOrElse(QuotedT et dFeature, None),
      quotedUser d = features.getOrElse(QuotedUser dFeature, None),
       nReplyToT et d = features.getOrElse( nReplyToT et dFeature, None),
       nReplyToUser d = features.getOrElse( nReplyToUser dFeature, None),
      d rectedAtUser d = features.getOrElse(D rectedAtUser dFeature, None),
       nNetwork = So (features.getOrElse( nNetworkFeature, true)),
      sgsVal dL kedByUser ds = So (features.getOrElse(SGSVal dL kedByUser dsFeature, Seq.empty)),
      sgsVal dFollo dByUser ds =
        So (features.getOrElse(SGSVal dFollo dByUser dsFeature, Seq.empty)),
      top c d = features.getOrElse(Top c dSoc alContextFeature, None),
      top cFunct onal yType = top cFunct onal yType,
      ancestors = So (features.getOrElse(AncestorsFeature, Seq.empty)),
       sReadFromCac  = So (features.getOrElse( sReadFromCac Feature, false)),
      streamToKafka = So (features.getOrElse(StreamToKafkaFeature, false)),
      exclus veConversat onAuthor d =
        features.getOrElse(Exclus veConversat onAuthor dFeature, None),
      author tadata = So (
        t.Author tadata(
          blueVer f ed = features.getOrElse(Author sBlueVer f edFeature, false),
          goldVer f ed = features.getOrElse(Author sGoldVer f edFeature, false),
          grayVer f ed = features.getOrElse(Author sGrayVer f edFeature, false),
          legacyVer f ed = features.getOrElse(Author sLegacyVer f edFeature, false),
          creator = features.getOrElse(Author sCreatorFeature, false)
        )),
      lastScoredT  stampMs = None,
      cand dateP pel ne dent f er = None,
      t etUrls = None,
      perspect veF lteredL kedByUser ds =
        So (features.getOrElse(Perspect veF lteredL kedByUser dsFeature, Seq.empty)),
    )
  }
}
