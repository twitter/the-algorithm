package com.tw ter.t etyp e
package serv ce
package observer

 mport com.tw ter.servo.except on.thr ftscala.Cl entError
 mport com.tw ter.t etyp e.thr ftscala.GetDeletedT etResult
 mport com.tw ter.t etyp e.thr ftscala.GetDeletedT etsRequest

pr vate[serv ce] object GetDeletedT etsObserver {
  type Type = ObserveExchange[GetDeletedT etsRequest, Seq[GetDeletedT etResult]]

  def observeExchange(stats: StatsRece ver): Effect[Type] = {
    val resultStateStats = ResultStateStats(stats)

    Effect {
      case (request, response) =>
        response match {
          case Return(_) | Throw(Cl entError(_)) =>
            resultStateStats.success(request.t et ds.s ze)
          case Throw(_) =>
            resultStateStats.fa led(request.t et ds.s ze)
        }
    }
  }
}
