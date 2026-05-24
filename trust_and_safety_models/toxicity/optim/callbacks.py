from collections import defaultdict
import os

from toxicity_ml_pipeline.settings.default_settings_tox import REMOTE_LOGDIR
from toxicity_ml_pipeline.settings.default_settings_abs import LABEL_NAMES
from toxicity_ml_pipeline.utils.absv_utils import parse_labeled_data
from toxicity_ml_pipeline.utils.helpers import compute_precision_fixed_recall, execute_command

from sklearn.metrics import average_precision_score, roc_auc_score
import tensorflow as tf
import wandb


class NothingCallback(tf.keras.callbacks.Callback):
  def on_epoch_begin(self, epoch, logs=None):
    print("ici, ", epoch)

  def on_epoch_end(self, epoch, logs=None):
    print("fin ", epoch)

  def on_train_batch_end(self, batch, logs=None):
    print("fin de batch ", batch)


class ControlledStoppingCheckpointCallback(tf.keras.callbacks.ModelCheckpoint):
  def __init__(self, stopping_epoch, *args, **kwargs):
    super().__init__(*args, **kwargs)
    self.stopping_epoch = stopping_epoch

  def on_epoch_end(self, epoch, logs=None):
    super().on_epoch_end(epoch, logs)
    if epoch == self.stopping_epoch:
      self.model.stop_training = True


class SyncingTensorBoard(tf.keras.callbacks.TensorBoard):
  def __init__(self, remote_logdir=None, *args, **kwargs):
    super().__init__(*args, **kwargs)
    self.remote_logdir = remote_logdir if remote_logdir is not None else REMOTE_LOGDIR

  def on_epoch_end(self, epoch, logs=None):
    super().on_epoch_end(epoch, logs=logs)
    self.synchronize()

  def synchronize(self):
    base_dir = os.path.dirname(self.log_dir)
    cmd = f"gsutil -m rsync -r {base_dir} {self.remote_logdir}"
    execute_command(cmd)


class GradientLoggingTensorBoard(SyncingTensorBoard):
  def __init__(self, loader, val_data, freq, *args, **kwargs):
    super().__init__(*args, **kwargs)
    val_dataset = loader.get_balanced_dataset(
      training_data=val_data, size_limit=50, return_as_batch=False
    )
    data_args = list(val_dataset.batch(32).take(1))[0]
    self.x_batch, self.y_batch = data_args[0], data_args[1]
    self.freq = freq
    self.counter = 0

  def _log_gradients(self):
    writer = self._train_writer

    with writer.as_default():
      with tf.GradientTape() as tape:
        y_pred = self.model(self.x_batch)
        loss = self.model.compiled_loss(y_true=self.y_batch, y_pred=y_pred)
        gradient_norm = tf.linalg.global_norm(tape.gradient(loss, self.model.trainable_weights))

      tf.summary.scalar("gradient_norm", data=gradient_norm, step=self.counter)
    writer.flush()

  def on_train_batch_end(self, batch, logs=None):
    super().on_batch_end(batch, logs=logs)
    self.counter += 1
    if batch % self.freq == 0:
      self._log_gradients()


class AdditionalResultLogger(tf.keras.callbacks.Callback):
  def __init__(
    self,
    data,
    set_,
    fixed_recall=0.85,
    from_logits=False,
    dataset_transform_func=None,
    batch_size=64,
    dual_head=None,
    *args,
    **kwargs,
  ):
    super().__init__(*args, **kwargs)
    self.set_ = set_
    if data is None:
      return None    

    self.single_head = True
    try:
      self.labels = data.int_label.values
    except AttributeError:
      self.labels = data.to_dataframe()[LABEL_NAMES].values.astype('int')
      self.data = data.to_tf_dataset().map(parse_labeled_data).batch(batch_size)
      self.label_names = LABEL_NAMES
    else:
      self.label_names = ['']
      if dual_head:
        self.label_names = [f'{e}_label' for e in dual_head]
        self.labels = {f'{e}_output': data[f'{e}_label'].values for e in dual_head}
        self.single_head = False
      if dataset_transform_func is None:
        self.data = data.text.values
      else:
        self.data = dataset_transform_func(data, mb_size=batch_size, shuffle=False)
        
    finally:
      if len(self.label_names) == 1:
        self.metric_kw = {}
      else:
        self.metric_kw = {'average': None}

      self.counter = 0
      self.best_metrics = defaultdict(float)
      self.from_logits = from_logits
      print(f"Loaded callback for {set_}, from_logits: {from_logits}, labels {self.label_names}")

      if 1 < fixed_recall <= 100:
        fixed_recall = fixed_recall / 100
      elif not (0 < fixed_recall <= 100):
        raise ValueError("Threshold should be between 0 and 1, or 0 and 100")
      self.fixed_recall = fixed_recall
      self.batch_size = batch_size

  def compute_precision_fixed_recall(self, labels, preds):
    result, _ = compute_precision_fixed_recall(labels=labels, preds=preds,
      fixed_recall=self.fixed_recall)

    return result

  def on_epoch_end(self, epoch, logs=None):
    self.additional_evaluations(step=epoch, eval_time="epoch")

  def on_train_batch_end(self, batch, logs=None):
    self.counter += 1
    if self.counter % 2000 == 0:
      self.additional_evaluations(step=self.counter, eval_time="batch")

  def _binary_evaluations(self, preds, label_name=None, class_index=None):
    mask = None
    curr_labels = self.labels
    if label_name is not None:
      curr_labels = self.labels[label_name]
      if class_index is not None:
        curr_labels = (curr_labels == class_index).astype(int)

    if -1 in curr_labels:
      mask = curr_labels != -1   
      curr_labels = curr_labels[mask]
      preds = preds[mask] 
    
    return {
        f"precision_recall{self.fixed_recall}": self.compute_precision_fixed_recall(
          labels=curr_labels, preds=preds
        ),
        "pr_auc": average_precision_score(y_true=curr_labels, y_score=preds),
        "roc_auc": roc_auc_score(y_true=curr_labels, y_score=preds),
      }


  def _multiclass_evaluations(self, preds):
    pr_auc_l = average_precision_score(y_true=self.labels, y_score=preds, **self.metric_kw)
    roc_auc_l = roc_auc_score(y_true=self.labels, y_score=preds, **self.metric_kw)
    metrics = {}
    for i, label in enumerate(self.label_names):
      metrics[f'pr_auc_{label}'] = pr_auc_l[i]
      metrics[f'roc_auc_{label}'] = roc_auc_l[i]

    return metrics
  
  def additional_evaluations(self, step, eval_time):
    print("Evaluating ", self.set_, eval_time, step)

    preds = self.model.predict(x=self.data, batch_size=self.batch_size)
    if self.from_logits:
      preds = tf.keras.activations.sigmoid(preds.logits).numpy()
    
    if self.single_head:
      if len(self.label_names) == 1:
        metrics = self._binary_evaluations(preds)
      else:
        metrics = self._multiclass_evaluations(preds)
    else:
      if preds[0].shape[1] == 1:
        binary_preds = preds[0]
        multic_preds = preds[1]
      else:
        binary_preds = preds[1]
        multic_preds = preds[0]

      binary_metrics = self._binary_evaluations(binary_preds, label_name='target_output')
      metrics = {f'{k}_target': v for k, v in binary_metrics.items()}
      num_classes = multic_preds.shape[1]
      for class_ in range(num_classes):
        binary_metrics = self._binary_evaluations(multic_preds[:, class_], label_name='content_output', class_index=class_)
        metrics.update({f'{k}_content_{class_}': v for k, v in binary_metrics.items()})

    for k, v in metrics.items():
      self.best_metrics[f"max_{k}"] = max(v, self.best_metrics[f"max_{k}"])

    self.log_metrics(metrics, step=step, eval_time=eval_time)

  def log_metrics(self, metrics_d, step, eval_time):
    commit = False if self.set_ == "validation" else True
    to_report = {self.set_: {**metrics_d, **self.best_metrics}}

    if eval_time == "epoch":
      to_report["epoch"] = step

    wandb.log(to_report, commit=commit)
