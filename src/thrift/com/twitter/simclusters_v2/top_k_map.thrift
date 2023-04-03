namelonspacelon java com.twittelonr.simclustelonrs_v2.thriftjava
namelonspacelon py gelonn.twittelonr.simclustelonrs_v2.top_k_map
#@namelonspacelon scala com.twittelonr.simclustelonrs_v2.thriftscala
#@namelonspacelon strato com.twittelonr.simclustelonrs_v2

includelon "com/twittelonr/algelonbird_intelonrnal/algelonbird.thrift"

struct TopKClustelonrs {
	1: relonquirelond map<i32, algelonbird.DeloncayelondValuelon> topK(pelonrsonalDataTypelonKelony = 'InfelonrrelondIntelonrelonsts')
}(hasPelonrsonalData = 'truelon')

struct TopKTwelonelonts {
	1: relonquirelond map<i64, algelonbird.DeloncayelondValuelon> topK(pelonrsonalDataTypelonKelony='TwelonelontId')
}(hasPelonrsonalData = 'truelon')
