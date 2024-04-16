"""
 mple nt ng Sequent al Layer conta ner
"""


from .layer  mport Layer

from tensorflow  mport keras
from tensorflow.python.layers  mport base


class Sequent al(Layer):
  """
  A sequent al stack of layers.

  Argu nts:
      layers: l st of layers to add to t  model.

  Output:
      t  output of t  sequent al layers
   """

  def __ n __(self, layers=None, **kwargs):
    self._layers = []  # Stack of layers.
    self._layer_na s = []  # Stack of layers na s
    self._layer_outputs = []
    # Add to t  model any layers passed to t  constructor.
     f layers:
      for layer  n layers:
        self.add(layer)
    super(Sequent al, self).__ n __(**kwargs)

  def add(self, layer):
    """Adds a layer  nstance on top of t  layer stack.

    Argu nts:
      layer:
        layer  nstance.

    Ra ses:
      TypeError:
         f t  layer argu nt  s not  nstance of base.Layer
    """
     f not  s nstance(layer, base.Layer) and not  s nstance(layer, keras.layers.Layer):
      ra se TypeError('T  added layer must be an  nstance of class Layer')

     f layer.na   n self._layer_na s:
      ra se ValueError('Layer w h na  %s already ex sts  n sequent al layer' % layer.na )

    self._layers.append(layer)
    self._layer_na s.append(layer.na )

  def pop(self):
    """Removes t  last layer  n t  model.

    Ra ses:
      TypeError:
         f t re are no layers  n t  model.
    """
     f not self._layers or not self._layer_na s:
      ra se TypeError('T re are no layers  n t  model.')
    self._layers.pop()
    self._layer_na s.pop()

  def call(self,  nputs, **kwargs):  # pyl nt: d sable=unused-argu nt
    """T  log c of t  layer l ves  re.

    Argu nts:
       nputs:
         nput tensor(s).

    Returns:
      T  output of t  sequent al layers
    """
    self._layer_outputs = []
    for layer  n self._layers:
      # don't use layer.call because   want to bu ld  nd v dual layers
       nputs = layer( nputs)  # overwr es t  current  nput after   has been processed
      self._layer_outputs.append( nputs)
    return  nputs

  @property
  def layers(self):
    """ Return t  layers  n t  sequent al layer """
    return self._layers

  @property
  def layer_na s(self):
    """ Return t  layer na s  n t  sequent al layer """
    return self._layer_na s

  @property
  def layer_outputs(self):
    """ Return t  layer outputs  n t  sequent al layer """
    return self._layer_outputs

  def get(self, key):
    """Retr eves t  n-th layer.

    Argu nts:
      key:
         ndex of t  layer

    Output:
      T  n-th layer w re n  s equal to t  key.
    """
    return self._layers[key]

  def get_output(self, key):
    """Retr eves t  n-th layer output.

    Argu nts:
      key:
         ndex of t  layer

    Output:
      T   nter d ary output equ valent to t  nth layer, w re n  s equal to t  key.
    """
    return self._layer_outputs[key]

  def get_layer_by_na (self, na ):
    """Retr eves t  layer correspond ng to t  na .

    Argu nts:
      na :
        na  of t  layer

    Output:
      l st of layers that have t  na  des red
    """
    return self._layers[self._layer_na s. ndex(na )]

  def get_layer_output_by_na (self, na ):
    """Retr eves t  layer output correspond ng to t  na .

    Argu nts:
      na :
        na  of t  layer

    Output:
      l st of t  output of t  layers that have t  des red na 
    """
    return self._layer_outputs[self._layer_na s. ndex(na )]

  @property
  def  n (self):
    """ returns a l st of  n  al zat on ops (one per layer) """
    return [layer. n  for layer  n self._layers]

  def compute_output_shape(self,  nput_shape):
    """Computes t  output shape of t  layer g ven t   nput shape.

    Args:
       nput_shape: A (poss bly nested tuple of) `TensorShape`.    need not
        be fully def ned (e.g. t  batch s ze may be unknown).

    Ra se Not mple ntedError.

    """
    ra se Not mple ntedError
