package src.scala.com.tw ter.recos.hose.common

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f natra.kafka.consu rs.F nagleKafkaConsu rBu lder
 mport com.tw ter.graphjet.b part e.Left ndexedMult Seg ntB part eGraph
 mport com.tw ter.graphjet.b part e.seg nt.Left ndexedB part eGraphSeg nt
 mport com.tw ter.kafka.cl ent.processor.AtLeastOnceProcessor
 mport com.tw ter.kafka.cl ent.processor.ThreadSafeKafkaConsu rCl ent
 mport com.tw ter.logg ng.Logger
 mport com.tw ter.recos.hose.common.BufferedEdgeCollector
 mport com.tw ter.recos.hose.common.BufferedEdgeWr er
 mport com.tw ter.recos.hose.common.EdgeCollector
 mport com.tw ter.recos.hose.common.RecosEdgeProcessor
 mport com.tw ter.recos. nternal.thr ftscala.RecosHose ssage
 mport com.tw ter.recos.ut l.Act on
 mport java.ut l.concurrent.atom c.Atom cBoolean
 mport java.ut l.concurrent.ConcurrentL nkedQueue
 mport java.ut l.concurrent.ExecutorServ ce
 mport java.ut l.concurrent.Executors
 mport java.ut l.concurrent.Semaphore

/**
 * T  class  s an var at on of Un f edGraphWr er wh ch allow one  nstance to hold mult ple graphs
 */
tra  Un f edGraphWr erMult [
  TSeg nt <: Left ndexedB part eGraphSeg nt,
  TGraph <: Left ndexedMult Seg ntB part eGraph[TSeg nt]] { wr er =>

   mport Un f edGraphWr erMult ._

  def shard d: Str ng
  def env: Str ng
  def hosena : Str ng
  def bufferS ze:  nt
  def consu rNum:  nt
  def catchupWr erNum:  nt
  def kafkaConsu rBu lder: F nagleKafkaConsu rBu lder[Str ng, RecosHose ssage]
  def cl ent d: Str ng
  def statsRece ver: StatsRece ver

  /**
   * Adds a RecosHose ssage to t  graph. used by l ve wr er to  nsert edges to t 
   * current seg nt
   */
  def addEdgeToGraph(
    graphs: Seq[(TGraph, Set[Act on.Value])],
    recosHose ssage: RecosHose ssage
  ): Un 

  /**
   * Adds a RecosHose ssage to t  g ven seg nt  n t  graph. Used by catch up wr ers to
   *  nsert edges to non-current (old) seg nts
   */
  def addEdgeToSeg nt(
    seg nt: Seq[(TSeg nt, Set[Act on.Value])],
    recosHose ssage: RecosHose ssage
  ): Un 

  pr vate val log = Logger()
  pr vate val  sRunn ng: Atom cBoolean = new Atom cBoolean(true)
  pr vate val  n  al zed: Atom cBoolean = new Atom cBoolean(false)
  pr vate var processors: Seq[AtLeastOnceProcessor[Str ng, RecosHose ssage]] = Seq.empty
  pr vate var consu rs: Seq[ThreadSafeKafkaConsu rCl ent[Str ng, RecosHose ssage]] = Seq.empty
  pr vate val threadPool: ExecutorServ ce = Executors.newCac dThreadPool()

  def shutdown(): Un  = {
    processors.foreach { processor =>
      processor.close()
    }
    processors = Seq.empty
    consu rs.foreach { consu r =>
      consu r.close()
    }
    consu rs = Seq.empty
    threadPool.shutdown()
     sRunn ng.set(false)
  }

  def  n Hose(l veGraphs: Seq[(TGraph, Set[Act on.Value])]): Un  = t .synchron zed {
     f (! n  al zed.get) {
       n  al zed.set(true)

      val queue: java.ut l.Queue[Array[RecosHose ssage]] =
        new ConcurrentL nkedQueue[Array[RecosHose ssage]]()
      val queuel m : Semaphore = new Semaphore(1024)

       n RecosHoseKafka(queue, queuel m )
       n GrpahWr ers(l veGraphs, queue, queuel m )
    } else {
      throw new Runt  Except on("attempt to re- n  kafka hose")
    }
  }

  pr vate def  n RecosHoseKafka(
    queue: java.ut l.Queue[Array[RecosHose ssage]],
    queuel m : Semaphore,
  ): Un  = {
    try {
      consu rs = (0 unt l consu rNum).map {  ndex =>
        new ThreadSafeKafkaConsu rCl ent(
          kafkaConsu rBu lder.cl ent d(s"cl ent d-$ ndex").enableAutoComm (false).conf g)
      }
      processors = consu rs.z pW h ndex.map {
        case (consu r,  ndex) =>
          val bufferedWr er = BufferedEdgeCollector(bufferS ze, queue, queuel m , statsRece ver)
          val processor = RecosEdgeProcessor(bufferedWr er)(statsRece ver)

          AtLeastOnceProcessor[Str ng, RecosHose ssage](
            s"recos- njector-kafka-$ ndex",
            hosena ,
            consu r,
            processor.process,
            maxPend ngRequests = MaxPend ngRequests * bufferS ze,
            workerThreads = ProcessorThreads,
            comm  ntervalMs = Comm  ntervalMs,
            statsRece ver = statsRece ver
          )
      }

      log. nfo(s"start ng ${processors.s ze} recosKafka processors")
      processors.foreach { processor =>
        processor.start()
      }
    } catch {
      case e: Throwable =>
        e.pr ntStackTrace()
        log.error(e, e.toStr ng)
        processors.foreach { processor =>
          processor.close()
        }
        processors = Seq.empty
        consu rs.foreach { consu r =>
          consu r.close()
        }
        consu rs = Seq.empty
    }
  }

  /**
   *  n  al ze t  graph wr ers,
   * by f rst creat ng catch up wr ers to bootstrap t  older seg nts,
   * and t n ass gn ng a l ve wr er to populate t  l ve seg nt.
   */
  pr vate def  n GrpahWr ers(
    l veGraphs: Seq[(TGraph, Set[Act on.Value])],
    queue: java.ut l.Queue[Array[RecosHose ssage]],
    queuel m : Semaphore
  ): Un  = {
    // def ne a number of (numBootstrapWr ers - 1) catchup wr er threads, each of wh ch w ll wr e
    // to a separate graph seg nt.
    val catchupWr ers = (0 unt l (catchupWr erNum - 1)).map {  ndex =>
      val seg nts = l veGraphs.map { case (graph, act ons) => (graph.getL veSeg nt, act ons) }
      for (l veGraph <- l veGraphs) {
        l veGraph._1.rollForwardSeg nt()
      }
      getCatchupWr er(seg nts, queue, queuel m ,  ndex)
    }
    val threadPool: ExecutorServ ce = Executors.newCac dThreadPool()

    log. nfo("start ng l ve graph wr er that runs unt l serv ce shutdown")

    // def ne one l ve wr er thread
    val l veWr er = getL veWr er(l veGraphs, queue, queuel m )
    threadPool.subm (l veWr er)

    log. nfo(
      "start ng catchup graph wr er, wh ch w ll term nate as soon as t  catchup seg nt  s full"
    )
    catchupWr ers.map(threadPool.subm (_))
  }

  pr vate def getL veWr er(
    l veGraphs: Seq[(TGraph, Set[Act on.Value])],
    queue: java.ut l.Queue[Array[RecosHose ssage]],
    queuel m : Semaphore,
  ): BufferedEdgeWr er = {
    val l veEdgeCollector = new EdgeCollector {
      overr de def addEdge( ssage: RecosHose ssage): Un  =
        addEdgeToGraph(l veGraphs,  ssage)
    }
    BufferedEdgeWr er(
      queue,
      queuel m ,
      l veEdgeCollector,
      statsRece ver.scope("l veWr er"),
       sRunn ng.get
    )
  }

  pr vate def getCatchupWr er(
    seg nts: Seq[(TSeg nt, Set[Act on.Value])],
    queue: java.ut l.Queue[Array[RecosHose ssage]],
    queuel m : Semaphore,
    catchupWr er ndex:  nt,
  ): BufferedEdgeWr er = {
    val catchupEdgeCollector = new EdgeCollector {
      var currentNumEdges = 0

      overr de def addEdge( ssage: RecosHose ssage): Un  = {
        currentNumEdges += 1
        addEdgeToSeg nt(seg nts,  ssage)
      }
    }
    val maxEdges = seg nts.map(_._1.getMaxNumEdges).sum

    def runCond  on(): Boolean = {
       sRunn ng.get && ((maxEdges - catchupEdgeCollector.currentNumEdges) > bufferS ze)
    }

    BufferedEdgeWr er(
      queue,
      queuel m ,
      catchupEdgeCollector,
      statsRece ver.scope("catc r_" + catchupWr er ndex),
      runCond  on
    )
  }
}

pr vate object Un f edGraphWr erMult  {

  // T  RecosEdgeProcessor  s not thread-safe. Only use one thread to process each  nstance.
  val ProcessorThreads = 1
  // Each one cac  at most 1000 * bufferS ze requests.
  val MaxPend ngRequests = 1000
  // Short Comm  MS to reduce dupl cate  ssages.
  val Comm  ntervalMs: Long = 5000 // 5 seconds, Default Kafka value.
}
