package com.twitter.frigate.pushservice.target

import com.twitter.featureswitches.FSCustomMapInput
import com.twitter.featureswitches.parsing.DynMap
import com.twitter.frigate.common.store.deviceinfo.DeviceInfo
import com.twitter.frigate.pushservice.util.NsfwInfo
import com.twitter.gizmoduck.thriftscala.User

object CustomFSFields {
  private val IsReturningUser = "is_returning_user"
  private val DaysSinceSignup = "days_since_signup"
  private val DaysSinceLogin = "days_since_login"
  private val DaysSinceReactivation = "days_since_reactivation"
  private val ReactivationDate = "reactivation_date"
  private val FollowGraphSize = "follow_graph_size"
  private val GizmoduckUserType = "gizmoduck_user_type"
  private val UserAge = "mr_user_age"
  private val SensitiveOptIn = "sensitive_opt_in"
  private val NsfwFollowRatio = "nsfw_follow_ratio"
  private val TotalFollows = "follow_count"
  private val NsfwRealGraphScore = "nsfw_real_graph_score"
  private val NsfwProfileVisit = "nsfw_profile_visit"
  private val TotalSearches = "total_searches"
  private val NsfwSearchScore = "nsfw_search_score"
  private val HasReportedNsfw = "nsfw_reported"
  private val HasDislikedNsfw = "nsfw_disliked"
  private val UserState = "user_state"
  private val MrUserState = "mr_user_state"
  private val NumDaysReceivedPushInLast30Days =
    "num_days_received_push_in_last_30_days"
  private val RecommendationsSetting = "recommendations_setting"
  private val TopicsSetting = "topics_setting"
  private val SpacesSetting = "spaces_setting"
  private val NewsSetting = "news_setting"
  private val LiveVideoSetting = "live_video_setting"
  private val HasRecentPushableRebDevice = "has_recent_pushable_rweb_device"
  private val RequestSource = "request_source"
}

case class CustomFSFields(
  isReactivatedUser: Boolean,
  daysSinceSignup: Int,
  numDaysReceivedPushInLast30Days: Int,
  daysSinceLogin: Option[Int],
  daysSinceReactivation: Option[Int],
  user: Option[User],
  userState: Option[String],
  mrUserState: Option[String],
  reactivationDate: Option[String],
  requestSource: Option[String],
  userAge: Option[Int],
  nsfwInfo: Option[NsfwInfo],
  deviceInfo: Option[DeviceInfo]) {

  import CustomFSFields._

  private val keyValMap: Map[String, Any] = Map(
    IsReturningUser -> isReactivatedUser,
    DaysSinceSignup -> daysSinceSignup,
    DaysSinceLogin -> daysSinceLogin,
    NumDaysReceivedPushInLast30Days -> numDaysReceivedPushInLast30Days
  ) ++
    daysSinceReactivation.map(DaysSinceReactivation -> _) ++
    reactivationDate.map(ReactivationDate -> _) ++
    user.flatMap(_.counts.map(counts => FollowGraphSize -> counts.following)) ++
    user.map(u => GizmoduckUserType -> u.userType.name) ++
    userState.map(UserState -> _) ++
    mrUserState.map(MrUserState -> _) ++
    requestSource.map(RequestSource -> _) ++
    userAge.map(UserAge -> _) ++
    nsfwInfo.flatMap(_.senstiveOptIn).map(SensitiveOptIn -> _) ++
    nsfwInfo
      .map { nsInfo =>
        Map[String, Any](
          NsfwFollowRatio -> nsInfo.nsfwFollowRatio,
          TotalFollows -> nsInfo.totalFollowCount,
          NsfwRealGraphScore -> nsInfo.realGraphScore,
          NsfwProfileVisit -> nsInfo.nsfwProfileVisits,
          TotalSearches -> nsInfo.totalSearches,
          NsfwSearchScore -> nsInfo.searchNsfwScore,
          HasReportedNsfw -> nsInfo.hasReported,
          HasDislikedNsfw -> nsInfo.hasDisliked
        )
      }.getOrElse(Map.empty[String, Any]) ++
    deviceInfo
      .map { deviceInfo =>
        Map[String, Boolean](
          RecommendationsSetting -> deviceInfo.isRecommendationsEligible,
          TopicsSetting -> deviceInfo.isTopicsEligible,
          SpacesSetting -> deviceInfo.isSpacesEligible,
          LiveVideoSetting -> deviceInfo.isBroadcastsEligible,
          NewsSetting -> deviceInfo.isNewsEligible,
          HasRecentPushableRebDevice -> deviceInfo.hasRecentPushableRWebDevice
        )
      }.getOrElse(Map.empty[String, Boolean])

  val fsMap = FSCustomMapInput(DynMap(keyValMap))
}
