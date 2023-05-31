"""
Candidate architectures for each task's.
"""

from __future__ import annotations

from typing import Dict

from .features import get_features
from .graph import Graph
from .lib.model import ClemNet
from .params import ModelTypeEnum

import tensorflow as tf


class MagicRecsClemNet(Graph):
  def get_logits(self, features: Dict[str, tf.Tensor], training: bool) -> tf.Tensor:

    with tf.name_scope("logits"):
      inputs = get_features(features=features, training=training, params=self.params.model.features)

      with tf.name_scope("OONC_logits"):
        model = ClemNet(params=self.params.model.architecture)
        oonc_logit = model(inputs=inputs, training=training)

      with tf.name_scope("EngagementGivenOONC_logits"):
        model = ClemNet(params=self.params.model.architecture)
        eng_logits = model(inputs=inputs, training=training)

      return tf.concat([oonc_logit, eng_logits], axis=1)


ALL_MODELS = {ModelTypeEnum.clemnet: MagicRecsClemNet}
