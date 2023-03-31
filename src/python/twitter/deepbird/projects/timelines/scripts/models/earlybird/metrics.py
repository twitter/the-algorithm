# checkstyle: noqa
import tensorflow.compat.v1 as tf
from collections import OrderedDict
from .constants import EB_SCORE_IDX
from .lolly.data_helpers import get_lolly_scores

import twml

def get_multi_binary_class_metric_fn(metrics, classes=None, class_dim=1):
  """
  This function was copied from twml/metrics.py with the following adjustments:
    - Override example weights with the ones set in graph_output.
    - Tile labels in order to support per engagement metrics for both TF and Lolly scores.
    - Add lolly_tf_score_MSE metric.
  Note: All custom lines have a comment that starts with 'Added'
  """
  # pylint: disable=invalid-name,dict-keys-not-iterating
  if metrics is None:
    # remove expensive metrics by default for faster eval
    metrics = list(twml.metrics.SUPPORTED_BINARY_CLASS_METRICS.keys())
    metrics.remove('pr_curve')

  def get_eval_metric_ops(graph_output, labels, weights):
    """
    graph_output:
      dict that is returned by build_graph given input features.
    labels:
      target labels associated to batch.
    weights:
      weights of the samples..
    """

    # Added to support the example weights overriding.
    weights = graph_output["weights"]
    # Added to support per engagement metrics for both TF and Lolly scores.
    labels = tf.tile(labels, [1, 2])

    eval_metric_ops = OrderedDict()

    preds = graph_output['output']

    threshold = graph_output['threshold'] if 'threshold' in graph_output else 0.5

    hard_preds = graph_output.get('hard_output')
    if not hard_preds:
      hard_preds = tf.greater_equal(preds, threshold)

    shape = labels.get_shape()

    # basic sanity check: multi_metric dimension must exist
    assert len(shape) > class_dim, "Dimension specified by class_dim does not exist."

    num_labels = shape[class_dim]
    # If we are doing multi-class / multi-label metric, the number of classes / labels must
    # be know at graph construction time.  This dimension cannot have size None.
    assert num_labels is not None, "The multi-metric dimension cannot be None."
    assert classes is None or len(classes) == num_labels, (
      "Number of classes must match the number of labels")

    weights_shape = weights.get_shape() if weights is not None else None
    if weights_shape is None:
      num_weights = None
    elif len(weights_shape) > 1:
      num_weights = weights_shape[class_dim]
    else:
      num_weights = 1

    for i in range(num_labels):

      # add metrics to eval_metric_ops dict
      for metric_name in metrics:
        metric_name = metric_name.lower()  # metric name are case insensitive.

        class_metric_name = metric_name + "_" + (classes[i] if classes is not None else str(i))

        if class_metric_name in eval_metric_ops:
          # avoid adding duplicate metrics.
          continue

        class_labels = tf.gather(labels, indices=[i], axis=class_dim)
        class_preds = tf.gather(preds, indices=[i], axis=class_dim)
        class_hard_preds = tf.gather(hard_preds, indices=[i], axis=class_dim)

        if num_weights is None:
          class_weights = None
        elif num_weights == num_labels:
          class_weights = tf.gather(weights, indices=[i], axis=class_dim)
        elif num_weights == 1:
          class_weights = weights
        else:
          raise ValueError("num_weights (%d) and num_labels (%d) do not match"
                           % (num_weights, num_labels))

        metric_factory, requires_threshold = twml.metrics.SUPPORTED_BINARY_CLASS_METRICS.get(metric_name)
        if metric_factory:
          value_op, update_op = metric_factory(
            labels=class_labels,
            predictions=(class_hard_preds if requires_threshold else class_preds),
            weights=class_weights, name=class_metric_name)
          eval_metric_ops[class_metric_name] = (value_op, update_op)
        else:
          raise ValueError('Cannot find the metric named ' + metric_name)

    # Added to compare TF and Lolly scores.
    eval_metric_ops["lolly_tf_score_MSE"] = get_mse(graph_output["output"], labels)

    return eval_metric_ops

  return get_eval_metric_ops


def get_mse(predictions, labels):
  lolly_scores = get_lolly_scores(labels)
  tf_scores = predictions[:, EB_SCORE_IDX]
  squared_lolly_tf_score_diff = tf.square(tf.subtract(tf_scores, lolly_scores))

  value_op = tf.reduce_mean(squared_lolly_tf_score_diff, name="value_op")
  update_op = tf.reduce_mean(squared_lolly_tf_score_diff, name="update_op")

  return value_op, update_op
