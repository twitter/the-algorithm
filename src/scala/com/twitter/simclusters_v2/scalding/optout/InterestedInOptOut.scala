package com.tw ter.s mclusters_v2.scald ng.optout

 mport com.tw ter.dal.cl ent.dataset.{KeyValDALDataset, SnapshotDALDataset}
 mport com.tw ter.scald ng.{
  Args,
  DateRange,
  Days,
  Durat on,
  Execut on,
  R chDate,
  TypedP pe,
  TypedTsv,
  Un que D
}
 mport com.tw ter.scald ng_ nternal.dalv2.DALWr e.D
 mport com.tw ter.scald ng_ nternal.dalv2.DALWr e._
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal
 mport com.tw ter.s mclusters_v2.common.{Cluster d, ModelVers ons, Semant cCoreEnt y d, User d}
 mport com.tw ter.s mclusters_v2.hdfs_s ces._
 mport com.tw ter.s mclusters_v2.scald ng. nferred_ent  es. nferredEnt  es
 mport com.tw ter.s mclusters_v2.thr ftscala.{
  ClusterType,
  ClustersUser s nterested n,
  Semant cCoreEnt yW hScore,
  UserTo nterested nClusters
}
 mport com.tw ter.wtf.scald ng.jobs.common.{AdhocExecut onApp, Sc duledExecut onApp}
 mport com.tw ter.s mclusters_v2.scald ng.common.TypedR chP pe._
 mport com.tw ter.s mclusters_v2.scald ng.common.Ut l
 mport java.ut l.T  Zone

object  nterested nOptOut {

  def f lterOptedOut nterested n(
     nterested nP pe: TypedP pe[(User d, ClustersUser s nterested n)],
    optedOutEnt  es: TypedP pe[(User d, Set[Semant cCoreEnt y d])],
    clusterToEnt  es: TypedP pe[(Cluster d, Seq[Semant cCoreEnt yW hScore])]
  ): TypedP pe[(User d, ClustersUser s nterested n)] = {

    val val d nterested n = S mClustersOptOutUt l.f lterOptedOutClusters(
      userToClusters =  nterested nP pe.mapValues(_.cluster dToScores.keySet.toSeq),
      optedOutEnt  es = optedOutEnt  es,
      leg bleClusters = clusterToEnt  es
    )

     nterested nP pe
      .leftJo n(val d nterested n)
      .mapValues {
        case (or g nal nterested n, val d nterested nOpt) =>
          val val d nterested n = val d nterested nOpt.getOrElse(Seq()).toSet

          or g nal nterested n.copy(
            cluster dToScores = or g nal nterested n.cluster dToScores.f lterKeys(val d nterested n)
          )
      }
      .f lter(_._2.cluster dToScores.nonEmpty)
  }

  /**
   * Wr es  nterested n data to HDFS
   */
  def wr e nterested nOutputExecut on(
     nterested n: TypedP pe[(User d, ClustersUser s nterested n)],
     nterested nDataset: KeyValDALDataset[KeyVal[Long, ClustersUser s nterested n]],
    outputPath: Str ng
  ): Execut on[Un ] = {
     nterested n
      .map { case (k, v) => KeyVal(k, v) }
      .wr eDALVers onedKeyValExecut on(
         nterested nDataset,
        D.Suff x(outputPath)
      )
  }

  /**
   * Convert  nterested n to thr ft structs, t n wr e to HDFS
   */
  def wr e nterested nThr ftOutputExecut on(
     nterested n: TypedP pe[(User d, ClustersUser s nterested n)],
    modelVers on: Str ng,
     nterested nThr ftDatset: SnapshotDALDataset[UserTo nterested nClusters],
    thr ftOutputPath: Str ng,
    dateRange: DateRange
  ): Execut on[Un ] = {
     nterested n
      .map {
        case (user d, clusters) =>
          UserTo nterested nClusters(user d, modelVers on, clusters.cluster dToScores)
      }
      .wr eDALSnapshotExecut on(
         nterested nThr ftDatset,
        D.Da ly,
        D.Suff x(thr ftOutputPath),
        D.EBLzo(),
        dateRange.end
      )
  }
}

/**
capesospy-v2 update --bu ld_locally --start_cron \
  --start_cron  nterested_ n_optout_da ly \
  src/scala/com/tw ter/s mclusters_v2/capesos_conf g/atla_proc.yaml
 */
object  nterested nOptOutDa lyBatchJob extends Sc duledExecut onApp {

  overr de def f rstT  : R chDate = R chDate("2019-11-24")

  overr de def batch ncre nt: Durat on = Days(1)

  overr de def runOnDateRange(
    args: Args
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): Execut on[Un ] = {

    val userOptoutEnt  es =
      S mClustersOptOutUt l
        .getP13nOptOutS ces(dateRange.emb ggen(Days(4)), ClusterType. nterested n)
        .count("num_users_w h_optouts")
        .forceToD sk

    val  nterested n2020P pe =  nterested nS ces
      .s mClustersRaw nterested n2020S ce(dateRange, t  Zone)
      .count("num_users_w h_2020_ nterested n")

    val  nterested nL e2020P pe =  nterested nS ces
      .s mClustersRaw nterested nL e2020S ce(dateRange, t  Zone)
      .count("num_users_w h_2020_ nterested n_l e")

    val clusterToEnt  es =  nferredEnt  es
      .getLeg bleEnt yEmbedd ngs(dateRange.prepend(Days(21)), t  Zone)
      .count("num_cluster_to_ent  es")

    val f ltered2020 nterested n =  nterested nOptOut
      .f lterOptedOut nterested n( nterested n2020P pe, userOptoutEnt  es, clusterToEnt  es)
      .count("num_users_w h_compl ant_2020_ nterested n")

    val wr e2020Exec =  nterested nOptOut.wr e nterested nOutputExecut on(
      f ltered2020 nterested n,
      S mclustersV2 nterested n20M145K2020ScalaDataset,
      DataPaths. nterested n2020Path
    )

    val wr e2020Thr ftExec =  nterested nOptOut.wr e nterested nThr ftOutputExecut on(
      f ltered2020 nterested n,
      ModelVers ons.Model20M145K2020,
      S mclustersV2UserTo nterested n20M145K2020ScalaDataset,
      DataPaths. nterested n2020Thr ftPath,
      dateRange
    )

    val san yC ck2020Exec = S mClustersOptOutUt l.san yC ckAndSendEma l(
      oldNumClustersPerUser =  nterested n2020P pe.map(_._2.cluster dToScores.s ze),
      newNumClustersPerUser = f ltered2020 nterested n.map(_._2.cluster dToScores.s ze),
      modelVers on = ModelVers ons.Model20M145K2020,
      alertEma l = S mClustersOptOutUt l.AlertEma l
    )

    val f ltered2020 nterested nL e =  nterested nOptOut
      .f lterOptedOut nterested n( nterested nL e2020P pe, userOptoutEnt  es, clusterToEnt  es)
      .count("num_users_w h_compl ant_2020_ nterested n_l e")

    val wr e2020L eExec =  nterested nOptOut.wr e nterested nOutputExecut on(
      f ltered2020 nterested nL e,
      S mclustersV2 nterested nL e20M145K2020ScalaDataset,
      DataPaths. nterested nL e2020Path
    )

    val wr e2020L eThr ftExec =  nterested nOptOut.wr e nterested nThr ftOutputExecut on(
      f ltered2020 nterested nL e,
      ModelVers ons.Model20M145K2020,
      S mclustersV2UserTo nterested nL e20M145K2020ScalaDataset,
      DataPaths. nterested nL e2020Thr ftPath,
      dateRange
    )

    val san yC ck2020L eExec = S mClustersOptOutUt l.san yC ckAndSendEma l(
      oldNumClustersPerUser =  nterested nL e2020P pe.map(_._2.cluster dToScores.s ze),
      newNumClustersPerUser = f ltered2020 nterested nL e.map(_._2.cluster dToScores.s ze),
      modelVers on = ModelVers ons.Model20M145K2020,
      alertEma l = S mClustersOptOutUt l.AlertEma l
    )

    Ut l.pr ntCounters(
      Execut on.z p(
        Execut on.z p(
          wr e2020Exec,
          wr e2020Thr ftExec,
          san yC ck2020Exec),
        Execut on.z p(
          wr e2020L eExec,
          wr e2020L eThr ftExec,
          san yC ck2020L eExec
        )
      )
    )
  }
}

/**
 * For debugg ng only. Does a f lter ng run and pr nts t  d fferences before/after t  opt out

 scald ng remote run --target src/scala/com/tw ter/s mclusters_v2/scald ng/optout: nterested_ n_optout-adhoc \
 --user cassowary --cluster blueb rd-qus1 \
 --ma n-class com.tw ter.s mclusters_v2.scald ng.optout. nterested nOptOutAdhocJob -- \
 --keytab /var/l b/tss/keys/fluffy/keytabs/cl ent/cassowary.keytab \
 --pr nc pal serv ce_acoount@TW TTER.B Z \
 -- \
 --outputD r /user/cassowary/adhoc/ nterested n_optout \
 --date 2020-09-03
 */
object  nterested nOptOutAdhocJob extends AdhocExecut onApp {
  overr de def runOnDateRange(
    args: Args
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): Execut on[Un ] = {
    val outputD r = args("outputD r")

    val  nterested nP pe =  nterested nS ces
      .s mClusters nterested nUpdatedS ce(dateRange, t  Zone)
      .count("num_users_w h_ nterested n")

    val userOptoutEnt  es: TypedP pe[(User d, Set[Semant cCoreEnt y d])] =
      S mClustersOptOutUt l
        .getP13nOptOutS ces(dateRange.emb ggen(Days(4)), ClusterType. nterested n)
        .count("num_users_w h_optouts")

    val clusterToEnt  es =  nferredEnt  es
      .getLeg bleEnt yEmbedd ngs(dateRange, t  Zone)
      .count("num_cluster_to_ent  es")

    val f ltered nterested nP pe =  nterested nOptOut
      .f lterOptedOut nterested n(
         nterested nP pe,
        userOptoutEnt  es,
        clusterToEnt  es
      )
      .count("num_users_w h_ nterested n_after_optout")

    val output =  nterested nP pe
      .jo n(f ltered nterested nP pe)
      .f lter {
        case (user d, (or g nal nterested n, f ltered)) =>
          or g nal nterested n.cluster dToScores != f ltered.cluster dToScores
      }
      .jo n(userOptoutEnt  es)
      .map {
        case (user d, ((or g nal nterested n, f ltered), optoutEnt  es)) =>
          Seq(
            "user d=" + user d,
            "or g nal nterested nVers on=" + or g nal nterested n.knownForModelVers on,
            "or g nal nterested n=" + or g nal nterested n.cluster dToScores.keySet,
            "f ltered nterested n=" + f ltered.knownForModelVers on,
            "f ltered nterested n=" + f ltered.cluster dToScores.keySet,
            "optoutEnt  es=" + optoutEnt  es
          ).mkStr ng("\t")
      }

    Ut l.pr ntCounters(
      output.wr eExecut on(TypedTsv(outputD r))
    )
  }
}
