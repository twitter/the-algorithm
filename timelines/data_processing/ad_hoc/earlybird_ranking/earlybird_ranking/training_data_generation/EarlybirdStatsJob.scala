package com.twitter.timelines.data_processing.ad_hoc.earlybird_ranking.training_data_generation

import com.twitter.ml.api.analytics.DataSetAnalyticsPlugin
import com.twitter.ml.api.matcher.FeatureMatcher
import com.twitter.ml.api.util.FDsl
import com.twitter.ml.api.DailySuffitwitterFeatureSource
import com.twitter.ml.api.DataRecord
import com.twitter.ml.api.DataSetPipe
import com.twitter.ml.api.FeatureStats
import com.twitter.ml.api.IMatcher
import com.twitter.scalding.typed.TypedPipe
import com.twitter.scalding.Etwitterecution
import com.twitter.scalding.TypedJson
import com.twitter.scalding_internal.job.TwitterEtwitterecutionApp
import com.twitter.timelines.data_processing.util.etwitterecution.UTCDateRangeFromArgs
import com.twitter.timelines.data_processing.ad_hoc.earlybird_ranking.common.EarlybirdTrainingConfiguration
import com.twitter.timelines.data_processing.ad_hoc.earlybird_ranking.common.EarlybirdTrainingRecapConfiguration
import com.twitter.timelines.prediction.features.recap.RecapFeatures
import scala.collection.JavaConverters._

/**
 * Compute counts and fractions for all labels in a Recap data source.
 *
 * Arguments:
 * --input   recap data source (containing all labels)
 * --output  path to output JSON file containing stats
 */
object EarlybirdStatsJob etwittertends TwitterEtwitterecutionApp with UTCDateRangeFromArgs {

  import DataSetAnalyticsPlugin._
  import FDsl._
  import RecapFeatures.IS_EARLYBIRD_UNIFIED_ENGAGEMENT

  lazy val constants: EarlybirdTrainingConfiguration = new EarlybirdTrainingRecapConfiguration
  private[this] def addGlobalEngagementLabel(record: DataRecord) = {
    if (constants.LabelInfos.etwitterists { labelInfo => record.hasFeature(labelInfo.feature) }) {
      record.setFeatureValue(IS_EARLYBIRD_UNIFIED_ENGAGEMENT, true)
    }
    record
  }

  private[this] def labelFeatureMatcher: IMatcher = {
    val allLabels =
      (IS_EARLYBIRD_UNIFIED_ENGAGEMENT :: constants.LabelInfos.map(_.feature)).map(_.getFeatureName)
    FeatureMatcher.names(allLabels.asJava)
  }

  private[this] def computeStats(data: DataSetPipe): TypedPipe[FeatureStats] = {
    data
      .viaRecords { _.map(addGlobalEngagementLabel) }
      .project(labelFeatureMatcher)
      .collectFeatureStats()
  }

  override def job: Etwitterecution[Unit] = {
    for {
      args <- Etwitterecution.getArgs
      dateRange <- dateRangeEtwitter
      data = DailySuffitwitterFeatureSource(args("input"))(dateRange).read
      _ <- computeStats(data).writeEtwitterecution(TypedJson(args("output")))
    } yield ()
  }
}
