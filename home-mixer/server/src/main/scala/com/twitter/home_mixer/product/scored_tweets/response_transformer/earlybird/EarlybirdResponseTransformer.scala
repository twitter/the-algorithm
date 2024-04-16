package com.tw ter.ho _m xer.product.scored_t ets.response_transfor r.earlyb rd

 mport com.tw ter.ho _m xer.model.Ho Features.Author dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Cand dateS ce dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.D rectedAtUser dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Earlyb rdScoreFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Earlyb rdSearchResultFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Exclus veConversat onAuthor dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.From nNetworkS ceFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Has mageFeature
 mport com.tw ter.ho _m xer.model.Ho Features.HasV deoFeature
 mport com.tw ter.ho _m xer.model.Ho Features. nReplyToT et dFeature
 mport com.tw ter.ho _m xer.model.Ho Features. nReplyToUser dFeature
 mport com.tw ter.ho _m xer.model.Ho Features. sRandomT etFeature
 mport com.tw ter.ho _m xer.model.Ho Features. sRet etFeature
 mport com.tw ter.ho _m xer.model.Ho Features. nt onScreenNa Feature
 mport com.tw ter.ho _m xer.model.Ho Features. nt onUser dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.QuotedT et dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.QuotedUser dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.S ceT et dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.S ceUser dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.StreamToKafkaFeature
 mport com.tw ter.ho _m xer.model.Ho Features.SuggestTypeFeature
 mport com.tw ter.ho _m xer.model.Ho Features.T etUrlsFeature
 mport com.tw ter.ho _m xer.ut l.t etyp e.content.T et d aFeaturesExtractor
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.search.earlyb rd.{thr ftscala => eb}

object Earlyb rdResponseTransfor r {

  val features: Set[Feature[_, _]] = Set(
    Author dFeature,
    Cand dateS ce dFeature,
    D rectedAtUser dFeature,
    Earlyb rdScoreFeature,
    Earlyb rdSearchResultFeature,
    Exclus veConversat onAuthor dFeature,
    From nNetworkS ceFeature,
    Has mageFeature,
    HasV deoFeature,
     nReplyToT et dFeature,
     nReplyToUser dFeature,
     sRandomT etFeature,
     sRet etFeature,
     nt onScreenNa Feature,
     nt onUser dFeature,
    StreamToKafkaFeature,
    QuotedT et dFeature,
    QuotedUser dFeature,
    S ceT et dFeature,
    S ceUser dFeature,
    SuggestTypeFeature,
    T etUrlsFeature
  )

  def transform(cand date: eb.Thr ftSearchResult): FeatureMap = {
    val t et = cand date.t etyp eT et
    val quotedT et = t et.flatMap(_.quotedT et)
    val  nt ons = t et.flatMap(_. nt ons).getOrElse(Seq.empty)
    val coreData = t et.flatMap(_.coreData)
    val share = coreData.flatMap(_.share)
    val reply = coreData.flatMap(_.reply)
    FeatureMapBu lder()
      .add(Author dFeature, coreData.map(_.user d))
      .add(D rectedAtUser dFeature, coreData.flatMap(_.d rectedAtUser.map(_.user d)))
      .add(Earlyb rdSearchResultFeature, So (cand date))
      .add(Earlyb rdScoreFeature, cand date. tadata.flatMap(_.score))
      .add(
        Exclus veConversat onAuthor dFeature,
        t et.flatMap(_.exclus veT etControl.map(_.conversat onAuthor d)))
      .add(From nNetworkS ceFeature, false)
      .add(Has mageFeature, t et.ex sts(T et d aFeaturesExtractor.has mage))
      .add(HasV deoFeature, t et.ex sts(T et d aFeaturesExtractor.hasV deo))
      .add( nReplyToT et dFeature, reply.flatMap(_. nReplyToStatus d))
      .add( nReplyToUser dFeature, reply.map(_. nReplyToUser d))
      .add( sRandomT etFeature, cand date.t etFeatures.ex sts(_. sRandomT et.getOrElse(false)))
      .add( sRet etFeature, share. sDef ned)
      .add( nt onScreenNa Feature,  nt ons.map(_.screenNa ))
      .add( nt onUser dFeature,  nt ons.flatMap(_.user d))
      .add(StreamToKafkaFeature, true)
      .add(QuotedT et dFeature, quotedT et.map(_.t et d))
      .add(QuotedUser dFeature, quotedT et.map(_.user d))
      .add(S ceT et dFeature, share.map(_.s ceStatus d))
      .add(S ceUser dFeature, share.map(_.s ceUser d))
      .add(
        T etUrlsFeature,
        cand date. tadata.flatMap(_.t etUrls.map(_.map(_.or g nalUrl))).getOrElse(Seq.empty))
      .bu ld()
  }
}
