package com.twitter.visibility.builder.tweets

import com.twitter.spam.rtf.thriftscala.SafetyLabelType
import com.twitter.spam.rtf.thriftscala.SafetyLabelType.ExperimentalNudge
import com.twitter.spam.rtf.thriftscala.SafetyLabelType.SemanticCoreMisinformation
import com.twitter.spam.rtf.thriftscala.SafetyLabelType.UnsafeUrl
import com.twitter.visibility.common.LocalizedNudgeSource
import com.twitter.visibility.common.actions.TweetVisibilityNudgeReason
import com.twitter.visibility.common.actions.TweetVisibilityNudgeReason.ExperimentalNudgeSafetyLabelReason
import com.twitter.visibility.common.actions.TweetVisibilityNudgeReason.SemanticCoreMisinformationLabelReason
import com.twitter.visibility.common.actions.TweetVisibilityNudgeReason.UnsafeURLLabelReason
import com.twitter.visibility.rules.LocalizedNudge

class TweetVisibilityNudgeSourceWrapper(localizedNudgeSource: LocalizedNudgeSource) {

  def getLocalizedNudge(
    reason: TweetVisibilityNudgeReason,
    languageCode: String,
    countryCode: Option[String]
  ): Option[LocalizedNudge] =
    reason match {
      case ExperimentalNudgeSafetyLabelReason =>
        fetchNudge(ExperimentalNudge, languageCode, countryCode)
      case SemanticCoreMisinformationLabelReason =>
        fetchNudge(SemanticCoreMisinformation, languageCode, countryCode)
      case UnsafeURLLabelReason =>
        fetchNudge(UnsafeUrl, languageCode, countryCode)
    }

  private def fetchNudge(
    safetyLabel: SafetyLabelType,
    languageCode: String,
    countryCode: Option[String]
  ): Option[LocalizedNudge] = {
    localizedNudgeSource
      .fetch(safetyLabel, languageCode, countryCode)
      .map(LocalizedNudge.fromStratoThrift)
  }
}
