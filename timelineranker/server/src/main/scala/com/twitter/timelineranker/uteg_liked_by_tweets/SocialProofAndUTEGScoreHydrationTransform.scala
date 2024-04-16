package com.tw ter.t  l neranker.uteg_l ked_by_t ets

 mport com.tw ter.servo.ut l.FutureArrow
 mport com.tw ter.t  l neranker.core.HydratedCand datesAndFeaturesEnvelope
 mport com.tw ter.ut l.Future

object Soc alProofAndUTEGScoreHydrat onTransform
    extends FutureArrow[
      HydratedCand datesAndFeaturesEnvelope,
      HydratedCand datesAndFeaturesEnvelope
    ] {
  overr de def apply(
    request: HydratedCand datesAndFeaturesEnvelope
  ): Future[HydratedCand datesAndFeaturesEnvelope] = {

    val updatedFeatures = request.features.map {
      case (t et d, features) =>
        t et d ->
          features.copy(
            utegSoc alProofByType =
              request.cand dateEnvelope.utegResults.get(t et d).map(_.soc alProofByType),
            utegScore = request.cand dateEnvelope.utegResults.get(t et d).map(_.score)
          )
    }

    Future.value(request.copy(features = updatedFeatures))
  }
}
