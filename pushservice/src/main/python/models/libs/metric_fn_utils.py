"""
Utilties for constructing a metric_fn for magic recs.
"""

from twml.contrib.metrics.metrics import (
  get_dual_binary_tasks_metric_fn,
  get_numeric_metric_fn,
  get_partial_multi_binary_class_metric_fn,
  get_single_binary_task_metric_fn,
)

from .model_utils import generate_disliked_mask

import tensorflow.compat.v1 as tf


METRIC_BOOK = {
  "OONC": ["OONC"],
  "OONC_Engagement": ["OONC", "Engagement"],
  "Sent": ["Sent"],
  "HeavyRankPosition": ["HeavyRankPosition"],
  "HeavyRankProbability": ["HeavyRankProbability"],
}

USER_AGE_FEATURE_NAME = "accountAge"
NEW_USER_AGE_CUTOFF = 0


def remove_padding_and_flatten(tensor, valid_batch_size):
  """Remove the padding of the input padded tensor given the valid batch size tensor,
    then flatten the output with respect to the first dimension.
  Args:
    tensor: A tensor of size [META_BATCH_SIZE, BATCH_SIZE, FEATURE_DIM].
    valid_batch_size: A tensor of size [META_BATCH_SIZE], with each element indicating
      the effective batch size of the BATCH_SIZE dimension.

  Returns:
    A tesnor of size [tf.reduce_sum(valid_batch_size), FEATURE_DIM].
  """
  unpadded_ragged_tensor = tf.RaggedTensor.from_tensor(tensor=tensor, lengths=valid_batch_size)

  return unpadded_ragged_tensor.flat_values


def safe_mask(values, mask):
  """Mask values if possible.

  Boolean mask inputed values if and only if values is a tensor of the same dimension as mask (or can be broadcasted to that dimension).

  Args:
      values (Any or Tensor): Input tensor to mask. Dim 0 should be size N.
      mask (boolean tensor): A boolean tensor of size N.

  Returns Values or Values masked.
  """
  if values is None:
    return values
  if not tf.is_tensor(values):
    return values
  values_shape = values.get_shape()
  if not values_shape or len(values_shape) == 0:
    return values
  if not mask.get_shape().is_compatible_with(values_shape[0]):
    return values
  return tf.boolean_mask(values, mask)


def add_new_user_metrics(metric_fn):
  """Will stratify the metric_fn by adding new user metrics.

  Given an input metric_fn, double every metric: One will be the orignal and the other will only include those for new users.

  Args:
      metric_fn (python function): Base twml metric_fn.

  Returns a metric_fn with new user metrics included.
  """

  def metric_fn_with_new_users(graph_output, labels, weights):
    if USER_AGE_FEATURE_NAME not in graph_output:
      raise ValueError(
        "In order to get metrics stratified by user age, {name} feature should be added to model graph output. However, only the following output keys were found: {keys}.".format(
          name=USER_AGE_FEATURE_NAME, keys=graph_output.keys()
        )
      )

    metric_ops = metric_fn(graph_output, labels, weights)

    is_new = tf.reshape(
      tf.math.less_equal(
        tf.cast(graph_output[USER_AGE_FEATURE_NAME], tf.int64),
        tf.cast(NEW_USER_AGE_CUTOFF, tf.int64),
      ),
      [-1],
    )

    labels = safe_mask(labels, is_new)
    weights = safe_mask(weights, is_new)
    graph_output = {key: safe_mask(values, is_new) for key, values in graph_output.items()}

    new_user_metric_ops = metric_fn(graph_output, labels, weights)
    new_user_metric_ops = {name + "_new_users": ops for name, ops in new_user_metric_ops.items()}
    metric_ops.update(new_user_metric_ops)
    return metric_ops

  return metric_fn_with_new_users


def get_meta_learn_single_binary_task_metric_fn(
  metrics, classnames, top_k=(5, 5, 5), use_top_k=False
):
  """Wrapper function to use the metric_fn with meta learning evaluation scheme.

  Args:
    metrics: A list of string representing metric names.
    classnames: A list of string repsenting class names, In case of multiple binary class models,
      the names for each class or label.
    top_k: A tuple of int to specify top K metrics.
    use_top_k: A boolean value indicating of top K of metrics is used.

  Returns:
    A customized metric_fn function.
  """

  def get_eval_metric_ops(graph_output, labels, weights):
    """The op func of the eval_metrics. Comparing with normal version,
      the difference is we flatten the output, label, and weights.

    Args:
      graph_output: A dict of tensors.
      labels: A tensor of int32 be the value of either 0 or 1.
      weights: A tensor of float32 to indicate the per record weight.

    Returns:
      A dict of metric names and values.
    """
    metric_op_weighted = get_partial_multi_binary_class_metric_fn(
      metrics, predcols=0, classes=classnames
    )
    classnames_unweighted = ["unweighted_" + classname for classname in classnames]
    metric_op_unweighted = get_partial_multi_binary_class_metric_fn(
      metrics, predcols=0, classes=classnames_unweighted
    )

    valid_batch_size = graph_output["valid_batch_size"]
    graph_output["output"] = remove_padding_and_flatten(graph_output["output"], valid_batch_size)
    labels = remove_padding_and_flatten(labels, valid_batch_size)
    weights = remove_padding_and_flatten(weights, valid_batch_size)

    tf.ensure_shape(graph_output["output"], [None, 1])
    tf.ensure_shape(labels, [None, 1])
    tf.ensure_shape(weights, [None, 1])

    metrics_weighted = metric_op_weighted(graph_output, labels, weights)
    metrics_unweighted = metric_op_unweighted(graph_output, labels, None)
    metrics_weighted.update(metrics_unweighted)

    if use_top_k:
      metric_op_numeric = get_numeric_metric_fn(metrics=None, topK=top_k, predcol=0, labelcol=1)
      metrics_numeric = metric_op_numeric(graph_output, labels, weights)
      metrics_weighted.update(metrics_numeric)
    return metrics_weighted

  return get_eval_metric_ops


def get_meta_learn_dual_binary_tasks_metric_fn(
  metrics, classnames, top_k=(5, 5, 5), use_top_k=False
):
  """Wrapper function to use the metric_fn with meta learning evaluation scheme.

  Args:
    metrics: A list of string representing metric names.
    classnames: A list of string repsenting class names, In case of multiple binary class models,
      the names for each class or label.
    top_k: A tuple of int to specify top K metrics.
    use_top_k: A boolean value indicating of top K of metrics is used.

  Returns:
    A customized metric_fn function.
  """

  def get_eval_metric_ops(graph_output, labels, weights):
    """The op func of the eval_metrics. Comparing with normal version,
      the difference is we flatten the output, label, and weights.

    Args:
      graph_output: A dict of tensors.
      labels: A tensor of int32 be the value of either 0 or 1.
      weights: A tensor of float32 to indicate the per record weight.

    Returns:
      A dict of metric names and values.
    """
    metric_op_weighted = get_partial_multi_binary_class_metric_fn(
      metrics, predcols=[0, 1], classes=classnames
    )
    classnames_unweighted = ["unweighted_" + classname for classname in classnames]
    metric_op_unweighted = get_partial_multi_binary_class_metric_fn(
      metrics, predcols=[0, 1], classes=classnames_unweighted
    )

    valid_batch_size = graph_output["valid_batch_size"]
    graph_output["output"] = remove_padding_and_flatten(graph_output["output"], valid_batch_size)
    labels = remove_padding_and_flatten(labels, valid_batch_size)
    weights = remove_padding_and_flatten(weights, valid_batch_size)

    tf.ensure_shape(graph_output["output"], [None, 2])
    tf.ensure_shape(labels, [None, 2])
    tf.ensure_shape(weights, [None, 1])

    metrics_weighted = metric_op_weighted(graph_output, labels, weights)
    metrics_unweighted = metric_op_unweighted(graph_output, labels, None)
    metrics_weighted.update(metrics_unweighted)

    if use_top_k:
      metric_op_numeric = get_numeric_metric_fn(metrics=None, topK=top_k, predcol=2, labelcol=2)
      metrics_numeric = metric_op_numeric(graph_output, labels, weights)
      metrics_weighted.update(metrics_numeric)
    return metrics_weighted

  return get_eval_metric_ops


def get_metric_fn(task_name, use_stratify_metrics, use_meta_batch=False):
  """Will retrieve the metric_fn for magic recs.

  Args:
    task_name (string): Which task is being used for this model.
    use_stratify_metrics (boolean): Should we add stratified metrics (new user metrics).
    use_meta_batch (boolean): If the output/label/weights are passed in 3D shape instead of
    2D shape.

  Returns:
    A metric_fn function to pass in twml Trainer.
  """
  if task_name not in METRIC_BOOK:
    raise ValueError(
      "Task name of {task_name} not recognized. Unable to retrieve metrics.".format(
        task_name=task_name
      )
    )
  class_names = METRIC_BOOK[task_name]
  if use_meta_batch:
    get_n_binary_task_metric_fn = (
      get_meta_learn_single_binary_task_metric_fn
      if len(class_names) == 1
      else get_meta_learn_dual_binary_tasks_metric_fn
    )
  else:
    get_n_binary_task_metric_fn = (
      get_single_binary_task_metric_fn if len(class_names) == 1 else get_dual_binary_tasks_metric_fn
    )

  metric_fn = get_n_binary_task_metric_fn(metrics=None, classnames=METRIC_BOOK[task_name])

  if use_stratify_metrics:
    metric_fn = add_new_user_metrics(metric_fn)

  return metric_fn


def flip_disliked_labels(metric_fn):
  """This function returns an adapted metric_fn which flips the labels of the OONCed evaluation data to 0 if it is disliked.
  Args:
    metric_fn: A metric_fn function to pass in twml Trainer.

  Returns:
    _adapted_metric_fn: A customized metric_fn function with disliked OONC labels flipped.
  """

  def _adapted_metric_fn(graph_output, labels, weights):
    """A customized metric_fn function with disliked OONC labels flipped.

    Args:
      graph_output: A dict of tensors.
      labels: labels of training samples, which is a 2D tensor of shape batch_size x 3: [OONCs, engagements, dislikes]
      weights: A tensor of float32 to indicate the per record weight.

    Returns:
      A dict of metric names and values.
    """
    # We want to multiply the label of the observation by 0 only when it is disliked
    disliked_mask = generate_disliked_mask(labels)

    # Extract OONC and engagement labels only.
    labels = tf.reshape(labels[:, 0:2], shape=[-1, 2])

    # Labels will be set to 0 if it is disliked.
    adapted_labels = labels * tf.cast(tf.logical_not(disliked_mask), dtype=labels.dtype)

    return metric_fn(graph_output, adapted_labels, weights)

  return _adapted_metric_fn
