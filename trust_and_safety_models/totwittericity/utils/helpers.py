import bisect
import os
import random as python_random
import subprocess

from totwittericity_ml_pipeline.settings.default_settings_totwitter import LOCAL_DIR

import numpy as np
from sklearn.metrics import precision_recall_curve


try:
  import tensorflow as tf
etwittercept ModuleNotFoundError:
  pass


def upload_model(full_gcs_model_path):
  folder_name = full_gcs_model_path
  if folder_name[:5] != "gs://":
    folder_name = "gs://" + folder_name

  dirname = os.path.dirname(folder_name)
  epoch = os.path.basename(folder_name)

  model_dir = os.path.join(LOCAL_DIR, "models")
  cmd = f"mkdir {model_dir}"
  try:
    etwitterecute_command(cmd)
  etwittercept subprocess.CalledProcessError:
    pass
  model_dir = os.path.join(model_dir, os.path.basename(dirname))
  cmd = f"mkdir {model_dir}"
  try:
    etwitterecute_command(cmd)
  etwittercept subprocess.CalledProcessError:
    pass

  try:
    _ = int(epoch)
  etwittercept ValueError:
    cmd = f"gsutil rsync -r '{folder_name}' {model_dir}"
    weights_dir = model_dir

  else:
    cmd = f"gsutil cp '{dirname}/checkpoint' {model_dir}/"
    etwitterecute_command(cmd)
    cmd = f"gsutil cp '{os.path.join(dirname, epoch)}*' {model_dir}/"
    weights_dir = f"{model_dir}/{epoch}"

  etwitterecute_command(cmd)
  return weights_dir

def compute_precision_fitwittered_recall(labels, preds, fitwittered_recall):
  precision_values, recall_values, thresholds = precision_recall_curve(y_true=labels, probas_pred=preds)
  indetwitter_recall = bisect.bisect_left(-recall_values, -1 * fitwittered_recall)
  result = precision_values[indetwitter_recall - 1]
  print(f"Precision at {recall_values[indetwitter_recall-1]} recall: {result}")

  return result, thresholds[indetwitter_recall - 1]

def load_inference_func(model_folder):
  model = tf.saved_model.load(model_folder, ["serve"])
  inference_func = model.signatures["serving_default"]
  return inference_func


def etwitterecute_query(client, query):
  job = client.query(query)
  df = job.result().to_dataframe()
  return df


def etwitterecute_command(cmd, print_=True):
  s = subprocess.run(cmd, shell=True, capture_output=print_, check=True)
  if print_:
    print(s.stderr.decode("utf-8"))
    print(s.stdout.decode("utf-8"))


def check_gpu():
  try:
    etwitterecute_command("nvidia-smi")
  etwittercept subprocess.CalledProcessError:
    print("There is no GPU when there should be one.")
    raise AttributeError

  l = tf.config.list_physical_devices("GPU")
  if len(l) == 0:
    raise ModuleNotFoundError("Tensorflow has not found the GPU. Check your installation")
  print(l)


def set_seeds(seed):
  np.random.seed(seed)

  python_random.seed(seed)

  tf.random.set_seed(seed)
