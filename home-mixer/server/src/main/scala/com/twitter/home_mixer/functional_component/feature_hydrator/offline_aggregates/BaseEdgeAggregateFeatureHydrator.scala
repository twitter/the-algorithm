package com.twitter.home_mixer.functional_component.feature_hydrator.offline_aggregates

import com.twitter.home_mixer.functional_component.feature_hydrator.WithDefaultFeatureMap
import com.twitter.ml.api.DataRecord
import com.twitter.ml.api.FeatureContext
import com.twitter.ml.api.IRecordOneToOneAdapter
import com.twitter.product_mixer.component_library.feature_hydrator.candidate.offline_aggregates.AggregateFeaturesToDecodeWithMetadata
import com.twitter.product_mixer.component_library.feature_hydrator.candidate.offline_aggregates.BaseAggregateRootFeature
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.feature.datarecord.DataRecordInAFeature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.util.OffloadFuturePools
import com.twitter.stitch.Stitch
import com.twitter.timelines.data_processing.ml_util.aggregation_framework.AggregateGroup
import com.twitter.timelines.data_processing.ml_util.aggregation_framework.AggregateType.AggregateType
import com.twitter.timelines.suggests.common.dense_data_record.thriftjava.DenseCompactDataRecord
import com.twitter.util.Future
import java.lang.{Long => JLong}
import java.util.{Map => JMap}

abstract case class BaseEdgeAggregateFeature(
  name: String,
  aggregateGroups: Set[AggregateGroup],
  aggregateType: AggregateType,
  extractMapFn: AggregateFeaturesToDecodeWithMetadata => JMap[JLong, DenseCompactDataRecord],
  adapter: IRecordOneToOneAdapter[Seq[DataRecord]],
  getSecondaryKeysFn: CandidateWithFeatures[TweetCandidate] => Seq[Long])
    extends DataRecordInAFeature[PipelineQuery]
    with FeatureWithDefaultOnFailure[PipelineQuery, DataRecord] {
  override def defaultValue: DataRecord = new DataRecord

  private val rootFeatureInfo = new AggregateFeatureInfo(aggregateGroups, aggregateType)
  val featureContext: FeatureContext = rootFeatureInfo.featureContext
  val rootFeature: BaseAggregateRootFeature = rootFeatureInfo.feature

  override def hashCode(): Int = name.hashCode
}

trait BaseEdgeAggregateFeatureHydrator
    extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate]
    with WithDefaultFeatureMap {

  def aggregateFeatures: Set[BaseEdgeAggregateFeature]

  override def features = aggregateFeatures.asInstanceOf[Set[Feature[_, _]]]

  private val batchSize = 32

  override lazy val defaultFeatureMap: FeatureMap = {
    val featureMapBuilder = new FeatureMapBuilder()
    aggregateFeatures.foreach(feature => featureMapBuilder.add(feature, feature.defaultValue))
    featureMapBuilder.build()
  }

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = OffloadFuturePools.offloadFuture {
    val featuresSeq = aggregateFeatures.toSeq
    val dataRecordsFuture = featuresSeq.map { feature =>
      hydrateAggregateFeature(query, candidates, feature)
    }

    Future.collect(dataRecordsFuture).map { dataRecordsPerFeature =>
      val dataRecordsPerCandidate = dataRecordsPerFeature.transpose
      dataRecordsPerCandidate.map { dataRecords =>
        assert(featuresSeq.size == dataRecords.size)
        val builder = FeatureMapBuilder(sizeHint = featuresSeq.size)
        featuresSeq.zip(dataRecords).map {
          case (feature, dataRecord) => builder.add(feature, dataRecord)
        }
        builder.build()
      }
    }
  }

  private def getDataRecord(
    ids: Seq[Long],
    decoded: Map[Long, DataRecord],
    feature: BaseEdgeAggregateFeature
  ) = {
    val dataRecords = ids.flatMap(decoded.get)
    feature.adapter.adaptToDataRecord(dataRecords)
  }

  private def hydrateAggregateFeature(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]],
    feature: BaseEdgeAggregateFeature
  ): Future[Seq[DataRecord]] = {
    val rootFeature = feature.rootFeature
    val extractMapFn = feature.extractMapFn
    val featureContext = feature.featureContext
    val secondaryIds: Seq[Seq[Long]] = candidates.map(feature.getSecondaryKeysFn)

    val featuresToDecodeWithMetadata = query.features
      .flatMap(_.getOrElse(rootFeature, None))
      .getOrElse(AggregateFeaturesToDecodeWithMetadata.empty)

    // Decode the DenseCompactDataRecords into DataRecords for each required secondary id.
    val decoded: Map[Long, DataRecord] = Utils.selectAndTransform(
      secondaryIds.flatten.distinct,
      featuresToDecodeWithMetadata.toDataRecord,
      extractMapFn(featuresToDecodeWithMetadata)
    )

    // Remove unnecessary features in-place. This is safe because the underlying DataRecords
    // are unique and have just been generated in the previous step.
    decoded.values.foreach(Utils.filterDataRecord(_, featureContext))

    // Put features into the FeatureMapBuilders

    OffloadFuturePools.offloadBatchElementToElement(
      secondaryIds,
      getDataRecord(_, decoded, feature),
      batchSize)
  }
}
