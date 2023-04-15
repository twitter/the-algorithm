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
