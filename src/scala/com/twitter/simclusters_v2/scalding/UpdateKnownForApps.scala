package com.tw ter.s mclusters_v2.scald ng

 mport com.tw ter.dal.cl ent.dataset.KeyValDALDataset
 mport com.tw ter. rm .cand date.thr ftscala.Cand dates
 mport com.tw ter.pluck.s ce.cassowary.Follow ngsCos neS m lar  esManhattanS ce
 mport com.tw ter.pluck.s ce.cassowary.S msCand datesS ce
 mport com.tw ter.scald ng._
 mport com.tw ter.scald ng_ nternal.dalv2.DAL
 mport com.tw ter.scald ng_ nternal.dalv2.DALWr e._
 mport com.tw ter.scald ng_ nternal.job.Tw terExecut onApp
 mport com.tw ter.scald ng_ nternal.job.analyt cs_batch.Analyt csBatchExecut on
 mport com.tw ter.scald ng_ nternal.job.analyt cs_batch.Analyt csBatchExecut onArgs
 mport com.tw ter.scald ng_ nternal.job.analyt cs_batch.BatchDescr pt on
 mport com.tw ter.scald ng_ nternal.job.analyt cs_batch.BatchF rstT  
 mport com.tw ter.scald ng_ nternal.job.analyt cs_batch.Batch ncre nt
 mport com.tw ter.scald ng_ nternal.job.analyt cs_batch.Tw terSc duledExecut onApp
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal
 mport com.tw ter.s mclusters_v2.common.ModelVers ons
 mport com.tw ter.s mclusters_v2.hdfs_s ces._
 mport com.tw ter.s mclusters_v2.scald ng.UpdateKnownFor.ClusterScoresForNode
 mport com.tw ter.s mclusters_v2.scald ng.UpdateKnownFor.Ne ghborhood nformat on
 mport com.tw ter.s mclusters_v2.scald ng.common.TypedR chP pe._
 mport com.tw ter.s mclusters_v2.scald ng.common.Ut l
 mport com.tw ter.s mclusters_v2.thr ftscala.ClustersUser sKnownFor
 mport com.tw ter.users ce.snapshot.flat.Users ceFlatScalaDataset
 mport scala.ut l.Success

object UpdateKnownForApps {

  /**
   * Average edge   ght of an  nput graph
   * @param graph a TypedP pe w h node d as key and adjacency l st as value.   don't care about
   *              t  keys  n t   thod.
   * @return avg edge   ght wrapped  n an opt on  n an execut on
   */
  def getGlobalAvg  ght(graph: TypedP pe[(Long, Map[Long, Float])]): Execut on[Opt on[Double]] = {
    graph.values
      .flatMap(_.values)
      .map { x => (x.toDouble, 1L) }
      .sum
      .toOpt onExecut on
      .map {
        case So ((sum, cnt)) =>
          val res = sum / cnt
          pr ntln("globalAvg  ght  s " + res)
          So (res)
        case _ =>
          pr ntln(" nput graph to globalAvg  ght seems to be empty")
          None
      }
  }

  /**
   * Average  mbersh p score for a part cular knownFor ass gn nt
   * @param knownFor TypedP pe from node d to t  clusters  's been ass gned to along w h
   *                  mbersh p scores.   don't care about t  keys  n t   thod.
   * @return average  mbersh p score
   */
  def getAvg mbersh pScore(knownFor: TypedP pe[(Long, Array[( nt, Float)])]): Execut on[Double] = {
    knownFor.values
      .flatMap(_.map(_._2))
      .map { x => (x, 1L) }
      .sum
      .map { case (num, den) => num / den.toDouble }
      .getExecut on
      .onComplete {
        case Success(x) => pr ntln("Avg.  mbersh p score  s " + x)
        case _ => pr ntln("Fa led to calculate avg.  mbersh p score")
      }
  }

  /**
   * For each cluster, get two stat st cs about  : t  number of nodes ass gned to  , and t 
   * sum of t   mbersh p scores
   *
   * @param knownFor TypedP pe from node d to t  clusters  's been ass gned to along w h
   *                  mbersh p scores.
   * @return Map g v ng t  Ne ghborhood nformat on for each cluster. T  nodeCount and
   *         sumOf mbersh p  ghts f elds  n Ne ghborhood nformat on are populated, ot rs are 0.
   */
  def getClusterStats(
    knownFor: TypedP pe[(Long, Array[( nt, Float)])]
  ): Execut on[Map[ nt, Ne ghborhood nformat on]] = {
    knownFor
      .flatMap {
        case (_, clusterArray) =>
          clusterArray.map {
            case (cluster d, score) =>
              Map(cluster d -> (1, score))
          }
      }
      .sum
      .getExecut on
      .map { map =>
        map.mapValues {
          case (count, sum) =>
            Ne ghborhood nformat on(count, 0, 0, sum)
        }
      }
  }

  /**
   * Adds self-loops and also potent ally ra ses all edge   ghts to an exponent
   * (typ cally exponent > 1, and has t  effect of  ncreas ng  nequal y  n edge   ghts to
   * "clar fy" structure  n t  graph - currently   just set exponent to 1).
   * @param sym tr zedS ms  nput sym tr zed s m lar y graph
   * @param exponentForEdge  ght exponent to ra se all edge   ghts to.
   *                              Set to 1.0 to make t  a no-op
   * @param maxWtToSelfLoopWtMultFactor What to mult ply t  max wt among non-self-loop edges to
   *                                    der ve t    ght on t  self-loop edge.
   * @return New graph
   */
  def s msGraphForUpdateFromSym tr zedS ms(
    sym tr zedS ms: TypedP pe[(Long, Map[Long, Float])],
    exponentForEdge  ght: Float,
    maxWtToSelfLoopWtMultFactor: Float
  ): TypedP pe[(Long, Map[Long, Float])] = {
    val exp  ghted = sym tr zedS ms.mapValues { y =>
      y.mapValues { x => math.pow(x, exponentForEdge  ght).toFloat }
    }

    TopUsersS m lar yGraph.addSelfLoop(
       nput = exp  ghted,
      maxToSelfLoop  ght = { x: Float => x * maxWtToSelfLoopWtMultFactor }
    )
  }

  /**
   * Runs t  job
   * @param args args wh ch spec fy many para ters
   * @param  nputKnownFor
   * @param  nputS msGraph
   * @param defaultEma lAddress by default, t  ema l address to send an to ema l to, wh ch has
   *                            a bunch of evaluat on  tr cs
   * @param wr eKnownForFunct on funct on that takes a knownFor and wr es to so 
   *                              pers stent locat on
   * @param readKnownForFunct on funct on that reads t  knownFor wh ch was wr ten to us ng t 
   *                             wr eKnownForFunct on
   * @param dateRange dateRange, used for read ng UserS ce
   * @param un que D need for creat ng stats
   * @return Execut on[Un ] encapsulat ng t  whole job
   */
  def runUpdateKnownForGener c(
    args: Args,
     nputKnownFor: TypedP pe[(Long, Array[( nt, Float)])],
     nputS msGraph: TypedP pe[Cand dates],
    defaultEma lAddress: Str ng,
    wr eKnownForFunct on: TypedP pe[(Long, Array[( nt, Float)])] => Execut on[Un ],
    readKnownForFunct on: => TypedP pe[(Long, Array[( nt, Float)])],
     ncludeEvaluat onResults nEma l: Boolean
  )(
     mpl c  dateRange: DateRange,
    un que D: Un que D
  ): Execut on[Un ] = {
    val m nAct veFollo rs = args. nt("m nAct veFollo rs", 400)
    val topK = args. nt("topK")
    val maxS msNe ghborsForUpdate =
      args. nt("maxS msNe ghborsForUpdate", 40)
    val m nNe ghbors nCluster = args. nt("m nNe ghbors nCluster", 2)
    val maxWtToSelfLoopWtMultFactor =
      args.float("maxWtToSelfLoopWtMultFactor", 2)
    val exponentForEdge  ght = args.float("exponentForEdge  ghts", 1.0f)
    val update thod: ClusterScoresForNode => Double = args("update thod") match {
      case "sumScore gnor ng mbersh pScores" => { x: ClusterScoresForNode =>
        x.sumScore gnor ng mbersh pScores
      }
      case "rat oScore gnor ng mbersh pScores" => { x: ClusterScoresForNode =>
        x.rat oScore gnor ng mbersh pScores
      }
      case "rat oScoreUs ng mbersh pScores" => { x: ClusterScoresForNode =>
        x.rat oScoreUs ng mbersh pScores
      }
      case x @ _ =>
        throw new Except on(s"value for --update thod $x  s unknown.   must be one of " +
          s"[sumScore gnor ng mbersh pScores, rat oScore gnor ng mbersh pScores, rat oScoreUs ng mbersh pScores]")
    }
    val truePos  veWtFactor = args.float("truePos  veWtFactor", 10)
    val modelVers on = args("outputModelVers on")
    val ema lAddress =
      args.opt onal("ema lAddress").getOrElse(defaultEma lAddress)

    val topUsers = TopUsersS m lar yGraph
      .topUser ds(
        DAL
          .readMostRecentSnapshot(Users ceFlatScalaDataset, dateRange)
          .toTypedP pe,
        m nAct veFollo rs,
        topK).count("num_top_users")

    TopUsersS m lar yGraph
      .getSubgraphFromUserGrouped nput(
        fullGraph =  nputS msGraph,
        usersTo nclude = topUsers,
        maxNe ghborsPerNode = maxS msNe ghborsForUpdate,
        degreeThresholdForStat = m nNe ghbors nCluster
      )
      .forceToD skExecut on
      .flatMap { sym tr zedS ms =>
        val mod f edS ms =
          UpdateKnownForApps.s msGraphForUpdateFromSym tr zedS ms(
            sym tr zedS ms = sym tr zedS ms,
            exponentForEdge  ght = exponentForEdge  ght,
            maxWtToSelfLoopWtMultFactor = maxWtToSelfLoopWtMultFactor
          )

        val prev ouslyFamousUsersExec =  nputKnownFor
          .leftJo n(topUsers.asKeys)
          .collect { case (user d, (clusters, None)) => user d }
          .getSummaryStr ng(
            "Users prev ously  n known for but not  n topUsers anymore",
            numRecords = 20)

        val clusterStatsExec = UpdateKnownForApps.getClusterStats( nputKnownFor)

        val globalAvg  ghtExec =
          UpdateKnownForApps.getGlobalAvg  ght(mod f edS ms)

        val globalAvg mbersh pScoreExec = UpdateKnownForApps.getAvg mbersh pScore( nputKnownFor)

        Execut on.z p(globalAvg  ghtExec, clusterStatsExec, globalAvg mbersh pScoreExec).flatMap {
          case (So (globalAvg  ght), clusterStats, globalAvg mbersh pScore) =>
            pr ntln("S ze of clusterStats: " + clusterStats.s ze)
            pr ntln("F rst few entr es from clusterStats: " + clusterStats.take(5))
            pr ntln("globalAvg  ght: " + globalAvg  ght)
            pr ntln("globalAvg mbersh pScore: " + globalAvg mbersh pScore)

            val knownForW hUnnormal zedScores = UpdateKnownFor
              .newKnownForScores(
                 nputKnownFor,
                mod f edS ms,
                globalAvg  ght,
                clusterStats,
                globalAvg mbersh pScore
              )
            val wr eNewKnownForExec = wr eKnownForFunct on(
              UpdateKnownFor.updateGener c(
                mod f edS ms,
                knownForW hUnnormal zedScores,
                clusterStats,
                m nNe ghbors nCluster,
                globalAvg  ght,
                globalAvg mbersh pScore,
                truePos  veWtFactor,
                update thod
              )
            )

            wr eNewKnownForExec.flatMap { _ =>
              Ut l.getCustomCountersStr ng(wr eNewKnownForExec).flatMap { customCountersStr ng =>
                 f ( ncludeEvaluat onResults nEma l) {
                  //  's unfortunate that  're not us ng t  newKnownFor d rectly, but are  nstead
                  // f rst wr  ng   out and t n read ng   back  n. T  reason for do ng    n t 
                  // convoluted way  s that w n   d rectly use t  newKnownFor, t  clusterEvaluat on
                  //  tr cs are be ng  ncorrectly computed.

                  val newKnownFor = readKnownForFunct on

                  val newResultsExec =
                    ClusterEvaluat on
                      .overallEvaluat on(sym tr zedS ms, newKnownFor, "newKnownForEval")
                  val oldResultsExec =
                    ClusterEvaluat on
                      .overallEvaluat on(sym tr zedS ms,  nputKnownFor, "oldKnownForEval")
                  val m nS zeOfB ggerClusterForCompar son = 10
                  val compareExec = CompareClusters.summar ze(
                    CompareClusters.compare(
                      KnownForS ces.transpose( nputKnownFor),
                      KnownForS ces.transpose(newKnownFor),
                      m nS zeOfB ggerCluster = m nS zeOfB ggerClusterForCompar son
                    ))

                  Execut on
                    .z p(oldResultsExec, newResultsExec, compareExec, prev ouslyFamousUsersExec)
                    .map {
                      case (oldResults, newResults, compareResults, prev ouslyFamousUsersStr ng) =>
                        val ema lText = "Evaluat on Results for ex st ng knownFor:\n" +
                          Ut l.prettyJsonMapper.wr eValueAsStr ng(oldResults) +
                          "\n\n-------------------\n\n" +
                          "Evaluat on Results for new knownFor:\n" +
                          Ut l.prettyJsonMapper.wr eValueAsStr ng(newResults) +
                          "\n\n-------------------\n\n" +
                          s"Cos ne s m lar y d str but on bet en cluster  mbersh p vectors for " +
                          s"clusters w h at least $m nS zeOfB ggerClusterForCompar son  mbers\n" +
                          Ut l.prettyJsonMapper
                            .wr eValueAsStr ng(compareResults) +
                          "\n\n-------------------\n\n" +
                          "Custom counters:\n" + customCountersStr ng +
                          "\n\n-------------------\n\n" +
                          prev ouslyFamousUsersStr ng

                        Ut l
                          .sendEma l(
                            ema lText,
                            s"Evaluat on results of new knownFor $modelVers on",
                            ema lAddress)
                    }
                } else {
                  Ut l
                    .sendEma l(
                      customCountersStr ng,
                      s"Change  n cluster ass gn nts for update of knownFor $modelVers on",
                      ema lAddress
                    )
                  Execut on.un 
                }

              }
            }
        }
      }
  }
}

tra  UpdateKnownForBatch extends Tw terSc duledExecut onApp {
   mpl c  val tz: java.ut l.T  Zone = DateOps.UTC
   mpl c  val dp = DateParser.default

  def f rstT  : Str ng

  val batch ncre nt: Durat on = Days(30)

  def batchDescr pt on: Str ng

  pr vate lazy val execArgs = Analyt csBatchExecut onArgs(
    batchDesc = BatchDescr pt on(batchDescr pt on),
    f rstT   = BatchF rstT  (R chDate(f rstT  )),
    lastT   = None,
    batch ncre nt = Batch ncre nt(batch ncre nt)
  )

  val ema lAddress: Str ng = "no-reply@tw ter.com"

  def  nputDALDataset: KeyValDALDataset[KeyVal[Long, ClustersUser sKnownFor]]

  def  nputModelVers on: Str ng

  def outputModelVers on: Str ng

  def outputPath: Str ng

  def outputDALDataset: KeyValDALDataset[KeyVal[Long, ClustersUser sKnownFor]]

  overr de def sc duledJob: Execut on[Un ] =
    Analyt csBatchExecut on(execArgs) {  mpl c  dateRange =>
      Execut on.w h d {  mpl c  un que d =>
        Execut on.w hArgs { args =>
          val  nputKnownFor =
            KnownForS ces.readDALDataset( nputDALDataset, Days(30),  nputModelVers on)

          val  nputS msGraph = TypedP pe
            .from(Follow ngsCos neS m lar  esManhattanS ce())
            .map(_._2)

          def wr eKnownFor(knownFor: TypedP pe[(Long, Array[( nt, Float)])]): Execut on[Un ] = {
            KnownForS ces
              .toKeyVal(knownFor, outputModelVers on)
              .wr eDALVers onedKeyValExecut on(
                outputDALDataset,
                D.Suff x(outputPath)
              )
          }

          def readKnownFor =
            KnownForS ces.readDALDataset(outputDALDataset, Days(1), outputModelVers on)

          UpdateKnownForApps.runUpdateKnownForGener c(
            args,
             nputKnownFor,
             nputS msGraph,
            ema lAddress,
            wr eKnownFor,
            readKnownFor,
             ncludeEvaluat onResults nEma l = false
          )
        }
      }
    }
}

/**
capesospy-v2 update --bu ld_locally --start_cron update_known_for_20M_145k \
 src/scala/com/tw ter/s mclusters_v2/capesos_conf g/atla_proc.yaml
 */
object UpdateKnownFor20M145K extends UpdateKnownForBatch {
  overr de val f rstT  : Str ng = "2019-06-06"

  overr de val batch ncre nt: Durat on = Days(7)

  overr de val batchDescr pt on: Str ng =
    "com.tw ter.s mclusters_v2.scald ng.UpdateKnownFor20M145K"

  overr de val  nputModelVers on: Str ng = ModelVers ons.Model20M145KUpdated

  overr de val  nputDALDataset: KeyValDALDataset[KeyVal[Long, ClustersUser sKnownFor]] =
    S mclustersV2RawKnownFor20M145KUpdatedScalaDataset

  overr de val outputModelVers on: Str ng = ModelVers ons.Model20M145KUpdated

  overr de val outputDALDataset: KeyValDALDataset[KeyVal[Long, ClustersUser sKnownFor]] =
    S mclustersV2RawKnownFor20M145KUpdatedScalaDataset

  overr de val outputPath: Str ng =  nternalDataPaths.RawKnownForUpdatedPath
}

/** T  one's end-to-end, doesn't save any  nter d ate data etc. **/
object UpdateKnownForAdhoc extends Tw terExecut onApp {
   mpl c  val tz: java.ut l.T  Zone = DateOps.UTC
   mpl c  val dp = DateParser.default

  def job: Execut on[Un ] =
    Execut on.getConf gMode.flatMap {
      case (conf g, mode) =>
        Execut on.w h d {  mpl c  un que d =>
          val args = conf g.getArgs
           mpl c  val date: DateRange = DateRange.parse(args("date"))
          val defaultEma lAddress = "y _ldap@tw ter.com"

          val  nputKnownFor = args.opt onal(" nputKnownForD r") match {
            case So ( nputKnownForD r) => KnownForS ces.readKnownFor( nputKnownForD r)
            case None => KnownForS ces.knownFor_20M_Dec11_145K
          }

          val  nputS msGraph = TopUsersS m lar yGraph.readS ms nput(
            args.boolean("s ms nput sKeyValS ce"),
            args("s ms nputD r")
          )

          def readKnownFor() = KnownForS ces.readKnownFor(args("outputD r"))

          UpdateKnownForApps.runUpdateKnownForGener c(
            args,
             nputKnownFor,
             nputS msGraph,
            defaultEma lAddress,
            {  nput: TypedP pe[(Long, Array[( nt, Float)])] =>
              KnownForS ces.wr eKnownForTypedTsv( nput, args("outputD r"))
            },
            readKnownFor,
             ncludeEvaluat onResults nEma l = true
          )
        }
    }
}
