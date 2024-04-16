package com.tw ter.t  l neranker.common

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle. nd v dualRequestT  outExcept on
 mport com.tw ter.search.earlyb rd.thr ftscala.Thr ftSearchResult
 mport com.tw ter.servo.ut l.FutureArrow
 mport com.tw ter.t  l neranker.core.Cand dateEnvelope
 mport com.tw ter.t  l neranker.core.HydratedT ets
 mport com.tw ter.t  l neranker.model.Part allyHydratedT et
 mport com.tw ter.t  l nes.model.t et.HydratedT et
 mport com.tw ter.ut l.Future

object T etHydrat onTransform {
  val EmptyHydratedT ets: HydratedT ets =
    HydratedT ets(Seq.empty[HydratedT et], Seq.empty[HydratedT et])
  val EmptyHydratedT etsFuture: Future[HydratedT ets] = Future.value(EmptyHydratedT ets)
}

object Cand dateT etHydrat onTransform extends T etHydrat onTransform {
  overr de def apply(envelope: Cand dateEnvelope): Future[Cand dateEnvelope] = {
    hydrate(
      searchResults = envelope.searchResults,
      envelope = envelope
    ).map { t ets => envelope.copy(hydratedT ets = t ets) }
  }
}

object S ceT etHydrat onTransform extends T etHydrat onTransform {
  overr de def apply(envelope: Cand dateEnvelope): Future[Cand dateEnvelope] = {
    hydrate(
      searchResults = envelope.s ceSearchResults,
      envelope = envelope
    ).map { t ets => envelope.copy(s ceHydratedT ets = t ets) }
  }
}

// Stat c  RTE to  nd cate t  out  n t et hydrator. Placeholder t  out durat on of 0 m ll s  s used
// s nce   are only concerned w h t  s ce of t  except on.
object T etHydrat onT  outExcept on extends  nd v dualRequestT  outExcept on(0.m ll s) {
  serv ceNa  = "t etHydrator"
}

/**
 * Transform wh ch hydrates t ets  n t  Cand dateEnvelope
 **/
tra  T etHydrat onTransform extends FutureArrow[Cand dateEnvelope, Cand dateEnvelope] {

   mport T etHydrat onTransform._

  protected def hydrate(
    searchResults: Seq[Thr ftSearchResult],
    envelope: Cand dateEnvelope
  ): Future[HydratedT ets] = {
     f (searchResults.nonEmpty) {
      Future.value(
        HydratedT ets(searchResults.map(Part allyHydratedT et.fromSearchResult))
      )
    } else {
      EmptyHydratedT etsFuture
    }
  }
}
