package com.twitter.timelineranker.core

import com.twitter.timelines.model.UserId

/**
 * Follow graph details of a given user. Includes users followed, but also followed users in various
 * states of mute.
 *
 * @param userId ID of a given user.
 * @param followedUserIds IDs of users who the given user follows.
 * @param mutuallyFollowingUserIds A subset of followedUserIds where followed users follow back the given user.
 * @param mutedUserIds A subset of followedUserIds that the given user has muted.
 * @param retweetsMutedUserIds A subset of followedUserIds whose retweets are muted by the given user.
 */
case class FollowGraphData(
  userId: UserId,
  followedUserIds: Seq[UserId],
  mutuallyFollowingUserIds: Set[UserId],
  mutedUserIds: Set[UserId],
  retweetsMutedUserIds: Set[UserId]) {
  val filteredFollowedUserIds: Seq[UserId] = followedUserIds.filterNot(mutedUserIds)
  val allUserIds: Seq[UserId] = filteredFollowedUserIds :+ userId
  val inNetworkUserIds: Seq[UserId] = followedUserIds :+ userId
}

object FollowGraphData {
  val Empty: FollowGraphData = FollowGraphData(
    0L,
    Seq.empty[UserId],
    Set.empty[UserId],
    Set.empty[UserId],
    Set.empty[UserId]
  )
}
