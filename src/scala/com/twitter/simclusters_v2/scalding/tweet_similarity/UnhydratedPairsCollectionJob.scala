package com.tw ter.s mclusters_v2.scald ng.t et_s m lar y

 mport com.tw ter.ads.dataserv ce_account.snapshot.jobs.DbSnapshotsPromotedT etsScalaDataset
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.dal.cl ent.dataset.T  Part  onedDALDataset
 mport com.tw ter.scald ng._
 mport com.tw ter.scald ng_ nternal.dalv2.DAL
 mport com.tw ter.scald ng_ nternal.dalv2.DALWr e._
 mport com.tw ter.scald ng_ nternal.dalv2.remote_access.Expl c Locat on
 mport com.tw ter.scald ng_ nternal.dalv2.remote_access.ProcrevAtla
 mport com.tw ter.scald ng_ nternal.job.Tw terExecut onApp
 mport com.tw ter.s mclusters_v2.scald ng.t et_s m lar y.T etPa rLabelCollect onUt l.MaxFavPerUser
 mport com.tw ter.s mclusters_v2.thr ftscala.LabelledT etPa rs
 mport com.tw ter.s mclusters_v2.thr ftscala.{FeaturedT et => FeaturedT etThr ft}
 mport com.tw ter.wtf.scald ng.jobs.common.Sc duledExecut onApp
 mport java.ut l.T  Zone

/**
 * Collect unhydrated tra n ng pa rs for superv sed t et s m lar y.
 *  re're t  steps for t  job
 * 1) Cons der non-promoted t ets that are created w h n t  g ven #lookback days
 * 2) From t  t ets  n 1), get co-engaged pa rs
 * 3) Take all t ets shown  n 2), and get co- mpressed pa rs. Note that   take all t ets (not t et pa rs)  n 2).
 *    That  s, a co- mpressed pa rs (t1,t2) w ll be cons dered  ff t1 appears  n 2) and t2 appears  n 2).
 *    But (t1, t2) doesn't need to appear as a pa r  n 2).
 * 4) Compute labels from co-engaged pa rs and co- mpressed pa rs.
 *    A pa r  s true  f  s user has co-engaged t  pa r, and  s false  f ot rw se.
 */
object UnhydratedPa rsCollect onJob {
  //t ets have to be created w h n dateRange - lookbackdays  n order to be cons dered
  val LookbackDays = 2

  def getLabelledPa rs(
    dateRange: DateRange,
    t  fra : Long,
    maxSamplesPerClass:  nt,
    dalDataset: T  Part  onedDALDataset[LabelledT etPa rs],
    outputPath: Str ng
  )(
     mpl c  t  Zone: T  Zone
  ): Execut on[Un ] = {

    val promotedT ets = DAL
      .readMostRecentSnapshot(DbSnapshotsPromotedT etsScalaDataset, dateRange)
      .w hRemoteReadPol cy(Expl c Locat on(ProcrevAtla))
      .toTypedP pe

    val t etAuthorPa rs =
      T etPa rLabelCollect onUt l.getT etAuthorPa rs(dateRange.prepend(Days(LookbackDays)))

    val t ets =
      T etPa rLabelCollect onUt l.getNonPromotedT ets(promotedT ets, t etAuthorPa rs.keys)

    val coengagedPa rs = T etPa rLabelCollect onUt l.getCoengagedPa rs(
      T etPa rLabelCollect onUt l.getFavEvents(dateRange, MaxFavPerUser),
      t ets,
      t  fra )

    val engagedT ets = coengagedPa rs.map {
      // Cons der only query t et b/c coengagedPa rs conta ns both (t1,t2) and (t2,t1)
      case (_, queryFeaturedT et, _, _) => queryFeaturedT et.t et
    }.d st nct

    val co mpressedPa rs = T etPa rLabelCollect onUt l
      .getCo mpressedPa rs(
        T etPa rLabelCollect onUt l.get mpress onEvents(dateRange),
        engagedT ets,
        t  fra )

    val rawLabelledPa rs =
      T etPa rLabelCollect onUt l.computeLabelledT etPa rs(coengagedPa rs, co mpressedPa rs)

    val labelledPa rs =
       f (maxSamplesPerClass > 0)
        T etPa rLabelCollect onUt l.getQueryT etBalancedClassPa rs(
          rawLabelledPa rs,
          maxSamplesPerClass)
      else
        rawLabelledPa rs

    val perQueryStatsExec =
       f (maxSamplesPerClass > 0) {
        Execut on
          .z p(
            T etPa rLabelCollect onUt l
              .getPerQueryStatsExec(rawLabelledPa rs, s"$outputPath/per_query_stats", "raw"),
            T etPa rLabelCollect onUt l
              .getPerQueryStatsExec(labelledPa rs, s"$outputPath/per_query_stats", "f nal")
          ).un 
      } else {
        T etPa rLabelCollect onUt l.getPerQueryStatsExec(
          labelledPa rs,
          s"$outputPath/per_query_stats",
          "f nal")
      }

    Execut on
      .z p(
        labelledPa rs
          .map {
            case (queryFeaturedT et, cand dateFeaturedT et, label) =>
              LabelledT etPa rs(
                FeaturedT etThr ft(
                  t et d = queryFeaturedT et.t et,
                  t  stamp = queryFeaturedT et.t  stamp),
                FeaturedT etThr ft(
                  t et d = cand dateFeaturedT et.t et,
                  t  stamp = cand dateFeaturedT et.t  stamp),
                label
              )
          }
          .wr eDALExecut on(dalDataset, D.Da ly, D.Suff x(outputPath), D.EBLzo())(dateRange),
        perQueryStatsExec
      ).un 
  }
}

/** To run:
 * scald ng remote run --target src/scala/com/tw ter/s mclusters_v2/scald ng/t et_s m lar y:unhydrated_pa r_collect on-adhoc \
  --user cassowary \
  --subm ter hadoopnest2.atla.tw ter.com \
  --hadoop-propert es "mapreduce.reduce.java.opts=-Xmx8000m mapreduce.reduce. mory.mb=8000 scald ng.w h.reducers.set.expl c ly=true mapreduce.job.reduces=2000 mapreduce.task.t  out=0" \
  --ma n-class com.tw ter.s mclusters_v2.scald ng.t et_s m lar y.UnhydratedPa rsCollect onAdhocApp -- \
  --date 2020-03-04 \
  --output_path /user/cassowary/adhoc/unhydrated_pa rs/2020-03-04_class_balanced \
  --samples_per_query_t et_class 2000
 * */
object UnhydratedPa rsCollect onAdhocApp extends Tw terExecut onApp {
   mpl c  val t  Zone: T  Zone = DateOps.UTC
   mpl c  val dateParser: DateParser = DateParser.default

  overr de def job: Execut on[Un ] =
    Execut on.w h d {  mpl c  un que d =>
      Execut on.w hArgs { args: Args =>
         mpl c  val dateRange: DateRange = DateRange.parse(args.l st("date"))
        val maxSamplesPerClass:  nt = args. nt("samples_per_query_t et_class", default = 2000)
        val t  fra :  nt = 30
        val outputPath: Str ng = s"${args("output_path")}_${t  fra }m n"

        UnhydratedPa rsCollect onJob.getLabelledPa rs(
          dateRange,
          t  fra .m nute. nM ll seconds,
          maxSamplesPerClass,
          T etS m lar yUnhydratedPa rs30M nScalaDataset,
          outputPath
        )
      }
    }
}

/**
capesospy-v2 update --bu ld_locally --start_cron \
unhydrated_pa r_collect on_30m n src/scala/com/tw ter/s mclusters_v2/capesos_conf g/atla_proc3.yaml
 */
object UnhydratedPa rsCollect on30M nSc duledApp extends Sc duledExecut onApp {

  overr de def batch ncre nt: Durat on = H s(24)
  overr de def f rstT  : R chDate = R chDate("2020-03-26")

  overr de def runOnDateRange(
    args: Args
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): Execut on[Un ] = {
    val maxSamplesPerClass:  nt = args. nt("samples_per_query_t et_class", default = 2000)
    val t  fra :  nt = 30
    val outputPath: Str ng =
      s"/user/cassowary/processed/t et_s m lar y/unhydrated_pa rs_${t  fra }m n"

    UnhydratedPa rsCollect onJob.getLabelledPa rs(
      dateRange,
      t  fra .m nute. nM ll seconds,
      maxSamplesPerClass,
      T etS m lar yUnhydratedPa rs30M nScalaDataset,
      outputPath)
  }
}

/**
capesospy-v2 update --bu ld_locally --start_cron \
unhydrated_pa r_collect on_120m n src/scala/com/tw ter/s mclusters_v2/capesos_conf g/atla_proc3.yaml
 */
object UnhydratedPa rsCollect on120M nSc duledApp extends Sc duledExecut onApp {

  overr de def batch ncre nt: Durat on = H s(24)
  overr de def f rstT  : R chDate = R chDate("2020-03-26")

  overr de def runOnDateRange(
    args: Args
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): Execut on[Un ] = {
    val maxSamplesPerClass:  nt = args. nt("samples_per_query_t et_class", default = 2000)
    val t  fra :  nt = 120
    val outputPath: Str ng =
      s"/user/cassowary/processed/t et_s m lar y/unhydrated_pa rs_${t  fra }m n"

    UnhydratedPa rsCollect onJob.getLabelledPa rs(
      dateRange,
      t  fra .m nute. nM ll seconds,
      maxSamplesPerClass,
      T etS m lar yUnhydratedPa rs120M nScalaDataset,
      outputPath)
  }
}
