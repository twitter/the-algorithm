package com.twitter.timelineranker.parameters.revchron

import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam

object ReverseChronParams {
  import ReverseChronTimelineQueryContext._

  /**
   * Controls limit on the number of followed users fetched from SGS when materializing home timelines.
   */
  object MaxFollowedUsersParam
      extends FSBoundedParam(
        "reverse_chron_max_followed_users",
        default = MaxFollowedUsers.default,
        min = MaxFollowedUsers.bounds.minInclusive,
        max = MaxFollowedUsers.bounds.maxInclusive
      )

  object ReturnEmptyWhenOverMaxFollowsParam
      extends FSParam(
        name = "reverse_chron_return_empty_when_over_max_follows",
        default = true
      )

  /**
   * When true, search requests for the reverse chron timeline will include an additional operator
   * so that search will not return tweets that are directed at non-followed users.
   */
  object DirectedAtNarrowcastingViaSearchParam
      extends FSParam(
        name = "reverse_chron_directed_at_narrowcasting_via_search",
        default = false
      )

  /**
   * When true, search requests for the reverse chron timeline will request additional metadata
   * from search and use this metadata for post filtering.
   */
  object PostFilteringBasedOnSearchMetadataEnabledParam
      extends FSParam(
        name = "reverse_chron_post_filtering_based_on_search_metadata_enabled",
        default = true
      )
}
