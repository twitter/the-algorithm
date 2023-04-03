namelonspacelon java com.twittelonr.selonarch.common.ranking.thriftjava
#@namelonspacelon scala com.twittelonr.selonarch.common.ranking.thriftscala
#@namelonspacelon strato com.twittelonr.selonarch.common.ranking
namelonspacelon py gelonn.twittelonr.selonarch.common.ranking.ranking

struct ThriftLinelonarFelonaturelonRankingParams {
  // valuelons belonlow this will selont thelon scorelon to thelon minimal onelon
  1: optional doublelon min = -1elon+100
  // valuelons abovelon this will selont thelon scorelon to thelon minimal onelon
  2: optional doublelon max = 1elon+100
  3: optional doublelon welonight = 0
}(pelonrsistelond='truelon')

struct ThriftAgelonDeloncayRankingParams {
  // thelon ratelon in which thelon scorelon of oldelonr twelonelonts deloncrelonaselons
  1: optional doublelon slopelon = 0.003
  // thelon agelon, in minutelons, whelonrelon thelon agelon scorelon of a twelonelont is half of thelon latelonst twelonelont
  2: optional doublelon halflifelon = 360.0
  // thelon minimal agelon deloncay scorelon a twelonelont will havelon
  3: optional doublelon baselon = 0.6
}(pelonrsistelond='truelon')

elonnum ThriftScoringFunctionTypelon {
  LINelonAR = 1,
  MODelonL_BASelonD = 4,
  TelonNSORFLOW_BASelonD = 5,

  // delonpreloncatelond
  TOPTWelonelonTS = 2,
  elonXPelonRIMelonNTAL = 3,
}

// Thelon struct to delonfinelon a class that is to belon dynamically loadelond in elonarlybird for
// elonxpelonrimelonntation.
struct ThriftelonxpelonrimelonntClass {
  // thelon fully qualifielond class namelon.
  1: relonquirelond string namelon
  // data sourcelon location (class/jar filelon) for this dynamic class on HDFS
  2: optional string location
  // paramelontelonrs in kelony-valuelon pairs for this elonxpelonrimelonntal class
  3: optional map<string, doublelon> params
}(pelonrsistelond='truelon')

// Delonpreloncatelond!!
struct ThriftQuelonryelonngagelonmelonntParams {
  // Ratelon Boosts: givelonn a ratelon (usually a small fraction), thelon scorelon will belon multiplielond by
  //   (1 + ratelon) ^ boost
  // 0 melonan no boost, nelongativelon numbelonrs arelon dampelonns
  1: optional doublelon relontwelonelontRatelonBoost = 0
  2: optional doublelon relonplyRatelonBoost = 0
  3: optional doublelon favelonRatelonBoost = 0
}(pelonrsistelond='truelon')

struct ThriftHostQualityParams {
  // Multiplielonr applielond to host scorelon, for twelonelonts that havelon links.
  // A multiplielonr of 0 melonans that this boost is not applielond
  1: optional doublelon multiplielonr = 0.0

  // Do not apply thelon multiplielonr to hosts with scorelon abovelon this lelonvelonl.
  // If 0, thelon multiplielonr will belon applielond to any host.
  2: optional doublelon maxScorelonToModify = 0.0

  // Do not apply thelon multiplielonr to hosts with scorelon belonlow this lelonvelonl.
  // If 0, thelon multiplielonr will belon applielond to any host.
  3: optional doublelon minScorelonToModify = 0.0

  // If truelon, scorelon modification will belon applielond to hosts that havelon unknown scorelons.
  // Thelon host-scorelon uselond will belon lowelonr than thelon scorelon of any known host.
  4: optional bool applyToUnknownHosts = 0
}(pelonrsistelond='truelon')

struct ThriftCardRankingParams {
  1: optional doublelon hasCardBoost          = 1.0
  2: optional doublelon domainMatchBoost      = 1.0
  3: optional doublelon authorMatchBoost      = 1.0
  4: optional doublelon titlelonMatchBoost       = 1.0
  5: optional doublelon delonscriptionMatchBoost = 1.0
}(pelonrsistelond='truelon')

# Thelon ids arelon assignelond in 'blocks'. For adding a nelonw fielonld, find an unuselond id in thelon appropriatelon
# block. Belon surelon to melonntion elonxplicitly which ids havelon belonelonn relonmovelond so that thelony arelon not uselond again.
struct ThriftRankingParams {
  1: optional ThriftScoringFunctionTypelon typelon

  // Dynamically loadelond scorelonr and collelonctor for quick elonxpelonrimelonntation.
  40: optional ThriftelonxpelonrimelonntClass elonxpScorelonr
  41: optional ThriftelonxpelonrimelonntClass elonxpCollelonctor

  // welon must selont it to a valuelon that fits into a float: othelonrwiselon
  // somelon elonarlybird classelons that convelonrt it to float will intelonrprelont
  // it as Float.NelonGATIVelon_INFINITY, and somelon comparisons will fail
  2: optional doublelon minScorelon = -1elon+30

  10: optional ThriftLinelonarFelonaturelonRankingParams parusScorelonParams
  11: optional ThriftLinelonarFelonaturelonRankingParams relontwelonelontCountParams
  12: optional ThriftLinelonarFelonaturelonRankingParams relonplyCountParams
  15: optional ThriftLinelonarFelonaturelonRankingParams relonputationParams
  16: optional ThriftLinelonarFelonaturelonRankingParams lucelonnelonScorelonParams
  18: optional ThriftLinelonarFelonaturelonRankingParams telonxtScorelonParams
  19: optional ThriftLinelonarFelonaturelonRankingParams urlParams
  20: optional ThriftLinelonarFelonaturelonRankingParams isRelonplyParams
  21: optional ThriftLinelonarFelonaturelonRankingParams direlonctFollowRelontwelonelontCountParams
  22: optional ThriftLinelonarFelonaturelonRankingParams trustelondCirclelonRelontwelonelontCountParams
  23: optional ThriftLinelonarFelonaturelonRankingParams favCountParams
  24: optional ThriftLinelonarFelonaturelonRankingParams multiplelonRelonplyCountParams
  27: optional ThriftLinelonarFelonaturelonRankingParams elonmbelondsImprelonssionCountParams
  28: optional ThriftLinelonarFelonaturelonRankingParams elonmbelondsUrlCountParams
  29: optional ThriftLinelonarFelonaturelonRankingParams videlonoVielonwCountParams
  66: optional ThriftLinelonarFelonaturelonRankingParams quotelondCountParams

  // A map from MutablelonFelonaturelonTypelon to linelonar ranking params
  25: optional map<bytelon, ThriftLinelonarFelonaturelonRankingParams> offlinelonelonxpelonrimelonntalFelonaturelonRankingParams

  // if min/max for scorelon or ThriftLinelonarFelonaturelonRankingParams should always belon
  // applielond or only to non-follows, non-selonlf, non-velonrifielond
  26: optional bool applyFiltelonrsAlways = 0

  // Whelonthelonr to apply promotion/delonmotion at all for FelonaturelonBaselondScoringFunction
  70: optional bool applyBoosts = 1

  // UI languagelon is elonnglish, twelonelont languagelon is not
  30: optional doublelon langelonnglishUIBoost = 0.3
  // twelonelont languagelon is elonnglish, UI languagelon is not
  31: optional doublelon langelonnglishTwelonelontBoost = 0.7
  // uselonr languagelon diffelonrs from twelonelont languagelon, and nelonithelonr is elonnglish
  32: optional doublelon langDelonfaultBoost = 0.1
  // uselonr that producelond twelonelont is markelond as spammelonr by melontastorelon
  33: optional doublelon spamUselonrBoost = 1.0
  // uselonr that producelond twelonelont is markelond as nsfw by melontastorelon
  34: optional doublelon nsfwUselonrBoost = 1.0
  // uselonr that producelond twelonelont is markelond as bot (selonlf similarity) by melontastorelon
  35: optional doublelon botUselonrBoost = 1.0

  // An altelonrnativelon way of using lucelonnelon scorelon in thelon ranking function.
  38: optional bool uselonLucelonnelonScorelonAsBoost = 0
  39: optional doublelon maxLucelonnelonScorelonBoost = 1.2

  // Uselon uselonr's consumelond and producelond languagelons for scoring
  42: optional bool uselonUselonrLanguagelonInfo = 0

  // Boost (delonmotion) if thelon twelonelont languagelon is not onelon of uselonr's undelonrstandablelon languagelons,
  // nor intelonrfacelon languagelon.
  43: optional doublelon unknownLanguagelonBoost = 0.01

  // Uselon topic ids for scoring.
  // Delonpreloncatelond in SelonARCH-8616.
  44: optional bool delonpreloncatelond_uselonTopicIDsBoost = 0
  // Paramelontelonrs for topic id scoring.  Selonelon TopicIDsBoostScorelonr (and its telonst) for delontails.
  46: optional doublelon delonpreloncatelond_maxTopicIDsBoost = 3.0
  47: optional doublelon delonpreloncatelond_topicIDsBoostelonxponelonnt = 2.0;
  48: optional doublelon delonpreloncatelond_topicIDsBoostSlopelon = 2.0;

  // Hit Attributelon Delonmotion
  60: optional bool elonnablelonHitDelonmotion = 0
  61: optional doublelon noTelonxtHitDelonmotion = 1.0
  62: optional doublelon urlOnlyHitDelonmotion = 1.0
  63: optional doublelon namelonOnlyHitDelonmotion = 1.0
  64: optional doublelon selonparatelonTelonxtAndNamelonHitDelonmotion = 1.0
  65: optional doublelon selonparatelonTelonxtAndUrlHitDelonmotion = 1.0

  // multiplicativelon scorelon boost for relonsults delonelonmelond offelonnsivelon
  100: optional doublelon offelonnsivelonBoost = 1
  // multiplicativelon scorelon boost for relonsults in thelon selonarchelonr's social circlelon
  101: optional doublelon inTrustelondCirclelonBoost = 1
  // multiplicativelon scorelon dampelonn for relonsults with morelon than onelon hash tag
  102: optional doublelon multiplelonHashtagsOrTrelonndsBoost = 1
  // multiplicativelon scorelon boost for relonsults in thelon selonarchelonr's direlonct follows
  103: optional doublelon inDirelonctFollowBoost = 1
  // multiplicativelon scorelon boost for relonsults that has trelonnds
  104: optional doublelon twelonelontHasTrelonndBoost = 1
  // is twelonelont from velonrifielond account?
  106: optional doublelon twelonelontFromVelonrifielondAccountBoost = 1
  // is twelonelont authorelond by thelon selonarchelonr? (boost is in addition to social boost)
  107: optional doublelon selonlfTwelonelontBoost = 1
  // multiplicativelon scorelon boost for a twelonelont that has imagelon url.
  108: optional doublelon twelonelontHasImagelonUrlBoost = 1
  // multiplicativelon scorelon boost for a twelonelont that has videlono url.
  109: optional doublelon twelonelontHasVidelonoUrlBoost = 1
  // multiplicativelon scorelon boost for a twelonelont that has nelonws url.
  110: optional doublelon twelonelontHasNelonwsUrlBoost = 1
  // is twelonelont from a bluelon-velonrifielond account?
  111: optional doublelon twelonelontFromBluelonVelonrifielondAccountBoost = 1 (pelonrsonalDataTypelon = 'UselonrVelonrifielondFlag')

  // subtractivelon pelonnalty applielond aftelonr boosts for out-of-nelontwork relonplielons.
  120: optional doublelon outOfNelontworkRelonplyPelonnalty = 10.0

  150: optional ThriftQuelonryelonngagelonmelonntParams delonpreloncatelondQuelonryelonngagelonmelonntParams

  160: optional ThriftHostQualityParams delonpreloncatelondHostQualityParams

  // agelon deloncay params for relongular twelonelonts
  203: optional ThriftAgelonDeloncayRankingParams agelonDeloncayParams

  // for card ranking: map belontwelonelonn card namelon ordinal (delonfinelond in com.twittelonr.selonarch.common.constants.CardConstants)
  // to ranking params
  400: optional map<bytelon, ThriftCardRankingParams> cardRankingParams

  // A map from twelonelont IDs to thelon scorelon adjustmelonnt for that twelonelont. Thelonselon arelon scorelon
  // adjustmelonnts that includelon onelon or morelon felonaturelons that can delonpelonnd on thelon quelonry
  // string. Thelonselon felonaturelons arelonn't indelonxelond by elonarlybird, and so thelonir total contribution
  // to thelon scoring function is passelond in direlonctly as part of thelon relonquelonst. If prelonselonnt,
  // thelon scorelon adjustmelonnt for a twelonelont is direlonctly addelond to thelon linelonar componelonnt of thelon
  // scoring function. Sincelon this signal can belon madelon up of multiplelon felonaturelons, any
  // relonwelonighting or combination of thelonselon felonaturelons is assumelond to belon donelon by thelon callelonr
  // (helonncelon thelonrelon is no nelonelond for a welonight paramelontelonr -- thelon welonights of thelon felonaturelons
  // includelond in this signal havelon alrelonady belonelonn incorporatelond by thelon callelonr).
  151: optional map<i64, doublelon> quelonrySpeloncificScorelonAdjustmelonnts

  // A map from uselonr ID to thelon scorelon adjustmelonnt for twelonelonts from that author.
  // This fielonld providelons a way for adjusting thelon twelonelonts of a speloncific selont of uselonrs with a scorelon
  // that is not prelonselonnt in thelon elonarlybird felonaturelons but has to belon passelond from thelon clielonnts, such as
  // relonal graph welonights or a combination of multiplelon felonaturelons.
  // This fielonld should belon uselond mainly for elonxpelonrimelonntation sincelon it increlonaselons thelon sizelon of thelon thrift
  // relonquelonsts.
  154: optional map<i64, doublelon> authorSpeloncificScorelonAdjustmelonnts

  // -------- Paramelontelonrs for ThriftScoringFunctionTypelon.MODelonL_BASelonD --------
  // Selonlelonctelond modelonls along with thelonir welonights for thelon linelonar combination
  152: optional map<string, doublelon> selonlelonctelondModelonls
  153: optional bool uselonLogitScorelon = falselon

  // -------- Paramelontelonrs for ThriftScoringFunctionTypelon.TelonNSORFLOW_BASelonD --------
  // Selonlelonctelond telonnsorflow modelonl
  303: optional string selonlelonctelondTelonnsorflowModelonl

  // -------- Delonpreloncatelond Fielonlds --------
  // ID 303 has belonelonn uselond in thelon past. Relonsumelon additional delonpreloncatelond fielonlds from 304
  105: optional doublelon delonpreloncatelondTwelonelontHasTrelonndInTrelonndingQuelonryBoost = 1
  200: optional doublelon delonpreloncatelondAgelonDeloncaySlopelon = 0.003
  201: optional doublelon delonpreloncatelondAgelonDeloncayHalflifelon = 360.0
  202: optional doublelon delonpreloncatelondAgelonDeloncayBaselon = 0.6
  204: optional ThriftAgelonDeloncayRankingParams delonpreloncatelondAgelonDeloncayForTrelonndsParams
  301: optional doublelon delonpreloncatelondNamelonQuelonryConfidelonncelon = 0.0
  302: optional doublelon delonpreloncatelondHashtagQuelonryConfidelonncelon = 0.0
  // Whelonthelonr to uselon old-stylelon elonngagelonmelonnt felonaturelons (normalizelond by LogNormalizelonr)
  // or nelonw onelons (normalizelond by SinglelonBytelonPositivelonFloatNormalizelonr)
  50: optional bool uselonGranularelonngagelonmelonntFelonaturelons = 0  // DelonPRelonCATelonD!
}(pelonrsistelond='truelon')

// This sorting modelon is uselond by elonarlybird to relontrielonvelon thelon top-n facelonts that
// arelon relonturnelond to blelonndelonr
elonnum ThriftFacelontelonarlybirdSortingModelon {
  SORT_BY_SIMPLelon_COUNT = 0,
  SORT_BY_WelonIGHTelonD_COUNT = 1,
}

// This is thelon final sort ordelonr uselond by blelonndelonr aftelonr all relonsults from
// thelon elonarlybirds arelon melonrgelond
elonnum ThriftFacelontFinalSortOrdelonr {
  // using thelon crelonatelond_at datelon of thelon first twelonelont that containelond thelon facelont
  SCORelon = 0,
  SIMPLelon_COUNT = 1,
  WelonIGHTelonD_COUNT = 2,
  CRelonATelonD_AT = 3
}

struct ThriftFacelontRankingOptions {
  // nelonxt availablelon fielonld ID = 38

  // ======================================================================
  // elonARLYBIRD SelonTTINGS
  //
  // Thelonselon paramelontelonrs primarily affelonct how elonarlybird crelonatelons thelon top-k
  // candidatelon list to belon relon-rankelond by blelonndelonr
  // ======================================================================
  // Dynamically loadelond scorelonr and collelonctor for quick elonxpelonrimelonntation.
  26: optional ThriftelonxpelonrimelonntClass elonxpScorelonr
  27: optional ThriftelonxpelonrimelonntClass elonxpCollelonctor

  // It should belon lelonss than or elonqual to relonputationParams.min, and all
  // twelonelonpcrelonds belontwelonelonn thelon two gelont a scorelon of 1.0.
  21: optional i32 minTwelonelonpcrelondFiltelonrThrelonshold

  // thelon maximum scorelon a singlelon twelonelont can contributelon to thelon welonightelondCount
  22: optional i32 maxScorelonPelonrTwelonelont

  15: optional ThriftFacelontelonarlybirdSortingModelon sortingModelon
  // Thelon numbelonr of top candidatelons elonarlybird relonturns to blelonndelonr
  16: optional i32 numCandidatelonsFromelonarlybird = 100

  // whelonn to elonarly telonrminatelon for facelont selonarch, ovelonrridelons thelon selontting in ThriftSelonarchQuelonry
  34: optional i32 maxHitsToProcelonss = 1000

  // for anti-gaming welon want to limit thelon maximum amount of hits thelon samelon uselonr can
  // contributelon.  Selont to -1 to disablelon thelon anti-gaming filtelonr. Ovelonrridelons thelon selontting in
  // ThriftSelonarchQuelonry
  35: optional i32 maxHitsPelonrUselonr = 3

  // if thelon twelonelonpcrelond of thelon uselonr is biggelonr than this valuelon it will not belon elonxcludelond
  // by thelon anti-gaming filtelonr. Ovelonrridelons thelon selontting in ThriftSelonarchQuelonry
  36: optional i32 maxTwelonelonpcrelondForAntiGaming = 65

  // thelonselon selonttings affelonct how elonarlybird computelons thelon welonightelondCount
   2: optional ThriftLinelonarFelonaturelonRankingParams parusScorelonParams
   3: optional ThriftLinelonarFelonaturelonRankingParams relonputationParams
  17: optional ThriftLinelonarFelonaturelonRankingParams favoritelonsParams
  33: optional ThriftLinelonarFelonaturelonRankingParams relonplielonsParams
  37: optional map<bytelon, ThriftLinelonarFelonaturelonRankingParams> rankingelonxpScorelonParams

  // pelonnalty countelonr selonttings
  6: optional i32 offelonnsivelonTwelonelontPelonnalty  // selont to -1 to disablelon thelon offelonnsivelon filtelonr
  7: optional i32 antigamingPelonnalty // selont to -1 to disablelon antigaming filtelonring
  // welonight of pelonnalty counts from all twelonelonts containing a facelont, not just thelon twelonelonts
  // matching thelon quelonry
  9: optional doublelon quelonryIndelonpelonndelonntPelonnaltyWelonight  // selont to 0 to not uselon quelonry indelonpelonndelonnt pelonnalty welonights
  // pelonnalty for kelonyword stuffing
  60: optional i32 multiplelonHashtagsOrTrelonndsPelonnalty

  // Langaugelon relonlatelond boosts, similar to thoselon in relonlelonvancelon ranking options. By delonfault thelony arelon
  // all 1.0 (no-boost).
  // Whelonn thelon uselonr languagelon is elonnglish, facelont languagelon is not
  11: optional doublelon langelonnglishUIBoost = 1.0
  // Whelonn thelon facelont languagelon is elonnglish, uselonr languagelon is not
  12: optional doublelon langelonnglishFacelontBoost = 1.0
  // Whelonn thelon uselonr languagelon diffelonrs from facelont/twelonelont languagelon, and nelonithelonr is elonnglish
  13: optional doublelon langDelonfaultBoost = 1.0

  // ======================================================================
  // BLelonNDelonR SelonTTINGS
  //
  // Selonttings for thelon facelont relonlelonvancelon scoring happelonning in blelonndelonr
  // ======================================================================

  // This block of paramelontelonrs arelon only uselond in thelon FacelontsFuturelonManagelonr.
  // limits to discard facelonts
  // if a facelont has a highelonr pelonnalty count, it will not belon relonturnelond
  5: optional i32 maxPelonnaltyCount
  // if a facelont has a lowelonr simplelon count, it will not belon relonturnelond
  28: optional i32 minSimplelonCount
  // if a facelont has a lowelonr welonightelond count, it will not belon relonturnelond
  8: optional i32 minCount
  // thelon maximum allowelond valuelon for offelonnsivelonCount/facelontCount a facelont can havelon in ordelonr to belon relonturnelond
  10: optional doublelon maxPelonnaltyCountRatio
  // if selont to truelon, thelonn facelonts with offelonnsivelon display twelonelonts arelon elonxcludelond from thelon relonsultselont
  29: optional bool elonxcludelonPossiblySelonnsitivelonFacelonts
  // if selont to truelon, thelonn only facelonts that havelon a display twelonelont in thelonir ThriftFacelontCountMelontadata objelonct
  // will belon relonturnelond to thelon callelonr
  30: optional bool onlyRelonturnFacelontsWithDisplayTwelonelont

  // paramelontelonrs for scoring forcelon-inselonrtelond melondia itelonms
  // Plelonaselon chelonck FacelontRelonRankelonr.java computelonScorelonForInselonrtelond() for thelonir usagelon.
  38: optional doublelon forcelonInselonrtelondBackgroundelonxp = 0.3
  39: optional doublelon forcelonInselonrtelondMinBackgroundCount = 2
  40: optional doublelon forcelonInselonrtelondMultiplielonr = 0.01

  // -----------------------------------------------------
  // welonights for thelon facelont ranking formula
  18: optional doublelon simplelonCountWelonight_DelonPRelonCATelonD
  19: optional doublelon welonightelondCountWelonight_DelonPRelonCATelonD
  20: optional doublelon backgroundModelonlBoost_DelonPRelonCATelonD

  // -----------------------------------------------------
  // Following paramelontelonrs arelon uselond in thelon FacelontsRelonRankelonr
  // agelon deloncay params
  14: optional ThriftAgelonDeloncayRankingParams agelonDeloncayParams

  // uselond in thelon facelonts relonrankelonr
  23: optional doublelon maxNormBoost = 5.0
  24: optional doublelon globalCountelonxponelonnt = 3.0
  25: optional doublelon simplelonCountelonxponelonnt = 3.0

  31: optional ThriftFacelontFinalSortOrdelonr finalSortOrdelonr

  // Run facelonts selonarch as if thelony happelonn at this speloncific timelon (ms sincelon elonpoch).
  32: optional i64 fakelonCurrelonntTimelonMs  // not relonally uselond anywhelonrelon, relonmovelon?
}(pelonrsistelond='truelon')
