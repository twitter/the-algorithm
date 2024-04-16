package com.tw ter.t etyp e
package handler

 mport com.tw ter.serv ce.talon.thr ftscala._
 mport com.tw ter.servo.ut l.FutureArrow
 mport com.tw ter.tco_ut l.D splayUrl
 mport com.tw ter.tco_ut l.TcoUrl
 mport com.tw ter.t etyp e.backends.Talon
 mport com.tw ter.t etyp e.core.OverCapac y
 mport com.tw ter.t etyp e.store.Guano
 mport com.tw ter.t etyp e.thr ftscala.ShortenedUrl
 mport scala.ut l.control.NoStackTrace

object UrlShortener {
  type Type = FutureArrow[(Str ng, Context), ShortenedUrl]

  case class Context(
    t et d: T et d,
    user d: User d,
    createdAt: T  ,
    userProtected: Boolean,
    cl entApp d: Opt on[Long] = None,
    remoteHost: Opt on[Str ng] = None,
    dark: Boolean = false)

  object MalwareUrlError extends Except on w h NoStackTrace
  object  nval dUrlError extends Except on w h NoStackTrace

  /**
   * Returns a new UrlShortener that c cks t  response from t  underly ng shortner
   * and,  f t  request  s not dark but fa ls w h a MalwareUrlError, scr bes request
   *  nfo to guano.
   */
  def scr beMalware(guano: Guano)(underly ng: Type): Type =
    FutureArrow {
      case (longUrl, ctx) =>
        underly ng((longUrl, ctx)).onFa lure {
          case MalwareUrlError  f !ctx.dark =>
            guano.scr beMalwareAttempt(
              Guano.MalwareAttempt(
                longUrl,
                ctx.user d,
                ctx.cl entApp d,
                ctx.remoteHost
              )
            )
          case _ =>
        }
    }

  def fromTalon(talonShorten: Talon.Shorten): Type = {
    val log = Logger(getClass)

    FutureArrow {
      case (longUrl, ctx) =>
        val request =
          ShortenRequest(
            user d = ctx.user d,
            longUrl = longUrl,
            aud Msg = "t etyp e",
            d rect ssage = So (false),
            protectedAccount = So (ctx.userProtected),
            maxShortUrlLength = None,
            t etData = So (T etData(ctx.t et d, ctx.createdAt. nM ll seconds)),
            traff cType =
               f (ctx.dark) ShortenTraff cType.Test ng
              else ShortenTraff cType.Product on
          )

        talonShorten(request).flatMap { res =>
          res.responseCode match {
            case ResponseCode.Ok =>
               f (res.malwareStatus == MalwareStatus.UrlBlocked) {
                Future.except on(MalwareUrlError)
              } else {
                val shortUrl =
                  res.fullShortUrl.getOrElse {
                    // fall back to fromSlug  f talon response does not have t  full short url
                    // Could be replaced w h an except on once t   n  al  ntegrat on on product on
                    //  s done
                    TcoUrl.fromSlug(res.shortUrl, TcoUrl. sHttps(res.longUrl))
                  }

                Future.value(
                  ShortenedUrl(
                    shortUrl = shortUrl,
                    longUrl = res.longUrl,
                    d splayText = D splayUrl(shortUrl, So (res.longUrl), true)
                  )
                )
              }

            case ResponseCode.Bad nput =>
              log.warn(s"Talon rejected URL that Extractor thought was f ne: $longUrl")
              Future.except on( nval dUrlError)

            //   shouldn't see ot r ResponseCodes, because Talon.Shorten translates t m to
            // except ons, but   have t  catch-all just  n case.
            case resCode =>
              log.warn(s"Unexpected response code $resCode for '$longUrl'")
              Future.except on(OverCapac y("talon"))
          }
        }
    }
  }
}
