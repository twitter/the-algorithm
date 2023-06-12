"""
This module includes facilities for manipulating data records in DeepBird v2.
This contains a submodule that allows for easy feature access as Tensors.
The result of this subclass methods are dictionaries of Tensors and SparseTensors
"""

from twitter.deepbird.io.legacy.contrib.readers.data_record import (  # noqa: F401
    SUPPORTED_DENSE_FEATURE_TYPES,
    DataRecord,
)
