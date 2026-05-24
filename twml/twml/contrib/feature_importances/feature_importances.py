# checkstyle: noqa

import time
from collections import defaultdict

from com.twitter.mlmetastore.modelrepo.client import ModelRepoClient
from com.twitter.mlmetastore.modelrepo.core import FeatureImportance, FeatureNames
from twitter.deepbird.io.util import match_feature_regex_list

from twml.contrib.feature_importances.helpers import (
  _get_feature_name_from_config,
  _get_feature_types_from_records,
  _get_metrics_hook,
  _expand_prefix,
  longest_common_prefix,
  write_list_to_hdfs_gfile)
from twml.contrib.feature_importances.feature_permutation import PermutedInputFnFactory
from twml.tracking import ExperimentTracker

from tensorflow.compat.v1 import logging
from requests.exceptions import HTTPError, RetryError
from queue import Queue


SERIAL = "serial"
TREE = "tree"
INDIVIDUAL = "Individual"
GROUP = "Group"
ROC_AUC = "roc_auc"
RCE = "rce"
LOSS = "loss"


def _repartition(feature_list_queue, fnames_ftypes, split_feature_group_on_period):
  """
  Iterate through letters to partition each feature by prefix, and then put each tuple
    (prefix, feature_partition) into the feature_list_queue
  Args:
    prefix (str): The prefix shared by each feature in list_of_feature_types
    feature_list_queue (Queue<(str, list<(str, str)>)>): The queue of feature groups
    fnames_ftypes (list<(str, str)>): List of (fname, ftype) pairs. Each fname begins with prefix
    split_feature_group_on_period (str): If true, require that feature groups end in a period
  Returns:
    Updated queue with each group in fnames_ftypes
  """
  assert len(fnames_ftypes) > 1

  split_character = "." if split_feature_group_on_period else None
  # Compute the longest prefix of the words
  prefix = longest_common_prefix(
    strings=[fname for fname, _ in fnames_ftypes], split_character=split_character)

  # Separate the features by prefix
  prefix_to_features = defaultdict(list)
  for fname, ftype in fnames_ftypes:
    assert fname.startswith(prefix)
    new_prefix = _expand_prefix(fname=fname, prefix=prefix, split_character=split_character)
    prefix_to_features[new_prefix].append((fname, ftype))

  # Add all of the new partitions to the queue
  for new_prefix, fname_ftype_list in prefix_to_features.items():
    extended_new_prefix = longest_common_prefix(
      strings=[fname for fname, _ in fname_ftype_list], split_character=split_character)
    assert extended_new_prefix.startswith(new_prefix)
    feature_list_queue.put((extended_new_prefix, fname_ftype_list))
  return feature_list_queue


def _infer_if_is_metric_larger_the_better(stopping_metric):
  # Infers whether a metric should be interpreted such that larger numbers are better (e.g. ROC_AUC), as opposed to
  #   larger numbers being worse (e.g. LOSS)
  if stopping_metric is None:
    raise ValueError("Error: Stopping Metric cannot be None")
  elif stopping_metric.startswith(LOSS):
    logging.info("Interpreting {} to be a metric where larger numbers are worse".format(stopping_metric))
    is_metric_larger_the_better = False
  else:
    logging.info("Interpreting {} to be a metric where larger numbers are better".format(stopping_metric))
    is_metric_larger_the_better = True
  return is_metric_larger_the_better


def _check_whether_tree_should_expand(baseline_performance, computed_performance, sensitivity, stopping_metric, is_metric_larger_the_better):
  """
  Returns True if
    - the metric is positive (e.g. ROC_AUC) and computed_performance is nontrivially smaller than the baseline_performance
    - the metric is negative (e.g. LOSS) and computed_performance is nontrivially larger than the baseline_performance
  """
  difference = ((baseline_performance[stopping_metric] - computed_performance[stopping_metric]) /
                 baseline_performance[stopping_metric])

  if not is_metric_larger_the_better:
      difference = -difference

  logging.info(
    "Found a {} difference of {}. Sensitivity is {}.".format("positive" if is_metric_larger_the_better else "negative", difference, sensitivity))
  return difference > sensitivity


def _compute_multiple_permuted_performances_from_trainer(
    factory, fname_ftypes, trainer, parse_fn, record_count):
  """Compute performances with fname and fype permuted
  """
  metrics_hook = _get_metrics_hook(trainer)
  trainer._estimator.evaluate(
    input_fn=factory.get_permuted_input_fn(
      batch_size=trainer._params.eval_batch_size, parse_fn=parse_fn, fname_ftypes=fname_ftypes),
    steps=(record_count + trainer._params.eval_batch_size) // trainer._params.eval_batch_size,
    hooks=[metrics_hook],
    checkpoint_path=trainer.best_or_latest_checkpoint)
  return metrics_hook.metric_values


def _get_extra_feature_group_performances(factory, trainer, parse_fn, extra_groups, feature_to_type, record_count):
  """Compute performance differences for the extra feature groups
  """
  extra_group_feature_performance_results = {}
  for group_name, raw_feature_regex_list in extra_groups.items():
    start = time.time()
    fnames = match_feature_regex_list(
      features=feature_to_type.keys(),
      feature_regex_list=[regex for regex in raw_feature_regex_list],
      preprocess=False,
      as_dict=False)

    fnames_ftypes = [(fname, feature_to_type[fname]) for fname in fnames]

    logging.info("Extracted extra group {} with features {}".format(group_name, fnames_ftypes))
    extra_group_feature_performance_results[group_name] = _compute_multiple_permuted_performances_from_trainer(
      factory=factory, fname_ftypes=fnames_ftypes,
      trainer=trainer, parse_fn=parse_fn, record_count=record_count)
    logging.info("\n\nImportances computed for {} in {} seconds \n\n".format(
      group_name, int(time.time() - start)))
  return extra_group_feature_performance_results


def _feature_importances_tree_algorithm(
    data_dir, trainer, parse_fn, fnames, stopping_metric, file_list=None, datarecord_filter_fn=None, split_feature_group_on_period=True,
    record_count=99999, is_metric_larger_the_better=None, sensitivity=0.025, extra_groups=None, dont_build_tree=False):
  """Tree algorithm for feature and feature group importances. This algorithm build a prefix tree of
  the feature names and then traverses the tree with a BFS. At each node (aka group of features with
  a shared prefix) the algorithm computes the performance of the model when we permute all features
  in the group. The algorithm only zooms-in on groups that impact the performance by more than
  sensitivity. As a result, features that affect the model performance by less than sensitivity will
  not have an exact importance.
  Args:
    data_dir: (str): The location of the training or testing data to compute importances over.
      If None, the trainer._eval_files are used
    trainer: (DataRecordTrainer): A DataRecordTrainer object
    parse_fn: (function): The parse_fn used by eval_input_fn
    fnames (list<string>): The list of feature names
    stopping_metric (str): The metric to use to determine when to stop expanding trees
    file_list (list<str>): The list of filenames. Exactly one of file_list and data_dir should be
      provided
    datarecord_filter_fn (function): a function takes a single data sample in com.twitter.ml.api.ttypes.DataRecord format
        and return a boolean value, to indicate if this data record should be kept in feature importance module or not.
    split_feature_group_on_period (boolean): If true, split feature groups by period rather than on
      optimal prefix
    record_count (int): The number of records to compute importances over
    is_metric_larger_the_better (boolean): If true, assume that stopping_metric is a metric where larger
      values are better (e.g. ROC-AUC)
    sensitivity (float): The smallest change in performance to continue to expand the tree
    extra_groups (dict<str, list<str>>): A dictionary mapping the name of extra feature groups to the list of
      the names of the features in the group. You should only supply a value for this argument if you have a set
      of features that you want to evaluate as a group but don't share a prefix
    dont_build_tree (boolean): If True, don't build the tree and only compute the extra_groups importances
  Returns:
    A dictionary that contains the individual and group feature importances
  """
  factory = PermutedInputFnFactory(
    data_dir=data_dir, record_count=record_count, file_list=file_list, datarecord_filter_fn=datarecord_filter_fn)
  baseline_performance = _compute_multiple_permuted_performances_from_trainer(
    factory=factory, fname_ftypes=[],
    trainer=trainer, parse_fn=parse_fn, record_count=record_count)
  out = {"None": baseline_performance}

  if stopping_metric not in baseline_performance:
    raise ValueError("The stopping metric '{}' not found in baseline_performance. Metrics are {}".format(
      stopping_metric, list(baseline_performance.keys())))

  is_metric_larger_the_better = (
    is_metric_larger_the_better if is_metric_larger_the_better is not None else _infer_if_is_metric_larger_the_better(stopping_metric))
  logging.info("Using {} as the stopping metric for the tree algorithm".format(stopping_metric))

  feature_to_type = _get_feature_types_from_records(records=factory.records, fnames=fnames)
  all_feature_types = list(feature_to_type.items())

  individual_feature_performances = {}
  feature_group_performances = {}
  if dont_build_tree:
    logging.info("Not building feature importance trie. Will only compute importances for the extra_groups")
  else:
    logging.info("Building feature importance trie")
    # Each element in the Queue will be a tuple of (prefix, list_of_feature_type_pairs) where
    #   each feature in list_of_feature_type_pairs will have have the prefix "prefix"
    feature_list_queue = _repartition(
      feature_list_queue=Queue(), fnames_ftypes=all_feature_types, split_feature_group_on_period=split_feature_group_on_period)

    while not feature_list_queue.empty():
      # Pop the queue. We should never have an empty list in the queue
      prefix, fnames_ftypes = feature_list_queue.get()
      assert len(fnames_ftypes) > 0

      # Compute performance from permuting all features in fname_ftypes
      logging.info(
        "\n\nComputing importances for {} ({}...). {} elements left in the queue \n\n".format(
          prefix, fnames_ftypes[:5], feature_list_queue.qsize()))
      start = time.time()
      computed_performance = _compute_multiple_permuted_performances_from_trainer(
        factory=factory, fname_ftypes=fnames_ftypes,
        trainer=trainer, parse_fn=parse_fn, record_count=record_count)
      logging.info("\n\nImportances computed for {} in {} seconds \n\n".format(
        prefix, int(time.time() - start)))
      if len(fnames_ftypes) == 1:
        individual_feature_performances[fnames_ftypes[0][0]] = computed_performance
      else:
        feature_group_performances[prefix] = computed_performance
      # Dig deeper into the features in fname_ftypes only if there is more than one feature in the
      #    list and the performance drop is nontrivial
      logging.info("Checking performance for {} ({}...)".format(prefix, fnames_ftypes[:5]))
      check = _check_whether_tree_should_expand(
        baseline_performance=baseline_performance, computed_performance=computed_performance,
        sensitivity=sensitivity, stopping_metric=stopping_metric, is_metric_larger_the_better=is_metric_larger_the_better)
      if len(fnames_ftypes) > 1 and check:
        logging.info("Expanding {} ({}...)".format(prefix, fnames_ftypes[:5]))
        feature_list_queue = _repartition(
          feature_list_queue=feature_list_queue, fnames_ftypes=fnames_ftypes, split_feature_group_on_period=split_feature_group_on_period)
      else:
        logging.info("Not expanding {} ({}...)".format(prefix, fnames_ftypes[:5]))

  # Baseline performance is grouped in with individual_feature_importance_results
  individual_feature_performance_results = dict(
    out, **{k: v for k, v in individual_feature_performances.items()})
  group_feature_performance_results = {k: v for k, v in feature_group_performances.items()}

  if extra_groups is not None:
    logging.info("Computing performances for extra groups {}".format(extra_groups.keys()))
    for group_name, performances in _get_extra_feature_group_performances(
        factory=factory,
        trainer=trainer,
        parse_fn=parse_fn,
        extra_groups=extra_groups,
        feature_to_type=feature_to_type,
        record_count=record_count).items():
      group_feature_performance_results[group_name] = performances
  else:
    logging.info("Not computing performances for extra groups")

  return {INDIVIDUAL: individual_feature_performance_results,
          GROUP: group_feature_performance_results}


def _feature_importances_serial_algorithm(
    data_dir, trainer, parse_fn, fnames, file_list=None, datarecord_filter_fn=None, factory=None, record_count=99999):
  """Serial algorithm for feature importances. This algorithm computes the
  importance of each feature.
  """
  factory = PermutedInputFnFactory(
    data_dir=data_dir, record_count=record_count, file_list=file_list, datarecord_filter_fn=datarecord_filter_fn)
  feature_to_type = _get_feature_types_from_records(records=factory.records, fnames=fnames)

  out = {}
  for fname, ftype in list(feature_to_type.items()) + [(None, None)]:
    logging.info("\n\nComputing importances for {}\n\n".format(fname))
    start = time.time()
    fname_ftypes = [(fname, ftype)] if fname is not None else []
    out[str(fname)] = _compute_multiple_permuted_performances_from_trainer(
      factory=factory, fname_ftypes=fname_ftypes,
      trainer=trainer, parse_fn=parse_fn, record_count=record_count)
    logging.info("\n\nImportances computed for {} in {} seconds \n\n".format(
      fname, int(time.time() - start)))
  # The serial algorithm does not compute group feature results.
  return {INDIVIDUAL: out, GROUP: {}}


def _process_feature_name_for_mldash(feature_name):
  # Using a forward slash in the name causes feature importance writing to fail because strato interprets it as
  #   part of a url
  return feature_name.replace("/", "__")


def compute_feature_importances(
    trainer, data_dir=None, feature_config=None, algorithm=TREE, parse_fn=None, datarecord_filter_fn=None, **kwargs):
  """Perform a feature importance analysis on a trained model
  Args:
    trainer: (DataRecordTrainer): A DataRecordTrainer object
    data_dir: (str): The location of the training or testing data to compute importances over.
      If None, the trainer._eval_files are used
    feature_config (contrib.FeatureConfig): The feature config object. If this is not provided, it
      is taken from the trainer
    algorithm (str): The algorithm to use
    parse_fn: (function): The parse_fn used by eval_input_fn. By default this is
      feature_config.get_parse_fn()
    datarecord_filter_fn (function): a function takes a single data sample in com.twitter.ml.api.ttypes.DataRecord format
        and return a boolean value, to indicate if this data record should be kept in feature importance module or not.
  """

  # We only use the trainer's eval files if an override data_dir is not provided
  if data_dir is None:
    logging.info("Using trainer._eval_files (found {} as files)".format(trainer._eval_files))
    file_list = trainer._eval_files
  else:
    logging.info("data_dir provided. Looking at {} for data.".format(data_dir))
    file_list = None

  feature_config = feature_config or trainer._feature_config
  out = {}
  if not feature_config:
    logging.warn("WARN: Not computing feature importance because trainer._feature_config is None")
    out = None
  else:
    parse_fn = parse_fn if parse_fn is not None else feature_config.get_parse_fn()
    fnames = _get_feature_name_from_config(feature_config)
    logging.info("Computing importances for {}".format(fnames))
    logging.info("Using the {} feature importance computation algorithm".format(algorithm))
    algorithm = {
      SERIAL: _feature_importances_serial_algorithm,
      TREE: _feature_importances_tree_algorithm}[algorithm]
    out = algorithm(data_dir=data_dir, trainer=trainer, parse_fn=parse_fn, fnames=fnames, file_list=file_list, datarecord_filter_fn=datarecord_filter_fn, **kwargs)
  return out


def write_feature_importances_to_hdfs(
    trainer, feature_importances, output_path=None, metric="roc_auc"):
  """Publish a feature importance analysis to hdfs as a tsv
  Args:
    (see compute_feature_importances for other args)
    trainer (Trainer)
    feature_importances (dict): Dictionary of feature importances
    output_path (str): The remote or local file to write the feature importances to. If not
      provided, this is inferred to be the trainer save dir
    metric (str): The metric to write to tsv
  """
  # String formatting appends (Individual) or (Group) to feature name depending on type
  perfs = {"{} ({})".format(k, importance_key) if k != "None" else k: v[metric]
    for importance_key, importance_value in feature_importances.items()
    for k, v in importance_value.items()}

  output_path = ("{}/feature_importances-{}".format(
    trainer._save_dir[:-1] if trainer._save_dir.endswith('/') else trainer._save_dir,
    output_path if output_path is not None else str(time.time())))

  if len(perfs) > 0:
    logging.info("Writing feature_importances for {} to hdfs".format(perfs.keys()))
    entries = [
      {
        "name": name,
        "drop": perfs["None"] - perfs[name],
        "pdrop": 100 * (perfs["None"] - perfs[name]) / (perfs["None"] + 1e-8),
        "perf": perfs[name]
      } for name in perfs.keys()]
    out = ["Name\tPerformance Drop\tPercent Performance Drop\tPerformance"]
    for entry in sorted(entries, key=lambda d: d["drop"]):
      out.append("{name}\t{drop}\t{pdrop}%\t{perf}".format(**entry))
    logging.info("\n".join(out))
    write_list_to_hdfs_gfile(out, output_path)
    logging.info("Wrote feature feature_importances to {}".format(output_path))
  else:
    logging.info("Not writing feature_importances to hdfs")
  return output_path


def write_feature_importances_to_ml_dash(trainer, feature_importances, feature_config=None):
  # type: (DataRecordTrainer, FeatureConfig, dict) -> None
  """Publish feature importances + all feature names to ML Metastore
  Args:
    trainer: (DataRecordTrainer): A DataRecordTrainer object
    feature_config (contrib.FeatureConfig): The feature config object. If this is not provided, it
      is taken from the trainer
    feature_importances (dict, default=None): Dictionary of precomputed feature importances
    feature_importance_metric (str, default=None): The metric to write to ML Dashboard
  """
  experiment_tracking_path = trainer.experiment_tracker.tracking_path\
    if trainer.experiment_tracker.tracking_path\
    else ExperimentTracker.guess_path(trainer._save_dir)

  logging.info('Computing feature importances for run: {}'.format(experiment_tracking_path))

  feature_importance_list = []
  for key in feature_importances:
    for feature, imps in feature_importances[key].items():
      logging.info('FEATURE NAME: {}'.format(feature))
      feature_name = feature.split(' (').pop(0)
      for metric_name, value in imps.items():
        try:
          imps[metric_name] = float(value)
          logging.info('Wrote feature importance value {} for metric: {}'.format(str(value), metric_name))
        except Exception as ex:
          logging.error("Skipping writing metric:{} to ML Metastore due to invalid metric value: {} or value type: {}. Exception: {}".format(metric_name, str(value), type(value), str(ex)))
          pass

      feature_importance_list.append(FeatureImportance(
        run_id=experiment_tracking_path,
        feature_name=_process_feature_name_for_mldash(feature_name),
        feature_importance_metrics=imps,
        is_group=key == GROUP
      ))

# setting feature config to match the one used in compute_feature_importances
  feature_config = feature_config or trainer._feature_config
  feature_names = FeatureNames(
    run_id=experiment_tracking_path,
    names=list(feature_config.features.keys())
  )

  try:
    client = ModelRepoClient()
    logging.info('Writing feature importances to ML Metastore')
    client.add_feature_importances(feature_importance_list)
    logging.info('Writing feature names to ML Metastore')
    client.add_feature_names(feature_names)
  except (HTTPError, RetryError) as err:
    logging.error('Feature importance is not being written due to: '
                  'HTTPError when attempting to write to ML Metastore: \n{}.'.format(err))
