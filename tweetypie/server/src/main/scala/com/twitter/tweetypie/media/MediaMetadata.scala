package com.tw ter.t etyp e
package  d a

 mport com.tw ter. d aserv ces.commons. d a nformat on.{thr ftscala => m c}
 mport com.tw ter. d aserv ces.commons.thr ftscala. d aKey
 mport com.tw ter. d aserv ces.commons.t et d a.thr ftscala._
 mport com.tw ter.t etyp e.thr ftscala._
 mport java.n o.ByteBuffer

/**
 *  d a tadata encapsulates t   tadata about t et  d a that   rece ve from
 * t  var ous  d a serv ces backends on t et create or on t et read.  T  data,
 * comb ned w h data stored on t  t et,  s suff c ent to hydrate t et  d a ent  es.
 */
case class  d a tadata(
   d aKey:  d aKey,
  assetUrlHttps: Str ng,
  s zes: Set[ d aS ze],
   d a nfo:  d a nfo,
  product tadata: Opt on[m c.UserDef nedProduct tadata] = None,
  extens onsReply: Opt on[ByteBuffer] = None,
  add  onal tadata: Opt on[m c.Add  onal tadata] = None) {
  def assetUrlHttp: Str ng =  d aUrl.httpsToHttp(assetUrlHttps)

  def attr butableUser d: Opt on[User d] =
    add  onal tadata.flatMap(_.ownersh p nfo).flatMap(_.attr butableUser d)

  def updateEnt y(
     d aEnt y:  d aEnt y,
    t etUser d: User d,
     ncludeAdd  onal tadata: Boolean
  ):  d aEnt y = {
    // Abort  f   acc dentally try to replace t   d a. T 
    //  nd cates a log c error that caused m smatc d  d a  nfo.
    // T  could be  nternal or external to T etyP e.
    requ re(
       d aEnt y. d a d ==  d aKey. d a d,
      "Tr ed to update  d a w h  d a d=%s w h  d a nfo. d a d=%s"
        .format( d aEnt y. d a d,  d aKey. d a d)
    )

     d aEnt y.copy(
       d aUrl = assetUrlHttp,
       d aUrlHttps = assetUrlHttps,
      s zes = s zes,
       d a nfo = So ( d a nfo),
      extens onsReply = extens onsReply,
      // t  follow ng two f elds are deprecated and w ll be removed soon
      nsfw = false,
       d aPath =  d aUrl. d aPathFromUrl(assetUrlHttps),
       tadata = product tadata,
      add  onal tadata = add  onal tadata.f lter(_ =>  ncludeAdd  onal tadata),
      // M S allows  d a to be shared among author zed users so add  n s ceUser d  f   doesn't
      // match t  current t et's user d.
      s ceUser d = attr butableUser d.f lter(_ != t etUser d)
    )
  }
}
