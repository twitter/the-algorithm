'\nContains implementations of functions to parse the contrib.FeatureConfig\n\nModelers can use the functions in this module as the the train/eval_parse_fn of\nthe DataRecordTrainer constructor to customize how to parse their datasets.\n\nModelers may also provide custom implementations of train/eval_parse_fn using these as reference.\n'
from twitter.deepbird.io.legacy.contrib.parsers import _convert_to_fixed_length_tensor,_get_input_receiver_fn_feature_dict,_merge_dictionaries,get_features_as_tensor_dict,get_keras_parse_fn,get_serving_input_receiver_fn_feature_dict,get_string_tensor_parse_fn,get_string_tensor_serving_input_receiver_fn,get_supervised_input_receiver_fn_feature_dict,parse_string_tensor