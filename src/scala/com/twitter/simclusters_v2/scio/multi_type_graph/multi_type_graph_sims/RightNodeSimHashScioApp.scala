package com.twittew.simcwustews_v2.scio
package muwti_type_gwaph.muwti_type_gwaph_sims

i-impowt com.twittew.daw.cwient.dataset.snapshotdawdataset
i-impowt com.twittew.simcwustews_v2.hdfs_souwces.wightnodesimhashscioscawadataset
i-impowt com.twittew.simcwustews_v2.thwiftscawa.wightnodesimhashsketch

/**
b-buiwd:
./bazew b-bundwe s-swc/scawa/com/twittew/simcwustews_v2/scio/muwti_type_gwaph/muwti_type_gwaph_sims:muwti-type-gwaph-sim-hash-scio-adhoc-app

t-to kick o-off an adhoc wun:
bin/d6w cweate \
  ${gcp_pwoject_name}/us-centwaw1/muwti-type-gwaph-sim-hash-scio-adhoc-app \
  swc/scawa/com/twittew/simcwustews_v2/scio/muwti_type_gwaph/muwti_type_gwaph_sims/sim-hash-scio-adhoc.d6w \
  --jaw dist/muwti-type-gwaph-sim-hash-scio-adhoc-app.jaw \
  --bind=pwofiwe.pwoject=${gcp_pwoject_name} \
  --bind=pwofiwe.usew_name=${usew} \
  --bind=pwofiwe.date="2021-12-01" \
  --bind=pwofiwe.machine="n2d-highmem-16" --ignowe-existing
 */
object wightnodesimhashscioadhocapp e-extends wightnodesimhashsciobaseapp {
  ovewwide vaw isadhoc: b-boowean = twue
  ovewwide v-vaw wightnodesimhashsnapshotdataset: snapshotdawdataset[wightnodesimhashsketch] =
    wightnodesimhashscioadhocscawadataset
}

/**
to depwoy the j-job:

bin/d6w scheduwe \
  ${gcp_pwoject_name}/us-centwaw1/muwti-type-gwaph-sim-hash-scio-batch-app \
  s-swc/scawa/com/twittew/simcwustews_v2/scio/muwti_type_gwaph/muwti_type_gwaph_sims/sim-hash-scio-batch.d6w \
  --bind=pwofiwe.pwoject=${gcp_pwoject_name} \
  --bind=pwofiwe.usew_name=wecos-pwatfowm \
  --bind=pwofiwe.date="2021-12-01" \
  --bind=pwofiwe.machine="n2d-highmem-16"
 */
o-object wightnodesimhashsciobatchapp extends wightnodesimhashsciobaseapp {
  ovewwide vaw isadhoc: boowean = fawse
  ovewwide v-vaw wightnodesimhashsnapshotdataset: snapshotdawdataset[wightnodesimhashsketch] =
    wightnodesimhashscioscawadataset
}
