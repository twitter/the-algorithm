namespace java com.twitter.ann.knn.thriftjava
#@namespace scala com.twitter.ann.knn.thriftscala
namespace py gen.twitter.ann.knn

include "com/twitter/ml/featurestore/entity.thrift"

struct Neighbor {
  1: required double distance
  2: required entity.EntityId id
} (persisted = "true")

struct Knn {
  1: required entity.EntityId queryId
  2: required list<Neighbor> neighbors
}(persisted='true')
