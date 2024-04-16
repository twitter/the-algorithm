 mport os
from typ ng  mport D ct

from tw ter.deepb rd.projects.mag c_recs.l bs.model_ut ls  mport f lter_nans_and_ nfs
 mport twml
from twml.layers  mport full_sparse, sparse_max_norm

from .params  mport FeaturesParams, GraphParams, SparseFeaturesParams

 mport tensorflow as tf
from tensorflow  mport Tensor
 mport tensorflow.compat.v1 as tf1


FEAT_CONF G_DEFAULT_VAL = 0
DEFAULT_FEATURE_L ST_PATH = "./feature_l st_default.yaml"
FEATURE_L ST_DEFAULT_PATH = os.path.jo n(
  os.path.d rna (os.path.realpath(__f le__)), DEFAULT_FEATURE_L ST_PATH
)


def get_feature_conf g(data_spec_path=None, feature_l st_prov ded=[], params: GraphParams = None):

  a_str ng_feat_l st = [feat for feat, feat_type  n feature_l st_prov ded  f feat_type != "S"]

  bu lder = twml.contr b.feature_conf g.FeatureConf gBu lder(
    data_spec_path=data_spec_path, debug=False
  )

  bu lder = bu lder.extract_feature_group(
    feature_regexes=a_str ng_feat_l st,
    group_na ="cont nuous_features",
    default_value=FEAT_CONF G_DEFAULT_VAL,
    type_f lter=["CONT NUOUS"],
  )

  bu lder = bu lder.extract_feature_group(
    feature_regexes=a_str ng_feat_l st,
    group_na ="b nary_features",
    type_f lter=["B NARY"],
  )

   f params.model.features.sparse_features:
    bu lder = bu lder.extract_features_as_has d_sparse(
      feature_regexes=a_str ng_feat_l st,
      hash_space_s ze_b s=params.model.features.sparse_features.b s,
      type_f lter=["D SCRETE", "STR NG", "SPARSE_B NARY"],
      output_tensor_na ="sparse_not_cont nuous",
    )

    bu lder = bu lder.extract_features_as_has d_sparse(
      feature_regexes=[feat for feat, feat_type  n feature_l st_prov ded  f feat_type == "S"],
      hash_space_s ze_b s=params.model.features.sparse_features.b s,
      type_f lter=["SPARSE_CONT NUOUS"],
      output_tensor_na ="sparse_cont nuous",
    )

  bu lder = bu lder.add_labels([task.label for task  n params.tasks] + ["label.ntabD sl ke"])

   f params.  ght:
    bu lder = bu lder.def ne_  ght(params.  ght)

  return bu lder.bu ld()


def dense_features(features: D ct[str, Tensor], tra n ng: bool) -> Tensor:
  """
  Performs feature transformat ons on t  raw dense features (cont nuous and b nary).
  """
  w h tf.na _scope("dense_features"):
    x = f lter_nans_and_ nfs(features["cont nuous_features"])

    x = tf.s gn(x) * tf.math.log(tf.abs(x) + 1)
    x = tf1.layers.batch_normal zat on(
      x, mo ntum=0.9999, tra n ng=tra n ng, renorm=tra n ng, ax s=1
    )
    x = tf.cl p_by_value(x, -5, 5)

    transfor d_cont nous_features = tf.w re(tf.math. s_nan(x), tf.zeros_l ke(x), x)

    b nary_features = f lter_nans_and_ nfs(features["b nary_features"])
    b nary_features = tf.dtypes.cast(b nary_features, tf.float32)

    output = tf.concat([transfor d_cont nous_features, b nary_features], ax s=1)

  return output


def sparse_features(
  features: D ct[str, Tensor], tra n ng: bool, params: SparseFeaturesParams
) -> Tensor:
  """
  Performs feature transformat ons on t  raw sparse features.
  """

  w h tf.na _scope("sparse_features"):
    w h tf.na _scope("sparse_not_cont nuous"):
      sparse_not_cont nuous = full_sparse(
         nputs=features["sparse_not_cont nuous"],
        output_s ze=params.embedd ng_s ze,
        use_sparse_grads=tra n ng,
        use_b nary_values=False,
      )

    w h tf.na _scope("sparse_cont nuous"):
      shape_enforced_ nput = twml.ut l.l m _sparse_tensor_s ze(
        sparse_tf=features["sparse_cont nuous"],  nput_s ze_b s=params.b s, mask_ nd ces=False
      )

      normal zed_cont nuous_sparse = sparse_max_norm(
         nputs=shape_enforced_ nput,  s_tra n ng=tra n ng
      )

      sparse_cont nuous = full_sparse(
         nputs=normal zed_cont nuous_sparse,
        output_s ze=params.embedd ng_s ze,
        use_sparse_grads=tra n ng,
        use_b nary_values=False,
      )

    output = tf.concat([sparse_not_cont nuous, sparse_cont nuous], ax s=1)

  return output


def get_features(features: D ct[str, Tensor], tra n ng: bool, params: FeaturesParams) -> Tensor:
  """
  Performs feature transformat ons on t  dense and sparse features and comb ne t  result ng
  tensors  nto a s ngle one.
  """
  w h tf.na _scope("features"):
    x = dense_features(features, tra n ng)
    tf1.logg ng. nfo(f"Dense features: {x.shape}")

     f params.sparse_features:
      x = tf.concat([x, sparse_features(features, tra n ng, params.sparse_features)], ax s=1)

  return x
