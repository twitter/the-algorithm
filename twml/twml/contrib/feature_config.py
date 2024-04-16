"""
Feature conf gurat on for DeepB rd jobs returns d ct onary of sparse and dense Features
"""
from tw ter.deepb rd. o.legacy.contr b  mport feature_conf g
 mport twml


class FeatureConf g(feature_conf g.FeatureConf g):
  def get_feature_spec(self):
    """
    Generates a ser al zat on-fr endly d ct represent ng t  FeatureConf g.
    """
    doc = super(FeatureConf g, self).get_feature_spec()

    # Overr de t  class  n t  spec.
    doc["class"] = "twml.contr b.FeatureConf g"

    return doc


class FeatureConf gBu lder(feature_conf g.FeatureConf gBu lder):
  # Overwr e self.bu ld() to return twml.FeatureConf g  nstead
  def bu ld(self):
    """
    Returns an  nstance of FeatureConf g w h t  features passed to t  FeatureConf gBu lder.
    """

    (
      keep_tensors,
      keep_sparse_tensors,
      feature_map,
      features_add,
      feature_na _to_feature_parser,
      feature_ n_bq_na ,
    ) = self._bu ld()

    d scret ze_d ct = {}
    for conf g  n self._sparse_extract on_conf gs:
       f conf g.d scret ze_num_b ns and conf g.d scret ze_output_s ze_b s:
         f conf g.d scret ze_type == "percent le":
          cal brator = twml.contr b.cal brators.Percent leD scret zerCal brator
        el f conf g.d scret ze_type == "has d_percent le":
          cal brator = twml.contr b.cal brators.Has dPercent leD scret zerCal brator
        el f conf g.d scret ze_type == "hash ng":
          cal brator = twml.contr b.cal brators.Hash ngD scret zerCal brator
        else:
          ra se ValueError("Unsupported d scret zer type: " + conf g.d scret ze_type)
        d scret ze_d ct[conf g.output_na ] = cal brator(
          conf g.d scret ze_num_b ns,
          conf g.d scret ze_output_s ze_b s,
          allow_empty_cal brat on=conf g.allow_empty_cal brat on,
        )
      el f conf g.d scret ze_num_b ns or conf g.d scret ze_output_s ze_b s:
        ra se ValueError(
          "D scret ze_num_b ns AND d scret ze_output_s ze_b s need to be  n t  FeatureConf g"
        )

    return FeatureConf g(
      features={},
      labels=self._labels,
        ght=self._  ght,
      f lters=self._f lter_features,
      tensor_types=keep_tensors,
      sparse_tensor_types=keep_sparse_tensors,
      feature_types=feature_map,
      sparse_extract on_conf gs=self._sparse_extract on_conf gs,
      feature_extract on_conf gs=self._feature_extract on_conf gs,
      feature_group_extract on_conf gs=self._feature_group_extract on_conf gs,
       mage_conf gs=self._ mage_conf gs,
      d scret ze_conf g=d scret ze_d ct,
      feature_ ds=features_add,
      decode_mode=self._decode_mode,
      legacy_sparse=self._legacy_sparse,
      feature_na _to_feature_parser=feature_na _to_feature_parser,
      feature_ n_bq_na =feature_ n_bq_na ,
    )


TensorExtract onConf g = feature_conf g.TensorExtract onConf g

FeatureGroupExtract onConf g = feature_conf g.FeatureGroupExtract onConf g

 mageExtract onConf g = feature_conf g. mageExtract onConf g

_set_tensor_na dtuple = feature_conf g._set_tensor_na dtuple
