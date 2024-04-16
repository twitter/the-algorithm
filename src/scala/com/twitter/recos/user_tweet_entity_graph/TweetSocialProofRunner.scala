package com.tw ter.recos.user_t et_ent y_graph

 mport java.ut l.Random
 mport com.tw ter.concurrent.AsyncQueue
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.graphjet.b part e.Node tadataLeft ndexedMult Seg ntB part eGraph
 mport com.tw ter.graphjet.algor hms.Recom ndat on nfo
 mport com.tw ter.graphjet.algor hms.soc alproof.{
  Soc alProofResult,
  T etSoc alProofGenerator,
  Soc alProofRequest => Soc alProofJavaRequest,
  Soc alProofResponse => Soc alProofJavaResponse
}
 mport com.tw ter.logg ng.Logger
 mport com.tw ter.recos.model.SalsaQueryRunner.SalsaRunnerConf g
 mport com.tw ter.recos.user_t et_ent y_graph.thr ftscala.{
  Recom ndat onType,
  Recom ndat onSoc alProofRequest => Recom ndat onSoc alProofThr ftRequest,
  Soc alProofRequest => Soc alProofThr ftRequest
}
 mport com.tw ter.ut l.{Future, Try}
 mport  .un m .ds .fastut l.longs.{Long2DoubleMap, Long2DoubleOpenHashMap, LongArraySet}
 mport scala.collect on.JavaConverters._

/**
 * T etSoc alProofRunner creates a queue of reader threads, T etSoc alProofGenerator, and each one
 * reads from t  graph and computes soc al proofs.
 */
class T etSoc alProofRunner(
  b part eGraph: Node tadataLeft ndexedMult Seg ntB part eGraph,
  salsaRunnerConf g: SalsaRunnerConf g,
  statsRece ver: StatsRece ver) {
  pr vate val log: Logger = Logger()
  pr vate val stats = statsRece ver.scope(t .getClass.getS mpleNa )
  pr vate val soc alProofS zeStat = stats.stat("soc alProofS ze")

  pr vate val soc alProofFa lureCounter = stats.counter("fa lure")
  pr vate val pollCounter = stats.counter("poll")
  pr vate val pollT  outCounter = stats.counter("pollT  out")
  pr vate val offerCounter = stats.counter("offer")
  pr vate val pollLatencyStat = stats.stat("pollLatency")
  pr vate val soc alProofRunnerPool =  n Soc alProofRunnerPool()

  pr vate def  n Soc alProofRunnerPool(): AsyncQueue[T etSoc alProofGenerator] = {
    val soc alProofQueue = new AsyncQueue[T etSoc alProofGenerator]
    (0 unt l salsaRunnerConf g.numSalsaRunners).foreach { _ =>
      soc alProofQueue.offer(new T etSoc alProofGenerator(b part eGraph))
    }
    soc alProofQueue
  }

  /**
   *  lper  thod to  nterpret t  output of Soc alProofJavaResponse
   *
   * @param soc alProofResponse  s t  response from runn ng T etSoc alProof
   * @return a sequence of Soc alProofResult
   */
  pr vate def transformSoc alProofResponse(
    soc alProofResponse: Opt on[Soc alProofJavaResponse]
  ): Seq[Recom ndat on nfo] = {
    soc alProofResponse match {
      case So (response) =>
        val scalaResponse = response.getRankedRecom ndat ons.asScala
        scalaResponse.foreach { result =>
          soc alProofS zeStat.add(result.as nstanceOf[Soc alProofResult].getSoc alProofS ze)
        }
        scalaResponse.toSeq
      case _ => N l
    }
  }

  /**
   *  lper  thod to run soc al proof computat on and convert t  results to Opt on
   *
   * @param soc alProof  s soc alProof reader on b part e graph
   * @param request  s t  soc alProof request
   * @return  s an opt on of Soc alProofJavaResponse
   */
  pr vate def getSoc alProofResponse(
    soc alProof: T etSoc alProofGenerator,
    request: Soc alProofJavaRequest,
    random: Random
  )(
     mpl c  statsRece ver: StatsRece ver
  ): Opt on[Soc alProofJavaResponse] = {
    val attempt = Try(soc alProof.computeRecom ndat ons(request, random)).onFa lure { e =>
      soc alProofFa lureCounter. ncr()
      log.error(e, "Soc alProof computat on fa led")
    }
    attempt.toOpt on
  }

  /**
   * Attempt to retr eve a T etSoc alProof thread from t  runner pool
   * to execute a soc alProofRequest
   */
  pr vate def handleSoc alProofRequest(soc alProofRequest: Soc alProofJavaRequest) = {
    pollCounter. ncr()
    val t0 = System.currentT  M ll s()
    soc alProofRunnerPool.poll().map { t etSoc alProof =>
      val pollT   = System.currentT  M ll s - t0
      pollLatencyStat.add(pollT  )
      val soc alProofResponse = Try {
         f (pollT   < salsaRunnerConf g.t  outSalsaRunner) {
          val response = getSoc alProofResponse(t etSoc alProof, soc alProofRequest, new Random())(
            statsRece ver
          )
          transformSoc alProofResponse(response)
        } else {
          //  f   d d not get a soc al proof  n t  , t n fa l fast  re and  m d ately put   back
          log.warn ng("soc alProof poll ng t  out")
          pollT  outCounter. ncr()
          throw new Runt  Except on("soc alProof poll t  out")
          N l
        }
      } ensure {
        soc alProofRunnerPool.offer(t etSoc alProof)
        offerCounter. ncr()
      }
      soc alProofResponse.toOpt on getOrElse N l
    }
  }

  /**
   * T  apply() supports requests com ng from t  old t et soc al proof endpo nt.
   * Currently t  supports cl ents such as Ema l Recom ndat ons, Mag cRecs, and Ho T  l ne.
   *  n order to avo d  avy m grat on work,   are reta n ng t  endpo nt.
   */
  def apply(request: Soc alProofThr ftRequest): Future[Seq[Recom ndat on nfo]] = {
    val t etSet = new LongArraySet(request. nputT ets.toArray)
    val leftSeedNodes: Long2DoubleMap = new Long2DoubleOpenHashMap(
      request.seedsW h  ghts.keys.toArray,
      request.seedsW h  ghts.values.toArray
    )

    val soc alProofRequest = new Soc alProofJavaRequest(
      t etSet,
      leftSeedNodes,
      UserT etEdgeTypeMask.getUserT etGraphSoc alProofTypes(request.soc alProofTypes)
    )

    handleSoc alProofRequest(soc alProofRequest)
  }

  /**
   * T  apply() supports requests com ng from t  new soc al proof endpo nt  n UTEG that works for
   * t et soc al proof generat on, as  ll as hashtag and url soc al proof generat on.
   * Currently t  endpo nt supports url soc al proof generat on for Gu de.
   */
  def apply(request: Recom ndat onSoc alProofThr ftRequest): Future[Seq[Recom ndat on nfo]] = {
    val t et ds = request.recom ndat on dsForSoc alProof.collect {
      case (Recom ndat onType.T et,  ds) =>  ds
    }.flatten
    val t etSet = new LongArraySet(t et ds.toArray)
    val leftSeedNodes: Long2DoubleMap = new Long2DoubleOpenHashMap(
      request.seedsW h  ghts.keys.toArray,
      request.seedsW h  ghts.values.toArray
    )

    val soc alProofRequest = new Soc alProofJavaRequest(
      t etSet,
      leftSeedNodes,
      UserT etEdgeTypeMask.getUserT etGraphSoc alProofTypes(request.soc alProofTypes)
    )

    handleSoc alProofRequest(soc alProofRequest)
  }
}
