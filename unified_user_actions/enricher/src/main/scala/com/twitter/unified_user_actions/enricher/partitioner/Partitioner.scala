package com.tw ter.un f ed_user_act ons.enr c r.part  oner

 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch ntEnvelop
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch nt nstruct on
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch ntKey

tra  Part  oner {
  def repart  on(
     nstruct on: Enr ch nt nstruct on,
    envelop: Enr ch ntEnvelop
  ): Opt on[Enr ch ntKey]
}
