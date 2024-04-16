package com.tw ter.s mclusters_v2.hdfs_s ces

 mport com.tw ter.scald ng.DateRange
 mport com.tw ter.scald ng.TypedP pe
 mport com.tw ter.scald ng_ nternal.dalv2.DAL
 mport com.tw ter.scald ng_ nternal.dalv2.remote_access.AllowCrossClusterSa DC
 mport com.tw ter.scald ng_ nternal.dalv2.remote_access.Expl c Locat on
 mport com.tw ter.scald ng_ nternal.dalv2.remote_access.Proc3Atla
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal
 mport com.tw ter.s mclusters_v2.thr ftscala.Embedd ngType
 mport com.tw ter.s mclusters_v2.thr ftscala. nternal d
 mport com.tw ter.s mclusters_v2.thr ftscala.ModelVers on
 mport com.tw ter.s mclusters_v2.thr ftscala.S mClustersEmbedd ng
 mport com.tw ter.s mclusters_v2.thr ftscala.S mClustersEmbedd ng d
 mport com.tw ter.s mclusters_v2.thr ftscala.TopS mClustersW hScore

object ProducerEmbedd ngS ces {

  /**
   *  lper funct on to retr eve producer S mClusters embedd ngs w h t  legacy `TopS mClustersW hScore`
   * value type.
   */
  def producerEmbedd ngS ceLegacy(
    embedd ngType: Embedd ngType,
    modelVers on: ModelVers on
  )(
     mpl c  dateRange: DateRange
  ): TypedP pe[(Long, TopS mClustersW hScore)] = {
    val producerEmbedd ngDataset = (embedd ngType, modelVers on) match {
      case (Embedd ngType.ProducerFollowBasedSemant cCoreEnt y, ModelVers on.Model20m145kDec11) =>
        ProducerTopKS mclusterEmbedd ngsByFollowScoreScalaDataset
      case (Embedd ngType.ProducerFavBasedSemant cCoreEnt y, ModelVers on.Model20m145kDec11) =>
        ProducerTopKS mclusterEmbedd ngsByFavScoreScalaDataset
      case (
            Embedd ngType.ProducerFollowBasedSemant cCoreEnt y,
            ModelVers on.Model20m145kUpdated) =>
        ProducerTopKS mclusterEmbedd ngsByFollowScoreUpdatedScalaDataset
      case (Embedd ngType.ProducerFavBasedSemant cCoreEnt y, ModelVers on.Model20m145kUpdated) =>
        ProducerTopKS mclusterEmbedd ngsByFavScoreUpdatedScalaDataset
      case (_, _) =>
        throw new ClassNotFoundExcept on(
          "Unsupported embedd ng type: " + embedd ngType + " and model vers on: " + modelVers on)
    }

    DAL
      .readMostRecentSnapshot(producerEmbedd ngDataset).w hRemoteReadPol cy(
        AllowCrossClusterSa DC)
      .toTypedP pe.map {
        case KeyVal(producer d, topS mClustersW hScore) =>
          (producer d, topS mClustersW hScore)
      }
  }

  def producerEmbedd ngS ce(
    embedd ngType: Embedd ngType,
    modelVers on: ModelVers on
  )(
     mpl c  dateRange: DateRange
  ): TypedP pe[(Long, S mClustersEmbedd ng)] = {
    val producerEmbedd ngDataset = (embedd ngType, modelVers on) match {
      case (Embedd ngType.AggregatableLogFavBasedProducer, ModelVers on.Model20m145k2020) =>
        AggregatableProducerS mclustersEmbedd ngsByLogFavScore2020ScalaDataset
      case (Embedd ngType.AggregatableFollowBasedProducer, ModelVers on.Model20m145k2020) =>
        AggregatableProducerS mclustersEmbedd ngsByFollowScore2020ScalaDataset
      case (Embedd ngType.RelaxedAggregatableLogFavBasedProducer, ModelVers on.Model20m145k2020) =>
        AggregatableProducerS mclustersEmbedd ngsByLogFavScoreRelaxedFavEngage ntThreshold2020ScalaDataset
      case (_, _) =>
        throw new ClassNotFoundExcept on(
          "Unsupported embedd ng type: " + embedd ngType + " and model vers on: " + modelVers on)
    }

    DAL
      .readMostRecentSnapshot(
        producerEmbedd ngDataset
      )
      .w hRemoteReadPol cy(Expl c Locat on(Proc3Atla))
      .toTypedP pe
      .map {
        case KeyVal(
              S mClustersEmbedd ng d(_, _,  nternal d.User d(producer d: Long)),
              embedd ng: S mClustersEmbedd ng) =>
          (producer d, embedd ng)
      }
  }

}
