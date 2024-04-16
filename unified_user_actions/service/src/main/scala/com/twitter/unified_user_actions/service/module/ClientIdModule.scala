package com.tw ter.un f ed_user_act ons.serv ce.module

 mport com.google. nject.Prov des
 mport com.tw ter.f nagle.thr ft.Cl ent d
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter. nject.annotat ons.Flag
 mport javax. nject.S ngleton

object Cl ent dModule extends Tw terModule {
  pr vate f nal val flagNa  = "thr ft.cl ent. d"

  flag[Str ng](
    na  = flagNa ,
     lp = "Thr ft Cl ent  D"
  )

  @Prov des
  @S ngleton
  def prov desCl ent d(
    @Flag(flagNa ) thr ftCl ent d: Str ng,
  ): Cl ent d = Cl ent d(
    na  = thr ftCl ent d
  )
}
