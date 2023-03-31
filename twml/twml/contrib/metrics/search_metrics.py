"""
Module containing extra tensorflow metrics used at Twitter.
This module conforms to conventions used by tf.metrics.*.
In particular, each metric constructs two subgraphs: value_op and update_op:
  - The value op is used to fetch the current metric value.
  - The update_op is used to accumulate into the metric.

Note: similar to tf.metrics.*, metrics in here do not support multi-label learning.
We will have to write wrapper classes to create one metric per label.

Note: similar to tf.metrics.*, batches added into a metric via its update_op are cumulative!

"""

from collections import OrderedDict
from functools import partial

import tensorflow.compat.v1 as tf
from tensorflow.python.eager import context
from tensorflow.python.framework import dtypes, ops
from tensorflow.python.ops import array_ops, state_ops
import twml
from twml.contrib.utils import math_fns


def ndcg(labels, predictions,
                  metrics_collections=None,
                  updates_collections=None,
                  name=None,
                  top_k_int=1):
  # pylint: disable=unused-argument
  """
  Compute full normalized discounted cumulative gain (ndcg) based on predictions
  ndcg = dcg_k/idcg_k, k is a cut off ranking postion
  There are a few variants of ndcg
  The dcg (discounted cumulative gain) formula used in
  twml.contrib.metrics.ndcg is::

    \\sum_{i=1}^k \frac{2^{relevance\\_score} -1}{\\log_{2}(i + 1)}

  k is the length of items to be ranked in a batch/query
  Notice that whether k will be replaced with a fixed value requires discussions
  The scores in predictions are transformed to order and relevance scores to calculate ndcg
  A relevance score means how relevant a DataRecord is to a particular query

  Arguments:
    labels: the ground truth value.
    predictions: the predicted values, whose shape must match labels. Ignored for CTR computation.
    metrics_collections: optional list of collections to add this metric into.
    updates_collections: optional list of collections to add the associated update_op into.
    name: an optional variable_scope name.

  Returns:
    ndcg: A `Tensor` representing the ndcg score.
    update_op: A update operation used to accumulate data into this metric.
  """
  with tf.variable_scope(name, 'ndcg', (labels, predictions)):
    label_scores = tf.to_float(labels, name='label_to_float')
    predicted_scores = tf.to_float(predictions, name='predictions_to_float')

    if context.executing_eagerly():
      raise RuntimeError('ndcg is not supported when eager execution '
                         'is enabled.')

    total_ndcg = _metric_variable([], dtypes.float32, name='total_ndcg')
    count_query = _metric_variable([], dtypes.float32, name='query_count')

    # actual ndcg cutoff position top_k_int
    max_prediction_size = array_ops.size(predicted_scores)
    top_k_int = tf.minimum(max_prediction_size, top_k_int)
    # the ndcg score of the batch
    ndcg = math_fns.cal_ndcg(label_scores,
      predicted_scores, top_k_int=top_k_int)
    # add ndcg of the current batch to total_ndcg
    update_total_op = state_ops.assign_add(total_ndcg, ndcg)
    with ops.control_dependencies([ndcg]):
      # count_query stores the number of queries
      # count_query increases by 1 for each batch/query
      update_count_op = state_ops.assign_add(count_query, 1)

    mean_ndcg = math_fns.safe_div(total_ndcg, count_query, 'mean_ndcg')
    update_op = math_fns.safe_div(update_total_op, update_count_op, 'update_mean_ndcg_op')

    if metrics_collections:
      ops.add_to_collections(metrics_collections, mean_ndcg)

    if updates_collections:
      ops.add_to_collections(updates_collections, update_op)

    return mean_ndcg, update_op


# Copied from metrics_impl.py with minor modifications.
# https://github.com/tensorflow/tensorflow/blob/v1.5.0/tensorflow/python/ops/metrics_impl.py#L39
def _metric_variable(shape, dtype, validate_shape=True, name=None):
  """Create variable in `GraphKeys.(LOCAL|METRIC_VARIABLES`) collections."""

  return tf.Variable(
    lambda: tf.zeros(shape, dtype),
    trainable=False,
    collections=[tf.GraphKeys.LOCAL_VARIABLES, tf.GraphKeys.METRIC_VARIABLES],
    validate_shape=validate_shape,
    name=name)


# binary metric_name: (metric, requires thresholded output)
SUPPORTED_BINARY_CLASS_METRICS = {
  # TWML binary metrics
  'rce': (twml.metrics.rce, False),
  'nrce': (partial(twml.metrics.rce, normalize=True), False),
  # CTR measures positive sample ratio. This terminology is inherited from Ads.
  'ctr': (twml.metrics.ctr, False),
  # predicted CTR measures predicted positive ratio.
  'predicted_ctr': (twml.metrics.predicted_ctr, False),
  # thresholded metrics
  'accuracy': (tf.metrics.accuracy, True),
  'precision': (tf.metrics.precision, True),
  'recall': (tf.metrics.recall, True),
  # tensorflow metrics
  'roc_auc': (partial(tf.metrics.auc, curve='ROC'), False),
  'pr_auc': (partial(tf.metrics.auc, curve='PR'), False),
}

# search metric_name: metric
SUPPORTED_SEARCH_METRICS = {
  # TWML search metrics
  # ndcg needs the raw prediction scores to sort
  'ndcg': ndcg,
}


def get_search_metric_fn(binary_metrics=None, search_metrics=None,
  ndcg_top_ks=[1, 3, 5, 10], use_binary_metrics=False):
  """
  Returns a function having signature:

  .. code-block:: python

    def get_eval_metric_ops(graph_output, labels, weights):
      ...
      return eval_metric_ops

  where the returned eval_metric_ops is a dict of common evaluation metric
  Ops for ranking. See `tf.estimator.EstimatorSpec
  <https://www.tensorflow.org/api_docs/python/tf/estimator/EstimatorSpec>`_
  for a description of eval_metric_ops. The graph_output is a the result
  dict returned by build_graph. Labels and weights are tf.Tensors.

  The following graph_output keys are recognized:
    output:
      the raw predictions. Required.
    threshold:
      Only used in SUPPORTED_BINARY_CLASS_METRICS
      If the lables are 0s and 1s
      A value between 0 and 1 used to threshold the output into a hard_output.
      Defaults to 0.5 when threshold and hard_output are missing.
      Either threshold or hard_output can be provided, but not both.
    hard_output:
      Only used in SUPPORTED_BINARY_CLASS_METRICS
      A thresholded output. Either threshold or hard_output can be provided, but not both.

  Arguments:
    only used in pointwise learning-to-rank

    binary_metrics (list of String):
      a list of metrics of interest. E.g. ['ctr', 'accuracy', 'rce']
      These metrics are evaluated and reported to tensorboard *during the eval phases only*.
      Supported metrics:
        - ctr (same as positive sample ratio.)
        - rce (cross entropy loss compared to the baseline model of always predicting ctr)
        - nrce (normalized rce, do not use this one if you do not understand what it is)
        - pr_auc
        - roc_auc
        - accuracy (percentage of predictions that are correct)
        - precision (true positives) / (true positives + false positives)
        - recall (true positives) / (true positives + false negatives)

      NOTE: accuracy / precision / recall apply to binary classification problems only.
      I.e. a prediction is only considered correct if it matches the label. E.g. if the label
      is 1.0, and the prediction is 0.99, it does not get credit.  If you want to use
      precision / recall / accuracy metrics with soft predictions, you'll need to threshold
      your predictions into hard 0/1 labels.

      When binary_metrics is None (the default), it defaults to all supported metrics

    search_metrics (list of String):
      a list of metrics of interest. E.g. ['ndcg']
      These metrics are evaluated and reported to tensorboard *during the eval phases only*.
      Supported metrics:
        - ndcg

      NOTE: ndcg works for ranking-relatd problems.
      A batch contains all DataRecords that belong to the same query
      If pair_in_batch_mode used in scalding -- a batch contains a pair of DataRecords
      that belong to the same query and have different labels -- ndcg does not apply in here.

      When search_metrics is None (the default), it defaults to all supported search metrics
      currently only 'ndcg'

    ndcg_top_ks (list of integers):
      The cut-off ranking postions for a query
      When ndcg_top_ks is None or empty (the default), it defaults to [1, 3, 5, 10]

    use_binary_metrics:
      False (default)
      Only set it to true in pointwise learning-to-rank
  """
  # pylint: disable=dict-keys-not-iterating

  if ndcg_top_ks is None or not ndcg_top_ks:
    ndcg_top_ks = [1, 3, 5, 10]

  if search_metrics is None:
    search_metrics = list(SUPPORTED_SEARCH_METRICS.keys())

  if binary_metrics is None and use_binary_metrics:
    # Added SUPPORTED_BINARY_CLASS_METRICS in twml.metics as well
    # they are only used in pointwise learing-to-rank
    binary_metrics = list(SUPPORTED_BINARY_CLASS_METRICS.keys())

  def get_eval_metric_ops(graph_output, labels, weights):
    """
    graph_output:
      dict that is returned by build_graph given input features.
    labels:
      target labels associated to batch.
    weights:
      weights of the samples..
    """

    eval_metric_ops = OrderedDict()

    preds = graph_output['output']

    threshold = graph_output['threshold'] if 'threshold' in graph_output else 0.5

    hard_preds = graph_output.get('hard_output')
    # hard_preds is a tensor
    # check hard_preds is None and then check if it is empty
    if hard_preds is None or tf.equal(tf.size(hard_preds), 0):
      hard_preds = tf.greater_equal(preds, threshold)

    # add search metrics to eval_metric_ops dict
    for metric_name in search_metrics:
      metric_name = metric_name.lower()  # metric name are case insensitive.

      if metric_name in eval_metric_ops:
        # avoid adding duplicate metrics.
        continue

      search_metric_factory = SUPPORTED_SEARCH_METRICS.get(metric_name)
      if search_metric_factory:
        if metric_name == 'ndcg':
          for top_k in ndcg_top_ks:
            # metric name will show as ndcg_1, ndcg_10, ...
            metric_name_ndcg_top_k = metric_name + '_' + str(top_k)
            top_k_int = tf.constant(top_k, dtype=tf.int32)
            # Note: having weights in ndcg does not make much sense
            # Because ndcg already has position weights/discounts
            # Thus weights are not applied in ndcg metric
            value_op, update_op = search_metric_factory(
              labels=labels,
              predictions=preds,
              name=metric_name_ndcg_top_k,
              top_k_int=top_k_int)
            eval_metric_ops[metric_name_ndcg_top_k] = (value_op, update_op)
      else:
        raise ValueError('Cannot find the search metric named ' + metric_name)

    if use_binary_metrics:
      # add binary metrics to eval_metric_ops dict
      for metric_name in binary_metrics:

        if metric_name in eval_metric_ops:
          # avoid adding duplicate metrics.
          continue

        metric_name = metric_name.lower()  # metric name are case insensitive.
        binary_metric_factory, requires_threshold = SUPPORTED_BINARY_CLASS_METRICS.get(metric_name)
        if binary_metric_factory:
          value_op, update_op = binary_metric_factory(
            labels=labels,
            predictions=(hard_preds if requires_threshold else preds),
            weights=weights,
            name=metric_name)
          eval_metric_ops[metric_name] = (value_op, update_op)
        else:
          raise ValueError('Cannot find the binary metric named ' + metric_name)

    return eval_metric_ops

  return get_eval_metric_ops
