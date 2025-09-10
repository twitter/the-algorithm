package com.twitter.tweet_mixer.candidate_pipeline

import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.functional_component.common.alert.Alert
import com.twitter.product_mixer.core.functional_component.common.alert.NotificationGroup
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mixer.core.gate.ParamGate
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.model.common.identifier.TransformerIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.simclusters_v2.thriftscala.EmbeddingType.FavBasedProducer
import com.twitter.simclusters_v2.thriftscala.InternalId
import com.twitter.tweet_mixer.candidate_source.simclusters_ann.SANNQuery
import com.twitter.tweet_mixer.candidate_source.simclusters_ann.SimClustersAnnCandidateSource
import com.twitter.tweet_mixer.functional_component.transformer.TweetMixerCandidateFeatureTransformer
import com.twitter.tweet_mixer.functional_component.transformer.SANNQueryTransformer
import com.twitter.tweet_mixer.model.response.TweetMixerCandidate
import com.twitter.tweet_mixer.param.SimClustersANNParams.EnableProducerBasedMaxCandidatesParam
import com.twitter.tweet_mixer.param.SimClustersANNParams.ProducerBasedClusterParamMap
import com.twitter.tweet_mixer.param.SimClustersANNParams.ProducerBasedMaxCandidatesParam
import com.twitter.tweet_mixer.param.SimClustersANNParams.ProducerBasedMinScoreParam
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.SimclustersProducerBasedEnabled
import com.twitter.tweet_mixer.utils.CandidatePipelineConstants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SimclustersProducerBasedCandidatePipelineConfigFactory @Inject() (
  simClustersAnnCandidateSource: SimClustersAnnCandidateSource) {

  def build[Query <: PipelineQuery](
    identifierPrefix: String,
    signalFn: PipelineQuery => Seq[Long],
    alerts: Seq[Alert] = Seq.empty
  )(
    implicit notificationGroup: Map[String, NotificationGroup]
  ): SimclustersProducerBasedCandidatePipelineConfig[Query] = {
    new SimclustersProducerBasedCandidatePipelineConfig(
      identifierPrefix,
      signalFn,
      simClustersAnnCandidateSource,
      alerts
    )
  }
}

class SimclustersProducerBasedCandidatePipelineConfig[Query <: PipelineQuery](
  identifierPrefix: String,
  signalFn: PipelineQuery => Seq[Long],
  simClustersAnnCandidateSource: SimClustersAnnCandidateSource,
  override val alerts: Seq[Alert]
)(
  implicit notificationGroup: Map[String, NotificationGroup])
    extends CandidatePipelineConfig[
      Query,
      SANNQuery,
      TweetMixerCandidate,
      TweetCandidate
    ] {
  override val identifier: CandidatePipelineIdentifier = CandidatePipelineIdentifier(
    identifierPrefix + CandidatePipelineConstants.SimClustersProducerBased)

  override val gates: Seq[Gate[PipelineQuery]] = Seq(
    ParamGate(
      name = "SimclustersProducerBasedEnabled",
      param = SimclustersProducerBasedEnabled
    )
  )

  override val queryTransformer: CandidatePipelineQueryTransformer[
    Query,
    SANNQuery
  ] = { query =>
    SANNQueryTransformer(
      TransformerIdentifier("SANNQueryProducerBased"),
      ProducerBasedClusterParamMap,
      query => signalFn(query).map(InternalId.UserId(_)),
      Seq(FavBasedProducer),
      ProducerBasedMinScoreParam,
      maxInterestedInCandidatesParam = if (query.params(EnableProducerBasedMaxCandidatesParam)) {
        Some(ProducerBasedMaxCandidatesParam)
      } else { None }
    ).transform(query)
  }

  override def candidateSource: CandidateSource[
    SANNQuery,
    TweetMixerCandidate
  ] = simClustersAnnCandidateSource

  override val featuresFromCandidateSourceTransformers = Seq(TweetMixerCandidateFeatureTransformer)

  override val resultTransformer: CandidatePipelineResultsTransformer[
    TweetMixerCandidate,
    TweetCandidate
  ] = { candidate =>
    TweetCandidate(id = candidate.tweetId)
  }
}
