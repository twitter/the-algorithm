package com.tw ter.ho _m xer.module

 mport com.google. nject.Prov des
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.thr ft.Cl ent d
 mport com.tw ter.f nagle.thr ftmux. thodBu lder
 mport com.tw ter.f natra.mtls.thr ftmux.modules.MtlsCl ent
 mport com.tw ter. nject. njector
 mport com.tw ter. nject.annotat ons.Flags
 mport com.tw ter. nject.thr ft.modules.Thr ft thodBu lderCl entModule
 mport com.tw ter.st ch.t etyp e.T etyP e
 mport com.tw ter.t etyp e.thr ftscala.T etServ ce
 mport com.tw ter.ut l.Durat on
 mport javax. nject.S ngleton

/**
 *  dempotent T etyp e Thr ft and St ch cl ent.
 */
object T etyp eCl entModule
    extends Thr ft thodBu lderCl entModule[
      T etServ ce.Serv cePerEndpo nt,
      T etServ ce. thodPerEndpo nt
    ]
    w h MtlsCl ent {

  pr vate val T  outRequest = "t etyp e.t  out_request"
  pr vate val T  outTotal = "t etyp e.t  out_total"

  flag[Durat on](T  outRequest, 1000.m ll s, "T  out per request")
  flag[Durat on](T  outTotal, 1000.m ll s, "Total t  out")

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
  ):  thodBu lder = {
    val t  outRequest =  njector. nstance[Durat on](Flags.na d(T  outRequest))
    val t  outTotal =  njector. nstance[Durat on](Flags.na d(T  outTotal))

     thodBu lder
      .w hT  outPerRequest(t  outRequest)
      .w hT  outTotal(t  outTotal)
  }

  overr de protected def sess onAcqu s  onT  out: Durat on = 500.m ll s
}
