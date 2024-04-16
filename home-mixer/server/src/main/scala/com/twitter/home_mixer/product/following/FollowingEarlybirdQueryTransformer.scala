package com.tw ter.ho _m xer.product.follow ng

 mport com.tw ter.f nagle.thr ft.Cl ent d
 mport com.tw ter.f nagle.trac ng.Trace
 mport com.tw ter.ho _m xer.model.Ho Features.RealGraph nNetworkScoresFeature
 mport com.tw ter.ho _m xer.product.follow ng.model.Follow ngQuery
 mport com.tw ter.ho _m xer.product.follow ng.param.Follow ngParam.ServerMaxResultsParam
 mport com.tw ter.product_m xer.component_l brary.feature_hydrator.query.soc al_graph.SGSFollo dUsersFeature
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateP pel neQueryTransfor r
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.operat on.BottomCursor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.operat on.GapCursor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.operat on.TopCursor
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.Malfor dCursor
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant
 mport com.tw ter.search.earlyb rd.{thr ftscala => t}
 mport com.tw ter.search.queryparser.query.Conjunct on
 mport com.tw ter.search.queryparser.query.search.SearchOperator
 mport javax. nject. nject
 mport javax. nject.S ngleton
 mport scala.jdk.Collect onConverters.asJava erableConverter

@S ngleton
case class Follow ngEarlyb rdQueryTransfor r @ nject() (cl ent d: Cl ent d)
    extends Cand dateP pel neQueryTransfor r[Follow ngQuery, t.Earlyb rdRequest] {

  overr de def transform(query: Follow ngQuery): t.Earlyb rdRequest = {
    val follo dUser ds =
      query.features.map(_.get(SGSFollo dUsersFeature)).getOrElse(Seq.empty).toSet
    val realGraph nNetworkFollo dUser ds =
      query.features.map(_.get(RealGraph nNetworkScoresFeature)).getOrElse(Map.empty).keySet
    val user d = query.getRequ redUser d
    val comb nedUser ds = user d +: follo dUser ds.toSeq

    val baseFollo dUsersSearchOperator = new SearchOperator.Bu lder()
      .setType(SearchOperator.Type.FEATURE_VALUE_ N_ACCEPT_L ST_OR_UNSET)
      .addOperand(Earlyb rdF eldConstant.D RECTED_AT_USER_ D_CSF.getF eldNa )

    val follo dUsersQuery =
      baseFollo dUsersSearchOperator.addOperands(comb nedUser ds.map(_.toStr ng).asJava).bu ld()

    val searchQuery = query.p pel neCursor
      .map { cursor =>
        val s nce dQuery =
          ( d: Long) => new SearchOperator(SearchOperator.Type.S NCE_ D,  d.toStr ng)
        val max dQuery = // max  D  s  nclus ve, so subtract 1
          ( d: Long) => new SearchOperator(SearchOperator.Type.MAX_ D, ( d - 1).toStr ng)

        (cursor.cursorType, cursor. d, cursor.gapBoundary d) match {
          case (So (TopCursor), So (s nce d), _) =>
            new Conjunct on(s nce dQuery(s nce d), follo dUsersQuery)
          case (So (BottomCursor), So (max d), _) =>
            new Conjunct on(max dQuery(max d), follo dUsersQuery)
          case (So (GapCursor), So (max d), So (s nce d)) =>
            new Conjunct on(s nce dQuery(s nce d), max dQuery(max d), follo dUsersQuery)
          case (So (GapCursor), _, _) =>
            throw P pel neFa lure(Malfor dCursor, " nval d cursor " + cursor.toStr ng)
          case _ => follo dUsersQuery
        }
      }.getOrElse(follo dUsersQuery)

    val  tadataOpt ons = t.Thr ftSearchResult tadataOpt ons(
      get nReplyToStatus d = true,
      getReferencedT etAuthor d = true,
      getFromUser d = true
    )

    t.Earlyb rdRequest(
      searchQuery = t.Thr ftSearchQuery(
        ser al zedQuery = So (searchQuery.ser al ze),
        fromUser DF lter64 = So (comb nedUser ds),
        numResults = query.requestedMaxResults.getOrElse(query.params(ServerMaxResultsParam)),
        rank ngMode = t.Thr ftSearchRank ngMode.Recency,
        result tadataOpt ons = So ( tadataOpt ons),
        searc r d = query.getOpt onalUser d,
      ),
      getOlderResults = So (true), // needed for arch ve access to older t ets
      cl entRequest D = So (s"${Trace. d.trace d}"),
      follo dUser ds = So (comb nedUser ds),
      numResultsToReturnAtRoot = So (query.params(ServerMaxResultsParam)),
      cl ent d = So (cl ent d.na ),
    )
  }
}
