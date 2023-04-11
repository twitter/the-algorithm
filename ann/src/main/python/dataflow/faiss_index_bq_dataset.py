import argparse
import logging
import os
import pkgutil
import sys
from urllib.parse import urlsplit

import apache_beam as beam
from apache_beam.options.pipeline_options import PipelineOptions
import faiss


def parse_d6w_config(argv=None):
  """Parse d6w config.
  :param argv: d6w config
  :return: dictionary containing d6w config
  """

  parser = argparse.ArgumentParser(
    description="See https://docbird.twitter.biz/d6w/model.html for any parameters inherited from d6w job config"
  )
  parser.add_argument("--job_name", dest="job_name", required=True, help="d6w attribute")
  parser.add_argument("--project", dest="project", required=True, help="d6w attribute")
  parser.add_argument(
    "--staging_location", dest="staging_location", required=True, help="d6w attribute"
  )
  parser.add_argument("--temp_location", dest="temp_location", required=True, help="d6w attribute")
  parser.add_argument(
    "--output_location",
    dest="output_location",
    required=True,
    help="GCS bucket and path where resulting artifacts are uploaded",
  )
  parser.add_argument(
    "--service_account_email", dest="service_account_email", required=True, help="d6w attribute"
  )
  parser.add_argument(
    "--factory_string",
    dest="factory_string",
    required=False,
    help="FAISS factory string describing index to build. See https://github.com/facebookresearch/faiss/wiki/The-index-factory",
  )
  parser.add_argument(
    "--metric",
    dest="metric",
    required=True,
    help="Metric used to compute distance between embeddings. Valid values are 'l2', 'ip', 'l1', 'linf'",
  )
  parser.add_argument(
    "--use_gpu",
    dest="gpu",
    required=True,
    help="--use_gpu=yes if you want to use GPU during index building",
  )

  known_args, unknown_args = parser.parse_known_args(argv)
  d6w_config = vars(known_args)
  d6w_config["gpu"] = d6w_config["gpu"].lower() == "yes"
  d6w_config["metric"] = parse_metric(d6w_config)

  """
  WARNING: Currently, d6w (a Twitter tool used to deploy Dataflow jobs to GCP) and
  PipelineOptions.for_dataflow_runner (a helper method in twitter.ml.common.apache_beam) do not
  play nicely together. The helper method will overwrite some of the config specified in the d6w
  file using the defaults in https://sourcegraph.twitter.biz/git.twitter.biz/source/-/blob/src/python/twitter/ml/common/apache_beam/__init__.py?L24.'
  However, the d6w output message will still report that the config specified in the d6w file was used.
  """
  logging.warning(
    f"The following d6w config parameters will be overwritten by the defaults in "
    f"https://sourcegraph.twitter.biz/git.twitter.biz/source/-/blob/src/python/twitter/ml/common/apache_beam/__init__.py?L24\n"
    f"{str(unknown_args)}"
  )
  return d6w_config


def get_bq_query():
  """
  Query is expected to return rows with unique entityId
  """
  return pkgutil.get_data(__name__, "bq.sql").decode("utf-8")


def parse_metric(config):
  metric_str = config["metric"].lower()
  if metric_str == "l2":
    return faiss.METRIC_L2
  elif metric_str == "ip":
    return faiss.METRIC_INNER_PRODUCT
  elif metric_str == "l1":
    return faiss.METRIC_L1
  elif metric_str == "linf":
    return faiss.METRIC_Linf
  else:
    raise Exception(f"Unknown metric: {metric_str}")


def run_pipeline(argv=[]):
  config = parse_d6w_config(argv)
  argv_with_extras = argv
  if config["gpu"]:
    argv_with_extras.extend(["--experiments", "use_runner_v2"])
    argv_with_extras.extend(
      ["--experiments", "worker_accelerator=type:nvidia-tesla-t4;count:1;install-nvidia-driver"]
    )
    argv_with_extras.extend(
      [
        "--worker_harness_container_image",
        "gcr.io/twttr-recos-ml-prod/dataflow-gpu/beam2_39_0_py3_7",
      ]
    )

  options = PipelineOptions(argv_with_extras)
  output_bucket_name = urlsplit(config["output_location"]).netloc

  with beam.Pipeline(options=options) as p:
    input_data = p | "Read from BigQuery" >> beam.io.ReadFromBigQuery(
      method=beam.io.ReadFromBigQuery.Method.DIRECT_READ,
      query=get_bq_query(),
      use_standard_sql=True,
    )

    index_built = input_data | "Build and upload index" >> beam.CombineGlobally(
      MergeAndBuildIndex(
        output_bucket_name,
        config["output_location"],
        config["factory_string"],
        config["metric"],
        config["gpu"],
      )
    )

    # Make linter happy
    index_built


class MergeAndBuildIndex(beam.CombineFn):
  def __init__(self, bucket_name, gcs_output_path, factory_string, metric, gpu):
    self.bucket_name = bucket_name
    self.gcs_output_path = gcs_output_path
    self.factory_string = factory_string
    self.metric = metric
    self.gpu = gpu

  def create_accumulator(self):
    return []

  def add_input(self, accumulator, element):
    accumulator.append(element)
    return accumulator

  def merge_accumulators(self, accumulators):
    merged = []
    for accum in accumulators:
      merged.extend(accum)
    return merged

  def extract_output(self, rows):
    # Reimports are needed on workers
    import glob
    import subprocess

    import faiss
    from google.cloud import storage
    import numpy as np

    client = storage.Client()
    bucket = client.get_bucket(self.bucket_name)

    logging.info("Building FAISS index")
    logging.info(f"There are {len(rows)} rows")

    ids = np.array([x["entityId"] for x in rows]).astype("long")
    embeds = np.array([x["embedding"] for x in rows]).astype("float32")
    dimensions = len(embeds[0])
    N = ids.shape[0]
    logging.info(f"There are {dimensions} dimensions")

    if self.factory_string is None:
      M = 48

      divideable_dimensions = (dimensions // M) * M
      if divideable_dimensions != dimensions:
        opq_prefix = f"OPQ{M}_{divideable_dimensions}"
      else:
        opq_prefix = f"OPQ{M}"

      clusters = N // 20
      self.factory_string = f"{opq_prefix},IVF{clusters},PQ{M}"

    logging.info(f"Factory string is {self.factory_string}, metric={self.metric}")

    if self.gpu:
      logging.info("Using GPU")

      res = faiss.StandardGpuResources()
      cpu_index = faiss.index_factory(dimensions, self.factory_string, self.metric)
      cpu_index = faiss.IndexIDMap(cpu_index)
      gpu_index = faiss.index_cpu_to_gpu(res, 0, cpu_index)
      gpu_index.train(embeds)
      gpu_index.add_with_ids(embeds, ids)
      cpu_index = faiss.index_gpu_to_cpu(gpu_index)
    else:
      logging.info("Using CPU")

      cpu_index = faiss.index_factory(dimensions, self.factory_string, self.metric)
      cpu_index = faiss.IndexIDMap(cpu_index)
      cpu_index.train(embeds)
      cpu_index.add_with_ids(embeds, ids)

    logging.info("Built faiss index")

    local_path = "/indices"
    logging.info(f"Writing indices to local {local_path}")
    subprocess.run(f"mkdir -p {local_path}".strip().split())
    local_index_path = os.path.join(local_path, "result.index")

    faiss.write_index(cpu_index, local_index_path)
    logging.info(f"Done writing indices to local {local_path}")

    logging.info(f"Uploading to GCS with path {self.gcs_output_path}")
    assert os.path.isdir(local_path)
    for local_file in glob.glob(local_path + "/*"):
      remote_path = os.path.join(
        self.gcs_output_path.split("/")[-1], local_file[1 + len(local_path) :]
      )
      blob = bucket.blob(remote_path)
      blob.upload_from_filename(local_file)


if __name__ == "__main__":
  logging.getLogger().setLevel(logging.INFO)
  run_pipeline(sys.argv)
