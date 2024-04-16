"""
Module conta n ng ClemNet.
"""
from typ ng  mport Any

from .layers  mport ChannelW seDense, KerasConv1D, Res dualLayer
from .params  mport BlockParams, ClemNetParams

 mport tensorflow as tf
 mport tensorflow.compat.v1 as tf1


class Block2(tf.keras.layers.Layer):
  """
  Poss ble ClemNet block. Arch ecture  s as follow:
    Opt onal(DenseLayer + BN + Act)
    Opt onal(ConvLayer + BN + Act)
    Opt onal(Res dual Layer)

  """

  def __ n __(self, params: BlockParams, **kwargs: Any):
    super(Block2, self).__ n __(**kwargs)
    self.params = params

  def bu ld(self,  nput_shape: tf.TensorShape) -> None:
    assert (
      len( nput_shape) == 3
    ), f"Tensor shape must be of length 3. Passed tensor of shape { nput_shape}."

  def call(self,  nputs: tf.Tensor, tra n ng: bool) -> tf.Tensor:
    x =  nputs
     f self.params.dense:
      x = ChannelW seDense(**self.params.dense.d ct())( nputs=x, tra n ng=tra n ng)
      x = tf1.layers.batch_normal zat on(x, mo ntum=0.9999, tra n ng=tra n ng, ax s=1)
      x = tf.keras.layers.Act vat on(self.params.act vat on)(x)

     f self.params.conv:
      x = KerasConv1D(**self.params.conv.d ct())( nputs=x, tra n ng=tra n ng)
      x = tf1.layers.batch_normal zat on(x, mo ntum=0.9999, tra n ng=tra n ng, ax s=1)
      x = tf.keras.layers.Act vat on(self.params.act vat on)(x)

     f self.params.res dual:
      x = Res dualLayer()( nputs= nputs, res dual=x)

    return x


class ClemNet(tf.keras.layers.Layer):
  """
  A res dual network stack ng res dual blocks composed of dense layers and convolut ons.
  """

  def __ n __(self, params: ClemNetParams, **kwargs: Any):
    super(ClemNet, self).__ n __(**kwargs)
    self.params = params

  def bu ld(self,  nput_shape: tf.TensorShape) -> None:
    assert len( nput_shape)  n (
      2,
      3,
    ), f"Tensor shape must be of length 3. Passed tensor of shape { nput_shape}."

  def call(self,  nputs: tf.Tensor, tra n ng: bool) -> tf.Tensor:
     f len( nputs.shape) < 3:
       nputs = tf.expand_d ms( nputs, ax s=-1)

    x =  nputs
    for block_params  n self.params.blocks:
      x = Block2(block_params)( nputs=x, tra n ng=tra n ng)

    x = tf.keras.layers.Flatten(na ="flattened")(x)
     f self.params.top:
      x = tf.keras.layers.Dense(un s=self.params.top.n_labels, na ="log s")(x)

    return x
