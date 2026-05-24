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
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.ContentExplorationMaxViewCountThreshold
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.ContentExplorationTier2MaxViewCountThreshold
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.ViewCountInfoOnTweetFeatureHydratorEnabled
import com.twitter.tweet_mixer.functional_component.hydrator.UecAggTweetTotalFeature
import com.twitter.tweet_mixer.utils.CandidatePipelineConstants
import com.twitter.strato.graphql.unified_counter.thriftscala.UecAnalyticsEngagementTypes

/**
 * Filters out tweets that pass max view count for specific candidate pipeline
 */
case class MaxViewCountFilter(
  candidatePipelinesToInclude: Option[Set[CandidatePipelineIdentifier]] = None,
  candidatePipelinesToExclude: Set[CandidatePipelineIdentifier] = Set.empty)
    extends Filter[PipelineQuery, TweetCandidate]
    with Filter.Conditionally[PipelineQuery, TweetCandidate]
    with ShouldIgnoreCandidatePipelinesFilter {
  override val identifier: FilterIdentifier = FilterIdentifier("MaxViewCount")

  override def onlyIf(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Boolean = {
    query.params(ViewCountInfoOnTweetFeatureHydratorEnabled) &&
    (query.params(ContentExplorationMaxViewCountThreshold) > 0 ||
    query.params(ContentExplorationTier2MaxViewCountThreshold) > 0)
  }

  // Keep it same as home recommended tweets domain marshaller name
  private val identifierPrefix: String = "HomeRecommendedTweets"
  private val pipelineDrI2i = CandidatePipelineIdentifier(
    identifierPrefix + CandidatePipelineConstants.ContentExplorationDRTweetTweet)
  private val pipelineDrI2iTierTwo = CandidatePipelineIdentifier(
    identifierPrefix + CandidatePipelineConstants.ContentExplorationDRTweetTweetTierTwo)
  private val pipelineDrU2i = CandidatePipelineIdentifier(
    identifierPrefix + CandidatePipelineConstants.ContentExplorationDRUserTweet)
  private val pipelineDrU2iTierTwo = CandidatePipelineIdentifier(
    identifierPrefix + CandidatePipelineConstants.ContentExplorationDRUserTweetTierTwo)
  private val pipelineEmb = CandidatePipelineIdentifier(
    identifierPrefix + CandidatePipelineConstants.ContentExplorationEmbeddingSimilarity)
  private val pipelineEmbTierTwo = CandidatePipelineIdentifier(
    identifierPrefix + CandidatePipelineConstants.ContentExplorationEmbeddingSimilarityTierTwo)

  def isInCandidateScope(features: FeatureMap): Boolean = {
    candidatePipelinesToInclude
      .map(candidatePipelines =>
        features.get(CandidatePipelines).exists(candidatePipelines.contains(_))).getOrElse(false)
  }

  def getSuggestionType(features: FeatureMap): String = {
    features.get(CandidatePipelines) match {
      case p if p.contains(pipelineDrI2i) => "ForYouContentExplorationDeepRetrievalI2i"
      case p if p.contains(pipelineDrI2iTierTwo) => "ForYouContentExplorationTier2DeepRetrievalI2i"
      case p if p.contains(pipelineDrU2i) => "ForYouContentExplorationDeepRetrievalI2i"
      case p if p.contains(pipelineDrU2iTierTwo) => "ForYouContentExplorationTier2DeepRetrievalI2i"
      case p if p.contains(pipelineEmb) => "ForYouContentExploration"
      case p if p.contains(pipelineEmbTierTwo) => "ForYouContentExplorationTier2"
      case _ => "ForYouContentExploration"
    }
  }

  def getMaxViewCount(features: FeatureMap, query: PipelineQuery): Int = {
    val tier2Pipelines = Set(
      pipelineDrI2iTierTwo,
      pipelineEmbTierTwo,
      pipelineDrU2iTierTwo
    )

    if (features.get(CandidatePipelines).exists(tier2Pipelines.contains(_))) {
      query.params(ContentExplorationTier2MaxViewCountThreshold)
    } else {
      query.params(ContentExplorationMaxViewCountThreshold)
    }
  }

  def passMaxViewCount(features: FeatureMap, maxViewCount: Int, suggestionType: String): Boolean = {
    features
      .get(UecAggTweetTotalFeature)
      .flatMap { aggTotalResponse =>
        aggTotalResponse.engagements
          .find { metric =>
            metric.engagementType == UecAnalyticsEngagementTypes.Displayed &&
            metric.suggestionType.contains(suggestionType)
          }.map(_.count)
      }
      .forall(_ < maxViewCount) // return true if value is missing
  }

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[FilterResult[TweetCandidate]] = {
    val (kept, removed) = candidates.partition { candidate =>
      if (isInCandidateScope(candidate.features)) {
        val suggestionType = getSuggestionType(candidate.features)
        val maxViewCount = getMaxViewCount(candidate.features, query)
        passMaxViewCount(candidate.features, maxViewCount, suggestionType)
      } else true // keep candidates not in the included pipelines
    }

    val filterResult = FilterResult(
      kept = kept.map(_.candidate),
      removed = removed.map(_.candidate)
    )

    Stitch.value(filterResult)
  }
}
