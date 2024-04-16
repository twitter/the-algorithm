na space java com.tw ter.s mclusters_v2.thr ftjava
na space py gen.tw ter.s mclusters_v2.score
#@na space scala com.tw ter.s mclusters_v2.thr ftscala
#@na space strato com.tw ter.s mclusters_v2

 nclude "com/tw ter/s mclusters_v2/embedd ng.thr ft"
 nclude "com/tw ter/s mclusters_v2/ dent f er.thr ft"

/**
  * T  algor hm type to  dent fy t  score algor hm.
  * Assu  that a algor hm support and only support one k nd
  * of [[Score nternal d]]
  **/
enum Scor ngAlgor hm {
	// Reserve 0001 - 999 for Bas c Pa rw se Scor ng Calculat on
	Pa rEmbedd ngDotProduct = 1,
	Pa rEmbedd ngCos neS m lar y = 2,
	Pa rEmbedd ngJaccardS m lar y = 3,
	Pa rEmbedd ngEucl deanD stance = 4,
	Pa rEmbedd ngManhattanD stance = 5,
  Pa rEmbedd ngLogCos neS m lar y = 6,
  Pa rEmbedd ngExpScaledCos neS m lar y = 7,

	// Reserve 1000 - 1999 for T et S m lar y Model
  TagSpaceCos neS m lar y = 1000,
	  ghtedSumTagSpaceRank ngExper  nt1 = 1001, //deprecated
	  ghtedSumTagSpaceRank ngExper  nt2 = 1002, //deprecated
    ghtedSumTagSpaceANNExper  nt = 1003,      //deprecated 

	// Reserved for 10001 - 20000 for Aggregate scor ng
	  ghtedSumTop cT etRank ng = 10001,
	CortexTop cT etLabel = 10002,
	// Reserved 20001 - 30000 for Top c T et scores 
	CertoNormal zedDotProductScore = 20001,
	CertoNormal zedCos neScore = 20002
}(hasPersonalData = 'false')

/**
  * T   dent f er type for t  score bet en a pa r of S mClusters Embedd ng.
  * Used as t  pers stent key of a S mClustersEmbedd ng score.
  * Support score bet en d fferent [[Embedd ngType]] / [[ModelVers on]]
  **/
struct S mClustersEmbedd ngPa rScore d {
  1: requ red  dent f er.S mClustersEmbedd ng d  d1
  2: requ red  dent f er.S mClustersEmbedd ng d  d2
}(hasPersonalData = 'true')

/**
  * T   dent f er type for t  score bet en a pa r of  nternal d.
  **/
struct Gener cPa rScore d {
  1: requ red  dent f er. nternal d  d1
  2: requ red  dent f er. nternal d  d2
}(hasPersonalData = 'true')

un on Score nternal d {
  1: Gener cPa rScore d gener cPa rScore d
  2: S mClustersEmbedd ngPa rScore d s mClustersEmbedd ngPa rScore d
}

/**
  * A un form  dent f er type for all k nds of Calculat on Score
  **/
struct Score d {
  1: requ red Scor ngAlgor hm algor hm
  2: requ red Score nternal d  nternal d
}(hasPersonalData = 'true')

struct Score {
  1: requ red double score
}(hasPersonalData = 'false')
