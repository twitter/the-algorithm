package com.tw ter.un f ed_user_act ons.enr c r.hydrator

 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch ntEnvelop
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch nt nstruct on
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch ntKey
 mport com.tw ter.ut l.Future

tra  Hydrator {
  def hydrate(
     nstruct on: Enr ch nt nstruct on,
    key: Opt on[Enr ch ntKey],
    envelop: Enr ch ntEnvelop
  ): Future[Enr ch ntEnvelop]
}
