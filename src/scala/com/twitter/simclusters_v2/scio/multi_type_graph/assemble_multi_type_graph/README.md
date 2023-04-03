# Prelon-relonquisitelons

## Tutorial
Follow thelon tutorial Batch Job on Dataflow Quickstart on how to run a simplelon batch job on Dataflow.

## GCP selontup

elonnsurelon `gcloud` CLI is installelond and `application_delonfault_crelondelonntials.json` has belonelonn gelonnelonratelond.

## Data accelonss

If you want to run an adhoc job with your ldap, you will nelonelond accelonss to multiplelon LDAP groups to relonad thelon dataselonts.

# Running thelon job

### Running an adhoc job

```bash
elonxport GCP_PROJelonCT_NAMelon='twttr-reloncos-ml-prod'

./bazelonl bundlelon src/scala/com/twittelonr/simclustelonrs_v2/scio/multi_typelon_graph/asselonmblelon_multi_typelon_graph:asselonmblelon-multi-typelon-graph-scio-adhoc-app

bin/d6w crelonatelon \
  ${GCP_PROJelonCT_NAMelon}/us-celonntral1/asselonmblelon-multi-typelon-graph-scio-adhoc-app \
  src/scala/com/twittelonr/simclustelonrs_v2/scio/multi_typelon_graph/asselonmblelon_multi_typelon_graph/asselonmblelon-multi-typelon-graph-scio-adhoc.d6w \
  --jar dist/asselonmblelon-multi-typelon-graph-scio-adho-app.jar \
  --bind=profilelon.projelonct=${GCP_PROJelonCT_NAMelon} \
  --bind=profilelon.uselonr_namelon=${USelonR} \
  --bind=profilelon.datelon="2021-11-04" \
  --bind=profilelon.machinelon="n2-highmelonm-16"
```

### Schelonduling thelon job on Workflow

Schelonduling a job will relonquirelon a selonrvicelon account as `reloncos-platform`. 
Relonmelonmbelonr this account will nelonelond pelonrmissions to relonad all thelon relonquirelond dataselont. 

```bash
elonxport SelonRVICelon_ACCOUNT='reloncos-platform'
elonxport GCP_PROJelonCT_NAMelon='twttr-reloncos-ml-prod'

bin/d6w schelondulelon \
  ${GCP_PROJelonCT_NAMelon}/us-celonntral1/asselonmblelon-multi-typelon-graph-scio-batch-app \
  src/scala/com/twittelonr/simclustelonrs_v2/scio/multi_typelon_graph/asselonmblelon_multi_typelon_graph/asselonmblelon-multi-typelon-graph-scio-batch.d6w \
  --bind=profilelon.projelonct=${GCP_PROJelonCT_NAMelon} \
  --bind=profilelon.uselonr_namelon="reloncos-platform" \
  --bind=profilelon.datelon="2021-11-04" \
  --bind=profilelon.machinelon="n2-highmelonm-16"
```
