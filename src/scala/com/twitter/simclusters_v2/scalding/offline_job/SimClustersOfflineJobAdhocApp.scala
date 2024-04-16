package com.tw ter.s mclusters_v2.scald ng.offl ne_job

 mport com.tw ter.scald ng._
 mport com.tw ter.scald ng_ nternal.job.Tw terExecut onApp
 mport com.tw ter.s mclusters_v2.common.ModelVers ons
 mport com.tw ter.s mclusters_v2.hdfs_s ces.AdhocKeyValS ces
 mport com.tw ter.s mclusters_v2.hdfs_s ces.ClusterTopKT etsH lySuff xS ce
 mport com.tw ter.s mclusters_v2.hdfs_s ces.T etClusterScoresH lySuff xS ce
 mport com.tw ter.s mclusters_v2.hdfs_s ces.T etTopKClustersH lySuff xS ce
 mport com.tw ter.s mclusters_v2.scald ng.common.Ut l
 mport com.tw ter.s mclusters_v2.scald ng.offl ne_job.S mClustersOffl neJob._
 mport com.tw ter.s mclusters_v2.thr ftscala.ClustersUser s nterested n
 mport java.ut l.T  Zone

/**
scald ng remote run --target src/scala/com/tw ter/s mclusters_v2/scald ng/offl ne_job:s mclusters_offl ne_job-adhoc \
--user cassowary \
--subm ter hadoopnest2.atla.tw ter.com \
--ma n-class com.tw ter.s mclusters_v2.scald ng.offl ne_job.S mClustersOffl neJobAdhocApp -- \
--date 2019-08-10 --batch_h s 24 \
--output_d r /user/cassowary/y _ldap/offl ne_s mcluster_20190810
--model_vers on 20M_145K_updated
 */
object S mClustersOffl neJobAdhocApp extends Tw terExecut onApp {

   mport S mClustersOffl neJobUt l._
   mport com.tw ter.s mclusters_v2.scald ng.common.TypedR chP pe._

  overr de def job: Execut on[Un ] =
    Execut on.w h d {  mpl c  un que d =>
      Execut on.w hArgs { args: Args =>
        // requ red
        val wholeDateRange: DateRange = DateRange.parse(args.l st("date"))
        val batchS ze: Durat on = H s(args. nt("batch_h s"))

        val outputD r = args("output_d r")

        val modelVers on = args.getOrElse("model_vers on", ModelVers ons.Model20M145KUpdated)

        val scor ng thod = args.getOrElse("score", "logFav")

        val t etClusterScoreOutputPath: Str ng = outputD r + "/t et_cluster_scores"

        val t etTopKClustersOutputPath: Str ng = outputD r + "/t et_top_k_clusters"

        val clusterTopKT etsOutputPath: Str ng = outputD r + "/cluster_top_k_t ets"

        val full nterested nData: TypedP pe[(Long, ClustersUser s nterested n)] =
          args.opt onal(" nterested_ n_path") match {
            case So (d r) =>
              pr ntln("Load ng  nterested n from suppl ed path " + d r)
              TypedP pe.from(AdhocKeyValS ces. nterested nS ce(d r))
            case None =>
              pr ntln("Load ng product on  nterested n data")
              read nterested nScalaDataset(wholeDateRange)
          }

        val  nterested nData: TypedP pe[(Long, ClustersUser s nterested n)] =
          full nterested nData.f lter(_._2.knownForModelVers on == modelVers on)

        val debugExec = Execut on.z p(
          full nterested nData.pr ntSummary("full nterested n", numRecords = 20),
           nterested nData.pr ntSummary(" nterested n", numRecords = 20)
        )

        // recurs ve funct on to calculate batc s one by one
        def runBatch(batchDateRange: DateRange): Execut on[Un ] = {
           f (batchDateRange.start.t  stamp > wholeDateRange.end.t  stamp) {
            Execut on.un  // stops  re
          } else {

            val prev ousScores =  f (batchDateRange.start == wholeDateRange.start) {
              TypedP pe.from(N l)
            } else {
              TypedP pe.from(
                T etClusterScoresH lySuff xS ce(
                  t etClusterScoreOutputPath,
                  batchDateRange - batchS ze
                )
              )
            }

            val latestScores = computeAggregatedT etClusterScores(
              batchDateRange,
               nterested nData,
              readT  l neFavor eData(batchDateRange),
              prev ousScores
            )

            val wr eLatestScoresExecut on = {
              Execut on.z p(
                latestScores.pr ntSummary(na  = "T etEnt yScores"),
                latestScores
                  .wr eExecut on(
                    T etClusterScoresH lySuff xS ce(
                      t etClusterScoreOutputPath,
                      batchDateRange
                    )
                  )
              )
            }

            val computeT etTopKExecut on = {
              val t etTopK = computeT etTopKClusters(latestScores)
              Execut on.z p(
                t etTopK.pr ntSummary(na  = "T etTopK"),
                t etTopK.wr eExecut on(
                  T etTopKClustersH lySuff xS ce(t etTopKClustersOutputPath, batchDateRange)
                )
              )
            }

            val computeClusterTopKExecut on = {
              val clusterTopK = computeClusterTopKT ets(latestScores)
              Execut on.z p(
                clusterTopK.pr ntSummary(na  = "ClusterTopK"),
                clusterTopK.wr eExecut on(
                  ClusterTopKT etsH lySuff xS ce(clusterTopKT etsOutputPath, batchDateRange)
                )
              )
            }

            Execut on
              .z p(
                wr eLatestScoresExecut on,
                computeT etTopKExecut on,
                computeClusterTopKExecut on
              ).flatMap { _ =>
                // run next batch
                runBatch(batchDateRange + batchS ze)
              }
          }
        }

        // start from t  f rst batch
        Ut l.pr ntCounters(
          Execut on.z p(
            debugExec,
            runBatch(
              DateRange(wholeDateRange.start, wholeDateRange.start + batchS ze - M ll secs(1)))
          )
        )
      }
    }
}

/**
For example:
scald ng remote run --target src/scala/com/tw ter/s mclusters_v2/scald ng/offl ne_job:dump_cluster_topk_job-adhoc \
--user cassowary
--ma n-class com.tw ter.s mclusters_v2.scald ng.offl ne_job.DumpClusterTopKT etsAdhoc \
--subm ter hadoopnest2.atla.tw ter.com -- \
--date 2019-08-03 \
--clusterTopKT etsPath /atla/proc3/user/cassowary/processed/s mclusters/cluster_top_k_t ets/ \
--clusters 4446

 */
object DumpClusterTopKT etsAdhoc extends Tw terExecut onApp {

   mpl c  val t  Zone: T  Zone = DateOps.UTC
   mpl c  val dateParser: DateParser = DateParser.default

   mport com.tw ter.s mclusters_v2.scald ng.common.TypedR chP pe._
   mport com.tw ter.s mclusters_v2.summ ngb rd.common.Thr ftDecayedValueMono d._

  overr de def job: Execut on[Un ] =
    Execut on.w h d {  mpl c  un que d =>
      Execut on.w hArgs { args: Args =>
        val date = DateRange.parse(args.l st("date"))
        val path = args("clusterTopKT etsPath")
        val  nput = TypedP pe.from(ClusterTopKT etsH lySuff xS ce(path, date))
        val clusters = args.l st("clusters").map(_.to nt).toSet

        val dvm = S mClustersOffl neJobUt l.thr ftDecayedValueMono d
         f (clusters. sEmpty) {
           nput.pr ntSummary("Cluster top k t ets")
        } else {
           nput
            .collect {
              case rec  f clusters.conta ns(rec.cluster d) =>
                val res = rec.topKT ets
                  .mapValues { x =>
                    x.score
                      .map { y =>
                        val enr c d = new Enr c dThr ftDecayedValue(y)(dvm)
                        enr c d.decayToT  stamp(date.end.t  stamp).value
                      }.getOrElse(0.0)
                  }.toL st.sortBy(-_._2)
                rec.cluster d + "\t" + Ut l.prettyJsonMapper
                  .wr eValueAsStr ng(res).replaceAll("\n", " ")
            }
            .to erableExecut on
            .map { str ngs => pr ntln(str ngs.mkStr ng("\n")) }
        }
      }
    }
}
