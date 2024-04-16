package com.tw ter.s mclusters_v2.scald ng.embedd ng

 mport com.tw ter.dal.cl ent.dataset.KeyValDALDataset
 mport com.tw ter.recos.ent  es.thr ftscala.Ent y
 mport com.tw ter.recos.ent  es.thr ftscala.Hashtag
 mport com.tw ter.recos.ent  es.thr ftscala.Semant cCoreEnt y
 mport com.tw ter.scald ng._
 mport com.tw ter.scald ng_ nternal.dalv2.DALWr e._
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal
 mport com.tw ter.s mclusters_v2.common.ModelVers ons
 mport com.tw ter.s mclusters_v2.common.S mClustersEmbedd ng
 mport com.tw ter.s mclusters_v2.hdfs_s ces.presto_hdfs_s ces._
 mport com.tw ter.s mclusters_v2.hdfs_s ces.AdhocKeyValS ces
 mport com.tw ter.s mclusters_v2.hdfs_s ces.Ent yEmbedd ngsS ces
 mport com.tw ter.s mclusters_v2.hdfs_s ces. nterested nS ces
 mport com.tw ter.s mclusters_v2.scald ng.embedd ng.LocaleEnt yS mClustersEmbedd ngsJob._
 mport com.tw ter.s mclusters_v2.scald ng.embedd ng.common.Embedd ngUt l
 mport com.tw ter.s mclusters_v2.scald ng.embedd ng.common.ExternalDataS ces
 mport com.tw ter.s mclusters_v2.scald ng.embedd ng.common.Embedd ngUt l._
 mport com.tw ter.s mclusters_v2.scald ng.embedd ng.common.Ent yEmbedd ngUt l._
 mport com.tw ter.s mclusters_v2.scald ng.embedd ng.common.S mClustersEmbedd ngJob._
 mport com.tw ter.s mclusters_v2.thr ftscala.{
  S mClustersEmbedd ng => Thr ftS mClustersEmbedd ng,
  _
}
 mport com.tw ter.wtf.ent y_real_graph.common.Ent yUt l
 mport com.tw ter.wtf.ent y_real_graph.thr ftscala.Edge
 mport com.tw ter.wtf.ent y_real_graph.thr ftscala.Ent yType
 mport com.tw ter.wtf.scald ng.jobs.common.AdhocExecut onApp
 mport com.tw ter.wtf.scald ng.jobs.common.DataS ces
 mport com.tw ter.wtf.scald ng.jobs.common.Sc duledExecut onApp
 mport java.ut l.T  Zone

/**
 * $ ./bazel bundle src/scala/com/tw ter/s mclusters_v2/scald ng/embedd ng:ent y_per_language_embedd ngs_job-adhoc
 *
 * ---------------------- Deploy to atla ----------------------
 * $ scald ng remote run \
  --ma n-class com.tw ter.s mclusters_v2.scald ng.embedd ng.LocaleEnt yS mClustersEmbedd ngAdhocApp \
  --target src/scala/com/tw ter/s mclusters_v2/scald ng/embedd ng:ent y_per_language_embedd ngs_job-adhoc \
  --user recos-platform \
  -- --date 2019-12-17 --model-vers on 20M_145K_updated --ent y-type Semant cCore
 */
object LocaleEnt yS mClustersEmbedd ngAdhocApp extends AdhocExecut onApp {

  //  mport  mpl c s

   mport Ent yUt l._

  def wr eOutput(
    embedd ngs: TypedP pe[(S mClustersEmbedd ng d, (Cluster d, Embedd ngScore))],
    topKEmbedd ngs: TypedP pe[(S mClustersEmbedd ng d, Seq[(Cluster d, Embedd ngScore)])],
    jobConf g: Ent yEmbedd ngsJobConf g
  ): Execut on[Un ] = {

    val toS mClusterEmbedd ngExec = topKEmbedd ngs
      .mapValues(S mClustersEmbedd ng.apply(_).toThr ft)
      .wr eExecut on(
        AdhocKeyValS ces.ent yToClustersS ce(
          LocaleEnt yS mClustersEmbedd ngsJob.getHdfsPath(
             sAdhoc = true,
             sManhattanKeyVal = true,
             sReverse ndex = false,
             sLogFav = false,
            jobConf g.modelVers on,
            jobConf g.ent yType)))

    val fromS mClusterEmbedd ngExec =
      toReverse ndexS mClusterEmbedd ng(embedd ngs, jobConf g.topK)
        .wr eExecut on(
          AdhocKeyValS ces.clusterToEnt  esS ce(
            LocaleEnt yS mClustersEmbedd ngsJob.getHdfsPath(
               sAdhoc = true,
               sManhattanKeyVal = true,
               sReverse ndex = true,
               sLogFav = false,
              jobConf g.modelVers on,
              jobConf g.ent yType)))

    Execut on.z p(toS mClusterEmbedd ngExec, fromS mClusterEmbedd ngExec).un 
  }

  overr de def runOnDateRange(
    args: Args
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): Execut on[Un ] = {

    val jobConf g = Ent yEmbedd ngsJobConf g(args,  sAdhoc = true)

    val numReducers = args.getOrElse("m", "2000").to nt

    /*
      Can use t  ERG da ly dataset  n t  adhoc job for qu ck prototyp ng, note that t re may be
       ssues w h scal ng t  job w n product on z ng on ERG aggregated dataset.
     */
    val userEnt yMatr x: TypedP pe[(User d, (Ent y, Double))] =
      getUserEnt yMatr x(
        jobConf g,
        DataS ces.ent yRealGraphAggregat onDataSetS ce(dateRange.emb ggen(Days(7))),
        So (ExternalDataS ces.uttEnt  esS ce())
      ).forceToD sk

    //determ ne wh ch data s ce to use based on model vers on
    val s mClustersS ce = jobConf g.modelVers on match {
      case ModelVers on.Model20m145kUpdated =>
         nterested nS ces.s mClusters nterested nUpdatedS ce(dateRange, t  Zone)
      case modelVers on =>
        throw new  llegalArgu ntExcept on(
          s"S mClusters model vers on not supported ${modelVers on.na }")
    }

    val ent yPerLanguage = userEnt yMatr x.jo n(ExternalDataS ces.userS ce).map {
      case (user d, ((ent y, score), (_, language))) =>
        ((ent y, language), (user d, score))
    }

    val normal zedUserEnt yMatr x =
      getNormal zedTranspose nputMatr x(ent yPerLanguage, numReducers = So (numReducers))

    val embedd ngs = computeEmbedd ngs[(Ent y, Str ng)](
      s mClustersS ce,
      normal zedUserEnt yMatr x,
      scoreExtractors,
      ModelVers on.Model20m145kUpdated,
      toS mClustersEmbedd ng d(jobConf g.modelVers on),
      numReducers = So (numReducers * 2)
    )

    val topKEmbedd ngs =
      embedd ngs.group
        .sortedReverseTake(jobConf g.topK)(Order ng.by(_._2))
        .w hReducers(numReducers)

    wr eOutput(embedd ngs, topKEmbedd ngs, jobConf g)
  }
}

/**
 * $ ./bazel bundle src/scala/com/tw ter/s mclusters_v2/scald ng/embedd ng:semant c_core_ent y_embedd ngs_per_language_job
 * $ capesospy-v2 update \
  --bu ld_locally \
  --start_cron semant c_core_ent y_embedd ngs_per_language_job src/scala/com/tw ter/s mclusters_v2/capesos_conf g/atla_proc3.yaml
 */
object LocaleEnt yS mClustersEmbedd ngSc duledApp extends Sc duledExecut onApp {

  //  mport  mpl c s

   mport Embedd ngUt l._
   mport Ent yUt l._

  overr de val f rstT  : R chDate = R chDate("2019-10-22")

  overr de val batch ncre nt: Durat on = Days(7)

  pr vate def wr eOutput(
    embedd ngs: TypedP pe[(S mClustersEmbedd ng d, (Cluster d, Embedd ngScore))],
    topKEmbedd ngs: TypedP pe[(S mClustersEmbedd ng d, Seq[(Cluster d, Embedd ngScore)])],
    jobConf g: Ent yEmbedd ngsJobConf g,
    clusterEmbedd ngsDataset: KeyValDALDataset[
      KeyVal[S mClustersEmbedd ng d, Thr ftS mClustersEmbedd ng]
    ],
    ent yEmbedd ngsDataset: KeyValDALDataset[KeyVal[S mClustersEmbedd ng d,  nternal dEmbedd ng]]
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone
  ): Execut on[Un ] = {

    val thr ftS mClustersEmbedd ng = topKEmbedd ngs
      .mapValues(S mClustersEmbedd ng.apply(_).toThr ft)

    val wr eS mClustersEmbedd ngKeyValDataset =
      thr ftS mClustersEmbedd ng
        .map {
          case (ent y d, topS mClusters) => KeyVal(ent y d, topS mClusters)
        }
        .wr eDALVers onedKeyValExecut on(
          clusterEmbedd ngsDataset,
          D.Suff x(
            LocaleEnt yS mClustersEmbedd ngsJob.getHdfsPath(
               sAdhoc = false,
               sManhattanKeyVal = true,
               sReverse ndex = false,
               sLogFav = false,
              jobConf g.modelVers on,
              jobConf g.ent yType))
        )

    val wr eS mClustersEmbedd ngDataset = thr ftS mClustersEmbedd ng
      .map {
        case (embedd ng d, embedd ng) => S mClustersEmbedd ngW h d(embedd ng d, embedd ng)
      }
      .wr eDALSnapshotExecut on(
        Semant cCorePerLanguageS mclustersEmbedd ngsPrestoScalaDataset,
        D.Da ly,
        D.Suff x(
          LocaleEnt yS mClustersEmbedd ngsJob.getHdfsPath(
             sAdhoc = false,
             sManhattanKeyVal = false,
             sReverse ndex = false,
             sLogFav = false,
            jobConf g.modelVers on,
            jobConf g.ent yType)),
        D.EBLzo(),
        dateRange.end
      )

    val thr ftReversedS mclustersEmbedd ngs =
      toReverse ndexS mClusterEmbedd ng(embedd ngs, jobConf g.topK)

    val wr eReverseS mClustersEmbedd ngKeyValDataset =
      thr ftReversedS mclustersEmbedd ngs
        .map {
          case (embedd ng d,  nternal dsW hScore) =>
            KeyVal(embedd ng d,  nternal dsW hScore)
        }
        .wr eDALVers onedKeyValExecut on(
          ent yEmbedd ngsDataset,
          D.Suff x(
            LocaleEnt yS mClustersEmbedd ngsJob.getHdfsPath(
               sAdhoc = false,
               sManhattanKeyVal = true,
               sReverse ndex = true,
               sLogFav = false,
              jobConf g.modelVers on,
              jobConf g.ent yType))
        )

    val wr eReverseS mClustersEmbedd ngDataset =
      thr ftReversedS mclustersEmbedd ngs
        .map {
          case (embedd ng d, embedd ng) =>  nternal dEmbedd ngW h d(embedd ng d, embedd ng)
        }.wr eDALSnapshotExecut on(
          Reverse ndexSemant cCorePerLanguageS mclustersEmbedd ngsPrestoScalaDataset,
          D.Da ly,
          D.Suff x(
            LocaleEnt yS mClustersEmbedd ngsJob.getHdfsPath(
               sAdhoc = false,
               sManhattanKeyVal = false,
               sReverse ndex = true,
               sLogFav = false,
              jobConf g.modelVers on,
              jobConf g.ent yType)),
          D.EBLzo(),
          dateRange.end
        )

    Execut on
      .z p(
        wr eS mClustersEmbedd ngDataset,
        wr eS mClustersEmbedd ngKeyValDataset,
        wr eReverseS mClustersEmbedd ngDataset,
        wr eReverseS mClustersEmbedd ngKeyValDataset
      ).un 
  }

  overr de def runOnDateRange(
    args: Args
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): Execut on[Un ] = {

    val jobConf g = Ent yEmbedd ngsJobConf g(args,  sAdhoc = false)

    val embedd ngsDataset = Ent yEmbedd ngsS ces.getEnt yEmbedd ngsDataset(
      jobConf g.ent yType,
      ModelVers ons.toKnownForModelVers on(jobConf g.modelVers on),
       sEmbedd ngsPerLocale = true
    )

    val reverse ndexEmbedd ngsDataset =
      Ent yEmbedd ngsS ces.getReverse ndexedEnt yEmbedd ngsDataset(
        jobConf g.ent yType,
        ModelVers ons.toKnownForModelVers on(jobConf g.modelVers on),
         sEmbedd ngsPerLocale = true
      )

    val userEnt yMatr x: TypedP pe[(User d, (Ent y, Double))] =
      getUserEnt yMatr x(
        jobConf g,
        DataS ces.ent yRealGraphAggregat onDataSetS ce(dateRange.emb ggen(Days(7))),
        So (ExternalDataS ces.uttEnt  esS ce())
      ).forceToD sk

    //determ ne wh ch data s ce to use based on model vers on
    val s mClustersS ce = jobConf g.modelVers on match {
      case ModelVers on.Model20m145kUpdated =>
         nterested nS ces.s mClusters nterested nUpdatedS ce(dateRange, t  Zone)
      case modelVers on =>
        throw new  llegalArgu ntExcept on(
          s"S mClusters model vers on not supported ${modelVers on.na }")
    }

    val ent yPerLanguage = userEnt yMatr x.jo n(ExternalDataS ces.userS ce).map {
      case (user d, ((ent y, score), (_, language))) =>
        ((ent y, language), (user d, score))
    }

    val normal zedUserEnt yMatr x =
      getNormal zedTranspose nputMatr x(ent yPerLanguage, numReducers = So (3000))

    val s mClustersEmbedd ng = jobConf g.modelVers on match {
      case ModelVers on.Model20m145kUpdated =>
        computeEmbedd ngs(
          s mClustersS ce,
          normal zedUserEnt yMatr x,
          scoreExtractors,
          ModelVers on.Model20m145kUpdated,
          toS mClustersEmbedd ng d(ModelVers on.Model20m145kUpdated),
          numReducers = So (8000)
        )
      case modelVers on =>
        throw new  llegalArgu ntExcept on(
          s"S mClusters model vers on not supported ${modelVers on.na }")
    }

    val topKEmbedd ngs =
      s mClustersEmbedd ng.group.sortedReverseTake(jobConf g.topK)(Order ng.by(_._2))

    wr eOutput(
      s mClustersEmbedd ng,
      topKEmbedd ngs,
      jobConf g,
      embedd ngsDataset,
      reverse ndexEmbedd ngsDataset)
  }
}

object LocaleEnt yS mClustersEmbedd ngsJob {

  def getUserEnt yMatr x(
    jobConf g: Ent yEmbedd ngsJobConf g,
    ent yRealGraphS ce: TypedP pe[Edge],
    semant cCoreEnt y dsToKeep: Opt on[TypedP pe[Long]],
    applyLogTransform: Boolean = false
  ): TypedP pe[(User d, (Ent y, Double))] =
    jobConf g.ent yType match {
      case Ent yType.Semant cCore =>
        semant cCoreEnt y dsToKeep match {
          case So (ent y dsToKeep) =>
            getEnt yUserMatr x(ent yRealGraphS ce, jobConf g.halfL fe, Ent yType.Semant cCore)
              .map {
                case (ent y, (user d, score)) =>
                  ent y match {
                    case Ent y.Semant cCore(Semant cCoreEnt y(ent y d, _)) =>
                       f (applyLogTransform) {
                        (ent y d, (user d, (ent y, Math.log(score + 1))))
                      } else {
                        (ent y d, (user d, (ent y, score)))
                      }
                    case _ =>
                      throw new  llegalArgu ntExcept on(
                        "Job conf g spec f ed Ent yType.Semant cCore, but non-semant c core ent y was found.")
                  }
              }.hashJo n(ent y dsToKeep.asKeys).values.map {
                case ((user d, (ent y, score)), _) => (user d, (ent y, score))
              }
          case _ =>
            getEnt yUserMatr x(ent yRealGraphS ce, jobConf g.halfL fe, Ent yType.Semant cCore)
              .map { case (ent y, (user d, score)) => (user d, (ent y, score)) }
        }
      case Ent yType.Hashtag =>
        getEnt yUserMatr x(ent yRealGraphS ce, jobConf g.halfL fe, Ent yType.Hashtag)
          .map { case (ent y, (user d, score)) => (user d, (ent y, score)) }
      case _ =>
        throw new  llegalArgu ntExcept on(
          s"Argu nt [--ent y-type] must be prov ded. Supported opt ons [${Ent yType.Semant cCore.na }, ${Ent yType.Hashtag.na }]")
    }

  def toS mClustersEmbedd ng d(
    modelVers on: ModelVers on
  ): ((Ent y, Str ng), ScoreType.ScoreType) => S mClustersEmbedd ng d = {
    case ((Ent y.Semant cCore(Semant cCoreEnt y(ent y d, _)), lang), ScoreType.FavScore) =>
      S mClustersEmbedd ng d(
        Embedd ngType.FavBasedSemat cCoreEnt y,
        modelVers on,
         nternal d.LocaleEnt y d(LocaleEnt y d(ent y d, lang)))
    case ((Ent y.Semant cCore(Semant cCoreEnt y(ent y d, _)), lang), ScoreType.FollowScore) =>
      S mClustersEmbedd ng d(
        Embedd ngType.FollowBasedSemat cCoreEnt y,
        modelVers on,
         nternal d.LocaleEnt y d(LocaleEnt y d(ent y d, lang)))
    case ((Ent y.Semant cCore(Semant cCoreEnt y(ent y d, _)), lang), ScoreType.LogFavScore) =>
      S mClustersEmbedd ng d(
        Embedd ngType.LogFavBasedLocaleSemant cCoreEnt y,
        modelVers on,
         nternal d.LocaleEnt y d(LocaleEnt y d(ent y d, lang)))
    case ((Ent y.Hashtag(Hashtag(hashtag)), _), ScoreType.FavScore) =>
      S mClustersEmbedd ng d(
        Embedd ngType.FavBasedHashtagEnt y,
        modelVers on,
         nternal d.Hashtag(hashtag))
    case ((Ent y.Hashtag(Hashtag(hashtag)), _), ScoreType.FollowScore) =>
      S mClustersEmbedd ng d(
        Embedd ngType.FollowBasedHashtagEnt y,
        modelVers on,
         nternal d.Hashtag(hashtag))
    case (scoreType, ent y) =>
      throw new  llegalArgu ntExcept on(
        s"(ScoreType, Ent y) ($scoreType, ${ent y.toStr ng}) not supported")
  }

  /**
   * Generates t  output path for t  Ent y Embedd ngs Job.
   *
   * Example Adhoc: /user/recos-platform/processed/adhoc/s mclusters_embedd ngs/hashtag_per_language/model_20m_145k_updated
   * Example Prod: /atla/proc/user/cassowary/processed/s mclusters_embedd ngs/semant c_core_per_language/model_20m_145k_updated
   *
   */
  def getHdfsPath(
     sAdhoc: Boolean,
     sManhattanKeyVal: Boolean,
     sReverse ndex: Boolean,
     sLogFav: Boolean,
    modelVers on: ModelVers on,
    ent yType: Ent yType
  ): Str ng = {

    val reverse ndex =  f ( sReverse ndex) "reverse_ ndex/" else ""

    val logFav =  f ( sLogFav) "log_fav/" else ""

    val ent yTypeSuff x = ent yType match {
      case Ent yType.Semant cCore => "semant c_core_per_language"
      case Ent yType.Hashtag => "hashtag_per_language"
      case _ => "unknown_per_language"
    }

    val pathSuff x = s"$logFav$reverse ndex$ent yTypeSuff x"

    Embedd ngUt l.getHdfsPath( sAdhoc,  sManhattanKeyVal, modelVers on, pathSuff x)
  }
}
