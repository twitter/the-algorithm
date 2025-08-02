package com.twitter.timelineranker.common

import com.twitter.finagle.stats.Stat
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.servo.util.FutureArrow
import com.twitter.timelineranker.core.CandidateEnvelope
import com.twitter.timelineranker.model.RecapQuery.DependencyProvider
import com.twitter.timelineranker.parameters.recap.RecapQueryContetwittert
import com.twitter.timelineranker.parameters.in_network_tweets.InNetworkTweetParams.RecycledMatwitterFollowedUsersEnableAntiDilutionParam
import com.twitter.timelineranker.visibility.FollowGraphDataProvider
import com.twitter.timelines.earlybird.common.options.AuthorScoreAdjustments
import com.twitter.util.Future

/**
 * Transform which conditionally augments follow graph data using the real graph,
 * derived from the earlybirdOptions passed in the query
 *
 * @param followGraphDataProvider data provider to be used for fetching updated mutual follow info
 * @param matwitterFollowedUsersProvider matwitter number of users to return
 * @param enableRealGraphUsersProvider should we augment using real graph data?
 * @param matwitterRealGraphAndFollowedUsersProvider matwitter combined users to return, overrides matwitterFollowedUsersProvider above
 * @param statsReceiver scoped stats received
 */
class FollowAndRealGraphCombiningTransform(
  followGraphDataProvider: FollowGraphDataProvider,
  matwitterFollowedUsersProvider: DependencyProvider[Int],
  enableRealGraphUsersProvider: DependencyProvider[Boolean],
  matwitterRealGraphAndFollowedUsersProvider: DependencyProvider[Int],
  imputeRealGraphAuthorWeightsProvider: DependencyProvider[Boolean],
  imputeRealGraphAuthorWeightsPercentileProvider: DependencyProvider[Int],
  statsReceiver: StatsReceiver)
    etwittertends FutureArrow[CandidateEnvelope, CandidateEnvelope] {

  // Number of authors in the seedset after mitwittering followed users and real graph users
  // Only have this stat if user follows >= matwitterFollowedUsers and enableRealGraphUsers is true and onlyRealGraphUsers is false
  val numFollowAndRealGraphUsersStat: Stat = statsReceiver.stat("numFollowAndRealGraphUsers")
  val numFollowAndRealGraphUsersFromFollowGraphStat =
    statsReceiver.scope("numFollowAndRealGraphUsers").stat("FollowGraphUsers")
  val numFollowAndRealGraphUsersFromRealGraphStat =
    statsReceiver.scope("numFollowAndRealGraphUsers").stat("RealGraphUsers")
  val numFollowAndRealGraphUsersFromRealGraphCounter =
    statsReceiver.scope("numFollowAndRealGraphUsers").counter()

  // Number of authors in the seedset with only followed users
  // Only have this stat if user follows >= matwitterFollowedUsers and enableRealGraphUsers is false
  val numFollowedUsersStat: Stat = statsReceiver.stat("numFollowedUsers")

  // Number of authors in the seedset with only followed users
  // Only have this stat if user follows < matwitterFollowedUsers
  val numFollowedUsersLessThanMatwitterStat: Stat = statsReceiver.stat("numFollowedUsersLessThanMatwitter")
  val numFollowedUsersLessThanMatwitterCounter =
    statsReceiver.scope("numFollowedUsersLessThanMatwitter").counter()
  val numFollowedUsersMoreThanMatwitterStat: Stat = statsReceiver.stat("numFollowedUsersMoreThanMatwitter")
  val numFollowedUsersMoreThanMatwitterCounter =
    statsReceiver.scope("numFollowedUsersMoreThanMatwitter").counter()

  val realGraphAuthorWeightsSumProdStat: Stat = statsReceiver.stat("realGraphAuthorWeightsSumProd")
  val realGraphAuthorWeightsSumMinEtwitterpStat: Stat =
    statsReceiver.stat("realGraphAuthorWeightsSumMinEtwitterp")
  val realGraphAuthorWeightsSumP50EtwitterpStat: Stat =
    statsReceiver.stat("realGraphAuthorWeightsSumP50Etwitterp")
  val realGraphAuthorWeightsSumP95EtwitterpStat: Stat =
    statsReceiver.stat("realGraphAuthorWeightsSumP95Etwitterp")
  val numAuthorsWithoutRealgraphScoreStat: Stat =
    statsReceiver.stat("numAuthorsWithoutRealgraphScore")

  override def apply(envelope: CandidateEnvelope): Future[CandidateEnvelope] = {
    val realGraphData = envelope.query.earlybirdOptions
      .map(_.authorScoreAdjustments.authorScoreMap)
      .getOrElse(Map.empty)

    Future
      .join(
        envelope.followGraphData.followedUserIdsFuture,
        envelope.followGraphData.mutedUserIdsFuture
      ).map {
        case (followedUserIds, mutedUserIds) =>
          // Anti-dilution param for DDG-16198
          val recycledMatwitterFollowedUsersEnableAntiDilutionParamProvider =
            DependencyProvider.from(RecycledMatwitterFollowedUsersEnableAntiDilutionParam)

          val matwitterFollowedUsers = {
            if (followedUserIds.size > RecapQueryContetwittert.MatwitterFollowedUsers.default && recycledMatwitterFollowedUsersEnableAntiDilutionParamProvider(
                envelope.query)) {
              // trigger etwitterperiment
              matwitterFollowedUsersProvider(envelope.query)
            } else {
              matwitterFollowedUsersProvider(envelope.query)
            }
          }

          val filteredRealGraphUserIds = realGraphData.keySet
            .filterNot(mutedUserIds)
            .take(matwitterFollowedUsers)
            .toSeq

          val filteredFollowedUserIds = followedUserIds.filterNot(mutedUserIds)

          if (followedUserIds.size < matwitterFollowedUsers) {
            numFollowedUsersLessThanMatwitterStat.add(filteredFollowedUserIds.size)
            // stats
            numFollowedUsersLessThanMatwitterCounter.incr()
            (filteredFollowedUserIds, false)
          } else {
            numFollowedUsersMoreThanMatwitterStat.add(filteredFollowedUserIds.size)
            numFollowedUsersMoreThanMatwitterCounter.incr()
            if (enableRealGraphUsersProvider(envelope.query)) {
              val matwitterRealGraphAndFollowedUsersNum =
                matwitterRealGraphAndFollowedUsersProvider(envelope.query)
              require(
                matwitterRealGraphAndFollowedUsersNum >= matwitterFollowedUsers,
                "matwitterRealGraphAndFollowedUsers must be greater than or equal to matwitterFollowedUsers."
              )
              val recentFollowedUsersNum = RecapQueryContetwittert.MatwitterFollowedUsers.bounds
                .apply(matwitterRealGraphAndFollowedUsersNum - filteredRealGraphUserIds.size)

              val recentFollowedUsers =
                filteredFollowedUserIds
                  .filterNot(filteredRealGraphUserIds.contains)
                  .take(recentFollowedUsersNum)

              val filteredFollowAndRealGraphUserIds =
                recentFollowedUsers ++ filteredRealGraphUserIds

              // Track the size of recentFollowedUsers from SGS
              numFollowAndRealGraphUsersFromFollowGraphStat.add(recentFollowedUsers.size)
              // Track the size of filteredRealGraphUserIds from real graph dataset.
              numFollowAndRealGraphUsersFromRealGraphStat.add(filteredRealGraphUserIds.size)

              numFollowAndRealGraphUsersFromRealGraphCounter.incr()

              numFollowAndRealGraphUsersStat.add(filteredFollowAndRealGraphUserIds.size)

              (filteredFollowAndRealGraphUserIds, true)
            } else {
              numFollowedUsersStat.add(followedUserIds.size)
              (filteredFollowedUserIds, false)
            }
          }
      }.map {
        case (updatedFollowSeq, shouldUpdateMutualFollows) =>
          val updatedMutualFollowing = if (shouldUpdateMutualFollows) {
            followGraphDataProvider.getMutuallyFollowingUserIds(
              envelope.query.userId,
              updatedFollowSeq)
          } else {
            envelope.followGraphData.mutuallyFollowingUserIdsFuture
          }

          val followGraphData = envelope.followGraphData.copy(
            followedUserIdsFuture = Future.value(updatedFollowSeq),
            mutuallyFollowingUserIdsFuture = updatedMutualFollowing
          )

          val authorIdsWithRealgraphScore = realGraphData.keySet
          val authorIdsWithoutRealgraphScores =
            updatedFollowSeq.filterNot(authorIdsWithRealgraphScore.contains)

          //stat for logging the percentage of users' followings that do not have a realgraph score
          if (updatedFollowSeq.nonEmpty)
            numAuthorsWithoutRealgraphScoreStat.add(
              authorIdsWithoutRealgraphScores.size / updatedFollowSeq.size * 100)

          if (imputeRealGraphAuthorWeightsProvider(envelope.query) && realGraphData.nonEmpty) {
            val imputedScorePercentile =
              imputeRealGraphAuthorWeightsPercentileProvider(envelope.query) / 100.0
            val etwitteristingAuthorIdScores = realGraphData.values.toList.sorted
            val imputedScoreIndetwitter = Math.min(
              etwitteristingAuthorIdScores.length - 1,
              (etwitteristingAuthorIdScores.length * imputedScorePercentile).toInt)
            val imputedScore = etwitteristingAuthorIdScores(imputedScoreIndetwitter)

            val updatedAuthorScoreMap = realGraphData ++ authorIdsWithoutRealgraphScores
              .map(_ -> imputedScore).toMap
            imputedScorePercentile match {
              case 0.0 =>
                realGraphAuthorWeightsSumMinEtwitterpStat.add(updatedAuthorScoreMap.values.sum.toFloat)
              case 0.5 =>
                realGraphAuthorWeightsSumP50EtwitterpStat.add(updatedAuthorScoreMap.values.sum.toFloat)
              case 0.95 =>
                realGraphAuthorWeightsSumP95EtwitterpStat.add(updatedAuthorScoreMap.values.sum.toFloat)
              case _ =>
            }
            val earlybirdOptionsWithUpdatedAuthorScoreMap = envelope.query.earlybirdOptions
              .map(_.copy(authorScoreAdjustments = AuthorScoreAdjustments(updatedAuthorScoreMap)))
            val updatedQuery =
              envelope.query.copy(earlybirdOptions = earlybirdOptionsWithUpdatedAuthorScoreMap)
            envelope.copy(query = updatedQuery, followGraphData = followGraphData)
          } else {
            envelope.query.earlybirdOptions
              .map(_.authorScoreAdjustments.authorScoreMap.values.sum.toFloat).foreach {
                realGraphAuthorWeightsSumProdStat.add(_)
              }
            envelope.copy(followGraphData = followGraphData)
          }
      }
  }
}
