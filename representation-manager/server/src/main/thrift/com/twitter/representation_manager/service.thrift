na space java com.tw ter.representat on_manager.thr ftjava
#@na space scala com.tw ter.representat on_manager.thr ftscala
#@na space strato com.tw ter.representat on_manager

 nclude "com/tw ter/s mclusters_v2/onl ne_store.thr ft"
 nclude "com/tw ter/s mclusters_v2/ dent f er.thr ft"

/**
  * A un form column v ew for all k nds of S mClusters based embedd ngs.
  **/
struct S mClustersEmbedd ngV ew {
  1: requ red  dent f er.Embedd ngType embedd ngType
  2: requ red onl ne_store.ModelVers on modelVers on
}(pers sted = 'false', hasPersonalData = 'false')
