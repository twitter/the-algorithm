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
    extends ConditionWithTweetLabelRule(
      Drop(Nsfw),
      And(LoggedInViewer, ViewerHasAdultMediaSettingLevel(SensitiveMediaSettingsLevel.Drop)),
      tweetSafetyLabelType
    )

abstract class ViolentMediaTweetLabelDropRule(tweetSafetyLabelType: TweetSafetyLabelType)
    extends ConditionWithTweetLabelRule(
      Drop(Nsfw),
      And(LoggedInViewer, ViewerHasViolentMediaSettingLevel(SensitiveMediaSettingsLevel.Drop)),
      tweetSafetyLabelType
    )

abstract class OtherSensitiveMediaTweetLabelDropRule(condition: Condition)
    extends RuleWithConstantAction(
      Drop(Nsfw),
      And(
        condition,
        And(
          TweetHasMedia,
          LoggedInViewer,
          ViewerHasOtherSensitiveMediaSettingLevel(SensitiveMediaSettingsLevel.Drop)))
    )

abstract class AdultMediaTweetLabelInterstitialRule(tweetSafetyLabelType: TweetSafetyLabelType)
    extends ConditionWithTweetLabelRule(
      Interstitial(Nsfw),
      Or(
        LoggedOutViewer,
        ViewerHasAdultMediaSettingLevel(SensitiveMediaSettingsLevel.Warn),
        Not(ViewerHasAdultMediaSettingLevel(SensitiveMediaSettingsLevel.Allow))
      ),
      tweetSafetyLabelType
    )

abstract class ViolentMediaTweetLabelInterstitialRule(tweetSafetyLabelType: TweetSafetyLabelType)
    extends ConditionWithTweetLabelRule(
      Interstitial(Nsfw),
      Or(
        LoggedOutViewer,
        ViewerHasViolentMediaSettingLevel(SensitiveMediaSettingsLevel.Warn),
        Not(ViewerHasViolentMediaSettingLevel(SensitiveMediaSettingsLevel.Allow))
      ),
      tweetSafetyLabelType
    )

abstract class OtherSensitiveMediaTweetLabelInterstitialRule(condition: Condition)
    extends RuleWithConstantAction(
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
    extends ConditionWithTweetLabelRule(
      Tombstone(Epitaph.AdultMedia),
      And(
        LoggedInViewer,
        NonAuthorViewer,
        ViewerHasAdultMediaSettingLevel(SensitiveMediaSettingsLevel.Drop)),
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


  object AdultMediaNsfwHighPrecisionTweetLabelDropRule
      extends AdultMediaTweetLabelDropRule(
        TweetSafetyLabelType.NsfwHighPrecision
      )

  object AdultMediaNsfwCardImageTweetLabelDropRule
      extends AdultMediaTweetLabelDropRule(
        TweetSafetyLabelType.NsfwCardImage
      )

  object AdultMediaNsfwReportedHeuristicsTweetLabelDropRule
      extends AdultMediaTweetLabelDropRule(
        TweetSafetyLabelType.NsfwReportedHeuristics
      )

  object AdultMediaNsfwVideoTweetLabelDropRule
      extends AdultMediaTweetLabelDropRule(
        TweetSafetyLabelType.NsfwVideo
      )

  object AdultMediaNsfwHighRecallTweetLabelDropRule
      extends AdultMediaTweetLabelDropRule(
        TweetSafetyLabelType.NsfwHighRecall
      )

  object AdultMediaNsfwTextTweetLabelDropRule
      extends AdultMediaTweetLabelDropRule(
        TweetSafetyLabelType.NsfwText
      )

  object ViolentMediaGoreAndViolenceHighPrecisionDropRule
      extends ViolentMediaTweetLabelDropRule(
        TweetSafetyLabelType.GoreAndViolenceHighPrecision
      )

  object ViolentMediaGoreAndViolenceReportedHeuristicsDropRule
      extends ViolentMediaTweetLabelDropRule(
        TweetSafetyLabelType.GoreAndViolenceReportedHeuristics
      )

  object OtherSensitiveMediaNsfwUserTweetFlagDropRule
      extends OtherSensitiveMediaTweetLabelDropRule(
        TweetHasNsfwUserAuthor
      )

  object OtherSensitiveMediaNsfwAdminTweetFlagDropRule
      extends OtherSensitiveMediaTweetLabelDropRule(
        TweetHasNsfwAdminAuthor
      )
}

case object SensitiveMediaTweetInterstitialRules {

  object AdultMediaNsfwHighPrecisionTweetLabelInterstitialRule
      extends AdultMediaTweetLabelInterstitialRule(
        TweetSafetyLabelType.NsfwHighPrecision
      )
      with DoesLogVerdict

  object AdultMediaNsfwCardImageTweetLabelInterstitialRule
      extends AdultMediaTweetLabelInterstitialRule(
        TweetSafetyLabelType.NsfwCardImage
      )

  object AdultMediaNsfwReportedHeuristicsTweetLabelInterstitialRule
      extends AdultMediaTweetLabelInterstitialRule(
        TweetSafetyLabelType.NsfwReportedHeuristics
      )

  object AdultMediaNsfwVideoTweetLabelInterstitialRule
      extends AdultMediaTweetLabelInterstitialRule(
        TweetSafetyLabelType.NsfwVideo
      )

  object AdultMediaNsfwHighRecallTweetLabelInterstitialRule
      extends AdultMediaTweetLabelInterstitialRule(
        TweetSafetyLabelType.NsfwHighRecall
      )

  object AdultMediaNsfwTextTweetLabelInterstitialRule
      extends AdultMediaTweetLabelInterstitialRule(
        TweetSafetyLabelType.NsfwText
      )

  object ViolentMediaGoreAndViolenceHighPrecisionInterstitialRule
      extends ViolentMediaTweetLabelInterstitialRule(
        TweetSafetyLabelType.GoreAndViolenceHighPrecision
      )
      with DoesLogVerdict

  object ViolentMediaGoreAndViolenceReportedHeuristicsInterstitialRule
      extends ViolentMediaTweetLabelInterstitialRule(
        TweetSafetyLabelType.GoreAndViolenceReportedHeuristics
      )

  object OtherSensitiveMediaNsfwUserTweetFlagInterstitialRule
      extends OtherSensitiveMediaTweetLabelInterstitialRule(
        TweetHasNsfwUserAuthor
      )

  object OtherSensitiveMediaNsfwAdminTweetFlagInterstitialRule
      extends OtherSensitiveMediaTweetLabelInterstitialRule(
        TweetHasNsfwAdminAuthor
      )

}

case object SensitiveMediaTweetDropSettingLevelTombstoneRules {


  object AdultMediaNsfwHighPrecisionTweetLabelDropSettingLevelTombstoneRule
      extends AdultMediaTweetLabelDropSettingLevelTombstoneRule(
        TweetSafetyLabelType.NsfwHighPrecision
      )

  object AdultMediaNsfwCardImageTweetLabelDropSettingLevelTombstoneRule
      extends AdultMediaTweetLabelDropSettingLevelTombstoneRule(
        TweetSafetyLabelType.NsfwCardImage
      )

  object AdultMediaNsfwReportedHeuristicsTweetLabelDropSettingLevelTombstoneRule
      extends AdultMediaTweetLabelDropSettingLevelTombstoneRule(
        TweetSafetyLabelType.NsfwReportedHeuristics
      )

  object AdultMediaNsfwVideoTweetLabelDropSettingLevelTombstoneRule
      extends AdultMediaTweetLabelDropSettingLevelTombstoneRule(
        TweetSafetyLabelType.NsfwVideo
      )

  object AdultMediaNsfwHighRecallTweetLabelDropSettingLevelTombstoneRule
      extends AdultMediaTweetLabelDropSettingLevelTombstoneRule(
        TweetSafetyLabelType.NsfwHighRecall
      )

  object AdultMediaNsfwTextTweetLabelDropSettingLevelTombstoneRule
      extends AdultMediaTweetLabelDropSettingLevelTombstoneRule(
        TweetSafetyLabelType.NsfwText
      )

  object ViolentMediaGoreAndViolenceHighPrecisionDropSettingLeveTombstoneRule
      extends ViolentMediaTweetLabelDropSettingLevelTombstoneRule(
        TweetSafetyLabelType.GoreAndViolenceHighPrecision
      )

  object ViolentMediaGoreAndViolenceReportedHeuristicsDropSettingLevelTombstoneRule
      extends ViolentMediaTweetLabelDropSettingLevelTombstoneRule(
        TweetSafetyLabelType.GoreAndViolenceReportedHeuristics
      )

  object OtherSensitiveMediaNsfwUserTweetFlagDropSettingLevelTombstoneRule
      extends OtherSensitiveMediaTweetLabelDropSettingLevelTombstoneRule(
        TweetHasNsfwUserAuthor
      )

  object OtherSensitiveMediaNsfwAdminTweetFlagDropSettingLevelTombstoneRule
      extends OtherSensitiveMediaTweetLabelDropSettingLevelTombstoneRule(
        TweetHasNsfwAdminAuthor
      )
}
