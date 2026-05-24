package com.twitter.cr_mixer.param

import scala.language.implicitConversions

object UnifiedSETweetCombinationMethod extends Enumeration {

  protected case class CombinationType(s: String) extends super.Val

  implicit def valueToCombinationType(x: Value): CombinationType = x.asInstanceOf[CombinationType]

  val Default: Value = CombinationType("")
  val Interleave: Value = CombinationType("Interleave")
  val Frontload: Value = CombinationType("Frontload")
  val Backfill: Value = CombinationType("Backfill")
}
