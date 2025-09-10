package com.twitter.tweet_mixer.candidate_pipeline

import com.twitter.finagle.thrift.ClientId
import com.twitter.product_mixer.component_library.gate.EmptySeqFeatureGate
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.candidate_source.BaseCandidateSource
import com.twitter.product_mixer.core.functional_component.common.alert.NotificationGroup
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mixer.core.gate.ParamGate
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.model.marshalling.request.HasExcludedIds
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.tweet_mixer.candidate_source.earlybird_realtime_cg.EarlybirdRealtimeCGTweetCandidateSource
import com.twitter.tweet_mixer.candidate_source.earlybird_realtime_cg.InNetworkRequest
import com.twitter.tweet_mixer.utils.CandidatePipelineConstants
import com.twitter.search.earlybird.{thriftscala => eb}
import com.twitter.tweet_mixer.functional_component.hydrator.HaploliteFeature
import com.twitter.tweet_mixer.functional_component.transformer.EarlybirdInNetworkQueryTransformer
import com.twitter.tweet_mixer.functional_component.transformer.EarlybirdInNetworkResponseFeatureTransformer
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.HaploliteTweetsBasedEnabled
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EarlybirdInNetworkCandidatePipelineConfigFactory @Inject() (
  earlybirdTweetCandidateSource: EarlybirdRealtimeCGTweetCandidateSource,
  clientId: ClientId) {

  def build[Query <: PipelineQuery with HasExcludedIds](
    identifierPrefix: String
  )(
    implicit notificationGroup: Map[String, NotificationGroup]
  ): EarlybirdInNetworkCandidatePipelineConfig[Query] = {
    new EarlybirdInNetworkCandidatePipelineConfig(
      identifierPrefix,
      earlybirdTweetCandidateSource,
      clientId)
  }
}

class EarlybirdInNetworkCandidatePipelineConfig[Query <: PipelineQuery with HasExcludedIds](
  identifierPrefix: String,
  earlybirdTweetCandidateSource: EarlybirdRealtimeCGTweetCandidateSource,
  clientId: ClientId
)(
  implicit notificationGroup: Map[String, NotificationGroup])
    extends CandidatePipelineConfig[
      Query,
      InNetworkRequest,
      eb.ThriftSearchResult,
      TweetCandidate
    ] {

  override val identifier: CandidatePipelineIdentifier = CandidatePipelineIdentifier(
    identifierPrefix + CandidatePipelineConstants.EarlybirdInNetwork)

  override val gates: Seq[Gate[PipelineQuery]] = Seq(
    ParamGate(
      name = "HaploliteTweetsBasedEBEnabled",
      param = HaploliteTweetsBasedEnabled
    ),
    EmptySeqFeatureGate(HaploliteFeature)
  )

  override val queryTransformer: CandidatePipelineQueryTransformer[
    Query,
    InNetworkRequest
  ] = EarlybirdInNetworkQueryTransformer(identifier, Some(clientId.name + ".prod"))

  override def candidateSource: BaseCandidateSource[InNetworkRequest, eb.ThriftSearchResult] =
    earlybirdTweetCandidateSource
  override val featuresFromCandidateSourceTransformers = Seq(
    EarlybirdInNetworkResponseFeatureTransformer)

  override val resultTransformer: CandidatePipelineResultsTransformer[
    eb.ThriftSearchResult,
    TweetCandidate
  ] = { candidate =>
    TweetCandidate(
      id = candidate.id
    )
  }
}
