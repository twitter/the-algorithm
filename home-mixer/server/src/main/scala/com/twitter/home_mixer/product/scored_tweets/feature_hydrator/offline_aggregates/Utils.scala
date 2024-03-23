package com.ExTwitter.home_mixer.product.scored_tweets.feature_hydrator.offline_aggregates

import com.ExTwitter.ml.api.DataRecord
import com.ExTwitter.ml.api.FeatureContext
import com.ExTwitter.ml.api.RichDataRecord
import com.ExTwitter.timelines.suggests.common.dense_data_record.thriftjava.DenseCompactDataRecord

private[offline_aggregates] object Utils {

  /**
   * Selects only those values in map that correspond to the keys in ids and apply the provided
   * transform to the selected values. This is a convenience method for use by Timelines Aggregation
   * Framework based features.
   *
   * @param idsToSelect The set of ids to extract values for.
   * @param transform A transform to apply to the selected values.
   * @param map Map[Long, DenseCompactDataRecord]
   */
  def selectAndTransform(
    idsToSelect: Seq[Long],
    transform: DenseCompactDataRecord => DataRecord,
    map: java.util.Map[java.lang.Long, DenseCompactDataRecord],
  ): Map[Long, DataRecord] = {
    val filtered: Seq[(Long, DataRecord)] =
      for {
        id <- idsToSelect if map.containsKey(id)
      } yield {
        id -> transform(map.get(id))
      }
    filtered.toMap
  }

  def filterDataRecord(dr: DataRecord, featureContext: FeatureContext): Unit = {
    new RichDataRecord(dr, featureContext).dropUnknownFeatures()
  }
}
