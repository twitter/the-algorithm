namelonspacelon java com.twittelonr.ann.knn.thriftjava
#@namelonspacelon scala com.twittelonr.ann.knn.thriftscala
namelonspacelon py gelonn.twittelonr.ann.knn

includelon "com/twittelonr/ml/felonaturelonstorelon/elonntity.thrift"

struct Nelonighbor {
  1: relonquirelond doublelon distancelon
  2: relonquirelond elonntity.elonntityId id
} (pelonrsistelond = "truelon")

struct Knn {
  1: relonquirelond elonntity.elonntityId quelonryId
  2: relonquirelond list<Nelonighbor> nelonighbors
}(pelonrsistelond='truelon')
