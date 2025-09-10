package com.twitter.tweet_mixer.candidate_pipeline

import com.twitter.ann.common.CosineDistance
import com.twitter.ann.common.NeighborWithDistanceWithSeed
import com.twitter.ann.common.QueryableById
import com.twitter.ann.hnsw.HnswParams
import com.twitter.conversions.DurationOps.richDurationFromInt
import com.twitter.product_mixer.component_library.candidate_source.ann.AnnCandidateSource
import com.twitter.product_mixer.component_library.candidate_source.ann.AnnIdQuery
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.functional_component.common.alert.NotificationGroup
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mixer.core.gate.ParamGate
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.tweet_mixer.functional_component.transformer.AnnCandidateFeatureTransformer
import com.twitter.tweet_mixer.model.ModuleNames
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.TwhinConsumerBasedEnabled
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultEmptyResponseRateAlert
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultSuccessRateAlert
import com.twitter.tweet_mixer.utils.CandidatePipelineConstants
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class TwhinConsumerBasedCandidatePipelineConfigFactory @Inject() (
  @Named(ModuleNames.ConsumerBasedTwHINAnnQueryableById)
  twhinConsumerBasedAnnQueryableById: QueryableById[Long, Long, HnswParams, CosineDistance]) {

  def build[Query <: PipelineQuery](
    identifierPrefix: String
  )(
    implicit notificationGroup: Map[String, NotificationGroup]
  ): TwhinConsumerBasedCandidatePipelineConfig[Query] = {
    new TwhinConsumerBasedCandidatePipelineConfig(
      identifierPrefix,
      twhinConsumerBasedAnnQueryableById
    )
  }
}

class TwhinConsumerBasedCandidatePipelineConfig[Query <: PipelineQuery](
  identifierPrefix: String,
  @Named(ModuleNames.ConsumerBasedTwHINAnnQueryableById)
  twhinConsumerBasedAnnQueryableById: QueryableById[Long, Long, HnswParams, CosineDistance]
)(
  implicit notificationGroup: Map[String, NotificationGroup])
    extends CandidatePipelineConfig[
      Query,
      AnnIdQuery[Long, HnswParams],
      NeighborWithDistanceWithSeed[Long, Long, CosineDistance],
      TweetCandidate
    ] {
  override val identifier: CandidatePipelineIdentifier = CandidatePipelineIdentifier(
    identifierPrefix + CandidatePipelineConstants.TwhinConsumerBased)

  override val gates: Seq[Gate[PipelineQuery]] = Seq(
    ParamGate(
      name = "TwhinConsumerBasedEnabled",
      param = TwhinConsumerBasedEnabled
    )
  )

  override val queryTransformer: CandidatePipelineQueryTransformer[
    Query,
    AnnIdQuery[Long, HnswParams]
  ] = { query =>
    AnnIdQuery(Seq(query.getRequiredUserId), numOfNeighbors = 200, HnswParams(800))
  }

  override def candidateSource: CandidateSource[
    AnnIdQuery[Long, HnswParams],
    NeighborWithDistanceWithSeed[Long, Long, CosineDistance]
  ] =
    new AnnCandidateSource(
      twhinConsumerBasedAnnQueryableById,
      batchSize = 20,
      timeoutPerRequest = 200.millis,
      identifier = CandidateSourceIdentifier("TwhinConsumerBasedAnn"))

  override def featuresFromCandidateSourceTransformers: Seq[
    CandidateFeatureTransformer[NeighborWithDistanceWithSeed[Long, Long, CosineDistance]]
  ] = Seq(AnnCandidateFeatureTransformer)

  override val resultTransformer: CandidatePipelineResultsTransformer[
    NeighborWithDistanceWithSeed[Long, Long, CosineDistance],
    TweetCandidate
  ] = { neighbor =>
    TweetCandidate(
      id = neighbor.neighbor
    )
  }

  override val alerts = Seq(
    defaultSuccessRateAlert(),
    defaultEmptyResponseRateAlert()
  )
}
