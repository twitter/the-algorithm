package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.adapters.earlyb rd

 mport com.tw ter.ml.ap .Feature
 mport com.tw ter.ml.ap .FeatureContext
 mport com.tw ter.ml.ap .R chDataRecord
 mport com.tw ter.ml.ap .ut l.DataRecordConverters.R chDataRecordWrapper
 mport com.tw ter.search.common.features.{thr ftscala => sc}
 mport com.tw ter.t  l nes.pred ct on.common.adapters.T  l nesMutat ngAdapterBase
 mport com.tw ter.t  l nes.pred ct on.features.common. nReplyToT etT  l nesSharedFeatures
 mport com.tw ter.t  l nes.pred ct on.features.recap. nReplyToRecapFeatures
 mport java.lang.{Boolean => JBoolean}
 mport java.lang.{Double => JDouble}

object  nReplyToEarlyb rdAdapter
    extends T  l nesMutat ngAdapterBase[Opt on[sc.Thr ftT etFeatures]] {

  overr de val getFeatureContext: FeatureContext = new FeatureContext(
    // TextFeatures
     nReplyToT etT  l nesSharedFeatures.WE GHTED_FAV_COUNT,
     nReplyToT etT  l nesSharedFeatures.WE GHTED_RETWEET_COUNT,
     nReplyToT etT  l nesSharedFeatures.WE GHTED_REPLY_COUNT,
     nReplyToT etT  l nesSharedFeatures.WE GHTED_QUOTE_COUNT,
     nReplyToT etT  l nesSharedFeatures.DECAYED_FAVOR TE_COUNT,
     nReplyToT etT  l nesSharedFeatures.DECAYED_RETWEET_COUNT,
     nReplyToT etT  l nesSharedFeatures.DECAYED_REPLY_COUNT,
     nReplyToT etT  l nesSharedFeatures.DECAYED_QUOTE_COUNT,
     nReplyToT etT  l nesSharedFeatures.QUOTE_COUNT,
     nReplyToT etT  l nesSharedFeatures.HAS_QUOTE,
     nReplyToT etT  l nesSharedFeatures.EARLYB RD_SCORE,
     nReplyToRecapFeatures.PREV_USER_TWEET_ENGAGEMENT,
     nReplyToRecapFeatures. S_SENS T VE,
     nReplyToRecapFeatures. S_AUTHOR_NEW,
     nReplyToRecapFeatures.NUM_MENT ONS,
     nReplyToRecapFeatures.HAS_MENT ON,
     nReplyToRecapFeatures.HAS_HASHTAG,
     nReplyToRecapFeatures. S_AUTHOR_NSFW,
     nReplyToRecapFeatures. S_AUTHOR_SPAM,
     nReplyToRecapFeatures. S_AUTHOR_BOT,
     nReplyToRecapFeatures.FROM_MUTUAL_FOLLOW,
     nReplyToRecapFeatures.USER_REP,
     nReplyToRecapFeatures.FROM_VER F ED_ACCOUNT,
     nReplyToRecapFeatures.HAS_ MAGE,
     nReplyToRecapFeatures.HAS_NEWS,
     nReplyToRecapFeatures.HAS_V DEO,
     nReplyToRecapFeatures.HAS_V S BLE_L NK,
     nReplyToRecapFeatures. S_OFFENS VE,
     nReplyToRecapFeatures. S_REPLY,
     nReplyToRecapFeatures.B D RECT ONAL_REPLY_COUNT,
     nReplyToRecapFeatures.UN D RECT ONAL_REPLY_COUNT,
     nReplyToRecapFeatures.B D RECT ONAL_RETWEET_COUNT,
     nReplyToRecapFeatures.UN D RECT ONAL_RETWEET_COUNT,
     nReplyToRecapFeatures.B D RECT ONAL_FAV_COUNT,
     nReplyToRecapFeatures.UN D RECT ONAL_FAV_COUNT,
     nReplyToRecapFeatures.CONVERSAT ONAL_COUNT,
     nReplyToRecapFeatures.REPLY_COUNT,
     nReplyToRecapFeatures.RETWEET_COUNT,
     nReplyToRecapFeatures.FAV_COUNT,
     nReplyToRecapFeatures.TEXT_SCORE,
     nReplyToRecapFeatures.FAV_COUNT_V2,
     nReplyToRecapFeatures.RETWEET_COUNT_V2,
     nReplyToRecapFeatures.REPLY_COUNT_V2)

  overr de val commonFeatures: Set[Feature[_]] = Set.empty

  overr de def setFeatures(
    ebFeatures: Opt on[sc.Thr ftT etFeatures],
    r chDataRecord: R chDataRecord
  ): Un  = {
     f (ebFeatures.nonEmpty) {
      val features = ebFeatures.get

      r chDataRecord.setFeatureValueFromOpt on(
         nReplyToT etT  l nesSharedFeatures.WE GHTED_FAV_COUNT,
        features.  ghtedFavor eCount.map(_.toDouble)
      )

      r chDataRecord.setFeatureValueFromOpt on(
         nReplyToT etT  l nesSharedFeatures.WE GHTED_RETWEET_COUNT,
        features.  ghtedRet etCount.map(_.toDouble)
      )

      r chDataRecord.setFeatureValueFromOpt on(
         nReplyToT etT  l nesSharedFeatures.WE GHTED_REPLY_COUNT,
        features.  ghtedReplyCount.map(_.toDouble)
      )

      r chDataRecord.setFeatureValueFromOpt on(
         nReplyToT etT  l nesSharedFeatures.WE GHTED_QUOTE_COUNT,
        features.  ghtedQuoteCount.map(_.toDouble)
      )

      r chDataRecord.setFeatureValueFromOpt on(
         nReplyToT etT  l nesSharedFeatures.DECAYED_FAVOR TE_COUNT,
        features.decayedFavor eCount.map(_.toDouble)
      )

      r chDataRecord.setFeatureValueFromOpt on(
         nReplyToT etT  l nesSharedFeatures.DECAYED_RETWEET_COUNT,
        features.decayedRet etCount.map(_.toDouble)
      )

      r chDataRecord.setFeatureValueFromOpt on(
         nReplyToT etT  l nesSharedFeatures.DECAYED_REPLY_COUNT,
        features.decayedReplyCount.map(_.toDouble)
      )

      r chDataRecord.setFeatureValueFromOpt on(
         nReplyToT etT  l nesSharedFeatures.DECAYED_QUOTE_COUNT,
        features.decayedQuoteCount.map(_.toDouble)
      )

      r chDataRecord.setFeatureValueFromOpt on(
         nReplyToT etT  l nesSharedFeatures.QUOTE_COUNT,
        features.quoteCount.map(_.toDouble)
      )

      r chDataRecord.setFeatureValueFromOpt on(
         nReplyToT etT  l nesSharedFeatures.HAS_QUOTE,
        features.hasQuote
      )

       f (features.earlyb rdScore > 0)
        r chDataRecord.setFeatureValue[JDouble](
           nReplyToT etT  l nesSharedFeatures.EARLYB RD_SCORE,
          features.earlyb rdScore
        )

      r chDataRecord.setFeatureValue[JDouble](
         nReplyToRecapFeatures.PREV_USER_TWEET_ENGAGEMENT,
        features.prevUserT etEngage nt.toDouble
      )

      r chDataRecord
        .setFeatureValue[JBoolean]( nReplyToRecapFeatures. S_SENS T VE, features. sSens  veContent)
      r chDataRecord
        .setFeatureValue[JBoolean]( nReplyToRecapFeatures. S_AUTHOR_NEW, features. sAuthorNew)
      r chDataRecord.setFeatureValue[JDouble](
         nReplyToRecapFeatures.NUM_MENT ONS,
        features.num nt ons.toDouble)
      r chDataRecord
        .setFeatureValue[JBoolean]( nReplyToRecapFeatures.HAS_MENT ON, (features.num nt ons > 0))
      r chDataRecord
        .setFeatureValue[JBoolean]( nReplyToRecapFeatures.HAS_HASHTAG, (features.numHashtags > 0))
      r chDataRecord
        .setFeatureValue[JBoolean]( nReplyToRecapFeatures. S_AUTHOR_NSFW, features. sAuthorNSFW)
      r chDataRecord
        .setFeatureValue[JBoolean]( nReplyToRecapFeatures. S_AUTHOR_SPAM, features. sAuthorSpam)
      r chDataRecord
        .setFeatureValue[JBoolean]( nReplyToRecapFeatures. S_AUTHOR_BOT, features. sAuthorBot)
      r chDataRecord.setFeatureValue[JBoolean](
         nReplyToRecapFeatures.FROM_MUTUAL_FOLLOW,
        features.fromMutualFollow)
      r chDataRecord.setFeatureValue[JDouble]( nReplyToRecapFeatures.USER_REP, features.userRep)
      r chDataRecord.setFeatureValue[JBoolean](
         nReplyToRecapFeatures.FROM_VER F ED_ACCOUNT,
        features.fromVer f edAccount)
      r chDataRecord.setFeatureValue[JBoolean]( nReplyToRecapFeatures.HAS_ MAGE, features.has mage)
      r chDataRecord.setFeatureValue[JBoolean]( nReplyToRecapFeatures.HAS_NEWS, features.hasNews)
      r chDataRecord.setFeatureValue[JBoolean]( nReplyToRecapFeatures.HAS_V DEO, features.hasV deo)
      r chDataRecord
        .setFeatureValue[JBoolean]( nReplyToRecapFeatures.HAS_V S BLE_L NK, features.hasV s bleL nk)
      r chDataRecord
        .setFeatureValue[JBoolean]( nReplyToRecapFeatures. S_OFFENS VE, features. sOffens ve)
      r chDataRecord.setFeatureValue[JBoolean]( nReplyToRecapFeatures. S_REPLY, features. sReply)
      r chDataRecord.setFeatureValue[JDouble](
         nReplyToRecapFeatures.B D RECT ONAL_REPLY_COUNT,
        features.b d rect onalReplyCount)
      r chDataRecord.setFeatureValue[JDouble](
         nReplyToRecapFeatures.UN D RECT ONAL_REPLY_COUNT,
        features.un d rect onalReplyCount)
      r chDataRecord.setFeatureValue[JDouble](
         nReplyToRecapFeatures.B D RECT ONAL_RETWEET_COUNT,
        features.b d rect onalRet etCount)
      r chDataRecord.setFeatureValue[JDouble](
         nReplyToRecapFeatures.UN D RECT ONAL_RETWEET_COUNT,
        features.un d rect onalRet etCount)
      r chDataRecord.setFeatureValue[JDouble](
         nReplyToRecapFeatures.B D RECT ONAL_FAV_COUNT,
        features.b d rect onalFavCount)
      r chDataRecord.setFeatureValue[JDouble](
         nReplyToRecapFeatures.UN D RECT ONAL_FAV_COUNT,
        features.un d rect onalFavCount)
      r chDataRecord.setFeatureValue[JDouble](
         nReplyToRecapFeatures.CONVERSAT ONAL_COUNT,
        features.conversat onCount)
      r chDataRecord
        .setFeatureValue[JDouble]( nReplyToRecapFeatures.REPLY_COUNT, features.replyCount.toDouble)
      r chDataRecord.setFeatureValue[JDouble](
         nReplyToRecapFeatures.RETWEET_COUNT,
        features.ret etCount.toDouble)
      r chDataRecord
        .setFeatureValue[JDouble]( nReplyToRecapFeatures.FAV_COUNT, features.favCount.toDouble)
      r chDataRecord.setFeatureValue[JDouble]( nReplyToRecapFeatures.TEXT_SCORE, features.textScore)
      r chDataRecord.setFeatureValueFromOpt on(
         nReplyToRecapFeatures.FAV_COUNT_V2,
        features.favCountV2.map(_.toDouble))
      r chDataRecord.setFeatureValueFromOpt on(
         nReplyToRecapFeatures.RETWEET_COUNT_V2,
        features.ret etCountV2.map(_.toDouble)
      )
      r chDataRecord.setFeatureValueFromOpt on(
         nReplyToRecapFeatures.REPLY_COUNT_V2,
        features.replyCountV2.map(_.toDouble))
    }
  }
}
