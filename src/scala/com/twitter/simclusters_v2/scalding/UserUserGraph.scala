package com.tw ter.s mclusters_v2.scald ng

 mport com.tw ter.scald ng._
 mport com.tw ter.scald ng_ nternal.dalv2.DALWr e.{D, Wr eExtens on}
 mport com.tw ter.scald ng_ nternal.job.analyt cs_batch.{
  Analyt csBatchExecut on,
  Analyt csBatchExecut onArgs,
  BatchDescr pt on,
  BatchF rstT  ,
  Batch ncre nt,
  Tw terSc duledExecut onApp
}
 mport com.tw ter.s mclusters_v2.scald ng.common.Ut l
 mport com.tw ter.s mclusters_v2.hdfs_s ces.{
  UserAndNe ghborsF xedPathS ce,
  UserUserGraphScalaDataset
}
 mport com.tw ter.s mclusters_v2.thr ftscala.{Ne ghborW h  ghts, UserAndNe ghbors}
 mport com.tw ter.wtf.scald ng.jobs.common.AdhocExecut onApp
 mport java.ut l.T  Zone

/**
 * T   s a sc duled vers on of t  user_user_normal zed_graph dataset generat on job.
 *
 * T  key d fference  n t   mple ntat on  s that   donot read t  ProducerNormsAndCounts dataset.
 * So   no longer store t  follow ng producer normal zed scores for t  edges  n t  Ne gborW h  ghts thr ft:
 * followScoreNormal zedByNe ghborFollo rsL2, favScoreHalfL fe100DaysNormal zedByNe ghborFaversL2 and logFavScoreL2Normal zed
 *
 */
object UserUserGraph {

  def getNe ghborW h  ghts(
     nputEdge: Edge
  ): Ne ghborW h  ghts = {
    val logFavScore = UserUserNormal zedGraph.logTransformat on( nputEdge.fav  ght)
    Ne ghborW h  ghts(
      ne ghbor d =  nputEdge.dest d,
       sFollo d = So ( nputEdge. sFollowEdge),
      favScoreHalfL fe100Days = So ( nputEdge.fav  ght),
      logFavScore = So (logFavScore),
    )
  }

  def add  ghtsAndAdjL st fy(
     nput: TypedP pe[Edge],
    maxNe ghborsPerUser:  nt
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

     nput
      .map { edge =>
        numEdgesBeforeTruncat on. nc()
         f (edge. sFollowEdge) numFollowEdgesBeforeTruncat on. nc()
         f (edge.fav  ght > 0) numFavEdgesBeforeTruncat on. nc()
        (edge.src d, getNe ghborW h  ghts(edge))
      }
      .group
      //      .w hReducers(10000)
      .sortedReverseTake(maxNe ghborsPerUser)(Order ng.by { x: Ne ghborW h  ghts =>
        x.favScoreHalfL fe100Days.getOrElse(0.0)
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

  def run(
    followEdges: TypedP pe[(Long, Long)],
    favEdges: TypedP pe[(Long, Long, Double)],
    maxNe ghborsPerUser:  nt
  )(
     mpl c  un que D: Un que D
  ): TypedP pe[UserAndNe ghbors] = {
    val comb ned = UserUserNormal zedGraph.comb neFollowAndFav(followEdges, favEdges)
    add  ghtsAndAdjL st fy(
      comb ned,
      maxNe ghborsPerUser
    )
  }
}

/**
 *
 * capesospy-v2 update --bu ld_locally --start_cron user_user_follow_fav_graph \
 * src/scala/com/tw ter/s mclusters_v2/capesos_conf g/atla_proc.yaml
 */

object UserUserGraphBatch extends Tw terSc duledExecut onApp {
  pr vate val f rstT  : Str ng = "2021-04-24"
   mpl c  val tz = DateOps.UTC
   mpl c  val parser = DateParser.default
  pr vate val batch ncre nt: Durat on = Days(2)
  pr vate val halfL fe nDaysForFavScore = 100

  pr vate val outputPath: Str ng = "/user/cassowary/processed/user_user_graph"

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

          Ut l.pr ntCounters(
            UserUserGraph
              .run(
                UserUserNormal zedGraph.getFollowEdges,
                UserUserNormal zedGraph.getFavEdges(halfL fe nDaysForFavScore),
                maxNe ghborsPerUser
              )
              .wr eDALSnapshotExecut on(
                UserUserGraphScalaDataset,
                D.Da ly,
                D.Suff x(outputPath),
                D.EBLzo(),
                dateRange.end)
          )
        }
      }
  }
}

/**
./bazel bundle src/scala/com/tw ter/s mclusters_v2/scald ng:user_user_graph-adhoc
scald ng remote run \
--user cassowary \
--keytab /var/l b/tss/keys/fluffy/keytabs/cl ent/cassowary.keytab \
--pr nc pal serv ce_acoount@TW TTER.B Z \
--cluster blueb rd-qus1 \
--ma n-class com.tw ter.s mclusters_v2.scald ng.UserUserGraphAdhoc \
--target src/scala/com/tw ter/s mclusters_v2/scald ng:user_user_graph-adhoc \
-- --date 2021-04-24 --outputD r "/user/cassowary/adhoc/user_user_graph_adhoc"
 */
object UserUserGraphAdhoc extends AdhocExecut onApp {
  overr de def runOnDateRange(
    args: Args
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): Execut on[Un ] = {
    val maxNe ghborsPerUser = args. nt("maxNe ghborsPerUser", 2000)
    val halfL fe nDaysForFavScore = 100
    val outputD r = args("outputD r")
    val userAndNe ghbors =
      UserUserGraph
        .run(
          UserUserNormal zedGraph.getFollowEdges,
          UserUserNormal zedGraph.getFavEdges(halfL fe nDaysForFavScore),
          maxNe ghborsPerUser)

    Execut on
      .z p(
        userAndNe ghbors.wr eExecut on(UserAndNe ghborsF xedPathS ce(outputD r)),
        userAndNe ghbors.wr eExecut on(TypedTsv(outputD r + "_tsv"))).un 
  }
}
