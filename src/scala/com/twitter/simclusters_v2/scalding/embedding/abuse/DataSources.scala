package com.tw ter.s mclusters_v2.scald ng.embedd ng.abuse

 mport com.tw ter.data.proto.Flock
 mport com.tw ter.scald ng.{DateOps, DateRange, Days, R chDate, Un que D}
 mport com.tw ter.scald ng_ nternal.dalv2.DAL
 mport com.tw ter.s mclusters_v2.hdfs_s ces. nterested nS ces
 mport com.tw ter.s mclusters_v2.scald ng.common.matr x.SparseMatr x
 mport com.tw ter.s mclusters_v2.scald ng.embedd ng.common.Embedd ngUt l.{Cluster d, User d}
 mport com.tw ter.s mclusters_v2.scald ng.embedd ng.common.ExternalDataS ces
 mport graphstore.common.FlockBlocksJavaDataset
 mport java.ut l.T  Zone

object DataS ces {

  pr vate val Val dEdgeState d = 0
  val NumBlocksP95 = 49

  /**
   *  lper funct on to return Sparse Matr x of user's  nterested n clusters and fav scores
   * @param dateRange
   * @return
   */
  def getUser nterested nSparseMatr x(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone
  ): SparseMatr x[User d, Cluster d, Double] = {
    val s mClusters = ExternalDataS ces.s mClusters nterest nS ce

    val s mClusterMatr xEntr es = s mClusters
      .flatMap { keyVal =>
        keyVal.value.cluster dToScores.flatMap {
          case (cluster d, score) =>
            score.favScore.map { favScore =>
              (keyVal.key, cluster d, favScore)
            }
        }
      }

    SparseMatr x.apply[User d, Cluster d, Double](s mClusterMatr xEntr es)
  }

  def getUser nterested nTruncatedKMatr x(
    topK:  nt
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): SparseMatr x[User d, Cluster d, Double] = {
    SparseMatr x(
       nterested nS ces
        .s mClusters nterested nUpdatedS ce(dateRange, t  Zone)
        .flatMap {
          case (user d, clustersUser s nterested n) =>
            val sortedAndTruncatedL st = clustersUser s nterested n.cluster dToScores
              .mapValues(_.favScore.getOrElse(0.0)).f lter(_._2 > 0.0).toL st.sortBy(-_._2).take(
                topK)
            sortedAndTruncatedL st.map {
              case (cluster d, score) =>
                (user d, cluster d, score)
            }
        }
    )
  }

  /**
   *  lper funct on to return SparseMatr x of user block  nteract ons from t  FlockBlocks
   * dataset. All users w h greater than numBlocks are f ltered out
   * @param dateRange
   * @return
   */
  def getFlockBlocksSparseMatr x(
    maxNumBlocks:  nt,
    rangeForData: DateRange
  )(
     mpl c  dateRange: DateRange
  ): SparseMatr x[User d, User d, Double] = {
     mpl c  val tz: java.ut l.T  Zone = DateOps.UTC
    val userG v ngBlocks = SparseMatr x.apply[User d, User d, Double](
      DAL
        .readMostRecentSnapshotNoOlderThan(FlockBlocksJavaDataset, Days(30))
        .toTypedP pe
        .flatMap { data: Flock.Edge =>
          // Cons der edges that are val d and have been updated  n t  past 1 year
           f (data.getState d == Val dEdgeState d &&
            rangeForData.conta ns(R chDate(data.getUpdatedAt * 1000L))) {
            So ((data.getS ce d, data.getDest nat on d, 1.0))
          } else {
            None
          }
        })
    // F nd all users who g ve less than numBlocksP95 blocks.
    // T   s to remove those who m ght be respons ble for automat cally block ng users
    // on t  tw ter platform.
    val usersW hLeg Blocks = userG v ngBlocks.rowL1Norms.collect {
      case (user d, l1Norm)  f l1Norm <= maxNumBlocks =>
        user d
    }
    // reta n only those users who g ve leg  blocks ( .e those users who g ve less than numBlocks95)
    userG v ngBlocks.f lterRows(usersW hLeg Blocks)
  }
}
