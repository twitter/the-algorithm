package com.tw ter.s mclusters_v2.sc o
package mult _type_graph.mult _type_graph_s ms

 mport com.tw ter.dal.cl ent.dataset.KeyValDALDataset
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal
 mport com.tw ter.s mclusters_v2.hdfs_s ces.R ghtNodeCos neS m lar ySc oScalaDataset
 mport com.tw ter.s mclusters_v2.thr ftscala.R ghtNode
 mport com.tw ter.s mclusters_v2.thr ftscala.S m larR ghtNodes
 mport com.tw ter.wtf.scald ng.jobs.cos ne_s m lar y.common.Approx mateMatr xSelfTransposeMult pl cat onJob

/**
Bu ld:
./bazel bundle src/scala/com/tw ter/s mclusters_v2/sc o/mult _type_graph/mult _type_graph_s ms:mult -type-graph-cos ne-s m lar y-sc o-adhoc-app

To k ck off an adhoc run:
b n/d6w create \
  ${GCP_PROJECT_NAME}/us-central1/mult -type-graph-cos ne-s m lar y-sc o-adhoc-app \
  src/scala/com/tw ter/s mclusters_v2/sc o/mult _type_graph/mult _type_graph_s ms/cos ne-s m lar y-sc o-adhoc.d6w \
  --jar d st/mult -type-graph-cos ne-s m lar y-sc o-adhoc-app.jar \
  --b nd=prof le.project=${GCP_PROJECT_NAME} \
  --b nd=prof le.user_na =${USER} \
  --b nd=prof le.date="2022-01-16" \
  --b nd=prof le.mach ne="n2d-h gh m-16" -- gnore-ex st ng
 */

object R ghtNodeCos neS m lar ySc oAdhocApp extends R ghtNodeCos neS m lar ySc oBaseApp {
  overr de val  sAdhoc = true
  overr de val cos neS mKeyValSnapshotDataset: KeyValDALDataset[
    KeyVal[R ghtNode, S m larR ghtNodes]
  ] =
    R ghtNodeCos neS m lar ySc oAdhocScalaDataset
  overr de val f lterCand dateS m lar yPa r: (Double, Double, Double) => Boolean =
    Approx mateMatr xSelfTransposeMult pl cat onJob.f lterCand dateS m lar yPa r
}

/**
To deploy t  job:

b n/d6w sc dule \
  ${GCP_PROJECT_NAME}/us-central1/mult -type-graph-cos ne-s m lar y-sc o-batch-app \
  src/scala/com/tw ter/s mclusters_v2/sc o/mult _type_graph/mult _type_graph_s ms/cos ne-s m lar y-sc o-batch.d6w \
  --b nd=prof le.project=${GCP_PROJECT_NAME} \
  --b nd=prof le.user_na =recos-platform \
  --b nd=prof le.date="2021-12-01" \
  --b nd=prof le.mach ne="n2d-h gh m-16"
 */
object R ghtNodeCos neS m lar ySc oBatchApp extends R ghtNodeCos neS m lar ySc oBaseApp {
  overr de val  sAdhoc = false
  overr de val cos neS mKeyValSnapshotDataset: KeyValDALDataset[
    KeyVal[R ghtNode, S m larR ghtNodes]
  ] =
    R ghtNodeCos neS m lar ySc oScalaDataset
  overr de val f lterCand dateS m lar yPa r: (Double, Double, Double) => Boolean =
    Approx mateMatr xSelfTransposeMult pl cat onJob.f lterCand dateS m lar yPa r
}
