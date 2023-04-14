try:
from twitter.deepbird.compat.v1.optimizers import (
  LazyAdamOptimizer,
  optimize_loss,
  OPTIMIZER_SUMMARIES) # noqa: F401

except Exception:
  pass
