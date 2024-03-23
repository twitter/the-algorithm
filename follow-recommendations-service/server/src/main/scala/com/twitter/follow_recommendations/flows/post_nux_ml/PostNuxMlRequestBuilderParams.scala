package com.ExTwitter.follow_recommendations.flows.post_nux_ml

import com.ExTwitter.timelines.configapi.FSBoundedParam
import com.ExTwitter.util.Duration
import com.ExTwitter.conversions.DurationOps._
import com.ExTwitter.timelines.configapi.DurationConversion
import com.ExTwitter.timelines.configapi.FSParam
import com.ExTwitter.timelines.configapi.HasDurationConversion

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
