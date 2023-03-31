# pylint: disable=no-member, attribute-defined-outside-init, too-many-instance-attributes
"""
Implementing HashedPercentileDiscretizer Layer
"""


from twitter.deepbird.util.hashing import (
  integer_multiplicative_hashing_uniform,
  integer_multiplicative_hashing,
)  # noqa: F401

from libtwml import percentile_discretizer_bin_indices
import numpy as np
import tensorflow.compat.v1 as tf
import twml
from twml.layers.layer import Layer
from twml.layers.partition import Partition
from twml.layers.stitch import Stitch


class HashedPercentileDiscretizer(Layer):
  """
  HashedPercentileDiscretizer layer is constructed by PercentileDiscretizerCalibrator
  after accumulating data
  and performing minimum description length (PercentileDiscretizer) calibration.

  HashedPercentileDiscretizer takes sparse continuous features and converts then to sparse
  binary features. Each binary output feature is associated to an HashedPercentileDiscretizer
  bin.
  Each HashedPercentileDiscretizer input feature is converted to n_bin bins.
  Each HashedPercentileDiscretizer calibration tries to find bin delimiters such
  that the number of features values
  per bin is roughly equal (for each given HashedPercentileDiscretizer feature).
  Note that if an input feature is rarely used, so will its associated output bin/features.
  The difference between this layer and PercentileDiscretizer is that the
  DeterministicPercentileDiscretize always assigns the same output id in the SparseTensor to the
  same input feature id + bin. This is useful if you want to user transfer learning on pre-trained
  sparse to dense embedding layers, but re-calibrate your discretizer on newer data.
  """

  def __init__(self, n_feature, n_bin, out_bits,
               bin_values=None, hash_keys=None, hash_values=None,
               bin_ids=None, feature_offsets=None,
               hash_fn=integer_multiplicative_hashing_uniform, **kwargs):
    """
    Creates a non-initialized `HashedPercentileDiscretizer` object.
    Before using the table you will have to initialize it. After initialization
    the table will be immutable.

    Parent class args:
      see [tf.layers.Layer](https://www.tensorflow.org/api_docs/python/tf/layers/Layer)
      for documentation of parent class arguments.

    Required args:
      n_feature:
        number of unique features accumulated during HashedPercentileDiscretizer calibration.
        This is the number of features in the hash map.
        Used to initialize bin_values, hash_keys, hash_values,
        bin_ids, bin_values and feature_offsets.
      n_bin:
        number of HashedPercentileDiscretizer bins used for
        HashedPercentileDiscretizer calibration. Used to initialize bin_values, hash_keys,
        hash_values, bin_ids, bin_values and feature_offsets.
      out_bits:
        Determines the maximum value for output feature IDs.
        The dense_shape of the SparseTensor returned by lookup(x)
        will be [x.shape[0], 1 << output_bits].

    Optional args:
      hash_keys:
        contains the features ID that HashedPercentileDiscretizer discretizes and knows
        about. The hash map (hash_keys->hash_values) is used for two reasons:
          1. divide inputs into two feature spaces:
          HashedPercentileDiscretizer vs non-HashedPercentileDiscretizer
          2. transate the HashedPercentileDiscretizer features into a hash_feature ID that
          HashedPercentileDiscretizer understands.
        The hash_map is expected to contain n_feature items.
      hash_values:
        translates the feature IDs into hash_feature IDs for HashedPercentileDiscretizer.
      bin_ids:
        a 1D Tensor of size n_feature * n_bin + 1 which contains
        unique IDs to which the HashedPercentileDiscretizer features will be translated to.
        For example, tf.Tensor(np.arange(n_feature * n_bin)) would produce
        the most efficient output space.
      bin_values:
        a 1D Tensor aligned with bin_ids.
        For a given hash_feature ID j, it's value bin's are indexed between
        `j*n_bin` and `j*n_bin + n_bin-1`.
        As such, bin_ids[j*n_bin+i] is translated from a hash_feature ID of j
        and a inputs value between
        `bin_values[j*n_bin + i]` and `bin_values[j*n_bin+i+1]`.
      feature_offsets:
        a 1D Tensor specifying the starting location of bins for a given feature id.
        For example, tf.Tensor(np.arange(0, bin_values.size, n_bin, dtype='int64')).
      hash_fn:
        a function that takes in `feature_ids`, `bucket_indices` and `output_size` and
        hashes the bucketed features into the `output_size` buckets. The default uses knuth's
        multiplicative hashing
    """
    super(HashedPercentileDiscretizer, self).__init__(**kwargs)

    max_discretizer_feature = n_feature * (n_bin + 1)
    self._n_feature = n_feature
    self._n_bin = n_bin

    if not self.built:
      self.build(input_shape=None)

    # build variables
    self.output_size = tf.convert_to_tensor(1 << out_bits, tf.int64)
    self._out_bits = out_bits

    hash_keys = hash_keys
    if hash_keys is None:
      hash_keys = np.empty(n_feature, dtype=np.int64)

    hash_values = hash_values
    if hash_values is None:
      hash_values = np.empty(n_feature, dtype=np.int64)

    initializer = tf.lookup.KeyValueTensorInitializer(hash_keys, hash_values)
    self.hash_map = tf.lookup.StaticHashTable(initializer, -1)
    self.bin_ids = bin_ids
    if bin_ids is None:
      bin_ids = np.empty(max_discretizer_feature, dtype=np.int64)

    self.bin_values = bin_values
    if bin_values is None:
      bin_values = np.empty(max_discretizer_feature, dtype=np.float32)

    self.feature_offsets = feature_offsets
    if feature_offsets is None:
      feature_offsets = np.empty(n_feature, dtype=np.int64)

    self.hash_fn = hash_fn

  def build(self, input_shape):  # pylint: disable=unused-argument
    """
    Creates the variables of the layer:
    hash_keys, hash_values, bin_ids, bin_values, feature_offsets and self.output_size.
    """
    # build layers
    self.partition = Partition()
    self.stitch = Stitch()
    # make sure this is last
    self.built = True

  def call(self, inputs, **kwargs):
    """Looks up `keys` in a table, outputs the corresponding values.

    Implements HashedPercentileDiscretizer inference where inputs are intersected with a
    hash_map.
    Part of the inputs are discretized using twml.discretizer
    to produce a discretizer_output SparseTensor.
    This SparseTensor is then joined with the original inputs SparseTensor,
    but only for the inputs keys that did not get discretized.

    Args:
      inputs: A 2D SparseTensor that is input to HashedPercentileDiscretizer for
        discretization. It has a dense_shape of [batch_size, input_size]
      name: A name for the operation (optional).
    Returns:
      A `SparseTensor` of the same type as `inputs`.
      Its dense_shape is [shape_input.dense_shape[0], 1 << output_bits].
    """
    if isinstance(inputs, tf.SparseTensor):
      inputs = twml.SparseTensor.from_tf(inputs)

    assert(isinstance(inputs, twml.SparseTensor))

    # sparse column indices
    ids = inputs.ids
    # sparse row indices
    keys = inputs.indices
    # sparse values
    vals = inputs.values

    hashed_keys = self.hash_map.lookup(keys)
    hashed_keys = tf.cast(hashed_keys, tf.int64)

    found = tf.not_equal(hashed_keys, tf.constant(-1, tf.int64))
    partition_ids = tf.cast(found, tf.int32)

    found = tf.reshape(found, [-1])
    continuous_feature_ids = tf.boolean_mask(keys, found)

    vals, key, indices = self.partition(partition_ids, vals, tf.where(found, hashed_keys, keys))
    non_discretizer_keys, discretizer_in_keys = key
    non_discretizer_vals, discretizer_in_vals = vals

    non_discretizer_keys = twml.util.limit_bits(non_discretizer_keys, self._out_bits)
    self.non_discretizer_keys = non_discretizer_keys

    # run HashedPercentileDiscretizer on the keys/values it knows about
    output = percentile_discretizer_bin_indices(discretizer_in_keys,
                                                discretizer_in_vals,
                                                self.bin_ids,
                                                self.bin_values,
                                                self.feature_offsets)
    discretizer_bucket_idxs, discretizer_vals = output
    new_discretizer_keys = self.hash_fn(continuous_feature_ids, discretizer_bucket_idxs,
                                        self.output_size)
    # Stitch the keys and values from discretizer and non discretizer indices back, with help
    # of the Stitch Layer
    self.discretizer_out_keys = new_discretizer_keys

    concat_data = self.stitch([non_discretizer_vals, discretizer_vals],
                              [non_discretizer_keys, new_discretizer_keys],
                              indices)

    concat_vals, concat_keys = concat_data

    # Generate output shape using _compute_output_shape

    batch_size = tf.to_int64(inputs.dense_shape[0])
    output_shape = [batch_size, self.output_size]
    return twml.SparseTensor(ids, concat_keys, concat_vals, output_shape).to_tf()
