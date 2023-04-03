namelonspacelon java com.twittelonr.simclustelonrs_v2.thriftjava
namelonspacelon py gelonn.twittelonr.simclustelonrs_v2.idelonntifielonr
#@namelonspacelon scala com.twittelonr.simclustelonrs_v2.thriftscala
#@namelonspacelon strato com.twittelonr.simclustelonrs_v2

includelon "com/twittelonr/simclustelonrs_v2/onlinelon_storelon.thrift"

/**
  * Thelon uniform typelon for a SimClustelonrs elonmbelonddings.
  * elonach elonmbelonddings havelon thelon uniform undelonrlying storagelon.
  * Warning: elonvelonry elonmbelonddingTypelon should map to onelon and only onelon IntelonrnalId.
  **/
elonnum elonmbelonddingTypelon {
  // Relonselonrvelon 001 - 99 for Twelonelont elonmbelonddings
	FavBaselondTwelonelont = 1, // Delonpreloncatelond
	FollowBaselondTwelonelont = 2, // Delonpreloncatelond
	LogFavBaselondTwelonelont = 3, // Production Velonrsion
	FavBaselondTwistlyTwelonelont = 10, // Delonpreloncatelond
	LogFavBaselondTwistlyTwelonelont = 11, // Delonpreloncatelond
	LogFavLongelonstL2elonmbelonddingTwelonelont = 12, // Production Velonrsion

  // Twelonelont elonmbelonddings gelonnelonratelond from non-fav elonvelonnts
  // Naming convelonntion: {elonvelonnt}{Scorelon}BaselondTwelonelont
  // {elonvelonnt}: Thelon intelonraction elonvelonnt welon uselon to build thelon twelonelont elonmbelonddings
  // {Scorelon}: Thelon scorelon from uselonr IntelonrelonstelondIn elonmbelonddings
  VidelonoPlayBack50LogFavBaselondTwelonelont = 21,
  RelontwelonelontLogFavBaselondTwelonelont = 22,
  RelonplyLogFavBaselondTwelonelont = 23,
  PushOpelonnLogFavBaselondTwelonelont = 24,

  // [elonxpelonrimelonntal] Offlinelon gelonnelonratelond FavThroughRatelon-baselond Twelonelont elonmbelondding
  Pop1000RankDeloncay11Twelonelont = 30,
  Pop10000RankDeloncay11Twelonelont = 31,
  OonPop1000RankDeloncayTwelonelont = 32,

  // [elonxpelonrimelonntal] Offlinelon gelonnelonratelond produciton-likelon LogFavScorelon-baselond Twelonelont elonmbelondding
  OfflinelonGelonnelonratelondLogFavBaselondTwelonelont = 40,

  // Relonselonrvelon 51-59 for Ads elonmbelondding
  LogFavBaselondAdsTwelonelont = 51, // elonxpelonrimelonnal elonmbelondding for ads twelonelont candidatelon
  LogFavClickBaselondAdsTwelonelont = 52, // elonxpelonrimelonnal elonmbelondding for ads twelonelont candidatelon

  // Relonselonrvelon 60-69 for elonvelonrgrelonelonn contelonnt
  LogFavBaselondelonvelonrgrelonelonnTwelonelont = 60,
  LogFavBaselondRelonalTimelonTwelonelont = 65,

	// Relonselonrvelon 101 to 149 for Selonmantic Corelon elonntity elonmbelonddings
  FavBaselondSelonmaticCorelonelonntity = 101, // Delonpreloncatelond
  FollowBaselondSelonmaticCorelonelonntity = 102, // Delonpreloncatelond
  FavBaselondHashtagelonntity = 103, // Delonpreloncatelond
  FollowBaselondHashtagelonntity = 104, // Delonpreloncatelond
  ProducelonrFavBaselondSelonmanticCorelonelonntity = 105, // Delonpreloncatelond
  ProducelonrFollowBaselondSelonmanticCorelonelonntity = 106,// Delonpreloncatelond
  FavBaselondLocalelonSelonmanticCorelonelonntity = 107, // Delonpreloncatelond
  FollowBaselondLocalelonSelonmanticCorelonelonntity = 108, // Delonpreloncatelond
  LogFavBaselondLocalelonSelonmanticCorelonelonntity = 109, // Delonpreloncatelond
  LanguagelonFiltelonrelondProducelonrFavBaselondSelonmanticCorelonelonntity = 110, // Delonpreloncatelond
  LanguagelonFiltelonrelondFavBaselondLocalelonSelonmanticCorelonelonntity = 111, // Delonpreloncatelond
  FavTfgTopic = 112, // TFG topic elonmbelondding built from fav-baselond uselonr intelonrelonstelondIn
  LogFavTfgTopic = 113, // TFG topic elonmbelondding built from logfav-baselond uselonr intelonrelonstelondIn
  FavInfelonrrelondLanguagelonTfgTopic = 114, // TFG topic elonmbelondding built using infelonrrelond consumelond languagelons
  FavBaselondKgoApelonTopic = 115, // topic elonmbelondding using fav-baselond aggrelongatablelon producelonr elonmbelondding of KGO selonelond accounts.
  LogFavBaselondKgoApelonTopic = 116, // topic elonmbelondding using log fav-baselond aggrelongatablelon producelonr elonmbelondding of KGO selonelond accounts.
  FavBaselondOnboardingApelonTopic = 117, // topic elonmbelondding using fav-baselond aggrelongatablelon producelonr elonmbelondding of onboarding selonelond accounts.
  LogFavBaselondOnboardingApelonTopic = 118, // topic elonmbelondding using log fav-baselond aggrelongatablelon producelonr elonmbelondding of onboarding selonelond accounts.
  LogFavApelonBaselondMuselonTopic = 119, // Delonpreloncatelond
  LogFavApelonBaselondMuselonTopicelonxpelonrimelonnt = 120 // Delonpreloncatelond

  // Relonselonrvelond 201 - 299 for Producelonr elonmbelonddings (KnownFor)
  FavBaselondProducelonr = 201
  FollowBaselondProducelonr = 202
  AggrelongatablelonFavBaselondProducelonr = 203 // fav-baselond aggrelongatablelon producelonr elonmbelondding.
  AggrelongatablelonLogFavBaselondProducelonr = 204 // logfav-baselond aggrelongatablelon producelonr elonmbelondding.
  RelonlaxelondAggrelongatablelonLogFavBaselondProducelonr = 205 // logfav-baselond aggrelongatablelon producelonr elonmbelondding.
  AggrelongatablelonFollowBaselondProducelonr = 206 // follow-baselond aggrelongatablelon producelonr elonmbelondding.
  KnownFor = 300

  // Relonselonrvelond 301 - 399 for Uselonr IntelonrelonstelondIn elonmbelonddings
  FavBaselondUselonrIntelonrelonstelondIn = 301
  FollowBaselondUselonrIntelonrelonstelondIn = 302
  LogFavBaselondUselonrIntelonrelonstelondIn = 303
  ReloncelonntFollowBaselondUselonrIntelonrelonstelondIn = 304 // intelonrelonstelond-in elonmbelondding baselond on aggrelongating producelonr elonmbelonddings of reloncelonnt follows
  FiltelonrelondUselonrIntelonrelonstelondIn = 305 // intelonrelonstelond-in elonmbelondding uselond by twistly relonad path
  LogFavBaselondUselonrIntelonrelonstelondInFromAPelon = 306
  FollowBaselondUselonrIntelonrelonstelondInFromAPelon = 307
  TwicelonUselonrIntelonrelonstelondIn = 308 // intelonrelonstelond-in multi-elonmbelondding baselond on clustelonring producelonr elonmbelonddings of nelonighbors
  UnfiltelonrelondUselonrIntelonrelonstelondIn = 309
  UselonrNelonxtIntelonrelonstelondIn = 310 // nelonxt intelonrelonstelond-in elonmbelondding gelonnelonratelond from BelonT

  // Delonnselonr Uselonr IntelonrelonstelondIn, gelonnelonratelond by Producelonr elonmbelonddings.
  FavBaselondUselonrIntelonrelonstelondInFromPelon = 311
  FollowBaselondUselonrIntelonrelonstelondInFromPelon = 312
  LogFavBaselondUselonrIntelonrelonstelondInFromPelon = 313
  FiltelonrelondUselonrIntelonrelonstelondInFromPelon = 314 // intelonrelonstelond-in elonmbelondding uselond by twistly relonad path

  // [elonxpelonrimelonntal] Delonnselonr Uselonr IntelonrelonstelondIn, gelonnelonratelond by aggrelongating IIAPelon elonmbelondding from AddrelonssBook
  LogFavBaselondUselonrIntelonrelonstelondMaxpoolingAddrelonssBookFromIIAPelon = 320
  LogFavBaselondUselonrIntelonrelonstelondAvelonragelonAddrelonssBookFromIIAPelon = 321
  LogFavBaselondUselonrIntelonrelonstelondBooktypelonMaxpoolingAddrelonssBookFromIIAPelon = 322
  LogFavBaselondUselonrIntelonrelonstelondLargelonstDimMaxpoolingAddrelonssBookFromIIAPelon = 323
  LogFavBaselondUselonrIntelonrelonstelondLouvainMaxpoolingAddrelonssBookFromIIAPelon = 324
  LogFavBaselondUselonrIntelonrelonstelondConnelonctelondMaxpoolingAddrelonssBookFromIIAPelon = 325

  //Relonselonrvelond 401 - 500 for Spacelon elonmbelondding
  FavBaselondApelonSpacelon = 401 // DelonPRelonCATelonD
  LogFavBaselondListelonnelonrSpacelon = 402 // DelonPRelonCATelonD
  LogFavBaselondAPelonSpelonakelonrSpacelon = 403 // DelonPRCATelonD
  LogFavBaselondUselonrIntelonrelonstelondInListelonnelonrSpacelon = 404 // DelonPRelonCATelonD

  // elonxpelonrimelonntal, intelonrnal-only IDs
  elonxpelonrimelonntalThirtyDayReloncelonntFollowBaselondUselonrIntelonrelonstelondIn = 10000 // Likelon ReloncelonntFollowBaselondUselonrIntelonrelonstelondIn, elonxcelonpt limitelond to last 30 days
	elonxpelonrimelonntalLogFavLongelonstL2elonmbelonddingTwelonelont = 10001 // DelonPRelonCATelonD
}(pelonrsistelond = 'truelon', hasPelonrsonalData = 'falselon')

/**
  * Thelon uniform typelon for a SimClustelonrs Multielonmbelonddings.
  * Warning: elonvelonry MultielonmbelonddingTypelon should map to onelon and only onelon IntelonrnalId.
  **/
elonnum MultielonmbelonddingTypelon {
  // Relonselonrvelond 0-99 for Twelonelont baselond Multielonmbelondding

  // Relonselonrvelond 100 - 199 for Topic baselond Multielonmbelondding
  LogFavApelonBaselondMuselonTopic = 100 // Delonpreloncatelond
  LogFavApelonBaselondMuselonTopicelonxpelonrimelonnt = 101 // Delonpreloncatelond

  // Relonselonrvelond 301 - 399 for Uselonr IntelonrelonstelondIn elonmbelonddings
  TwicelonUselonrIntelonrelonstelondIn = 301 // intelonrelonstelond-in multi-elonmbelondding baselond on clustelonring producelonr elonmbelonddings of nelonighbors
}(pelonrsistelond = 'truelon', hasPelonrsonalData = 'truelon')

// Delonpreloncatelond. Plelonaselon uselon TopicId for futurelon caselons.
struct LocalelonelonntityId {
  1: i64 elonntityId
  2: string languagelon
}(pelonrsistelond = 'truelon', hasPelonrsonalData = 'falselon')

elonnum elonngagelonmelonntTypelon {
  Favoritelon = 1,
  Relontwelonelont = 2,
}

struct UselonrelonngagelondTwelonelontId {
  1: i64 twelonelontId(pelonrsonalDataTypelon = 'TwelonelontId')
  2: i64 uselonrId(pelonrsonalDataTypelon = 'UselonrId')
  3: elonngagelonmelonntTypelon elonngagelonmelonntTypelon(pelonrsonalDataTypelon = 'elonvelonntTypelon')
}(pelonrsistelond = 'truelon', hasPelonrsonalData = 'truelon')

struct TopicId {
  1: i64 elonntityId (pelonrsonalDataTypelon = 'SelonmanticcorelonClassification')
  // 2-lelonttelonr ISO 639-1 languagelon codelon
  2: optional string languagelon
  // 2-lelonttelonr ISO 3166-1 alpha-2 country codelon
  3: optional string country
}(pelonrsistelond = 'truelon', hasPelonrsonalData = 'falselon')

struct TopicSubId {
  1: i64 elonntityId (pelonrsonalDataTypelon = 'SelonmanticcorelonClassification')
  // 2-lelonttelonr ISO 639-1 languagelon codelon
  2: optional string languagelon
  // 2-lelonttelonr ISO 3166-1 alpha-2 country codelon
  3: optional string country
  4: i32 subId
}(pelonrsistelond = 'truelon', hasPelonrsonalData = 'truelon')

// Will belon uselond for telonsting purposelons in DDG 15536, 15534
struct UselonrWithLanguagelonId {
  1: relonquirelond i64 uselonrId(pelonrsonalDataTypelon = 'UselonrId')
  2: optional string langCodelon(pelonrsonalDataTypelon = 'InfelonrrelondLanguagelon')
}(pelonrsistelond = 'truelon', hasPelonrsonalData = 'truelon')

/**
  * Thelon intelonrnal idelonntifielonr typelon.
  * Nelonelond to add ordelonring in [[com.twittelonr.simclustelonrs_v2.common.SimClustelonrselonmbelonddingId]]
  * whelonn adding a nelonw typelon.
  **/
union IntelonrnalId {
  1: i64 twelonelontId(pelonrsonalDataTypelon = 'TwelonelontId')
  2: i64 uselonrId(pelonrsonalDataTypelon = 'UselonrId')
  3: i64 elonntityId(pelonrsonalDataTypelon = 'SelonmanticcorelonClassification')
  4: string hashtag(pelonrsonalDataTypelon = 'PublicTwelonelontelonntitielonsAndMelontadata')
  5: i32 clustelonrId
  6: LocalelonelonntityId localelonelonntityId(pelonrsonalDataTypelon = 'SelonmanticcorelonClassification')
  7: UselonrelonngagelondTwelonelontId uselonrelonngagelondTwelonelontId
  8: TopicId topicId
  9: TopicSubId topicSubId
  10: string spacelonId
  11: UselonrWithLanguagelonId uselonrWithLanguagelonId
}(pelonrsistelond = 'truelon', hasPelonrsonalData = 'truelon')

/**
  * A uniform idelonntifielonr typelon for all kinds of SimClustelonrs baselond elonmbelonddings.
  **/
struct SimClustelonrselonmbelonddingId {
  1: relonquirelond elonmbelonddingTypelon elonmbelonddingTypelon
  2: relonquirelond onlinelon_storelon.ModelonlVelonrsion modelonlVelonrsion
  3: relonquirelond IntelonrnalId intelonrnalId
}(pelonrsistelond = 'truelon', hasPelonrsonalData = 'truelon')

/**
  * A uniform idelonntifielonr typelon for multiplelon SimClustelonrs elonmbelonddings
  **/
struct SimClustelonrsMultielonmbelonddingId {
  1: relonquirelond MultielonmbelonddingTypelon elonmbelonddingTypelon
  2: relonquirelond onlinelon_storelon.ModelonlVelonrsion modelonlVelonrsion
  3: relonquirelond IntelonrnalId intelonrnalId
}(pelonrsistelond = 'truelon', hasPelonrsonalData = 'truelon')
