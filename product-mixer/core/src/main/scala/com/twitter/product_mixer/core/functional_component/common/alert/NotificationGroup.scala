package com.tw ter.product_m xer.core.funct onal_component.common.alert

 mport com.tw ter.ut l.Try
 mport javax.ma l. nternet. nternetAddress

/**
 * Dest nat on represents t  place to wh ch alerts w ll be sent. Often   w ll only need one f eld
 * populated (e  r a Pager Duty key or a l st of ema ls).
 *
 * See t  Mon or ng 2.0 docs for more  nformat on on [[https://docb rd.tw ter.b z/mon/how-to-gu des.html?h ghl ght=not f cat ongroup#set-up-ema l-pagerduty-and-slack-not f cat ons sett ng up a Pager Duty rotat on]]
 */
case class Dest nat on(
  pagerDutyKey: Opt on[Str ng] = None,
  ema ls: Seq[Str ng] = Seq.empty) {

  requ re(
    pagerDutyKey.forall(_.length == 32),
    s"Expected `pagerDutyKey` to be 32 characters long but got `$pagerDutyKey`")
  ema ls.foreach { ema l =>
    requ re(
      Try(new  nternetAddress(ema l).val date()). sReturn,
      s"Expected only val d ema l addresses but got an  nval d ema l address: `$ema l`")
  }
  requ re(
    pagerDutyKey.nonEmpty || ema ls.nonEmpty,
    s"Expected a `pagerDutyKey` or at least 1 ema l address but got ne  r")
}

/**
 * Not f cat onGroup maps alert levels to dest nat ons. Hav ng d fferent dest nat ons based on t 
 * urgency of t  alert can so t  s be useful. For example,   could have a dayt   on-call for
 * warn alerts and a 24 on-call for cr  cal alerts.
 */
case class Not f cat onGroup(
  cr  cal: Dest nat on,
  warn: Dest nat on)
