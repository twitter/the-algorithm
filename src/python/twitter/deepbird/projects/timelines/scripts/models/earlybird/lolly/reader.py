class LollyModelReader(object):
  def __init__(self, lolly_model_file_path):
    self._lolly_model_file_path = lolly_model_file_path

  def read(self, process_line_fn):
    with open(self._lolly_model_file_path, "r") as file:
      for line in file:
        process_line_fn(line)
