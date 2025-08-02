package com.twitter.follow_recommendations.common.models

import com.twitter.adserver.thriftscala.{DisplayLocation => AdDisplayLocation}
import com.twitter.follow_recommendations.logging.thriftscala.{
  OfflineDisplayLocation => TOfflineDisplayLocation
}
import com.twitter.follow_recommendations.thriftscala.{DisplayLocation => TDisplayLocation}

sealed trait DisplayLocation {
  def toThrift: TDisplayLocation

  def toOfflineThrift: TOfflineDisplayLocation

  def toFsName: String

  // corresponding display location in adserver if available
  // make sure to be consistent with the definition here
  def toAdDisplayLocation: Option[AdDisplayLocation] = None
}

/**
 * Make sure you add the new DL to the following files and redeploy our attribution jobs
 *  - follow-recommendations-service/thrift/src/main/thrift/display_location.thrift
 *  - follow-recommendations-service/thrift/src/main/thrift/logging/display_location.thrift
 *  - follow-recommendations-service/common/src/main/scala/com/twitter/follow_recommendations/common/models/DisplayLocation.scala
 */

object DisplayLocation {

  case object ProfileSidebar etwittertends DisplayLocation {
    override val toThrift: TDisplayLocation = TDisplayLocation.ProfileSidebar
    override val toOfflineThrift: TOfflineDisplayLocation = TOfflineDisplayLocation.ProfileSidebar
    override val toFsName: String = "ProfileSidebar"

    override val toAdDisplayLocation: Option[AdDisplayLocation] = Some(
      AdDisplayLocation.ProfileAccountsSidebar
    )
  }

  case object HomeTimeline etwittertends DisplayLocation {
    override val toThrift: TDisplayLocation = TDisplayLocation.HomeTimeline
    override val toOfflineThrift: TOfflineDisplayLocation = TOfflineDisplayLocation.HomeTimeline
    override val toFsName: String = "HomeTimeline"
    override val toAdDisplayLocation: Option[AdDisplayLocation] = Some(
      // it is based on the logic that HTL DL should correspond to Sidebar:
      AdDisplayLocation.WtfSidebar
    )
  }

  case object ReactiveFollow etwittertends DisplayLocation {
    override val toThrift: TDisplayLocation = TDisplayLocation.ReactiveFollow
    override val toOfflineThrift: TOfflineDisplayLocation = TOfflineDisplayLocation.ReactiveFollow
    override val toFsName: String = "ReactiveFollow"
  }

  case object EtwitterploreTab etwittertends DisplayLocation {
    override val toThrift: TDisplayLocation = TDisplayLocation.EtwitterploreTab
    override val toOfflineThrift: TOfflineDisplayLocation = TOfflineDisplayLocation.EtwitterploreTab
    override val toFsName: String = "EtwitterploreTab"
  }

  case object MagicRecs etwittertends DisplayLocation {
    override val toThrift: TDisplayLocation = TDisplayLocation.MagicRecs
    override val toOfflineThrift: TOfflineDisplayLocation = TOfflineDisplayLocation.MagicRecs
    override val toFsName: String = "MagicRecs"
  }

  case object AbUploadInjection etwittertends DisplayLocation {
    override val toThrift: TDisplayLocation = TDisplayLocation.AbUploadInjection
    override val toOfflineThrift: TOfflineDisplayLocation =
      TOfflineDisplayLocation.AbUploadInjection
    override val toFsName: String = "AbUploadInjection"
  }

  case object RutwitterLandingPage etwittertends DisplayLocation {
    override val toThrift: TDisplayLocation = TDisplayLocation.RutwitterLandingPage
    override val toOfflineThrift: TOfflineDisplayLocation = TOfflineDisplayLocation.RutwitterLandingPage
    override val toFsName: String = "RutwitterLandingPage"
  }

  case object ProfileBonusFollow etwittertends DisplayLocation {
    override val toThrift: TDisplayLocation = TDisplayLocation.ProfileBonusFollow
    override val toOfflineThrift: TOfflineDisplayLocation =
      TOfflineDisplayLocation.ProfileBonusFollow
    override val toFsName: String = "ProfileBonusFollow"
  }

  case object ElectionEtwitterploreWtf etwittertends DisplayLocation {
    override val toThrift: TDisplayLocation = TDisplayLocation.ElectionEtwitterploreWtf
    override val toOfflineThrift: TOfflineDisplayLocation =
      TOfflineDisplayLocation.ElectionEtwitterploreWtf
    override val toFsName: String = "ElectionEtwitterploreWtf"
  }

  case object ClusterFollow etwittertends DisplayLocation {
    override val toThrift: TDisplayLocation = TDisplayLocation.ClusterFollow
    override val toOfflineThrift: TOfflineDisplayLocation =
      TOfflineDisplayLocation.ClusterFollow
    override val toFsName: String = "ClusterFollow"
    override val toAdDisplayLocation: Option[AdDisplayLocation] = Some(
      AdDisplayLocation.ClusterFollow
    )
  }

  case object HtlBonusFollow etwittertends DisplayLocation {
    override val toThrift: TDisplayLocation = TDisplayLocation.HtlBonusFollow
    override val toOfflineThrift: TOfflineDisplayLocation =
      TOfflineDisplayLocation.HtlBonusFollow
    override val toFsName: String = "HtlBonusFollow"
  }

  case object TopicLandingPageHeader etwittertends DisplayLocation {
    override val toThrift: TDisplayLocation = TDisplayLocation.TopicLandingPageHeader
    override val toOfflineThrift: TOfflineDisplayLocation =
      TOfflineDisplayLocation.TopicLandingPageHeader
    override val toFsName: String = "TopicLandingPageHeader"
  }

  case object NewUserSarusBackfill etwittertends DisplayLocation {
    override val toThrift: TDisplayLocation = TDisplayLocation.NewUserSarusBackfill
    override val toOfflineThrift: TOfflineDisplayLocation =
      TOfflineDisplayLocation.NewUserSarusBackfill
    override val toFsName: String = "NewUserSarusBackfill"
  }

  case object NutwitterPymk etwittertends DisplayLocation {
    override val toThrift: TDisplayLocation = TDisplayLocation.NutwitterPymk
    override val toOfflineThrift: TOfflineDisplayLocation =
      TOfflineDisplayLocation.NutwitterPymk
    override val toFsName: String = "NutwitterPymk"
  }

  case object NutwitterInterests etwittertends DisplayLocation {
    override val toThrift: TDisplayLocation = TDisplayLocation.NutwitterInterests
    override val toOfflineThrift: TOfflineDisplayLocation =
      TOfflineDisplayLocation.NutwitterInterests
    override val toFsName: String = "NutwitterInterests"
  }

  case object NutwitterTopicBonusFollow etwittertends DisplayLocation {
    override val toThrift: TDisplayLocation = TDisplayLocation.NutwitterTopicBonusFollow
    override val toOfflineThrift: TOfflineDisplayLocation =
      TOfflineDisplayLocation.NutwitterTopicBonusFollow
    override val toFsName: String = "NutwitterTopicBonusFollow"
  }

  case object Sidebar etwittertends DisplayLocation {
    override val toThrift: TDisplayLocation = TDisplayLocation.Sidebar
    override val toOfflineThrift: TOfflineDisplayLocation = TOfflineDisplayLocation.Sidebar
    override val toFsName: String = "Sidebar"

    override val toAdDisplayLocation: Option[AdDisplayLocation] = Some(
      AdDisplayLocation.WtfSidebar
    )
  }

  case object CampaignForm etwittertends DisplayLocation {
    override val toThrift: TDisplayLocation = TDisplayLocation.CampaignForm
    override val toOfflineThrift: TOfflineDisplayLocation = TOfflineDisplayLocation.CampaignForm
    override val toFsName: String = "CampaignForm"
  }

  case object ProfileTopFollowers etwittertends DisplayLocation {
    override val toThrift: TDisplayLocation = TDisplayLocation.ProfileTopFollowers
    override val toOfflineThrift: TOfflineDisplayLocation =
      TOfflineDisplayLocation.ProfileTopFollowers
    override val toFsName: String = "ProfileTopFollowers"
  }

  case object ProfileTopFollowing etwittertends DisplayLocation {
    override val toThrift: TDisplayLocation = TDisplayLocation.ProfileTopFollowing
    override val toOfflineThrift: TOfflineDisplayLocation =
      TOfflineDisplayLocation.ProfileTopFollowing
    override val toFsName: String = "ProfileTopFollowing"
  }

  case object RutwitterPymk etwittertends DisplayLocation {
    override val toThrift: TDisplayLocation = TDisplayLocation.RutwitterPymk
    override val toOfflineThrift: TOfflineDisplayLocation =
      TOfflineDisplayLocation.RutwitterPymk
    override val toFsName: String = "RutwitterPymk"
  }

  case object IndiaCovid19CuratedAccountsWtf etwittertends DisplayLocation {
    override val toThrift: TDisplayLocation = TDisplayLocation.IndiaCovid19CuratedAccountsWtf
    override val toOfflineThrift: TOfflineDisplayLocation =
      TOfflineDisplayLocation.IndiaCovid19CuratedAccountsWtf
    override val toFsName: String = "IndiaCovid19CuratedAccountsWtf"
  }

  case object PeoplePlusPlus etwittertends DisplayLocation {
    override val toThrift: TDisplayLocation = TDisplayLocation.PeoplePlusPlus
    override val toOfflineThrift: TOfflineDisplayLocation =
      TOfflineDisplayLocation.PeoplePlusPlus
    override val toFsName: String = "PeoplePlusPlus"
  }

  case object TweetNotificationRecs etwittertends DisplayLocation {
    override val toThrift: TDisplayLocation = TDisplayLocation.TweetNotificationRecs
    override val toOfflineThrift: TOfflineDisplayLocation =
      TOfflineDisplayLocation.TweetNotificationRecs
    override val toFsName: String = "TweetNotificationRecs"
  }

  case object ProfileDeviceFollow etwittertends DisplayLocation {
    override val toThrift: TDisplayLocation = TDisplayLocation.ProfileDeviceFollow
    override val toOfflineThrift: TOfflineDisplayLocation =
      TOfflineDisplayLocation.ProfileDeviceFollow
    override val toFsName: String = "ProfileDeviceFollow"
  }

  case object RecosBackfill etwittertends DisplayLocation {
    override val toThrift: TDisplayLocation = TDisplayLocation.RecosBackfill
    override val toOfflineThrift: TOfflineDisplayLocation =
      TOfflineDisplayLocation.RecosBackfill
    override val toFsName: String = "RecosBackfill"
  }

  case object HtlSpaceHosts etwittertends DisplayLocation {
    override val toThrift: TDisplayLocation = TDisplayLocation.HtlSpaceHosts
    override val toOfflineThrift: TOfflineDisplayLocation =
      TOfflineDisplayLocation.HtlSpaceHosts
    override val toFsName: String = "HtlSpaceHosts"
  }

  case object PostNutwitterFollowTask etwittertends DisplayLocation {
    override val toThrift: TDisplayLocation = TDisplayLocation.PostNutwitterFollowTask
    override val toOfflineThrift: TOfflineDisplayLocation =
      TOfflineDisplayLocation.PostNutwitterFollowTask
    override val toFsName: String = "PostNutwitterFollowTask"
  }

  case object TopicLandingPage etwittertends DisplayLocation {
    override val toThrift: TDisplayLocation = TDisplayLocation.TopicLandingPage
    override val toOfflineThrift: TOfflineDisplayLocation =
      TOfflineDisplayLocation.TopicLandingPage
    override val toFsName: String = "TopicLandingPage"
  }

  case object UserTypeaheadPrefetch etwittertends DisplayLocation {
    override val toThrift: TDisplayLocation = TDisplayLocation.UserTypeaheadPrefetch
    override val toOfflineThrift: TOfflineDisplayLocation =
      TOfflineDisplayLocation.UserTypeaheadPrefetch
    override val toFsName: String = "UserTypeaheadPrefetch"
  }

  case object HomeTimelineRelatableAccounts etwittertends DisplayLocation {
    override val toThrift: TDisplayLocation = TDisplayLocation.HomeTimelineRelatableAccounts
    override val toOfflineThrift: TOfflineDisplayLocation =
      TOfflineDisplayLocation.HomeTimelineRelatableAccounts
    override val toFsName: String = "HomeTimelineRelatableAccounts"
  }

  case object NutwitterGeoCategory etwittertends DisplayLocation {
    override val toThrift: TDisplayLocation = TDisplayLocation.NutwitterGeoCategory
    override val toOfflineThrift: TOfflineDisplayLocation =
      TOfflineDisplayLocation.NutwitterGeoCategory
    override val toFsName: String = "NutwitterGeoCategory"
  }

  case object NutwitterInterestsCategory etwittertends DisplayLocation {
    override val toThrift: TDisplayLocation = TDisplayLocation.NutwitterInterestsCategory
    override val toOfflineThrift: TOfflineDisplayLocation =
      TOfflineDisplayLocation.NutwitterInterestsCategory
    override val toFsName: String = "NutwitterInterestsCategory"
  }

  case object TopArticles etwittertends DisplayLocation {
    override val toThrift: TDisplayLocation = TDisplayLocation.TopArticles
    override val toOfflineThrift: TOfflineDisplayLocation =
      TOfflineDisplayLocation.TopArticles
    override val toFsName: String = "TopArticles"
  }

  case object NutwitterPymkCategory etwittertends DisplayLocation {
    override val toThrift: TDisplayLocation = TDisplayLocation.NutwitterPymkCategory
    override val toOfflineThrift: TOfflineDisplayLocation =
      TOfflineDisplayLocation.NutwitterPymkCategory
    override val toFsName: String = "NutwitterPymkCategory"
  }

  case object HomeTimelineTweetRecs etwittertends DisplayLocation {
    override val toThrift: TDisplayLocation = TDisplayLocation.HomeTimelineTweetRecs
    override val toOfflineThrift: TOfflineDisplayLocation =
      TOfflineDisplayLocation.HomeTimelineTweetRecs
    override val toFsName: String = "HomeTimelineTweetRecs"
  }

  case object HtlBulkFriendFollows etwittertends DisplayLocation {
    override val toThrift: TDisplayLocation = TDisplayLocation.HtlBulkFriendFollows
    override val toOfflineThrift: TOfflineDisplayLocation =
      TOfflineDisplayLocation.HtlBulkFriendFollows
    override val toFsName: String = "HtlBulkFriendFollows"
  }

  case object NutwitterAutoFollow etwittertends DisplayLocation {
    override val toThrift: TDisplayLocation = TDisplayLocation.NutwitterAutoFollow
    override val toOfflineThrift: TOfflineDisplayLocation =
      TOfflineDisplayLocation.NutwitterAutoFollow
    override val toFsName: String = "NutwitterAutoFollow"
  }

  case object SearchBonusFollow etwittertends DisplayLocation {
    override val toThrift: TDisplayLocation = TDisplayLocation.SearchBonusFollow
    override val toOfflineThrift: TOfflineDisplayLocation =
      TOfflineDisplayLocation.SearchBonusFollow
    override val toFsName: String = "SearchBonusFollow"
  }

  case object ContentRecommender etwittertends DisplayLocation {
    override val toThrift: TDisplayLocation = TDisplayLocation.ContentRecommender
    override val toOfflineThrift: TOfflineDisplayLocation =
      TOfflineDisplayLocation.ContentRecommender
    override val toFsName: String = "ContentRecommender"
  }

  case object HomeTimelineReverseChron etwittertends DisplayLocation {
    override val toThrift: TDisplayLocation = TDisplayLocation.HomeTimelineReverseChron
    override val toOfflineThrift: TOfflineDisplayLocation =
      TOfflineDisplayLocation.HomeTimelineReverseChron
    override val toFsName: String = "HomeTimelineReverseChron"
  }

  def fromThrift(displayLocation: TDisplayLocation): DisplayLocation = displayLocation match {
    case TDisplayLocation.ProfileSidebar => ProfileSidebar
    case TDisplayLocation.HomeTimeline => HomeTimeline
    case TDisplayLocation.MagicRecs => MagicRecs
    case TDisplayLocation.AbUploadInjection => AbUploadInjection
    case TDisplayLocation.RutwitterLandingPage => RutwitterLandingPage
    case TDisplayLocation.ProfileBonusFollow => ProfileBonusFollow
    case TDisplayLocation.ElectionEtwitterploreWtf => ElectionEtwitterploreWtf
    case TDisplayLocation.ClusterFollow => ClusterFollow
    case TDisplayLocation.HtlBonusFollow => HtlBonusFollow
    case TDisplayLocation.ReactiveFollow => ReactiveFollow
    case TDisplayLocation.TopicLandingPageHeader => TopicLandingPageHeader
    case TDisplayLocation.NewUserSarusBackfill => NewUserSarusBackfill
    case TDisplayLocation.NutwitterPymk => NutwitterPymk
    case TDisplayLocation.NutwitterInterests => NutwitterInterests
    case TDisplayLocation.NutwitterTopicBonusFollow => NutwitterTopicBonusFollow
    case TDisplayLocation.EtwitterploreTab => EtwitterploreTab
    case TDisplayLocation.Sidebar => Sidebar
    case TDisplayLocation.CampaignForm => CampaignForm
    case TDisplayLocation.ProfileTopFollowers => ProfileTopFollowers
    case TDisplayLocation.ProfileTopFollowing => ProfileTopFollowing
    case TDisplayLocation.RutwitterPymk => RutwitterPymk
    case TDisplayLocation.IndiaCovid19CuratedAccountsWtf => IndiaCovid19CuratedAccountsWtf
    case TDisplayLocation.PeoplePlusPlus => PeoplePlusPlus
    case TDisplayLocation.TweetNotificationRecs => TweetNotificationRecs
    case TDisplayLocation.ProfileDeviceFollow => ProfileDeviceFollow
    case TDisplayLocation.RecosBackfill => RecosBackfill
    case TDisplayLocation.HtlSpaceHosts => HtlSpaceHosts
    case TDisplayLocation.PostNutwitterFollowTask => PostNutwitterFollowTask
    case TDisplayLocation.TopicLandingPage => TopicLandingPage
    case TDisplayLocation.UserTypeaheadPrefetch => UserTypeaheadPrefetch
    case TDisplayLocation.HomeTimelineRelatableAccounts => HomeTimelineRelatableAccounts
    case TDisplayLocation.NutwitterGeoCategory => NutwitterGeoCategory
    case TDisplayLocation.NutwitterInterestsCategory => NutwitterInterestsCategory
    case TDisplayLocation.TopArticles => TopArticles
    case TDisplayLocation.NutwitterPymkCategory => NutwitterPymkCategory
    case TDisplayLocation.HomeTimelineTweetRecs => HomeTimelineTweetRecs
    case TDisplayLocation.HtlBulkFriendFollows => HtlBulkFriendFollows
    case TDisplayLocation.NutwitterAutoFollow => NutwitterAutoFollow
    case TDisplayLocation.SearchBonusFollow => SearchBonusFollow
    case TDisplayLocation.ContentRecommender => ContentRecommender
    case TDisplayLocation.HomeTimelineReverseChron => HomeTimelineReverseChron
    case TDisplayLocation.EnumUnknownDisplayLocation(i) =>
      throw new UnknownDisplayLocationEtwitterception(
        s"Unknown display location thrift enum with value: ${i}")
  }

  def fromOfflineThrift(displayLocation: TOfflineDisplayLocation): DisplayLocation =
    displayLocation match {
      case TOfflineDisplayLocation.ProfileSidebar => ProfileSidebar
      case TOfflineDisplayLocation.HomeTimeline => HomeTimeline
      case TOfflineDisplayLocation.MagicRecs => MagicRecs
      case TOfflineDisplayLocation.AbUploadInjection => AbUploadInjection
      case TOfflineDisplayLocation.RutwitterLandingPage => RutwitterLandingPage
      case TOfflineDisplayLocation.ProfileBonusFollow => ProfileBonusFollow
      case TOfflineDisplayLocation.ElectionEtwitterploreWtf => ElectionEtwitterploreWtf
      case TOfflineDisplayLocation.ClusterFollow => ClusterFollow
      case TOfflineDisplayLocation.HtlBonusFollow => HtlBonusFollow
      case TOfflineDisplayLocation.TopicLandingPageHeader => TopicLandingPageHeader
      case TOfflineDisplayLocation.NewUserSarusBackfill => NewUserSarusBackfill
      case TOfflineDisplayLocation.NutwitterPymk => NutwitterPymk
      case TOfflineDisplayLocation.NutwitterInterests => NutwitterInterests
      case TOfflineDisplayLocation.NutwitterTopicBonusFollow => NutwitterTopicBonusFollow
      case TOfflineDisplayLocation.EtwitterploreTab => EtwitterploreTab
      case TOfflineDisplayLocation.ReactiveFollow => ReactiveFollow
      case TOfflineDisplayLocation.Sidebar => Sidebar
      case TOfflineDisplayLocation.CampaignForm => CampaignForm
      case TOfflineDisplayLocation.ProfileTopFollowers => ProfileTopFollowers
      case TOfflineDisplayLocation.ProfileTopFollowing => ProfileTopFollowing
      case TOfflineDisplayLocation.RutwitterPymk => RutwitterPymk
      case TOfflineDisplayLocation.IndiaCovid19CuratedAccountsWtf => IndiaCovid19CuratedAccountsWtf
      case TOfflineDisplayLocation.PeoplePlusPlus => PeoplePlusPlus
      case TOfflineDisplayLocation.TweetNotificationRecs => TweetNotificationRecs
      case TOfflineDisplayLocation.ProfileDeviceFollow => ProfileDeviceFollow
      case TOfflineDisplayLocation.RecosBackfill => RecosBackfill
      case TOfflineDisplayLocation.HtlSpaceHosts => HtlSpaceHosts
      case TOfflineDisplayLocation.PostNutwitterFollowTask => PostNutwitterFollowTask
      case TOfflineDisplayLocation.TopicLandingPage => TopicLandingPage
      case TOfflineDisplayLocation.UserTypeaheadPrefetch => UserTypeaheadPrefetch
      case TOfflineDisplayLocation.HomeTimelineRelatableAccounts => HomeTimelineRelatableAccounts
      case TOfflineDisplayLocation.NutwitterGeoCategory => NutwitterGeoCategory
      case TOfflineDisplayLocation.NutwitterInterestsCategory => NutwitterInterestsCategory
      case TOfflineDisplayLocation.TopArticles => TopArticles
      case TOfflineDisplayLocation.NutwitterPymkCategory => NutwitterPymkCategory
      case TOfflineDisplayLocation.HomeTimelineTweetRecs => HomeTimelineTweetRecs
      case TOfflineDisplayLocation.HtlBulkFriendFollows => HtlBulkFriendFollows
      case TOfflineDisplayLocation.NutwitterAutoFollow => NutwitterAutoFollow
      case TOfflineDisplayLocation.SearchBonusFollow => SearchBonusFollow
      case TOfflineDisplayLocation.ContentRecommender => ContentRecommender
      case TOfflineDisplayLocation.HomeTimelineReverseChron => HomeTimelineReverseChron
      case TOfflineDisplayLocation.EnumUnknownOfflineDisplayLocation(i) =>
        throw new UnknownDisplayLocationEtwitterception(
          s"Unknown offline display location thrift enum with value: ${i}")
    }
}

class UnknownDisplayLocationEtwitterception(message: String) etwittertends Etwitterception(message)
