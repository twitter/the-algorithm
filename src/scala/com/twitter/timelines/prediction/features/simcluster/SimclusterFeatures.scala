package com.tw ter.t  l nes.pred ct on.features.s mcluster

 mport com.tw ter.dal.personal_data.thr ftjava.PersonalDataType._
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.ml.ap .Feature._
 mport com.tw ter.s mclusters_v2.thr ftscala.ClustersUser s nterested n
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work.TypedAggregateGroup
 mport scala.collect on.JavaConverters._

class S mclusterFeatures lper(statsRece ver: StatsRece ver) {
   mport S mclusterFeatures._

  pr vate[t ] val scopedStatsRece ver = statsRece ver.scope(getClass.getS mpleNa )
  pr vate[t ] val  nval dS mclusterModelVers on = scopedStatsRece ver
    .counter(" nval dS mclusterModelVers on")

  def fromUserCluster nterestsPa r(
    user nterestClustersPa r: (Long, ClustersUser s nterested n)
  ): Opt on[S mclusterFeatures] = {
    val (user d, user nterestClusters) = user nterestClustersPa r
     f (user nterestClusters.knownForModelVers on == S MCLUSTER_MODEL_VERS ON) {
      val user nterestClustersFavScores = for {
        (cluster d, scores) <- user nterestClusters.cluster dToScores
        favScore <- scores.favScore
      } y eld (cluster d.toStr ng, favScore)
      So (
        S mclusterFeatures(
          user d,
          user nterestClusters.knownForModelVers on,
          user nterestClustersFavScores.toMap
        )
      )
    } else {
      //   ma nta n t  counter to make sure that t  hardcoded modelVers on   are us ng  s correct.
       nval dS mclusterModelVers on. ncr
      None
    }
  }
}

object S mclusterFeatures {
  // C ck http://go/s mclustersv2runbook for product on vers ons
  //   models are tra ned for t  spec f c model vers on only.
  val S MCLUSTER_MODEL_VERS ON = "20M_145K_dec11"
  val pref x = s"s mcluster.v2.$S MCLUSTER_MODEL_VERS ON"

  val S MCLUSTER_USER_ NTEREST_CLUSTER_SCORES = new SparseCont nuous(
    s"$pref x.user_ nterest_cluster_scores",
    Set(Engage ntScore,  nferred nterests).asJava
  )
  val S MCLUSTER_USER_ NTEREST_CLUSTER_ DS = new SparseB nary(
    s"$pref x.user_ nterest_cluster_ ds",
    Set( nferred nterests).asJava
  )
  val S MCLUSTER_MODEL_VERS ON_METADATA = new Text(" ta.s mcluster_vers on")
}

case class S mclusterFeatures(
  user d: Long,
  modelVers on: Str ng,
   nterestClusterScoresMap: Map[Str ng, Double])
