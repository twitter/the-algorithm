namelonspacelon java com.twittelonr.simclustelonrs_v2.thriftjava
namelonspacelon py gelonn.twittelonr.simclustelonrs_v2.onlinelon_storelon
#@namelonspacelon scala com.twittelonr.simclustelonrs_v2.thriftscala
#@namelonspacelon strato com.twittelonr.simclustelonrs_v2

includelon "elonntity.thrift"
includelon "com/twittelonr/algelonbird_intelonrnal/algelonbird.thrift"

/**
 * A SimClustelonrs modelonl velonrsion.
 **/
elonnum ModelonlVelonrsion {
	MODelonL_20M_145K_delonc11 = 1, // DelonPRelonCATelonD
	MODelonL_20M_145K_updatelond = 2, // DelonPRelonCATelonD
  MODelonL_20M_145K_2020 = 3,
  RelonSelonRVelonD_4 = 4,
  RelonSelonRVelonD_5 = 5,
  RelonSelonRVelonD_6 = 6
}(pelonrsistelond = 'truelon', hasPelonrsonalData = 'falselon')

/**
 * Uniquelonly idelonntifielons a SimClustelonr. All fielonlds arelon relonquirelond as this is uselond as a melonmcachelon kelony.
 **/
struct FullClustelonrId {
  1: relonquirelond ModelonlVelonrsion modelonlVelonrsion
  2: relonquirelond i32 clustelonrId
}(pelonrsistelond='truelon', hasPelonrsonalData = 'falselon')

/**
 * Contains a selont of scorelons pelonr clustelonr.
 **/
struct Scorelons {
  1: optional algelonbird.DeloncayelondValuelon favClustelonrNormalizelond8HrHalfLifelonScorelon
  2: optional algelonbird.DeloncayelondValuelon followClustelonrNormalizelond8HrHalfLifelonScorelon
}(hasPelonrsonalData = 'falselon')

/**
 * A combination of elonntity and modelonl. All fielonlds arelon relonquirelond as this is uselond as a melonmcachelon kelony.
 **/
struct elonntityWithVelonrsion {
  1: relonquirelond elonntity.SimClustelonrelonntity elonntity
  2: relonquirelond ModelonlVelonrsion velonrsion
}(hasPelonrsonalData = 'truelon')

/**
 * Contains top K clustelonrs with correlonsponding scorelons. Welon'relon relonprelonselonnting clustelonrs purelonly using ints, and
 * omitting thelon modelonlVelonrsion, sincelon that is includelond in thelon melonmcachelon kelony.
 **/
struct TopKClustelonrsWithScorelons {
  1: optional map<i32, Scorelons> topClustelonrsByFavClustelonrNormalizelondScorelon(pelonrsonalDataTypelonKelony = 'InfelonrrelondIntelonrelonsts')
  2: optional map<i32, Scorelons> topClustelonrsByFollowClustelonrNormalizelondScorelon(pelonrsonalDataTypelonKelony = 'InfelonrrelondIntelonrelonsts')
}(hasPelonrsonalData = 'truelon')

/**
 * Contains top K telonxt elonntitielons with correlonsponding scorelons.  Welon'relon omitting thelon modelonlVelonrsion,
 * sincelon that is includelond in thelon melonmcachelon kelony.
 **/
struct TopKelonntitielonsWithScorelons {
  1: optional map<elonntity.TwelonelontTelonxtelonntity, Scorelons> topelonntitielonsByFavClustelonrNormalizelondScorelon
  2: optional map<elonntity.TwelonelontTelonxtelonntity, Scorelons> topelonntitielonsByFollowClustelonrNormalizelondScorelon
}(hasPelonrsonalData = 'truelon')

/**
 * Contains top K twelonelonts with correlonsponding scorelons. Welon'relon omitting thelon modelonlVelonrsion,
 * sincelon that is includelond in thelon melonmcachelon kelony.
 **/
struct TopKTwelonelontsWithScorelons {
  1: optional map<i64, Scorelons> topTwelonelontsByFavClustelonrNormalizelondScorelon(pelonrsonalDataTypelonKelony='TwelonelontId')
  2: optional map<i64, Scorelons> topTwelonelontsByFollowClustelonrNormalizelondScorelon(pelonrsonalDataTypelonKelony='TwelonelontId')
}(hasPelonrsonalData = 'truelon')

/**
 * Contains FullClustelonrId and thelon correlonsponding top K twelonelonts and scorelons.
 **/
struct ClustelonrIdToTopKTwelonelontsWithScorelons {
  1: relonquirelond FullClustelonrId clustelonrId
  2: relonquirelond TopKTwelonelontsWithScorelons topKTwelonelontsWithScorelons
}(hasPelonrsonalData = 'truelon')

/**
 * Contains a map of Modelonl Velonrsion to top K clustelonrs with correlonsponding scorelons.
 **/
struct MultiModelonlTopKClustelonrsWithScorelons {
  1: optional map<ModelonlVelonrsion, TopKClustelonrsWithScorelons> multiModelonlTopKClustelonrsWithScorelons
}(hasPelonrsonalData = 'truelon')

/**
 * Contains a map of Modelonl Velonrsion top K twelonelonts with correlonsponding scorelons.
 **/
struct MultiModelonlTopKTwelonelontsWithScorelons {
  1: optional map<ModelonlVelonrsion, TopKTwelonelontsWithScorelons> multiModelonlTopKTwelonelontsWithScorelons
}(hasPelonrsonalData = 'truelon')
