package com.X.home_mixer.product.scored_tweets.feature_hydrator.adapters.offline_aggregates

import com.X.ml.api.DataRecord
import com.X.ml.api.FeatureContext
import com.X.ml.api.RichDataRecord
import com.X.timelines.data_processing.ml_util.aggregation_framework.conversion.CombineCountsPolicy
import com.X.timelines.prediction.common.adapters.TimelinesIRecordAdapter

class SparseAggregatesToDenseAdapter(policy: CombineCountsPolicy)
    extends TimelinesIRecordAdapter[Seq[DataRecord]] {

  override def setFeatures(input: Seq[DataRecord], mutableDataRecord: RichDataRecord): Unit =
    policy.defaultMergeRecord(mutableDataRecord.getRecord, input.toList)

  override val getFeatureContext: FeatureContext =
    new FeatureContext(policy.outputFeaturesPostMerge.toSeq: _*)
}
