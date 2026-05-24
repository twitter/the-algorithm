from functools import partial

from twitter.cortex.ml.embeddings.deepbird.grouped_metrics.configuration import (
  GroupedMetricsConfiguration,
)
from twitter.cortex.ml.embeddings.deepbird.grouped_metrics.helpers import (
  extract_prediction_from_prediction_record,
)


# checkstyle: noqa


def score_loss_at_n(labels, predictions, lightN):
  """
  Compute the absolute ScoreLoss ranking metric
  Args:
    labels (list)     : A list of label values       (HeavyRanking Reference)
    predictions (list): A list of prediction values  (LightRanking Predictions)
    lightN (int): size of the list at which of Initial candidates to compute ScoreLoss. (LightRanking)
  """
  assert len(labels) == len(predictions)

  if lightN <= 0:
    return None

  labels_with_predictions = zip(labels, predictions)
  labels_with_sorted_predictions = sorted(
    labels_with_predictions, key=lambda x: x[1], reverse=True
  )[:lightN]
  labels_top1_light = max([label for label, _ in labels_with_sorted_predictions])
  labels_top1_heavy = max(labels)

  return labels_top1_heavy - labels_top1_light


def cgr_at_nk(labels, predictions, lightN, heavyK):
  """
  Compute Cumulative Gain Ratio (CGR) ranking metric
  Args:
    labels (list)     : A list of label values       (HeavyRanking Reference)
    predictions (list): A list of prediction values  (LightRanking Predictions)
    lightN (int): size of the list at which of Initial candidates to compute CGR. (LightRanking)
    heavyK (int): size of the list at which of Refined candidates to compute CGR. (HeavyRanking)
  """
  assert len(labels) == len(predictions)

  if (not lightN) or (not heavyK):
    out = None
  elif lightN <= 0 or heavyK <= 0:
    out = None
  else:

    labels_with_predictions = zip(labels, predictions)
    labels_with_sorted_predictions = sorted(
      labels_with_predictions, key=lambda x: x[1], reverse=True
    )[:lightN]
    labels_topN_light = [label for label, _ in labels_with_sorted_predictions]

    if lightN <= heavyK:
      cg_light = sum(labels_topN_light)
    else:
      labels_topK_heavy_from_light = sorted(labels_topN_light, reverse=True)[:heavyK]
      cg_light = sum(labels_topK_heavy_from_light)

    ideal_ordering = sorted(labels, reverse=True)
    cg_heavy = sum(ideal_ordering[: min(lightN, heavyK)])

    out = 0.0
    if cg_heavy != 0:
      out = max(cg_light / cg_heavy, 0)

  return out


def _get_weight(w, atK):
  if not w:
    return 1.0
  elif len(w) <= atK:
    return 0.0
  else:
    return w[atK]


def recall_at_nk(labels, predictions, n=None, k=None, w=None):
  """
  Recall at N-K ranking metric
  Args:
    labels (list): A list of label values
    predictions (list): A list of prediction values
    n (int): size of the list at which of predictions to compute recall. (Light Ranking Predictions)
             The default is None in which case the length of the provided predictions is used as L
    k (int): size of the list at which of labels to compute recall. (Heavy Ranking Predictions)
             The default is None in which case the length of the provided labels is used as L
    w (list): weight vector sorted by labels
  """
  assert len(labels) == len(predictions)

  if not any(labels):
    out = None
  else:

    safe_n = len(predictions) if not n else min(len(predictions), n)
    safe_k = len(labels) if not k else min(len(labels), k)

    labels_with_predictions = zip(labels, predictions)
    sorted_labels_with_predictions = sorted(
      labels_with_predictions, key=lambda x: x[0], reverse=True
    )

    order_sorted_labels_predictions = zip(range(len(labels)), *zip(*sorted_labels_with_predictions))

    order_with_predictions = [
      (order, pred) for order, label, pred in order_sorted_labels_predictions
    ]
    order_with_sorted_predictions = sorted(order_with_predictions, key=lambda x: x[1], reverse=True)

    pred_sorted_order_at_n = [order for order, _ in order_with_sorted_predictions][:safe_n]

    intersection_weight = [
      _get_weight(w, order) if order < safe_k else 0 for order in pred_sorted_order_at_n
    ]

    intersection_score = sum(intersection_weight)
    full_score = sum(w) if w else float(safe_k)

    out = 0.0
    if full_score != 0:
      out = intersection_score / full_score

  return out


class ExpectedLossGroupedMetricsConfiguration(GroupedMetricsConfiguration):
  """
  This is the Expected Loss Grouped metric computation configuration.
  """

  def __init__(self, lightNs=[]):
    """
    Args:
      lightNs (list): size of the list at which of Initial candidates to compute Expected Loss. (LightRanking)
    """
    self.lightNs = lightNs

  @property
  def name(self):
    return "ExpectedLoss"

  @property
  def metrics_dict(self):
    metrics_to_compute = {}
    for lightN in self.lightNs:
      metric_name = "ExpectedLoss_atLight_" + str(lightN)
      metrics_to_compute[metric_name] = partial(score_loss_at_n, lightN=lightN)
    return metrics_to_compute

  def extract_label(self, prec, drec, drec_label):
    return drec_label

  def extract_prediction(self, prec, drec, drec_label):
    return extract_prediction_from_prediction_record(prec)


class CGRGroupedMetricsConfiguration(GroupedMetricsConfiguration):
  """
  This is the Cumulative Gain Ratio (CGR) Grouped metric computation configuration.
  CGR at the max length of each session is the default.
  CGR at additional positions can be computed by specifying a list of 'n's and 'k's
  """

  def __init__(self, lightNs=[], heavyKs=[]):
    """
    Args:
      lightNs (list): size of the list at which of Initial candidates to compute CGR. (LightRanking)
      heavyK (int):   size of the list at which of Refined candidates to compute CGR. (HeavyRanking)
    """
    self.lightNs = lightNs
    self.heavyKs = heavyKs

  @property
  def name(self):
    return "cgr"

  @property
  def metrics_dict(self):
    metrics_to_compute = {}
    for lightN in self.lightNs:
      for heavyK in self.heavyKs:
        metric_name = "cgr_atLight_" + str(lightN) + "_atHeavy_" + str(heavyK)
        metrics_to_compute[metric_name] = partial(cgr_at_nk, lightN=lightN, heavyK=heavyK)
    return metrics_to_compute

  def extract_label(self, prec, drec, drec_label):
    return drec_label

  def extract_prediction(self, prec, drec, drec_label):
    return extract_prediction_from_prediction_record(prec)


class RecallGroupedMetricsConfiguration(GroupedMetricsConfiguration):
  """
  This is the Recall Grouped metric computation configuration.
  Recall at the max length of each session is the default.
  Recall at additional positions can be computed by specifying a list of 'n's and 'k's
  """

  def __init__(self, n=[], k=[], w=[]):
    """
    Args:
      n (list): A list of ints. List of prediction rank thresholds (for light)
      k (list): A list of ints. List of label rank thresholds (for heavy)
    """
    self.predN = n
    self.labelK = k
    self.weight = w

  @property
  def name(self):
    return "group_recall"

  @property
  def metrics_dict(self):
    metrics_to_compute = {"group_recall_unweighted": recall_at_nk}
    if not self.weight:
      metrics_to_compute["group_recall_weighted"] = partial(recall_at_nk, w=self.weight)

    if self.predN and self.labelK:
      for n in self.predN:
        for k in self.labelK:
          if n >= k:
            metrics_to_compute[
              "group_recall_unweighted_at_L" + str(n) + "_at_H" + str(k)
            ] = partial(recall_at_nk, n=n, k=k)
            if self.weight:
              metrics_to_compute[
                "group_recall_weighted_at_L" + str(n) + "_at_H" + str(k)
              ] = partial(recall_at_nk, n=n, k=k, w=self.weight)

    if self.labelK and not self.predN:
      for k in self.labelK:
        metrics_to_compute["group_recall_unweighted_at_full_at_H" + str(k)] = partial(
          recall_at_nk, k=k
        )
        if self.weight:
          metrics_to_compute["group_recall_weighted_at_full_at_H" + str(k)] = partial(
            recall_at_nk, k=k, w=self.weight
          )
    return metrics_to_compute

  def extract_label(self, prec, drec, drec_label):
    return drec_label

  def extract_prediction(self, prec, drec, drec_label):
    return extract_prediction_from_prediction_record(prec)
