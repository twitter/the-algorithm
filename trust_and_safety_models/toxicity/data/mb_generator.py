from importlib import import_module
import os

from toxicity_ml_pipeline.settings.default_settings_tox import (
  INNER_CV,
  LOCAL_DIR,
  MAX_SEQ_LENGTH,
  NUM_PREFETCH,
  NUM_WORKERS,
  OUTER_CV,
  TARGET_POS_PER_EPOCH,
)
from toxicity_ml_pipeline.utils.helpers import execute_command

import numpy as np
import pandas
from sklearn.model_selection import StratifiedKFold
import tensorflow as tf


try:
  from transformers import AutoTokenizer, DataCollatorWithPadding
except ModuleNotFoundError:
  print("...")
else:
  from datasets import Dataset


class BalancedMiniBatchLoader(object):
  def __init__(
    self,
    fold,
    mb_size,
    seed,
    perc_training_tox,
    scope="TOX",
    project=...,
    dual_head=None,
    n_outer_splits=None,
    n_inner_splits=None,
    sample_weights=None,
    huggingface=False,
  ):
    if 0 >= perc_training_tox or perc_training_tox > 0.5:
      raise ValueError("Perc_training_tox should be in ]0; 0.5]")

    self.perc_training_tox = perc_training_tox
    if not n_outer_splits:
      n_outer_splits = OUTER_CV
    if isinstance(n_outer_splits, int):
      self.n_outer_splits = n_outer_splits
      self.get_outer_fold = self._get_outer_cv_fold
      if fold < 0 or fold >= self.n_outer_splits or int(fold) != fold:
        raise ValueError(f"Number of fold should be an integer in [0 ; {self.n_outer_splits} [.")

    elif n_outer_splits == "time":
      self.get_outer_fold = self._get_time_fold
      if fold != "time":
        raise ValueError(
          "To avoid repeating the same run many times, the external fold"
          "should be time when test data is split according to dates."
        )
      try:
        setting_file = import_module(f"toxicity_ml_pipeline.settings.{scope.lower()}{project}_settings")
      except ModuleNotFoundError:
        raise ValueError(f"You need to define a setting file for your project {project}.")
      self.test_begin_date = setting_file.TEST_BEGIN_DATE
      self.test_end_date = setting_file.TEST_END_DATE

    else:
      raise ValueError(
        f"Argument n_outer_splits should either an integer or 'time'. Provided: {n_outer_splits}"
      )

    self.n_inner_splits = n_inner_splits if n_inner_splits is not None else INNER_CV

    self.seed = seed
    self.mb_size = mb_size
    self.fold = fold

    self.sample_weights = sample_weights
    self.dual_head = dual_head
    self.huggingface = huggingface
    if self.huggingface:
      self._load_tokenizer()

  def _load_tokenizer(self):
    print("Making a local copy of Bertweet-base model")
    local_model_dir = os.path.join(LOCAL_DIR, "models")
    cmd = f"mkdir {local_model_dir} ; gsutil -m cp -r gs://... {local_model_dir}"
    execute_command(cmd)

    self.tokenizer = AutoTokenizer.from_pretrained(
      os.path.join(local_model_dir, "bertweet-base"), normalization=True
    )

  def tokenize_function(self, el):
    return self.tokenizer(
      el["text"],
      max_length=MAX_SEQ_LENGTH,
      padding="max_length",
      truncation=True,
      add_special_tokens=True,
      return_token_type_ids=False,
      return_attention_mask=False,
    )

  def _get_stratified_kfold(self, n_splits):
    return StratifiedKFold(shuffle=True, n_splits=n_splits, random_state=self.seed)

  def _get_time_fold(self, df):
    test_begin_date = pandas.to_datetime(self.test_begin_date).date()
    test_end_date = pandas.to_datetime(self.test_end_date).date()
    print(f"Test is going from {test_begin_date} to {test_end_date}.")
    test_data = df.query("@test_begin_date <= date <= @test_end_date")

    query = "date < @test_begin_date"
    other_set = df.query(query)
    return other_set, test_data

  def _get_outer_cv_fold(self, df):
    labels = df.int_label
    stratifier = self._get_stratified_kfold(n_splits=self.n_outer_splits)

    k = 0
    for train_index, test_index in stratifier.split(np.zeros(len(labels)), labels):
      if k == self.fold:
        break
      k += 1

    train_data = df.iloc[train_index].copy()
    test_data = df.iloc[test_index].copy()

    return train_data, test_data

  def get_steps_per_epoch(self, nb_pos_examples):
    return int(max(TARGET_POS_PER_EPOCH, nb_pos_examples) / self.mb_size / self.perc_training_tox)

  def make_huggingface_tensorflow_ds(self, group, mb_size=None, shuffle=True):
    huggingface_ds = Dataset.from_pandas(group).map(self.tokenize_function, batched=True)
    data_collator = DataCollatorWithPadding(tokenizer=self.tokenizer, return_tensors="tf")
    tensorflow_ds = huggingface_ds.to_tf_dataset(
      columns=["input_ids"],
      label_cols=["labels"],
      shuffle=shuffle,
      batch_size=self.mb_size if mb_size is None else mb_size,
      collate_fn=data_collator,
    )

    if shuffle:
      return tensorflow_ds.repeat()
    return tensorflow_ds

  def make_pure_tensorflow_ds(self, df, nb_samples):
    buffer_size = nb_samples * 2

    if self.sample_weights is not None:
      if self.sample_weights not in df.columns:
        raise ValueError
      ds = tf.data.Dataset.from_tensor_slices(
        (df.text.values, df.label.values, df[self.sample_weights].values)
      )
    elif self.dual_head:
      label_d = {f'{e}_output': df[f'{e}_label'].values for e in self.dual_head}
      label_d['content_output'] = tf.keras.utils.to_categorical(label_d['content_output'], num_classes=3)
      ds = tf.data.Dataset.from_tensor_slices((df.text.values, label_d))

    else:
      ds = tf.data.Dataset.from_tensor_slices((df.text.values, df.label.values))
    ds = ds.shuffle(buffer_size, seed=self.seed, reshuffle_each_iteration=True).repeat()
    return ds

  def get_balanced_dataset(self, training_data, size_limit=None, return_as_batch=True):
    training_data = training_data.sample(frac=1, random_state=self.seed)
    nb_samples = training_data.shape[0] if not size_limit else size_limit

    num_classes = training_data.int_label.nunique()
    toxic_class = training_data.int_label.max()
    if size_limit:
      training_data = training_data[: size_limit * num_classes]

    print(
      ".... {} examples, incl. {:.2f}% tox in train, {} classes".format(
        nb_samples,
        100 * training_data[training_data.int_label == toxic_class].shape[0] / nb_samples,
        num_classes,
      )
    )
    label_groups = training_data.groupby("int_label")
    if self.huggingface:
      label_datasets = {
        label: self.make_huggingface_tensorflow_ds(group) for label, group in label_groups
      }

    else:
      label_datasets = {
        label: self.make_pure_tensorflow_ds(group, nb_samples=nb_samples * 2)
        for label, group in label_groups
      }

    datasets = [label_datasets[0], label_datasets[1]]
    weights = [1 - self.perc_training_tox, self.perc_training_tox]
    if num_classes == 3:
      datasets.append(label_datasets[2])
      weights = [1 - self.perc_training_tox, self.perc_training_tox / 2, self.perc_training_tox / 2]
    elif num_classes != 2:
      raise ValueError("Currently it should not be possible to get other than 2 or 3 classes")
    resampled_ds = tf.data.experimental.sample_from_datasets(datasets, weights, seed=self.seed)

    if return_as_batch and not self.huggingface:
      return resampled_ds.batch(
        self.mb_size, drop_remainder=True, num_parallel_calls=NUM_WORKERS, deterministic=True
      ).prefetch(NUM_PREFETCH)

    return resampled_ds

  @staticmethod
  def _compute_int_labels(full_df):
    if full_df.label.dtype == int:
      full_df["int_label"] = full_df.label

    elif "int_label" not in full_df.columns:
      if full_df.label.max() > 1:
        raise ValueError("Binarizing labels that should not be.")
      full_df["int_label"] = np.where(full_df.label >= 0.5, 1, 0)

    return full_df

  def __call__(self, full_df, *args, **kwargs):
    full_df = self._compute_int_labels(full_df)

    train_data, test_data = self.get_outer_fold(df=full_df)

    stratifier = self._get_stratified_kfold(n_splits=self.n_inner_splits)
    for train_index, val_index in stratifier.split(
      np.zeros(train_data.shape[0]), train_data.int_label
    ):
      curr_train_data = train_data.iloc[train_index]

      mini_batches = self.get_balanced_dataset(curr_train_data)

      steps_per_epoch = self.get_steps_per_epoch(
        nb_pos_examples=curr_train_data[curr_train_data.int_label != 0].shape[0]
      )

      val_data = train_data.iloc[val_index].copy()

      yield mini_batches, steps_per_epoch, val_data, test_data

  def simple_cv_load(self, full_df):
    full_df = self._compute_int_labels(full_df)

    train_data, test_data = self.get_outer_fold(df=full_df)
    if test_data.shape[0] == 0:
      test_data = train_data.iloc[:500]

    mini_batches = self.get_balanced_dataset(train_data)
    steps_per_epoch = self.get_steps_per_epoch(
      nb_pos_examples=train_data[train_data.int_label != 0].shape[0]
    )

    return mini_batches, test_data, steps_per_epoch

  def no_cv_load(self, full_df):
    full_df = self._compute_int_labels(full_df)

    val_test = full_df[full_df.origin == "precision"].copy(deep=True)
    val_data, test_data = self.get_outer_fold(df=val_test)

    train_data = full_df.drop(full_df[full_df.origin == "precision"].index, axis=0)
    if test_data.shape[0] == 0:
      test_data = train_data.iloc[:500]

    mini_batches = self.get_balanced_dataset(train_data)
    if train_data.int_label.nunique() == 1:
      raise ValueError('Should be at least two labels')

    num_examples = train_data[train_data.int_label == 1].shape[0]
    if train_data.int_label.nunique() > 2:
      second_most_frequent_label = train_data.loc[train_data.int_label != 0, 'int_label'].mode().values[0]
      num_examples = train_data[train_data.int_label == second_most_frequent_label].shape[0] * 2
    steps_per_epoch = self.get_steps_per_epoch(nb_pos_examples=num_examples)

    return mini_batches, steps_per_epoch, val_data, test_data
