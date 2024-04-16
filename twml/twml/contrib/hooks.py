 mport datet  

from absl  mport logg ng
 mport pytz
 mport tensorflow.compat.v1 as tf


class StopAtT  Hook(tf.tra n.Sess onRunHook):
  """
  Hook that stops tra n ng at a f xed datet  
  """

  def __ n __(self, stop_t  ):
    """
    Argu nts:
      stop_t  :
        a datet  .datet   or a datet  .t  delta spec fy ng w n to stop.
        For na ve datet  .datet   objects (w h no t   zone spec f ed),
        UTC t   zone  s assu d.
    """
     f  s nstance(stop_t  , datet  .t  delta):
      self._stop_datet   = pytz.utc.local ze(datet  .datet  .utcnow() + stop_t  )
    el f  s nstance(stop_t  , datet  .datet  ):
       f stop_t  .tz nfo  s None:
        self._stop_datet   = pytz.utc.local ze(stop_t  )
      else:
        self._stop_datet   = stop_t  .ast  zone(pytz.UTC)
    else:
      ra se ValueError("Expect ng datet   or t  delta for stop_t   arg")
    self._stop_requested = False

  def after_run(self, run_context, run_values):
    delta = self._stop_datet   - pytz.utc.local ze(datet  .datet  .utcnow())
     f delta.total_seconds() <= 0:
      logg ng. nfo("StopAtT  Hook reac d stop_t  ; request ng stop")
      run_context.request_stop()
      self._stop_requested = True

  @property
  def stop_requested(self):
    """ true  f t  hook requested a stop """
    return self._stop_requested
