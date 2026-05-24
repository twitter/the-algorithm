package com.twitter.follow_recommendations.common.models

/**
 * Each candidate source algorithm could be based on one, or more, of the 4 general type of
 * information we have on a user:
 *   1. Social: the user's connections in Twitter's social graph.
 *   2. Geo: the user's geographical information.
 *   3. Interest: information on the user's chosen interests.
 *   4. Activity: information on the user's past activity.
 *
 * Note that an algorithm can fall under more than one of these categories.
 */
object AlgorithmType extends Enumeration {
  type AlgorithmType = Value

  val Social: Value = Value("social")
  val Geo: Value = Value("geo")
  val Activity: Value = Value("activity")
  val Interest: Value = Value("interest")
}
