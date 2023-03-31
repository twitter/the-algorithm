# pylint: disable=too-many-branches
""" This module includes functions for managing learning rate decay """
import tensorflow.compat.v1 as tf


def get_learning_rate_decay_fn(params):
  """
  Returns a learning rate decay function that takes the initial
  learning_rate and global_step
  as arguments and returns the current learning rate.

  Currently supports params.learning_rate_decay values of:
  exponential | polynomial | piecewise_constant | cosine | cosine restarts.
  See `Decaying the Leanring Rate
  <https://www.tensorflow.org/api_guides/python/train#Decaying_the_learning_rate>`_ for details.

  Arguments:
    params:
      a tensorflow.contrib.train.HParams object containing the relevant hyperparameters.
  """
  paramsv = params.values()
  if 'learning_rate_decay' not in paramsv or params.learning_rate_decay == 'no_learning_rate_decay':
    return None
  elif params.learning_rate_decay == 'exponential_learning_rate_decay':
    if 'decay_steps' not in paramsv:
      raise ValueError("Expecting params.decay_steps for "
                       "params.learning_rate_decay == 'exponential'")
    if 'exponential_decay_rate' not in paramsv:
      raise ValueError("Expecting params.exponential_decay_rate for "
                       "params.learning_rate_decay == 'exponential'")

    def exponential_decay_fn(learning_rate, global_step):
      """ exponential decay function to be passed to optimize_loss """
      return tf.train.exponential_decay(
        learning_rate=learning_rate,
        global_step=global_step,
        decay_steps=params.decay_steps,
        decay_rate=params.exponential_decay_rate
      )
    return exponential_decay_fn
  elif params.learning_rate_decay == 'piecewise_constant_learning_rate_decay':
    if 'piecewise_constant_boundaries' not in paramsv:
      raise ValueError("Expecting params.piecewise_constant_boundaries for "
                       "params.learning_rate_decay == 'piecewise_constant'")
    if 'piecewise_constant_values' not in paramsv:
      raise ValueError("Expecting params.piecewise_constant_values for "
                       "params.learning_rate_decay == 'piecewise_constant'")
    # pylint: disable=unused-argument

    def piecewise_constant_fn(learning_rate, global_step):
      """ piecewise_constant decay function to be passed to optimize_loss """
      return tf.train.piecewise_constant(
        x=global_step,
        boundaries=params.piecewise_constant_boundaries,
        values=params.piecewise_constant_values
      )
    return piecewise_constant_fn
  elif params.learning_rate_decay == 'polynomial_learning_rate_decay':
    if 'decay_steps' not in paramsv:
      raise ValueError("Expecting params.decay_steps for "
                       "params.learning_rate_decay == 'polynomial'")
    if 'end_learning_rate' not in paramsv:
      raise ValueError("Expecting params.end_learning_rate for "
                       "params.learning_rate_decay == 'polynomial'")

    def polynomial_decay_fn(learning_rate, global_step):
      """ polynomial decay function to be passed to optimize_loss """
      return tf.train.polynomial_decay(
        learning_rate=learning_rate,
        global_step=global_step,
        decay_steps=params.decay_steps,
        end_learning_rate=params.end_learning_rate,
        power=params.polynomial_power if 'polynomial_power' in paramsv else 1.0,
      )
    return polynomial_decay_fn

  elif params.learning_rate_decay == 'inverse_learning_rate_decay':
    if 'min_learning_rate' not in paramsv:
      raise ValueError("Expecting params.min_learning_rate for "
                       "params.learning_rate_decay == 'inverse'")
    if 'decay_rate' not in paramsv:
      raise ValueError("Expecting params.decay_rate for "
                       "params.learning_rate_decay == 'inverse'")
    if 'decay_steps' not in paramsv:
      raise ValueError("Expecting params.decay_steps for "
                       "params.learning_rate_decay == 'inverse'")

    def bounded_inverse_time_decay_fn(learning_rate, global_step):
      '''
      Returns the decayed learning_rate by applying the function:
      decayed_lr = max(lr /(1 + decay_rate * floor(global_step /decay_step)),
                       min_learning_rate)
      Arguments:
        learning_rate:
          A scalar `float32` or `float64` `Tensor` or a Python number.
          The initial learning rate.
        global_step:
          A scalar `int32` or `int64` `Tensor` or a Python number.
          Global step to use for the decay computation.  Must not be negative.
        min_learning_rate:
          A scalar `int32` or `int64` `Tensor` or a Python number.
          Minimum possible learning_rate. The decayed learning_rate will not be
          smaller than the min_learning_rate
        decay_steps:
          How often to apply decay. In dbv1, this should be 1.
        decay_rate:
          A scalar `int32` or `int64` `Tensor` or a Python number.
          Rate in which we decay the learning rate.
        Returns:
        A scalar `Tensor` of the same type as `learning_rate`.  The decayed
        learning rate.
      '''
      decayed_rate = tf.train.inverse_time_decay(
        learning_rate=learning_rate,
        global_step=global_step,
        decay_steps=params.decay_steps,
        decay_rate=params.decay_rate)
      # Getting dtype of returned Tensor
      dtype = decayed_rate.dtype
      # Casting the min_learning rate the same dtype as decayes rate
      min_learning_rate = tf.cast(params.min_learning_rate, dtype)
      # Returning the maximum between the two
      return tf.maximum(decayed_rate, min_learning_rate)

    return bounded_inverse_time_decay_fn

  elif params.learning_rate_decay == 'cosine_learning_rate_decay':
    if 'decay_steps' not in paramsv:
      raise ValueError("Expecting params.decay_steps for "
                       "params.learning_rate_decay == 'cosine_decay'")
    if "alpha" not in paramsv:
      raise ValueError("Expecting params.alpha for "
                       "params.learning_rate_decay == 'cosine_decay'")
    def cosine_decay_fn(learning_rate, global_step):
      """ cosine decay function to be passed to optimize_loss """
      return tf.train.cosine_decay(
        learning_rate=learning_rate,
        global_step=global_step,
        decay_steps=params.decay_steps,
        alpha=params.alpha
      )
    return cosine_decay_fn
  elif params.learning_rate_decay == 'cosine_restarts_learning_rate_decay':
    if 'first_decay_steps' not in paramsv:
      raise ValueError("Expecting params.first_decay_steps for "
                       "params.learning_rate_decay == 'cosine_restarts_decay'")
    if 't_mul' not in paramsv:
      raise ValueError("Expecting params.t_mul for "
                       "params.learning_rate_decay == 'cosine_restarts_decay'")
    if 'm_mul' not in paramsv:
      raise ValueError("Expecting params.m_mul for "
                       "params.learning_rate_decay == 'cosine_restarts_decay'")
    if "alpha" not in paramsv:
      raise ValueError("Expecting params.alpha for "
                       "params.learning_rate_decay == 'cosine_restarts_decay'")
    def cosine_restart_decay_fn(learning_rate, global_step):
      """ cosine decay function to be passed to optimize_loss """
      return tf.train.cosine_decay_restarts(
        learning_rate=learning_rate,
        global_step=global_step,
        first_decay_steps=params.first_decay_steps,
        t_mul=params.t_mul,
        m_mul=params.m_mul,
        alpha=params.alpha
      )
    return cosine_restart_decay_fn

  raise ValueError("Unsupported params.learning_rate_decay: %s" % params.learning_rate_decay)
