package com.tw ter.s mclusters_v2.scald ng.top c_recom ndat ons

 mport com.tw ter.esc rb rd.scald ng.s ce.Full tadataS ce
 mport com.tw ter. nterests_ds.jobs. nterests_serv ce.UserTop cRelat onSnapshotScalaDataset
 mport com.tw ter. nterests.thr ftscala. nterestRelat onType
 mport com.tw ter. nterests.thr ftscala.User nterestsRelat onSnapshot
 mport com.tw ter.recos.ent  es.thr ftscala.Semant cCoreEnt y
 mport com.tw ter.recos.ent  es.thr ftscala.Semant cCoreEnt yScoreL st
 mport com.tw ter.recos.ent  es.thr ftscala.Semant cEnt yScore
 mport com.tw ter.scald ng._
 mport com.tw ter.scald ng_ nternal.dalv2.DAL
 mport com.tw ter.scald ng_ nternal.dalv2.DALWr e._
 mport com.tw ter.scald ng_ nternal.dalv2.remote_access.Expl c Locat on
 mport com.tw ter.scald ng_ nternal.dalv2.remote_access.ProcAtla
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal
 mport com.tw ter.s mclusters_v2.common.Semant cCoreEnt y d
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.s mclusters_v2.hdfs_s ces.S m larTop csFromTop cFollowGraphScalaDataset
 mport com.tw ter.s mclusters_v2.scald ng.common.matr x.SparseRowMatr x
 mport com.tw ter.wtf.scald ng.jobs.common.AdhocExecut onApp
 mport com.tw ter.wtf.scald ng.jobs.common.Sc duledExecut onApp
 mport java.ut l.T  Zone

/**
 *  n t  f le,   compute t  s m lar  es bet en top cs based on how often t y are co-follo d
 * by users.
 *
  * S m lar y( , j) = #co-follow( ,j) / sqrt(#follow( ) * #follow(j))
 *
  *   works as follows:
 *
  *  1.   f rst reads t  data set of user to top cs follow graph, and construct a sparse matr x M w h
 *    N rows and K columns, w re N  s t  number of users, and K  s t  number of top cs.
 *     n t  matr x, M(u, ) = 1  f user u follows top c  ; ot rw se    s 0.  n t  sparse matr x,
 *      only save non-zero ele nts.
 *
  *  2.   do l2-normal zat on for each column of t  matr x M, to get a normal zed vers on M'.
 *
  *  3.   get top c-top c s m lar y matr x S = M'.transpose.mult ply(M'). T  result ng matr x w ll
 *    conta n t  s m lar  es bet en all top cs,  .e., S( ,j)  s t  s m lar y    nt oned above.
 *
  *  4. for each top c,   only keep  s K s m lar top cs w h largest s m lar y scores, wh le not
 *    nclud ng those w h scores lo r than a threshold.
 *
  */
/**
 * capesospy-v2 update --bu ld_locally \
 * --start_cron s m lar_top cs_from_top c_follow_graph \
 * src/scala/com/tw ter/s mclusters_v2/capesos_conf g/atla_proc3.yaml
 */
object S m larTop csFromTop cFollowGraphSc duledApp extends Sc duledExecut onApp {
   mport S m larTop cs._

  pr vate val outputPath: Str ng =
    "/user/cassowary/manhattan_sequence_f les/s m lar_top cs_from_top cs_follow_graph"

  overr de def f rstT  : R chDate = R chDate("2020-05-07")

  overr de def batch ncre nt: Durat on = Days(7)

  overr de def runOnDateRange(
    args: Args
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): Execut on[Un ] = {
    val numS m larTop cs = args. nt("numS m larTop cs", default = 100)
    val scoreThreshold = args.double("scoreThreshold", default = 0.01)

    val numOutputTop cs = Stat("NumOutputTop cs")

    computeS m larTop cs(
      getExpl c Follo dTop cs,
      getFollowableTop cs,
      numS m larTop cs,
      scoreThreshold)
      .map {
        case (top c d, s m larTop cs) =>
          numOutputTop cs. nc()
          KeyVal(
            top c d,
            Semant cCoreEnt yScoreL st(s m larTop cs.map {
              case (s m larTop c d, score) =>
                Semant cEnt yScore(Semant cCoreEnt y(s m larTop c d), score)
            }))
      }
      .wr eDALVers onedKeyValExecut on(
        S m larTop csFromTop cFollowGraphScalaDataset,
        D.Suff x(outputPath),
        vers on = Expl c EndT  (dateRange.end)
      )
  }

}

/**
 scald ng remote run --user cassowary --reducers 2000 \
 --target src/scala/com/tw ter/s mclusters_v2/scald ng/top c_recom ndat ons:s m lar_top cs_from_top c_follow_graph-adhoc \
 --ma n-class com.tw ter.s mclusters_v2.scald ng.top c_recom ndat ons.S m larTop csFromTop cFollowGraphAdhocApp \
 --subm ter  hadoopnest1.atla.tw ter.com  \
 --  --date 2020-04-28
 */
object S m larTop csFromTop cFollowGraphAdhocApp extends AdhocExecut onApp {
   mport S m larTop cs._

  overr de def runOnDateRange(
    args: Args
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): Execut on[Un ] = {
    val numS m larTop cs = args. nt("numS m larTop cs", default = 100)
    val scoreThreshold = args.double("scoreThreshold", default = 0.01)

    val numOutputTop cs = Stat("NumOutputTop cs")

    computeS m larTop cs(
      getExpl c Follo dTop cs,
      getFollowableTop cs,
      numS m larTop cs,
      scoreThreshold)
      .map {
        case (top c d, s m larTop cs) =>
          numOutputTop cs. nc()
          top c d -> s m larTop cs
            .collect {
              case (s m larTop c, score)  f s m larTop c != top c d =>
                s"$s m larTop c:$score"
            }
            .mkStr ng(",")
      }
      .wr eExecut on(
        TypedTsv("/user/cassowary/adhoc/top c_recos/s m lar_top cs")
      )
  }

}

object S m larTop cs {

  val UTTDoma n: Long = 131L

  val FollowableTag: Str ng = "utt:followable_top c"

  def getFollowableTop cs(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): TypedP pe[Semant cCoreEnt y d] = {
    val NumFollowableTop cs = Stat("NumFollowableTop cs")

    TypedP pe
      .from(
        new Full tadataS ce("/atla/proc" + Full tadataS ce.DefaultHdfsPath)()(
          dateRange.emb ggen(Days(7))))
      .flatMap {
        case full tadata  f full tadata.doma n d == UTTDoma n =>
          for {
            bas c tadata <- full tadata.bas c tadata
             ndexableF elds <- bas c tadata. ndexableF elds
            tags <-  ndexableF elds.tags
             f tags.conta ns(FollowableTag)
          } y eld {
            NumFollowableTop cs. nc()
            full tadata.ent y d
          }
        case _ => None
      }
      .forceToD sk
  }

  def getExpl c Follo dTop cs(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): TypedP pe[(User d, Map[Semant cCoreEnt y d, Double])] = {

    DAL
      .readMostRecentSnapshotNoOlderThan(UserTop cRelat onSnapshotScalaDataset, Days(7))
      .w hRemoteReadPol cy(Expl c Locat on(ProcAtla))
      .toTypedP pe
      .collect {
        case user nterestsRelat onSnapshot: User nterestsRelat onSnapshot
             f user nterestsRelat onSnapshot. nterestType == "UTT" &&
              user nterestsRelat onSnapshot.relat on ==  nterestRelat onType.Follo d =>
          (
            user nterestsRelat onSnapshot.user d,
            Map(user nterestsRelat onSnapshot. nterest d -> 1.0))
      }
      .sumByKey
  }

  def computeS m larTop cs(
    userTop csFollowGraph: TypedP pe[(User d, Map[Semant cCoreEnt y d, Double])],
    followableTop cs: TypedP pe[Semant cCoreEnt y d],
    numS m larTop cs:  nt,
    scoreThreshold: Double
  ): TypedP pe[(Semant cCoreEnt y d, Seq[(Semant cCoreEnt y d, Double)])] = {
    val userTop cFollowGraph =
      SparseRowMatr x[User d, Semant cCoreEnt y d, Double](
        userTop csFollowGraph,
         sSk nnyMatr x = true)
        .f lterCols(followableTop cs) // f lter out unfollowable top cs
        .colL2Normal ze // normal zat on
        // due to t  small number of t  top cs,
        // Scald ng only allocates 1-2 mappers for t  next step wh ch makes   take unnecessar ly long t  .
        // Chang ng   to 10 to make   a b  faster
        .forceToD sk(numShardsOpt = So (10))

    userTop cFollowGraph
      .transposeAndMult plySk nnySparseRowMatr x(userTop cFollowGraph)
      .f lter { ( , j, v) =>
        // exclude top c  self from be ng cons dered as s m lar; also t  s m lar y score should
        // be larger than a threshold
          != j && v > scoreThreshold
      }
      .sortW hTakePerRow(numS m larTop cs)(Order ng.by(-_._2))
  }

}
