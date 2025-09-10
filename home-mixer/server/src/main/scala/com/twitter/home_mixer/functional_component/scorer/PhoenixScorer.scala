package com.twitter.home_mixer.functional_component.scorer

import com.google.inject.name.Named
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.model.HomeFeatures.SourceTweetIdFeature
import com.twitter.home_mixer.model.HomeFeatures.UserActionsFeature
import com.twitter.home_mixer.model.PhoenixPredictedScoreFeature.PhoenixPredictedScoreFeatureSet
import com.twitter.home_mixer.param.HomeGlobalParams.PhoenixCluster
import com.twitter.home_mixer.param.HomeGlobalParams.PhoenixInferenceClusterParam
import com.twitter.home_mixer.param.HomeGlobalParams.PhoenixTimeoutInMsParam
import com.twitter.home_mixer.util.PhoenixUtils.createCandidateSets
import com.twitter.home_mixer.util.PhoenixUtils.getPredictionResponseMap
import com.twitter.home_mixer.util.PhoenixUtils.getTweetInfoFromCandidates
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.scorer.Scorer
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.Conditionally
import com.twitter.product_mixer.core.model.common.identifier.ScorerIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.util.OffloadFuturePools
import com.twitter.servo.util.MemoizingStatsReceiver
import com.twitter.stitch.Stitch
import io.grpc.ManagedChannel
import javax.inject.Inject
import javax.inject.Singleton
@Singleton
case class PhoenixScorer @Inject() (
  @Named("PhoenixClient") channelsMap: Map[PhoenixCluster.Value, Seq[ManagedChannel]],
  statsReceiver: StatsReceiver)
    extends Scorer[PipelineQuery, TweetCandidate]
    with Conditionally[PipelineQuery] {

  override val identifier: ScorerIdentifier = ScorerIdentifier("Phoenix")

  override val features: Set[Feature[_, _]] =
    PhoenixPredictedScoreFeatureSet.asInstanceOf[Set[Feature[_, _]]]

  val memoizingStatsReceiver: MemoizingStatsReceiver = new MemoizingStatsReceiver(
    statsReceiver.scope(this.getClass.getSimpleName))

  override def onlyIf(query: PipelineQuery): Boolean = {
    query.features.flatMap(_.getOrElse(UserActionsFeature, None)).isDefined
  }

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = OffloadFuturePools.offloadStitch {
    val phoenixCluster = query.params(PhoenixInferenceClusterParam)
    val channels = channelsMap(phoenixCluster)

    val tweetInfos =
      getTweetInfoFromCandidates(candidates.map(_.candidate), candidates.map(_.features))
    val request = createCandidateSets(query, tweetInfos)
    val timeoutMs = query.params(PhoenixTimeoutInMsParam)
    val predictionsMapStitch =
      getPredictionResponseMap(
        request,
        channels,
        phoenixCluster.toString,
        timeoutMs,
        memoizingStatsReceiver)

    predictionsMapStitch.map { predictionsMap =>
      candidates.map { candidate =>
        val sourceTweetId =
          candidate.features.getOrElse(SourceTweetIdFeature, None) match {
            case Some(sourceTweetId) => sourceTweetId
            case _ => candidate.candidate.id
          }
        val actionPredictionsMap = predictionsMap.getOrElse(sourceTweetId, Map.empty)
        val fmBuilder = FeatureMapBuilder()
        PhoenixPredictedScoreFeatureSet.map { feature =>
          val predictions = feature.actions.flatMap(actionPredictionsMap.get)
          val score = if (predictions.nonEmpty) Some(predictions.max) else None
          fmBuilder.add(feature, score)
        }
        fmBuilder.build()
      }
    }
  }
}
