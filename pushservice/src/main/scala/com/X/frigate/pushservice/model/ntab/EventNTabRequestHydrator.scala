package com.X.frigate.pushservice.model.ntab

import com.X.frigate.pushservice.model.PushTypes.PushCandidate
import com.X.notificationservice.thriftscala.DisplayText
import com.X.notificationservice.thriftscala.InlineCard
import com.X.notificationservice.thriftscala.StoryContext
import com.X.util.Future

trait EventNTabRequestHydrator extends NTabRequestHydrator {
  self: PushCandidate =>

  override def senderIdFut: Future[Long] = Future.value(0L)

  override def facepileUsersFut: Future[Seq[Long]] = Future.Nil

  override val storyContext: Option[StoryContext] = None

  override val inlineCard: Option[InlineCard] = None

  override val socialProofDisplayText: Option[DisplayText] = Some(DisplayText())
}
