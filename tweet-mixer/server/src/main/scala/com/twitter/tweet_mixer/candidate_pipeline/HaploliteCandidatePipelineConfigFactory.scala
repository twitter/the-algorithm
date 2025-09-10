package com.twitter.tweet_mixer.candidate_pipeline

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.candidate_source.BaseCandidateSource
import com.twitter.product_mixer.core.functional_component.candidate_source.PassthroughCandidateSource
import com.twitter.product_mixer.core.functional_component.common.alert.NotificationGroup
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mixer.core.gate.ParamGate
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.product_mixer.core.model.marshalling.request.HasExcludedIds
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.timelines.clients.haplolite.TweetTimelineHaploCodec
import com.twitter.timelineservice.model.Tweet
import com.twitter.timelineservice.model.core.TimelineKind
import com.twitter.tweet_mixer.functional_component.hydrator.HaploliteFeature
import com.twitter.tweet_mixer.functional_component.transformer.HaploliteResponseFeatureTransformer
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.HaploliteTweetsBasedEnabled
import com.twitter.tweet_mixer.utils.CandidatePipelineConstants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HaploliteCandidatePipelineConfigFactory @Inject() (statsReceiver: StatsReceiver) {
  private val tweetTimelineHaploCodec = new TweetTimelineHaploCodec(statsReceiver)
  def build[Query <: PipelineQuery with HasExcludedIds](
    identifierPrefix: String
  )(
    implicit notificationGroup: Map[String, NotificationGroup]
  ): HaploliteCandidatePipelineConfig[Query] = {
    new HaploliteCandidatePipelineConfig(identifierPrefix, tweetTimelineHaploCodec)
  }
}

class HaploliteCandidatePipelineConfig[Query <: PipelineQuery with HasExcludedIds](
  identifierPrefix: String,
  codec: TweetTimelineHaploCodec
)(
  implicit notificationGroup: Map[String, NotificationGroup])
    extends CandidatePipelineConfig[
      Query,
      Query,
      Tweet,
      TweetCandidate
    ] {

  override val identifier: CandidatePipelineIdentifier = CandidatePipelineIdentifier(
    identifierPrefix + CandidatePipelineConstants.Haplolite)

  override val gates: Seq[Gate[PipelineQuery]] = Seq(
    ParamGate(
      name = "HaploliteTweets",
      param = HaploliteTweetsBasedEnabled
    )
  )

  override val queryTransformer: CandidatePipelineQueryTransformer[
    Query,
    Query
  ] = identity

  private def getTweets(query: PipelineQuery): Seq[Tweet] = {
    val haploliteFeature = query.features
      .getOrElse(FeatureMap.empty)
      .getOrElse(HaploliteFeature, Seq.empty)
    val result = codec
      .decode(haploliteFeature, TimelineKind.home)
    result
  }

  override def candidateSource: BaseCandidateSource[Query, Tweet] =
    PassthroughCandidateSource(
      CandidateSourceIdentifier(identifier.name),
      getTweets
    )

  override val featuresFromCandidateSourceTransformers = Seq(HaploliteResponseFeatureTransformer)

  override val resultTransformer: CandidatePipelineResultsTransformer[
    Tweet,
    TweetCandidate
  ] = { candidate =>
    TweetCandidate(id = candidate.tweetId)
  }
}
