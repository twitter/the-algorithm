package com.twitter.visibility.interfaces

import com.twitter.servo.repository.KeyValueRepository
import com.twitter.spam.rtf.thriftscala.SafetyLabel
import com.twitter.spam.rtf.thriftscala.SafetyLabelType
import scala.collection.Map

package object conversations {
  type BatchSafetyLabelRepository =
    KeyValueRepository[(Long, Seq[Long]), Long, Map[SafetyLabelType, SafetyLabel]]
}
