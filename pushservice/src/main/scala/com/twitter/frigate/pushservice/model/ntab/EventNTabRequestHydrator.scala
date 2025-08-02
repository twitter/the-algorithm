package com.twitter.frigate.pushservice.model.ntab

import com.twitter.frigate.pushservice.model.PushTypes.PushCandidate
import com.twitter.notificationservice.thriftscala.DisplayTetwittert
import com.twitter.notificationservice.thriftscala.InlineCard
import com.twitter.notificationservice.thriftscala.StoryContetwittert
import com.twitter.util.Future

trait EventNTabRequestHydrator etwittertends NTabRequestHydrator {
  self: PushCandidate =>

  override def senderIdFut: Future[Long] = Future.value(0L)

  override def facepileUsersFut: Future[Seq[Long]] = Future.Nil

  override val storyContetwittert: Option[StoryContetwittert] = None

  override val inlineCard: Option[InlineCard] = None

  override val socialProofDisplayTetwittert: Option[DisplayTetwittert] = Some(DisplayTetwittert())
}
