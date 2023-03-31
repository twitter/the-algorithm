'\nImplementing partition Layer\n'
from .layer import Layer
import tensorflow.compat.v1 as tf
class Partition(Layer):
	'\n  This layer implements:\n\n  .. code-block:: python\n\n    tf.dynamic_partition(input_vals, partition_ids, self.partitions)\n\n  Input:\n    partitions:\n      the number of partitions which we will divide the hashmap keys/bvalues\n\n  Output:\n    A layer that performs partitioning\n   '
	def __init__(A,partitions=2,**B):A.partitions=partitions;super(Partition,A).__init__(**B)
	def compute_output_shape(A,input_shape):'Computes the output shape of the layer given the input shape.\n\n    Args:\n      input_shape: A (possibly nested tuple of) `TensorShape`.  It need not\n        be fully defined (e.g. the batch size may be unknown).\n\n    Raises NotImplementedError.\n\n    ';raise NotImplementedError
	def call(B,partition_ids,input_vals,input_keys,**F):'This layer is responsible for partitioning the values/keys of a hashmap\n\n    Arguments:\n      partition_ids:\n        Tensor that is equivalent to boolean (int32).\n      input_vals:\n        Tensor that represents the values of the hashmap(float).\n      input_keys:\n        Tensor that represents the keys of the hashmap(float)\n\n    Returns:\n      The output of the partition layer, which is a list of lists which looks\n      something like:\n\n      .. code-block:: python\n\n        [[vals_0, vals_1], [keys_0, keys_1], [indices_0, indices_1]]\n\n      where:\n        vals_x:\n          values of the hashmap for partition x\n        keys_x:\n          keys of the hashmap for partition x\n        indices_x:\n          indices of the hashmap for partition x\n    ';A=partition_ids;C=tf.dynamic_partition(input_vals,A,B.partitions);D=tf.dynamic_partition(input_keys,A,B.partitions);E=tf.dynamic_partition(tf.range(tf.shape(A)[0]),tf.cast(A,tf.int32),B.partitions);return[C,D,E]