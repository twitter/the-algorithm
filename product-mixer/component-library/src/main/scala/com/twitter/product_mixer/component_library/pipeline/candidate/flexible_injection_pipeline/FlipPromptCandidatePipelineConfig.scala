package com.tw ter.product_m xer.component_l brary.p pel ne.cand date.flex ble_ nject on_p pel ne

 mport com.tw ter.onboard ng.task.serv ce.thr ftscala.Get nject onsRequest
 mport com.tw ter.onboard ng.task.serv ce.{thr ftscala => serv cethr ft}
 mport com.tw ter.product_m xer.component_l brary.cand date_s ce.flex ble_ nject on_p pel ne. nter d atePrompt
 mport com.tw ter.product_m xer.component_l brary.cand date_s ce.flex ble_ nject on_p pel ne.PromptCand dateS ce
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.Urt emCand dateDecorator
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.UrtMult pleModulesDecorator
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.flex ble_ nject on_p pel ne.Fl pPromptCand dateUrt emBu lder
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.flex ble_ nject on_p pel ne.Fl pPromptModuleGroup ng
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.flex ble_ nject on_p pel ne.Fl pPromptUrtModuleBu lder
 mport com.tw ter.product_m xer.component_l brary.model.cand date.BasePromptCand date
 mport com.tw ter.product_m xer.component_l brary.p pel ne.cand date.flex ble_ nject on_p pel ne.transfor r.Fl pCand dateFeatureTransfor r
 mport com.tw ter.product_m xer.component_l brary.p pel ne.cand date.flex ble_ nject on_p pel ne.transfor r.Fl pQueryTransfor r
 mport com.tw ter.product_m xer.component_l brary.p pel ne.cand date.flex ble_ nject on_p pel ne.transfor r.HasFl p nject onParams
 mport com.tw ter.product_m xer.component_l brary.p pel ne.cand date.flex ble_ nject on_p pel ne.transfor r.PromptResultsTransfor r
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.Cand dateDecorator
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateFeatureTransfor r
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateP pel neQueryTransfor r
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateP pel neResultsTransfor r
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.p pel ne.cand date.Cand dateP pel neConf g
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .dec der.Dec derParam

/**
 * A cand date p pel ne for Flex ble  nject on P pel ne Cand dates.
 * Fetc s prompts from FL P ( ns de onboard ng-task-serv ce).
 */
class Fl pPromptCand dateP pel neConf g[Query <: P pel neQuery w h HasFl p nject onParams](
  overr de val  dent f er: Cand dateP pel ne dent f er,
  overr de val enabledDec derParam: Opt on[Dec derParam[Boolean]],
  overr de val supportedCl entParam: Opt on[FSParam[Boolean]],
  promptCand dateS ce: PromptCand dateS ce)
    extends Cand dateP pel neConf g[
      Query,
      serv cethr ft.Get nject onsRequest,
       nter d atePrompt,
      BasePromptCand date[Any]
    ] {

  overr de val cand dateS ce: Cand dateS ce[Get nject onsRequest,  nter d atePrompt] =
    promptCand dateS ce

  overr de val queryTransfor r: Cand dateP pel neQueryTransfor r[Query, Get nject onsRequest] =
    Fl pQueryTransfor r

  overr de val resultTransfor r: Cand dateP pel neResultsTransfor r[
     nter d atePrompt,
    BasePromptCand date[Any]
  ] = PromptResultsTransfor r

  overr de val decorator: Opt on[
    Cand dateDecorator[Query, BasePromptCand date[Any]]
  ] = So (
    UrtMult pleModulesDecorator(
      urt emCand dateDecorator = Urt emCand dateDecorator(Fl pPromptCand dateUrt emBu lder()),
      moduleBu lder = Fl pPromptUrtModuleBu lder(),
      groupByKey = Fl pPromptModuleGroup ng
    ))

  overr de val featuresFromCand dateS ceTransfor rs: Seq[
    Cand dateFeatureTransfor r[ nter d atePrompt]
  ] = Seq(Fl pCand dateFeatureTransfor r)
}
