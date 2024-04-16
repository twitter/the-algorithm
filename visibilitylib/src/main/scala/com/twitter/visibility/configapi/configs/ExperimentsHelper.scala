package com.tw ter.v s b l y.conf gap .conf gs

 mport com.tw ter.t  l nes.conf gap .Conf g
 mport com.tw ter.t  l nes.conf gap .Exper  ntConf gBu lder
 mport com.tw ter.t  l nes.conf gap .Param
 mport com.tw ter.v s b l y.conf gap .params.V s b l yExper  nt
 mport com.tw ter.v s b l y.models.SafetyLevel

object Exper  nts lper {

  def mkABExper  ntConf g(exper  nt: V s b l yExper  nt, param: Param[Boolean]): Conf g = {
    Exper  ntConf gBu lder(exper  nt)
      .addBucket(
        exper  nt.ControlBucket,
        param := true
      )
      .addBucket(
        exper  nt.Treat ntBucket,
        param := false
      )
      .bu ld
  }

  def mkABExper  ntConf g(exper  nt: V s b l yExper  nt, safetyLevel: SafetyLevel): Conf g =
    mkABExper  ntConf g(exper  nt, safetyLevel.enabledParam)
}
