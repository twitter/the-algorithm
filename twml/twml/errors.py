"""
Error classes for twml
"""


class EarlyStopError(Exception):
  """Exception used to indicate evaluator needs to early stop."""
  pass


class CheckpointNotFoundError(Exception):
  """Exception used to indicate a checkpoint hasnt been found."""
  pass
