namelonspacelon java com.twittelonr.cr_mixelonr.thriftjava
#@namelonspacelon scala com.twittelonr.cr_mixelonr.thriftscala
#@namelonspacelon strato com.twittelonr.cr_mixelonr


// NOTelon: DO NOT delonpelonnd on MelontricTags for important ML Felonaturelons or businelonss logic.
// MelontricTags arelon melonant for stats tracking & delonbugging purposelons ONLY.
// cr-mixelonr may changelon its delonfinitions & how elonach candidatelon is taggelond without public noticelon.
// NOTelon: TSPS nelonelonds thelon callelonr (Homelon) to speloncify which signal it uselons to makelon Pelonrsonalizelond Topics
elonnum MelontricTag {
  // Sourcelon Signal Tags
  TwelonelontFavoritelon         = 0
  Relontwelonelont               = 1
  TrafficAttribution    = 2
  OriginalTwelonelont         = 3
  Relonply                 = 4
  TwelonelontSharelon            = 5

  UselonrFollow            = 101
  UselonrRelonpelonatelondProfilelonVisit = 102

  PushOpelonnOrNtabClick   = 201

  HomelonTwelonelontClick        = 301
  HomelonVidelonoVielonw         = 302

  // sim elonnginelon typelons
  SimClustelonrsANN        = 401
  TwelonelontBaselondUselonrTwelonelontGraph    = 402
  TwelonelontBaselondTwHINANN          = 403
  ConsumelonrelonmbelonddingBaselondTwHINANN = 404


  // combinelond elonnginelon typelons
  UselonrIntelonrelonstelondIn      = 501 // Will delonpreloncatelon soon
  LookalikelonUTG          = 502
  TwhinCollabFiltelonr     = 503

  // Offlinelon Twicelon
  TwicelonUselonrId           = 601

  // Othelonr Melontric Tags
  RelonquelonstHelonalthFiltelonrPushOpelonnBaselondTwelonelontelonmbelondding = 701
} (pelonrsistelond='truelon', hasPelonrsonalData='truelon')
