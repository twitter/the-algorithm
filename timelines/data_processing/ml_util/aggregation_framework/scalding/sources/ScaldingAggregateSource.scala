package com.X.timelines.data_processing.ml_util.aggregation_framework.scalding.sources

import com.X.ml.api.DailySuffixFeatureSource
import com.X.ml.api.DataRecord
import com.X.ml.api.FixedPathFeatureSource
import com.X.ml.api.HourlySuffixFeatureSource
import com.X.ml.api.util.SRichDataRecord
import com.X.scalding._
import com.X.scalding_internal.dalv2.DAL
import com.X.scalding_internal.dalv2.remote_access.AllowCrossClusterSameDC
import com.X.statebird.v2.thriftscala.Environment
import com.X.summingbird._
import com.X.summingbird.scalding.Scalding.pipeFactoryExact
import com.X.summingbird.scalding._
import com.X.summingbird_internal.sources.SourceFactory
import com.X.timelines.data_processing.ml_util.aggregation_framework.OfflineAggregateSource
import java.lang.{Long => JLong}

/*
 * Summingbird offline HDFS source that reads from data records on HDFS.
 *
 * @param offlineSource Underlying offline source that contains
 *   all the config info to build this platform-specific (scalding) source.
 */
case class ScaldingAggregateSource(offlineSource: OfflineAggregateSource)
    extends SourceFactory[Scalding, DataRecord] {

  val hdfsPath: String = offlineSource.scaldingHdfsPath.getOrElse("")
  val suffixType: String = offlineSource.scaldingSuffixType.getOrElse("daily")
  val withValidation: Boolean = offlineSource.withValidation
  def name: String = offlineSource.name
  def description: String =
    "Summingbird offline source that reads from data records at: " + hdfsPath

  implicit val timeExtractor: TimeExtractor[DataRecord] = TimeExtractor((record: DataRecord) =>
    SRichDataRecord(record).getFeatureValue[JLong, JLong](offlineSource.timestampFeature))

  def getSourceForDateRange(dateRange: DateRange) = {
    suffixType match {
      case "daily" => DailySuffixFeatureSource(hdfsPath)(dateRange).source
      case "hourly" => HourlySuffixFeatureSource(hdfsPath)(dateRange).source
      case "fixed_path" => FixedPathFeatureSource(hdfsPath).source
      case "dal" =>
        offlineSource.dalDataSet match {
          case Some(dataset) =>
            DAL
              .read(dataset, dateRange)
              .withRemoteReadPolicy(AllowCrossClusterSameDC)
              .withEnvironment(Environment.Prod)
              .toTypedSource
          case _ =>
            throw new IllegalArgumentException(
              "cannot provide an empty dataset when defining DAL as the suffix type"
            )
        }
    }
  }

  /**
   * This method is similar to [[Scalding.sourceFromMappable]] except that this uses [[pipeFactoryExact]]
   * instead of [[pipeFactory]]. [[pipeFactoryExact]] also invokes [[FileSource.validateTaps]] on the source.
   * The validation ensures the presence of _SUCCESS file before processing. For more details, please refer to
   * https://jira.X.biz/browse/TQ-10618
   */
  def sourceFromMappableWithValidation[T: TimeExtractor: Manifest](
    factory: (DateRange) => Mappable[T]
  ): Producer[Scalding, T] = {
    Producer.source[Scalding, T](pipeFactoryExact(factory))
  }

  def source: Producer[Scalding, DataRecord] = {
    if (withValidation)
      sourceFromMappableWithValidation(getSourceForDateRange)
    else
      Scalding.sourceFromMappable(getSourceForDateRange)
  }
}
