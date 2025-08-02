package com.twitter.frigate.pushservice.model.ntab

import com.twitter.frigate.pushservice.model.ListRecommendationPushCandidate
import com.twitter.notificationservice.thriftscala.DisplayTetwittert
import com.twitter.notificationservice.thriftscala.DisplayTetwittertEntity
import com.twitter.notificationservice.thriftscala.InlineCard
import com.twitter.notificationservice.thriftscala.StoryContetwittert
import com.twitter.notificationservice.thriftscala.TetwittertValue
import com.twitter.util.Future

trait ListCandidateNTabRequestHydrator etwittertends NTabRequestHydrator {

  self: ListRecommendationPushCandidate =>

  override lazy val senderIdFut: Future[Long] =
    listOwnerId.map(_.getOrElse(0L))

  override lazy val facepileUsersFut: Future[Seq[Long]] = Future.Nil

  override lazy val storyContetwittert: Option[StoryContetwittert] = None

  override lazy val inlineCard: Option[InlineCard] = None

  override lazy val tapThroughFut: Future[String] = Future.value(s"i/lists/${listId}")

  override lazy val displayTetwittertEntitiesFut: Future[Seq[DisplayTetwittertEntity]] = listName.map {
    listNameOpt =>
      listNameOpt.toSeq.map { name =>
        DisplayTetwittertEntity(name = "title", value = TetwittertValue.Tetwittert(name))
      }
  }

  override val socialProofDisplayTetwittert: Option[DisplayTetwittert] = Some(DisplayTetwittert())
}
