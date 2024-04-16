package com.tw ter.product_m xer.core.qual y_factor

 mport com.tw ter.product_m xer.core.model.common. dent f er.Component dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.M sconf guredQual yFactor
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure

case class Qual yFactorStatus(
  qual yFactorByP pel ne: Map[Component dent f er, Qual yFactor[_]]) {

  /**
   * returns a new [[Qual yFactorStatus]] w h all t  ele nts of current Qual yFactorStatus and `ot r`.
   *  f a [[Component dent f er]] ex sts  n both maps, t  Value from `ot r` takes precedence
   */
  def ++(ot r: Qual yFactorStatus): Qual yFactorStatus = {
     f (ot r.qual yFactorByP pel ne. sEmpty) {
      t 
    } else  f (qual yFactorByP pel ne. sEmpty) {
      ot r
    } else {
      Qual yFactorStatus(qual yFactorByP pel ne ++ ot r.qual yFactorByP pel ne)
    }
  }
}

object Qual yFactorStatus {
  def bu ld[ dent f er <: Component dent f er](
    qual yFactorConf gs: Map[ dent f er, Qual yFactorConf g]
  ): Qual yFactorStatus = {
    Qual yFactorStatus(
      qual yFactorConf gs.map {
        case (key, conf g: L nearLatencyQual yFactorConf g) =>
          key -> L nearLatencyQual yFactor(conf g)
        case (key, conf g: Quer esPerSecondBasedQual yFactorConf g) =>
          key -> Quer esPerSecondBasedQual yFactor(conf g)
      }
    )
  }

  val empty: Qual yFactorStatus = Qual yFactorStatus(Map.empty)
}

tra  HasQual yFactorStatus {
  def qual yFactorStatus: Opt on[Qual yFactorStatus] = None
  def w hQual yFactorStatus(qual yFactorStatus: Qual yFactorStatus): P pel neQuery

  def getQual yFactorCurrentValue(
     dent f er: Component dent f er
  ): Double = getQual yFactor( dent f er).currentValue

  def getQual yFactor(
     dent f er: Component dent f er
  ): Qual yFactor[_] = qual yFactorStatus
    .flatMap(_.qual yFactorByP pel ne.get( dent f er))
    .getOrElse {
      throw P pel neFa lure(
        M sconf guredQual yFactor,
        s"Qual y factor not conf gured for $ dent f er")
    }
}
