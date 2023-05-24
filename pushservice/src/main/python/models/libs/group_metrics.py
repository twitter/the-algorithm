import os
import time

from twitter.cortex.ml.embeddings.deepbird.grouped_metrics.computation import (
  write_grouped_metrics_to_mldash,
)
from twitter.cortex.ml.embeddings.deepbird.grouped_metrics.configuration import (
  ClassificationGroupedMetricsConfiguration,
  NDCGGroupedMetricsConfiguration,
)
import twml

from .light_ranking_metrics import (
  CGRGroupedMetricsConfiguration,
  ExpectedLossGroupedMetricsConfiguration,
  RecallGroupedMetricsConfiguration,
)

import numpy as np
import tensorflow.compat.v1 as tf
from tensorflow.compat.v1 import logging


# checkstyle: noqa


def run_group_metrics(trainer, data_dir, model_path, parse_fn, group_feature_name="meta.user_id"):

  start_time = time.time()
  logging.info("Evaluating with group metrics.")

  metrics = write_grouped_metrics_to_mldash(
    trainer=trainer,
    data_dir=data_dir,
    model_path=model_path,
    group_fn=lambda datarecord: str(
      datarecord.discreteFeatures[twml.feature_id(group_feature_name)[0]]
    ),
    parse_fn=parse_fn,
    metric_configurations=[
      ClassificationGroupedMetricsConfiguration(),
      NDCGGroupedMetricsConfiguration(k=[5, 10, 20]),
    ],
    total_records_to_read=1000000000,
    shuffle=False,
    mldash_metrics_name="grouped_metrics",
  )

  end_time = time.time()
  logging.info(f"Evaluated Group Metics: {metrics}.")
  logging.info(f"Group metrics evaluation time {end_time - start_time}.")


def run_group_metrics_light_ranking(
  trainer, data_dir, model_path, parse_fn, group_feature_name="meta.trace_id"
):

  start_time = time.time()
  logging.info("Evaluating with group metrics.")

  metrics = write_grouped_metrics_to_mldash(
    trainer=trainer,
    data_dir=data_dir,
    model_path=model_path,
    group_fn=lambda datarecord: str(
      datarecord.discreteFeatures[twml.feature_id(group_feature_name)[0]]
    ),
    parse_fn=parse_fn,
    metric_configurations=[
      CGRGroupedMetricsConfiguration(lightNs=[50, 100, 200], heavyKs=[1, 3, 10, 20, 50]),
      RecallGroupedMetricsConfiguration(n=[50, 100, 200], k=[1, 3, 10, 20, 50]),
      ExpectedLossGroupedMetricsConfiguration(lightNs=[50, 100, 200]),
    ],
    total_records_to_read=10000000,
    num_batches_to_load=50,
    batch_size=1024,
    shuffle=False,
    mldash_metrics_name="grouped_metrics_for_light_ranking",
  )

  end_time = time.time()
  logging.info(f"Evaluated Group Metics for Light Ranking: {metrics}.")
  logging.info(f"Group metrics evaluation time {end_time - start_time}.")


def run_group_metrics_light_ranking_in_bq(trainer, params, checkpoint_path):
  logging.info("getting Test Predictions for Light Ranking Group Metrics in BigQuery !!!")
  eval_input_fn = trainer.get_eval_input_fn(repeat=False, shuffle=False)
  info_pool = []

  for result in trainer.estimator.predict(
    eval_input_fn, checkpoint_path=checkpoint_path, yield_single_examples=False
  ):
    traceID = result["trace_id"]
    pred = result["prediction"]
    label = result["target"]
    info = np.concatenate([traceID, pred, label], axis=1)
    info_pool.append(info)

  info_pool = np.concatenate(info_pool)

  locname = "/tmp/000/"
  if not os.path.exists(locname):
    os.makedirs(locname)

  locfile = locname + params.pred_file_name
  columns = ["trace_id", "model_prediction", "meta__ranking__weighted_oonc_model_score"]
  np.savetxt(locfile, info_pool, delimiter=",", header=",".join(columns))
  tf.io.gfile.copy(locfile, params.pred_file_path + params.pred_file_name, overwrite=True)

  if os.path.isfile(locfile):
    os.remove(locfile)

  logging.info("Done Prediction for Light Ranking Group Metrics in BigQuery.")
