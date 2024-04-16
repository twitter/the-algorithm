# Pre-requ s es

## Tutor al
Follow t  tutor al Batch Job on Dataflow Qu ckstart on how to run a s mple batch job on Dataflow.

## GCP setup

Ensure `gcloud` CL   s  nstalled and `appl cat on_default_credent als.json` has been generated.

## Data access

 f   want to run an adhoc job w h y  ldap,   w ll need access to mult ple LDAP groups to read t  datasets.

# Runn ng t  job

### Runn ng an adhoc job

```bash
export GCP_PROJECT_NAME='twttr-recos-ml-prod'

./bazel bundle src/scala/com/tw ter/s mclusters_v2/sc o/mult _type_graph/assemble_mult _type_graph:assemble-mult -type-graph-sc o-adhoc-app

b n/d6w create \
  ${GCP_PROJECT_NAME}/us-central1/assemble-mult -type-graph-sc o-adhoc-app \
  src/scala/com/tw ter/s mclusters_v2/sc o/mult _type_graph/assemble_mult _type_graph/assemble-mult -type-graph-sc o-adhoc.d6w \
  --jar d st/assemble-mult -type-graph-sc o-adho-app.jar \
  --b nd=prof le.project=${GCP_PROJECT_NAME} \
  --b nd=prof le.user_na =${USER} \
  --b nd=prof le.date="2021-11-04" \
  --b nd=prof le.mach ne="n2-h gh m-16"
```

### Sc dul ng t  job on Workflow

Sc dul ng a job w ll requ re a serv ce account as `recos-platform`. 
Re mber t  account w ll need perm ss ons to read all t  requ red dataset. 

```bash
export SERV CE_ACCOUNT='recos-platform'
export GCP_PROJECT_NAME='twttr-recos-ml-prod'

b n/d6w sc dule \
  ${GCP_PROJECT_NAME}/us-central1/assemble-mult -type-graph-sc o-batch-app \
  src/scala/com/tw ter/s mclusters_v2/sc o/mult _type_graph/assemble_mult _type_graph/assemble-mult -type-graph-sc o-batch.d6w \
  --b nd=prof le.project=${GCP_PROJECT_NAME} \
  --b nd=prof le.user_na ="recos-platform" \
  --b nd=prof le.date="2021-11-04" \
  --b nd=prof le.mach ne="n2-h gh m-16"
```
