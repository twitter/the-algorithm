package com.tw ter.ho _m xer.product.for_ 

 mport com.tw ter.ho _m xer.model.Ho Features._
 mport com.tw ter.ho _m xer.product.for_ .cand date_s ce.ScoredT etW hConversat on tadata
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateFeatureTransfor r
 mport com.tw ter.product_m xer.core.model.common. dent f er.Transfor r dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Bas cTop cContextFunct onal yType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.RecW hEducat onTop cContextFunct onal yType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Recom ndat onTop cContextFunct onal yType
 mport com.tw ter.t  l nes.render.{thr ftscala => tl}
 mport com.tw ter.t  l neserv ce.suggests.{thr ftscala => tls}

object For ScoredT etsResponseFeatureTransfor r
    extends Cand dateFeatureTransfor r[ScoredT etW hConversat on tadata] {

  overr de val  dent f er: Transfor r dent f er =
    Transfor r dent f er("For ScoredT etsResponse")

  overr de val features: Set[Feature[_, _]] = Set(
    AncestorsFeature,
    Author dFeature,
    Author sBlueVer f edFeature,
    Author sCreatorFeature,
    Author sGoldVer f edFeature,
    Author sGrayVer f edFeature,
    Author sLegacyVer f edFeature,
    Conversat onModuleFocalT et dFeature,
    Conversat onModule dFeature,
    D rectedAtUser dFeature,
    Exclus veConversat onAuthor dFeature,
    FullScor ngSucceededFeature,
    Favor edByUser dsFeature,
    Follo dByUser dsFeature,
     nNetworkFeature,
     nReplyToT et dFeature,
     nReplyToUser dFeature,
     sAncestorCand dateFeature,
     sReadFromCac Feature,
     sRet etFeature,
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
    Top c dSoc alContextFeature
  )

  overr de def transform( nput: ScoredT etW hConversat on tadata): FeatureMap =
    FeatureMapBu lder()
      .add(AncestorsFeature,  nput.ancestors.getOrElse(Seq.empty))
      .add(Author dFeature, So ( nput.author d))
      .add(Author sBlueVer f edFeature,  nput.author sBlueVer f ed.getOrElse(false))
      .add(Author sGoldVer f edFeature,  nput.author sGoldVer f ed.getOrElse(false))
      .add(Author sGrayVer f edFeature,  nput.author sGrayVer f ed.getOrElse(false))
      .add(Author sLegacyVer f edFeature,  nput.author sLegacyVer f ed.getOrElse(false))
      .add(Author sCreatorFeature,  nput.author sCreator.getOrElse(false))
      .add(Conversat onModule dFeature,  nput.conversat on d)
      .add(Conversat onModuleFocalT et dFeature,  nput.conversat onFocalT et d)
      .add(D rectedAtUser dFeature,  nput.d rectedAtUser d)
      .add(Exclus veConversat onAuthor dFeature,  nput.exclus veConversat onAuthor d)
      .add(SGSVal dL kedByUser dsFeature,  nput.sgsVal dL kedByUser ds.getOrElse(Seq.empty))
      .add(SGSVal dFollo dByUser dsFeature,  nput.sgsVal dFollo dByUser ds.getOrElse(Seq.empty))
      .add(Favor edByUser dsFeature,  nput.sgsVal dL kedByUser ds.getOrElse(Seq.empty))
      .add(Follo dByUser dsFeature,  nput.sgsVal dFollo dByUser ds.getOrElse(Seq.empty))
      .add(FullScor ngSucceededFeature, true)
      .add( nNetworkFeature,  nput. nNetwork.getOrElse(true))
      .add( nReplyToT et dFeature,  nput. nReplyToT et d)
      .add( nReplyToUser dFeature,  nput. nReplyToUser d)
      .add( sAncestorCand dateFeature,  nput.conversat onFocalT et d.ex sts(_ !=  nput.t et d))
      .add( sReadFromCac Feature,  nput. sReadFromCac .getOrElse(false))
      .add( sRet etFeature,  nput.s ceT et d. sDef ned)
      .add(
        Perspect veF lteredL kedByUser dsFeature,
         nput.perspect veF lteredL kedByUser ds.getOrElse(Seq.empty))
      .add(QuotedT et dFeature,  nput.quotedT et d)
      .add(QuotedUser dFeature,  nput.quotedUser d)
      .add(ScoreFeature,  nput.score)
      .add(S ceT et dFeature,  nput.s ceT et d)
      .add(S ceUser dFeature,  nput.s ceUser d)
      .add(StreamToKafkaFeature,  nput.streamToKafka.getOrElse(false))
      .add(SuggestTypeFeature,  nput.suggestType.orElse(So (tls.SuggestType.Undef ned)))
      .add(
        Top cContextFunct onal yTypeFeature,
         nput.top cFunct onal yType.collect {
          case tl.Top cContextFunct onal yType.Bas c => Bas cTop cContextFunct onal yType
          case tl.Top cContextFunct onal yType.Recom ndat on =>
            Recom ndat onTop cContextFunct onal yType
          case tl.Top cContextFunct onal yType.RecW hEducat on =>
            RecW hEducat onTop cContextFunct onal yType
        }
      )
      .add(Top c dSoc alContextFeature,  nput.top c d)
      .bu ld()
}
