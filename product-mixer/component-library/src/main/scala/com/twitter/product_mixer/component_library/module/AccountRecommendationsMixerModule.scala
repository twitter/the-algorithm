package com.tw ter.product_m xer.component_l brary.module

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.convers ons.PercentOps._
 mport com.tw ter.f nagle.thr ftmux. thodBu lder
 mport com.tw ter.f natra.mtls.thr ftmux.modules.MtlsCl ent
 mport com.tw ter. nject.annotat ons.Flags
 mport com.tw ter. nject. njector
 mport com.tw ter. nject.thr ft.modules.Thr ft thodBu lderCl entModule
 mport com.tw ter.account_recom ndat ons_m xer.thr ftscala.AccountRecom ndat onsM xer
 mport com.tw ter.ut l.Durat on

/**
 *  mple ntat on w h reasonable defaults for an  dempotent Account Recom ndat ons M xer Thr ft cl ent.
 *
 * Note that t  per request and total t  outs conf gured  n t  module are  ant to represent a
 * reasonable start ng po nt only. T se  re selected based on common pract ce, and should not be
 * assu d to be opt mal for any part cular use case.  f   are  nterested  n furt r tun ng t 
 * sett ngs  n t  module,    s recom nded to create local copy for y  serv ce.
 */
object AccountRecom ndat onsM xerModule
    extends Thr ft thodBu lderCl entModule[
      AccountRecom ndat onsM xer.Serv cePerEndpo nt,
      AccountRecom ndat onsM xer. thodPerEndpo nt
    ]
    w h MtlsCl ent {
  f nal val AccountRecom ndat onsM xerT  outPerRequest =
    "account_recom ndat ons_m xer.t  out_per_request"
  f nal val AccountRecom ndat onsM xerT  outTotal = "account_recom ndat ons_m xer.t  out_total"

  flag[Durat on](
    na  = AccountRecom ndat onsM xerT  outPerRequest,
    default = 800.m ll seconds,
     lp = "T  out per request for AccountRecom ndat onsM xer")

  flag[Durat on](
    na  = AccountRecom ndat onsM xerT  outTotal,
    default = 1200.m ll seconds,
     lp = "T  out total for AccountRecom ndat onsM xer")

  overr de val label: Str ng = "account-recs-m xer"

  overr de val dest: Str ng = "/s/account-recs-m xer/account-recs-m xer:thr ft"

  overr de protected def conf gure thodBu lder(
     njector:  njector,
     thodBu lder:  thodBu lder
  ):  thodBu lder = {
    val t  OutPerRequest: Durat on =  njector
      . nstance[Durat on](Flags.na d(AccountRecom ndat onsM xerT  outPerRequest))
    val t  OutTotal: Durat on =
       njector. nstance[Durat on](Flags.na d(AccountRecom ndat onsM xerT  outTotal))
     thodBu lder
      .w hT  outPerRequest(t  OutPerRequest)
      .w hT  outTotal(t  OutTotal)
      . dempotent(5.percent)
  }

  overr de protected def sess onAcqu s  onT  out: Durat on = 500.m ll seconds
}
