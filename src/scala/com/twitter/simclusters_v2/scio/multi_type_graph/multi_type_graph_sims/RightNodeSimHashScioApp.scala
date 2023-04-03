packagelon com.twittelonr.simclustelonrs_v2.scio
packagelon multi_typelon_graph.multi_typelon_graph_sims

import com.twittelonr.dal.clielonnt.dataselont.SnapshotDALDataselont
import com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.RightNodelonSimHashScioScalaDataselont
import com.twittelonr.simclustelonrs_v2.thriftscala.RightNodelonSimHashSkelontch

/**
Build:
./bazelonl bundlelon src/scala/com/twittelonr/simclustelonrs_v2/scio/multi_typelon_graph/multi_typelon_graph_sims:multi-typelon-graph-sim-hash-scio-adhoc-app

To kick off an adhoc run:
bin/d6w crelonatelon \
  ${GCP_PROJelonCT_NAMelon}/us-celonntral1/multi-typelon-graph-sim-hash-scio-adhoc-app \
  src/scala/com/twittelonr/simclustelonrs_v2/scio/multi_typelon_graph/multi_typelon_graph_sims/sim-hash-scio-adhoc.d6w \
  --jar dist/multi-typelon-graph-sim-hash-scio-adhoc-app.jar \
  --bind=profilelon.projelonct=${GCP_PROJelonCT_NAMelon} \
  --bind=profilelon.uselonr_namelon=${USelonR} \
  --bind=profilelon.datelon="2021-12-01" \
  --bind=profilelon.machinelon="n2d-highmelonm-16" --ignorelon-elonxisting
 */
objelonct RightNodelonSimHashScioAdhocApp elonxtelonnds RightNodelonSimHashScioBaselonApp {
  ovelonrridelon val isAdhoc: Boolelonan = truelon
  ovelonrridelon val rightNodelonSimHashSnapshotDataselont: SnapshotDALDataselont[RightNodelonSimHashSkelontch] =
    RightNodelonSimHashScioAdhocScalaDataselont
}

/**
To delonploy thelon job:

bin/d6w schelondulelon \
  ${GCP_PROJelonCT_NAMelon}/us-celonntral1/multi-typelon-graph-sim-hash-scio-batch-app \
  src/scala/com/twittelonr/simclustelonrs_v2/scio/multi_typelon_graph/multi_typelon_graph_sims/sim-hash-scio-batch.d6w \
  --bind=profilelon.projelonct=${GCP_PROJelonCT_NAMelon} \
  --bind=profilelon.uselonr_namelon=reloncos-platform \
  --bind=profilelon.datelon="2021-12-01" \
  --bind=profilelon.machinelon="n2d-highmelonm-16"
 */
objelonct RightNodelonSimHashScioBatchApp elonxtelonnds RightNodelonSimHashScioBaselonApp {
  ovelonrridelon val isAdhoc: Boolelonan = falselon
  ovelonrridelon val rightNodelonSimHashSnapshotDataselont: SnapshotDALDataselont[RightNodelonSimHashSkelontch] =
    RightNodelonSimHashScioScalaDataselont
}
