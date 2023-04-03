packagelon com.twittelonr.simclustelonrs_v2.scio
packagelon multi_typelon_graph.multi_typelon_graph_sims

import com.twittelonr.dal.clielonnt.dataselont.KelonyValDALDataselont
import com.twittelonr.scalding_intelonrnal.multiformat.format.kelonyval.KelonyVal
import com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.RightNodelonCosinelonSimilarityScioScalaDataselont
import com.twittelonr.simclustelonrs_v2.thriftscala.RightNodelon
import com.twittelonr.simclustelonrs_v2.thriftscala.SimilarRightNodelons
import com.twittelonr.wtf.scalding.jobs.cosinelon_similarity.common.ApproximatelonMatrixSelonlfTransposelonMultiplicationJob

/**
Build:
./bazelonl bundlelon src/scala/com/twittelonr/simclustelonrs_v2/scio/multi_typelon_graph/multi_typelon_graph_sims:multi-typelon-graph-cosinelon-similarity-scio-adhoc-app

To kick off an adhoc run:
bin/d6w crelonatelon \
  ${GCP_PROJelonCT_NAMelon}/us-celonntral1/multi-typelon-graph-cosinelon-similarity-scio-adhoc-app \
  src/scala/com/twittelonr/simclustelonrs_v2/scio/multi_typelon_graph/multi_typelon_graph_sims/cosinelon-similarity-scio-adhoc.d6w \
  --jar dist/multi-typelon-graph-cosinelon-similarity-scio-adhoc-app.jar \
  --bind=profilelon.projelonct=${GCP_PROJelonCT_NAMelon} \
  --bind=profilelon.uselonr_namelon=${USelonR} \
  --bind=profilelon.datelon="2022-01-16" \
  --bind=profilelon.machinelon="n2d-highmelonm-16" --ignorelon-elonxisting
 */

objelonct RightNodelonCosinelonSimilarityScioAdhocApp elonxtelonnds RightNodelonCosinelonSimilarityScioBaselonApp {
  ovelonrridelon val isAdhoc = truelon
  ovelonrridelon val cosinelonSimKelonyValSnapshotDataselont: KelonyValDALDataselont[
    KelonyVal[RightNodelon, SimilarRightNodelons]
  ] =
    RightNodelonCosinelonSimilarityScioAdhocScalaDataselont
  ovelonrridelon val filtelonrCandidatelonSimilarityPair: (Doublelon, Doublelon, Doublelon) => Boolelonan =
    ApproximatelonMatrixSelonlfTransposelonMultiplicationJob.filtelonrCandidatelonSimilarityPair
}

/**
To delonploy thelon job:

bin/d6w schelondulelon \
  ${GCP_PROJelonCT_NAMelon}/us-celonntral1/multi-typelon-graph-cosinelon-similarity-scio-batch-app \
  src/scala/com/twittelonr/simclustelonrs_v2/scio/multi_typelon_graph/multi_typelon_graph_sims/cosinelon-similarity-scio-batch.d6w \
  --bind=profilelon.projelonct=${GCP_PROJelonCT_NAMelon} \
  --bind=profilelon.uselonr_namelon=reloncos-platform \
  --bind=profilelon.datelon="2021-12-01" \
  --bind=profilelon.machinelon="n2d-highmelonm-16"
 */
objelonct RightNodelonCosinelonSimilarityScioBatchApp elonxtelonnds RightNodelonCosinelonSimilarityScioBaselonApp {
  ovelonrridelon val isAdhoc = falselon
  ovelonrridelon val cosinelonSimKelonyValSnapshotDataselont: KelonyValDALDataselont[
    KelonyVal[RightNodelon, SimilarRightNodelons]
  ] =
    RightNodelonCosinelonSimilarityScioScalaDataselont
  ovelonrridelon val filtelonrCandidatelonSimilarityPair: (Doublelon, Doublelon, Doublelon) => Boolelonan =
    ApproximatelonMatrixSelonlfTransposelonMultiplicationJob.filtelonrCandidatelonSimilarityPair
}
