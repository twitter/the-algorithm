package com.twitter.timelines.data_processing.ml_util.aggregation_framework.metrics

import com.twitter.ml.api._
import com.twitter.ml.api.util.SRichDataRecord
import com.twitter.util.Time
import java.lang.{Double => JDouble}
import java.lang.{Long => JLong}

case class TypedSumMetric(
) extends TypedSumLikeMetric[JDouble] {
  import AggregationMetricCommon._

  override val operatorName = "sum"

  /*
   * Transform feature -> its value in the given record,
   * or 0 when feature = None (sum has no meaning in this case)
   */
  override def getIncrementValue(
    record: DataRecord,
    feature: Option[Feature[JDouble]],
    timestampFeature: Feature[JLong]
  ): TimedValue[Double] = feature match {
    case Some(f) => {
      TimedValue[Double](
        value = Option(SRichDataRecord(record).getFeatureValue(f)).map(_.toDouble).getOrElse(0.0),
        timestamp = Time.fromMilliseconds(getTimestamp(record, timestampFeature))
      )
    }

    case None =>
      TimedValue[Double](
        value = 0.0,
        timestamp = Time.fromMilliseconds(getTimestamp(record, timestampFeature))
      )
  }
}

/**
 * Syntactic sugar for the sum metric that works with continuous features.
 * See EasyMetric.scala for more details on why this is useful.
 */
object SumMetric extends EasyMetric {
  override def forFeatureType[T](
    featureType: FeatureType
  ): Option[AggregationMetric[T, _]] =
    featureType match {
      case FeatureType.CONTINUOUS =>
        Some(TypedSumMetric().asInstanceOf[AggregationMetric[T, Double]])
      case _ => None
    }
}
