package com.tw ter.product_m xer.core.serv ce.selector_executor

 mport com.tw ter.product_m xer.core.funct onal_component.selector.SelectorResult
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.serv ce.ExecutorResult

case class SelectorExecutorResult(
  selectedCand dates: Seq[Cand dateW hDeta ls],
  rema n ngCand dates: Seq[Cand dateW hDeta ls],
  droppedCand dates: Seq[Cand dateW hDeta ls],
   nd v dualSelectorResults: Seq[SelectorResult])
    extends ExecutorResult
