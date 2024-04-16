package com.tw ter.s mclusters_v2.scald ng.embedd ng

 mport com.tw ter.onboard ng.relevance.cand dates.thr ftscala. nterestBasedUserRecom ndat ons
 mport com.tw ter.onboard ng.relevance.cand dates.thr ftscala.UTT nterest
 mport com.tw ter.onboard ng.relevance.s ce.UttAccountRecom ndat onsScalaDataset
 mport com.tw ter.scald ng.Args
 mport com.tw ter.scald ng.DateRange
 mport com.tw ter.scald ng.Days
 mport com.tw ter.scald ng.Durat on
 mport com.tw ter.scald ng.Execut on
 mport com.tw ter.scald ng.R chDate
 mport com.tw ter.scald ng.Un que D
 mport com.tw ter.scald ng.typed.TypedP pe
 mport com.tw ter.scald ng.typed.UnsortedGrouped
 mport com.tw ter.scald ng_ nternal.dalv2.DAL
 mport com.tw ter.scald ng_ nternal.dalv2.DALWr e._
 mport com.tw ter.scald ng_ nternal.dalv2.remote_access.Expl c Locat on
 mport com.tw ter.scald ng_ nternal.dalv2.remote_access.ProcAtla
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal
 mport com.tw ter.s mclusters_v2.common.ModelVers ons
 mport com.tw ter.s mclusters_v2.common.S mClustersEmbedd ng
 mport com.tw ter.s mclusters_v2.hdfs_s ces.AdhocKeyValS ces
 mport com.tw ter.s mclusters_v2.hdfs_s ces.ProducerEmbedd ngS ces
 mport com.tw ter.s mclusters_v2.hdfs_s ces.Semant cCoreEmbedd ngsFromProducerScalaDataset
 mport com.tw ter.s mclusters_v2.scald ng.embedd ng.common.Embedd ngUt l._
 mport com.tw ter.s mclusters_v2.thr ftscala
 mport com.tw ter.s mclusters_v2.thr ftscala.Embedd ngType
 mport com.tw ter.s mclusters_v2.thr ftscala. nternal d
 mport com.tw ter.s mclusters_v2.thr ftscala.ModelVers on
 mport com.tw ter.s mclusters_v2.thr ftscala.S mClusterW hScore
 mport com.tw ter.s mclusters_v2.thr ftscala.S mClustersEmbedd ng d
 mport com.tw ter.s mclusters_v2.thr ftscala.TopS mClustersW hScore
 mport com.tw ter.wtf.scald ng.jobs.common.AdhocExecut onApp
 mport com.tw ter.wtf.scald ng.jobs.common.Sc duledExecut onApp
 mport com.tw ter.wtf.scald ng.jobs.common.StatsUt l._
 mport java.ut l.T  Zone

/*
  $ ./bazel bundle src/scala/com/tw ter/s mclusters_v2/scald ng/embedd ng:ent y_embedd ng_from_producer_embedd ng-adhoc

  $ scald ng remote run \
  --ma n-class com.tw ter.s mclusters_v2.scald ng.embedd ng.Ent yEmbedd ngFromProducerEmbedd ngAdhocJob \
  --target src/scala/com/tw ter/s mclusters_v2/scald ng/embedd ng:ent y_embedd ng_from_producer_embedd ng-adhoc \
  --user recos-platform \
  -- --date 2019-10-23 --model_vers on 20M_145K_updated
 */
object Ent yEmbedd ngFromProducerEmbedd ngAdhocJob extends AdhocExecut onApp {
  overr de def runOnDateRange(
    args: Args
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): Execut on[Un ] = {
    // step 1: read  n (ent y, producer) pa rs and remove dupl cates
    val topK = args.getOrElse("top_k", "100").to nt

    val modelVers on = ModelVers ons.toModelVers on(
      args.getOrElse("model_vers on", ModelVers ons.Model20M145KUpdated))

    val ent yKnownForProducers =
      Ent yEmbedd ngFromProducerEmbedd ngJob
        .getNormal zedEnt yProducerMatr x(dateRange.emb ggen(Days(7)))
        .count("num un que ent y producer pa rs").map {
          case (ent y d, producer d, score) => (producer d, (ent y d, score))
        }

    // step 2: read  n producer to s mclusters embedd ngs

    val producersEmbedd ngsFollowBased =
      ProducerEmbedd ngS ces.producerEmbedd ngS ceLegacy(
        Embedd ngType.ProducerFollowBasedSemant cCoreEnt y,
        modelVers on)(dateRange.emb ggen(Days(7)))

    val producersEmbedd ngsFavBased =
      ProducerEmbedd ngS ces.producerEmbedd ngS ceLegacy(
        Embedd ngType.ProducerFavBasedSemant cCoreEnt y,
        modelVers on)(dateRange.emb ggen(Days(7)))

    // step 3: jo n producer embedd ng w h ent y, producer pa rs and reformat result  nto format [S mClustersEmbedd ng d, S mClustersEmbedd ng]
    val producerBasedEnt yEmbedd ngsFollowBased =
      Ent yEmbedd ngFromProducerEmbedd ngJob
        .computeEmbedd ng(
          producersEmbedd ngsFollowBased,
          ent yKnownForProducers,
          topK,
          modelVers on,
          Embedd ngType.ProducerFollowBasedSemant cCoreEnt y).toTypedP pe.count(
          "follow_based_ent y_count")

    val producerBasedEnt yEmbedd ngsFavBased =
      Ent yEmbedd ngFromProducerEmbedd ngJob
        .computeEmbedd ng(
          producersEmbedd ngsFavBased,
          ent yKnownForProducers,
          topK,
          modelVers on,
          Embedd ngType.ProducerFavBasedSemant cCoreEnt y).toTypedP pe.count(
          "fav_based_ent y_count")

    val producerBasedEnt yEmbedd ngs =
      producerBasedEnt yEmbedd ngsFollowBased ++ producerBasedEnt yEmbedd ngsFavBased

    // step 4 wr e results to f le
    producerBasedEnt yEmbedd ngs
      .count("total_count").wr eExecut on(
        AdhocKeyValS ces.ent yToClustersS ce(
          getHdfsPath( sAdhoc = true,  sManhattanKeyVal = true, modelVers on, "producer")))
  }

}

/*
 $ ./bazel bundle src/scala/com/tw ter/s mclusters_v2/scald ng/embedd ng:ent y_embedd ng_from_producer_embedd ng_job
 $ capesospy-v2 update \
  --bu ld_locally \
  --start_cron ent y_embedd ng_from_producer_embedd ng_job src/scala/com/tw ter/s mclusters_v2/capesos_conf g/atla_proc3.yaml
 */
object Ent yEmbedd ngFromProducerEmbedd ngSc duledJob extends Sc duledExecut onApp {
  overr de def f rstT  : R chDate = R chDate("2019-10-16")

  overr de def batch ncre nt: Durat on = Days(7)

  overr de def runOnDateRange(
    args: Args
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): Execut on[Un ] = {
    // parse args: modelVers on, topK
    val topK = args.getOrElse("top_k", "100").to nt
    // only support dec11 now s nce updated model  s not product on zed for producer embedd ng
    val modelVers on =
      ModelVers ons.toModelVers on(
        args.getOrElse("model_vers on", ModelVers ons.Model20M145KUpdated))

    val ent yKnownForProducers =
      Ent yEmbedd ngFromProducerEmbedd ngJob
        .getNormal zedEnt yProducerMatr x(dateRange.emb ggen(Days(7)))
        .count("num un que ent y producer pa rs").map {
          case (ent y d, producer d, score) => (producer d, (ent y d, score))
        }

    val favBasedEmbedd ngs = Ent yEmbedd ngFromProducerEmbedd ngJob
      .computeEmbedd ng(
        ProducerEmbedd ngS ces.producerEmbedd ngS ceLegacy(
          Embedd ngType.ProducerFavBasedSemant cCoreEnt y,
          modelVers on)(dateRange.emb ggen(Days(7))),
        ent yKnownForProducers,
        topK,
        modelVers on,
        Embedd ngType.ProducerFavBasedSemant cCoreEnt y
      ).toTypedP pe.count("follow_based_ent y_count")

    val followBasedEmbedd ngs = Ent yEmbedd ngFromProducerEmbedd ngJob
      .computeEmbedd ng(
        ProducerEmbedd ngS ces.producerEmbedd ngS ceLegacy(
          Embedd ngType.ProducerFollowBasedSemant cCoreEnt y,
          modelVers on)(dateRange.emb ggen(Days(7))),
        ent yKnownForProducers,
        topK,
        modelVers on,
        Embedd ngType.ProducerFollowBasedSemant cCoreEnt y
      ).toTypedP pe.count("fav_based_ent y_count")

    val embedd ng = favBasedEmbedd ngs ++ followBasedEmbedd ngs

    embedd ng
      .count("total_count")
      .map {
        case (embedd ng d, embedd ng) => KeyVal(embedd ng d, embedd ng)
      }.wr eDALVers onedKeyValExecut on(
        Semant cCoreEmbedd ngsFromProducerScalaDataset,
        D.Suff x(getHdfsPath( sAdhoc = false,  sManhattanKeyVal = true, modelVers on, "producer"))
      )

  }

}

pr vate object Ent yEmbedd ngFromProducerEmbedd ngJob {
  def computeEmbedd ng(
    producersEmbedd ngs: TypedP pe[(Long, TopS mClustersW hScore)],
    ent yKnownForProducers: TypedP pe[(Long, (Long, Double))],
    topK:  nt,
    modelVers on: ModelVers on,
    embedd ngType: Embedd ngType
  ): UnsortedGrouped[S mClustersEmbedd ng d, thr ftscala.S mClustersEmbedd ng] = {
    producersEmbedd ngs
      .hashJo n(ent yKnownForProducers).flatMap {
        case (_, (topS mClustersW hScore, (ent y d, producerScore))) => {
          val ent yEmbedd ng = topS mClustersW hScore.topClusters
          ent yEmbedd ng.map {
            case S mClusterW hScore(cluster d, score) =>
              (
                (
                  S mClustersEmbedd ng d(
                    embedd ngType,
                    modelVers on,
                     nternal d.Ent y d(ent y d)),
                  cluster d),
                score * producerScore)
          }
        }
      }.sumByKey.map {
        case ((embedd ng d, cluster d), clusterScore) =>
          (embedd ng d, (cluster d, clusterScore))
      }.group.sortedReverseTake(topK)(Order ng.by(_._2)).mapValues(S mClustersEmbedd ng
        .apply(_).toThr ft)
  }

  def getNormal zedEnt yProducerMatr x(
     mpl c  dateRange: DateRange
  ): TypedP pe[(Long, Long, Double)] = {
    val uttRecs: TypedP pe[(UTT nterest,  nterestBasedUserRecom ndat ons)] =
      DAL
        .readMostRecentSnapshot(UttAccountRecom ndat onsScalaDataset).w hRemoteReadPol cy(
          Expl c Locat on(ProcAtla)).toTypedP pe.map {
          case KeyVal( nterest, cand dates) => ( nterest, cand dates)
        }

    uttRecs
      .flatMap {
        case ( nterest, cand dates) => {
          // current populated features
          val top20Producers = cand dates.recom ndat ons.sortBy(-_.score.getOrElse(0.0d)).take(20)
          val producerScorePa rs = top20Producers.map { producer =>
            (producer.cand dateUser D, producer.score.getOrElse(0.0))
          }
          val scoreSum = producerScorePa rs.map(_._2).sum
          producerScorePa rs.map {
            case (producer d, score) => ( nterest.utt D, producer d, score / scoreSum)
          }
        }
      }
  }

}
