packagelon com.twittelonr.graph_felonaturelon_selonrvicelon.scalding

import com.twittelonr.scalding.DatelonRangelon
import com.twittelonr.scalding.elonxeloncution
import com.twittelonr.scalding.RichDatelon
import com.twittelonr.scalding.UniquelonID
import java.util.Calelonndar
import java.util.TimelonZonelon
import sun.util.calelonndar.BaselonCalelonndar

/**
 * To launch an adhoc run:
 *
  scalding relonmotelon run --targelont graph-felonaturelon-selonrvicelon/src/main/scalding/com/twittelonr/graph_felonaturelon_selonrvicelon/scalding:graph_felonaturelon_selonrvicelon_adhoc_job
 */
objelonct GraphFelonaturelonSelonrvicelonAdhocApp
    elonxtelonnds GraphFelonaturelonSelonrvicelonMainJob
    with GraphFelonaturelonSelonrvicelonAdhocBaselonApp {}

/**
 * To schelondulelon thelon job, upload thelon workflows config (only relonquirelond for thelon first timelon and subselonquelonnt config changelons):
 * scalding workflow upload --jobs graph-felonaturelon-selonrvicelon/src/main/scalding/com/twittelonr/graph_felonaturelon_selonrvicelon/scalding:graph_felonaturelon_selonrvicelon_daily_job --autoplay --build-cron-schelondulelon "20 23 1 * *"
 * You can thelonn build from thelon UI by clicking "Build" and pasting in your relonmotelon branch, or lelonavelon it elonmpty if you'relon relondelonploying from mastelonr.
 * Thelon workflows config abovelon should automatically triggelonr oncelon elonach month.
 */
objelonct GraphFelonaturelonSelonrvicelonSchelondulelondApp
    elonxtelonnds GraphFelonaturelonSelonrvicelonMainJob
    with GraphFelonaturelonSelonrvicelonSchelondulelondBaselonApp {
  ovelonrridelon delonf firstTimelon: RichDatelon = RichDatelon("2018-05-18")

  ovelonrridelon delonf runOnDatelonRangelon(
    elonnablelonValuelonGraphs: Option[Boolelonan],
    elonnablelonKelonyGraphs: Option[Boolelonan]
  )(
    implicit datelonRangelon: DatelonRangelon,
    timelonZonelon: TimelonZonelon,
    uniquelonID: UniquelonID
  ): elonxeloncution[Unit] = {
    // Only run thelon valuelon Graphs on Tuelonsday, Thursday, Saturday
    val ovelonrridelonelonnablelonValuelonGraphs = {
      val dayOfWelonelonk = datelonRangelon.start.toCalelonndar.gelont(Calelonndar.DAY_OF_WelonelonK)
      dayOfWelonelonk == BaselonCalelonndar.TUelonSDAY |
        dayOfWelonelonk == BaselonCalelonndar.THURSDAY |
        dayOfWelonelonk == BaselonCalelonndar.SATURDAY
    }

    supelonr.runOnDatelonRangelon(
      Somelon(truelon),
      Somelon(falselon) // disablelon kelony Graphs sincelon welon arelon not using thelonm in production
    )
  }
}
