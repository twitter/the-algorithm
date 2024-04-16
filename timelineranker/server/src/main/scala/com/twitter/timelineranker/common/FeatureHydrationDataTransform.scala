package com.tw ter.t  l neranker.common

 mport com.tw ter.servo.ut l.FutureArrow
 mport com.tw ter.t  l neranker.core.Cand dateEnvelope
 mport com.tw ter.t  l neranker.core.HydratedCand datesAndFeaturesEnvelope
 mport com.tw ter.t  l neranker.model.RecapQuery
 mport com.tw ter.ut l.Future

/**
 * Fetc s all data requ red for feature hydrat on and generates t  HydratedCand datesAndFeaturesEnvelope
 * @param t etHydrat onAndF lter ngP pel ne P pel ne wh ch fetc s t  cand date t ets, hydrates and f lters t m
 * @param languagesServ ce Fetch user languages, requ red for feature hydrat on
 * @param userProf le nfoServ ce Fetch user prof le  nfo, requ red for feature hydrat on
 */
class FeatureHydrat onDataTransform(
  t etHydrat onAndF lter ngP pel ne: FutureArrow[RecapQuery, Cand dateEnvelope],
  languagesServ ce: UserLanguagesTransform,
  userProf le nfoServ ce: UserProf le nfoTransform)
    extends FutureArrow[RecapQuery, HydratedCand datesAndFeaturesEnvelope] {
  overr de def apply(request: RecapQuery): Future[HydratedCand datesAndFeaturesEnvelope] = {
    Future
      .jo n(
        languagesServ ce(request),
        userProf le nfoServ ce(request),
        t etHydrat onAndF lter ngP pel ne(request)).map {
        case (languages, userProf le nfo, transfor dCand dateEnvelope) =>
          HydratedCand datesAndFeaturesEnvelope(
            transfor dCand dateEnvelope,
            languages,
            userProf le nfo)
      }
  }
}
