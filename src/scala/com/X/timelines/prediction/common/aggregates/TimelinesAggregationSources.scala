package com.X.timelines.prediction.common.aggregates

import com.X.ml.api.constant.SharedFeatures.TIMESTAMP
import com.X.timelines.data_processing.ml_util.aggregation_framework.OfflineAggregateSource
import com.X.timelines.prediction.features.p_home_latest.HomeLatestUserAggregatesFeatures
import timelines.data_processing.ad_hoc.recap.data_record_preparation.RecapDataRecordsAggMinimalJavaDataset

/**
 * Any update here should be in sync with [[TimelinesFeatureGroups]] and [[AggMinimalDataRecordGeneratorJob]].
 */
object TimelinesAggregationSources {

  /**
   * This is the recap data records after post-processing in [[GenerateRecapAggMinimalDataRecordsJob]]
   */
  val timelinesDailyRecapMinimalSource = OfflineAggregateSource(
    name = "timelines_daily_recap",
    timestampFeature = TIMESTAMP,
    dalDataSet = Some(RecapDataRecordsAggMinimalJavaDataset),
    scaldingSuffixType = Some("dal"),
    withValidation = true
  )
  val timelinesDailyXWideSource = OfflineAggregateSource(
    name = "timelines_daily_X_wide",
    timestampFeature = TIMESTAMP,
    scaldingHdfsPath = Some("/user/timelines/processed/suggests/recap/X_wide_data_records"),
    scaldingSuffixType = Some("daily"),
    withValidation = true
  )

  val timelinesDailyListTimelineSource = OfflineAggregateSource(
    name = "timelines_daily_list_timeline",
    timestampFeature = TIMESTAMP,
    scaldingHdfsPath = Some("/user/timelines/processed/suggests/recap/all_features/list"),
    scaldingSuffixType = Some("hourly"),
    withValidation = true
  )

  val timelinesDailyHomeLatestSource = OfflineAggregateSource(
    name = "timelines_daily_home_latest",
    timestampFeature = HomeLatestUserAggregatesFeatures.AGGREGATE_TIMESTAMP_MS,
    scaldingHdfsPath = Some("/user/timelines/processed/p_home_latest/user_aggregates"),
    scaldingSuffixType = Some("daily")
  )
}
