import numpy as np
import tensorflow.compat.v1 as tf


def create_sparse_tensor(
    batch_size: int,
    input_size: int,
    num_values: int,
    dtype: tf.DType = tf.float32,
) -> tf.SparseTensor:
    """
    Creates a sparse tensor with the given batch size, input size, and number of values.

    Args:
        batch_size (int): The batch size of the sparse tensor.
        input_size (int): The input size of the sparse tensor.
        num_values (int): The number of values in the sparse tensor.
        dtype (tf.DType): The dtype of the sparse tensor.

    Returns:
        A sparse tensor with the given batch size, input size, and number of values.
    """
    random_indices = np.sort(
        np.random.randint(batch_size * input_size, size=num_values)
    )
    test_indices_i = random_indices // input_size
    test_indices_j = random_indices % input_size
    test_indices = np.stack([test_indices_i, test_indices_j], axis=1)
    test_values = np.random.random(num_values).astype(dtype.as_numpy_dtype)

    return tf.SparseTensor(
        indices=tf.constant(test_indices),
        values=tf.constant(test_values),
        dense_shape=(batch_size, input_size),
    )


def create_reference_input(
    sparse_input: tf.SparseTensor, use_binary_values: bool
) -> tf.SparseTensor:
    """
    Creates a reference input for the sparse input.

    Args:
        sparse_input (tf.SparseTensor): The sparse input.
        use_binary_values (bool): Whether to use binary values.

    Returns:
        A reference input for the sparse input.
    """

    if use_binary_values:
        sp_a = tf.SparseTensor(
            indices=sparse_input.indices,
            values=tf.ones_like(sparse_input.values),
            dense_shape=sparse_input.dense_shape,
        )
    else:
        sp_a = sparse_input
    return sp_a
