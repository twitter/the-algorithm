"""
Parameters used in ClemNet.
"""
from typing import List, Optional

from pydantic import BaseModel, Extra, Field, PositiveInt


# checkstyle: noqa


class ExtendedBaseModel(BaseModel):
  class Config:
    extra = Extra.forbid


class DenseParams(ExtendedBaseModel):
  name: Optional[str]
  bias_initializer: str = "zeros"
  kernel_initializer: str = "glorot_uniform"
  output_size: PositiveInt
  use_bias: bool = Field(True)


class ConvParams(ExtendedBaseModel):
  name: Optional[str]
  bias_initializer: str = "zeros"
  filters: PositiveInt
  kernel_initializer: str = "glorot_uniform"
  kernel_size: PositiveInt
  padding: str = "SAME"
  strides: PositiveInt = 1
  use_bias: bool = Field(True)


class BlockParams(ExtendedBaseModel):
  activation: Optional[str]
  conv: Optional[ConvParams]
  dense: Optional[DenseParams]
  residual: Optional[bool]


class TopLayerParams(ExtendedBaseModel):
  n_labels: PositiveInt


class ClemNetParams(ExtendedBaseModel):
  blocks: List[BlockParams] = []
  top: Optional[TopLayerParams]
