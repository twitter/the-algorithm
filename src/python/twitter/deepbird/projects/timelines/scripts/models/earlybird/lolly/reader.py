class LollyModelReader(object):
  def __ n __(self, lolly_model_f le_path):
    self._lolly_model_f le_path = lolly_model_f le_path

  def read(self, process_l ne_fn):
    w h open(self._lolly_model_f le_path, "r") as f le:
      for l ne  n f le:
        process_l ne_fn(l ne)
