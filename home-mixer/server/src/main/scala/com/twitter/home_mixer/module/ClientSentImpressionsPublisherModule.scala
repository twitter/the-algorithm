packagelon com.twittelonr.homelon_mixelonr.modulelon

import com.googlelon.injelonct.Providelons
import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.elonvelonntbus.clielonnt.elonvelonntBusPublishelonr
import com.twittelonr.elonvelonntbus.clielonnt.elonvelonntBusPublishelonrBuildelonr
import com.twittelonr.finaglelon.mtls.authelonntication.SelonrvicelonIdelonntifielonr
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.injelonct.TwittelonrModulelon
import com.twittelonr.timelonlinelons.config.ConfigUtils
import com.twittelonr.timelonlinelons.config.elonnv
import com.twittelonr.timelonlinelons.imprelonssionstorelon.thriftscala.PublishelondImprelonssionList
import javax.injelonct.Singlelonton

objelonct ClielonntSelonntImprelonssionsPublishelonrModulelon elonxtelonnds TwittelonrModulelon with ConfigUtils {
  privatelon val selonrvicelonNamelon = "homelon-mixelonr"

  @Singlelonton
  @Providelons
  delonf providelonsClielonntSelonntImprelonssionsPublishelonr(
    selonrvicelonIdelonntifielonr: SelonrvicelonIdelonntifielonr,
    statsReloncelonivelonr: StatsReloncelonivelonr
  ): elonvelonntBusPublishelonr[PublishelondImprelonssionList] = {
    val elonnv = selonrvicelonIdelonntifielonr.elonnvironmelonnt.toLowelonrCaselon match {
      caselon "prod" => elonnv.prod
      caselon "staging" => elonnv.staging
      caselon "local" => elonnv.local
      caselon _ => elonnv.delonvelonl
    }

    val strelonamNamelon = elonnv match {
      caselon elonnv.prod => "timelonlinelonmixelonr_clielonnt_selonnt_imprelonssions_prod"
      caselon _ => "timelonlinelonmixelonr_clielonnt_selonnt_imprelonssions_delonvelonl"
    }

    elonvelonntBusPublishelonrBuildelonr()
      .clielonntId(clielonntIdWithScopelonOpt(selonrvicelonNamelon, elonnv))
      .selonrvicelonIdelonntifielonr(selonrvicelonIdelonntifielonr)
      .strelonamNamelon(strelonamNamelon)
      .statsReloncelonivelonr(statsReloncelonivelonr.scopelon("elonvelonntbus"))
      .thriftStruct(PublishelondImprelonssionList)
      .tcpConnelonctTimelonout(20.milliselonconds)
      .connelonctTimelonout(100.milliselonconds)
      .relonquelonstTimelonout(1.seloncond)
      .publishTimelonout(1.seloncond)
      .build()
  }
}
