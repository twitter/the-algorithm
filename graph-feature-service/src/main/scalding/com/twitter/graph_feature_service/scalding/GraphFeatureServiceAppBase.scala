package com.tw ter.graph_feature_serv ce.scald ng

 mport com.tw ter.scald ng._
 mport com.tw ter.scald ng_ nternal.job.Tw terExecut onApp
 mport com.tw ter.scald ng_ nternal.job.analyt cs_batch.{
  Analyt csBatchExecut on,
  Analyt csBatchExecut onArgs,
  BatchDescr pt on,
  BatchF rstT  ,
  Batch ncre nt,
  Tw terSc duledExecut onApp
}
 mport java.ut l.T  Zone

/**
 * Each job only needs to  mple nt t  runOnDateRange() funct on.   makes   eas er for test ng.
 */
tra  GraphFeatureServ ceBaseJob {
   mpl c  val t  Zone: T  Zone = DateOps.UTC
   mpl c  val dateParser: DateParser = DateParser.default

  def runOnDateRange(
    enableValueGraphs: Opt on[Boolean] = None,
    enableKeyGraphs: Opt on[Boolean] = None
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): Execut on[Un ]

  /**
   * Pr nt custom zed counters  n t  log
   */
  def pr nterCounters[T](execut on: Execut on[T]): Execut on[Un ] = {
    execut on.getCounters
      .flatMap {
        case (_, counters) =>
          counters.toMap.toSeq
            .sortBy(e => (e._1.group, e._1.counter))
            .foreach {
              case (statKey, value) =>
                pr ntln(s"${statKey.group}\t${statKey.counter}\t$value")
            }
          Execut on.un 
      }
  }
}

/**
 * Tra  that wraps th ngs about adhoc jobs.
 */
tra  GraphFeatureServ ceAdhocBaseApp extends Tw terExecut onApp w h GraphFeatureServ ceBaseJob {
  overr de def job: Execut on[Un ] = Execut on.w h d {  mpl c  un que d =>
    Execut on.getArgs.flatMap { args: Args =>
       mpl c  val dateRange: DateRange = DateRange.parse(args.l st("date"))(t  Zone, dateParser)
      pr nterCounters(runOnDateRange())
    }
  }
}

/**
 * Tra  that wraps th ngs about sc duled jobs.
 *
 * A new da ly app only needs to declare t  start ng date.
 */
tra  GraphFeatureServ ceSc duledBaseApp
    extends Tw terSc duledExecut onApp
    w h GraphFeatureServ ceBaseJob {

  def f rstT  : R chDate // for example: R chDate("2018-02-21")

  def batch ncre nt: Durat on = Days(1)

  overr de def sc duledJob: Execut on[Un ] = Execut on.w h d {  mpl c  un que d =>
    val analyt csArgs = Analyt csBatchExecut onArgs(
      batchDesc = BatchDescr pt on(getClass.getNa ),
      f rstT   = BatchF rstT  (f rstT  ),
      batch ncre nt = Batch ncre nt(batch ncre nt)
    )

    Analyt csBatchExecut on(analyt csArgs) {  mpl c  dateRange =>
      pr nterCounters(runOnDateRange())
    }
  }
}
