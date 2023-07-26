package com.twittew.simcwustews_v2.scio.muwti_type_gwaph.assembwe_muwti_type_gwaph

/**
buiwd:
./bazew b-bundwe swc/scawa/com/twittew/simcwustews_v2/scio/muwti_type_gwaph/assembwe_muwti_type_gwaph:assembwe-muwti-type-gwaph-scio-adhoc-app

t-to kick o-off an adhoc w-wun:
bin/d6w cweate \
  ${gcp_pwoject_name}/us-centwaw1/assembwe-muwti-type-gwaph-scio-adhoc-app \
  s-swc/scawa/com/twittew/simcwustews_v2/scio/muwti_type_gwaph/assembwe_muwti_type_gwaph/assembwe-muwti-type-gwaph-scio-adhoc.d6w \
  --jaw d-dist/assembwe-muwti-type-gwaph-scio-adhoc-app.jaw \
  --bind=pwofiwe.pwoject=${gcp_pwoject_name} \
  --bind=pwofiwe.usew_name=${usew} \
  --bind=pwofiwe.date="2021-11-04" \
  --bind=pwofiwe.machine="n2-highmem-16"
 */

o-object a-assembwemuwtitypegwaphscioadhocapp extends assembwemuwtitypegwaphsciobaseapp {
  ovewwide vaw isadhoc: boowean = twue
  ovewwide v-vaw wootmhpath: stwing = config.adhocwootpath
  ovewwide vaw wootthwiftpath: s-stwing = config.adhocwootpath
}

/**
t-to depwoy the job:

bin/d6w scheduwe \
  ${gcp_pwoject_name}/us-centwaw1/assembwe-muwti-type-gwaph-scio-batch-app \
  swc/scawa/com/twittew/simcwustews_v2/scio/muwti_type_gwaph/assembwe_muwti_type_gwaph/assembwe-muwti-type-gwaph-scio-batch.d6w \
  --bind=pwofiwe.pwoject=${gcp_pwoject_name} \
  --bind=pwofiwe.usew_name=wecos-pwatfowm \
  --bind=pwofiwe.date="2021-11-04" \
  --bind=pwofiwe.machine="n2-highmem-16"
 */
object assembwemuwtitypegwaphsciobatchapp e-extends assembwemuwtitypegwaphsciobaseapp {
  ovewwide v-vaw isadhoc: b-boowean = fawse
  ovewwide vaw wootmhpath: stwing = config.wootmhpath
  ovewwide v-vaw wootthwiftpath: stwing = config.wootthwiftpath
}
