package com.tw ter.product_m xer.core.qual y_factor

 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Try

/** Updates t  [[Qual yFactor]] */
tra  Qual yFactorObserver {

  /** T  [[Qual yFactor]] to update w n observ ng */
  def qual yFactor: Qual yFactor[_]

  /**
   * updates t  [[qual yFactor]] g ven t  result [[Try]] and t  latency
   * @note  mple ntat ons must be sure to correctly  gnore
   *       [[Qual yFactor.conf g]]'s [[Qual yFactorConf g. gnorableFa lures]]
   */
  def apply(result: Try[_], latency: Durat on): Un 
}
