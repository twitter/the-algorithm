package com.tw ter.cr_m xer.module.thr ft_cl ent
 mport com.tw ter.app.Flag
 mport com.tw ter.f nagle.Thr ftMux
 mport com.tw ter.f natra.mtls.thr ftmux.modules.MtlsCl ent
 mport com.tw ter. nject.thr ft.modules.Thr ft thodBu lderCl entModule
 mport com.tw ter.search.earlyb rd.thr ftscala.Earlyb rdServ ce
 mport com.tw ter. nject. njector
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.cr_m xer.module.core.T  outConf gModule.Earlyb rdCl entT  outFlagNa 
 mport com.tw ter.f nagle.serv ce.RetryBudget
 mport com.tw ter.ut l.Durat on
 mport org.apac .thr ft.protocol.TCompactProtocol

object Earlyb rdSearchCl entModule
    extends Thr ft thodBu lderCl entModule[
      Earlyb rdServ ce.Serv cePerEndpo nt,
      Earlyb rdServ ce. thodPerEndpo nt
    ]
    w h MtlsCl ent {

  overr de def label: Str ng = "earlyb rd"
  overr de def dest: Str ng = "/s/earlyb rd-root-superroot/root-superroot"
  pr vate val requestT  outFlag: Flag[Durat on] =
    flag[Durat on](Earlyb rdCl entT  outFlagNa , "Earlyb rd cl ent t  out")
  overr de protected def requestT  out: Durat on = requestT  outFlag()

  overr de def retryBudget: RetryBudget = RetryBudget.Empty

  overr de def conf gureThr ftMuxCl ent(
     njector:  njector,
    cl ent: Thr ftMux.Cl ent
  ): Thr ftMux.Cl ent = {
    super
      .conf gureThr ftMuxCl ent( njector, cl ent)
      .w hProtocolFactory(new TCompactProtocol.Factory())
      .w hSess onQual f er
      .successRateFa lureAccrual(successRate = 0.9, w ndow = 30.seconds)
  }
}
