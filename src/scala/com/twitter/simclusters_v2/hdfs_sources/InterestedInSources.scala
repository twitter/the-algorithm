package com.tw ter.s mclusters_v2.hdfs_s ces

 mport com.tw ter.dal.cl ent.dataset.KeyValDALDataset
 mport com.tw ter.scald ng.{DateOps, DateRange, Days, TypedP pe}
 mport com.tw ter.scald ng_ nternal.dalv2.DAL
 mport com.tw ter.scald ng_ nternal.dalv2.remote_access.{Expl c Locat on, ProcAtla}
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.s mclusters_v2.thr ftscala.ModelVers on
 mport com.tw ter.s mclusters_v2.thr ftscala.ClustersUser s nterested n
 mport java.ut l.T  Zone

object  nterested nS ces {

  pr vate val ModelVers on nterested nDatasetMap: Map[ModelVers on, KeyValDALDataset[
    KeyVal[User d, ClustersUser s nterested n]
  ]] = Map(
    ModelVers on.Model20m145kDec11 -> S mclustersV2 nterested nScalaDataset,
    ModelVers on.Model20m145kUpdated -> S mclustersV2 nterested n20M145KUpdatedScalaDataset,
    ModelVers on.Model20m145k2020 -> S mclustersV2 nterested n20M145K2020ScalaDataset
  )

  /**
   *  nternal vers on, not PDP compl ant, not to be used outs de s mclusters_v2
   * Reads 20M145KDec11 product on  nterested n data from atla-proc, w h a 14-day extended w ndow
   */
  pr vate[s mclusters_v2] def s mClustersRaw nterested nDec11S ce(
    dateRange: DateRange,
    t  Zone: T  Zone
  ): TypedP pe[(User d, ClustersUser s nterested n)] = {

    DAL
      .readMostRecentSnapshot(
        S mclustersV2Raw nterested n20M145KDec11ScalaDataset,
        dateRange.prepend(Days(14)(t  Zone))
      )
      .w hRemoteReadPol cy(Expl c Locat on(ProcAtla))
      .toTypedP pe
      .map {
        case KeyVal(user d, clustersUser s nterested n) =>
          (user d, clustersUser s nterested n)
      }
  }

  /**
   *  nternal vers on, not PDP compl ant, not to be used outs de s mclusters_v2
   * Reads 20M145KUpdated  nterested n data from atla-proc, w h a 14-day extended w ndow
   */
  pr vate[s mclusters_v2] def s mClustersRaw nterested nUpdatedS ce(
    dateRange: DateRange,
    t  Zone: T  Zone
  ): TypedP pe[(User d, ClustersUser s nterested n)] = {
    DAL
      .readMostRecentSnapshot(
        S mclustersV2Raw nterested n20M145KUpdatedScalaDataset,
        dateRange.prepend(Days(14)(t  Zone))
      )
      .w hRemoteReadPol cy(Expl c Locat on(ProcAtla))
      .toTypedP pe.map {
        case KeyVal(user d, clustersUser s nterested n) =>
          (user d, clustersUser s nterested n)
      }
  }

  /**
   *  nternal vers on, not PDP compl ant, not to be used outs de s mclusters_v2
   * Reads 20M145K2020  nterested n data from atla-proc, w h a 14-day extended w ndow
   */
  pr vate[s mclusters_v2] def s mClustersRaw nterested n2020S ce(
    dateRange: DateRange,
    t  Zone: T  Zone
  ): TypedP pe[(User d, ClustersUser s nterested n)] = {
    DAL
      .readMostRecentSnapshot(
        S mclustersV2Raw nterested n20M145K2020ScalaDataset,
        dateRange.prepend(Days(14)(t  Zone))
      )
      .w hRemoteReadPol cy(Expl c Locat on(ProcAtla))
      .toTypedP pe.map {
        case KeyVal(user d, clustersUser s nterested n) =>
          (user d, clustersUser s nterested n)
      }
  }

  pr vate[s mclusters_v2] def s mClustersRaw nterested nL e2020S ce(
    dateRange: DateRange,
    t  Zone: T  Zone
  ): TypedP pe[(User d, ClustersUser s nterested n)] = {
    DAL
      .readMostRecentSnapshot(
        S mclustersV2Raw nterested nL e20M145K2020ScalaDataset,
        dateRange.extend(Days(14)(t  Zone)))
      .w hRemoteReadPol cy(Expl c Locat on(ProcAtla))
      .toTypedP pe.map {
        case KeyVal(user d, clustersUser s nterested n) =>
          (user d, clustersUser s nterested n)
      }
  }

  /**
   * Reads 20M145KDec11 product on  nterested n data from atla-proc, w h a 14-day extended w ndow
   */
  def s mClusters nterested nDec11S ce(
    dateRange: DateRange,
    t  Zone: T  Zone
  ): TypedP pe[(User d, ClustersUser s nterested n)] = {

    DAL
      .readMostRecentSnapshot(
        S mclustersV2 nterested nScalaDataset,
        dateRange.prepend(Days(14)(t  Zone)))
      .w hRemoteReadPol cy(Expl c Locat on(ProcAtla))
      .toTypedP pe.map {
        case KeyVal(user d, clustersUser s nterested n) =>
          (user d, clustersUser s nterested n)
      }
  }

  /**
   * Reads 20M145KUpdated  nterested n data from atla-proc, w h a 14-day extended w ndow
   */
  def s mClusters nterested nUpdatedS ce(
    dateRange: DateRange,
    t  Zone: T  Zone
  ): TypedP pe[(User d, ClustersUser s nterested n)] = {
    DAL
      .readMostRecentSnapshot(
        S mclustersV2 nterested n20M145KUpdatedScalaDataset,
        dateRange.prepend(Days(14)(t  Zone))
      )
      .w hRemoteReadPol cy(Expl c Locat on(ProcAtla))
      .toTypedP pe.map {
        case KeyVal(user d, clustersUser s nterested n) =>
          (user d, clustersUser s nterested n)
      }
  }

  /**
   * Reads 20M145K2020  nterested n data from atla-proc, w h a 14-day extended w ndow
   */
  def s mClusters nterested n2020S ce(
    dateRange: DateRange,
    t  Zone: T  Zone
  ): TypedP pe[(User d, ClustersUser s nterested n)] = {
    DAL
      .readMostRecentSnapshot(
        S mclustersV2 nterested n20M145K2020ScalaDataset,
        dateRange.prepend(Days(14)(t  Zone))
      )
      .w hRemoteReadPol cy(Expl c Locat on(ProcAtla))
      .toTypedP pe.map {
        case KeyVal(user d, clustersUser s nterested n) =>
          (user d, clustersUser s nterested n)
      }
  }

  /**
   * Reads  nterested n data based on ModelVers on from atla-proc, w h a 14-day extended w ndow
   */
  def s mClusters nterested nS ce(
    modelVers on: ModelVers on,
    dateRange: DateRange,
    t  Zone: T  Zone
  ): TypedP pe[(User d, ClustersUser s nterested n)] = {

    DAL
      .readMostRecentSnapshot(
        ModelVers on nterested nDatasetMap(modelVers on),
        dateRange.prepend(Days(14)(t  Zone))
      )
      .w hRemoteReadPol cy(Expl c Locat on(ProcAtla))
      .toTypedP pe.map {
        case KeyVal(user d, clustersUser s nterested n) =>
          (user d, clustersUser s nterested n)
      }
  }

}
