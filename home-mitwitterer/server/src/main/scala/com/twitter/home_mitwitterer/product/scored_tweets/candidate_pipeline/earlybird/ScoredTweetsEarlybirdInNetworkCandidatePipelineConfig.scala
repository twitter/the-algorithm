package com.twitter.home_mitwitterer.product.scored_tweets.candidate_pipeline.earlybird

import com.twitter.finagle.thrift.ClientId
import com.twitter.home_mitwitterer.functional_component.candidate_source.EarlybirdCandidateSource
import com.twitter.home_mitwitterer.product.scored_tweets.gate.MinCachedTweetsGate
import com.twitter.home_mitwitterer.product.scored_tweets.model.ScoredTweetsQuery
import com.twitter.home_mitwitterer.product.scored_tweets.param.ScoredTweetsParam.CachedScoredTweets
import com.twitter.home_mitwitterer.product.scored_tweets.query_transformer.earlybird.EarlybirdInNetworkQueryTransformer
import com.twitter.home_mitwitterer.product.scored_tweets.response_transformer.earlybird.ScoredTweetsEarlybirdInNetworkResponseFeatureTransformer
import com.twitter.product_mitwitterer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.BaseCandidateSource
import com.twitter.product_mitwitterer.core.functional_component.gate.Gate
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mitwitterer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.search.earlybird.{thriftscala => eb}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

/**
 * Candidate Pipeline Config that fetches tweets from the earlybird InNetwork Candidate Source
 */
@Singleton
class ScoredTweetsEarlybirdInNetworkCandidatePipelineConfig @Inject() (
  earlybirdCandidateSource: EarlybirdCandidateSource,
  clientId: ClientId)
    etwittertends CandidatePipelineConfig[
      ScoredTweetsQuery,
      eb.EarlybirdRequest,
      eb.ThriftSearchResult,
      TweetCandidate
    ] {

  override val identifier: CandidatePipelineIdentifier =
    CandidatePipelineIdentifier("ScoredTweetsEarlybirdInNetwork")

  override val gates: Seq[Gate[ScoredTweetsQuery]] = Seq(
    MinCachedTweetsGate(identifier, CachedScoredTweets.MinCachedTweetsParam)
  )

  override val candidateSource: BaseCandidateSource[eb.EarlybirdRequest, eb.ThriftSearchResult] =
    earlybirdCandidateSource

  override val queryTransformer: CandidatePipelineQueryTransformer[
    ScoredTweetsQuery,
    eb.EarlybirdRequest
  ] = EarlybirdInNetworkQueryTransformer(identifier, clientId = Some(clientId.name))

  override val featuresFromCandidateSourceTransformers: Seq[
    CandidateFeatureTransformer[eb.ThriftSearchResult]
  ] = Seq(ScoredTweetsEarlybirdInNetworkResponseFeatureTransformer)

  override val resultTransformer: CandidatePipelineResultsTransformer[
    eb.ThriftSearchResult,
    TweetCandidate
  ] = { sourceResult => TweetCandidate(id = sourceResult.id) }
}
