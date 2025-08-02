package com.twitter.cr_mitwitterer.param

import scala.language.implicitConversions

object UnifiedSETweetCombinationMethod etwittertends Enumeration {

  protected case class CombinationType(s: String) etwittertends super.Val

  implicit def valueToCombinationType(twitter: Value): CombinationType = twitter.asInstanceOf[CombinationType]

  val Default: Value = CombinationType("")
  val Interleave: Value = CombinationType("Interleave")
  val Frontload: Value = CombinationType("Frontload")
  val Backfill: Value = CombinationType("Backfill")
}
