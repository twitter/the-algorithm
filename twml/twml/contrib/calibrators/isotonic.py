# pyl nt: d sable=argu nts-d ffer, unused-argu nt
''' Conta ns  soton c Cal brat on'''

from .cal brator  mport Cal brat onFeature, Cal brator

from absl  mport logg ng
 mport numpy as np
from sklearn. soton c  mport  soton c_regress on
 mport tensorflow.compat.v1 as tf
 mport tensorflow_hub as hub
 mport twml
 mport twml.layers


DEFAULT_SAMPLE_WE GHT = 1


def sort_values( nputs, target,   ght, ascend ng=True):
  '''
  Sorts arrays based on t  f rst array.

  Argu nts:
     nputs:
      1D array wh ch w ll d ctate t  order wh ch t  rema nder 2 arrays w ll be sorted
    target:
      1D array
      ght:
      1D array
    ascend ng:
      Boolean.  f set to True (t  default), sorts values  n ascend ng order.

  Returns:
    sorted  nputs:
      1D array sorted by t  order of `ascend ng`
    sorted targets:
      1D array
    sorted   ght:
      1D array
  '''
  # assert that t  length of  nputs and target are t  sa 
   f len( nputs) != len(target):
    ra se ValueError('Expect ng  nputs and target s zes to match')
   # assert that t  length of  nputs and   ght are t  sa 
   f len( nputs) != len(  ght):
    ra se ValueError('Expect ng  nputs and   ght s zes to match')
   nds =  nputs.argsort()
   f not ascend ng:
     nds =  nds[::-1]
  return  nputs[ nds], target[ nds],   ght[ nds]


class  soton cFeature(Cal brat onFeature):
  '''
   soton cFeature adds values,   ghts and targets to each feature and t n runs
   soton c regress on by call ng `sklearn. soton c. soton c_regress on
  <http://sc k -learn.org/stable/auto_examples/plot_ soton c_regress on.html>`_
  '''

  def _get_b n_boundar es(self, n_samples, b ns, s m lar_b ns):
    """
    Calculates t  sample  nd ces that def ne b n boundar es

    Argu nts:
      n_samples:
        ( nt) number of samples
      b ns:
        ( nt) number of b ns. Needs to be smaller or equal than n_samples.
      s m lar_b ns:
        (bool)  f True, samples w ll be d str buted  n b ns of equal s ze (up to one sample).
         f False b ns w ll be f lled w h step = N_samples//b ns, and last b n w ll conta n all rema n ng samples.
        Note that equal_b ns=False can create a last b ns w h a very large number of samples.

    Returns:
      (l st[ nt]) L st of sample  nd ces def n ng b n boundar es
    """

     f b ns > n_samples:
      ra se ValueError(
        "T  number of b ns needs to be less than or equal to t  number of samples. "
        "Currently b ns={0} and n_samples={1}.".format(b ns, n_samples)
      )

    step = n_samples // b ns

     f s m lar_b ns:
      # dtype= nt w ll floor t  l nspace
      b n_boundar es = np.l nspace(0, n_samples - step, num=b ns, dtype= nt)
    else:
      b n_boundar es = range(0, step * b ns, step)

    b n_boundar es = np.append(b n_boundar es, n_samples)

    return b n_boundar es

  def cal brate(self, b ns, s m lar_b ns=False, debug=False):
    '''Cal brates t   soton cFeature  nto cal brated   ghts and b as.

    1. Sorts t  values of t  feature class, based on t  order of values
    2. Performs  soton c regress on us ng sklearn. soton c. soton c_regress on
    3. Performs t  b nn ng of t  samples,  n order to obta n t  f nal   ght and b as
      wh ch w ll be used for  nference

    Note that t   thod can only be called once.

    Argu nts:
      b ns:
        number of b ns.
      s m lar_b ns:
         f True, samples w ll be d str buted  n b ns of equal s ze (up to one sample).
         f False b ns w ll be f lled w h step = N_samples//b ns, and last b n w ll conta n all rema n ng samples.
        Note that equal_b ns=False can create a last b ns w h a very large number of samples.
      debug:
        Defaults to False.  f debug  s set to true, output ot r para ters useful for debugg ng.

    Returns:
      [cal brated   ght, cal brated b as]
    '''
     f self._cal brated:
      ra se Runt  Error("Can only cal brate once")
    # parse through t  d ct to obta n t  targets,   ghts and values
    self._concat_arrays()
    feature_targets = self._features_d ct['targets']
    feature_values = self._features_d ct['values']
    feature_  ghts = self._features_d ct['  ghts']
    srtd_feature_values, srtd_feature_targets, srtd_feature_  ghts = sort_values(
       nputs=feature_values,
      target=feature_targets,
        ght=feature_  ghts
    )
    cal brated_feature_values =  soton c_regress on(
      srtd_feature_targets, sample_  ght=srtd_feature_  ghts)
    # create t  f nal outputs for t  pred ct on of each class
    bpreds = []
    btargets = []
    b  ghts = []
    rpreds = []

    # Create b n boundar es
    b n_boundar es = self._get_b n_boundar es(
      len(cal brated_feature_values), b ns, s m lar_b ns=s m lar_b ns)

    for s dx, e dx  n z p(b n_boundar es, b n_boundar es[1:]):
      # separate each one of t  arrays based on t  r respect ve b ns
      lpreds = srtd_feature_values[ nt(s dx): nt(e dx)]
      lrpreds = cal brated_feature_values[ nt(s dx): nt(e dx)]
      ltargets = srtd_feature_targets[ nt(s dx): nt(e dx)]
      l  ghts = srtd_feature_  ghts[ nt(s dx): nt(e dx)]

      # calculate t  outputs ( nclud ng t  bpreds and rpreds)
      bpreds.append(np.sum(lpreds * l  ghts) / (np.squeeze(np.sum(l  ghts))))
      rpreds.append(np.sum(lrpreds * l  ghts) / (np.squeeze(np.sum(l  ghts))))
      btargets.append(np.sum(ltargets * l  ghts) / (np.squeeze(np.sum(l  ghts))))
      b  ghts.append(np.squeeze(np.sum(l  ghts)))
    # transpos ng t  bpreds and rpreds wh ch w ll be used as  nput to t   nference step
    bpreds = np.asarray(bpreds).T
    rpreds = np.asarray(rpreds).T
    btargets = np.asarray(btargets).T
    b  ghts = np.asarray(b  ghts).T
    # sett ng _cal brated to be True wh ch  s necessary  n order to prevent   to re-cal brate
    self._cal brated = True
     f debug:
      return bpreds, rpreds, btargets, b  ghts
    return bpreds, rpreds


class  soton cCal brator(Cal brator):
  ''' Accumulates features and t  r respect ve values for  soton c cal brat on.
   nternally, each feature's values  s accumulated v a  s own  soton cFeature object.
  T  steps for cal brat on are typ cally as follows:

   1. accumulate feature values from batc s by call ng ``accumulate()``;
   2. cal brate all feature  nto  soton c ``bpreds``, ``rpreds`` by call ng ``cal brate()``; and
   3. convert to a ``twml.layers. soton c`` layer by call ng ``to_layer()``.

  '''

  def __ n __(self, n_b n, s m lar_b ns=False, **kwargs):
    ''' Constructs an  soton cCal brator  nstance.

    Argu nts:
      n_b n:
        t  number of b ns per feature to use for  soton c.
        Note that each feature actually maps to ``n_b n+1`` output  Ds.
    '''
    super( soton cCal brator, self).__ n __(**kwargs)
    self._n_b n = n_b n
    self._s m lar_b ns = s m lar_b ns
    self._ys_ nput = []
    self._xs_ nput = []
    self._ soton c_feature_d ct = {}

  def accumulate_feature(self, output):
    '''
    Wrapper around accumulate for tra ner AP .
    Argu nts:
      output: output of pred ct on of bu ld_graph for cal brator
    '''
      ghts = output['  ghts']  f '  ghts'  n output else None
    return self.accumulate(output['pred ct ons'], output['targets'],   ghts)

  def accumulate(self, pred ct ons, targets,   ghts=None):
    '''
    Accumulate a s ngle batch of class pred ct ons, class targets and class   ghts.
    T se are accumulated unt l cal brate()  s called.

    Argu nts:
      pred ct ons:
        float matr x of class values. Each d  ns on corresponds to a d fferent class.
        Shape  s ``[n, d]``, w re d  s t  number of classes.
      targets:
        float matr x of class targets. Each d  ns on corresponds to a d fferent class.
        Shape ``[n, d]``, w re d  s t  number of classes.
        ghts:
        Defaults to   ghts of 1.
        1D array conta n ng t    ghts of each pred ct on.
    '''
     f pred ct ons.shape != targets.shape:
      ra se ValueError(
        'Expect ng pred ct ons.shape == targets.shape, got %s and %s  nstead' %
        (str(pred ct ons.shape), str(targets.shape)))
     f   ghts  s not None:
       f   ghts.nd m != 1:
        ra se ValueError('Expect ng 1D   ght, got %dD  nstead' %   ghts.nd m)
      el f   ghts.s ze != pred ct ons.shape[0]:
        ra se ValueError(
          'Expect ng pred ct ons.shape[0] ==   ghts.s ze, got %d != %d  nstead' %
          (pred ct ons.shape[0],   ghts.s ze))
    #  erate through t  rows of pred ct ons and sets one class to each row
     f   ghts  s None:
        ghts = np.full(pred ct ons.shape[0], f ll_value=DEFAULT_SAMPLE_WE GHT)
    for class_key  n range(pred ct ons.shape[1]):
      # gets t  pred ct ons and targets for that class
      class_pred ct ons = pred ct ons[:, class_key]
      class_targets = targets[:, class_key]
       f class_key not  n self._ soton c_feature_d ct:
         soton c_feature =  soton cFeature(class_key)
        self._ soton c_feature_d ct[class_key] =  soton c_feature
      else:
         soton c_feature = self._ soton c_feature_d ct[class_key]
       soton c_feature.add_values({'values': class_pred ct ons, '  ghts':   ghts,
                                   'targets': class_targets})

  def cal brate(self, debug=False):
    '''
    Cal brates each  soton cFeature after accumulat on  s complete.
    Results are stored  n ``self._ys_ nput`` and ``self._xs_ nput``

    Argu nts:
      debug:
        Defaults to False.  f set to true, returns t  ``xs_ nput`` and ``ys_ nput``.
    '''
    super( soton cCal brator, self).cal brate()
    b as_temp = []
      ght_temp = []
    logg ng. nfo("Beg nn ng  soton c cal brat on.")
     soton c_features_d ct = self._ soton c_feature_d ct
    for class_ d  n  soton c_features_d ct:
      bpreds, rpreds =  soton c_features_d ct[class_ d].cal brate(b ns=self._n_b n, s m lar_b ns=self._s m lar_b ns)
        ght_temp.append(bpreds)
      b as_temp.append(rpreds)
    # save  soton c results onto a matr x
    self._xs_ nput = np.array(  ght_temp, dtype=np.float32)
    self._ys_ nput = np.array(b as_temp, dtype=np.float32)
    logg ng. nfo(" soton c cal brat on f n s d.")
     f debug:
      return np.array(  ght_temp), np.array(b as_temp)
    return None

  def save(self, save_d r, na ="default", verbose=False):
    '''Save t  cal brator  nto t  g ven save_d rectory.
    Argu nts:
      save_d r:
        na  of t  sav ng d rectory. Default (str ng): "default".
    '''
     f not self._cal brated:
      ra se Runt  Error("Expect ng pr or call to cal brate().Cannot save() pr or to cal brate()")

    # T  module allows for t  cal brator to save be saved as part of
    # Tensorflow Hub (t  w ll allow   to be used  n furt r steps)
    logg ng. nfo("  probably do not need to save t   soton c layer. \
                  So feel free to set save to False  n t  Tra ner. \
                  Add  onally t  only saves t  layer not t  whole graph.")

    def cal brator_module():
      '''
      Way to save  soton c layer
      '''
      # T   nput to  soton c  s a dense layer
       nputs = tf.placeholder(tf.float32)
      cal brator_layer = self.to_layer()
      output = cal brator_layer( nputs)
      # creates t  s gnature to t  cal brator module
      hub.add_s gnature( nputs= nputs, outputs=output, na =na )

    # exports t  module to t  save_d r
    spec = hub.create_module_spec(cal brator_module)
    w h tf.Graph().as_default():
      module = hub.Module(spec)
      w h tf.Sess on() as sess on:
        module.export(save_d r, sess on)

  def to_layer(self):
    """ Returns a twml.layers. soton c Layer that can be used for feature d scret zat on.
    """
     f not self._cal brated:
      ra se Runt  Error("Expect ng pr or call to cal brate()")

     soton c_layer = twml.layers. soton c(
      n_un =self._xs_ nput.shape[0], n_b n=self._xs_ nput.shape[1],
      xs_ nput=self._xs_ nput, ys_ nput=self._ys_ nput,
      **self._kwargs)

    return  soton c_layer

  def get_layer_args(self, na =None):
    """ Returns layer args. See ``Cal brator.get_layer_args`` for more deta led docu ntat on """
    return {'n_un ': self._xs_ nput.shape[0], 'n_b n': self._xs_ nput.shape[1]}
