package com.twitter.home_mixer.product.scored_tweets.candidate_pipeline

import com.twitter.home_mixer.functional_component.feature_hydrator.TweetEntityServiceFeatureHydrator
import com.twitter.home_mixer.functional_component.gate.AllowForYouRecommendationsGate
import com.twitter.home_mixer.model.HomeFeatures.SignupCountryFeature
import com.twitter.home_mixer.product.scored_tweets.candidate_pipeline.ScoredTweetsStaticCandidatePipelineConfig.Identifier
import com.twitter.home_mixer.product.scored_tweets.candidate_source.StaticPostsCandidateSource
import com.twitter.home_mixer.product.scored_tweets.candidate_source.StaticSourceRequest
import com.twitter.home_mixer.product.scored_tweets.model.ScoredTweetsQuery
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.CandidateSourceParams
import com.twitter.home_mixer.product.scored_tweets.response_transformer.ScoredTweetsStaticResponseFeatureTransformer
import com.twitter.product_mixer.component_library.feature_hydrator.query.social_graph.SGSFollowedUsersFeature
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
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

@Singleton
class ScoredTweetsStaticCandidatePipelineConfig @Inject() (
  staticPostsCandidateSource: StaticPostsCandidateSource,
  tweetEntityServiceFeatureHydrator: TweetEntityServiceFeatureHydrator)
    extends CandidatePipelineConfig[
      ScoredTweetsQuery,
      StaticSourceRequest,
      Long,
      TweetCandidate
    ] {

  override val identifier: CandidatePipelineIdentifier = Identifier

  override val gates: Seq[Gate[ScoredTweetsQuery]] =
    Seq(AllowForYouRecommendationsGate)

  override val supportedClientParam: Option[FSParam[Boolean]] =
    Some(CandidateSourceParams.EnableStaticSourceParam)

  override val queryTransformer: CandidatePipelineQueryTransformer[
    ScoredTweetsQuery,
    StaticSourceRequest
  ] = { query =>
    val following =
      query.features.map(_.getOrElse(SGSFollowedUsersFeature, Seq.empty)).getOrElse(Seq.empty).toSet
    val countryCode = query.clientContext.countryCode
    val signupCountryCode = query.features.flatMap(_.getOrElse(SignupCountryFeature, None))
    val countryCodes = Seq(countryCode, signupCountryCode).flatten
    StaticSourceRequest(countryCodes = countryCodes, following = following)
  }

  override def candidateSource: CandidateSource[StaticSourceRequest, Long] =
    staticPostsCandidateSource

  override val preFilterFeatureHydrationPhase1: Seq[
    BaseCandidateFeatureHydrator[ScoredTweetsQuery, TweetCandidate, _]
  ] = Seq(tweetEntityServiceFeatureHydrator)

  override val featuresFromCandidateSourceTransformers: Seq[
    CandidateFeatureTransformer[Long]
  ] = Seq(ScoredTweetsStaticResponseFeatureTransformer)

  override val resultTransformer: CandidatePipelineResultsTransformer[
    Long,
    TweetCandidate
  ] = { sourceResult => TweetCandidate(id = sourceResult) }
}

object ScoredTweetsStaticCandidatePipelineConfig {
  val SourceIdentifier = "ScoredTweetsStatic"
  val Identifier: CandidatePipelineIdentifier = CandidatePipelineIdentifier(SourceIdentifier)
}
