# pyl nt: d sable=m ss ng-docstr ng, unused-argu nt
''' Conta ns t  base classes for Cal brat onFeature and Cal brator '''


from collect ons  mport defaultd ct

 mport numpy as np
 mport tensorflow.compat.v1 as tf
 mport tensorflow_hub as hub
 mport twml
 mport twml.ut l


class Cal brat onFeature(object):
  '''
  Accumulates values and   ghts for  nd v dual features.
  Typ cally, each un que feature def ned  n t  accumulated SparseTensor or Tensor
  would have  s own Cal brat onFeature  nstance.
  '''

  def __ n __(self, feature_ d):
    ''' Constructs a Cal brat onFeature

    Argu nts:
      feature_ d:
        number  dent fy ng t  feature.
    '''
    self.feature_ d = feature_ d
    self._cal brated = False
    self._features_d ct = defaultd ct(l st)

  def add_values(self, new_features):
    '''
    Extends l sts to conta n t  values  n t  batch
    '''
    for key  n new_features:
      self._features_d ct[key].append(new_features[key])

  def _concat_arrays(self):
    '''
    T  class calls t  funct on after   have added all t  values.
      creates a d ct onary w h t  concatanated arrays
    '''
    self._features_d ct.update((k, np.concatenate(v)) for k, v  n self._features_d ct. ems())

  def cal brate(self, *args, **kwargs):
    ra se Not mple ntedError


class Cal brator(object):
  '''
  Accumulates features and t  r respect ve values for Cal brat on
  T  steps for cal brat on are typ cally as follows:

   1. accumulate feature values from batc s by call ng ``accumulate()`` and;
   2. cal brate by call ng ``cal brate()``;
   3. convert to a twml.layers layer by call ng ``to_layer()``.

  Note   can only use one cal brator per Tra ner.
  '''

  def __ n __(self, cal brator_na =None, **kwargs):
    '''
    Argu nts:
      cal brator_na .
        Default:  f set to None   w ll be t  sa  as t  class na .
        Please be rem nded that  f  n t  model t re are many cal brators
        of t  sa  type t  cal brator_na  should be changed to avo d confus on.
    '''
    self._cal brated = False
     f cal brator_na   s None:
      cal brator_na  = twml.ut l.to_snake_case(self.__class__.__na __)
    self._cal brator_na  = cal brator_na 
    self._kwargs = kwargs

  @property
  def  s_cal brated(self):
    return self._cal brated

  @property
  def na (self):
    return self._cal brator_na 

  def accumulate(self, *args, **kwargs):
    '''Accumulates features and t  r respect ve values for Cal brat on.'''
    ra se Not mple ntedError

  def cal brate(self):
    '''Cal brates after t  accumulat on has ended.'''
    self._cal brated = True

  def to_layer(self, na =None):
    '''
    Returns a twml.layers.Layer  nstance w h t  result of cal brator.

    Argu nts:
      na :
        na -scope of t  layer
    '''
    ra se Not mple ntedError

  def get_layer_args(self):
    '''
    Returns layer argu nts requ red to  mple nt mult -phase tra n ng.

    Returns:
      d ct onary of Layer constructor argu nts to  n  al ze t 
      layer Var ables. Typ cally, t  should conta n enough  nformat on
      to  n  al ze empty layer Var ables of t  correct s ze, wh ch w ll t n
      be f lled w h t  r ght data us ng  n _map.
    '''
    ra se Not mple ntedError

  def save(self, save_d r, na ="default", verbose=False):
    '''Save t  cal brator  nto t  g ven save_d rectory.
    Argu nts:
      save_d r:
        na  of t  sav ng d rectory. Default (str ng): "default".
      na :
        na  for t  cal brator.
    '''
     f not self._cal brated:
      ra se Runt  Error("Expect ng pr or call to cal brate().Cannot save() pr or to cal brate()")

    # T  module allows for t  cal brator to save be saved as part of
    # Tensorflow Hub (t  w ll allow   to be used  n furt r steps)
    def cal brator_module():
      # Note that t   s usually expect ng a sparse_placeholder
       nputs = tf.sparse_placeholder(tf.float32)
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

  def wr e_summary(self, wr er, sess=None):
    """
    T   thod  s called by save() to wr e tensorboard summar es to d sk.
    See MDLCal brator.wr e_summary for an example.
    By default, t   thod does noth ng.   can be overloaded by ch ld-classes.

    Argu nts:
      wr er:
        `tf.summary.F lteWr er
        <https://www.tensorflow.org/vers ons/master/ap _docs/python/tf/summary/F leWr er>`_
         nstance.
        T  ``wr er``  s used to add summar es to event f les for  nclus on  n tensorboard.
      sess (opt onal):
        `tf.Sess on <https://www.tensorflow.org/vers ons/master/ap _docs/python/tf/Sess on>`_
         nstance. T  ``sess``  s used to produces summar es for t  wr er.
    """
