import os
import re
import time

from collections import OrderedDict

from absl import logging
import numpy as np
import tensorflow.compat.v1 as tf
from tensorflow.python.ops.lookup_ops import index_table_from_tensor

import twml

# Padding is 0, UNK is 1:
PAD_WORD_ID = 0
OOV_WORD_ID = 1


def load_initializers_from_csv(
  embedding_path, vocab_size=-1, embedding_size=None, separator=None, vocab=None
):
  """
  Loads embeddings saved in the `glove format <https://nlp.stanford.edu/projects/glove/>`_.
  The glove format is a txt file separated by spaces.
  Each line looks like: "word 0.00001 0.2334 ...".

  Arguments:
    embedding_path:
      path to the embeddings file on HDFS (hdfs://default/...)
      or its local_path (/path/to/...).
      The embedding_path may also specify a pattern. In which case, the embeddings
      are read in the lexical order of the filenames that match the order.
    vocab_size:
      the maximum size of the vocabulary. The top ``vocab_size`` words in the file
      are included in the vocabulary. If you specify a positive vocab_size,
      the words are expected to be in descending order of frequency.
      This allows the embeddings to be easily filtered to top vocab_size words.
      Reducing the vocab_size acts as a regularizer, preventing the model to overfit on rarer words.
      A negative vocab_size loads all embeddings.
      Reducing the vocab_size may also help with memory issues,
      allowing the embedding initializers to fit inside the graph.
    embedding_size:
      Defaults to None. If None, the embedding size is infered from the file name.
      For example, ``glove.300d.txt`` and ``glove300d200.txt`` will both infrered
      as ``embedding_size=300``. If this can't be done, the ``embedding_size`` is
      inferred from the first line in the file. If ``embedding_size`` is provided,
      only the last ``embedding_size`` values of each line are considered. This
      allows the line parser to recover from partial word parsing errors.
    separator:
      Specifies the separator to use when splitting each line into values.
      Default value is a whitespace (same as glove format).
    vocab:
      OrderedDict mapping words to np.array embedding vectors. Initializes the vocabulary.
      Duplicate words found in the file are ignored.
      Defaults to a vocabulary of two words::

        vocab = OrderedDict()
        vocab[''] = np.random.randn(embedding_size)
        vocab['<UNK>'] = np.random.randn(embedding_size)

  Returns:
    tuple of (vocab_initializer, weight_initializer, shape)

    vocab_initializer:
      A tf.constant_initializer containing a vector of word strings of size vocab_size.
    weight_initializer:
      A twml.contrib.initializers.partition_constant_initializer containing
      the weight matrix of embeddings of size vocab_size x embedding_size.
    shape:
      A tuple containing of (vocab_size, embedding_size).

  """

  start = time.time()

  embedding_path = twml.util.sanitize_hdfs_path(embedding_path)

  is_user_vocab = True
  if vocab is None:
    vocab = OrderedDict()
    vocab[''] = True
    vocab['<UNK>'] = True
    is_user_vocab = False
  elif not isinstance(vocab, OrderedDict):
    raise RuntimeError(
      "Expecting vocab argument of type OrderedDict or None. "
      "Got type %s instead." % type(vocab).__name__
    )

  if embedding_size is None:
    embedding_file = os.path.basename(embedding_path)
    match = re.search(r"[^\d]([\d]+)d", embedding_file)
    if match is not None:
      embedding_size = int(match.group(1))

  if embedding_size is not None and not isinstance(embedding_size, int):
    raise RuntimeError(
      "Expecting embedding_size argument of type int or None. "
      "Got type %s, instead." % type(embedding_size).__name__
    )

  embedding_paths = sorted(tf.io.gfile.glob(embedding_path))

  if len(embedding_paths) > 1:
    raise ValueError(
      "You are most likely using a the wrong --embedding.path"
    )

  embedding_path = embedding_paths[0]
  logging.info("Reading embeddings file from path %s.." % embedding_path)

  with tf.io.gfile.GFile(embedding_path) as f:
    lines = f.readlines()

  logging.info("Done reading embeddings file from path %s." % embedding_path)

  logging.info("Parsing vocbulary and embeddings...")

  for line in lines:
    # Word and weights separated by space
    values = line.strip().split(separator)
    # Word is first symbol on each line
    word = values[0]

    if word not in vocab:
      if embedding_size is None or embedding_size <= 0:
        # get all elements after the first one.
        word_weights = values[1:]
        embedding_size = len(word_weights)
      else:
        # get the last embedding_size elements
        word_weights = values[-min(embedding_size, len(values) - 1) :]

      try:
        if len(word_weights) != embedding_size:
          raise ValueError

        word_weights = np.asarray(word_weights, dtype=np.float32)
        vocab[word] = word_weights
      except ValueError:
        logging.info("Wasn't able to load embeddings for word '%s'. Ignoring it" % word)

      vocab_len = len(vocab)
      if vocab_size > 0 and vocab_len == vocab_size:
        # Limit vocabulary to top terms
        break
      elif (vocab_len % 1000) == 0:
        logging.info("Loaded %d words into vocab" % vocab_len)

    else:
      logging.info("found duplicate word: %s" % word)

  if not is_user_vocab:
    vocab[''] = np.random.randn(embedding_size)
    vocab['<UNK>'] = np.random.randn(embedding_size)

  words = list(vocab.keys())
  weights = list(vocab.values())

  weights = np.asarray(weights, dtype=np.float32)
  assert weights.shape[0] == len(vocab)
  assert weights.shape[1] == embedding_size

  vocab_initializer = tf.constant_initializer(words, tf.string)
  weight_initializer = twml.contrib.initializers.PartitionConstant(weights, tf.float32)

  logging.info("Loaded %d embeddings in %d seconds." % (len(vocab), time.time() - start))
  return vocab_initializer, weight_initializer, weights.shape


def add_parser_arguments(parser):
  """
  Adds the embedding.path and embedding.vocab_size command-line arguments to the parser.
  These can be used to call an initializer loader function like
  the ``load_initializers_from_csv`` function.

  Arguments:
    parser: argparse.ArgumentParser instance obtained from Trainer.get_trainer_parser

  Returns:
    argparse.ArgumentParser instance with discretizer-specific arguments added
  """

  parser.add_argument(
    "--embedding.path",
    "--embedding_path",
    dest="embedding_path",
    type=str,
    default=None,
    help="When specified, loads glove embeddings from .txt glove file",
  )
  parser.add_argument(
    "--embedding.vocab_size",
    "--embedding_vocab_size",
    dest="embedding_vocab_size",
    type=int,
    default=-1,
    help="Size of vocabulary. Uses this many of the most frequent terms. Defaults to -1 (use full vocab).",
  )

  return parser


class EmbeddingLookup(twml.layers.Layer):
  """Layer for looking up embeddings.
  Transforms a sequence of strings to a sequence of embeddings.

  Arguments:
    vocab_size:
      The number of word strings and embeddings in the vocabulary.
    output_size:
      Long or Integer, dimensionality of the output space. The embedding vector size.
    vocab_initializer:
      Initializer function for the vocabulary. Required. The initializer should
      return a list of strings of size vocab_size.
    weight_initializer:
      Initializer function for the weight matrix of size vocab_size x output_size.
      This argument defaults to zeros_initializer().
      This is valid when the EmbeddingLookup is the first layer of
      parameters but should be changed otherwise.
    trainable:
      Boolean, if `True` adds variables to the graph collection
      ``GraphKeys.TRAINABLE_VARIABLES`` (see `tf.Variable
      <https://www.tensorflow.org/versions/master/api_docs/python/tf/Variable>`_).
      Defaults to True: trains the embeddings.
    num_oov_buckets:
      The number of buckets to use for OOV strings. These bucket ids occur after the vocab bucket
      ids. Hashing is used to assign OOV strings to these buckets. If `num_oov_buckets` is not
      specified, index `OOV_WORD_ID` is used for OOV strings.
    name:
      String, the name of the layer. Layers with the same name will
      share weights, but to avoid mistakes we require ``reuse=True`` in such cases.
    num_partitions:
      Number of partitions to use for the weight variable. Defaults to 1.
    partition_axis:
      If num_partitions is specified, the partition axis for the weight variable
      Defaults to 0 (partition by row).
      Must be 0 (row) or 1 (column, does not support yet)
    weight_regularizer:
      Regularizer function for the weight matrix.
      Ensure to add tf.losses.get_regularization_loss() to your loss for this to take effect.
    dtype:
      Defaults to tf.float32. Specifies the dtype of the weights.
    use_placeholder:
      Defaults to True.
      If set to `True`, the initializer is passed via a placeholder. The initializer in this case needs to be of type `keras.initializers.Constant`.
      If set to `False`, the initializer becomes part of the graph. This can sometimes be beyond what protobuf clients support.
    checkpoint_dir:
      Default to None.
      If set to the path of a checkpoint, load embedding from the checkpoint.
    convert_to_lowercase:
      Default to True.
      Converting all string inputs to lowercase.

  Notes: If `use_placeholder` is set to `True`, the feed dictionary can be accessed by calling `twml.contrib.initializers.get_init_feed_dict()`.
  """

  def __init__(
    self,
    vocab_size,
    output_size,
    vocab_initializer,
    weight_initializer=None,
    trainable=True,
    num_oov_buckets=None,
    oov_word_id=None,
    name=None,
    num_partitions=1,
    partition_axis=0,
    weight_regularizer=None,
    dtype=None,
    use_placeholder=True,
    checkpoint_dir=None,
    convert_to_lowercase=True,
    **kwargs,
  ):
    if dtype is None:
      # prevents a bug where the parent class defaults to the type of the first input tensor.
      dtype = tf.float32
    super().__init__(trainable=trainable, name=name, dtype=dtype, **kwargs)
    # Weights initialization is set to 0s. This is safe for full sparse layers because
    # you are supposed to learn your embedding from the label.

    is_constant_init = isinstance(weight_initializer, tf.keras.initializers.Constant)
    if use_placeholder and (not is_constant_init) and (weight_initializer is not None):
      raise ValueError("Weight initializer should be a `Constant` or `None`.")

    if weight_initializer is None:
      self.weight_initializer = tf.zeros_initializer()
    else:
      self.weight_initializer = weight_initializer
    self.use_placeholder = use_placeholder
    self.checkpoint_dir = checkpoint_dir
    self.convert_to_lowercase = convert_to_lowercase

    self.vocab_initializer = vocab_initializer
    self.vocab_size = vocab_size
    self.output_size = output_size
    self.num_partitions = num_partitions
    self.partition_axis = partition_axis
    self.weight_regularizer = weight_regularizer
    self.trainable = trainable
    self.oov_word_id = oov_word_id
    self.num_oov_buckets = num_oov_buckets

    if self.oov_word_id is not None and self.num_oov_buckets is not None:
      raise ValueError("At most one of oov_word_id or num_oov_buckets should be specified")
    elif self.oov_word_id is None and self.num_oov_buckets is None:
      self.oov_word_id = OOV_WORD_ID  # use the default OOV word id

    if partition_axis != 0:
      raise NotImplementedError("embedding_lookup only supports partition_axis = 0")

  def build(self, input_shapes):
    """
    creates the ``vocab`` and ``weight`` Variables
    of shape ``[vocab_size]`` and ``[vocab_size, output_size]`` respectively.
    """
    partitioner = None

    additional_buckets_for_oov = self.num_oov_buckets if self.num_oov_buckets is not None else 0
    shape = [self.vocab_size + additional_buckets_for_oov, self.output_size]

    if self.use_placeholder:
      embedding_weight_initializer = twml.contrib.initializers.PlaceholderInitializer(
        shape, self.dtype
      )
      tf.add_to_collection(
        twml.contrib.initializers.TWML_INIT_FEED_KEY,
        {embedding_weight_initializer.value: self.weight_initializer.value},
      )
    else:
      embedding_weight_initializer = self.weight_initializer

    if self.num_partitions:
      partition_axis = int(self.partition_axis)
      partitioner = tf.fixed_size_partitioner(self.num_partitions, axis=partition_axis)
    else:
      # Regular variables do not like it when you pass both constant tensors and shape
      if not callable(self.weight_initializer):
        shape = None

    self.vocab = self.add_variable(
      'vocab',
      initializer=self.vocab_initializer,
      shape=[self.vocab_size],
      dtype=tf.string,
      trainable=False,
    )

    self.weight = self.add_variable(
      'weight',
      initializer=None if self.checkpoint_dir is not None else embedding_weight_initializer,
      regularizer=self.weight_regularizer,
      shape=shape,
      dtype=self.dtype,
      trainable=self.trainable,
      partitioner=partitioner,
    )
    if self.checkpoint_dir is not None:
      twml.trainers.trainer.init_from_checkpoint(self.checkpoint_dir, {'weight': self.weight.name})

    self.built = True

  def call(
    self, inputs, debug=False, oov_summaries=False, **kwargs
  ):  # pylint: disable=unused-argument
    """Converts word strings to word ids using the vocabulary lookup table.
    Then converts the word ids to their commensurate embedding vector.

    Arguments:
      inputs:
        A tensor of word strings. Typically, of size batch_size x seq_len.
      debug:
        When True, prints the input strings and their commensurate input_ids.
        Defaults to False.
      oov_summaries:
        When True, log the out-of-vocabulary (OOV) rate to TensorBoard
        Defaults to False.

    Returns:
      The mapping of input word strings to output embedding vectors.
      Given an input of shape ``batch_size x seq_len``, the output has shape
      ``batch_size x seq_len x embedding_size``.
    """
    if self.convert_to_lowercase:
      inputs = tf.strings.lower(inputs)
    if self.num_oov_buckets is None:
      lookup_table = index_table_from_tensor(self.vocab, default_value=self.oov_word_id)
    else:
      lookup_table = index_table_from_tensor(self.vocab, num_oov_buckets=self.num_oov_buckets)
    input_ids = lookup_table.lookup(inputs)

    if oov_summaries:
      oov_count = tf.reduce_sum(
        tf.cast(tf.math.equal(input_ids, self.oov_word_id), tf.dtypes.float32)
      )
      valid_count = tf.reduce_sum(
        tf.cast(tf.math.not_equal(input_ids, PAD_WORD_ID), tf.dtypes.float32)
      )
      oov_rate = oov_count / valid_count
      tf.summary.scalar('OOV_rate', oov_rate)

    if debug:

      def print_debug():
        return tf.print("input_strings:", inputs, "\ninput_ids: ", input_ids, summarize=140)

      with tf.control_dependencies([twml.util.do_every_n_steps(print_debug, 1000)]):
        input_ids = tf.identity(input_ids)

    output_embeddings = tf.nn.embedding_lookup(
      params=self.weight, ids=input_ids, partition_strategy='div'
    )

    output_shape = inputs.shape.concatenate(tf.TensorShape([self.output_size]))
    output_embeddings.set_shape(output_shape)

    return output_embeddings
