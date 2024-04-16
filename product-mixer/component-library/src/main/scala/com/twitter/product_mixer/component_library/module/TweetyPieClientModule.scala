package com.tw ter.product_m xer.component_l brary.module

 mport com.google. nject.Prov des
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.convers ons.PercentOps._
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.thr ft.Cl ent d
 mport com.tw ter.f nagle.thr ftmux. thodBu lder
 mport com.tw ter.f natra.mtls.thr ftmux.modules.MtlsCl ent
 mport com.tw ter. nject. njector
 mport com.tw ter. nject.thr ft.modules.Thr ft thodBu lderCl entModule
 mport com.tw ter.st ch.t etyp e.T etyP e
 mport com.tw ter.t etyp e.thr ftscala.T etServ ce
 mport com.tw ter.ut l.Durat on
 mport javax. nject.S ngleton

/**
 *  mple ntat on w h reasonable defaults for an  dempotent T etyP e Thr ft and St ch cl ent.
 *
 * Note that t  per request and total t  outs are  ant to represent a reasonable start ng po nt
 * only. T se  re selected based on common pract ce, and should not be assu d to be opt mal for
 * any part cular use case.  f   are  nterested  n furt r tun ng t  sett ngs  n t  module,
 *    s recom nded to create local copy for y  serv ce.
 */
object T etyP eCl entModule
    extends Thr ft thodBu lderCl entModule[
      T etServ ce.Serv cePerEndpo nt,
      T etServ ce. thodPerEndpo nt
    ]
    w h MtlsCl ent {
  overr de val label: Str ng = "t etyp e"
  overr de val dest: Str ng = "/s/t etyp e/t etyp e"

  @S ngleton
  @Prov des
  def prov desT etyp eSt chCl ent(t etServ ce: T etServ ce. thodPerEndpo nt): T etyP e =
    new T etyP e(t etServ ce)

  /**
   * T etyP e cl ent  d must be  n t  form of {serv ce.env} or   w ll not be treated as an
   * unauthor zed cl ent
   */
  overr de protected def cl ent d( njector:  njector): Cl ent d = {
    val serv ce dent f er =  njector. nstance[Serv ce dent f er]
    Cl ent d(s"${serv ce dent f er.serv ce}.${serv ce dent f er.env ron nt}")
  }

  overr de protected def conf gure thodBu lder(
     njector:  njector,
     thodBu lder:  thodBu lder
  ):  thodBu lder =
     thodBu lder
      .w hT  outPerRequest(200.m ll seconds)
      .w hT  outTotal(400.m ll seconds)
      . dempotent(1.percent)

  overr de protected def sess onAcqu s  onT  out: Durat on = 500.m ll seconds
}
