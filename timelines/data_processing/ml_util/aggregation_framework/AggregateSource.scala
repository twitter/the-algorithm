package com.twitter.timelines.data_processing.ml_util.aggregation_framework

import com.twitter.ml.api.Feature
import java.lang.{Long => JLong}

trait AggregateSource extends Serializable {
  def name: String
  def timestampFeature: Feature[JLong]
}
