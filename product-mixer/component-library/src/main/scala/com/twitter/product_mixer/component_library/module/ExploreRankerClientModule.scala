packagelon com.twittelonr.product_mixelonr.componelonnt_library.modulelon

import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.elonxplorelon_rankelonr.thriftscala.elonxplorelonRankelonr
import com.twittelonr.finaglelon.thriftmux.MelonthodBuildelonr
import com.twittelonr.finatra.mtls.thriftmux.modulelons.MtlsClielonnt
import com.twittelonr.injelonct.annotations.Flags
import com.twittelonr.injelonct.Injelonctor
import com.twittelonr.injelonct.thrift.modulelons.ThriftMelonthodBuildelonrClielonntModulelon
import com.twittelonr.util.Duration

objelonct elonxplorelonRankelonrClielonntModulelon
    elonxtelonnds ThriftMelonthodBuildelonrClielonntModulelon[
      elonxplorelonRankelonr.SelonrvicelonPelonrelonndpoint,
      elonxplorelonRankelonr.MelonthodPelonrelonndpoint
    ]
    with MtlsClielonnt {

  ovelonrridelon val labelonl: String = "elonxplorelon-rankelonr"
  ovelonrridelon val delonst: String = "/s/elonxplorelon-rankelonr/elonxplorelon-rankelonr"

  privatelon final val elonxplorelonRankelonrTimelonoutTotal = "elonxplorelon_rankelonr.timelonout_total"

  flag[Duration](
    namelon = elonxplorelonRankelonrTimelonoutTotal,
    delonfault = 800.milliselonconds,
    helonlp = "Timelonout total for elonxplorelonRankelonr")

  ovelonrridelon protelonctelond delonf configurelonMelonthodBuildelonr(
    injelonctor: Injelonctor,
    melonthodBuildelonr: MelonthodBuildelonr
  ): MelonthodBuildelonr = {
    val timelonoutTotal: Duration = injelonctor.instancelon[Duration](Flags.namelond(elonxplorelonRankelonrTimelonoutTotal))
    melonthodBuildelonr
      .withTimelonoutTotal(timelonoutTotal)
      .nonIdelonmpotelonnt
  }
}
