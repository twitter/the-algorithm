package com.X.home_mixer.product.scored_tweets.filter

import com.X.home_mixer.model.HomeFeatures.EarlybirdFeature
import com.X.home_mixer.model.HomeFeatures.InReplyToTweetIdFeature
import com.X.home_mixer.util.ReplyRetweetUtil
import com.X.product_mixer.component_library.model.candidate.TweetCandidate
import com.X.product_mixer.core.functional_component.filter.Filter
import com.X.product_mixer.core.functional_component.filter.FilterResult
import com.X.product_mixer.core.model.common.CandidateWithFeatures
import com.X.product_mixer.core.model.common.identifier.FilterIdentifier
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.stitch.Stitch

/**
 * This filter removes source tweets of retweets, added via second EB call in TLR
 */
object RetweetSourceTweetRemovingFilter extends Filter[PipelineQuery, TweetCandidate] {

  override val identifier: FilterIdentifier = FilterIdentifier("RetweetSourceTweetRemoving")

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[FilterResult[TweetCandidate]] = {
    val (kept, removed) =
      candidates.partition(
        _.features.getOrElse(EarlybirdFeature, None).exists(_.isSourceTweet)) match {
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
