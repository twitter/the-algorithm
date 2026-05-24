package com.twitter.timelines.data_processing.ml_util.aggregation_framework.metrics

object ConversionUtils {
  def booleanToDouble(value: Boolean): Double = if (value) 1.0 else 0.0
}
