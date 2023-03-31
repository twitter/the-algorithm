# pylint: disable=no-member, attribute-defined-outside-init, too-many-instance-attributes
"""
Implementing MDL Layer
"""


from .layer import Layer
from .partition import Partition
from .stitch import Stitch

import libtwml
import numpy as np
import tensorflow.compat.v1 as tf
import twml


class MDL(Layer):  # noqa: T000
  """
  MDL layer is constructed by MDLCalibrator after accumulating data
  and performing minimum description length (MDL) calibration.

  MDL takes sparse continuous features and converts then to sparse
  binary features. Each binary output feature is associated to an MDL bin.
  Each MDL input feature is converted to n_bin bins.
  Each MDL calibration tries to find bin delimiters such that the number of features values
  per bin is roughly equal (for each given MDL feature).
  Note that if an input feature is rarely used, so will its associated output bin/features.
  """

  def __init__(
          self,
          n_feature, n_bin, out_bits,
          bin_values=None, hash_keys=None, hash_values=None,
          bin_ids=None, feature_offsets=None, **kwargs):
    """
    Creates a non-initialized `MDL` object.
    Before using the table you will have to initialize it. After initialization
    the table will be immutable.

    Parent class args:
      see [tf.layers.Layer](https://www.tensorflow.org/api_docs/python/tf/layers/Layer)
      for documentation of parent class arguments.

    Required args:
      n_feature:
        number of unique features accumulated during MDL calibration.
        This is the number of features in the hash map.
        Used to initialize bin_values, hash_keys, hash_values,
        bin_ids, bin_values and feature_offsets.
      n_bin:
        number of MDL bins used for MDL calibration.
        Used to initialize bin_values, hash_keys, hash_values,
        bin_ids, bin_values and feature_offsets.
      out_bits:
        Determines the maximum value for output feature IDs.
        The dense_shape of the SparseTensor returned by lookup(x)
        will be [x.shape[0], 1 << output_bits].

    Optional args:
      hash_keys:
        contains the features ID that MDL discretizes and knows about.
        The hash map (hash_keys->hash_values) is used for two reasons:
          1. divide inputs into two feature spaces: MDL vs non-MDL
          2. transate the MDL features into a hash_feature ID that MDL understands.
        The hash_map is expected to contain n_feature items.
      hash_values:
        translates the feature IDs into hash_feature IDs for MDL.
      bin_ids:
        a 1D Tensor of size n_feature * n_bin + 1 which contains
        unique IDs to which the MDL features will be translated to.
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
    """
    super(MDL, self).__init__(**kwargs)
    tf.logging.warning("MDL will be deprecated. Please use PercentileDiscretizer instead")

    max_mdl_feature = n_feature * (n_bin + 1)
    self._n_feature = n_feature
    self._n_bin = n_bin

    self._hash_keys_initializer = tf.constant_initializer(
      hash_keys if hash_keys is not None
      else np.empty(n_feature, dtype=np.int64),
      dtype=np.int64
    )
    self._hash_values_initializer = tf.constant_initializer(
      hash_values if hash_values is not None
      else np.empty(n_feature, dtype=np.int64),
      dtype=np.int64
    )
    self._bin_ids_initializer = tf.constant_initializer(
      bin_ids if bin_ids is not None
      else np.empty(max_mdl_feature, dtype=np.int64),
      dtype=np.int64
    )
    self._bin_values_initializer = tf.constant_initializer(
      bin_values if bin_values is not None
      else np.empty(max_mdl_feature, dtype=np.float32),
      dtype=np.float32
    )
    self._feature_offsets_initializer = tf.constant_initializer(
      feature_offsets if feature_offsets is not None
      else np.empty(n_feature, dtype=np.int64),
      dtype=np.int64
    )

    # note that calling build here is an exception as typically __call__ would call build().
    # We call it here because we need to initialize hash_map.
    # Also note that the variable_scope is set by add_variable in build()
    if not self.built:
      self.build(input_shape=None)

    self.output_size = tf.convert_to_tensor(1 << out_bits, tf.int64)

  def build(self, input_shape):  # pylint: disable=unused-argument
    """
    Creates the variables of the layer:
    hash_keys, hash_values, bin_ids, bin_values, feature_offsets and self.output_size.
    """

    # build layers
    self.partition = Partition()
    self.stitch = Stitch()

    # build variables

    hash_keys = self.add_variable(
      'hash_keys',
      initializer=self._hash_keys_initializer,
      shape=[self._n_feature],
      dtype=tf.int64,
      trainable=False)

    hash_values = self.add_variable(
      'hash_values',
      initializer=self._hash_values_initializer,
      shape=[self._n_feature],
      dtype=tf.int64,
      trainable=False)

    # hashmap converts known features into range [0, n_feature)
    initializer = tf.lookup.KeyValueTensorInitializer(hash_keys, hash_values)
    self.hash_map = tf.lookup.StaticHashTable(initializer, -1)

    self.bin_ids = self.add_variable(
      'bin_ids',
      initializer=self._bin_ids_initializer,
      shape=[self._n_feature * (self._n_bin + 1)],
      dtype=tf.int64,
      trainable=False)

    self.bin_values = self.add_variable(
      'bin_values',
      initializer=self._bin_values_initializer,
      shape=[self._n_feature * (self._n_bin + 1)],
      dtype=tf.float32,
      trainable=False)

    self.feature_offsets = self.add_variable(
      'feature_offsets',
      initializer=self._feature_offsets_initializer,
      shape=[self._n_feature],
      dtype=tf.int64,
      trainable=False)

    # make sure this is last
    self.built = True

  def call(self, inputs, **kwargs):
    """Looks up `keys` in a table, outputs the corresponding values.

    Implements MDL inference where inputs are intersected with a hash_map.
    Part of the inputs are discretized using twml.mdl to produce a mdl_output SparseTensor.
    This SparseTensor is then joined with the original inputs SparseTensor,
    but only for the inputs keys that did not get discretized.

    Args:
      inputs: A 2D SparseTensor that is input to MDL for discretization.
        It has a dense_shape of [batch_size, input_size]
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

    # get intersect(keys, hash_map)
    hashed_keys = self.hash_map.lookup(keys)

    found = tf.not_equal(hashed_keys, tf.constant(-1, tf.int64))
    partition_ids = tf.cast(found, tf.int32)

    vals, key, indices = self.partition(partition_ids, vals, tf.where(found, hashed_keys, keys))
    non_mdl_keys, mdl_in_keys = key
    non_mdl_vals, mdl_in_vals = vals

    self.non_mdl_keys = non_mdl_keys

    # run MDL on the keys/values it knows about
    mdl_keys, mdl_vals = libtwml.ops.mdl(mdl_in_keys, mdl_in_vals, self.bin_ids, self.bin_values,
                                         self.feature_offsets)

    # handle output ID conflicts
    mdl_size = tf.size(self.bin_ids, out_type=tf.int64)
    non_mdl_size = tf.subtract(self.output_size, mdl_size)
    non_mdl_keys = tf.add(tf.floormod(non_mdl_keys, non_mdl_size), mdl_size)

    # Stitch the keys and values from mdl and non mdl indices back, with help
    # of the Stitch Layer

    # out for inference checking
    self.mdl_out_keys = mdl_keys

    concat_data = self.stitch([non_mdl_vals, mdl_vals],
                              [non_mdl_keys, mdl_keys],
                              indices)

    concat_vals, concat_keys = concat_data

    # Generate output shape using _compute_output_shape

    batch_size = tf.to_int64(inputs.dense_shape[0])
    output_shape = [batch_size, self.output_size]
    return twml.SparseTensor(ids, concat_keys, concat_vals, output_shape).to_tf()

  def compute_output_shape(self, input_shape):
    """Computes the output shape of the layer given the input shape.

    Args:
      input_shape: A (possibly nested tuple of) `TensorShape`.  It need not
        be fully defined (e.g. the batch size may be unknown).

    Raises NotImplementedError.

    """
    raise NotImplementedError
