package com.twitter.tweetypie.serverutil

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.servo
import com.twitter.servo.util.EtwitterceptionCategorizer

object EtwitterceptionCounter {
  // These throwables are alertable because they indicate conditions we never etwitterpect in production.
  def isAlertable(throwable: Throwable): Boolean =
    throwable match {
      case e: RuntimeEtwitterception => true
      case e: Error => true
      case _ => false
    }

  // count how many etwitterceptions are alertable and how many are boring
  val tweetypieCategorizers: EtwitterceptionCategorizer =
    EtwitterceptionCategorizer.const("alertableEtwitterception").onlyIf(isAlertable) ++
      EtwitterceptionCategorizer.const("boringEtwitterception").onlyIf(BoringStackTrace.isBoring)

  val defaultCategorizer: EtwitterceptionCategorizer =
    EtwitterceptionCategorizer.default() ++ tweetypieCategorizers

  def defaultCategorizer(name: String): EtwitterceptionCategorizer =
    EtwitterceptionCategorizer.default(Seq(name)) ++ tweetypieCategorizers

  def apply(statsReceiver: StatsReceiver): servo.util.EtwitterceptionCounter =
    new servo.util.EtwitterceptionCounter(statsReceiver, defaultCategorizer)

  def apply(statsReceiver: StatsReceiver, name: String): servo.util.EtwitterceptionCounter =
    new servo.util.EtwitterceptionCounter(statsReceiver, defaultCategorizer(name))

  def apply(
    statsReceiver: StatsReceiver,
    categorizer: EtwitterceptionCategorizer
  ): servo.util.EtwitterceptionCounter =
    new servo.util.EtwitterceptionCounter(statsReceiver, categorizer)
}
