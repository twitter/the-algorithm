package com.tw ter.s mclusters_v2.scald ng.embedd ng

 mport com.tw ter.dal.cl ent.dataset.KeyValDALDataset
 mport com.tw ter.dal.cl ent.dataset.SnapshotDALDataset
 mport com.tw ter.scald ng.DateRange
 mport com.tw ter.scald ng.Days
 mport com.tw ter.scald ng.Un que D
 mport com.tw ter.scald ng._
 mport com.tw ter.scald ng.typed.TypedP pe
 mport com.tw ter.scald ng_ nternal.dalv2.DALWr e.D
 mport com.tw ter.scald ng_ nternal.dalv2.DALWr e.Expl c EndT  
 mport com.tw ter.scald ng_ nternal.dalv2.DALWr e.Wr eExtens on
 mport com.tw ter.scald ng_ nternal.job.Requ redB naryComparators.ordSer
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal
 mport com.tw ter.s mclusters_v2.common.Country
 mport com.tw ter.s mclusters_v2.common.Language
 mport com.tw ter.s mclusters_v2.common.T  stamp
 mport com.tw ter.s mclusters_v2.common.T et d
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.s mclusters_v2.hdfs_s ces. nterested nS ces
 mport com.tw ter.s mclusters_v2.scald ng.embedd ng.common.ExternalDataS ces
 mport com.tw ter.s mclusters_v2.thr ftscala.ClustersUser s nterested n
 mport com.tw ter.s mclusters_v2.thr ftscala. nternal d.Cluster d
 mport com.tw ter.s mclusters_v2.thr ftscala.ModelVers on
 mport com.tw ter.s mclusters_v2.thr ftscala.UserTo nterested nClusterScores
 mport com.tw ter.wtf.scald ng.jobs.common.Sc duledExecut onApp
 mport com.tw ter.s mclusters_v2.hdfs_s ces.S mclustersV2GlobalLanguageEmbedd ngScalaDataset
 mport com.tw ter.s mclusters_v2.hdfs_s ces.S mclustersV2GlobalLanguageEmbedd ngThr ftScalaDataset
 mport com.tw ter.s mclusters_v2.thr ftscala.LanguageToClusters
 mport java.ut l.T  Zone

/**
capesospy-v2 update --bu ld_locally --start_cron \
  --start_cron global_s mclusters_language_embedd ng_job \
  src/scala/com/tw ter/s mclusters_v2/capesos_conf g/atla_proc.yaml
 */
object GlobalS mClustersLanguageEmbedd ngBatchApp extends Sc duledExecut onApp {

  overr de val f rstT  : R chDate = R chDate("2023-03-07")

  overr de val batch ncre nt: Durat on = Days(1)

  val outputHdfsD rectory =
    "/user/cassowary/manhattan_sequence_f les/global_s mclusters_language_embedd ngs"

  val outputThr ftHdfsD rectory =
    "/user/cassowary/processed/global_s mclusters_language_embedd ngs"

  val globalLanguageEmbedd ngsKeyValDataset: KeyValDALDataset[
    KeyVal[Str ng, ClustersUser s nterested n]
  ] = S mclustersV2GlobalLanguageEmbedd ngScalaDataset

  val globalLanguageEmbedd ngsThr ftDataset: SnapshotDALDataset[LanguageToClusters] =
    S mclustersV2GlobalLanguageEmbedd ngThr ftScalaDataset

  val numOfClustersPerLanguage:  nt = 400

  def get nterested nFn: (
    DateRange,
    T  Zone
  ) => TypedP pe[(User d, ClustersUser s nterested n)] =
     nterested nS ces.s mClusters nterested n2020S ce

  def flattenAndF lterUser nterested n(
     nterested n: TypedP pe[(User d, ClustersUser s nterested n)]
  ): TypedP pe[(User d, ( nt, Double))] = {
     nterested n
    // Get (user d, Seq[(cluster d, scores)]
      .map {
        case (user, clusterUser s nterested n) => {
          (user, clusterUser s nterested n.cluster dToScores)
        }
      }
      // Flatten    nto (User d, Cluster d, LogFavScore)
      .flatMap {
        case (user d, clusterUser s nterested n) => {
          clusterUser s nterested n.toSeq.map {
            case (cluster d, scores) => {
              (user d, (cluster d, scores.logFavScore.getOrElse(0.0)))
            }
          }
        }
      }.f lter(_._2._2 > 0.0) // F lter out zero scores
  }

  def getGlobalS mClustersEmbedd ngPerLanguage(
     nterested n: TypedP pe[(User d, ( nt, Double))],
    favEdges: TypedP pe[(User d, T et d, T  stamp)],
    language: TypedP pe[(User d, (Country, Language))]
  ): TypedP pe[(Language, ClustersUser s nterested n)] = {
    // Engage nt fav edges
    val edges = favEdges.map { case (user d, t et d, ts) => (user d, (t et d, ts)) }

    // Language  nformat on for users
    val userLanguage = language.map {
      case (user d, (country, lang)) => (user d, lang)
    }
    val numUsersPerLanguage = userLanguage.map {
      case (_, lang) => (lang, 1L)
    }.sumByKey

    val embedd ngs =
       nterested n
        .jo n(edges) // Jo n  nterested n and user-t et engage nts
        .map {
          case (user d, ((cluster d, score), (_, _))) => {
            (user d, (cluster d, score))
          }
        }
        .jo n(userLanguage) // Jo n and get cluster scores per language
        .map {
          case (user d, ((cluster d, score), lang)) => {
            ((lang, cluster d), score)
          }
        }
        .sumByKey // Sum t  user embedd ngs per language based on t  engage nts
        .map { case ((lang, cluster d), score) => (lang, (cluster d, score)) }
        .jo n(numUsersPerLanguage)
        //   compute t  average cluster scores per language
        .map {
          case (lang, ((cluster d, score), count)) => (lang, (cluster d -> score / count))
        }
        .group
        .sortedReverseTake(numOfClustersPerLanguage)(Order ng
          .by(_._2)) // Take top 400 clusters per language
        .flatMap {
          case (lang, clusterScores) => {
            clusterScores.map {
              case (cluster d, score) => (lang, (cluster d, score))
            }
          }
        }.mapValues { case (cluster d, score) => Map(cluster d -> score) }

    // Bu ld t  f nal S mClusters embedd ngs per language
    embedd ngs.sumByKey.map {
      case (lang, clusterToScore) => {
        val clusterScores = clusterToScore.map {
          case (cluster d, score) =>
            cluster d -> UserTo nterested nClusterScores(logFavScore = So (score))
        }
        (lang, ClustersUser s nterested n(ModelVers on.Model20m145k2020.na , clusterScores))
      }
    }
  }
  overr de def runOnDateRange(
    args: Args
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): Execut on[Un ] = {
    // Read t  most recent  nterested n snapshot from t  past 21 days
    val  nterested n =
       nterested nS ces
        .s mClusters nterested n2020S ce(dateRange.prepend(Days(21)), t  Zone).forceToD sk

    // Get t  user t et fav engage nt  tory from t  past 2 days
    val userT etFavEdges = ExternalDataS ces.userT etFavor esS ce

    // Read user language from UserS ce
    val userLanguages = ExternalDataS ces.userS ce

    val globalEmbedd ngs = getGlobalS mClustersEmbedd ngPerLanguage(
      flattenAndF lterUser nterested n( nterested n),
      userT etFavEdges,
      userLanguages)

    // Wr e results as a key-val dataset
    globalEmbedd ngs
      .map {
        case (lang, embedd ngs) =>
          KeyVal(lang, embedd ngs)
      }
      .wr eDALVers onedKeyValExecut on(
        globalLanguageEmbedd ngsKeyValDataset,
        D.Suff x(outputHdfsD rectory)
      )

    // Wr e results as a thr ft dataset
    globalEmbedd ngs
      .map {
        case (lang, clusterUser s nterested n) =>
          LanguageToClusters(
            lang,
            clusterUser s nterested n.knownForModelVers on,
            clusterUser s nterested n.cluster dToScores
          )
      }
      .wr eDALSnapshotExecut on(
        globalLanguageEmbedd ngsThr ftDataset,
        D.Da ly,
        D.Suff x(outputThr ftHdfsD rectory),
        D.Parquet,
        dateRange.`end`
      )
  }
}
