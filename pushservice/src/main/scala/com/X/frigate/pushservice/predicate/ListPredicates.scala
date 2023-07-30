package com.X.frigate.pushservice.predicate

import com.X.finagle.stats.StatsReceiver
import com.X.frigate.pushservice.model.ListRecommendationPushCandidate
import com.X.frigate.pushservice.params.PushFeatureSwitchParams
import com.X.hermit.predicate.socialgraph.Edge
import com.X.hermit.predicate.socialgraph.RelationEdge
import com.X.hermit.predicate.socialgraph.SocialGraphPredicate
import com.X.hermit.predicate.NamedPredicate
import com.X.hermit.predicate.Predicate
import com.X.socialgraph.thriftscala.RelationshipType
import com.X.storehaus.ReadableStore
import com.X.util.Future

object ListPredicates {

  def listNameExistsPredicate(
  )(
    implicit stats: StatsReceiver
  ): NamedPredicate[ListRecommendationPushCandidate] = {
    Predicate
      .fromAsync { candidate: ListRecommendationPushCandidate =>
        candidate.listName.map(_.isDefined)
      }
      .withStats(stats)
      .withName("list_name_exists")
  }

  def listAuthorExistsPredicate(
  )(
    implicit stats: StatsReceiver
  ): NamedPredicate[ListRecommendationPushCandidate] = {
    Predicate
      .fromAsync { candidate: ListRecommendationPushCandidate =>
        candidate.listOwnerId.map(_.isDefined)
      }
      .withStats(stats)
      .withName("list_owner_exists")
  }

  def listAuthorAcceptableToTargetUser(
    edgeStore: ReadableStore[RelationEdge, Boolean]
  )(
    implicit statsReceiver: StatsReceiver
  ): NamedPredicate[ListRecommendationPushCandidate] = {
    val name = "list_author_acceptable_to_target_user"
    val sgsPredicate = SocialGraphPredicate
      .anyRelationExists(
        edgeStore,
        Set(
          RelationshipType.Blocking,
          RelationshipType.BlockedBy,
          RelationshipType.Muting
        )
      )
      .withStats(statsReceiver.scope("list_sgs_any_relation_exists"))
      .withName("list_sgs_any_relation_exists")

    Predicate
      .fromAsync { candidate: ListRecommendationPushCandidate =>
        candidate.listOwnerId.flatMap {
          case Some(ownerId) =>
            sgsPredicate.apply(Seq(Edge(candidate.target.targetId, ownerId))).map(_.head)
          case _ => Future.True
        }
      }
      .withStats(statsReceiver.scope(s"predicate_$name"))
      .withName(name)
  }

  /**
   * Checks if the list is acceptable to Target user =>
   *    - Is Target not following the list
   *    - Is Target not muted the list
   */
  def listAcceptablePredicate(
  )(
    implicit stats: StatsReceiver
  ): NamedPredicate[ListRecommendationPushCandidate] = {
    val name = "list_acceptable_to_target_user"
    Predicate
      .fromAsync { candidate: ListRecommendationPushCandidate =>
        candidate.apiList.map {
          case Some(apiList) =>
            !(apiList.following.contains(true) || apiList.muting.contains(true))
          case _ => false
        }
      }
      .withStats(stats.scope(name))
      .withName(name)
  }

  def listSubscriberCountPredicate(
  )(
    implicit stats: StatsReceiver
  ): NamedPredicate[ListRecommendationPushCandidate] = {
    val name = "list_subscribe_count"
    Predicate
      .fromAsync { candidate: ListRecommendationPushCandidate =>
        candidate.apiList.map { apiListOpt =>
          apiListOpt.exists { apiList =>
            apiList.subscriberCount >= candidate.target.params(
              PushFeatureSwitchParams.ListRecommendationsSubscriberCount)
          }
        }
      }
      .withStats(stats.scope(name))
      .withName(name)
  }
}
