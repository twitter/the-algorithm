package com.tw ter.t  l neranker.common

 mport com.tw ter.servo.ut l.FutureArrow
 mport com.tw ter.t  l neranker.core.Cand dateEnvelope
 mport com.tw ter.ut l.Future

/**
 * Transform that expl c ly hydrates cand date t ets and fetc s s ce t ets  n parallel
 * and t n jo ns t  results back  nto t  or g nal Envelope
 * @param cand dateT etHydrat on P pel ne that hydrates cand date t ets
 * @param s ceT etHydrat on P pel ne that fetc s and hydrates s ce t ets
 */
class HydrateT etsAndS ceT ets nParallelTransform(
  cand dateT etHydrat on: FutureArrow[Cand dateEnvelope, Cand dateEnvelope],
  s ceT etHydrat on: FutureArrow[Cand dateEnvelope, Cand dateEnvelope])
    extends FutureArrow[Cand dateEnvelope, Cand dateEnvelope] {
  overr de def apply(envelope: Cand dateEnvelope): Future[Cand dateEnvelope] = {
    Future
      .jo n(
        cand dateT etHydrat on(envelope),
        s ceT etHydrat on(envelope)
      ).map {
        case (cand dateT etEnvelope, s ceT etEnvelope) =>
          envelope.copy(
            hydratedT ets = cand dateT etEnvelope.hydratedT ets,
            s ceSearchResults = s ceT etEnvelope.s ceSearchResults,
            s ceHydratedT ets = s ceT etEnvelope.s ceHydratedT ets
          )
      }
  }
}
