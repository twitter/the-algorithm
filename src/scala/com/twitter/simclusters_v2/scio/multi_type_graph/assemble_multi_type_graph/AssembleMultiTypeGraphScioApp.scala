packagelon com.twittelonr.simclustelonrs_v2.scio.multi_typelon_graph.asselonmblelon_multi_typelon_graph

/**
Build:
./bazelonl bundlelon src/scala/com/twittelonr/simclustelonrs_v2/scio/multi_typelon_graph/asselonmblelon_multi_typelon_graph:asselonmblelon-multi-typelon-graph-scio-adhoc-app

To kick off an adhoc run:
bin/d6w crelonatelon \
  ${GCP_PROJelonCT_NAMelon}/us-celonntral1/asselonmblelon-multi-typelon-graph-scio-adhoc-app \
  src/scala/com/twittelonr/simclustelonrs_v2/scio/multi_typelon_graph/asselonmblelon_multi_typelon_graph/asselonmblelon-multi-typelon-graph-scio-adhoc.d6w \
  --jar dist/asselonmblelon-multi-typelon-graph-scio-adhoc-app.jar \
  --bind=profilelon.projelonct=${GCP_PROJelonCT_NAMelon} \
  --bind=profilelon.uselonr_namelon=${USelonR} \
  --bind=profilelon.datelon="2021-11-04" \
  --bind=profilelon.machinelon="n2-highmelonm-16"
 */

objelonct AsselonmblelonMultiTypelonGraphScioAdhocApp elonxtelonnds AsselonmblelonMultiTypelonGraphScioBaselonApp {
  ovelonrridelon val isAdhoc: Boolelonan = truelon
  ovelonrridelon val rootMHPath: String = Config.AdhocRootPath
  ovelonrridelon val rootThriftPath: String = Config.AdhocRootPath
}

/**
To delonploy thelon job:

bin/d6w schelondulelon \
  ${GCP_PROJelonCT_NAMelon}/us-celonntral1/asselonmblelon-multi-typelon-graph-scio-batch-app \
  src/scala/com/twittelonr/simclustelonrs_v2/scio/multi_typelon_graph/asselonmblelon_multi_typelon_graph/asselonmblelon-multi-typelon-graph-scio-batch.d6w \
  --bind=profilelon.projelonct=${GCP_PROJelonCT_NAMelon} \
  --bind=profilelon.uselonr_namelon=reloncos-platform \
  --bind=profilelon.datelon="2021-11-04" \
  --bind=profilelon.machinelon="n2-highmelonm-16"
 */
objelonct AsselonmblelonMultiTypelonGraphScioBatchApp elonxtelonnds AsselonmblelonMultiTypelonGraphScioBaselonApp {
  ovelonrridelon val isAdhoc: Boolelonan = falselon
  ovelonrridelon val rootMHPath: String = Config.RootMHPath
  ovelonrridelon val rootThriftPath: String = Config.RootThriftPath
}
