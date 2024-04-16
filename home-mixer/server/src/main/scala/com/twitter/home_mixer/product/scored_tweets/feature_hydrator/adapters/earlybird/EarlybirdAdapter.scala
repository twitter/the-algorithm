package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.adapters.earlyb rd

 mport com.tw ter.ml.ap .Feature
 mport com.tw ter.ml.ap .FeatureContext
 mport com.tw ter.ml.ap .R chDataRecord
 mport com.tw ter.ml.ap .ut l.DataRecordConverters._
 mport com.tw ter.t  l nes.pred ct on.common.adapters.T  l nesMutat ngAdapterBase
 mport com.tw ter.search.common.features.{thr ftscala => sc}
 mport com.tw ter.t  l nes.pred ct on.features.common.T  l nesSharedFeatures
 mport com.tw ter.t  l nes.pred ct on.features.recap.RecapFeatures
 mport com.tw ter.t  l nes.ut l.UrlExtractorUt l
 mport java.lang.{Boolean => JBoolean}
 mport java.lang.{Double => JDouble}
 mport java.ut l.{Map => JMap}
 mport scala.collect on.JavaConverters._

object Earlyb rdAdapter extends T  l nesMutat ngAdapterBase[Opt on[sc.Thr ftT etFeatures]] {

  overr de val getFeatureContext: FeatureContext = new FeatureContext(
    RecapFeatures.B D RECT ONAL_FAV_COUNT,
    RecapFeatures.B D RECT ONAL_REPLY_COUNT,
    RecapFeatures.B D RECT ONAL_RETWEET_COUNT,
    RecapFeatures.BLENDER_SCORE,
    RecapFeatures.CONTA NS_MED A,
    RecapFeatures.CONVERSAT ONAL_COUNT,
    RecapFeatures.EMBEDS_ MPRESS ON_COUNT,
    RecapFeatures.EMBEDS_URL_COUNT,
    RecapFeatures.FAV_COUNT,
    RecapFeatures.FAV_COUNT_V2,
    RecapFeatures.FROM_ NACT VE_USER,
    RecapFeatures.FROM_MUTUAL_FOLLOW,
    RecapFeatures.FROM_VER F ED_ACCOUNT,
    RecapFeatures.HAS_CARD,
    RecapFeatures.HAS_CONSUMER_V DEO,
    RecapFeatures.HAS_HASHTAG,
    RecapFeatures.HAS_ MAGE,
    RecapFeatures.HAS_L NK,
    RecapFeatures.HAS_MENT ON,
    RecapFeatures.HAS_MULT PLE_HASHTAGS_OR_TRENDS,
    RecapFeatures.HAS_MULT PLE_MED A,
    RecapFeatures.HAS_NAT VE_ MAGE,
    RecapFeatures.HAS_NAT VE_V DEO,
    RecapFeatures.HAS_NEWS,
    RecapFeatures.HAS_PER SCOPE,
    RecapFeatures.HAS_PRO_V DEO,
    RecapFeatures.HAS_TREND,
    RecapFeatures.HAS_V DEO,
    RecapFeatures.HAS_V NE,
    RecapFeatures.HAS_V S BLE_L NK,
    RecapFeatures. S_AUTHOR_BOT,
    RecapFeatures. S_AUTHOR_NEW,
    RecapFeatures. S_AUTHOR_NSFW,
    RecapFeatures. S_AUTHOR_PROF LE_EGG,
    RecapFeatures. S_AUTHOR_SPAM,
    RecapFeatures. S_BUS NESS_SCORE,
    RecapFeatures. S_OFFENS VE,
    RecapFeatures. S_REPLY,
    RecapFeatures. S_RETWEET,
    RecapFeatures. S_RETWEETER_BOT,
    RecapFeatures. S_RETWEETER_NEW,
    RecapFeatures. S_RETWEETER_NSFW,
    RecapFeatures. S_RETWEETER_PROF LE_EGG,
    RecapFeatures. S_RETWEETER_SPAM,
    RecapFeatures. S_RETWEET_OF_REPLY,
    RecapFeatures. S_SENS T VE,
    RecapFeatures.LANGUAGE,
    RecapFeatures.L NK_COUNT,
    RecapFeatures.L NK_LANGUAGE,
    RecapFeatures.MATCH_SEARCHER_LANGS,
    RecapFeatures.MATCH_SEARCHER_MA N_LANG,
    RecapFeatures.MATCH_U _LANG,
    RecapFeatures.MENT ONED_SCREEN_NAMES,
    RecapFeatures.MENT ON_SEARCHER,
    RecapFeatures.NUM_HASHTAGS,
    RecapFeatures.NUM_MENT ONS,
    RecapFeatures.PREV_USER_TWEET_ENGAGEMENT,
    RecapFeatures.PROBABLY_FROM_FOLLOWED_AUTHOR,
    RecapFeatures.REPLY_COUNT,
    RecapFeatures.REPLY_COUNT_V2,
    RecapFeatures.REPLY_OTHER,
    RecapFeatures.REPLY_SEARCHER,
    RecapFeatures.RETWEET_COUNT,
    RecapFeatures.RETWEET_COUNT_V2,
    RecapFeatures.RETWEET_D RECTED_AT_USER_ N_F RST_DEGREE,
    RecapFeatures.RETWEET_OF_MUTUAL_FOLLOW,
    RecapFeatures.RETWEET_OTHER,
    RecapFeatures.RETWEET_SEARCHER,
    RecapFeatures.S GNATURE,
    RecapFeatures.SOURCE_AUTHOR_REP,
    RecapFeatures.TEXT_SCORE,
    RecapFeatures.TWEET_COUNT_FROM_USER_ N_SNAPSHOT,
    RecapFeatures.UN D RECT ONAL_FAV_COUNT,
    RecapFeatures.UN D RECT ONAL_REPLY_COUNT,
    RecapFeatures.UN D RECT ONAL_RETWEET_COUNT,
    RecapFeatures.URL_DOMA NS,
    RecapFeatures.USER_REP,
    RecapFeatures.V DEO_V EW_COUNT,
    // shared features
    T  l nesSharedFeatures.WE GHTED_FAV_COUNT,
    T  l nesSharedFeatures.WE GHTED_RETWEET_COUNT,
    T  l nesSharedFeatures.WE GHTED_REPLY_COUNT,
    T  l nesSharedFeatures.WE GHTED_QUOTE_COUNT,
    T  l nesSharedFeatures.EMBEDS_ MPRESS ON_COUNT_V2,
    T  l nesSharedFeatures.EMBEDS_URL_COUNT_V2,
    T  l nesSharedFeatures.DECAYED_FAVOR TE_COUNT,
    T  l nesSharedFeatures.DECAYED_RETWEET_COUNT,
    T  l nesSharedFeatures.DECAYED_REPLY_COUNT,
    T  l nesSharedFeatures.DECAYED_QUOTE_COUNT,
    T  l nesSharedFeatures.FAKE_FAVOR TE_COUNT,
    T  l nesSharedFeatures.FAKE_RETWEET_COUNT,
    T  l nesSharedFeatures.FAKE_REPLY_COUNT,
    T  l nesSharedFeatures.FAKE_QUOTE_COUNT,
    T  l nesSharedFeatures.QUOTE_COUNT,
    T  l nesSharedFeatures.EARLYB RD_SCORE,
    // Safety features
    T  l nesSharedFeatures.LABEL_ABUS VE_FLAG,
    T  l nesSharedFeatures.LABEL_ABUS VE_H _RCL_FLAG,
    T  l nesSharedFeatures.LABEL_DUP_CONTENT_FLAG,
    T  l nesSharedFeatures.LABEL_NSFW_H _PRC_FLAG,
    T  l nesSharedFeatures.LABEL_NSFW_H _RCL_FLAG,
    T  l nesSharedFeatures.LABEL_SPAM_FLAG,
    T  l nesSharedFeatures.LABEL_SPAM_H _RCL_FLAG,
    // per scope features
    T  l nesSharedFeatures.PER SCOPE_EX STS,
    T  l nesSharedFeatures.PER SCOPE_ S_L VE,
    T  l nesSharedFeatures.PER SCOPE_HAS_BEEN_FEATURED,
    T  l nesSharedFeatures.PER SCOPE_ S_CURRENTLY_FEATURED,
    T  l nesSharedFeatures.PER SCOPE_ S_FROM_QUAL TY_SOURCE,
    // V S BLE_TOKEN_RAT O
    T  l nesSharedFeatures.V S BLE_TOKEN_RAT O,
    T  l nesSharedFeatures.HAS_QUOTE,
    T  l nesSharedFeatures. S_COMPOSER_SOURCE_CAMERA,
    //  alth features
    T  l nesSharedFeatures.PREPORTED_TWEET_SCORE,
    //  d a
    T  l nesSharedFeatures.CLASS F CAT ON_LABELS
  )

  overr de val commonFeatures: Set[Feature[_]] = Set.empty

  overr de def setFeatures(
    ebFeatures: Opt on[sc.Thr ftT etFeatures],
    r chDataRecord: R chDataRecord
  ): Un  = {
     f (ebFeatures.nonEmpty) {
      val features = ebFeatures.get
      r chDataRecord.setFeatureValue[JDouble](
        RecapFeatures.PREV_USER_TWEET_ENGAGEMENT,
        features.prevUserT etEngage nt.toDouble
      )
      r chDataRecord
        .setFeatureValue[JBoolean](RecapFeatures. S_SENS T VE, features. sSens  veContent)
      r chDataRecord
        .setFeatureValue[JBoolean](RecapFeatures.HAS_MULT PLE_MED A, features.hasMult ple d a)
      r chDataRecord
        .setFeatureValue[JBoolean](RecapFeatures. S_AUTHOR_PROF LE_EGG, features. sAuthorProf leEgg)
      r chDataRecord.setFeatureValue[JBoolean](RecapFeatures. S_AUTHOR_NEW, features. sAuthorNew)
      r chDataRecord
        .setFeatureValue[JDouble](RecapFeatures.NUM_MENT ONS, features.num nt ons.toDouble)
      r chDataRecord.setFeatureValue[JBoolean](RecapFeatures.HAS_MENT ON, features.num nt ons > 0)
      r chDataRecord
        .setFeatureValue[JDouble](RecapFeatures.NUM_HASHTAGS, features.numHashtags.toDouble)
      r chDataRecord.setFeatureValue[JBoolean](RecapFeatures.HAS_HASHTAG, features.numHashtags > 0)
      r chDataRecord
        .setFeatureValue[JDouble](RecapFeatures.L NK_LANGUAGE, features.l nkLanguage.toDouble)
      r chDataRecord.setFeatureValue[JBoolean](RecapFeatures. S_AUTHOR_NSFW, features. sAuthorNSFW)
      r chDataRecord.setFeatureValue[JBoolean](RecapFeatures. S_AUTHOR_SPAM, features. sAuthorSpam)
      r chDataRecord.setFeatureValue[JBoolean](RecapFeatures. S_AUTHOR_BOT, features. sAuthorBot)
      r chDataRecord.setFeatureValueFromOpt on(
        RecapFeatures.LANGUAGE,
        features.language.map(_.getValue.toLong))
      r chDataRecord.setFeatureValueFromOpt on(
        RecapFeatures.S GNATURE,
        features.s gnature.map(_.toLong))
      r chDataRecord
        .setFeatureValue[JBoolean](RecapFeatures.FROM_ NACT VE_USER, features.from nAct veUser)
      r chDataRecord
        .setFeatureValue[JBoolean](
          RecapFeatures.PROBABLY_FROM_FOLLOWED_AUTHOR,
          features.probablyFromFollo dAuthor)
      r chDataRecord
        .setFeatureValue[JBoolean](RecapFeatures.FROM_MUTUAL_FOLLOW, features.fromMutualFollow)
      r chDataRecord.setFeatureValue[JBoolean](
        RecapFeatures.FROM_VER F ED_ACCOUNT,
        features.fromVer f edAccount)
      r chDataRecord.setFeatureValue[JDouble](RecapFeatures.USER_REP, features.userRep)
      r chDataRecord
        .setFeatureValue[JDouble](RecapFeatures. S_BUS NESS_SCORE, features. sBus nessScore)
      r chDataRecord
        .setFeatureValue[JBoolean](RecapFeatures.HAS_CONSUMER_V DEO, features.hasConsu rV deo)
      r chDataRecord.setFeatureValue[JBoolean](RecapFeatures.HAS_PRO_V DEO, features.hasProV deo)
      r chDataRecord.setFeatureValue[JBoolean](RecapFeatures.HAS_V NE, features.hasV ne)
      r chDataRecord.setFeatureValue[JBoolean](RecapFeatures.HAS_PER SCOPE, features.hasPer scope)
      r chDataRecord
        .setFeatureValue[JBoolean](RecapFeatures.HAS_NAT VE_V DEO, features.hasNat veV deo)
      r chDataRecord
        .setFeatureValue[JBoolean](RecapFeatures.HAS_NAT VE_ MAGE, features.hasNat ve mage)
      r chDataRecord.setFeatureValue[JBoolean](RecapFeatures.HAS_CARD, features.hasCard)
      r chDataRecord.setFeatureValue[JBoolean](RecapFeatures.HAS_ MAGE, features.has mage)
      r chDataRecord.setFeatureValue[JBoolean](RecapFeatures.HAS_NEWS, features.hasNews)
      r chDataRecord.setFeatureValue[JBoolean](RecapFeatures.HAS_V DEO, features.hasV deo)
      r chDataRecord.setFeatureValue[JBoolean](RecapFeatures.CONTA NS_MED A, features.conta ns d a)
      r chDataRecord
        .setFeatureValue[JBoolean](RecapFeatures.RETWEET_SEARCHER, features.ret etSearc r)
      r chDataRecord.setFeatureValue[JBoolean](RecapFeatures.REPLY_SEARCHER, features.replySearc r)
      r chDataRecord
        .setFeatureValue[JBoolean](RecapFeatures.MENT ON_SEARCHER, features. nt onSearc r)
      r chDataRecord.setFeatureValue[JBoolean](RecapFeatures.REPLY_OTHER, features.replyOt r)
      r chDataRecord.setFeatureValue[JBoolean](RecapFeatures.RETWEET_OTHER, features.ret etOt r)
      r chDataRecord.setFeatureValue[JBoolean](RecapFeatures. S_REPLY, features. sReply)
      r chDataRecord.setFeatureValue[JBoolean](RecapFeatures. S_RETWEET, features. sRet et)
      r chDataRecord.setFeatureValue[JBoolean](RecapFeatures. S_OFFENS VE, features. sOffens ve)
      r chDataRecord.setFeatureValue[JBoolean](RecapFeatures.MATCH_U _LANG, features.matc sU Lang)
      r chDataRecord
        .setFeatureValue[JBoolean](
          RecapFeatures.MATCH_SEARCHER_MA N_LANG,
          features.matc sSearc rMa nLang)
      r chDataRecord.setFeatureValue[JBoolean](
        RecapFeatures.MATCH_SEARCHER_LANGS,
        features.matc sSearc rLangs)
      r chDataRecord
        .setFeatureValue[JDouble](
          RecapFeatures.B D RECT ONAL_FAV_COUNT,
          features.b d rect onalFavCount)
      r chDataRecord
        .setFeatureValue[JDouble](
          RecapFeatures.UN D RECT ONAL_FAV_COUNT,
          features.un d rect onalFavCount)
      r chDataRecord
        .setFeatureValue[JDouble](
          RecapFeatures.B D RECT ONAL_REPLY_COUNT,
          features.b d rect onalReplyCount)
      r chDataRecord
        .setFeatureValue[JDouble](
          RecapFeatures.UN D RECT ONAL_REPLY_COUNT,
          features.un d rect onalReplyCount)
      r chDataRecord
        .setFeatureValue[JDouble](
          RecapFeatures.B D RECT ONAL_RETWEET_COUNT,
          features.b d rect onalRet etCount)
      r chDataRecord
        .setFeatureValue[JDouble](
          RecapFeatures.UN D RECT ONAL_RETWEET_COUNT,
          features.un d rect onalRet etCount)
      r chDataRecord
        .setFeatureValue[JDouble](RecapFeatures.CONVERSAT ONAL_COUNT, features.conversat onCount)
      r chDataRecord.setFeatureValue[JDouble](
        RecapFeatures.TWEET_COUNT_FROM_USER_ N_SNAPSHOT,
        features.t etCountFromUser nSnapshot
      )
      r chDataRecord
        .setFeatureValue[JBoolean](
          RecapFeatures. S_RETWEETER_PROF LE_EGG,
          features. sRet eterProf leEgg)
      r chDataRecord
        .setFeatureValue[JBoolean](RecapFeatures. S_RETWEETER_NEW, features. sRet eterNew)
      r chDataRecord
        .setFeatureValue[JBoolean](RecapFeatures. S_RETWEETER_BOT, features. sRet eterBot)
      r chDataRecord
        .setFeatureValue[JBoolean](RecapFeatures. S_RETWEETER_NSFW, features. sRet eterNSFW)
      r chDataRecord
        .setFeatureValue[JBoolean](RecapFeatures. S_RETWEETER_SPAM, features. sRet eterSpam)
      r chDataRecord
        .setFeatureValue[JBoolean](
          RecapFeatures.RETWEET_OF_MUTUAL_FOLLOW,
          features.ret etOfMutualFollow)
      r chDataRecord
        .setFeatureValue[JDouble](RecapFeatures.SOURCE_AUTHOR_REP, features.s ceAuthorRep)
      r chDataRecord
        .setFeatureValue[JBoolean](RecapFeatures. S_RETWEET_OF_REPLY, features. sRet etOfReply)
      r chDataRecord.setFeatureValueFromOpt on(
        RecapFeatures.RETWEET_D RECTED_AT_USER_ N_F RST_DEGREE,
        features.ret etD rectedAtUser nF rstDegree
      )
      r chDataRecord
        .setFeatureValue[JDouble](
          RecapFeatures.EMBEDS_ MPRESS ON_COUNT,
          features.embeds mpress onCount.toDouble)
      r chDataRecord
        .setFeatureValue[JDouble](RecapFeatures.EMBEDS_URL_COUNT, features.embedsUrlCount.toDouble)
      r chDataRecord
        .setFeatureValue[JDouble](RecapFeatures.V DEO_V EW_COUNT, features.v deoV ewCount.toDouble)
      r chDataRecord
        .setFeatureValue[JDouble](RecapFeatures.REPLY_COUNT, features.replyCount.toDouble)
      r chDataRecord
        .setFeatureValue[JDouble](RecapFeatures.RETWEET_COUNT, features.ret etCount.toDouble)
      r chDataRecord.setFeatureValue[JDouble](RecapFeatures.FAV_COUNT, features.favCount.toDouble)
      r chDataRecord.setFeatureValue[JDouble](RecapFeatures.BLENDER_SCORE, features.blenderScore)
      r chDataRecord.setFeatureValue[JDouble](RecapFeatures.TEXT_SCORE, features.textScore)
      r chDataRecord
        .setFeatureValue[JBoolean](RecapFeatures.HAS_V S BLE_L NK, features.hasV s bleL nk)
      r chDataRecord.setFeatureValue[JBoolean](RecapFeatures.HAS_L NK, features.hasL nk)
      r chDataRecord.setFeatureValue[JBoolean](RecapFeatures.HAS_TREND, features.hasTrend)
      r chDataRecord.setFeatureValue[JBoolean](
        RecapFeatures.HAS_MULT PLE_HASHTAGS_OR_TRENDS,
        features.hasMult pleHashtagsOrTrends
      )
      r chDataRecord.setFeatureValueFromOpt on(
        RecapFeatures.FAV_COUNT_V2,
        features.favCountV2.map(_.toDouble))
      r chDataRecord.setFeatureValueFromOpt on(
        RecapFeatures.RETWEET_COUNT_V2,
        features.ret etCountV2.map(_.toDouble)
      )
      r chDataRecord.setFeatureValueFromOpt on(
        RecapFeatures.REPLY_COUNT_V2,
        features.replyCountV2.map(_.toDouble))
      r chDataRecord.setFeatureValueFromOpt on(
        RecapFeatures.MENT ONED_SCREEN_NAMES,
        features. nt onsL st.map(_.toSet.asJava)
      )
      val urls = features.urlsL st.getOrElse(Seq.empty)
      r chDataRecord.setFeatureValue(
        RecapFeatures.URL_DOMA NS,
        urls.toSet.flatMap(UrlExtractorUt l.extractDoma n).asJava)
      r chDataRecord.setFeatureValue[JDouble](RecapFeatures.L NK_COUNT, urls.s ze.toDouble)
      // shared features
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.WE GHTED_FAV_COUNT,
        features.  ghtedFavor eCount.map(_.toDouble)
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.WE GHTED_RETWEET_COUNT,
        features.  ghtedRet etCount.map(_.toDouble)
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.WE GHTED_REPLY_COUNT,
        features.  ghtedReplyCount.map(_.toDouble)
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.WE GHTED_QUOTE_COUNT,
        features.  ghtedQuoteCount.map(_.toDouble)
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.EMBEDS_ MPRESS ON_COUNT_V2,
        features.embeds mpress onCountV2.map(_.toDouble)
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.EMBEDS_URL_COUNT_V2,
        features.embedsUrlCountV2.map(_.toDouble)
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.DECAYED_FAVOR TE_COUNT,
        features.decayedFavor eCount.map(_.toDouble)
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.DECAYED_RETWEET_COUNT,
        features.decayedRet etCount.map(_.toDouble)
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.DECAYED_REPLY_COUNT,
        features.decayedReplyCount.map(_.toDouble)
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.DECAYED_QUOTE_COUNT,
        features.decayedQuoteCount.map(_.toDouble)
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.FAKE_FAVOR TE_COUNT,
        features.fakeFavor eCount.map(_.toDouble)
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.FAKE_RETWEET_COUNT,
        features.fakeRet etCount.map(_.toDouble)
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.FAKE_REPLY_COUNT,
        features.fakeReplyCount.map(_.toDouble)
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.FAKE_QUOTE_COUNT,
        features.fakeQuoteCount.map(_.toDouble)
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.QUOTE_COUNT,
        features.quoteCount.map(_.toDouble)
      )
      r chDataRecord.setFeatureValue[JDouble](
        T  l nesSharedFeatures.EARLYB RD_SCORE,
        features.earlyb rdScore
      )
      // safety features
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.LABEL_ABUS VE_FLAG,
        features.labelAbus veFlag
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.LABEL_ABUS VE_H _RCL_FLAG,
        features.labelAbus veH RclFlag
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.LABEL_DUP_CONTENT_FLAG,
        features.labelDupContentFlag
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.LABEL_NSFW_H _PRC_FLAG,
        features.labelNsfwH PrcFlag
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.LABEL_NSFW_H _RCL_FLAG,
        features.labelNsfwH RclFlag
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.LABEL_SPAM_FLAG,
        features.labelSpamFlag
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.LABEL_SPAM_H _RCL_FLAG,
        features.labelSpamH RclFlag
      )
      // per scope features
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.PER SCOPE_EX STS,
        features.per scopeEx sts
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.PER SCOPE_ S_L VE,
        features.per scope sL ve
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.PER SCOPE_HAS_BEEN_FEATURED,
        features.per scopeHasBeenFeatured
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.PER SCOPE_ S_CURRENTLY_FEATURED,
        features.per scope sCurrentlyFeatured
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.PER SCOPE_ S_FROM_QUAL TY_SOURCE,
        features.per scope sFromQual yS ce
      )
      // m sc features
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.V S BLE_TOKEN_RAT O,
        features.v s bleTokenRat o.map(_.toDouble)
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.HAS_QUOTE,
        features.hasQuote
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures. S_COMPOSER_SOURCE_CAMERA,
        features. sComposerS ceCa ra
      )
      //  alth scores
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.PREPORTED_TWEET_SCORE,
        features.pReportedT etScore
      )
      //  d a
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.CLASS F CAT ON_LABELS,
        features. d aClass f cat on nfo.map(_.toMap.asJava.as nstanceOf[JMap[Str ng, JDouble]])
      )
    }
  }
}
