namelonspacelon java com.twittelonr.cr_mixelonr.thriftjava
#@namelonspacelon scala com.twittelonr.cr_mixelonr.thriftscala
#@namelonspacelon strato com.twittelonr.cr_mixelonr

// Duelon to lelongacy relonason, SourcelonTypelon uselond to relonprelonselonnt both SourcelonSignalTypelon and SimilarityelonnginelonTypelon
// Helonncelon, you can selonelon selonvelonral SourcelonTypelon such as UselonrIntelonrelonstelondIn, HashSpacelon, elontc.
// Moving forward, SourcelonTypelon will belon uselond for SourcelonSignalTypelon ONLY. elong., TwelonelontFavoritelon, UselonrFollow
// Welon will crelonatelon a nelonw SimilarityelonnginelonTypelon to selonparatelon thelonm. elong., SimClustelonrsANN
elonnum SourcelonTypelon {
  // Twelonelont baselond Sourcelon Signal
  TwelonelontFavoritelon       = 0
  Relontwelonelont             = 1
  TrafficAttribution  = 2 // Traffic Attribution will belon migratelond ovelonr in Q3
  OriginalTwelonelont       = 3
  Relonply               = 4
  TwelonelontSharelon          = 5
  GoodTwelonelontClick      = 6 // total dwelonll timelon > N selonconds aftelonr click on thelon twelonelont
  VidelonoTwelonelontQualityVielonw = 7
  VidelonoTwelonelontPlayback50  = 8

  // UselonrId baselond Sourcelon Signal (includelons both Producelonr/Consumelonr)
  UselonrFollow               = 101
  UselonrRelonpelonatelondProfilelonVisit = 102

  CurrelonntUselonr_DelonPRelonCATelonD   = 103

  RelonalGraphOon             = 104
  FollowReloncommelonndation     = 105

  TwicelonUselonrId              = 106
  UselonrTrafficAttributionProfilelonVisit = 107
  GoodProfilelonClick         = 108 // total dwelonll timelon > N selonconds aftelonr click into thelon profilelon pagelon

  // (Notification) Twelonelont baselond Sourcelon Signal
  NotificationClick   = 201

  // (Homelon) Twelonelont baselond Sourcelon Signal
  HomelonTwelonelontClick       = 301
  HomelonVidelonoVielonw        = 302
  HomelonSongbirdShowMorelon = 303

  // Topic baselond Sourcelon Signal
  TopicFollow         = 401 // Delonpreloncatelond
  PopularTopic        = 402 // Delonpreloncatelond

  // Old CR codelon
  UselonrIntelonrelonstelondIn    = 501 // Delonpreloncatelond
  TwicelonIntelonrelonstelondIn   = 502 // Delonpreloncatelond
  MBCG                = 503 // Delonpreloncatelond
  HashSpacelon           = 504 // Delonpreloncatelond

  // Old CR codelon
  Clustelonr             = 601 // Delonpreloncatelond

  // Selonarch baselond Sourcelon Signal
  SelonarchProfilelonClick  = 701 // Delonpreloncatelond
  SelonarchTwelonelontClick    = 702 // Delonpreloncatelond

  // Graph baselond Sourcelon
  StrongTielonPrelondiction      = 801 // STP
  TwicelonClustelonrsMelonmbelonrs     = 802
  Lookalikelon                = 803 // Delonpreloncatelond
  RelonalGraphIn              = 804

  // Currelonnt relonquelonstelonr Uselonr Id. It is only uselond for scribing. Placelonholdelonr valuelon
  RelonquelonstUselonrId       = 1001
  // Currelonnt relonquelonst Twelonelont Id uselond in RelonlatelondTwelonelont. Placelonholdelonr valuelon
  RelonquelonstTwelonelontId      = 1002

  // Nelongativelon Signals
  TwelonelontRelonport = 1101
  TwelonelontDontLikelon = 1102
  TwelonelontSelonelonFelonwelonr = 1103
  AccountBlock = 1104
  AccountMutelon = 1105

  // Aggrelongatelond Signals
  TwelonelontAggrelongation = 1201
  ProducelonrAggrelongation = 1202
} (pelonrsistelond='truelon', hasPelonrsonalData='truelon')

elonnum SimilarityelonnginelonTypelon {
  SimClustelonrsANN              = 1
  TwelonelontBaselondUselonrTwelonelontGraph    = 2
  TwelonelontBaselondTwHINANN          = 3
  Follow2VeloncANN               = 4 // ConsumelonrelonmbelonddingBaselondFollow2Velonc
  QIG                         = 5
  OfflinelonSimClustelonrsANN       = 6
  LookalikelonUTG_DelonPRelonCATelonD     = 7
  ProducelonrBaselondUselonrTwelonelontGraph = 8
  FrsUTG_DelonPRelonCATelonD           = 9
  RelonalGraphOonUTG_DelonPRelonCATelonD  = 10
  ConsumelonrelonmbelonddingBaselondTwHINANN = 11
  TwhinCollabFiltelonr           = 12
  TwicelonUTG_DelonPRelonCATelonD         = 13
  ConsumelonrelonmbelonddingBaselondTwoTowelonrANN = 14
  TwelonelontBaselondBelonTANN            = 15
  StpUTG_DelonPRelonCATelonD           = 16
  UTelonG                        = 17
  ROMR                        = 18
  ConsumelonrsBaselondUselonrTwelonelontGraph  = 19
  TwelonelontBaselondUselonrVidelonoGraph    = 20
  CelonrtoTopicTwelonelont             = 24
  ConsumelonrsBaselondUselonrAdGraph   = 25
  TwelonelontBaselondUselonrAdGraph       = 26
  SkitTfgTopicTwelonelont           = 27
  ConsumelonrBaselondWalsANN        = 28
  ProducelonrBaselondUselonrAdGraph    = 29
  SkitHighPreloncisionTopicTwelonelont = 30
  SkitIntelonrelonstBrowselonrTopicTwelonelont = 31
  SkitProducelonrBaselondTopicTwelonelont   = 32
  elonxplorelonTripOfflinelonSimClustelonrsTwelonelonts = 33
  DiffusionBaselondTwelonelont = 34
  ConsumelonrsBaselondUselonrVidelonoGraph  = 35

  // In nelontwork
  elonarlybirdReloncelonncyBaselondSimilarityelonnginelon = 21
  elonarlybirdModelonlBaselondSimilarityelonnginelon = 22
  elonarlybirdTelonnsorflowBaselondSimilarityelonnginelon = 23
  // Compositelon
  TwelonelontBaselondUnifielondSimilarityelonnginelon    = 1001
  ProducelonrBaselondUnifielondSimilarityelonnginelon = 1002
} (pelonrsistelond='truelon')
