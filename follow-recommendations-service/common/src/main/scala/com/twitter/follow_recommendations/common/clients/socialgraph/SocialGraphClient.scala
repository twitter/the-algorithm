package com.tw ter.follow_recom ndat ons.common.cl ents.soc algraph

 mport com.tw ter.esc rb rd.ut l.st chcac .St chCac 
 mport com.tw ter.f nagle.stats.NullStatsRece ver
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.base.StatsUt l
 mport com.tw ter.follow_recom ndat ons.common.models.FollowProof
 mport com.tw ter.follow_recom ndat ons.common.models.User dW hT  stamp
 mport com.tw ter. nject.Logg ng
 mport com.tw ter.soc algraph.thr ftscala.EdgesRequest
 mport com.tw ter.soc algraph.thr ftscala. dsRequest
 mport com.tw ter.soc algraph.thr ftscala. dsResult
 mport com.tw ter.soc algraph.thr ftscala.LookupContext
 mport com.tw ter.soc algraph.thr ftscala.OverCapac y
 mport com.tw ter.soc algraph.thr ftscala.PageRequest
 mport com.tw ter.soc algraph.thr ftscala.Relat onsh pType
 mport com.tw ter.soc algraph.thr ftscala.SrcRelat onsh p
 mport com.tw ter.soc algraph.ut l.ByteBufferUt l
 mport com.tw ter.st ch.St ch
 mport com.tw ter.st ch.soc algraph.Soc alGraph
 mport com.tw ter.strato.cl ent.Fetc r
 mport com.tw ter.strato.generated.cl ent.onboard ng.soc alGraphServ ce. dsCl entColumn
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.T  
 mport java.n o.ByteBuffer
 mport javax. nject. nject
 mport javax. nject.S ngleton

case class RecentEdgesQuery(
  user d: Long,
  relat ons: Seq[Relat onsh pType],
  // prefer to default value to better ut l ze t  cach ng funct on of st ch
  count: Opt on[ nt] = So (Soc alGraphCl ent.MaxQueryS ze),
  performUn on: Boolean = true,
  recentEdgesW ndowOpt: Opt on[Durat on] = None,
  targets: Opt on[Seq[Long]] = None)

case class EdgeRequestQuery(
  user d: Long,
  relat on: Relat onsh pType,
  count: Opt on[ nt] = So (Soc alGraphCl ent.MaxQueryS ze),
  performUn on: Boolean = true,
  recentEdgesW ndowOpt: Opt on[Durat on] = None,
  targets: Opt on[Seq[Long]] = None)

@S ngleton
class Soc alGraphCl ent @ nject() (
  soc alGraph: Soc alGraph,
   dsCl entColumn:  dsCl entColumn,
  statsRece ver: StatsRece ver = NullStatsRece ver)
    extends Logg ng {

  pr vate val stats = statsRece ver.scope(t .getClass.getS mpleNa )
  pr vate val cac Stats = stats.scope("cac ")
  pr vate val get ntersect onsStats = stats.scope("get ntersect ons")
  pr vate val get ntersect onsFromCac dColumnStats =
    stats.scope("get ntersect onsFromCac dColumn")
  pr vate val getRecentEdgesStats = stats.scope("getRecentEdges")
  pr vate val getRecentEdgesCac dStats = stats.scope("getRecentEdgesCac d")
  pr vate val getRecentEdgesFromCac dColumnStats = stats.scope("getRecentEdgesFromCac dColumn")
  pr vate val getRecentEdgesCac d nternalStats = stats.scope("getRecentEdgesCac d nternal")
  pr vate val getRecentEdgesW hT  Stats = stats.scope("getRecentEdgesW hT  ")

  val sgs dsFetc r: Fetc r[ dsRequest, Un ,  dsResult] =  dsCl entColumn.fetc r

  pr vate val recentEdgesCac  = St chCac [RecentEdgesQuery, Seq[Long]](
    maxCac S ze = Soc alGraphCl ent.MaxCac S ze,
    ttl = Soc alGraphCl ent.Cac TTL,
    statsRece ver = cac Stats,
    underly ngCall = getRecentEdges
  )

  def getRecentEdgesCac d(
    rq: RecentEdgesQuery,
    useCac dStratoColumn: Boolean = true
  ): St ch[Seq[Long]] = {
    getRecentEdgesCac dStats.counter("requests"). ncr()
     f (useCac dStratoColumn) {
      getRecentEdgesFromCac dColumn(rq)
    } else {
      StatsUt l.prof leSt ch(
        getRecentEdgesCac d nternal(rq),
        getRecentEdgesCac d nternalStats
      )
    }
  }

  def getRecentEdgesCac d nternal(rq: RecentEdgesQuery): St ch[Seq[Long]] = {
    recentEdgesCac .readThrough(rq)
  }

  def getRecentEdgesFromCac dColumn(rq: RecentEdgesQuery): St ch[Seq[Long]] = {
    val pageRequest = rq.recentEdgesW ndowOpt match {
      case So (recentEdgesW ndow) =>
        PageRequest(
          count = rq.count,
          cursor = So (getEdgeCursor(recentEdgesW ndow)),
          selectAll = So (true)
        )
      case _ => PageRequest(count = rq.count)
    }
    val  dsRequest =  dsRequest(
      rq.relat ons.map { relat onsh pType =>
        SrcRelat onsh p(
          s ce = rq.user d,
          relat onsh pType = relat onsh pType,
          targets = rq.targets
        )
      },
      pageRequest = So (pageRequest),
      context = So (LookupContext(performUn on = So (rq.performUn on)))
    )

    val soc alGraphSt ch = sgs dsFetc r
      .fetch( dsRequest, Un )
      .map(_.v)
      .map { result =>
        result
          .map {  dResult =>
            val user ds: Seq[Long] =  dResult. ds
            getRecentEdgesFromCac dColumnStats.stat("num_edges").add(user ds.s ze)
            user ds
          }.getOrElse(Seq.empty)
      }
      .rescue {
        case e: Except on =>
          stats.counter(e.getClass.getS mpleNa ). ncr()
          St ch.N l
      }

    StatsUt l.prof leSt ch(
      soc alGraphSt ch,
      getRecentEdgesFromCac dColumnStats
    )
  }

  def getRecentEdges(rq: RecentEdgesQuery): St ch[Seq[Long]] = {
    val pageRequest = rq.recentEdgesW ndowOpt match {
      case So (recentEdgesW ndow) =>
        PageRequest(
          count = rq.count,
          cursor = So (getEdgeCursor(recentEdgesW ndow)),
          selectAll = So (true)
        )
      case _ => PageRequest(count = rq.count)
    }
    val soc alGraphSt ch = soc alGraph
      . ds(
         dsRequest(
          rq.relat ons.map { relat onsh pType =>
            SrcRelat onsh p(
              s ce = rq.user d,
              relat onsh pType = relat onsh pType,
              targets = rq.targets
            )
          },
          pageRequest = So (pageRequest),
          context = So (LookupContext(performUn on = So (rq.performUn on)))
        )
      )
      .map {  dsResult =>
        val user ds: Seq[Long] =  dsResult. ds
        getRecentEdgesStats.stat("num_edges").add(user ds.s ze)
        user ds
      }
      .rescue {
        case e: OverCapac y =>
          stats.counter(e.getClass.getS mpleNa ). ncr()
          logger.warn("SGS Over Capac y", e)
          St ch.N l
      }
    StatsUt l.prof leSt ch(
      soc alGraphSt ch,
      getRecentEdgesStats
    )
  }

  // T   thod return recent edges of (user d, t   nMs)
  def getRecentEdgesW hT  (rq: EdgeRequestQuery): St ch[Seq[User dW hT  stamp]] = {
    val pageRequest = rq.recentEdgesW ndowOpt match {
      case So (recentEdgesW ndow) =>
        PageRequest(
          count = rq.count,
          cursor = So (getEdgeCursor(recentEdgesW ndow)),
          selectAll = So (true)
        )
      case _ => PageRequest(count = rq.count)
    }

    val soc alGraphSt ch = soc alGraph
      .edges(
        EdgesRequest(
          SrcRelat onsh p(
            s ce = rq.user d,
            relat onsh pType = rq.relat on,
            targets = rq.targets
          ),
          pageRequest = So (pageRequest),
          context = So (LookupContext(performUn on = So (rq.performUn on)))
        )
      )
      .map { edgesResult =>
        val user ds = edgesResult.edges.map { soc alEdge =>
          User dW hT  stamp(soc alEdge.target, soc alEdge.updatedAt)
        }
        getRecentEdgesW hT  Stats.stat("num_edges").add(user ds.s ze)
        user ds
      }
      .rescue {
        case e: OverCapac y =>
          stats.counter(e.getClass.getS mpleNa ). ncr()
          logger.warn("SGS Over Capac y", e)
          St ch.N l
      }
    StatsUt l.prof leSt ch(
      soc alGraphSt ch,
      getRecentEdgesW hT  Stats
    )
  }

  // T   thod returns t  cursor for a t   durat on, such that all t  edges returned by SGS w ll be created
  //  n t  range (now-w ndow, now)
  def getEdgeCursor(w ndow: Durat on): ByteBuffer = {
    val cursor nLong = (-(T  .now - w ndow). nM ll seconds) << 20
    ByteBufferUt l.fromLong(cursor nLong)
  }

  // not ce that t   s more expens ve but more realt   than t  GFS one
  def get ntersect ons(
    user d: Long,
    cand date ds: Seq[Long],
    num ntersect on ds:  nt
  ): St ch[Map[Long, FollowProof]] = {
    val soc alGraphSt ch: St ch[Map[Long, FollowProof]] = St ch
      .collect(cand date ds.map { cand date d =>
        soc alGraph
          . ds(
             dsRequest(
              Seq(
                SrcRelat onsh p(user d, Relat onsh pType.Follow ng),
                SrcRelat onsh p(cand date d, Relat onsh pType.Follo dBy)
              ),
              pageRequest = So (PageRequest(count = So (num ntersect on ds)))
            )
          ).map {  dsResult =>
            get ntersect onsStats.stat("num_edges").add( dsResult. ds.s ze)
            (cand date d -> FollowProof( dsResult. ds,  dsResult. ds.s ze))
          }
      }).map(_.toMap)
      .rescue {
        case e: OverCapac y =>
          stats.counter(e.getClass.getS mpleNa ). ncr()
          logger.warn("soc al graph over capac y  n hydrat ng soc al proof", e)
          St ch.value(Map.empty)
      }
    StatsUt l.prof leSt ch(
      soc alGraphSt ch,
      get ntersect onsStats
    )
  }

  def get ntersect onsFromCac dColumn(
    user d: Long,
    cand date ds: Seq[Long],
    num ntersect on ds:  nt
  ): St ch[Map[Long, FollowProof]] = {
    val soc alGraphSt ch: St ch[Map[Long, FollowProof]] = St ch
      .collect(cand date ds.map { cand date d =>
        val  dsRequest =  dsRequest(
          Seq(
            SrcRelat onsh p(user d, Relat onsh pType.Follow ng),
            SrcRelat onsh p(cand date d, Relat onsh pType.Follo dBy)
          ),
          pageRequest = So (PageRequest(count = So (num ntersect on ds)))
        )

        sgs dsFetc r
          .fetch( dsRequest, Un )
          .map(_.v)
          .map { resultOpt =>
            resultOpt.map {  dsResult =>
              get ntersect onsFromCac dColumnStats.stat("num_edges").add( dsResult. ds.s ze)
              cand date d -> FollowProof( dsResult. ds,  dsResult. ds.s ze)
            }
          }
      }).map(_.flatten.toMap)
      .rescue {
        case e: Except on =>
          stats.counter(e.getClass.getS mpleNa ). ncr()
          St ch.value(Map.empty)
      }
    StatsUt l.prof leSt ch(
      soc alGraphSt ch,
      get ntersect onsFromCac dColumnStats
    )
  }

  def get nval dRelat onsh pUser ds(
    user d: Long,
    maxNumRelat onsh p:  nt = Soc alGraphCl ent.MaxNum nval dRelat onsh p
  ): St ch[Seq[Long]] = {
    getRecentEdges(
      RecentEdgesQuery(
        user d,
        Soc alGraphCl ent. nval dRelat onsh pTypes,
        So (maxNumRelat onsh p)
      )
    )
  }

  def get nval dRelat onsh pUser dsFromCac dColumn(
    user d: Long,
    maxNumRelat onsh p:  nt = Soc alGraphCl ent.MaxNum nval dRelat onsh p
  ): St ch[Seq[Long]] = {
    getRecentEdgesFromCac dColumn(
      RecentEdgesQuery(
        user d,
        Soc alGraphCl ent. nval dRelat onsh pTypes,
        So (maxNumRelat onsh p)
      )
    )
  }

  def getRecentFollo dUser ds(user d: Long): St ch[Seq[Long]] = {
    getRecentEdges(
      RecentEdgesQuery(
        user d,
        Seq(Relat onsh pType.Follow ng)
      )
    )
  }

  def getRecentFollo dUser dsFromCac dColumn(user d: Long): St ch[Seq[Long]] = {
    getRecentEdgesFromCac dColumn(
      RecentEdgesQuery(
        user d,
        Seq(Relat onsh pType.Follow ng)
      )
    )
  }

  def getRecentFollo dUser dsW hT  (user d: Long): St ch[Seq[User dW hT  stamp]] = {
    getRecentEdgesW hT  (
      EdgeRequestQuery(
        user d,
        Relat onsh pType.Follow ng
      )
    )
  }

  def getRecentFollo dByUser ds(user d: Long): St ch[Seq[Long]] = {
    getRecentEdges(
      RecentEdgesQuery(
        user d,
        Seq(Relat onsh pType.Follo dBy)
      )
    )
  }

  def getRecentFollo dByUser dsFromCac dColumn(user d: Long): St ch[Seq[Long]] = {
    getRecentEdgesFromCac dColumn(
      RecentEdgesQuery(
        user d,
        Seq(Relat onsh pType.Follo dBy)
      )
    )
  }

  def getRecentFollo dUser dsW hT  W ndow(
    user d: Long,
    t  W ndow: Durat on
  ): St ch[Seq[Long]] = {
    getRecentEdges(
      RecentEdgesQuery(
        user d,
        Seq(Relat onsh pType.Follow ng),
        recentEdgesW ndowOpt = So (t  W ndow)
      )
    )
  }
}

object Soc alGraphCl ent {

  val MaxQueryS ze:  nt = 500
  val MaxCac S ze:  nt = 5000000
  // Ref: src/thr ft/com/tw ter/soc algraph/soc al_graph_serv ce.thr ft
  val MaxNum nval dRelat onsh p:  nt = 5000
  val Cac TTL: Durat on = Durat on.fromH s(24)

  val  nval dRelat onsh pTypes: Seq[Relat onsh pType] = Seq(
    Relat onsh pType.H deRecom ndat ons,
    Relat onsh pType.Block ng,
    Relat onsh pType.BlockedBy,
    Relat onsh pType.Mut ng,
    Relat onsh pType.MutedBy,
    Relat onsh pType.ReportedAsSpam,
    Relat onsh pType.ReportedAsSpamBy,
    Relat onsh pType.ReportedAsAbuse,
    Relat onsh pType.ReportedAsAbuseBy,
    Relat onsh pType.FollowRequestOutgo ng,
    Relat onsh pType.Follow ng,
    Relat onsh pType.UsedToFollow,
  )

  /**
   *
   * W t r to call SGS to val date each cand date based on t  number of  nval d relat onsh p users
   * prefetc d dur ng request bu ld ng step. T  a ms to not om  any  nval d cand dates that are
   * not f ltered out  n prev ous steps.
   *    f t  number  s 0, t  m ght be a fa l-opened SGS call.
   *    f t  number  s larger or equal to 5000, t  could h  SGS page s ze l m .
   * Both cases account for a small percentage of t  total traff c (<5%).
   *
   * @param num nval dRelat onsh pUsers number of  nval d relat onsh p users fetc d from get nval dRelat onsh pUser ds
   * @return w t r to enable post-ranker SGS pred cate
   */
  def enablePostRankerSgsPred cate(num nval dRelat onsh pUsers:  nt): Boolean = {
    num nval dRelat onsh pUsers == 0 || num nval dRelat onsh pUsers >= MaxNum nval dRelat onsh p
  }
}
