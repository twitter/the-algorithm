package com.tw ter.s mclusters_v2.scald ng.update_known_for

 mport com.tw ter.b ject on.scrooge.B naryScalaCodec
 mport com.tw ter. rm .cand date.thr ftscala.Cand dates
 mport com.tw ter.logg ng.Logger
 mport com.tw ter.pluck.s ce.cassowary.Follow ngsCos neS m lar  esManhattanS ce
 mport com.tw ter.scald ng.typed.TypedP pe
 mport com.tw ter.scald ng.DateOps
 mport com.tw ter.scald ng.DateParser
 mport com.tw ter.scald ng.Days
 mport com.tw ter.scald ng.Execut on
 mport com.tw ter.scald ng.R chDate
 mport com.tw ter.scald ng.TypedTsv
 mport com.tw ter.scald ng.Un que D
 mport com.tw ter.scald ng._
 mport com.tw ter.scald ng_ nternal.dalv2.DAL
 mport com.tw ter.scald ng_ nternal.dalv2.DALWr e.D
 mport com.tw ter.scald ng_ nternal.dalv2.DALWr e._
 mport com.tw ter.scald ng_ nternal.dalv2.remote_access.AllowCrossClusterSa DC
 mport com.tw ter.scald ng_ nternal.job.Tw terExecut onApp
 mport com.tw ter.s mclusters_v2.common.Cluster d
 mport com.tw ter.s mclusters_v2.common.ModelVers ons
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.s mclusters_v2.hdfs_s ces.AdhocKeyValS ces
 mport com.tw ter.s mclusters_v2.hdfs_s ces. nternalDataPaths
 mport com.tw ter.s mclusters_v2.hdfs_s ces.S mclustersV2KnownFor20M145KDec11ScalaDataset
 mport com.tw ter.s mclusters_v2.hdfs_s ces.S mclustersV2KnownFor20M145KUpdatedScalaDataset
 mport com.tw ter.s mclusters_v2.hdfs_s ces.S mclustersV2RawKnownFor20M145K2020ScalaDataset
 mport com.tw ter.s mclusters_v2.scald ng.KnownForS ces
 mport com.tw ter.s mclusters_v2.scald ng.KnownForS ces.fromKeyVal
 mport com.tw ter.s mclusters_v2.scald ng.common.Ut l
 mport com.tw ter.wtf.scald ng.jobs.common.Sc duledExecut onApp
 mport java.ut l.T  Zone

/**
 * Sc duled job
 *
 * capesospy-v2 update --bu ld_locally --start_cron update_known_for_20m_145k_2020 \
 * src/scala/com/tw ter/s mclusters_v2/capesos_conf g/atla_proc.yaml
 */

object UpdateKnownFor20M145K2020 extends Sc duledExecut onApp {

  overr de val f rstT  : R chDate = R chDate("2020-10-04")

  overr de val batch ncre nt: Durat on = Days(7)

  pr vate val tempLocat onPath = "/user/cassowary/temp/s mclusters_v2/known_for_20m_145k_2020"

  pr vate val s msGraphPath =
    "/atla/proc/user/cassowary/manhattan_sequence_f les/approx mate_cos ne_s m lar y_follow"

  overr de def runOnDateRange(
    args: Args
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): Execut on[Un ] = {

    Execut on.getConf gMode.flatMap {
      case (_, mode) =>
         mpl c  def valueCodec: B naryScalaCodec[Cand dates] = B naryScalaCodec(Cand dates)
        // Step - 1 (DataProcess ng): Para ters for gett ng mapped  nd ces for user- ds
        val m nAct veFollo rs = args. nt("m nAct veFollo rs", 400)
        val topK = args. nt("topK", 20000000)

        // Step - 2 (DataProcess ng): Para ters to remove users not  n t  topK most follo d users from s msGraph
        val maxNe ghbors = args. nt("maxNe ghbors", 400)

        // Step - 3 (F nal Cluster ng): Para ters to run t  cluster ng algor hm
        /* square  ghtEnable  s a boolean flag that changes t  edge   ghts obta ned from t 
          underly ng s ms graph
           a)  f false -  edge   ght bet en two ne ghbors  s just t  r cos ne s m lar y.
           b)  f true - edge   ght = cos ne_s m * cos ne_s m * 10. T  squar ng makes t  h g r
             ght edges relat vely more  mportant; t   s based on t   ntu  on that a ne ghbor
           w h cos ne s m lar y of 0.1  s more than 2x  mportant compared to a ne ghbor w h
           cos ne s m lar y of 0.05. T  mult pl cat on w h 10 br ngs t    ghts back  nto a
           'n cer' range s nce squar ng w ll reduce t  r absolute value.
         */
        val square  ghtsEnable = args.boolean("square  ghtsEnable")

        val maxEpochsForCluster ng = args. nt("maxEpochs", 3)
        val wtCoeff = args.double("wtCoeff", 10.0)

        val prev ousKnownFor: TypedP pe[(User d, Array[(Cluster d, Float)])] =
          fromKeyVal(
            DAL
              .readMostRecentSnapshot(
                S mclustersV2RawKnownFor20M145K2020ScalaDataset,
                dateRange.emb ggen(Days(30)))
              .w hRemoteReadPol cy(AllowCrossClusterSa DC)
              .toTypedP pe,
            ModelVers ons.Model20M145K2020
          )

        UpdateKnownForSBFRunner
          .runUpdateKnownFor(
            TypedP pe
              .from(Follow ngsCos neS m lar  esManhattanS ce(s msGraphPath))
              .map(_._2),
            m nAct veFollo rs,
            topK,
            maxNe ghbors,
            tempLocat onPath,
            prev ousKnownFor,
            maxEpochsForCluster ng,
            square  ghtsEnable,
            wtCoeff,
            mode
          )
          .flatMap { updateKnownFor =>
            Execut on
              .z p(
                KnownForS ces
                  .toKeyVal(updateKnownFor, ModelVers ons.Model20M145K2020)
                  .wr eDALVers onedKeyValExecut on(
                    S mclustersV2RawKnownFor20M145K2020ScalaDataset,
                    D.Suff x( nternalDataPaths.RawKnownFor2020Path)
                  ),
                UpdateKnownForSBFRunner
                  .evaluateUpdatedKnownFor(updateKnownFor, prev ousKnownFor)
                  .flatMap { ema lText =>
                    Ut l
                      .sendEma l(
                        ema lText,
                        s"Change  n cluster ass gn nts for new KnownFor ModelVers on: 20M145K2020",
                        "no-reply@tw ter.com")
                    Execut on.un 
                  }
              ).un 
          }
    }
  }
}
/*
knownFor  ek-1:
scald ng remote run \
--target src/scala/com/tw ter/s mclusters_v2/scald ng/update_known_for:update_known_for_20m_145k_2020-adhoc \
--ma n-class com.tw ter.s mclusters_v2.scald ng.update_known_for.UpdateKnownFor20M145K2020Adhoc \
--subm ter  atla-aor-08-sr1 --user cassowary \
--subm ter- mory 128192. gabyte --hadoop-propert es "mapreduce.map. mory.mb=8192 mapreduce.map.java.opts='-Xmx7618M' mapreduce.reduce. mory.mb=8192 mapreduce.reduce.java.opts='-Xmx7618M'" \
-- \
--date 2020-08-30  --maxNe ghbors 100 --m nAct veFollo rs 400 --topK 20000000 --numNodesPerCommun y 200  --maxEpochs 4 --square  ghtsEnable --wtCoeff 10.0 \
-- nputS msD r /atla/proc/user/cassowary/manhattan_sequence_f les/approx mate_cos ne_s m lar y_follow  \
--outputClusterD r /user/cassowary/adhoc/y _ldap/s mclusters/cluster ng_outputs/output_cluster ng_ass gn nts_2020_readAga n_v4_ ek_1

knownFor  ek-2:
scald ng remote run \
--target src/scala/com/tw ter/s mclusters_v2/scald ng/update_known_for:update_known_for_20m_145k_2020-adhoc \
--ma n-class com.tw ter.s mclusters_v2.scald ng.update_known_for.UpdateKnownFor20M145K2020Adhoc \
--subm ter  atla-aor-08-sr1 --user cassowary \
--subm ter- mory 128192. gabyte --hadoop-propert es "mapreduce.map. mory.mb=8192 mapreduce.map.java.opts='-Xmx7618M' mapreduce.reduce. mory.mb=8192 mapreduce.reduce.java.opts='-Xmx7618M'" \
-- \
--date 2020-08-30  --maxNe ghbors 100 --m nAct veFollo rs 400 --topK 20000000 --numNodesPerCommun y 200  --maxEpochs 4 --square  ghtsEnable --wtCoeff 10.0 \
-- nputS msD r /atla/proc/user/cassowary/manhattan_sequence_f les/approx mate_cos ne_s m lar y_follow  \
-- nputPrev ousKnownForDataSet /user/cassowary/adhoc/y _ldap/s mclusters/cluster ng_outputs/output_cluster ng_ass gn nts_2020_readAga n_v4_ ek_1_KeyVal \
--outputClusterD r /user/cassowary/adhoc/y _ldap/s mclusters/cluster ng_outputs/output_cluster ng_ass gn nts_2020_readAga n_v4_ ek_2
 */

object UpdateKnownFor20M145K2020Adhoc extends Tw terExecut onApp {
   mpl c  val tz: java.ut l.T  Zone = DateOps.UTC
   mpl c  val dp = DateParser.default
  val log = Logger()

  def job: Execut on[Un ] =
    Execut on.getConf gMode.flatMap {
      case (conf g, mode) =>
        Execut on.w h d {  mpl c  un que d =>
          val args = conf g.getArgs

           mpl c  def valueCodec: B naryScalaCodec[Cand dates] = B naryScalaCodec(Cand dates)
          // Step - 1 (DataProcess ng): Para ters for gett ng mapped  nd ces for user- ds
          val m nAct veFollo rs = args. nt("m nAct veFollo rs", 400)
          val topK = args. nt("topK", 20000000)

          // Step - 2 (DataProcess ng): Para ters to remove users not  n t  topK most follo d users from s msGraph
          val clusterAss gn ntOutput = args("outputClusterD r")
          val maxNe ghbors = args. nt("maxNe ghbors", 400)

          // Step - 3 (F nal Cluster ng): Para ters to run t  cluster ng algor hm
          val square  ghtsEnable = args.boolean("square  ghtsEnable")

          val maxEpochsForCluster ng = args. nt("maxEpochs", 3)
          val wtCoeff = args.double("wtCoeff", 10.0)

          val s msGraphPath =
            "/atla/proc/user/cassowary/manhattan_sequence_f les/approx mate_cos ne_s m lar y_follow"
          // Read  n t  knownFor dataset, that can be used to  n  al ze t  clusters for t   ek.
          val  nputPrev ousKnownFor: TypedP pe[(Long, Array[( nt, Float)])] =
            args.opt onal(" nputPrev ousKnownForDataSet") match {
              case So ( nputKnownForD r) =>
                pr ntln(
                  " nput knownFors prov ded, us ng t se as t   n  al cluster ass gn nts for users")
                TypedP pe
                  .from(AdhocKeyValS ces.knownForSBFResultsDevelS ce( nputKnownForD r))
              case None =>
                pr ntln(
                  "Us ng knownFor Ass gn nts from prod as no prev ous ass gn nt was prov ded  n t   nput")
                 f (args.boolean("dec11")) {
                  KnownForS ces
                    .fromKeyVal(
                      DAL
                        .readMostRecentSnapshotNoOlderThan(
                          S mclustersV2KnownFor20M145KDec11ScalaDataset,
                          Days(30)).w hRemoteReadPol cy(AllowCrossClusterSa DC).toTypedP pe,
                      ModelVers ons.Model20M145KDec11
                    )
                } else {
                  KnownForS ces
                    .fromKeyVal(
                      DAL
                        .readMostRecentSnapshotNoOlderThan(
                          S mclustersV2KnownFor20M145KUpdatedScalaDataset,
                          Days(30)).w hRemoteReadPol cy(AllowCrossClusterSa DC).toTypedP pe,
                      ModelVers ons.Model20M145KUpdated
                    )
                }
            }
          UpdateKnownForSBFRunner
            .runUpdateKnownFor(
              TypedP pe
                .from(Follow ngsCos neS m lar  esManhattanS ce(s msGraphPath))
                .map(_._2),
              m nAct veFollo rs,
              topK,
              maxNe ghbors,
              clusterAss gn ntOutput,
               nputPrev ousKnownFor,
              maxEpochsForCluster ng,
              square  ghtsEnable,
              wtCoeff,
              mode
            )
            .flatMap { updateKnownFor =>
              Execut on
                .z p(
                  updateKnownFor
                    .mapValues(_.toL st).wr eExecut on(TypedTsv(clusterAss gn ntOutput)),
                  updateKnownFor.wr eExecut on(AdhocKeyValS ces.knownForSBFResultsDevelS ce(
                    clusterAss gn ntOutput + "_KeyVal")),
                  UpdateKnownForSBFRunner
                    .evaluateUpdatedKnownFor(updateKnownFor,  nputPrev ousKnownFor)
                    .flatMap { ema lText =>
                      Ut l
                        .sendEma l(
                          ema lText,
                          s"Change  n cluster ass gn nts for new KnownFor ModelVers on: 20M145K2020" + clusterAss gn ntOutput,
                          "no-reply@tw ter.com")
                      Execut on.un 
                    }
                ).un 
            }
        }
    }
}
