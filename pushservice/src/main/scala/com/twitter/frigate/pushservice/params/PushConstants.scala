package com.twitter.frigate.pushservice.params

import com.twitter.conversions.DurationOps._
import com.twitter.frigate.user_states.thriftscala.UserState
import java.util.Locale

object PushConstants {

  final val ServiceProdEnvironmentName = "prod"

  final val RestrictLightRankingCandidatesThreshold = 1

  final val DownSampleLightRankingScribeCandidatesRate = 1

  final val NewUserLookbackWindow = 1.days

  final val PushCapInactiveUserAndroid = 1
  final val PushCapInactiveUserIos = 1
  final val PushCapLightOccasionalOpenerUserAndroid = 1
  final val PushCapLightOccasionalOpenerUserIos = 1

  final val UserStateToPushCapIos = Map(
    UserState.Inactive.name -> PushCapInactiveUserIos,
    UserState.LightOccasionalOpener.name -> PushCapLightOccasionalOpenerUserIos
  )
  final val UserStateToPushCapAndroid = Map(
    UserState.Inactive.name -> PushCapInactiveUserAndroid,
    UserState.LightOccasionalOpener.name -> PushCapLightOccasionalOpenerUserAndroid
  )

  final val AcceptableTimeSinceLastNegativeResponse = 1.days

  final val DefaultLookBackForHistory = 1.hours

  final val DefaultEventMediaUrl = ""

  final val ConnectTabPushTapThrough = "i/connect_people"

  final val AddressBookUploadTapThrough = "i/flow/mr-address-book-upload"
  final val InterestPickerTapThrough = "i/flow/mr-interest-picker"
  final val CompleteOnboardingInterestAddressTapThrough = "i/flow/mr-interest-address"

  final val IndiaCountryCode = "IN"
  final val JapanCountryCode = Locale.JAPAN.getCountry.toUpperCase
  final val UKCountryCode = Locale.UK.getCountry.toUpperCase

  final val IndiaTimeZoneCode = "Asia/Kolkata"
  final val JapanTimeZoneCode = "Asia/Tokyo"
  final val UKTimeZoneCode = "Europe/London"

  final val countryCodeToTimeZoneMap = Map(
    IndiaCountryCode -> IndiaTimeZoneCode,
    JapanCountryCode -> JapanTimeZoneCode,
    UKCountryCode -> UKTimeZoneCode
  )

  final val AbuseStrike_Top2Percent_Id = "AbuseStrike_Top2Percent_Id"
  final val AbuseStrike_Top1Percent_Id = "AbuseStrike_Top1Percent_Id"
  final val AbuseStrike_Top05Percent_Id = "AbuseStrike_Top05Percent_Id"
  final val AbuseStrike_Top025Percent_Id = "AbuseStrike_Top025Percent_Id"
  final val AllSpamReportsPerFav_Top1Percent_Id = "AllSpamReportsPerFav_Top1Percent_Id"
  final val ReportsPerFav_Top1Percent_Id = "ReportsPerFav_Top1Percent_Id"
  final val ReportsPerFav_Top2Percent_Id = "ReportsPerFav_Top2Percent_Id"
  final val MediaUnderstanding_Nudity_Id = "MediaUnderstanding_Nudity_Id"
  final val MediaUnderstanding_Beauty_Id = "MediaUnderstanding_Beauty_Id"
  final val MediaUnderstanding_SinglePerson_Id = "MediaUnderstanding_SinglePerson_Id"
  final val PornList_Id = "PornList_Id"
  final val PornographyAndNsfwContent_Id = "PornographyAndNsfwContent_Id"
  final val SexLife_Id = "SexLife_Id"
  final val SexLifeOrSexualOrientation_Id = "SexLifeOrSexualOrientation_Id"
  final val ProfanityFilter_Id = "ProfanityFilter_Id"
  final val TweetSemanticCoreIdFeature = "tweet.core.tweet.semantic_core_annotations"
  final val targetUserGenderFeatureName = "Target.User.Gender"
  final val targetUserAgeFeatureName = "Target.User.AgeBucket"
  final val targetUserPreferredLanguage = "user.language.user.preferred_contents"
  final val tweetAgeInHoursFeatureName = "RecTweet.TweetyPieResult.TweetAgeInHrs"
  final val authorActiveFollowerFeatureName = "RecTweetAuthor.User.ActiveFollowers"
  final val favFeatureName = "tweet.core.tweet_counts.favorite_count"
  final val sentFeatureName =
    "tweet.magic_recs_tweet_real_time_aggregates_v2.pair.v2.magicrecs.realtime.is_sent.any_feature.Duration.Top.count"
  final val authorSendCountFeatureName =
    "tweet_author_aggregate.pair.any_label.any_feature.28.days.count"
  final val authorReportCountFeatureName =
    "tweet_author_aggregate.pair.label.reportTweetDone.any_feature.28.days.count"
  final val authorDislikeCountFeatureName =
    "tweet_author_aggregate.pair.label.ntab.isDisliked.any_feature.28.days.count"
  final val TweetLikesFeatureName = "tweet.core.tweet_counts.favorite_count"
  final val TweetRepliesFeatureName = "tweet.core.tweet_counts.reply_count"

  final val EnableCopyFeaturesForIbis2ModelValues = "has_copy_features"

  final val EmojiFeatureNameForIbis2ModelValues = "emoji"

  final val TargetFeatureNameForIbis2ModelValues = "target"

  final val CopyBodyExpIbisModelValues = "enable_body_exp"

  final val TweetMediaEmbeddingBQKeyIds = Seq(
    230, 110, 231, 111, 232, 233, 112, 113, 234, 235, 114, 236, 115, 237, 116, 117, 238, 118, 239,
    119, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 240, 120, 241, 121, 242, 0, 1, 122, 243, 244, 123,
    2, 124, 245, 3, 4, 246, 125, 5, 126, 247, 127, 248, 6, 128, 249, 7, 8, 129, 9, 20, 21, 22, 23,
    24, 25, 26, 27, 28, 29, 250, 130, 251, 252, 131, 132, 253, 133, 254, 134, 255, 135, 136, 137,
    138, 139, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 140, 141, 142, 143, 144, 145, 146, 147, 148,
    149, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 150, 151, 152, 153, 154, 155, 156, 157, 158, 159,
    50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 160, 161, 162, 163, 164, 165, 166, 167, 168, 169, 60,
    61, 62, 63, 64, 65, 66, 67, 68, 69, 170, 171, 172, 173, 174, 175, 176, 177, 178, 179, 70, 71,
    72, 73, 74, 75, 76, 77, 78, 79, 180, 181, 182, 183, 184, 185, 186, 187, 188, 189, 80, 81, 82,
    83, 84, 85, 86, 87, 88, 89, 190, 191, 192, 193, 194, 195, 196, 197, 198, 199, 90, 91, 92, 93,
    94, 95, 96, 97, 98, 99, 200, 201, 202, 203, 204, 205, 206, 207, 208, 209, 210, 211, 212, 213,
    214, 215, 216, 217, 218, 219, 220, 100, 221, 101, 222, 223, 102, 224, 103, 104, 225, 105, 226,
    227, 106, 107, 228, 108, 229, 109
  )

  final val SportsEventDomainId = 6L

  final val OoncQualityCombinedScore = "OoncQualityCombinedScore"
}

object PushQPSLimitConstants {

  final val PerspectiveStoreQPS = 100000

  final val IbisOrNTabQPSForRFPH = 100000

  final val SocialGraphServiceBatchSize = 100000
}
