package com.twitter.home_mixer.model.request

/**
 * [[HasSeenTweetIds]] enables shared components to access the list of impressed tweet IDs
 * sent by clients across different Home Mixer query types (e.g. FollowingQuery, ForYouQuery)
 */
trait HasSeenTweetIds {
  def seenTweetIds: Option[Seq[Long]]
}
