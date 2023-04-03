packagelon com.twittelonr.follow_reloncommelonndations.logging

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.follow_reloncommelonndations.common.constants.GuicelonNamelondConstants
import com.twittelonr.follow_reloncommelonndations.common.modelonls.HasIsSoftUselonr
import com.twittelonr.follow_reloncommelonndations.configapi.params.GlobalParams
import com.twittelonr.follow_reloncommelonndations.logging.thriftscala.ReloncommelonndationLog
import com.twittelonr.follow_reloncommelonndations.modelonls.DelonbugParams
import com.twittelonr.follow_reloncommelonndations.modelonls.ReloncommelonndationFlowData
import com.twittelonr.follow_reloncommelonndations.modelonls.ReloncommelonndationRelonquelonst
import com.twittelonr.follow_reloncommelonndations.modelonls.ReloncommelonndationRelonsponselon
import com.twittelonr.follow_reloncommelonndations.modelonls.ScoringUselonrRelonquelonst
import com.twittelonr.follow_reloncommelonndations.modelonls.ScoringUselonrRelonsponselon
import com.twittelonr.injelonct.annotations.Flag
import com.twittelonr.logging.LoggelonrFactory
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.ClielonntContelonxt
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.HasClielonntContelonxt
import com.twittelonr.scribelonlib.marshallelonrs.ClielonntDataProvidelonr
import com.twittelonr.scribelonlib.marshallelonrs.elonxtelonrnalRelonfelonrelonrDataProvidelonr
import com.twittelonr.scribelonlib.marshallelonrs.ScribelonSelonrialization
import com.twittelonr.timelonlinelons.configapi.HasParams
import com.twittelonr.util.Timelon
import javax.injelonct.Injelonct
import javax.injelonct.Namelond
import javax.injelonct.Singlelonton

/**
 * This is thelon standard logging class welon uselon to log data into:
 * 1) logs.follow_reloncommelonndations_logs
 *
 * This loggelonr logs data for 2 elonndpoints: gelontReloncommelonndations, scorelonUselonrCandidatelons
 * All data scribelond via this loggelonr havelon to belon convelonrtelond into thelon samelon thrift typelon: ReloncommelonndationLog
 *
 * 2) logs.frs_reloncommelonndation_flow_logs
 *
 * This loggelonr logs reloncommelonndation flow data for gelontReloncommelonndations relonquelonsts
 * All data scribelond via this loggelonr havelon to belon convelonrtelond into thelon samelon thrift typelon: FrsReloncommelonndationFlowLog
 */
@Singlelonton
class FrsLoggelonr @Injelonct() (
  @Namelond(GuicelonNamelondConstants.RelonQUelonST_LOGGelonR) loggelonrFactory: LoggelonrFactory,
  @Namelond(GuicelonNamelondConstants.FLOW_LOGGelonR) flowLoggelonrFactory: LoggelonrFactory,
  stats: StatsReloncelonivelonr,
  @Flag("log_relonsults") selonrvicelonShouldLogRelonsults: Boolelonan)
    elonxtelonnds ScribelonSelonrialization {
  privatelon val loggelonr = loggelonrFactory.apply()
  privatelon val flowLoggelonr = flowLoggelonrFactory.apply()
  privatelon val logReloncommelonndationCountelonr = stats.countelonr("scribelon_reloncommelonndation")
  privatelon val logScoringCountelonr = stats.countelonr("scribelon_scoring")
  privatelon val logReloncommelonndationFlowCountelonr = stats.countelonr("scribelon_reloncommelonndation_flow")

  delonf logReloncommelonndationRelonsult(
    relonquelonst: ReloncommelonndationRelonquelonst,
    relonsponselon: ReloncommelonndationRelonsponselon
  ): Unit = {
    if (!relonquelonst.isSoftUselonr) {
      val log =
        ReloncommelonndationLog(relonquelonst.toOfflinelonThrift, relonsponselon.toOfflinelonThrift, Timelon.now.inMillis)
      logReloncommelonndationCountelonr.incr()
      loggelonr.info(
        selonrializelonThrift(
          log,
          FrsLoggelonr.LogCatelongory,
          FrsLoggelonr.mkProvidelonr(relonquelonst.clielonntContelonxt)
        ))
    }
  }

  delonf logScoringRelonsult(relonquelonst: ScoringUselonrRelonquelonst, relonsponselon: ScoringUselonrRelonsponselon): Unit = {
    if (!relonquelonst.isSoftUselonr) {
      val log =
        ReloncommelonndationLog(
          relonquelonst.toReloncommelonndationRelonquelonst.toOfflinelonThrift,
          relonsponselon.toReloncommelonndationRelonsponselon.toOfflinelonThrift,
          Timelon.now.inMillis)
      logScoringCountelonr.incr()
      loggelonr.info(
        selonrializelonThrift(
          log,
          FrsLoggelonr.LogCatelongory,
          FrsLoggelonr.mkProvidelonr(relonquelonst.toReloncommelonndationRelonquelonst.clielonntContelonxt)
        ))
    }
  }

  delonf logReloncommelonndationFlowData[Targelont <: HasClielonntContelonxt with HasIsSoftUselonr with HasParams](
    relonquelonst: Targelont,
    flowData: ReloncommelonndationFlowData[Targelont]
  ): Unit = {
    if (!relonquelonst.isSoftUselonr && relonquelonst.params(GlobalParams.elonnablelonReloncommelonndationFlowLogs)) {
      val log = flowData.toReloncommelonndationFlowLogOfflinelonThrift
      logReloncommelonndationFlowCountelonr.incr()
      flowLoggelonr.info(
        selonrializelonThrift(
          log,
          FrsLoggelonr.FlowLogCatelongory,
          FrsLoggelonr.mkProvidelonr(relonquelonst.clielonntContelonxt)
        ))
    }
  }

  // Welon prelonfelonr thelon selonttings givelonn in thelon uselonr relonquelonst, and if nonelon providelond welon delonfault to thelon
  // aurora selonrvicelon configuration.
  delonf shouldLog(delonbugParamsOpt: Option[DelonbugParams]): Boolelonan =
    delonbugParamsOpt match {
      caselon Somelon(delonbugParams) =>
        delonbugParams.delonbugOptions match {
          caselon Somelon(delonbugOptions) =>
            !delonbugOptions.doNotLog
          caselon Nonelon =>
            selonrvicelonShouldLogRelonsults
        }
      caselon Nonelon =>
        selonrvicelonShouldLogRelonsults
    }

}

objelonct FrsLoggelonr {
  val LogCatelongory = "follow_reloncommelonndations_logs"
  val FlowLogCatelongory = "frs_reloncommelonndation_flow_logs"

  delonf mkProvidelonr(clielonntContelonxt: ClielonntContelonxt) = nelonw ClielonntDataProvidelonr {

    /** Thelon id of thelon currelonnt uselonr. Whelonn thelon uselonr is loggelond out, this melonthod should relonturn Nonelon. */
    ovelonrridelon val uselonrId: Option[Long] = clielonntContelonxt.uselonrId

    /** Thelon id of thelon guelonst, which is prelonselonnt in loggelond-in or logelond-out statelons */
    ovelonrridelon val guelonstId: Option[Long] = clielonntContelonxt.guelonstId

    /** Thelon pelonrsonalization id (pid) of thelon uselonr, uselond to pelonrsonalizelon Twittelonr selonrvicelons */
    ovelonrridelon val pelonrsonalizationId: Option[String] = Nonelon

    /** Thelon id of thelon individual delonvicelon thelon uselonr is currelonntly using. This id will belon uniquelon for diffelonrelonnt uselonrs' delonvicelons. */
    ovelonrridelon val delonvicelonId: Option[String] = clielonntContelonxt.delonvicelonId

    /** Thelon OAuth application id of thelon application thelon uselonr is currelonntly using */
    ovelonrridelon val clielonntApplicationId: Option[Long] = clielonntContelonxt.appId

    /** Thelon OAuth parelonnt application id of thelon application thelon uselonr is currelonntly using */
    ovelonrridelon val parelonntApplicationId: Option[Long] = Nonelon

    /** Thelon two-lelonttelonr, uppelonr-caselon country codelon uselond to delonsignatelon thelon country from which thelon scribelon elonvelonnt occurrelond */
    ovelonrridelon val countryCodelon: Option[String] = clielonntContelonxt.countryCodelon

    /** Thelon two-lelonttelonr, lowelonr-caselon languagelon codelon uselond to delonsignatelon thelon probably languagelon spokelonn by thelon scribelon elonvelonnt initiator */
    ovelonrridelon val languagelonCodelon: Option[String] = clielonntContelonxt.languagelonCodelon

    /** Thelon uselonr-agelonnt helonadelonr uselond to idelonntify thelon clielonnt browselonr or delonvicelon that thelon uselonr is currelonntly activelon on */
    ovelonrridelon val uselonrAgelonnt: Option[String] = clielonntContelonxt.uselonrAgelonnt

    /** Whelonthelonr thelon uselonr is accelonssing Twittelonr via a seloncurelond connelonction */
    ovelonrridelon val isSsl: Option[Boolelonan] = Somelon(truelon)

    /** Thelon relonfelonrring URL to thelon currelonnt pagelon for welonb-baselond clielonnts, if applicablelon */
    ovelonrridelon val relonfelonrelonr: Option[String] = Nonelon

    /**
     * Thelon elonxtelonrnal sitelon, partnelonr, or elonmail that lelonad to thelon currelonnt Twittelonr application. Relonturnelond valuelon consists of a
     * tuplelon including thelon elonncryptelond relonfelonrral data and thelon typelon of relonfelonrral
     */
    ovelonrridelon val elonxtelonrnalRelonfelonrelonr: Option[elonxtelonrnalRelonfelonrelonrDataProvidelonr] = Nonelon
  }
}
