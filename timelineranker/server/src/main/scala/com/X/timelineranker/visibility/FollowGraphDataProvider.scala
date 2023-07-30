package com.X.timelineranker.visibility

import com.X.timelineranker.core.FollowGraphData
import com.X.timelineranker.core.FollowGraphDataFuture
import com.X.timelines.model.UserId
import com.X.util.Future

trait FollowGraphDataProvider {

  /**
   * Gets follow graph data for the given user.
   *
   * @param userId user whose follow graph details are to be obtained.
   * @param maxFollowingCount Maximum number of followed user IDs to fetch.
   *          If the given user follows more than these many users,
   *          then the most recent maxFollowingCount users are returned.
   */
  def get(userId: UserId, maxFollowingCount: Int): Future[FollowGraphData]

  def getAsync(userId: UserId, maxFollowingCount: Int): FollowGraphDataFuture

  def getFollowing(userId: UserId, maxFollowingCount: Int): Future[Seq[UserId]]

  def getMutuallyFollowingUserIds(userId: UserId, followingIds: Seq[UserId]): Future[Set[UserId]]
}
