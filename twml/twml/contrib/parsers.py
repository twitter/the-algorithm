"""
Contains implementations of functions to parse the contrib.FeatureConfig

Modelers can use the functions in this module as the the train/eval_parse_fn of
the DataRecordTrainer constructor to customize how to parse their datasets.

Modelers may also provide custom implementations of train/eval_parse_fn using these as reference.
"""

from twitter.deepbird.io.legacy.contrib.parsers import (
    _convert_to_fixed_length_tensor,
)  # noqa: F401
from twitter.deepbird.io.legacy.contrib.parsers import (
    _get_input_receiver_fn_feature_dict,
)  # noqa: F401
from twitter.deepbird.io.legacy.contrib.parsers import _merge_dictionaries  # noqa: F401
from twitter.deepbird.io.legacy.contrib.parsers import (
    get_features_as_tensor_dict,
)  # noqa: F401
from twitter.deepbird.io.legacy.contrib.parsers import get_keras_parse_fn  # noqa: F401
from twitter.deepbird.io.legacy.contrib.parsers import (
    get_serving_input_receiver_fn_feature_dict,
)  # noqa: F401
from twitter.deepbird.io.legacy.contrib.parsers import (
    get_string_tensor_parse_fn,
)  # noqa: F401
from twitter.deepbird.io.legacy.contrib.parsers import (
    get_string_tensor_serving_input_receiver_fn,
)  # noqa: F401
from twitter.deepbird.io.legacy.contrib.parsers import (
    get_supervised_input_receiver_fn_feature_dict,
)  # noqa: F401
from twitter.deepbird.io.legacy.contrib.parsers import parse_string_tensor  # noqa: F401
