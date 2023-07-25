package com.twitter.timelines.data_processing.ml_util.aggregation_framework.metrics

import com.twitter.ml.api._
import com.twitter.ml.api.util.SRichDataRecord
import com.twitter.ml.api.DataRecord
import com.twitter.ml.api.Feature
import com.twitter.ml.api.FeatureType
import com.twitter.timelines.data_processing.ml_util.aggregation_framework.metrics.AggregationMetricCommon.getTimestamp
import com.twitter.timelines.data_processing.ml_util.aggregation_framework.metrics.AggregationMetric
import com.twitter.timelines.data_processing.ml_util.aggregation_framework.metrics.EasyMetric
import com.twitter.util.Duration
import com.twitter.util.Time
import java.lang.{Double => JDouble}
import java.lang.{Long => JLong}
import java.lang.{Number => JNumber}

case class TypedLatestMetric[T <: JNumber](defaultValue: Double = 0.0)
    extends TimedValueAggregationMetric[T] {
  override val operatorName = "latest"

  override def plus(
    left: TimedValue[Double],
    right: TimedValue[Double],
    halfLife: Duration
  ): TimedValue[Double] = {
    assert(
      halfLife.toString == "Duration.Top",
      s"halfLife must be Duration.Top when using latest metric, but ${halfLife.toString} is used"
    )

    if (left.timestamp > right.timestamp) {
      left
    } else {
      right
    }
  }

  override def getIncrementValue(
    dataRecord: DataRecord,
    feature: Option[Feature[T]],
    timestampFeature: Feature[JLong]
  ): TimedValue[Double] = {
    val value = feature
      .flatMap(SRichDataRecord(dataRecord).getFeatureValueOpt(_))
      .map(_.doubleValue()).getOrElse(defaultValue)
    val timestamp = Time.fromMilliseconds(getTimestamp(dataRecord, timestampFeature))
    TimedValue[Double](value = value, timestamp = timestamp)
  }

  override def zero(timeOpt: Option[Long]): TimedValue[Double] =
    TimedValue[Double](
      value = 0.0,
      timestamp = Time.fromMilliseconds(0)
    )
}

object LatestMetric extends EasyMetric {
  override def forFeatureType[T](
    featureType: FeatureType
  ): Option[AggregationMetric[T, _]] = {
    featureType match {
      case FeatureType.CONTINUOUS =>
        Some(TypedLatestMetric[JDouble]().asInstanceOf[AggregationMetric[T, Double]])
      case FeatureType.DISCRETE =>
        Some(TypedLatestMetric[JLong]().asInstanceOf[AggregationMetric[T, Double]])
      case _ => None
    }
  }
}
