# pylint: disable=wildcard-import
"""
This module contains classes used for calibration.
Typically, each calibrator defines a ``twml.calibrator.Calibrator`` subclass
and a ``twml.calibrator.CalibrationFeature``.
The latter manages weights and values of individual features.
The former manages a set of ``CalibratorFeatures``
(although some ``Calibrators`` don't use ``CalibrationFeature``).
Ultimately, the ``Calibrator`` should produce an initialized layer via its ``to_layer()`` method.
"""

from .common_calibrators import calibrate_discretizer_and_export, add_discretizer_arguments  # noqa: F401
from .calibrator import Calibrator  # noqa: F401
from .mdl import MDLCalibrator  # noqa: F401
from .isotonic import IsotonicCalibrator  # noqa: F401
from .percentile_discretizer import PercentileDiscretizerCalibrator  # noqa: F401
from .hashed_percentile_discretizer import HashedPercentileDiscretizerCalibrator  # noqa: F401
from .hashing_discretizer import HashingDiscretizerCalibrator  # noqa: F401