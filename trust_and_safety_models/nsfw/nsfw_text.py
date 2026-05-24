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
from twitter.cuad.representation.models.text_encoder import TextEncoder

pd.set_option('display.max_colwidth', None)
pd.set_option('display.expand_frame_repr', False)

print(tf.__version__)
print(tf.config.list_physical_devices())

log_path = os.path.join('pnsfwtweettext_model_runs', datetime.now().strftime('%Y-%m-%d_%H.%M.%S'))

tweet_text_feature = 'text'

params = {
  'batch_size': 32,
  'max_seq_lengths': 256,
  'model_type': 'twitter_bert_base_en_uncased_augmented_mlm',
  'trainable_text_encoder': True,
  'lr': 5e-5,
  'epochs': 10,
}

REGEX_PATTERNS = [
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

def clean_tweet(text):
    for pattern in REGEX_PATTERNS:
        text = re.sub(pattern, '', text)

    text = re.sub(EMOJI_PATTERN, r' \1 ', text)
    
    text = re.sub(r'\n', ' ', text)
    
    return text.strip().lower()


df['processed_text'] = df['text'].astype(str).map(clean_tweet)
df.sample(10)

X_train, X_val, y_train, y_val = train_test_split(df[['processed_text']], df['is_nsfw'], test_size=0.1, random_state=1)

def df_to_ds(X, y, shuffle=False):
  ds = tf.data.Dataset.from_tensor_slices((
    X.values,
    tf.one_hot(tf.cast(y.values, tf.int32), depth=2, axis=-1)
  ))
  
  if shuffle:
    ds = ds.shuffle(1000, seed=1, reshuffle_each_iteration=True)
  
  return ds.map(lambda text, label: ({ tweet_text_feature: text }, label)).batch(params['batch_size'])

ds_train = df_to_ds(X_train, y_train, shuffle=True)
ds_val = df_to_ds(X_val, y_val)
X_train.values

inputs = tf.keras.layers.Input(shape=(), dtype=tf.string, name=tweet_text_feature)
encoder = TextEncoder(
    max_seq_lengths=params['max_seq_lengths'],
    model_type=params['model_type'],
    trainable=params['trainable_text_encoder'],
    local_preprocessor_path='demo-preprocessor'
)
embedding = encoder([inputs])["pooled_output"]
predictions = tf.keras.layers.Dense(2, activation='softmax')(embedding)
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

model.predict(["xxx 🍑"])

preds = X_val.processed_text.apply(apply_model)
print(classification_report(y_val, preds >= 0.90, digits=4))

precision, recall, thresholds = precision_recall_curve(y_val, preds)

fig = plt.figure(figsize=(15, 10))
plt.plot(precision, recall, lw=2)
plt.grid()
plt.xlim(0.2, 1)
plt.ylim(0.3, 1)
plt.xlabel("Recall", size=20)
plt.ylabel("Precision", size=20)

average_precision_score(y_val, preds)
