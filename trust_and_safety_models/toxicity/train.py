from datetime import datetime
from importlib import import_module
import os

from toxicity_ml_pipeline.data.data_preprocessing import (
  DefaultENNoPreprocessor,
  DefaultENPreprocessor,
)
from toxicity_ml_pipeline.data.dataframe_loader import ENLoader, ENLoaderWithSampling
from toxicity_ml_pipeline.data.mb_generator import BalancedMiniBatchLoader
from toxicity_ml_pipeline.load_model import load, get_last_layer
from toxicity_ml_pipeline.optim.callbacks import (
  AdditionalResultLogger,
  ControlledStoppingCheckpointCallback,
  GradientLoggingTensorBoard,
  SyncingTensorBoard,
)
from toxicity_ml_pipeline.optim.schedulers import WarmUp
from toxicity_ml_pipeline.settings.default_settings_abs import GCS_ADDRESS as ABS_GCS
from toxicity_ml_pipeline.settings.default_settings_tox import (
  GCS_ADDRESS as TOX_GCS,
  MODEL_DIR,
  RANDOM_SEED,
  REMOTE_LOGDIR,
  WARM_UP_PERC,
)
from toxicity_ml_pipeline.utils.helpers import check_gpu, set_seeds, upload_model

import numpy as np
import tensorflow as tf


try:
  from tensorflow_addons.optimizers import AdamW
except ModuleNotFoundError:
  print("No TFA")


class Trainer(object):
  OPTIMIZERS = ["Adam", "AdamW"]

  def __init__(
    self,
    optimizer_name,
    weight_decay,
    learning_rate,
    mb_size,
    train_epochs,
    content_loss_weight=1,
    language="en",
    scope='TOX',
    project=...,
    experiment_id="default",
    gradient_clipping=None,
    fold="time",
    seed=RANDOM_SEED,
    log_gradients=False,
    kw="",
    stopping_epoch=None,
    test=False,
  ):
    self.seed = seed
    self.weight_decay = weight_decay
    self.learning_rate = learning_rate
    self.mb_size = mb_size
    self.train_epochs = train_epochs
    self.gradient_clipping = gradient_clipping

    if optimizer_name not in self.OPTIMIZERS:
      raise ValueError(
        f"Optimizer {optimizer_name} not implemented. Accepted values {self.OPTIMIZERS}."
      )
    self.optimizer_name = optimizer_name
    self.log_gradients = log_gradients
    self.test = test
    self.fold = fold
    self.stopping_epoch = stopping_epoch
    self.language = language
    if scope == 'TOX':
      GCS_ADDRESS = TOX_GCS.format(project=project)
    elif scope == 'ABS':
      GCS_ADDRESS = ABS_GCS
    else:
      raise ValueError
    GCS_ADDRESS = GCS_ADDRESS.format(project=project)
    try:
      self.setting_file = import_module(f"toxicity_ml_pipeline.settings.{scope.lower()}{project}_settings")
    except ModuleNotFoundError:
      raise ValueError(f"You need to define a setting file for your project {project}.")
    experiment_settings = self.setting_file.experiment_settings

    self.project = project
    self.remote_logdir = REMOTE_LOGDIR.format(GCS_ADDRESS=GCS_ADDRESS, project=project)
    self.model_dir = MODEL_DIR.format(GCS_ADDRESS=GCS_ADDRESS, project=project)

    if experiment_id not in experiment_settings:
      raise ValueError("This is not an experiment id as defined in the settings file.")

    for var, default_value in experiment_settings["default"].items():
      override_val = experiment_settings[experiment_id].get(var, default_value)
      print("Setting ", var, override_val)
      self.__setattr__(var, override_val)

    self.content_loss_weight = content_loss_weight if self.dual_head else None

    self.mb_loader = BalancedMiniBatchLoader(
      fold=self.fold,
      seed=self.seed,
      perc_training_tox=self.perc_training_tox,
      mb_size=self.mb_size,
      n_outer_splits="time",
      scope=scope,
      project=project,
      dual_head=self.dual_head,
      sample_weights=self.sample_weights,
      huggingface=("bertweet" in self.model_type),
    )
    self._init_dirnames(kw=kw, experiment_id=experiment_id)
    print("------- Checking there is a GPU")
    check_gpu()

  def _init_dirnames(self, kw, experiment_id):
    kw = "test" if self.test else kw
    hyper_param_kw = ""
    if self.optimizer_name == "AdamW":
      hyper_param_kw += f"{self.weight_decay}_"
    if self.gradient_clipping:
      hyper_param_kw += f"{self.gradient_clipping}_"
    if self.content_loss_weight:
      hyper_param_kw += f"{self.content_loss_weight}_"
    experiment_name = (
      f"{self.language}{str(datetime.now()).replace(' ', '')[:-7]}{kw}_{experiment_id}{self.fold}_"
      f"{self.optimizer_name}_"
      f"{self.learning_rate}_"
      f"{hyper_param_kw}"
      f"{self.mb_size}_"
      f"{self.perc_training_tox}_"
      f"{self.train_epochs}_seed{self.seed}"
    )
    print("------- Experiment name: ", experiment_name)
    self.logdir = (
      f"..."
      if self.test
      else f"..."
    )
    self.checkpoint_path = f"{self.model_dir}/{experiment_name}"

  @staticmethod
  def _additional_writers(logdir, metric_name):
    return tf.summary.create_file_writer(os.path.join(logdir, metric_name))

  def get_callbacks(self, fold, val_data, test_data):
    fold_logdir = self.logdir + f"_fold{fold}"
    fold_checkpoint_path = self.checkpoint_path + f"_fold{fold}/{{epoch:02d}}"

    tb_args = {
      "log_dir": fold_logdir,
      "histogram_freq": 0,
      "update_freq": 500,
      "embeddings_freq": 0,
      "remote_logdir": f"{self.remote_logdir}_{self.language}"
      if not self.test
      else f"{self.remote_logdir}_test",
    }
    tensorboard_callback = (
      GradientLoggingTensorBoard(loader=self.mb_loader, val_data=val_data, freq=10, **tb_args)
      if self.log_gradients
      else SyncingTensorBoard(**tb_args)
    )

    callbacks = [tensorboard_callback]
    if "bertweet" in self.model_type:
      from_logits = True
      dataset_transform_func = self.mb_loader.make_huggingface_tensorflow_ds
    else:
      from_logits = False
      dataset_transform_func = None

    fixed_recall = 0.85 if not self.dual_head else 0.5
    val_callback = AdditionalResultLogger(
      data=val_data,
      set_="validation",
      from_logits=from_logits,
      dataset_transform_func=dataset_transform_func,
      dual_head=self.dual_head,
      fixed_recall=fixed_recall
    )
    if val_callback is not None:
      callbacks.append(val_callback)

    test_callback = AdditionalResultLogger(
      data=test_data,
      set_="test",
      from_logits=from_logits,
      dataset_transform_func=dataset_transform_func,
      dual_head=self.dual_head,
      fixed_recall=fixed_recall
    )
    callbacks.append(test_callback)

    checkpoint_args = {
      "filepath": fold_checkpoint_path,
      "verbose": 0,
      "monitor": "val_pr_auc",
      "save_weights_only": True,
      "mode": "max",
      "save_freq": "epoch",
    }
    if self.stopping_epoch:
      checkpoint_callback = ControlledStoppingCheckpointCallback(
        **checkpoint_args,
        stopping_epoch=self.stopping_epoch,
        save_best_only=False,
      )
      callbacks.append(checkpoint_callback)

    return callbacks

  def get_lr_schedule(self, steps_per_epoch):
    total_num_steps = steps_per_epoch * self.train_epochs

    warm_up_perc = WARM_UP_PERC if self.learning_rate >= 1e-3 else 0
    warm_up_steps = int(total_num_steps * warm_up_perc)
    if self.linear_lr_decay:
      learning_rate_fn = tf.keras.optimizers.schedules.PolynomialDecay(
        self.learning_rate,
        total_num_steps - warm_up_steps,
        end_learning_rate=0.0,
        power=1.0,
        cycle=False,
      )
    else:
      print('Constant learning rate')
      learning_rate_fn = self.learning_rate

    if warm_up_perc > 0:
      print(f".... using warm-up for {warm_up_steps} steps")
      warm_up_schedule = WarmUp(
        initial_learning_rate=self.learning_rate,
        decay_schedule_fn=learning_rate_fn,
        warmup_steps=warm_up_steps,
      )
      return warm_up_schedule
    return learning_rate_fn

  def get_optimizer(self, schedule):
    optim_args = {
      "learning_rate": schedule,
      "beta_1": 0.9,
      "beta_2": 0.999,
      "epsilon": 1e-6,
      "amsgrad": False,
    }
    if self.gradient_clipping:
      optim_args["global_clipnorm"] = self.gradient_clipping

    print(f".... {self.optimizer_name} w global clipnorm {self.gradient_clipping}")
    if self.optimizer_name == "Adam":
      return tf.keras.optimizers.Adam(**optim_args)

    if self.optimizer_name == "AdamW":
      optim_args["weight_decay"] = self.weight_decay
      return AdamW(**optim_args)
    raise NotImplementedError

  def get_training_actors(self, steps_per_epoch, val_data, test_data, fold):
    callbacks = self.get_callbacks(fold=fold, val_data=val_data, test_data=test_data)
    schedule = self.get_lr_schedule(steps_per_epoch=steps_per_epoch)

    optimizer = self.get_optimizer(schedule)

    return optimizer, callbacks

  def load_data(self):
    if self.project == 435 or self.project == 211:
      if self.dataset_type is None:
        data_loader = ENLoader(project=self.project, setting_file=self.setting_file)
        dataset_type_args = {}
      else:
        data_loader = ENLoaderWithSampling(project=self.project, setting_file=self.setting_file)
        dataset_type_args = self.dataset_type

    df = data_loader.load_data(
      language=self.language, test=self.test, reload=self.dataset_reload, **dataset_type_args
    )

    return df

  def preprocess(self, df):
    if self.project == 435 or self.project == 211:
      if self.preprocessing is None:
        data_prepro = DefaultENNoPreprocessor()
      elif self.preprocessing == "default":
        data_prepro = DefaultENPreprocessor()
      else:
        raise NotImplementedError

    return data_prepro(
      df=df,
      label_column=self.label_column,
      class_weight=self.perc_training_tox if self.sample_weights == 'class_weight' else None,
      filter_low_agreements=self.filter_low_agreements,
      num_classes=self.num_classes,
    )

  def load_model(self, optimizer):
    smart_bias_value = (
      np.log(self.perc_training_tox / (1 - self.perc_training_tox)) if self.smart_bias_init else 0
    )
    model = load(
      optimizer,
      seed=self.seed,
      trainable=self.trainable,
      model_type=self.model_type,
      loss_name=self.loss_name,
      num_classes=self.num_classes,
      additional_layer=self.additional_layer,
      smart_bias_value=smart_bias_value,
      content_num_classes=self.content_num_classes,
      content_loss_name=self.content_loss_name,
      content_loss_weight=self.content_loss_weight
    )

    if self.model_reload is not False:
      model_folder = upload_model(full_gcs_model_path=os.path.join(self.model_dir, self.model_reload))
      model.load_weights(model_folder)
      if self.scratch_last_layer:
        print('Putting the last layer back to scratch')
        model.layers[-1] = get_last_layer(seed=self.seed,
                                        num_classes=self.num_classes,
                                        smart_bias_value=smart_bias_value)

    return model

  def _train_single_fold(self, mb_generator, test_data, steps_per_epoch, fold, val_data=None):
    steps_per_epoch = 100 if self.test else steps_per_epoch

    optimizer, callbacks = self.get_training_actors(
      steps_per_epoch=steps_per_epoch, val_data=val_data, test_data=test_data, fold=fold
    )
    print("Loading model")
    model = self.load_model(optimizer)
    print(f"Nb of steps per epoch: {steps_per_epoch} ---- launching training")
    training_args = {
      "epochs": self.train_epochs,
      "steps_per_epoch": steps_per_epoch,
      "batch_size": self.mb_size,
      "callbacks": callbacks,
      "verbose": 2,
    }

    model.fit(mb_generator, **training_args)
    return

  def train_full_model(self):
    print("Setting up random seed.")
    set_seeds(self.seed)

    print(f"Loading {self.language} data")
    df = self.load_data()
    df = self.preprocess(df=df)

    print("Going to train on everything but the test dataset")
    mini_batches, test_data, steps_per_epoch = self.mb_loader.simple_cv_load(df)

    self._train_single_fold(
      mb_generator=mini_batches, test_data=test_data, steps_per_epoch=steps_per_epoch, fold="full"
    )

  def train(self):
    print("Setting up random seed.")
    set_seeds(self.seed)

    print(f"Loading {self.language} data")
    df = self.load_data()
    df = self.preprocess(df=df)

    print("Loading MB generator")
    i = 0
    if self.project == 435 or self.project == 211:
      mb_generator, steps_per_epoch, val_data, test_data = self.mb_loader.no_cv_load(full_df=df)
      self._train_single_fold(
        mb_generator=mb_generator,
        val_data=val_data,
        test_data=test_data,
        steps_per_epoch=steps_per_epoch,
        fold=i,
      )
    else:
      raise ValueError("Sure you want to do multiple fold training")
      for mb_generator, steps_per_epoch, val_data, test_data in self.mb_loader(full_df=df):
        self._train_single_fold(
          mb_generator=mb_generator,
          val_data=val_data,
          test_data=test_data,
          steps_per_epoch=steps_per_epoch,
          fold=i,
        )
        i += 1
        if i == 3:
          break
