namespace java com.X.ann.knn.thriftjava
#@namespace scala com.X.ann.knn.thriftscala
namespace py gen.X.ann.knn

include "com/X/ml/featurestore/entity.thrift"

struct Neighbor {
  1: required double distance
  2: required entity.EntityId id
} (persisted = "true")

struct Knn {
  1: required entity.EntityId queryId
  2: required list<Neighbor> neighbors
}(persisted='true')
