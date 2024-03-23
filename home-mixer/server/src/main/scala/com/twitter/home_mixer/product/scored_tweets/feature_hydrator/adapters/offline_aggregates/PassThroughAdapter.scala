package com.ExTwitter.home_mixer.product.scored_tweets.feature_hydrator.adapters.offline_aggregates

import com.ExTwitter.ml.api.DataRecord
import com.ExTwitter.ml.api.IRecordOneToOneAdapter

object PassThroughAdapter extends IRecordOneToOneAdapter[Seq[DataRecord]] {
  override def adaptToDataRecord(record: Seq[DataRecord]): DataRecord =
    record.headOption.getOrElse(new DataRecord)

  // This is not necessary and should not be used.
  override def getFeatureContext = ???
}
