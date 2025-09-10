package com.twitter.home_mixer.product.scored_tweets.candidate_pipeline

import com.twitter.home_mixer.functional_component.feature_hydrator.TweetEntityServiceFeatureHydrator
import com.twitter.home_mixer.functional_component.filter.HasAuthorFilter
import com.twitter.home_mixer.functional_component.filter.ReplyFilter
import com.twitter.home_mixer.model.HomeFeatures.CachedScoredTweetsFeature
import com.twitter.home_mixer.model.HomeFeatures.TimelineServiceTweetsFeature
import com.twitter.home_mixer.product.scored_tweets.gate.DenyLowSignalUserGate
import com.twitter.home_mixer.product.scored_tweets.gate.MinTimeSinceLastRequestGate
import com.twitter.home_mixer.product.scored_tweets.model.ScoredTweetsQuery
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.EnableBackfillCandidatePipelineParam
import com.twitter.home_mixer.product.scored_tweets.response_transformer.ScoredTweetsBackfillResponseFeatureTransformer
import com.twitter.product_mixer.component_library.gate.EmptySeqFeatureGate
import com.twitter.product_mixer.component_library.gate.NonEmptySeqFeatureGate
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.functional_component.candidate_source.PassthroughCandidateSource
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BaseCandidateFeatureHydrator
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.timelines.configapi.FSParam
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScoredTweetsBackfillCandidatePipelineConfig @Inject() (
  tweetEntityServiceFeatureHydrator: TweetEntityServiceFeatureHydrator)
    extends CandidatePipelineConfig[
      ScoredTweetsQuery,
      ScoredTweetsQuery,
      Long,
      TweetCandidate
    ] {

  override val identifier: CandidatePipelineIdentifier =
    CandidatePipelineIdentifier("ScoredTweetsBackfill")

  override val supportedClientParam: Option[FSParam[Boolean]] =
    Some(EnableBackfillCandidatePipelineParam)

  override val gates: Seq[Gate[ScoredTweetsQuery]] =
    Seq(
      MinTimeSinceLastRequestGate,
      DenyLowSignalUserGate,
      NonEmptySeqFeatureGate(TimelineServiceTweetsFeature),
      EmptySeqFeatureGate(CachedScoredTweetsFeature)
    )

  override val queryTransformer: CandidatePipelineQueryTransformer[
    ScoredTweetsQuery,
    ScoredTweetsQuery
  ] = identity

  override def candidateSource: CandidateSource[ScoredTweetsQuery, Long] =
    PassthroughCandidateSource(
      identifier = CandidateSourceIdentifier("ScoredTweetsBackfill"),
      candidateExtractor = { query =>
        query.features.map(_.getOrElse(TimelineServiceTweetsFeature, Seq.empty)).toSeq.flatten
      }
    )

  override val featuresFromCandidateSourceTransformers: Seq[
    CandidateFeatureTransformer[Long]
  ] = Seq(ScoredTweetsBackfillResponseFeatureTransformer)

  override val resultTransformer: CandidatePipelineResultsTransformer[Long, TweetCandidate] = {
    sourceResult => TweetCandidate(id = sourceResult)
  }

  override val preFilterFeatureHydrationPhase1: Seq[
    BaseCandidateFeatureHydrator[ScoredTweetsQuery, TweetCandidate, _]
  ] = Seq(tweetEntityServiceFeatureHydrator)

  override val filters: Seq[Filter[ScoredTweetsQuery, TweetCandidate]] = Seq(
    ReplyFilter,
    HasAuthorFilter
  )
}
