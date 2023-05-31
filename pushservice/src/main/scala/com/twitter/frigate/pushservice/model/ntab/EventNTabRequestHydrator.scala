package com.twitter.frigate.pushservice.model.ntab

import com.twitter.frigate.pushservice.model.PushTypes.PushCandidate
import com.twitter.notificationservice.thriftscala.DisplayText
import com.twitter.notificationservice.thriftscala.InlineCard
import com.twitter.notificationservice.thriftscala.StoryContext
import com.twitter.util.Future

trait EventNTabRequestHydrator extends NTabRequestHydrator {
  self: PushCandidate =>

  override def senderIdFut: Future[Long] = Future.value(0L)

  override def facepileUsersFut: Future[Seq[Long]] = Future.Nil

  override val storyContext: Option[StoryContext] = None

  override val inlineCard: Option[InlineCard] = None

  override val socialProofDisplayText: Option[DisplayText] = Some(DisplayText())
}
