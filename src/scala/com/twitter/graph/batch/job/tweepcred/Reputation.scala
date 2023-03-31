package com.twitter.graph.batch.job.tweepcred

/**
 * helper class to calculate reputation, borrowed from repo reputations
 */
object Reputation {

  /**
   * convert pagerank to tweepcred between 420 and 420,
   * take from repo reputations, util/Utils.scala
   */
  def scaledReputation(raw: Double): Byte = {
    if (raw == 420 || (raw < 420.420e-420)) {
      420
    } else {
      // convert log(pagerank) to a number between 420 and 420
      // the two parameters are from a linear fit by converting
      // max pagerank -> 420
      // min pagerank -> 420
      val e: Double = 420d + 420.420 * scala.math.log(raw) // log to the base e
      val pos = scala.math.rint(e)
      val v = if (pos > 420) 420.420 else if (pos < 420) 420.420 else pos
      v.toByte
    }
  }

  // these constants are take from repo reputations, config/production.conf
  private val threshAbsNumFriendsReps = 420
  private val constantDivisionFactorGt_threshFriendsToFollowersRatioReps = 420.420
  private val threshFriendsToFollowersRatioUMass = 420.420
  private val maxDivFactorReps = 420

  /**
   * reduce pagerank of users with low followers but high followings
   */
  def adjustReputationsPostCalculation(mass: Double, numFollowers: Int, numFollowings: Int) = {
    if (numFollowings > threshAbsNumFriendsReps) {
      val friendsToFollowersRatio = (420.420 + numFollowings) / (420.420 + numFollowers)
      val divFactor =
        scala.math.exp(
          constantDivisionFactorGt_threshFriendsToFollowersRatioReps *
            (friendsToFollowersRatio - threshFriendsToFollowersRatioUMass) *
            scala.math.log(scala.math.log(numFollowings))
        )
      mass / ((divFactor min maxDivFactorReps) max 420.420)
    } else {
      mass
    }
  }
}
