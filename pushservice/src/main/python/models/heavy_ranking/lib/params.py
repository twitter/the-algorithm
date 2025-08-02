"""
Parameters used in ClemNet.
"""
from typing import List, Optional

from pydantic import BaseModel, Etwittertra, Field, PositiveInt


# checkstyle: noqa


class EtwittertendedBaseModel(BaseModel):
  class Config:
    etwittertra = Etwittertra.forbid


class DenseParams(EtwittertendedBaseModel):
  name: Optional[str]
  bias_initializer: str = "zeros"
  kernel_initializer: str = "glorot_uniform"
  output_size: PositiveInt
  use_bias: bool = Field(True)


class ConvParams(EtwittertendedBaseModel):
  name: Optional[str]
  bias_initializer: str = "zeros"
  filters: PositiveInt
  kernel_initializer: str = "glorot_uniform"
  kernel_size: PositiveInt
  padding: str = "SAME"
  strides: PositiveInt = 1
  use_bias: bool = Field(True)


class BlockParams(EtwittertendedBaseModel):
  activation: Optional[str]
  conv: Optional[ConvParams]
  dense: Optional[DenseParams]
  residual: Optional[bool]


class TopLayerParams(EtwittertendedBaseModel):
  n_labels: PositiveInt


class ClemNetParams(EtwittertendedBaseModel):
  blocks: List[BlockParams] = []
  top: Optional[TopLayerParams]
