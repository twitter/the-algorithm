package com.tw ter.cr_m xer.f lter

 mport com.tw ter.cr_m xer.f lter.V deoT etF lter.F lterConf g
 mport com.tw ter.cr_m xer.model.Cand dateGeneratorQuery
 mport com.tw ter.cr_m xer.model.CrCand dateGeneratorQuery
 mport com.tw ter.cr_m xer.model. n  alCand date
 mport com.tw ter.cr_m xer.model.RelatedT etCand dateGeneratorQuery
 mport com.tw ter.cr_m xer.model.RelatedV deoT etCand dateGeneratorQuery
 mport com.tw ter.cr_m xer.param.V deoT etF lterParams
 mport com.tw ter.ut l.Future
 mport javax. nject.S ngleton

@S ngleton
case class V deoT etF lter() extends F lterBase {
  overr de val na : Str ng = t .getClass.getCanon calNa 

  overr de type Conf gType = F lterConf g

  overr de def f lter(
    cand dates: Seq[Seq[ n  alCand date]],
    conf g: Conf gType
  ): Future[Seq[Seq[ n  alCand date]]] = {
    Future.value(cand dates.map {
      _.flatMap {
        cand date =>
           f (!conf g.enableV deoT etF lter) {
            So (cand date)
          } else {
            //  f hasV deo  s true, has mage, hasG f should be false
            val hasV deo = c ckT et nfoAttr bute(cand date.t et nfo.hasV deo)
            val  sH gh d aResolut on =
              c ckT et nfoAttr bute(cand date.t et nfo. sH gh d aResolut on)
            val  sQuoteT et = c ckT et nfoAttr bute(cand date.t et nfo. sQuoteT et)
            val  sReply = c ckT et nfoAttr bute(cand date.t et nfo. sReply)
            val hasMult ple d a = c ckT et nfoAttr bute(cand date.t et nfo.hasMult ple d a)
            val hasUrl = c ckT et nfoAttr bute(cand date.t et nfo.hasUrl)

             f (hasV deo &&  sH gh d aResolut on && ! sQuoteT et &&
              ! sReply && !hasMult ple d a && !hasUrl) {
              So (cand date)
            } else {
              None
            }
          }
      }
    })
  }

  def c ckT et nfoAttr bute(attr buteOpt: => Opt on[Boolean]): Boolean = {
     f (attr buteOpt. sDef ned)
      attr buteOpt.get
    else {
      // takes Quoted T et (T et nfo. sQuoteT et) as an example,
      //  f t  attr buteOpt  s None,   by default say    s not a quoted t et
      // s m larly,  f T et nfo.hasV deo  s a None,
      //   say   does not have v deo.
      false
    }
  }

  overr de def requestToConf g[CGQueryType <: Cand dateGeneratorQuery](
    query: CGQueryType
  ): F lterConf g = {
    val enableV deoT etF lter = query match {
      case _: CrCand dateGeneratorQuery | _: RelatedT etCand dateGeneratorQuery |
          _: RelatedV deoT etCand dateGeneratorQuery =>
        query.params(V deoT etF lterParams.EnableV deoT etF lterParam)
      case _ => false // e.g., GetRelatedT ets()
    }
    F lterConf g(
      enableV deoT etF lter = enableV deoT etF lter
    )
  }
}

object V deoT etF lter {
  // extend t  f lterConf g to add more flags  f needed.
  // now t y are hardcoded accord ng to t  prod sett ng
  case class F lterConf g(
    enableV deoT etF lter: Boolean)
}
