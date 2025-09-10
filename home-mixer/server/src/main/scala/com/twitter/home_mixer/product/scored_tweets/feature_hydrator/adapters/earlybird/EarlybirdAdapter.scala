package com.twitter.home_mixer.product.scored_tweets.feature_hydrator.adapters.earlybird

import com.twitter.ml.api.Feature
import com.twitter.ml.api.FeatureContext
import com.twitter.ml.api.RichDataRecord
import com.twitter.ml.api.util.DataRecordConverters._
import com.twitter.timelines.prediction.common.adapters.TimelinesMutatingAdapterBase
import com.twitter.search.common.features.{thriftscala => sc}
import com.twitter.timelines.prediction.features.common.TimelinesSharedFeatures
import com.twitter.timelines.prediction.features.recap.RecapFeatures
import com.twitter.timelines.util.UrlExtractorUtil
import java.lang.{Boolean => JBoolean}
import java.lang.{Double => JDouble}
import java.util.{Map => JMap}
import scala.collection.JavaConverters._

object EarlybirdAdapter extends TimelinesMutatingAdapterBase[Option[sc.ThriftTweetFeatures]] {

  override val getFeatureContext: FeatureContext = new FeatureContext(
    RecapFeatures.BIDIRECTIONAL_FAV_COUNT,
    RecapFeatures.BIDIRECTIONAL_REPLY_COUNT,
    RecapFeatures.BIDIRECTIONAL_RETWEET_COUNT,
    RecapFeatures.BLENDER_SCORE,
    RecapFeatures.CONTAINS_MEDIA,
    RecapFeatures.CONVERSATIONAL_COUNT,
    RecapFeatures.EMBEDS_IMPRESSION_COUNT,
    RecapFeatures.EMBEDS_URL_COUNT,
    RecapFeatures.FAV_COUNT,
    RecapFeatures.FAV_COUNT_V2,
    RecapFeatures.FROM_INACTIVE_USER,
    RecapFeatures.FROM_MUTUAL_FOLLOW,
    RecapFeatures.FROM_VERIFIED_ACCOUNT,
    RecapFeatures.HAS_CARD,
    RecapFeatures.HAS_CONSUMER_VIDEO,
    RecapFeatures.HAS_HASHTAG,
//    RecapFeatures.HAS_IMAGE,
    RecapFeatures.HAS_LINK,
    RecapFeatures.HAS_MENTION,
    RecapFeatures.HAS_MULTIPLE_HASHTAGS_OR_TRENDS,
    RecapFeatures.HAS_MULTIPLE_MEDIA,
    RecapFeatures.HAS_NATIVE_IMAGE,
    RecapFeatures.HAS_NATIVE_VIDEO,
    RecapFeatures.HAS_NEWS,
    RecapFeatures.HAS_PERISCOPE,
    RecapFeatures.HAS_PRO_VIDEO,
    RecapFeatures.HAS_TREND,
//    RecapFeatures.HAS_VIDEO,
    RecapFeatures.HAS_VINE,
    RecapFeatures.HAS_VISIBLE_LINK,
    RecapFeatures.IS_AUTHOR_BOT,
    RecapFeatures.IS_AUTHOR_NEW,
    RecapFeatures.IS_AUTHOR_NSFW,
    RecapFeatures.IS_AUTHOR_PROFILE_EGG,
    RecapFeatures.IS_AUTHOR_SPAM,
    RecapFeatures.IS_BUSINESS_SCORE,
    RecapFeatures.IS_OFFENSIVE,
    RecapFeatures.IS_REPLY,
    RecapFeatures.IS_RETWEET,
    RecapFeatures.IS_RETWEETER_BOT,
    RecapFeatures.IS_RETWEETER_NEW,
    RecapFeatures.IS_RETWEETER_NSFW,
    RecapFeatures.IS_RETWEETER_PROFILE_EGG,
    RecapFeatures.IS_RETWEETER_SPAM,
    RecapFeatures.IS_RETWEET_OF_REPLY,
    RecapFeatures.IS_SENSITIVE,
    RecapFeatures.LANGUAGE,
    RecapFeatures.LINK_COUNT,
    RecapFeatures.LINK_LANGUAGE,
    RecapFeatures.MATCH_SEARCHER_LANGS,
    RecapFeatures.MATCH_SEARCHER_MAIN_LANG,
    RecapFeatures.MATCH_UI_LANG,
    RecapFeatures.MENTIONED_SCREEN_NAMES,
    RecapFeatures.MENTION_SEARCHER,
    RecapFeatures.NUM_HASHTAGS,
    RecapFeatures.NUM_MENTIONS,
    RecapFeatures.PREV_USER_TWEET_ENGAGEMENT,
    RecapFeatures.PROBABLY_FROM_FOLLOWED_AUTHOR,
    RecapFeatures.REPLY_COUNT,
    RecapFeatures.REPLY_COUNT_V2,
    RecapFeatures.REPLY_OTHER,
    RecapFeatures.REPLY_SEARCHER,
    RecapFeatures.RETWEET_COUNT,
    RecapFeatures.RETWEET_COUNT_V2,
    RecapFeatures.RETWEET_DIRECTED_AT_USER_IN_FIRST_DEGREE,
    RecapFeatures.RETWEET_OF_MUTUAL_FOLLOW,
    RecapFeatures.RETWEET_OTHER,
    RecapFeatures.RETWEET_SEARCHER,
    RecapFeatures.SIGNATURE,
    RecapFeatures.SOURCE_AUTHOR_REP,
    RecapFeatures.TEXT_SCORE,
    RecapFeatures.TWEET_COUNT_FROM_USER_IN_SNAPSHOT,
    RecapFeatures.UNIDIRECTIONAL_FAV_COUNT,
    RecapFeatures.UNIDIRECTIONAL_REPLY_COUNT,
    RecapFeatures.UNIDIRECTIONAL_RETWEET_COUNT,
    RecapFeatures.URL_DOMAINS,
    RecapFeatures.USER_REP,
    RecapFeatures.VIDEO_VIEW_COUNT,
    // shared features
    TimelinesSharedFeatures.WEIGHTED_FAV_COUNT,
    TimelinesSharedFeatures.WEIGHTED_RETWEET_COUNT,
    TimelinesSharedFeatures.WEIGHTED_REPLY_COUNT,
    TimelinesSharedFeatures.WEIGHTED_QUOTE_COUNT,
    TimelinesSharedFeatures.EMBEDS_IMPRESSION_COUNT_V2,
    TimelinesSharedFeatures.EMBEDS_URL_COUNT_V2,
    TimelinesSharedFeatures.DECAYED_FAVORITE_COUNT,
    TimelinesSharedFeatures.DECAYED_RETWEET_COUNT,
    TimelinesSharedFeatures.DECAYED_REPLY_COUNT,
    TimelinesSharedFeatures.DECAYED_QUOTE_COUNT,
    TimelinesSharedFeatures.FAKE_FAVORITE_COUNT,
    TimelinesSharedFeatures.FAKE_RETWEET_COUNT,
    TimelinesSharedFeatures.FAKE_REPLY_COUNT,
    TimelinesSharedFeatures.FAKE_QUOTE_COUNT,
    TimelinesSharedFeatures.QUOTE_COUNT,
    TimelinesSharedFeatures.EARLYBIRD_SCORE,
    // Safety features
    TimelinesSharedFeatures.LABEL_ABUSIVE_FLAG,
    TimelinesSharedFeatures.LABEL_ABUSIVE_HI_RCL_FLAG,
    TimelinesSharedFeatures.LABEL_DUP_CONTENT_FLAG,
    TimelinesSharedFeatures.LABEL_NSFW_HI_PRC_FLAG,
    TimelinesSharedFeatures.LABEL_NSFW_HI_RCL_FLAG,
    TimelinesSharedFeatures.LABEL_SPAM_FLAG,
    TimelinesSharedFeatures.LABEL_SPAM_HI_RCL_FLAG,
    // periscope features
    TimelinesSharedFeatures.PERISCOPE_EXISTS,
    TimelinesSharedFeatures.PERISCOPE_IS_LIVE,
    TimelinesSharedFeatures.PERISCOPE_HAS_BEEN_FEATURED,
    TimelinesSharedFeatures.PERISCOPE_IS_CURRENTLY_FEATURED,
    TimelinesSharedFeatures.PERISCOPE_IS_FROM_QUALITY_SOURCE,
    // VISIBLE_TOKEN_RATIO
    TimelinesSharedFeatures.VISIBLE_TOKEN_RATIO,
    TimelinesSharedFeatures.HAS_QUOTE,
    TimelinesSharedFeatures.IS_COMPOSER_SOURCE_CAMERA,
    // health features
    TimelinesSharedFeatures.PREPORTED_TWEET_SCORE,
    // media
    TimelinesSharedFeatures.CLASSIFICATION_LABELS
  )

  override val commonFeatures: Set[Feature[_]] = Set.empty

  override def setFeatures(
    ebFeatures: Option[sc.ThriftTweetFeatures],
    richDataRecord: RichDataRecord
  ): Unit = {
    if (ebFeatures.nonEmpty) {
      val features = ebFeatures.get
      richDataRecord.setFeatureValue[JDouble](
        RecapFeatures.PREV_USER_TWEET_ENGAGEMENT,
        features.prevUserTweetEngagement.toDouble
      )
      richDataRecord
        .setFeatureValue[JBoolean](RecapFeatures.IS_SENSITIVE, features.isSensitiveContent)
      richDataRecord
        .setFeatureValue[JBoolean](RecapFeatures.HAS_MULTIPLE_MEDIA, features.hasMultipleMedia)
      richDataRecord
        .setFeatureValue[JBoolean](RecapFeatures.IS_AUTHOR_PROFILE_EGG, features.isAuthorProfileEgg)
      richDataRecord.setFeatureValue[JBoolean](RecapFeatures.IS_AUTHOR_NEW, features.isAuthorNew)
      richDataRecord
        .setFeatureValue[JDouble](RecapFeatures.NUM_MENTIONS, features.numMentions.toDouble)
      richDataRecord.setFeatureValue[JBoolean](RecapFeatures.HAS_MENTION, features.numMentions > 0)
      richDataRecord
        .setFeatureValue[JDouble](RecapFeatures.NUM_HASHTAGS, features.numHashtags.toDouble)
      richDataRecord.setFeatureValue[JBoolean](RecapFeatures.HAS_HASHTAG, features.numHashtags > 0)
      richDataRecord
        .setFeatureValue[JDouble](RecapFeatures.LINK_LANGUAGE, features.linkLanguage.toDouble)
      richDataRecord.setFeatureValue[JBoolean](RecapFeatures.IS_AUTHOR_NSFW, features.isAuthorNSFW)
      richDataRecord.setFeatureValue[JBoolean](RecapFeatures.IS_AUTHOR_SPAM, features.isAuthorSpam)
      richDataRecord.setFeatureValue[JBoolean](RecapFeatures.IS_AUTHOR_BOT, features.isAuthorBot)
      richDataRecord.setFeatureValueFromOption(
        RecapFeatures.LANGUAGE,
        features.language.map(_.getValue.toLong))
      richDataRecord.setFeatureValueFromOption(
        RecapFeatures.SIGNATURE,
        features.signature.map(_.toLong))
      richDataRecord
        .setFeatureValue[JBoolean](RecapFeatures.FROM_INACTIVE_USER, features.fromInActiveUser)
      richDataRecord
        .setFeatureValue[JBoolean](
          RecapFeatures.PROBABLY_FROM_FOLLOWED_AUTHOR,
          features.probablyFromFollowedAuthor)
      richDataRecord
        .setFeatureValue[JBoolean](RecapFeatures.FROM_MUTUAL_FOLLOW, features.fromMutualFollow)
      richDataRecord.setFeatureValue[JBoolean](
        RecapFeatures.FROM_VERIFIED_ACCOUNT,
        features.fromVerifiedAccount)
      richDataRecord.setFeatureValue[JDouble](RecapFeatures.USER_REP, features.userRep)
      richDataRecord
        .setFeatureValue[JDouble](RecapFeatures.IS_BUSINESS_SCORE, features.isBusinessScore)
      richDataRecord
        .setFeatureValue[JBoolean](RecapFeatures.HAS_CONSUMER_VIDEO, features.hasConsumerVideo)
      richDataRecord.setFeatureValue[JBoolean](RecapFeatures.HAS_PRO_VIDEO, features.hasProVideo)
      richDataRecord.setFeatureValue[JBoolean](RecapFeatures.HAS_VINE, features.hasVine)
      richDataRecord.setFeatureValue[JBoolean](RecapFeatures.HAS_PERISCOPE, features.hasPeriscope)
      richDataRecord
        .setFeatureValue[JBoolean](RecapFeatures.HAS_NATIVE_VIDEO, features.hasNativeVideo)
      richDataRecord
        .setFeatureValue[JBoolean](RecapFeatures.HAS_NATIVE_IMAGE, features.hasNativeImage)
      richDataRecord.setFeatureValue[JBoolean](RecapFeatures.HAS_CARD, features.hasCard)
//      richDataRecord.setFeatureValue[JBoolean](RecapFeatures.HAS_IMAGE, features.hasImage) //handled via TweetEntityServiceContentFeatureHydrator
      richDataRecord.setFeatureValue[JBoolean](RecapFeatures.HAS_NEWS, features.hasNews)
//      richDataRecord.setFeatureValue[JBoolean](RecapFeatures.HAS_VIDEO, features.hasVideo) //handled via TweetEntityServiceContentFeatureHydrator
      richDataRecord.setFeatureValue[JBoolean](RecapFeatures.CONTAINS_MEDIA, features.containsMedia)
      richDataRecord
        .setFeatureValue[JBoolean](RecapFeatures.RETWEET_SEARCHER, features.retweetSearcher)
      richDataRecord.setFeatureValue[JBoolean](RecapFeatures.REPLY_SEARCHER, features.replySearcher)
      richDataRecord
        .setFeatureValue[JBoolean](RecapFeatures.MENTION_SEARCHER, features.mentionSearcher)
      richDataRecord.setFeatureValue[JBoolean](RecapFeatures.REPLY_OTHER, features.replyOther)
      richDataRecord.setFeatureValue[JBoolean](RecapFeatures.RETWEET_OTHER, features.retweetOther)
      richDataRecord.setFeatureValue[JBoolean](RecapFeatures.IS_REPLY, features.isReply)
      richDataRecord.setFeatureValue[JBoolean](RecapFeatures.IS_RETWEET, features.isRetweet)
      richDataRecord.setFeatureValue[JBoolean](RecapFeatures.IS_OFFENSIVE, features.isOffensive)
      richDataRecord.setFeatureValue[JBoolean](RecapFeatures.MATCH_UI_LANG, features.matchesUILang)
      richDataRecord
        .setFeatureValue[JBoolean](
          RecapFeatures.MATCH_SEARCHER_MAIN_LANG,
          features.matchesSearcherMainLang)
      richDataRecord.setFeatureValue[JBoolean](
        RecapFeatures.MATCH_SEARCHER_LANGS,
        features.matchesSearcherLangs)
      richDataRecord
        .setFeatureValue[JDouble](
          RecapFeatures.BIDIRECTIONAL_FAV_COUNT,
          features.bidirectionalFavCount)
      richDataRecord
        .setFeatureValue[JDouble](
          RecapFeatures.UNIDIRECTIONAL_FAV_COUNT,
          features.unidirectionalFavCount)
      richDataRecord
        .setFeatureValue[JDouble](
          RecapFeatures.BIDIRECTIONAL_REPLY_COUNT,
          features.bidirectionalReplyCount)
      richDataRecord
        .setFeatureValue[JDouble](
          RecapFeatures.UNIDIRECTIONAL_REPLY_COUNT,
          features.unidirectionalReplyCount)
      richDataRecord
        .setFeatureValue[JDouble](
          RecapFeatures.BIDIRECTIONAL_RETWEET_COUNT,
          features.bidirectionalRetweetCount)
      richDataRecord
        .setFeatureValue[JDouble](
          RecapFeatures.UNIDIRECTIONAL_RETWEET_COUNT,
          features.unidirectionalRetweetCount)
      richDataRecord
        .setFeatureValue[JDouble](RecapFeatures.CONVERSATIONAL_COUNT, features.conversationCount)
      richDataRecord.setFeatureValue[JDouble](
        RecapFeatures.TWEET_COUNT_FROM_USER_IN_SNAPSHOT,
        features.tweetCountFromUserInSnapshot
      )
      richDataRecord
        .setFeatureValue[JBoolean](
          RecapFeatures.IS_RETWEETER_PROFILE_EGG,
          features.isRetweeterProfileEgg)
      richDataRecord
        .setFeatureValue[JBoolean](RecapFeatures.IS_RETWEETER_NEW, features.isRetweeterNew)
      richDataRecord
        .setFeatureValue[JBoolean](RecapFeatures.IS_RETWEETER_BOT, features.isRetweeterBot)
      richDataRecord
        .setFeatureValue[JBoolean](RecapFeatures.IS_RETWEETER_NSFW, features.isRetweeterNSFW)
      richDataRecord
        .setFeatureValue[JBoolean](RecapFeatures.IS_RETWEETER_SPAM, features.isRetweeterSpam)
      richDataRecord
        .setFeatureValue[JBoolean](
          RecapFeatures.RETWEET_OF_MUTUAL_FOLLOW,
          features.retweetOfMutualFollow)
      richDataRecord
        .setFeatureValue[JDouble](RecapFeatures.SOURCE_AUTHOR_REP, features.sourceAuthorRep)
      richDataRecord
        .setFeatureValue[JBoolean](RecapFeatures.IS_RETWEET_OF_REPLY, features.isRetweetOfReply)
      richDataRecord.setFeatureValueFromOption(
        RecapFeatures.RETWEET_DIRECTED_AT_USER_IN_FIRST_DEGREE,
        features.retweetDirectedAtUserInFirstDegree
      )
      richDataRecord
        .setFeatureValue[JDouble](
          RecapFeatures.EMBEDS_IMPRESSION_COUNT,
          features.embedsImpressionCount.toDouble)
      richDataRecord
        .setFeatureValue[JDouble](RecapFeatures.EMBEDS_URL_COUNT, features.embedsUrlCount.toDouble)
      richDataRecord
        .setFeatureValue[JDouble](RecapFeatures.VIDEO_VIEW_COUNT, features.videoViewCount.toDouble)
      richDataRecord
        .setFeatureValue[JDouble](RecapFeatures.REPLY_COUNT, features.replyCount.toDouble)
      richDataRecord
        .setFeatureValue[JDouble](RecapFeatures.RETWEET_COUNT, features.retweetCount.toDouble)
      richDataRecord.setFeatureValue[JDouble](RecapFeatures.FAV_COUNT, features.favCount.toDouble)
      richDataRecord.setFeatureValue[JDouble](RecapFeatures.BLENDER_SCORE, features.blenderScore)
      richDataRecord.setFeatureValue[JDouble](RecapFeatures.TEXT_SCORE, features.textScore)
      richDataRecord
        .setFeatureValue[JBoolean](RecapFeatures.HAS_VISIBLE_LINK, features.hasVisibleLink)
      richDataRecord.setFeatureValue[JBoolean](RecapFeatures.HAS_LINK, features.hasLink)
      richDataRecord.setFeatureValue[JBoolean](RecapFeatures.HAS_TREND, features.hasTrend)
      richDataRecord.setFeatureValue[JBoolean](
        RecapFeatures.HAS_MULTIPLE_HASHTAGS_OR_TRENDS,
        features.hasMultipleHashtagsOrTrends
      )
      richDataRecord.setFeatureValueFromOption(
        RecapFeatures.FAV_COUNT_V2,
        features.favCountV2.map(_.toDouble))
      richDataRecord.setFeatureValueFromOption(
        RecapFeatures.RETWEET_COUNT_V2,
        features.retweetCountV2.map(_.toDouble)
      )
      richDataRecord.setFeatureValueFromOption(
        RecapFeatures.REPLY_COUNT_V2,
        features.replyCountV2.map(_.toDouble))
      richDataRecord.setFeatureValueFromOption(
        RecapFeatures.MENTIONED_SCREEN_NAMES,
        features.mentionsList.map(_.toSet.asJava)
      )
      val urls = features.urlsList.getOrElse(Seq.empty)
      richDataRecord.setFeatureValue(
        RecapFeatures.URL_DOMAINS,
        urls.toSet.flatMap(UrlExtractorUtil.extractDomain).asJava)
      richDataRecord.setFeatureValue[JDouble](RecapFeatures.LINK_COUNT, urls.size.toDouble)
      // shared features
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.WEIGHTED_FAV_COUNT,
        features.weightedFavoriteCount.map(_.toDouble)
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.WEIGHTED_RETWEET_COUNT,
        features.weightedRetweetCount.map(_.toDouble)
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.WEIGHTED_REPLY_COUNT,
        features.weightedReplyCount.map(_.toDouble)
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.WEIGHTED_QUOTE_COUNT,
        features.weightedQuoteCount.map(_.toDouble)
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.EMBEDS_IMPRESSION_COUNT_V2,
        features.embedsImpressionCountV2.map(_.toDouble)
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.EMBEDS_URL_COUNT_V2,
        features.embedsUrlCountV2.map(_.toDouble)
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.DECAYED_FAVORITE_COUNT,
        features.decayedFavoriteCount.map(_.toDouble)
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.DECAYED_RETWEET_COUNT,
        features.decayedRetweetCount.map(_.toDouble)
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.DECAYED_REPLY_COUNT,
        features.decayedReplyCount.map(_.toDouble)
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.DECAYED_QUOTE_COUNT,
        features.decayedQuoteCount.map(_.toDouble)
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.FAKE_FAVORITE_COUNT,
        features.fakeFavoriteCount.map(_.toDouble)
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.FAKE_RETWEET_COUNT,
        features.fakeRetweetCount.map(_.toDouble)
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.FAKE_REPLY_COUNT,
        features.fakeReplyCount.map(_.toDouble)
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.FAKE_QUOTE_COUNT,
        features.fakeQuoteCount.map(_.toDouble)
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.QUOTE_COUNT,
        features.quoteCount.map(_.toDouble)
      )
      richDataRecord.setFeatureValue[JDouble](
        TimelinesSharedFeatures.EARLYBIRD_SCORE,
        features.earlybirdScore
      )
      // safety features
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.LABEL_ABUSIVE_FLAG,
        features.labelAbusiveFlag
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.LABEL_ABUSIVE_HI_RCL_FLAG,
        features.labelAbusiveHiRclFlag
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.LABEL_DUP_CONTENT_FLAG,
        features.labelDupContentFlag
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.LABEL_NSFW_HI_PRC_FLAG,
        features.labelNsfwHiPrcFlag
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.LABEL_NSFW_HI_RCL_FLAG,
        features.labelNsfwHiRclFlag
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.LABEL_SPAM_FLAG,
        features.labelSpamFlag
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.LABEL_SPAM_HI_RCL_FLAG,
        features.labelSpamHiRclFlag
      )
      // periscope features
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.PERISCOPE_EXISTS,
        features.periscopeExists
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.PERISCOPE_IS_LIVE,
        features.periscopeIsLive
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.PERISCOPE_HAS_BEEN_FEATURED,
        features.periscopeHasBeenFeatured
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.PERISCOPE_IS_CURRENTLY_FEATURED,
        features.periscopeIsCurrentlyFeatured
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.PERISCOPE_IS_FROM_QUALITY_SOURCE,
        features.periscopeIsFromQualitySource
      )
      // misc features
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.VISIBLE_TOKEN_RATIO,
        features.visibleTokenRatio.map(_.toDouble)
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.HAS_QUOTE,
        features.hasQuote
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.IS_COMPOSER_SOURCE_CAMERA,
        features.isComposerSourceCamera
      )
      // health scores
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.PREPORTED_TWEET_SCORE,
        features.pReportedTweetScore
      )
      // media
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.CLASSIFICATION_LABELS,
        features.mediaClassificationInfo.map(_.toMap.asJava.asInstanceOf[JMap[String, JDouble]])
      )
    }
  }
}
