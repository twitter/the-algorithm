import datetime

from absl import logging
import pytz
import tensorflow.compat.v1 as tf


class StopAtTimeHook(tf.train.SessionRunHook):
  """
  Hook that stops training at a fixed datetime
  """

  def __init__(self, stop_time):
    """
    Arguments:
      stop_time:
        a datetime.datetime or a datetime.timedelta specifying when to stop.
        For naive datetime.datetime objects (with no time zone specified),
        UTC time zone is assumed.
    """
    if isinstance(stop_time, datetime.timedelta):
      self._stop_datetime = pytz.utc.localize(datetime.datetime.utcnow() + stop_time)
    elif isinstance(stop_time, datetime.datetime):
      if stop_time.tzinfo is None:
        self._stop_datetime = pytz.utc.localize(stop_time)
      else:
        self._stop_datetime = stop_time.astimezone(pytz.UTC)
    else:
      raise ValueError("Expecting datetime or timedelta for stop_time arg")
    self._stop_requested = False

  def after_run(self, run_context, run_values):
    delta = self._stop_datetime - pytz.utc.localize(datetime.datetime.utcnow())
    if delta.total_seconds() <= 0:
      logging.info("StopAtTimeHook reached stop_time; requesting stop")
      run_context.request_stop()
      self._stop_requested = True

  @property
  def stop_requested(self):
    """ true if this hook requested a stop """
    return self._stop_requested
