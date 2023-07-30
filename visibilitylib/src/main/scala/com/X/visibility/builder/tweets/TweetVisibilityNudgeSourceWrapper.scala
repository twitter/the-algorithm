package com.X.visibility.builder.tweets

import com.X.spam.rtf.thriftscala.SafetyLabelType
import com.X.spam.rtf.thriftscala.SafetyLabelType.ExperimentalNudge
import com.X.spam.rtf.thriftscala.SafetyLabelType.SemanticCoreMisinformation
import com.X.spam.rtf.thriftscala.SafetyLabelType.UnsafeUrl
import com.X.visibility.common.LocalizedNudgeSource
import com.X.visibility.common.actions.TweetVisibilityNudgeReason
import com.X.visibility.common.actions.TweetVisibilityNudgeReason.ExperimentalNudgeSafetyLabelReason
import com.X.visibility.common.actions.TweetVisibilityNudgeReason.SemanticCoreMisinformationLabelReason
import com.X.visibility.common.actions.TweetVisibilityNudgeReason.UnsafeURLLabelReason
import com.X.visibility.rules.LocalizedNudge

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
