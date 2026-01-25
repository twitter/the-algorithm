package com.twitter.follow_recommendations.common.candidate_sources.sims_expansion

import com.google.inject.Singleton
import com.twitter.follow_recommendations.common.candidate_sources.sims.SwitchingSimsSource
import com.twitter.follow_recommendations.common.models.CandidateUser
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.stitch.Stitch
import com.twitter.timelines.configapi.HasParams
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.product_mixer.core.model.marshalling.request.HasClientContext
import javax.inject.Inject

/**
 * Trait for requests that have a recently muted user ID.
 * This is used to find similar accounts to the user that was just muted.
 */
trait HasRecentlyMutedUserId {
  def recentlyMutedUserId: Option[Long]
}

object SimilarAccountsToMuteSource {
  val Identifier = CandidateSourceIdentifier("SimilarToMutedUser")
  
  // Default configuration values
  val DefaultMaxSecondaryDegreeNodes: Int = 20
  val DefaultMaxResults: Int = 10
}

/**
 * Candidate source for finding similar accounts to mute.
 * 
 * When a user mutes an account, this source finds similar accounts that the user
 * might also want to mute. This helps users quickly curate their timeline by
 * muting clusters of related accounts at once.
 * 
 * The similarity is based on the same algorithms used for follow recommendations,
 * but applied in reverse - instead of finding accounts to follow, we find accounts
 * similar to one the user wants to avoid.
 */
@Singleton
class SimilarAccountsToMuteSource @Inject() (
  switchingSimsSource: SwitchingSimsSource,
  statsReceiver: StatsReceiver)
    extends SimsExpansionBasedCandidateSource[
      HasParams with HasRecentlyMutedUserId with HasClientContext
    ](switchingSimsSource) {

  override val identifier: CandidateSourceIdentifier = SimilarAccountsToMuteSource.Identifier
  
  private val stats = statsReceiver.scope(identifier.name)
  private val muteCandidatesCounter = stats.counter("mute_candidates_generated")
  private val noMutedUserCounter = stats.counter("no_muted_user_id")

  /**
   * The first degree node is the user that was just muted.
   * We expand from this user to find similar accounts.
   */
  override def firstDegreeNodes(
    request: HasParams with HasRecentlyMutedUserId with HasClientContext
  ): Stitch[Seq[CandidateUser]] = {
    request.recentlyMutedUserId match {
      case Some(mutedUserId) =>
        Stitch.value(Seq(CandidateUser(mutedUserId, score = Some(1.0))))
      case None =>
        noMutedUserCounter.incr()
        Stitch.value(Seq.empty)
    }
  }

  override def maxSecondaryDegreeNodes(
    req: HasParams with HasRecentlyMutedUserId with HasClientContext
  ): Int = {
    SimilarAccountsToMuteSource.DefaultMaxSecondaryDegreeNodes
  }

  override def maxResults(
    req: HasParams with HasRecentlyMutedUserId with HasClientContext
  ): Int = {
    muteCandidatesCounter.incr()
    SimilarAccountsToMuteSource.DefaultMaxResults
  }

  /**
   * Score candidates based on their similarity to the muted user.
   * Higher scores indicate more similar accounts.
   */
  override def scoreCandidate(sourceScore: Double, similarToScore: Double): Double = {
    sourceScore * similarToScore
  }

  override def calibrateDivisor(
    req: HasParams with HasRecentlyMutedUserId with HasClientContext
  ): Double = {
    1.0 // No calibration needed for mute candidates
  }

  override def calibrateScore(
    candidateScore: Double,
    req: HasParams with HasRecentlyMutedUserId with HasClientContext
  ): Double = {
    candidateScore
  }
}
