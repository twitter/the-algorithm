package com.twitter.frigate.pushservice.model.ntab

import com.twitter.frigate.pushservice.model.PushTypes.PushCandidate
import com.twitter.frigate.pushservice.model.MagicFanoutEventHydratedCandidate
import com.twitter.notificationservice.thriftscala.DisplayTextEntity
import com.twitter.notificationservice.thriftscala.TextValue
import com.twitter.util.Future

trait MagicFanoutNewsEventNTabRequestHydrator extends EventNTabRequestHydrator {
  self: PushCandidate with MagicFanoutEventHydratedCandidate =>
  override lazy val tapThroughFut: Future[String] = Future.value(s"i/events/$eventId")
  override lazy val displayTextEntitiesFut: Future[Seq[DisplayTextEntity]] =
    eventTitleFut.map { eventTitle =>
      Seq(DisplayTextEntity(name = "title", value = TextValue.Text(eventTitle)))
    }
}
