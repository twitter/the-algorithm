from typing import Callable

import tensorflow as tf


class WarmUp(tf.keras.optimizers.schedules.LearningRateSchedule):
  def __init__(
    self,
    initial_learning_rate: float,
    decay_schedule_fn: Callable,
    warmup_steps: int,
    power: float = 1.0,
    name: str = "",
  ):
    super().__init__()
    self.initial_learning_rate = initial_learning_rate
    self.warmup_steps = warmup_steps
    self.power = power
    self.decay_schedule_fn = decay_schedule_fn
    self.name = name

  def __call__(self, step):
    with tf.name_scope(self.name or "WarmUp") as name:
      global_step_float = tf.cast(step, tf.float32)
      warmup_steps_float = tf.cast(self.warmup_steps, tf.float32)
      warmup_percent_done = global_step_float / warmup_steps_float
      warmup_learning_rate = self.initial_learning_rate * tf.math.pow(
        warmup_percent_done, self.power
      )
      return tf.cond(
        global_step_float < warmup_steps_float,
        lambda: warmup_learning_rate,
        lambda: self.decay_schedule_fn(step - self.warmup_steps),
        name=name,
      )

  def get_config(self):
    return {
      "initial_learning_rate": self.initial_learning_rate,
      "decay_schedule_fn": self.decay_schedule_fn,
      "warmup_steps": self.warmup_steps,
      "power": self.power,
      "name": self.name,
    }
