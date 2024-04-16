package com.tw ter.s mclusters_v2.scald ng.embedd ng.abuse

 mport com.google.common.annotat ons.V s bleForTest ng
 mport com.tw ter.scald ng._
 mport com.tw ter.s mclusters_v2.scald ng.common.matr x.SparseMatr x
 mport com.tw ter.s mclusters_v2.scald ng.embedd ng.common.Embedd ngUt l.Cluster d
 mport com.tw ter.s mclusters_v2.scald ng.embedd ng.common.Embedd ngUt l.User d
 mport com.tw ter.s mclusters_v2.thr ftscala.AdhocS ngleS deClusterScores
 mport com.tw ter.s mclusters_v2.thr ftscala.S mClusterW hScore
 mport com.tw ter.s mclusters_v2.thr ftscala.S mClustersEmbedd ng

/**
 * Log c for bu ld ng a S mCluster represenat on of  nteract on s gnals. T  purpose of t  job  s
 * to model negat ve behav or (l ke abuse and blocks).
 *
 * T   s a "S ngleS de", because   are only cons der ng one s de of t   nteract on graph to
 * bu ld t se features. So for  nstance   would keep track of wh ch s mclusters are most l kely to
 * get reported for abuse regardless of who reported  . Anot r job w ll be respons ble for
 * bu ld ng t  s mcluster to s mcluster  nteract on matr x as descr bed  n t  doc.
 */
object S ngleS de nteract onTransformat on {

  /**
   * Compute a score for every S mCluster. T  S mCluster score  s a count of t  number of
   *  nteract ons for each S mCluster. For a user that has many S mClusters,   d str bute each of
   * t  r  nteract ons across all of t se S mClusters.
   *
   * @param normal zedUserS mClusters Sparse matr x of User-S mCluster scores. Users are rows and
   *                                  S mClusters are columns. T  should already by L2normal zed.
   *                                     s  mportant that   normal ze so that each  nteract on
   *                                  only adds 1 to t  counts.
   * @param  nteract onGraph Graph of  nteract ons. Rows are t  users, columns are not used.
   *                   All values  n t  graph are assu d to be pos  ve; t y are t  number of
   *                    nteract ons.
   *
   * @return S ngleS deClusterFeatures for each S mCluster that has user w h an  nteract on.
   */
  def computeClusterFeatures(
    normal zedUserS mClusters: SparseMatr x[User d, Cluster d, Double],
     nteract onGraph: SparseMatr x[User d, _, Double]
  ): TypedP pe[S mClusterW hScore] = {

    val numReportsForUserEntr es =  nteract onGraph.rowL1Norms.map {
      // turn  nto a vector w re   use 1 as t  column key for every entry.
      case (user, count) => (user, 1, count)
    }

    val numReportsForUser = SparseMatr x[User d,  nt, Double](numReportsForUserEntr es)

    normal zedUserS mClusters.transpose
      .mult plySparseMatr x(numReportsForUser)
      .toTypedP pe
      .map {
        case (cluster d, _, clusterScore: Double) =>
          S mClusterW hScore(cluster d, clusterScore)
      }
  }

  /**
   * G ven that   have t  score for each S mCluster and t  user's S mClusters, create a
   * representat on of t  user so that t  new S mCluster scores are an est mate of t 
   *  nteract ons for t  user.
   *
   * @param normal zedUserS mClusters sparse matr x of User-S mCluster scores. Users are rows and
   *                                  S mClusters are columns. T  should already be L2 normal zed.
   * @param s mClusterFeatures For each S mCluster, a score assoc ated w h t   nteract on type.
   *
   * @return S ngleS deAbuseFeatures for each user t  S mClusters and scores for t 
   */
  @V s bleForTest ng
  pr vate[abuse] def computeUserFeaturesFromClusters(
    normal zedUserS mClusters: SparseMatr x[User d, Cluster d, Double],
    s mClusterFeatures: TypedP pe[S mClusterW hScore]
  ): TypedP pe[(User d, S mClustersEmbedd ng)] = {

    normal zedUserS mClusters.toTypedP pe
      .map {
        case (user d, cluster d, score) =>
          (cluster d, (user d, score))
      }
      .group
      // T re are at most 140k S mClusters. T y should f   n  mory
      .hashJo n(s mClusterFeatures.groupBy(_.cluster d))
      .map {
        case (_, ((user d, score), s ngleS deClusterFeatures)) =>
          (
            user d,
            L st(
              S mClusterW hScore(
                s ngleS deClusterFeatures.cluster d,
                s ngleS deClusterFeatures.score * score))
          )
      }
      .sumByKey
      .mapValues(S mClustersEmbedd ng.apply)
  }

  /**
   * Comb nes all t  d fferent S mClustersEmbedd ng for a user  nto one
   * AdhocS ngleS deClusterScores.
   *
   * @param  nteract onMap T  key  s an  dent f er for t  embedd ng type. T  typed p pe w ll have
   *                       embedd ngs of only for that type of embedd ng.
   * @return Typed p pe w h one AdhocS ngleS deClusterScores per user.
   */
  def pa rScores(
     nteract onMap: Map[Str ng, TypedP pe[(User d, S mClustersEmbedd ng)]]
  ): TypedP pe[AdhocS ngleS deClusterScores] = {

    val comb ned nteract ons =  nteract onMap
      .map {
        case ( nteract onTypeNa , user nteract onFeatures) =>
          user nteract onFeatures.map {
            case (user d, s mClustersEmbedd ng) =>
              (user d, L st(( nteract onTypeNa , s mClustersEmbedd ng)))
          }
      }
      .reduce[TypedP pe[(User d, L st[(Str ng, S mClustersEmbedd ng)])]] {
        case (l st1, l st2) =>
          l st1 ++ l st2
      }
      .group
      .sumByKey

    comb ned nteract ons.toTypedP pe
      .map {
        case (user d,  nteract onFeatureL st) =>
          AdhocS ngleS deClusterScores(
            user d,
             nteract onFeatureL st.toMap
          )
      }
  }

  /**
   * G ven t  S mCluster and  nteract on graph get t  user representat on for t   nteract on.
   * See t  docu ntat on of t  underly ng  thods for more deta ls
   *
   * @param normal zedUserS mClusters sparse matr x of User-S mCluster scores. Users are rows and
   *                                  S mClusters are columns. T  should already by L2normal zed.
   * @param  nteract onGraph Graph of  nteract ons. Rows are t  users, columns are not used.
   *                   All values  n t  graph are assu d to be pos  ve; t y are t  number of
   *                    nteract ons.
   *
   * @return S mClustersEmbedd ng for all users  n t  g ve S mCluster graphs
   */
  def clusterScoresFromGraphs(
    normal zedUserS mClusters: SparseMatr x[User d, Cluster d, Double],
     nteract onGraph: SparseMatr x[User d, _, Double]
  ): TypedP pe[(User d, S mClustersEmbedd ng)] = {
    val clusterFeatures = computeClusterFeatures(normal zedUserS mClusters,  nteract onGraph)
    computeUserFeaturesFromClusters(normal zedUserS mClusters, clusterFeatures)
  }
}
