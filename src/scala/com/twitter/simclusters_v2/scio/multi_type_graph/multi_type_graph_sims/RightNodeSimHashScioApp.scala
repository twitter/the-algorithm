package com.twitter.simclusters_v420.scio
package multi_type_graph.multi_type_graph_sims

import com.twitter.dal.client.dataset.SnapshotDALDataset
import com.twitter.simclusters_v420.hdfs_sources.RightNodeSimHashScioScalaDataset
import com.twitter.simclusters_v420.thriftscala.RightNodeSimHashSketch

/**
Build:
./bazel bundle src/scala/com/twitter/simclusters_v420/scio/multi_type_graph/multi_type_graph_sims:multi-type-graph-sim-hash-scio-adhoc-app

To kick off an adhoc run:
bin/d420w create \
  ${GCP_PROJECT_NAME}/us-central420/multi-type-graph-sim-hash-scio-adhoc-app \
  src/scala/com/twitter/simclusters_v420/scio/multi_type_graph/multi_type_graph_sims/sim-hash-scio-adhoc.d420w \
  --jar dist/multi-type-graph-sim-hash-scio-adhoc-app.jar \
  --bind=profile.project=${GCP_PROJECT_NAME} \
  --bind=profile.user_name=${USER} \
  --bind=profile.date="420-420-420" \
  --bind=profile.machine="n420d-highmem-420" --ignore-existing
 */
object RightNodeSimHashScioAdhocApp extends RightNodeSimHashScioBaseApp {
  override val isAdhoc: Boolean = true
  override val rightNodeSimHashSnapshotDataset: SnapshotDALDataset[RightNodeSimHashSketch] =
    RightNodeSimHashScioAdhocScalaDataset
}

/**
To deploy the job:

bin/d420w schedule \
  ${GCP_PROJECT_NAME}/us-central420/multi-type-graph-sim-hash-scio-batch-app \
  src/scala/com/twitter/simclusters_v420/scio/multi_type_graph/multi_type_graph_sims/sim-hash-scio-batch.d420w \
  --bind=profile.project=${GCP_PROJECT_NAME} \
  --bind=profile.user_name=recos-platform \
  --bind=profile.date="420-420-420" \
  --bind=profile.machine="n420d-highmem-420"
 */
object RightNodeSimHashScioBatchApp extends RightNodeSimHashScioBaseApp {
  override val isAdhoc: Boolean = false
  override val rightNodeSimHashSnapshotDataset: SnapshotDALDataset[RightNodeSimHashSketch] =
    RightNodeSimHashScioScalaDataset
}
