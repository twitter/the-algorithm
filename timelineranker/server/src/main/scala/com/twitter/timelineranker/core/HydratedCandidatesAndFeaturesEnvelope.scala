package com.tw ter.t  l neranker.core

 mport com.tw ter.search.common.constants.thr ftscala.Thr ftLanguage
 mport com.tw ter.search.common.features.thr ftscala.Thr ftT etFeatures
 mport com.tw ter.t  l neranker.recap.model.ContentFeatures
 mport com.tw ter.t  l nes.cl ents.g zmoduck.UserProf le nfo
 mport com.tw ter.t  l nes.model.T et d
 mport com.tw ter.t  l nes.ut l.FutureUt ls
 mport com.tw ter.ut l.Future

case class HydratedCand datesAndFeaturesEnvelope(
  cand dateEnvelope: Cand dateEnvelope,
  userLanguages: Seq[Thr ftLanguage],
  userProf le nfo: UserProf le nfo,
  features: Map[T et d, Thr ftT etFeatures] = Map.empty,
  contentFeaturesFuture: Future[Map[T et d, ContentFeatures]] = FutureUt ls.EmptyMap,
  t etS ceT etMap: Map[T et d, T et d] = Map.empty,
   nReplyToT et ds: Set[T et d] = Set.empty)
