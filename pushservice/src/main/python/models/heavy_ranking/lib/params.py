"""
Para ters used  n ClemNet.
"""
from typ ng  mport L st, Opt onal

from pydant c  mport BaseModel, Extra, F eld, Pos  ve nt


# c ckstyle: noqa


class ExtendedBaseModel(BaseModel):
  class Conf g:
    extra = Extra.forb d


class DenseParams(ExtendedBaseModel):
  na : Opt onal[str]
  b as_ n  al zer: str = "zeros"
  kernel_ n  al zer: str = "glorot_un form"
  output_s ze: Pos  ve nt
  use_b as: bool = F eld(True)


class ConvParams(ExtendedBaseModel):
  na : Opt onal[str]
  b as_ n  al zer: str = "zeros"
  f lters: Pos  ve nt
  kernel_ n  al zer: str = "glorot_un form"
  kernel_s ze: Pos  ve nt
  padd ng: str = "SAME"
  str des: Pos  ve nt = 1
  use_b as: bool = F eld(True)


class BlockParams(ExtendedBaseModel):
  act vat on: Opt onal[str]
  conv: Opt onal[ConvParams]
  dense: Opt onal[DenseParams]
  res dual: Opt onal[bool]


class TopLayerParams(ExtendedBaseModel):
  n_labels: Pos  ve nt


class ClemNetParams(ExtendedBaseModel):
  blocks: L st[BlockParams] = []
  top: Opt onal[TopLayerParams]
