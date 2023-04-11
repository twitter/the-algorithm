'''
Contains implementations of functions to read input data.
'''
from .dataset import stream_block_format_dataset

import tensorflow.compat.v1 as tf


def data_record_input_fn(
        files, batch_size, parse_fn,
        num_threads=2, repeat=False, dataset_fn=None,
        keep_rate=None, parts_downsampling_rate=None,
        shards=None, shard_index=None, shuffle=True, shuffle_files=True, interleave=True,
        initializable=False, log_tf_data_summaries=False,
        **kwargs):
  """
  Returns a nested structure of tf.Tensors containing the next element.
  Used by ``train_input_fn`` and ``eval_input_fn`` in DataRecordTrainer.
  By default, works with DataRecord dataset for compressed partition files.

  Args:
    files:
      List of files that will be parsed.
    batch_size:
      number of samples per batch.
    parse_fn:
      function passed to data loading for parsing individual data records.
      Usually one of the decoder functions like ``parsers.get_sparse_parse_fn``.
    num_threads (optional):
      number of threads used for loading data. Defaults to 2.
    repeat (optional):
      Repeat the dataset indefinitely. Defaults to False.
      Useful when you want to use ``train_steps`` or ``eval_steps``
      greater than the size of the dataset
      (otherwise Estimator.[train,evaluate] stops when the end of the dataset is reached).
    dataset_fn (optional):
      A function that modifies the dataset after it reads different interleaved parts files.
      Defaults to:

      .. code-block:: python

        def dataset_fn(dataset, parse_fn, batch_size):
          return dataset.batch(batch_size).map(parse_fn, 1)

    keep_rate (optional):
      A float value in (0.0, 1.0] that indicates to drop records according to the Bernoulli
      distribution with p = 1 - keep_rate.
      Defaults to None (no records dropped).

    parts_downsampling_rate (optional):
      A float value in (0.0, 1.0] that indicates the factor by which to downsample part files.
      For example, a value of 0.2 means only 20 percent of part files become part of the dataset.

    shards (optional):
      Number of partitions to shard the dataset into. This is useful for codistillation
      (https://arxiv.org/pdf/1804.03235.pdf) and other techniques that require each worker to
      train on disjoint partitions of the dataset.
      The dataset is not sharded by default.

    shard_index (optional):
      Which partition of the dataset to use if ``shards`` is set.

    shuffle (optional):
      Whether to shuffle the records. Defaults to True.

    shuffle_files (optional):
      Shuffle the list of files. Defaults to True.
      When False, files are iterated in the order they are passed in.

    interleave (optional):
      Interleave records from multiple files in parallel. Defaults to True.

    initializable (optional):
      A boolean indicator. When the Dataset Iterator depends on some resource, e.g. a HashTable or
      a Tensor, i.e. it's an initializable iterator, set it to True. Otherwise, default value (false)
      is used for most plain iterators.

      log_tf_data_summaries (optional):
        A boolean indicator denoting whether to add a `tf.data.experimental.StatsAggregator` to the
        tf.data pipeline. This adds summaries of pipeline utilization and buffer sizes to the output
        events files. This requires that `initializable` is `True` above.

  Returns:
    Iterator of elements of the dataset.
  """
  if not parse_fn:
    raise ValueError("default_input_fn requires a parse_fn")

  if log_tf_data_summaries and not initializable:
    raise ValueError("Require `initializable` if `log_tf_data_summaries`.")

  dataset = stream_block_format_dataset(
    files=files,
    parse_fn=parse_fn,
    batch_size=batch_size,
    repeat=repeat,
    num_threads=num_threads,
    dataset_fn=dataset_fn,
    keep_rate=keep_rate,
    parts_downsampling_rate=parts_downsampling_rate,
    shards=shards,
    shard_index=shard_index,
    shuffle=shuffle,
    shuffle_files=shuffle_files,
    interleave=interleave,
    **kwargs
  )

  # Add a tf.data.experimental.StatsAggregator
  # https://www.tensorflow.org/versions/r1.15/api_docs/python/tf/data/experimental/StatsAggregator
  if log_tf_data_summaries:
    aggregator = tf.data.experimental.StatsAggregator()
    options = tf.data.Options()
    options.experimental_stats.aggregator = aggregator
    dataset = dataset.with_options(options)
    stats_summary = aggregator.get_summary()
    tf.add_to_collection(tf.GraphKeys.SUMMARIES, stats_summary)

  if initializable:
    # when the data parsing dpends on some HashTable or Tensor, the iterator is initalizable and
    # therefore we need to be run explicitly
    iterator = dataset.make_initializable_iterator()
    tf.add_to_collection(tf.GraphKeys.TABLE_INITIALIZERS, iterator.initializer)
  else:
    iterator = dataset.make_one_shot_iterator()
  return iterator.get_next()


default_input_fn = data_record_input_fn  # pylint: disable=invalid-name
