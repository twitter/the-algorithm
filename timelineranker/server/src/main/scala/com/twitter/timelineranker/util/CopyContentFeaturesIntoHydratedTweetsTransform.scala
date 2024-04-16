package com.tw ter.t  l neranker.ut l

 mport com.tw ter.servo.ut l.FutureArrow
 mport com.tw ter.t  l neranker.core.HydratedCand datesAndFeaturesEnvelope
 mport com.tw ter.t  l neranker.recap.model.ContentFeatures
 mport com.tw ter.t  l nes.model.t et.HydratedT et
 mport com.tw ter.ut l.Future

object CopyContentFeatures ntoHydratedT etsTransform
    extends FutureArrow[
      HydratedCand datesAndFeaturesEnvelope,
      HydratedCand datesAndFeaturesEnvelope
    ] {

  overr de def apply(
    request: HydratedCand datesAndFeaturesEnvelope
  ): Future[HydratedCand datesAndFeaturesEnvelope] = {

    request.contentFeaturesFuture.map { s ceT etContentFeaturesMap =>
      val updatedHyratedT ets = request.cand dateEnvelope.hydratedT ets.outerT ets.map {
        hydratedT et =>
          val contentFeaturesOpt = request.t etS ceT etMap
            .get(hydratedT et.t et d)
            .flatMap(s ceT etContentFeaturesMap.get)

          val updatedHyratedT et = contentFeaturesOpt match {
            case So (contentFeatures: ContentFeatures) =>
              copyContentFeatures ntoHydratedT ets(
                contentFeatures,
                hydratedT et
              )
            case _ => hydratedT et
          }

          updatedHyratedT et
      }

      request.copy(
        cand dateEnvelope = request.cand dateEnvelope.copy(
          hydratedT ets = request.cand dateEnvelope.hydratedT ets.copy(
            outerT ets = updatedHyratedT ets
          )
        )
      )
    }
  }

  def copyContentFeatures ntoHydratedT ets(
    contentFeatures: ContentFeatures,
    hydratedT et: HydratedT et
  ): HydratedT et = {
    HydratedT et(
      hydratedT et.t et.copy(
        selfThread tadata = contentFeatures.selfThread tadata,
         d a = contentFeatures. d a
      )
    )
  }
}
