package com.tw ter.s mclusters_v2.scald ng

 mport com.tw ter.b ject on. nject on
 mport com.tw ter.fr gate.user_sampler.common.Employee ds
 mport com.tw ter.hash ng.KeyHas r
 mport com.tw ter.logg ng.Logger
 mport com.tw ter.scald ng._
 mport com.tw ter.scald ng_ nternal.dalv2.DAL
 mport com.tw ter.scald ng_ nternal.dalv2.DALWr e._
 mport com.tw ter.scald ng_ nternal.dalv2.remote_access.Expl c Locat on
 mport com.tw ter.scald ng_ nternal.dalv2.remote_access.ProcAtla
 mport com.tw ter.scald ng_ nternal.job.Tw terExecut onApp
 mport com.tw ter.scald ng_ nternal.job.analyt cs_batch.Analyt csBatchExecut on
 mport com.tw ter.scald ng_ nternal.job.analyt cs_batch.Analyt csBatchExecut onArgs
 mport com.tw ter.scald ng_ nternal.job.analyt cs_batch.BatchDescr pt on
 mport com.tw ter.scald ng_ nternal.job.analyt cs_batch.BatchF rstT  
 mport com.tw ter.scald ng_ nternal.job.analyt cs_batch.Batch ncre nt
 mport com.tw ter.scald ng_ nternal.job.analyt cs_batch.Tw terSc duledExecut onApp
 mport com.tw ter.s mclusters_v2.hdfs_s ces._
 mport com.tw ter.s mclusters_v2.scald ng.common.TypedR chP pe._
 mport com.tw ter.s mclusters_v2.scald ng.common.Ut l
 mport com.tw ter.s mclusters_v2.thr ftscala.EdgeW hDecayed  ghts
 mport com.tw ter.s mclusters_v2.thr ftscala.Ne ghborW h  ghts
 mport com.tw ter.s mclusters_v2.thr ftscala.NormsAndCounts
 mport com.tw ter.s mclusters_v2.thr ftscala.UserAndNe ghbors
 mport com.tw ter.users ce.snapshot.flat.Users ceFlatScalaDataset
 mport flockdb_tools.datasets.flock.FlockFollowsEdgesScalaDataset

case class Edge(src d: Long, dest d: Long,  sFollowEdge: Boolean, fav  ght: Double)

object UserUserNormal zedGraph {

  // T  common funct on for apply ng logar hm c transformat on
  def logTransformat on(  ght: Double): Double = {
    math.max(math.log10(1.0 +   ght), 0.0)
  }

  def getFollowEdges( mpl c  dateRange: DateRange, un que D: Un que D): TypedP pe[(Long, Long)] = {
    val num nputFollowEdges = Stat("num_ nput_follow_edges")
    DAL
      .readMostRecentSnapshot(FlockFollowsEdgesScalaDataset)
      .toTypedP pe
      .collect {
        case edge  f edge.state == 0 =>
          num nputFollowEdges. nc()
          (edge.s ce d, edge.dest nat on d)
      }
  }

  def transformFavEdges(
     nput: TypedP pe[EdgeW hDecayed  ghts],
    halfL fe nDaysForFavScore:  nt
  )(
     mpl c  un que D: Un que D
  ): TypedP pe[(Long, Long, Double)] = {
    val numEdgesW hSpec f edHalfL fe = Stat(
      s"num_edges_w h_spec f ed_half_l fe_${halfL fe nDaysForFavScore}_days")
    val numEdgesW houtSpec f edHalfL fe = Stat(
      s"num_edges_w hout_spec f ed_half_l fe_${halfL fe nDaysForFavScore}_days")
     nput
      .flatMap { edge =>
         f (edge.  ghts.halfL fe nDaysToDecayedSums.conta ns(halfL fe nDaysForFavScore)) {
          numEdgesW hSpec f edHalfL fe. nc()
          So ((edge.s ce d, edge.dest nat on d, edge.  ghts.halfL fe nDaysToDecayedSums(100)))
        } else {
          numEdgesW houtSpec f edHalfL fe. nc()
          None
        }
      }
  }

  def getFavEdges(
    halfL fe nDaysForFavScore:  nt
  )(
     mpl c  dateRange: DateRange,
    un que D: Un que D
  ): TypedP pe[(Long, Long, Double)] = {
     mpl c  val tz: java.ut l.T  Zone = DateOps.UTC
    transformFavEdges(
      DAL
        .readMostRecentSnapshot(UserUserFavGraphScalaDataset)
        .w hRemoteReadPol cy(Expl c Locat on(ProcAtla))
        .toTypedP pe,
      halfL fe nDaysForFavScore
    )
  }

  def getNe ghborW h  ghts(
     nputEdge: Edge,
    follo rL2NormOfDest: Double,
    faverL2NormOfDest: Double,
    logFavL2Norm: Double
  ): Ne ghborW h  ghts = {
    val normal zedFollowScore = {
      val nu rator =  f ( nputEdge. sFollowEdge) 1.0 else 0.0
       f (follo rL2NormOfDest > 0) nu rator / follo rL2NormOfDest else 0.0
    }
    val normal zedFavScore =
       f (faverL2NormOfDest > 0)  nputEdge.fav  ght / faverL2NormOfDest else 0.0
    val logFavScore =  f ( nputEdge.fav  ght > 0) logTransformat on( nputEdge.fav  ght) else 0.0
    val logFavScoreL2Normal zed =  f (logFavL2Norm > 0) logFavScore / logFavL2Norm else 0.0
    Ne ghborW h  ghts(
       nputEdge.dest d,
      So ( nputEdge. sFollowEdge),
      So (normal zedFollowScore),
      So ( nputEdge.fav  ght),
      So (normal zedFavScore),
      logFavScore = So (logFavScore),
      logFavScoreL2Normal zed = So (logFavScoreL2Normal zed)
    )
  }

  def addNormal zed  ghtsAndAdjL st fy(
     nput: TypedP pe[Edge],
    maxNe ghborsPerUser:  nt,
    normsAndCountsFull: TypedP pe[NormsAndCounts]
  )(
     mpl c  un que d: Un que D
  ): TypedP pe[UserAndNe ghbors] = {
    val numUsersNeed ngNe ghborTruncat on = Stat("num_users_need ng_ne ghbor_truncat on")
    val numEdgesAfterTruncat on = Stat("num_edges_after_truncat on")
    val numEdgesBeforeTruncat on = Stat("num_edges_before_truncat on")
    val numFollowEdgesBeforeTruncat on = Stat("num_follow_edges_before_truncat on")
    val numFavEdgesBeforeTruncat on = Stat("num_fav_edges_before_truncat on")
    val numFollowEdgesAfterTruncat on = Stat("num_follow_edges_after_truncat on")
    val numFavEdgesAfterTruncat on = Stat("num_fav_edges_after_truncat on")
    val numRecords nOutputGraph = Stat("num_records_ n_output_graph")

    val norms = normsAndCountsFull.map { record =>
      (
        record.user d,
        (
          record.follo rL2Norm.getOrElse(0.0),
          record.faverL2Norm.getOrElse(0.0),
          record.logFavL2Norm.getOrElse(0.0)))
    }

     mpl c  val l2b: Long => Array[Byte] =  nject on.long2B gEnd an
     nput
      .map { edge => (edge.dest d, edge) }
      .sketch(reducers = 2000)
      .jo n(norms)
      .map {
        case (dest d, (edge, (followNorm, favNorm, logFavNorm))) =>
          numEdgesBeforeTruncat on. nc()
           f (edge. sFollowEdge) numFollowEdgesBeforeTruncat on. nc()
           f (edge.fav  ght > 0) numFavEdgesBeforeTruncat on. nc()
          (edge.src d, getNe ghborW h  ghts(edge, followNorm, favNorm, logFavNorm))
      }
      .group
      //.w hReducers(1000)
      .sortedReverseTake(maxNe ghborsPerUser)(Order ng.by { x: Ne ghborW h  ghts =>
        (
          x.favScoreHalfL fe100Days.getOrElse(0.0),
          x.followScoreNormal zedByNe ghborFollo rsL2.getOrElse(0.0)
        )
      })
      .map {
        case (src d, ne ghborL st) =>
           f (ne ghborL st.s ze >= maxNe ghborsPerUser) numUsersNeed ngNe ghborTruncat on. nc()
          ne ghborL st.foreach { ne ghbor =>
            numEdgesAfterTruncat on. nc()
             f (ne ghbor.favScoreHalfL fe100Days.ex sts(_ > 0)) numFavEdgesAfterTruncat on. nc()
             f (ne ghbor. sFollo d.conta ns(true)) numFollowEdgesAfterTruncat on. nc()
          }
          numRecords nOutputGraph. nc()
          UserAndNe ghbors(src d, ne ghborL st)
      }
  }

  def comb neFollowAndFav(
    followEdges: TypedP pe[(Long, Long)],
    favEdges: TypedP pe[(Long, Long, Double)]
  ): TypedP pe[Edge] = {
    (
      followEdges.map { case (src, dest) => ((src, dest), (1, 0.0)) } ++
        favEdges.map { case (src, dest, wt) => ((src, dest), (0, wt)) }
    ).sumByKey
    //.w hReducers(2500)
      .map {
        case ((src, dest), (follow, favWt)) =>
          Edge(src, dest,  sFollowEdge = follow > 0, favWt)
      }
  }

  def run(
    followEdges: TypedP pe[(Long, Long)],
    favEdges: TypedP pe[(Long, Long, Double)],
    normsAndCounts: TypedP pe[NormsAndCounts],
    maxNe ghborsPerUser:  nt
  )(
     mpl c  un que D: Un que D
  ): TypedP pe[UserAndNe ghbors] = {
    val comb ned = comb neFollowAndFav(followEdges, favEdges)
    addNormal zed  ghtsAndAdjL st fy(
      comb ned,
      maxNe ghborsPerUser,
      normsAndCounts
    )
  }
}

object UserUserNormal zedGraphBatch extends Tw terSc duledExecut onApp {
  pr vate val f rstT  : Str ng = "2018-06-16"
   mpl c  val tz = DateOps.UTC
   mpl c  val parser = DateParser.default
  pr vate val batch ncre nt: Durat on = Days(7)
  pr vate val halfL fe nDaysForFavScore = 100

  pr vate val outputPath: Str ng = "/user/cassowary/processed/user_user_normal zed_graph"

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
          val maxNe ghborsPerUser = args. nt("maxNe ghborsPerUser", 2000)

          val producerNormsAndCounts =
            DAL.readMostRecentSnapshot(ProducerNormsAndCountsScalaDataset).toTypedP pe

          Ut l.pr ntCounters(
            UserUserNormal zedGraph
              .run(
                UserUserNormal zedGraph.getFollowEdges,
                UserUserNormal zedGraph.getFavEdges(halfL fe nDaysForFavScore),
                producerNormsAndCounts,
                maxNe ghborsPerUser
              )
              .wr eDALSnapshotExecut on(
                UserUserNormal zedGraphScalaDataset,
                D.Da ly,
                D.Suff x(outputPath),
                D.EBLzo(),
                dateRange.end)
          )
        }
      }
  }
}

object UserUserNormal zedGraphAdhoc extends Tw terExecut onApp {
   mpl c  val tz: java.ut l.T  Zone = DateOps.UTC
   mpl c  val dp = DateParser.default
  val log = Logger()

  def hashToLong( nput: Long): Long = {
    val bb = java.n o.ByteBuffer.allocate(8)
    bb.putLong( nput)
    Math.abs(KeyHas r.KETAMA.hashKey(bb.array()))
  }

  def job: Execut on[Un ] =
    Execut on.getConf gMode.flatMap {
      case (conf g, mode) =>
        Execut on.w h d {  mpl c  un que d =>
          val args = conf g.getArgs
           mpl c  val dateRange: DateRange = DateRange.parse(args.l st("date"))
          val halfL fe nDaysForFavScore = 100
          val maxNe ghborsPerUser = args. nt("maxNe ghborsPerUser", 2000)
          val producerNormsAndCounts = TypedP pe.from(
            NormsAndCountsF xedPathS ce(args("norms nputD r"))
          )
          val favEdges = args.opt onal("favGraph nputD r") match {
            case So (favGraph nputD r) =>
              UserUserNormal zedGraph.transformFavEdges(
                TypedP pe.from(
                  EdgeW hDecayedWtsF xedPathS ce(favGraph nputD r)
                ),
                halfL fe nDaysForFavScore
              )
            case None =>
              UserUserNormal zedGraph.getFavEdges(halfL fe nDaysForFavScore)
          }

          val followEdges = UserUserNormal zedGraph.getFollowEdges

          Ut l.pr ntCounters(
            UserUserNormal zedGraph
              .run(
                followEdges,
                favEdges,
                producerNormsAndCounts,
                maxNe ghborsPerUser
              ).wr eExecut on(UserAndNe ghborsF xedPathS ce(args("outputD r")))
          )
        }
    }
}

object DumpUserUserGraphAdhoc extends Tw terExecut onApp {
   mpl c  val tz: java.ut l.T  Zone = DateOps.UTC
  def job: Execut on[Un ] =
    Execut on.getConf gMode.flatMap {
      case (conf g, mode) =>
        Execut on.w h d {  mpl c  un que d =>
          val args = conf g.getArgs
          val  nput = args.opt onal(" nputD r") match {
            case So ( nputD r) => TypedP pe.from(UserAndNe ghborsF xedPathS ce( nputD r))
            case None =>
              DAL
                .readMostRecentSnapshotNoOlderThan(UserUserNormal zedGraphScalaDataset, Days(30))
                .w hRemoteReadPol cy(Expl c Locat on(ProcAtla))
                .toTypedP pe
          }
          val users = args.l st("users").map(_.toLong).toSet
           f (users. sEmpty) {
             nput.pr ntSummary("Producer norms and counts")
          } else {
             nput
              .collect {
                case rec  f users.conta ns(rec.user d) =>
                  (Seq(rec.user d.toStr ng) ++ rec.ne ghbors.map { n =>
                    Ut l.prettyJsonMapper.wr eValueAsStr ng(n).replaceAll("\n", " ")
                  }).mkStr ng("\n")
              }
              .to erableExecut on
              .map { str ngs => pr ntln(str ngs.mkStr ng("\n")) }
          }
        }
    }
}

/*
 * ./bazel bundle src/scala/com/tw ter/s mclusters_v2/scald ng:user_user_normal zed_graph && \
 * oscar hdfs --host hadoopnest2.atla.tw ter.com --bundle user_user_normal zed_graph \
 * --tool com.tw ter.s mclusters_v2.scald ng.EmployeeGraph --screen --screen-detac d \
 * --tee y _ldap/employeeGraph20190809 -- --outputD r adhoc/employeeGraph20190809
 */
object EmployeeGraph extends Tw terExecut onApp {
   mpl c  val tz: java.ut l.T  Zone = DateOps.UTC
  def job: Execut on[Un ] =
    Execut on.getConf gMode.flatMap {
      case (conf g, mode) =>
        Execut on.w h d {  mpl c  un que d =>
          val args = conf g.getArgs
          val  nput = args.opt onal(" nputD r") match {
            case So ( nputD r) => TypedP pe.from(UserAndNe ghborsF xedPathS ce( nputD r))
            case None =>
              DAL
                .readMostRecentSnapshotNoOlderThan(UserUserNormal zedGraphScalaDataset, Days(30))
                .w hRemoteReadPol cy(Expl c Locat on(ProcAtla))
                .toTypedP pe
          }
          val employee ds = Employee ds.bu ld rl nCl entAndGetEmployees("fr gate-scald ng.dev")
           nput
            .collect {
              case rec  f employee ds.conta ns(rec.user d) =>
                rec.ne ghbors.collect {
                  case n  f employee ds.conta ns(n.ne ghbor d) =>
                    (
                      rec.user d,
                      n.ne ghbor d,
                      n.favScoreHalfL fe100Days.getOrElse(0),
                      n. sFollo d.getOrElse(false))
                }
            }
            .flatten
            .wr eExecut on(TypedTsv(args("outputD r")))

        }
    }
}
/*
 * scald ng remote run --target src/scala/com/tw ter/s mclusters_v2/scald ng:employee_graph_from_user_user
 * --ma n-class com.tw ter.s mclusters_v2.scald ng.EmployeeGraphFromUserUser
 * --subm ter  hadoopnest2.atla.tw ter.com --user recos-platform -- --graphOutputD r "/user/recos-platform/adhoc/employee_graph_from_user_user/"
 */

object EmployeeGraphFromUserUser extends Tw terExecut onApp {
   mpl c  val tz: java.ut l.T  Zone = DateOps.UTC
  def job: Execut on[Un ] =
    Execut on.getConf gMode.flatMap {
      case (conf g, mode) =>
        Execut on.w h d {  mpl c  un que d =>
          val args = conf g.getArgs
          val graphOutputD r = args("graphOutputD r")
          val  nput = args.opt onal(" nputD r") match {
            case So ( nputD r) => TypedP pe.from(UserAndNe ghborsF xedPathS ce( nputD r))
            case None =>
              DAL
                .readMostRecentSnapshotNoOlderThan(UserUserNormal zedGraphScalaDataset, Days(30))
                .w hRemoteReadPol cy(Expl c Locat on(ProcAtla))
                .toTypedP pe
          }
          val employee ds = Employee ds.bu ld rl nCl entAndGetEmployees("fr gate-scald ng.dev")
           nput
            .collect {
              case rec  f employee ds.conta ns(rec.user d) =>
                rec
            }
            .wr eExecut on(UserAndNe ghborsF xedPathS ce(graphOutputD r))

        }
    }
}

/*
 * ./bazel bundle src/scala/com/tw ter/s mclusters_v2/scald ng:user_user_normal zed_graph && \
 * oscar hdfs --host hadoopnest2.atla.tw ter.com --bundle user_user_normal zed_graph \
 * --tool com.tw ter.s mclusters_v2.scald ng.V Graph --screen --screen-detac d \
 * --tee y _ldap/v Graph20190809 -- --outputD r adhoc/v Graph20190809
 */
object V Graph extends Tw terExecut onApp {
   mpl c  val tz: java.ut l.T  Zone = DateOps.UTC
  def job: Execut on[Un ] =
    Execut on.getConf gMode.flatMap {
      case (conf g, mode) =>
        Execut on.w h d {  mpl c  un que d =>
          val args = conf g.getArgs
          val m nAct veFollo rs = args. nt("m nAct veFollo rs")
          val topK = args. nt("topK")
          val  nput = args.opt onal(" nputD r") match {
            case So ( nputD r) => TypedP pe.from(UserAndNe ghborsF xedPathS ce( nputD r))
            case None =>
              DAL
                .readMostRecentSnapshotNoOlderThan(UserUserNormal zedGraphScalaDataset, Days(30))
                .w hRemoteReadPol cy(Expl c Locat on(ProcAtla))
                .toTypedP pe
          }
          val userS ce =
            DAL.readMostRecentSnapshotNoOlderThan(Users ceFlatScalaDataset, Days(30)).toTypedP pe

          TopUsersS m lar yGraph
            .v s(userS ce, m nAct veFollo rs, topK).to erableExecut on.flatMap { v s er =>
              val v s = v s er.toSet
              pr ntln(s"Found ${v s.s ze} many v s. F rst few: " + v s.take(5).mkStr ng(","))
               nput
                .collect {
                  case rec  f v s.conta ns(rec.user d) =>
                    rec.ne ghbors.collect {
                      case n  f v s.conta ns(n.ne ghbor d) =>
                        (
                          rec.user d,
                          n.ne ghbor d,
                          n.favScoreHalfL fe100Days.getOrElse(0),
                          n. sFollo d.getOrElse(false))
                    }
                }
                .flatten
                .wr eExecut on(TypedTsv(args("outputD r")))
            }

        }
    }

}
