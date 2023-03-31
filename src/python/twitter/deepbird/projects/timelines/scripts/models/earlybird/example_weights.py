# checkstyle: noqa
import tensorflow.compat.v1 as tf
from .constants import INDEX_BY_LABEL, LABEL_NAMES

# TODO: Read these from command line arguments, since they specify the existing example weights in the input data.
DEFAULT_WEIGHT_BY_LABEL = {
  "is_clicked": 0.3,
  "is_favorited": 1.0,
  "is_open_linked": 0.1,
  "is_photo_expanded": 0.03,
  "is_profile_clicked": 1.0,
  "is_replied": 9.0,
  "is_retweeted": 1.0,
  "is_video_playback_50": 0.01
}

def add_weight_arguments(parser):
  for label_name in LABEL_NAMES:
    parser.add_argument(
      _make_weight_cli_argument_name(label_name),
      type=float,
      default=DEFAULT_WEIGHT_BY_LABEL[label_name],
      dest=_make_weight_param_name(label_name)
    )

def make_weights_tensor(input_weights, label, params):
  '''
  Replaces the weights for each positive engagement and keeps the input weights for negative examples.
  '''
  weight_tensors = [input_weights]
  for label_name in LABEL_NAMES:
    index, default_weight = INDEX_BY_LABEL[label_name], DEFAULT_WEIGHT_BY_LABEL[label_name]
    weight_param_name =_make_weight_param_name(label_name)
    weight_tensors.append(
      tf.reshape(tf.math.scalar_mul(getattr(params, weight_param_name) - default_weight, label[:, index]), [-1, 1])
    )
  return tf.math.accumulate_n(weight_tensors)

def _make_weight_cli_argument_name(label_name):
  return f"--weight.{label_name}"

def _make_weight_param_name(label_name):
  return f"weight_{label_name}"
