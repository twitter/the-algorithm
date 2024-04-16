package com.tw ter.s mclusters_v2.summ ngb rd.common

 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.s mclusters_v2.common.ModelVers ons._
 mport com.tw ter.s mclusters_v2.summ ngb rd.common.Cl entConf gs._
 mport com.tw ter.s mclusters_v2.summ ngb rd.common.S mClustersProf le.AltSett ng.AltSett ng
 mport com.tw ter.s mclusters_v2.summ ngb rd.common.S mClustersProf le.Env ron nt.Env ron nt
 mport com.tw ter.s mclusters_v2.summ ngb rd.common.S mClustersProf le.JobType.JobType
 mport com.tw ter.s mclusters_v2.summ ngb rd.common.S mClustersProf le.AltSett ng
 mport com.tw ter.s mclusters_v2.summ ngb rd.common.S mClustersProf le.JobType
 mport com.tw ter.s mclusters_v2.thr ftscala.Embedd ngType
 mport com.tw ter.s mclusters_v2.thr ftscala.ModelVers on

sealed tra  S mClustersProf le {
  val env: Env ron nt
  val alt: AltSett ng
  val modelVers onStr: Str ng

  lazy val modelVers on: ModelVers on = modelVers onStr
}

sealed tra  S mClustersJobProf le extends S mClustersProf le {

  val jobType: JobType

  f nal lazy val jobNa : Str ng = {
    alt match {
      case AltSett ng.Alt =>
        s"s mclusters_v2_${jobType}_alt_job_$env"
      case AltSett ng.Esc =>
        s"s mclusters_v2_${jobType}_esc_job_$env"
      case _ =>
        s"s mclusters_v2_${jobType}_job_$env"
    }
  }

  // Bu ld t  serv ce dent f er by jobType, env and zone(dc)
  f nal lazy val serv ce dent f er: Str ng => Serv ce dent f er = { zone =>
    Serv ce dent f er(Conf gs.role, s"summ ngb rd_$jobNa ", env.toStr ng, zone)
  }

  f nal lazy val favScoreThresholdForUser nterest: Double =
    Conf gs.favScoreThresholdForUser nterest(modelVers onStr)

  lazy val t  l neEventS ceSubscr ber d: Str ng = {
    val jobTypeStr = jobType match {
      case JobType.Mult ModelT et => "mult _model_t et_"
      case JobType.Pers stentT et => "pers stent_t et_"
      case JobType.T et => ""
    }

    val pref x = alt match {
      case AltSett ng.Alt =>
        "alt_"
      case AltSett ng.Esc =>
        "esc_"
      case _ =>
        ""
    }

    s"s mclusters_v2_${jobTypeStr}summ ngb rd_$pref x$env"
  }

}

object S mClustersProf le {

  object JobType extends Enu rat on {
    type JobType = Value
    val T et: JobType = Value("t et")
    val Pers stentT et: JobType = Value("pers stent_t et")
    val Mult ModelT et: JobType = Value("mult model_t et")
  }

  object Env ron nt extends Enu rat on {
    type Env ron nt = Value
    val Prod: Env ron nt = Value("prod")
    val Devel: Env ron nt = Value("devel")

    def apply(sett ng: Str ng): Env ron nt = {
       f (sett ng == Prod.toStr ng) {
        Prod
      } else {
        Devel
      }
    }
  }

  object AltSett ng extends Enu rat on {
    type AltSett ng = Value
    val Normal: AltSett ng = Value("normal")
    val Alt: AltSett ng = Value("alt")
    val Esc: AltSett ng = Value("esc")

    def apply(sett ng: Str ng): AltSett ng = {

      sett ng match {
        case "alt" => Alt
        case "esc" => Esc
        case _ => Normal
      }
    }
  }

  case class S mClustersT etProf le(
    env: Env ron nt,
    alt: AltSett ng,
    modelVers onStr: Str ng,
    ent yClusterScorePath: Str ng,
    t etTopKClustersPath: Str ng,
    clusterTopKT etsPath: Str ng,
    coreEmbedd ngType: Embedd ngType,
    clusterTopKT etsL ghtPath: Opt on[Str ng] = None)
      extends S mClustersJobProf le {

    f nal val jobType: JobType = JobType.T et
  }

  case class Pers stentT etProf le(
    env: Env ron nt,
    alt: AltSett ng,
    modelVers onStr: Str ng,
    pers stentT etStratoPath: Str ng,
    coreEmbedd ngType: Embedd ngType)
      extends S mClustersJobProf le {
    f nal val jobType: JobType = JobType.Pers stentT et
  }

  f nal val AltProdT etJobProf le = S mClustersT etProf le(
    env = Env ron nt.Prod,
    alt = AltSett ng.Alt,
    modelVers onStr = Model20M145K2020,
    ent yClusterScorePath = s mClustersCoreAltCac Path,
    t etTopKClustersPath = s mClustersCoreAltCac Path,
    clusterTopKT etsPath = s mClustersCoreAltCac Path,
    clusterTopKT etsL ghtPath = So (s mClustersCoreAltL ghtCac Path),
    coreEmbedd ngType = Embedd ngType.LogFavBasedT et
  )

  f nal val AltDevelT etJobProf le = S mClustersT etProf le(
    env = Env ron nt.Devel,
    alt = AltSett ng.Alt,
    modelVers onStr = Model20M145K2020,
    // us ng t  sa  devel cac  w h job
    ent yClusterScorePath = develS mClustersCoreCac Path,
    t etTopKClustersPath = develS mClustersCoreCac Path,
    clusterTopKT etsPath = develS mClustersCoreCac Path,
    clusterTopKT etsL ghtPath = So (develS mClustersCoreL ghtCac Path),
    coreEmbedd ngType = Embedd ngType.LogFavBasedT et,
  )

  f nal val ProdPers stentT etProf le = Pers stentT etProf le(
    env = Env ron nt.Prod,
    alt = AltSett ng.Normal,
    modelVers onStr = Model20M145K2020,
    // T  prof le  s used by t  pers stent t et embedd ng job to update t  embedd ng.  
    // use t  uncac d column to avo d read ng stale data
    pers stentT etStratoPath = logFavBasedT et20M145K2020Uncac dStratoPath,
    coreEmbedd ngType = Embedd ngType.LogFavBasedT et
  )

  f nal val DevelPers stentT etProf le = Pers stentT etProf le(
    env = Env ron nt.Devel,
    alt = AltSett ng.Normal,
    modelVers onStr = Model20M145K2020,
    pers stentT etStratoPath = develLogFavBasedT et20M145K2020StratoPath,
    coreEmbedd ngType = Embedd ngType.LogFavBasedT et
  )

  def fetchT etJobProf le(
    env: Env ron nt,
    alt: AltSett ng = AltSett ng.Normal
  ): S mClustersT etProf le = {
    (env, alt) match {
      case (Env ron nt.Prod, AltSett ng.Alt) => AltProdT etJobProf le
      case (Env ron nt.Devel, AltSett ng.Alt) => AltDevelT etJobProf le
      case _ => throw new  llegalArgu ntExcept on(" nval d env or alt sett ng")
    }
  }

  def fetchPers stentJobProf le(
    env: Env ron nt,
    alt: AltSett ng = AltSett ng.Normal
  ): Pers stentT etProf le = {
    (env, alt) match {
      case (Env ron nt.Prod, AltSett ng.Normal) => ProdPers stentT etProf le
      case (Env ron nt.Devel, AltSett ng.Normal) => DevelPers stentT etProf le
      case _ => throw new  llegalArgu ntExcept on(" nval d env or alt sett ng")
    }
  }

  /**
   * For short term, fav based t et embedd ng and log fav based t ets embedd ng ex sts at t 
   * sa  t  .   want to move to log fav based t et embedd ng eventually.
   * Follow based t et embedd ngs ex sts  n both env ron nt.
   * A un form t et embedd ng AP   s t  future to replace t  ex st ng use case.
   */
  f nal lazy val t etJobProf leMap: Env ron nt => Map[
    (Embedd ngType, Str ng),
    S mClustersT etProf le
  ] = {
    case Env ron nt.Prod =>
      Map(
        (Embedd ngType.LogFavBasedT et, Model20M145K2020) -> AltProdT etJobProf le
      )
    case Env ron nt.Devel =>
      Map(
        (Embedd ngType.LogFavBasedT et, Model20M145K2020) -> AltDevelT etJobProf le
      )
  }

}
