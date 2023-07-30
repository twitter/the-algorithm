package com.X.visibility.interfaces.tweets.enrichments

import com.X.finagle.stats.StatsReceiver
import com.X.visibility.builder.VisibilityResult
import com.X.visibility.results.richtext.PublicInterestReasonToPlainText
import com.X.visibility.rules.Action
import com.X.visibility.rules.ComplianceTweetNoticePreEnrichment
import com.X.visibility.rules.PublicInterest
import com.X.visibility.rules.Reason

object ComplianceTweetNoticeEnrichment {
  val ComplianceTweetNoticeEnrichmentScope = "compliance_tweet_notice_enrichment"
  val ComplianceTweetNoticePreEnrichmentActionScope =
    "compliance_tweet_notice_pre_enrichment_action"

  val englishLanguageTag = "en"

  def apply(result: VisibilityResult, statsReceiver: StatsReceiver): VisibilityResult = {
    val scopedStatsReceiver = statsReceiver.scope(ComplianceTweetNoticeEnrichmentScope)

    val enrichedVerdict = enrichVerdict(result.verdict, scopedStatsReceiver)
    result.copy(verdict = enrichedVerdict)
  }

  private def enrichVerdict(
    verdict: Action,
    statsReceiver: StatsReceiver
  ): Action = {
    val preEnrichmentActionScope =
      statsReceiver.scope(ComplianceTweetNoticePreEnrichmentActionScope)

    verdict match {
      case complianceTweetNoticePreEnrichmentVerdict: ComplianceTweetNoticePreEnrichment =>
        preEnrichmentActionScope.counter("").incr()

        val verdictWithDetailsAndUrl = complianceTweetNoticePreEnrichmentVerdict.reason match {
          case Reason.Unspecified =>
            preEnrichmentActionScope.counter("reason_unspecified").incr()
            complianceTweetNoticePreEnrichmentVerdict

          case reason =>
            preEnrichmentActionScope.counter("reason_specified").incr()
            val safetyResultReason = PublicInterest.ReasonToSafetyResultReason(reason)
            val (details, url) =
              PublicInterestReasonToPlainText(safetyResultReason, englishLanguageTag)
            complianceTweetNoticePreEnrichmentVerdict.copy(
              details = Some(details),
              extendedDetailsUrl = Some(url))
        }
        verdictWithDetailsAndUrl.toComplianceTweetNotice()

      case _ => verdict
    }
  }
}
