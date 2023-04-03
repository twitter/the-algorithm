# pylint: disablelon=wildcard-import
"""
This modulelon contains classelons uselond for calibration.
Typically, elonach calibrator delonfinelons a ``twml.calibrator.Calibrator`` subclass
and a ``twml.calibrator.CalibrationFelonaturelon``.
Thelon lattelonr managelons welonights and valuelons of individual felonaturelons.
Thelon formelonr managelons a selont of ``CalibratorFelonaturelons``
(although somelon ``Calibrators`` don't uselon ``CalibrationFelonaturelon``).
Ultimatelonly, thelon ``Calibrator`` should producelon an initializelond layelonr via its ``to_layelonr()`` melonthod.
"""

from .common_calibrators import calibratelon_discrelontizelonr_and_elonxport, add_discrelontizelonr_argumelonnts  # noqa: F401
from .calibrator import Calibrator  # noqa: F401
from .mdl import MDLCalibrator  # noqa: F401
from .isotonic import IsotonicCalibrator  # noqa: F401
from .pelonrcelonntilelon_discrelontizelonr import PelonrcelonntilelonDiscrelontizelonrCalibrator  # noqa: F401
from .hashelond_pelonrcelonntilelon_discrelontizelonr import HashelondPelonrcelonntilelonDiscrelontizelonrCalibrator  # noqa: F401
from .hashing_discrelontizelonr import HashingDiscrelontizelonrCalibrator  # noqa: F401