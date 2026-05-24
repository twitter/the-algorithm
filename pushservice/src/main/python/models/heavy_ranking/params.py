import enum
import json
from typing import List, Optional

from .lib.params import BlockParams, ClemNetParams, ConvParams, DenseParams, TopLayerParams

from pydantic import BaseModel, Extra, NonNegativeFloat
import tensorflow.compat.v1 as tf


# checkstyle: noqa


class ExtendedBaseModel(BaseModel):
  class Config:
    extra = Extra.forbid


class SparseFeaturesParams(ExtendedBaseModel):
  bits: int
  embedding_size: int


class FeaturesParams(ExtendedBaseModel):
  sparse_features: Optional[SparseFeaturesParams]


class ModelTypeEnum(str, enum.Enum):
  clemnet: str = "clemnet"


class ModelParams(ExtendedBaseModel):
  name: ModelTypeEnum
  features: FeaturesParams
  architecture: ClemNetParams


class TaskNameEnum(str, enum.Enum):
  oonc: str = "OONC"
  engagement: str = "Engagement"


class Task(ExtendedBaseModel):
  name: TaskNameEnum
  label: str
  score_weight: NonNegativeFloat


DEFAULT_TASKS = [
  Task(name=TaskNameEnum.oonc, label="label", score_weight=0.9),
  Task(name=TaskNameEnum.engagement, label="label.engagement", score_weight=0.1),
]


class GraphParams(ExtendedBaseModel):
  tasks: List[Task] = DEFAULT_TASKS
  model: ModelParams
  weight: Optional[str]


DEFAULT_ARCHITECTURE_PARAMS = ClemNetParams(
  blocks=[
    BlockParams(
      activation="relu",
      conv=ConvParams(kernel_size=3, filters=5),
      dense=DenseParams(output_size=output_size),
      residual=False,
    )
    for output_size in [1024, 512, 256, 128]
  ],
  top=TopLayerParams(n_labels=1),
)

DEFAULT_GRAPH_PARAMS = GraphParams(
  model=ModelParams(
    name=ModelTypeEnum.clemnet,
    architecture=DEFAULT_ARCHITECTURE_PARAMS,
    features=FeaturesParams(sparse_features=SparseFeaturesParams(bits=18, embedding_size=50)),
  ),
)


def load_graph_params(args) -> GraphParams:
  params = DEFAULT_GRAPH_PARAMS
  if args.param_file:
    with tf.io.gfile.GFile(args.param_file, mode="r+") as file:
      params = GraphParams.parse_obj(json.load(file))

  return params
