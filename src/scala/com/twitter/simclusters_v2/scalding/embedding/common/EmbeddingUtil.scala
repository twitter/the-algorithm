package com.tw ter.s mclusters_v2.scald ng.embedd ng.common

 mport com.tw ter.s mclusters_v2.thr ftscala._
 mport java.net. netAddress
 mport java.net.UnknownHostExcept on

object Embedd ngUt l {

  type User d = Long
  type Cluster d =  nt
  type Producer d = Long
  type Embedd ngScore = Double
  type Semant cCoreEnt y d = Long
  type Hashtag d = Str ng
  type Language = Str ng

   mpl c  val  nternal dOrder ng: Order ng[ nternal d] = Order ng.by {
    case  nternal d.Ent y d( d) =>  d.toStr ng
    case  nternal d.Hashtag(str d) => str d
    case  nternal d.Cluster d(  d) =>   d.toStr ng
    case  nternal d.LocaleEnt y d(LocaleEnt y d(ent y d, lang)) => lang + ent y d.toStr ng
  }

   mpl c  val embedd ngTypeOrder ng: Order ng[Embedd ngType] = Order ng.by(_.getValue)

  /**
   *   do not need to group by model vers on s nce   are mak ng t 
   * T  order ng holds t  assumpt on that   would NEVER generate embedd ngs for two separate
   * S mClusters KnownFor vers ons under t  sa  dataset.
   */
   mpl c  val S mClustersEmbedd ng dOrder ng: Order ng[S mClustersEmbedd ng d] = Order ng.by {
    case S mClustersEmbedd ng d(embedd ngType, _,  nternal d) => (embedd ngType,  nternal d)
  }

  val ModelVers onPathMap: Map[ModelVers on, Str ng] = Map(
    ModelVers on.Model20m145kDec11 -> "model_20m_145k_dec11",
    ModelVers on.Model20m145kUpdated -> "model_20m_145k_updated",
    ModelVers on.Model20m145k2020 -> "model_20m_145k_2020"
  )

  /**
   * Generates t  HDFS output path  n order to consol date t  offl ne embedd ngs datasets under
   * a common d rectory pattern.
   * Prepends "/gcs"  f t  detected data center  s qus1.
   *
   * @param  sAdhoc W t r t  dataset was generated from an adhoc run
   * @param  sManhattanKeyVal W t r t  dataset  s wr ten as KeyVal and  s  ntended to be  mported to Manhattan
   * @param modelVers on T  model vers on of S mClusters KnownFor that  s used to generate t  embedd ng
   * @param pathSuff x Any add  onal path structure suff xed at t  end of t  path
   * @return T  consol dated HDFS path, for example:
   *         /user/cassowary/adhoc/manhattan_sequence_f les/s mclusters_embedd ngs/model_20m_145k_updated/...
   */
  def getHdfsPath(
     sAdhoc: Boolean,
     sManhattanKeyVal: Boolean,
    modelVers on: ModelVers on,
    pathSuff x: Str ng
  ): Str ng = {
    val adhoc =  f ( sAdhoc) "adhoc/" else ""

    val user = System.getenv("USER")

    val gcs: Str ng =
      try {
         netAddress.getAllByNa (" tadata.google. nternal") // throws Except on  f not  n GCP.
        "/gcs"
      } catch {
        case _: UnknownHostExcept on => ""
      }

    val datasetType =  f ( sManhattanKeyVal) "manhattan_sequence_f les" else "processed"

    val path = s"/user/$user/$adhoc$datasetType/s mclusters_embedd ngs"

    s"$gcs${path}_${ModelVers onPathMap(modelVers on)}_$pathSuff x"
  }

  def favScoreExtractor(u: UserTo nterested nClusterScores): (Double, ScoreType.ScoreType) = {
    (u.favScoreClusterNormal zedOnly.getOrElse(0.0), ScoreType.FavScore)
  }

  def followScoreExtractor(u: UserTo nterested nClusterScores): (Double, ScoreType.ScoreType) = {
    (u.followScoreClusterNormal zedOnly.getOrElse(0.0), ScoreType.FollowScore)
  }

  def logFavScoreExtractor(u: UserTo nterested nClusterScores): (Double, ScoreType.ScoreType) = {
    (u.logFavScoreClusterNormal zedOnly.getOrElse(0.0), ScoreType.LogFavScore)
  }

  // Def ne all scores to extract from t  S mCluster  nterested n s ce
  val scoreExtractors: Seq[UserTo nterested nClusterScores => (Double, ScoreType.ScoreType)] =
    Seq(
      favScoreExtractor,
      followScoreExtractor
    )

  object ScoreType extends Enu rat on {
    type ScoreType = Value
    val FavScore: Value = Value(1)
    val FollowScore: Value = Value(2)
    val LogFavScore: Value = Value(3)
  }

  @deprecated("Use 'common/ModelVers ons'", "2019-09-04")
  f nal val ModelVers on20M145KDec11: Str ng = "20M_145K_dec11"
  @deprecated("Use 'common/ModelVers ons'", "2019-09-04")
  f nal val ModelVers on20M145KUpdated: Str ng = "20M_145K_updated"

  @deprecated("Use 'common/ModelVers ons'", "2019-09-04")
  f nal val ModelVers onMap: Map[Str ng, ModelVers on] = Map(
    ModelVers on20M145KDec11 -> ModelVers on.Model20m145kDec11,
    ModelVers on20M145KUpdated -> ModelVers on.Model20m145kUpdated
  )
}
