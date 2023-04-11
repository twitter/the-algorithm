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

import tensorflow.compat.v1 as tf
from twml.metrics import get_multi_binary_class_metric_fn



# checkstyle: noqa
def get_partial_multi_binary_class_metric_fn(metrics, classes=None, class_dim=1, predcols=None):

  def get_eval_metric_ops(graph_output, labels, weights):
    if predcols is None:
      preds = graph_output['output']
    else:
      if isinstance(predcols, int):
        predcol_list=[predcols]
      else:
        predcol_list=list(predcols)
      for col in predcol_list:
        assert 0 <= col < graph_output['output'].shape[class_dim], 'Invalid Prediction Column Index !'
      preds  = tf.gather(graph_output['output'], indices=predcol_list, axis=class_dim)     # [batchSz, num_col]
      labels = tf.gather(labels, indices=predcol_list, axis=class_dim)                     # [batchSz, num_col]

    predInfo = {'output': preds}
    if 'threshold' in graph_output:
      predInfo['threshold'] = graph_output['threshold']
    if 'hard_output' in graph_output:
      predInfo['hard_output'] = graph_output['hard_output']

    metrics_op = get_multi_binary_class_metric_fn(metrics, classes, class_dim)
    metrics_op_res = metrics_op(predInfo, labels, weights)
    return metrics_op_res

  return get_eval_metric_ops



# Numeric Prediction Performance among TopK Predictions
def mean_numeric_label_topK(labels, predictions, weights, name, topK_id):
  top_k_labels  = tf.gather(params=labels, indices=topK_id, axis=0)                # [topK, 1]
  return tf.metrics.mean(values=top_k_labels, name=name)

def mean_gated_numeric_label_topK(labels, predictions, weights, name, topK_id, bar=2.0):
  assert isinstance(bar, int) or isinstance(bar, float), "bar must be int or float"
  top_k_labels  = tf.gather(params=labels, indices=topK_id, axis=0)                # [topK, 1]
  gated_top_k_labels  = tf.cast(top_k_labels > bar*1.0, tf.int32)
  return tf.metrics.mean(values=gated_top_k_labels, name=name)

SUPPORTED_NUMERIC_METRICS = {
  'mean_numeric_label_topk': mean_numeric_label_topK,
  'mean_gated_numeric_label_topk': mean_gated_numeric_label_topK
}
DEFAULT_NUMERIC_METRICS = ['mean_numeric_label_topk', 'mean_gated_numeric_label_topk']



def get_metric_topK_fn_helper(targetMetrics, supportedMetrics_op, metrics=None, topK=(5,5,5), predcol=None, labelcol=None):
  """
  :param targetMetrics:        Target Metric List
  :param supportedMetrics_op:  Supported Metric Operators             Dict
  :param metrics:              Metric Set to evaluate
  :param topK:                 (topK_min, topK_max, topK_delta)       Tuple
  :param predcol:              Prediction Column Index
  :param labelcol:             Label Column Index
  :return:
  """
  # pylint: disable=dict-keys-not-iterating
  if targetMetrics is None or supportedMetrics_op is None:
    raise ValueError("Invalid Target Metric List/op !")

  targetMetrics = set([m.lower() for m in targetMetrics])
  if metrics is None:
    metrics = list(targetMetrics)
  else:
    metrics = [m.lower() for m in metrics if m.lower() in targetMetrics]

  num_k     = int((topK[1]-topK[0])/topK[2]+1)
  topK_list = [topK[0]+d*topK[2] for d in range(num_k)]
  if 1 not in topK_list:
    topK_list = [1] + topK_list


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

    if predcol is None:
      pred = graph_output['output']
    else:
      assert 0 <= predcol < graph_output['output'].shape[1], 'Invalid Prediction Column Index !'
      assert labelcol is not None
      pred   = tf.reshape(graph_output['output'][:, predcol], shape=[-1, 1])
      labels = tf.reshape(labels[:, labelcol], shape=[-1, 1])
    numOut = graph_output['output'].shape[1]
    pred_score = tf.reshape(graph_output['output'][:, numOut-1], shape=[-1, 1])

    # add metrics to eval_metric_ops dict
    for metric_name in metrics:
      metric_name = metric_name.lower()  # metric name are case insensitive.

      if metric_name in supportedMetrics_op:
        metric_factory = supportedMetrics_op.get(metric_name)

        if 'topk' not in metric_name:
          value_op, update_op = metric_factory(
            labels=labels,
            predictions=pred,
            weights=weights,
            name=metric_name)
          eval_metric_ops[metric_name] = (value_op, update_op)
        else:
          for K in topK_list:
            K_min = tf.minimum(K, tf.shape(pred_score)[0])
            topK_id = tf.nn.top_k(tf.reshape(pred_score, shape=[-1]), k=K_min)[1]           # [topK]
            value_op, update_op = metric_factory(
              labels=labels,
              predictions=pred,
              weights=weights,
              name=metric_name+'__k_'+str(K),
              topK_id=topK_id)
            eval_metric_ops[metric_name+'__k_'+str(K)] = (value_op, update_op)

      else:
        raise ValueError('Cannot find the metric named ' + metric_name)

    return eval_metric_ops

  return get_eval_metric_ops



def get_numeric_metric_fn(metrics=None, topK=(5,5,5), predcol=None, labelcol=None):
  if metrics is None:
    metrics = list(DEFAULT_NUMERIC_METRICS)
  metrics   = list(set(metrics))

  metric_op = get_metric_topK_fn_helper(targetMetrics=list(DEFAULT_NUMERIC_METRICS),
                                        supportedMetrics_op=SUPPORTED_NUMERIC_METRICS,
                                        metrics=metrics, topK=topK, predcol=predcol, labelcol=labelcol)
  return metric_op



def get_single_binary_task_metric_fn(metrics, classnames, topK=(5,5,5), use_topK=False):
  """
  graph_output['output']:        [BatchSz, 1]        [pred_Task1]
  labels:                        [BatchSz, 2]        [Task1, NumericLabel]
  """
  def get_eval_metric_ops(graph_output, labels, weights):
    metric_op_base = get_partial_multi_binary_class_metric_fn(metrics, predcols=0, classes=classnames)
    classnames_unw = ['unweighted_'+cs for cs in classnames]
    metric_op_unw = get_partial_multi_binary_class_metric_fn(metrics, predcols=0, classes=classnames_unw)

    metrics_base_res = metric_op_base(graph_output, labels, weights)
    metrics_unw_res = metric_op_unw(graph_output, labels, None)
    metrics_base_res.update(metrics_unw_res)

    if use_topK:
      metric_op_numeric = get_numeric_metric_fn(metrics=None, topK=topK, predcol=0, labelcol=1)
      metrics_numeric_res = metric_op_numeric(graph_output, labels, weights)
      metrics_base_res.update(metrics_numeric_res)
    return metrics_base_res

  return get_eval_metric_ops


def get_dual_binary_tasks_metric_fn(metrics, classnames, topK=(5,5,5), use_topK=False):
  """
  graph_output['output']:        [BatchSz, 3]        [pred_Task1, pred_Task2, Score]
  labels:                        [BatchSz, 3]        [Task1, Task2, NumericLabel]
  """
  def get_eval_metric_ops(graph_output, labels, weights):

    metric_op_base = get_partial_multi_binary_class_metric_fn(metrics, predcols=[0, 1], classes=classnames)
    classnames_unw = ['unweighted_'+cs for cs in classnames]
    metric_op_unw = get_partial_multi_binary_class_metric_fn(metrics, predcols=[0, 1], classes=classnames_unw)

    metrics_base_res = metric_op_base(graph_output, labels, weights)
    metrics_unw_res = metric_op_unw(graph_output, labels, None)
    metrics_base_res.update(metrics_unw_res)

    if use_topK:
      metric_op_numeric = get_numeric_metric_fn(metrics=None, topK=topK, predcol=2, labelcol=2)
      metrics_numeric_res = metric_op_numeric(graph_output, labels, weights)
      metrics_base_res.update(metrics_numeric_res)
    return metrics_base_res

  return get_eval_metric_ops
