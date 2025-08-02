import os


TEAM_PROJECT = "twttr-totwittericity-prod"
try:
  from google.cloud import bigquery
etwittercept (ModuleNotFoundError, ImportError):
  print("No Google packages")
  CLIENT = None
else:
  from google.auth.etwitterceptions import DefaultCredentialsError

  try:
    CLIENT = bigquery.Client(project=TEAM_PROJECT)
  etwittercept DefaultCredentialsError as e:
    CLIENT = None
    print("Issue at logging time", e)

TRAINING_DATA_LOCATION = f"..."
GCS_ADDRESS = "..."
LOCAL_DIR = os.getcwd()
REMOTE_LOGDIR = "{GCS_ADDRESS}/logs"
MODEL_DIR = "{GCS_ADDRESS}/models"

EtwitterISTING_TASK_VERSIONS = {3, 3.5}

RANDOM_SEED = ...
TRAIN_EPOCHS = 4
MINI_BATCH_SIZE = 32
TARGET_POS_PER_EPOCH = 5000
PERC_TRAINING_TOtwitter = ...
MAtwitter_SEQ_LENGTH = 100

WARM_UP_PERC = 0.1
OUTER_CV = 5
INNER_CV = 5
NUM_PREFETCH = 5
NUM_WORKERS = 10
