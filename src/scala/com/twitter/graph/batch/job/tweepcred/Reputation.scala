package com.twitter.graph.batch.job.tweepcred

/**
 * helper class to calculate reputation, borrowed from repo reputations
 */
object Reputation {

  /**
   * convert pagerank to tweepcred between 0 and 100,
   * take from repo reputations, util/Utils.scala
   */
  def scaledReputation(raw: Double): Byte = {
    if (raw == 0 || (raw < 1.0e-20)) {
      0
    } else {
      // convert log(pagerank) to a number between 0 and 100
      // the two parameters are from a linear fit by converting
      // max pagerank -> 95
      // min pagerank -> 15
      val e: Double = 130d + 5.21 * scala.math.log(raw) // log to the base e
      val pos = scala.math.rint(e)
      val v = if (pos > 100) 100.0 else if (pos < 0) 0.0 else pos
      v.toByte
    }
  }

  // these constants are take from repo reputations, config/production.conf
  private val threshAbsNumFriendsReps = 2500
  private val constantDivisionFactorGt_threshFriendsToFollowersRatioReps = 3.0
  private val threshFriendsToFollowersRatioUMass = 0.6
  private val maxDivFactorReps = 50
  private val maxDailyNegDelta = -100.0 // NEW: legacy cap retained for compatibility
  private val normalCap = -100.0 // NEW: dynamic cap (normal conditions)
  private val attackCap = -20.0 // NEW: dynamic cap when a brigade is detected
  private val brigadeDiversityThreshold = 0.5 // NEW: <50% unique issuers => coordinated attack

  private def incrCounter(name: String): Unit = () // NEW: telemetry stub; no-op to preserve structure

  /**
   * reduce pagerank of users with low followers but high followings
   */
  def adjustReputationsPostCalculation(mass: Double, numFollowers: Int, numFollowings: Int) = {
    adjustReputationsPostCalculation(mass, numFollowers, numFollowings, Seq.empty) // NEW: delegate to extended method with empty issuers
  }

  /**
   * NEW overload to support entropy/diversity guard and dynamic caps
   */
  def adjustReputationsPostCalculation(mass: Double, numFollowers: Int, numFollowings: Int, blockIssuers: Seq[Long]) = { // NEW: extended signature with issuers
    if (numFollowings > threshAbsNumFriendsReps) {

      if (mass.isNaN || mass.isInfinity) return mass
      if (numFollowers < 0 || numFollowings < 0) return mass

      val baseMass = math.max(0.0, mass)

      val friendsToFollowersRatio = (1.0 + numFollowings.toDouble) / (1.0 + numFollowers.toDouble)
      val divFactorRaw = math.exp(
        constantDivisionFactorGt_threshFriendsToFollowersRatioReps *
          (friendsToFollowersRatio - threshFriendsToFollowersRatioUMass) *
          math.log(scala.math.log1p(numFollowings.toDouble))
      )
      val divFactor = ((divFactorRaw min maxDivFactorReps) max 1.0)
      val adjusted = baseMass / divFactor

      val diversity = if (blockIssuers.nonEmpty) blockIssuers.distinct.size.toDouble / blockIssuers.size else 1.0 // NEW: issuer diversity (unique/total)
      val isBrigade = diversity < brigadeDiversityThreshold // NEW: boolean flag for coordinated attack
      val cap = if (isBrigade) attackCap else normalCap // NEW: select dynamic cap based on brigade flag
      if (isBrigade) incrCounter("tweepcred.brigade.detected") // NEW: telemetry when brigade detected

      val delta = adjusted - baseMass // FIX: correct penalty sign
      val deltaCapped = math.min(0.0, math.max(delta, cap)) // FIX: clamp penalty to cap, never positive
      if (delta < cap) incrCounter(if (isBrigade) "tweepcred.delta.capped.attack" else "tweepcred.delta.capped.normal") // NEW: telemetry when clamp engages

      baseMass + deltaCapped // apply capped delta
    } else {
      mass
    }
  }
}
