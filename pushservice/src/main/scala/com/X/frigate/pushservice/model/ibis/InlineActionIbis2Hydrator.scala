package com.X.frigate.pushservice.model.ibis

import com.X.frigate.pushservice.model.PushTypes.PushCandidate
import com.X.frigate.pushservice.util.InlineActionUtil
import com.X.util.Future

trait InlineActionIbis2Hydrator {
  self: PushCandidate =>

  lazy val tweetInlineActionModelValue: Future[Map[String, String]] =
    InlineActionUtil.getTweetInlineActionValue(target)
}
