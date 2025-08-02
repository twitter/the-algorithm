package com.twitter.home_mitwitterer.product.scored_tweets.feature_hydrator.real_time_aggregates

import com.twitter.product_mitwitterer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.datarecord.DataRecordInAFeature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.twitter.product_mitwitterer.core.model.common.CandidateWithFeatures
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.util.OffloadFuturePools
import com.twitter.stitch.Stitch

trait BaseRealTimeAggregateBulkCandidateFeatureHydrator[K]
    etwittertends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate]
    with BaseRealtimeAggregateHydrator[K] {

  val outputFeature: DataRecordInAFeature[TweetCandidate]

  override def features: Set[Feature[_, _]] = Set(outputFeature)

  override lazy val statScope: String = identifier.toString

  def keysFromQueryAndCandidates(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Seq[Option[K]]

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = OffloadFuturePools.offloadFuture {
    val possiblyKeys = keysFromQueryAndCandidates(query, candidates)
    fetchAndConstructDataRecords(possiblyKeys).map { dataRecords =>
      dataRecords.map { dataRecord =>
        FeatureMapBuilder().add(outputFeature, dataRecord).build()
      }
    }
  }
}
