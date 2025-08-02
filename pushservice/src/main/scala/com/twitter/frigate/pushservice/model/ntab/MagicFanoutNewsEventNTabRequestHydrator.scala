package com.twitter.frigate.pushservice.model.ntab

import com.twitter.frigate.pushservice.model.PushTypes.PushCandidate
import com.twitter.frigate.pushservice.model.MagicFanoutEventHydratedCandidate
import com.twitter.notificationservice.thriftscala.DisplayTetwittertEntity
import com.twitter.notificationservice.thriftscala.TetwittertValue
import com.twitter.util.Future

trait MagicFanoutNewsEventNTabRequestHydrator etwittertends EventNTabRequestHydrator {
  self: PushCandidate with MagicFanoutEventHydratedCandidate =>
  override lazy val tapThroughFut: Future[String] = Future.value(s"i/events/$eventId")
  override lazy val displayTetwittertEntitiesFut: Future[Seq[DisplayTetwittertEntity]] =
    eventTitleFut.map { eventTitle =>
      Seq(DisplayTetwittertEntity(name = "title", value = TetwittertValue.Tetwittert(eventTitle)))
    }
}
