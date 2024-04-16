package com.tw ter.product_m xer.core.qual y_factor

 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Try

case class Quer esPerSecondBasedQual yFactorObserver(
  overr de val qual yFactor: Quer esPerSecondBasedQual yFactor)
    extends Qual yFactorObserver {
  overr de def apply(
    result: Try[_],
    latency: Durat on
  ): Un  = {
    result
      .onSuccess(_ => qual yFactor.update())
      .onFa lure {
        case t  f qual yFactor.conf g. gnorableFa lures. sDef nedAt(t) => ()
        // Degrade qf as a proact ve m  gat on for any non  gnorable fa lures.
        case _ => qual yFactor.update( nt.MaxValue)
      }
  }
}
