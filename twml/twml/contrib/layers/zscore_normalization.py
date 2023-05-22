"""
Contains the twml.layers.ZscoreNormalization layer.
"""
from typing import Optional, Tuple, Union

import tensorflow.compat.v1 as tf
from tensorflow.python.training import moving_averages

from twml.layers.layer import Layer


# This is copied from tensorflow.contrib.framework.python.ops.add_model_variable in 1.15
# Not available in 2.x
# TODO: Figure out if this is really necessary.
def _add_model_variable(var: tf.Variable) -> None:
    """Adds a variable to the `GraphKeys.MODEL_VARIABLES` collection.

    Args:
        var: a variable.
    """

    if var not in tf.get_collection(tf.GraphKeys.MODEL_VARIABLES):
        tf.add_to_collection(tf.GraphKeys.MODEL_VARIABLES, var)


def update_moving_variable(
    batch_var: tf.Variable,
    moving_var: tf.Variable,
    decay: float,
    zero_debias: bool = True,
    name: Optional[str] = None,
) -> tf.Variable:
    """Update moving variable using batch variable.

    Args:
        batch_var: a variable.
        moving_var: a variable.
        decay: decay rate.
        zero_debias: whether to use zero debias.
        name: name of the operation.

    Returns:
        A variable representing the updated moving variable.
    """

    update_op = moving_averages.assign_moving_average(
        moving_var, batch_var, decay, zero_debias=zero_debias, name=None
    )
    _add_model_variable(moving_var)
    with tf.control_dependencies([update_op]):
        return tf.identity(moving_var)


class ZscoreNormalization(Layer):
    """
    Perform z-score normalization using moving mean and std.
    Missing values are not included during mean/std calculation
    This layer should only be used right after input layer.

    Args:
        decay:
            using large decay to include longer moving means.
        data_type:
            use float64 to prevent overflow during variance calculation.
        name:
            Layer name

    Returns:
        A layer representing the output of the ZscoreNormalization transformation.
    """

    def __init__(self, decay=0.9999, data_type=tf.float64, name=None, **kwargs):
        super(ZscoreNormalization, self).__init__(name=name, **kwargs)
        self.epsilon = tf.constant(1.0, data_type)
        self.decay = decay
        self.data_type = data_type

    def build(self, input_shape: tf.TensorShape):
        """Creates the moving_mean and moving_var tf.Variables of the layer."""
        input_dim = input_shape[1]
        self.moving_mean = self.add_variable(
            f"{self.name}_mean/EMA",
            initializer=tf.constant_initializer(),
            shape=[input_dim],
            dtype=self.data_type,
            trainable=False,
        )
        self.moving_var = self.add_variable(
            f"{self.name}_variance/EMA",
            initializer=tf.constant_initializer(),
            shape=[input_dim],
            dtype=self.data_type,
            trainable=False,
        )
        self.built = True

    def compute_output_shape(
        self, input_shape: Union[tf.TensorShape, Tuple[tf.TensorShape]]
    ) -> tf.TensorShape:
        """Computes the output shape of the layer given the input shape."""

        return input_shape

    def _training_pass(
        self,
        input: tf.Tensor,
        dense_mask: tf.Tensor,
        input_dtype: tf.DType,
        handle_single: bool = False,
        zero_debias: bool = True,
    ) -> tf.Tensor:
        """Perform z-score normalization in training mode."""

        epsilon = self.epsilon
        moving_mean, moving_var = self.moving_mean, self.moving_var
        # calculate the number of exisiting value for each feature
        tensor_batch_num = tf.reduce_sum(tf.cast(dense_mask, self.data_type), axis=0)
        mask_ones = tf.cast(tensor_batch_num, tf.bool)
        eps_vector = tf.fill(tf.shape(tensor_batch_num), epsilon)
        # the following filled 0 with epision
        tensor_batch_num_eps = tf.where(mask_ones, tensor_batch_num, eps_vector)
        tensor_batch_num_eps_broacast = tf.expand_dims(tensor_batch_num_eps, 0)
        tensor_batch_divided = input / tensor_batch_num_eps_broacast
        tensor_batch_mean = tf.reduce_sum(tensor_batch_divided, axis=0)

        # update moving mean here, and use it to calculate the std.
        tensor_moving_mean = update_moving_variable(
            tensor_batch_mean, moving_mean, self.decay, zero_debias, name="mean_ema_op"
        )

        tensor_batch_sub_mean = input - tf.expand_dims(tensor_moving_mean, 0)
        tensor_batch_sub_mean = tf.where(
            dense_mask, tensor_batch_sub_mean, tf.zeros_like(tensor_batch_sub_mean)
        )
        # divided by sqrt(n) before square, and then do summation for numeric stability.
        broad_sqrt_num_eps = tf.expand_dims(tf.sqrt(tensor_batch_num_eps), 0)
        tensor_batch_sub_mean_div = tensor_batch_sub_mean / broad_sqrt_num_eps
        tensor_batch_sub_mean_div_square = tf.square(tensor_batch_sub_mean_div)
        tensor_batch_var = tf.reduce_sum(tensor_batch_sub_mean_div_square, axis=0)

        # update moving var here, dont replace 0 with eps before updating.
        tensor_moving_var = update_moving_variable(
            tensor_batch_var, moving_var, self.decay, zero_debias, name="var_ema_op"
        )

        # if std is 0, replace it with epsilon
        tensor_moving_std = tf.sqrt(tensor_moving_var)
        tensor_moving_std_eps = tf.where(
            tf.equal(tensor_moving_std, 0), eps_vector, tensor_moving_std
        )

        missing_input_norm = tensor_batch_sub_mean / tf.expand_dims(
            tensor_moving_std_eps, 0
        )

        if handle_single:
            # if std==0 and value not missing, reset it to 1.
            moving_var_mask_zero = tf.math.equal(tensor_moving_var, 0)
            moving_var_mask_zero = tf.expand_dims(moving_var_mask_zero, 0)
            missing_input_norm = tf.where(
                tf.math.logical_and(dense_mask, moving_var_mask_zero),
                tf.ones_like(missing_input_norm),
                missing_input_norm,
            )
        if input_dtype != self.data_type:
            missing_input_norm = tf.cast(missing_input_norm, input_dtype)
        return missing_input_norm

    def _infer_pass(
        self,
        input: tf.Tensor,
        dense_mask: tf.Tensor,
        input_dtype: tf.DType,
        handle_single: bool = False,
    ) -> tf.Tensor:
        """Perform z-score normalization in inference mode."""

        epsilon = tf.cast(self.epsilon, input_dtype)
        testing_moving_mean = tf.cast(self.moving_mean, input_dtype)
        tensor_moving_std = tf.cast(tf.sqrt(self.moving_var), input_dtype)

        broad_mean = tf.expand_dims(testing_moving_mean, 0)
        tensor_batch_sub_mean = input - broad_mean

        tensor_batch_sub_mean = tf.where(
            dense_mask, tensor_batch_sub_mean, tf.zeros_like(tensor_batch_sub_mean)
        )
        tensor_moving_std_eps = tf.where(
            tf.equal(tensor_moving_std, 0),
            tf.fill(tf.shape(tensor_moving_std), epsilon),
            tensor_moving_std,
        )
        missing_input_norm = tensor_batch_sub_mean / tf.expand_dims(
            tensor_moving_std_eps, 0
        )
        if handle_single:
            # if std==0 and value not missing, reset it to 1.
            moving_var_broad = tf.expand_dims(tensor_moving_std, 0)
            moving_var_mask_zero = tf.math.logical_not(
                tf.cast(moving_var_broad, tf.bool)
            )

            missing_input_norm = tf.where(
                tf.math.logical_and(dense_mask, moving_var_mask_zero),
                tf.ones_like(missing_input_norm),
                missing_input_norm,
            )
        return missing_input_norm

    def call(
        self,
        input: tf.Tensor,
        is_training: bool = True,
        dense_mask: bool = None,
        zero_debias: bool = True,
        handle_single: bool = False,
    ) -> tf.Tensor:
        """
        Args:
            input:  B x D : float32/float64
                missing value must be set to 0.
            is_training: bool
                training phase or testing phase
            dense_mask: B x D : bool
                missing value should be marked as 0, non-missing as 1. same shape as input
            zero_debias: bool
                bias correction of the moving average. (biased towards 0 in the beginning.
                see adam paper. https://arxiv.org/abs/1412.6980)
            handle_single: bool
                if std==0, and feature is not missing value, set the value to 1, instead of 0.
                This is super rare if input only consists of continuous feature.
                But if one-hot feature is included,
                they will all have same values 1, in that case, make sure to set handle_single to true.
        """

        if dense_mask is None:
            dense_mask = tf.math.logical_not(tf.equal(input, 0))
        input_dtype = input.dtype

        if is_training:
            if input_dtype != self.data_type:
                input = tf.cast(input, self.data_type)
            return self._training_pass(
                input, dense_mask, input_dtype, handle_single, zero_debias
            )
        else:
            return self._infer_pass(input, dense_mask, input_dtype, handle_single)


def zscore_normalization(
    input: tf.Tensor,
    is_training: bool = True,
    decay: float = 0.9999,
    data_type: tf.DType = tf.float64,
    name: Optional[str] = None,
    dense_mask: Optional[tf.Tensor] = None,
    zero_debias: bool = True,
    handle_single: bool = False,
    **kwargs,
):
    """
    Args:
        input:  B x D : float32/float64
            missing value must be set to 0.
        is_training: bool
            training phase or testing phase
        decay: float
            using large decay to include longer moving means.
        data_type: tf.DType
            use float64 to zprevent overflow during variance calculation.
        name: str
            Layer name
        dense_mask: B x D : bool
            missing value should be marked as 0, non-missing as 1. same shape as input
        zero_debias: bool
            bias correction of the moving average. (biased towards 0 in the beginning.
            see adam paper. https://arxiv.org/abs/1412.6980)
        handle_single: bool
            if std == 0, and feature is not missing value, set the value to 1, instead of 0.
            This is super rare if input only consists of continuous feature.
            But if one-hot feature is included,
            they will all have same values 1, in that case, make sure to set handle_single to true.
    """

    norm_layer = ZscoreNormalization(
        decay=decay, data_type=data_type, name=name, **kwargs
    )
    return norm_layer(
        input,
        is_training,
        dense_mask=dense_mask,
        zero_debias=zero_debias,
        handle_single=handle_single,
    )
