package com.tw ter.ann.serv ce.query_server.common

 mport com.google. nject.Module
 mport com.tw ter.ann.common.thr ftscala.AnnQueryServ ce
 mport com.tw ter.app.Flag
 mport com.tw ter.f natra.dec der.modules.Dec derModule
 mport com.tw ter.f natra.thr ft.Thr ftServer
 mport com.tw ter.f natra.mtls.thr ftmux.Mtls
 mport com.tw ter.f natra.mtls.thr ftmux.modules.MtlsThr ft bFormsModule
 mport com.tw ter.f natra.thr ft.f lters.{
  AccessLogg ngF lter,
  Logg ngMDCF lter,
  StatsF lter,
  Thr ftMDCF lter,
  Trace dMDCF lter
}
 mport com.tw ter.f natra.thr ft.rout ng.Thr ftRouter

/**
 * T  class prov des most of t  conf gurat on needed for logg ng, stats, dec ders etc.
 */
abstract class BaseQuery ndexServer extends Thr ftServer w h Mtls {

  protected val env ron nt: Flag[Str ng] = flag[Str ng]("env ron nt", "serv ce env ron nt")

  /**
   * Overr de w h  thod to prov de more module to gu ce.
   */
  protected def add  onalModules: Seq[Module]

  /**
   * Overr de t   thod to add t  controller to t  thr ft router. BaseQuery ndexServer takes
   * care of most of t  ot r conf gurat on for  .
   * @param router
   */
  protected def addController(router: Thr ftRouter): Un 

  overr de protected f nal lazy val modules: Seq[Module] = Seq(
    Dec derModule,
    new MtlsThr ft bFormsModule[AnnQueryServ ce. thodPerEndpo nt](t )
  ) ++ add  onalModules

  overr de protected f nal def conf gureThr ft(router: Thr ftRouter): Un  = {
    router
      .f lter[Logg ngMDCF lter]
      .f lter[Trace dMDCF lter]
      .f lter[Thr ftMDCF lter]
      .f lter[AccessLogg ngF lter]
      .f lter[StatsF lter]

    addController(router)
  }
}
