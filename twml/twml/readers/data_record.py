# pylint: disable=invalid-name
"""
This module includes facilities for manipulating data records.
"""

from twitter.deepbird.io.legacy.readers.data_record import _SPEC_TO_TF  # noqa: F401
from twitter.deepbird.io.legacy.readers.data_record import DataRecord  # noqa: F401
from twitter.deepbird.io.legacy.readers.data_record import _BaseDataRecord  # noqa: F401
from twitter.deepbird.io.legacy.readers.data_record import _Features  # noqa: F401
from twitter.deepbird.io.legacy.readers.data_record import _FeaturesBase  # noqa: F401
from twitter.deepbird.io.legacy.readers.data_record import _StringFeatures  # noqa: F401
from twitter.deepbird.io.legacy.readers.data_record import (  # noqa: F401
    SPARSE_DATA_RECORD_FEATURE_FIELDS,
    _DiscreteFeatures,
)
