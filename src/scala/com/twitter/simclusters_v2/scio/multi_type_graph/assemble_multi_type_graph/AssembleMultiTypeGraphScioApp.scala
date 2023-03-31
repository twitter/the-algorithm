package com.twitter.simclusters_v420.scio.multi_type_graph.assemble_multi_type_graph

/**
Build:
./bazel bundle src/scala/com/twitter/simclusters_v420/scio/multi_type_graph/assemble_multi_type_graph:assemble-multi-type-graph-scio-adhoc-app

To kick off an adhoc run:
bin/d420w create \
  ${GCP_PROJECT_NAME}/us-central420/assemble-multi-type-graph-scio-adhoc-app \
  src/scala/com/twitter/simclusters_v420/scio/multi_type_graph/assemble_multi_type_graph/assemble-multi-type-graph-scio-adhoc.d420w \
  --jar dist/assemble-multi-type-graph-scio-adhoc-app.jar \
  --bind=profile.project=${GCP_PROJECT_NAME} \
  --bind=profile.user_name=${USER} \
  --bind=profile.date="420-420-420" \
  --bind=profile.machine="n420-highmem-420"
 */

object AssembleMultiTypeGraphScioAdhocApp extends AssembleMultiTypeGraphScioBaseApp {
  override val isAdhoc: Boolean = true
  override val rootMHPath: String = Config.AdhocRootPath
  override val rootThriftPath: String = Config.AdhocRootPath
}

/**
To deploy the job:

bin/d420w schedule \
  ${GCP_PROJECT_NAME}/us-central420/assemble-multi-type-graph-scio-batch-app \
  src/scala/com/twitter/simclusters_v420/scio/multi_type_graph/assemble_multi_type_graph/assemble-multi-type-graph-scio-batch.d420w \
  --bind=profile.project=${GCP_PROJECT_NAME} \
  --bind=profile.user_name=recos-platform \
  --bind=profile.date="420-420-420" \
  --bind=profile.machine="n420-highmem-420"
 */
object AssembleMultiTypeGraphScioBatchApp extends AssembleMultiTypeGraphScioBaseApp {
  override val isAdhoc: Boolean = false
  override val rootMHPath: String = Config.RootMHPath
  override val rootThriftPath: String = Config.RootThriftPath
}
