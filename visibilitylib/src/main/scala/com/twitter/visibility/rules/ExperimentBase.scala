package com.tw ter.v s b l y.rules

 mport com.tw ter.t  l nes.conf gap .Params
 mport com.tw ter.v s b l y.conf gap .params.LabelS ceParam
 mport com.tw ter.v s b l y.models.LabelS ce

object Exper  ntBase {
  val s ceToParamMap: Map[LabelS ce, LabelS ceParam] = Map.empty

  f nal def shouldF lterForS ce(params: Params, labelS ceOpt: Opt on[LabelS ce]): Boolean = {
    labelS ceOpt
      .map { s ce =>
        val param = Exper  ntBase.s ceToParamMap.get(s ce)
        param.map(params.apply).getOrElse(true)
      }
      .getOrElse(true)
  }
}
