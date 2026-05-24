import random

import twml

get_time_based_dataset_files = twml.util.list_files_by_datetime


def resolve_train_and_eval_files_overlap(
  train_files, eval_files, fraction_kept_for_eval, seed=None
):
  """Resolve any overlap between train and eval files.

  Specifically, if there's an overlap between `train_files` and `eval_files`, then a fraction of
  the overlap (i.e. `fraction_kept_for_eval`) will be randomly assigned (exclusively) to the
  `eval_files`.

  The following example demonstrates its usage:

  >>> orig_train_files = ['f1', 'f2', 'f3', 'f4']
  >>> orig_eval_files = ['f1', 'f2', 'f3']
  >>> resolved_train_files, resolved_eval_files = resolve_train_and_eval_files_overlap(
  ...     orig_train_files, orig_eval_files, 0.5
  ... )
  >>> set(resolved_train_files) & set(resolved_eval_files) == set()
  True
  >>> len(resolved_train_files) == 3
  True
  >>> len(resolved_eval_files) == 2
  True

  Args:
    train_files: A list of the files used for training.
    eval_files: A list of the files used for validation.
    fraction_kept_for_eval: A fraction of files in the intersection between `train_files` and
      `eval_files` exclusively kept for evaluation.
    seed: A seed for generating random numbers.

  Returns:
    A tuple `(new_train_files, new_eval_files)` with the overlapping resolved.
  """

  rng = random.Random(seed)

  train_files = set(train_files)
  eval_files = set(eval_files)
  overlapping_files = train_files & eval_files
  train_files_selected_for_eval = set(rng.sample(
    overlapping_files,
    int(len(overlapping_files) * fraction_kept_for_eval)
  ))
  train_files = train_files - train_files_selected_for_eval
  eval_files = (eval_files - overlapping_files) | train_files_selected_for_eval
  return list(train_files), list(eval_files)


def get_time_based_dataset_files_for_train_and_eval(
  base_path,
  train_start_datetime,
  train_end_datetime,
  eval_start_datetime,
  eval_end_datetime,
  fraction_kept_for_eval,
  datetime_prefix_format='%Y/%m/%d/%H',
  extension='lzo',
  parallelism=1
):
  """Get train/eval dataset files organized with a time-based prefix.

  This is just a convenience built around `get_dataset_files_prefixed_by_time` and
  `resolve_train_and_eval_files_overlap`. Please refer to these functions for documentation.
  """

  train_files = get_time_based_dataset_files(
    base_path=base_path,
    start_datetime=train_start_datetime,
    end_datetime=train_end_datetime,
    datetime_prefix_format=datetime_prefix_format,
    extension=extension,
    parallelism=parallelism
  )
  eval_files = get_time_based_dataset_files(
    base_path=base_path,
    start_datetime=eval_start_datetime,
    end_datetime=eval_end_datetime,
    datetime_prefix_format=datetime_prefix_format,
    extension=extension,
    parallelism=parallelism
  )
  return resolve_train_and_eval_files_overlap(
    train_files=train_files,
    eval_files=eval_files,
    fraction_kept_for_eval=fraction_kept_for_eval
  )
