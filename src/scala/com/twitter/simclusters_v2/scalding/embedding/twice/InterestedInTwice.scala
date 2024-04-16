package com.tw ter.s mclusters_v2.scald ng.embedd ng.tw ce

 mport com.tw ter.scald ng.Args
 mport com.tw ter.scald ng.DateRange
 mport com.tw ter.scald ng.Days
 mport com.tw ter.scald ng.Durat on
 mport com.tw ter.scald ng.Execut on
 mport com.tw ter.scald ng.R chDate
 mport com.tw ter.scald ng.Un que D
 mport com.tw ter.s mclusters_v2.common.S mClustersEmbedd ng
 mport com.tw ter.s mclusters_v2.common.cluster ng.ConnectedComponentsCluster ng thod
 mport com.tw ter.s mclusters_v2.common.cluster ng.LargestD  ns onCluster ng thod
 mport com.tw ter.s mclusters_v2.common.cluster ng.Louva nCluster ng thod
 mport com.tw ter.s mclusters_v2.common.cluster ng. do dRepresentat veSelect on thod
 mport com.tw ter.s mclusters_v2.common.cluster ng.MaxFavScoreRepresentat veSelect on thod
 mport com.tw ter.s mclusters_v2.common.cluster ng.S m lar yFunct ons
 mport com.tw ter.s mclusters_v2.hdfs_s ces.Clusters mbersConnectedComponentsApeS m lar yScalaDataset
 mport com.tw ter.s mclusters_v2.hdfs_s ces.Clusters mbersLargestD mApeS m lar y2DayUpdateScalaDataset
 mport com.tw ter.s mclusters_v2.hdfs_s ces.Clusters mbersLargestD mApeS m lar yScalaDataset
 mport com.tw ter.s mclusters_v2.hdfs_s ces.Clusters mbersLouva nApeS m lar yScalaDataset
 mport com.tw ter.s mclusters_v2.hdfs_s ces. nterested nTw ceByLargestD m2DayUpdateScalaDataset
 mport com.tw ter.s mclusters_v2.hdfs_s ces. nterested nTw ceByLargestD mScalaDataset
 mport com.tw ter.s mclusters_v2.hdfs_s ces. nterested nTw ceByLargestD mFavScoreScalaDataset
 mport com.tw ter.s mclusters_v2.hdfs_s ces. nterested nTw ceConnectedComponentsScalaDataset
 mport com.tw ter.s mclusters_v2.hdfs_s ces. nterested nTw ceLouva nScalaDataset
 mport com.tw ter.s mclusters_v2.scald ng.embedd ng.tw ce. nterested nTw ceBaseApp.ProducerEmbedd ngS ce
 mport com.tw ter.wtf.scald ng.jobs.common.AdhocExecut onApp
 mport com.tw ter.wtf.scald ng.jobs.common.Sc duledExecut onApp
 mport java.ut l.T  Zone

/**
 To bu ld & deploy t  TW CE sc duled jobs v a workflows:

 scald ng workflow upload \
  --workflow  nterested_ n_tw ce-batch \
  --jobs src/scala/com/tw ter/s mclusters_v2/scald ng/embedd ng/tw ce: nterested_ n_tw ce_largest_d m-batch,src/scala/com/tw ter/s mclusters_v2/scald ng/embedd ng/tw ce: nterested_ n_tw ce_louva n-batch,src/scala/com/tw ter/s mclusters_v2/scald ng/embedd ng/tw ce: nterested_ n_tw ce_connected_components-batch \
  --scm-paths "src/scala/com/tw ter/s mclusters_v2/scald ng/embedd ng/tw ce/*" \
  --autoplay \

 -> See workflow  re: https://workflows.tw ter.b z/workflow/cassowary/ nterested_ n_tw ce-batch

 (Use `scald ng workflow upload -- lp` for a breakdown of t  d fferent flags)
 */*/

object  nterested nTw ceLargestD mSc duledApp
    extends  nterested nTw ceBaseApp[S mClustersEmbedd ng]
    w h Sc duledExecut onApp {

  overr de def f rstT  : R chDate = R chDate("2021-09-02")
  overr de def batch ncre nt: Durat on = Days(7)

  overr de def producerProducerS m lar yFnForCluster ng: (
    S mClustersEmbedd ng,
    S mClustersEmbedd ng
  ) => Double =
    S m lar yFunct ons.s mClustersMatch ngLargestD  ns on
  overr de def producerProducerS m lar yFnForClusterRepresentat ve: (
    S mClustersEmbedd ng,
    S mClustersEmbedd ng
  ) => Double =
    S m lar yFunct ons.s mClustersCos neS m lar y

  /**
   * Top-level  thod of t  appl cat on.
   */
  def runOnDateRange(
    args: Args
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que d: Un que D
  ): Execut on[Un ] = {

    runSc duledApp(
      new LargestD  ns onCluster ng thod(),
      new  do dRepresentat veSelect on thod[S mClustersEmbedd ng](
        producerProducerS m lar yFnForClusterRepresentat ve),
      ProducerEmbedd ngS ce.getAggregatableProducerEmbedd ngs,
      " nterested_ n_tw ce_by_largest_d m",
      "clusters_ mbers_largest_d m_ape_s m lar y",
       nterested nTw ceByLargestD mScalaDataset,
      Clusters mbersLargestD mApeS m lar yScalaDataset,
      args.getOrElse("num-reducers", "4000").to nt
    )

  }

}

object  nterested nTw ceLargestD mMaxFavScoreSc duledApp
    extends  nterested nTw ceBaseApp[S mClustersEmbedd ng]
    w h Sc duledExecut onApp {

  overr de def f rstT  : R chDate = R chDate("2022-06-30")
  overr de def batch ncre nt: Durat on = Days(7)

  overr de def producerProducerS m lar yFnForCluster ng: (
    S mClustersEmbedd ng,
    S mClustersEmbedd ng
  ) => Double =
    S m lar yFunct ons.s mClustersMatch ngLargestD  ns on
  overr de def producerProducerS m lar yFnForClusterRepresentat ve: (
    S mClustersEmbedd ng,
    S mClustersEmbedd ng
  ) => Double =
    S m lar yFunct ons.s mClustersCos neS m lar y

  /**
   * Top-level  thod of t  appl cat on.
   */
  def runOnDateRange(
    args: Args
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que d: Un que D
  ): Execut on[Un ] = {

    runSc duledApp(
      new LargestD  ns onCluster ng thod(),
      new MaxFavScoreRepresentat veSelect on thod[S mClustersEmbedd ng](),
      ProducerEmbedd ngS ce.getAggregatableProducerEmbedd ngs,
      " nterested_ n_tw ce_by_largest_d m_fav_score",
      "clusters_ mbers_largest_d m_ape_s m lar y",
       nterested nTw ceByLargestD mFavScoreScalaDataset,
      Clusters mbersLargestD mApeS m lar yScalaDataset,
      args.getOrElse("num-reducers", "4000").to nt
    )

  }

}

object  nterested nTw ceLouva nSc duledApp
    extends  nterested nTw ceBaseApp[S mClustersEmbedd ng]
    w h Sc duledExecut onApp {

  overr de def f rstT  : R chDate = R chDate("2021-09-02")
  overr de def batch ncre nt: Durat on = Days(7)

  overr de def producerProducerS m lar yFnForCluster ng: (
    S mClustersEmbedd ng,
    S mClustersEmbedd ng
  ) => Double =
    S m lar yFunct ons.s mClustersCos neS m lar y
  overr de def producerProducerS m lar yFnForClusterRepresentat ve: (
    S mClustersEmbedd ng,
    S mClustersEmbedd ng
  ) => Double =
    S m lar yFunct ons.s mClustersCos neS m lar y

  /**
   * Top-level  thod of t  appl cat on.
   */
  def runOnDateRange(
    args: Args
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que d: Un que D
  ): Execut on[Un ] = {

    runSc duledApp(
      new Louva nCluster ng thod(
        args.requ red("cos ne_s m lar y_threshold").toDouble,
        args.opt onal("resolut on_factor").map(_.toDouble)),
      new  do dRepresentat veSelect on thod[S mClustersEmbedd ng](
        producerProducerS m lar yFnForClusterRepresentat ve),
      ProducerEmbedd ngS ce.getAggregatableProducerEmbedd ngs,
      " nterested_ n_tw ce_louva n",
      "clusters_ mbers_louva n_ape_s m lar y",
       nterested nTw ceLouva nScalaDataset,
      Clusters mbersLouva nApeS m lar yScalaDataset,
      args.getOrElse("num-reducers", "4000").to nt
    )

  }

}

object  nterested nTw ceConnectedComponentsSc duledApp
    extends  nterested nTw ceBaseApp[S mClustersEmbedd ng]
    w h Sc duledExecut onApp {

  overr de def f rstT  : R chDate = R chDate("2021-09-02")
  overr de def batch ncre nt: Durat on = Days(7)
  overr de def producerProducerS m lar yFnForCluster ng: (
    S mClustersEmbedd ng,
    S mClustersEmbedd ng
  ) => Double =
    S m lar yFunct ons.s mClustersCos neS m lar y
  overr de def producerProducerS m lar yFnForClusterRepresentat ve: (
    S mClustersEmbedd ng,
    S mClustersEmbedd ng
  ) => Double =
    S m lar yFunct ons.s mClustersCos neS m lar y

  /**
   * Top-level  thod of t  appl cat on.
   */
  def runOnDateRange(
    args: Args
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que d: Un que D
  ): Execut on[Un ] = {

    runSc duledApp(
      new ConnectedComponentsCluster ng thod(
        args.requ red("cos ne_s m lar y_threshold").toDouble),
      new  do dRepresentat veSelect on thod[S mClustersEmbedd ng](
        producerProducerS m lar yFnForClusterRepresentat ve),
      ProducerEmbedd ngS ce.getAggregatableProducerEmbedd ngs,
      " nterested_ n_tw ce_connected_components",
      "clusters_ mbers_connected_components_ape_s m lar y",
       nterested nTw ceConnectedComponentsScalaDataset,
      Clusters mbersConnectedComponentsApeS m lar yScalaDataset,
      args.getOrElse("num-reducers", "4000").to nt
    )

  }

}

/** Product on Scald ng job that calculates TW CE embedd ngs  n a shorter per od (every two days).
 *
 * G ven that t   nput s ces of TW CE are updated more frequently (e.g., user_user_graph  s
 * updated every 2 day), updat ng TW CE embedd ng every 2 day w ll better capture  nterests of new
 * users and t   nterest sh ft of ex st ng users.
 *
 * To bu ld & deploy t  sc duled job v a workflows:
 * {{{
 * scald ng workflow upload \
 * --workflow  nterested_ n_tw ce_2_day_update-batch \
 * --jobs src/scala/com/tw ter/s mclusters_v2/scald ng/embedd ng/tw ce: nterested_ n_tw ce_largest_d m_2_day_update-batch \
 * --scm-paths "src/scala/com/tw ter/s mclusters_v2/scald ng/embedd ng/tw ce/*" \
 * --autoplay
 * }}}
 *
 */*/
object  nterested nTw ceLargestD m2DayUpdateSc duledApp
    extends  nterested nTw ceBaseApp[S mClustersEmbedd ng]
    w h Sc duledExecut onApp {

  overr de def f rstT  : R chDate = R chDate("2022-04-06")
  overr de def batch ncre nt: Durat on = Days(2)

  overr de def producerProducerS m lar yFnForCluster ng: (
    S mClustersEmbedd ng,
    S mClustersEmbedd ng
  ) => Double =
    S m lar yFunct ons.s mClustersMatch ngLargestD  ns on
  overr de def producerProducerS m lar yFnForClusterRepresentat ve: (
    S mClustersEmbedd ng,
    S mClustersEmbedd ng
  ) => Double =
    S m lar yFunct ons.s mClustersCos neS m lar y

  /**
   * Top-level  thod of t  appl cat on.
   */
  def runOnDateRange(
    args: Args
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que d: Un que D
  ): Execut on[Un ] = {

    runSc duledApp(
      new LargestD  ns onCluster ng thod(),
      new  do dRepresentat veSelect on thod[S mClustersEmbedd ng](
        producerProducerS m lar yFnForClusterRepresentat ve),
      ProducerEmbedd ngS ce.getAggregatableProducerEmbedd ngs,
      " nterested_ n_tw ce_by_largest_d m_2_day_update",
      "clusters_ mbers_largest_d m_ape_s m lar y_2_day_update",
       nterested nTw ceByLargestD m2DayUpdateScalaDataset,
      Clusters mbersLargestD mApeS m lar y2DayUpdateScalaDataset,
      args.getOrElse("num-reducers", "4000").to nt
    )
  }
}

/**

[Preferred way] To run a locally bu lt adhoc job:
 ./bazel bundle src/scala/com/tw ter/s mclusters_v2/scald ng/embedd ng/tw ce: nterested_ n_tw ce_<CLUSTER NG_METHOD>-adhoc
 scald ng remote run --target src/scala/com/tw ter/s mclusters_v2/scald ng/embedd ng/tw ce: nterested_ n_tw ce_<CLUSTER NG_METHOD>-adhoc

To bu ld and run a adhoc job w h workflows:
 scald ng workflow upload \
  --workflow  nterested_ n_tw ce-adhoc \
  --jobs src/scala/com/tw ter/s mclusters_v2/scald ng/embedd ng/tw ce: nterested_ n_tw ce_largest_d m-adhoc,src/scala/com/tw ter/s mclusters_v2/scald ng/embedd ng/tw ce: nterested_ n_tw ce_louva n-adhoc,src/scala/com/tw ter/s mclusters_v2/scald ng/embedd ng/tw ce: nterested_ n_tw ce_connected_components-adhoc \
  --scm-paths "src/scala/com/tw ter/s mclusters_v2/scald ng/embedd ng/tw ce/*" \
  --autoplay \

 */*/
object  nterested nTw ceLargestD mAdhocApp
    extends  nterested nTw ceBaseApp[S mClustersEmbedd ng]
    w h AdhocExecut onApp {

  overr de def producerProducerS m lar yFnForCluster ng: (
    S mClustersEmbedd ng,
    S mClustersEmbedd ng
  ) => Double =
    S m lar yFunct ons.s mClustersMatch ngLargestD  ns on
  overr de def producerProducerS m lar yFnForClusterRepresentat ve: (
    S mClustersEmbedd ng,
    S mClustersEmbedd ng
  ) => Double =
    S m lar yFunct ons.s mClustersCos neS m lar y

  /**
   * Top-level  thod of t  appl cat on.
   */
  def runOnDateRange(
    args: Args
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que d: Un que D
  ): Execut on[Un ] = {

    runAdhocApp(
      new LargestD  ns onCluster ng thod(),
      new  do dRepresentat veSelect on thod[S mClustersEmbedd ng](
        producerProducerS m lar yFnForClusterRepresentat ve),
      ProducerEmbedd ngS ce.getAggregatableProducerEmbedd ngs,
      " nterested_ n_tw ce_by_largest_d m",
      "clusters_ mbers_largest_d m_ape_s m lar y",
      args.getOrElse("num-reducers", "4000").to nt
    )

  }
}

object  nterested nTw ceLargestD mMaxFavScoreAdhocApp
    extends  nterested nTw ceBaseApp[S mClustersEmbedd ng]
    w h AdhocExecut onApp {

  overr de def producerProducerS m lar yFnForCluster ng: (
    S mClustersEmbedd ng,
    S mClustersEmbedd ng
  ) => Double =
    S m lar yFunct ons.s mClustersMatch ngLargestD  ns on
  overr de def producerProducerS m lar yFnForClusterRepresentat ve: (
    S mClustersEmbedd ng,
    S mClustersEmbedd ng
  ) => Double =
    S m lar yFunct ons.s mClustersCos neS m lar y

  /**
   * Top-level  thod of t  appl cat on.
   */
  def runOnDateRange(
    args: Args
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que d: Un que D
  ): Execut on[Un ] = {

    runAdhocApp(
      new LargestD  ns onCluster ng thod(),
      new MaxFavScoreRepresentat veSelect on thod[S mClustersEmbedd ng](),
      ProducerEmbedd ngS ce.getAggregatableProducerEmbedd ngs,
      " nterested_ n_tw ce_by_largest_d m_fav_score",
      "clusters_ mbers_largest_d m_ape_s m lar y",
      args.getOrElse("num-reducers", "4000").to nt
    )

  }
}

object  nterested nTw ceLouva nAdhocApp
    extends  nterested nTw ceBaseApp[S mClustersEmbedd ng]
    w h AdhocExecut onApp {

  overr de def producerProducerS m lar yFnForCluster ng: (
    S mClustersEmbedd ng,
    S mClustersEmbedd ng
  ) => Double =
    S m lar yFunct ons.s mClustersCos neS m lar y
  overr de def producerProducerS m lar yFnForClusterRepresentat ve: (
    S mClustersEmbedd ng,
    S mClustersEmbedd ng
  ) => Double =
    S m lar yFunct ons.s mClustersCos neS m lar y

  /**
   * Top-level  thod of t  appl cat on.
   */
  def runOnDateRange(
    args: Args
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que d: Un que D
  ): Execut on[Un ] = {

    runAdhocApp(
      new Louva nCluster ng thod(
        args.requ red("cos ne_s m lar y_threshold").toDouble,
        args.opt onal("resolut on_factor").map(_.toDouble)),
      new  do dRepresentat veSelect on thod[S mClustersEmbedd ng](
        producerProducerS m lar yFnForClusterRepresentat ve),
      ProducerEmbedd ngS ce.getAggregatableProducerEmbedd ngs,
      " nterested_ n_tw ce_louva n",
      "clusters_ mbers_louva n_ape_s m lar y",
      args.getOrElse("num-reducers", "4000").to nt
    )

  }
}

object  nterested nTw ceConnectedComponentsAdhocApp
    extends  nterested nTw ceBaseApp[S mClustersEmbedd ng]
    w h AdhocExecut onApp {

  overr de def producerProducerS m lar yFnForCluster ng: (
    S mClustersEmbedd ng,
    S mClustersEmbedd ng
  ) => Double =
    S m lar yFunct ons.s mClustersCos neS m lar y
  overr de def producerProducerS m lar yFnForClusterRepresentat ve: (
    S mClustersEmbedd ng,
    S mClustersEmbedd ng
  ) => Double =
    S m lar yFunct ons.s mClustersCos neS m lar y

  /**
   * Top-level  thod of t  appl cat on.
   */
  def runOnDateRange(
    args: Args
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que d: Un que D
  ): Execut on[Un ] = {

    runAdhocApp(
      new ConnectedComponentsCluster ng thod(
        args.requ red("cos ne_s m lar y_threshold").toDouble),
      new  do dRepresentat veSelect on thod[S mClustersEmbedd ng](
        producerProducerS m lar yFnForClusterRepresentat ve),
      ProducerEmbedd ngS ce.getAggregatableProducerEmbedd ngs,
      " nterested_ n_tw ce_connected_components",
      "clusters_ mbers_connected_components_ape_s m lar y",
      args.getOrElse("num-reducers", "4000").to nt
    )
  }
}
