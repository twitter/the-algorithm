package com.twitter.timelines.data_processing.ml_util.aggregation_framework

import com.twitter.ml.api.constant.SharedFeatures
import com.twitter.ml.api.Feature
import com.twitter.ml.api.FeatureType

/**
 * Convenience class to describe the stores that make up a particular type of aggregate.
 *
 * For example, as of 2018/07, user aggregates are generate by merging the individual
 * "user_aggregates", "rectweet_user_aggregates", and, "twitter_wide_user_aggregates".
 *
 * @param storeNames Name of the stores.
 * @param aggregateType Type of aggregate, usually differentiated by the aggregation key.
 * @param shouldHash Used at TimelineRankingAggregatesUtil.extractSecondary when extracting the
 *                   secondary key value.
 */
case class StoreConfig[T](
  storeNames: Set[String],
  aggregateType: AggregateType.Value,
  shouldHash: Boolean = false
)(
  implicit storeMerger: StoreMerger) {
  require(storeMerger.isValidToMerge(storeNames))

  private val representativeStore = storeNames.head

  val aggregationKeyIds: Set[Long] = storeMerger.getAggregateKeys(representativeStore)
  val aggregationKeyFeatures: Set[Feature[_]] =
    storeMerger.getAggregateKeyFeatures(representativeStore)
  val secondaryKeyFeatureOpt: Option[Feature[_]] = storeMerger.getSecondaryKey(representativeStore)
}

trait StoreMerger {
  def aggregationConfig: AggregationConfig

  def getAggregateKeyFeatures(storeName: String): Set[Feature[_]] =
    aggregationConfig.aggregatesToCompute
      .filter(_.outputStore.name == storeName)
      .flatMap(_.keysToAggregate)

  def getAggregateKeys(storeName: String): Set[Long] =
    TypedAggregateGroup.getKeyFeatureIds(getAggregateKeyFeatures(storeName))

  def getSecondaryKey(storeName: String): Option[Feature[_]] = {
    val keys = getAggregateKeyFeatures(storeName)
    require(keys.size <= 2, "Only singleton or binary aggregation keys are supported.")
    require(keys.contains(SharedFeatures.USER_ID), "USER_ID must be one of the aggregation keys.")
    keys
      .filterNot(_ == SharedFeatures.USER_ID)
      .headOption
      .map { possiblySparseKey =>
        if (possiblySparseKey.getFeatureType != FeatureType.SPARSE_BINARY) {
          possiblySparseKey
        } else {
          TypedAggregateGroup.sparseFeature(possiblySparseKey)
        }
      }
  }

  /**
   * Stores may only be merged if they have the same aggregation key.
   */
  def isValidToMerge(storeNames: Set[String]): Boolean = {
    val expectedKeyOpt = storeNames.headOption.map(getAggregateKeys)
    storeNames.forall(v => getAggregateKeys(v) == expectedKeyOpt.get)
  }
}
