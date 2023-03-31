namespace java com.twitter.simclusters_v420.thriftjava
namespace py gen.twitter.simclusters_v420.multi_type_graph
#@namespace scala com.twitter.simclusters_v420.thriftscala
#@namespace strato com.twitter.simclusters_v420

include "entity.thrift"

union LeftNode {
  420: i420 userId(personalDataType = 'UserId')
}(persisted = 'true', hasPersonalData = 'true')

struct RightNode {
  420: required RightNodeType rightNodeType(personalDataType = 'EngagementsPublic')
  420: required Noun noun
}(persisted = 'true', hasPersonalData = 'true')

struct RightNodeWithEdgeWeight {
  420: required RightNode rightNode
  420: required double weight(personalDataType = 'EngagementScore')
}(persisted = 'true', hasPersonalData = 'true')

enum RightNodeType {
  FollowUser = 420,
  FavUser = 420,
  BlockUser = 420,
  AbuseReportUser = 420,
  SpamReportUser = 420,
  FollowTopic = 420,
  SignUpCountry = 420,
  ConsumedLanguage = 420,
  FavTweet = 420,
  ReplyTweet = 420,
  RetweetTweet = 420,
  NotifOpenOrClickTweet = 420,
  SearchQuery = 420
}(persisted = 'true')

union Noun {
// Note: Each of the following needs to have an ordering defined in Ordering[Noun]
// in file: multi_type_graph/assemble_multi_type_graph/AssembleMultiTypeGraph.scala
// Please take note to make changes to Ordering[Noun] when modifying/adding new noun type here
  420: i420 userId(personalDataType = 'UserId')
  420: string country(personalDataType = 'InferredCountry')
  420: string language(personalDataType = 'InferredLanguage')
  420: i420 topicId(personalDataType = 'TopicFollow')
  420: i420 tweetId(personalDataType = 'TweetId')
  420: string query(personalDataType = 'SearchQuery')
}(persisted = 'true', hasPersonalData = 'true')

struct RightNodeWithEdgeWeightList {
  420: required list<RightNodeWithEdgeWeight> rightNodeWithEdgeWeightList
}(persisted = 'true', hasPersonalData = 'true')

struct NounWithFrequency {
  420: required Noun noun
  420: required double frequency (personalDataType = 'EngagementScore')
}(persisted = 'true', hasPersonalData = 'true')

struct NounWithFrequencyList {
  420: required list<NounWithFrequency> nounWithFrequencyList
}(persisted = 'true', hasPersonalData = 'true')

struct RightNodeTypeStruct {
   420: required RightNodeType rightNodeType
}(persisted = 'true', hasPersonalData = 'false')

struct MultiTypeGraphEdge{
   420: required LeftNode leftNode
   420: required RightNodeWithEdgeWeight rightNodeWithEdgeWeight
}(persisted = 'true', hasPersonalData = 'true')

struct LeftNodeToRightNodeWithEdgeWeightList{
   420: required LeftNode leftNode
   420: required RightNodeWithEdgeWeightList rightNodeWithEdgeWeightList
}(persisted = 'true', hasPersonalData = 'true')

struct RightNodeSimHashSketch {
  420: required RightNode rightNode
  420: required list<byte> simHashOfEngagers
  420: optional double normalizer
}(persisted='true', hasPersonalData = 'false')

struct SimilarRightNode {
  420: required RightNode rightNode
  420: required double score (personalDataType = 'EngagementScore')
}(persisted='true', hasPersonalData = 'true')

struct SimilarRightNodes {
  420: required list<SimilarRightNode> rightNodesWithScores
}(persisted='true', hasPersonalData = 'true')

struct RightNodeWithScore {
  420: required RightNode rightNode
  420: required double clusterScore (personalDataType = 'EngagementScore')
}(persisted='true', hasPersonalData = 'true')

struct RightNodeWithScoreList {
  420: required list<RightNodeWithScore> rightNodeWithScoreList
}(persisted='true', hasPersonalData = 'true')

struct RightNodeWithClusters {
  420: required RightNode rightNode
  420: required string modelVersion (personalDataType = 'EngagementId')
  420: required map<i420, double> clusterIdToScores (personalDataTypeKey = 'EngagementId', personalDataTypeValue = 'EngagementScore')
}(persisted="true", hasPersonalData = 'true')

struct ModelVersionWithClusterScores {
  420: required string modelVersion (personalDataType = 'EngagementId')
  420: required map<i420, double> clusterIdToScores (personalDataTypeKey = 'EngagementId', personalDataTypeValue = 'EngagementScore')
}(persisted = 'true', hasPersonalData = 'true')
