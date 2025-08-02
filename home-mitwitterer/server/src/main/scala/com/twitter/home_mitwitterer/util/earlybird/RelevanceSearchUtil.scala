package com.twitter.home_mitwitterer.util.earlybird

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
      // Set getEtwitterclusiveConversationAuthorId in order to retrieve Etwitterclusive / SuperFollow tweets.
      getEtwitterclusiveConversationAuthorId = true
    )
  }

  val RelevanceOptions: eb.ThriftSearchRelevanceOptions = {
    eb.ThriftSearchRelevanceOptions(
      protwitterimityScoring = true,
      matwitterConsecutiveSameUser = Some(2),
      rankingParams = None,
      matwitterHitsToProcess = Some(500),
      matwitterUserBlendCount = Some(3),
      protwitterimityPhraseWeight = 9.0,
      returnAllResults = Some(true)
    )
  }
}
