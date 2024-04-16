package com.tw ter.s mclusters_v2.scald ng.offl ne_job

 mport com.tw ter.algeb rd.Aggregator.s ze
 mport com.tw ter.algeb rd.{Aggregator, QTreeAggregatorLo rBound}
 mport com.tw ter.scald ng.{Execut on, Stat, TypedP pe, Un que D}
 mport com.tw ter.s mclusters_v2.cand date_s ce._
 mport com.tw ter.s mclusters_v2.common.T et d
 mport com.tw ter.s mclusters_v2.thr ftscala.{
  ClusterTopKT etsW hScores,
  ClustersUser s nterested n
}
 mport java.n o.ByteBuffer

case class Offl neRecConf g(
  maxT etRecs:  nt, // total number of t et recs.
  maxT etsPerUser:  nt,
  maxClustersToQuery:  nt,
  m nT etScoreThreshold: Double,
  rankClustersBy: ClusterRanker.Value)

/**
 * An offl ne s mulat on of t  t et rec log c  n [[ nterested nT etCand dateStore]].
 * T  ma n d fference  s that  nstead of us ng  mcac ,   uses an offl ne clusterTopK store as
 * t  t et s ce.
 * Also,  nstead of tak ng a s ngle user d as  nput,   processes a p pe of users altoget r.
 */
object Offl neT etRecom ndat on {

  case class ScoredT et(t et d: T et d, score: Double) {

    def toTuple: (T et d, Double) = {
      (t et d, score)
    }
  }

  object ScoredT et {
    def apply(tuple: (T et d, Double)): ScoredT et = new ScoredT et(tuple._1, tuple._2)
     mpl c  val scoredOrder ng: Order ng[ScoredT et] = (x: ScoredT et, y: ScoredT et) => {
      Order ng.Double.compare(x.score, y.score)
    }
  }

  def getTopT ets(
    conf g: Offl neRecConf g,
    targetUsersP pe: TypedP pe[Long],
    user s nterested nP pe: TypedP pe[(Long, ClustersUser s nterested n)],
    clusterTopKT etsP pe: TypedP pe[ClusterTopKT etsW hScores]
  )(
     mpl c  un que D: Un que D
  ): Execut on[TypedP pe[(Long, Seq[ScoredT et])]] = {
    val t etRecom ndedCount = Stat("NumT etsReco nded")
    val targetUserCount = Stat("NumTargetUsers")
    val userW hRecsCount = Stat("NumUsersW hAtLeastT etRec")

    // For every user, read t  user's  nterested- n clusters and cluster's   ghts
    val userCluster  ghtP pe: TypedP pe[( nt, (Long, Double))] =
      targetUsersP pe.asKeys
        .jo n(user s nterested nP pe)
        .flatMap {
          case (user d, (_, clustersW hScores)) =>
            targetUserCount. nc()
            val topClusters = ClusterRanker
              .getTopKClustersByScore(
                clustersW hScores.cluster dToScores.toMap,
                ClusterRanker.RankByNormal zedFavScore,
                conf g.maxClustersToQuery
              ).toL st
            topClusters.map {
              case (cluster d, cluster  ghtForUser) =>
                (cluster d, (user d, cluster  ghtForUser))
            }
        }

    // For every cluster, read t  top t ets  n t  cluster, and t  r   ghts
    val clusterT et  ghtP pe: TypedP pe[( nt, L st[(Long, Double)])] =
      clusterTopKT etsP pe
        .flatMap { cluster =>
          val t ets =
            cluster.topKT ets.toL st // Convert to a L st, ot rw se .flatMap dedups by cluster ds
              .flatMap {
                case (t d, pers stedScores) =>
                  val t et  ght = pers stedScores.score.map(_.value).getOrElse(0.0)
                   f (t et  ght > 0) {
                    So ((t d, t et  ght))
                  } else {
                    None
                  }
              }
           f (t ets.nonEmpty) {
            So ((cluster.cluster d, t ets))
          } else {
            None
          }
        }

    // Collect all t  t ets from clusters user  s  nterested  n
    val recom ndedT etsP pe = userCluster  ghtP pe
      .sketch(4000)(c d => ByteBuffer.allocate(4).put nt(c d).array(), Order ng. nt)
      .jo n(clusterT et  ghtP pe)
      .flatMap {
        case (_, ((user d, cluster  ght), t etsPerCluster)) =>
          t etsPerCluster.map {
            case (t d, t et  ght) =>
              val contr but on = cluster  ght * t et  ght
              ((user d, t d), contr but on)
          }
      }
      .sumByKey
      .w hReducers(5000)

    // F lter by m n mum score threshold
    val scoreF lteredT etsP pe = recom ndedT etsP pe
      .collect {
        case ((user d, t d), score)  f score >= conf g.m nT etScoreThreshold =>
          (user d, ScoredT et(t d, score))
      }

    // Rank top t ets for each user
    val topT etsPerUserP pe = scoreF lteredT etsP pe.group
      .sortedReverseTake(conf g.maxT etsPerUser)(ScoredT et.scoredOrder ng)
      .flatMap {
        case (user d, t ets) =>
          userW hRecsCount. nc()
          t etRecom ndedCount. ncBy(t ets.s ze)

          t ets.map { t => (user d, t) }
      }
      .forceToD skExecut on

    val topT etsP pe = topT etsPerUserP pe
      .flatMap { t ets =>
        approx mateScoreAtTopK(t ets.map(_._2.score), conf g.maxT etRecs).map { threshold =>
          t ets
            .collect {
              case (user d, t et)  f t et.score >= threshold =>
                (user d, L st(t et))
            }
            .sumByKey
            .toTypedP pe
        }
      }
    topT etsP pe
  }

  /**
   * Returns t  approx mate score at t  k'th top ranked record us ng sampl ng.
   * T  score can t n be used to f lter for t  top K ele nts  n a b g p pe w re
   * K  s too b g to f   n  mory.
   *
   */
  def approx mateScoreAtTopK(p pe: TypedP pe[Double], topK:  nt): Execut on[Double] = {
    val defaultScore = 0.0
    pr ntln("approx mateScoreAtTopK: topK=" + topK)
    p pe
      .aggregate(s ze)
      .getOrElseExecut on(0L)
      .flatMap { len =>
        pr ntln("approx mateScoreAtTopK: len=" + len)
        val topKPercent le =  f (len == 0 || topK > len) 0 else 1 - topK.toDouble / len.toDouble
        val randomSample = Aggregator.reservo rSample[Double](Math.max(100000, topK / 100))
        p pe
          .aggregate(randomSample)
          .getOrElseExecut on(L st.empty)
          .flatMap { sample =>
            TypedP pe
              .from(sample)
              .aggregate(QTreeAggregatorLo rBound[Double](topKPercent le))
              .getOrElseExecut on(defaultScore)
          }
      }
      .map { score =>
        pr ntln("approx mateScoreAtTopK: topK percent le score=" + score)
        score
      }
  }
}
