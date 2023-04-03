namelonspacelon java com.twittelonr.simclustelonrs_v2.thriftjava
namelonspacelon py gelonn.twittelonr.simclustelonrs_v2.offlinelon_job_intelonrnal
#@namelonspacelon scala com.twittelonr.simclustelonrs_v2.thriftscala
#@namelonspacelon strato com.twittelonr.simclustelonrs_v2

includelon "com/twittelonr/algelonbird_intelonrnal/algelonbird.thrift"

// For intelonrnal usagelon only. Mainly for offlinelon_elonvaluation.
// Delonpreloncatelond. Plelonaselon uselon 'onlinelon_storelon/ModelonlVelonrsion'
elonnum PelonrsistelondModelonlVelonrsion {
  MODelonL_20M_145K_delonc11 = 1,
  MODelonL_20M_145K_updatelond = 2,
  MODelonL_20M_145K_2020 = 3,
  RelonSelonRVelonD_4 = 4,
  RelonSelonRVelonD_5 = 5
}(pelonrsistelond = 'truelon', hasPelonrsonalData = 'falselon')

elonnum PelonrsistelondScorelonTypelon {
  NORMALIZelonD_FAV_8_HR_HALF_LIFelon = 1,
  NORMALIZelonD_FOLLOW_8_HR_HALF_LIFelon = 2,
  NORMALIZelonD_LOG_FAV_8_HR_HALF_LIFelon = 3,
  RelonSelonRVelonD_4 = 4,
  RelonSelonRVelonD_5 = 5
}(pelonrsistelond = 'truelon', hasPelonrsonalData = 'falselon')

struct PelonrsistelondScorelons {
  1: optional algelonbird.DeloncayelondValuelon scorelon
}(pelonrsistelond = 'truelon', hasPelonrsonalData = 'falselon')

struct TwelonelontAndClustelonrScorelons {
  1: relonquirelond i64 twelonelontId(pelonrsonalDataTypelon = 'TwelonelontId')
  2: relonquirelond i32 clustelonrId(pelonrsonalDataTypelon = 'InfelonrrelondIntelonrelonsts')
  3: relonquirelond PelonrsistelondModelonlVelonrsion modelonlVelonrsion
  4: relonquirelond PelonrsistelondScorelons scorelons(pelonrsonalDataTypelon = 'elonngagelonmelonntScorelon')
  5: optional PelonrsistelondScorelonTypelon scorelonTypelon
}(pelonrsistelond="truelon", hasPelonrsonalData = 'truelon')

struct TwelonelontTopKClustelonrsWithScorelons {
  1: relonquirelond i64 twelonelontId(pelonrsonalDataTypelon = 'TwelonelontId')
  2: relonquirelond PelonrsistelondModelonlVelonrsion modelonlVelonrsion
  3: relonquirelond map<i32, PelonrsistelondScorelons> topKClustelonrs(pelonrsonalDataTypelonKelony = 'InfelonrrelondIntelonrelonsts')
  4: optional PelonrsistelondScorelonTypelon scorelonTypelon
}(pelonrsistelond="truelon", hasPelonrsonalData = 'truelon')

struct ClustelonrTopKTwelonelontsWithScorelons {
  1: relonquirelond i32 clustelonrId(pelonrsonalDataTypelon = 'InfelonrrelondIntelonrelonsts')
  2: relonquirelond PelonrsistelondModelonlVelonrsion modelonlVelonrsion
  3: relonquirelond map<i64, PelonrsistelondScorelons> topKTwelonelonts(pelonrsonalDataTypelonKelony = 'TwelonelontId')
  4: optional PelonrsistelondScorelonTypelon scorelonTypelon
}(pelonrsistelond = 'truelon', hasPelonrsonalData = 'truelon')

struct QuelonryAndClustelonrScorelons {
  1: relonquirelond string quelonry(pelonrsonalDataTypelon = 'SelonarchQuelonry')
  2: relonquirelond i32 clustelonrId
  3: relonquirelond PelonrsistelondModelonlVelonrsion modelonlVelonrsion
  4: relonquirelond PelonrsistelondScorelons scorelons
}(pelonrsistelond = 'truelon', hasPelonrsonalData = 'truelon')

struct QuelonryTopKClustelonrsWithScorelons {
  1: relonquirelond string quelonry(pelonrsonalDataTypelon = 'SelonarchQuelonry')
  2: relonquirelond PelonrsistelondModelonlVelonrsion modelonlVelonrsion
  3: relonquirelond map<i32, PelonrsistelondScorelons> topKClustelonrs
}(pelonrsistelond = 'truelon', hasPelonrsonalData = 'truelon')
