package com.tw ter.product_m xer.core.p pel ne

 mport com.tw ter.product_m xer.core.model.common. dent f er.P pel neStep dent f er

case class  nval dStepStateExcept on(step: P pel neStep dent f er, m ss ngData: Str ng)
    extends Except on(
      s" nval d Step State: Step $step requ res $m ss ngData"
    )
