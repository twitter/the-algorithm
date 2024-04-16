package com.tw ter.product_m xer.component_l brary.module

 mport com.tw ter.dec der.Dec der
 mport com.tw ter.dec der.RandomRec p ent
 mport com.tw ter.f nagle.thr ft.Cl ent d
 mport com.tw ter.f nagle.thr ft.serv ce.F lterable
 mport com.tw ter.f nagle.thr ft.serv ce.ReqRepServ cePerEndpo ntBu lder
 mport com.tw ter.f natra.mtls.thr ftmux.modules.MtlsCl ent
 mport com.tw ter. nject. njector
 mport com.tw ter. nject.annotat ons.Flags
 mport com.tw ter. nject.thr ft.modules.ReqRepDarkTraff cF lterModule
 mport scala.reflect.ClassTag

class DarkTraff cF lterModule[ thod face <: F lterable[ thod face]: ClassTag](
   mpl c  serv ceBu lder: ReqRepServ cePerEndpo ntBu lder[ thod face])
    extends ReqRepDarkTraff cF lterModule
    w h MtlsCl ent {

  overr de protected def enableSampl ng( njector:  njector): Any => Boolean = _ => {
    val dec der =  njector. nstance[Dec der]
    val dec derKey =
       njector. nstance[Str ng](Flags.na d("thr ft.dark.traff c.f lter.dec der_key"))
    val fromProxy = Cl ent d.current
      .map(_.na ).ex sts(na  => na .conta ns("d ffy") || na .conta ns("darktraff c"))
    !fromProxy && dec der. sAva lable(dec derKey, rec p ent = So (RandomRec p ent))
  }
}
