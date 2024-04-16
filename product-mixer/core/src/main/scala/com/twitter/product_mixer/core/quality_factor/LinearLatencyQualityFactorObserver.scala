package com.tw ter.product_m xer.core.qual y_factor

 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Try

case class L nearLatencyQual yFactorObserver(
  overr de val qual yFactor: L nearLatencyQual yFactor)
    extends Qual yFactorObserver {

  overr de def apply(result: Try[_], latency: Durat on): Un  = {
    result
      .onSuccess(_ => qual yFactor.update(latency))
      .onFa lure {
        case t  f qual yFactor.conf g. gnorableFa lures. sDef nedAt(t) => ()
        case _ => qual yFactor.update(Durat on.Top)
      }
  }
}
