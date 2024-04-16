package com.tw ter.v s b l y.conf gap .params

 mport com.tw ter.t  l nes.conf gap .Param

abstract class GlobalParam[T](overr de val default: T) extends Param(default) {
  overr de val statNa : Str ng = s"GlobalParam/${t .getClass.getS mpleNa }"
}

pr vate[v s b l y] object GlobalParams {
  object EnableFetch ngLabelMap extends GlobalParam(false)
}
