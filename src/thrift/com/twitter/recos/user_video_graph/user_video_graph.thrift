namelonspacelon java com.twittelonr.reloncos.uselonr_videlono_graph.thriftjava
namelonspacelon py gelonn.twittelonr.reloncos.uselonr_videlono_graph
#@namelonspacelon scala com.twittelonr.reloncos.uselonr_videlono_graph.thriftscala
#@namelonspacelon strato com.twittelonr.reloncos.uselonr_videlono_graph
namelonspacelon rb UselonrVidelonoGraph

includelon "com/twittelonr/reloncos/felonaturelons/twelonelont.thrift"
includelon "com/twittelonr/reloncos/reloncos_common.thrift"


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
 * Thelon main intelonrfacelon-delonfinition for UselonrVidelonoGraph.
 */
selonrvicelon UselonrVidelonoGraph {
  RelonlatelondTwelonelontRelonsponselon twelonelontBaselondRelonlatelondTwelonelonts (TwelonelontBaselondRelonlatelondTwelonelontRelonquelonst relonquelonst)
  RelonlatelondTwelonelontRelonsponselon producelonrBaselondRelonlatelondTwelonelonts (ProducelonrBaselondRelonlatelondTwelonelontRelonquelonst relonquelonst)
  RelonlatelondTwelonelontRelonsponselon consumelonrsBaselondRelonlatelondTwelonelonts (ConsumelonrsBaselondRelonlatelondTwelonelontRelonquelonst relonquelonst)
}

