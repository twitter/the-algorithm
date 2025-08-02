package com.twitter.home_mitwitterer.product.scored_tweets.filter

import com.twitter.home_mitwitterer.model.HomeFeatures.EarlybirdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.InReplyToTweetIdFeature
import com.twitter.home_mitwitterer.util.ReplyRetweetUtil
import com.twitter.product_mitwitterer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mitwitterer.core.functional_component.filter.Filter
import com.twitter.product_mitwitterer.core.functional_component.filter.FilterResult
import com.twitter.product_mitwitterer.core.model.common.CandidateWithFeatures
import com.twitter.product_mitwitterer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

/**
 * This filter removes source tweets of retweets, added via second EB call in TLR
 */
object RetweetSourceTweetRemovingFilter etwittertends Filter[PipelineQuery, TweetCandidate] {

  override val identifier: FilterIdentifier = FilterIdentifier("RetweetSourceTweetRemoving")

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[FilterResult[TweetCandidate]] = {
    val (kept, removed) =
      candidates.partition(
        _.features.getOrElse(EarlybirdFeature, None).etwitterists(_.isSourceTweet)) match {
        case (sourceTweets, nonSourceTweets) =>
          val inReplyToTweetIds: Set[Long] =
            nonSourceTweets
              .filter(ReplyRetweetUtil.isEligibleReply(_)).flatMap(
                _.features.getOrElse(InReplyToTweetIdFeature, None)).toSet
          val (keptSourceTweets, removedSourceTweets) = sourceTweets
            .map(_.candidate)
            .partition(candidate => inReplyToTweetIds.contains(candidate.id))
          (nonSourceTweets.map(_.candidate) ++ keptSourceTweets, removedSourceTweets)
      }
    Stitch.value(FilterResult(kept = kept, removed = removed))
  }
}
