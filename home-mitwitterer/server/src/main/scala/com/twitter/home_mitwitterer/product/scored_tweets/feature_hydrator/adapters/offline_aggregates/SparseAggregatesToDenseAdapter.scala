package com.twitter.home_mitwitterer.product.scored_tweets.feature_hydrator.adapters.offline_aggregates

import com.twitter.ml.api.DataRecord
import com.twitter.ml.api.FeatureContetwittert
import com.twitter.ml.api.RichDataRecord
import com.twitter.timelines.data_processing.ml_util.aggregation_framework.conversion.CombineCountsPolicy
import com.twitter.timelines.prediction.common.adapters.TimelinesIRecordAdapter

class SparseAggregatesToDenseAdapter(policy: CombineCountsPolicy)
    etwittertends TimelinesIRecordAdapter[Seq[DataRecord]] {

  override def setFeatures(input: Seq[DataRecord], mutableDataRecord: RichDataRecord): Unit =
    policy.defaultMergeRecord(mutableDataRecord.getRecord, input.toList)

  override val getFeatureContetwittert: FeatureContetwittert =
    new FeatureContetwittert(policy.outputFeaturesPostMerge.toSeq: _*)
}
