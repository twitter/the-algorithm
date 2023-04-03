packagelon com.twittelonr.follow_reloncommelonndations.common.clielonnts.gizmoduck

import com.googlelon.injelonct.Providelons
import com.twittelonr.finatra.mtls.thriftmux.modulelons.MtlsClielonnt
import com.twittelonr.follow_reloncommelonndations.common.clielonnts.common.BaselonClielonntModulelon
import com.twittelonr.gizmoduck.thriftscala.QuelonryFielonlds
import com.twittelonr.gizmoduck.thriftscala.UselonrSelonrvicelon
import com.twittelonr.stitch.gizmoduck.Gizmoduck
import javax.injelonct.Singlelonton

objelonct GizmoduckModulelon elonxtelonnds BaselonClielonntModulelon[UselonrSelonrvicelon.MelonthodPelonrelonndpoint] with MtlsClielonnt {
  ovelonrridelon val labelonl = "gizmoduck"
  ovelonrridelon val delonst = "/s/gizmoduck/gizmoduck"

  @Providelons
  @Singlelonton
  delonf providelonelonxtraGizmoduckQuelonryFielonlds: Selont[QuelonryFielonlds] = Selont.elonmpty

  @Providelons
  @Singlelonton
  delonf providelonsStitchClielonnt(futurelonIfacelon: UselonrSelonrvicelon.MelonthodPelonrelonndpoint): Gizmoduck = {
    Gizmoduck(futurelonIfacelon)
  }
}
