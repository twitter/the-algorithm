package com.tw ter.t etyp e
package hydrator

 mport com.tw ter.t etyp e
 mport com.tw ter.t etyp e.core.T etData
 mport com.tw ter.t etyp e.repos ory._
 mport com.tw ter.t etyp e.thr ftscala._
 mport org.apac .thr ft.protocol.TF eld

/**
 * Encapsulates bas c,  mmutable deta ls about a t et to be hydrated, along w h t 
 * `T etQuery.Opt ons`.  Only t et data that are not affected by hydrat on should be
 * exposed  re, as a s ngle `T etCtx`  nstance should be usable for t  ent re hydrat on
 * of a t et.
 */
tra  T etCtx {
  def opts: T etQuery.Opt ons

  def t et d: T et d
  def user d: User d
  def text: Str ng
  def createdAt: T  
  def createdV a: Str ng
  def  sRet et: Boolean
  def  sReply: Boolean
  def  sSelfReply: Boolean
  def s ceUser d: Opt on[User d]
  def s ceT et d: Opt on[T et d]
  def  nReplyToT et d: Opt on[T et d]
  def geoCoord nates: Opt on[GeoCoord nates]
  def place d: Opt on[Str ng]
  def hasTakedown: Boolean
  def quotedT et: Opt on[QuotedT et]

  def completedHydrat ons: Set[Hydrat onType]

  def  s n  al nsert: Boolean = opts.cause. n  al nsert(t et d)

  def t etF eldRequested(f eld: TF eld): Boolean = t etF eldRequested(f eld. d)
  def t etF eldRequested(f eld d: F eld d): Boolean = opts. nclude.t etF elds.conta ns(f eld d)

  def  d aF eldRequested(f eld: TF eld): Boolean =  d aF eldRequested(f eld. d)
  def  d aF eldRequested(f eld d: F eld d): Boolean = opts. nclude. d aF elds.conta ns(f eld d)
}

object T etCtx {
  def from(td: T etData, opts: T etQuery.Opt ons): T etCtx = FromT etData(td, opts)

  tra  Proxy extends T etCtx {
    protected def underly ngT etCtx: T etCtx

    def opts: T etQuery.Opt ons = underly ngT etCtx.opts
    def t et d: T et d = underly ngT etCtx.t et d
    def user d: User d = underly ngT etCtx.user d
    def text: Str ng = underly ngT etCtx.text
    def createdAt: T   = underly ngT etCtx.createdAt
    def createdV a: Str ng = underly ngT etCtx.createdV a
    def  sRet et: Boolean = underly ngT etCtx. sRet et
    def  sReply: Boolean = underly ngT etCtx. sReply
    def  sSelfReply: Boolean = underly ngT etCtx. sSelfReply
    def s ceUser d: Opt on[User d] = underly ngT etCtx.s ceUser d
    def s ceT et d: Opt on[T et d] = underly ngT etCtx.s ceT et d
    def  nReplyToT et d: Opt on[T et d] = underly ngT etCtx. nReplyToT et d
    def geoCoord nates: Opt on[GeoCoord nates] = underly ngT etCtx.geoCoord nates
    def place d: Opt on[Str ng] = underly ngT etCtx.place d
    def hasTakedown: Boolean = underly ngT etCtx.hasTakedown
    def completedHydrat ons: Set[Hydrat onType] = underly ngT etCtx.completedHydrat ons
    def quotedT et: Opt on[QuotedT et] = underly ngT etCtx.quotedT et
  }

  pr vate case class FromT etData(td: T etData, opts: T etQuery.Opt ons) extends T etCtx {
    pr vate val t et = td.t et
    def t et d:  d a d = t et. d
    def user d: User d = getUser d(t et)
    def text: Str ng = getText(t et)
    def createdAt: T   = getT  stamp(t et)
    def createdV a: Str ng = T etLenses.createdV a.get(t et)
    def  sRet et: Boolean = getShare(t et). sDef ned
    def  sSelfReply: Boolean = t etyp e. sSelfReply(t et)
    def  sReply: Boolean = getReply(t et). sDef ned
    def s ceUser d: Opt on[ d a d] = getShare(t et).map(_.s ceUser d)
    def s ceT et d: Opt on[ d a d] = getShare(t et).map(_.s ceStatus d)
    def  nReplyToT et d: Opt on[ d a d] = getReply(t et).flatMap(_. nReplyToStatus d)
    def geoCoord nates: Opt on[GeoCoord nates] = T etLenses.geoCoord nates.get(t et)
    def place d: Opt on[Str ng] = T etLenses.place d.get(t et)
    def hasTakedown: Boolean = T etLenses.hasTakedown(t et)
    def completedHydrat ons: Set[Hydrat onType] = td.completedHydrat ons
    def quotedT et: Opt on[QuotedT et] = getQuotedT et(t et)
  }
}
