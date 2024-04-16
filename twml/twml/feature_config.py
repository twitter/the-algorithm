"""
Feature conf gurat on for DeepB rd jobs:
- Wh ch features to keep
- Wh ch features to blackl st
- Wh ch features are labels
- Wh ch feature  s t    ght
"""

from tw ter.deepb rd. o.legacy  mport feature_conf g


class FeatureConf g(feature_conf g.FeatureConf g):
  def get_feature_spec(self):
    """
    Generates a ser al zat on-fr endly d ct represent ng t  FeatureConf g.
    """
    doc = super(FeatureConf g, self).get_feature_spec()
    # Overr de t  class  n t  spec.
    doc["class"] = "twml.FeatureConf g"
    return doc


class FeatureConf gBu lder(feature_conf g.FeatureConf gBu lder):
  def bu ld(self):
    # Overwr e self.bu ld() to return twml.FeatureConf g  nstead
    """
    Bu lds and returns FeatureConf g object.
    """

    (
      features,
      tensor_types,
      sparse_tensor_types,
      feature_map,
      feature_na _to_feature_parser,
      feature_ n_bq_na ,
    ) = self._bu ld()

    return FeatureConf g(
      features=features,
      labels=self._labels,
        ght=self._  ght,
      f lters=self._f lter_features,
      tensor_types=tensor_types,
      sparse_tensor_types=sparse_tensor_types,
      feature_types=feature_map,
      decode_mode=self._decode_mode,
      legacy_sparse=self._legacy_sparse,
      feature_na _to_feature_parser=self._feature_na _to_feature_parser,
      feature_ n_bq_na =self._feature_ n_bq_na ,
    )


_na _to_ d = feature_conf g._na _to_ d
