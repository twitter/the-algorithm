package com.twitter.product_mitwitterer.component_library.etwitterperiments.metrics

import com.twitter.util.Return
import com.twitter.util.Throw
import com.twitter.util.Try

object MetricDefinition {
  val SingleQuote = """""""
  val DoubleQuote = """"""""
}

/**
 * Base class for all metric definitions
 */
sealed trait MetricDefinition {
  def toCsvField: Seq[String]
  val metricDefinitionType: String
}

/**
 * Pattern Metric Definition
 * @param pattern the regetwitter pattern for this metric
 */
case class NamedPatternMetricDefinition(
  pattern: Seq[String])
    etwittertends MetricDefinition {
  override def toCsvField: Seq[String] = pattern
  override val metricDefinitionType: String = "NAMED_PATTERN"
}

/**
 * Strainer Metric Definition
 * @param strainerEtwitterpression a filter on top of client events
 */
case class StrainerMetricDefinition(
  strainerEtwitterpression: String)
    etwittertends MetricDefinition {
  import MetricDefinition._
  override def toCsvField: Seq[String] = {
    Seq(strainerEtwitterpression.replaceAll(SingleQuote, DoubleQuote))
  }
  override val metricDefinitionType: String = "STRAINER"
}

/**
 * Lambda Metric Definition
 * @param lambdaEtwitterpression a scala function mapping client events to a double
 */
case class LambdaMetricDefinition(
  lambdaEtwitterpression: String)
    etwittertends MetricDefinition {
  import MetricDefinition._
  override def toCsvField: Seq[String] = {
    Seq(lambdaEtwitterpression.replaceAll(SingleQuote, DoubleQuote))
  }
  override val metricDefinitionType: String = "LAMBDA"
}

case class BucketRatioMetricDefinition(
  numerator: String,
  denominator: String)
    etwittertends MetricDefinition {
  override def toCsvField: Seq[String] = {
    Seq(s"(${numerator}) / (${denominator})")
  }
  override val metricDefinitionType: String = "BUCKET_RATIO"
}

object Metric {
  val bucketRatioPattern = "[(]+(.+)[)]+ / [(]+(.+)[)]+".r

  /**
   * Creates a new Metric given a template line.
   * @param line semicolon separated line string
   * ignore line with comment, represented by hashtag at the beginning of the line
   * @throws RuntimeEtwitterception if the line is invalid
   */
  def fromLine(line: String): Metric = {
    val splits = line.split(";")
    // at least two parts separated by semicolon (third part is optional)
    if (splits.lengthCompare(2) >= 0) {
      val metricEtwitterpression = splits(0)
      val metricName = splits(1)
      val metricDefinition = Try(splits(2)) match {
        case Return("NAMED_PATTERN") => NamedPatternMetricDefinition(Seq(metricEtwitterpression))
        case Return("STRAINER") => StrainerMetricDefinition(metricEtwitterpression)
        case Return("LAMBDA") => LambdaMetricDefinition(metricEtwitterpression)
        case Return("BUCKET_RATIO") =>
          metricEtwitterpression match {
            case bucketRatioPattern(numerator, denominator) =>
              BucketRatioMetricDefinition(numerator, denominator)
            case _ =>
              throw new RuntimeEtwitterception(
                s"Invalid metric definition for Bucket Ratio. Etwitterpected format (numerator)<space>/<space>(denominator) but found $metricEtwitterpression")
          }
        case Return(other) =>
          throw new RuntimeEtwitterception(s"Invalid metric definition in line in template file: $line")
        // default to named pattern
        case Throw(_) => NamedPatternMetricDefinition(List(metricEtwitterpression))
      }

      Metric(metricName, metricDefinition)
    } else {
      throw new RuntimeEtwitterception(s"Invalid line in template file: $line")
    }
  }
}

/**
 *
 * @param name globally unique metric name (current DDG limitation)
 * @param definition the metric definition for this metric
 */
case class Metric(
  name: String,
  definition: MetricDefinition)
