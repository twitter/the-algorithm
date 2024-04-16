package com.tw ter.t  l neranker.common

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.servo.ut l.FutureArrow
 mport com.tw ter.t  l neranker.core.HydratedCand datesAndFeaturesEnvelope
 mport com.tw ter.t  l neranker.model.Cand dateT et
 mport com.tw ter.t  l neranker.model.Cand dateT etsResult
 mport com.tw ter.ut l.Future

class Cand dateGenerat onTransform(statsRece ver: StatsRece ver)
    extends FutureArrow[HydratedCand datesAndFeaturesEnvelope, Cand dateT etsResult] {
  pr vate[t ] val numCand dateT etsStat = statsRece ver.stat("numCand dateT ets")
  pr vate[t ] val numS ceT etsStat = statsRece ver.stat("numS ceT ets")

  overr de def apply(
    cand datesAndFeaturesEnvelope: HydratedCand datesAndFeaturesEnvelope
  ): Future[Cand dateT etsResult] = {
    val hydratedT ets = cand datesAndFeaturesEnvelope.cand dateEnvelope.hydratedT ets.outerT ets

     f (hydratedT ets.nonEmpty) {
      val cand dates = hydratedT ets.map { hydratedT et =>
        Cand dateT et(hydratedT et, cand datesAndFeaturesEnvelope.features(hydratedT et.t et d))
      }
      numCand dateT etsStat.add(cand dates.s ze)

      val s ceT ets =
        cand datesAndFeaturesEnvelope.cand dateEnvelope.s ceHydratedT ets.outerT ets.map {
          hydratedT et =>
            Cand dateT et(
              hydratedT et,
              cand datesAndFeaturesEnvelope.features(hydratedT et.t et d))
        }
      numS ceT etsStat.add(s ceT ets.s ze)

      Future.value(Cand dateT etsResult(cand dates, s ceT ets))
    } else {
      Future.value(Cand dateT etsResult.Empty)
    }
  }
}
