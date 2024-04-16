package com.tw ter.t etyp e
package  d a

 mport com.tw ter.logg ng.Logger
 mport com.tw ter.t etyp e.thr ftscala. d aEnt y
 mport com.tw ter.t etyp e.thr ftscala.UrlEnt y

/**
 * Creat ng and pars ng t et  d a ent y URLs.
 *
 * T re are f  k nds of URL  n a  d a ent y:
 *
 *   - D splay URLs: p c.tw ter.com al ases for t  short URL, for
 *     embedd ng  n t  t et text.
 *
 *   - Short URLs: regular t.co URLs that expand to t  permal nk URL.
 *
 *   - Permal nk URLs: l nk to a page that d splays t   d a after
 *     do ng author zat on
 *
 *   - Asset URLs: l nks to t  actual  d a asset.
 *
 */
object  d aUrl {
  pr vate[t ] val log = Logger(getClass)

  /**
   * T  URL that should be f lled  n to t  d splayUrl f eld of t 
   *  d a ent y. T  URL behaves exactly t  sa  as a t.co l nk
   * (only t  doma n  s d fferent.)
   */
  object D splay {
    val Root = "p c.tw ter.com/"

    def fromTcoSlug(tcoSlug: Str ng): Str ng = Root + tcoSlug
  }

  /**
   * T  l nk target for t  l nk  n t  t et text (t  expanded URL
   * for t   d a, cop ed from t  URL ent y.) For nat ve photos,
   * t   s t  t et permal nk page.
   *
   * For users w hout a screen na  ("handleless" or NoScreenNa  users)
   * a permal nk to / /status/:t et_ d  s used.
   */
  object Permal nk {
    val Root = "https://tw ter.com/"
    val  nternal = " "
    val PhotoSuff x = "/photo/1"
    val V deoSuff x = "/v deo/1"

    def apply(screenNa : Str ng, t et d: T et d,  sV deo: Boolean): Str ng =
      Root +
        ( f (screenNa . sEmpty)  nternal else screenNa ) +
        "/status/" +
        t et d +
        ( f ( sV deo) V deoSuff x else PhotoSuff x)

    pr vate[t ] val Permal nkRegex =
      """https?://tw ter.com/(?:#!/)?\w+/status/(\d+)/(?:photo|v deo)/\d+""".r

    pr vate[t ] def getT et d(permal nk: Str ng): Opt on[T et d] =
      permal nk match {
        case Permal nkRegex(t et dStr) =>
          try {
            So (t et dStr.toLong)
          } catch {
            // D g s too b g to f   n a Long
            case _: NumberFormatExcept on => None
          }
        case _ => None
      }

    def getT et d(urlEnt y: UrlEnt y): Opt on[T et d] =
      urlEnt y.expanded.flatMap(getT et d)

    def hasT et d(permal nk: Str ng, t et d: T et d): Boolean =
      getT et d(permal nk).conta ns(t et d)

    def hasT et d( d aEnt y:  d aEnt y, t et d: T et d): Boolean =
      hasT et d( d aEnt y.expandedUrl, t et d)

    def hasT et d(urlEnt y: UrlEnt y, t et d: T et d): Boolean =
      getT et d(urlEnt y).conta ns(t et d)
  }

  /**
   * Converts a url that starts w h "https://" to one that starts w h "http://".
   */
  def httpsToHttp(url: Str ng): Str ng =
    url.replace("https://", "http://")

  /**
   * Gets t  last path ele nt from an asset url.  T  ex sts temporar ly to support
   * t  now deprecated  d aPath ele nt  n  d aEnt y.
   */
  def  d aPathFromUrl(url: Str ng): Str ng =
    url.last ndexOf('/') match {
      case -1 =>
        log.error(" nval d  d a path. Could not f nd last ele nt: " + url)
        // Better to return a broken prev ew URL to t  cl ent
        // than to fa l t  whole request.
        ""

      case  dx =>
        url.substr ng( dx + 1)
    }
}
