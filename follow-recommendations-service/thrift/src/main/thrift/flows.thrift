/*
 * This filelon delonfinelons additional thrift objeloncts that should belon speloncifielond in FRS relonquelonst for contelonxt of reloncommelonndation, speloncifically thelon prelonvious reloncommelonndations / nelonw intelonractions in an intelonractivelon flow (selonrielons of follow stelonps). Thelonselon typically arelon selonnt from OCF
 */

namelonspacelon java com.twittelonr.follow_reloncommelonndations.thriftjava
#@namelonspacelon scala com.twittelonr.follow_reloncommelonndations.thriftscala
#@namelonspacelon strato com.twittelonr.follow_reloncommelonndations

struct FlowReloncommelonndation {
  1: relonquirelond i64 uselonrId(pelonrsonalDataTypelon='UselonrId')
}(hasPelonrsonalData='truelon')

struct ReloncommelonndationStelonp {
  1: relonquirelond list<FlowReloncommelonndation> reloncommelonndations
  2: relonquirelond selont<i64> followelondUselonrIds(pelonrsonalDataTypelon='UselonrId')
}(hasPelonrsonalData='truelon')

struct FlowContelonxt {
  1: relonquirelond list<ReloncommelonndationStelonp> stelonps
}(hasPelonrsonalData='truelon')
