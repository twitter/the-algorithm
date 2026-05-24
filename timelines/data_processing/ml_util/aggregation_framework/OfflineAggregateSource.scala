package com.twitter.timelines.data_processing.ml_util.aggregation_framework

import com.twitter.dal.client.dataset.TimePartitionedDALDataset
import com.twitter.ml.api.DataRecord
import com.twitter.ml.api.Feature
import java.lang.{Long => JLong}

case class OfflineAggregateSource(
  override val name: String,
  override val timestampFeature: Feature[JLong],
  scaldingHdfsPath: Option[String] = None,
  scaldingSuffixType: Option[String] = None,
  dalDataSet: Option[TimePartitionedDALDataset[DataRecord]] = None,
  withValidation: Boolean = true) // context: https://jira.twitter.biz/browse/TQ-10618
    extends AggregateSource {
  /*
   * Th help transition callers to use DAL.read, we check that either the HDFS
   * path is defined, or the dalDataset. Both options cannot be set at the same time.
   */
  assert(!(scaldingHdfsPath.isDefined && dalDataSet.isDefined))
}
