package com.tw ter.ann.fa ss

 mport com.tw ter.ann.common.thr ftscala.Fa ssRunt  Param
 mport com.tw ter.b ject on. nject on
 mport scala.ut l.Fa lure
 mport scala.ut l.Success
 mport scala.ut l.Try
 mport com.tw ter.ann.common.thr ftscala.{Runt  Params => Serv ceRunt  Params}
 mport com.tw ter.search.common.f le.AbstractF le

object Fa ssCommon {
  val Runt  Params nject on:  nject on[Fa ssParams, Serv ceRunt  Params] =
    new  nject on[Fa ssParams, Serv ceRunt  Params] {
      overr de def apply(scalaParams: Fa ssParams): Serv ceRunt  Params = {
        Serv ceRunt  Params.Fa ssParam(
          Fa ssRunt  Param(
            scalaParams.nprobe,
            scalaParams.quant zerEf,
            scalaParams.quant zerKFactorRF,
            scalaParams.quant zerNprobe,
            scalaParams.ht)
        )
      }

      overr de def  nvert(thr ftParams: Serv ceRunt  Params): Try[Fa ssParams] =
        thr ftParams match {
          case Serv ceRunt  Params.Fa ssParam(fa ssParam) =>
            Success(
              Fa ssParams(
                fa ssParam.nprobe,
                fa ssParam.quant zerEf,
                fa ssParam.quant zerKfactorRf,
                fa ssParam.quant zerNprobe,
                fa ssParam.ht))
          case p => Fa lure(new  llegalArgu ntExcept on(s"Expected Fa ssParams got $p"))
        }
    }

  def  sVal dFa ss ndex(path: AbstractF le): Boolean = {
    path. sD rectory &&
    path.hasSuccessF le &&
    path.getCh ld("fa ss. ndex").ex sts()
  }
}
