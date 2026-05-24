package com.twitter.visibility.builder.users

import com.twitter.finagle.stats.Counter
import com.twitter.gizmoduck.thriftscala.Perspective
import com.twitter.gizmoduck.thriftscala.User
import com.twitter.stitch.Stitch
import com.twitter.visibility.common.UserId

case object ViewerVerbsAuthor {
  def apply(
    authorId: UserId,
    viewerIdOpt: Option[UserId],
    relationship: (UserId, UserId) => Stitch[Boolean],
    relationshipCounter: Counter
  ): Stitch[Boolean] = {
    relationshipCounter.incr()

    viewerIdOpt match {
      case Some(viewerId) => relationship(viewerId, authorId)
      case _ => Stitch.False
    }
  }

  def apply(
    author: User,
    viewerId: Option[UserId],
    checkPerspective: Perspective => Option[Boolean],
    relationship: (UserId, UserId) => Stitch[Boolean],
    relationshipCounter: Counter
  ): Stitch[Boolean] = {
    author.perspective match {
      case Some(perspective) =>
        checkPerspective(perspective) match {
          case Some(status) =>
            relationshipCounter.incr()
            Stitch.value(status)
          case None =>
            ViewerVerbsAuthor(author.id, viewerId, relationship, relationshipCounter)
        }
      case None => ViewerVerbsAuthor(author.id, viewerId, relationship, relationshipCounter)
    }
  }
}

case object AuthorVerbsViewer {

  def apply(
    authorId: UserId,
    viewerIdOpt: Option[UserId],
    relationship: (UserId, UserId) => Stitch[Boolean],
    relationshipCounter: Counter
  ): Stitch[Boolean] = {
    relationshipCounter.incr()

    viewerIdOpt match {
      case Some(viewerId) => relationship(authorId, viewerId)
      case _ => Stitch.False
    }
  }
  def apply(
    author: User,
    viewerId: Option[UserId],
    checkPerspective: Perspective => Option[Boolean],
    relationship: (UserId, UserId) => Stitch[Boolean],
    relationshipCounter: Counter
  ): Stitch[Boolean] = {
    author.perspective match {
      case Some(perspective) =>
        checkPerspective(perspective) match {
          case Some(status) =>
            relationshipCounter.incr()
            Stitch.value(status)
          case None =>
            AuthorVerbsViewer(author.id, viewerId, relationship, relationshipCounter)
        }
      case None => AuthorVerbsViewer(author.id, viewerId, relationship, relationshipCounter)
    }
  }
}
