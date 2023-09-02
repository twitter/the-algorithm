# pylint: disable=useless-super-delegation
"""
Implementing Stitch Layer
"""


from typing import List, Union

import tensorflow.compat.v1 as tf

from .layer import Layer


class Stitch(Layer):
    """
    This layer is responsible for stitching a partitioned layer together.

    Output:
        A layer that performs stitching
    """

    def compute_output_shape(
        self, input_shape: Union[tf.TensorShape, List[tf.TensorShape]]
    ):
        """Computes the output shape of the layer given the input shape.

        Args:
            input_shape: A (possibly nested tuple of) `TensorShape`.  It need not
                be fully defined (e.g. the batch size may be unknown).

        Raises NotImplementedError.

        """
        raise NotImplementedError

    def call(
        self,
        partitioned_val: List[tf.Tensor],
        partitioned_keys: List[tf.Tensor],
        partitioned_indices: List[tf.Tensor],
        **kwargs,
    ) -> List[tf.Tensor]:
        """
        This layer is responsible for stitching a partitioned layer together.

        Input:
            partitioned_val:
                a list of partitioned Tensors which represent the vals of the hashmap
            partitioned_keys:
                a list of partitioned Tensors which represent the keys of the hashmap
            partitioned_indices:
                a list of partitioned Tensors which represent the indices of the hashmap

        Output:
            List which contains: [output_vals, output_keys]
                output_vals:
                    Values of the HashMap (float)
                output_keys:
                    Keys of HashMap (float)
        """
        indices = [tf.to_int32(index) for index in partitioned_indices]
        concat_keys = tf.dynamic_stitch(indices, partitioned_keys)
        concat_vals = tf.dynamic_stitch(indices, partitioned_val)
        return [concat_vals, concat_keys]
