package com.tw ter.t etyp e.hydrator

 mport com.tw ter.coreserv ces. M3433
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.core.F lteredState
 mport com.tw ter.t etyp e.core.ValueState

object  M3433F lterHydrator {
  type Type = ValueHydrator[Un , T etCtx]

  pr vate val Drop =
    St ch.except on(F lteredState.Unava lable.DropUnspec f ed)
  pr vate val Success = St ch.value(ValueState.unmod f ed(()))

  def apply(stats: StatsRece ver): Type = {

    ValueHydrator[Un , T etCtx] { (_, ctx) =>
      val userAgent = Tw terContext().flatMap(_.userAgent)
      val userAgentAffected = userAgent.ex sts( M3433. sAffectedCl ent)
      val m ghtCrash = userAgentAffected &&  M3433.textM ghtCrash OS(ctx.text)
       f (m ghtCrash) Drop else Success
    }
  }
}
