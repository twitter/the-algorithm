package com.tw ter.t etyp e.hydrator

 mport com.tw ter.coreserv ces. M2884
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.t etyp e.core.F lteredState
 mport com.tw ter.t etyp e.core.ValueState
 mport com.tw ter.st ch.St ch

object  M2884F lterHydrator {
  type Type = ValueHydrator[Un , T etCtx]

  pr vate val Drop =
    St ch.except on(F lteredState.Unava lable.DropUnspec f ed)
  pr vate val Success = St ch.value(ValueState.unmod f ed(()))

  def apply(stats: StatsRece ver): Type = {

    val  m2884 = new  M2884(stats)

    ValueHydrator[Un , T etCtx] { (_, ctx) =>
      val userAgent = Tw terContext().flatMap(_.userAgent)
      val userAgentAffected = userAgent.ex sts( m2884. sAffectedCl ent)
      val m ghtCrash = userAgentAffected &&  m2884.textM ghtCrash OS(ctx.text)
       f (m ghtCrash) Drop else Success
    }
  }
}
