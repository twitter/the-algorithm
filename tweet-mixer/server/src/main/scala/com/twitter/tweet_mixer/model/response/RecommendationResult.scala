package com.twitter.tweet_mixer.model.response

import com.twitter.product_mixer.core.model.marshalling.HasMarshalling
import com.twitter.tweet_mixer.{thriftscala => t}

sealed trait RecommendationResult extends HasMarshalling

case class TweetResult(
  id: Long,
  score: Double,
  metricTags: Seq[t.MetricTag],
  metadata: Option[t.TweetMetadata] = None,
  inReplyToTweetId: Option[Long] = None,
  authorId: Option[Long] = None)
    extends RecommendationResult
