package com.twitter.home_mitwitterer.product.scored_tweets.feature_hydrator.real_time_aggregates

import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.datarecord.DataRecordInAFeature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.util.OffloadFuturePools
import com.twitter.stitch.Stitch

trait BaseRealTimeAggregateQueryFeatureHydrator[K]
    etwittertends QueryFeatureHydrator[PipelineQuery]
    with BaseRealtimeAggregateHydrator[K] {

  val outputFeature: DataRecordInAFeature[PipelineQuery]

  override def features: Set[Feature[_, _]] = Set(outputFeature)

  override lazy val statScope: String = identifier.toString

  def keysFromQueryAndCandidates(
    query: PipelineQuery
  ): Option[K]

  override def hydrate(
    query: PipelineQuery
  ): Stitch[FeatureMap] = OffloadFuturePools.offloadFuture {
    val possiblyKeys = keysFromQueryAndCandidates(query)
    fetchAndConstructDataRecords(Seq(possiblyKeys)).map { dataRecords =>
      FeatureMapBuilder()
        .add(outputFeature, dataRecords.head)
        .build()
    }
  }
}
