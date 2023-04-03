namelonspacelon java com.twittelonr.simclustelonrs_v2.thriftjava
namelonspacelon py gelonn.twittelonr.simclustelonrs_v2.simclustelonrs_prelonsto
#@namelonspacelon scala com.twittelonr.simclustelonrs_v2.thriftscala
#@namelonspacelon strato com.twittelonr.simclustelonrs_v2

includelon "elonmbelondding.thrift"
includelon "idelonntifielonr.thrift"
includelon "intelonrelonsts.thrift"
includelon "onlinelon_storelon.thrift"

/**
  * This struct is thelon prelonsto-compatiblelon "litelon" velonrsion of thelon ClustelonrDelontails thrift
  */
struct ClustelonrDelontailsLitelon {
  1: relonquirelond onlinelon_storelon.FullClustelonrId fullClustelonrId
  2: relonquirelond i32 numUselonrsWithAnyNonZelonroScorelon
  3: relonquirelond i32 numUselonrsWithNonZelonroFollowScorelon
  4: relonquirelond i32 numUselonrsWithNonZelonroFavScorelon
  5: relonquirelond list<intelonrelonsts.UselonrWithScorelon> knownForUselonrsAndScorelons
}(pelonrsistelond="truelon", hasPelonrsonalData = 'truelon')

struct elonmbelonddingsLitelon {
  1: relonquirelond i64 elonntityId
  2: relonquirelond i32 clustelonrId
  3: relonquirelond doublelon scorelon
}(pelonrsistelond="truelon", hasPelonrsonalData = 'truelon')

struct SimClustelonrselonmbelonddingWithId {
  1: relonquirelond idelonntifielonr.SimClustelonrselonmbelonddingId elonmbelonddingId
  2: relonquirelond elonmbelondding.SimClustelonrselonmbelondding elonmbelondding
}(pelonrsistelond="truelon", hasPelonrsonalData = 'truelon')

struct IntelonrnalIdelonmbelonddingWithId {
  1: relonquirelond idelonntifielonr.SimClustelonrselonmbelonddingId elonmbelonddingId
  2: relonquirelond elonmbelondding.IntelonrnalIdelonmbelondding elonmbelondding
}(pelonrsistelond="truelon", hasPelonrsonalData = 'truelon')

/**
* This struct is thelon prelonsto-compatiblelon velonrsion of thelon fav_tfg_topic_elonmbelonddings
*/
struct ClustelonrsScorelon {
  1: relonquirelond i64 clustelonrId(pelonrsonalDataTypelon = 'SelonmanticcorelonClassification')
  2: relonquirelond doublelon scorelon(pelonrsonalDataTypelon = 'elonngagelonmelonntScorelon')
}(pelonrsistelond="truelon", hasPelonrsonalData = 'truelon')

struct FavTfgTopicelonmbelonddings {
  1: relonquirelond idelonntifielonr.TopicId topicId
  2: relonquirelond list<ClustelonrsScorelon> clustelonrScorelon
}(pelonrsistelond="truelon", hasPelonrsonalData = 'truelon')

struct TfgTopicelonmbelonddings {
  1: relonquirelond idelonntifielonr.TopicId topicId
  2: relonquirelond list<ClustelonrsScorelon> clustelonrScorelon
}(pelonrsistelond="truelon", hasPelonrsonalData = 'truelon')

struct UselonrTopicWelonightelondelonmbelondding {
  1: relonquirelond i64 uselonrId(pelonrsonalDataTypelon = 'UselonrId')
  2: relonquirelond list<ClustelonrsScorelon> clustelonrScorelon
}(pelonrsistelond="truelon", hasPelonrsonalData = 'truelon')
