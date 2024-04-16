package com.tw ter.t  l neranker.server

 mport com.tw ter.thr ft bforms. thodOpt ons
 mport com.tw ter.thr ft bforms.v ew.Serv ceResponseV ew
 mport com.tw ter.t  l neranker.{thr ftscala => thr ft}
 mport com.tw ter.ut l.Future

object T  l neRankerThr ft bForms {

  pr vate def renderT et ds(t et Ds: Seq[Long]): Future[Serv ceResponseV ew] = {
    val html = t et Ds.map { t et D =>
      s"""<blockquote class="tw ter-t et"><a href="https://tw ter.com/t et/statuses/$t et D"></a></blockquote>"""
    }.mkStr ng
    Future.value(
      Serv ceResponseV ew(
        "T ets",
        html,
        Seq("//platform.tw ter.com/w dgets.js")
      )
    )
  }

  pr vate def renderGetCand dateT etsResponse(r: AnyRef): Future[Serv ceResponseV ew] = {
    val responses = r.as nstanceOf[Seq[thr ft.GetCand dateT etsResponse]]
    val t et ds = responses.flatMap(
      _.cand dates.map(_.flatMap(_.t et.map(_. d))).getOrElse(N l)
    )
    renderT et ds(t et ds)
  }

  def  thodOpt ons: Map[Str ng,  thodOpt ons] =
    Map(
      thr ft.T  l neRanker.GetRecycledT etCand dates.na  ->  thodOpt ons(
        responseRenderers = Seq(renderGetCand dateT etsResponse)
      ),
      thr ft.T  l neRanker.HydrateT etCand dates.na  ->  thodOpt ons(
        responseRenderers = Seq(renderGetCand dateT etsResponse)
      ),
      thr ft.T  l neRanker.GetRecapCand datesFromAuthors.na  ->  thodOpt ons(
        responseRenderers = Seq(renderGetCand dateT etsResponse)
      ),
      thr ft.T  l neRanker.GetEnt yT etCand dates.na  ->  thodOpt ons(
        responseRenderers = Seq(renderGetCand dateT etsResponse)
      )
    )
}
