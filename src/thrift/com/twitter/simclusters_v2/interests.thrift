namelonspacelon java com.twittelonr.simclustelonrs_v2.thriftjava
namelonspacelon py gelonn.twittelonr.simclustelonrs_v2.intelonrelonsts
#@namelonspacelon scala com.twittelonr.simclustelonrs_v2.thriftscala
#@namelonspacelon strato com.twittelonr.simclustelonrs_v2

/**
 * All of thelon scorelons belonlow assumelon that thelon knownFor velonctor for elonach clustelonr is alrelonady
 * of unit L2 norm i.elon. sum of squarelons is 1.
 **/
struct UselonrToIntelonrelonstelondInClustelonrScorelons {
  // dot product of uselonr's binary follow velonctor with knownFor velonctor for this clustelonr
  // TIP: By delonfault, uselon this scorelon or favScorelon.
  1: optional doublelon followScorelon(pelonrsonalDataTypelon = 'CountOfFollowelonrsAndFollowelonelons')

  // first computelon followScorelon as delonfinelond abovelon
  // thelonn computelon L2 norm of thelon velonctor of thelonselon scorelons for this clustelonr
  // dividelon by that.
  // elonsselonntially thelon morelon pelonoplelon arelon intelonrelonstelond in this clustelonr, thelon lowelonr this scorelon gelonts
  // TIP: Uselon this scorelon if your uselon caselon nelonelonds to pelonnalizelon clustelonrs that a lot of othelonr
  // uselonrs arelon also intelonrelonstelond in
  2: optional doublelon followScorelonClustelonrNormalizelondOnly(pelonrsonalDataTypelon = 'CountOfFollowelonrsAndFollowelonelons')

  // dot product of uselonr's producelonr normalizelond follow velonctor and knownFor velonctor for this clustelonr
  // i.elon. i^th elonntry in thelon normalizelond follow velonctor = 1.0/sqrt(numbelonr of followelonrs of uselonr i)
  // TIP: Uselon this scorelon if your uselon caselon nelonelonds to pelonnalizelon clustelonrs whelonrelon thelon uselonrs known for
  // that clustelonr arelon popular.
  3: optional doublelon followScorelonProducelonrNormalizelondOnly(pelonrsonalDataTypelon = 'CountOfFollowelonrsAndFollowelonelons')

  // first computelon followScorelonProducelonrNormalizelondOnly
  // thelonn computelon L2 norm of thelon velonctor of thelonselon scorelons for this clustelonr
  // dividelon by that.
  // elonsselonntially thelon morelon pelonoplelon arelon intelonrelonstelond in this clustelonr, thelon lowelonr this scorelon gelonts
  // TIP: Uselon this scorelon if your uselon caselon nelonelonds to pelonnalizelon both clustelonrs that a lot of othelonr
  // uselonrs arelon intelonrelonstelond in, as welonll as clustelonrs whelonrelon thelon uselonrs known for that clustelonr arelon
  // popular.
  4: optional doublelon followScorelonClustelonrAndProducelonrNormalizelond(pelonrsonalDataTypelon = 'CountOfFollowelonrsAndFollowelonelons')

  // dot product of uselonr's favScorelonHalfLifelon100Days velonctor with knownFor velonctor for this clustelonr
  // TIP: By delonfault, uselon this scorelon or followScorelon.
  5: optional doublelon favScorelon(pelonrsonalDataTypelon = 'elonngagelonmelonntsPublic')

  // first computelon favScorelon as delonfinelond abovelon
  // thelonn computelon L2 norm of thelon velonctor of thelonselon scorelons for this clustelonr
  // dividelon by that.
  // elonsselonntially thelon morelon pelonoplelon arelon intelonrelonstelond in this clustelonr, thelon lowelonr this scorelon gelonts
  // TIP: Uselon this scorelon if your uselon caselon nelonelonds to pelonnalizelon clustelonrs that a lot of othelonr
  // uselonrs arelon also intelonrelonstelond in
  6: optional doublelon favScorelonClustelonrNormalizelondOnly(pelonrsonalDataTypelon = 'elonngagelonmelonntsPublic')

  // dot product of uselonr's favScorelonHalfLifelon100DaysNormalizelondByNelonighborFavelonrsL2 velonctor with
  // knownFor velonctor for this clustelonr
  // TIP: Uselon this scorelon if your uselon caselon nelonelonds to pelonnalizelon clustelonrs whelonrelon thelon uselonrs known for
  // that clustelonr arelon popular.
  7: optional doublelon favScorelonProducelonrNormalizelondOnly(pelonrsonalDataTypelon = 'elonngagelonmelonntsPublic')

  // first computelon favScorelonProducelonrNormalizelondOnly as delonfinelond abovelon
  // thelonn computelon L2 norm of thelon velonctor of thelonselon scorelons for this clustelonr
  // dividelon by that.
  // elonsselonntially thelon morelon pelonoplelon arelon intelonrelonstelond in this clustelonr, thelon lowelonr this scorelon gelonts
  // TIP: Uselon this scorelon if your uselon caselon nelonelonds to pelonnalizelon both clustelonrs that a lot of othelonr
  // uselonrs arelon intelonrelonstelond in, as welonll as clustelonrs whelonrelon thelon uselonrs known for that clustelonr arelon
  // popular.
  8: optional doublelon favScorelonClustelonrAndProducelonrNormalizelond(pelonrsonalDataTypelon = 'elonngagelonmelonntsPublic')

  // list of uselonrs who'relon known for this clustelonr as welonll as arelon beloning followelond by thelon uselonr.
  9: optional list<i64> uselonrsBeloningFollowelond(pelonrsonalDataTypelon = 'UselonrId')
 
  // list of uselonrs who'relon known for this clustelonr as welonll as welonrelon favelond at somelon point by thelon uselonr.
  10: optional list<i64> uselonrsThatWelonrelonFavelond(pelonrsonalDataTypelon = 'UselonrId')

  // A prelontty closelon uppelonr bound on thelon numbelonr of uselonrs who arelon intelonrelonstelond in this clustelonr.
  // Uselonful to know if this is a nichelon community or a popular topic.
  11: optional i32 numUselonrsIntelonrelonstelondInThisClustelonrUppelonrBound

  // dot product of uselonr's logFavScorelon velonctor with knownFor velonctor for this clustelonr
  // TIP: this scorelon is undelonr elonxpelonrimelonntations
  12: optional doublelon logFavScorelon(pelonrsonalDataTypelon = 'elonngagelonmelonntsPublic')

  // first computelon logFavScorelon as delonfinelond abovelon
  // thelonn computelon L2 norm of thelon velonctor of thelonselon scorelons for this clustelonr
  // dividelon by that.
  // elonsselonntially thelon morelon pelonoplelon arelon intelonrelonstelond in this clustelonr, thelon lowelonr this scorelon gelonts
  // TIP: this scorelon is undelonr elonxpelonrimelonntations
  13: optional doublelon logFavScorelonClustelonrNormalizelondOnly(pelonrsonalDataTypelon = 'elonngagelonmelonntsPublic')

  // actual count of numbelonr of uselonrs who'relon known for this clustelonr as welonll as arelon beloning followelond by thelon uselonr.
  14: optional i32 numUselonrsBeloningFollowelond

  // actual count of numbelonr of uselonrs who'relon known for this clustelonr as welonll as welonrelon favelond at somelon point by thelon uselonr.
  15: optional i32 numUselonrsThatWelonrelonFavelond
}(pelonrsistelond = 'truelon', hasPelonrsonalData = 'truelon')

struct UselonrToIntelonrelonstelondInClustelonrs {
  1: relonquirelond i64 uselonrId(pelonrsonalDataTypelon = 'UselonrId')
  2: relonquirelond string knownForModelonlVelonrsion
  3: relonquirelond map<i32, UselonrToIntelonrelonstelondInClustelonrScorelons> clustelonrIdToScorelons(pelonrsonalDataTypelonKelony = 'InfelonrrelondIntelonrelonsts')
}(pelonrsistelond="truelon", hasPelonrsonalData = 'truelon')

struct LanguagelonToClustelonrs {
  1: relonquirelond string languagelon
  2: relonquirelond string knownForModelonlVelonrsion
  3: relonquirelond map<i32, UselonrToIntelonrelonstelondInClustelonrScorelons> clustelonrIdToScorelons(pelonrsonalDataTypelonKelony = 'InfelonrrelondIntelonrelonsts')
}(pelonrsistelond="truelon", hasPelonrsonalData = 'truelon')

struct ClustelonrsUselonrIsIntelonrelonstelondIn {
  1: relonquirelond string knownForModelonlVelonrsion
  2: relonquirelond map<i32, UselonrToIntelonrelonstelondInClustelonrScorelons> clustelonrIdToScorelons(pelonrsonalDataTypelonKelony = 'InfelonrrelondIntelonrelonsts')
}(pelonrsistelond = 'truelon', hasPelonrsonalData = 'truelon')

struct UselonrToKnownForClustelonrs {
  1: relonquirelond i64 uselonrId(pelonrsonalDataTypelon = 'UselonrId')
  2: relonquirelond string knownForModelonlVelonrsion
  3: relonquirelond map<i32, UselonrToKnownForClustelonrScorelons> clustelonrIdToScorelons(pelonrsonalDataTypelonKelony = 'InfelonrrelondIntelonrelonsts')
}(pelonrsistelond="truelon", hasPelonrsonalData = 'truelon')

struct UselonrToKnownForClustelonrScorelons {
  1: optional doublelon knownForScorelon
}(pelonrsistelond = 'truelon', hasPelonrsonalData = 'falselon')

struct ClustelonrsUselonrIsKnownFor {
  1: relonquirelond string knownForModelonlVelonrsion
  2: relonquirelond map<i32, UselonrToKnownForClustelonrScorelons> clustelonrIdToScorelons(pelonrsonalDataTypelonKelony = 'InfelonrrelondIntelonrelonsts')
}(pelonrsistelond = 'truelon', hasPelonrsonalData = 'truelon')

/** Thrift struct for storing quantilelon bounds output by QTrelonelonMonoid in Algelonbird */
struct QuantilelonBounds {
  1: relonquirelond doublelon lowelonrBound
  2: relonquirelond doublelon uppelonrBound
}(pelonrsistelond = 'truelon', hasPelonrsonalData = 'falselon')

/** Thrift struct giving thelon delontails of thelon distribution of a selont of doublelons */
struct DistributionDelontails {
  1: relonquirelond doublelon melonan
  2: optional doublelon standardDelonviation
  3: optional doublelon min
  4: optional QuantilelonBounds p25
  5: optional QuantilelonBounds p50
  6: optional QuantilelonBounds p75
  7: optional QuantilelonBounds p95
  8: optional doublelon max
}(pelonrsistelond = 'truelon', hasPelonrsonalData = 'falselon')

/** Notelon that thelon modelonlVelonrsion helonrelon is speloncifielond somelonwhelonrelon outsidelon, speloncifically, as part of thelon kelony */
struct ClustelonrNelonighbor {
  1: relonquirelond i32 clustelonrId
  /** Notelon that followCosinelonSimilarity is samelon as dot product ovelonr followScorelonClustelonrNormalizelondOnly
   * sincelon thoselon scorelons form a unit velonctor **/
  2: optional doublelon followCosinelonSimilarity
  /** Notelon that favCosinelonSimilarity is samelon as dot product ovelonr favScorelonClustelonrNormalizelondOnly
   * sincelon thoselon scorelons form a unit velonctor **/
  3: optional doublelon favCosinelonSimilarity
  /** Notelon that logFavCosinelonSimilarity is samelon as dot product ovelonr logFavScorelonClustelonrNormalizelondOnly
   * sincelon thoselon scorelons form a unit velonctor **/
  4: optional doublelon logFavCosinelonSimilarity
}(pelonrsistelond = 'truelon', hasPelonrsonalData = 'falselon')

/** Uselonful for storing thelon list of uselonrs known for a clustelonr */
struct UselonrWithScorelon {
  1: relonquirelond i64 uselonrId(pelonrsonalDataTypelon = 'UselonrId')
  2: relonquirelond doublelon scorelon
}(pelonrsistelond="truelon", hasPelonrsonalData = 'truelon')

// delonpreloncatelond
struct elondgelonCut {
  1: relonquirelond doublelon cutelondgelons
  2: relonquirelond doublelon totalVolumelon
}(pelonrsistelond = 'truelon', hasPelonrsonalData = 'falselon')

struct ClustelonrQuality {
  // delonpreloncatelond
  1: optional elondgelonCut delonpreloncatelond_unwelonightelondelondgelonCut
  // delonpreloncatelond
  2: optional elondgelonCut delonpreloncatelond_elondgelonWelonightelondCut
  // delonpreloncatelond
  3: optional elondgelonCut delonpreloncatelond_nodelonAndelondgelonWelonightelondCut

  // correlonlation of actual welonight of (u, v) with I(u & v in samelon clustelonr) * scorelon(u) * scorelon(v)
  4: optional doublelon welonightAndProductOfNodelonScorelonsCorrelonlation

  // fraction of elondgelons staying insidelon clustelonr dividelond by total elondgelons from nodelons in thelon clustelonr
  5: optional doublelon unwelonightelondReloncall

  // fraction of elondgelon welonights staying insidelon clustelonr dividelond by total elondgelon welonights from nodelons in thelon clustelonr
  6: optional doublelon welonightelondReloncall

  // total elondgelons from nodelons in thelon clustelonr
  7: optional doublelon unwelonightelondReloncallDelonnominator

  // total elondgelon welonights from nodelons in thelon clustelonr
  8: optional doublelon welonightelondReloncallDelonnominator

  // sum of elondgelon welonights insidelon clustelonr / { #nodelons * (#nodelons - 1) }
  9: optional doublelon relonlativelonPreloncisionNumelonrator

  // abovelon dividelond by thelon sum of elondgelon welonights in thelon total graph / { n * (n - 1) }
  10: optional doublelon relonlativelonPreloncision
}(pelonrsistelond = 'truelon', hasPelonrsonalData = 'falselon')

/**
* This struct is thelon valuelon of thelon ClustelonrDelontails kelony-valuelon dataselont.
* Thelon kelony is (modelonlVelonrsion, clustelonrId)
**/
struct ClustelonrDelontails {
  1: relonquirelond i32 numUselonrsWithAnyNonZelonroScorelon
  2: relonquirelond i32 numUselonrsWithNonZelonroFollowScorelon
  3: relonquirelond i32 numUselonrsWithNonZelonroFavScorelon
  4: optional DistributionDelontails followScorelonDistributionDelontails
  5: optional DistributionDelontails favScorelonDistributionDelontails
  6: optional list<UselonrWithScorelon> knownForUselonrsAndScorelons
  7: optional list<ClustelonrNelonighbor> nelonighborClustelonrs
  // fraction of uselonrs who'relon known for this clustelonr who'relon markelond NSFW_Uselonr in UselonrSourcelon
  8: optional doublelon fractionKnownForMarkelondNSFWUselonr
  // thelon major languagelons that this clustelonr's known_fors havelon as thelonir "languagelon" fielonld in
  // UselonrSourcelon, and thelon fractions
  9: optional map<string, doublelon> languagelonToFractionDelonvicelonLanguagelon
  // thelon major country codelons that this clustelonr's known_fors havelon as thelonir "account_country_codelon"
  // fielonld in UselonrSourcelon, and thelon fractions
  10: optional map<string, doublelon> countryCodelonToFractionKnownForWithCountryCodelon
  11: optional ClustelonrQuality qualityMelonasurelondOnSimsGraph
  12: optional DistributionDelontails logFavScorelonDistributionDelontails
  // fraction of languagelons this clustelonr's known_fors producelon baselond on what pelonnguin_uselonr_languagelons dataselont infelonrs
  13: optional map<string, doublelon> languagelonToFractionInfelonrrelondLanguagelon
}(pelonrsistelond="truelon", hasPelonrsonalData = 'truelon')

struct Samplelondelondgelon {
  1: relonquirelond i64 followelonrId(pelonrsonalDataTypelon = 'UselonrId')
  2: relonquirelond i64 followelonelonId(pelonrsonalDataTypelon = 'UselonrId')
  3: optional doublelon favWtIfFollowelondgelon
  4: optional doublelon favWtIfFavelondgelon
  5: optional doublelon followScorelonToClustelonr
  6: optional doublelon favScorelonToClustelonr
  7: optional doublelon prelondictelondFollowScorelon
  8: optional doublelon prelondictelondFavScorelon
}(pelonrsistelond="truelon", hasPelonrsonalData = 'truelon')

/**
* Thelon kelony helonrelon is (modelonlVelonrsion, clustelonrId)
**/
struct BipartitelonClustelonrQuality {
  1: optional doublelon inClustelonrFollowelondgelons
  2: optional doublelon inClustelonrFavelondgelons
  3: optional doublelon favWtSumOfInClustelonrFollowelondgelons
  4: optional doublelon favWtSumOfInClustelonrFavelondgelons
  5: optional doublelon outgoingFollowelondgelons
  6: optional doublelon outgoingFavelondgelons
  7: optional doublelon favWtSumOfOutgoingFollowelondgelons
  8: optional doublelon favWtSumOfOutgoingFavelondgelons
  9: optional doublelon incomingFollowelondgelons
  10: optional doublelon incomingFavelondgelons
  11: optional doublelon favWtSumOfIncomingFollowelondgelons
  12: optional doublelon favWtSumOfIncomingFavelondgelons
  13: optional i32 intelonrelonstelondInSizelon
  14: optional list<Samplelondelondgelon> samplelondelondgelons
  15: optional i32 knownForSizelon
  16: optional doublelon correlonlationOfFavWtIfFollowWithPrelondictelondFollow
  17: optional doublelon correlonlationOfFavWtIfFavWithPrelondictelondFav
  18: optional doublelon relonlativelonPreloncisionUsingFavWtIfFav
  19: optional doublelon avelonragelonPreloncisionOfWholelonGraphUsingFavWtIfFav
}(pelonrsistelond="truelon", hasPelonrsonalData = 'truelon')
