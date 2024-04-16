package com.tw ter.s mclusters_v2.scald ng.offl ne_job.adhoc

 mport com.tw ter.b ject on.{Bufferable,  nject on}
 mport com.tw ter.scald ng._
 mport com.tw ter.scald ng.commons.s ce.Vers onedKeyValS ce
 mport com.tw ter.scald ng_ nternal.dalv2.DAL
 mport com.tw ter.scald ng_ nternal.dalv2.remote_access.{Expl c Locat on, ProcAtla}
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal
 mport com.tw ter.s mclusters_v2.common.{Cluster d, T et d, User d}
 mport com.tw ter.s mclusters_v2.hdfs_s ces.S mclustersV2 nterested n20M145KUpdatedScalaDataset
 mport com.tw ter.s mclusters_v2.scald ng.common.matr x.{SparseMatr x, SparseRowMatr x}
 mport com.tw ter.s mclusters_v2.scald ng.offl ne_job.S mClustersOffl neJobUt l
 mport com.tw ter.s mclusters_v2.summ ngb rd.common.{Conf gs, S mClusters nterested nUt l}
 mport com.tw ter.s mclusters_v2.thr ftscala.ClustersUser s nterested n
 mport com.tw ter.wtf.scald ng.jobs.common.AdhocExecut onApp
 mport java.ut l.T  Zone

/**
 * Adhoc job for comput ng T et S mClusters embedd ngs.
 * T  output of t  job  ncludes two data sets: t et -> top clusters (or T et Embedd ng), and cluster -> top t ets.
 * T se data sets are supposed to be t  snapshot of t  two  ndex at t  end of t  dataRange   run.
 *
 * Note that   can also use t  output from S mClustersOffl neJobSc duledApp for analys s purpose.
 * T  outputs from that job m ght be more close to t  data   use  n product on.
 * T  benef  of hav ng t  job  s to keep t  flex b l y of exper  nt d fferent  deas.
 *
 *    s recom nded to put at least 2 days  n t  --date (dataRange  n t  code)  n order to make sure
 *   have enough engage nt data for t ets have more engage nts  n t  last 1+ days.
 *
 *
 * T re are several para ters to tune  n t  job. T y are expla ned  n t   nl ne com nts.
 *
 *
 * To run t  job:
    scald ng remote run \
    --target src/scala/com/tw ter/s mclusters_v2/scald ng/offl ne_job/adhoc:t et_embedd ng-adhoc \
    --user recos-platform \
    --reducers 1000 \
    --ma n-class com.tw ter.s mclusters_v2.scald ng.offl ne_job.adhoc.S mClustersT etEmbedd ngAdhocApp -- \
    --date 2021-01-27 2021-01-28 \
    --score_type logFav \
    --output_d r /user/recos-platform/adhoc/t et_embedd ng_01_27_28_unnormal zed_t9
 */
object S mClustersT etEmbedd ngAdhocApp extends AdhocExecut onApp {

   mport S mClustersOffl neJobUt l._

  overr de def runOnDateRange(
    args: Args
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): Execut on[Un ] = {

    val outputD r = args("output_d r")

    // what  nterested n score to use. logFav  s what   use  n product on
    val scor ng thod = args.getOrElse("score_type", "logFav")

    // w t r to use normal zed score  n t  cluster -> top t ets.
    // Currently,   do not do t   n product on. DONOT turn   on unless   know what   are do ng.
    // NOTE that for scald ng args, "--run_normal zed" w ll just set t  arg to be true, and
    // even   use "--run_normal zed false",   w ll st ll be true.
    val us ngNormal zedScor ngFunct on = args.boolean("run_normal zed")

    // f lter out t ets that has less than X favs  n t  dateRange.
    val t etFavThreshold = args.long("t et_fav_threshold", 0L)

    // t et -> top clusters w ll be saved  n t  subfolder
    val t etTopKClustersOutputPath: Str ng = outputD r + "/t et_top_k_clusters"

    // cluster -> top t ets w ll be saved  n t  subfolder
    val clusterTopKT etsOutputPath: Str ng = outputD r + "/cluster_top_k_t ets"

    val  nterested nData: TypedP pe[(Long, ClustersUser s nterested n)] =
      DAL
        .readMostRecentSnapshot(
          S mclustersV2 nterested n20M145KUpdatedScalaDataset,
          dateRange.emb ggen(Days(14))
        )
        .w hRemoteReadPol cy(Expl c Locat on(ProcAtla))
        .toTypedP pe
        .map {
          case KeyVal(key, value) => (key, value)
        }

    // read user-t et fav data. set t    ght to be a decayed value. t y w ll be decayed to t  dateRang.end
    val userT etFavData: SparseMatr x[User d, T et d, Double] =
      SparseMatr x(readT  l neFavor eData(dateRange)).tr pleApply {
        case (user d, t et d, t  stamp) =>
          (
            user d,
            t et d,
            thr ftDecayedValueMono d
              .plus(
                thr ftDecayedValueMono d.bu ld(1.0, t  stamp),
                thr ftDecayedValueMono d.bu ld(0.0, dateRange.end.t  stamp)
              )
              .value)
      }

    // f lter out t ets w hout x favs
    val t etSubset =
      userT etFavData.colNnz.f lter(
        _._2 > t etFavThreshold.toDouble
      ) // keep t ets w h at least x favs

    val userT etFavDataSubset = userT etFavData.f lterCols(t etSubset.keys)

    // construct user-s mclusters matr x
    val userS mClusters nterested nData: SparseRowMatr x[User d, Cluster d, Double] =
      SparseRowMatr x(
         nterested nData.map {
          case (user d, clusters) =>
            val topClustersW hScores =
              S mClusters nterested nUt l
                .topClustersW hScores(clusters)
                .collect {
                  case (cluster d, scores)
                       f scores.favScore > Conf gs
                        .favScoreThresholdForUser nterest(
                          clusters.knownForModelVers on
                        ) => // t   s t  sa  threshold used  n t  summ ngb rd job
                    scor ng thod match {
                      case "fav" =>
                        cluster d -> scores.clusterNormal zedFavScore
                      case "follow" =>
                        cluster d -> scores.clusterNormal zedFollowScore
                      case "logFav" =>
                        cluster d -> scores.clusterNormal zedLogFavScore
                      case _ =>
                        throw new  llegalArgu ntExcept on(
                          "score_type can only be fav, follow or logFav")
                    }
                }
                .f lter(_._2 > 0.0)
                .toMap
            user d -> topClustersW hScores
        },
         sSk nnyMatr x = true
      )

    // mult ply t et -> user matr x w h user -> cluster matr x to get t et -> cluster matr x
    val t etClusterScoreMatr x =  f (us ngNormal zedScor ngFunct on) {
      userT etFavDataSubset.transpose.rowL2Normal ze
        .mult plySk nnySparseRowMatr x(userS mClusters nterested nData)
    } else {
      userT etFavDataSubset.transpose.mult plySk nnySparseRowMatr x(
        userS mClusters nterested nData)
    }

    // get t  t et -> top clusters by tak ng top K  n each row
    val t etTopClusters = t etClusterScoreMatr x
      .sortW hTakePerRow(Conf gs.topKClustersPerT et)(Order ng.by(-_._2))
      .fork

    // get t  cluster -> top t ets by tak ng top K  n each colum
    val clusterTopT ets = t etClusterScoreMatr x
      .sortW hTakePerCol(Conf gs.topKT etsPerCluster)(Order ng.by(-_._2))
      .fork

    //  nject ons for sav ng a l st
     mpl c  val  nj1:  nject on[L st[( nt, Double)], Array[Byte]] =
      Bufferable. nject onOf[L st[( nt, Double)]]
     mpl c  val  nj2:  nject on[L st[(Long, Double)], Array[Byte]] =
      Bufferable. nject onOf[L st[(Long, Double)]]

    // save t  data sets and also output to so  tsv f les for eyeball ng t  results
    Execut on
      .z p(
        t etTopClusters
          .mapValues(_.toL st)
          .wr eExecut on(
            Vers onedKeyValS ce[T et d, L st[(Cluster d, Double)]](t etTopKClustersOutputPath)
          ),
        t etTopClusters
          .map {
            case (t et d, topKClusters) =>
              t et d -> topKClusters
                .map {
                  case (cluster d, score) =>
                    s"$cluster d:" + "%.3g".format(score)
                }
                .mkStr ng(",")
          }
          .wr eExecut on(
            TypedTsv(t etTopKClustersOutputPath + "_tsv")
          ),
        t etSubset.wr eExecut on(TypedTsv(t etTopKClustersOutputPath + "_t et_favs")),
        clusterTopT ets
          .mapValues(_.toL st)
          .wr eExecut on(
            Vers onedKeyValS ce[Cluster d, L st[(T et d, Double)]](clusterTopKT etsOutputPath)
          ),
        clusterTopT ets
          .map {
            case (cluster d, topKT ets) =>
              cluster d -> topKT ets
                .map {
                  case (t et d, score) => s"$t et d:" + "%.3g".format(score)
                }
                .mkStr ng(",")
          }
          .wr eExecut on(
            TypedTsv(clusterTopKT etsOutputPath + "_tsv")
          )
      )
      .un 
  }
}
