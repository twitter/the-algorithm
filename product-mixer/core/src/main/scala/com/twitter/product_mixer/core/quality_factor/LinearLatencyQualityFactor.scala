package com.tw ter.product_m xer.core.qual y_factor

 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Stopwatch

case class L nearLatencyQual yFactor(
  overr de val conf g: L nearLatencyQual yFactorConf g)
    extends Qual yFactor[Durat on] {

  pr vate val delayedUnt l nM ll s = Stopwatch.t  M ll s() + conf g. n  alDelay. nM ll s

  pr vate var state: Double = conf g.qual yFactorBounds.default

  overr de def currentValue: Double = state

  overr de def update(latency: Durat on): Un  = {
     f (Stopwatch.t  M ll s() >= delayedUnt l nM ll s) {
       f (latency > conf g.targetLatency) {
        adjustState(getNegat veDelta)
      } else {
        adjustState(conf g.delta)
      }
    }
  }

  overr de def bu ldObserver(): Qual yFactorObserver = L nearLatencyQual yFactorObserver(t )

  pr vate def getNegat veDelta: Double =
    -conf g.delta * conf g.targetLatencyPercent le / (100.0 - conf g.targetLatencyPercent le)

  pr vate def adjustState(delta: Double): Un  = {
    state = conf g.qual yFactorBounds.bounds(state + delta)
  }
}
