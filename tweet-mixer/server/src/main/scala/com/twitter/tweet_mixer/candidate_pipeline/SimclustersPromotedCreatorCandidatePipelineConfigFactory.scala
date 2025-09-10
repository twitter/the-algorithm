package com.twitter.tweet_mixer.candidate_pipeline

import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.functional_component.common.alert.NotificationGroup
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mixer.core.gate.ParamGate
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.model.common.identifier.TransformerIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.simclusters_v2.thriftscala.EmbeddingType.LogFavLongestL2EmbeddingTweet
import com.twitter.simclusters_v2.thriftscala.InternalId
import com.twitter.tweet_mixer.candidate_source.simclusters_ann.SANNQuery
import com.twitter.tweet_mixer.candidate_source.simclusters_ann.SimClustersAnnCandidateSource
import com.twitter.tweet_mixer.candidate_source.simclusters_ann.SimclusterPromotedCreatorAnnCandidateSource
import com.twitter.tweet_mixer.functional_component.transformer.TweetMixerCandidateFeatureTransformer
import com.twitter.tweet_mixer.functional_component.transformer.SANNQueryTransformer
import com.twitter.tweet_mixer.model.response.TweetMixerCandidate
import com.twitter.tweet_mixer.param.SimClustersANNParams.TweetBasedClusterParamMap
import com.twitter.tweet_mixer.param.SimClustersANNParams.TweetBasedMinScoreParam
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.SimclustersPromotedCreatorEnabled
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultEmptyResponseRateAlert
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultSuccessRateAlert
import com.twitter.tweet_mixer.utils.CandidatePipelineConstants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SimclustersPromotedCreatorCandidatePipelineConfigFactory @Inject() (
  simClustersAnnCandidateSource: SimclusterPromotedCreatorAnnCandidateSource) {

  def build[Query <: PipelineQuery](
    identifierPrefix: String,
    signalsFn: PipelineQuery => Seq[Long]
  )(
    implicit notificationGroup: Map[String, NotificationGroup]
  ): SimclustersPromotedCreatorCandidatePipelineConfig[Query] = {
    new SimclustersPromotedCreatorCandidatePipelineConfig(
      identifierPrefix,
      signalsFn = signalsFn,
      simClustersAnnCandidateSource)
  }
}

class SimclustersPromotedCreatorCandidatePipelineConfig[Query <: PipelineQuery](
  identifierPrefix: String,
  signalsFn: PipelineQuery => Seq[Long],
  simClustersAnnCandidateSource: SimClustersAnnCandidateSource
)(
  implicit notificationGroup: Map[String, NotificationGroup])
    extends CandidatePipelineConfig[
      Query,
      SANNQuery,
      TweetMixerCandidate,
      TweetCandidate
    ] {
  override val identifier: CandidatePipelineIdentifier = CandidatePipelineIdentifier(
    identifierPrefix + CandidatePipelineConstants.SimClustersPromotedCreator)

  override val gates: Seq[Gate[PipelineQuery]] = Seq(
    ParamGate(
      name = "SimclustersPromotedCreatorEnabled",
      param = SimclustersPromotedCreatorEnabled
    )
  )

  override val queryTransformer: CandidatePipelineQueryTransformer[
    Query,
    SANNQuery
  ] = {
    SANNQueryTransformer(
      TransformerIdentifier("SANNQueryPromotedCreator"),
      TweetBasedClusterParamMap,
      query => signalFnSelector(query),
      Seq(LogFavLongestL2EmbeddingTweet),
      TweetBasedMinScoreParam
    )
  }

  private def signalFnSelector(query: PipelineQuery): Seq[InternalId.TweetId] = {
    signalsFn(query).map(InternalId.TweetId(_))
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

  override val alerts = Seq(
    defaultSuccessRateAlert(),
    defaultEmptyResponseRateAlert()
  )
}
