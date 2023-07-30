package com.X.timelines.data_processing.ml_util.aggregation_framework

import com.X.dal.personal_data.thriftscala.PersonalDataType
import com.X.ml.api.DataRecord
import com.X.ml.api.Feature
import com.X.scalding_internal.multiformat.format.keyval.KeyValInjection
import com.X.scalding_internal.multiformat.format.keyval.KeyValInjection.Batched
import com.X.scalding_internal.multiformat.format.keyval.KeyValInjection.JavaCompactThrift
import com.X.scalding_internal.multiformat.format.keyval.KeyValInjection.genericInjection
import com.X.summingbird.batch.BatchID
import scala.collection.JavaConverters._

object OfflineAggregateInjections {
  val offlineDataRecordAggregateInjection: KeyValInjection[AggregationKey, (BatchID, DataRecord)] =
    KeyValInjection(
      genericInjection(AggregationKeyInjection),
      Batched(JavaCompactThrift[DataRecord])
    )

  private[aggregation_framework] def getPdts[T](
    aggregateGroups: Iterable[T],
    featureExtractor: T => Iterable[Feature[_]]
  ): Option[Set[PersonalDataType]] = {
    val pdts: Set[PersonalDataType] = for {
      group <- aggregateGroups.toSet[T]
      feature <- featureExtractor(group)
      pdtSet <- feature.getPersonalDataTypes.asSet().asScala
      javaPdt <- pdtSet.asScala
      scalaPdt <- PersonalDataType.get(javaPdt.getValue)
    } yield {
      scalaPdt
    }
    if (pdts.nonEmpty) Some(pdts) else None
  }

  def getInjection(
    aggregateGroups: Set[TypedAggregateGroup[_]]
  ): KeyValInjection[AggregationKey, (BatchID, DataRecord)] = {
    val keyPdts = getPdts[TypedAggregateGroup[_]](aggregateGroups, _.allOutputKeys)
    val valuePdts = getPdts[TypedAggregateGroup[_]](aggregateGroups, _.allOutputFeatures)
    KeyValInjection(
      genericInjection(AggregationKeyInjection, keyPdts),
      genericInjection(Batched(JavaCompactThrift[DataRecord]), valuePdts)
    )
  }
}
