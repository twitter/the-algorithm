namelonspacelon java com.twittelonr.simclustelonrs_v2.thriftjava
namelonspacelon py gelonn.twittelonr.simclustelonrs_v2.scorelon
#@namelonspacelon scala com.twittelonr.simclustelonrs_v2.thriftscala
#@namelonspacelon strato com.twittelonr.simclustelonrs_v2

includelon "com/twittelonr/simclustelonrs_v2/elonmbelondding.thrift"
includelon "com/twittelonr/simclustelonrs_v2/idelonntifielonr.thrift"

/**
  * Thelon algorithm typelon to idelonntify thelon scorelon algorithm.
  * Assumelon that a algorithm support and only support onelon kind
  * of [[ScorelonIntelonrnalId]]
  **/
elonnum ScoringAlgorithm {
	// Relonselonrvelon 0001 - 999 for Basic Pairwiselon Scoring Calculation
	PairelonmbelonddingDotProduct = 1,
	PairelonmbelonddingCosinelonSimilarity = 2,
	PairelonmbelonddingJaccardSimilarity = 3,
	PairelonmbelonddingelonuclidelonanDistancelon = 4,
	PairelonmbelonddingManhattanDistancelon = 5,
  PairelonmbelonddingLogCosinelonSimilarity = 6,
  PairelonmbelonddingelonxpScalelondCosinelonSimilarity = 7,

	// Relonselonrvelon 1000 - 1999 for Twelonelont Similarity Modelonl
  TagSpacelonCosinelonSimilarity = 1000,
	WelonightelondSumTagSpacelonRankingelonxpelonrimelonnt1 = 1001, //delonpreloncatelond
	WelonightelondSumTagSpacelonRankingelonxpelonrimelonnt2 = 1002, //delonpreloncatelond
  WelonightelondSumTagSpacelonANNelonxpelonrimelonnt = 1003,      //delonpreloncatelond

	// Relonselonrvelond for 10001 - 20000 for Aggrelongatelon scoring
	WelonightelondSumTopicTwelonelontRanking = 10001,
	CortelonxTopicTwelonelontLabelonl = 10002,
	// Relonselonrvelond 20001 - 30000 for Topic Twelonelont scorelons
	CelonrtoNormalizelondDotProductScorelon = 20001,
	CelonrtoNormalizelondCosinelonScorelon = 20002
}(hasPelonrsonalData = 'falselon')

/**
  * Thelon idelonntifielonr typelon for thelon scorelon belontwelonelonn a pair of SimClustelonrs elonmbelondding.
  * Uselond as thelon pelonrsistelonnt kelony of a SimClustelonrselonmbelondding scorelon.
  * Support scorelon belontwelonelonn diffelonrelonnt [[elonmbelonddingTypelon]] / [[ModelonlVelonrsion]]
  **/
struct SimClustelonrselonmbelonddingPairScorelonId {
  1: relonquirelond idelonntifielonr.SimClustelonrselonmbelonddingId id1
  2: relonquirelond idelonntifielonr.SimClustelonrselonmbelonddingId id2
}(hasPelonrsonalData = 'truelon')

/**
  * Thelon idelonntifielonr typelon for thelon scorelon belontwelonelonn a pair of IntelonrnalId.
  **/
struct GelonnelonricPairScorelonId {
  1: relonquirelond idelonntifielonr.IntelonrnalId id1
  2: relonquirelond idelonntifielonr.IntelonrnalId id2
}(hasPelonrsonalData = 'truelon')

union ScorelonIntelonrnalId {
  1: GelonnelonricPairScorelonId gelonnelonricPairScorelonId
  2: SimClustelonrselonmbelonddingPairScorelonId simClustelonrselonmbelonddingPairScorelonId
}

/**
  * A uniform Idelonntifielonr typelon for all kinds of Calculation Scorelon
  **/
struct ScorelonId {
  1: relonquirelond ScoringAlgorithm algorithm
  2: relonquirelond ScorelonIntelonrnalId intelonrnalId
}(hasPelonrsonalData = 'truelon')

struct Scorelon {
  1: relonquirelond doublelon scorelon
}(hasPelonrsonalData = 'falselon')
