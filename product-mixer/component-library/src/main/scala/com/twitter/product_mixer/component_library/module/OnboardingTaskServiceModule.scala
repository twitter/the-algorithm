package com.tw ter.product_m xer.component_l brary.module

 mport com.tw ter.f nagle.thr ftmux. thodBu lder
 mport com.tw ter.f natra.mtls.thr ftmux.modules.MtlsCl ent
 mport com.tw ter.onboard ng.task.serv ce.thr ftscala.TaskServ ce
 mport com.tw ter. nject. njector
 mport com.tw ter. nject.thr ft.modules.Thr ft thodBu lderCl entModule
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.convers ons.Durat onOps._

object Onboard ngTaskServ ceModule
    extends Thr ft thodBu lderCl entModule[
      TaskServ ce.Serv cePerEndpo nt,
      TaskServ ce. thodPerEndpo nt
    ]
    w h MtlsCl ent {
  overr de val label: Str ng = "onboard ng-task-serv ce"
  overr de val dest: Str ng = "/s/onboard ng-task-serv ce/onboard ng-task-serv ce"

  overr de protected def conf gure thodBu lder(
     njector:  njector,
     thodBu lder:  thodBu lder
  ):  thodBu lder = {
     thodBu lder
      .w hT  outPerRequest(500.m ll s)
      .w hT  outTotal(1000.m ll s)
  }

  overr de protected def sess onAcqu s  onT  out: Durat on = 500.m ll seconds
}
