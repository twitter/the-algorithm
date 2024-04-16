package com.tw ter.product_m xer.component_l brary.p pel ne.cand date.flex ble_ nject on_p pel ne

 mport com.tw ter.product_m xer.component_l brary.cand date_s ce.flex ble_ nject on_p pel ne.PromptCand dateS ce
 mport com.tw ter.product_m xer.component_l brary.p pel ne.cand date.flex ble_ nject on_p pel ne.transfor r.HasFl p nject onParams
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .dec der.Dec derParam
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Fl pPromptDependentCand dateP pel neConf gBu lder @ nject() (
  promptCand dateS ce: PromptCand dateS ce) {

  /**
   * Bu ld a Fl pPromptDependentCand dateP pel neConf g
   *
   * @note  f  njected classes are needed to populate para ters  n t   thod, cons der creat ng a
   *       ProductFl pPromptDependentCand dateP pel neConf gBu lder w h a s ngle `def bu ld()`  thod.
   *       That product-spec f c bu lder class can t n  nject everyth ng   needs ( nclud ng t 
   *       class), and delegate to t  class's bu ld()  thod w h n  s own bu ld()  thod.
   */
  def bu ld[Query <: P pel neQuery w h HasFl p nject onParams](
     dent f er: Cand dateP pel ne dent f er = Cand dateP pel ne dent f er("Fl pPrompt"),
    enabledDec derParam: Opt on[Dec derParam[Boolean]] = None,
    supportedCl entParam: Opt on[FSParam[Boolean]] = None,
  ): Fl pPromptDependentCand dateP pel neConf g[Query] = {
    new Fl pPromptDependentCand dateP pel neConf g(
       dent f er =  dent f er,
      enabledDec derParam = enabledDec derParam,
      supportedCl entParam = supportedCl entParam,
      promptCand dateS ce = promptCand dateS ce)
  }
}
