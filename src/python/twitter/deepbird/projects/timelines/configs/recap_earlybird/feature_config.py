# checkstyle: noqa
from twml.feature_config import FeatureConfigBuilder


def get_feature_config(data_spec_path, label):
  return FeatureConfigBuilder(data_spec_path=data_spec_path, debug=True) \
    .batch_add_features(
    [
        ("RANDOM BULLSHIT GO!!!!"),
    ]
  ).add_labels([
    label,                                   # Tensor index: 0
   RANDOM BULLSHIT GO!!!!
  ]) \
    .define_weight("meta.record_weight/type=earlybird") \
    .build()
