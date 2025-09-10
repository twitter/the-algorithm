package com.twitter.home_mixer.product.scored_tweets.candidate_pipeline

import com.twitter.home_mixer.functional_component.feature_hydrator.TweetEntityServiceFeatureHydrator
import com.twitter.home_mixer.model.HomeFeatures.CachedScoredTweetsFeature
import com.twitter.home_mixer.product.scored_tweets.candidate_source.ContentExplorationCandidateResponse
import com.twitter.home_mixer.product.scored_tweets.model.ScoredTweetsQuery
import com.twitter.home_mixer.product.scored_tweets.candidate_source.ContentExplorationCandidateSource
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.EnableContentExplorationCandidatePipelineParam
import com.twitter.home_mixer.product.scored_tweets.query_transformer.ContentExplorationQueryRequest
import com.twitter.home_mixer.product.scored_tweets.query_transformer.ContentExplorationQueryTransformer
import com.twitter.home_mixer.product.scored_tweets.response_transformer.ScoredTweetsContentExplorationResponseFeatureTransformer
import com.twitter.product_mixer.component_library.gate.EmptySeqFeatureGate
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BaseCandidateFeatureHydrator
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.timelines.configapi.FSParam
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Candidate Pipeline Config that fetches tweets from the content exploration post source
 */
@Singleton
class ScoredTweetsContentExplorationCandidatePipelineConfig @Inject() (
  contentExplorationCandidateSource: ContentExplorationCandidateSource,
  tweetEntityServiceFeatureHydrator: TweetEntityServiceFeatureHydrator)
    extends CandidatePipelineConfig[
      ScoredTweetsQuery,
      ContentExplorationQueryRequest,
      ContentExplorationCandidateResponse,
      TweetCandidate
    ] {

  override val supportedClientParam: Option[FSParam[Boolean]] = Some(
    EnableContentExplorationCandidatePipelineParam)

  override val gates: Seq[Gate[ScoredTweetsQuery]] = Seq(
    EmptySeqFeatureGate(CachedScoredTweetsFeature))

  override val identifier: CandidatePipelineIdentifier =
    ScoredTweetsContentExplorationCandidatePipelineConfig.identifier

  override val candidateSource: ContentExplorationCandidateSource =
    contentExplorationCandidateSource

  override val queryTransformer: CandidatePipelineQueryTransformer[
    ScoredTweetsQuery,
    ContentExplorationQueryRequest
  ] = ContentExplorationQueryTransformer

  override val featuresFromCandidateSourceTransformers: Seq[
    CandidateFeatureTransformer[ContentExplorationCandidateResponse]
  ] = Seq(ScoredTweetsContentExplorationResponseFeatureTransformer)

  override val postFilterFeatureHydration: Seq[
    BaseCandidateFeatureHydrator[ScoredTweetsQuery, TweetCandidate, _]
  ] = Seq(tweetEntityServiceFeatureHydrator)

  override val resultTransformer: CandidatePipelineResultsTransformer[
    ContentExplorationCandidateResponse,
    TweetCandidate
  ] = { sourceResult =>
    TweetCandidate(id = sourceResult.tweetId)
  }
}

object ScoredTweetsContentExplorationCandidatePipelineConfig {
  val identifier: CandidatePipelineIdentifier =
    CandidatePipelineIdentifier("ScoredTweetsContentExploration")
}
