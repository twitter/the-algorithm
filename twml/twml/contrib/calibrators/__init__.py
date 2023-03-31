"\nThis module contains classes used for calibration.\nTypically, each calibrator defines a ``twml.calibrator.Calibrator`` subclass\nand a ``twml.calibrator.CalibrationFeature``.\nThe latter manages weights and values of individual features.\nThe former manages a set of ``CalibratorFeatures``\n(although some ``Calibrators`` don't use ``CalibrationFeature``).\nUltimately, the ``Calibrator`` should produce an initialized layer via its ``to_layer()`` method.\n"
from .common_calibrators import calibrate_discretizer_and_export,add_discretizer_arguments
from .calibrator import Calibrator
from .mdl import MDLCalibrator
from .isotonic import IsotonicCalibrator
from .percentile_discretizer import PercentileDiscretizerCalibrator
from .hashed_percentile_discretizer import HashedPercentileDiscretizerCalibrator
from .hashing_discretizer import HashingDiscretizerCalibrator