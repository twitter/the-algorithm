import numpy as np
from tensorflow.keras import backend as K


class VarianceScaling(object):
  """Initializer capable of adapting its scale to the shape of weights.
  With `distribution="normal"`, samples are drawn from a truncated normal
  distribution centered on zero, with `stddev = sqrt(scale / n)` where n is:
      - number of input units in the weight tensor, if mode = "fan_in"
      - number of output units, if mode = "fan_out"
      - average of the numbers of input and output units, if mode = "fan_avg"
  With `distribution="uniform"`,
  samples are drawn from a uniform distribution
  within [-limit, limit], with `limit = sqrt(3 * scale / n)`.
  # Arguments
      scale: Scaling factor (positive float).
      mode: One of "fan_in", "fan_out", "fan_avg".
      distribution: Random distribution to use. One of "normal", "uniform".
      seed: A Python integer. Used to seed the random generator.
  # Raises
      ValueError: In case of an invalid value for the "scale", mode" or
        "distribution" arguments."""

  def __init__(
    self,
    scale=1.0,
    mode="fan_in",
    distribution="normal",
    seed=None,
    fan_in=None,
    fan_out=None,
  ):
    self.fan_in = fan_in
    self.fan_out = fan_out
    if scale <= 0.0:
      raise ValueError("`scale` must be a positive float. Got:", scale)
    mode = mode.lower()
    if mode not in {"fan_in", "fan_out", "fan_avg"}:
      raise ValueError(
        "Invalid `mode` argument: " 'expected on of {"fan_in", "fan_out", "fan_avg"} ' "but got",
        mode,
      )
    distribution = distribution.lower()
    if distribution not in {"normal", "uniform"}:
      raise ValueError(
        "Invalid `distribution` argument: " 'expected one of {"normal", "uniform"} ' "but got",
        distribution,
      )
    self.scale = scale
    self.mode = mode
    self.distribution = distribution
    self.seed = seed

  def __call__(self, shape, dtype=None, partition_info=None):
    fan_in = shape[-2] if self.fan_in is None else self.fan_in
    fan_out = shape[-1] if self.fan_out is None else self.fan_out

    scale = self.scale
    if self.mode == "fan_in":
      scale /= max(1.0, fan_in)
    elif self.mode == "fan_out":
      scale /= max(1.0, fan_out)
    else:
      scale /= max(1.0, float(fan_in + fan_out) / 2)
    if self.distribution == "normal":
      stddev = np.sqrt(scale) / 0.87962566103423978
      return K.truncated_normal(shape, 0.0, stddev, dtype=dtype, seed=self.seed)
    else:
      limit = np.sqrt(3.0 * scale)
      return K.random_uniform(shape, -limit, limit, dtype=dtype, seed=self.seed)

  def get_config(self):
    return {
      "scale": self.scale,
      "mode": self.mode,
      "distribution": self.distribution,
      "seed": self.seed,
    }


def customized_glorot_uniform(seed=None, fan_in=None, fan_out=None):
  """Glorot uniform initializer, also called Xavier uniform initializer.
  It draws samples from a uniform distribution within [-limit, limit]
  where `limit` is `sqrt(6 / (fan_in + fan_out))`
  where `fan_in` is the number of input units in the weight tensor
  and `fan_out` is the number of output units in the weight tensor.
  # Arguments
      seed: A Python integer. Used to seed the random generator.
  # Returns
      An initializer."""
  return VarianceScaling(
    scale=1.0,
    mode="fan_avg",
    distribution="uniform",
    seed=seed,
    fan_in=fan_in,
    fan_out=fan_out,
  )


def customized_glorot_norm(seed=None, fan_in=None, fan_out=None):
  """Glorot norm initializer, also called Xavier uniform initializer.
  It draws samples from a uniform distribution within [-limit, limit]
  where `limit` is `sqrt(6 / (fan_in + fan_out))`
  where `fan_in` is the number of input units in the weight tensor
  and `fan_out` is the number of output units in the weight tensor.
  # Arguments
      seed: A Python integer. Used to seed the random generator.
  # Returns
      An initializer."""
  return VarianceScaling(
    scale=1.0,
    mode="fan_avg",
    distribution="normal",
    seed=seed,
    fan_in=fan_in,
    fan_out=fan_out,
  )
