package com.twitter.tweet_mixer.functional_component.filter

import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.filter.FilterResult
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mixer.core.model.common.presentation.CandidatePipelines
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.tweet_mixer.feature.ScoreFeature
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.DeepRetrievalTweetTweetANNScoreThreshold
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.DeepRetrievalTweetTweetANNScoreMaxThreshold
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.DeepRetrievalTweetTweetEmbeddingANNScoreThreshold
import com.twitter.tweet_mixer.utils.CandidatePipelineConstants

/**
 * Filters out tweets that pass min score for specific candidate pipeline
 */
case class MinScoreFilter(
  candidatePipelinesToInclude: Option[Set[CandidatePipelineIdentifier]] = None,
  candidatePipelinesToExclude: Set[CandidatePipelineIdentifier] = Set.empty)
    extends Filter[PipelineQuery, TweetCandidate]
    with Filter.Conditionally[PipelineQuery, TweetCandidate]
    with ShouldIgnoreCandidatePipelinesFilter {

  override val identifier: FilterIdentifier = FilterIdentifier("MinScore")

  override def onlyIf(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Boolean = {
    query.params(DeepRetrievalTweetTweetANNScoreThreshold) > 0.0 ||
    query.params(DeepRetrievalTweetTweetANNScoreMaxThreshold) < 1.0 ||
    query.params(DeepRetrievalTweetTweetEmbeddingANNScoreThreshold) > 0.0
  }

  def isInCandidateScope(features: FeatureMap): Boolean = {
    candidatePipelinesToInclude
      .map(candidatePipelines =>
        features.get(CandidatePipelines).exists(candidatePipelines.contains(_))).getOrElse(false)
  }

  def passMinScore(features: FeatureMap, minScore: Double): Boolean =
    features.get(ScoreFeature) >= minScore

  def passMaxScore(features: FeatureMap, maxScore: Double): Boolean =
    features.get(ScoreFeature) <= maxScore

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[FilterResult[TweetCandidate]] = {
    val minScoreThreshold = query.params(DeepRetrievalTweetTweetANNScoreThreshold)
    val maxScoreThreshold = query.params(DeepRetrievalTweetTweetANNScoreMaxThreshold)
    val embMinScoreThreshold = query.params(DeepRetrievalTweetTweetEmbeddingANNScoreThreshold)
    val embPipelineId = CandidatePipelineIdentifier(
      "HomeRecommendedTweets" + CandidatePipelineConstants.DeepRetrievalTweetTweetEmbeddingSimilarity)

    val (kept, removed) = candidates.partition { candidate =>
      if (isInCandidateScope(candidate.features)) {
        val minScore =
          if (candidate.features.get(CandidatePipelines).contains(embPipelineId))
            embMinScoreThreshold
          else minScoreThreshold
        passMinScore(candidate.features, minScore) &&
        passMaxScore(candidate.features, maxScoreThreshold)
      } else true // keep candidates not in the included pipelines
    }

    val filterResult = FilterResult(
      kept = kept.map(_.candidate),
      removed = removed.map(_.candidate)
    )

    Stitch.value(filterResult)
  }
}
