package com.twitter.home_mixer.functional_component.feature_hydrator.adapters.offline_aggregates

import com.twitter.ml.api.DataRecord
import com.twitter.ml.api.IRecordOneToOneAdapter

object PassThroughAdapter extends IRecordOneToOneAdapter[Seq[DataRecord]] {
  override def adaptToDataRecord(record: Seq[DataRecord]): DataRecord =
    record.headOption.getOrElse(new DataRecord)

  // This is not necessary and should not be used.
  override def getFeatureContext = ???
}
