package com.tw ter.product_m xer.core.module

 mport com.google. nject.Prov des
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.ssl.Opportun st cTls
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter. nject.annotat ons.Flag
 mport com.tw ter.product_m xer.core.module.product_m xer_flags.ProductM xerFlagModule.Serv ceLocal
 mport com.tw ter.product_m xer.core.module.product_m xer_flags.ProductM xerFlagModule.StratoLocalRequestT  out
 mport com.tw ter.strato.cl ent.Cl ent
 mport com.tw ter.strato.cl ent.Strato
 mport com.tw ter.ut l.Durat on
 mport javax. nject.S ngleton

/**
 * Product M xer prefers to use a s ngle strato cl ent module over hav ng a var ety w h d fferent
 * t  outs. Latency Budgets  n Product M xer systems should be def ned at t  appl cat on layer.
 */
object StratoCl entModule extends Tw terModule {

  @Prov des
  @S ngleton
  def prov desStratoCl ent(
    serv ce dent f er: Serv ce dent f er,
    @Flag(Serv ceLocal)  sServ ceLocal: Boolean,
    @Flag(StratoLocalRequestT  out) t  out: Opt on[Durat on]
  ): Cl ent = {
    val stratoCl ent = Strato.cl ent.w hMutualTls(serv ce dent f er, Opportun st cTls.Requ red)

    // For local develop nt   can be useful to have a larger t  out than t  Strato default of
    // 280ms.   strongly d sc age sett ng cl ent-level t  outs outs de of t  use-case.  
    // recom nd sett ng an overall t  out for y  p pel ne's end-to-end runn ng t  .
     f ( sServ ceLocal && t  out. sDef ned)
      stratoCl ent.w hRequestT  out(t  out.get).bu ld()
    else {
      stratoCl ent.bu ld()
    }
  }
}
