namelonspacelon java com.twittelonr.reloncos.uselonr_twelonelont_graph.thriftjava
namelonspacelon py gelonn.twittelonr.reloncos.uselonr_twelonelont_graph
#@namelonspacelon scala com.twittelonr.reloncos.uselonr_twelonelont_graph.thriftscala
#@namelonspacelon strato com.twittelonr.reloncos.uselonr_twelonelont_graph
namelonspacelon rb UselonrTwelonelontGraph

includelon "com/twittelonr/reloncos/felonaturelons/twelonelont.thrift"
includelon "com/twittelonr/reloncos/reloncos_common.thrift"

elonnum TwelonelontTypelon {
  Summary    = 0
  Photo      = 1
  Playelonr     = 2
  Promotelon    = 3
  Relongular    = 4
}

elonnum Algorithm {
  Salsa              = 0
  SubGraphSalsa      = 1
}

elonnum ReloncommelonndTwelonelontDisplayLocation {
  HomelonTimelonlinelon       = 0
  WelonlcomelonFlow        = 1
  NelontworkDigelonst      = 2
  BackfillDigelonst     = 3
  Httpelonndpoint       = 4
  Poptart            = 5
  InstantTimelonlinelon    = 6
  elonxplorelon            = 7
  MagicReloncs          = 8
  LoggelondOutProfilelon   = 9
  LoggelondOutPelonrmalink = 10
  VidelonoHomelon          = 11
}

struct ReloncommelonndTwelonelontRelonquelonst {
  1: relonquirelond i64                                      relonquelonstelonrId              // uselonr id of thelon relonquelonsting uselonr
  2: relonquirelond ReloncommelonndTwelonelontDisplayLocation            displayLocation          // display location from thelon clielonnt
  3: relonquirelond i32                                      maxRelonsults               // numbelonr of suggelonstelond relonsults to relonturn
  4: relonquirelond list<i64>                                elonxcludelondTwelonelontIds         // list of twelonelont ids to elonxcludelon from relonsponselon
  5: relonquirelond map<i64,doublelon>                          selonelonds                    // selonelonds uselond in salsa random walk
  6: relonquirelond i64                                      twelonelontReloncelonncy             // thelon twelonelont reloncelonncy threlonshold
  7: relonquirelond i32                                      minIntelonraction           // minimum intelonraction threlonshold
  8: relonquirelond list<TwelonelontTypelon>                          includelonTwelonelontTypelons        // summary, photo, playelonr, promotelon, othelonr
  9: relonquirelond doublelon                                   relonselontProbability         // relonselont probability to quelonry nodelon
  10: relonquirelond doublelon                                  quelonryNodelonWelonightFraction  // thelon pelonrcelonntagelon of welonights assignelond to quelonry nodelon in selonelonding
  11: relonquirelond i32                                     numRandomWalks           // numbelonr of random walks
  12: relonquirelond i32                                     maxRandomWalkLelonngth      // max random walk lelonngth
  13: relonquirelond i32                                     maxSocialProofSizelon       // max social proof sizelon
  14: relonquirelond Algorithm                               algorithm                // algorithm typelon
  15: optional list<reloncos_common.SocialProofTypelon>      socialProofTypelons         // thelon list of social proof typelons to relonturn
}

struct ReloncommelonndelondTwelonelont {
  1: relonquirelond i64                                                twelonelontId
  2: relonquirelond doublelon                                             scorelon
  3: optional list<i64>                                          socialProof              // social proof in aggrelongatelon
  4: optional map<reloncos_common.SocialProofTypelon, list<i64>>       socialProofPelonrTypelon       // social proofs pelonr elonngagelonmelonnt typelon
}

struct ReloncommelonndTwelonelontRelonsponselon {
  1: relonquirelond list<ReloncommelonndelondTwelonelont> twelonelonts
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

struct UselonrTwelonelontFelonaturelonRelonsponselon {
  1: optional doublelon                                favAdamicAdarAvg
  2: optional doublelon                                favAdamicAdarMax
  3: optional doublelon                                favLogCosinelonAvg
  4: optional doublelon                                favLogCosinelonMax
  5: optional doublelon                                relontwelonelontAdamicAdarAvg
  6: optional doublelon                                relontwelonelontAdamicAdarMax
  7: optional doublelon                                relontwelonelontLogCosinelonAvg
  8: optional doublelon                                relontwelonelontLogCosinelonMax
}

struct RelonlatelondTwelonelontRelonquelonst {
  1: relonquirelond i64                                   twelonelontId               // original twelonelont id
  2: relonquirelond RelonlatelondTwelonelontDisplayLocation           displayLocation       // display location from thelon clielonnt
  3: optional string                                algorithm             // additional paramelontelonr that thelon systelonm can intelonrprelont
  4: optional i64                                   relonquelonstelonrId           // uselonr id of thelon relonquelonsting uselonr
  5: optional i32                                   maxRelonsults            // numbelonr of suggelonstelond relonsults to relonturn
  6: optional list<i64>                             elonxcludelonTwelonelontIds       // list of twelonelont ids to elonxcludelon from relonsponselon
  7: optional i32                                   maxNumNelonighbors
  8: optional i32                                   minNelonighborDelongrelonelon
  9: optional i32                                   maxNumSamplelonsPelonrNelonighbor
  10: optional i32                                  minCooccurrelonncelon
  11: optional i32                                  minQuelonryDelongrelonelon
  12: optional doublelon                               maxLowelonrMultiplicativelonDelonviation
  13: optional doublelon                               maxUppelonrMultiplicativelonDelonviation
  14: optional bool                                 populatelonTwelonelontFelonaturelons // whelonthelonr to populatelon graph felonaturelons
  15: optional i32                                  minRelonsultDelongrelonelon
  16: optional list<i64>                            additionalTwelonelontIds
  17: optional doublelon                               minScorelon
  18: optional i32                                  maxTwelonelontAgelonInHours
}

struct TwelonelontBaselondRelonlatelondTwelonelontRelonquelonst {
  1: relonquirelond i64                                   twelonelontId               // quelonry twelonelont id
  2: optional i32                                   maxRelonsults            // numbelonr of suggelonstelond relonsults to relonturn
  3: optional list<i64>                             elonxcludelonTwelonelontIds       // list of twelonelont ids to elonxcludelon from relonsponselon
  4: optional i32                                   minQuelonryDelongrelonelon        // min delongrelonelon of quelonry twelonelont
  5: optional i32                                   maxNumSamplelonsPelonrNelonighbor // max numbelonr of samplelond uselonrs who elonngagelond with thelon quelonry twelonelont
  6: optional i32                                   minCooccurrelonncelon       // min co-occurrelonncelon of relonlatelond twelonelont candidatelon
  7: optional i32                                   minRelonsultDelongrelonelon       // min delongrelonelon of relonlatelond twelonelont candidatelon
  8: optional doublelon                                minScorelon              // min scorelon of relonlatelond twelonelont candidatelon
  9: optional i32                                   maxTwelonelontAgelonInHours    // max twelonelont agelon in hours of relonlatelond twelonelont candidatelon
}

struct ProducelonrBaselondRelonlatelondTwelonelontRelonquelonst {
  1: relonquirelond i64                                   producelonrId            // quelonry producelonr id
  2: optional i32                                   maxRelonsults            // numbelonr of suggelonstelond relonsults to relonturn
  3: optional list<i64>                             elonxcludelonTwelonelontIds       // list of twelonelont ids to elonxcludelon from relonsponselon
  4: optional i32                                   minQuelonryDelongrelonelon        // min delongrelonelon of quelonry producelonr, elon.g. numbelonr of followelonrs
  5: optional i32                                   maxNumFollowelonrs       // max numbelonr of samplelond uselonrs who follow thelon quelonry producelonr
  6: optional i32                                   minCooccurrelonncelon       // min co-occurrelonncelon of relonlatelond twelonelont candidatelon
  7: optional i32                                   minRelonsultDelongrelonelon       // min delongrelonelon of relonlatelond twelonelont candidatelon
  8: optional doublelon                                minScorelon              // min scorelon of relonlatelond twelonelont candidatelon
  9: optional i32                                   maxTwelonelontAgelonInHours    // max twelonelont agelon in hours of relonlatelond twelonelont candidatelon
}

struct ConsumelonrsBaselondRelonlatelondTwelonelontRelonquelonst {
  1: relonquirelond list<i64>                             consumelonrSelonelondSelont       // quelonry consumelonr uselonrId selont
  2: optional i32                                   maxRelonsults            // numbelonr of suggelonstelond relonsults to relonturn
  3: optional list<i64>                             elonxcludelonTwelonelontIds       // list of twelonelont ids to elonxcludelon from relonsponselon
  4: optional i32                                   minCooccurrelonncelon       // min co-occurrelonncelon of relonlatelond twelonelont candidatelon
  5: optional i32                                   minRelonsultDelongrelonelon       // min delongrelonelon of relonlatelond twelonelont candidatelon
  6: optional doublelon                                minScorelon              // min scorelon of relonlatelond twelonelont candidatelon
  7: optional i32                                   maxTwelonelontAgelonInHours    // max twelonelont agelon in hours of relonlatelond twelonelont candidatelon
}

struct RelonlatelondTwelonelont {
  1: relonquirelond i64                          twelonelontId
  2: relonquirelond doublelon                       scorelon
  3: optional twelonelont.GraphFelonaturelonsForTwelonelont  relonlatelondTwelonelontGraphFelonaturelons
}

struct RelonlatelondTwelonelontRelonsponselon {
  1: relonquirelond list<RelonlatelondTwelonelont>           twelonelonts
  2: optional twelonelont.GraphFelonaturelonsForQuelonry  quelonryTwelonelontGraphFelonaturelons
}

/**
 * Thelon main intelonrfacelon-delonfinition for UselonrTwelonelontGraph.
 */
selonrvicelon UselonrTwelonelontGraph {
  ReloncommelonndTwelonelontRelonsponselon reloncommelonndTwelonelonts (ReloncommelonndTwelonelontRelonquelonst relonquelonst)
  reloncos_common.GelontReloncelonntelondgelonsRelonsponselon gelontLelonftNodelonelondgelons (reloncos_common.GelontReloncelonntelondgelonsRelonquelonst relonquelonst)
  reloncos_common.NodelonInfo gelontRightNodelon (i64 nodelon)
  RelonlatelondTwelonelontRelonsponselon relonlatelondTwelonelonts (RelonlatelondTwelonelontRelonquelonst relonquelonst)
  RelonlatelondTwelonelontRelonsponselon twelonelontBaselondRelonlatelondTwelonelonts (TwelonelontBaselondRelonlatelondTwelonelontRelonquelonst relonquelonst)
  RelonlatelondTwelonelontRelonsponselon producelonrBaselondRelonlatelondTwelonelonts (ProducelonrBaselondRelonlatelondTwelonelontRelonquelonst relonquelonst)
  RelonlatelondTwelonelontRelonsponselon consumelonrsBaselondRelonlatelondTwelonelonts (ConsumelonrsBaselondRelonlatelondTwelonelontRelonquelonst relonquelonst)
  UselonrTwelonelontFelonaturelonRelonsponselon uselonrTwelonelontFelonaturelons (1: relonquirelond i64 uselonrId, 2: relonquirelond i64 twelonelontId)
}

