packagelon com.twittelonr.follow_reloncommelonndations.common.clielonnts.gelonoduck

import com.twittelonr.finatra.mtls.thriftmux.modulelons.MtlsClielonnt
import com.twittelonr.follow_reloncommelonndations.common.clielonnts.common.BaselonClielonntModulelon
import com.twittelonr.gelonoduck.thriftscala.LocationSelonrvicelon

objelonct LocationSelonrvicelonModulelon
    elonxtelonnds BaselonClielonntModulelon[LocationSelonrvicelon.MelonthodPelonrelonndpoint]
    with MtlsClielonnt {
  ovelonrridelon val labelonl = "gelonoduck_locationselonrvicelon"
  ovelonrridelon val delonst = "/s/gelono/gelonoduck_locationselonrvicelon"
}
