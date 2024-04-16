package com.tw ter.product_m xer.core.serv ce.scor ng_p pel ne_executor

 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common.presentat on. emCand dateW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.scor ng.Scor ngP pel neResult

case class Scor ngP pel neExecutorResult[Cand date <: Un versalNoun[Any]](
  result: Seq[ emCand dateW hDeta ls],
   nd v dualP pel neResults: Seq[Scor ngP pel neResult[Cand date]])
