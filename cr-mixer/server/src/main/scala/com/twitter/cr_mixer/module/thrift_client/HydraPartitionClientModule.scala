package com.tw ter.cr_m xer.module.thr ft_cl ent

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.thr ftmux. thodBu lder
 mport com.tw ter.f natra.mtls.thr ftmux.modules.MtlsCl ent
 mport com.tw ter. nject. njector
 mport com.tw ter. nject.thr ft.modules.Thr ft thodBu lderCl entModule
 mport com.tw ter.hydra.part  on.{thr ftscala => ht}

object HydraPart  onCl entModule
    extends Thr ft thodBu lderCl entModule[
      ht.HydraPart  on.Serv cePerEndpo nt,
      ht.HydraPart  on. thodPerEndpo nt
    ]
    w h MtlsCl ent {
  overr de def label: Str ng = "hydra-part  on"

  overr de def dest: Str ng = "/s/hydra/hydra-part  on"

  overr de protected def conf gure thodBu lder(
     njector:  njector,
     thodBu lder:  thodBu lder
  ):  thodBu lder =  thodBu lder.w hT  outTotal(500.m ll seconds)

}
