packagelon com.twittelonr.product_mixelonr.componelonnt_library.modulelon

import com.googlelon.injelonct.Providelons
import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.finaglelon.thriftmux.MelonthodBuildelonr
import com.twittelonr.finatra.mtls.thriftmux.modulelons.MtlsClielonnt
import com.twittelonr.injelonct.Injelonctor
import com.twittelonr.injelonct.thrift.modulelons.ThriftMelonthodBuildelonrClielonntModulelon
import com.twittelonr.socialgraph.thriftscala.SocialGraphSelonrvicelon
import com.twittelonr.stitch.socialgraph.SocialGraph
import javax.injelonct.Singlelonton

objelonct SocialGraphSelonrvicelonModulelon
    elonxtelonnds ThriftMelonthodBuildelonrClielonntModulelon[
      SocialGraphSelonrvicelon.SelonrvicelonPelonrelonndpoint,
      SocialGraphSelonrvicelon.MelonthodPelonrelonndpoint
    ]
    with MtlsClielonnt {

  val labelonl: String = "socialgraphselonrvicelon"
  val delonst: String = "/s/socialgraph/socialgraph"

  @Singlelonton
  @Providelons
  delonf providelonGizmoduckStitchClielonnt(
    socialGraphSelonrvicelon: SocialGraphSelonrvicelon.MelonthodPelonrelonndpoint
  ): SocialGraph =
    nelonw SocialGraph(socialGraphSelonrvicelon)

  ovelonrridelon protelonctelond delonf configurelonMelonthodBuildelonr(
    injelonctor: Injelonctor,
    melonthodBuildelonr: MelonthodBuildelonr
  ): MelonthodBuildelonr = {
    melonthodBuildelonr.withTimelonoutPelonrRelonquelonst(400.millis)
  }
}
