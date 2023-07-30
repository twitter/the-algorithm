package com.X.frigate.pushservice.model.ntab

import com.X.frigate.pushservice.model.ListRecommendationPushCandidate
import com.X.notificationservice.thriftscala.DisplayText
import com.X.notificationservice.thriftscala.DisplayTextEntity
import com.X.notificationservice.thriftscala.InlineCard
import com.X.notificationservice.thriftscala.StoryContext
import com.X.notificationservice.thriftscala.TextValue
import com.X.util.Future

trait ListCandidateNTabRequestHydrator extends NTabRequestHydrator {

  self: ListRecommendationPushCandidate =>

  override lazy val senderIdFut: Future[Long] =
    listOwnerId.map(_.getOrElse(0L))

  override lazy val facepileUsersFut: Future[Seq[Long]] = Future.Nil

  override lazy val storyContext: Option[StoryContext] = None

  override lazy val inlineCard: Option[InlineCard] = None

  override lazy val tapThroughFut: Future[String] = Future.value(s"i/lists/${listId}")

  override lazy val displayTextEntitiesFut: Future[Seq[DisplayTextEntity]] = listName.map {
    listNameOpt =>
      listNameOpt.toSeq.map { name =>
        DisplayTextEntity(name = "title", value = TextValue.Text(name))
      }
  }

  override val socialProofDisplayText: Option[DisplayText] = Some(DisplayText())
}
