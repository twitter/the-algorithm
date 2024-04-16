package com.tw ter.v s b l y.conf gap .params

 mport com.tw ter.t  l nes.conf gap .Param

abstract class LabelS ceParam(overr de val default: Boolean) extends Param(default) {
  overr de val statNa : Str ng = s"LabelS ceParam/${t .getClass.getS mpleNa }"
}

pr vate[v s b l y] object LabelS ceParams {
  object F lterLabelsFromBot7174Param extends LabelS ceParam(false)

  object F lterT etsS teAutomat onParamA extends LabelS ceParam(false)
  object F lterT etsS teAutomat onParamB extends LabelS ceParam(false)
  object F lterT etsS teAutomat onParamAB extends LabelS ceParam(false)
}
