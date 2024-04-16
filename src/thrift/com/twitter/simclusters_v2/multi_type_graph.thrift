na space java com.tw ter.s mclusters_v2.thr ftjava
na space py gen.tw ter.s mclusters_v2.mult _type_graph
#@na space scala com.tw ter.s mclusters_v2.thr ftscala
#@na space strato com.tw ter.s mclusters_v2

 nclude "ent y.thr ft"

un on LeftNode {
  1:  64 user d(personalDataType = 'User d')
}(pers sted = 'true', hasPersonalData = 'true')

struct R ghtNode {
  1: requ red R ghtNodeType r ghtNodeType(personalDataType = 'Engage ntsPubl c')
  2: requ red Noun noun
}(pers sted = 'true', hasPersonalData = 'true')

struct R ghtNodeW hEdge  ght {
  1: requ red R ghtNode r ghtNode
  2: requ red double   ght(personalDataType = 'Engage ntScore')
}(pers sted = 'true', hasPersonalData = 'true')

enum R ghtNodeType {
  FollowUser = 1,
  FavUser = 2,
  BlockUser = 3,
  AbuseReportUser = 4,
  SpamReportUser = 5,
  FollowTop c = 6,
  S gnUpCountry = 7,
  Consu dLanguage = 8,
  FavT et = 9,
  ReplyT et = 10,
  Ret etT et = 11,
  Not fOpenOrCl ckT et = 12,
  SearchQuery = 13
}(pers sted = 'true')

un on Noun {
// Note: Each of t  follow ng needs to have an order ng def ned  n Order ng[Noun]
//  n f le: mult _type_graph/assemble_mult _type_graph/AssembleMult TypeGraph.scala
// Please take note to make changes to Order ng[Noun] w n mod fy ng/add ng new noun type  re
  1:  64 user d(personalDataType = 'User d')
  2: str ng country(personalDataType = ' nferredCountry')
  3: str ng language(personalDataType = ' nferredLanguage')
  4:  64 top c d(personalDataType = 'Top cFollow')
  5:  64 t et d(personalDataType = 'T et d')
  6: str ng query(personalDataType = 'SearchQuery')
}(pers sted = 'true', hasPersonalData = 'true')

struct R ghtNodeW hEdge  ghtL st {
  1: requ red l st<R ghtNodeW hEdge  ght> r ghtNodeW hEdge  ghtL st
}(pers sted = 'true', hasPersonalData = 'true')

struct NounW hFrequency {
  1: requ red Noun noun
  2: requ red double frequency (personalDataType = 'Engage ntScore')
}(pers sted = 'true', hasPersonalData = 'true')

struct NounW hFrequencyL st {
  1: requ red l st<NounW hFrequency> nounW hFrequencyL st
}(pers sted = 'true', hasPersonalData = 'true')

struct R ghtNodeTypeStruct {
   1: requ red R ghtNodeType r ghtNodeType
}(pers sted = 'true', hasPersonalData = 'false')

struct Mult TypeGraphEdge{
   1: requ red LeftNode leftNode
   2: requ red R ghtNodeW hEdge  ght r ghtNodeW hEdge  ght
}(pers sted = 'true', hasPersonalData = 'true')

struct LeftNodeToR ghtNodeW hEdge  ghtL st{
   1: requ red LeftNode leftNode
   2: requ red R ghtNodeW hEdge  ghtL st r ghtNodeW hEdge  ghtL st
}(pers sted = 'true', hasPersonalData = 'true')

struct R ghtNodeS mHashSketch {
  1: requ red R ghtNode r ghtNode
  2: requ red l st<byte> s mHashOfEngagers
  3: opt onal double normal zer
}(pers sted='true', hasPersonalData = 'false')

struct S m larR ghtNode {
  1: requ red R ghtNode r ghtNode
  2: requ red double score (personalDataType = 'Engage ntScore')
}(pers sted='true', hasPersonalData = 'true')

struct S m larR ghtNodes {
  1: requ red l st<S m larR ghtNode> r ghtNodesW hScores
}(pers sted='true', hasPersonalData = 'true')

struct R ghtNodeW hScore {
  1: requ red R ghtNode r ghtNode
  2: requ red double clusterScore (personalDataType = 'Engage ntScore')
}(pers sted='true', hasPersonalData = 'true')

struct R ghtNodeW hScoreL st {
  1: requ red l st<R ghtNodeW hScore> r ghtNodeW hScoreL st
}(pers sted='true', hasPersonalData = 'true')

struct R ghtNodeW hClusters {
  1: requ red R ghtNode r ghtNode
  2: requ red str ng modelVers on (personalDataType = 'Engage nt d')
  3: requ red map< 32, double> cluster dToScores (personalDataTypeKey = 'Engage nt d', personalDataTypeValue = 'Engage ntScore')
}(pers sted="true", hasPersonalData = 'true')

struct ModelVers onW hClusterScores {
  1: requ red str ng modelVers on (personalDataType = 'Engage nt d')
  2: requ red map< 32, double> cluster dToScores (personalDataTypeKey = 'Engage nt d', personalDataTypeValue = 'Engage ntScore')
}(pers sted = 'true', hasPersonalData = 'true')
