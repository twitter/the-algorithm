package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.finagle.stats.Counter
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.HeartbeatOptimizerParamsMHPkey
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.strato.client.Fetcher
import com.twitter.strato.columns.heartbeat_optimizer.thriftscala.OptimizerMHParamsValue
import com.twitter.strato.columns.heartbeat_optimizer.thriftscala.ParameterAndValue
import com.twitter.strato.generated.client.heartbeat_optimizer.HeartbeatOptimizerParamsMHClientColumn
import javax.inject.Inject
import javax.inject.Singleton

object HeartbeatOptimizerWeightsFeature extends Feature[PipelineQuery, Option[OptimizerParams]]

case class OptimizerParams(
  epochTimestamp: Long,
  nBuckets: Int,
  optimizerWeights: Seq[Map[String, Double]])

@Singleton
class HeartbeatOptimizerParamsHydrator @Inject() (
  heartbeatOptimizerParamsMHClientColumn: HeartbeatOptimizerParamsMHClientColumn,
  statsReceiver: StatsReceiver)
    extends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier(
    "HeartbeatOptimizerParams")

  private val fetcher: Fetcher[(String, String), Unit, OptimizerMHParamsValue] =
    heartbeatOptimizerParamsMHClientColumn.fetcher

  override val features: Set[Feature[_, _]] = Set(HeartbeatOptimizerWeightsFeature)

  private val LKEY = "0"

  private val sucessStat: Counter = statsReceiver.counter("success")
  private val failureStat: Counter = statsReceiver.counter("failure")

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    val pkey = query.params(HeartbeatOptimizerParamsMHPkey)
    fetcher
      .fetch((pkey, LKEY)).map { manhattanResult =>
        manhattanResult.v match {
          case Some(optimizerMHParamsValue) =>
            val parameterAndValueList: Seq[Seq[ParameterAndValue]] =
              optimizerMHParamsValue.parameterAndValueList

            val paramMaps: Seq[Map[String, Double]] = parameterAndValueList.map {
              paramAndValueList =>
                paramAndValueList.map { paramAndValue =>
                  (paramAndValue.parameter, paramAndValue.value)
                }.toMap
            }
            val optimizerWeights = OptimizerParams(
              epochTimestamp = optimizerMHParamsValue.epochTimestamp.toLong,
              nBuckets = paramMaps.size,
              optimizerWeights = paramMaps
            )
            sucessStat.incr(1)
            FeatureMapBuilder()
              .add(HeartbeatOptimizerWeightsFeature, Some(optimizerWeights))
              .build()
          case _ =>
            failureStat.incr(1)
            FeatureMapBuilder()
              .add(HeartbeatOptimizerWeightsFeature, None)
              .build()
        }
      }.handle {
        case _ =>
          failureStat.incr(1)
          FeatureMapBuilder()
            .add(HeartbeatOptimizerWeightsFeature, None)
            .build()
      }
  }
}
