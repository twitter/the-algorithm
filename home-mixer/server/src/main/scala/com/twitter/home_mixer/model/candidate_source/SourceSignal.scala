package com.twitter.home_mixer.model.candidate_source

import com.twitter.usersignalservice.{thriftscala => se}

case class SourceSignal(
  id: Long,
  signalType: Option[String],
  signalEntity: Option[se.SignalEntity],
  authorId: Option[Long])
