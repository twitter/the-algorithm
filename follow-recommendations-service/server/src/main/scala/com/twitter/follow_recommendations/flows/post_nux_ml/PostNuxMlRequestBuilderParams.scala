package com.twitter.follow_recommendations.flows.post_nux_ml

import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.util.Duration
import com.twitter.conversions.DurationOps._
import com.twitter.timelines.configapi.DurationConversion
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.HasDurationConversion

object PostNuxMlRequestBuilderParams {
  case object TopicIdFetchBudget
      extends FSBoundedParam[Duration](
        name = "post_nux_ml_request_builder_topic_id_fetch_budget_millis",
        default = 200.millisecond,
        min = 80.millisecond,
        max = 400.millisecond)
      with HasDurationConversion {
    override val durationConversion: DurationConversion = DurationConversion.FromMillis
  }

  case object DismissedIdScanBudget
      extends FSBoundedParam[Duration](
        name = "post_nux_ml_request_builder_dismissed_id_scan_budget_millis",
        default = 200.millisecond,
        min = 80.millisecond,
        max = 400.millisecond)
      with HasDurationConversion {
    override val durationConversion: DurationConversion = DurationConversion.FromMillis
  }

  case object WTFImpressionsScanBudget
      extends FSBoundedParam[Duration](
        name = "post_nux_ml_request_builder_wtf_impressions_scan_budget_millis",
        default = 200.millisecond,
        min = 80.millisecond,
        max = 400.millisecond)
      with HasDurationConversion {
    override val durationConversion: DurationConversion = DurationConversion.FromMillis
  }

  case object EnableInvalidRelationshipPredicate
      extends FSParam[Boolean](
        name = "post_nux_ml_request_builder_enable_invalid_relationship_predicate",
        false)
}
