package com.tw ter.recos.user_user_graph

 mport java.ut l.Random
 mport com.google.common.collect.L sts
 mport com.tw ter.concurrent.AsyncQueue
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.graphjet.algor hms.count ng.TopSecondDegreeByCountResponse
 mport com.tw ter.graphjet.algor hms.count ng.user.TopSecondDegreeByCountForUser
 mport com.tw ter.graphjet.algor hms.count ng.user.TopSecondDegreeByCountRequestForUser
 mport com.tw ter.graphjet.algor hms.count ng.user.UserRecom ndat on nfo
 mport com.tw ter.graphjet.algor hms.Connect ngUsersW h tadata
 mport com.tw ter.graphjet.algor hms.f lters._
 mport com.tw ter.graphjet.b part e.Node tadataLeft ndexedPo rLawMult Seg ntB part eGraph
 mport com.tw ter.logg ng.Logger
 mport com.tw ter.recos.dec der.UserUserGraphDec der
 mport com.tw ter.recos.graph_common.F nagleStatsRece verWrapper
 mport com.tw ter.recos.model.SalsaQueryRunner.SalsaRunnerConf g
 mport com.tw ter.recos.recos_common.thr ftscala.UserSoc alProofType
 mport com.tw ter.recos.user_user_graph.thr ftscala._
 mport com.tw ter.recos.ut l.Stats._
 mport com.tw ter.servo.request.RequestHandler
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.Try
 mport  .un m .ds .fastut l.longs.Long2DoubleOpenHashMap
 mport  .un m .ds .fastut l.longs.LongOpenHashSet
 mport scala.collect on.JavaConverters._

tra  Recom ndUsersHandler extends RequestHandler[Recom ndUserRequest, Recom ndUserResponse]

/**
 * Computes user recom ndat ons based on a Recom ndUserRequest by us ng
 * TopSecondDegree algor hm  n GraphJet.
 */
case class Recom ndUsersHandler mpl(
  b part eGraph: Node tadataLeft ndexedPo rLawMult Seg ntB part eGraph,
  salsaRunnerConf g: SalsaRunnerConf g,
  dec der: UserUserGraphDec der,
  statsRece verWrapper: F nagleStatsRece verWrapper)
    extends Recom ndUsersHandler {

  pr vate val log: Logger = Logger(t .getClass.getS mpleNa )
  pr vate val stats = statsRece verWrapper.statsRece ver.scope(t .getClass.getS mpleNa )
  pr vate val fa lureCounter = stats.counter("fa lure")
  pr vate val recsStat = stats.stat("recs_count")
  pr vate val emptyCounter = stats.counter("empty")
  pr vate val pollCounter = stats.counter("poll")
  pr vate val pollT  outCounter = stats.counter("pollT  out")
  pr vate val offerCounter = stats.counter("offer")
  pr vate val pollLatencyStat = stats.stat("pollLatency")
  pr vate val graphJetQueue = new AsyncQueue[TopSecondDegreeByCountForUser]
  (0 unt l salsaRunnerConf g.numSalsaRunners).foreach { _ =>
    graphJetQueue.offer(
      new TopSecondDegreeByCountForUser(
        b part eGraph,
        salsaRunnerConf g.expectedNodesToH  nSalsa,
        statsRece verWrapper.scope(t .getClass.getS mpleNa )
      )
    )
  }

  /**
   * G ven a user_user_graph request, make   conform to GraphJet's request format
   */
  pr vate def convertRequestToJava(
    request: Recom ndUserRequest
  ): TopSecondDegreeByCountRequestForUser = {
    val queryNode = request.requester d
    val leftSeedNodesW h  ght = new Long2DoubleOpenHashMap(
      request.seedsW h  ghts.keys.toArray,
      request.seedsW h  ghts.values.toArray
    )
    val toBeF ltered = new LongOpenHashSet(request.excludedUser ds.getOrElse(N l).toArray)
    val maxNumResults = request.maxNumResults.getOrElse(DefaultRequestParams.MaxNumResults)
    val maxNumSoc alProofs =
      request.maxNumSoc alProofs.getOrElse(DefaultRequestParams.MaxNumSoc alProofs)
    val m nUserPerSoc alProof = convertM nUserPerSoc alProofToJava(request.m nUserPerSoc alProof)
    val soc alProofTypes =
      UserEdgeTypeMask.getUserUserGraphSoc alProofTypes(request.soc alProofTypes)
    val maxR ghtNodeAge nM ll s = DefaultRequestParams.MaxR ghtNodeAgeThreshold
    val maxEdgeEngage ntAge nM ll s =
      request.maxEdgeEngage ntAge nM ll s.getOrElse(DefaultRequestParams.MaxEdgeAgeThreshold)
    val resultF lterCha n = new ResultF lterCha n(
      L sts.newArrayL st(
        new Soc alProofTypesF lter(statsRece verWrapper),
        new RequestedSetF lter(statsRece verWrapper)
      )
    )

    new TopSecondDegreeByCountRequestForUser(
      queryNode,
      leftSeedNodesW h  ght,
      toBeF ltered,
      maxNumResults,
      maxNumSoc alProofs,
      UserEdgeTypeMask.S ZE.to nt,
      m nUserPerSoc alProof,
      soc alProofTypes,
      maxR ghtNodeAge nM ll s,
      maxEdgeEngage ntAge nM ll s,
      resultF lterCha n
    )
  }

  /**
   * Converts t  thr ft scala type to t  Java equ valent
   */
  pr vate def convertM nUserPerSoc alProofToJava(
    soc alProof nScala: Opt on[scala.collect on.Map[UserSoc alProofType,  nt]]
  ): java.ut l.Map[java.lang.Byte, java.lang. nteger] = {
    soc alProof nScala
      .map {
        _.map {
          case (key: UserSoc alProofType, value:  nt) =>
            (new java.lang.Byte(key.getValue.toByte), new java.lang. nteger(value))
        }
      }
      .getOrElse(Map.empty[java.lang.Byte, java.lang. nteger])
      .asJava
  }

  /**
   * Converts a byte-array format of soc al proofs  n Java to  s Scala equ valent
   */
  pr vate def convertSoc alProofsToScala(
    soc alProofs: java.ut l.Map[java.lang.Byte, Connect ngUsersW h tadata]
  ): scala.collect on.mutable.Map[UserSoc alProofType, scala.Seq[Long]] = {
    soc alProofs.asScala.map {
      case (soc alProofByte, soc alProof) =>
        val proofType = UserSoc alProofType(soc alProofByte.toByte)
        val  ds = soc alProof.getConnect ngUsers.asScala.map(_.toLong)
        (proofType,  ds)
    }
  }

  /**
   * Converts Java recom ndat on results to  s Scala equ valent
   */
  pr vate def convertResponseToScala(
    responseOpt: Opt on[TopSecondDegreeByCountResponse]
  ): Recom ndUserResponse = {
    responseOpt match {
      case So (rawResponse) =>
        val userSeq = rawResponse.getRankedRecom ndat ons.asScala.toSeq.flatMap {
          case userRecs: UserRecom ndat on nfo =>
            So (
              Recom ndedUser(
                userRecs.getRecom ndat on,
                userRecs.get  ght,
                convertSoc alProofsToScala(userRecs.getSoc alProof)
              )
            )
          case _ =>
            None
        }
        recsStat.add(userSeq.s ze)
         f (userSeq. sEmpty) {
          emptyCounter. ncr()
        }
        Recom ndUserResponse(userSeq)
      case None =>
        emptyCounter. ncr()
        Recom ndUserResponse(N l)
    }
  }

  pr vate def getGraphJetResponse(
    graphJet: TopSecondDegreeByCountForUser,
    request: TopSecondDegreeByCountRequestForUser,
    random: Random
  )(
     mpl c  statsRece ver: StatsRece ver
  ): Opt on[TopSecondDegreeByCountResponse] = {
    trackBlockStats(stats) {
      // compute recs -- need to catch and pr nt except ons  re ot rw se t y are swallo d
      val recAttempt = Try(graphJet.computeRecom ndat ons(request, random)).onFa lure { e =>
        fa lureCounter. ncr()
        log.error(e, "GraphJet computat on fa led")
      }
      recAttempt.toOpt on
    }
  }

  overr de def apply(request: Recom ndUserRequest): Future[Recom ndUserResponse] = {
    val random = new Random()
    val graphJetRequest = convertRequestToJava(request)
    pollCounter. ncr()
    val t0 = System.currentT  M ll s
    graphJetQueue.poll().map { graphJetRunner =>
      val pollT   = System.currentT  M ll s - t0
      pollLatencyStat.add(pollT  )
      val response = Try {
         f (pollT   < salsaRunnerConf g.t  outSalsaRunner) {
          convertResponseToScala(
            getGraphJetResponse(
              graphJetRunner,
              graphJetRequest,
              random
            )(statsRece verWrapper.statsRece ver)
          )
        } else {
          //  f   d d not get a runner  n t  , t n fa l fast  re and  m d ately put   back
          log.warn ng("GraphJet Queue poll ng t  out")
          pollT  outCounter. ncr()
          throw new Runt  Except on("GraphJet poll t  out")
          Recom ndUserResponse(N l)
        }
      } ensure {
        graphJetQueue.offer(graphJetRunner)
        offerCounter. ncr()
      }
      response.toOpt on.getOrElse(Recom ndUserResponse(N l))
    }
  }

  object DefaultRequestParams {
    val MaxNumResults = 100
    val MaxNumSoc alProofs = 100
    val MaxR ghtNodeAgeThreshold: Long = Long.MaxValue
    val MaxEdgeAgeThreshold: Long = Long.MaxValue
  }
}
