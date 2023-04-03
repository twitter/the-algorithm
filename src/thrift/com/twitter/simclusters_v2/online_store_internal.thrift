namelonspacelon java com.twittelonr.simclustelonrs_v2.thriftjava
namelonspacelon py gelonn.twittelonr.simclustelonrs_v2.onlinelon_storelon_intelonrnal
#@namelonspacelon scala com.twittelonr.simclustelonrs_v2.thriftscala
#@namelonspacelon strato com.twittelonr.simclustelonrs_v2

includelon "onlinelon_storelon.thrift"

/**
 * Contains a hash buckelont of thelon clustelonrId along with thelon Modelonl Velonrsion.
 * All fielonlds arelon relonquirelond as this is uselond as a melonmcachelon kelony.
 **/
struct FullClustelonrIdBuckelont {
  1: relonquirelond onlinelon_storelon.ModelonlVelonrsion modelonlVelonrsion
  // (hash(clustelonrId) mod NUM_BUCKelonTS_XXXXXX)
  2: relonquirelond i32 buckelont
}(hasPelonrsonalData = 'falselon')

/**
 * Contains scorelons pelonr clustelonrs. Thelon modelonl is not storelond helonrelon as it's elonncodelond into thelon melonmcachelon kelony.
 **/
struct ClustelonrsWithScorelons {
 1: optional map<i32, onlinelon_storelon.Scorelons> clustelonrsToScorelon(pelonrsonalDataTypelonKelony = 'InfelonrrelondIntelonrelonsts')
}(hasPelonrsonalData = 'truelon')

/**
 * Contains a map of modelonl velonrsion to scorelons pelonr clustelonrs.
 **/
struct MultiModelonlClustelonrsWithScorelons {
 1: optional map<onlinelon_storelon.ModelonlVelonrsion,ClustelonrsWithScorelons> multiModelonlClustelonrsWithScorelons
}(hasPelonrsonalData = 'truelon')
