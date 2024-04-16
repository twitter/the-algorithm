package com.tw ter.ho _m xer.serv ce

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.Dest nat on
 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.EmptyResponseRateAlert
 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.LatencyAlert
 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.Not f cat onGroup
 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.P99
 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.Percent le
 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.SuccessRateAlert
 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.pred cate.Tr gger fAbove
 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.pred cate.Tr gger fBelow
 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.pred cate.Tr gger fLatencyAbove
 mport com.tw ter.ut l.Durat on

/**
 * Not f cat ons (ema l, pagerduty, etc) can be spec f c per-alert but    s common for mult ple
 * products to share not f cat on conf gurat on.
 */
object Ho M xerAlertConf g {
  val DefaultNot f cat onGroup: Not f cat onGroup = Not f cat onGroup(
    warn = Dest nat on(ema ls = Seq("")),
    cr  cal = Dest nat on(ema ls = Seq(""))
  )

  object Bus nessH s {
    val DefaultNot f cat onGroup: Not f cat onGroup = Not f cat onGroup(
      warn = Dest nat on(ema ls = Seq("")),
      cr  cal = Dest nat on(ema ls =
        Seq(""))
    )

    def defaultEmptyResponseRateAlert(warnThreshold: Double = 50, cr  calThreshold: Double = 80) =
      EmptyResponseRateAlert(
        not f cat onGroup = DefaultNot f cat onGroup,
        warnPred cate = Tr gger fAbove(warnThreshold),
        cr  calPred cate = Tr gger fAbove(cr  calThreshold)
      )

    def defaultSuccessRateAlert(
      threshold: Double = 99.5,
      warnDatapo ntsPastThreshold:  nt = 20,
      cr  calDatapo ntsPastThreshold:  nt = 30,
      durat on:  nt = 30
    ) = SuccessRateAlert(
      not f cat onGroup = DefaultNot f cat onGroup,
      warnPred cate = Tr gger fBelow(threshold, warnDatapo ntsPastThreshold, durat on),
      cr  calPred cate = Tr gger fBelow(threshold, cr  calDatapo ntsPastThreshold, durat on),
    )

    def defaultLatencyAlert(
      latencyThreshold: Durat on = 200.m ll s,
      warn ngDatapo ntsPastThreshold:  nt = 15,
      cr  calDatapo ntsPastThreshold:  nt = 30,
      durat on:  nt = 30,
      percent le: Percent le = P99
    ): LatencyAlert = LatencyAlert(
      not f cat onGroup = DefaultNot f cat onGroup,
      percent le = percent le,
      warnPred cate =
        Tr gger fLatencyAbove(latencyThreshold, warn ngDatapo ntsPastThreshold, durat on),
      cr  calPred cate =
        Tr gger fLatencyAbove(latencyThreshold, cr  calDatapo ntsPastThreshold, durat on)
    )
  }
}
