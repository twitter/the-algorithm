"""
T   s a temporary close gap solut on that allows TensorFlow users to do explorat on and
exper  ntat on us ng Keras models, and product on tra n ng us ng twml Tra ner.

As of now (Q4 2019), Keras model tra n ng us ng `model.f ()` has var ous  ssues, mak ng   unf 
for product on tra n ng:
  1. `model.f ()`  s slow  n TF 1.14. T  w ll be f xed w h future TensorFlow updates.
  2. `model.f ()` cras s dur ng model sav ng or  n eager mode w n t   nput has SparseTensor.
  3. Models saved us ng TF 2.0 AP  cannot be served by TensorFlow's Java AP .

Unt l MLCE team resolves t  above  ssues, MLCE team recom nds t  follow ng:
  - Please feel free to use Keras models for exper  ntat on and explorat on.
  - Please st ck to twml Tra ner for product on tra n ng & export ng,
    espec ally  f   want to serve y  model us ng Tw ter's pred ct on servers.

T  module prov de tool ng for eas ly tra n ng keras models us ng twml Tra ner.

T  module takes a Keras model that performs b nary class f cat on, and returns a
`twml.tra ners.Tra ner` object perform ng t  sa  task.
T  common way to use t  returned Tra ner object  s to call  s
`tra n`, `evaluate`, `learn`, or `tra n_and_evaluate`  thod w h an  nput funct on.
T   nput funct on can be created from t  tf.data.Dataset   used w h y  Keras model.

.. note: t  ut l handles t  most common case.  f   have cases not sat sf ed by t  ut l,
         cons der wr  ng y  own bu ld_graph to wrap y  keras models.
"""
from tw ter.deepb rd.hparam  mport HParams

 mport tensorflow  # noqa: F401
 mport tensorflow.compat.v2 as tf

 mport twml


def bu ld_keras_tra ner(
  na ,
  model_factory,
  save_d r,
  loss_fn=None,
   tr cs_fn=None,
  **kwargs):
  """
  Comp le t  g ven model_factory  nto a twml Tra ner.

  Args:
    na : a str ng na  for t  returned twml Tra ner.

    model_factory: a callable that returns a keras model w n called.
      T  keras model  s expected to solve a b nary class f cat on problem.
      T  keras model takes a d ct of tensors as  nput, and outputs a log  or probab l y.

    save_d r: a d rectory w re t  tra ner saves data. Can be an HDFS path.

    loss_fn: t  loss funct on to use. Defaults to tf.keras.losses.B naryCrossentropy.

     tr cs_fn:  tr cs funct on used by TensorFlow est mators.
    Defaults to twml. tr cs.get_b nary_class_ tr c_fn().

    **kwargs: for people fam l ar w h twml Tra ner's opt ons, t y can be passed  n  re
      as kwargs, and t y w ll be forwarded to Tra ner as opts.
      See https://cg .tw ter.b z/s ce/tree/twml/twml/argu nt_parser.py#n43 for ava lable args.

  Returns:
    a twml.tra ners.Tra ner object wh ch can be used for tra n ng and export ng models.
  """
  bu ld_graph = create_bu ld_graph_fn(model_factory, loss_fn)

   f  tr cs_fn  s None:
     tr cs_fn = twml. tr cs.get_b nary_class_ tr c_fn()

  opts = HParams(**kwargs)
  opts.add_hparam('save_d r', save_d r)

  return twml.tra ners.Tra ner(
    na ,
    opts,
    bu ld_graph_fn=bu ld_graph,
    save_d r=save_d r,
     tr c_fn= tr cs_fn)


def create_bu ld_graph_fn(model_factory, loss_fn=None):
  """Create a bu ld graph funct on from t  g ven keras model."""

  def bu ld_graph(features, label, mode, params, conf g=None):
    # create model from model factory.
    model = model_factory()

    # create loss funct on  f t  user d dn't spec fy one.
     f loss_fn  s None:
      bu ld_graph_loss_fn = tf.keras.losses.B naryCrossentropy(from_log s=False)
    else:
      bu ld_graph_loss_fn = loss_fn

    output = model(features)
     f mode == ' nfer':
      loss = None
    else:
        ghts = features.get('  ghts', None)
      loss = bu ld_graph_loss_fn(y_true=label, y_pred=output, sample_  ght=  ghts)

     f  s nstance(output, d ct):
       f loss  s None:
        return output
      else:
        output['loss'] = loss
        return output
    else:
      return {'output': output, 'loss': loss}

  return bu ld_graph
