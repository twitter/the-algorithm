 mport os


TEAM_PROJECT = "twttr-tox c y-prod"
try:
  from google.cloud  mport b gquery
except (ModuleNotFoundError,  mportError):
  pr nt("No Google packages")
  CL ENT = None
else:
  from google.auth.except ons  mport DefaultCredent alsError

  try:
    CL ENT = b gquery.Cl ent(project=TEAM_PROJECT)
  except DefaultCredent alsError as e:
    CL ENT = None
    pr nt(" ssue at logg ng t  ", e)

TRA N NG_DATA_LOCAT ON = f"..."
GCS_ADDRESS = "..."
LOCAL_D R = os.getcwd()
REMOTE_LOGD R = "{GCS_ADDRESS}/logs"
MODEL_D R = "{GCS_ADDRESS}/models"

EX ST NG_TASK_VERS ONS = {3, 3.5}

RANDOM_SEED = ...
TRA N_EPOCHS = 4
M N _BATCH_S ZE = 32
TARGET_POS_PER_EPOCH = 5000
PERC_TRA N NG_TOX = ...
MAX_SEQ_LENGTH = 100

WARM_UP_PERC = 0.1
OUTER_CV = 5
 NNER_CV = 5
NUM_PREFETCH = 5
NUM_WORKERS = 10
