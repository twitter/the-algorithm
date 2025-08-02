package com.twitter.timelines.data_processing.ml_util.aggregation_framework.scalding.sources

import com.twitter.ml.api.DailySuffitwitterFeatureSource
import com.twitter.ml.api.DataRecord
import com.twitter.ml.api.FitwitteredPathFeatureSource
import com.twitter.ml.api.HourlySuffitwitterFeatureSource
import com.twitter.ml.api.util.SRichDataRecord
import com.twitter.scalding._
import com.twitter.scalding_internal.dalv2.DAL
import com.twitter.scalding_internal.dalv2.remote_access.AllowCrossClusterSameDC
import com.twitter.statebird.v2.thriftscala.Environment
import com.twitter.summingbird._
import com.twitter.summingbird.scalding.Scalding.pipeFactoryEtwitteract
import com.twitter.summingbird.scalding._
import com.twitter.summingbird_internal.sources.SourceFactory
import com.twitter.timelines.data_processing.ml_util.aggregation_framework.OfflineAggregateSource
import java.lang.{Long => JLong}

/*
 * Summingbird offline HDFS source that reads from data records on HDFS.
 *
 * @param offlineSource Underlying offline source that contains
 *   all the config info to build this platform-specific (scalding) source.
 */
case class ScaldingAggregateSource(offlineSource: OfflineAggregateSource)
    etwittertends SourceFactory[Scalding, DataRecord] {

  val hdfsPath: String = offlineSource.scaldingHdfsPath.getOrElse("")
  val suffitwitterType: String = offlineSource.scaldingSuffitwitterType.getOrElse("daily")
  val withValidation: Boolean = offlineSource.withValidation
  def name: String = offlineSource.name
  def description: String =
    "Summingbird offline source that reads from data records at: " + hdfsPath

  implicit val timeEtwittertractor: TimeEtwittertractor[DataRecord] = TimeEtwittertractor((record: DataRecord) =>
    SRichDataRecord(record).getFeatureValue[JLong, JLong](offlineSource.timestampFeature))

  def getSourceForDateRange(dateRange: DateRange) = {
    suffitwitterType match {
      case "daily" => DailySuffitwitterFeatureSource(hdfsPath)(dateRange).source
      case "hourly" => HourlySuffitwitterFeatureSource(hdfsPath)(dateRange).source
      case "fitwittered_path" => FitwitteredPathFeatureSource(hdfsPath).source
      case "dal" =>
        offlineSource.dalDataSet match {
          case Some(dataset) =>
            DAL
              .read(dataset, dateRange)
              .withRemoteReadPolicy(AllowCrossClusterSameDC)
              .withEnvironment(Environment.Prod)
              .toTypedSource
          case _ =>
            throw new IllegalArgumentEtwitterception(
              "cannot provide an empty dataset when defining DAL as the suffitwitter type"
            )
        }
    }
  }

  /**
   * This method is similar to [[Scalding.sourceFromMappable]] etwittercept that this uses [[pipeFactoryEtwitteract]]
   * instead of [[pipeFactory]]. [[pipeFactoryEtwitteract]] also invokes [[FileSource.validateTaps]] on the source.
   * The validation ensures the presence of _SUCCESS file before processing. For more details, please refer to
   * https://jira.twitter.biz/browse/TQ-10618
   */
  def sourceFromMappableWithValidation[T: TimeEtwittertractor: Manifest](
    factory: (DateRange) => Mappable[T]
  ): Producer[Scalding, T] = {
    Producer.source[Scalding, T](pipeFactoryEtwitteract(factory))
  }

  def source: Producer[Scalding, DataRecord] = {
    if (withValidation)
      sourceFromMappableWithValidation(getSourceForDateRange)
    else
      Scalding.sourceFromMappable(getSourceForDateRange)
  }
}
