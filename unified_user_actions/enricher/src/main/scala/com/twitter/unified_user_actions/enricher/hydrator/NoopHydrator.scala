package com.tw ter.un f ed_user_act ons.enr c r.hydrator
 mport com.tw ter.un f ed_user_act ons.enr c r. mple ntat onExcept on
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch ntEnvelop
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch nt nstruct on
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch ntKey
 mport com.tw ter.ut l.Future

/**
 * T  hydrator does noth ng.  f  's used by m stake for any reason, an except on w ll be thrown.
 * Use t  w n   expect to have no hydrat on (for example, t  planner shouldn't hydrate anyth ng
 * and only would perform t  part  on ng funct on).
 */
object NoopHydrator {
  val OutputTop c: Opt on[Str ng] = None
}

class NoopHydrator extends Hydrator {
  overr de def hydrate(
     nstruct on: Enr ch nt nstruct on,
    key: Opt on[Enr ch ntKey],
    envelop: Enr ch ntEnvelop
  ): Future[Enr ch ntEnvelop] = {
    throw new  mple ntat onExcept on(
      "NoopHydrator shouldn't be  nvoked w n conf gure. C ck y  " +
        "enr ch nt plan.")
  }
}
