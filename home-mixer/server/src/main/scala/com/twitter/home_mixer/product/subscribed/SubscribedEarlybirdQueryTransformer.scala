package com.tw ter.ho _m xer.product.subscr bed

 mport com.tw ter.f nagle.thr ft.Cl ent d
 mport com.tw ter.f nagle.trac ng.Trace
 mport com.tw ter.ho _m xer.product.subscr bed.model.Subscr bedQuery
 mport com.tw ter.ho _m xer.product.subscr bed.param.Subscr bedParam.ServerMaxResultsParam
 mport com.tw ter.product_m xer.component_l brary.feature_hydrator.query.soc al_graph.SGSSubscr bedUsersFeature
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

@S ngleton
case class Subscr bedEarlyb rdQueryTransfor r @ nject() (cl ent d: Cl ent d)
    extends Cand dateP pel neQueryTransfor r[Subscr bedQuery, t.Earlyb rdRequest] {

  overr de def transform(query: Subscr bedQuery): t.Earlyb rdRequest = {
    val subscr bedUser ds =
      query.features.map(_.get(SGSSubscr bedUsersFeature)).getOrElse(Seq.empty)

    val subscr bedUsersQuery = new SearchOperator.Bu lder()
      .setType(SearchOperator.Type.F LTER)
      .addOperand(Earlyb rdF eldConstant.EXCLUS VE_F LTER_TERM)
      .bu ld()

    val searchQuery = query.p pel neCursor
      .map { cursor =>
        val s nce dQuery =
          ( d: Long) => new SearchOperator(SearchOperator.Type.S NCE_ D,  d.toStr ng)
        val max dQuery = // max  D  s  nclus ve, so subtract 1
          ( d: Long) => new SearchOperator(SearchOperator.Type.MAX_ D, ( d - 1).toStr ng)

        (cursor.cursorType, cursor. d, cursor.gapBoundary d) match {
          case (So (TopCursor), So (s nce d), _) =>
            new Conjunct on(s nce dQuery(s nce d), subscr bedUsersQuery)
          case (So (BottomCursor), So (max d), _) =>
            new Conjunct on(max dQuery(max d), subscr bedUsersQuery)
          case (So (GapCursor), So (max d), So (s nce d)) =>
            new Conjunct on(s nce dQuery(s nce d), max dQuery(max d), subscr bedUsersQuery)
          case (So (GapCursor), _, _) =>
            throw P pel neFa lure(Malfor dCursor, " nval d cursor " + cursor.toStr ng)
          case _ => subscr bedUsersQuery
        }
      }.getOrElse(subscr bedUsersQuery)

    t.Earlyb rdRequest(
      searchQuery = t.Thr ftSearchQuery(
        ser al zedQuery = So (searchQuery.ser al ze),
        fromUser DF lter64 = So (subscr bedUser ds),
        numResults = query.requestedMaxResults.getOrElse(query.params(ServerMaxResultsParam)),
        rank ngMode = t.Thr ftSearchRank ngMode.Recency,
      ),
      getOlderResults = So (true), // needed for arch ve access to older t ets
      cl entRequest D = So (s"${Trace. d.trace d}"),
      numResultsToReturnAtRoot = So (query.params(ServerMaxResultsParam)),
      cl ent d = So (cl ent d.na ),
    )
  }
}
