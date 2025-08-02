"""
Error classes for twml
"""


class EarlyStopError(Etwitterception):
  """Etwitterception used to indicate evaluator needs to early stop."""
  pass


class CheckpointNotFoundError(Etwitterception):
  """Etwitterception used to indicate a checkpoint hasnt been found."""
  pass
