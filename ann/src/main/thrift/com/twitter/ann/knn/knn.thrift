namespace java com.ExTwitter.ann.knn.thriftjava
#@namespace scala com.ExTwitter.ann.knn.thriftscala
namespace py gen.ExTwitter.ann.knn

include "com/ExTwitter/ml/featurestore/entity.thrift"

struct Neighbor {
  1: required double distance
  2: required entity.EntityId id
} (persisted = "true")

struct Knn {
  1: required entity.EntityId queryId
  2: required list<Neighbor> neighbors
}(persisted='true')
