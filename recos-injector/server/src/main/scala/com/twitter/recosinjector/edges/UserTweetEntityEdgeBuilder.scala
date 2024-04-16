package com.tw ter.recos njector.edges

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.graphjet.algor hms.Recom ndat onType
 mport com.tw ter.recos njector.cl ents.Cac Ent yEntry
 mport com.tw ter.recos njector.cl ents.RecosHoseEnt  esCac 
 mport com.tw ter.recos njector.cl ents.UrlResolver
 mport com.tw ter.recos njector.ut l.T etDeta ls
 mport com.tw ter.ut l.Future
 mport scala.collect on.Map
 mport scala.ut l.hash ng.MurmurHash3

class UserT etEnt yEdgeBu lder(
  cac : RecosHoseEnt  esCac ,
  urlResolver: UrlResolver
)(
   mpl c  val stats: StatsRece ver) {

  def getHas dEnt  es(ent  es: Seq[Str ng]): Seq[ nt] = {
    ent  es.map(MurmurHash3.str ngHash)
  }

  /**
   * G ven t  ent  es and t  r correspond ng has d ds, store t  hash d->ent y mapp ng  nto a
   * cac .
   * T   s because UTEG edges only store t  hash ds, and rel es on t  cac  values to
   * recover t  actual ent  es. T  allows us to store  nteger values  nstead of str ng  n t 
   * edges to save space.
   */
  pr vate def storeEnt  es nCac (
    urlEnt  es: Seq[Str ng],
    urlHash ds: Seq[ nt]
  ): Future[Un ] = {
    val urlCac Entr es = urlHash ds.z p(urlEnt  es).map {
      case (hash d, url) =>
        Cac Ent yEntry(RecosHoseEnt  esCac .UrlPref x, hash d, url)
    }
    cac .updateEnt  esCac (
      newCac Entr es = urlCac Entr es,
      stats = stats.scope("urlCac ")
    )
  }

  /**
   * Return an ent y mapp ng from GraphJet recType -> hash(ent y)
   */
  pr vate def getEnt  esMap(
    urlHash ds: Seq[ nt]
  ) = {
    val ent  esMap = Seq(
      Recom ndat onType.URL.getValue.toByte -> urlHash ds
    ).collect {
      case (keys,  ds)  f  ds.nonEmpty => keys ->  ds
    }.toMap
     f (ent  esMap. sEmpty) None else So (ent  esMap)
  }

  def getEnt  esMapAndUpdateCac (
    t et d: Long,
    t etDeta ls: Opt on[T etDeta ls]
  ): Future[Opt on[Map[Byte, Seq[ nt]]]] = {
    val resolvedUrlFut = urlResolver
      .getResolvedUrls(
        urls = t etDeta ls.flatMap(_.urls).getOrElse(N l),
        t et d = t et d
      ).map(_.values.toSeq)

    resolvedUrlFut.map { resolvedUrls =>
      val urlEnt  es = resolvedUrls
      val urlHash ds = getHas dEnt  es(urlEnt  es)

      // Async call to cac 
      storeEnt  es nCac (
        urlEnt  es = urlEnt  es,
        urlHash ds = urlHash ds
      )
      getEnt  esMap(urlHash ds)
    }
  }
}
