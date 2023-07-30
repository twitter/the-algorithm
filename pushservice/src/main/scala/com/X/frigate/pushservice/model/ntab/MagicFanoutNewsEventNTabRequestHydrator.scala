package com.X.frigate.pushservice.model.ntab

import com.X.frigate.pushservice.model.PushTypes.PushCandidate
import com.X.frigate.pushservice.model.MagicFanoutEventHydratedCandidate
import com.X.notificationservice.thriftscala.DisplayTextEntity
import com.X.notificationservice.thriftscala.TextValue
import com.X.util.Future

trait MagicFanoutNewsEventNTabRequestHydrator extends EventNTabRequestHydrator {
  self: PushCandidate with MagicFanoutEventHydratedCandidate =>
  override lazy val tapThroughFut: Future[String] = Future.value(s"i/events/$eventId")
  override lazy val displayTextEntitiesFut: Future[Seq[DisplayTextEntity]] =
    eventTitleFut.map { eventTitle =>
      Seq(DisplayTextEntity(name = "title", value = TextValue.Text(eventTitle)))
    }
}
