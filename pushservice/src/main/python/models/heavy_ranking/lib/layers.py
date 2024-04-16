"""
D fferent type of convolut on layers to be used  n t  ClemNet.
"""
from typ ng  mport Any

 mport tensorflow as tf


class KerasConv1D(tf.keras.layers.Layer):
  """
  Bas c Conv1D layer  n a wrapper to be compat ble w h ClemNet.
  """

  def __ n __(
    self,
    kernel_s ze:  nt,
    f lters:  nt,
    str des:  nt,
    padd ng: str,
    use_b as: bool = True,
    kernel_ n  al zer: str = "glorot_un form",
    b as_ n  al zer: str = "zeros",
    **kwargs: Any,
  ):
    super(KerasConv1D, self).__ n __(**kwargs)
    self.kernel_s ze = kernel_s ze
    self.f lters = f lters
    self.use_b as = use_b as
    self.kernel_ n  al zer = kernel_ n  al zer
    self.b as_ n  al zer = b as_ n  al zer
    self.str des = str des
    self.padd ng = padd ng

  def bu ld(self,  nput_shape: tf.TensorShape) -> None:
    assert (
      len( nput_shape) == 3
    ), f"Tensor shape must be of length 3. Passed tensor of shape { nput_shape}."

    self.features =  nput_shape[1]

    self.w = tf.keras.layers.Conv1D(
      kernel_s ze=self.kernel_s ze,
      f lters=self.f lters,
      str des=self.str des,
      padd ng=self.padd ng,
      use_b as=self.use_b as,
      kernel_ n  al zer=self.kernel_ n  al zer,
      b as_ n  al zer=self.b as_ n  al zer,
      na =self.na ,
    )

  def call(self,  nputs: tf.Tensor, **kwargs: Any) -> tf.Tensor:
    return self.w( nputs)


class ChannelW seDense(tf.keras.layers.Layer):
  """
  Dense layer  s appl ed to each channel separately. T   s more  mory and computat onally
  eff c ent than flatten ng t  channels and perform ng s ngle dense layers over   wh ch  s t 
  default behav or  n tf1.
  """

  def __ n __(
    self,
    output_s ze:  nt,
    use_b as: bool,
    kernel_ n  al zer: str = "un form_glorot",
    b as_ n  al zer: str = "zeros",
    **kwargs: Any,
  ):
    super(ChannelW seDense, self).__ n __(**kwargs)
    self.output_s ze = output_s ze
    self.use_b as = use_b as
    self.kernel_ n  al zer = kernel_ n  al zer
    self.b as_ n  al zer = b as_ n  al zer

  def bu ld(self,  nput_shape: tf.TensorShape) -> None:
    assert (
      len( nput_shape) == 3
    ), f"Tensor shape must be of length 3. Passed tensor of shape { nput_shape}."

     nput_s ze =  nput_shape[1]
    channels =  nput_shape[2]

    self.kernel = self.add_  ght(
      na ="kernel",
      shape=(channels,  nput_s ze, self.output_s ze),
       n  al zer=self.kernel_ n  al zer,
      tra nable=True,
    )

    self.b as = self.add_  ght(
      na ="b as",
      shape=(channels, self.output_s ze),
       n  al zer=self.b as_ n  al zer,
      tra nable=self.use_b as,
    )

  def call(self,  nputs: tf.Tensor, **kwargs: Any) -> tf.Tensor:
    x =  nputs

    transposed_x = tf.transpose(x, perm=[2, 0, 1])
    transposed_res dual = (
      tf.transpose(tf.matmul(transposed_x, self.kernel), perm=[1, 0, 2]) + self.b as
    )
    output = tf.transpose(transposed_res dual, perm=[0, 2, 1])

    return output


class Res dualLayer(tf.keras.layers.Layer):
  """
  Layer  mple nt ng a 3D-res dual connect on.
  """

  def bu ld(self,  nput_shape: tf.TensorShape) -> None:
    assert (
      len( nput_shape) == 3
    ), f"Tensor shape must be of length 3. Passed tensor of shape { nput_shape}."

  def call(self,  nputs: tf.Tensor, res dual: tf.Tensor, **kwargs: Any) -> tf.Tensor:
    shortcut = tf.keras.layers.Conv1D(
      f lters= nt(res dual.shape[2]), str des=1, kernel_s ze=1, padd ng="SAME", use_b as=False
    )( nputs)

    output = tf.add(shortcut, res dual)

    return output
