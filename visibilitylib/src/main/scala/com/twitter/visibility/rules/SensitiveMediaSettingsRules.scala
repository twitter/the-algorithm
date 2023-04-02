package com.twitter.visibility.rules

import com.twitter.visibility.rules.Condition.ViewerHasViolentMediaSettingLevel
import com.twitter.visibility.rules.Condition.ViewerHasOtherSensitiveMediaSettingLevel
import com.twitter.visibility.rules.Condition.LoggedInViewer
import com.twitter.visibility.rules.Condition.LoggedOutViewer
import com.twitter.visibility.rules.Condition.And
import com.twitter.visibility.rules.Condition.Or
import com.twitter.visibility.rules.Condition.Not
import com.twitter.visibility.rules.Condition.NonAuthorViewer
import com.twitter.visibility.rules.Condition.TweetHasMedia
import com.twitter.visibility.models.TweetSafetyLabelType
import com.twitter.contenthealth.sensitivemediasettings.thriftscala.SensitiveMediaSettingsLevel

abstract class ViolentMediaTweetLabelDropRule(tweetSafetyLabelType: TweetSafetyLabelType)
    extends ConditionWithTweetLabelRule(
      Drop(Epitaph.ViolentMedia),
      And(LoggedInViewer, ViewerHasViolentMediaSettingLevel(SensitiveMediaSettingsLevel.Drop)),
      tweetSafetyLabelType
    )

abstract class ViolentMediaTweetLabelDropSettingLevelTombstoneRule(
  tweetSafetyLabelType: TweetSafetyLabelType)
    extends ConditionWithTweetLabelRule(
      Tombstone(Epitaph.ViolentMedia),
      And(
        LoggedInViewer,
        NonAuthorViewer,
        ViewerHasViolentMediaSettingLevel(SensitiveMediaSettingsLevel.Drop)),
      tweetSafetyLabelType
    )

abstract class OtherSensitiveMediaTweetLabelDropSettingLevelTombstoneRule(condition: Condition)
    extends RuleWithConstantAction(
      Tombstone(Epitaph.OtherSensitiveMedia),
      And(
        condition,
        And(
          TweetHasMedia,
          LoggedInViewer,
          NonAuthorViewer,
          ViewerHasOtherSensitiveMediaSettingLevel(SensitiveMediaSettingsLevel.Drop))
      )
    )

case object SensitiveMediaTweetDropRules {

  object ViolentMediaGoreAndViolenceHighPrecisionDropRule
      extends ViolentMediaTweetLabelDropRule(
        TweetSafetyLabelType.GoreAndViolenceHighPrecision
      )

  object ViolentMediaGoreAndViolenceReportedHeuristicsDropRule
      extends ViolentMediaTweetLabelDropRule(
        TweetSafetyLabelType.GoreAndViolenceReportedHeuristics
      )

}

case object SensitiveMediaTweetInterstitialRules {

  object ViolentMediaGoreAndViolenceHighPrecisionInterstitialRule
      extends ViolentMediaTweetLabelInterstitialRule(
        TweetSafetyLabelType.GoreAndViolenceHighPrecision
      )
      with DoesLogVerdict

  object ViolentMediaGoreAndViolenceReportedHeuristicsInterstitialRule
      extends ViolentMediaTweetLabelInterstitialRule(
        TweetSafetyLabelType.GoreAndViolenceReportedHeuristics
      )

}

case object SensitiveMediaTweetDropSettingLevelTombstoneRules {

  object ViolentMediaGoreAndViolenceHighPrecisionDropSettingLeveTombstoneRule
      extends ViolentMediaTweetLabelDropSettingLevelTombstoneRule(
        TweetSafetyLabelType.GoreAndViolenceHighPrecision
      )

  object ViolentMediaGoreAndViolenceReportedHeuristicsDropSettingLevelTombstoneRule
      extends ViolentMediaTweetLabelDropSettingLevelTombstoneRule(
        TweetSafetyLabelType.GoreAndViolenceReportedHeuristics
      )
}
