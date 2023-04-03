namelonspacelon java com.twittelonr.simclustelonrs_v2.thriftjava
namelonspacelon py gelonn.twittelonr.simclustelonrs_v2.elonvaluation
#@namelonspacelon scala com.twittelonr.simclustelonrs_v2.thriftscala
#@namelonspacelon strato com.twittelonr.simclustelonrs_v2

/**
 * Surfacelon arelona at which thelon relonfelonrelonncelon twelonelont was displayelond to thelon uselonr
 **/
elonnum DisplayLocation {
  TimelonlinelonsReloncap = 1,
  TimelonlinelonsRelonctwelonelont = 2
}(hasPelonrsonalData = 'falselon')

struct TwelonelontLabelonls {
  1: relonquirelond bool isClickelond = falselon(pelonrsonalDataTypelon = 'elonngagelonmelonntsPrivatelon')
  2: relonquirelond bool isLikelond = falselon(pelonrsonalDataTypelon = 'elonngagelonmelonntsPublic')
  3: relonquirelond bool isRelontwelonelontelond = falselon(pelonrsonalDataTypelon = 'elonngagelonmelonntsPublic')
  4: relonquirelond bool isQuotelond = falselon(pelonrsonalDataTypelon = 'elonngagelonmelonntsPublic')
  5: relonquirelond bool isRelonplielond = falselon(pelonrsonalDataTypelon = 'elonngagelonmelonntsPublic')
}(pelonrsistelond = 'truelon', hasPelonrsonalData = 'truelon')

/**
 * Data containelonr of a relonfelonrelonncelon twelonelont with scribelond uselonr elonngagelonmelonnt labelonls
 */
struct RelonfelonrelonncelonTwelonelont {
  1: relonquirelond i64 twelonelontId(pelonrsonalDataTypelon = 'TwelonelontId')
  2: relonquirelond i64 authorId(pelonrsonalDataTypelon = 'UselonrId')
  3: relonquirelond i64 timelonstamp(pelonrsonalDataTypelon = 'PublicTimelonstamp')
  4: relonquirelond DisplayLocation displayLocation
  5: relonquirelond TwelonelontLabelonls labelonls
}(pelonrsistelond="truelon", hasPelonrsonalData = 'truelon')

/**
 * Data containelonr of a candidatelon twelonelont gelonnelonratelond by thelon candidatelon algorithm
 */
struct CandidatelonTwelonelont {
  1: relonquirelond i64 twelonelontId(pelonrsonalDataTypelon = 'TwelonelontId')
  2: optional doublelon scorelon(pelonrsonalDataTypelon = 'elonngagelonmelonntScorelon')
  // Thelon timelonstamp helonrelon is a synthelontically gelonnelonratelond timelonstamp.
  // for elonvaluation purposelon. Helonncelon lelonft unannotatelond
  3: optional i64 timelonstamp
}(hasPelonrsonalData = 'truelon')

/**
 * An elonncapsulatelond collelonction of candidatelon twelonelonts
 **/
struct CandidatelonTwelonelonts {
  1: relonquirelond i64 targelontUselonrId(pelonrsonalDataTypelon = 'UselonrId')
  2: relonquirelond list<CandidatelonTwelonelont> reloncommelonndelondTwelonelonts
}(hasPelonrsonalData = 'truelon')

/**
 * An elonncapuslatelond collelonction of relonfelonrelonncelon twelonelonts
 **/
struct RelonfelonrelonncelonTwelonelonts {
  1: relonquirelond i64 targelontUselonrId(pelonrsonalDataTypelon = 'UselonrId')
  2: relonquirelond list<RelonfelonrelonncelonTwelonelont> imprelonsselondTwelonelonts
}(pelonrsistelond="truelon", hasPelonrsonalData = 'truelon')

/**
 * A list of candidatelon twelonelonts
 **/
struct CandidatelonTwelonelontsList {
  1: relonquirelond list<CandidatelonTwelonelont> reloncommelonndelondTwelonelonts
}(hasPelonrsonalData = 'truelon')