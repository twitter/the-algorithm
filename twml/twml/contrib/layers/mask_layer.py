from twml.contr b.prun ng  mport apply_mask
from twml.layers  mport Layer


class MaskLayer(Layer):
  """
  T  layer corresponds to `twml.contr b.prun ng.apply_mask`.

    appl es a b nary mask to mask out channels of a g ven tensor. T  masks can be
  opt m zed us ng `twml.contr b.tra ners.Prun ngDataRecordTra ner`.
  """

  def call(self,  nputs, **kwargs):
    """
    Appl es a b nary mask to t  channels of t   nput.

    Argu nts:
       nputs:
         nput tensor
      **kwargs:
        add  onal keyword argu nts

    Returns:
      Masked tensor
    """
    return apply_mask( nputs)

  def compute_output_shape(self,  nput_shape):
    return  nput_shape
