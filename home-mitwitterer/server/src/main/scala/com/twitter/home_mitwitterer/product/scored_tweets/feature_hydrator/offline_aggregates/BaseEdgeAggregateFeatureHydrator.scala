package com.twitter.home_mitwitterer.product.scored_tweets.feature_hydrator.offline_aggregates

import com.twitter.ml.api.DataRecord
import com.twitter.ml.api.FeatureContetwittert
import com.twitter.ml.api.IRecordOneToOneAdapter
import com.twitter.product_mitwitterer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mitwitterer.core.feature.datarecord.DataRecordInAFeature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.twitter.product_mitwitterer.core.model.common.CandidateWithFeatures
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.util.OffloadFuturePools
import com.twitter.stitch.Stitch
import com.twitter.timelines.data_processing.ml_util.aggregation_framework.AggregateGroup
import com.twitter.timelines.data_processing.ml_util.aggregation_framework.AggregateType.AggregateType
import com.twitter.timelines.suggests.common.dense_data_record.thriftjava.DenseCompactDataRecord
import java.lang.{Long => JLong}
import java.util.{Map => JMap}

abstract case class BaseEdgeAggregateFeature(
  aggregateGroups: Set[AggregateGroup],
  aggregateType: AggregateType,
  etwittertractMapFn: AggregateFeaturesToDecodeWithMetadata => JMap[JLong, DenseCompactDataRecord],
  adapter: IRecordOneToOneAdapter[Seq[DataRecord]],
  getSecondaryKeysFn: CandidateWithFeatures[TweetCandidate] => Seq[Long])
    etwittertends DataRecordInAFeature[PipelineQuery]
    with FeatureWithDefaultOnFailure[PipelineQuery, DataRecord] {
  override def defaultValue: DataRecord = new DataRecord

  private val rootFeatureInfo = new AggregateFeatureInfo(aggregateGroups, aggregateType)
  val featureContetwittert: FeatureContetwittert = rootFeatureInfo.featureContetwittert
  val rootFeature: BaseAggregateRootFeature = rootFeatureInfo.feature
}

trait BaseEdgeAggregateFeatureHydrator
    etwittertends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate] {

  def aggregateFeatures: Set[BaseEdgeAggregateFeature]

  override def features = aggregateFeatures.asInstanceOf[Set[Feature[_, _]]]

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = OffloadFuturePools.offload {
    val featureMapBuilders: Seq[FeatureMapBuilder] =
      for (_ <- candidates) yield FeatureMapBuilder()

    aggregateFeatures.foreach { feature =>
      val featureValues = hydrateAggregateFeature(query, candidates, feature)
      (featureMapBuilders zip featureValues).foreach {
        case (featureMapBuilder, featureValue) => featureMapBuilder.add(feature, featureValue)
      }
    }

    featureMapBuilders.map(_.build())
  }

  private def hydrateAggregateFeature(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]],
    feature: BaseEdgeAggregateFeature
  ): Seq[DataRecord] = {
    val rootFeature = feature.rootFeature
    val etwittertractMapFn = feature.etwittertractMapFn
    val featureContetwittert = feature.featureContetwittert
    val secondaryIds: Seq[Seq[Long]] = candidates.map(feature.getSecondaryKeysFn)

    val featuresToDecodeWithMetadata = query.features
      .flatMap(_.getOrElse(rootFeature, None))
      .getOrElse(AggregateFeaturesToDecodeWithMetadata.empty)

    // Decode the DenseCompactDataRecords into DataRecords for each required secondary id.
    val decoded: Map[Long, DataRecord] = Utils.selectAndTransform(
      secondaryIds.flatten.distinct,
      featuresToDecodeWithMetadata.toDataRecord,
      etwittertractMapFn(featuresToDecodeWithMetadata)
    )

    // Remove unnecessary features in-place. This is safe because the underlying DataRecords
    // are unique and have just been generated in the previous step.
    decoded.values.foreach(Utils.filterDataRecord(_, featureContetwittert))

    // Put features into the FeatureMapBuilders
    secondaryIds.map { ids =>
      val dataRecords = ids.flatMap(decoded.get)
      feature.adapter.adaptToDataRecord(dataRecords)
    }
  }
}
