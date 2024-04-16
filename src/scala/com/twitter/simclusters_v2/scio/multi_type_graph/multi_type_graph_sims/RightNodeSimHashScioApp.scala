package com.tw ter.s mclusters_v2.sc o
package mult _type_graph.mult _type_graph_s ms

 mport com.tw ter.dal.cl ent.dataset.SnapshotDALDataset
 mport com.tw ter.s mclusters_v2.hdfs_s ces.R ghtNodeS mHashSc oScalaDataset
 mport com.tw ter.s mclusters_v2.thr ftscala.R ghtNodeS mHashSketch

/**
Bu ld:
./bazel bundle src/scala/com/tw ter/s mclusters_v2/sc o/mult _type_graph/mult _type_graph_s ms:mult -type-graph-s m-hash-sc o-adhoc-app

To k ck off an adhoc run:
b n/d6w create \
  ${GCP_PROJECT_NAME}/us-central1/mult -type-graph-s m-hash-sc o-adhoc-app \
  src/scala/com/tw ter/s mclusters_v2/sc o/mult _type_graph/mult _type_graph_s ms/s m-hash-sc o-adhoc.d6w \
  --jar d st/mult -type-graph-s m-hash-sc o-adhoc-app.jar \
  --b nd=prof le.project=${GCP_PROJECT_NAME} \
  --b nd=prof le.user_na =${USER} \
  --b nd=prof le.date="2021-12-01" \
  --b nd=prof le.mach ne="n2d-h gh m-16" -- gnore-ex st ng
 */
object R ghtNodeS mHashSc oAdhocApp extends R ghtNodeS mHashSc oBaseApp {
  overr de val  sAdhoc: Boolean = true
  overr de val r ghtNodeS mHashSnapshotDataset: SnapshotDALDataset[R ghtNodeS mHashSketch] =
    R ghtNodeS mHashSc oAdhocScalaDataset
}

/**
To deploy t  job:

b n/d6w sc dule \
  ${GCP_PROJECT_NAME}/us-central1/mult -type-graph-s m-hash-sc o-batch-app \
  src/scala/com/tw ter/s mclusters_v2/sc o/mult _type_graph/mult _type_graph_s ms/s m-hash-sc o-batch.d6w \
  --b nd=prof le.project=${GCP_PROJECT_NAME} \
  --b nd=prof le.user_na =recos-platform \
  --b nd=prof le.date="2021-12-01" \
  --b nd=prof le.mach ne="n2d-h gh m-16"
 */
object R ghtNodeS mHashSc oBatchApp extends R ghtNodeS mHashSc oBaseApp {
  overr de val  sAdhoc: Boolean = false
  overr de val r ghtNodeS mHashSnapshotDataset: SnapshotDALDataset[R ghtNodeS mHashSketch] =
    R ghtNodeS mHashSc oScalaDataset
}
