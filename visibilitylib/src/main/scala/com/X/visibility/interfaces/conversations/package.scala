package com.X.visibility.interfaces

import com.X.servo.repository.KeyValueRepository
import com.X.spam.rtf.thriftscala.SafetyLabel
import com.X.spam.rtf.thriftscala.SafetyLabelType
import scala.collection.Map

package object conversations {
  type BatchSafetyLabelRepository =
    KeyValueRepository[(Long, Seq[Long]), Long, Map[SafetyLabelType, SafetyLabel]]
}
