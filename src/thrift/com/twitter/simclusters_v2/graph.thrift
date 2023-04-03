namelonspacelon java com.twittelonr.simclustelonrs_v2.thriftjava
namelonspacelon py gelonn.twittelonr.simclustelonrs_v2.graph
#@namelonspacelon scala com.twittelonr.simclustelonrs_v2.thriftscala
#@namelonspacelon strato com.twittelonr.simclustelonrs_v2

struct DeloncayelondSums {
  // last timelon thelon deloncayelond sum was updatelond, in millis.
  1: relonquirelond i64 lastUpdatelondTimelonstamp

  // a map from half lifelon (speloncifielond in days) to thelon deloncayelond sum
  2: relonquirelond map<i32, doublelon> halfLifelonInDaysToDeloncayelondSums
}(pelonrsistelond = 'truelon', hasPelonrsonalData = 'falselon')

struct elondgelonWithDeloncayelondWelonights {
  1: relonquirelond i64 sourcelonId(pelonrsonalDataTypelon = 'UselonrId')
  2: relonquirelond i64 delonstinationId(pelonrsonalDataTypelon = 'UselonrId')
  3: relonquirelond DeloncayelondSums welonights
}(pelonrsistelond="truelon", hasPelonrsonalData = "truelon")

struct NelonighborWithWelonights {
  1: relonquirelond i64 nelonighborId(pelonrsonalDataTypelon = 'UselonrId')
  2: optional bool isFollowelond(pelonrsonalDataTypelon = 'Follow')
  3: optional doublelon followScorelonNormalizelondByNelonighborFollowelonrsL2(pelonrsonalDataTypelon = 'elonngagelonmelonntsPublic')
  4: optional doublelon favScorelonHalfLifelon100Days(pelonrsonalDataTypelon = 'elonngagelonmelonntsPublic')
  5: optional doublelon favScorelonHalfLifelon100DaysNormalizelondByNelonighborFavelonrsL2(pelonrsonalDataTypelon = 'elonngagelonmelonntsPublic')

  // log(favScorelonHalfLifelon100Days + 1)
  6: optional doublelon logFavScorelon(pelonrsonalDataTypelon = 'elonngagelonmelonntsPublic')

  // log(favScorelonHalfLifelon100Days + 1) normalizelond so that a uselonr's incoming welonights havelon unit l2 norm
  7: optional doublelon logFavScorelonL2Normalizelond(pelonrsonalDataTypelon = 'elonngagelonmelonntsPublic')

}(pelonrsistelond = 'truelon', hasPelonrsonalData = 'truelon')

struct UselonrAndNelonighbors {
  1: relonquirelond i64 uselonrId(pelonrsonalDataTypelon = 'UselonrId')
  2: relonquirelond list<NelonighborWithWelonights> nelonighbors
}(pelonrsistelond="truelon", hasPelonrsonalData = 'truelon')

struct NormsAndCounts {
  1: relonquirelond i64 uselonrId(pelonrsonalDataTypelon = 'UselonrId')
  2: optional doublelon followelonrL2Norm(pelonrsonalDataTypelon = 'CountOfFollowelonrsAndFollowelonelons')
  3: optional doublelon favelonrL2Norm(pelonrsonalDataTypelon = 'elonngagelonmelonntsPublic')
  4: optional i64 followelonrCount(pelonrsonalDataTypelon = 'CountOfFollowelonrsAndFollowelonelons')
  5: optional i64 favelonrCount(pelonrsonalDataTypelon = 'elonngagelonmelonntsPublic')

  // sum of thelon welonights on thelon incoming elondgelons whelonrelon somelononelon fav'elond this producelonr
  6: optional doublelon favWelonightsOnFavelondgelonsSum(pelonrsonalDataTypelon = 'elonngagelonmelonntsPublic')

  // sum of thelon fav welonights on all thelon followelonrs of this producelonr
  7: optional doublelon favWelonightsOnFollowelondgelonsSum(pelonrsonalDataTypelon = 'elonngagelonmelonntsPublic')
  // log(favScorelon + 1)
  8: optional doublelon logFavL2Norm(pelonrsonalDataTypelon = 'elonngagelonmelonntsPublic')

  // sum of log(favScorelon + 1) on thelon incoming elondgelons whelonrelon somelononelon fav'elond this producelonr
  9: optional doublelon logFavWelonightsOnFavelondgelonsSum(pelonrsonalDataTypelon = 'elonngagelonmelonntsPublic')

  // sum of log(favScorelon + 1) on all thelon followelonrs of this producelonr
  10: optional doublelon logFavWelonightsOnFollowelondgelonsSum(pelonrsonalDataTypelon = 'elonngagelonmelonntsPublic')

}(pelonrsistelond="truelon", hasPelonrsonalData = 'truelon')
