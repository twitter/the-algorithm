"""
Graph class defining methods to obtain key quantities such as:
  * the logits
  * the probabilities
  * the final score
  * the loss function
  * the training operator
"""
from __future__ import annotations

from abc import ABC, abstractmethod
from typing import Any, Dict

from twitter.deepbird.hparam import HParams
import twml

from ..libs.model_utils import generate_disliked_mask
from .params import GraphParams

import tensorflow as tf
import tensorflow.compat.v1 as tf1


class Graph(ABC):
  def __init__(self, params: GraphParams):
    self.params = params

  @abstractmethod
  def get_logits(self, features: Dict[str, tf.Tensor], mode: tf.estimator.ModeKeys) -> tf.Tensor:
    pass

  def get_probabilities(self, logits: tf.Tensor) -> tf.Tensor:
    return tf.math.cumprod(tf.nn.sigmoid(logits), axis=1, name="probabilities")

  def get_task_weights(self, labels: tf.Tensor) -> tf.Tensor:
    oonc_label = tf.reshape(labels[:, 0], shape=(-1, 1))
    task_weights = tf.concat([tf.ones_like(oonc_label), oonc_label], axis=1)

    n_labels = len(self.params.tasks)
    task_weights = tf.reshape(task_weights[:, 0:n_labels], shape=(-1, n_labels))

    return task_weights

  def get_loss(self, labels: tf.Tensor, logits: tf.Tensor, **kwargs: Any) -> tf.Tensor:
    with tf.name_scope("weights"):
      disliked_mask = generate_disliked_mask(labels)

      labels = tf.reshape(labels[:, 0:2], shape=[-1, 2])

      labels = labels * tf.cast(tf.logical_not(disliked_mask), dtype=labels.dtype)

      with tf.name_scope("task_weight"):
        task_weights = self.get_task_weights(labels)

      with tf.name_scope("batch_size"):
        batch_size = tf.cast(tf.shape(labels)[0], dtype=tf.float32, name="batch_size")

      weights = task_weights / batch_size

    with tf.name_scope("loss"):
      loss = tf.reduce_sum(
        tf.nn.sigmoid_cross_entropy_with_logits(labels=labels, logits=logits) * weights,
      )

    return loss

  def get_score(self, probabilities: tf.Tensor) -> tf.Tensor:
    with tf.name_scope("score_weight"):
      score_weights = tf.constant([task.score_weight for task in self.params.tasks])
      score_weights = score_weights / tf.reduce_sum(score_weights, axis=0)

    with tf.name_scope("score"):
      score = tf.reshape(tf.reduce_sum(probabilities * score_weights, axis=1), shape=[-1, 1])

    return score

  def get_train_op(self, loss: tf.Tensor, twml_params) -> Any:
    with tf.name_scope("optimizer"):
      learning_rate = twml_params.learning_rate
      optimizer = tf1.train.GradientDescentOptimizer(learning_rate=learning_rate)

    update_ops = set(tf1.get_collection(tf1.GraphKeys.UPDATE_OPS))
    with tf.control_dependencies(update_ops):
      train_op = twml.optimizers.optimize_loss(
        loss=loss,
        variables=tf1.trainable_variables(),
        global_step=tf1.train.get_global_step(),
        optimizer=optimizer,
        learning_rate=None,
      )

    return train_op

  def __call__(
    self,
    features: Dict[str, tf.Tensor],
    labels: tf.Tensor,
    mode: tf.estimator.ModeKeys,
    params: HParams,
    config=None,
  ) -> Dict[str, tf.Tensor]:
    training = mode == tf.estimator.ModeKeys.TRAIN
    logits = self.get_logits(features=features, training=training)
    probabilities = self.get_probabilities(logits=logits)
    score = None
    loss = None
    train_op = None

    if mode == tf.estimator.ModeKeys.PREDICT:
      score = self.get_score(probabilities=probabilities)
      output = {"loss": loss, "train_op": train_op, "prediction": score}

    elif mode in (tf.estimator.ModeKeys.TRAIN, tf.estimator.ModeKeys.EVAL):
      loss = self.get_loss(labels=labels, logits=logits)

      if mode == tf.estimator.ModeKeys.TRAIN:
        train_op = self.get_train_op(loss=loss, twml_params=params)

      output = {"loss": loss, "train_op": train_op, "output": probabilities}

    else:
      raise ValueError(
        f"""
        Invalid mode. Possible values are: {tf.estimator.ModeKeys.PREDICT}, {tf.estimator.ModeKeys.TRAIN}, and {tf.estimator.ModeKeys.EVAL}
        . Passed: {mode}
      """
      )

    return output
