"""
T  module conta ns ut l y funct ons for twml.
"""

 mport argparse
from datet    mport datet  
 mport  ertools
 mport json
 mport logg ng as _logg ng
 mport os
 mport re

from tw ter.ml.common.res ces  mport AuroraPath
from tw ter.deepb rd.hparam  mport HParams
from tw ter.deepb rd. o.ut l  mport (
  _get_feature_ d,  # noqa: F401
  feature_ d,  # noqa: F401
  preprocess_feature_regex,  # noqa: F401
  preprocess_path,  # noqa: F401
  san  ze_hdfs_path,  # noqa: F401
   s_str ng,  # noqa: F401
  l st_f les,  # noqa: F401
  match_f les,  # noqa: F401
)
from tw ter.deepb rd. o.legacy.ut l  mport (
  batch_apply,  # noqa: F401
  boolean_mask,  # noqa: F401
  f xed_length_tensor,  # noqa: F401
)
from tw ter.deepb rd.sparse.ut l  mport (
  convert_to_sparse,  # noqa: F401
  l m _b s,  # noqa: F401
)

from dateut l  mport rrule
from jobl b  mport delayed, Parallel
from s x  mport str ng_types

from absl  mport logg ng
from l btwml  mport CL B, OPL B  # noqa: F401
 mport tensorflow.compat.v1 as tf
from tensorflow.python.platform  mport tf_logg ng
 mport twml
from twml.feature_conf g  mport FeatureConf gBu lder


# b g_pr    s less than 2**32
# T  just needs to be co-pr   w h po rs of 2
# any large pr    s suff c ent, but  's not necessary.
HASH NG_PR ME = 2479700537


def mult pl cat ve_hash( nput, hash_constant=HASH NG_PR ME):
  return  nput * hash_constant


def _return_tensors_from_c ckpo nt_folder( n _d r, model_na =None):
  """Returns tensors l st from a c ckpo nt folder

  Args:
     n _d r: Na  of t  c ckpo nt d rectory.
    model_na : t  model wh ch   w ll use to obta n t  c ckpo nt
      (e.g. model.ckpt-50000)  f set to None   w ll default to t 
      latest model saved  n t  c ckpont f le.

  """
   f model_na   s None:
    # gets t  most recently generated model.cpkt f le
    model_path = tf.tra n.latest_c ckpo nt( n _d r)
     f model_path  s None:
      ra se ValueError("Could not f nd a val d model c ckpo nt  ns de t  d rectory")
  else:
    model_path = os.path.jo n( n _d r, model_na )
  reader = tf.tra n.NewC ckpo ntReader(model_path)
  try:
    return (reader.debug_str ng().decode("utf-8"))
  except OSError:
    logg ng.error('Could not decode t  str ng')


def get_scope_d ct( n _d r,  ncom ng_scope_na , current_scope_na , model_na =None):
  """Returns tensors map from a c ckpo nt f le.

  Args:
    f le_na :
      Na  of t  c ckpo nt d rectory.
     ncom ng_scope_na :
      scope na  of t  prev ous phase
    current_scope_na :
      scope na  of current phase
    model_na :
      t  model wh ch   w ll use to obta n t  c ckpo nt
      (e.g. model.ckpt-50000)  f set to None   w ll default
      to t  latest model saved  n t  c ckpo nt f le.
  Returns:
     n _map:
       n _map wh ch w ll be  nputted to t  c ckpo nt
  """
   n _map = {}
  reader_dump = _return_tensors_from_c ckpo nt_folder( n _d r= n _d r,
                                                       model_na =model_na ).spl l nes()
  for  mber  n reader_dump:
    # remove global_step s nce    s not necessary
     f 'global_step' not  n  mber:
      saved_var ables = str( mber.spl (" ")[0])
      saved_scope = saved_var ables.rspl ('/', 1)[0] + "/"
      new_scope = saved_scope.replace( ncom ng_scope_na , current_scope_na , 1)
      # create key  n  n _map
       f saved_scope not  n  n _map.keys():  # pyl nt: d sable=d ct-keys-not- erat ng
         n _map[saved_scope] = new_scope
  return  n _map


def get_ n _map(
         n _from_d r,
        exclude_var_na s=None,
        exclude_na _scopes=None,
        na _scope_to_remove=None,
        na _scope_to_prepend=None):
  """
  Bu lds a map for  n  al z ng from a c ckpo nt (see tf.tra n. n _from_c ckpo nt).

    assu s that t  latter part of t  var able na s are cons stent bet en t  c ckpo nt and
  t  new model, but t  r na _scopes may be d fferent.  f t  c ckpo nt model has var able na s
  of t  form old/scope/var/foo, and t  correspond ng var able na s for t  new model should be
   /new/scope/var/foo, t n   should set na _scope_to_remove = 'old/' and
  na _scope_to_prepend = ' /new/'.

  T  funct on can be used to

  1. Generate an `` n _map`` map that can be passed to t  ``Tra ner``  n  or
  2. Used to generate an `` n _map`` d rectly  ns de ``bu ld_graph_fn``,  n
     wh ch case   should be passed d rectly to ``tf.tra n. n _from_c ckpo nt``  ns de
     ``bu ld_graph_fn``,  n wh ch case   do not also need to spec fy t  `` n _map`` argu nt to
     t  tra ner.

  Para ters
  ----------
   n _from_d r: D rectory conta n ng c ckpo nt
  exclude_var_na s: l st[str]
    L st of var ables  n t  c ckpo nt that should be excluded from t  map.
  exclude_na _scopes: l st[str]
    L st of na _scopes  n t  c ckpo nt model that should be excluded from t  map.
  na _scope_to_remove: str
    port on of na _scope for c ckpo nt var ables that should not be  ncluded  n var able na s
    for new model.
  na _scope_to_prepend: str
    na _scope to prepend to var able na s  n c ckpo nt to g ve var able na s for new model.

  Returns
  -------
  d ct
    keys are var able na s  n t  c ckpo nt and values are var able na s  n t  new model,
     nto wh ch t  c ckpo nt para ters should be loaded.
  """
  vars_to_restore = get_c ckpo nt_var able_na s(
     n _from_d r,
    exclude_var_na s=exclude_var_na s,
    exclude_scopes=exclude_na _scopes,
  )

   f na _scope_to_prepend  s not None:
     f not na _scope_to_prepend.endsw h('/'):
      na _scope_to_prepend += '/'

   f na _scope_to_remove  s not None:
     f not na _scope_to_remove.endsw h('/'):
      na _scope_to_remove += '/'

   n _map = {}

  for var_na   n vars_to_restore:
    var_na _c ckpo nt = var_na 

     f na _scope_to_remove  s not None:
      var_na  = var_na .replace(na _scope_to_remove, '')

    var_na _new_model = var_na 

     f na _scope_to_prepend  s not None:
      var_na _new_model = na _scope_to_prepend + var_na _new_model

     n _map[var_na _c ckpo nt] = var_na _new_model

  return  n _map


def get_c ckpo nt_var able_na s(model_d r, exclude_var_na s=None, exclude_scopes=None):
  """
  Gets a l st of var able na s from t  latest c ckpo nt  n model_d r.
  Removes var ables w h scope def ned by exclude_scopes, and/or w h na s def ned by
  exclude_var_na s.

  Args:
    model_d r (str): D rectory conta n ng c ckpo nt f le for t  pre-tra ned model
    exclude_var_na s (l st): Opt onal var able na s to exclude (can  nclude full/part al scope)
    exclude_scopes (l st): Opt onal scopes to exclude

  Returns:
    l st: var able na s
  """
  c ckpo nt_path = tf.tra n.latest_c ckpo nt(model_d r)
  var ables_and_shapes = tf.tra n.l st_var ables(c ckpo nt_path)

  def _keep(na ):
     f exclude_scopes and any(na .startsw h(exc_scope) for exc_scope  n exclude_scopes):
      return False
     f exclude_var_na s and any(na .endsw h(exc_var) for exc_var  n exclude_var_na s):
      return False
    return True

  na s = [x[0] for x  n var ables_and_shapes  f _keep(x[0])]

  return na s


def to_snake_case(na ):
  """
  Changes na  to snake case
  """
   nter d ate = re.sub('(.)([A-Z][a-z0-9]+)', r'\1_\2', na )
   nsecure = re.sub('([a-z])([A-Z])', r'\1_\2',  nter d ate).lo r()
  #  f t  class  s pr vate t  na  starts w h "_" wh ch  s not secure
  # for creat ng scopes.   pref x t  na  w h "pr vate"  n t  case.
   f  nsecure[0] != '_':
    return  nsecure
  return 'pr vate' +  nsecure


def copy_phase_ nputs( n _d r, dest_d r):
  """Automat cally cop es t  .json.tf from t   n _d r to save_d r
  so   can load mult ple para ters at t  sa  t  .

  Args:
     n _d r:
      Na  of t  c ckpo nt d rectory.
    dest_d r:
      Na  of t  output d rectory.
  """
   f  n _d r  s not None:
    #   are us ng tf. o.gf le so   can use   w h both local and hdfs paths
    for f les  n tf. o.gf le.l std r( n _d r):
       f f les.endsw h(".json.tf"):
        src_f le = os.path.jo n( n _d r, f les)
        dest_f le = os.path.jo n(dest_d r, f les)
         f not tf. o.gf le.ex sts(dest_d r):
          # creates t  folder
          try:
            tf. o.gf le.maked rs(dest_d r)
          # to prevent rac ng cond  on
          except OSError:
             f not tf. o.gf le. sd r(dest_d r):
              ra se
        # dest_f le may be old  f   ex sts and
        # dest_f le gets cop ed several t  s  n d str buted tra n ng
        tf. o.gf le.copy(src_f le, dest_f le, overwr e=True)


def rehash_sparse_features_nb s(sp_a, nb s, hash_fn=mult pl cat ve_hash):
  """
  Rehash t  feature  ds of t  sparse tensor,
  and l m  t  output to n b s.

  T   s useful for mak ng t  d str but on of
  feature_ ds more un form, wh ch may  mprove performance
   n so  s uat ons.

  T  would typ cally be used on t  output of
  Percent leD scret zer, s nce   ass gns many
  b ns to low-valued output feature  ds.

   nput feature  Ds should take values less than 2**32,
  and nb s should be less than 32

  Args:
    sp_a:
      a tf.SparseTensor object
    nb s:
       nteger number of b s to mask output feature_ ds
    hash_fn:
      Funct on that takes  nteger values and returns has s of t se values.
      T  output does not need to be masked to t  des red number of b s,
      as t  mask ng w ll be taken care of. Default value = mult pl cat ve_hash.

  Returns:
    a new tf.SparseTensor
  """

  feature_ ds = sp_a. nd ces[:, 1]
  feature_ ds = hash_fn(feature_ ds)

  sample_ ds = sp_a. nd ces[:, 0]
  values = sp_a.values
  dense_shape = sp_a.dense_shape

   nd ces = tf.stack([sample_ ds, feature_ ds], ax s=1)

  sp_a = tf.SparseTensor( nd ces, values, dense_shape)

  # note -   need 2**nb s >= batch s ze
  # ot rw se, sample_ ds w ll be squas d by t  mask.
  return l m _sparse_tensor_s ze(sp_a, nb s)


def convert_to_hparams(opt):
  """
  Converts argparse.Na space object to tw ter.deepb rd.hparam.hparam.HParams.
  Note that tensorflow.contr b.tra n ng.HParams  s gone  n TF 2.x, and   forward ported
  tensorflow.contr b.tra n ng.HParams to tw ter.deepb rd.hparam.hapram.HParams.

  NOTE:  f   are us ng est mators, please don't call t   thod and d rectly pass python d ct
  to TensorFlow est mator. Start ng TensorFlow 2.0, Est mator w ll only accept d cts.
  """

  # Convert to d ct so   can  erate through   cleanly.
   f  s nstance(opt, argparse.Na space):
    params_d ct = vars(opt)
  el f  s nstance(opt, d ct):
    params_d ct = opt
  el f  s nstance(opt, HParams):
    logg ng.warn ng(' f   are us ng Est mator, please pass python d ct d rectly to Est mator.')
    params_d ct = opt.values()
  else:
    ra se ValueError(" nput can not be of type %s. "
                     "  can be one of { argparse.Na space, d ct, "
                     "tw ter.deepb rd.hparam.HParams}."
                     % type(opt))

  params = HParams()
  # Hack to convert all para ters from hdfs:/// format to hdfs://default/
  # Note: . ems() makes a copy  n python 2.7, but that  s f ne s nce t  performance  sn't cr  cal.
  for key, val  n params_d ct. ems():
    val = params_d ct[key]
    # F x t  path  f t  value  s a str ng
     f  s nstance(val, str):
      params.add_hparam(key, san  ze_hdfs_path(val))
    else:
      params.add_hparam(key, val)

  return params


def dynam c_part  on(features, part  ons, num_part  ons=2, na =None):
  """
  Part  ons each of t  tensor  n features us ng t  prov ded mask.

  Args:
    features:
      A s ngle tensor or an  erable of tensors (l st, tuple, d ct)
    part  ons:
      A bool or  nteger tensor represent ng t  part  ons.

  Returns part  oned outputs as a l st. Each ele nt of t  l st  s t  sa  type as features.

  T  uses tf.dynam c_part  on but adds t  follow ng n cet es:
    - features can be a l st or d ct of d fferent tensor types.
    - only a part  on tensor  s used to part  on all t  feature tensors recurs vely.
    - t  part  on tensor  s automat cally converted  nto an  nteger tensor.
    - defaults to num_part  ons == 2
  """

   f not  s nstance(features, (d ct, l st, tuple, tf.Tensor)):
    ra se Assert onError("features conta ner must be a d ct, l st, or tuple, tf.Tensor")

   f  s nstance(part  ons, tf.Tensor):
    part  ons = tf.cast(part  ons, tf. nt32)

   f  s nstance(features, tf.Tensor):
    return tf.dynam c_part  on(features, part  ons, num_part  ons, na )

  outputs = []
  for _  n range(num_part  ons):
     f  s nstance(features, (tuple, l st)):
      # Create an empty l st of l sts f rst, w ll be converted to r ght type afterwards.
      outputs.append([None for _  n range(len(features))])
    else:
      outputs.append(d ct())

   erable = features. ems()  f  s nstance(features, d ct) else enu rate(features)

  # Handl ng part  ons of nested classes handled  re:
  # Recurs vely call dynam c_part  on for conta ners
  for key, feature  n  erable:
    na _key = None  f na   s None else na  + "_" + str(key)
     f  s nstance(part  ons, tf.Tensor):
      results = tf.dynam c_part  on(feature, part  ons, num_part  ons, na _key)
    else:
      results = tf.dynam c_part  on(feature, part  ons[key], num_part  ons[key], na _key)
      # Append t  result to t  proper output conta ner
    for  dx, result  n enu rate(results):
      outputs[ dx][key] = result

  #  f  nput  s tuple, convert l st of l sts back to l st of tuples
   f  s nstance(features, tuple):
    outputs = [type(features)(output) for output  n outputs]

  return outputs


def wr e_f le(f lena , contents, encode=False):
  '''
  Opt onally encodes contents and wr es contents to a f le.

  Argu nts:
    f lena :
      path to f le w re t  contents w ll be saved.
      Accepts HDFS and local paths.
    contents:
      contents to save to t  f le.
      Must be a str ng w n encode  s False.
    encode:
      False | 'json'. W n encode='json', contents  s encoded
      w h json.dumps.
  '''
   f encode == 'json':
    contents = json.dumps(contents)
  el f not  s_str ng(contents):
    ra se ValueError("Expect ng str ng for encode=False")

  graph = tf.Graph()
  w h graph.as_default():
    wr e = tf.wr e_f le(f lena , contents)

  w h tf.Sess on(graph=graph) as sess:
    sess.run(wr e)


def read_f le(f lena , decode=False):
  '''
  Reads contents from a f le and opt onally decodes  .

  Argu nts:
    f lena :
      path to f le w re t  contents w ll be loaded from.
      Accepts HDFS and local paths.
    decode:
      False | 'json'. W n decode='json', contents  s decoded
      w h json.loads. W n False, contents  s returned as  s.

  Returns:
    contents
  '''
  graph = tf.Graph()
  w h graph.as_default():
    read = tf.read_f le(f lena )

  w h tf.Sess on(graph=graph) as sess:
    contents = (sess.run(read))
    # part cular vers on of TF and/or Python may or may not perform decod ng step from utf-8 to str
     f not  s nstance(contents, str):
      contents = contents.decode()

   f decode == 'json':
    contents = json.loads(contents)

  return contents

def setup_tf_logg ng_formatter():
  formatter = _logg ng.Formatter(
      '%(asct  )s [%(levelna )s] %(na )s: %( ssage)s',
      None)
  # Sett ng up absl logg ng verbos y
  logg ng.set_verbos y(' nfo')
  logg ng.set_stderrthreshold(' nfo')
  logg ng.get_absl_handler().setFormatter(formatter)
  tf.logg ng.set_verbos y(tf.logg ng. NFO)
  # Set tensorflow logg ng handler format
   f len(tf_logg ng.get_logger().handlers) > 0:
    tf_logg ng.get_logger().handlers[0].setFormatter(formatter)


def set_tensorflow_log_level(log_level):
  """
  Sets tensorflow's default logg ng level.

  0. all logs are shown.
  1. f lter out  NFO logs.
  2. f lter out WARN NGs and  NFOs.
  3. f lter out ERRORs, WARN NGs, and  NFOs.

  Note that tf.Pr nt output are  NFO logs, so sett ng log_level above 0 would h de
  output from tf.Pr nt.
  """
  assert  s nstance(log_level,  nt) and log_level >= 0 and log_level <= 3
  os.env ron['TF_CPP_M N_LOG_LEVEL'] = str(log_level)


def   ghted_average(values,   ghts):
  """
  Compute a   ghted average us ng t  g ven values and   ghts.
  E.g. t   s usually used to compute a   ghted loss g ven sample   ghts.
  """
  return tf.reduce_sum(tf.mult ply(values,   ghts)) / tf.reduce_sum(  ghts)


def backup_c ckpo nt(c ckpo nt_path_pref x,
                      backup_path='backup',
                      empty_backup=True):
  """
  Creates a backup copy of a c ckpo nt  n backup_d r.
  T  funct on  s used by t  Tra ner for early-stopp ng.

  Argu nts:
    c ckpo nt_path_pref x:
      Pref x of t  path to t  c ckpo nt f les.
    backup_path:
      path to a d rectory w re c ckpo nt f les w ll be backed up.
    empty_backup:
      W n True (t  default), t  current contents of t  backup d rectory
      are removed before t  backup  s perfor d.

  Returns:
    T  number of backed up f les.
  """
  c ckpo nt_f le_pref x = os.path.basena (c ckpo nt_path_pref x)

   f tf. o.gf le.ex sts(backup_path) and empty_backup:
    tf. o.gf le.rmtree(backup_path)

  tf. o.gf le.mkd r(backup_path)

  n_backup = 0
  # copy all c ckpo nt f les to backup d rectory (TODO use gf le.glob  nstead)
  try:
    c ckpo nt_f les = tf. o.gf le.glob(c ckpo nt_path_pref x + "*")
     f len(c ckpo nt_f les) == 0:
      ra se twml.errors.C ckpo ntNotFoundError("%s not found" % c ckpo nt_path_pref x)
    for f lena   n c ckpo nt_f les:
      n_backup += 1
      tf. o.gf le.copy(
        src=f lena ,
        dst=os.path.jo n(backup_path, os.path.basena (f lena ))
      )
  except tf.errors.OpError as ex:
    ra se twml.errors.C ckpo ntNotFoundError(
      f"{str(ex)}\n {c ckpo nt_path_pref x} not found."
    )

  # tf.tra n.latest_c ckpo nt needs t  'c ckpo nt' f le.
  w h tf. o.gf le.GF le(os.path.jo n(backup_path, 'c ckpo nt'), 'w') as f:
    f.wr e('model_c ckpo nt_path: "%s"\n' % c ckpo nt_f le_pref x)

  return n_backup


def set_only_c ckpo nt(s ce_path, dest_path, remove_s ce=True):
  """
  Removes t  c ckpo nt and model.ckpt* f les from dest_path.
  Moves t  latest c ckpo nt from s ce_path to dest_path.

  Argu nts:
    s ce_path:
      path to d rectory conta n ng t  latest c ckpo nt.
      Should conta n a val d c ckpo nt f le and model.ckpt f les.
      For early-stopp ng, t  should be t  save_d r/best_c ckpo nt d r.
    dest_path:
      path to d rectory w re t  latest c ckpo nt f les w ll be moved.
      All  s c ckpo nt and model.ckpt* f les w ll be removed.
      For early-stopp ng, t  should be t  save_d r.
    remove_s ce:
      W n True (t  default), deletes t  s ce d rectory.
      Note that even w n False,  s c ckpo nt f les are moved to
      dest_path anyway.
      T  deletes t  s ce d rectory (and any rema n ng contents).
  """
  # make   so that s ce_path c ckpo nt  s t  only c ckpo nt
  s ce_path_pref x = tf.tra n.latest_c ckpo nt(s ce_path)
   f s ce_path_pref x  s not None:
    # remove  nter d ate c ckpo nts
    for f lena   n tf. o.gf le.l std r(dest_path):
       f f lena .startsw h("model.ckpt"):
        tf. o.gf le.Remove(os.path.jo n(dest_path, f lena ))
    # move contents of s ce_path to dest_path
    for f lena   n tf. o.gf le.l std r(s ce_path):
      tf. o.gf le.rena (
        oldna =os.path.jo n(s ce_path, f lena ),
        newna =os.path.jo n(dest_path, f lena ),
        overwr e=True)  # overwr e "c ckpo nt" f le
    # delete t  s ce_path d r
     f remove_s ce:
      tf. o.gf le.rmtree(s ce_path)


def l st_f les_by_datet  (
  base_path,
  start_datet  ,
  end_datet  =None,
  datet  _pref x_format='%Y/%m/%d/%H',
  extens on='lzo',
  parallel sm=1,
  h _resolut on=1,
  sort=False
):
  """L st f les match ng `base_path/dt_pref x_format/*.extens on` for t  requested datet   range.

  Args:
    base_path:
      T  base path.  f `None`, returns `None`.
    start_datet  :
      A `datet  .datet  ` or str ng represent ng t  start of t  range ( nclus ve).
       f `None`,   returns `l st_f les(base_path, extens on, sort)`.
    end_datet  :
      A `datet  .datet  ` or str ng represent ng t  end of t  range ( nclus ve).
       f `None`, assu d to be t  sa  as start_datet  .
    datet  _pref x_format:
      Format compat ble w h `datet  .datet  .strft  `
      (https://docs.python.org/2/l brary/datet  .html#strft  -and-strpt  -behav or).
    extens on:
      T  extens on of t  f les compos ng t  dataset (e.g. 'lzo').
    parallel sm:
      T  number of threads used to process l st patterns (t   s mostly useful
      w n deal ng w h f lesystems such as HDFS  n wh ch l st ng f les  s a potent ally expens ve
      operat on).
    h _resolut on:
      T  separat on bet en consecut ve h s. T  default value  s 1.
    sort:
      bool, w t r to return a sorted l st of f les. Default False.

  Returns:
    A l st w h all t  match ng f les.

  Ra ses:
    errors.OpError:  f t re are f lesystem / d rectory l st ng errors.
  """
   f h _resolut on  s None:
    h _resolut on = 1

   f base_path  s None:
    return None

   f start_datet    s None:
    return l st_f les(base_path, extens on, sort)

  # Do t   n case people want to use a s ngle day for tra n ng.
   f end_datet    s None:
    end_datet   = start_datet  

  assert parallel sm > 0
  assert start_datet   <= end_datet  

   f  s nstance(start_datet  , str):
    start_datet   = datet  .strpt  (start_datet  , datet  _pref x_format)

   f  s nstance(end_datet  , str):
    end_datet   = datet  .strpt  (end_datet  , datet  _pref x_format)

  assert  s nstance(start_datet  , datet  )
  assert  s nstance(end_datet  , datet  )

  base_path = preprocess_path(base_path)

  def _handle_m ss ng_globs(pattern):
    try:
      return tf. o.gf le.glob(pattern)
    except tf.errors.NotFoundError as e:
      tf.logg ng.warn ng(e. ssage)
      return []

  # a set  s used because t re m ght be so  repeated globs depend ng on dt_pref x_format
  globs = {
    os.path.jo n(base_path, dt.strft  (datet  _pref x_format), '*.%s' % extens on)
    for dt  n rrule.rrule(
      freq=rrule.HOURLY,  nterval=h _resolut on, dtstart=start_datet  , unt l=end_datet  )
  }
  nested_f les = Parallel(n_jobs=parallel sm, backend='thread ng')(
    delayed(_handle_m ss ng_globs)(p) for p  n globs
  )
  flattened_f les = l st( ertools.cha n.from_ erable(nested_f les))

   f not flattened_f les:
    error_msg = "F les l st  s empty: base_path={base_path}, start_datet  ={start_datet  }, end_datet  ={end_datet  }".format(
      base_path=base_path, start_datet  =start_datet  , end_datet  =end_datet  
    )
    ra se OSError(error_msg)

   f sort:
    flattened_f les = sorted(flattened_f les)

  return flattened_f les


def l m _sparse_tensor_s ze(sparse_tf,  nput_s ze_b s, mask_ nd ces=True):
  """
  Returns a ``tf.SparseTensor`` wh ch  s t   nput SparseTensor
  l m ed to t  spec f ed  nput_s ze_b s

  Args:
    sparse_tf:
      twml.SparseTensor or tf.SparseTensor
     nput_s ze_b s:
      T  number of b s allocated to t   nput s ze.
       nput s ze w ll be po r(2, nput_s ze_b s).
      Note that twml.l m _b s truncates any feature keys that
      exceed t   nput s ze.
    mask_ nd ces:
       f mask  nd ces  s False; only t  shape  s changed. Defaults to True.
  """
   f  s nstance(sparse_tf, twml.SparseTensor):
    sparse_tf = sparse_tf.to_tf()
   f not  s nstance(sparse_tf, tf.SparseTensor):
    ra se TypeError(' nput argu nt `sparse_tf` should e  r be of type'
                    'twml.SparseTensor of tf.SparseTensor. Found type: {}'.
                    format(type(sparse_tf)))
   f mask_ nd ces:
     nd ces = twml.l m _b s(sparse_tf. nd ces,  nput_s ze_b s)
  else:
     nd ces = sparse_tf. nd ces
  dense_shape = tf.stack([sparse_tf.dense_shape[0], 1 <<  nput_s ze_b s])
  return tf.SparseTensor( nd ces= nd ces, values=sparse_tf.values,
                         dense_shape=dense_shape)


def create_module_spec(mlp_fn, mode, params, drop_collect ons=None):
  """
  Creates a standard tags_and_args wh ch should be passed to t  create_module_spec
  spec = hub.create_module_spec(mlp_fn, tags_and_args=tags_and_args).

  Args:
    module_fn:
      a funct on to bu ld a graph for t  Module.
    mode:
      mode  n wh ch t  Est mator  s run
    params:
      para ters passed to t  Est mator
  """
   mport tensorflow_hub as hub # noqa: F402
  tags_and_args = [(set(), {"params": params, "mode": mode}),  # serv ng graph
                   ({"tra n"}, {"params": params, "mode": mode})  # tra n ng graph
                   ]
  spec = hub.create_module_spec(mlp_fn, tags_and_args=tags_and_args, drop_collect ons=drop_collect ons)
  return spec


def change_na _scope_from_d r( n _scope_na , f nal_scope_na , save_d r):
  """
  Changes t  na  of t  saved scope to t  des red na  and saves  
  to t  sa  save_d r.

  Args:
     n _scope_na :
       n  al scope na 
    f nal_scope_na :
      des red (f nal) scope na 
    save_d r:
      d rectory wh ch t  scopes are saved

   n t  follw ng sect on  :
    - Read all t  var ables from t  latest c ckpo nt.
    - Make a copy of t  var ables w h new na  scope.
    - Store both sets of var ables  nto t  latest c ckpo nt.
  T  essent ally doubles up t  s ze of t  c ckpo nt.
  But w n a job  s restarted after t  part  s done, t  c ckpo nt s ze doubles aga n.
  To avo d do ng t ,   create a copy  n backup  f a backup  sn't found.
  T  allows us always read (from backup) and wr e sa  s zed c ckpo nt f les.
  """

  # Create a backup_c ckpo nts d r
  backup_d r = os.path.jo n(save_d r, "change_na _scope_backups")
  tf. o.gf le.maked rs(backup_d r)

  latest_c ckpo nt = tf.tra n.latest_c ckpo nt(save_d r)

   f latest_c ckpo nt  s None:
    ra se OSError("No c ckpo nts found  n save_d r: %s" % save_d r)

  latest_backup_c ckpo nt = tf.tra n.latest_c ckpo nt(backup_d r)

   f (latest_backup_c ckpo nt  s None or
      (os.path.basena (latest_c ckpo nt) !=
       os.path.basena (latest_backup_c ckpo nt))):
    backup_c ckpo nt(latest_c ckpo nt, backup_d r, empty_backup=False)

  var ables = tf.tra n.l st_var ables(backup_d r)
  w h tf.Graph().as_default(), tf.Sess on().as_default() as sess:
    new_var ables = []
    for na , _  n var ables:
      var = tf.tra n.load_var able(backup_d r, na )
      # Append both t  rena  and t  or g nal var able
      new_var ables.append(
        tf.Var able(var, na =na .replace( n _scope_na , f nal_scope_na )))
      new_var ables.append(tf.Var able(var, na =na ))
    # Save t  to t  c ckpo nt  n t  save_d r
    saver = tf.tra n.Saver(new_var ables)
    sess.run(tf.global_var ables_ n  al zer())
    saver.save(sess, latest_c ckpo nt)  # pyl nt: d sable=no- mber


def hub_ mport( nput, module, module_na , tra nable=False):
  """
  Loads exported hub module.

  Args:
     nput:
       nput to hub module
    module:
      module path
    module_na :
      s gnature of t  exported hub module
  """
   mport tensorflow_hub as hub # noqa: F402
  hub_module = hub.Module(module, tra nable=tra nable)
  output = hub_module( nput, s gnature=module_na )
  return output


def _extract_hash_space_b s(feature_conf g):
  """
  Extract Sparse Shapes for contr b.FeatureConf g.
  Argu nts:
    feature_conf g:
      Feature Conf gurat on of t  type contr b.FeatureConf g
  Returns:
    D ct onary of tensor na s and hash space b s.
  """
   f not  s nstance(feature_conf g, twml.contr b.feature_conf g.FeatureConf g):
    fc_type = type(feature_conf g)
    ra se TypeError(f"Feature conf g must be of type contr b.FeatureConf g: {fc_type}")
  sparse_shapes_d ct = {}
  for conf g  n feature_conf g.sparse_extract on_conf gs:
    sparse_shapes_d ct[conf g.output_na ] = conf g.hash_space_b s
  return sparse_shapes_d ct


def f x_shape_sparse(features, feature_conf g):
  """
  Mod f es t  shape of features wh ch are extracted us ng t  hash ng tr ck.
  Features  self  s changed by t  funct on.
  Argu nts:
    features:
      Feature d ct onary extracted by t  feature conf g
    feature_conf g:
      Feature Conf gurat on of t  type contr b.FeatureConf g
  """
   f not  s nstance(feature_conf g, twml.contr b.feature_conf g.FeatureConf g):
    ra se TypeError(f"Feature conf g must be of type contr b.FeatureConf g, currently of {type(feature_conf g)}")
  sparse_shape = _extract_hash_space_b s(feature_conf g)
   f not  s nstance(features, d ct):
    ra se TypeError(f"features must be of d ct onary type,    s of {type(features)} type")
  for key  n set(features) & set(sparse_shape):
    features[key] = l m _sparse_tensor_s ze(features[key], sparse_shape[key], mask_ nd ces=False)


def touch_f le_ n_d r(d rectory, f lena ):
  """
  Creates a f le na d f lena   n d rectory.

  Argu nts:
    f lena : (str)
    d rectory: (str)
  """
  f le_path = os.path.jo n(d rectory, f lena )
  w h tf. o.gf le.GF le(f le_path, "w") as f:
    f.wr e("")


def f le_ex st_ n_d r(d rectory: str, f lena : str) -> bool:
  f le_path = os.path.jo n(d rectory, f lena )
  return tf. o.gf le.ex sts(f le_path)


def copy_to_local(remote, local, f lena , overwr e=False):
  """Funct on to f le from remote d rectory to local d rectory."""
  assert "hdfs://" not  n local
  tf. o.gf le.maked rs(local)
  return tf. o.gf le.copy(
    os.path.jo n(remote, f lena ),
    os.path.jo n(local, f lena ),
    overwr e=overwr e,
  )


def copy_recurs ve(src, dst, overwr e=False):
  """
  Funct on to copy a d rectory recurs vely.

  Argu nts:
    src: S ce d rectory.
    dst: Dest nat on d rectory.
    overwr e: Spec f es  f f les are to be overwr ten  f t y ex st.
  """

  src = src.rstr p("/")
  dst = dst.rstr p("/")

  for d rna , subd rs, f les  n tf. o.gf le.walk(src):
    dst_d rna  = d rna .replace(src, dst)
    tf. o.gf le.maked rs(dst_d rna )

    for f  n f les:
      src_f = os.path.jo n(d rna , f)
      dst_f = os.path.jo n(dst_d rna , f)

      tf.logg ng. nfo(f"Copy ng {src_f} to {dst_f}")
      tf. o.gf le.copy(src_f, dst_f, overwr e=overwr e)


def delete_f le_or_d r(path):
  """
  Delete t  f le or d rectory g ven by `path`
  Argu nts:
    path:
      str ng  nd cat ng path of f le or d rectory to remove
  """
   f tf. o.gf le. sd r(path):
    tf. o.gf le.rmtree(path)
  else:
    tf. o.gf le.remove(path)


def get_d str buted_tra n ng_job_path():
  """
  Funct on to get d str buted tra n ng job path.
  Note: d str buted tra n ng has three jobs, one para ter server job,
  one worker job and one evaluator job. All of t se three jobs' na 
  share a common base job na .
  """
  job_path = AuroraPath(dc=os.env ron.get("TWML_JOB_CLUSTER"),
    role=os.env ron.get("TWML_JOB_ROLE"),
    env=os.env ron.get("TWML_JOB_ENV"),
    job_na =os.env ron.get("TWML_D STR BUTED_BASE_JOBNAME"))
  return job_path

def do_every_n_steps(act on, num_steps):
  """
  Execute a sequence of TensorFlow operat ons only once  n a wh le.
  Spec f cally, `act on`  s perfor d  f `global_step`  s a
    mult ple of `num_steps`

  Args:
    act on: callable to be perfor d at regular  ntervals. T  callable
      must return a TF op w h no output tensors.
    num_steps: per od of perform ng t  act on, as  asured
       n number of tra n ng steps

  Returns:
    A TensorFlow op w h no output tensors, l ke a tf.pr nt() or tf.no_op().
      must use tf.control_dependenc es() to execute t  op.

  """
  global_step = tf.tra n.get_or_create_global_step()
  cond  on = tf.math.equal(tf.math.floormod(global_step, num_steps), 0)
  return tf.cond(cond  on, act on, lambda: tf.no_op())
