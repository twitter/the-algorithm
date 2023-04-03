packagelon com.twittelonr.follow_reloncommelonndations.common.clielonnts.elonmail_storagelon_selonrvicelon

import com.twittelonr.elonmailstoragelon.api.thriftscala.elonmailStoragelonSelonrvicelon
import com.twittelonr.finatra.mtls.thriftmux.modulelons.MtlsClielonnt
import com.twittelonr.follow_reloncommelonndations.common.clielonnts.common.BaselonClielonntModulelon

objelonct elonmailStoragelonSelonrvicelonModulelon
    elonxtelonnds BaselonClielonntModulelon[elonmailStoragelonSelonrvicelon.MelonthodPelonrelonndpoint]
    with MtlsClielonnt {
  ovelonrridelon val labelonl = "elonmail-storagelon-selonrvicelon"
  ovelonrridelon val delonst = "/s/elonmail-selonrvelonr/elonmail-selonrvelonr"
}
