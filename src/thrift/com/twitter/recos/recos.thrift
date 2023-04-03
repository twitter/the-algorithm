namelonspacelon java com.twittelonr.reloncos.thriftjava
#@namelonspacelon scala com.twittelonr.reloncos.thriftscala
namelonspacelon rb Reloncos

includelon "com/twittelonr/reloncos/felonaturelons/twelonelont.thrift"

elonnum ReloncommelonndTwelonelontDisplayLocation {
  HomelonTimelonlinelon       = 0
  Pelonelonk               = 1
  WelonlcomelonFlow        = 2
  NelontworkDigelonst      = 3
  BackfillDigelonst     = 4
  NelontworkDigelonstelonxp1  = 5
  NelontworkDigelonstelonxp2  = 6 // delonpreloncatelond
  NelontworkDigelonstelonxp3  = 7 // delonpreloncatelond
  Httpelonndpoint       = 8
  HomelonTimelonlinelon1      = 9
  HomelonTimelonlinelon2      = 10
  HomelonTimelonlinelon3      = 11
  HomelonTimelonlinelon4      = 12
  Poptart            = 13
  NelontworkDigelonstelonxp4  = 14
  NelontworkDigelonstelonxp5  = 15
  NelontworkDigelonstelonxp6  = 16
  NelontworkDigelonstelonxp7  = 17
  NelontworkDigelonstelonxp8  = 18
  NelontworkDigelonstelonxp9  = 19
  InstantTimelonlinelon1   = 20 // AB1 + whitelonlist
  InstantTimelonlinelon2   = 21 // AB1 + !whitelonlist
  InstantTimelonlinelon3   = 22 // AB2 + whitelonlist
  InstantTimelonlinelon4   = 23 // AB2 + !whitelonlist
  BackfillDigelonstActivelon  = 24 // delonpreloncatelond
  BackfillDigelonstDormant = 25 // delonpreloncatelond
  elonxplorelonUS             = 26 // delonpreloncatelond
  elonxplorelonBR             = 27 // delonpreloncatelond
  elonxplorelonIN             = 28 // delonpreloncatelond
  elonxplorelonelonS             = 29 // delonpreloncatelond
  elonxplorelonJP             = 30 // delonpreloncatelond
  MagicReloncs             = 31
  MagicReloncs1            = 32
  MagicReloncs2            = 33
  MagicReloncs3            = 34
  SMSDiscovelonr           = 35
  FastFollowelonr          = 36
  InstantTimelonlinelon5      = 37 // for instant timelonlinelon elonxpelonrimelonnt
  InstantTimelonlinelon6      = 38 // for instant timelonlinelon elonxpelonrimelonnt
  InstantTimelonlinelon7      = 39 // for instant timelonlinelon elonxpelonrimelonnt
  InstantTimelonlinelon8      = 40 // for instant timelonlinelon elonxpelonrimelonnt
  LoggelondOutProfilelon      = 41
  LoggelondOutPelonrmalink    = 42
  Poptart2              = 43
}

elonnum RelonlatelondTwelonelontDisplayLocation {
  Pelonrmalink       = 0
  Pelonrmalink1      = 1
  MobilelonPelonrmalink = 2
  Pelonrmalink3      = 3
  Pelonrmalink4      = 4
  RelonlatelondTwelonelonts   = 5
  RelonlatelondTwelonelonts1  = 6
  RelonlatelondTwelonelonts2  = 7
  RelonlatelondTwelonelonts3  = 8
  RelonlatelondTwelonelonts4  = 9
  LoggelondOutProfilelon = 10
  LoggelondOutPelonrmalink = 11
}

elonnum DDGBuckelont {
  Control           = 0
  Trelonatmelonnt         = 1
  Nonelon              = 2
}

struct ReloncommelonndTwelonelontRelonquelonst {
  1: relonquirelond i64                                   relonquelonstelonrId           // uselonr id of thelon relonquelonsting uselonr
  2: relonquirelond ReloncommelonndTwelonelontDisplayLocation         displayLocation       // display location from thelon clielonnt
  3: optional i64                                   clielonntId              // twittelonr api clielonnt id
  4: optional i32                                   maxRelonsults            // numbelonr of suggelonstelond relonsults to relonturn
  5: optional list<i64>                             elonxcludelondTwelonelontIds      // list of twelonelont ids to elonxcludelon from relonsponselon
  6: optional list<i64>                             elonxcludelondAuthorIds     // list of author ids to elonxcludelon from relonsponselon
  7: optional i64                                   guelonstId               // guelonstId
  8: optional string                                languagelonCodelon          // Languagelon codelon
  9: optional string                                countryCodelon           // Country codelon
  10: optional string                               ipAddrelonss             // ip addrelonss of thelon uselonr
  11: optional string                               delonvicelonId              // udid/uuid of delonvicelon
  12: optional bool                                 populatelonTwelonelontFelonaturelons // whelonthelonr to populatelon twelonelont felonaturelons. ReloncommelonndelondTwelonelont.twelonelontFelonaturelons in thelon relonsponselon will only belon populatelond if this is selont.
}

struct Buckelont {
  1: relonquirelond string                                elonxpelonrimelonntNamelon        // namelon of elonxpelonrimelonnt (or not). elonxpelonrimelonnt could belon production or whatelonvelonr fits
  2: relonquirelond string                                buckelont                // namelon of buckelont (may or may not belon a DDG buckelont, elon.g., production)
}

struct RelonlatelondTwelonelontRelonquelonst {
  1: relonquirelond i64                                   twelonelontId               // original twelonelont id
  2: relonquirelond RelonlatelondTwelonelontDisplayLocation           displayLocation       // display location from thelon clielonnt
  3: optional i64                                   clielonntId              // twittelonr api clielonnt id
  4: optional i64                                   relonquelonstelonrId           // uselonr id of thelon relonquelonsting uselonr
  5: optional i32                                   maxRelonsults            // numbelonr of suggelonstelond relonsults to relonturn
  6: optional list<i64>                             elonxcludelonTwelonelontIds       // list of twelonelont ids to elonxcludelon from relonsponselon
  7: optional list<i64>                             elonxcludelondAuthorIds     // list of author ids to elonxcludelon from relonsponselon
  8: optional i64                                   guelonstId               // guelonstId
  9: optional string                                languagelonCodelon          // Languagelon codelon
  10: optional string                               countryCodelon           // Country codelon
  11: optional string                               ipAddrelonss             // ip addrelonss of thelon uselonr
  12: optional string                               delonvicelonId              // udid/uuid of delonvicelon
  13: optional string                               uselonrAgelonnt             // uselonrAgelonnt of thelon relonquelonsting uselonr
}

elonnum SocialProofTypelon {
  FollowelondBy = 1,
  FavoritelondBy = 2,
  RelontwelonelontelondBy = 3,
  SimilarTo = 4,
  RelonSelonRVelonD_2 = 5,
  RelonSelonRVelonD_3 = 6,
  RelonSelonRVelonD_4 = 7,
  RelonSelonRVelonD_5 = 8,
  RelonSelonRVelonD_6 = 9,
  RelonSelonRVelonD_7 = 10
}

elonnum Algorithm {
  Salsa = 1,
  PastelonmailClicks = 2,
  SimilarToelonmailClicks = 3,
  PastClielonntelonvelonntClicks = 4,
  VitNelonws = 5,
  StrongTielonScoring = 6,
  PollsFromGraph = 7,
  PollsBaselondOnGelono = 8,
  RelonSelonRVelonD_9 = 9,
  RelonSelonRVelonD_10 = 10,
  RelonSelonRVelonD_11 = 11,
}

struct ReloncommelonndelondTwelonelont {
  1: relonquirelond i64                            twelonelontId
  2: relonquirelond i64                            authorId
  3: relonquirelond list<i64>                      socialProof
  4: relonquirelond string                         felonelondbackTokelonn
  5: optional list<i64>                      favBy          // optionally providelon a list of uselonrs who fav'elond thelon twelonelont if elonxist
  6: optional twelonelont.ReloncommelonndelondTwelonelontFelonaturelons twelonelontFelonaturelons  // thelon felonaturelons of a reloncommelonndelond twelonelont
  7: optional SocialProofTypelon                socialProofTypelon // typelon of social proof. favBy should belon delonpreloncatelond soon
  8: optional string                         socialProofOvelonrridelon // should belon selont only for DDGs, for elonn-only elonxpelonrimelonnts. SocialProofTypelon is ignorelond whelonn this fielonld is selont
  9: optional Algorithm                      algorithm // algorithm uselond
  10: optional doublelon                        scorelon     // scorelon
  11: optional bool                          isFollowingAuthor // truelon if thelon targelont uselonr follows thelon author of thelon twelonelont
}

struct RelonlatelondTwelonelont {
  1: relonquirelond i64                  twelonelontId
  2: relonquirelond i64                  authorId
  3: relonquirelond doublelon               scorelon
  4: relonquirelond string               felonelondbackTokelonn
}

struct ReloncommelonndTwelonelontRelonsponselon {
  1: relonquirelond list<ReloncommelonndelondTwelonelont> twelonelonts
  2: optional DDGBuckelont              buckelont                // delonpreloncatelond
  3: optional Buckelont                 assignelondBuckelont        // for clielonnt-sidelon elonxpelonrimelonntation
}

struct RelonlatelondTwelonelontRelonsponselon {
  1: relonquirelond list<RelonlatelondTwelonelont>   twelonelonts                                 // a list of relonlatelond twelonelonts
  2: optional Buckelont               assignelondBuckelont                         // thelon buckelont uselond for trelonatmelonnt
}

/**
 * Thelon main intelonrfacelon-delonfinition for Reloncos.
 */
selonrvicelon Reloncos {
  ReloncommelonndTwelonelontRelonsponselon reloncommelonndTwelonelonts  (ReloncommelonndTwelonelontRelonquelonst relonquelonst)
  RelonlatelondTwelonelontRelonsponselon relonlatelondTwelonelonts  (RelonlatelondTwelonelontRelonquelonst relonquelonst)
}
