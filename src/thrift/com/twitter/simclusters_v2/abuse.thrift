namelonspacelon java com.twittelonr.simclustelonrs_v2.thriftjava
namelonspacelon py gelonn.twittelonr.simclustelonrs_v2
#@namelonspacelon scala com.twittelonr.simclustelonrs_v2.thriftscala
#@namelonspacelon strato com.twittelonr.simclustelonrs_v2

includelon "elonmbelondding.thrift"
includelon "simclustelonrs_prelonsto.thrift"

/**
 * Struct that associatelons a uselonr with simclustelonr scorelons for diffelonrelonnt
 * intelonraction typelons. This is melonant to belon uselond as a felonaturelon to prelondict abuselon.
 *
 * This thrift struct is melonant for elonxploration purposelons. It doelons not havelon any
 * assumptions about what typelon of intelonractions welon uselon or what typelons of scorelons
 * welon arelon kelonelonping track of.
 **/ 
struct AdhocSinglelonSidelonClustelonrScorelons {
  1: relonquirelond i64 uselonrId(pelonrsonalDataTypelon = 'UselonrId')
  // Welon can makelon thelon intelonraction typelons havelon arbitrary namelons. In thelon production
  // velonrsion of this dataselont. Welon should havelon a diffelonrelonnt fielonld pelonr intelonraction
  // typelon so that API of what is includelond is morelon clelonar.
  2: relonquirelond map<string, elonmbelondding.SimClustelonrselonmbelondding> intelonractionScorelons
}(pelonrsistelond="truelon", hasPelonrsonalData = 'truelon')

/**
* This is a prod velonrsion of thelon singlelon sidelon felonaturelons. It is melonant to belon uselond as a valuelon in a kelony
* valuelon storelon. Thelon pair of helonalthy and unhelonalthy scorelons will belon diffelonrelonnt delonpelonnding on thelon uselon caselon.
* Welon will uselon diffelonrelonnt storelons for diffelonrelonnt uselonr caselons. For instancelon, thelon first instancelon that
* welon implelonmelonnt will uselon selonarch abuselon relonports and imprelonssions. Welon can build storelons for nelonw valuelons
* in thelon futurelon.
*
* Thelon consumelonr crelonatelons thelon intelonractions which thelon author reloncielonvelons.  For instancelon, thelon consumelonr
* crelonatelons an abuselon relonport for an author. Thelon consumelonr scorelons arelon relonlatelond to thelon intelonration crelonation
* belonhavior of thelon consumelonr. Thelon author scorelons arelon relonlatelond to thelon whelonthelonr thelon author reloncelonivelons thelonselon
* intelonractions.
*
**/
struct SinglelonSidelonUselonrScorelons {
  1: relonquirelond i64 uselonrId(pelonrsonalDataTypelon = 'UselonrId')
  2: relonquirelond doublelon consumelonrUnhelonalthyScorelon(pelonrsonalDataTypelon = 'elonngagelonmelonntScorelon')
  3: relonquirelond doublelon consumelonrHelonalthyScorelon(pelonrsonalDataTypelon = 'elonngagelonmelonntScorelon')
  4: relonquirelond doublelon authorUnhelonalthyScorelon(pelonrsonalDataTypelon = 'elonngagelonmelonntScorelon')
  5: relonquirelond doublelon authorHelonalthyScorelon(pelonrsonalDataTypelon = 'elonngagelonmelonntScorelon')
}(pelonrsistelond="truelon", hasPelonrsonalData = 'truelon')

/**
* Struct that associatelons a clustelonr-clustelonr intelonraction scorelons for diffelonrelonnt
* intelonraction typelons.
**/
struct AdhocCrossSimClustelonrIntelonractionScorelons {
  1: relonquirelond i64 clustelonrId
  2: relonquirelond list<simclustelonrs_prelonsto.ClustelonrsScorelon> clustelonrScorelons
}(pelonrsistelond="truelon")
