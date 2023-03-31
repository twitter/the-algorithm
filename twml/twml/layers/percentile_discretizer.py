# pylint: disable=no-member, attribute-defined-outside-init, too-many-instance-attributes
"""
Implementing PercentileDiscretizer Layer
"""


import libtwml
import numpy as np
import tensorflow.compat.v1 as tf
import twml
from twml.layers import Layer


class PercentileDiscretizer(Layer):
  """
  PercentileDiscretizer layer is constructed by PercentileDiscretizerCalibrator after
  accumulating data and performing percentile bucket calibration.

  PercentileDiscretizer takes sparse continuous features and converts then to sparse
  binary features. Each binary output feature is associated to an PercentileDiscretizer bin.
  Each PercentileDiscretizer input feature is converted to n_bin bins.
  Each PercentileDiscretizer calibration tries to find bin delimiters such
  that the number of features values per bin is roughly equal (for
  each given PercentileDiscretizer feature). In other words, bins are calibrated to be approx.
  equiprobable, according to the given calibration data.
  Note that if an input feature is rarely used, so will its associated output bin/features.
  """

  def __init__(
      self,
      n_feature, n_bin, out_bits,
      bin_values=None, hash_keys=None, hash_values=None,
      bin_ids=None, feature_offsets=None, num_parts=1, cost_per_unit=100, **kwargs):
    """
    Creates a non-initialized `PercentileDiscretizer` object.
    Before using the table you will have to initialize it. After initialization
    the table will be immutable.

    If there are no calibrated features, then the discretizer will only apply
    twml.util.limit_bits to the the feature keys (aka "feature_ids"). Essentially,
    the discretizer will be a "no-operation", other than obeying `out_bits`

    Parent class args:
      see [tf.layers.Layer](https://www.tensorflow.org/api_docs/python/tf/layers/Layer)
      for documentation of parent class arguments.

    Required args:
      n_feature:
        number of unique features accumulated during PercentileDiscretizer calibration.
        This is the number of features in the hash map.
        Used to initialize bin_values, hash_keys, hash_values,
        bin_ids, bin_values and feature_offsets.
      n_bin:
        number of PercentileDiscretizer bins used for PercentileDiscretizer calibration.
        Used to initialize bin_values, hash_keys, hash_values,
        bin_ids, bin_values and feature_offsets.
      out_bits:
        Determines the maximum value for output feature IDs.
        The dense_shape of the SparseTensor returned by lookup(x)
        will be [x.shape[0], 1 << output_bits].

    Optional args:
      hash_keys:
        contains the features ID that PercentileDiscretizer discretizes and knows about.
        The hash map (hash_keys->hash_values) is used for two reasons:
          1. divide inputs into two feature spaces:
          PercentileDiscretizer vs non-PercentileDiscretizer
          2. transate the PercentileDiscretizer features into a hash_feature ID that
          PercentileDiscretizer understands.
        The hash_map is expected to contain n_feature items.
      hash_values:
        translates the feature IDs into hash_feature IDs for PercentileDiscretizer.
      bin_ids:
        a 1D Tensor of size n_feature * n_bin + 1 which contains
        unique IDs to which the PercentileDiscretizer features will be translated to.
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

    super(PercentileDiscretizer, self).__init__(**kwargs)

    if not self.built:
      self.build(input_shape=None)

    max_discretizer_feature = n_feature * (n_bin + 1)
    self._n_feature = n_feature
    self._n_bin = n_bin

    # build variables
    self._out_bits = out_bits
    self._output_size = tf.convert_to_tensor(1 << out_bits, tf.int64)
    self._hash_keys = (hash_keys if hash_keys is not None else
     np.empty(n_feature, dtype=np.int64))
    self._hash_values = (hash_values if hash_values is not None else
     np.empty(n_feature, dtype=np.int64))
    self._bin_ids = (bin_ids if bin_ids is not None else
     np.empty(max_discretizer_feature, dtype=np.int64))
    self._bin_values = (bin_values if bin_values is not None else
     np.empty(max_discretizer_feature, dtype=np.float32))
    self._feature_offsets = (feature_offsets if feature_offsets is not None else
     np.empty(n_feature, dtype=np.int64))
    self.num_parts = num_parts
    self.cost_per_unit = cost_per_unit

  def build(self, input_shape):  # pylint: disable=unused-argument
    """
    Creates the variables of the layer
    """
    self.built = True

  def call(self, inputs, keep_inputs=False, **kwargs):
    """Looks up `keys` in a table, outputs the corresponding values.

    Implements PercentileDiscretizer inference where inputs are intersected with a hash_map.
    Input features that were not calibrated have their feature IDs truncated, so as
    to be less than 1<<output_bits, but their values remain untouched (not discretized)

    If there are no calibrated features, then the discretizer will only apply
    twml.util.limit_bits to the the feature keys (aka "feature_ids"). Essentially,
    the discretizer will be a "no-operation", other than obeying `out_bits`

    Args:
      inputs: A 2D SparseTensor that is input to PercentileDiscretizer for discretization.
        It has a dense_shape of [batch_size, input_size]
      keep_inputs:
        Include the original inputs in the output.
        Note - if True, undiscretized features will be passed through, but will have
        their values doubled (unless there are no calibrated features to discretize).
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

    if self._n_feature > 0:
      discretizer_keys, discretizer_vals = libtwml.ops.percentile_discretizer_v2(
        input_ids=keys,  # inc key assigned to feature_id, or -1
        input_vals=vals,  # the observed feature values
        bin_ids=self._bin_ids,  # n_feat X (n_bin+1) 2D arange
        bin_vals=self._bin_values,  # bin boundaries
        feature_offsets=self._feature_offsets,  # 0 : nbin_1 : max_feat
        output_bits=self._out_bits,
        feature_ids=tf.make_tensor_proto(self._hash_keys),  # feature ids to build internal hash map
        feature_indices=tf.make_tensor_proto(self._hash_values),  # keys associated w/ feat. indices
        start_compute=tf.constant(0, shape=[], dtype=tf.int64),
        end_compute=tf.constant(-1, shape=[], dtype=tf.int64),
        cost_per_unit=self.cost_per_unit
      )
    else:
      discretizer_keys = twml.util.limit_bits(keys, self._out_bits)
      discretizer_vals = vals
      # don't 2x the input.
      keep_inputs = False

    batch_size = tf.to_int64(inputs.dense_shape[0])
    output_shape = [batch_size, self._output_size]

    output = twml.SparseTensor(ids, discretizer_keys, discretizer_vals, output_shape).to_tf()

    if keep_inputs:
      # Note the non-discretized features will end up doubled,
      #   since these are already in `output`
      # handle output ID conflicts
      mdl_size = self._n_feature * (self._n_bin + 1)
      non_mdl_size = tf.subtract(self._output_size, mdl_size)
      input_keys = tf.add(tf.floormod(keys, non_mdl_size), mdl_size)

      new_input = twml.SparseTensor(
        ids=ids, indices=input_keys, values=vals, dense_shape=output_shape).to_tf()

      # concatenate discretizer output with original input
      sparse_add = tf.sparse_add(new_input, output)
      output = tf.SparseTensor(sparse_add.indices, sparse_add.values, output_shape)

    return output

  def compute_output_shape(self, input_shape):
    """Computes the output shape of the layer given the input shape.

    Args:
      input_shape: A (possibly nested tuple of) `TensorShape`.  It need not
        be fully defined (e.g. the batch size may be unknown).

    Raises NotImplementedError.

    """
    raise NotImplementedError
