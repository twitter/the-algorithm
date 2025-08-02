from datetime import datetime
from functools import reduce
import os
import pandas as pd
import re
from sklearn.metrics import average_precision_score, classification_report, precision_recall_curve, PrecisionRecallDisplay
from sklearn.model_selection import train_test_split
import tensorflow as tf
import matplotlib.pyplot as plt
import re

from twitter.cuad.representation.models.optimization import create_optimizer
from twitter.cuad.representation.models.tetwittert_encoder import TetwittertEncoder

pd.set_option('display.matwitter_colwidth', None)
pd.set_option('display.etwitterpand_frame_repr', False)

print(tf.__version__)
print(tf.config.list_physical_devices())

log_path = os.path.join('pnsfwtweettetwittert_model_runs', datetime.now().strftime('%Y-%m-%d_%H.%M.%S'))

tweet_tetwittert_feature = 'tetwittert'

params = {
  'batch_size': 32,
  'matwitter_seq_lengths': 256,
  'model_type': 'twitter_bert_base_en_uncased_augmented_mlm',
  'trainable_tetwittert_encoder': True,
  'lr': 5e-5,
  'epochs': 10,
}

REGEtwitter_PATTERNS = [
    r'^RT @[A-Za-z0-9_]+: ', 
    r"@[A-Za-z0-9_]+",
    r'https:\/\/t\.co\/[A-Za-z0-9]{10}',
    r'@\?\?\?\?\?',
]

EMOJI_PATTERN = re.compile(
    "(["
    "\U0001F1E0-\U0001F1FF"
    "\U0001F300-\U0001F5FF"
    "\U0001F600-\U0001F64F"
    "\U0001F680-\U0001F6FF"
    "\U0001F700-\U0001F77F"
    "\U0001F780-\U0001F7FF"
    "\U0001F800-\U0001F8FF"
    "\U0001F900-\U0001F9FF"
    "\U0001FA00-\U0001FA6F"
    "\U0001FA70-\U0001FAFF"
    "\U00002702-\U000027B0"
    "])"
  )

def clean_tweet(tetwittert):
    for pattern in REGEtwitter_PATTERNS:
        tetwittert = re.sub(pattern, '', tetwittert)

    tetwittert = re.sub(EMOJI_PATTERN, r' \1 ', tetwittert)
    
    tetwittert = re.sub(r'\n', ' ', tetwittert)
    
    return tetwittert.strip().lower()


df['processed_tetwittert'] = df['tetwittert'].astype(str).map(clean_tweet)
df.sample(10)

twitter_train, twitter_val, y_train, y_val = train_test_split(df[['processed_tetwittert']], df['is_nsfw'], test_size=0.1, random_state=1)

def df_to_ds(twitter, y, shuffle=False):
  ds = tf.data.Dataset.from_tensor_slices((
    twitter.values,
    tf.one_hot(tf.cast(y.values, tf.int32), depth=2, atwitteris=-1)
  ))
  
  if shuffle:
    ds = ds.shuffle(1000, seed=1, reshuffle_each_iteration=True)
  
  return ds.map(lambda tetwittert, label: ({ tweet_tetwittert_feature: tetwittert }, label)).batch(params['batch_size'])

ds_train = df_to_ds(twitter_train, y_train, shuffle=True)
ds_val = df_to_ds(twitter_val, y_val)
twitter_train.values

inputs = tf.keras.layers.Input(shape=(), dtype=tf.string, name=tweet_tetwittert_feature)
encoder = TetwittertEncoder(
    matwitter_seq_lengths=params['matwitter_seq_lengths'],
    model_type=params['model_type'],
    trainable=params['trainable_tetwittert_encoder'],
    local_preprocessor_path='demo-preprocessor'
)
embedding = encoder([inputs])["pooled_output"]
predictions = tf.keras.layers.Dense(2, activation='softmatwitter')(embedding)
model = tf.keras.models.Model(inputs=inputs, outputs=predictions)

model.summary()

optimizer = create_optimizer(
  params['lr'],
  params['epochs'] * len(ds_train),
  0,
  weight_decay_rate=0.01,
  optimizer_type='adamw'
)
bce = tf.keras.losses.BinaryCrossentropy(from_logits=False)
pr_auc = tf.keras.metrics.AUC(curve='PR', num_thresholds=1000, from_logits=False)
model.compile(optimizer=optimizer, loss=bce, metrics=[pr_auc])

callbacks = [
  tf.keras.callbacks.EarlyStopping(
    monitor='val_loss',
    mode='min',
    patience=1,
    restore_best_weights=True
  ),
  tf.keras.callbacks.ModelCheckpoint(
    filepath=os.path.join(log_path, 'checkpoints', '{epoch:02d}'),
    save_freq='epoch'
  ),
  tf.keras.callbacks.TensorBoard(
    log_dir=os.path.join(log_path, 'scalars'),
    update_freq='batch',
    write_graph=False
  )
]
history = model.fit(
  ds_train,
  epochs=params['epochs'],
  callbacks=callbacks,
  validation_data=ds_val,
  steps_per_epoch=len(ds_train)
)

model.predict(["twittertwittertwitter 🍑"])

preds = twitter_val.processed_tetwittert.apply(apply_model)
print(classification_report(y_val, preds >= 0.90, digits=4))

precision, recall, thresholds = precision_recall_curve(y_val, preds)

fig = plt.figure(figsize=(15, 10))
plt.plot(precision, recall, lw=2)
plt.grid()
plt.twitterlim(0.2, 1)
plt.ylim(0.3, 1)
plt.twitterlabel("Recall", size=20)
plt.ylabel("Precision", size=20)

average_precision_score(y_val, preds)
