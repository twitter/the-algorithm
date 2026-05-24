package com.twitter.frigate.pushservice.util

import com.twitter.frigate.common.base.TweetAuthor
import com.twitter.frigate.pushservice.model.PushTypes.RawCandidate
import com.twitter.hermit.predicate.socialgraph.Edge
import com.twitter.hermit.predicate.socialgraph.RelationEdge
import com.twitter.socialgraph.thriftscala.RelationshipType

/**
 * This class provides utility functions for relationshipEdge for each Candidate type.
 */
object RelationshipUtil {

  /**
   * Form relationEdges
   * @param candidate PushCandidate
   * @param relationship relationshipTypes for different candidate types
   * @return relationEdges for different candidate types
   */
  private def formRelationEdgeWithTargetIdAndAuthorId(
    candidate: RawCandidate,
    relationship: List[RelationshipType with Product]
  ): List[RelationEdge] = {
    candidate match {
      case candidate: RawCandidate with TweetAuthor =>
        candidate.authorId match {
          case Some(authorId) =>
            val edge = Edge(candidate.target.targetId, authorId)
            for {
              r <- relationship
            } yield RelationEdge(edge, r)
          case _ => List.empty[RelationEdge]
        }
      case _ => List.empty[RelationEdge]
    }
  }

  /**
   * Form all relationshipEdges for basicTweetRelationShips
   * @param candidate PushCandidate
   * @return List of relationEdges for basicTweetRelationShips
   */
  def getBasicTweetRelationships(candidate: RawCandidate): List[RelationEdge] = {
    val relationship = List(
      RelationshipType.DeviceFollowing,
      RelationshipType.Blocking,
      RelationshipType.BlockedBy,
      RelationshipType.HideRecommendations,
      RelationshipType.Muting)
    formRelationEdgeWithTargetIdAndAuthorId(candidate, relationship)
  }

  /**
   * Form all relationshipEdges for F1tweetsRelationships
   * @param candidate PushCandidate
   * @return List of relationEdges for F1tweetsRelationships
   */
  def getPreCandidateRelationshipsForInNetworkTweets(
    candidate: RawCandidate
  ): List[RelationEdge] = {
    val relationship = List(RelationshipType.Following)
    getBasicTweetRelationships(candidate) ++ formRelationEdgeWithTargetIdAndAuthorId(
      candidate,
      relationship)
  }
}
