package com.tw ter.t  l neranker.common

 mport com.tw ter.servo.ut l.FutureArrow
 mport com.tw ter.t  l neranker.core.Cand dateEnvelope
 mport com.tw ter.t  l neranker.core.HydratedT ets
 mport com.tw ter.t  l nes.v s b l y.V s b l yEnforcer
 mport com.tw ter.ut l.Future

/**
 * Transform wh ch uses an  nstance of a V s bl yEnforcer to f lter down HydratedT ets
 */
class V s b l yEnforc ngTransform(v s b l yEnforcer: V s b l yEnforcer)
    extends FutureArrow[Cand dateEnvelope, Cand dateEnvelope] {
  overr de def apply(envelope: Cand dateEnvelope): Future[Cand dateEnvelope] = {
    v s b l yEnforcer.apply(So (envelope.query.user d), envelope.hydratedT ets.outerT ets).map {
      v s bleT ets =>
        val  nnerT ets = envelope.hydratedT ets. nnerT ets
        envelope.copy(
          hydratedT ets = HydratedT ets(outerT ets = v s bleT ets,  nnerT ets =  nnerT ets))
    }
  }
}
