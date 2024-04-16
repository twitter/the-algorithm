package com.tw ter.t  l neranker.core

 mport com.tw ter.recos.user_t et_ent y_graph.thr ftscala.T etRecom ndat on
 mport com.tw ter.search.earlyb rd.thr ftscala.Thr ftSearchResult
 mport com.tw ter.t  l neranker.model.RecapQuery
 mport com.tw ter.t  l nes.model.T et d

object Cand dateEnvelope {
  val EmptySearchResults: Seq[Thr ftSearchResult] = Seq.empty[Thr ftSearchResult]
  val EmptyHydratedT ets: HydratedT ets = HydratedT ets(Seq.empty, Seq.empty)
  val EmptyUtegResults: Map[T et d, T etRecom ndat on] = Map.empty[T et d, T etRecom ndat on]
}

case class Cand dateEnvelope(
  query: RecapQuery,
  searchResults: Seq[Thr ftSearchResult] = Cand dateEnvelope.EmptySearchResults,
  utegResults: Map[T et d, T etRecom ndat on] = Cand dateEnvelope.EmptyUtegResults,
  hydratedT ets: HydratedT ets = Cand dateEnvelope.EmptyHydratedT ets,
  followGraphData: FollowGraphDataFuture = FollowGraphDataFuture.EmptyFollowGraphDataFuture,
  // T  s ce t ets are
  // - t  ret eted t et, for ret ets
  // - t   nReplyTo t et, for extended repl es
  s ceSearchResults: Seq[Thr ftSearchResult] = Cand dateEnvelope.EmptySearchResults,
  s ceHydratedT ets: HydratedT ets = Cand dateEnvelope.EmptyHydratedT ets)
