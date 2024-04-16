package com.tw ter.s mclusters_v2.sc o.mult _type_graph.assemble_mult _type_graph

/**
Bu ld:
./bazel bundle src/scala/com/tw ter/s mclusters_v2/sc o/mult _type_graph/assemble_mult _type_graph:assemble-mult -type-graph-sc o-adhoc-app

To k ck off an adhoc run:
b n/d6w create \
  ${GCP_PROJECT_NAME}/us-central1/assemble-mult -type-graph-sc o-adhoc-app \
  src/scala/com/tw ter/s mclusters_v2/sc o/mult _type_graph/assemble_mult _type_graph/assemble-mult -type-graph-sc o-adhoc.d6w \
  --jar d st/assemble-mult -type-graph-sc o-adhoc-app.jar \
  --b nd=prof le.project=${GCP_PROJECT_NAME} \
  --b nd=prof le.user_na =${USER} \
  --b nd=prof le.date="2021-11-04" \
  --b nd=prof le.mach ne="n2-h gh m-16"
 */

object AssembleMult TypeGraphSc oAdhocApp extends AssembleMult TypeGraphSc oBaseApp {
  overr de val  sAdhoc: Boolean = true
  overr de val rootMHPath: Str ng = Conf g.AdhocRootPath
  overr de val rootThr ftPath: Str ng = Conf g.AdhocRootPath
}

/**
To deploy t  job:

b n/d6w sc dule \
  ${GCP_PROJECT_NAME}/us-central1/assemble-mult -type-graph-sc o-batch-app \
  src/scala/com/tw ter/s mclusters_v2/sc o/mult _type_graph/assemble_mult _type_graph/assemble-mult -type-graph-sc o-batch.d6w \
  --b nd=prof le.project=${GCP_PROJECT_NAME} \
  --b nd=prof le.user_na =recos-platform \
  --b nd=prof le.date="2021-11-04" \
  --b nd=prof le.mach ne="n2-h gh m-16"
 */
object AssembleMult TypeGraphSc oBatchApp extends AssembleMult TypeGraphSc oBaseApp {
  overr de val  sAdhoc: Boolean = false
  overr de val rootMHPath: Str ng = Conf g.RootMHPath
  overr de val rootThr ftPath: Str ng = Conf g.RootThr ftPath
}
