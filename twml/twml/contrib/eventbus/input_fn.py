from reader import EventBusPipedBinaryRecordReader
import tensorflow.compat.v1 as tf
import twml


"""
This module provides input function for DeepBird v2 training.
The training data records are loaded from an EventBus reader.
"""


def get_eventbus_data_record_generator(eventbus_reader):
  """
  This module provides a data record generater from EventBus reader.

  Args:
    eventbus_reader: EventBus reader

  Returns:
    gen: Data record generater
  """
  eventbus_reader.initialize()
  counter = [0]

  def gen():
    while True:
      record = eventbus_reader.read()
      if eventbus_reader.debug:
        tf.logging.warn("counter: {}".format(counter[0]))
        with open('tmp_record_{}.bin'.format(counter[0]), 'wb') as f:
          f.write(record)
        counter[0] = counter[0] + 1
      yield record
  return gen


def get_eventbus_data_record_dataset(eventbus_reader, parse_fn, batch_size):
  """
  This module generates batch data for training from a data record generator.
  """
  dataset = tf.data.Dataset.from_generator(
    get_eventbus_data_record_generator(eventbus_reader), tf.string, tf.TensorShape([]))
  return dataset.batch(batch_size).map(parse_fn, num_parallel_calls=4).prefetch(buffer_size=10)


def get_train_input_fn(feature_config, params, parse_fn=None):
  """
  This module provides input function for DeepBird v2 training.
  It gets batched training data from data record generator.
  """
  eventbus_reader = EventBusPipedBinaryRecordReader(
    params.jar_file, params.num_eb_threads, params.subscriber_id,
    filter_str=params.filter_str, debug=params.debug)

  train_parse_fn = parse_fn or twml.parsers.get_sparse_parse_fn(
    feature_config, ["ids", "keys", "values", "batch_size", "weights"])

  return lambda: get_eventbus_data_record_dataset(
    eventbus_reader, train_parse_fn, params.train_batch_size)
