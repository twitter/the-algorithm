package com.tw ter.ho _m xer.funct onal_component.query_transfor r

 mport com.tw ter.common_ nternal.analyt cs.tw ter_cl ent_user_agent_parser.UserAgent
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.ho _m xer.model.Ho Features.Pers stenceEntr esFeature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateP pel neQueryTransfor r
 mport com.tw ter.product_m xer.core.model.common. dent f er.Transfor r dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.t  l nem xer.cl ents.pers stence.EntryW h em ds
 mport com.tw ter.t  l nes.pers stence.thr ftscala.RequestType
 mport com.tw ter.t  l nes.ut l.cl ent_ nfo.Cl entPlatform
 mport com.tw ter.t  l neserv ce.model.r ch.Ent y dType
 mport com.tw ter.ut l.T  

object Ed edT etsCand dateP pel neQueryTransfor r
    extends Cand dateP pel neQueryTransfor r[P pel neQuery, Seq[Long]] {

  overr de val  dent f er: Transfor r dent f er = Transfor r dent f er("Ed edT ets")

  // T  t   w ndow for wh ch a t et rema ns ed able after creat on.
  pr vate val Ed T  W ndow = 60.m nutes

  overr de def transform(query: P pel neQuery): Seq[Long] = {
    val appl cableCand dates = getAppl cableCand dates(query)

     f (appl cableCand dates.nonEmpty) {
      //  nclude t  response correspond ng w h t  Prev ous T  l ne Load (PTL).
      // Any t ets  n   could have beco  stale s nce be ng served.
      val prev ousT  l neLoadT   = appl cableCand dates. ad.servedT  

      // T  t   w ndow for ed  ng a t et  s 60 m nutes,
      // so    gnore responses older than (PTL T   - 60 m ns).
      val  nW ndowCand dates: Seq[Pers stenceStoreEntry] = appl cableCand dates
        .takeWh le(_.servedT  .unt l(prev ousT  l neLoadT  ) < Ed T  W ndow)

      // Exclude t  t et  Ds for wh ch ReplaceEntry  nstruct ons have already been sent.
      val (t etsAlreadyReplaced, t etsToC ck) =  nW ndowCand dates
        .part  on(_.entryW h em ds. em ds.ex sts(_. ad.entry dToReplace.nonEmpty))

      val t et dFromEntry: Part alFunct on[Pers stenceStoreEntry, Long] = {
        case entry  f entry.t et d.nonEmpty => entry.t et d.get
      }

      val t et dsAlreadyReplaced: Set[Long] = t etsAlreadyReplaced.collect(t et dFromEntry).toSet
      val t et dsToC ck: Seq[Long] = t etsToC ck.collect(t et dFromEntry)

      t et dsToC ck.f lterNot(t et dsAlreadyReplaced.conta ns).d st nct
    } else Seq.empty
  }

  // T  cand dates  re co  from t  T  l nes Pers stence Store, v a a query feature
  pr vate def getAppl cableCand dates(query: P pel neQuery): Seq[Pers stenceStoreEntry] = {
    val userAgent = UserAgent.fromStr ng(query.cl entContext.userAgent.getOrElse(""))
    val cl entPlatform = Cl entPlatform.fromQueryOpt ons(query.cl entContext.app d, userAgent)

    val sortedResponses = query.features
      .getOrElse(FeatureMap.empty)
      .getOrElse(Pers stenceEntr esFeature, Seq.empty)
      .f lter(_.cl entPlatform == cl entPlatform)
      .sortBy(-_.servedT  . nM ll seconds)

    val recentResponses = sortedResponses. ndexW re(_.requestType == RequestType. n  al) match {
      case -1 => sortedResponses
      case lastGet n  al ndex => sortedResponses.take(lastGet n  al ndex + 1)
    }

    recentResponses.flatMap { r =>
      r.entr es.collect {
        case entry  f entry.ent y dType == Ent y dType.T et =>
          Pers stenceStoreEntry(entry, r.servedT  , r.cl entPlatform, r.requestType)
      }
    }.d st nct
  }
}

case class Pers stenceStoreEntry(
  entryW h em ds: EntryW h em ds,
  servedT  : T  ,
  cl entPlatform: Cl entPlatform,
  requestType: RequestType) {

  // T  l nes Pers stence Store currently  ncludes 1 t et  D per entryW h em ds for t ets
  val t et d: Opt on[Long] = entryW h em ds. em ds.flatMap(_. ad.t et d)
}
