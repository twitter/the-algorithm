package com.tw ter.t etyp e
package handler

 mport com.tw ter.tco_ut l.TcoUrl
 mport com.tw ter.t etyp e.core._
 mport com.tw ter.t etyp e.thr ftscala.ent  es.Ent yExtractor
 mport com.tw ter.t etyp e.thr ftscala._
 mport com.tw ter.t etyp e.t ettext. ndexConverter
 mport com.tw ter.t etyp e.t ettext.Offset
 mport com.tw ter.t etyp e.t ettext.Preprocessor._

object UrlEnt yBu lder {
   mport UpstreamFa lure.UrlShorten ngFa lure
   mport UrlShortener.Context

  /**
   * Extracts URLs from t  g ven t et text, shortens t m, and returns an updated t et
   * text that conta ns t  shortened URLs, along w h t  generated `UrlEnt y`s.
   */
  type Type = FutureArrow[(Str ng, Context), (Str ng, Seq[UrlEnt y])]

  def fromShortener(shortener: UrlShortener.Type): Type =
    FutureArrow {
      case (text, ctx) =>
        Future
          .collect(Ent yExtractor.extractAllUrls(text).map(shortenEnt y(shortener, _, ctx)))
          .map(_.flatMap(_.toSeq))
          .map(updateTextAndUrls(text, _)(replace nv s blesW hWh espace))
    }

  /**
   * Update a url ent y w h tco-ed url
   *
   * @param urlEnt y an url ent y w h long url  n t  `url` f eld
   * @param ctx add  onal data needed to bu ld t  shortener request
   * @return an updated url ent y w h tco-ed url  n t  `url` f eld,
   *         and long url  n t  `expanded` f eld
   */
  pr vate def shortenEnt y(
    shortener: UrlShortener.Type,
    ent y: UrlEnt y,
    ctx: Context
  ): Future[Opt on[UrlEnt y]] =
    shortener((TcoUrl.normal zeProtocol(ent y.url), ctx))
      .map { urlData =>
        So (
          ent y.copy(
            url = urlData.shortUrl,
            expanded = So (urlData.longUrl),
            d splay = So (urlData.d splayText)
          )
        )
      }
      .rescue {
        // fa l t ets w h  nval d urls
        case UrlShortener. nval dUrlError =>
          Future.except on(T etCreateFa lure.State(T etCreateState. nval dUrl))
        // fa l t ets w h malware urls
        case UrlShortener.MalwareUrlError =>
          Future.except on(T etCreateFa lure.State(T etCreateState.MalwareUrl))
        // propagate OverCapac y
        case e @ OverCapac y(_) => Future.except on(e)
        // convert any ot r fa lure  nto UrlShorten ngFa lure
        case e => Future.except on(UrlShorten ngFa lure(e))
      }

  /**
   * Appl es a text-mod f cat on funct on to all parts of t  text not found w h n a UrlEnt y,
   * and t n updates all t  UrlEnt y  nd ces as necessary.
   */
  def updateTextAndUrls(
    text: Str ng,
    urlEnt  es: Seq[UrlEnt y]
  )(
    textMod: Str ng => Str ng
  ): (Str ng, Seq[UrlEnt y]) = {
    var offset nText = Offset.CodePo nt(0)
    var offset nNewText = Offset.CodePo nt(0)
    val newText = new Str ngBu lder
    val newUrlEnt  es = Seq.newBu lder[UrlEnt y]
    val  ndexConverter = new  ndexConverter(text)

    urlEnt  es.foreach { e =>
      val nonUrl = textMod( ndexConverter.substr ngByCodePo nts(offset nText.to nt, e.from ndex))
      newText.append(nonUrl)
      newText.append(e.url)
      offset nText = Offset.CodePo nt(e.to ndex.to nt)

      val urlFrom = offset nNewText + Offset.CodePo nt.length(nonUrl)
      val urlTo = urlFrom + Offset.CodePo nt.length(e.url)
      val newEnt y =
        e.copy(from ndex = urlFrom.toShort, to ndex = urlTo.toShort)

      newUrlEnt  es += newEnt y
      offset nNewText = urlTo
    }

    newText.append(textMod( ndexConverter.substr ngByCodePo nts(offset nText.to nt)))

    (newText.toStr ng, newUrlEnt  es.result())
  }
}
