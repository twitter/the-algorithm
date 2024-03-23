package com.ExTwitter.follow_recommendations.common.predicates.sgs

import com.google.common.annotations.VisibleForTesting
import com.ExTwitter.finagle.stats.NullStatsReceiver
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.follow_recommendations.common.base.Predicate
import com.ExTwitter.follow_recommendations.common.base.PredicateResult
import com.ExTwitter.follow_recommendations.common.models.CandidateUser
import com.ExTwitter.follow_recommendations.common.models.HasProfileId
import com.ExTwitter.follow_recommendations.common.models.FilterReason.FailOpen
import com.ExTwitter.follow_recommendations.common.models.FilterReason.InvalidRelationshipTypes
import com.ExTwitter.product_mixer.core.model.marshalling.request.HasClientContext
import com.ExTwitter.socialgraph.thriftscala.ExistsRequest
import com.ExTwitter.socialgraph.thriftscala.ExistsResult
import com.ExTwitter.socialgraph.thriftscala.LookupContext
import com.ExTwitter.socialgraph.thriftscala.Relationship
import com.ExTwitter.socialgraph.thriftscala.RelationshipType
import com.ExTwitter.stitch.Stitch
import com.ExTwitter.stitch.socialgraph.SocialGraph
import com.ExTwitter.timelines.configapi.HasParams
import com.ExTwitter.util.TimeoutException
import com.ExTwitter.util.logging.Logging

import javax.inject.Inject
import javax.inject.Singleton

case class RelationshipMapping(
  relationshipType: RelationshipType,
  includeBasedOnRelationship: Boolean)

class SgsRelationshipsPredicate(
  socialGraph: SocialGraph,
  relationshipMappings: Seq[RelationshipMapping],
  statsReceiver: StatsReceiver = NullStatsReceiver)
    extends Predicate[(HasClientContext with HasParams, CandidateUser)]
    with Logging {

  private val stats: StatsReceiver = statsReceiver.scope(this.getClass.getSimpleName)

  override def apply(
    pair: (HasClientContext with HasParams, CandidateUser)
  ): Stitch[PredicateResult] = {
    val (target, candidate) = pair
    val timeout = target.params(SgsPredicateParams.SgsRelationshipsPredicateTimeout)
    SgsRelationshipsPredicate
      .extractUserId(target)
      .map { id =>
        val relationships = relationshipMappings.map { relationshipMapping: RelationshipMapping =>
          Relationship(
            relationshipMapping.relationshipType,
            relationshipMapping.includeBasedOnRelationship)
        }
        val existsRequest = ExistsRequest(
          id,
          candidate.id,
          relationships = relationships,
          context = SgsRelationshipsPredicate.UnionLookupContext
        )
        socialGraph
          .exists(existsRequest).map { existsResult: ExistsResult =>
            if (existsResult.exists) {
              PredicateResult.Invalid(Set(InvalidRelationshipTypes(relationshipMappings
                .map { relationshipMapping: RelationshipMapping =>
                  relationshipMapping.relationshipType
                }.mkString(", "))))
            } else {
              PredicateResult.Valid
            }
          }
          .within(timeout)(com.ExTwitter.finagle.util.DefaultTimer)
      }
      // if no user id is present, return true by default
      .getOrElse(Stitch.value(PredicateResult.Valid))
      .rescue {
        case e: TimeoutException =>
          stats.counter("timeout").incr()
          Stitch(PredicateResult.Invalid(Set(FailOpen)))
        case e: Exception =>
          stats.counter(e.getClass.getSimpleName).incr()
          Stitch(PredicateResult.Invalid(Set(FailOpen)))
      }

  }
}

object SgsRelationshipsPredicate {
  // OR Operation
  @VisibleForTesting
  private[follow_recommendations] val UnionLookupContext = Some(
    LookupContext(performUnion = Some(true)))

  private def extractUserId(target: HasClientContext with HasParams): Option[Long] = target match {
    case profRequest: HasProfileId => Some(profRequest.profileId)
    case userRequest: HasClientContext with HasParams => userRequest.getOptionalUserId
    case _ => None
  }
}

@Singleton
class InvalidTargetCandidateRelationshipTypesPredicate @Inject() (
  socialGraph: SocialGraph)
    extends SgsRelationshipsPredicate(
      socialGraph,
      InvalidRelationshipTypesPredicate.InvalidRelationshipTypes) {}

@Singleton
class NoteworthyAccountsSgsPredicate @Inject() (
  socialGraph: SocialGraph)
    extends SgsRelationshipsPredicate(
      socialGraph,
      InvalidRelationshipTypesPredicate.NoteworthyAccountsInvalidRelationshipTypes)

object InvalidRelationshipTypesPredicate {

  val InvalidRelationshipTypesExcludeFollowing: Seq[RelationshipMapping] = Seq(
    RelationshipMapping(RelationshipType.HideRecommendations, true),
    RelationshipMapping(RelationshipType.Blocking, true),
    RelationshipMapping(RelationshipType.BlockedBy, true),
    RelationshipMapping(RelationshipType.Muting, true),
    RelationshipMapping(RelationshipType.MutedBy, true),
    RelationshipMapping(RelationshipType.ReportedAsSpam, true),
    RelationshipMapping(RelationshipType.ReportedAsSpamBy, true),
    RelationshipMapping(RelationshipType.ReportedAsAbuse, true),
    RelationshipMapping(RelationshipType.ReportedAsAbuseBy, true)
  )

  val InvalidRelationshipTypes: Seq[RelationshipMapping] = Seq(
    RelationshipMapping(RelationshipType.FollowRequestOutgoing, true),
    RelationshipMapping(RelationshipType.Following, true),
    RelationshipMapping(
      RelationshipType.UsedToFollow,
      true
    ) // this data is accessible for 90 days.
  ) ++ InvalidRelationshipTypesExcludeFollowing

  val NoteworthyAccountsInvalidRelationshipTypes: Seq[RelationshipMapping] = Seq(
    RelationshipMapping(RelationshipType.Blocking, true),
    RelationshipMapping(RelationshipType.BlockedBy, true),
    RelationshipMapping(RelationshipType.Muting, true),
    RelationshipMapping(RelationshipType.MutedBy, true),
    RelationshipMapping(RelationshipType.ReportedAsSpam, true),
    RelationshipMapping(RelationshipType.ReportedAsSpamBy, true),
    RelationshipMapping(RelationshipType.ReportedAsAbuse, true),
    RelationshipMapping(RelationshipType.ReportedAsAbuseBy, true)
  )
}
