package com.tw ter.s mclusters_v2.scald ng

 mport com.tw ter.logg ng.Logger
 mport com.tw ter.scald ng._
 mport com.tw ter.scald ng_ nternal.dalv2.DAL
 mport com.tw ter.scald ng_ nternal.dalv2.DALWr e._
 mport com.tw ter.scald ng_ nternal.dalv2.remote_access.{Expl c Locat on, ProcAtla}
 mport com.tw ter.scald ng_ nternal.job.Tw terExecut onApp
 mport com.tw ter.scald ng_ nternal.job.analyt cs_batch._
 mport com.tw ter.s mclusters_v2.hdfs_s ces.{
  NormsAndCountsF xedPathS ce,
  ProducerNormsAndCountsScalaDataset
}
 mport com.tw ter.s mclusters_v2.scald ng.common.TypedR chP pe._
 mport com.tw ter.s mclusters_v2.scald ng.common.Ut l
 mport com.tw ter.s mclusters_v2.thr ftscala.NormsAndCounts

object ProducerNormsAndCounts {

  def getNormsAndCounts(
     nput: TypedP pe[Edge]
  )(
     mpl c  un que D: Un que D
  ): TypedP pe[NormsAndCounts] = {
    val numRecords nNormsAndCounts = Stat("num_records_ n_norms_and_counts")
     nput
      .map {
        case Edge(src d, dest d,  sFollowEdge, favWt) =>
          val followOrNot =  f ( sFollowEdge) 1 else 0
          ((src d, dest d), (followOrNot, favWt))
      }
      .sumByKey
      // Uncom nt for adhoc job
      //.w hReducers(2500)
      .map {
        case ((src d, dest d), (followOrNot, favWt)) =>
          val favOrNot =  f (favWt > 0) 1 else 0
          val logFavScore =  f (favWt > 0) UserUserNormal zedGraph.logTransformat on(favWt) else 0.0
          (
            dest d,
            (
              followOrNot,
              favWt * favWt,
              favOrNot,
              favWt,
              favWt * followOrNot.toDouble,
              logFavScore * logFavScore,
              logFavScore,
              logFavScore * followOrNot.toDouble))
      }
      .sumByKey
      // Uncom nt for adhoc job
      //.w hReducers(500)
      .map {
        case (
               d,
              (
                followCount,
                favSumSquare,
                favCount,
                favSumOnFavEdges,
                favSumOnFollowEdges,
                logFavSumSquare,
                logFavSumOnFavEdges,
                logFavSumOnFollowEdges)) =>
          val follo rNorm = math.sqrt(followCount)
          val faverNorm = math.sqrt(favSumSquare)
          numRecords nNormsAndCounts. nc()
          NormsAndCounts(
            user d =  d,
            follo rL2Norm = So (follo rNorm),
            faverL2Norm = So (faverNorm),
            follo rCount = So (followCount),
            faverCount = So (favCount),
            fav  ghtsOnFavEdgesSum = So (favSumOnFavEdges),
            fav  ghtsOnFollowEdgesSum = So (favSumOnFollowEdges),
            logFavL2Norm = So (math.sqrt(logFavSumSquare)),
            logFav  ghtsOnFavEdgesSum = So (logFavSumOnFavEdges),
            logFav  ghtsOnFollowEdgesSum = So (logFavSumOnFollowEdges)
          )
      }
  }

  def run(
    halfL fe nDaysForFavScore:  nt
  )(
     mpl c  un que D: Un que D,
    date: DateRange
  ): TypedP pe[NormsAndCounts] = {
    val  nput =
      UserUserNormal zedGraph.getFollowEdges.map {
        case (src, dest) =>
          Edge(src, dest,  sFollowEdge = true, 0.0)
      } ++ UserUserNormal zedGraph.getFavEdges(halfL fe nDaysForFavScore).map {
        case (src, dest, wt) =>
          Edge(src, dest,  sFollowEdge = false, wt)
      }
    getNormsAndCounts( nput)
  }
}

object ProducerNormsAndCountsBatch extends Tw terSc duledExecut onApp {
  pr vate val f rstT  : Str ng = "2018-06-16"
   mpl c  val tz = DateOps.UTC
   mpl c  val parser = DateParser.default
  pr vate val batch ncre nt: Durat on = Days(7)
  pr vate val f rstStartDate = DateRange.parse(f rstT  ).start
  pr vate val halfL fe nDaysForFavScore = 100

  pr vate val outputPath: Str ng = "/user/cassowary/processed/producer_norms_and_counts"
  pr vate val log = Logger()

  pr vate val execArgs = Analyt csBatchExecut onArgs(
    batchDesc = BatchDescr pt on(t .getClass.getNa .replace("$", "")),
    f rstT   = BatchF rstT  (R chDate(f rstT  )),
    lastT   = None,
    batch ncre nt = Batch ncre nt(batch ncre nt)
  )

  overr de def sc duledJob: Execut on[Un ] = Analyt csBatchExecut on(execArgs) {
     mpl c  dateRange =>
      Execut on.w h d {  mpl c  un que d =>
        Execut on.w hArgs { args =>
          Ut l.pr ntCounters(
            ProducerNormsAndCounts
              .run(halfL fe nDaysForFavScore)
              .wr eDALSnapshotExecut on(
                ProducerNormsAndCountsScalaDataset,
                D.Da ly,
                D.Suff x(outputPath),
                D.EBLzo(),
                dateRange.end)
          )
        }
      }
  }
}

object ProducerNormsAndCountsAdhoc extends Tw terExecut onApp {
   mpl c  val tz: java.ut l.T  Zone = DateOps.UTC
   mpl c  val dp = DateParser.default

  def job: Execut on[Un ] =
    Execut on.getConf gMode.flatMap {
      case (conf g, mode) =>
        Execut on.w h d {  mpl c  un que d =>
          val args = conf g.getArgs
           mpl c  val date = DateRange.parse(args.l st("date"))

          Ut l.pr ntCounters(
            ProducerNormsAndCounts
              .run(halfL fe nDaysForFavScore = 100)
              .forceToD skExecut on.flatMap { result =>
                Execut on.z p(
                  result.wr eExecut on(NormsAndCountsF xedPathS ce(args("outputD r"))),
                  result.pr ntSummary("Producer norms and counts")
                )
              }
          )
        }
    }
}

object DumpNormsAndCountsAdhoc extends Tw terExecut onApp {
   mpl c  val tz: java.ut l.T  Zone = DateOps.UTC
  def job: Execut on[Un ] =
    Execut on.getConf gMode.flatMap {
      case (conf g, mode) =>
        Execut on.w h d {  mpl c  un que d =>
          val args = conf g.getArgs

          val users = args.l st("users").map(_.toLong).toSet
          val  nput = args.opt onal(" nputD r") match {
            case So ( nputD r) => TypedP pe.from(NormsAndCountsF xedPathS ce( nputD r))
            case None =>
              DAL
                .readMostRecentSnapshotNoOlderThan(ProducerNormsAndCountsScalaDataset, Days(30))
                .w hRemoteReadPol cy(Expl c Locat on(ProcAtla))
                .toTypedP pe
          }

           f (users. sEmpty) {
             nput.pr ntSummary("Producer norms and counts")
          } else {
             nput
              .collect {
                case rec  f users.conta ns(rec.user d) =>
                  Ut l.prettyJsonMapper.wr eValueAsStr ng(rec).replaceAll("\n", " ")
              }
              .to erableExecut on
              .map { str ngs => pr ntln(str ngs.mkStr ng("\n")) }
          }
        }
    }
}
