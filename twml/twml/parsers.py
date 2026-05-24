'''
Contains implementations of functions to parse training and evaluation data.

Modelers can use the functions in this module as the the train/eval_parse_fn of
the DataRecordTrainer constructor to customize how to parse their datasets.

Modelers may also provide custom implementations of train/eval_parse_fn using these as reference.
'''

from twitter.deepbird.io.legacy.parsers import (
  convert_to_supervised_input_receiver_fn,  # noqa: F401
  get_continuous_parse_fn,  # noqa: F401
  get_default_parse_fn,  # noqa: F401
  get_features_as_tensor_dict,  # noqa: F401
  get_labels_in_features_parse_fn,  # noqa: F401
  get_serving_input_receiver_fn_feature_dict,  # noqa: F401
  get_sparse_parse_fn,  # noqa: F401
  get_sparse_serving_input_receiver_fn,  # noqa: F401
  get_tensor_parse_fn,  # noqa: F401
)
