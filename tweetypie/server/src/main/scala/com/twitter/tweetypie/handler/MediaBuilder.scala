package com.tw ter.t etyp e
package handler

 mport com.tw ter. d aserv ces.commons. d a nformat on.thr ftscala.UserDef nedProduct tadata
 mport com.tw ter. d aserv ces.commons.thr ftscala. d aKey
 mport com.tw ter. d aserv ces.commons.t et d a.thr ftscala._
 mport com.tw ter.servo.ut l.FutureArrow
 mport com.tw ter.tco_ut l.TcoSlug
 mport com.tw ter.t etyp e.core._
 mport com.tw ter.t etyp e. d a._
 mport com.tw ter.t etyp e.serverut l.Except onCounter
 mport com.tw ter.t etyp e.thr ftscala._
 mport com.tw ter.t etyp e.t ettext.Offset

object Create d aTco {
   mport UpstreamFa lure._

  case class Request(
    t et d: T et d,
    user d: User d,
    userScreenNa : Str ng,
     sProtected: Boolean,
    createdAt: T  ,
     sV deo: Boolean,
    dark: Boolean)

  type Type = FutureArrow[Request,  d a. d aTco]

  def apply(urlShortener: UrlShortener.Type): Type =
    FutureArrow[Request,  d a. d aTco] { req =>
      val expandedUrl =  d aUrl.Permal nk(req.userScreenNa , req.t et d, req. sV deo)
      val shortenCtx =
        UrlShortener.Context(
          user d = req.user d,
          userProtected = req. sProtected,
          t et d = req.t et d,
          createdAt = req.createdAt,
          dark = req.dark
        )

      urlShortener((expandedUrl, shortenCtx))
        .flatMap {  tadata =>
           tadata.shortUrl match {
            case TcoSlug(slug) =>
              Future.value(
                 d a. d aTco(
                  expandedUrl,
                   tadata.shortUrl,
                   d aUrl.D splay.fromTcoSlug(slug)
                )
              )

            case _ =>
              // should never get  re, s nce shortened urls from talon
              // always start w h "http://t.co/", just  n case...
              Future.except on( d aShortenUrlMalfor dFa lure)
          }
        }
        .rescue {
          case UrlShortener. nval dUrlError =>
            // should never get  re, s nce  d a expandedUrl should always be a val d
            //  nput to talon.
            Future.except on( d aExpandedUrlNotVal dFa lure)
        }
    }
}

object  d aBu lder {
  pr vate val log = Logger(getClass)

  case class Request(
     d aUpload ds: Seq[ d a d],
    text: Str ng,
    t et d: T et d,
    user d: User d,
    userScreenNa : Str ng,
     sProtected: Boolean,
    createdAt: T  ,
    dark: Boolean = false,
    product tadata: Opt on[Map[ d a d, UserDef nedProduct tadata]] = None)

  case class Result(updatedText: Str ng,  d aEnt  es: Seq[ d aEnt y],  d aKeys: Seq[ d aKey])

  type Type = FutureArrow[Request, Result]

  def apply(
    process d a:  d aCl ent.Process d a,
    create d aTco: Create d aTco.Type,
    stats: StatsRece ver
  ): Type =
    FutureArrow[Request, Result] {
      case Request(
             d aUpload ds,
            text,
            t et d,
            user d,
            screenNa ,
             sProtected,
            createdAt,
            dark,
            product tadata
          ) =>
        for {
           d aKeys <- process d a(
            Process d aRequest(
               d aUpload ds,
              user d,
              t et d,
               sProtected,
              product tadata
            )
          )
           d aTco <- create d aTco(
            Create d aTco.Request(
              t et d,
              user d,
              screenNa ,
               sProtected,
              createdAt,
               d aKeys.ex sts( d aKeyClass f er. sV deo(_)),
              dark
            )
          )
        } y eld produceResult(text,  d aTco,  sProtected,  d aKeys)
    }.countExcept ons(
        Except onCounter(stats)
      )
      .onFa lure[Request] { (req, ex) => log. nfo(req.toStr ng, ex) }
      .translateExcept ons {
        case e:  d aExcept ons. d aCl entExcept on =>
          T etCreateFa lure.State(T etCreateState. nval d d a, So (e.get ssage))
      }

  def produceResult(
    text: Str ng,
     d aTco:  d a. d aTco,
    user sProtected: Boolean,
     d aKeys: Seq[ d aKey]
  ): Result = {

    val newText =
       f (text == "")  d aTco.url
      else text + " " +  d aTco.url

    val to = Offset.CodePo nt.length(newText)
    val from = to - Offset.CodePo nt.length( d aTco.url)

    val  d aEnt  es =
       d aKeys.map {  d aKey =>
         d aEnt y(
           d aKey = So ( d aKey),
          from ndex = from.toShort,
          to ndex = to.toShort,
          url =  d aTco.url,
          d splayUrl =  d aTco.d splayUrl,
          expandedUrl =  d aTco.expandedUrl,
           d a d =  d aKey. d a d,
           d aPath = "", // to be hydrated
           d aUrl = null, // to be hydrated
           d aUrlHttps = null, // to be hydrated
          nsfw = false, // deprecated
          s zes = Set(
             d aS ze(
              s zeType =  d aS zeType.Or g,
              res ze thod =  d aRes ze thod.F ,
              deprecatedContentType =  d aKeyUt l.contentType( d aKey),
              w dth = -1, // to be hydrated
                ght = -1 // to be hydrated
            )
          )
        )
      }

    Result(newText,  d aEnt  es,  d aKeys)
  }
}
