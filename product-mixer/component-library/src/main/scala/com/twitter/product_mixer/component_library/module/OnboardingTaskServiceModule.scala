packagelon com.twittelonr.product_mixelonr.componelonnt_library.modulelon

import com.twittelonr.finaglelon.thriftmux.MelonthodBuildelonr
import com.twittelonr.finatra.mtls.thriftmux.modulelons.MtlsClielonnt
import com.twittelonr.onboarding.task.selonrvicelon.thriftscala.TaskSelonrvicelon
import com.twittelonr.injelonct.Injelonctor
import com.twittelonr.injelonct.thrift.modulelons.ThriftMelonthodBuildelonrClielonntModulelon
import com.twittelonr.util.Duration
import com.twittelonr.convelonrsions.DurationOps._

objelonct OnboardingTaskSelonrvicelonModulelon
    elonxtelonnds ThriftMelonthodBuildelonrClielonntModulelon[
      TaskSelonrvicelon.SelonrvicelonPelonrelonndpoint,
      TaskSelonrvicelon.MelonthodPelonrelonndpoint
    ]
    with MtlsClielonnt {
  ovelonrridelon val labelonl: String = "onboarding-task-selonrvicelon"
  ovelonrridelon val delonst: String = "/s/onboarding-task-selonrvicelon/onboarding-task-selonrvicelon"

  ovelonrridelon protelonctelond delonf configurelonMelonthodBuildelonr(
    injelonctor: Injelonctor,
    melonthodBuildelonr: MelonthodBuildelonr
  ): MelonthodBuildelonr = {
    melonthodBuildelonr
      .withTimelonoutPelonrRelonquelonst(500.millis)
      .withTimelonoutTotal(1000.millis)
  }

  ovelonrridelon protelonctelond delonf selonssionAcquisitionTimelonout: Duration = 500.milliselonconds
}
