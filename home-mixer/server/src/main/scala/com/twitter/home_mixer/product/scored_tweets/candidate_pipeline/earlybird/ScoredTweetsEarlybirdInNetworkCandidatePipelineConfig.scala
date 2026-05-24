package com.twitter.home_mixer.product.scored_tweets.candidate_pipeline.earlybird

import com.twitter.finagle.thrift.ClientId
import com.twitter.home_mixer.functional_component.feature_hydrator.TweetEntityServiceFeatureHydrator
import com.twitter.home_mixer.product.scored_tweets.candidate_source.EarlybirdRealtimeCGTweetCandidateSource
import com.twitter.home_mixer.product.scored_tweets.feature_hydrator.FollowedUserScoresFeatureHydrator
import com.twitter.home_mixer.product.scored_tweets.feature_hydrator.IsExtendedReplyFeatureHydrator
import com.twitter.home_mixer.product.scored_tweets.filter.ExtendedDirectedAtFilter
import com.twitter.home_mixer.product.scored_tweets.filter.QualifiedRepliesFilter
import com.twitter.home_mixer.product.scored_tweets.gate.MinCachedTweetsGate
import com.twitter.home_mixer.product.scored_tweets.model.ScoredTweetsQuery
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.CachedScoredTweets
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.CandidateSourceParams
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.FeatureHydration
import com.twitter.home_mixer.product.scored_tweets.query_transformer.earlybird.EarlybirdInNetworkQueryTransformer
import com.twitter.home_mixer.product.scored_tweets.response_transformer.earlybird.ScoredTweetsEarlybirdInNetworkResponseFeatureTransformer
import com.twitter.product_mixer.component_library.feature_hydrator.query.param_gated.ParamGatedQueryFeatureHydrator
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.candidate_source.BaseCandidateSource
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BaseCandidateFeatureHydrator
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BaseQueryFeatureHydrator
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.search.earlybird.{thriftscala => eb}
import com.twitter.timelines.configapi.FSParam
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Candidate Pipeline Config that fetches tweets from the earlybird InNetwork Candidate Source
 */
@Singleton
class ScoredTweetsEarlybirdInNetworkCandidatePipelineConfig @Inject() (
  earlybirdTweetCandidateSource: EarlybirdRealtimeCGTweetCandidateSource,
  followedUserScoresFeatureHydrator: FollowedUserScoresFeatureHydrator,
  tweetEntityServiceFeatureHydrator: TweetEntityServiceFeatureHydrator,
  clientId: ClientId)
    extends CandidatePipelineConfig[
      ScoredTweetsQuery,
      eb.EarlybirdRequest,
      eb.ThriftSearchResult,
      TweetCandidate
    ] {

  override val identifier: CandidatePipelineIdentifier =
    CandidatePipelineIdentifier("ScoredTweetsEarlybirdInNetwork")

  override val supportedClientParam: Option[FSParam[Boolean]] = Some(
    CandidateSourceParams.EnableInNetworkCandidateSourceParam)

  override val gates: Seq[Gate[ScoredTweetsQuery]] = Seq(
    MinCachedTweetsGate(identifier, CachedScoredTweets.MinCachedTweetsParam)
  )

  override val queryFeatureHydration: Seq[
    BaseQueryFeatureHydrator[ScoredTweetsQuery, _]
  ] = Seq(
    ParamGatedQueryFeatureHydrator(
      FeatureHydration.EnableFollowedUserScoreBackfillFeaturesParam,
      followedUserScoresFeatureHydrator
    )
  )

  override val candidateSource: BaseCandidateSource[eb.EarlybirdRequest, eb.ThriftSearchResult] =
    earlybirdTweetCandidateSource

  override val queryTransformer: CandidatePipelineQueryTransformer[
    ScoredTweetsQuery,
    eb.EarlybirdRequest
  ] = EarlybirdInNetworkQueryTransformer(identifier, clientId = Some(clientId.name))

  override val featuresFromCandidateSourceTransformers: Seq[
    CandidateFeatureTransformer[eb.ThriftSearchResult]
  ] = Seq(ScoredTweetsEarlybirdInNetworkResponseFeatureTransformer)

  override val preFilterFeatureHydrationPhase1: Seq[
    BaseCandidateFeatureHydrator[ScoredTweetsQuery, TweetCandidate, _]
  ] = Seq(tweetEntityServiceFeatureHydrator)

  override val filters: Seq[Filter[ScoredTweetsQuery, TweetCandidate]] = Seq(
    QualifiedRepliesFilter,
    ExtendedDirectedAtFilter
  )

  override val postFilterFeatureHydration: Seq[
    BaseCandidateFeatureHydrator[ScoredTweetsQuery, TweetCandidate, _]
  ] = Seq(IsExtendedReplyFeatureHydrator)

  override val resultTransformer: CandidatePipelineResultsTransformer[
    eb.ThriftSearchResult,
    TweetCandidate
  ] = { sourceResult => TweetCandidate(id = sourceResult.id) }
}
