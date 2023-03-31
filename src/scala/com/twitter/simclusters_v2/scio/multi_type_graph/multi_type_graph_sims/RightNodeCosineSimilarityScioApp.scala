package com.twitter.simclusters_v2.scio
package multi_type_graph.multi_type_graph_sims

import com.twitter.dal.client.dataset.KeyValDALDataset
import com.twitter.scalding_internal.multiformat.format.keyval.KeyVal
import com.twitter.simclusters_v2.hdfs_sources.RightNodeCosineSimilarityScioScalaDataset
import com.twitter.simclusters_v2.thriftscala.RightNode
import com.twitter.simclusters_v2.thriftscala.SimilarRightNodes
import com.twitter.wtf.scalding.jobs.cosine_similarity.common.ApproximateMatrixSelfTransposeMultiplicationJob

/**
Build:
./bazel bundle src/scala/com/twitter/simclusters_v2/scio/multi_type_graph/multi_type_graph_sims:multi-type-graph-cosine-similarity-scio-adhoc-app

To kick off an adhoc run:
bin/d6w create \
  ${GCP_PROJECT_NAME}/us-central1/multi-type-graph-cosine-similarity-scio-adhoc-app \
  src/scala/com/twitter/simclusters_v2/scio/multi_type_graph/multi_type_graph_sims/cosine-similarity-scio-adhoc.d6w \
  --jar dist/multi-type-graph-cosine-similarity-scio-adhoc-app.jar \
  --bind=profile.project=${GCP_PROJECT_NAME} \
  --bind=profile.user_name=${USER} \
  --bind=profile.date="2022-01-16" \
  --bind=profile.machine="n2d-highmem-16" --ignore-existing
 */

object RightNodeCosineSimilarityScioAdhocApp extends RightNodeCosineSimilarityScioBaseApp {
  override val isAdhoc = true
  override val cosineSimKeyValSnapshotDataset: KeyValDALDataset[
    KeyVal[RightNode, SimilarRightNodes]
  ] =
    RightNodeCosineSimilarityScioAdhocScalaDataset
  override val filterCandidateSimilarityPair: (Double, Double, Double) => Boolean =
    ApproximateMatrixSelfTransposeMultiplicationJob.filterCandidateSimilarityPair
}

/**
To deploy the job:

bin/d6w schedule \
  ${GCP_PROJECT_NAME}/us-central1/multi-type-graph-cosine-similarity-scio-batch-app \
  src/scala/com/twitter/simclusters_v2/scio/multi_type_graph/multi_type_graph_sims/cosine-similarity-scio-batch.d6w \
  --bind=profile.project=${GCP_PROJECT_NAME} \
  --bind=profile.user_name=recos-platform \
  --bind=profile.date="2021-12-01" \
  --bind=profile.machine="n2d-highmem-16"
 */
object RightNodeCosineSimilarityScioBatchApp extends RightNodeCosineSimilarityScioBaseApp {
  override val isAdhoc = false
  override val cosineSimKeyValSnapshotDataset: KeyValDALDataset[
    KeyVal[RightNode, SimilarRightNodes]
  ] =
    RightNodeCosineSimilarityScioScalaDataset
  override val filterCandidateSimilarityPair: (Double, Double, Double) => Boolean =
    ApproximateMatrixSelfTransposeMultiplicationJob.filterCandidateSimilarityPair
}
