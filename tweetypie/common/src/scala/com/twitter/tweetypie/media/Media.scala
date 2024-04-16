package com.tw ter.t etyp e
package  d a

 mport com.tw ter. d aserv ces.commons.thr ftscala. d aCategory
 mport com.tw ter. d aserv ces.commons.t et d a.thr ftscala._
 mport com.tw ter.tco_ut l.TcoSlug
 mport com.tw ter.t etyp e.thr ftscala._
 mport com.tw ter.t etyp e.ut l.T etLenses

/**
 * A smörgåsbord of  d a-related  lper  thods.
 */
object  d a {
  val An matedG fContentType = "v deo/mp4 codecs=avc1.42E0"

  case class  d aTco(expandedUrl: Str ng, url: Str ng, d splayUrl: Str ng)

  val  mageContentTypes: Set[ d aContentType] =
    Set[ d aContentType](
       d aContentType. mageJpeg,
       d aContentType. magePng,
       d aContentType. mageG f
    )

  val An matedG fContentTypes: Set[ d aContentType] =
    Set[ d aContentType](
       d aContentType.V deoMp4
    )

  val V deoContentTypes: Set[ d aContentType] =
    Set[ d aContentType](
       d aContentType.V deoGener c
    )

  val  nUseContentTypes: Set[ d aContentType] =
    Set[ d aContentType](
       d aContentType. mageG f,
       d aContentType. mageJpeg,
       d aContentType. magePng,
       d aContentType.V deoMp4,
       d aContentType.V deoGener c
    )

  def  s mage(contentType:  d aContentType): Boolean =
     mageContentTypes.conta ns(contentType)

  def contentTypeToStr ng(contentType:  d aContentType): Str ng =
    contentType match {
      case  d aContentType. mageG f => " mage/g f"
      case  d aContentType. mageJpeg => " mage/jpeg"
      case  d aContentType. magePng => " mage/png"
      case  d aContentType.V deoMp4 => "v deo/mp4"
      case  d aContentType.V deoGener c => "v deo"
      case _ => throw new  llegalArgu ntExcept on(s"Unknown d aContentType: $contentType")
    }

  def str ngToContentType(str: Str ng):  d aContentType =
    str match {
      case " mage/g f" =>  d aContentType. mageG f
      case " mage/jpeg" =>  d aContentType. mageJpeg
      case " mage/png" =>  d aContentType. magePng
      case "v deo/mp4" =>  d aContentType.V deoMp4
      case "v deo" =>  d aContentType.V deoGener c
      case _ => throw new  llegalArgu ntExcept on(s"Unknown Content Type Str ng: $str")
    }

  def extens onForContentType(cType:  d aContentType): Str ng =
    cType match {
      case  d aContentType. mageJpeg => "jpg"
      case  d aContentType. magePng => "png"
      case  d aContentType. mageG f => "g f"
      case  d aContentType.V deoMp4 => "mp4"
      case  d aContentType.V deoGener c => ""
      case _ => "unknown"
    }

  /**
   * Extract a URL ent y from a  d a ent y.
   */
  def extractUrlEnt y( d aEnt y:  d aEnt y): UrlEnt y =
    UrlEnt y(
      from ndex =  d aEnt y.from ndex,
      to ndex =  d aEnt y.to ndex,
      url =  d aEnt y.url,
      expanded = So ( d aEnt y.expandedUrl),
      d splay = So ( d aEnt y.d splayUrl)
    )

  /**
   * Copy t  f elds from t  URL ent y  nto t   d a ent y.
   */
  def copyFromUrlEnt y( d aEnt y:  d aEnt y, urlEnt y: UrlEnt y):  d aEnt y = {
    val expandedUrl =
      urlEnt y.expanded.orElse(Opt on( d aEnt y.expandedUrl)).getOrElse(urlEnt y.url)

    val d splayUrl =
      urlEnt y.url match {
        case TcoSlug(slug) =>  d aUrl.D splay.fromTcoSlug(slug)
        case _ => urlEnt y.expanded.getOrElse(urlEnt y.url)
      }

     d aEnt y.copy(
      from ndex = urlEnt y.from ndex,
      to ndex = urlEnt y.to ndex,
      url = urlEnt y.url,
      expandedUrl = expandedUrl,
      d splayUrl = d splayUrl
    )
  }

  def getAspectRat o(s ze:  d aS ze): AspectRat o =
    getAspectRat o(s ze.w dth, s ze.  ght)

  def getAspectRat o(w dth:  nt,   ght:  nt): AspectRat o = {
     f (w dth == 0 ||   ght == 0) {
      throw new  llegalArgu ntExcept on(s"D  ns ons must be non zero: ($w dth, $  ght)")
    }

    def calculateGcd(a:  nt, b:  nt):  nt =
       f (b == 0) a else calculateGcd(b, a % b)

    val gcd = calculateGcd(math.max(w dth,   ght), math.m n(w dth,   ght))
    AspectRat o((w dth / gcd).toShort, (  ght / gcd).toShort)
  }

  /**
   * Return just t   d a that belongs to t  t et
   */
  def own d a(t et: T et): Seq[ d aEnt y] =
    T etLenses. d a.get(t et).f lter( sOwn d a(t et. d, _))

  /**
   * Does t  g ven  d a ent y, wh ch  s was found on t  t et w h t  spec f ed
   * t et d, belong to that t et?
   */
  def  sOwn d a(t et d: T et d, ent y:  d aEnt y): Boolean =
    ent y.s ceStatus d.forall(_ == t et d)

  /**
   * M xed  d a  s any case w re t re  s more than one  d a  em & any of t m  s not an  mage.
   */

  def  sM xed d a( d aEnt  es: Seq[ d aEnt y]): Boolean =
     d aEnt  es.length > 1 && ( d aEnt  es.flatMap(_. d a nfo).ex sts {
      case _:  d a nfo. mage nfo => false
      case _ => true
    } ||
       d aEnt  es.flatMap(_. d aKey).map(_. d aCategory).ex sts(_ !=  d aCategory.T et mage))
}
