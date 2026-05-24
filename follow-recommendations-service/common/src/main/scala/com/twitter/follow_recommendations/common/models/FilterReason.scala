package com.twitter.follow_recommendations.common.models

sealed trait FilterReason {
  def reason: String
}

object FilterReason {

  case object NoReason extends FilterReason {
    override val reason: String = "no_reason"
  }

  case class ParamReason(paramName: String) extends FilterReason {
    override val reason: String = s"param_$paramName"
  }

  case object ExcludedId extends FilterReason {
    override val reason: String = "excluded_id_from_request"
  }

  case object ProfileSidebarBlacklist extends FilterReason {
    override val reason: String = "profile_sidebar_blacklisted_id"
  }

  case object CuratedAccountsCompetitorList extends FilterReason {
    override val reason: String = "curated_blacklisted_id"
  }

  case class InvalidRelationshipTypes(relationshipTypes: String) extends FilterReason {
    override val reason: String = s"invalid_relationship_types $relationshipTypes"
  }

  case object ProfileId extends FilterReason {
    override val reason: String = "candidate_has_same_id_as_profile"
  }

  case object DismissedId extends FilterReason {
    override val reason: String = s"dismissed_candidate"
  }

  case object OptedOutId extends FilterReason {
    override val reason: String = s"candidate_opted_out_from_criteria_in_request"
  }

  // gizmoduck predicates
  case object NoUser extends FilterReason {
    override val reason: String = "no_user_result_from_gizmoduck"
  }

  case object AddressBookUndiscoverable extends FilterReason {
    override val reason: String = "not_discoverable_via_address_book"
  }

  case object PhoneBookUndiscoverable extends FilterReason {
    override val reason: String = "not_discoverable_via_phone_book"
  }

  case object Deactivated extends FilterReason {
    override val reason: String = "deactivated"
  }

  case object Suspended extends FilterReason {
    override val reason: String = "suspended"
  }

  case object Restricted extends FilterReason {
    override val reason: String = "restricted"
  }

  case object NsfwUser extends FilterReason {
    override val reason: String = "nsfwUser"
  }

  case object NsfwAdmin extends FilterReason {
    override val reason: String = "nsfwAdmin"
  }

  case object HssSignal extends FilterReason {
    override val reason: String = "hssSignal"
  }

  case object IsProtected extends FilterReason {
    override val reason: String = "isProtected"
  }

  case class CountryTakedown(countryCode: String) extends FilterReason {
    override val reason: String = s"takedown_in_$countryCode"
  }

  case object Blink extends FilterReason {
    override val reason: String = "blink"
  }

  case object AlreadyFollowed extends FilterReason {
    override val reason: String = "already_followed"
  }

  case object InvalidRelationship extends FilterReason {
    override val reason: String = "invalid_relationship"
  }

  case object NotFollowingTargetUser extends FilterReason {
    override val reason: String = "not_following_target_user"
  }

  case object CandidateSideHoldback extends FilterReason {
    override val reason: String = "candidate_side_holdback"
  }

  case object Inactive extends FilterReason {
    override val reason: String = "inactive"
  }

  case object MissingRecommendabilityData extends FilterReason {
    override val reason: String = "missing_recommendability_data"
  }

  case object HighTweetVelocity extends FilterReason {
    override val reason: String = "high_tweet_velocity"
  }

  case object AlreadyRecommended extends FilterReason {
    override val reason: String = "already_recommended"
  }

  case object MinStateNotMet extends FilterReason {
    override val reason: String = "min_state_user_not_met"
  }

  case object FailOpen extends FilterReason {
    override val reason: String = "fail_open"
  }
}
