import kerastuner as kt
import math
import numpy as np
import pandas as pd
import random
import sklearn.metrics
import tensorflow as tf
import os
import glob

from tqdm import tqdm
from matplotlib import pyplot as plt
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense
from google.cloud import storage

physical_devices = tf.config.list_physical_devices('GPU')
physical_devices

tf.config.set_visible_devices([tf.config.PhysicalDevice(name='/physical_device:GPU:1', device_type='GPU')], 'GPU')
tf.config.get_visible_devices('GPU')

def decode_fn_embedding(example_proto):
  
  feature_description = {
    "embedding": tf.io.FixedLenFeature([256], dtype=tf.float32),
    "labels": tf.io.FixedLenFeature([], dtype=tf.int64),
  }
  
  example = tf.io.parse_single_example(
      example_proto,
      feature_description
  )

  return example

def preprocess_embedding_example(example_dict, positive_label=1, features_as_dict=False):
  labels = example_dict["labels"]
  label = tf.math.reduce_any(labels == positive_label)
  label = tf.cast(label, tf.int32)
  embedding = example_dict["embedding"]
  
  if features_as_dict:
    features = {"embedding": embedding}
  else:
    features = embedding
    
  return features, label
input_root = ...
sens_prev_input_root = ...

use_sens_prev_data = True
has_validation_data = True
positive_label = 1

train_batch_size = 256
test_batch_size = 256
validation_batch_size = 256

do_resample = False
def class_func(features, label):
  return label

resample_fn = tf.data.experimental.rejection_resample(
    class_func, target_dist = [0.5, 0.5], seed=0
)
train_glob = f"{input_root}/train/tfrecord/*.tfrecord"
train_files = tf.io.gfile.glob(train_glob)

if use_sens_prev_data:
  train_sens_prev_glob = f"{sens_prev_input_root}/train/tfrecord/*.tfrecord"
  train_sens_prev_files = tf.io.gfile.glob(train_sens_prev_glob)
  train_files = train_files + train_sens_prev_files
  
random.shuffle(train_files)

if not len(train_files):
  raise ValueError(f"Did not find any train files matching {train_glob}")


test_glob = f"{input_root}/test/tfrecord/*.tfrecord"
test_files =  tf.io.gfile.glob(test_glob)

if not len(test_files):
  raise ValueError(f"Did not find any eval files matching {test_glob}")
  
test_ds = tf.data.TFRecordDataset(test_files).map(decode_fn_embedding)
test_ds = test_ds.map(lambda x: preprocess_embedding_example(x, positive_label=positive_label)).batch(batch_size=test_batch_size)
  
if use_sens_prev_data:
  test_sens_prev_glob = f"{sens_prev_input_root}/test/tfrecord/*.tfrecord"
  test_sens_prev_files =  tf.io.gfile.glob(test_sens_prev_glob)
  
  if not len(test_sens_prev_files):
    raise ValueError(f"Did not find any eval files matching {test_sens_prev_glob}")
  
  test_sens_prev_ds = tf.data.TFRecordDataset(test_sens_prev_files).map(decode_fn_embedding)
  test_sens_prev_ds = test_sens_prev_ds.map(lambda x: preprocess_embedding_example(x, positive_label=positive_label)).batch(batch_size=test_batch_size)

train_ds = tf.data.TFRecordDataset(train_files).map(decode_fn_embedding)
train_ds = train_ds.map(lambda x: preprocess_embedding_example(x, positive_label=positive_label))

if do_resample:
  train_ds = train_ds.apply(resample_fn).map(lambda _,b:(b))

train_ds = train_ds.batch(batch_size=256).shuffle(buffer_size=10)
train_ds = train_ds.repeat()
  

if has_validation_data: 
  eval_glob = f"{input_root}/validation/tfrecord/*.tfrecord"
  eval_files =  tf.io.gfile.glob(eval_glob)
    
  if use_sens_prev_data:
    eval_sens_prev_glob = f"{sens_prev_input_root}/validation/tfrecord/*.tfrecord"
    eval_sens_prev_files = tf.io.gfile.glob(eval_sens_prev_glob)
    eval_files =  eval_files + eval_sens_prev_files
    
    
  if not len(eval_files):
    raise ValueError(f"Did not find any eval files matching {eval_glob}")
  
  eval_ds = tf.data.TFRecordDataset(eval_files).map(decode_fn_embedding)
  eval_ds = eval_ds.map(lambda x: preprocess_embedding_example(x, positive_label=positive_label)).batch(batch_size=validation_batch_size)

else:
  
  eval_ds = tf.data.TFRecordDataset(test_files).map(decode_fn_embedding)
  eval_ds = eval_ds.map(lambda x: preprocess_embedding_example(x, positive_label=positive_label)).batch(batch_size=validation_batch_size)
check_ds = tf.data.TFRecordDataset(train_files).map(decode_fn_embedding)
cnt = 0
pos_cnt = 0
for example in tqdm(check_ds):
  label = example['labels']
  if label == 1:
    pos_cnt += 1
  cnt += 1
print(f'{cnt} train entries with {pos_cnt} positive')

metrics = []

metrics.append(
  tf.keras.metrics.PrecisionAtRecall(
    recall=0.9, num_thresholds=200, class_id=None, name=None, dtype=None
  )
)

metrics.append(
  tf.keras.metrics.AUC(
    num_thresholds=200,
    curve="PR",
  )
)
def build_model(hp):
  model = Sequential()

  optimizer = tf.keras.optimizers.Adam(
    learning_rate=0.001,
    beta_1=0.9,
    beta_2=0.999,
    epsilon=1e-08,
    amsgrad=False,
    name="Adam",
  )
  
  activation=hp.Choice("activation", ["tanh", "gelu"])
  kernel_initializer=hp.Choice("kernel_initializer", ["he_uniform", "glorot_uniform"])
  for i in range(hp.Int("num_layers", 1, 2)):
    model.add(tf.keras.layers.BatchNormalization())

    units=hp.Int("units", min_value=128, max_value=256, step=128)
    
    if i == 0:
      model.add(
        Dense(
          units=units,
          activation=activation,
          kernel_initializer=kernel_initializer,
          input_shape=(None, 256)
        )
      )
    else:
      model.add(
        Dense(
          units=units,
          activation=activation,
          kernel_initializer=kernel_initializer,
        )
      )
    
  model.add(Dense(1, activation='sigmoid', kernel_initializer=kernel_initializer))
  model.compile(optimizer=optimizer, loss='binary_crossentropy', metrics=metrics)

  return model

tuner = kt.tuners.BayesianOptimization(
  build_model,
  objective=kt.Objective('val_loss', direction="min"),
  max_trials=30,
  directory='tuner_dir',
  project_name='with_twitter_clip')

callbacks = [tf.keras.callbacks.EarlyStopping(
    monitor='val_loss', min_delta=0, patience=5, verbose=0,
    mode='auto', baseline=None, restore_best_weights=True
)]

steps_per_epoch = 400
tuner.search(train_ds,
             epochs=100,
             batch_size=256,
             steps_per_epoch=steps_per_epoch,
             verbose=2,
             validation_data=eval_ds,
             callbacks=callbacks)

tuner.results_summary()
models = tuner.get_best_models(num_models=2)
best_model = models[0]

best_model.build(input_shape=(None, 256))
best_model.summary()

tuner.get_best_hyperparameters()[0].values

optimizer = tf.keras.optimizers.Adam(
    learning_rate=0.001,
    beta_1=0.9,
    beta_2=0.999,
    epsilon=1e-08,
    amsgrad=False,
    name="Adam",
  )
best_model.compile(optimizer=optimizer, loss='binary_crossentropy', metrics=metrics)
best_model.summary()

callbacks = [tf.keras.callbacks.EarlyStopping(
    monitor='val_loss', min_delta=0, patience=10, verbose=0,
    mode='auto', baseline=None, restore_best_weights=True
)]
history = best_model.fit(train_ds, epochs=100, validation_data=eval_ds, steps_per_epoch=steps_per_epoch, callbacks=callbacks)

model_name = 'twitter_hypertuned'
model_path = f'models/nsfw_Keras_with_CLIP_{model_name}'
tf.keras.models.save_model(best_model, model_path)

def copy_local_directory_to_gcs(local_path, bucket, gcs_path):
    """Recursively copy a directory of files to GCS.

    local_path should be a directory and not have a trailing slash.
    """
    assert os.path.isdir(local_path)
    for local_file in glob.glob(local_path + '/**'):
        if not os.path.isfile(local_file):
            dir_name = os.path.basename(os.path.normpath(local_file))
            copy_local_directory_to_gcs(local_file, bucket, f"{gcs_path}/{dir_name}")
        else:
          remote_path = os.path.join(gcs_path, local_file[1 + len(local_path) :])
          blob = bucket.blob(remote_path)
          blob.upload_from_filename(local_file)

client = storage.Client(project=...)
bucket = client.get_bucket(...)
copy_local_directory_to_gcs(model_path, bucket, model_path)
copy_local_directory_to_gcs('tuner_dir', bucket, 'tuner_dir')
loaded_model = tf.keras.models.load_model(model_path)
print(history.history.keys())

plt.figure(figsize = (20, 5))

plt.subplot(1, 3, 1)
plt.plot(history.history['auc'])
plt.plot(history.history['val_auc'])
plt.title('model auc')
plt.ylabel('auc')
plt.xlabel('epoch')
plt.legend(['train', 'test'], loc='upper left')

plt.subplot(1, 3, 2)
plt.plot(history.history['loss'])
plt.plot(history.history['val_loss'])
plt.title('model loss')
plt.ylabel('loss')
plt.xlabel('epoch')
plt.legend(['train', 'test'], loc='upper left')

plt.subplot(1, 3, 3)
plt.plot(history.history['precision_at_recall'])
plt.plot(history.history['val_precision_at_recall'])
plt.title('model precision at 0.9 recall')
plt.ylabel('precision_at_recall')
plt.xlabel('epoch')
plt.legend(['train', 'test'], loc='upper left')

plt.savefig('history_with_twitter_clip.pdf')

test_labels = []
test_preds = []

for batch_features, batch_labels in tqdm(test_ds):
  test_preds.extend(loaded_model.predict_proba(batch_features))
  test_labels.extend(batch_labels.numpy())
  
test_sens_prev_labels = []
test_sens_prev_preds = []

for batch_features, batch_labels in tqdm(test_sens_prev_ds):
  test_sens_prev_preds.extend(loaded_model.predict_proba(batch_features))
  test_sens_prev_labels.extend(batch_labels.numpy())
  
n_test_pos = 0
n_test_neg = 0
n_test = 0

for label in test_labels:
  n_test +=1
  if label == 1:
    n_test_pos +=1
  else:
    n_test_neg +=1

print(f'n_test = {n_test}, n_pos = {n_test_pos}, n_neg = {n_test_neg}')

n_test_sens_prev_pos = 0
n_test_sens_prev_neg = 0
n_test_sens_prev = 0

for label in test_sens_prev_labels:
  n_test_sens_prev +=1
  if label == 1:
    n_test_sens_prev_pos +=1
  else:
    n_test_sens_prev_neg +=1

print(f'n_test_sens_prev = {n_test_sens_prev}, n_pos_sens_prev = {n_test_sens_prev_pos}, n_neg = {n_test_sens_prev_neg}')

test_weights = np.ones(np.asarray(test_preds).shape)

test_labels = np.asarray(test_labels)
test_preds = np.asarray(test_preds)
test_weights = np.asarray(test_weights)

pr = sklearn.metrics.precision_recall_curve(
  test_labels, 
  test_preds)

auc = sklearn.metrics.auc(pr[1], pr[0])
plt.plot(pr[1], pr[0])
plt.title("nsfw (MU test set)")

test_sens_prev_weights = np.ones(np.asarray(test_sens_prev_preds).shape)

test_sens_prev_labels = np.asarray(test_sens_prev_labels)
test_sens_prev_preds = np.asarray(test_sens_prev_preds)
test_sens_prev_weights = np.asarray(test_sens_prev_weights)

pr_sens_prev = sklearn.metrics.precision_recall_curve(
  test_sens_prev_labels, 
  test_sens_prev_preds)

auc_sens_prev = sklearn.metrics.auc(pr_sens_prev[1], pr_sens_prev[0])
plt.plot(pr_sens_prev[1], pr_sens_prev[0])
plt.title("nsfw (sens prev test set)")

df = pd.DataFrame(
  {
    "label": test_labels.squeeze(), 
    "preds_keras": np.asarray(test_preds).flatten(),
  })
plt.figure(figsize=(15, 10))
df["preds_keras"].hist()
plt.title("Keras predictions", size=20)
plt.xlabel('score')
plt.ylabel("freq")

plt.figure(figsize = (20, 5))
plt.subplot(1, 3, 1)

plt.plot(pr[2], pr[0][0:-1])
plt.xlabel("threshold")
plt.ylabel("precision")

plt.subplot(1, 3, 2)

plt.plot(pr[2], pr[1][0:-1])
plt.xlabel("threshold")
plt.ylabel("recall")
plt.title("Keras", size=20)

plt.subplot(1, 3, 3)

plt.plot(pr[1], pr[0])
plt.xlabel("recall")
plt.ylabel("precision")

plt.savefig('with_twitter_clip.pdf')

def get_point_for_recall(recall_value, recall, precision):
  idx = np.argmin(np.abs(recall - recall_value))
  return (recall[idx], precision[idx])

def get_point_for_precision(precision_value, recall, precision):
  idx = np.argmin(np.abs(precision - precision_value))
  return (recall[idx], precision[idx])
precision, recall, thresholds = pr

auc_precision_recall = sklearn.metrics.auc(recall, precision)

print(auc_precision_recall)

plt.figure(figsize=(15, 10))
plt.plot(recall, precision)

plt.xlabel("recall")
plt.ylabel("precision")

ptAt50 = get_point_for_recall(0.5, recall, precision)
print(ptAt50)
plt.plot( [ptAt50[0],ptAt50[0]], [0,ptAt50[1]], 'r')
plt.plot([0, ptAt50[0]], [ptAt50[1], ptAt50[1]], 'r')

ptAt90 = get_point_for_recall(0.9, recall, precision)
print(ptAt90)
plt.plot( [ptAt90[0],ptAt90[0]], [0,ptAt90[1]], 'b')
plt.plot([0, ptAt90[0]], [ptAt90[1], ptAt90[1]], 'b')

ptAt50fmt = "%.4f" % ptAt50[1]
ptAt90fmt = "%.4f" % ptAt90[1]
aucFmt = "%.4f" % auc_precision_recall
plt.title(
  f"Keras (nsfw MU test)\nAUC={aucFmt}\np={ptAt50fmt} @ r=0.5\np={ptAt90fmt} @ r=0.9\nN_train={...}} ({...} pos), N_test={n_test} ({n_test_pos} pos)",
  size=20
)
plt.subplots_adjust(top=0.72)
plt.savefig('recall_precision_nsfw_Keras_with_twitter_CLIP_MU_test.pdf')

precision, recall, thresholds = pr_sens_prev

auc_precision_recall = sklearn.metrics.auc(recall, precision)
print(auc_precision_recall)
plt.figure(figsize=(15, 10))

plt.plot(recall, precision)

plt.xlabel("recall")
plt.ylabel("precision")

ptAt50 = get_point_for_recall(0.5, recall, precision)
print(ptAt50)
plt.plot( [ptAt50[0],ptAt50[0]], [0,ptAt50[1]], 'r')
plt.plot([0, ptAt50[0]], [ptAt50[1], ptAt50[1]], 'r')

ptAt90 = get_point_for_recall(0.9, recall, precision)
print(ptAt90)
plt.plot( [ptAt90[0],ptAt90[0]], [0,ptAt90[1]], 'b')
plt.plot([0, ptAt90[0]], [ptAt90[1], ptAt90[1]], 'b')

ptAt50fmt = "%.4f" % ptAt50[1]
ptAt90fmt = "%.4f" % ptAt90[1]
aucFmt = "%.4f" % auc_precision_recall
plt.title(
  f"Keras (nsfw sens prev test)\nAUC={aucFmt}\np={ptAt50fmt} @ r=0.5\np={ptAt90fmt} @ r=0.9\nN_train={...} ({...} pos), N_test={n_test_sens_prev} ({n_test_sens_prev_pos} pos)",
  size=20
)
plt.subplots_adjust(top=0.72)
plt.savefig('recall_precision_nsfw_Keras_with_twitter_CLIP_sens_prev_test.pdf')