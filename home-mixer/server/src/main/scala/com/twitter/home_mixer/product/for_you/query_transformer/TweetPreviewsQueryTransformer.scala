package com.tw ter.ho _m xer.product.for_ .query_transfor r

 mport com.tw ter.convers ons.Durat onOps.r chDurat onFrom nt
 mport com.tw ter.f nagle.thr ft.Cl ent d
 mport com.tw ter.f nagle.trac ng.Trace
 mport com.tw ter.product_m xer.component_l brary.feature_hydrator.query.soc al_graph.Prev ewCreatorsFeature
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateP pel neQueryTransfor r
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.search.common.rank ng.{thr ftscala => scr}
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant
 mport com.tw ter.search.earlyb rd.{thr ftscala => t}
 mport com.tw ter.search.queryparser.query.Conjunct on
 mport com.tw ter.search.queryparser.query.Query
 mport com.tw ter.search.queryparser.query.search.SearchOperator
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class T etPrev ewsQueryTransfor r @ nject() (cl ent d: Cl ent d)
    extends Cand dateP pel neQueryTransfor r[P pel neQuery, t.Earlyb rdRequest] {

  pr vate val MaxPrev ewT ets = 200
  pr vate val Earlyb rdRelevanceTensorflowModel = "t  l nes_rect et_repl ca"
  pr vate val S nceDurat on = 7.days

  val  tadataOpt ons = t.Thr ftSearchResult tadataOpt ons(
    getReferencedT etAuthor d = true,
    getFromUser d = true
  )

  overr de def transform(query: P pel neQuery): t.Earlyb rdRequest = {
    val cand datePrev ewCreator ds =
      query.features.map(_.get(Prev ewCreatorsFeature)).getOrElse(Seq.empty)

    val searchQuery = new Conjunct on(
      //  nclude subscr ber only (aka exclus ve) T ets
      new SearchOperator.Bu lder()
        .setType(SearchOperator.Type.F LTER)
        .addOperand(Earlyb rdF eldConstant.EXCLUS VE_F LTER_TERM)
        .bu ld(),
      //  nclude only or g nal T ets
      new SearchOperator.Bu lder()
        .setType(SearchOperator.Type.F LTER)
        .addOperand(Earlyb rdF eldConstant.NAT VE_RETWEETS_F LTER_TERM)
        .setOccur(Query.Occur.MUST_NOT)
        .bu ld(),
      new SearchOperator.Bu lder()
        .setType(SearchOperator.Type.F LTER)
        .addOperand(Earlyb rdF eldConstant.REPL ES_F LTER_TERM)
        .setOccur(Query.Occur.MUST_NOT)
        .bu ld(),
      new SearchOperator.Bu lder()
        .setType(SearchOperator.Type.F LTER)
        .addOperand(Earlyb rdF eldConstant.QUOTE_F LTER_TERM)
        .setOccur(Query.Occur.MUST_NOT)
        .bu ld(),
      new SearchOperator(SearchOperator.Type.S NCE_T ME, S nceDurat on.ago. nSeconds.toStr ng)
    )

    t.Earlyb rdRequest(
      searchQuery = t.Thr ftSearchQuery(
        ser al zedQuery = So (searchQuery.ser al ze),
        fromUser DF lter64 = So (cand datePrev ewCreator ds),
        numResults = MaxPrev ewT ets,
        rank ngMode = t.Thr ftSearchRank ngMode.Relevance,
        relevanceOpt ons = So (
          t.Thr ftSearchRelevanceOpt ons(
            f lterDups = true,
            keepDupW hH g rScore = true,
            prox m yScor ng = true,
            maxConsecut veSa User = So (5),
            rank ngParams = So (
              scr.Thr ftRank ngParams(
                `type` = So (scr.Thr ftScor ngFunct onType.TensorflowBased),
                selectedTensorflowModel = So (Earlyb rdRelevanceTensorflowModel),
                m nScore = -1.0e100,
                applyBoosts = false,
              )
            ),
          ),
        ),
        result tadataOpt ons = So ( tadataOpt ons),
        searc r d = query.getOpt onalUser d,
      ),
      getOlderResults = So (true), // needed for arch ve access to older t ets
      cl entRequest D = So (s"${Trace. d.trace d}"),
      follo dUser ds = So (cand datePrev ewCreator ds.toSeq),
      numResultsToReturnAtRoot = So (MaxPrev ewT ets),
      cl ent d = So (cl ent d.na ),
    )
  }
}
