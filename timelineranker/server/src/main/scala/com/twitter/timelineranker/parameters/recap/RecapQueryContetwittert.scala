package com.twitter.timelineranker.parameters.recap

import com.twitter.timelineranker.model.RecapQuery
import com.twitter.timelines.util.bounds.BoundsWithDefault

object RecapQueryContetwittert {
  val MatwitterFollowedUsers: BoundsWithDefault[Int] = BoundsWithDefault[Int](1, 3000, 1000)
  val MatwitterCountMultiplier: BoundsWithDefault[Double] = BoundsWithDefault[Double](0.1, 2.0, 2.0)
  val MatwitterRealGraphAndFollowedUsers: BoundsWithDefault[Int] = BoundsWithDefault[Int](0, 2000, 1000)

  def getDefaultContetwittert(query: RecapQuery): RecapQueryContetwittert = {
    new RecapQueryContetwittertImpl(
      query,
      getEnableHydrationUsingTweetyPie = () => false,
      getMatwitterFollowedUsers = () => MatwitterFollowedUsers.default,
      getMatwitterCountMultiplier = () => MatwitterCountMultiplier.default,
      getEnableRealGraphUsers = () => false,
      getOnlyRealGraphUsers = () => false,
      getMatwitterRealGraphAndFollowedUsers = () => MatwitterRealGraphAndFollowedUsers.default,
      getEnableTetwittertFeatureHydration = () => false
    )
  }
}

// Note that methods that return parameter value always use () to indicate that
// side effects may be involved in their invocation.
trait RecapQueryContetwittert {
  def query: RecapQuery

  // If true, tweet hydration are performed by calling TweetyPie.
  // Otherwise, tweets are partially hydrated based on information in ThriftSearchResult.
  def enableHydrationUsingTweetyPie(): Boolean

  // Matwitterimum number of followed user accounts to use when fetching recap tweets.
  def matwitterFollowedUsers(): Int

  // We multiply matwitterCount (caller supplied value) by this multiplier and fetch those many
  // candidates from search so that we are left with sufficient number of candidates after
  // hydration and filtering.
  def matwitterCountMultiplier(): Double

  // Only used if user follows >= 1000.
  // If true, fetches recap/recycled tweets using author seedset mitwittering with real graph users and followed users.
  // Otherwise, fetches recap/recycled tweets only using followed users
  def enableRealGraphUsers(): Boolean

  // Only used if enableRealGraphUsers is true.
  // If true, user seedset only contains real graph users.
  // Otherwise, user seedset contains real graph users and recent followed users.
  def onlyRealGraphUsers(): Boolean

  // Only used if enableRealGraphUsers is true and onlyRealGraphUsers is false.
  // Matwitterimum number of real graph users and recent followed users when mitwittering recent/real-graph users.
  def matwitterRealGraphAndFollowedUsers(): Int

  // If true, tetwittert features are hydrated for prediction.
  // Otherwise those feature values are not set at all.
  def enableTetwittertFeatureHydration(): Boolean
}

class RecapQueryContetwittertImpl(
  override val query: RecapQuery,
  getEnableHydrationUsingTweetyPie: () => Boolean,
  getMatwitterFollowedUsers: () => Int,
  getMatwitterCountMultiplier: () => Double,
  getEnableRealGraphUsers: () => Boolean,
  getOnlyRealGraphUsers: () => Boolean,
  getMatwitterRealGraphAndFollowedUsers: () => Int,
  getEnableTetwittertFeatureHydration: () => Boolean)
    etwittertends RecapQueryContetwittert {

  override def enableHydrationUsingTweetyPie(): Boolean = { getEnableHydrationUsingTweetyPie() }
  override def matwitterFollowedUsers(): Int = { getMatwitterFollowedUsers() }
  override def matwitterCountMultiplier(): Double = { getMatwitterCountMultiplier() }
  override def enableRealGraphUsers(): Boolean = { getEnableRealGraphUsers() }
  override def onlyRealGraphUsers(): Boolean = { getOnlyRealGraphUsers() }
  override def matwitterRealGraphAndFollowedUsers(): Int = { getMatwitterRealGraphAndFollowedUsers() }
  override def enableTetwittertFeatureHydration(): Boolean = { getEnableTetwittertFeatureHydration() }
}
