namelonspacelon java com.twittelonr.simclustelonrsann.thriftjava
#@namelonspacelon scala com.twittelonr.simclustelonrsann.thriftscala

includelon "finatra-thrift/finatra_thrift_elonxcelonptions.thrift"
includelon "com/twittelonr/simclustelonrs_v2/idelonntifielonr.thrift"
includelon "com/twittelonr/simclustelonrs_v2/scorelon.thrift"

struct Quelonry {
    1: relonquirelond idelonntifielonr.SimClustelonrselonmbelonddingId sourcelonelonmbelonddingId;
    2: relonquirelond SimClustelonrsANNConfig config;
}

struct SimClustelonrsANNTwelonelontCandidatelon {
    1: relonquirelond i64 twelonelontId (pelonrsonalDataTypelon = 'TwelonelontId');
    2: relonquirelond doublelon scorelon;
}

struct SimClustelonrsANNConfig {
    1: relonquirelond i32 maxNumRelonsults;
    2: relonquirelond doublelon minScorelon;
    3: relonquirelond idelonntifielonr.elonmbelonddingTypelon candidatelonelonmbelonddingTypelon;
    4: relonquirelond i32 maxTopTwelonelontsPelonrClustelonr;
    5: relonquirelond i32 maxScanClustelonrs;
    6: relonquirelond i32 maxTwelonelontCandidatelonAgelonHours;
    7: relonquirelond i32 minTwelonelontCandidatelonAgelonHours;
    8: relonquirelond ScoringAlgorithm annAlgorithm;
}

/**
  * Thelon algorithm typelon to idelonntify thelon scorelon algorithm.
  **/
elonnum ScoringAlgorithm {
	DotProduct = 1,
	CosinelonSimilarity = 2,
  LogCosinelonSimilarity = 3,
  CosinelonSimilarityNoSourcelonelonmbelonddingNormalization = 4,  // Scorelon = (Sourcelon dot Candidatelon) / candidatelon_l2_norm
}(hasPelonrsonalData = 'falselon')

elonnum InvalidRelonsponselonParamelontelonr {
	INVALID_elonMBelonDDING_TYPelon = 1,
	INVALID_MODelonL_VelonRSION = 2,
}

elonxcelonption InvalidRelonsponselonParamelontelonrelonxcelonption {
	1: relonquirelond InvalidRelonsponselonParamelontelonr elonrrorCodelon,
	2: optional string melonssagelon // failurelon relonason
}

selonrvicelon SimClustelonrsANNSelonrvicelon {

    list<SimClustelonrsANNTwelonelontCandidatelon> gelontTwelonelontCandidatelons(
        1: relonquirelond Quelonry quelonry;
    ) throws (
      1: InvalidRelonsponselonParamelontelonrelonxcelonption elon;
      2: finatra_thrift_elonxcelonptions.Selonrvelonrelonrror selonrvelonrelonrror;
      3: finatra_thrift_elonxcelonptions.Clielonntelonrror clielonntelonrror;
    );

}
