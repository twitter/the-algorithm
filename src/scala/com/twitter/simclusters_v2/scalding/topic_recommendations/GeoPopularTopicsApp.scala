package com.tw ter.s mclusters_v2.scald ng.top c_recom ndat ons

 mport com.tw ter.b ject on.Bufferable
 mport com.tw ter.b ject on. nject on
 mport com.tw ter.recos.ent  es.thr ftscala.Semant cCoreEnt y
 mport com.tw ter.recos.ent  es.thr ftscala.Semant cCoreEnt yScoreL st
 mport com.tw ter.recos.ent  es.thr ftscala.Semant cEnt yScore
 mport com.tw ter.scald ng.commons.s ce.Vers onedKeyValS ce
 mport com.tw ter.scald ng.Execut on
 mport com.tw ter.scald ng._
 mport com.tw ter.scald ng_ nternal.dalv2.DAL
 mport com.tw ter.scald ng_ nternal.dalv2.DALWr e._
 mport com.tw ter.scald ng_ nternal.dalv2.remote_access.Expl c Locat on
 mport com.tw ter.scald ng_ nternal.dalv2.remote_access.Proc2Atla
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal
 mport com.tw ter.s mclusters_v2.common.Semant cCoreEnt y d
 mport com.tw ter.s mclusters_v2.hdfs_s ces.GeopopularTopT et mpressedTop csScalaDataset
 mport com.tw ter.t  l nes.per_top c_ tr cs.thr ftscala.PerTop cAggregateEngage nt tr c
 mport com.tw ter.wtf.scald ng.jobs.common.AdhocExecut onApp
 mport com.tw ter.wtf.scald ng.jobs.common.Sc duledExecut onApp
 mport java.ut l.T  Zone
 mport t  l nes.data_process ng.jobs. tr cs.per_top c_ tr cs.PerTop cAggregateEngage ntScalaDataset

/**
 scald ng remote run \
 --target src/scala/com/tw ter/s mclusters_v2/scald ng/top c_recom ndat ons:geopopular_top_t ets_ mpressed_top cs_adhoc \
 --ma n-class com.tw ter.s mclusters_v2.scald ng.top c_recom ndat ons.GeoPopularTop csAdhocApp \
 --subm ter  hadoopnest1.atla.tw ter.com --user recos-platform \
 -- \
 --date 2020-03-28 --output_d r /user/recos-platform/adhoc/y _ldap/top cs_country_counts
 */
object GeoPopularTop csAdhocApp extends AdhocExecut onApp {
  overr de def runOnDateRange(
    args: Args
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): Execut on[Un ] = {
    val maxTop csPerCountry = args. nt("maxTop cs", 2000)
    val typedTsv = args.boolean("tsv")
     mpl c  val  nj:  nject on[L st[(Semant cCoreEnt y d, Double)], Array[Byte]] =
      Bufferable. nject onOf[L st[(Semant cCoreEnt y d, Double)]]

    val perTop cEngage ntLogData = DAL
      .read(PerTop cAggregateEngage ntScalaDataset, dateRange.prepend(Days(7)))
      .toTypedP pe
    val top csW hEngage nt =
      GeoPopularTop csApp
        .getPopularTop csFromLogs(perTop cEngage ntLogData, maxTop csPerCountry)
        .mapValues(_.toL st)

     f (typedTsv) {
      top csW hEngage nt.wr eExecut on(
        TypedTsv(args("/user/recos-platform/adhoc/y _ldap/top cs_country_counts_tsv"))
      )
    } else {
      top csW hEngage nt.wr eExecut on(
        Vers onedKeyValS ce[Str ng, L st[(Semant cCoreEnt y d, Double)]](args("output_d r"))
      )
    }
  }
}

/**
 capesospy-v2 update --bu ld_locally \
 --start_cron popular_top cs_per_country \
 src/scala/com/tw ter/s mclusters_v2/capesos_conf g/atla_proc3.yaml
 */
object GeoPopularTop csBatchApp extends Sc duledExecut onApp {
  overr de val f rstT  : R chDate = R chDate("2020-04-06")

  overr de val batch ncre nt: Durat on = Days(1)

  overr de def runOnDateRange(
    args: Args
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): Execut on[Un ] = {
    val maxTop csPerCountry = args. nt("maxTop cs", 2000)

    val geoPopularTop csPath: Str ng =
      "/user/cassowary/manhattan_sequence_f les/geo_popular_top_t et_ mpressed_top cs"

    // Read engage nt logs from t  past 7 days
    val perTop cEngage ntLogData = DAL
      .read(PerTop cAggregateEngage ntScalaDataset, dateRange.prepend(Days(7)))
      .w hRemoteReadPol cy(Expl c Locat on(Proc2Atla))
      .toTypedP pe

    val top csW hScores =
      GeoPopularTop csApp.getPopularTop csFromLogs(perTop cEngage ntLogData, maxTop csPerCountry)

    val top csW hEnt yScores = top csW hScores
      .mapValues(_.map {
        case (top c d, top cScore) =>
          Semant cEnt yScore(Semant cCoreEnt y(ent y d = top c d), top cScore)
      })
      .mapValues(Semant cCoreEnt yScoreL st(_))

    val wr eKeyValResultExec = top csW hEnt yScores
      .map { case (country, top cs) => KeyVal(country, top cs) }
      .wr eDALVers onedKeyValExecut on(
        GeopopularTopT et mpressedTop csScalaDataset,
        D.Suff x(geoPopularTop csPath)
      )
    wr eKeyValResultExec
  }
}

object GeoPopularTop csApp {

  def getPopularTop csFromLogs(
    engage ntLogs: TypedP pe[PerTop cAggregateEngage nt tr c],
    maxTop cs:  nt
  )(
     mpl c  un que d: Un que D
  ): TypedP pe[(Str ng, Seq[(Semant cCoreEnt y d, Double)])] = {
    val numTop cEngage ntsRead = Stat("num_top c_engage nts_read")
    val  nter d ate = engage ntLogs
      .map {
        case PerTop cAggregateEngage nt tr c(
              top c d,
              date d,
              country,
              page,
               em,
              engage ntType,
              engage ntCount,
              algor hmType,
              annotat onType) =>
          numTop cEngage ntsRead. nc()
          (
            top c d,
            date d,
            country,
            page,
             em,
            engage ntType,
            engage ntCount,
            algor hmType,
            annotat onType)
      }

    //   want to f nd t  top cs w h t  most  mpressed t ets  n each country
    // T  w ll ensure that t  top cs suggested as recom ndat ons also have t ets that can be recom nded
     nter d ate
      .collect {
        case (top c d, _, So (country), _,  em, engage ntType, engage ntCount, _, _)
             f  em == "T et" && engage ntType == " mpress on" =>
          ((country, top c d), engage ntCount)
      }
      .sumByKey // returns country-w se engage nts for top cs
      .map {
        case ((country, top c d), totalEngage ntCountryCount) =>
          (country, (top c d, totalEngage ntCountryCount.toDouble))
      }
      .group
      .sortedReverseTake(maxTop cs)(Order ng.by(_._2))
      .toTypedP pe
  }

}
