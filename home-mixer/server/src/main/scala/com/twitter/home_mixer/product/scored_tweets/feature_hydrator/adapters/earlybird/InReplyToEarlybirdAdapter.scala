package com.twitter.home_mixer.product.scored_tweets.feature_hydrator.adapters.earlybird

import com.twitter.ml.api.Feature
import com.twitter.ml.api.FeatureContext
import com.twitter.ml.api.RichDataRecord
import com.twitter.ml.api.util.DataRecordConverters.RichDataRecordWrapper
import com.twitter.search.common.features.{thriftscala => sc}
import com.twitter.timelines.prediction.common.adapters.TimelinesMutatingAdapterBase
import com.twitter.timelines.prediction.features.common.InReplyToTweetTimelinesSharedFeatures
import com.twitter.timelines.prediction.features.recap.InReplyToRecapFeatures
import java.lang.{Boolean => JBoolean}
import java.lang.{Double => JDouble}

object InReplyToEarlybirdAdapter
    extends TimelinesMutatingAdapterBase[Option[sc.ThriftTweetFeatures]] {

  override val getFeatureContext: FeatureContext = new FeatureContext(
    // TextFeatures
    InReplyToTweetTimelinesSharedFeatures.WEIGHTED_FAV_COUNT,
    InReplyToTweetTimelinesSharedFeatures.WEIGHTED_RETWEET_COUNT,
    InReplyToTweetTimelinesSharedFeatures.WEIGHTED_REPLY_COUNT,
    InReplyToTweetTimelinesSharedFeatures.WEIGHTED_QUOTE_COUNT,
    InReplyToTweetTimelinesSharedFeatures.DECAYED_FAVORITE_COUNT,
    InReplyToTweetTimelinesSharedFeatures.DECAYED_RETWEET_COUNT,
    InReplyToTweetTimelinesSharedFeatures.DECAYED_REPLY_COUNT,
    InReplyToTweetTimelinesSharedFeatures.DECAYED_QUOTE_COUNT,
    InReplyToTweetTimelinesSharedFeatures.QUOTE_COUNT,
    InReplyToTweetTimelinesSharedFeatures.HAS_QUOTE,
    InReplyToTweetTimelinesSharedFeatures.EARLYBIRD_SCORE,
    InReplyToRecapFeatures.PREV_USER_TWEET_ENGAGEMENT,
    InReplyToRecapFeatures.IS_SENSITIVE,
    InReplyToRecapFeatures.IS_AUTHOR_NEW,
    InReplyToRecapFeatures.NUM_MENTIONS,
    InReplyToRecapFeatures.HAS_MENTION,
    InReplyToRecapFeatures.HAS_HASHTAG,
    InReplyToRecapFeatures.IS_AUTHOR_NSFW,
    InReplyToRecapFeatures.IS_AUTHOR_SPAM,
    InReplyToRecapFeatures.IS_AUTHOR_BOT,
    InReplyToRecapFeatures.FROM_MUTUAL_FOLLOW,
    InReplyToRecapFeatures.USER_REP,
    InReplyToRecapFeatures.FROM_VERIFIED_ACCOUNT,
    InReplyToRecapFeatures.HAS_IMAGE,
    InReplyToRecapFeatures.HAS_NEWS,
    InReplyToRecapFeatures.HAS_VIDEO,
    InReplyToRecapFeatures.HAS_VISIBLE_LINK,
    InReplyToRecapFeatures.IS_OFFENSIVE,
    InReplyToRecapFeatures.IS_REPLY,
    InReplyToRecapFeatures.BIDIRECTIONAL_REPLY_COUNT,
    InReplyToRecapFeatures.UNIDIRECTIONAL_REPLY_COUNT,
    InReplyToRecapFeatures.BIDIRECTIONAL_RETWEET_COUNT,
    InReplyToRecapFeatures.UNIDIRECTIONAL_RETWEET_COUNT,
    InReplyToRecapFeatures.BIDIRECTIONAL_FAV_COUNT,
    InReplyToRecapFeatures.UNIDIRECTIONAL_FAV_COUNT,
    InReplyToRecapFeatures.CONVERSATIONAL_COUNT,
    InReplyToRecapFeatures.REPLY_COUNT,
    InReplyToRecapFeatures.RETWEET_COUNT,
    InReplyToRecapFeatures.FAV_COUNT,
    InReplyToRecapFeatures.TEXT_SCORE,
    InReplyToRecapFeatures.FAV_COUNT_V2,
    InReplyToRecapFeatures.RETWEET_COUNT_V2,
    InReplyToRecapFeatures.REPLY_COUNT_V2)

  override val commonFeatures: Set[Feature[_]] = Set.empty

  override def setFeatures(
    ebFeatures: Option[sc.ThriftTweetFeatures],
    richDataRecord: RichDataRecord
  ): Unit = {
    if (ebFeatures.nonEmpty) {
      val features = ebFeatures.get

      richDataRecord.setFeatureValueFromOption(
        InReplyToTweetTimelinesSharedFeatures.WEIGHTED_FAV_COUNT,
        features.weightedFavoriteCount.map(_.toDouble)
      )

      richDataRecord.setFeatureValueFromOption(
        InReplyToTweetTimelinesSharedFeatures.WEIGHTED_RETWEET_COUNT,
        features.weightedRetweetCount.map(_.toDouble)
      )

      richDataRecord.setFeatureValueFromOption(
        InReplyToTweetTimelinesSharedFeatures.WEIGHTED_REPLY_COUNT,
        features.weightedReplyCount.map(_.toDouble)
      )

      richDataRecord.setFeatureValueFromOption(
        InReplyToTweetTimelinesSharedFeatures.WEIGHTED_QUOTE_COUNT,
        features.weightedQuoteCount.map(_.toDouble)
      )

      richDataRecord.setFeatureValueFromOption(
        InReplyToTweetTimelinesSharedFeatures.DECAYED_FAVORITE_COUNT,
        features.decayedFavoriteCount.map(_.toDouble)
      )

      richDataRecord.setFeatureValueFromOption(
        InReplyToTweetTimelinesSharedFeatures.DECAYED_RETWEET_COUNT,
        features.decayedRetweetCount.map(_.toDouble)
      )

      richDataRecord.setFeatureValueFromOption(
        InReplyToTweetTimelinesSharedFeatures.DECAYED_REPLY_COUNT,
        features.decayedReplyCount.map(_.toDouble)
      )

      richDataRecord.setFeatureValueFromOption(
        InReplyToTweetTimelinesSharedFeatures.DECAYED_QUOTE_COUNT,
        features.decayedQuoteCount.map(_.toDouble)
      )

      richDataRecord.setFeatureValueFromOption(
        InReplyToTweetTimelinesSharedFeatures.QUOTE_COUNT,
        features.quoteCount.map(_.toDouble)
      )

      richDataRecord.setFeatureValueFromOption(
        InReplyToTweetTimelinesSharedFeatures.HAS_QUOTE,
        features.hasQuote
      )

      if (features.earlybirdScore > 0)
        richDataRecord.setFeatureValue[JDouble](
          InReplyToTweetTimelinesSharedFeatures.EARLYBIRD_SCORE,
          features.earlybirdScore
        )

      richDataRecord.setFeatureValue[JDouble](
        InReplyToRecapFeatures.PREV_USER_TWEET_ENGAGEMENT,
        features.prevUserTweetEngagement.toDouble
      )

      richDataRecord
        .setFeatureValue[JBoolean](InReplyToRecapFeatures.IS_SENSITIVE, features.isSensitiveContent)
      richDataRecord
        .setFeatureValue[JBoolean](InReplyToRecapFeatures.IS_AUTHOR_NEW, features.isAuthorNew)
      richDataRecord.setFeatureValue[JDouble](
        InReplyToRecapFeatures.NUM_MENTIONS,
        features.numMentions.toDouble)
      richDataRecord
        .setFeatureValue[JBoolean](InReplyToRecapFeatures.HAS_MENTION, (features.numMentions > 0))
      richDataRecord
        .setFeatureValue[JBoolean](InReplyToRecapFeatures.HAS_HASHTAG, (features.numHashtags > 0))
      richDataRecord
        .setFeatureValue[JBoolean](InReplyToRecapFeatures.IS_AUTHOR_NSFW, features.isAuthorNSFW)
      richDataRecord
        .setFeatureValue[JBoolean](InReplyToRecapFeatures.IS_AUTHOR_SPAM, features.isAuthorSpam)
      richDataRecord
        .setFeatureValue[JBoolean](InReplyToRecapFeatures.IS_AUTHOR_BOT, features.isAuthorBot)
      richDataRecord.setFeatureValue[JBoolean](
        InReplyToRecapFeatures.FROM_MUTUAL_FOLLOW,
        features.fromMutualFollow)
      richDataRecord.setFeatureValue[JDouble](InReplyToRecapFeatures.USER_REP, features.userRep)
      richDataRecord.setFeatureValue[JBoolean](
        InReplyToRecapFeatures.FROM_VERIFIED_ACCOUNT,
        features.fromVerifiedAccount)
      richDataRecord.setFeatureValue[JBoolean](InReplyToRecapFeatures.HAS_IMAGE, features.hasImage)
      richDataRecord.setFeatureValue[JBoolean](InReplyToRecapFeatures.HAS_NEWS, features.hasNews)
      richDataRecord.setFeatureValue[JBoolean](InReplyToRecapFeatures.HAS_VIDEO, features.hasVideo)
      richDataRecord
        .setFeatureValue[JBoolean](InReplyToRecapFeatures.HAS_VISIBLE_LINK, features.hasVisibleLink)
      richDataRecord
        .setFeatureValue[JBoolean](InReplyToRecapFeatures.IS_OFFENSIVE, features.isOffensive)
      richDataRecord.setFeatureValue[JBoolean](InReplyToRecapFeatures.IS_REPLY, features.isReply)
      richDataRecord.setFeatureValue[JDouble](
        InReplyToRecapFeatures.BIDIRECTIONAL_REPLY_COUNT,
        features.bidirectionalReplyCount)
      richDataRecord.setFeatureValue[JDouble](
        InReplyToRecapFeatures.UNIDIRECTIONAL_REPLY_COUNT,
        features.unidirectionalReplyCount)
      richDataRecord.setFeatureValue[JDouble](
        InReplyToRecapFeatures.BIDIRECTIONAL_RETWEET_COUNT,
        features.bidirectionalRetweetCount)
      richDataRecord.setFeatureValue[JDouble](
        InReplyToRecapFeatures.UNIDIRECTIONAL_RETWEET_COUNT,
        features.unidirectionalRetweetCount)
      richDataRecord.setFeatureValue[JDouble](
        InReplyToRecapFeatures.BIDIRECTIONAL_FAV_COUNT,
        features.bidirectionalFavCount)
      richDataRecord.setFeatureValue[JDouble](
        InReplyToRecapFeatures.UNIDIRECTIONAL_FAV_COUNT,
        features.unidirectionalFavCount)
      richDataRecord.setFeatureValue[JDouble](
        InReplyToRecapFeatures.CONVERSATIONAL_COUNT,
        features.conversationCount)
      richDataRecord
        .setFeatureValue[JDouble](InReplyToRecapFeatures.REPLY_COUNT, features.replyCount.toDouble)
      richDataRecord.setFeatureValue[JDouble](
        InReplyToRecapFeatures.RETWEET_COUNT,
        features.retweetCount.toDouble)
      richDataRecord
        .setFeatureValue[JDouble](InReplyToRecapFeatures.FAV_COUNT, features.favCount.toDouble)
      richDataRecord.setFeatureValue[JDouble](InReplyToRecapFeatures.TEXT_SCORE, features.textScore)
      richDataRecord.setFeatureValueFromOption(
        InReplyToRecapFeatures.FAV_COUNT_V2,
        features.favCountV2.map(_.toDouble))
      richDataRecord.setFeatureValueFromOption(
        InReplyToRecapFeatures.RETWEET_COUNT_V2,
        features.retweetCountV2.map(_.toDouble)
      )
      richDataRecord.setFeatureValueFromOption(
        InReplyToRecapFeatures.REPLY_COUNT_V2,
        features.replyCountV2.map(_.toDouble))
    }
  }
}
