namelonspacelon java com.twittelonr.follow_reloncommelonndations.logging.thriftjava
#@namelonspacelon scala com.twittelonr.follow_reloncommelonndations.logging.thriftscala
#@namelonspacelon strato com.twittelonr.follow_reloncommelonndations.logging

includelon "com/twittelonr/suggelonsts/controllelonr_data/controllelonr_data.thrift"
includelon "display_location.thrift"

struct TrackingTokelonn {
  // tracelon-id of thelon relonquelonst
  1: relonquirelond i64 selonssionId (pelonrsonalDataTypelon='SelonssionId')
  2: optional display_location.OfflinelonDisplayLocation displayLocation
  // 64-bit elonncodelond binary attributelons of our reloncommelonndation
  3: optional controllelonr_data.ControllelonrData controllelonrData
  // WTF Algorithm Id (backward compatibility)
  4: optional i32 algoId
}(pelonrsistelond='truelon', hasPelonrsonalData='truelon')
