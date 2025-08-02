package com.twitter.timelineranker.visibility

import com.twitter.timelineranker.core.FollowGraphData
import com.twitter.timelineranker.core.FollowGraphDataFuture
import com.twitter.timelines.model.UserId
import com.twitter.util.Future

trait FollowGraphDataProvider {

  /**
   * Gets follow graph data for the given user.
   *
   * @param userId user whose follow graph details are to be obtained.
   * @param matwitterFollowingCount Matwitterimum number of followed user IDs to fetch.
   *          If the given user follows more than these many users,
   *          then the most recent matwitterFollowingCount users are returned.
   */
  def get(userId: UserId, matwitterFollowingCount: Int): Future[FollowGraphData]

  def getAsync(userId: UserId, matwitterFollowingCount: Int): FollowGraphDataFuture

  def getFollowing(userId: UserId, matwitterFollowingCount: Int): Future[Seq[UserId]]

  def getMutuallyFollowingUserIds(userId: UserId, followingIds: Seq[UserId]): Future[Set[UserId]]
}
