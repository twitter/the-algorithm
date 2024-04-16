na space java com.tw ter.ann.knn.thr ftjava
#@na space scala com.tw ter.ann.knn.thr ftscala
na space py gen.tw ter.ann.knn

 nclude "com/tw ter/ml/featurestore/ent y.thr ft"

struct Ne ghbor {
  1: requ red double d stance
  2: requ red ent y.Ent y d  d
} (pers sted = "true")

struct Knn {
  1: requ red ent y.Ent y d query d
  2: requ red l st<Ne ghbor> ne ghbors
}(pers sted='true')
