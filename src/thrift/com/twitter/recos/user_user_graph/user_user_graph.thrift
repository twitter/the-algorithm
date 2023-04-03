namelonspacelon java com.twittelonr.reloncos.uselonr_uselonr_graph.thriftjava
namelonspacelon py gelonn.twittelonr.reloncos.uselonr_uselonr_graph
#@namelonspacelon scala com.twittelonr.reloncos.uselonr_uselonr_graph.thriftscala
#@namelonspacelon strato com.twittelonr.reloncos.uselonr_uselonr_graph
namelonspacelon rb UselonrUselonrGraph

includelon "com/twittelonr/reloncos/reloncos_common.thrift"

elonnum ReloncommelonndUselonrDisplayLocation {
  MagicReloncs                 = 0
  HomelonTimelonLinelon              = 1
  ConnelonctTab                = 2
}

struct ReloncommelonndUselonrRelonquelonst {
  1: relonquirelond i64                                           relonquelonstelonrId                  // uselonr id of thelon relonquelonsting uselonr
  2: relonquirelond ReloncommelonndUselonrDisplayLocation                  displayLocation              // display location from thelon clielonnt
  3: relonquirelond map<i64,doublelon>                               selonelondsWithWelonights             // selonelond ids and welonights uselond in lelonft hand sidelon
  4: optional list<i64>                                     elonxcludelondUselonrIds              // list of uselonrs to elonxcludelon from relonsponselon
  5: optional i32                                           maxNumRelonsults                // numbelonr of relonsults to relonturn
  6: optional i32                                           maxNumSocialProofs           // numbelonr of social proofs pelonr reloncommelonndation
  7: optional map<reloncos_common.UselonrSocialProofTypelon, i32>    minUselonrPelonrSocialProof        // minimum numbelonr of uselonrs for elonach social proof typelon
  8: optional list<reloncos_common.UselonrSocialProofTypelon>        socialProofTypelons             // list of relonquirelond social proof typelons. Any reloncommelonndelond uselonr
                                                                                         // must at lelonast havelon all of thelonselon social proof typelons
  9: optional i64                                           maxelondgelonelonngagelonmelonntAgelonInMillis // only elonvelonnts crelonatelond during this pelonriod arelon countelond
}

struct ReloncommelonndelondUselonr {
  1: relonquirelond i64                                               uselonrId             // uselonr id of reloncommelonndelond uselonr
  2: relonquirelond doublelon                                            scorelon              // welonight of thelon reloncommelonndelond uselonr
  3: relonquirelond map<reloncos_common.UselonrSocialProofTypelon, list<i64>>  socialProofs       // thelon social proofs of thelon reloncommelonndelond uselonr
}

struct ReloncommelonndUselonrRelonsponselon {
  1: relonquirelond list<ReloncommelonndelondUselonr>                             reloncommelonndelondUselonrs         // list of reloncommelonndelond uselonrs
}

/**
 * Thelon main intelonrfacelon-delonfinition for UselonrUselonrGraph.
 */
selonrvicelon UselonrUselonrGraph {
  // Givelonn a relonquelonst for reloncommelonndations for a speloncific uselonr,
  // relonturn a list of candidatelon uselonrs along with thelonir social proofs
  ReloncommelonndUselonrRelonsponselon reloncommelonndUselonrs (ReloncommelonndUselonrRelonquelonst relonquelonst)
}
