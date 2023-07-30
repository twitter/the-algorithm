package com.X.frigate.pushservice.predicate.ntab_caret_fatigue

import com.X.finagle.stats.StatsReceiver
import com.X.frigate.common.predicate.FatiguePredicate
import com.X.frigate.pushservice.model.PushTypes.PushCandidate
import com.X.hermit.predicate.NamedPredicate
import com.X.frigate.common.base.Candidate
import com.X.frigate.common.base.TargetInfo
import com.X.frigate.common.rec_types.RecTypes
import com.X.frigate.common.base.{RecommendationType => BaseRecommendationType}
import com.X.frigate.common.predicate.CandidateWithRecommendationTypeAndTargetInfoWithCaretFeedbackHistory
import com.X.frigate.common.predicate.FrigateHistoryFatiguePredicate.TimeSeries
import com.X.notificationservice.thriftscala.CaretFeedbackDetails
import com.X.frigate.pushservice.model.PushTypes.Target
import com.X.frigate.pushservice.predicate.CaretFeedbackHistoryFilter

object NtabCaretClickContFnFatiguePredicate {

  private val MagicRecsCategory = "MagicRecs"

  def ntabCaretClickContFnFatiguePredicates(
    filterHistory: TimeSeries => TimeSeries =
      FatiguePredicate.recTypesOnlyFilter(RecTypes.sharedNTabCaretFatigueTypes),
    filterCaretFeedbackHistory: Target => Seq[
      CaretFeedbackDetails
    ] => Seq[CaretFeedbackDetails] =
      CaretFeedbackHistoryFilter.caretFeedbackHistoryFilter(Seq(MagicRecsCategory)),
    filterInlineFeedbackHistory: Seq[FeedbackModel] => Seq[FeedbackModel] =
      NtabCaretClickFatigueUtils.feedbackModelFilterByCRT(RecTypes.sharedNTabCaretFatigueTypes),
    name: String = "NTabCaretClickFnCandidatePredicates"
  )(
    implicit globalStats: StatsReceiver
  ): NamedPredicate[PushCandidate] = {
    val scopedStats = globalStats.scope(name)
    CRTBasedNtabCaretClickFatiguePredicates
      .f1TriggeredCRTBasedNtabCaretClickFnFatiguePredicate[
        Candidate with BaseRecommendationType with TargetInfo[
          Target
        ]
      ](
        filterHistory = filterHistory,
        filterCaretFeedbackHistory = filterCaretFeedbackHistory,
        filterInlineFeedbackHistory = filterInlineFeedbackHistory
      )
      .applyOnlyToCandidateWithRecommendationTypeAndTargetWithCaretFeedbackHistory
      .withName("f1_triggered_fn_seelessoften_fatigue")
      .andThen(
        CRTBasedNtabCaretClickFatiguePredicates
          .nonF1TriggeredCRTBasedNtabCaretClickFnFatiguePredicate[
            Candidate with BaseRecommendationType with TargetInfo[
              Target
            ]
          ](
            filterHistory = filterHistory,
            filterCaretFeedbackHistory = filterCaretFeedbackHistory,
            filterInlineFeedbackHistory = filterInlineFeedbackHistory
          )
          .applyOnlyToCandidateWithRecommendationTypeAndTargetWithCaretFeedbackHistory)
      .withName("nonf1_triggered_fn_seelessoften_fatigue")
      .andThen(
        CRTBasedNtabCaretClickFatiguePredicates
          .tripHqTweetTriggeredCRTBasedNtabCaretClickFnFatiguePredicate[
            Candidate with BaseRecommendationType with TargetInfo[
              Target
            ]
          ](
            filterHistory = filterHistory,
            filterCaretFeedbackHistory = filterCaretFeedbackHistory,
            filterInlineFeedbackHistory = filterInlineFeedbackHistory
          )
          .applyOnlyToCandidateWithRecommendationTypeAndTargetWithCaretFeedbackHistory)
      .withName("trip_hq_tweet_triggered_fn_seelessoften_fatigue")
      .andThen(
        CRTBasedNtabCaretClickFatiguePredicates
          .genericCRTBasedNtabCaretClickFnFatiguePredicate[
            Candidate with BaseRecommendationType with TargetInfo[
              Target
            ]
          ](
            filterHistory = filterHistory,
            filterCaretFeedbackHistory = filterCaretFeedbackHistory,
            filterInlineFeedbackHistory = filterInlineFeedbackHistory)
          .applyOnlyToCandidateWithRecommendationTypeAndTargetWithCaretFeedbackHistory
          .withName("generic_fn_seelessoften_fatigue")
      )
  }
}
