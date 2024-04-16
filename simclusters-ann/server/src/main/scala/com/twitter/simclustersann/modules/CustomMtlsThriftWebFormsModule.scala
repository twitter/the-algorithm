package com.tw ter.s mclustersann.modules

 mport com.tw ter.f natra.mtls.thr ftmux.modules.MtlsThr ft bFormsModule
 mport com.tw ter.f natra.thr ft.Thr ftServer
 mport com.tw ter.s mclusters_v2.thr ftscala.Embedd ngType
 mport com.tw ter.s mclusters_v2.thr ftscala. nternal d
 mport com.tw ter.s mclusters_v2.thr ftscala.ModelVers on
 mport com.tw ter.s mclusters_v2.thr ftscala.S mClustersEmbedd ng d
 mport com.tw ter.thr ft bforms. thodOpt ons
 mport com.tw ter.thr ft bforms.v ew.Serv ceResponseV ew
 mport com.tw ter.ut l.Future
 mport com.tw ter.s mclustersann.thr ftscala.S mClustersANNT etCand date
 mport com.tw ter.s mclustersann.thr ftscala.Query
 mport com.tw ter.s mclustersann.thr ftscala.S mClustersANNConf g
 mport com.tw ter.s mclustersann.thr ftscala.Scor ngAlgor hm
 mport com.tw ter.thr ft bforms. thodOpt ons.Access
 mport scala.reflect.ClassTag
 mport com.tw ter.s mclustersann.thr ftscala.S mClustersANNServ ce
 mport scala.collect on.mutable

class CustomMtlsThr ft bFormsModule[T: ClassTag](server: Thr ftServer)
    extends MtlsThr ft bFormsModule[T](server: Thr ftServer) {

  pr vate val Nbsp = "&nbsp;"
  pr vate val LdapGroups = Seq("recosplat-sens  ve-data- d um", "s mclusters-ann-adm ns")

  overr de protected def  thodOpt ons: Map[Str ng,  thodOpt ons] = {
    val t et d = 1568796529690902529L
    val sannDefaultQuery = S mClustersANNServ ce.GetT etCand dates.Args(
      query = Query(
        s ceEmbedd ng d = S mClustersEmbedd ng d(
          embedd ngType = Embedd ngType.LogFavLongestL2Embedd ngT et,
          modelVers on = ModelVers on.Model20m145k2020,
           nternal d =  nternal d.T et d(t et d)
        ),
        conf g = S mClustersANNConf g(
          maxNumResults = 10,
          m nScore = 0.0,
          cand dateEmbedd ngType = Embedd ngType.LogFavBasedT et,
          maxTopT etsPerCluster = 400,
          maxScanClusters = 50,
          maxT etCand dateAgeH s = 24,
          m nT etCand dateAgeH s = 0,
          annAlgor hm = Scor ngAlgor hm.Cos neS m lar y
        )
      ))

    Seq("getT etCand dates")
      .map(
        _ ->  thodOpt ons(
          defaultRequestValue = So (sannDefaultQuery),
          responseRenderers = Seq(renderT  l ne),
          allo dAccessOverr de = So (Access.ByLdapGroup(LdapGroups))
        )).toMap
  }

  val FullAccessLdapGroups: Seq[Str ng] =
    Seq(
      "recosplat-sens  ve-data- d um",
      "s mclusters-ann-adm ns",
      "recos-platform-adm ns"
    )

  overr de protected def default thodAccess:  thodOpt ons.Access = {
     thodOpt ons.Access.ByLdapGroup(FullAccessLdapGroups)
  }

  def renderT  l ne(r: AnyRef): Future[Serv ceResponseV ew] = {
    val s mClustersANNT etCand dates = r match {
      case response:  erable[_] =>
        response.map(x => x.as nstanceOf[S mClustersANNT etCand date]).toSeq
      case _ => Seq()
    }
    renderT ets(s mClustersANNT etCand dates)
  }

  pr vate def renderT ets(
    s mClustersANNT etCand dates: Seq[S mClustersANNT etCand date]
  ): Future[Serv ceResponseV ew] = {
    val htmlSb = new mutable.Str ngBu lder()
    val  aderHtml = s"""<h3>T et Cand dates</h3>"""
    val t etsHtml = s mClustersANNT etCand dates.map { s mClustersANNT etCand date =>
      val t et d = s mClustersANNT etCand date.t et d
      val score = s mClustersANNT etCand date.score
      s"""<blockquote class="tw ter-t et"><a href="https://tw ter.com/t et/statuses/$t et d"></a></blockquote> <b>score:</b> $score <br><br>"""
    }.mkStr ng

    htmlSb ++=  aderHtml
    htmlSb ++= Nbsp
    htmlSb ++= t etsHtml
    Future.value(
      Serv ceResponseV ew(
        "S mClusters ANN T et Cand dates",
        htmlSb.toStr ng(),
        Seq("//platform.tw ter.com/w dgets.js")
      )
    )
  }
}
