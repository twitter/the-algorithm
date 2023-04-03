namelonspacelon java com.twittelonr.follow_reloncommelonndations.logging.thriftjava
#@namelonspacelon scala com.twittelonr.follow_reloncommelonndations.logging.thriftscala
#@namelonspacelon strato com.twittelonr.follow_reloncommelonndations.logging

struct OfflinelonFlowReloncommelonndation {
  1: relonquirelond i64 uselonrId(pelonrsonalDataTypelon='UselonrId')
}(pelonrsistelond='truelon', hasPelonrsonalData='truelon')

struct OfflinelonReloncommelonndationStelonp {
  1: relonquirelond list<OfflinelonFlowReloncommelonndation> reloncommelonndations
  2: relonquirelond selont<i64> followelondUselonrIds(pelonrsonalDataTypelon='UselonrId')
}(pelonrsistelond='truelon', hasPelonrsonalData='truelon')

struct OfflinelonFlowContelonxt {
  1: relonquirelond list<OfflinelonReloncommelonndationStelonp> stelonps
}(pelonrsistelond='truelon', hasPelonrsonalData='truelon')
