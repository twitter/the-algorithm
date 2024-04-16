package com.tw ter.graph_feature_serv ce.scald ng

 mport com.tw ter.scald ng.DateRange
 mport com.tw ter.scald ng.Execut on
 mport com.tw ter.scald ng.R chDate
 mport com.tw ter.scald ng.Un que D
 mport java.ut l.Calendar
 mport java.ut l.T  Zone
 mport sun.ut l.calendar.BaseCalendar

/**
 * To launch an adhoc run:
 *
  scald ng remote run --target graph-feature-serv ce/src/ma n/scald ng/com/tw ter/graph_feature_serv ce/scald ng:graph_feature_serv ce_adhoc_job
 */
object GraphFeatureServ ceAdhocApp
    extends GraphFeatureServ ceMa nJob
    w h GraphFeatureServ ceAdhocBaseApp {}

/**
 * To sc dule t  job, upload t  workflows conf g (only requ red for t  f rst t   and subsequent conf g changes):
 * scald ng workflow upload --jobs graph-feature-serv ce/src/ma n/scald ng/com/tw ter/graph_feature_serv ce/scald ng:graph_feature_serv ce_da ly_job --autoplay --bu ld-cron-sc dule "20 23 1 * *"
 *   can t n bu ld from t  U  by cl ck ng "Bu ld" and past ng  n y  remote branch, or leave   empty  f   redeploy ng from master.
 * T  workflows conf g above should automat cally tr gger once each month.
 */
object GraphFeatureServ ceSc duledApp
    extends GraphFeatureServ ceMa nJob
    w h GraphFeatureServ ceSc duledBaseApp {
  overr de def f rstT  : R chDate = R chDate("2018-05-18")

  overr de def runOnDateRange(
    enableValueGraphs: Opt on[Boolean],
    enableKeyGraphs: Opt on[Boolean]
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): Execut on[Un ] = {
    // Only run t  value Graphs on Tuesday, Thursday, Saturday
    val overr deEnableValueGraphs = {
      val dayOf ek = dateRange.start.toCalendar.get(Calendar.DAY_OF_WEEK)
      dayOf ek == BaseCalendar.TUESDAY |
        dayOf ek == BaseCalendar.THURSDAY |
        dayOf ek == BaseCalendar.SATURDAY
    }

    super.runOnDateRange(
      So (true),
      So (false) // d sable key Graphs s nce   are not us ng t m  n product on
    )
  }
}
