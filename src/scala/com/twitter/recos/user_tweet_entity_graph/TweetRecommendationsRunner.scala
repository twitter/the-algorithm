package com.tw ter.recos.user_t et_ent y_graph

 mport java.ut l.Random
 mport com.tw ter.concurrent.AsyncQueue
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.graphjet.algor hms._
 mport com.tw ter.graphjet.algor hms.f lters._
 mport com.tw ter.graphjet.algor hms.count ng.TopSecondDegreeByCountResponse
 mport com.tw ter.graphjet.algor hms.count ng.t et.TopSecondDegreeByCountForT et
 mport com.tw ter.graphjet.algor hms.count ng.t et.TopSecondDegreeByCountRequestForT et
 mport com.tw ter.graphjet.b part e.Node tadataLeft ndexedMult Seg ntB part eGraph
 mport com.tw ter.logg ng.Logger
 mport com.tw ter.recos.graph_common.F nagleStatsRece verWrapper
 mport com.tw ter.recos.model.SalsaQueryRunner.SalsaRunnerConf g
 mport com.tw ter.recos.recos_common.thr ftscala.Soc alProofType
 mport com.tw ter.recos.user_t et_ent y_graph.thr ftscala.Recom ndT etEnt yRequest
 mport com.tw ter.recos.user_t et_ent y_graph.thr ftscala.T etEnt yD splayLocat on
 mport com.tw ter.recos.user_t et_ent y_graph.thr ftscala.T etType
 mport com.tw ter.recos.ut l.Stats.trackBlockStats
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.JavaT  r
 mport com.tw ter.ut l.Try
 mport  .un m .ds .fastut l.longs.Long2DoubleOpenHashMap
 mport  .un m .ds .fastut l.longs.LongOpenHashSet
 mport scala.collect on.JavaConverters._

 mport com.tw ter.graphjet.algor hms.Recom ndat onType
 mport com.tw ter.recos.user_t et_ent y_graph.thr ftscala.{
  Recom ndat onType => Thr ftRecom ndat onType
}
 mport scala.collect on.Map
 mport scala.collect on.Set

object T etRecom ndat onsRunner {
  pr vate val DefaultT etTypes: Seq[T etType] =
    Seq(T etType.Regular, T etType.Summary, T etType.Photo, T etType.Player)
  pr vate val DefaultF1ExactSoc alProofS ze = 1
  pr vate val DefaultRareT etRecencyM ll s: Long = 7.days. nM ll s

  /**
   * Map val d soc al proof types spec f ed by cl ents to an array of bytes.  f cl ents do not
   * spec fy any soc al proof type un ons  n thr ft,   w ll return an empty set by default.
   */
  pr vate def getSoc alProofTypeUn ons(
    soc alProofTypeUn ons: Opt on[Set[Seq[Soc alProofType]]]
  ): Set[Array[Byte]] = {
    soc alProofTypeUn ons
      .map {
        _.map {
          _.map {
            _.getValue.toByte
          }.toArray
        }
      }
      .getOrElse(Set.empty)
  }

  pr vate def getRecom ndat onTypes(
    recom ndat onTypes: Seq[Thr ftRecom ndat onType]
  ): Set[Recom ndat onType] = {
    recom ndat onTypes.flatMap {
      _ match {
        case Thr ftRecom ndat onType.T et => So (Recom ndat onType.TWEET)
        case Thr ftRecom ndat onType.Hashtag => So (Recom ndat onType.HASHTAG)
        case Thr ftRecom ndat onType.Url => So (Recom ndat onType.URL)
        case _ =>
          throw new Except on("Unmatc d Recom ndat on Type  n getRecom ndat onTypes")
      }
    }.toSet
  }

  pr vate def convertThr ftEnumsToJavaEnums(
    maxResults: Opt on[Map[Thr ftRecom ndat onType,  nt]]
  ): Map[Recom ndat onType,  nteger] = {
    maxResults
      .map {
        _.flatMap {
          _ match {
            case (Thr ftRecom ndat onType.T et, v) => So ((Recom ndat onType.TWEET, v:  nteger))
            case (Thr ftRecom ndat onType.Hashtag, v) =>
              So ((Recom ndat onType.HASHTAG, v:  nteger))
            case (Thr ftRecom ndat onType.Url, v) => So ((Recom ndat onType.URL, v:  nteger))
            case _ =>
              throw new Except on("Unmatc d Recom ndat on Type  n convertThr ftEnumsToJavaEnums")
          }
        }
      }
      .getOrElse(Map.empty)
  }

}

/**
 * T  Mag cRecsRunner creates a queue of reader threads, Mag cRecs, and each one reads from t 
 * graph and computes recom ndat ons.
 */
class T etRecom ndat onsRunner(
  b part eGraph: Node tadataLeft ndexedMult Seg ntB part eGraph,
  salsaRunnerConf g: SalsaRunnerConf g,
  statsRece verWrapper: F nagleStatsRece verWrapper) {

   mport T etRecom ndat onsRunner._

  pr vate val log: Logger = Logger()

  pr vate val stats = statsRece verWrapper.statsRece ver.scope(t .getClass.getS mpleNa )
  pr vate val mag cRecsFa lureCounter = stats.counter("fa lure")
  pr vate val pollCounter = stats.counter("poll")
  pr vate val pollT  outCounter = stats.counter("pollT  out")
  pr vate val offerCounter = stats.counter("offer")
  pr vate val pollLatencyStat = stats.stat("pollLatency")

  pr vate val mag cRecsQueue = new AsyncQueue[TopSecondDegreeByCountForT et]
  (0 unt l salsaRunnerConf g.numSalsaRunners).foreach { _ =>
    mag cRecsQueue.offer(
      new TopSecondDegreeByCountForT et(
        b part eGraph,
        salsaRunnerConf g.expectedNodesToH  nSalsa,
        statsRece verWrapper.scope(t .getClass.getS mpleNa )
      )
    )
  }

  pr vate  mpl c  val t  r: JavaT  r = new JavaT  r(true)

  pr vate def getBaseF lters(
    staleT etDurat on: Long,
    t etTypes: Seq[T etType]
  ) = {
    L st(
      // Keep RecentT etF lter f rst s nce  's t  c apest
      new RecentT etF lter(staleT etDurat on, statsRece verWrapper),
      new T etCardF lter(
        t etTypes.conta ns(T etType.Regular),
        t etTypes.conta ns(T etType.Summary),
        t etTypes.conta ns(T etType.Photo),
        t etTypes.conta ns(T etType.Player),
        false, // no promoted t ets
        statsRece verWrapper
      ),
      new D rect nteract onsF lter(b part eGraph, statsRece verWrapper),
      new RequestedSetF lter(statsRece verWrapper),
      new Soc alProofTypesF lter(statsRece verWrapper)
    )
  }

  /**
   *  lper  thod to  nterpret t  output of Mag cRecs graph
   *
   * @param mag cRecsResponse  s t  response from runn ng Mag cRecs
   * @return a sequence of cand date  ds, w h score and l st of soc al proofs
   */
  pr vate def transformMag cRecsResponse(
    mag cRecsResponse: Opt on[TopSecondDegreeByCountResponse]
  ): Seq[Recom ndat on nfo] = {
    val responses = mag cRecsResponse match {
      case So (response) => response.getRankedRecom ndat ons.asScala.toSeq
      case _ => N l
    }
    responses
  }

  /**
   *  lper funct on to determ ne d fferent post-process f lter ng log c  n GraphJet,
   * based on d splay locat ons
   */
  pr vate def getF ltersByD splayLocat ons(
    d splayLocat on: T etEnt yD splayLocat on,
    wh el stAuthors: LongOpenHashSet,
    blackl stAuthors: LongOpenHashSet,
    val dSoc alProofs: Array[Byte]
  ) = {
    d splayLocat on match {
      case T etEnt yD splayLocat on.Mag cRecsF1 =>
        Seq(
          new ANDF lters(
            L st[ResultF lter](
              new T etAuthorF lter(
                b part eGraph,
                wh el stAuthors,
                new LongOpenHashSet(),
                statsRece verWrapper),
              new ExactUserSoc alProofS zeF lter(
                DefaultF1ExactSoc alProofS ze,
                val dSoc alProofs,
                statsRece verWrapper
              )
            ).asJava,
            statsRece verWrapper
          ),
          // Blackl st f lter must be appl ed separately from F1's AND f lter cha n
          new T etAuthorF lter(
            b part eGraph,
            new LongOpenHashSet(),
            blackl stAuthors,
            statsRece verWrapper)
        )
      case T etEnt yD splayLocat on.Mag cRecsRareT et =>
        Seq(
          new T etAuthorF lter(
            b part eGraph,
            wh el stAuthors,
            blackl stAuthors,
            statsRece verWrapper),
          new RecentEdge tadataF lter(
            DefaultRareT etRecencyM ll s,
            UserT etEdgeTypeMask.T et. d.toByte,
            statsRece verWrapper
          )
        )
      case _ =>
        Seq(
          new T etAuthorF lter(
            b part eGraph,
            wh el stAuthors,
            blackl stAuthors,
            statsRece verWrapper))
    }
  }

  /**
   *  lper  thod to run salsa computat on and convert t  results to Opt on
   *
   * @param mag cRecs  s mag cRecs reader on b part e graph
   * @param mag cRecsRequest  s t  mag cRecs request
   * @return  s an opt on of Mag cRecsResponse
   */
  pr vate def getMag cRecsResponse(
    mag cRecs: TopSecondDegreeByCountForT et,
    mag cRecsRequest: TopSecondDegreeByCountRequestForT et
  )(
     mpl c  statsRece ver: StatsRece ver
  ): Opt on[TopSecondDegreeByCountResponse] = {
    trackBlockStats(stats) {
      val random = new Random()
      // compute recs -- need to catch and pr nt except ons  re ot rw se t y are swallo d
      val mag cRecsAttempt =
        Try(mag cRecs.computeRecom ndat ons(mag cRecsRequest, random)).onFa lure { e =>
          mag cRecsFa lureCounter. ncr()
          log.error(e, "Mag cRecs computat on fa led")
        }
      mag cRecsAttempt.toOpt on
    }
  }

  pr vate def getMag cRecsRequest(
    request: Recom ndT etEnt yRequest
  ): TopSecondDegreeByCountRequestForT et = {
    val requester d = request.requester d
    val leftSeedNodes = new Long2DoubleOpenHashMap(
      request.seedsW h  ghts.keys.toArray,
      request.seedsW h  ghts.values.toArray
    )
    val t etsToExcludeArray = new LongOpenHashSet(request.excludedT et ds.getOrElse(N l).toArray)
    val staleT etDurat on = request.maxT etAge nM ll s.getOrElse(RecosConf g.maxT etAge nM ll s)
    val staleEngage ntDurat on =
      request.maxEngage ntAge nM ll s.getOrElse(RecosConf g.maxEngage ntAge nM ll s)
    val t etTypes = request.t etTypes.getOrElse(DefaultT etTypes)
    val t etAuthors = new LongOpenHashSet(request.t etAuthors.getOrElse(N l).toArray)
    val excludedT etAuthors = new LongOpenHashSet(
      request.excludedT etAuthors.getOrElse(N l).toArray)
    val val dSoc alProofs =
      UserT etEdgeTypeMask.getUserT etGraphSoc alProofTypes(request.soc alProofTypes)

    val resultF lterCha n = new ResultF lterCha n(
      (
        getBaseF lters(staleT etDurat on, t etTypes) ++
          getF ltersByD splayLocat ons(
            d splayLocat on = request.d splayLocat on,
            wh el stAuthors = t etAuthors,
            blackl stAuthors = excludedT etAuthors,
            val dSoc alProofs = val dSoc alProofs
          )
      ).asJava
    )

    new TopSecondDegreeByCountRequestForT et(
      requester d,
      leftSeedNodes,
      t etsToExcludeArray,
      getRecom ndat onTypes(request.recom ndat onTypes).asJava,
      convertThr ftEnumsToJavaEnums(request.maxResultsByType).asJava,
      UserT etEdgeTypeMask.S ZE,
      request.maxUserSoc alProofS ze.getOrElse(RecosConf g.maxUserSoc alProofS ze),
      request.maxT etSoc alProofS ze.getOrElse(RecosConf g.maxT etSoc alProofS ze),
      convertThr ftEnumsToJavaEnums(request.m nUserSoc alProofS zes).asJava,
      val dSoc alProofs,
      staleT etDurat on,
      staleEngage ntDurat on,
      resultF lterCha n,
      getSoc alProofTypeUn ons(request.soc alProofTypeUn ons).asJava
    )
  }

  def apply(request: Recom ndT etEnt yRequest): Future[Seq[Recom ndat on nfo]] = {
    pollCounter. ncr()
    val t0 = System.currentT  M ll s
    mag cRecsQueue.poll().map { mag cRecs =>
      val pollT   = System.currentT  M ll s - t0
      pollLatencyStat.add(pollT  )
      val mag cRecsResponse = Try {
         f (pollT   < salsaRunnerConf g.t  outSalsaRunner) {
          val mag cRecsRequest = getMag cRecsRequest(request)
          transformMag cRecsResponse(
            getMag cRecsResponse(mag cRecs, mag cRecsRequest)(statsRece verWrapper.statsRece ver)
          )
        } else {
          //  f   d d not get a mag cRecs  n t  , t n fa l fast  re and  m d ately put   back
          log.warn ng("mag cRecsQueue poll ng t  out")
          pollT  outCounter. ncr()
          throw new Runt  Except on("mag cRecs poll t  out")
          N l
        }
      } ensure {
        mag cRecsQueue.offer(mag cRecs)
        offerCounter. ncr()
      }
      mag cRecsResponse.toOpt on getOrElse N l
    }
  }
}
