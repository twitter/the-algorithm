package com.tw ter.t etyp e
package hydrator

 mport com.tw ter.coreserv ces. M1837
 mport com.tw ter.t etyp e.core._
 mport com.tw ter.st ch.St ch

object  M1837F lterHydrator {
  type Type = ValueHydrator[Un , T etCtx]

  pr vate val Drop =
    St ch.except on(F lteredState.Unava lable.DropUnspec f ed)
  pr vate val Success = St ch.value(ValueState.unmod f ed(()))

  def apply(): Type =
    ValueHydrator[Un , T etCtx] { (_, ctx) =>
      val userAgent = Tw terContext().flatMap(_.userAgent)
      val userAgentAffected = userAgent.ex sts( M1837. sAffectedCl ent)
      val m ghtCrash = userAgentAffected &&  M1837.textM ghtCrash OS(ctx.text)

       f (m ghtCrash) Drop else Success
    }
}
