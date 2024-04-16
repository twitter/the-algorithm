package com.tw ter.t  l neranker.common

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.servo.ut l.FutureArrow
 mport com.tw ter.servo.ut l.Gate
 mport com.tw ter.storehaus.Store
 mport com.tw ter.t  l neranker.contentfeatures.ContentFeaturesProv der
 mport com.tw ter.t  l neranker.core.FutureDependencyTransfor r
 mport com.tw ter.t  l neranker.core.HydratedCand datesAndFeaturesEnvelope
 mport com.tw ter.t  l neranker.model.RecapQuery
 mport com.tw ter.t  l neranker.recap.model.ContentFeatures
 mport com.tw ter.t  l neranker.ut l.SearchResultUt l._
 mport com.tw ter.t  l neranker.ut l.Cach ngContentFeaturesProv der
 mport com.tw ter.t  l neranker.ut l.T etHydrator
 mport com.tw ter.t  l neranker.ut l.T etyp eContentFeaturesProv der
 mport com.tw ter.t  l nes.cl ents.t etyp e.T etyP eCl ent
 mport com.tw ter.t  l nes.model.T et d
 mport com.tw ter.ut l.Future
 mport com.tw ter.t  l nes.conf gap 
 mport com.tw ter.t  l nes.ut l.FutureUt ls

class ContentFeaturesHydrat onTransformBu lder(
  t etyP eCl ent: T etyP eCl ent,
  contentFeaturesCac : Store[T et d, ContentFeatures],
  enableContentFeaturesGate: Gate[RecapQuery],
  enableTokens nContentFeaturesGate: Gate[RecapQuery],
  enableT etText nContentFeaturesGate: Gate[RecapQuery],
  enableConversat onControlContentFeaturesGate: Gate[RecapQuery],
  enableT et d aHydrat onGate: Gate[RecapQuery],
  hydrate nReplyToT ets: Boolean,
  statsRece ver: StatsRece ver) {
  val scopedStatsRece ver: StatsRece ver = statsRece ver.scope("ContentFeaturesHydrat onTransform")
  val t etHydrator: T etHydrator = new T etHydrator(t etyP eCl ent, scopedStatsRece ver)
  val t etyp eContentFeaturesProv der: ContentFeaturesProv der =
    new T etyp eContentFeaturesProv der(
      t etHydrator,
      enableContentFeaturesGate,
      enableTokens nContentFeaturesGate,
      enableT etText nContentFeaturesGate,
      enableConversat onControlContentFeaturesGate,
      enableT et d aHydrat onGate,
      scopedStatsRece ver
    )

  val cach ngContentFeaturesProv der: ContentFeaturesProv der = new Cach ngContentFeaturesProv der(
    underly ng = t etyp eContentFeaturesProv der,
    contentFeaturesCac  = contentFeaturesCac ,
    statsRece ver = scopedStatsRece ver
  )

  val contentFeaturesProv der: conf gap .FutureDependencyTransfor r[RecapQuery, Seq[T et d], Map[
    T et d,
    ContentFeatures
  ]] = FutureDependencyTransfor r.part  on(
    gate = enableContentFeaturesGate,
     fTrue = cach ngContentFeaturesProv der,
     fFalse = t etyp eContentFeaturesProv der
  )

  lazy val contentFeaturesHydrat onTransform: ContentFeaturesHydrat onTransform =
    new ContentFeaturesHydrat onTransform(
      contentFeaturesProv der,
      enableContentFeaturesGate,
      hydrate nReplyToT ets
    )
  def bu ld(): ContentFeaturesHydrat onTransform = contentFeaturesHydrat onTransform
}

class ContentFeaturesHydrat onTransform(
  contentFeaturesProv der: ContentFeaturesProv der,
  enableContentFeaturesGate: Gate[RecapQuery],
  hydrate nReplyToT ets: Boolean)
    extends FutureArrow[
      HydratedCand datesAndFeaturesEnvelope,
      HydratedCand datesAndFeaturesEnvelope
    ] {
  overr de def apply(
    request: HydratedCand datesAndFeaturesEnvelope
  ): Future[HydratedCand datesAndFeaturesEnvelope] = {
     f (enableContentFeaturesGate(request.cand dateEnvelope.query)) {
      val searchResults = request.cand dateEnvelope.searchResults

      val s ceT et dMap = searchResults.map { searchResult =>
        (searchResult. d, getRet etS ceT et d(searchResult).getOrElse(searchResult. d))
      }.toMap

      val  nReplyToT et ds =  f (hydrate nReplyToT ets) {
        searchResults.flatMap(get nReplyToT et d)
      } else {
        Seq.empty
      }

      val t et dsToHydrate = (s ceT et dMap.values ++  nReplyToT et ds).toSeq.d st nct

      val contentFeaturesMapFuture =  f (t et dsToHydrate.nonEmpty) {
        contentFeaturesProv der(request.cand dateEnvelope.query, t et dsToHydrate)
      } else {
        FutureUt ls.EmptyMap[T et d, ContentFeatures]
      }

      Future.value(
        request.copy(
          contentFeaturesFuture = contentFeaturesMapFuture,
          t etS ceT etMap = s ceT et dMap,
           nReplyToT et ds =  nReplyToT et ds.toSet
        )
      )
    } else {
      Future.value(request)
    }
  }
}
