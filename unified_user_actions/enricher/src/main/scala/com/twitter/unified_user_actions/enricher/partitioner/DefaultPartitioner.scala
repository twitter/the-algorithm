package com.tw ter.un f ed_user_act ons.enr c r.part  oner
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch ntEnvelop
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch nt dType
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch nt nstruct on
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch nt nstruct on.Not f cat onT etEnr ch nt
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch nt nstruct on.T etEnr ch nt
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch ntKey
 mport com.tw ter.un f ed_user_act ons.enr c r.part  oner.DefaultPart  oner.NullKey
 mport com.tw ter.un f ed_user_act ons.thr ftscala. em
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Not f cat onContent

object DefaultPart  oner {
  val NullKey: Opt on[Enr ch ntKey] = None
}

class DefaultPart  oner extends Part  oner {
  overr de def repart  on(
     nstruct on: Enr ch nt nstruct on,
    envelop: Enr ch ntEnvelop
  ): Opt on[Enr ch ntKey] = {
    ( nstruct on, envelop.uua. em) match {
      case (T etEnr ch nt,  em.T et nfo( nfo)) =>
        So (Enr ch ntKey(Enr ch nt dType.T et d,  nfo.act onT et d))
      case (Not f cat onT etEnr ch nt,  em.Not f cat on nfo( nfo)) =>
         nfo.content match {
          case Not f cat onContent.T etNot f cat on(content) =>
            So (Enr ch ntKey(Enr ch nt dType.T et d, content.t et d))
          case Not f cat onContent.Mult T etNot f cat on(content) =>
            //   scar fy on cac  performance  n t  case s nce only a small % of
            // not f cat on content w ll be mult -t et types.
            So (Enr ch ntKey(Enr ch nt dType.T et d, content.t et ds. ad))
          case _ => NullKey
        }
      case _ => NullKey
    }
  }
}
