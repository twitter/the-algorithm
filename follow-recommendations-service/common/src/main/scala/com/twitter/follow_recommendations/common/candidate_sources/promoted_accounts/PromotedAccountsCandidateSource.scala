package com.twitter.follow_recommendations.common.candidate_sources.promoted_accounts

import com.twitter.adserver.thriftscala.AdServerEtwitterception
import com.twitter.adserver.{thriftscala => adthrift}
import com.twitter.finagle.TimeoutEtwitterception
import com.twitter.finagle.stats.Counter
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.follow_recommendations.common.clients.adserver.AdRequest
import com.twitter.follow_recommendations.common.clients.adserver.AdserverClient
import com.twitter.follow_recommendations.common.clients.socialgraph.SocialGraphClient
import com.twitter.follow_recommendations.common.models.FollowProof
import com.twitter.hermit.model.Algorithm
import com.twitter.inject.Logging
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.stitch.Stitch
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

case class PromotedCandidateUser(
  id: Long,
  position: Int,
  adImpression: adthrift.AdImpression,
  followProof: FollowProof,
  primaryCandidateSource: Option[CandidateSourceIdentifier])

@Singleton
class PromotedAccountsCandidateSource @Inject() (
  adserverClient: AdserverClient,
  sgsClient: SocialGraphClient,
  statsReceiver: StatsReceiver)
    etwittertends CandidateSource[AdRequest, PromotedCandidateUser]
    with Logging {

  override val identifier: CandidateSourceIdentifier =
    PromotedAccountsCandidateSource.Identifier

  val stats: StatsReceiver = statsReceiver.scope(identifier.name)
  val failureStat: StatsReceiver = stats.scope("failures")
  val adServerEtwitterceptionsCounter: Counter = failureStat.counter("AdServerEtwitterception")
  val timeoutCounter: Counter = failureStat.counter("TimeoutEtwitterception")

  def apply(request: AdRequest): Stitch[Seq[PromotedCandidateUser]] = {
    adserverClient
      .getAdImpressions(request)
      .rescue {
        case e: TimeoutEtwitterception =>
          timeoutCounter.incr()
          logger.warn("Timeout on Adserver", e)
          Stitch.Nil
        case e: AdServerEtwitterception =>
          adServerEtwitterceptionsCounter.incr()
          logger.warn("Failed to fetch ads", e)
          Stitch.Nil
      }
      .flatMap { adImpressions: Seq[adthrift.AdImpression] =>
        profileNumResults(adImpressions.size, "results_from_ad_server")
        val idToImpMap = (for {
          imp <- adImpressions
          promotedAccountId <- imp.promotedAccountId
        } yield promotedAccountId -> imp).toMap
        request.clientContetwittert.userId
          .map { userId =>
            sgsClient
              .getIntersections(
                userId,
                adImpressions.filter(shouldShowSocialContetwittert).flatMap(_.promotedAccountId),
                PromotedAccountsCandidateSource.NumIntersections
              ).map { promotedAccountWithIntersections =>
                idToImpMap.map {
                  case (promotedAccountId, imp) =>
                    PromotedCandidateUser(
                      promotedAccountId,
                      imp.insertionPosition
                        .map(_.toInt).getOrElse(
                          getInsertionPositionDefaultValue(request.isTest.getOrElse(false))
                        ),
                      imp,
                      promotedAccountWithIntersections
                        .getOrElse(promotedAccountId, FollowProof(Nil, 0)),
                      Some(identifier)
                    )
                }.toSeq
              }.onSuccess(result => profileNumResults(result.size, "final_results"))
          }.getOrElse(Stitch.Nil)
      }
  }

  private def shouldShowSocialContetwittert(imp: adthrift.AdImpression): Boolean =
    imp.etwitterperimentValues.etwitterists { etwitterpValues =>
      etwitterpValues.get("display.display_style").contains("show_social_contetwittert")
    }

  private def getInsertionPositionDefaultValue(isTest: Boolean): Int = {
    if (isTest) 0 else -1
  }

  private def profileNumResults(resultsSize: Int, statName: String): Unit = {
    if (resultsSize <= 5) {
      stats.scope(statName).counter(resultsSize.toString).incr()
    } else {
      stats.scope(statName).counter("more_than_5").incr()
    }
  }
}

object PromotedAccountsCandidateSource {
  val Identifier: CandidateSourceIdentifier = CandidateSourceIdentifier(
    Algorithm.PromotedAccount.toString)
  val NumIntersections = 3
}
