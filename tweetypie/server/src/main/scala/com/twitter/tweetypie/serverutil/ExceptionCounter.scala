package com.tw ter.t etyp e.serverut l

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.servo
 mport com.tw ter.servo.ut l.Except onCategor zer

object Except onCounter {
  // T se throwables are alertable because t y  nd cate cond  ons   never expect  n product on.
  def  sAlertable(throwable: Throwable): Boolean =
    throwable match {
      case e: Runt  Except on => true
      case e: Error => true
      case _ => false
    }

  // count how many except ons are alertable and how many are bor ng
  val t etyp eCategor zers: Except onCategor zer =
    Except onCategor zer.const("alertableExcept on").only f( sAlertable) ++
      Except onCategor zer.const("bor ngExcept on").only f(Bor ngStackTrace. sBor ng)

  val defaultCategor zer: Except onCategor zer =
    Except onCategor zer.default() ++ t etyp eCategor zers

  def defaultCategor zer(na : Str ng): Except onCategor zer =
    Except onCategor zer.default(Seq(na )) ++ t etyp eCategor zers

  def apply(statsRece ver: StatsRece ver): servo.ut l.Except onCounter =
    new servo.ut l.Except onCounter(statsRece ver, defaultCategor zer)

  def apply(statsRece ver: StatsRece ver, na : Str ng): servo.ut l.Except onCounter =
    new servo.ut l.Except onCounter(statsRece ver, defaultCategor zer(na ))

  def apply(
    statsRece ver: StatsRece ver,
    categor zer: Except onCategor zer
  ): servo.ut l.Except onCounter =
    new servo.ut l.Except onCounter(statsRece ver, categor zer)
}
