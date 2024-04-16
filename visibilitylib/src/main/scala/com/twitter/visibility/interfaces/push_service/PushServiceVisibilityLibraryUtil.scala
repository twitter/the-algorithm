package com.tw ter.v s b l y. nterfaces.push_serv ce

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.t etyp e.thr ftscala.T et
 mport com.tw ter.v s b l y.bu lder.V s b l yResult
 mport com.tw ter.v s b l y.rules.Rule
 mport com.tw ter.v s b l y.rules.RuleResult
 mport com.tw ter.v s b l y.rules.State

object PushServ ceV s b l yL braryUt l {
  def ruleEnabled(ruleResult: RuleResult): Boolean = {
    ruleResult.state match {
      case State.D sabled => false
      case State.ShortC rcu ed => false
      case _ => true
    }
  }
  def getM ss ngFeatures(ruleResult: RuleResult): Set[Str ng] = {
    ruleResult.state match {
      case State.M ss ngFeature(features) => features.map(f => f.na )
      case _ => Set.empty
    }
  }
  def getM ss ngFeatureCounts(results: Seq[V s b l yResult]): Map[Str ng,  nt] = {
    results
      .flatMap(_.ruleResultMap.values.toL st)
      .flatMap(getM ss ngFeatures(_).toL st).groupBy( dent y).mapValues(_.length)
  }

  def logAllStats(
    response: PushServ ceV s b l yResponse
  )(
     mpl c  statsRece ver: StatsRece ver
  ) = {
    val rulesStatsRece ver = statsRece ver.scope("rules")
    logStats(response.t etV s b l yResult, rulesStatsRece ver.scope("t et"))
    logStats(response.authorV s b l yResult, rulesStatsRece ver.scope("author"))
  }

  def logStats(result: V s b l yResult, statsRece ver: StatsRece ver) = {
    result.ruleResultMap.toL st
      .f lter { case (_, ruleResult) => ruleEnabled(ruleResult) }
      .flatMap { case (rule, ruleResult) => getCounters(rule, ruleResult) }
      .foreach(statsRece ver.counter(_). ncr())
  }

  def getCounters(rule: Rule, ruleResult: RuleResult): L st[Str ng] = {
    val m ss ngFeatures = getM ss ngFeatures(ruleResult)
    L st(s"${rule.na }/${ruleResult.act on.na }") ++
      m ss ngFeatures.map(feat => s"${rule.na }/${feat}") ++
      m ss ngFeatures
  }

  def getAuthor d(t et: T et): Opt on[Long] = t et.coreData.map(_.user d)
  def  sRet et(t et: T et): Boolean = t et.coreData.flatMap(_.share). sDef ned
  def  sQuotedT et(t et: T et): Boolean = t et.quotedT et. sDef ned
}
