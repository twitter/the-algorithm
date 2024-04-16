package com.tw ter.cr_m xer.serv ce

 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.Dest nat on
 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.Not f cat onGroup

/**
 * Not f cat ons (ema l, pagerduty, etc) can be spec f c per-alert but    s common for mult ple
 * products to share not f cat on conf gurat on.
 *
 *   conf gurat on uses only ema l not f cat ons because SampleM xer  s a demonstrat on serv ce
 * w h ne  r  nternal nor custo r-fac ng users.   w ll l kely want to use a PagerDuty
 * dest nat on  nstead. For example:
 * {{{
 *   cr  cal = Dest nat on(pagerDutyKey = So ("y -pagerduty-key"))
 * }}}
 *
 *
 * For more  nformat on about how to get a PagerDuty key, see:
 * https://docb rd.tw ter.b z/mon/how-to-gu des.html?h ghl ght=not f cat ongroup#set-up-ema l-pagerduty-and-slack-not f cat ons
 */
object CrM xerAlertNot f cat onConf g {
  val DefaultNot f cat onGroup: Not f cat onGroup = Not f cat onGroup(
    warn = Dest nat on(ema ls = Seq("no-reply@tw ter.com")),
    cr  cal = Dest nat on(ema ls = Seq("no-reply@tw ter.com"))
  )
}
