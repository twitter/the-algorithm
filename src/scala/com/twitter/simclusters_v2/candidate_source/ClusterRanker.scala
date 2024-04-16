package com.tw ter.s mclusters_v2.cand date_s ce

 mport com.tw ter.s mclusters_v2.thr ftscala.UserTo nterested nClusterScores

object ClusterRanker extends Enu rat on {
  val RankByNormal zedFavScore: ClusterRanker.Value = Value
  val RankByFavScore: ClusterRanker.Value = Value
  val RankByFollowScore: ClusterRanker.Value = Value
  val RankByLogFavScore: ClusterRanker.Value = Value
  val RankByNormal zedLogFavScore: ClusterRanker.Value = Value

  /**
   * G ven a map of clusters, sort out t  top scor ng clusters by a rank ng sc  
   * prov ded by t  caller
   */
  def getTopKClustersByScore(
    clustersW hScores: Map[ nt, UserTo nterested nClusterScores],
    rankByScore: ClusterRanker.Value,
    topK:  nt
  ): Map[ nt, Double] = {
    val rankedClustersW hScores = clustersW hScores.map {
      case (cluster d, score) =>
        rankByScore match {
          case ClusterRanker.RankByFavScore =>
            (cluster d, (score.favScore.getOrElse(0.0), score.followScore.getOrElse(0.0)))
          case ClusterRanker.RankByFollowScore =>
            (cluster d, (score.followScore.getOrElse(0.0), score.favScore.getOrElse(0.0)))
          case ClusterRanker.RankByLogFavScore =>
            (cluster d, (score.logFavScore.getOrElse(0.0), score.followScore.getOrElse(0.0)))
          case ClusterRanker.RankByNormal zedLogFavScore =>
            (
              cluster d,
              (
                score.logFavScoreClusterNormal zedOnly.getOrElse(0.0),
                score.followScore.getOrElse(0.0)))
          case ClusterRanker.RankByNormal zedFavScore =>
            (
              cluster d,
              (
                score.favScoreProducerNormal zedOnly.getOrElse(0.0),
                score.followScore.getOrElse(0.0)))
          case _ =>
            (
              cluster d,
              (
                score.favScoreProducerNormal zedOnly.getOrElse(0.0),
                score.followScore.getOrElse(0.0)))
        }
    }
    rankedClustersW hScores.toSeq
      .sortBy(_._2) // sort  n ascend ng order
      .takeR ght(topK)
      .map { case (cluster d, scores) => cluster d -> math.max(scores._1, 1e-4) }
      .toMap
  }
}
