packagelon com.twittelonr.follow_reloncommelonndations.common.clielonnts.phonelon_storagelon_selonrvicelon

import com.twittelonr.finatra.mtls.thriftmux.modulelons.MtlsClielonnt
import com.twittelonr.follow_reloncommelonndations.common.clielonnts.common.BaselonClielonntModulelon
import com.twittelonr.phonelonstoragelon.api.thriftscala.PhonelonStoragelonSelonrvicelon

objelonct PhonelonStoragelonSelonrvicelonModulelon
    elonxtelonnds BaselonClielonntModulelon[PhonelonStoragelonSelonrvicelon.MelonthodPelonrelonndpoint]
    with MtlsClielonnt {
  ovelonrridelon val labelonl = "phonelon-storagelon-selonrvicelon"
  ovelonrridelon val delonst = "/s/ibis-ds-api/ibis-ds-api:thrift2"
}
