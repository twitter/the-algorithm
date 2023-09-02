package com.twitter.home_mixer.util.earlybird

import com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant
import com.twitter.search.earlybird.{thriftscala => eb}

object RelevanceSearchUtil {

  val Mentions: String = EarlybirdFieldConstant.MENTIONS_FACET
  val Hashtags: String = EarlybirdFieldConstant.HASHTAGS_FACET
  val FacetsToFetch: Seq[String] = Seq(Mentions, Hashtags)

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
      rankingParams = None,
      maxHitsToProcess = Some(500),
      maxUserBlendCount = Some(3),
      proximityPhraseWeight = 9.0,
      returnAllResults = Some(true)
    )
  }
}
