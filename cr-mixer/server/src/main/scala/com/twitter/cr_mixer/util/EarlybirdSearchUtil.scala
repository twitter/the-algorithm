package com.tw ter.cr_m xer.ut l

 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant
 mport com.tw ter.search.queryparser.query.search.SearchOperator
 mport com.tw ter.search.queryparser.query.search.SearchOperatorConstants
 mport com.tw ter.search.queryparser.query.{Query => EbQuery}
 mport com.tw ter.search.queryparser.query.Conjunct on
 mport scala.collect on.JavaConverters._
 mport com.tw ter.search.earlyb rd.thr ftscala.Thr ftSearchResult tadataOpt ons
 mport com.tw ter.s mclusters_v2.common.T et d
 mport com.tw ter.search.queryparser.query.Query
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.search.common.query.thr ftjava.thr ftscala.CollectorTerm nat onParams

object Earlyb rdSearchUt l {
  val Earlyb rdCl ent d: Str ng = "cr-m xer.prod"

  val  nt ons: Str ng = Earlyb rdF eldConstant.MENT ONS_FACET
  val Hashtags: Str ng = Earlyb rdF eldConstant.HASHTAGS_FACET
  val FacetsToFetch: Seq[Str ng] = Seq( nt ons, Hashtags)

  val  tadataOpt ons: Thr ftSearchResult tadataOpt ons = Thr ftSearchResult tadataOpt ons(
    getT etUrls = true,
    getResultLocat on = false,
    getLuceneScore = false,
    get nReplyToStatus d = true,
    getReferencedT etAuthor d = true,
    get d aB s = true,
    getAllFeatures = true,
    getFromUser d = true,
    returnSearchResultFeatures = true,
    // Set getExclus veConversat onAuthor d  n order to retr eve Exclus ve / SuperFollow t ets.
    getExclus veConversat onAuthor d = true
  )

  // F lter out ret ets and repl es
  val T etTypesToExclude: Seq[Str ng] =
    Seq(
      SearchOperatorConstants.NAT VE_RETWEETS,
      SearchOperatorConstants.REPL ES)

  def GetCollectorTerm nat onParams(
    maxNumH sPerShard:  nt,
    process ngT  out: Durat on
  ): Opt on[CollectorTerm nat onParams] = {
    So (
      CollectorTerm nat onParams(
        // maxH sToProcess  s used for early term nat on on each EB shard
        maxH sToProcess = So (maxNumH sPerShard),
        t  outMs = process ngT  out. nM ll seconds.to nt
      ))
  }

  /**
   * Get Earlyb rdQuery
   * T  funct on creates a EBQuery based on t  search  nput
   */
  def GetEarlyb rdQuery(
    beforeT et dExclus ve: Opt on[T et d],
    afterT et dExclus ve: Opt on[T et d],
    excludedT et ds: Set[T et d],
    f lterOutRet etsAndRepl es: Boolean
  ): Opt on[EbQuery] =
    CreateConjunct on(
      Seq(
        CreateRangeQuery(beforeT et dExclus ve, afterT et dExclus ve),
        CreateExcludedT et dsQuery(excludedT et ds),
        CreateT etTypesF lters(f lterOutRet etsAndRepl es)
      ).flatten)

  def CreateRangeQuery(
    beforeT et dExclus ve: Opt on[T et d],
    afterT et dExclus ve: Opt on[T et d]
  ): Opt on[EbQuery] = {
    val before dClause = beforeT et dExclus ve.map { before d =>
      // MAX_ D  s an  nclus ve value t refore   subtract 1 from before d.
      new SearchOperator(SearchOperator.Type.MAX_ D, (before d - 1).toStr ng)
    }
    val after dClause = afterT et dExclus ve.map { after d =>
      new SearchOperator(SearchOperator.Type.S NCE_ D, after d.toStr ng)
    }
    CreateConjunct on(Seq(before dClause, after dClause).flatten)
  }

  def CreateT etTypesF lters(f lterOutRet etsAndRepl es: Boolean): Opt on[EbQuery] = {
     f (f lterOutRet etsAndRepl es) {
      val t etTypeF lters = T etTypesToExclude.map { searchOperator =>
        new SearchOperator(SearchOperator.Type.EXCLUDE, searchOperator)
      }
      CreateConjunct on(t etTypeF lters)
    } else None
  }

  def CreateConjunct on(clauses: Seq[EbQuery]): Opt on[EbQuery] = {
    clauses.s ze match {
      case 0 => None
      case 1 => So (clauses. ad)
      case _ => So (new Conjunct on(clauses.asJava))
    }
  }

  def CreateExcludedT et dsQuery(t et ds: Set[T et d]): Opt on[EbQuery] = {
     f (t et ds.nonEmpty) {
      So (
        new SearchOperator.Bu lder()
          .setType(SearchOperator.Type.NAMED_MULT _TERM_D SJUNCT ON)
          .addOperand(Earlyb rdF eldConstant. D_F ELD.getF eldNa )
          .addOperand(EXCLUDE_TWEET_ DS)
          .setOccur(Query.Occur.MUST_NOT)
          .bu ld())
    } else None
  }

  /**
   * Get Na dD sjunct ons w h excludedT et ds
   */
  def GetNa dD sjunct ons(excludedT et ds: Set[T et d]): Opt on[Map[Str ng, Seq[Long]]] =
     f (excludedT et ds.nonEmpty)
      createNa dD sjunct onsExcludedT et ds(excludedT et ds)
    else None

  val EXCLUDE_TWEET_ DS = "exclude_t et_ ds"
  pr vate def createNa dD sjunct onsExcludedT et ds(
    t et ds: Set[T et d]
  ): Opt on[Map[Str ng, Seq[Long]]] = {
     f (t et ds.nonEmpty) {
      So (Map(EXCLUDE_TWEET_ DS -> t et ds.toSeq))
    } else None
  }
}
