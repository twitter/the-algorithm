namelonspacelon java com.twittelonr.simclustelonrs_v2.thriftjava
namelonspacelon py gelonn.twittelonr.simclustelonrs_v2.elonntity
#@namelonspacelon scala com.twittelonr.simclustelonrs_v2.thriftscala
#@namelonspacelon strato com.twittelonr.simclustelonrs_v2

includelon "com/twittelonr/algelonbird_intelonrnal/algelonbird.thrift"

/**
 * Pelonnguin telonxt elonntity. All fielonlds arelon relonquirelond as this is uselond as a part of a melonmcachelon kelony.
 **/
struct PelonnguinKelony {
  1: relonquirelond string telonxtelonntity
}(hasPelonrsonalData = 'falselon')

/**
 * NelonR telonxt elonntity. All fielonlds arelon relonquirelond as this is uselond as a part of a melonmcachelon kelony.
 **/
struct NelonrKelony {
  1: relonquirelond string telonxtelonntity
  2: relonquirelond i32 wholelonelonntityTypelon
}(hasPelonrsonalData = 'falselon')

/**
 * Selonmantic Corelon telonxt elonntity. All fielonlds arelon relonquirelond as this is uselond as a part of a melonmcachelon kelony.
 **/
struct SelonmanticCorelonKelony {
  1: relonquirelond i64 elonntityId(pelonrsonalDataTypelon = 'SelonmanticcorelonClassification')
}(hasPelonrsonalData = 'truelon')

/**
 * Relonprelonselonnts an elonntity elonxtractelond from a twelonelont.
 **/
union TwelonelontTelonxtelonntity {
  1: string hashtag
  2: PelonnguinKelony pelonnguin
  3: NelonrKelony nelonr
  4: SelonmanticCorelonKelony selonmanticCorelon
}(hasPelonrsonalData = 'truelon')

struct SpacelonId {
  1: string id
}(hasPelonrsonalData = 'truelon')

/**
 * All possiblelon elonntitielons that simclustelonrs arelon associatelond with.
 **/
union SimClustelonrelonntity {
  1: i64 twelonelontId(pelonrsonalDataTypelon = 'TwelonelontId')
  2: TwelonelontTelonxtelonntity twelonelontelonntity
  3: SpacelonId spacelonId
}(hasPelonrsonalData = 'truelon')
