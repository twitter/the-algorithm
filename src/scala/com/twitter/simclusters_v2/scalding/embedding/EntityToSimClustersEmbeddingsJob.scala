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
 mport com.tw ter.s mclusters_v2.hdfs_s ces._
 mport com.tw ter.s mclusters_v2.scald ng.embedd ng.common.Embedd ngUt l
 mport com.tw ter.s mclusters_v2.scald ng.embedd ng.common.Embedd ngUt l._
 mport com.tw ter.s mclusters_v2.scald ng.embedd ng.common.Ent yEmbedd ngUt l
 mport com.tw ter.s mclusters_v2.scald ng.embedd ng.common.S mClustersEmbedd ngJob
 mport com.tw ter.s mclusters_v2.thr ftscala.{
  S mClustersEmbedd ng => Thr ftS mClustersEmbedd ng,
  _
}
 mport com.tw ter.wtf.ent y_real_graph.common.Ent yUt l
 mport com.tw ter.wtf.ent y_real_graph.thr ftscala.Ent yType
 mport com.tw ter.wtf.scald ng.jobs.common.AdhocExecut onApp
 mport com.tw ter.wtf.scald ng.jobs.common.DataS ces
 mport com.tw ter.wtf.scald ng.jobs.common.Sc duledExecut onApp
 mport java.ut l.T  Zone

/**
 * $ ./bazel bundle src/scala/com/tw ter/s mclusters_v2/scald ng/embedd ng:ent y_embedd ngs_job-adhoc
 *
 * ---------------------- Deploy to atla ----------------------
 * $ scald ng remote run \
  --ma n-class com.tw ter.s mclusters_v2.scald ng.embedd ng.Ent yToS mClustersEmbedd ngAdhocApp \
  --target src/scala/com/tw ter/s mclusters_v2/scald ng/embedd ng:ent y_embedd ngs_job-adhoc \
  --user recos-platform \
  -- --date 2019-09-09 --model-vers on 20M_145K_updated --ent y-type Semant cCore
 */
object Ent yToS mClustersEmbedd ngAdhocApp extends AdhocExecut onApp {

   mport Embedd ngUt l._
   mport Ent yEmbedd ngUt l._
   mport Ent yToS mClustersEmbedd ngsJob._
   mport Ent yUt l._
   mport S mClustersEmbedd ngJob._

  def wr eOutput(
    embedd ngs: TypedP pe[(S mClustersEmbedd ng d, (Cluster d, Embedd ngScore))],
    topKEmbedd ngs: TypedP pe[(S mClustersEmbedd ng d, Seq[(Cluster d, Embedd ngScore)])],
    jobConf g: Ent yEmbedd ngsJobConf g
  ): Execut on[Un ] = {

    val toS mClusterEmbedd ngExec = topKEmbedd ngs
      .mapValues(S mClustersEmbedd ng.apply(_).toThr ft)
      .wr eExecut on(
        AdhocKeyValS ces.ent yToClustersS ce(
          Ent yToS mClustersEmbedd ngsJob.getHdfsPath(
             sAdhoc = true,
             sManhattanKeyVal = true,
             sReverse ndex = false,
            jobConf g.modelVers on,
            jobConf g.ent yType)))

    val fromS mClusterEmbedd ngExec =
      toReverse ndexS mClusterEmbedd ng(embedd ngs, jobConf g.topK)
        .wr eExecut on(
          AdhocKeyValS ces.clusterToEnt  esS ce(
            Ent yToS mClustersEmbedd ngsJob.getHdfsPath(
               sAdhoc = true,
               sManhattanKeyVal = true,
               sReverse ndex = true,
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

    val numReducers = args.getOrElse("m", "1000").to nt

    /*
      Us ng t  ERG da ly dataset  n t  adhoc job for qu ck prototyp ng, note that t re may be
       ssues w h scal ng t  job w n product on z ng on ERG aggregated dataset.
     */
    val ent yRealGraphS ce = DataS ces.ent yRealGraphDa lyDataSetS ce

    val ent yUserMatr x: TypedP pe[(Ent y, (User d, Double))] =
      (jobConf g.ent yType match {
        case Ent yType.Semant cCore =>
          getEnt yUserMatr x(ent yRealGraphS ce, jobConf g.halfL fe, Ent yType.Semant cCore)
        case Ent yType.Hashtag =>
          getEnt yUserMatr x(ent yRealGraphS ce, jobConf g.halfL fe, Ent yType.Hashtag)
        case _ =>
          throw new  llegalArgu ntExcept on(
            s"Argu nt [--ent y-type] must be prov ded. Supported opt ons [${Ent yType.Semant cCore.na }, ${Ent yType.Hashtag.na }]")
      }).forceToD sk

    val normal zedUserEnt yMatr x =
      getNormal zedTranspose nputMatr x(ent yUserMatr x, numReducers = So (numReducers))

    //determ ne wh ch data s ce to use based on model vers on
    val s mClustersS ce = jobConf g.modelVers on match {
      case ModelVers on.Model20m145kUpdated =>
         nterested nS ces.s mClusters nterested nUpdatedS ce(dateRange, t  Zone)
      case _ =>
         nterested nS ces.s mClusters nterested nDec11S ce(dateRange, t  Zone)
    }

    val embedd ngs = computeEmbedd ngs(
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
 * $ ./bazel bundle src/scala/com/tw ter/s mclusters_v2/scald ng/embedd ng:semant c_core_ent y_embedd ngs_2020_job
 * $ capesospy-v2 update \
  --bu ld_locally \
  --start_cron semant c_core_ent y_embedd ngs_2020_job src/scala/com/tw ter/s mclusters_v2/capesos_conf g/atla_proc3.yaml
 */
object Semant cCoreEnt yEmbedd ngs2020App extends Ent yToS mClustersEmbedd ngApp

tra  Ent yToS mClustersEmbedd ngApp extends Sc duledExecut onApp {

   mport Embedd ngUt l._
   mport Ent yEmbedd ngUt l._
   mport Ent yToS mClustersEmbedd ngsJob._
   mport Ent yUt l._
   mport S mClustersEmbedd ngJob._

  overr de val f rstT  : R chDate = R chDate("2023-01-01")

  overr de val batch ncre nt: Durat on = Days(7)

  pr vate def wr eOutput(
    embedd ngs: TypedP pe[(S mClustersEmbedd ng d, (Cluster d, Embedd ngScore))],
    topKEmbedd ngs: TypedP pe[(S mClustersEmbedd ng d, Seq[(Cluster d, Embedd ngScore)])],
    jobConf g: Ent yEmbedd ngsJobConf g,
    clusterEmbedd ngsDataset: KeyValDALDataset[
      KeyVal[S mClustersEmbedd ng d, Thr ftS mClustersEmbedd ng]
    ],
    ent yEmbedd ngsDataset: KeyValDALDataset[KeyVal[S mClustersEmbedd ng d,  nternal dEmbedd ng]]
  ): Execut on[Un ] = {

    val toS mClustersEmbedd ngs =
      topKEmbedd ngs
        .mapValues(S mClustersEmbedd ng.apply(_).toThr ft)
        .map {
          case (ent y d, topS mClusters) => KeyVal(ent y d, topS mClusters)
        }
        .wr eDALVers onedKeyValExecut on(
          clusterEmbedd ngsDataset,
          D.Suff x(
            Ent yToS mClustersEmbedd ngsJob.getHdfsPath(
               sAdhoc = false,
               sManhattanKeyVal = true,
               sReverse ndex = false,
              jobConf g.modelVers on,
              jobConf g.ent yType))
        )

    val fromS mClustersEmbedd ngs =
      toReverse ndexS mClusterEmbedd ng(embedd ngs, jobConf g.topK)
        .map {
          case (embedd ng d,  nternal dsW hScore) =>
            KeyVal(embedd ng d,  nternal dsW hScore)
        }
        .wr eDALVers onedKeyValExecut on(
          ent yEmbedd ngsDataset,
          D.Suff x(
            Ent yToS mClustersEmbedd ngsJob.getHdfsPath(
               sAdhoc = false,
               sManhattanKeyVal = true,
               sReverse ndex = true,
              jobConf g.modelVers on,
              jobConf g.ent yType))
        )

    Execut on.z p(toS mClustersEmbedd ngs, fromS mClustersEmbedd ngs).un 
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
      ModelVers ons.toKnownForModelVers on(jobConf g.modelVers on)
    )

    val reverse ndexEmbedd ngsDataset =
      Ent yEmbedd ngsS ces.getReverse ndexedEnt yEmbedd ngsDataset(
        jobConf g.ent yType,
        ModelVers ons.toKnownForModelVers on(jobConf g.modelVers on)
      )

    val ent yRealGraphS ce =
      DataS ces.ent yRealGraphAggregat onDataSetS ce(dateRange.emb ggen(Days(7)))

    val ent yUserMatr x: TypedP pe[(Ent y, (User d, Double))] =
      getEnt yUserMatr x(
        ent yRealGraphS ce,
        jobConf g.halfL fe,
        jobConf g.ent yType).forceToD sk

    val normal zedUserEnt yMatr x = getNormal zedTranspose nputMatr x(ent yUserMatr x)

    val s mClustersEmbedd ng = jobConf g.modelVers on match {
      case ModelVers on.Model20m145k2020 =>
        val s mClustersS ce2020 =
           nterested nS ces.s mClusters nterested n2020S ce(dateRange, t  Zone)
        computeEmbedd ngs(
          s mClustersS ce2020,
          normal zedUserEnt yMatr x,
          scoreExtractors,
          ModelVers on.Model20m145k2020,
          toS mClustersEmbedd ng d(ModelVers on.Model20m145k2020)
        )
      case modelVers on =>
        throw new  llegalArgu ntExcept on(s"Model Vers on ${modelVers on.na } not supported")
    }

    val topKEmbedd ngs =
      s mClustersEmbedd ng.group.sortedReverseTake(jobConf g.topK)(Order ng.by(_._2))

    val s mClustersEmbedd ngsExec =
      wr eOutput(
        s mClustersEmbedd ng,
        topKEmbedd ngs,
        jobConf g,
        embedd ngsDataset,
        reverse ndexEmbedd ngsDataset)

    //   don't support embedd ngsL e for t  2020 model vers on.
    val embedd ngsL eExec =  f (jobConf g.modelVers on == ModelVers on.Model20m145kUpdated) {
      topKEmbedd ngs
        .collect {
          case (
                S mClustersEmbedd ng d(
                  Embedd ngType.FavBasedSemat cCoreEnt y,
                  ModelVers on.Model20m145kUpdated,
                   nternal d.Ent y d(ent y d)),
                clustersW hScores) =>
            ent y d -> clustersW hScores
        }
        .flatMap {
          case (ent y d, clustersW hScores) =>
            clustersW hScores.map {
              case (cluster d, score) => Embedd ngsL e(ent y d, cluster d, score)
            }
          case _ => N l
        }.wr eDALSnapshotExecut on(
          S mclustersV2Embedd ngsL eScalaDataset,
          D.Da ly,
          D.Suff x(embedd ngsL ePath(ModelVers on.Model20m145kUpdated, "fav_based")),
          D.EBLzo(),
          dateRange.end)
    } else {
      Execut on.un 
    }

    Execut on
      .z p(s mClustersEmbedd ngsExec, embedd ngsL eExec).un 
  }
}

object Ent yToS mClustersEmbedd ngsJob {

  def toS mClustersEmbedd ng d(
    modelVers on: ModelVers on
  ): (Ent y, ScoreType.ScoreType) => S mClustersEmbedd ng d = {
    case (Ent y.Semant cCore(Semant cCoreEnt y(ent y d, _)), ScoreType.FavScore) =>
      S mClustersEmbedd ng d(
        Embedd ngType.FavBasedSemat cCoreEnt y,
        modelVers on,
         nternal d.Ent y d(ent y d))
    case (Ent y.Semant cCore(Semant cCoreEnt y(ent y d, _)), ScoreType.FollowScore) =>
      S mClustersEmbedd ng d(
        Embedd ngType.FollowBasedSemat cCoreEnt y,
        modelVers on,
         nternal d.Ent y d(ent y d))
    case (Ent y.Hashtag(Hashtag(hashtag)), ScoreType.FavScore) =>
      S mClustersEmbedd ng d(
        Embedd ngType.FavBasedHashtagEnt y,
        modelVers on,
         nternal d.Hashtag(hashtag))
    case (Ent y.Hashtag(Hashtag(hashtag)), ScoreType.FollowScore) =>
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
   * Example Adhoc: /user/recos-platform/processed/adhoc/s mclusters_embedd ngs/hashtag/model_20m_145k_updated
   * Example Prod: /atla/proc/user/cassowary/processed/s mclusters_embedd ngs/semant c_core/model_20m_145k_dec11
   *
   */
  def getHdfsPath(
     sAdhoc: Boolean,
     sManhattanKeyVal: Boolean,
     sReverse ndex: Boolean,
    modelVers on: ModelVers on,
    ent yType: Ent yType
  ): Str ng = {

    val reverse ndex =  f ( sReverse ndex) "reverse_ ndex/" else ""

    val ent yTypeSuff x = ent yType match {
      case Ent yType.Semant cCore => "semant c_core"
      case Ent yType.Hashtag => "hashtag"
      case _ => "unknown"
    }

    val pathSuff x = s"$reverse ndex$ent yTypeSuff x"

    Embedd ngUt l.getHdfsPath( sAdhoc,  sManhattanKeyVal, modelVers on, pathSuff x)
  }

  def embedd ngsL ePath(modelVers on: ModelVers on, pathSuff x: Str ng): Str ng = {
    s"/user/cassowary/processed/ent y_real_graph/s mclusters_embedd ng/l e/$modelVers on/$pathSuff x/"
  }
}
