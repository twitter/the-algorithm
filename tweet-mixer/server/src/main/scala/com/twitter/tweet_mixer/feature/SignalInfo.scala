package com.twitter.tweet_mixer.feature

import com.twitter.usersignalservice.thriftscala._
import com.twitter.util.Time

case class SignalInfo(
  signalEntity: SignalEntity,
  signalType: SignalType,
  sourceEventTime: Option[Time],
  authorId: Option[Long] = None,
)
