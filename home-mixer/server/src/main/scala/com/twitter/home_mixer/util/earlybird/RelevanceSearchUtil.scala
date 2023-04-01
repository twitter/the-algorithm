package com.twitter.home_mixer.util.earlybird

import com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant
import com.twitter.search.common.ranking.{thriftscala => scr}
import com.twitter.search.earlybird.{thriftscala => eb}

object RelevanceSearchUtil {

  val Mentions: String = EarlybirdFieldConstant.MENTIONS_FACET
  val Hashtags: String = EarlybirdFieldConstant.HASHTAGS_FACET
  val FacetsToFetch: Seq[String] = Seq(Mentions, Hashtags)

  private val RankingParams: scr.ThriftRankingParams = {
    scr.ThriftRankingParams(
      `type` = Some(scr.ThriftScoringFunctionType.TensorflowBased),
      selectedTensorflowModel = Some("timelines_rectweet_replica"),
      minScore = -1.0e100,
      retweetCountParams = Some(scr.ThriftLinearFeatureRankingParams(weight = 20.0)),
      replyCountParams = Some(scr.ThriftLinearFeatureRankingParams(weight = 1.0)),
      reputationParams = Some(scr.ThriftLinearFeatureRankingParams(weight = 0.2)),
      luceneScoreParams = Some(scr.ThriftLinearFeatureRankingParams(weight = 2.0)),
      textScoreParams = Some(scr.ThriftLinearFeatureRankingParams(weight = 0.18)),
      urlParams = Some(scr.ThriftLinearFeatureRankingParams(weight = 2.0)),
      isReplyParams = Some(scr.ThriftLinearFeatureRankingParams(weight = 1.0)),
      favCountParams = Some(scr.ThriftLinearFeatureRankingParams(weight = 30.0)),
      langEnglishUIBoost = 0.5,
      langEnglishTweetBoost = 0.2,
      langDefaultBoost = 0.02,
      unknownLanguageBoost = 0.05,
      offensiveBoost = 0.1,
      inTrustedCircleBoost = 3.0,
      multipleHashtagsOrTrendsBoost = 0.6,
      inDirectFollowBoost = 4.0,
      tweetHasTrendBoost = 1.1,
      selfTweetBoost = 2.0,
      tweetHasImageUrlBoost = 2.0,
      tweetHasVideoUrlBoost = 2.0,
      useUserLanguageInfo = true,
      ageDecayParams = Some(scr.ThriftAgeDecayRankingParams(slope = 0.005, base = 1.0)),
      selectedModels = Some(Map("home_mixer_unified_engagement_prod" -> 1.0)),
      applyBoosts = false,
    )
  }

  val MetadataOptions: eb.ThriftSearchResultMetadataOptions = {
    eb.ThriftSearchResultMetadataOptions(
      getTweetUrls = true,
      getResultLocation = false,
      getLuceneScore = false,
      getInReplyToStatusId = true,
      getReferencedTweetAuthorId = true,
      getMediaBits = true,
      getAllFeatures = true,
      returnSearchResultFeatures = true,
      // Set getExclusiveConversationAuthorId in order to retrieve Exclusive / SuperFollow tweets.
      getExclusiveConversationAuthorId = true
    )
  }

  val RelevanceOptions: eb.ThriftSearchRelevanceOptions = {
    eb.ThriftSearchRelevanceOptions(
      proximityScoring = true,
      maxConsecutiveSameUser = Some(2),
      rankingParams = Some(RankingParams),
      maxHitsToProcess = Some(500),
      maxUserBlendCount = Some(3),
      proximityPhraseWeight = 9.0,
      returnAllResults = Some(true)
    )
  }
}
