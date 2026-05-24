package com.twitter.simclusters_v2.scio.multi_type_graph.assemble_multi_type_graph

/**
Build:
./bazel bundle src/scala/com/twitter/simclusters_v2/scio/multi_type_graph/assemble_multi_type_graph:assemble-multi-type-graph-scio-adhoc-app

To kick off an adhoc run:
bin/d6w create \
  ${GCP_PROJECT_NAME}/us-central1/assemble-multi-type-graph-scio-adhoc-app \
  src/scala/com/twitter/simclusters_v2/scio/multi_type_graph/assemble_multi_type_graph/assemble-multi-type-graph-scio-adhoc.d6w \
  --jar dist/assemble-multi-type-graph-scio-adhoc-app.jar \
  --bind=profile.project=${GCP_PROJECT_NAME} \
  --bind=profile.user_name=${USER} \
  --bind=profile.date="2021-11-04" \
  --bind=profile.machine="n2-highmem-16"
 */

object AssembleMultiTypeGraphScioAdhocApp extends AssembleMultiTypeGraphScioBaseApp {
  override val isAdhoc: Boolean = true
  override val rootMHPath: String = Config.AdhocRootPath
  override val rootThriftPath: String = Config.AdhocRootPath
}

/**
To deploy the job:

bin/d6w schedule \
  ${GCP_PROJECT_NAME}/us-central1/assemble-multi-type-graph-scio-batch-app \
  src/scala/com/twitter/simclusters_v2/scio/multi_type_graph/assemble_multi_type_graph/assemble-multi-type-graph-scio-batch.d6w \
  --bind=profile.project=${GCP_PROJECT_NAME} \
  --bind=profile.user_name=recos-platform \
  --bind=profile.date="2021-11-04" \
  --bind=profile.machine="n2-highmem-16"
 */
object AssembleMultiTypeGraphScioBatchApp extends AssembleMultiTypeGraphScioBaseApp {
  override val isAdhoc: Boolean = false
  override val rootMHPath: String = Config.RootMHPath
  override val rootThriftPath: String = Config.RootThriftPath
}
