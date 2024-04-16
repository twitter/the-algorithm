package com.tw ter.t  l neranker.common

 mport com.tw ter.servo.ut l.FutureArrow
 mport com.tw ter.t  l neranker.core.HydratedCand datesAndFeaturesEnvelope
 mport com.tw ter.t  l nes.earlyb rd.common.ut ls.Earlyb rdFeaturesHydrator
 mport com.tw ter.ut l.Future

object  nNetworkT etsSearchFeaturesHydrat onTransform
    extends FutureArrow[
      HydratedCand datesAndFeaturesEnvelope,
      HydratedCand datesAndFeaturesEnvelope
    ] {
  overr de def apply(
    request: HydratedCand datesAndFeaturesEnvelope
  ): Future[HydratedCand datesAndFeaturesEnvelope] = {
    Future
      .jo n(
        request.cand dateEnvelope.followGraphData.follo dUser dsFuture,
        request.cand dateEnvelope.followGraphData.mutuallyFollow ngUser dsFuture
      ).map {
        case (follo d ds, mutuallyFollow ng ds) =>
          val featuresByT et d = Earlyb rdFeaturesHydrator.hydrate(
            searc rUser d = request.cand dateEnvelope.query.user d,
            searc rProf le nfo = request.userProf le nfo,
            follo dUser ds = follo d ds,
            mutuallyFollow ngUser ds = mutuallyFollow ng ds,
            userLanguages = request.userLanguages,
            u LanguageCode = request.cand dateEnvelope.query.dev ceContext.flatMap(_.languageCode),
            searchResults = request.cand dateEnvelope.searchResults,
            s ceT etSearchResults = request.cand dateEnvelope.s ceSearchResults,
            t ets = request.cand dateEnvelope.hydratedT ets.outerT ets,
            s ceT ets = request.cand dateEnvelope.s ceHydratedT ets.outerT ets
          )

          request.copy(features = featuresByT et d)
      }
  }
}
