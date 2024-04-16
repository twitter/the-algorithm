package com.tw ter.t  l neranker.common

 mport com.tw ter.servo.ut l.FutureArrow
 mport com.tw ter.t  l neranker.core.HydratedCand datesAndFeaturesEnvelope
 mport com.tw ter.t  l nes.earlyb rd.common.ut ls.Earlyb rdFeaturesHydrator
 mport com.tw ter.ut l.Future

object OutOfNetworkT etsSearchFeaturesHydrat onTransform
    extends FutureArrow[
      HydratedCand datesAndFeaturesEnvelope,
      HydratedCand datesAndFeaturesEnvelope
    ] {
  overr de def apply(
    request: HydratedCand datesAndFeaturesEnvelope
  ): Future[HydratedCand datesAndFeaturesEnvelope] = {
    val featuresByT et d = Earlyb rdFeaturesHydrator.hydrate(
      searc rUser d = request.cand dateEnvelope.query.user d,
      searc rProf le nfo = request.userProf le nfo,
      follo dUser ds = Seq.empty,
      mutuallyFollow ngUser ds = Set.empty,
      userLanguages = request.userLanguages,
      u LanguageCode = request.cand dateEnvelope.query.dev ceContext.flatMap(_.languageCode),
      searchResults = request.cand dateEnvelope.searchResults,
      s ceT etSearchResults = Seq.empty,
      t ets = request.cand dateEnvelope.hydratedT ets.outerT ets,
      s ceT ets = Seq.empty
    )

    Future.value(request.copy(features = featuresByT et d))
  }
}
