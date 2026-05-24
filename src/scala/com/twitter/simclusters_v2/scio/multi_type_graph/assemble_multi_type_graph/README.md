# Pre-requisites

## Tutorial
Follow the tutorial Batch Job on Dataflow Quickstart on how to run a simple batch job on Dataflow.

## GCP setup

Ensure `gcloud` CLI is installed and `application_default_credentials.json` has been generated.

## Data access

If you want to run an adhoc job with your ldap, you will need access to multiple LDAP groups to read the datasets.

# Running the job

### Running an adhoc job

```bash
export GCP_PROJECT_NAME='twttr-recos-ml-prod'

./bazel bundle src/scala/com/twitter/simclusters_v2/scio/multi_type_graph/assemble_multi_type_graph:assemble-multi-type-graph-scio-adhoc-app

bin/d6w create \
  ${GCP_PROJECT_NAME}/us-central1/assemble-multi-type-graph-scio-adhoc-app \
  src/scala/com/twitter/simclusters_v2/scio/multi_type_graph/assemble_multi_type_graph/assemble-multi-type-graph-scio-adhoc.d6w \
  --jar dist/assemble-multi-type-graph-scio-adho-app.jar \
  --bind=profile.project=${GCP_PROJECT_NAME} \
  --bind=profile.user_name=${USER} \
  --bind=profile.date="2021-11-04" \
  --bind=profile.machine="n2-highmem-16"
```

### Scheduling the job on Workflow

Scheduling a job will require a service account as `recos-platform`. 
Remember this account will need permissions to read all the required dataset. 

```bash
export SERVICE_ACCOUNT='recos-platform'
export GCP_PROJECT_NAME='twttr-recos-ml-prod'

bin/d6w schedule \
  ${GCP_PROJECT_NAME}/us-central1/assemble-multi-type-graph-scio-batch-app \
  src/scala/com/twitter/simclusters_v2/scio/multi_type_graph/assemble_multi_type_graph/assemble-multi-type-graph-scio-batch.d6w \
  --bind=profile.project=${GCP_PROJECT_NAME} \
  --bind=profile.user_name="recos-platform" \
  --bind=profile.date="2021-11-04" \
  --bind=profile.machine="n2-highmem-16"
```
