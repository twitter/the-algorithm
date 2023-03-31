# pylint: disable=wildcard-import
"""
This module contains the Trainer and DataRecordTrainer classes.
Trainers wrap a
`tf.estimator.Estimator
<https://www.tensorflow.org/versions/master/api_docs/python/tf/estimator/Estimator>`_.
"""

from .trainer import Trainer  # noqa: F401
from .data_record_trainer import DataRecordTrainer  # noqa: F401
