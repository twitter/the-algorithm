import datetime
from absl import logging
import pytz,tensorflow.compat.v1 as tf
class StopAtTimeHook(tf.train.SessionRunHook):
	'\n  Hook that stops training at a fixed datetime\n  '
	def __init__(B,stop_time):
		'\n    Arguments:\n      stop_time:\n        a datetime.datetime or a datetime.timedelta specifying when to stop.\n        For naive datetime.datetime objects (with no time zone specified),\n        UTC time zone is assumed.\n    ';A=stop_time
		if isinstance(A,datetime.timedelta):B._stop_datetime=pytz.utc.localize(datetime.datetime.utcnow()+A)
		elif isinstance(A,datetime.datetime):
			if A.tzinfo is None:B._stop_datetime=pytz.utc.localize(A)
			else:B._stop_datetime=A.astimezone(pytz.UTC)
		else:raise ValueError('Expecting datetime or timedelta for stop_time arg')
		B._stop_requested=False
	def after_run(A,run_context,run_values):
		B=A._stop_datetime-pytz.utc.localize(datetime.datetime.utcnow())
		if B.total_seconds()<=0:logging.info('StopAtTimeHook reached stop_time; requesting stop');run_context.request_stop();A._stop_requested=True
	@property
	def stop_requested(self):' true if this hook requested a stop ';return self._stop_requested