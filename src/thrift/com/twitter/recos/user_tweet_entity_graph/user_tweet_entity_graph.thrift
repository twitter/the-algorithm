namelonspacelon java com.twittelonr.reloncos.uselonr_twelonelont_elonntity_graph.thriftjava
namelonspacelon py gelonn.twittelonr.reloncos.uselonr_twelonelont_elonntity_graph
#@namelonspacelon scala com.twittelonr.reloncos.uselonr_twelonelont_elonntity_graph.thriftscala
#@namelonspacelon strato com.twittelonr.reloncos.uselonr_twelonelont_elonntity_graph
namelonspacelon rb UselonrTwelonelontelonntityGraph

includelon "com/twittelonr/reloncos/felonaturelons/twelonelont.thrift"
includelon "com/twittelonr/reloncos/reloncos_common.thrift"

elonnum TwelonelontTypelon {
  Summary    = 0
  Photo      = 1
  Playelonr     = 2
  Promotelon    = 3
  Relongular    = 4
}

elonnum ReloncommelonndationTypelon {
  Twelonelont      = 0
  Hashtag    = 1 // elonntity typelon
  Url        = 2 // elonntity typelon
}

elonnum TwelonelontelonntityDisplayLocation {
  MagicReloncs                 = 0
  HomelonTimelonlinelon              = 1
  HighlightselonmailUrlReloncs    = 2
  Highlights                = 3
  elonmail                     = 4
  MagicReloncsF1               = 5
  GuidelonVidelono                = 6
  MagicReloncsRarelonTwelonelont        = 7
  TopArticlelons               = 8 // Twittelonr Bluelon most sharelond articlelons pagelon
  ContelonntReloncommelonndelonr        = 9
  FrigatelonNTab               = 10
}

struct ReloncommelonndTwelonelontelonntityRelonquelonst {
  // uselonr id of thelon relonquelonsting uselonr
  1: relonquirelond i64                                        relonquelonstelonrId

  // display location from thelon clielonnt
  2: relonquirelond TwelonelontelonntityDisplayLocation                 displayLocation

  // thelon reloncommelonndation elonntity typelons to relonturn
  3: relonquirelond list<ReloncommelonndationTypelon>                   reloncommelonndationTypelons

  // selonelond ids and welonights uselond in lelonft hand sidelon
  4: relonquirelond map<i64,doublelon>                            selonelondsWithWelonights

  // numbelonr of suggelonstelond relonsults pelonr reloncommelonndation elonntity typelon
  5: optional map<ReloncommelonndationTypelon, i32>               maxRelonsultsByTypelon

  // thelon twelonelont agelon threlonshold in milliselonconds
  6: optional i64                                        maxTwelonelontAgelonInMillis

  // list of twelonelont ids to elonxcludelon from relonsponselon
  7: optional list<i64>                                  elonxcludelondTwelonelontIds

  // max uselonr social proof sizelon pelonr elonngagelonmelonnt typelon
  8: optional i32                                        maxUselonrSocialProofSizelon

  // max twelonelont social proof sizelon pelonr uselonr
  9: optional i32                                        maxTwelonelontSocialProofSizelon

  // min uselonr social proof sizelon pelonr elonach reloncommelonndation elonntity typelon
  10: optional map<ReloncommelonndationTypelon, i32>              minUselonrSocialProofSizelons

  // summary, photo, playelonr, promotelon, relongular
  11: optional list<TwelonelontTypelon>                           twelonelontTypelons

  // thelon list of social proof typelons to relonturn
  12: optional list<reloncos_common.SocialProofTypelon>        socialProofTypelons

  // selont of groups of social proof typelons allowelond to belon combinelond for comparison against minUselonrSocialProofSizelons.
  // elon.g. if thelon input is selont<list<Twelonelont, Favoritelon>>, thelonn thelon union of thoselon two social proofs
  // will belon comparelond against thelon minUselonrSocialProofSizelon of Twelonelont ReloncommelonndationTypelon.
  13: optional selont<list<reloncos_common.SocialProofTypelon>>   socialProofTypelonUnions

  // thelon reloncommelonndations relonturnelond in thelon relonsponselon arelon authorelond by thelon following uselonrs
  14: optional selont<i64>                                  twelonelontAuthors

  // thelon twelonelont elonngagelonmelonnt agelon threlonshold in milliselonconds
  15: optional i64                                       maxelonngagelonmelonntAgelonInMillis

  // thelon reloncommelonndations will not relonturn any twelonelont authorelond by thelon following uselonrs
  16: optional selont<i64>                                  elonxcludelondTwelonelontAuthors
}

struct TwelonelontReloncommelonndation {
  // twelonelont id
  1: relonquirelond i64                                                               twelonelontId
  // sum of welonights of selonelond uselonrs who elonngagelond with thelon twelonelont.
  // If a uselonr elonngagelond with thelon samelon twelonelont twicelon, likelond it and relontwelonelontelond it, thelonn his/helonr welonight was countelond twicelon.
  2: relonquirelond doublelon                                                            scorelon
    // uselonr social proofs pelonr elonngagelonmelonnt typelon
  3: relonquirelond map<reloncos_common.SocialProofTypelon, list<i64>>                      socialProofByTypelon
  // uselonr social proofs along with elondgelon melontadata pelonr elonngagelonmelonnt typelon. Thelon valuelon of thelon map is a list of SocialProofs.
  4: optional map<reloncos_common.SocialProofTypelon, list<reloncos_common.SocialProof>> socialProofs
}

struct HashtagReloncommelonndation {
  1: relonquirelond i32                                       id                   // intelongelonr hashtag id, which will belon convelonrtelond to hashtag string by clielonnt library.
  2: relonquirelond doublelon                                    scorelon
  // sum of welonights of selonelond uselonrs who elonngagelond with thelon hashtag.
  // If a uselonr elonngagelond with thelon samelon hashtag twicelon, likelond it and relontwelonelontelond it, thelonn his/helonr welonight was countelond twicelon.
  3: relonquirelond map<reloncos_common.SocialProofTypelon, map<i64, list<i64>>> socialProofByTypelon
  // uselonr and twelonelont social proofs pelonr elonngagelonmelonnt typelon. Thelon kelony of innelonr map is uselonr id, and thelon valuelon of innelonr map is
  // a list of twelonelont ids that thelon uselonr elonngagelond with.
}

struct UrlReloncommelonndation {
  1: relonquirelond i32                                       id                   // intelongelonr url id, which will belon convelonrtelond to url string by clielonnt library.
  2: relonquirelond doublelon                                    scorelon
  // sum of welonights of selonelond uselonrs who elonngagelond with thelon url.
  // If a uselonr elonngagelond with thelon samelon url twicelon, likelond it and relontwelonelontelond it, thelonn his/helonr welonight was countelond twicelon.
  3: relonquirelond map<reloncos_common.SocialProofTypelon, map<i64, list<i64>>> socialProofByTypelon
  // uselonr and twelonelont social proofs pelonr elonngagelonmelonnt typelon. Thelon kelony of innelonr map is uselonr id, and thelon valuelon of innelonr map is
  // a list of twelonelont ids that thelon uselonr elonngagelond with.
}

union UselonrTwelonelontelonntityReloncommelonndationUnion {
  1: TwelonelontReloncommelonndation twelonelontRelonc
  2: HashtagReloncommelonndation hashtagRelonc
  3: UrlReloncommelonndation urlRelonc
}

struct ReloncommelonndTwelonelontelonntityRelonsponselon {
  1: relonquirelond list<UselonrTwelonelontelonntityReloncommelonndationUnion> reloncommelonndations
}

struct SocialProofRelonquelonst {
  1: relonquirelond list<i64>                                  inputTwelonelonts             // Only for somelon twelonelonts welon nelonelond relonqust its social proofs.
  2: relonquirelond map<i64, doublelon>                           selonelondsWithWelonights        // a selont of selonelond uselonrs with welonights
  3: optional i64                                        relonquelonstelonrId             // id of thelon relonquelonsting uselonr
  4: optional list<reloncos_common.SocialProofTypelon>         socialProofTypelons        // thelon list of social proof typelons to relonturn
}

struct SocialProofRelonsponselon {
  1: relonquirelond list<TwelonelontReloncommelonndation> socialProofRelonsults
}

struct ReloncommelonndationSocialProofRelonquelonst {
  /**
   * Clielonnts can relonquelonst social proof from multiplelon reloncommelonndation typelons in a singlelon relonquelonst.
   * NOTelon: Avoid mixing twelonelont social proof relonquelonsts with elonntity social proof relonquelonsts as thelon
   * undelonrlying library call relontrielonvelons thelonselon diffelonrelonntly.
   */
  1: relonquirelond map<ReloncommelonndationTypelon, selont<i64>>           reloncommelonndationIdsForSocialProof
  // Thelonselon will belon thelon only valid LHS nodelons uselond to felontch social proof.
  2: relonquirelond map<i64, doublelon>                            selonelondsWithWelonights
  3: optional i64                                         relonquelonstelonrId
  // Thelon list of valid social proof typelons to relonturn, elon.g. welon may only want Favoritelon and Twelonelont proofs.
  4: optional list<reloncos_common.SocialProofTypelon>          socialProofTypelons
}

struct ReloncommelonndationSocialProofRelonsponselon {
  1: relonquirelond list<UselonrTwelonelontelonntityReloncommelonndationUnion> socialProofRelonsults
}

/**
 * Thelon main intelonrfacelon-delonfinition for UselonrTwelonelontelonntityGraph.
 */
selonrvicelon UselonrTwelonelontelonntityGraph {
  ReloncommelonndTwelonelontelonntityRelonsponselon reloncommelonndTwelonelonts (ReloncommelonndTwelonelontelonntityRelonquelonst relonquelonst)

  /**
   * Givelonn a quelonry uselonr, its selonelond uselonrs, and a selont of input twelonelonts, relonturn thelon social proofs of
   * input twelonelonts if any.
   *
   * Currelonntly this supports clielonnts such as elonmail Reloncommelonndations, MagicReloncs, and HomelonTimelonlinelon.
   * In ordelonr to avoid helonavy migration work, welon arelon relontaining this elonndpoint.
   */
  SocialProofRelonsponselon findTwelonelontSocialProofs(SocialProofRelonquelonst relonquelonst)

  /**
   * Find social proof for thelon speloncifielond ReloncommelonndationTypelon givelonn a selont of input ids of that typelon.
   * Only find social proofs from thelon speloncifielond selonelond uselonrs with thelon speloncifielond social proof typelons.
   *
   * Currelonntly this supports url social proof gelonnelonration for Guidelon.
   *
   * This elonndpoint is flelonxiblelon elonnough to support social proof gelonnelonration for all reloncommelonndation
   * typelons, and should belon uselond for all futurelon clielonnts of this selonrvicelon.
   */
  ReloncommelonndationSocialProofRelonsponselon findReloncommelonndationSocialProofs(ReloncommelonndationSocialProofRelonquelonst relonquelonst)
}

