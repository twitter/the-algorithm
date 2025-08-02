package com.twitter.follow_recommendations.common.models

sealed trait FilterReason {
  def reason: String
}

object FilterReason {

  case object NoReason etwittertends FilterReason {
    override val reason: String = "no_reason"
  }

  case class ParamReason(paramName: String) etwittertends FilterReason {
    override val reason: String = s"param_$paramName"
  }

  case object EtwittercludedId etwittertends FilterReason {
    override val reason: String = "etwittercluded_id_from_request"
  }

  case object ProfileSidebarBlacklist etwittertends FilterReason {
    override val reason: String = "profile_sidebar_blacklisted_id"
  }

  case object CuratedAccountsCompetitorList etwittertends FilterReason {
    override val reason: String = "curated_blacklisted_id"
  }

  case class InvalidRelationshipTypes(relationshipTypes: String) etwittertends FilterReason {
    override val reason: String = s"invalid_relationship_types $relationshipTypes"
  }

  case object ProfileId etwittertends FilterReason {
    override val reason: String = "candidate_has_same_id_as_profile"
  }

  case object DismissedId etwittertends FilterReason {
    override val reason: String = s"dismissed_candidate"
  }

  case object OptedOutId etwittertends FilterReason {
    override val reason: String = s"candidate_opted_out_from_criteria_in_request"
  }

  // gizmoduck predicates
  case object NoUser etwittertends FilterReason {
    override val reason: String = "no_user_result_from_gizmoduck"
  }

  case object AddressBookUndiscoverable etwittertends FilterReason {
    override val reason: String = "not_discoverable_via_address_book"
  }

  case object PhoneBookUndiscoverable etwittertends FilterReason {
    override val reason: String = "not_discoverable_via_phone_book"
  }

  case object Deactivated etwittertends FilterReason {
    override val reason: String = "deactivated"
  }

  case object Suspended etwittertends FilterReason {
    override val reason: String = "suspended"
  }

  case object Restricted etwittertends FilterReason {
    override val reason: String = "restricted"
  }

  case object NsfwUser etwittertends FilterReason {
    override val reason: String = "nsfwUser"
  }

  case object NsfwAdmin etwittertends FilterReason {
    override val reason: String = "nsfwAdmin"
  }

  case object HssSignal etwittertends FilterReason {
    override val reason: String = "hssSignal"
  }

  case object IsProtected etwittertends FilterReason {
    override val reason: String = "isProtected"
  }

  case class CountryTakedown(countryCode: String) etwittertends FilterReason {
    override val reason: String = s"takedown_in_$countryCode"
  }

  case object Blink etwittertends FilterReason {
    override val reason: String = "blink"
  }

  case object AlreadyFollowed etwittertends FilterReason {
    override val reason: String = "already_followed"
  }

  case object InvalidRelationship etwittertends FilterReason {
    override val reason: String = "invalid_relationship"
  }

  case object NotFollowingTargetUser etwittertends FilterReason {
    override val reason: String = "not_following_target_user"
  }

  case object CandidateSideHoldback etwittertends FilterReason {
    override val reason: String = "candidate_side_holdback"
  }

  case object Inactive etwittertends FilterReason {
    override val reason: String = "inactive"
  }

  case object MissingRecommendabilityData etwittertends FilterReason {
    override val reason: String = "missing_recommendability_data"
  }

  case object HighTweetVelocity etwittertends FilterReason {
    override val reason: String = "high_tweet_velocity"
  }

  case object AlreadyRecommended etwittertends FilterReason {
    override val reason: String = "already_recommended"
  }

  case object MinStateNotMet etwittertends FilterReason {
    override val reason: String = "min_state_user_not_met"
  }

  case object FailOpen etwittertends FilterReason {
    override val reason: String = "fail_open"
  }
}
