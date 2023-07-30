package com.X.timelines.data_processing.ad_hoc.earlybird_ranking.training_data_generation

import com.X.ml.api.analytics.DataSetAnalyticsPlugin
import com.X.ml.api.matcher.FeatureMatcher
import com.X.ml.api.util.FDsl
import com.X.ml.api.DailySuffixFeatureSource
import com.X.ml.api.DataRecord
import com.X.ml.api.DataSetPipe
import com.X.ml.api.FeatureStats
import com.X.ml.api.IMatcher
import com.X.scalding.typed.TypedPipe
import com.X.scalding.Execution
import com.X.scalding.TypedJson
import com.X.scalding_internal.job.XExecutionApp
import com.X.timelines.data_processing.util.execution.UTCDateRangeFromArgs
import com.X.timelines.data_processing.ad_hoc.earlybird_ranking.common.EarlybirdTrainingConfiguration
import com.X.timelines.data_processing.ad_hoc.earlybird_ranking.common.EarlybirdTrainingRecapConfiguration
import com.X.timelines.prediction.features.recap.RecapFeatures
import scala.collection.JavaConverters._

/**
 * Compute counts and fractions for all labels in a Recap data source.
 *
 * Arguments:
 * --input   recap data source (containing all labels)
 * --output  path to output JSON file containing stats
 */
object EarlybirdStatsJob extends XExecutionApp with UTCDateRangeFromArgs {

  import DataSetAnalyticsPlugin._
  import FDsl._
  import RecapFeatures.IS_EARLYBIRD_UNIFIED_ENGAGEMENT

  lazy val constants: EarlybirdTrainingConfiguration = new EarlybirdTrainingRecapConfiguration
  private[this] def addGlobalEngagementLabel(record: DataRecord) = {
    if (constants.LabelInfos.exists { labelInfo => record.hasFeature(labelInfo.feature) }) {
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

  override def job: Execution[Unit] = {
    for {
      args <- Execution.getArgs
      dateRange <- dateRangeEx
      data = DailySuffixFeatureSource(args("input"))(dateRange).read
      _ <- computeStats(data).writeExecution(TypedJson(args("output")))
    } yield ()
  }
}
