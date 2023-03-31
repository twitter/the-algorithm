'\nError classes for twml\n'
class EarlyStopError(Exception):'Exception used to indicate evaluator needs to early stop.'
class CheckpointNotFoundError(Exception):'Exception used to indicate a checkpoint hasnt been found.'