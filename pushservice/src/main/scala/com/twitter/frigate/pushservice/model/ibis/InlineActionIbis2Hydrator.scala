package com.twitter.frigate.pushservice.model.ibis

import com.twitter.frigate.pushservice.model.PushTypes.PushCandidate
import com.twitter.frigate.pushservice.util.InlineActionUtil
import com.twitter.util.Future

trait InlineActionIbis2Hydrator {
  self: PushCandidate =>

  lazy val tweetInlineActionModelValue: Future[Map[String, String]] =
    InlineActionUtil.getTweetInlineActionValue(target)
}
