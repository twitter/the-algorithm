"""
Cand date arch ectures for each task's.
"""

from __future__  mport annotat ons

from typ ng  mport D ct

from .features  mport get_features
from .graph  mport Graph
from .l b.model  mport ClemNet
from .params  mport ModelTypeEnum

 mport tensorflow as tf


class Mag cRecsClemNet(Graph):
  def get_log s(self, features: D ct[str, tf.Tensor], tra n ng: bool) -> tf.Tensor:

    w h tf.na _scope("log s"):
       nputs = get_features(features=features, tra n ng=tra n ng, params=self.params.model.features)

      w h tf.na _scope("OONC_log s"):
        model = ClemNet(params=self.params.model.arch ecture)
        oonc_log  = model( nputs= nputs, tra n ng=tra n ng)

      w h tf.na _scope("Engage ntG venOONC_log s"):
        model = ClemNet(params=self.params.model.arch ecture)
        eng_log s = model( nputs= nputs, tra n ng=tra n ng)

      return tf.concat([oonc_log , eng_log s], ax s=1)


ALL_MODELS = {ModelTypeEnum.clemnet: Mag cRecsClemNet}
