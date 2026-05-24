import tensorflow as tf

physical_devices = tf.config.list_physical_devices('GPU') 
for device in physical_devices:
    tf.config.experimental.set_memory_growth(device, True)

from twitter.hmli.nimbus.modeling.model_config import FeatureType, EncodingType, Feature, Model, LogType
from twitter.hmli.nimbus.modeling.feature_loader import BigQueryFeatureLoader
from twitter.cuad.representation.models.text_encoder import TextEncoder
from twitter.cuad.representation.models.optimization import create_optimizer
from twitter.hmli.nimbus.modeling.feature_encoder import FeatureEncoder

import numpy as np
import pandas as pd
import utils

cat_names = [
...
]

category_features = [Feature(name=cat_name, ftype=FeatureType.CONTINUOUS) for cat_name in cat_names]
features = [
  Feature(name="tweet_text_with_media_annotations", ftype=FeatureType.STRING, encoding=EncodingType.BERT),
  Feature(name="precision_nsfw", ftype=FeatureType.CONTINUOUS),
  Feature(name="has_media", ftype=FeatureType.BINARY),
  Feature(name="num_media", ftype=FeatureType.DISCRETE)
] + category_features

ptos_prototype = Model(
  name='ptos_prototype',
  export_path="...",
  features=features,
)
print(ptos_prototype)

cq_loader = BigQueryFeatureLoader(gcp_project=COMPUTE_PROJECT)
labels = [
  "has_non_punitive_action",
  "has_punitive_action",
  "has_punitive_action_contains_self_harm",
  "has_punitive_action_encourage_self_harm",
  "has_punitive_action_episodic",
  "has_punitive_action_episodic_hateful_conduct",
  "has_punitive_action_other_abuse_policy",
  "has_punitive_action_without_self_harm"
]

train_query = f"""
SELECT 
  {{feature_names}},
  {",".join(labels)},
...
"""
val_query = f"""
SELECT 
  {{feature_names}},
  {",".join(labels)},
...
"""

print(train_query)
train = cq_loader.load_features(ptos_prototype, "", "", custom_query=train_query)
val = cq_loader.load_features(ptos_prototype, "", "", custom_query=val_query)
print(train.describe(model=ptos_prototype))

params = {
  'max_seq_lengths': 128,
  'batch_size': 196,
  'lr': 1e-5,
  'optimizer_type': 'adamw',
  'warmup_steps': 0,
  'cls_dropout_rate': 0.1,
  'epochs': 30,
  'steps_per_epoch': 5000,
  'model_type': 'twitter_multilingual_bert_base_cased_mlm', 
  'mixed_precision': True,
}
params

def parse_labeled_data(row_dict):
  label = [row_dict.pop(l) for l in labels]
  return row_dict, label

mirrored_strategy = tf.distribute.MirroredStrategy()
BATCH_SIZE = params['batch_size'] * mirrored_strategy.num_replicas_in_sync

train_ds = train.to_tf_dataset().map(parse_labeled_data).shuffle(BATCH_SIZE*100).batch(BATCH_SIZE).repeat()
val_ds = val.to_tf_dataset().map(parse_labeled_data).batch(BATCH_SIZE)

for record in train_ds:
  tf.print(record)
  break

def get_positive_weights():
  """Computes positive weights used for class imbalance from training data."""
  label_weights_df = utils.get_label_weights(
      "tos-data-media-full",
      project_id="twttr-abusive-interact-prod",
      dataset_id="tos_policy"
  )
  pos_weight_tensor = tf.cast(
      label_weights_df.sort_values(by='label').positive_class_weight,
      dtype=tf.float32
  )
  return pos_weight_tensor

pos_weight_tensor = get_positive_weights()
print(pos_weight_tensor)

class TextEncoderPooledOutput(TextEncoder):
  def call(self, x):
    return super().call([x])["pooled_output"]  

  def get_config(self):
    return super().get_config()

with mirrored_strategy.scope():
  text_encoder_pooled_output = TextEncoderPooledOutput(
                                params['max_seq_lengths'], 
                                model_type=params['model_type'],
                                trainable=True
                              )

  fe = FeatureEncoder(train)
  inputs, preprocessing_head = fe.build_model_head(model=ptos_prototype, text_encoder=text_encoder_pooled_output)

  cls_dropout = tf.keras.layers.Dropout(params['cls_dropout_rate'], name="cls_dropout")
  outputs = cls_dropout(preprocessing_head)
  outputs = tf.keras.layers.Dense(8, name="output", dtype="float32")(outputs)

  model = tf.keras.Model(
      inputs=inputs,
      outputs=outputs
  )
  pr_auc = tf.keras.metrics.AUC(curve="PR", num_thresholds=1000, multi_label=True, from_logits=True)

  custom_loss = lambda y_true, y_pred: utils.multilabel_weighted_loss(y_true, y_pred, weights=pos_weight_tensor)
  optimizer = create_optimizer(
    init_lr=params["lr"], 
    num_train_steps=(params["epochs"] * params["steps_per_epoch"]),
    num_warmup_steps=params["warmup_steps"],
    optimizer_type=params["optimizer_type"],
  )
  if params.get("mixed_precision"):
      optimizer = tf.train.experimental.enable_mixed_precision_graph_rewrite(optimizer)
      
  model.compile(
    optimizer=optimizer,
    loss=custom_loss,
    metrics=[pr_auc]
  )

model.weights
model.summary()
pr_auc.name

import getpass
import wandb
from wandb.keras import WandbCallback
try:
  wandb_key = ...
  wandb.login(...)
  run = wandb.init(project='ptos_with_media',
             group='new-split-trains',
             notes='tweet text with only (num_media, precision_nsfw). on full train set, new split.',
             entity='absv',
             config=params,
             name='tweet-text-w-nsfw-1.1',
             sync_tensorboard=True)
except FileNotFoundError:
  print('Wandb key not found')
  run = wandb.init(mode='disabled')
import datetime
import os

start_train_time = datetime.datetime.now()
print(start_train_time.strftime("%m-%d-%Y (%H:%M:%S)"))
checkpoint_path = os.path.join("...")
print("Saving model checkpoints here: ", checkpoint_path)

cp_callback = tf.keras.callbacks.ModelCheckpoint(
  filepath=os.path.join(checkpoint_path, "model.{epoch:04d}.tf"),
  verbose=1,
  monitor=f'val_{pr_auc.name}',
  mode='max',
  save_freq='epoch',
  save_best_only=True
)

early_stopping_callback = tf.keras.callbacks.EarlyStopping(patience=7,
                                                           monitor=f"val_{pr_auc.name}",
                                                           mode="max")

model.fit(train_ds, epochs=params["epochs"], validation_data=val_ds, callbacks=[cp_callback, early_stopping_callback],
        steps_per_epoch=params["steps_per_epoch"], 
        verbose=2)

import tensorflow_hub as hub

gs_model_path = ...
reloaded_keras_layer = hub.KerasLayer(gs_model_path)
inputs = tf.keras.layers.Input(name="tweet__core__tweet__text", shape=(1,), dtype=tf.string)
output = reloaded_keras_layer(inputs)
v7_model = tf.keras.models.Model(inputs=inputs, outputs=output)
pr_auc = tf.keras.metrics.AUC(curve="PR", name="pr_auc")
roc_auc = tf.keras.metrics.AUC(curve="ROC", name="roc_auc")
v7_model.compile(metrics=[pr_auc, roc_auc])

model.load_weights("...")
candidate_model = model

with mirrored_strategy.scope():
  candidate_eval = candidate_model.evaluate(val_ds)

test_query = f"""
SELECT 
  {",".join(ptos_prototype.feature_names())},
  has_media,
  precision_nsfw,
  {",".join(labels)},
...
"""

test = cq_loader.load_features(ptos_prototype, "", "", custom_query=test_query)
test = test.to_tf_dataset().map(parse_labeled_data)

print(test)

test_only_media = test.filter(lambda x, y: tf.equal(x["has_media"], True))
test_only_nsfw = test.filter(lambda x, y: tf.greater_equal(x["precision_nsfw"], 0.95))
test_no_media = test.filter(lambda x, y: tf.equal(x["has_media"], False))
test_media_not_nsfw = test.filter(lambda x, y: tf.logical_and(tf.equal(x["has_media"], True), tf.less(x["precision_nsfw"], 0.95)))
for d in [test, test_only_media, test_only_nsfw, test_no_media, test_media_not_nsfw]:
  print(d.reduce(0, lambda x, _: x + 1).numpy())

from notebook_eval_utils import SparseMultilabelEvaluator, EvalConfig
from dataclasses import asdict

def display_metrics(probs, targets, labels=labels):
  eval_config = EvalConfig(prediction_threshold=0.5, precision_k=0.9)
  for eval_mode, y_mask in [("implicit", np.ones(targets.shape))]:
    print("Evaluation mode", eval_mode)
    metrics = SparseMultilabelEvaluator.evaluate(
        targets, np.array(probs), y_mask, classes=labels, eval_config=eval_config
    )
    metrics_df = pd.DataFrame.from_dict(asdict(metrics)["per_topic_metrics"]).transpose()
    metrics_df["pos_to_neg"] = metrics_df["num_pos_samples"] / (metrics_df["num_neg_samples"] + 1)
    display(metrics_df.median())    
    display(metrics_df)
    return metrics_df


def eval_model(model, df):
  with mirrored_strategy.scope():
    targets = np.stack(list(df.map(lambda x, y: y).as_numpy_iterator()), axis=0)
    df = df.padded_batch(BATCH_SIZE)
    preds = model.predict(df)
    return display_metrics(preds, targets)

subsets = {"test": test,
          "test_only_media": test_only_media,
          "test_only_nsfw": test_only_nsfw,
          "test_no_media": test_no_media,
          "test_media_not_nsfw": test_media_not_nsfw}

metrics = {}
for name, df in subsets.items():
  metrics[name] = eval_model(candidate_model, df)
[(name, m.pr_auc) for name, m in metrics.items()]
for name, x in [(name, m.pr_auc.to_string(index=False).strip().split("\n")) for name, m in metrics.items()]:
  print(name)
  for y in x:
    print(y.strip(), end="\t")
  print(".")
for d in [test, test_only_media, test_only_nsfw, test_no_media, test_media_not_nsfw]:
  print(d.reduce(0, lambda x, _: x + 1).numpy())