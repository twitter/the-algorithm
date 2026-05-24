package com.twitter.tweet_mixer.functional_component.hydrator

import com.twitter.finagle.stats.Counter
import com.twitter.finagle.stats.Stat
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.tweet_mixer.param.UTGParams.OutlierFilterPercentileThresholdParam
import com.twitter.tweet_mixer.param.UTGParams.OutlierMinRequiredSignalsParam

import javax.inject.Inject

object UTGOutlierSignalsFeature extends FeatureWithDefaultOnFailure[PipelineQuery, Set[Long]] {
  override def defaultValue: Set[Long] = Set.empty
}

class UTGOutlierSignalsQueryFeatureHydrator @Inject() (stats: StatsReceiver)
    extends QueryFeatureHydrator[PipelineQuery] {

  private val statsScope: StatsReceiver = stats.scope("utg_outlier_signal_counter")
  private val overallFilterRate: Stat = statsScope.stat("overall_filter_rate")
  private val lowSignalUserCount: Counter = statsScope.counter("low_signal_user_counter")
  private val filteredCount: Counter = statsScope.counter("filtered_signal_counter")
  private val totalCount: Counter = statsScope.counter("total_signal_counter")

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier(
    "UTGOutlierSignals")

  override def features: Set[Feature[_, _]] = Set(UTGOutlierSignalsFeature)

  private def dot(a: Array[Double], b: Array[Double]): Double = {
    a.zip(b).map { case (x, y) => x * y }.sum
  }

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    val embeddingMap =
      query.features.get
        .getOrElse(OutlierDeepRetrievalTweetEmbeddingFeature, None).getOrElse(Map.empty)
    val tweetIds = embeddingMap.keys.toSeq

    val outliers = {
      if (tweetIds.length < query.params(OutlierMinRequiredSignalsParam)) {
        lowSignalUserCount.incr()
        Set.empty[Long]
      } else {
        val vectors = tweetIds.map(id => embeddingMap(id).map(_.toDouble).toArray).toArray

        val normalized = vectors.map { vec =>
          val norm = math.sqrt(vec.map(x => x * x).sum)
          if (norm == 0.0) vec else vec.map(_ / norm)
        }

        val n = normalized.length

        val dists = Array.tabulate(n, n) { (i, j) =>
          if (i == j) Double.PositiveInfinity else 1.0 - dot(normalized(i), normalized(j))
        }

        val minDists = dists.map(_.min)

        val sortedMinDists = minDists.sorted

        val percentileThreshold = query.params(OutlierFilterPercentileThresholdParam) / 100.0

        val rank = percentileThreshold * (n - 1)
        val lower = math.floor(rank).toInt
        val upper = math.ceil(rank).toInt
        val threshold = if (lower == upper) {
          sortedMinDists(lower)
        } else {
          sortedMinDists(lower) + (rank - lower) * (sortedMinDists(upper) - sortedMinDists(lower))
        }

        val outlierSet =
          tweetIds.zip(minDists).collect { case (id, dist) if dist > threshold => id }.toSet

        filteredCount.incr(outlierSet.size)
        totalCount.incr(tweetIds.length)
        overallFilterRate.add(outlierSet.size / tweetIds.length.toFloat)

        outlierSet
      }
    }

    Stitch.value(
      FeatureMap(UTGOutlierSignalsFeature, outliers)
    )
  }
}
