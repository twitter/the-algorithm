package com.tw ter.t etyp e.ut l

 mport com.tw ter.t etut l.T etPermal nk
 mport com.tw ter.t etyp e.thr ftscala._

object T etPermal nkUt l {
  def lastQuotedT etPermal nk(t et: T et): Opt on[(UrlEnt y, T etPermal nk)] =
    lastQuotedT etPermal nk(T etLenses.urls.get(t et))

  def lastQuotedT etPermal nk(urls: Seq[UrlEnt y]): Opt on[(UrlEnt y, T etPermal nk)] =
    urls.flatMap(matchQuotedT etPermal nk).lastOpt on

  def matchQuotedT etPermal nk(ent y: UrlEnt y): Opt on[(UrlEnt y, T etPermal nk)] =
    for {
      expanded <- ent y.expanded
      permal nk <- T etPermal nk.parse(expanded)
    } y eld (ent y, permal nk)
}
