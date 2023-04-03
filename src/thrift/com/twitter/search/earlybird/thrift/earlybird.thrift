namelonspacelon java com.twittelonr.selonarch.elonarlybird.thrift
#@namelonspacelon scala com.twittelonr.selonarch.elonarlybird.thriftscala
#@namelonspacelon strato com.twittelonr.selonarch.elonarlybird
namelonspacelon py gelonn.twittelonr.selonarch.elonarlybird

includelon "com/twittelonr/ads/adselonrvelonr/adselonrvelonr_common.thrift"
includelon "com/twittelonr/selonarch/common/caching/caching.thrift"
includelon "com/twittelonr/selonarch/common/constants/quelonry.thrift"
includelon "com/twittelonr/selonarch/common/constants/selonarch_languagelon.thrift"
includelon "com/twittelonr/selonarch/common/convelonrsation/convelonrsation.thrift"
includelon "com/twittelonr/selonarch/common/felonaturelons/felonaturelons.thrift"
includelon "com/twittelonr/selonarch/common/indelonxing/status.thrift"
includelon "com/twittelonr/selonarch/common/quelonry/selonarch.thrift"
includelon "com/twittelonr/selonarch/common/ranking/ranking.thrift"
includelon "com/twittelonr/selonarch/common/relonsults/elonxpansions.thrift"
includelon "com/twittelonr/selonarch/common/relonsults/highlight.thrift"
includelon "com/twittelonr/selonarch/common/relonsults/hit_attribution.thrift"
includelon "com/twittelonr/selonarch/common/relonsults/hits.thrift"
includelon "com/twittelonr/selonarch/common/relonsults/social.thrift"
includelon "com/twittelonr/selonrvicelon/spidelonrduck/gelonn/melontadata_storelon.thrift"
includelon "com/twittelonr/twelonelontypielon/delonpreloncatelond.thrift"
includelon "com/twittelonr/twelonelontypielon/twelonelont.thrift"
includelon "com/twittelonr/elonschelonrbird/twelonelont_annotation.thrift"

elonnum ThriftSelonarchRankingModelon {
  // good old relonaltimelon selonarch modelon
  RelonCelonNCY = 0,
  // nelonw supelonr fancy relonlelonvancelon ranking
  RelonLelonVANCelon = 1,
  DelonPRelonCATelonD_DISCOVelonRY = 2,
  // top twelonelonts ranking modelon
  TOPTWelonelonTS = 3,
  // relonsults from accounts followelond by thelon selonarchelonr
  FOLLOWS = 4,

  PLACelon_HOLDelonR5 = 5,
  PLACelon_HOLDelonR6 = 6,
}

elonnum ThriftSelonarchRelonsultTypelon {
  // it's a timelon-ordelonrelond relonsult.
  RelonCelonNCY = 0,
  // it's a highly relonlelonvant twelonelont (aka top twelonelont).
  RelonLelonVANCelon = 1,
  // top twelonelont relonsult typelon
  POPULAR = 2,
  // promotelond twelonelonts (ads)
  PROMOTelonD = 3,
  // relonlelonvancelon-ordelonrelond (as opposelond to timelon-ordelonrelond) twelonelonts gelonnelonratelond from a varielonty of candidatelons
  RelonLelonVANCelon_ORDelonRelonD = 4,

  PLACelon_HOLDelonR5 = 5,
  PLACelon_HOLDelonR6 = 6,
}

elonnum ThriftSocialFiltelonrTypelon {
  // filtelonr only uselonrs that thelon selonarchelonr is direlonctly following.
  FOLLOWS = 0,
  // filtelonr only uselonrs that arelon in selonarchelonr's social circlelon of trust.
  TRUSTelonD = 1,
  // filtelonr both follows and trustelond.
  ALL = 2,

  PLACelon_HOLDelonR3 = 3,
  PLACelon_HOLDelonR4 = 4,

}

elonnum ThriftTwelonelontSourcelon {
  ///// elonnums selont by elonarlybird
  RelonALTIMelon_CLUSTelonR = 1,
  FULL_ARCHIVelon_CLUSTelonR = 2,
  RelonALTIMelon_PROTelonCTelonD_CLUSTelonR = 4,

  ///// elonnums selont insidelon Blelonndelonr
  ADSelonRVelonR = 0,
  // from top nelonws selonarch, only uselond in univelonrsal selonarch
  TOP_NelonWS = 3,
  // speloncial twelonelonts includelond just for elonvelonntParrot.
  FORCelon_INCLUDelonD = 5,
  // from Contelonnt Reloncommelonndelonr
  // from topic to Twelonelont path
  CONTelonNT_RelonCS_TOPIC_TO_TWelonelonT = 6,
  // uselond for hydrating QIG Twelonelonts (go/qig)
  QIG = 8,
  // uselond for TOPTWelonelonTS ranking modelon
  TOP_TWelonelonT = 9,
  // uselond for elonxpelonrimelonntal candidatelon sourcelons
  elonXPelonRIMelonNTAL = 7,
  // from Scanr selonrvicelon
  SCANR = 10,

  PLACelon_HOLDelonR11 = 11,
  PLACelon_HOLDelonR12 = 12
}

elonnum NamelondelonntitySourcelon {
  TelonXT = 0,
  URL = 1,

  PLACelon_HOLDelonR2 = 2,
  PLACelon_HOLDelonR3 = 3,
  PLACelon_HOLDelonR4 = 4,
}

elonnum elonxpelonrimelonntClustelonr {
  elonXP0 = 0, // Selonnd relonquelonsts to thelon elonarlybird-relonaltimelon-elonxp0 clustelonr
  PLACelon_HOLDelonR1 = 1,
  PLACelon_HOLDelonR2 = 2,
}

elonnum AudioSpacelonStatelon {
   RUNNING = 0,
   elonNDelonD = 1,

   PLACelon_HOLDelonR2 = 2,
   PLACelon_HOLDelonR3 = 3,
   PLACelon_HOLDelonR4 = 4,
   PLACelon_HOLDelonR5 = 5,
}

// Contains all scoring and relonlelonvancelon-filtelonring relonlatelond controls and options for elonarlybird.
struct ThriftSelonarchRelonlelonvancelonOptions {
  // Nelonxt availablelon fielonld ID: 31 and notelon that 45 and 50 havelon belonelonn uselond alrelonady

  2: optional bool filtelonrDups = 0         // filtelonr out duplicatelon selonarch relonsults
  26: optional bool kelonelonpDupWithHighelonrScorelon = 1 // kelonelonp thelon duplicatelon twelonelont with thelon highelonr scorelon

  3: optional bool proximityScoring = 0   // whelonthelonr to do proximity scoring or not
  4: optional i32 maxConseloncutivelonSamelonUselonr  // filtelonr conseloncutivelon relonsults from thelon samelon uselonr
  5: optional ranking.ThriftRankingParams rankingParams  // composelond by blelonndelonr
  // delonpreloncatelond in favor of thelon maxHitsToProcelonss in CollelonctorParams
  6: optional i32 maxHitsToProcelonss // whelonn to elonarly-telonrminatelon for relonlelonvancelon
  7: optional string elonxpelonrimelonntNamelon      // what relonlelonvancelon elonxpelonrimelonnt is running
  8: optional string elonxpelonrimelonntBuckelont    // what buckelont thelon uselonr is in; DDG delonfaults to hard-codelond 'control'
  9: optional bool intelonrprelontSincelonId = 1   // whelonthelonr to intelonrprelont sincelon_id opelonrator

  24: optional i32 maxHitsPelonrUselonr // Ovelonrridelons ThriftSelonarchQuelonry.maxHitsPelonrUselonr

  // only uselond by discovelonry for capping direlonct follow twelonelonts
  10: optional i32 maxConseloncutivelonDirelonctFollows

  // Notelon - thelon ordelonrByRelonlelonvancelon flag is critical to undelonrstanding how melonrging
  // and trimming works in relonlelonvancelon modelon in thelon selonarch root.
  //
  // Whelonn ordelonrByRelonlelonvancelon is truelon, relonsults arelon trimmelond in scorelon-ordelonr.  This melonans thelon
  // clielonnt will gelont thelon top relonsults from (maxHitsToProcelonss * numHashPartitions) hits,
  // ordelonrelond by scorelon.
  //
  // Whelonn ordelonrByRelonlelonvancelon is falselon, relonsults arelon trimmelond in id-ordelonr.  This melonans thelon
  // clielonnt will gelont thelon top relonsults from an approximation of maxHitsToProcelonss hits
  // (across thelon elonntirelon corpus).  Thelonselon relonsults ordelonrelond by ID.
  14: optional bool ordelonrByRelonlelonvancelon = 0

  // Max blelonnding count for relonsults relonturnelond duelon to from:uselonr relonwritelons
  16: optional i32 maxUselonrBlelonndCount

  // Thelon welonight for proximity phraselons gelonnelonratelond whilelon translating thelon selonrializelond quelonry to thelon
  // lucelonnelon quelonry.
  19: optional doublelon proximityPhraselonWelonight = 1.0
  20: optional i32 proximityPhraselonSlop = 255

  // Ovelonrridelon thelon welonights of selonarchablelon fielonlds.
  // Nelongativelon welonight melonans thelon thelon fielonld is not elonnablelond for selonarch by delonfault,
  // but if it is (elon.g., by annotation), thelon absolutelon valuelon of thelon welonight shall belon
  // uselond (if thelon annotation doelons not speloncify a welonight).
  21: optional map<string, doublelon> fielonldWelonightMapOvelonrridelon

  // whelonthelonr disablelon thelon coordination in thelon relonwrittelonn disjunction quelonry, telonrm quelonry and phraselon quelonry
  // thelon delontails can belon found in LucelonnelonVisitor
  22: optional bool delonpreloncatelond_disablelonCoord = 0

  // Root only. Relonturns all relonsults selonelonn by root to thelon clielonnt without trimming
  // if selont to truelon.
  23: optional bool relonturnAllRelonsults

  // DelonPRelonCATelonD: All v2 countelonrs will belon uselond elonxplicitly in thelon scoring function and
  // relonturnelond in thelonir own fielonld (in elonithelonr melontadata or felonaturelon map in relonsponselon).
  25: optional bool uselonelonngagelonmelonntCountelonrsV2 = 0

  // -------- PelonRSONALIZATION-RelonLATelonD RelonLelonVANCelon OPTIONS --------
  // Takelon speloncial carelon with thelonselon options whelonn relonasoning about caching.

  // Delonpreloncatelond in SelonARCH-8616.
  45: optional map<i32, doublelon> delonpreloncatelond_topicIDWelonights

  // Collelonct hit attribution on quelonrielons and likelondByUselonrIDFiltelonr64-elonnhancelond quelonrielons to
  // gelont likelondByUselonrIds list in melontadata fielonld.
  // NOTelon: this flag has no affelonct on fromUselonrIDFiltelonr64.
  50: optional bool collelonctFielonldHitAttributions = 0

  // Whelonthelonr to collelonct all hits relongardlelonss of thelonir scorelon with RelonlelonvancelonAllCollelonctor.
  27: optional bool uselonRelonlelonvancelonAllCollelonctor = 0

  // Ovelonrridelon felonaturelons of speloncific twelonelonts belonforelon thelon twelonelonts arelon scorelond.
  28: optional map<i64, felonaturelons.ThriftSelonarchRelonsultFelonaturelons> pelonrTwelonelontFelonaturelonsOvelonrridelon

  // Ovelonrridelon felonaturelons of all twelonelonts from speloncific uselonrs belonforelon thelon twelonelonts arelon scorelond.
  29: optional map<i64, felonaturelons.ThriftSelonarchRelonsultFelonaturelons> pelonrUselonrFelonaturelonsOvelonrridelon

  // Ovelonrridelon felonaturelons of all twelonelonts belonforelon thelon twelonelonts arelon scorelond.
  30: optional felonaturelons.ThriftSelonarchRelonsultFelonaturelons globalFelonaturelonsOvelonrridelon
}(pelonrsistelond='truelon')

// Facelonts typelons that may havelon diffelonrelonnt ranking paramelontelonrs.
elonnum ThriftFacelontTypelon {
  DelonFAULT = 0,
  MelonNTIONS_FACelonT = 1,
  HASHTAGS_FACelonT = 2,
  // Delonpreloncatelond in SelonARCH-13708
  DelonPRelonCATelonD_NAMelonD_elonNTITIelonS_FACelonT = 3,
  STOCKS_FACelonT = 4,
  VIDelonOS_FACelonT = 5,
  IMAGelonS_FACelonT = 6,
  NelonWS_FACelonT = 7,
  LANGUAGelonS_FACelonT = 8,
  SOURCelonS_FACelonT = 9,
  TWIMG_FACelonT = 10,
  FROM_USelonR_ID_FACelonT = 11,
  DelonPRelonCATelonD_TOPIC_IDS_FACelonT = 12,
  RelonTWelonelonTS_FACelonT = 13,
  LINKS_FACelonT = 14,

  PLACelon_HOLDelonR15 = 15,
  PLACelon_HOLDelonR16 = 16,
}

struct ThriftSelonarchDelonbugOptions {
  // Makelon elonarlybird only scorelon and relonturn twelonelonts (speloncifielond by twelonelont id) helonrelon, relongardlelonss
  // if thelony havelon a hit for thelon currelonnt quelonry or not.
  1: optional selont<i64> statusIds;

  // Assortelond structurelons to pass in delonbug options.
  2: optional map<string, string> stringMap;
  3: optional map<string, doublelon> valuelonMap;
  4: optional list<doublelon> valuelonList;
}(pelonrsistelond='truelon')

// Thelonselon options control what melontadata will belon relonturnelond by elonarlybird for elonach selonarch relonsult
// in thelon ThriftSelonarchRelonsultMelontadata struct.  Thelonselon options arelon currelonntly mostly supportelond by
// AbstractRelonlelonvancelonCollelonctor and partially in SelonarchRelonsultsCollelonctor.  Most arelon truelon by delonfault to
// prelonselonrvelon backwards compatibility, but can belon disablelond as neloncelonssary to optimizelon selonarchelons relonturning
// many relonsults (such as discovelonr).
struct ThriftSelonarchRelonsultMelontadataOptions {
  // If truelon, fills in thelon twelonelontUrls fielonld in ThriftSelonarchRelonsultMelontadata.
  // Populatelond by AbstractRelonlelonvancelonCollelonctor.
  1: optional bool gelontTwelonelontUrls = 1

  // If truelon, fills in thelon relonsultLocation fielonld in ThriftSelonarchRelonsultMelontadata.
  // Populatelond by AbstractRelonlelonvancelonCollelonctor.
  2: optional bool gelontRelonsultLocation = 1
  
  // Delonpreloncatelond in SelonARCH-8616.
  3: optional bool delonpreloncatelond_gelontTopicIDs = 1

  // If truelon, fills in thelon lucelonnelonScorelon fielonld in ThriftSelonarchRelonsultMelontadata.
  // Populatelond by LinelonarScoringFunction.
  4: optional bool gelontLucelonnelonScorelon = 0

  // Delonpreloncatelond but uselond to belon for Offlinelon felonaturelon valuelons for static indelonx
  5: optional bool delonpreloncatelond_gelontelonxpFelonaturelonValuelons = 0

  // If truelon, will omit all felonaturelons delonrivablelon from packelondFelonaturelons, and selont packelondFelonaturelons
  // instelonad.
  6: optional bool delonpreloncatelond_uselonPackelondFelonaturelons = 0

  // If truelon, fills sharelondStatusId. For relonplielons this is thelon in-relonply-to status id and for
  // relontwelonelonts this is thelon relontwelonelont sourcelon status id.
  // Also fills in thelon thelon isRelontwelonelont and isRelonply flags.
  7: optional bool gelontInRelonplyToStatusId = 0

  // If truelon, fills relonfelonrelonncelondTwelonelontAuthorId. Also fills in thelon thelon isRelontwelonelont and isRelonply flags.
  8: optional bool gelontRelonfelonrelonncelondTwelonelontAuthorId = 0

  // If truelon, fills melondia bits (videlono/vinelon/pelonriscopelon/elontc.)
  9: optional bool gelontMelondiaBits = 0

  // If truelon, will relonturn all delonfinelond felonaturelons in thelon packelond felonaturelons.  This flag doelons not covelonr
  // thelon abovelon delonfinelond felonaturelons.
  10: optional bool gelontAllFelonaturelons = 0

  // If truelon, will relonturn all felonaturelons as ThriftSelonarchRelonsultFelonaturelons format.
  11: optional bool relonturnSelonarchRelonsultFelonaturelons = 0

  // If thelon clielonnt cachelons somelon felonaturelons schelonmas, clielonnt can indicatelon its cachelon schelonmas through
  // this fielonld baselond on (velonrsion, cheloncksum).
  12: optional list<felonaturelons.ThriftSelonarchFelonaturelonSchelonmaSpeloncifielonr> felonaturelonSchelonmasAvailablelonInClielonnt

  // Speloncific felonaturelon IDs to relonturn for reloncelonncy relonquelonsts. Populatelond in SelonarchRelonsultFelonaturelons.
  // Valuelons must belon IDs of CSF fielonlds from elonarlybirdFielonldConstants.
  13: optional list<i32> relonquelonstelondFelonaturelonIDs

  // If truelon, fills in thelon namelondelonntitielons fielonld in ThriftSelonarchRelonsultelonxtraMelontadata
  14: optional bool gelontNamelondelonntitielons = 0

  // If truelon, fills in thelon elonntityAnnotations fielonld in ThriftSelonarchRelonsultelonxtraMelontadata
  15: optional bool gelontelonntityAnnotations = 0

  // If truelon, fills in thelon fromUselonrId fielonld in thelon ThriftSelonarchRelonsultelonxtraMelontadata
  16: optional bool gelontFromUselonrId = 0

  // If truelon, fills in thelon spacelons fielonld in thelon ThriftSelonarchRelonsultelonxtraMelontadata
  17: optional bool gelontSpacelons = 0

  18: optional bool gelontelonxclusivelonConvelonrsationAuthorId = 0
}(pelonrsistelond='truelon')


// ThriftSelonarchQuelonry delonscribelons an elonarlybird selonarch relonquelonst, which typically consists
// of thelonselon parts:
//  - a quelonry to relontrielonvelon hits
//  - relonlelonvancelon options to scorelon hits
//  - a collelonctor to collelonct hits and procelonss into selonarch relonsults
// Notelon that this struct is uselond in both ThriftBlelonndelonrRelonquelonst and elonarlybirdRelonquelonst.
// Most fielonlds arelon not selont whelonn this struct is elonmbelonddelond in ThriftBlelonndelonrRelonquelonst, and
// arelon fillelond in by thelon blelonndelonr belonforelon selonnding to elonarlybird.
struct ThriftSelonarchQuelonry {
  // Nelonxt availablelon fielonld ID: 42

  // -------- SelonCTION ZelonRO: THINGS USelonD ONLY BY THelon BLelonNDelonR --------
  // Selonelon SelonARCHQUAL-2398
  // Thelonselon fielonlds arelon uselond by thelon blelonndelonr and clielonnts of thelon blelonndelonr, but not by elonarlybird.

  // blelonndelonr uselon only
  // Thelon raw un-parselond uselonr selonarch quelonry.
  6: optional string rawQuelonry(pelonrsonalDataTypelon = 'SelonarchQuelonry')

  // blelonndelonr uselon only
  // Languagelon of thelon rawQuelonry.
  18: optional string quelonryLang(pelonrsonalDataTypelon = 'InfelonrrelondLanguagelon')

  // blelonndelonr uselon only
  // What pagelon of relonsults to relonturn, indelonxelond from 1.
  7: optional i32 pagelon = 1

  // blelonndelonr uselon only
  // Numbelonr of relonsults to skip (for pagination).  Indelonxelond from 0.
  2: optional i32 delonpreloncatelond_relonsultOffselont = 0


  // -------- SelonCTION ONelon: RelonTRIelonVAL OPTIONS --------
  // Thelonselon options control thelon quelonry that will belon uselond to relontrielonvelon documelonnts / hits.

  // Thelon parselond quelonry trelonelon, selonrializelond to a string.  Relonstricts thelon selonarch relonsults to
  // twelonelonts matching this quelonry.
  1: optional string selonrializelondQuelonry(pelonrsonalDataTypelon = 'SelonarchQuelonry')

  // Relonstricts thelon selonarch relonsults to twelonelonts having this minimum twelonelonp crelond, out of 100.
  5: optional i32 minTwelonelonpCrelondFiltelonr = -1

  // Relonstricts thelon selonarch relonsults to twelonelonts from thelonselon uselonrs.
  34: optional list<i64> fromUselonrIDFiltelonr64(pelonrsonalDataTypelon = 'PrivatelonAccountsFollowing, PublicAccountsFollowing')
  // Relonstricts thelon selonarch relonsults to twelonelonts likelond by thelonselon uselonrs.
  40: optional list<i64> likelondByUselonrIDFiltelonr64(pelonrsonalDataTypelon = 'PrivatelonAccountsFollowing, PublicAccountsFollowing')

  // If selonarchStatusIds arelon prelonselonnt, elonarlybird will ignorelon thelon selonrializelondQuelonry complelontelonly
  // and simply scorelon elonach of selonarchStatusIds, also bypassing felonaturelons likelon duplicatelon
  // filtelonring and elonarly telonrmination.
  // IMPORTANT: this melonans that it is possiblelon to gelont scorelons elonqual to ScoringFunction.SKIP_HIT,
  // for relonsults skippelond by thelon scoring function.
  31: optional selont<i64> selonarchStatusIds

  35: optional selont<i64> delonpreloncatelond_elonvelonntClustelonrIdsFiltelonr

  41: optional map<string, list<i64>> namelondDisjunctionMap

  // -------- SelonCTION TWO: HIT COLLelonCTOR OPTIONS --------
  // Thelonselon options control what hits will belon collelonctelond by thelon hit collelonctor.
  // Whelonthelonr welon want to collelonct and relonturn pelonr-fielonld hit attributions is selont in RelonlelonvancelonOptions.
  // Selonelon SelonARCH-2784
  // Numbelonr of relonsults to relonturn (aftelonr offselont/pagelon correlonction).
  // This is ignorelond whelonn selonarchStatusIds is selont.
  3: relonquirelond i32 numRelonsults

  // Maximum numbelonr of hits to procelonss by thelon collelonctor.
  // delonpreloncatelond in favor of thelon maxHitsToProcelonss in CollelonctorParams
  4: optional i32 maxHitsToProcelonss = 1000

  // Collelonct hit counts for thelonselon timelon pelonriods (in milliselonconds).
  30: optional list<i64> hitCountBuckelonts

  // If selont, elonarlybird will also relonturn thelon facelont labelonls of thelon speloncifielond facelont fielonlds
  // in relonsult twelonelonts.
  33: optional list<string> facelontFielonldNamelons

  // Options controlling which selonarch relonsult melontadata is relonturnelond.
  36: optional ThriftSelonarchRelonsultMelontadataOptions relonsultMelontadataOptions

  // Collelonction relonlatelond Params
  38: optional selonarch.CollelonctorParams collelonctorParams

  // Whelonthelonr to collelonct convelonrsation IDs
  39: optional bool collelonctConvelonrsationId = 0

  // -------- SelonCTION THRelonelon: RelonLelonVANCelon OPTIONS --------
  // Thelonselon options control relonlelonvancelon scoring and anti-gaming.

  // Ranking modelon (RelonCelonNCY melonans timelon-ordelonrelond ranking with no relonlelonvancelon).
  8: optional ThriftSelonarchRankingModelon rankingModelon = ThriftSelonarchRankingModelon.RelonCelonNCY

  // Relonlelonvancelon scoring options.
  9: optional ThriftSelonarchRelonlelonvancelonOptions relonlelonvancelonOptions

  // Limits thelon numbelonr of hits that can belon contributelond by thelon samelon uselonr, for anti-gaming.
  // Selont to -1 to disablelon thelon anti-gaming filtelonr.  This is ignorelond whelonn selonarchStatusIds
  // is selont.
  11: optional i32 maxHitsPelonrUselonr = 3

  // Disablelons anti-gaming filtelonr cheloncks for any twelonelonts that elonxcelonelond this twelonelonpcrelond.
  12: optional i32 maxTwelonelonpcrelondForAntiGaming = 65

  // -------- PelonRSONALIZATION-RelonLATelonD RelonLelonVANCelon OPTIONS --------
  // Takelon speloncial carelon with thelonselon options whelonn relonasoning about caching.  All of thelonselon
  // options, if selont, will bypass thelon cachelon with thelon elonxcelonption of uiLang which is thelon
  // only form of pelonrsonalization allowelond for caching.

  // Uselonr ID of selonarchelonr.  This is uselond for relonlelonvancelon, and will belon uselond for relontrielonval
  // by thelon protelonctelond twelonelonts indelonx.  If selont, quelonry will not belon cachelond.
  20: optional i64 selonarchelonrId(pelonrsonalDataTypelon = 'UselonrId')

  // Bloom filtelonr containing trustelond uselonr IDs.  If selont, quelonry will not belon cachelond.
  10: optional binary trustelondFiltelonr(pelonrsonalDataTypelon = 'UselonrId')

  // Bloom filtelonr containing direlonct follow uselonr IDs.  If selont, quelonry will not belon cachelond.
  16: optional binary direlonctFollowFiltelonr(pelonrsonalDataTypelon = 'UselonrId, PrivatelonAccountsFollowing, PublicAccountsFollowing')

  // UI languagelon from thelon selonarchelonr's profilelon selonttings.
  14: optional string uiLang(pelonrsonalDataTypelon = 'GelonnelonralSelonttings')

  // Confidelonncelon of thelon undelonrstandability of diffelonrelonnt languagelons for this uselonr.
  // uiLang fielonld abovelon is trelonatelond as a uselonrlang with a confidelonncelon of 1.0.
  28: optional map<selonarch_languagelon.ThriftLanguagelon, doublelon> uselonrLangs(pelonrsonalDataTypelonKelony = 'InfelonrrelondLanguagelon')

  // An altelonrnativelon to fromUselonrIDFiltelonr64 that relonlielons on thelon relonlelonvancelon bloom filtelonrs
  // for uselonr filtelonring.  Not currelonntly uselond in production.  Only supportelond for relonaltimelon
  // selonarchelons.
  // If selont, elonarlybird elonxpeloncts both trustelondFiltelonr and direlonctFollowFiltelonr to also belon selont.
  17: optional ThriftSocialFiltelonrTypelon socialFiltelonrTypelon

  // -------- SelonCTION FOUR: DelonBUG OPTIONS, FORGOTTelonN FelonATURelonS --------

  // elonarlybird selonarch delonbug options.
  19: optional ThriftSelonarchDelonbugOptions delonbugOptions

  // Ovelonrridelons thelon quelonry timelon for delonbugging.
  29: optional i64 timelonstampMseloncs = 0

  // Support for this felonaturelon has belonelonn relonmovelond and this fielonld is lelonft for backwards compatibility
  // (and to delontelonct impropelonr usagelon by clielonnts whelonn it is selont).
  25: optional list<string> delonpreloncatelond_itelonrativelonQuelonrielons

  // Speloncifielons a lucelonnelon quelonry that will only belon uselond if selonrializelondQuelonry is not selont,
  // for delonbugging.  Not currelonntly uselond in production.
  27: optional string lucelonnelonQuelonry(pelonrsonalDataTypelon = 'SelonarchQuelonry')

  // This fielonld is delonpreloncatelond and is not uselond by elonarlybirds whelonn procelonssing thelon quelonry.
  21: optional i32 delonpreloncatelond_minDocsToProcelonss = 0
}(pelonrsistelond='truelon', hasPelonrsonalData = 'truelon')


struct ThriftFacelontLabelonl {
  1: relonquirelond string fielonldNamelon
  2: relonquirelond string labelonl
  // thelon numbelonr of timelons this facelont has shown up in twelonelonts with offelonnsivelon words.
  3: optional i32 offelonnsivelonCount = 0

  // only fillelond for TWIMG facelonts
  4: optional string nativelonPhotoUrl
}(pelonrsistelond='truelon')

struct ThriftSelonarchRelonsultGelonoLocation {
  1: optional doublelon latitudelon(pelonrsonalDataTypelon = 'GpsCoordinatelons')
  2: optional doublelon longitudelon(pelonrsonalDataTypelon = 'GpsCoordinatelons')
  3: optional doublelon distancelonKm
}(pelonrsistelond='truelon', hasPelonrsonalData = 'truelon')

// Contains an elonxpandelond url and melondia typelon from thelon URL facelont fielonlds in elonarlybird.
// Notelon: thrift copielond from status.thrift with unuselond fielonlds relonnamelond.
struct ThriftSelonarchRelonsultUrl {
  // Nelonxt availablelon fielonld ID: 6.  Fielonlds 2-4 relonmovelond.

  // Notelon: this is actually thelon elonxpandelond url.  Relonnamelon aftelonr delonpreloncatelond fielonlds arelon relonmovelond.
  1: relonquirelond string originalUrl

  // Melondia typelon of thelon url.
  5: optional melontadata_storelon.MelondiaTypelons melondiaTypelon
}(pelonrsistelond='truelon')

struct ThriftSelonarchRelonsultNamelondelonntity {
  1: relonquirelond string canonicalNamelon
  2: relonquirelond string elonntityTypelon
  3: relonquirelond NamelondelonntitySourcelon sourcelon
}(pelonrsistelond='truelon')

struct ThriftSelonarchRelonsultAudioSpacelon {
  1: relonquirelond string id
  2: relonquirelond AudioSpacelonStatelon statelon
}(pelonrsistelond='truelon')

// elonvelonn morelon melontadata
struct ThriftSelonarchRelonsultelonxtraMelontadata {
  // Nelonxt availablelon fielonld ID: 49

  1: optional doublelon uselonrLangScorelon
  2: optional bool hasDiffelonrelonntLang
  3: optional bool haselonnglishTwelonelontAndDiffelonrelonntUILang
  4: optional bool haselonnglishUIAndDiffelonrelonntTwelonelontLang
  5: optional i32 quotelondCount
  6: optional doublelon quelonrySpeloncificScorelon
  7: optional bool hasQuotelon
  29: optional i64 quotelondTwelonelontId
  30: optional i64 quotelondUselonrId
  31: optional selonarch_languagelon.ThriftLanguagelon cardLang
  8: optional i64 convelonrsationId
  9: optional bool isSelonnsitivelonContelonnt
  10: optional bool hasMultiplelonMelondiaFlag
  11: optional bool profilelonIselonggFlag
  12: optional bool isUselonrNelonwFlag
  26: optional doublelon authorSpeloncificScorelon
  28: optional bool isComposelonrSourcelonCamelonra

  // telonmporary V2 elonngagelonmelonnt countelonrs, original onelons in ThriftSelonarchRelonsultMelontadata has log()
  // applielond on thelonm and thelonn convelonrtelond to int in Thrift, which is elonffelonctivelonly a prelonmaturelon
  // discrelontization. It doelonsn't affelonct thelon scoring insidelon elonarlybird but for scoring and ML training
  // outsidelon elonarlybird, thelony welonrelon bad. Thelonselon nelonwly addelond onelons storelons a propelonr valuelon of thelonselon
  // counts. This also providelons an elonasielonr transition to v2 countelonr whelonn elonarlybird is elonvelonntually
  // relonady to consumelon thelonm from DL
  // Selonelon SelonARCHQUAL-9536, SelonARCH-11181
  18: optional i32 relontwelonelontCountV2
  19: optional i32 favCountV2
  20: optional i32 relonplyCountV2
  // Twelonelonpcrelond welonightelond velonrsion of various elonngagelonmelonnt counts
  22: optional i32 welonightelondRelontwelonelontCount
  23: optional i32 welonightelondRelonplyCount
  24: optional i32 welonightelondFavCount
  25: optional i32 welonightelondQuotelonCount

  // 2 bits - 0, 1, 2, 3+
  13: optional i32 numMelonntions
  14: optional i32 numHashtags

  // 1 bytelon - 256 possiblelon languagelons
  15: optional i32 linkLanguagelon
  // 6 bits - 64 possiblelon valuelons
  16: optional i32 prelonvUselonrTwelonelontelonngagelonmelonnt

  17: optional felonaturelons.ThriftSelonarchRelonsultFelonaturelons felonaturelons

  // If thelon ThriftSelonarchQuelonry.likelondByUselonrIdFiltelonr64 and ThriftSelonarchRelonlelonvancelonOptions.collelonctFielonldHitAttributions
  // fielonlds arelon selont, thelonn this fielonld will contain thelon list of all uselonrs in thelon quelonry that likelond this twelonelont.
  // Othelonrwiselon, this fielonld is not selont.
  27: optional list<i64> likelondByUselonrIds


  // Delonpreloncatelond. Selonelon SelonARCHQUAL-10321
  21: optional doublelon dopaminelonNonPelonrsonalizelondScorelon

  32: optional list<ThriftSelonarchRelonsultNamelondelonntity> namelondelonntitielons
  33: optional list<twelonelont_annotation.TwelonelontelonntityAnnotation> elonntityAnnotations

  // Helonalth modelonl scorelons from HML
  34: optional doublelon toxicityScorelon // (go/toxicity)
  35: optional doublelon pBlockScorelon // (go/pblock)
  36: optional doublelon elonxpelonrimelonntalHelonalthModelonlScorelon1
  37: optional doublelon elonxpelonrimelonntalHelonalthModelonlScorelon2
  38: optional doublelon elonxpelonrimelonntalHelonalthModelonlScorelon3
  39: optional doublelon elonxpelonrimelonntalHelonalthModelonlScorelon4

  40: optional i64 direlonctelondAtUselonrId

  // Helonalth modelonl scorelons from HML (cont.)
  41: optional doublelon pSpammyTwelonelontScorelon // (go/pspammytwelonelont)
  42: optional doublelon pRelonportelondTwelonelontScorelon // (go/prelonportelondtwelonelont)
  43: optional doublelon spammyTwelonelontContelonntScorelon // (go/spammy-twelonelont-contelonnt)
  // it is populatelond by looking up uselonr tablelon and it is only availablelon in archivelon elonarlybirds relonsponselon
  44: optional bool isUselonrProtelonctelond
  45: optional list<ThriftSelonarchRelonsultAudioSpacelon> spacelons

  46: optional i64 elonxclusivelonConvelonrsationAuthorId
  47: optional string cardUri
  48: optional bool fromBluelonVelonrifielondAccount(pelonrsonalDataTypelon = 'UselonrVelonrifielondFlag')
}(pelonrsistelond='truelon')

// Somelon basic melontadata about a selonarch relonsult.  Uselonful for relon-sorting, filtelonring, elontc.
//
// NOTelon: DO NOT ADD NelonW FIelonLD!!
// Stop adding nelonw fielonlds to this struct, all nelonw fielonlds should go to
// ThriftSelonarchRelonsultelonxtraMelontadata (VM-1897), or thelonrelon will belon pelonrformancelon issuelons in production.
struct ThriftSelonarchRelonsultMelontadata {
  // Nelonxt availablelon fielonld ID: 86

  // -------- BASIC SCORING MelonTADATA --------

  // Whelonn relonsultTypelon is RelonCelonNCY most scoring melontadata will not belon availablelon.
  1: relonquirelond ThriftSelonarchRelonsultTypelon relonsultTypelon

  // Relonlelonvancelon scorelon computelond for this relonsult.
  3: optional doublelon scorelon

  // Truelon if thelon relonsult was skippelond by thelon scoring function.  Only selont whelonn thelon collelonct-all
  // relonsults collelonctor was uselond - in othelonr caselons skippelond relonsults arelon not relonturnelond.
  // Thelon scorelon will belon ScoringFunction.SKIP_HIT whelonn skippelond is truelon.
  43: optional bool skippelond

  // optionally a Lucelonnelon-stylelon elonxplanation for this relonsult
  5: optional string elonxplanation


  // -------- NelonTWORK-BASelonD SCORING MelonTADATA --------

  // Found thelon twelonelont in thelon trustelond circlelon.
  6: optional bool isTrustelond

  // Found thelon twelonelont in thelon direlonct follows.
  8: optional bool isFollow

  // Truelon if thelon fromUselonrId of this twelonelont was whitelonlistelond by thelon dup / antigaming filtelonr.
  // This typically indicatelons thelon relonsult was from a twelonelont that matchelond a fromUselonrId quelonry.
  9: optional bool dontFiltelonrUselonr


  // -------- COMMON DOCUMelonNT MelonTADATA --------

  // Uselonr ID of thelon author.  Whelonn isRelontwelonelont is truelon, this is thelon uselonr ID of thelon relontwelonelontelonr
  // and NOT that of thelon original twelonelont.
  7: optional i64 fromUselonrId = 0

  // Whelonn isRelontwelonelont (or packelond felonaturelons elonquivalelonnt) is truelon, this is thelon status id of thelon
  // original twelonelont. Whelonn isRelonply and gelontRelonplySourcelon arelon truelon, this is thelon status id of thelon
  // original twelonelont. In all othelonr circumstancelons this is 0.
  40: optional i64 sharelondStatusId = 0

  // Whelonn hasCard (or packelond felonaturelons elonquivalelonnt) is truelon, this is onelon of SelonarchCardTypelon.
  49: optional i8 cardTypelon = 0

  // -------- elonXTelonNDelonD DOCUMelonNT MelonTADATA --------
  // This is additional melontadata from facelont fielonlds and column stridelon fielonlds.
  // Relonturn of thelonselon fielonlds is controllelond by ThriftSelonarchRelonsultMelontadataOptions to
  // allow for finelon-grainelond control ovelonr whelonn thelonselon fielonlds arelon relonturnelond, as an
  // optimization for selonarchelons relonturning a largelon quantity of relonsults.

  // Lucelonnelon componelonnt of thelon relonlelonvancelon scorelon.  Only relonturnelond whelonn
  // ThriftSelonarchRelonsultMelontadataOptions.gelontLucelonnelonScorelon is truelon.
  31: optional doublelon lucelonnelonScorelon = 0.0

  // Urls found in thelon twelonelont.  Only relonturnelond whelonn
  // ThriftSelonarchRelonsultMelontadataOptions.gelontTwelonelontUrls is truelon.
  18: optional list<ThriftSelonarchRelonsultUrl> twelonelontUrls

  // Delonpreloncatelond in SelonARCH-8616.
  36: optional list<i32> delonpreloncatelond_topicIDs

  // Facelonts availablelon in this twelonelont, this will only belon fillelond if
  // ThriftSelonarchQuelonry.facelontFielonldNamelons is selont in thelon relonquelonst.
  22: optional list<ThriftFacelontLabelonl> facelontLabelonls

  // Thelon location of thelon relonsult, and thelon distancelon to it from thelon celonntelonr of thelon quelonry
  // location.  Only relonturnelond whelonn ThriftSelonarchRelonsultMelontadataOptions.gelontRelonsultLocation is truelon.
  35: optional ThriftSelonarchRelonsultGelonoLocation relonsultLocation

  // Pelonr fielonld hit attribution.
  55: optional hit_attribution.FielonldHitAttribution fielonldHitAttribution

  // whelonthelonr this has gelonolocation_typelon:gelonotag hit
  57: optional bool gelonotagHit = 0

  // thelon uselonr id of thelon author of thelon sourcelon/relonfelonrelonncelond twelonelont (thelon twelonelont onelon relonplielond
  // to, relontwelonelontelond and possibly quotelond, elontc.) (SelonARCH-8561)
  // Only relonturnelond whelonn ThriftSelonarchRelonsultMelontadataOptions.gelontRelonfelonrelonncelondTwelonelontAuthorId is truelon.
  60: optional i64 relonfelonrelonncelondTwelonelontAuthorId = 0

  // Whelonthelonr this twelonelont has celonrtain typelons of melondia.
  // Only relonturnelond whelonn ThriftSelonarchRelonsultMelontadataOptions.gelontMelondiaBits is truelon.
  // "Nativelon videlono" is elonithelonr consumelonr, pro, vinelon, or pelonriscopelon.
  // "Nativelon imagelon" is an imagelon hostelond on pic.twittelonr.com.
  62: optional bool hasConsumelonrVidelono
  63: optional bool hasProVidelono
  64: optional bool hasVinelon
  65: optional bool hasPelonriscopelon
  66: optional bool hasNativelonVidelono
  67: optional bool hasNativelonImagelon

  // Packelond felonaturelons for this relonsult. This fielonld is nelonvelonr populatelond.
  50: optional status.PackelondFelonaturelons delonpreloncatelond_packelondFelonaturelons

  // Thelon felonaturelons storelond in elonarlybird

  // From intelongelonr 0 from elonarlybirdFelonaturelonConfiguration:
  16: optional bool isRelontwelonelont
  71: optional bool isSelonlfTwelonelont
  10: optional bool isOffelonnsivelon
  11: optional bool hasLink
  12: optional bool hasTrelonnd
  13: optional bool isRelonply
  14: optional bool hasMultiplelonHashtagsOrTrelonnds
  23: optional bool fromVelonrifielondAccount
  // Static telonxt quality scorelon.  This is actually an int belontwelonelonn 0 and 100.
  30: optional doublelon telonxtScorelon
  51: optional selonarch_languagelon.ThriftLanguagelon languagelon

  // From intelongelonr 1 from elonarlybirdFelonaturelonConfiguration:
  52: optional bool hasImagelon
  53: optional bool hasVidelono
  28: optional bool hasNelonws
  48: optional bool hasCard
  61: optional bool hasVisiblelonLink
  // Twelonelonp crelond aka uselonr relonp.  This is actually an int belontwelonelonn 0 and 100.
  32: optional doublelon uselonrRelonp
  24: optional bool isUselonrSpam
  25: optional bool isUselonrNSFW
  26: optional bool isUselonrBot
  54: optional bool isUselonrAntiSocial

  // From intelongelonr 2 from elonarlybirdFelonaturelonConfiguration:

  // Relontwelonelont, fav, relonply, elonmbelonds counts, and videlono vielonw counts arelon APPROXIMATelon ONLY.
  // Notelon that relontwelonelontCount, favCount and relonplyCount arelon not original unnormalizelond valuelons,
  // but aftelonr a log2() function for historical relonason, this loselons us somelon granularity.
  // For morelon accuratelon counts, uselon {relontwelonelont, fav, relonply}CountV2 in elonxtraMelontadata.
  2: optional i32 relontwelonelontCount
  33: optional i32 favCount
  34: optional i32 relonplyCount
  58: optional i32 elonmbelondsImprelonssionCount
  59: optional i32 elonmbelondsUrlCount
  68: optional i32 videlonoVielonwCount

  // Parus scorelon.  This is actually an int belontwelonelonn 0 and 100.
  29: optional doublelon parusScorelon

  // elonxtra felonaturelon data, all nelonw felonaturelon fielonlds you want to relonturn from elonarlybird should go into
  // this onelon, thelon outelonr onelon is always relonaching its limit of thelon nubmelonr of fielonlds JVM can
  // comfortably support!!
  86: optional ThriftSelonarchRelonsultelonxtraMelontadata elonxtraMelontadata

  // Intelongelonr 3 is omittelond, selonelon elonxpFelonaturelonValuelons abovelon for morelon delontails.

  // From intelongelonr 4 from elonarlybirdFelonaturelonConfiguration:
  // Signaturelon, for duplicatelon delontelonction and relonmoval.
  4: optional i32 signaturelon

  // -------- THINGS USelonD ONLY BY THelon BLelonNDelonR --------

  // Social proof of thelon twelonelont, for nelontwork discovelonry.
  // Do not uselon thelonselon fielonlds outsidelon of nelontwork discovelonry.
  41: optional list<i64> relontwelonelontelondUselonrIDs64
  42: optional list<i64> relonplyUselonrIDs64

  // Social connelonction belontwelonelonn thelon selonarch uselonr and this relonsult.
  19: optional social.ThriftSocialContelonxt socialContelonxt

  // uselond by RelonlelonvancelonTimelonlinelonSelonarchWorkflow, whelonthelonr a twelonelont should belon highlightelond or not
  46: optional bool highlightRelonsult

  // uselond by RelonlelonvancelonTimelonlinelonSelonarchWorkflow, thelon highlight contelonxt of thelon highlightelond twelonelont
  47: optional highlight.ThriftHighlightContelonxt highlightContelonxt

  // thelon pelonnguin velonrsion uselond to tokelonnizelon thelon twelonelonts by thelon selonrving elonarlybird indelonx as delonfinelond
  // in com.twittelonr.common.telonxt.velonrsion.PelonnguinVelonrsion
  56: optional i8 pelonnguinVelonrsion

  69: optional bool isNullcast

  // This is thelon normalizelond ratio(0.00 to 1.00) of nth tokelonn(starting belonforelon 140) dividelond by
  // numTokelonns and thelonn normalizelond into 16 positions(4 bits) but on a scalelon of 0 to 100% as
  // welon unnormalizelon it for you
  70: optional doublelon tokelonnAt140DividelondByNumTokelonnsBuckelont

}(pelonrsistelond='truelon')

// Quelonry lelonvelonl relonsult stats.
// Nelonxt id: 20
struct ThriftSelonarchRelonsultsRelonlelonvancelonStats {
  1: optional i32 numScorelond = 0
  // Skippelond documelonnts count, thelony welonrelon also scorelond but thelonir scorelons got ignorelond (skippelond), notelon that this is diffelonrelonnt
  // from numRelonsultsSkippelond in thelon ThriftSelonarchRelonsults.
  2: optional i32 numSkippelond = 0
  3: optional i32 numSkippelondForAntiGaming = 0
  4: optional i32 numSkippelondForLowRelonputation = 0
  5: optional i32 numSkippelondForLowTelonxtScorelon = 0
  6: optional i32 numSkippelondForSocialFiltelonr = 0
  7: optional i32 numSkippelondForLowFinalScorelon = 0
  8: optional i32 oldelonstScorelondTwelonelontAgelonInSelonconds = 0

  // Morelon countelonrs for various felonaturelons.
  9:  optional i32 numFromDirelonctFollows = 0
  10: optional i32 numFromTrustelondCirclelon = 0
  11: optional i32 numRelonplielons = 0
  12: optional i32 numRelonplielonsTrustelond = 0
  13: optional i32 numRelonplielonsOutOfNelontwork = 0
  14: optional i32 numSelonlfTwelonelonts = 0
  15: optional i32 numWithMelondia = 0
  16: optional i32 numWithNelonws = 0
  17: optional i32 numSpamUselonr = 0
  18: optional i32 numOffelonnsivelon = 0
  19: optional i32 numBot = 0
}(pelonrsistelond='truelon')

// Pelonr relonsult delonbug info.
struct ThriftSelonarchRelonsultDelonbugInfo {
  1: optional string hostnamelon
  2: optional string clustelonrNamelon
  3: optional i32 partitionId
  4: optional string tielonrnamelon
}(pelonrsistelond='truelon')

struct ThriftSelonarchRelonsult {
  // Nelonxt availablelon fielonld ID: 22

  // Relonsult status id.
  1: relonquirelond i64 id

  // TwelonelontyPielon status of thelon selonarch relonsult
  7: optional delonpreloncatelond.Status twelonelontypielonStatus
  19: optional twelonelont.Twelonelont twelonelontypielonTwelonelont  // v2 struct

  // If thelon selonarch relonsult is a relontwelonelont, this fielonld contains thelon sourcelon TwelonelontyPielon status.
  10: optional delonpreloncatelond.Status sourcelonTwelonelontypielonStatus
  20: optional twelonelont.Twelonelont sourcelonTwelonelontypielonTwelonelont  // v2 struct

  // If thelon selonarch relonsult is a quotelon twelonelont, this fielonld contains thelon quotelond TwelonelontyPielon status.
  17: optional delonpreloncatelond.Status quotelondTwelonelontypielonStatus
  21: optional twelonelont.Twelonelont quotelondTwelonelontypielonTwelonelont  // v2 struct

  // Additional melontadata about a selonarch relonsult.
  5: optional ThriftSelonarchRelonsultMelontadata melontadata

  // Hit highlights for various parts of this twelonelont
  // for twelonelont telonxt
  6: optional list<hits.ThriftHits> hitHighlights
  // for thelon titlelon and delonscription in thelon card elonxpando.
  12: optional list<hits.ThriftHits> cardTitlelonHitHighlights
  13: optional list<hits.ThriftHits> cardDelonscriptionHitHighlights

  // elonxpansion typelons, if elonxpandRelonsult == Falselon, thelon elonxpasions selont should belon ignorelond.
  8: optional bool elonxpandRelonsult = 0
  9: optional selont<elonxpansions.ThriftTwelonelontelonxpansionTypelon> elonxpansions

  // Only selont if this is a promotelond twelonelont
  11: optional adselonrvelonr_common.AdImprelonssion adImprelonssion

  // whelonrelon this twelonelont is from
  // Sincelon ThriftSelonarchRelonsult uselond not only as an elonarlybird relonsponselon, but also an intelonrnal
  // data transfelonr objelonct of Blelonndelonr, thelon valuelon of this fielonld is mutablelon in Blelonndelonr, not
  // neloncelonssarily relonfleloncting elonarlybird relonsponselon.
  14: optional ThriftTwelonelontSourcelon twelonelontSourcelon

  // thelon felonaturelons of a twelonelont uselond for relonlelonvancelon timelonlinelon
  // this fielonld is populatelond by blelonndelonr in RelonlelonvancelonTimelonlinelonSelonarchWorkflow
  15: optional felonaturelons.ThriftTwelonelontFelonaturelons twelonelontFelonaturelons

  // thelon convelonrsation contelonxt of a twelonelont
  16: optional convelonrsation.ThriftConvelonrsationContelonxt convelonrsationContelonxt

  // pelonr-relonsult delonbugging info that's pelonrsistelond across melonrgelons.
  18: optional ThriftSelonarchRelonsultDelonbugInfo delonbugInfo
}(pelonrsistelond='truelon')

elonnum ThriftFacelontRankingModelon {
  COUNT = 0,
  FILTelonR_WITH_TelonRM_STATISTICS = 1,
}

struct ThriftFacelontFielonldRelonquelonst {
  // nelonxt availablelon fielonld ID: 4
  1: relonquirelond string fielonldNamelon
  2: optional i32 numRelonsults = 5

  // uselon facelontRankingOptions in ThriftFacelontRelonquelonst instelonad
  3: optional ThriftFacelontRankingModelon rankingModelon = ThriftFacelontRankingModelon.COUNT
}(pelonrsistelond='truelon')

struct ThriftFacelontRelonquelonst {
  // Nelonxt availablelon fielonld ID: 7
  1: optional list<ThriftFacelontFielonldRelonquelonst> facelontFielonlds
  5: optional ranking.ThriftFacelontRankingOptions facelontRankingOptions
  6: optional bool usingQuelonryCachelon = 0
}(pelonrsistelond='truelon')

struct ThriftTelonrmRelonquelonst {
  1: optional string fielonldNamelon = "telonxt"
  2: relonquirelond string telonrm
}(pelonrsistelond='truelon')

elonnum ThriftHistogramGranularityTypelon {
  MINUTelonS = 0
  HOURS = 1,
  DAYS = 2,
  CUSTOM = 3,

  PLACelon_HOLDelonR4 = 4,
  PLACelon_HOLDelonR5 = 5,
}

struct ThriftHistogramSelonttings {
  1: relonquirelond ThriftHistogramGranularityTypelon granularity
  2: optional i32 numBins = 60
  3: optional i32 samplingRatelon = 1
  4: optional i32 binSizelonInSelonconds   // thelon bin sizelon, only uselond if granularity is selont to CUSTOM.
}(pelonrsistelond='truelon')

// nelonxt id is 4
struct ThriftTelonrmStatisticsRelonquelonst {
  1: optional list<ThriftTelonrmRelonquelonst> telonrmRelonquelonsts
  2: optional ThriftHistogramSelonttings histogramSelonttings
  // If this is selont to truelon, elonvelonn if thelonrelon is no telonrmRelonquelonsts abovelon, so long as thelon histogramSelonttings
  // is selont, elonarlybird will relonturn a null->ThriftTelonrmRelonsults elonntry in thelon telonrmRelonsults map, containing
  // thelon global twelonelont count histogram for currelonnt quelonry, which is thelon numbelonr of twelonelonts matching this
  // quelonry in diffelonrelonnt minutelons/hours/days.
  3: optional bool includelonGlobalCounts = 0
  // Whelonn this is selont, thelon background facelonts call doelons anothelonr selonarch in ordelonr to find thelon belonst
  // relonprelonselonntativelon twelonelont for a givelonn telonrm relonquelonst, thelon relonprelonselonntativelon twelonelont is storelond in thelon
  // melontadata of thelon telonrmstats relonsult
  4: optional bool scorelonTwelonelontsForRelonprelonselonntativelons = 0
}(pelonrsistelond='truelon')

// Nelonxt id is 12
struct ThriftFacelontCountMelontadata {
  // this is thelon id of thelon first twelonelont in thelon indelonx that containelond this facelont
  1: optional i64 statusId = -1

  // whelonthelonr thelon twelonelont with thelon abovelon statusId is NSFW, from an antisocial uselonr,
  // markelond as selonnsitivelon contelonnt, elontc.
  10: optional bool statusPossiblySelonnsitivelon

  // thelon id of thelon uselonr who selonnt thelon twelonelont abovelon - only relonturnelond if
  // statusId is relonturnelond too
  // NOTelon: for nativelon photos welon may not belon ablelon to delontelonrminelon thelon uselonr,
  // elonvelonn though thelon statusId can belon relonturnelond. This is beloncauselon thelon statusId
  // can belon delontelonrminelond from thelon url, but thelon uselonr can't and thelon twelonelont may
  // not belon in thelon indelonx anymorelon. In this caselon statusId would belon selont but
  // twittelonrUselonrId would not.
  2: optional i64 twittelonrUselonrId = -1

  // thelon languagelon of thelon twelonelont abovelon.
  8: optional selonarch_languagelon.ThriftLanguagelon statusLanguagelon

  // optionally whitelonlist thelon fromUselonrId from dup/twittelonrUselonrId filtelonring
  3: optional bool dontFiltelonrUselonr = 0;

  // if this facelont is a nativelon photo welon relonturn for convelonnielonncelon thelon
  // twimg url
  4: optional string nativelonPhotoUrl

  // optionally relonturns somelon delonbug information about this facelont
  5: optional string elonxplanation

  // thelon crelonatelond_at valuelon for thelon twelonelont from statusId - only relonturnelond
  // if statusId is relonturnelond too
  6: optional i64 crelonatelond_at

  // thelon maximum twelonelonpcrelond of thelon hits that containelond this facelont
  7: optional i32 maxTwelonelonpCrelond

  // Whelonthelonr this facelont relonsult is forcelon inselonrtelond, instelonad of organically relonturnelond from selonarch.
  // This fielonld is only uselond in Blelonndelonr to mark thelon forcelon-inselonrtelond facelont relonsults
  // (from reloncelonnt twelonelonts, elontc).
  11: optional bool forcelonInselonrtelond = 0
}(pelonrsistelond='truelon')

struct ThriftTelonrmRelonsults {
  1: relonquirelond i32 totalCount
  2: optional list<i32> histogramBins
  3: optional ThriftFacelontCountMelontadata melontadata
}(pelonrsistelond='truelon')

struct ThriftTelonrmStatisticsRelonsults {
  1: relonquirelond map<ThriftTelonrmRelonquelonst,ThriftTelonrmRelonsults> telonrmRelonsults
  2: optional ThriftHistogramSelonttings histogramSelonttings
  // If histogramSelonttings arelon selont, this will havelon a list of ThriftHistogramSelonttings.numBins binIds,
  // that thelon correlonsponding histogramBins in ThriftTelonrmRelonsults will havelon counts for.
  // Thelon binIds will correlonspond to thelon timelons of thelon hits matching thelon driving selonarch quelonry for this
  // telonrm statistics relonquelonst.
  // If thelonrelon welonrelon no hits matching thelon selonarch quelonry, numBins binIds will belon relonturnelond, but thelon
  // valuelons of thelon binIds will not melonaninfully correlonspond to anything relonlatelond to thelon quelonry, and
  // should not belon uselond. Such caselons can belon idelonntifielond by ThriftSelonarchRelonsults.numHitsProcelonsselond beloning
  // selont to 0 in thelon relonsponselon, and thelon relonsponselon not beloning elonarly telonrminatelond.
  3: optional list<i32> binIds
  // If selont, this id indicatelons thelon id of thelon minimum (oldelonst) bin that has belonelonn complelontelonly selonarchelond,
  // elonvelonn if thelon quelonry was elonarly telonrminatelond. If not selont no bin was selonarchelond fully, or no histogram
  // was relonquelonstelond.
  // Notelon that if elon.g. a quelonry only matchelons a bin partially (duelon to elon.g. a sincelon opelonrator) thelon bin
  // is still considelonrelond fully selonarchelond if thelon quelonry did not elonarly telonrminatelon.
  4: optional i32 minComplelontelonBinId
}(pelonrsistelond='truelon')

struct ThriftFacelontCount {
  // thelon telonxt of thelon facelont
  1: relonquirelond string facelontLabelonl

  // delonpreloncatelond; currelonntly matchelons welonightelondCount for backwards-compatibility relonasons
  2: optional i32 facelontCount

  // thelon simplelon count of twelonelonts that containelond this facelont, without any
  // welonighting applielond
  7: optional i32 simplelonCount

  // a welonightelond velonrsion of thelon count, using signals likelon twelonelonpcrelond, parus, elontc.
  8: optional i32 welonightelondCount

  // thelon numbelonr of timelons this facelont occurrelond in twelonelonts matching thelon background quelonry
  // using thelon telonrm statistics API - only selont if FILTelonR_WITH_TelonRM_STATISTICS was uselond
  3: optional i32 backgroundCount

  // thelon relonlelonvancelon scorelon that was computelond for this facelont if FILTelonR_WITH_TelonRM_STATISTICS
  // was uselond
  4: optional doublelon scorelon

  // a countelonr for how oftelonn this facelont was pelonnalizelond
  5: optional i32 pelonnaltyCount

  6: optional ThriftFacelontCountMelontadata melontadata
}(pelonrsistelond='truelon')

// List of facelont labelonls and counts for a givelonn facelont fielonld, thelon
// total count for this fielonld, and a quality scorelon for this fielonld
struct ThriftFacelontFielonldRelonsults {
  1: relonquirelond list<ThriftFacelontCount> topFacelonts
  2: relonquirelond i32 totalCount
  3: optional doublelon scorelonQuality
  4: optional i32 totalScorelon
  5: optional i32 totalPelonnalty

  // Thelon ratio of thelon twelonelont languagelon in thelon twelonelonts with this facelont fielonld, a map from thelon languagelon
  // namelon to a numbelonr belontwelonelonn (0.0, 1.0]. Only languagelons with ratio highelonr than 0.1 will belon includelond.
  6: optional map<selonarch_languagelon.ThriftLanguagelon, doublelon> languagelonHistogram
}

struct ThriftFacelontRelonsults {
  1: relonquirelond map<string, ThriftFacelontFielonldRelonsults> facelontFielonlds
  2: optional i32 backgroundNumHits
  // relonturns optionally a list of uselonr ids that should not gelont filtelonrelond
  // out by things likelon antigaming filtelonrs, beloncauselon thelonselon uselonrs welonrelon elonxplicitly
  // quelonrielond for
  // Notelon that ThriftFacelontCountMelontadata relonturns alrelonady dontFiltelonrUselonr
  // for facelont relonquelonsts in which caselon this list is not nelonelondelond. Howelonvelonr, it
  // is nelonelondelond for subselonquelonnt telonrm statistics quelonrielons, welonrelon uselonr id lookups
  // arelon pelonrformelond, but a diffelonrelonnt background quelonry is uselond.
  3: optional selont<i64> uselonrIDWhitelonlist
}

struct ThriftSelonarchRelonsults {
  // Nelonxt availablelon fielonld ID: 23
  1: relonquirelond list<ThriftSelonarchRelonsult> relonsults = []

  // (SelonARCH-11950): Now relonsultOffselont is delonpreloncatelond, so thelonrelon is no uselon in numRelonsultsSkippelond too.
  9: optional i32 delonpreloncatelond_numRelonsultsSkippelond

  // Numbelonr of docs that matchelond thelon quelonry and welonrelon procelonsselond.
  7: optional i32 numHitsProcelonsselond

  // Rangelon of status IDs selonarchelond, from max ID to min ID (both inclusivelon).
  // Thelonselon may belon unselont in caselon that thelon selonarch quelonry containelond ID or timelon
  // opelonrators that welonrelon complelontelonly out of rangelon for thelon givelonn indelonx.
  10: optional i64 maxSelonarchelondStatusID
  11: optional i64 minSelonarchelondStatusID

  // Timelon rangelon that was selonarchelond (both inclusivelon).
  19: optional i32 maxSelonarchelondTimelonSincelonelonpoch
  20: optional i32 minSelonarchelondTimelonSincelonelonpoch

  12: optional ThriftSelonarchRelonsultsRelonlelonvancelonStats relonlelonvancelonStats

  // Ovelonrall quality of this selonarch relonsult selont
  13: optional doublelon scorelon = -1.0
  18: optional doublelon nsfwRatio = 0.0

  // Thelon count of hit documelonnts in elonach languagelon.
  14: optional map<selonarch_languagelon.ThriftLanguagelon, i32> languagelonHistogram

  // Hit counts pelonr timelon pelonriod:
  // Thelon kelony is a timelon cutoff in milliselonconds (elon.g. 60000 mseloncs ago).
  // Thelon valuelon is thelon numbelonr of hits that arelon morelon reloncelonnt than thelon cutoff.
  15: optional map<i64, i32> hitCounts

  // thelon total cost for this quelonry
  16: optional doublelon quelonryCost

  // Selont to non-0 if this quelonry was telonrminatelond elonarly (elonithelonr duelon to a timelonout, or elonxcelonelondelond quelonry cost)
  // Whelonn gelontting this relonsponselon from a singlelon elonarlybird, this will belon selont to 1, if thelon quelonry
  // telonrminatelond elonarly.
  // Whelonn gelontting this relonsponselon from a selonarch root, this should belon selont to thelon numbelonr of individual
  // elonarlybird relonquelonsts that welonrelon telonrminatelond elonarly.
  17: optional i32 numPartitionselonarlyTelonrminatelond

  // If ThriftSelonarchRelonsults relonturns felonaturelons in felonaturelons.ThriftSelonarchRelonsultFelonaturelon format, this
  // fielonld would delonfinelon thelon schelonma of thelon felonaturelons.
  // If thelon elonarlybird schelonma is alrelonady in thelon clielonnt cachelond schelonmas indicatelond in thelon relonquelonst, thelonn
  // selonarchFelonaturelonSchelonma would only havelon (velonrsion, cheloncksum) information.
  //
  // Noticelon that elonarlybird root only selonnds onelon schelonma back to thelon supelonrroot elonvelonn though elonarlybird
  // root might reloncelonivelon multiplelon velonrsion of schelonmas.
  //
  // elonarlybird roots' schelonma melonrgelon/chooselon logic whelonn relonturning relonsults to supelonrroot:
  // . pick thelon most occurrelond velonrsionelond schelonma and relonturn thelon schelonma to thelon supelonrroot
  // . if thelon supelonrroot alrelonady cachelons thelon schelonma, only selonnd thelon velonrsion information back
  //
  // Supelonrroots' schelonma melonrgelon/chooselon logic whelonn relonturning relonsults to clielonnts:
  // . pick thelon schelonma baselond on thelon ordelonr of: relonaltimelon > protelonctelond > archivelon
  // . beloncauselon of thelon abovelon ordelonring, it is possiblelon that archivelon elonarlybird schelonma with a nelonw flush
  //   velonrion (with nelonw bit felonaturelons) might belon lost to oldelonr relonaltimelon elonarlybird schelonma; this is
  //   considelonrelond to to belon rarelon and accelontablelon beloncauselon onelon relonaltimelon elonarlybird delonploy would fix it
  21: optional felonaturelons.ThriftSelonarchFelonaturelonSchelonma felonaturelonSchelonma

  // How long it took to scorelon thelon relonsults in elonarlybird (in nanoselonconds). Thelon numbelonr of relonsults
  // that welonrelon scorelond should belon selont in numHitsProcelonsselond.
  // elonxpelonctelond to only belon selont for relonquelonsts that actually do scoring (i.elon. Relonlelonvancelon and TopTwelonelonts).
  22: optional i64 scoringTimelonNanos

  8: optional i32 delonpreloncatelond_numDocsProcelonsselond
}

// Notelon: elonarlybird no longelonr relonspeloncts this fielonld, as it doelons not contain statuselons.
// Blelonndelonr should relonspelonct it.
elonnum elonarlybirdRelonturnStatusTypelon {
  NO_STATUS = 0
  // delonpreloncatelond
  DelonPRelonCATelonD_BASIC_STATUS = 1,
  // delonpreloncatelond
  DelonPRelonCATelonD_SelonARCH_STATUS = 2,
  TWelonelonTYPIelon_STATUS = 3,

  PLACelon_HOLDelonR4 = 4,
  PLACelon_HOLDelonR5 = 5,
}

struct AdjustelondRelonquelonstParams {
  // Nelonxt availablelon fielonld ID: 4

  // Adjustelond valuelon for elonarlybirdRelonquelonst.selonarchQuelonry.numRelonsults.
  1: optional i32 numRelonsults

  // Adjustelond valuelon for elonarlybirdRelonquelonst.selonarchQuelonry.maxHitsToProcelonss and
  // elonarlybirdRelonquelonst.selonarchQuelonry.relonlelonvancelonOptions.maxHitsToProcelonss.
  2: optional i32 maxHitsToProcelonss

  // Adjustelond valuelon for elonarlybirdRelonquelonst.selonarchQuelonry.relonlelonvancelonOptions.relonturnAllRelonsults
  3: optional bool relonturnAllRelonsults
}

struct elonarlybirdRelonquelonst {
  // Nelonxt availablelon fielonld ID: 36

  // -------- COMMON RelonQUelonST OPTIONS --------
  // Thelonselon fielonlds contain options relonspelonctelond by all kinds of elonarlybird relonquelonsts.

  // Selonarch quelonry containing gelonnelonral elonarlybird relontrielonval and hit collelonction options.
  // Also contains thelon options speloncific to selonarch relonquelonsts.
  1: relonquirelond ThriftSelonarchQuelonry selonarchQuelonry

  // Common RPC information - clielonnt hostnamelon and relonquelonst ID.
  12: optional string clielonntHost
  13: optional string clielonntRelonquelonstID

  // A string idelonntifying thelon clielonnt that initiatelond thelon relonquelonst.
  // elonx: macaw-selonarch.prod, welonbforall.prod, welonbforall.staging.
  // Thelon intelonntion is to track thelon load welon gelont from elonach clielonnt, and elonvelonntually elonnforcelon
  // pelonr-clielonnt QPS quotas, but this fielonld could also belon uselond to allow accelonss to celonrtain felonaturelons
  // only to celonrtain clielonnts, elontc.
  21: optional string clielonntId

  // Thelon timelon (in millis sincelon elonpoch) whelonn thelon elonarlybird clielonnt issuelond this relonquelonst.
  // Can belon uselond to elonstimatelon relonquelonst timelonout timelon, capturing in-transit timelon for thelon relonquelonst.
  23: optional i64 clielonntRelonquelonstTimelonMs

  // Caching paramelontelonrs uselond by elonarlybird roots.
  24: optional caching.CachingParams cachingParams

  // Delonpreloncatelond. Selonelon SelonARCH-2784
  // elonarlybird relonquelonsts will belon elonarly telonrminatelond in a belonst-elonffort way to prelonvelonnt thelonm from
  // elonxcelonelonding thelon givelonn timelonout.  If timelonout is <= 0 this elonarly telonrmination critelonria is
  // disablelond.
  17: optional i32 timelonoutMs = -1

  // Delonpreloncatelond. Selonelon SelonARCH-2784
  // elonarlybird relonquelonsts will belon elonarly telonrminatelond in a belonst-elonffort way to prelonvelonnt thelonm from
  // elonxcelonelonding thelon givelonn quelonry cost.  If maxQuelonryCost <= 0 this elonarly telonrmination critelonria
  // is disablelond.
  20: optional doublelon maxQuelonryCost = -1


  // -------- RelonQUelonST-TYPelon SPelonCIFIC OPTIONS --------
  // Thelonselon fielonlds contain options for onelon speloncific kind of relonquelonst.  If onelon of thelonselon options
  // is selont thelon relonquelonst will belon considelonrelond to belon thelon appropriatelon typelon of relonquelonst.

  // Options for facelont counting relonquelonsts.
  11: optional ThriftFacelontRelonquelonst facelontRelonquelonst

  // Options for telonrm statistics relonquelonsts.
  14: optional ThriftTelonrmStatisticsRelonquelonst telonrmStatisticsRelonquelonst


  // -------- DelonBUG OPTIONS --------
  // Uselond for delonbugging only.

  // Delonbug modelon, 0 for no delonbug information.
  15: optional i8 delonbugModelon = 0

  // Can belon uselond to pass elonxtra delonbug argumelonnts to elonarlybird.
  34: optional elonarlybirdDelonbugOptions delonbugOptions

  // Selonarchelons a speloncific selongmelonnt by timelon slicelon id if selont and selongmelonnt id is > 0.
  22: optional i64 selonarchSelongmelonntId

  // -------- THINGS USelonD ONLY BY THelon BLelonNDelonR --------
  // Thelonselon fielonlds arelon uselond by thelon blelonndelonr and clielonnts of thelon blelonndelonr, but not by elonarlybird.

  // Speloncifielons what kind of status objelonct to relonturn, if any.
  7: optional elonarlybirdRelonturnStatusTypelon relonturnStatusTypelon


  // -------- THINGS USelonD BY THelon ROOTS --------
  // Thelonselon fielonlds arelon not in uselon by elonarlybirds thelonmselonlvelons, but arelon in uselon by elonarlybird roots
  // (and thelonir clielonnts).
  // Thelonselon fielonlds livelon helonrelon sincelon welon currelonntly relonuselon thelon samelon thrift relonquelonst and relonsponselon structs
  // for both elonarlybirds and elonarlybird roots, and could potelonntially belon movelond out if welon welonrelon to
  // introducelon selonparatelon relonquelonst / relonsponselon structs speloncifically for thelon roots.

  // Welon havelon a threlonshold for how many hash partition relonquelonsts nelonelond to succelonelond at thelon root lelonvelonl
  // in ordelonr for thelon elonarlybird root relonquelonst to belon considelonrelond succelonssful.
  // elonach typelon or elonarlybird quelonrielons (elon.g. relonlelonvancelon, or telonrm statistics) has a prelondelonfinelond delonfault
  // threlonshold valuelon (elon.g. 90% or hash partitions nelonelond to succelonelond for a reloncelonncy quelonry).
  // Thelon clielonnt can optionally selont thelon threlonshold valuelon to belon somelonthing othelonr than thelon delonfault,
  // by selontting this fielonld to a valuelon in thelon rangelon of 0 (elonxclusivelon) to 1 (inclusivelon).
  // If this valuelon is selont outsidelon of thelon (0, 1] rangelon, a CLIelonNT_elonRROR elonarlybirdRelonsponselonCodelon will
  // belon relonturnelond.
  25: optional doublelon succelonssfulRelonsponselonThrelonshold

  // Whelonrelon doelons thelon quelonry comelon from?
  26: optional quelonry.ThriftQuelonrySourcelon quelonrySourcelon

  // Whelonthelonr to gelont archivelon relonsults This flag is advisory. A relonquelonst may still belon relonstrictelond from
  // gelontting relonqults from thelon archivelon baselond on thelon relonquelonsting clielonnt, quelonry sourcelon, relonquelonstelond
  // timelon/id rangelon, elontc.
  27: optional bool gelontOldelonrRelonsults

  // Thelon list of uselonrs followelond by thelon currelonnt uselonr.
  // Uselond to relonstrict thelon valuelons in thelon fromUselonrIDFiltelonr64 fielonld whelonn selonnding a relonquelonst
  // to thelon protelonctelonctelond clustelonr.
  28: optional list<i64> followelondUselonrIds

  // Thelon adjustelond paramelontelonrs for thelon protelonctelond relonquelonst.
  29: optional AdjustelondRelonquelonstParams adjustelondProtelonctelondRelonquelonstParams

  // Thelon adjustelond paramelontelonrs for thelon full archivelon relonquelonst.
  30: optional AdjustelondRelonquelonstParams adjustelondFullArchivelonRelonquelonstParams

  // Relonturn only thelon protelonctelond twelonelonts. This flag is uselond by thelon SupelonrRoot to relonturn relonlelonvancelon
  // relonsults that contain only protelonctelond twelonelonts.
  31: optional bool gelontProtelonctelondTwelonelontsOnly

  // Tokelonnizelon selonrializelond quelonrielons with thelon appropriatelon Pelonngin velonrsion(s).
  // Only has an elonffelonct on supelonrroot.
  32: optional bool relontokelonnizelonSelonrializelondQuelonry

  // Flag to ignorelon twelonelonts that arelon velonry reloncelonnt and could belon incomplelontelonly indelonxelond.
  // If falselon, will allow quelonrielons to selonelon relonsults that may violatelon implicit strelonaming
  // guarantelonelons and will selonarch Twelonelonts that havelon belonelonn partially indelonxelond.
  // Selonelon go/indelonxing-latelonncy for morelon delontails. Whelonn elonnablelond, prelonvelonnts seloneloning twelonelonts
  // that arelon lelonss than 15 selonconds old (or a similarly configurelond threlonshold).
  // May belon selont to falselon unlelonss elonxplicitly selont to truelon.
  33: optional bool skipVelonryReloncelonntTwelonelonts = 1

  // Selontting an elonxpelonrimelonntal clustelonr will relonroutelon traffic at thelon relonaltimelon root layelonr to an elonxpelonrimelonntal
  // elonarlybird clustelonr. This will havelon no impact if selont on relonquelonsts to anywhelonrelon othelonr than relonaltimelon root.
  35: optional elonxpelonrimelonntClustelonr elonxpelonrimelonntClustelonrToUselon

  // Caps numbelonr of relonsults relonturnelond by roots aftelonr melonrging relonsults from diffelonrelonnt elonarlybird partitions/clustelonrs.
  // If not selont, ThriftSelonarchQuelonry.numRelonsults or CollelonctorParams.numRelonsultsToRelonturn will belon uselond to cap relonsults.
  // This paramelontelonr will belon ignorelond if ThriftRelonlelonvancelonOptions.relonturnAllRelonsults is selont to truelon.
  36: optional i32 numRelonsultsToRelonturnAtRoot
}

elonnum elonarlybirdRelonsponselonCodelon {
  SUCCelonSS = 0,
  PARTITION_NOT_FOUND = 1,
  PARTITION_DISABLelonD = 2,
  TRANSIelonNT_elonRROR = 3,
  PelonRSISTelonNT_elonRROR = 4,
  CLIelonNT_elonRROR = 5,
  PARTITION_SKIPPelonD = 6,
  // Relonquelonst was quelonuelond up on thelon selonrvelonr for so long that it timelond out, and was not
  // elonxeloncutelond at all.
  SelonRVelonR_TIMelonOUT_elonRROR = 7,
  TIelonR_SKIPPelonD = 8,
  // Not elonnough partitions relonturnelond a succelonssful relonsponselon. Thelon melonrgelond relonsponselon will havelon partition
  // counts and elonarly telonrmination info selont, but will not havelon selonarch relonsults.
  TOO_MANY_PARTITIONS_FAILelonD_elonRROR = 9,
  // Clielonnt welonnt ovelonr its quota, and thelon relonquelonst was throttlelond.
  QUOTA_elonXCelonelonDelonD_elonRROR = 10,
  // Clielonnt's relonquelonst is blockelond baselond on Selonarch Infra's policy. Selonarch Infra can can block clielonnt's
  // relonquelonsts baselond on thelon quelonry sourcelon of thelon relonquelonst.
  RelonQUelonST_BLOCKelonD_elonRROR = 11,

  CLIelonNT_CANCelonL_elonRROR = 12,

  CLIelonNT_BLOCKelonD_BY_TIelonR_elonRROR = 13,

  PLACelon_HOLDelonR_2015_09_21 = 14,
}

// A reloncordelond relonquelonst and relonsponselon.
struct elonarlybirdRelonquelonstRelonsponselon {
  // Whelonrelon did welon selonnd this relonquelonst to.
  1: optional string selonntTo;
  2: optional elonarlybirdRelonquelonst relonquelonst;
  // This can't belon an elonarlybirdRelonsponselon, beloncauselon thelon thrift compilelonr for Python
  // doelonsn't allow cyclic relonfelonrelonncelons and welon havelon somelon Python utilitielons that will fail.
  3: optional string relonsponselon;
}

struct elonarlybirdDelonbugInfo {
  1: optional string host
  2: optional string parselondQuelonry
  3: optional string lucelonnelonQuelonry
  // Relonquelonsts selonnt to delonpelonndelonnt selonrvicelons. For elonxamplelon, supelonrroot selonnds to relonaltimelon root,
  // archivelon root, elontc.
  4: optional list<elonarlybirdRelonquelonstRelonsponselon> selonntRelonquelonsts;
  // selongmelonnt lelonvelonl delonbug info (elong. hitsPelonrSelongmelonnt, max/minSelonarchelondTimelon elontc.)
  5: optional list<string> collelonctorDelonbugInfo
  6: optional list<string> telonrmStatisticsDelonbugInfo
}

struct elonarlybirdDelonbugOptions {
  1: optional bool includelonCollelonctorDelonbugInfo
}

struct TielonrRelonsponselon {
  1: optional elonarlybirdRelonsponselonCodelon tielonrRelonsponselonCodelon
  2: optional i32 numPartitions
  3: optional i32 numSuccelonssfulPartitions
}

struct elonarlybirdSelonrvelonrStats {
  // Thelon hostnamelon of thelon elonarlybird that procelonsselond this relonquelonst.
  1: optional string hostnamelon

  // Thelon partition to which this elonarlybird belonlongs.
  2: optional i32 partition

  // Currelonnt elonarlybird QPS.
  // elonarlybirds should selont this fielonld at thelon elonnd of a relonquelonst (not at thelon start). This would givelon
  // roots a morelon up-to-datelon vielonw of thelon load on thelon elonarlybirds.
  3: optional i64 currelonntQps

  // Thelon timelon thelon relonquelonst waitelond in thelon quelonuelon belonforelon elonarlybird startelond procelonssing it.
  // This doelons not includelon thelon timelon spelonnt in thelon finaglelon quelonuelon: it's thelon timelon belontwelonelonn thelon momelonnt
  // elonarlybird reloncelonivelond thelon relonquelonst, and thelon momelonnt it startelond procelonssing thelon relonquelonst.
  4: optional i64 quelonuelonTimelonMillis

  // Thelon avelonragelon relonquelonst timelon in thelon quelonuelon belonforelon elonarlybird startelond procelonssing it.
  // This doelons not includelon thelon timelon that relonquelonsts spelonnt in thelon finaglelon quelonuelon: it's thelon avelonragelon timelon
  // belontwelonelonn thelon momelonnt elonarlybird reloncelonivelond its relonquelonsts, and thelon momelonnt it startelond procelonssing thelonm.
  5: optional i64 avelonragelonQuelonuelonTimelonMillis

  // Currelonnt avelonragelon pelonr-relonquelonst latelonncy as pelonrcelonivelond by elonarlybird.
  6: optional i64 avelonragelonLatelonncyMicros

  // Thelon tielonr to which this elonarlybird belonlongs.
  7: optional string tielonrNamelon
}

struct elonarlybirdRelonsponselon {
  // Nelonxt availablelon fielonld ID: 17
  1: optional ThriftSelonarchRelonsults selonarchRelonsults
  5: optional ThriftFacelontRelonsults facelontRelonsults
  6: optional ThriftTelonrmStatisticsRelonsults telonrmStatisticsRelonsults
  2: relonquirelond elonarlybirdRelonsponselonCodelon relonsponselonCodelon
  3: relonquirelond i64 relonsponselonTimelon
  7: optional i64 relonsponselonTimelonMicros
  // fielonlds belonlow will only belon relonturnelond if delonbug > 1 in thelon relonquelonst.
  4: optional string delonbugString
  8: optional elonarlybirdDelonbugInfo delonbugInfo

  // Only elonxists for melonrgelond elonarlybird relonsponselon.
  10: optional i32 numPartitions
  11: optional i32 numSuccelonssfulPartitions
  // Only elonxists for melonrgelond elonarlybird relonsponselon from multiplelon tielonrs.
  13: optional list<TielonrRelonsponselon> pelonrTielonrRelonsponselon

  // Total numbelonr of selongmelonnts that welonrelon selonarchelond. Partially selonarchelond selongmelonnts arelon fully countelond.
  // elon.g. if welon selonarchelond 1 selongmelonnt fully, and elonarly telonrminatelond half way through thelon seloncond
  // selongmelonnt, this fielonld should belon selont to 2.
  15: optional i32 numSelonarchelondSelongmelonnts

  // Whelonthelonr thelon relonquelonst elonarly telonrminatelond, if so, thelon telonrmination relonason.
  12: optional selonarch.elonarlyTelonrminationInfo elonarlyTelonrminationInfo

  // Whelonthelonr this relonsponselon is from cachelon.
  14: optional bool cachelonHit

  // Stats uselond by roots to delontelonrminelon if welon should go into delongradelond modelon.
  16: optional elonarlybirdSelonrvelonrStats elonarlybirdSelonrvelonrStats
}

elonnum elonarlybirdStatusCodelon {
  STARTING = 0,
  CURRelonNT = 1,
  STOPPING = 2,
  UNHelonALTHY = 3,
  BLACKLISTelonD = 4,

  PLACelon_HOLDelonR5 = 5,
  PLACelon_HOLDelonR6 = 6,
}

struct elonarlybirdStatusRelonsponselon {
  1: relonquirelond elonarlybirdStatusCodelon codelon
  2: relonquirelond i64 alivelonSincelon
  3: optional string melonssagelon
}

selonrvicelon elonarlybirdSelonrvicelon {
  string gelontNamelon(),
  elonarlybirdStatusRelonsponselon gelontStatus(),
  elonarlybirdRelonsponselon selonarch( 1: elonarlybirdRelonquelonst relonquelonst )
}
