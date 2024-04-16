package com.tw ter.s mclusters_v2.scald ng.offl ne_job.adhoc

 mport com.tw ter.b ject on.{Bufferable,  nject on}
 mport com.tw ter.scald ng._
 mport com.tw ter.scald ng.commons.s ce.Vers onedKeyValS ce
 mport com.tw ter.s mclusters_v2.common.{Cluster d, Cos neS m lar yUt l, T et d}
 mport com.tw ter.s mclusters_v2.scald ng.common.matr x.SparseRowMatr x
 mport com.tw ter.s mclusters_v2.scald ng.offl ne_job.S mClustersOffl neJobUt l
 mport com.tw ter.wtf.scald ng.jobs.common.AdhocExecut onApp
 mport java.ut l.T  Zone

/**
 *
 * A job to sample so  t ets for evaluat on.
 *
 *   bucket t ets by t  log(# of fav + 1) and randomly p ck 1000 for each bucket for evaluat on.
 *
 * to run t  job:
 *
  scald ng remote run \
     --target src/scala/com/tw ter/s mclusters_v2/scald ng/offl ne_job/adhoc:t et_embedd ng_evaluat on_samples-adhoc \
     --user recos-platform \
     --reducers 1000 \
     --ma n-class com.tw ter.s mclusters_v2.scald ng.offl ne_job.adhoc.T etS m lar yEvaluat onSampl ngAdhocApp -- \
     --date 2021-01-27 2021-01-28 \
     --output /user/recos-platform/adhoc/t et_embedd ng_01_27_28_sample_t ets
 */
object T etS m lar yEvaluat onSampl ngAdhocApp extends AdhocExecut onApp {

  overr de def runOnDateRange(
    args: Args
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): Execut on[Un ] = {

    val random = new java.ut l.Random(args.long("seed", 20200322L))

    // # of t ets  n each bucket
    val topK = args. nt("bucket_s ze", 1000)

    val output = args("output")

    S mClustersOffl neJobUt l
      .readT  l neFavor eData(dateRange)
      .map {
        case (_, t et d, _) =>
          t et d -> 1L
      }
      .sumByKey
      .f lter(_._2 >= 10L) // only cons der t ets w h more than 10 favs
      .map {
        case (t et d, t etFavs) =>
          val bucket = math.log10(t etFavs + 1.0).to nt
          bucket -> (t et d, random.nextDouble())
      }
      .group
      .sortedReverseTake(topK)(Order ng.by(_._2))
      .flatMap {
        case (bucket, t ets) =>
          val bucketS ze = t ets.length
          t ets.map {
            case (t et d, _) =>
              (t et d, bucket, bucketS ze)
          }
      }
      .wr eExecut on(
        TypedTsv[(Long,  nt,  nt)](output)
      )

  }
}

/**
 *
 * A job for evaluat ng t  performance of an approx mate nearest ne ghbor search  thod w h a brute
 * force  thod.
 *
 * Evaluat on  thod:
 *
 * After gett ng t  embedd ngs for t se t ets,   bucket ze t ets based on t  number of favs t y have
 * ( .e., math.log10(numFavors).to nt), and t n randomly select 1000 t ets from each bucket.
 *   do not  nclude t ets w h fe r than 10 favs.   compute t  nearest ne ghbors ( n terms of cos ne s m lar y)
 * for t se t ets us ng t  brute force  thod and use up to top 100 ne ghbors w h t  cos ne
 * s m lar y score >0.8 for each t et as ground-truth set G.
 *
 *   t n compute t  nearest ne ghbors for t se t ets based on t  approx mate nearest ne ghbor search: for each t et,   f nd t  top clusters, and t n f nd top t ets  n each cluster as potent al cand dates.   rank t se potent al cand dates by t  cos ne s m lar y scores and take top 100 as pred ct on set P.   evaluate t  prec s on and recall us ng
 *
 * Prec s on = |P \ ntersect G| / |P|
 * Recall = |P \ ntersect G| / |G|
 *
 * Note that |P| and |G| can be d fferent, w n t re are not many ne ghbors returned.
 *
  scald ng remote run \
  --target src/scala/com/tw ter/s mclusters_v2/scald ng/offl ne_job/adhoc:t et_embedd ng_evaluat on-adhoc \
  --user recos-platform \
  --reducers 1000 \
  --ma n-class com.tw ter.s mclusters_v2.scald ng.offl ne_job.adhoc.T etS m lar yEvaluat onAdhocApp -- \
  --date 2021-01-27 \
  --t et_top_k /user/recos-platform/adhoc/t et_embedd ng_01_27_28_unnormal zed_t9/t et_top_k_clusters \
  --cluster_top_k /user/recos-platform/adhoc/t et_embedd ng_01_27_28_unnormal zed_t9/cluster_top_k_t ets \
  --t ets /user/recos-platform/adhoc/t et_embedd ng_01_27_28_sample_t ets \
  --output  /user/recos-platform/adhoc/t et_embedd ng_evaluat on_01_27_28_t05_k50_1
 */
object T etS m lar yEvaluat onAdhocApp extends AdhocExecut onApp {

   mpl c  val  nj1:  nject on[L st[( nt, Double)], Array[Byte]] =
    Bufferable. nject onOf[L st[( nt, Double)]]
   mpl c  val  nj2:  nject on[L st[(Long, Double)], Array[Byte]] =
    Bufferable. nject onOf[L st[(Long, Double)]]

  // Take top 20 cand dates, t  score * 100
  pr vate def formatL st(cand dates: Seq[(T et d, Double)]): Seq[(T et d,  nt)] = {
    cand dates.take(10).map {
      case (cluster d, score) =>
        (cluster d, (score * 100).to nt)
    }
  }

  overr de def runOnDateRange(
    args: Args
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): Execut on[Un ] = {

    // path to read t  t et -> top cluster data set. should be t  sa  from t  S mClustersT etEmbedd ngAdhocApp job
    val t etTopKClustersPath = args("t et_top_k")

    // path to read t  cluster -> top t ets data set. should be t  sa  from t  S mClustersT etEmbedd ngAdhocApp job
    val clusterTopKT etsPath = args("cluster_top_k")

    // path to read t  sampled t ets, should be t  sa  from T etS m lar yEvaluat onSampl ngAdhocApp
    val t etsPath = args("t ets")

    // see t  com nt of t  class. t   s to determ ne wh ch t et should be ground truth
    val threshold = args.double("threshold", 0.8)

    // see t  com nt of t  class. t   s to determ ne wh ch t et should be ground truth
    val topK = args. nt("topK", 100)

    // output path for evaluat on results
    val output = args("output")

    // read t et -> top clusters data set
    val t etTopKClusters: SparseRowMatr x[T et d, Cluster d, Double] =
      SparseRowMatr x(
        TypedP pe
          .from(
            Vers onedKeyValS ce[T et d, L st[(Cluster d, Double)]](t etTopKClustersPath)
          )
          .mapValues(_.f lter(_._2 > 0.001).toMap),
         sSk nnyMatr x = true
      ).rowL2Normal ze

    // read cluster -> top t ets data set
    val clusterTopT ets: SparseRowMatr x[Cluster d, T et d, Double] =
      SparseRowMatr x(
        TypedP pe
          .from(
            Vers onedKeyValS ce[Cluster d, L st[(T et d, Double)]](clusterTopKT etsPath)
          )
          .mapValues(_.f lter(_._2 > 0.02).toMap),
         sSk nnyMatr x = false
      )

    // read t  sampled t ets from T etS m lar yEvaluat onSampl ngAdhocApp
    val t etSubset = TypedP pe.from(TypedTsv[(Long,  nt,  nt)](t etsPath))

    // t  t et -> top clusters for t  sampled t ets
    val t etEmbedd ngSubset =
      t etTopKClusters.f lterRows(t etSubset.map(_._1))

    // compute ground-truth top s m lar t ets for each sampled t ets.
    // for each sampled t ets,   compute t  r s m lar y w h every t ets  n t  t et -> top clusters data set.
    //   f lter out those w h s m lar y score smaller than t  threshold and keep top k as t  ground truth s m lar t ets
    val groundTruthData = t etTopKClusters.toSparseMatr x
      .mult plySk nnySparseRowMatr x(
        t etEmbedd ngSubset.toSparseMatr x.transpose.toSparseRowMatr x(true),
        numReducersOpt = So (5000)
      )
      .toSparseMatr x
      .transpose
      .f lter((_, _, v) => v > threshold)
      .sortW hTakePerRow(topK)(Order ng.by(-_._2))

    // compute approx mate s m lar t ets for each sampled t ets.
    // t   s ach eved by mult ply ng "sampled_t ets -> top clusters" matr x w h "cluster -> top t ets" matr x.
    // note that  n t   mple ntat on,   f rst compute t  transponse of t  matr x  n order to ultl ze t  opt m zat on done on sk nny matr ces
    val pred ct onData = clusterTopT ets.toSparseMatr x.transpose
      .mult plySk nnySparseRowMatr x(
        t etEmbedd ngSubset.toSparseMatr x.transpose.toSparseRowMatr x(true),
        numReducersOpt = So (5000)
      )
      .toSparseMatr x
      .transpose
      .toTypedP pe
      .map {
        case (queryT et, cand dateT et, _) =>
          (queryT et, cand dateT et)
      }
      .jo n(t etEmbedd ngSubset.toTypedP pe)
      .map {
        case (query d, (cand date d, queryEmbedd ng)) =>
          cand date d -> (query d, queryEmbedd ng)
      }
      .jo n(t etTopKClusters.toTypedP pe)
      .map {
        case (cand date d, ((query d, queryEmbedd ng), cand dateEmbedd ng)) =>
          query d -> (cand date d, Cos neS m lar yUt l
            .dotProduct(
              queryEmbedd ng,
              cand dateEmbedd ng
            ))
      }
      .f lter(_._2._2 > threshold)
      .group
      .sortedReverseTake(topK)(Order ng.by(_._2))

    // Ex st  n Ground Truth but not ex st  n Pred cat on
    val potent alData =
      groundTruthData
        .leftJo n(pred ct onData)
        .map {
          case (t et d, (groundTruthCand dates, pred ctedCand dates)) =>
            val pred ctedCand dateSet = pred ctedCand dates.toSeq.flatten.map(_._1).toSet
            val potent alT ets = groundTruthCand dates.f lterNot {
              case (cand date d, _) =>
                pred ctedCand dateSet.conta ns(cand date d)
            }
            (t et d, potent alT ets)
        }

    val debugg ngData =
      groundTruthData
        .leftJo n(pred ct onData)
        .map {
          case (t et d, (groundTruthT ets, maybepred ctedT ets)) =>
            val pred ctedT ets = maybepred ctedT ets.toSeq.flatten
            val pred ctedT etSet = pred ctedT ets.map(_._1).toSet
            val potent alT ets = groundTruthT ets.f lterNot {
              case (cand date d, _) =>
                pred ctedT etSet.conta ns(cand date d)
            }

            (
              t et d,
              Seq(
                formatL st(potent alT ets),
                formatL st(groundTruthT ets),
                formatL st(pred ctedT ets)))
        }

    // for each t et, compare t  approx mate topk and ground-truth topk.
    // compute prec s on and recall, t n averag ng t m per bucket.
    val eval = t etSubset
      .map {
        case (t et d, bucket, bucketS ze) =>
          t et d -> (bucket, bucketS ze)
      }
      .leftJo n(groundTruthData)
      .leftJo n(pred ct onData)
      .map {
        case (_, (((bucket, bucketS ze), groundTruthOpt), pred ct onOpt)) =>
          val groundTruth = groundTruthOpt.getOrElse(N l).map(_._1)
          val pred ct on = pred ct onOpt.getOrElse(N l).map(_._1)

          assert(groundTruth.d st nct.s ze == groundTruth.s ze)
          assert(pred ct on.d st nct.s ze == pred ct on.s ze)

          val  ntersect on = groundTruth.toSet. ntersect(pred ct on.toSet)

          val prec s on =
             f (pred ct on.nonEmpty)
               ntersect on.s ze.toDouble / pred ct on.s ze.toDouble
            else 0.0
          val recall =
             f (groundTruth.nonEmpty)
               ntersect on.s ze.toDouble / groundTruth.s ze.toDouble
            else 0.0

          (
            bucket,
            bucketS ze) -> (groundTruth.s ze, pred ct on.s ze,  ntersect on.s ze, prec s on, recall, 1.0)
      }
      .sumByKey
      .map {
        case (
              (bucket, bucketS ze),
              (groundTruthSum, pred ct onSum,  nterSect onSum, prec s onSum, recallSum, count)) =>
          (
            bucket,
            bucketS ze,
            groundTruthSum / count,
            pred ct onSum / count,
             nterSect onSum / count,
            prec s onSum / count,
            recallSum / count,
            count)
      }

    // output t  eval results and so  sample results for eyeball ng
    Execut on
      .z p(
        eval
          .wr eExecut on(TypedTsv(output)),
        groundTruthData
          .map {
            case (t et d, ne ghbors) =>
              t et d -> ne ghbors
                .map {
                  case ( d, score) => s"$ d:$score"
                }
                .mkStr ng(",")
          }
          .wr eExecut on(
            TypedTsv(args("output") + "_ground_truth")
          ),
        pred ct onData
          .map {
            case (t et d, ne ghbors) =>
              t et d -> ne ghbors
                .map {
                  case ( d, score) => s"$ d:$score"
                }
                .mkStr ng(",")
          }
          .wr eExecut on(
            TypedTsv(args("output") + "_pred ct on")
          ),
        potent alData
          .map {
            case (t et d, ne ghbors) =>
              t et d -> ne ghbors
                .map {
                  case ( d, score) => s"$ d:$score"
                }
                .mkStr ng(",")
          }.wr eExecut on(
            TypedTsv(args("output") + "_potent al")
          ),
        debugg ngData
          .map {
            case (t et d, cand dateL st) =>
              val value = cand dateL st
                .map { cand dates =>
                  cand dates
                    .map {
                      case ( d, score) =>
                        s"${ d}D$score"
                    }.mkStr ng("C")
                }.mkStr ng("B")
              s"${t et d}A$value"
          }.wr eExecut on(
            TypedTsv(args("output") + "_debugg ng")
          )
      )
      .un 
  }
}
