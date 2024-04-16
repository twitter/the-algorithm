package com.tw ter.t  l neranker.ut l

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.search.earlyb rd.thr ftscala.Thr ftSearchResult
 mport com.tw ter.t  l nes.model.T et d
 mport com.tw ter.t  l nes.model.User d
 mport com.tw ter.t  l nes.v s b l y.model.C ckedUserActor
 mport com.tw ter.t  l nes.v s b l y.model.HasV s b l yActors
 mport com.tw ter.t  l nes.v s b l y.model.V s b l yC ckUser

case class SearchResultW hV s b l yActors(
  searchResult: Thr ftSearchResult,
  statsRece ver: StatsRece ver)
    extends HasV s b l yActors {

  pr vate[t ] val searchResultW hout tadata =
    statsRece ver.counter("searchResultW hout tadata")

  val t et d: T et d = searchResult. d
  val  tadata = searchResult. tadata
  val (user d,  sRet et, s ceUser d, s ceT et d) =  tadata match {
    case So (md) => {
      (
        md.fromUser d,
        md. sRet et,
        md. sRet et.getOrElse(false) match {
          case true => So (md.referencedT etAuthor d)
          case false => None
        },
        //  tadata.sharedStatus d  s default ng to 0 for t ets that don't have one
        // 0  s not a val d t et  d so convert ng to None. Also d sregard ng sharedStatus d
        // for non-ret ets.
         f (md. sRet et. sDef ned && md. sRet et.get)
          md.sharedStatus d match {
            case 0 => None
            case  d => So ( d)
          }
        else None
      )
    }
    case None => {
      searchResultW hout tadata. ncr()
      throw new  llegalArgu ntExcept on(
        "searchResult  s m ss ng  tadata: " + searchResult.toStr ng)
    }
  }

  /**
   * Returns t  set of users (or 'actors') relevant for T et v s b l y f lter ng. Usually t 
   * T et author, but  f t   s a Ret et, t n t  s ce T et author  s also relevant.
   */
  def getV s b l yActors(v e r dOpt: Opt on[User d]): Seq[C ckedUserActor] = {
    val  sSelf =  sV e rAlsoT etAuthor(v e r dOpt, So (user d))
    Seq(
      So (C ckedUserActor( sSelf, V s b l yC ckUser.T eter, user d)),
      s ceUser d.map {
        C ckedUserActor( sSelf, V s b l yC ckUser.S ceUser, _)
      }
    ).flatten
  }
}
