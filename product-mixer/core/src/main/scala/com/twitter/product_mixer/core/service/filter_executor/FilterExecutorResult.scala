package com.tw ter.product_m xer.core.serv ce.f lter_executor

 mport com.tw ter.product_m xer.core.model.common. dent f er.F lter dent f er
 mport com.tw ter.product_m xer.core.serv ce.ExecutorResult

case class F lterExecutorResult[Cand date](
  result: Seq[Cand date],
   nd v dualF lterResults: Seq[ nd v dualF lterResults[Cand date]])
    extends ExecutorResult

sealed tra   nd v dualF lterResults[+Cand date]
case class Cond  onalF lterD sabled( dent f er: F lter dent f er)
    extends  nd v dualF lterResults[Noth ng]
case class F lterExecutor nd v dualResult[+Cand date](
   dent f er: F lter dent f er,
  kept: Seq[Cand date],
  removed: Seq[Cand date])
    extends  nd v dualF lterResults[Cand date]
