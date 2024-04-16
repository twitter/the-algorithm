package com.tw ter.t  l nes.pred ct on.common.aggregates.real_t  

 mport com.tw ter.summ ngb rd_ nternal.runner.storm.Gener cRunner

object TypeSafeRunner {
  def ma n(args: Array[Str ng]): Un  = Gener cRunner(args, T  l nesRealT  AggregatesJob(_))
}
