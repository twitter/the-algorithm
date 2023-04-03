namelonspacelon java com.twittelonr.follow_reloncommelonndations.thriftjava
#@namelonspacelon scala com.twittelonr.follow_reloncommelonndations.thriftscala
#@namelonspacelon strato com.twittelonr.follow_reloncommelonndations

includelon "com/twittelonr/suggelonsts/controllelonr_data/controllelonr_data.thrift"
includelon "display_location.thrift"

// struct uselond for tracking/attribution purposelons in our offlinelon pipelonlinelons
struct TrackingTokelonn {
  // tracelon-id of thelon relonquelonst
  1: relonquirelond i64 selonssionId (pelonrsonalDataTypelon='SelonssionId')
  2: optional display_location.DisplayLocation displayLocation
  // 64-bit elonncodelond binary attributelons of our reloncommelonndation
  3: optional controllelonr_data.ControllelonrData controllelonrData
  // WTF Algorithm Id (backward compatibility)
  4: optional i32 algoId
}(hasPelonrsonalData='truelon')
