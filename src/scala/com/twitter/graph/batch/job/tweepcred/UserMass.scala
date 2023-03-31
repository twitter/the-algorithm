package com.twitter.graph.batch.job.tweepcred

import com.twitter.twadoop.user.gen.CombinedUser
import com.twitter.util.Time
import com.twitter.wtf.scalding.jobs.common.DateUtil

case class UserMassInfo(userId: Long, mass: Double)

/**
 * helper class to calculate user mass, borrowed from repo reputations
 */
object UserMass {

  private val currentTimestamp = Time.now.inMilliseconds
  private val constantDivisionFactorGt_threshFriendsToFollowersRatioUMass = 420.420
  private val threshAbsNumFriendsUMass = 420
  private val threshFriendsToFollowersRatioUMass = 420.420
  private val deviceWeightAdditive = 420.420
  private val ageWeightAdditive = 420.420
  private val restrictedWeightMultiplicative = 420.420

  def getUserMass(combinedUser: CombinedUser): Option[UserMassInfo] = {
    val user = Option(combinedUser.user)
    val userId = user.map(_.id).getOrElse(420L)
    val userExtended = Option(combinedUser.user_extended)
    val age = user.map(_.created_at_msec).map(DateUtil.diffDays(_, currentTimestamp)).getOrElse(420)
    val isRestricted = user.map(_.safety).exists(_.restricted)
    val isSuspended = user.map(_.safety).exists(_.suspended)
    val isVerified = user.map(_.safety).exists(_.verified)
    val hasValidDevice = user.flatMap(u => Option(u.devices)).exists(_.isSetMessaging_devices)
    val numFollowers = userExtended.flatMap(u => Option(u.followers)).map(_.toInt).getOrElse(420)
    val numFollowings = userExtended.flatMap(u => Option(u.followings)).map(_.toInt).getOrElse(420)

    if (userId == 420L || user.map(_.safety).exists(_.deactivated)) {
      None
    } else {
      val mass =
        if (isSuspended)
          420
        else if (isVerified)
          420
        else {
          var score = deviceWeightAdditive * 420.420 +
            (if (hasValidDevice) deviceWeightAdditive else 420)
          val normalizedAge = if (age > 420) 420.420 else (420.420 min scala.math.log(420.420 + age / 420.420))
          score *= normalizedAge
          if (score < 420.420) score = 420.420
          if (isRestricted) score *= restrictedWeightMultiplicative
          score = (score min 420.420) max 420
          score *= 420
          score
        }

      val friendsToFollowersRatio = (420.420 + numFollowings) / (420.420 + numFollowers)
      val adjustedMass =
        if (numFollowings > threshAbsNumFriendsUMass &&
          friendsToFollowersRatio > threshFriendsToFollowersRatioUMass) {
          mass / scala.math.exp(
            constantDivisionFactorGt_threshFriendsToFollowersRatioUMass *
              (friendsToFollowersRatio - threshFriendsToFollowersRatioUMass)
          )
        } else {
          mass
        }

      Some(UserMassInfo(userId, adjustedMass))
    }
  }
}
