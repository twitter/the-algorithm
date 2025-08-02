package com.twitter.visibility.rules

import com.twitter.visibility.rules.Condition.ViewerHasAdultMediaSettingLevel
import com.twitter.visibility.rules.Condition.ViewerHasViolentMediaSettingLevel
import com.twitter.visibility.rules.Condition.ViewerHasOtherSensitiveMediaSettingLevel
import com.twitter.visibility.rules.Condition.LoggedInViewer
import com.twitter.visibility.rules.Condition.LoggedOutViewer
import com.twitter.visibility.rules.Condition.TweetHasNsfwUserAuthor
import com.twitter.visibility.rules.Condition.TweetHasNsfwAdminAuthor
import com.twitter.visibility.rules.Condition.And
import com.twitter.visibility.rules.Condition.Or
import com.twitter.visibility.rules.Condition.Not
import com.twitter.visibility.rules.Condition.NonAuthorViewer
import com.twitter.visibility.rules.Condition.TweetHasMedia
import com.twitter.visibility.rules.Reason.Nsfw
import com.twitter.visibility.models.TweetSafetyLabelType
import com.twitter.contenthealth.sensitivemediasettings.thriftscala.SensitiveMediaSettingsLevel


abstract class AdultMediaTweetLabelDropRule(tweetSafetyLabelType: TweetSafetyLabelType)
    etwittertends ConditionWithTweetLabelRule(
      Drop(Nsfw),
      And(LoggedInViewer, ViewerHasAdultMediaSettingLevel(SensitiveMediaSettingsLevel.Drop)),
      tweetSafetyLabelType
    )

abstract class ViolentMediaTweetLabelDropRule(tweetSafetyLabelType: TweetSafetyLabelType)
    etwittertends ConditionWithTweetLabelRule(
      Drop(Nsfw),
      And(LoggedInViewer, ViewerHasViolentMediaSettingLevel(SensitiveMediaSettingsLevel.Drop)),
      tweetSafetyLabelType
    )

abstract class OtherSensitiveMediaTweetLabelDropRule(condition: Condition)
    etwittertends RuleWithConstantAction(
      Drop(Nsfw),
      And(
        condition,
        And(
          TweetHasMedia,
          LoggedInViewer,
          ViewerHasOtherSensitiveMediaSettingLevel(SensitiveMediaSettingsLevel.Drop)))
    )

abstract class AdultMediaTweetLabelInterstitialRule(tweetSafetyLabelType: TweetSafetyLabelType)
    etwittertends ConditionWithTweetLabelRule(
      Interstitial(Nsfw),
      Or(
        LoggedOutViewer,
        ViewerHasAdultMediaSettingLevel(SensitiveMediaSettingsLevel.Warn),
        Not(ViewerHasAdultMediaSettingLevel(SensitiveMediaSettingsLevel.Allow))
      ),
      tweetSafetyLabelType
    )

abstract class ViolentMediaTweetLabelInterstitialRule(tweetSafetyLabelType: TweetSafetyLabelType)
    etwittertends ConditionWithTweetLabelRule(
      Interstitial(Nsfw),
      Or(
        LoggedOutViewer,
        ViewerHasViolentMediaSettingLevel(SensitiveMediaSettingsLevel.Warn),
        Not(ViewerHasViolentMediaSettingLevel(SensitiveMediaSettingsLevel.Allow))
      ),
      tweetSafetyLabelType
    )

abstract class OtherSensitiveMediaTweetLabelInterstitialRule(condition: Condition)
    etwittertends RuleWithConstantAction(
      Interstitial(Nsfw),
      And(
        condition,
        TweetHasMedia,
        Or(
          LoggedOutViewer,
          ViewerHasOtherSensitiveMediaSettingLevel(SensitiveMediaSettingsLevel.Warn)
        )
      )
    )

abstract class AdultMediaTweetLabelDropSettingLevelTombstoneRule(
  tweetSafetyLabelType: TweetSafetyLabelType)
    etwittertends ConditionWithTweetLabelRule(
      Tombstone(Epitaph.AdultMedia),
      And(
        LoggedInViewer,
        NonAuthorViewer,
        ViewerHasAdultMediaSettingLevel(SensitiveMediaSettingsLevel.Drop)),
      tweetSafetyLabelType
    )

abstract class ViolentMediaTweetLabelDropSettingLevelTombstoneRule(
  tweetSafetyLabelType: TweetSafetyLabelType)
    etwittertends ConditionWithTweetLabelRule(
      Tombstone(Epitaph.ViolentMedia),
      And(
        LoggedInViewer,
        NonAuthorViewer,
        ViewerHasViolentMediaSettingLevel(SensitiveMediaSettingsLevel.Drop)),
      tweetSafetyLabelType
    )

abstract class OtherSensitiveMediaTweetLabelDropSettingLevelTombstoneRule(condition: Condition)
    etwittertends RuleWithConstantAction(
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


  object AdultMediaNsfwHighPrecisionTweetLabelDropRule
      etwittertends AdultMediaTweetLabelDropRule(
        TweetSafetyLabelType.NsfwHighPrecision
      )

  object AdultMediaNsfwCardImageTweetLabelDropRule
      etwittertends AdultMediaTweetLabelDropRule(
        TweetSafetyLabelType.NsfwCardImage
      )

  object AdultMediaNsfwReportedHeuristicsTweetLabelDropRule
      etwittertends AdultMediaTweetLabelDropRule(
        TweetSafetyLabelType.NsfwReportedHeuristics
      )

  object AdultMediaNsfwVideoTweetLabelDropRule
      etwittertends AdultMediaTweetLabelDropRule(
        TweetSafetyLabelType.NsfwVideo
      )

  object AdultMediaNsfwHighRecallTweetLabelDropRule
      etwittertends AdultMediaTweetLabelDropRule(
        TweetSafetyLabelType.NsfwHighRecall
      )

  object AdultMediaNsfwTetwittertTweetLabelDropRule
      etwittertends AdultMediaTweetLabelDropRule(
        TweetSafetyLabelType.NsfwTetwittert
      )

  object ViolentMediaGoreAndViolenceHighPrecisionDropRule
      etwittertends ViolentMediaTweetLabelDropRule(
        TweetSafetyLabelType.GoreAndViolenceHighPrecision
      )

  object ViolentMediaGoreAndViolenceReportedHeuristicsDropRule
      etwittertends ViolentMediaTweetLabelDropRule(
        TweetSafetyLabelType.GoreAndViolenceReportedHeuristics
      )

  object OtherSensitiveMediaNsfwUserTweetFlagDropRule
      etwittertends OtherSensitiveMediaTweetLabelDropRule(
        TweetHasNsfwUserAuthor
      )

  object OtherSensitiveMediaNsfwAdminTweetFlagDropRule
      etwittertends OtherSensitiveMediaTweetLabelDropRule(
        TweetHasNsfwAdminAuthor
      )
}

case object SensitiveMediaTweetInterstitialRules {

  object AdultMediaNsfwHighPrecisionTweetLabelInterstitialRule
      etwittertends AdultMediaTweetLabelInterstitialRule(
        TweetSafetyLabelType.NsfwHighPrecision
      )
      with DoesLogVerdict

  object AdultMediaNsfwCardImageTweetLabelInterstitialRule
      etwittertends AdultMediaTweetLabelInterstitialRule(
        TweetSafetyLabelType.NsfwCardImage
      )

  object AdultMediaNsfwReportedHeuristicsTweetLabelInterstitialRule
      etwittertends AdultMediaTweetLabelInterstitialRule(
        TweetSafetyLabelType.NsfwReportedHeuristics
      )

  object AdultMediaNsfwVideoTweetLabelInterstitialRule
      etwittertends AdultMediaTweetLabelInterstitialRule(
        TweetSafetyLabelType.NsfwVideo
      )

  object AdultMediaNsfwHighRecallTweetLabelInterstitialRule
      etwittertends AdultMediaTweetLabelInterstitialRule(
        TweetSafetyLabelType.NsfwHighRecall
      )

  object AdultMediaNsfwTetwittertTweetLabelInterstitialRule
      etwittertends AdultMediaTweetLabelInterstitialRule(
        TweetSafetyLabelType.NsfwTetwittert
      )

  object ViolentMediaGoreAndViolenceHighPrecisionInterstitialRule
      etwittertends ViolentMediaTweetLabelInterstitialRule(
        TweetSafetyLabelType.GoreAndViolenceHighPrecision
      )
      with DoesLogVerdict

  object ViolentMediaGoreAndViolenceReportedHeuristicsInterstitialRule
      etwittertends ViolentMediaTweetLabelInterstitialRule(
        TweetSafetyLabelType.GoreAndViolenceReportedHeuristics
      )

  object OtherSensitiveMediaNsfwUserTweetFlagInterstitialRule
      etwittertends OtherSensitiveMediaTweetLabelInterstitialRule(
        TweetHasNsfwUserAuthor
      )

  object OtherSensitiveMediaNsfwAdminTweetFlagInterstitialRule
      etwittertends OtherSensitiveMediaTweetLabelInterstitialRule(
        TweetHasNsfwAdminAuthor
      )

}

case object SensitiveMediaTweetDropSettingLevelTombstoneRules {


  object AdultMediaNsfwHighPrecisionTweetLabelDropSettingLevelTombstoneRule
      etwittertends AdultMediaTweetLabelDropSettingLevelTombstoneRule(
        TweetSafetyLabelType.NsfwHighPrecision
      )

  object AdultMediaNsfwCardImageTweetLabelDropSettingLevelTombstoneRule
      etwittertends AdultMediaTweetLabelDropSettingLevelTombstoneRule(
        TweetSafetyLabelType.NsfwCardImage
      )

  object AdultMediaNsfwReportedHeuristicsTweetLabelDropSettingLevelTombstoneRule
      etwittertends AdultMediaTweetLabelDropSettingLevelTombstoneRule(
        TweetSafetyLabelType.NsfwReportedHeuristics
      )

  object AdultMediaNsfwVideoTweetLabelDropSettingLevelTombstoneRule
      etwittertends AdultMediaTweetLabelDropSettingLevelTombstoneRule(
        TweetSafetyLabelType.NsfwVideo
      )

  object AdultMediaNsfwHighRecallTweetLabelDropSettingLevelTombstoneRule
      etwittertends AdultMediaTweetLabelDropSettingLevelTombstoneRule(
        TweetSafetyLabelType.NsfwHighRecall
      )

  object AdultMediaNsfwTetwittertTweetLabelDropSettingLevelTombstoneRule
      etwittertends AdultMediaTweetLabelDropSettingLevelTombstoneRule(
        TweetSafetyLabelType.NsfwTetwittert
      )

  object ViolentMediaGoreAndViolenceHighPrecisionDropSettingLeveTombstoneRule
      etwittertends ViolentMediaTweetLabelDropSettingLevelTombstoneRule(
        TweetSafetyLabelType.GoreAndViolenceHighPrecision
      )

  object ViolentMediaGoreAndViolenceReportedHeuristicsDropSettingLevelTombstoneRule
      etwittertends ViolentMediaTweetLabelDropSettingLevelTombstoneRule(
        TweetSafetyLabelType.GoreAndViolenceReportedHeuristics
      )

  object OtherSensitiveMediaNsfwUserTweetFlagDropSettingLevelTombstoneRule
      etwittertends OtherSensitiveMediaTweetLabelDropSettingLevelTombstoneRule(
        TweetHasNsfwUserAuthor
      )

  object OtherSensitiveMediaNsfwAdminTweetFlagDropSettingLevelTombstoneRule
      etwittertends OtherSensitiveMediaTweetLabelDropSettingLevelTombstoneRule(
        TweetHasNsfwAdminAuthor
      )
}
