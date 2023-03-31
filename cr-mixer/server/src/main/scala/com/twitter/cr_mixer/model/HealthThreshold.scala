package com.twitter.cr_mixer.model

object HealthThreshold {
  object Enum extends Enumeration {
    val Off: Value = Value(1)
    val Moderate: Value = Value(2)
    val Strict: Value = Value(3)
    val Stricter: Value = Value(4)
    val StricterPlus: Value = Value(5)
  }
}
