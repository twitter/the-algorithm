""" Importing the pyton op wrappers """

import os

# Import from twitter.deepbird
from twitter.deepbird.logging.log_level import set_logging_level  # noqa: F401
from twitter.deepbird.sparse import SparseTensor  # noqa: F401
from twitter.deepbird.sparse import sparse_dense_matmul  # noqa: F401

from .util import dynamic_partition, feature_id, limit_bits, limit_sparse_tensor_size  # noqa: F401
from .util import write_file, fixed_length_tensor, setup_tf_logging_formatter  # noqa: F401
from .array import Array  # noqa: F401

# Module to parse feature patterns and match them from data_spec.json
from .feature_config import FeatureConfig, FeatureConfigBuilder  # noqa: F401

# Data record streaming, reading, writing, and parsing.
from .dataset import *  # noqa: T400
from .readers import *  # noqa: T400
from .block_format_writer import *  # noqa: T400

# Graph output functions
from .export_output_fns import *  # noqa: T400

# Input parsers
from .parsers import *  # noqa: T400

# Input functions
from .input_fns import *  # noqa: T400

# Feature filter functions
from .filters import *  # noqa: T400

# Custom argparser for Trainer
from .argument_parser import *  # noqa: T400

from . import constants  # noqa: F401
from . import errors  # noqa: F401
from . import layers  # noqa: F401
from . import lookup  # noqa: F401
from . import readers  # noqa: F401
from . import summary  # noqa: F401
from . import tensorboard  # noqa: F401

import tensorflow.compat.v1 as tf  # noqa: F402
tf.disable_eager_execution()

# TODO: Figure out a better way to deal with this.
if 'OMP_NUM_THREADS' not in os.environ and 'MKL_NUM_THREADS' not in os.environ:
  os.environ["OMP_NUM_THREADS"] = '1'

# Import all custom C++ ops
from libtwml import add1, partition_sparse_tensor, CLIB  # noqa: F401

# Configure logging levels to info for various frameworks
set_logging_level('INFO')

from . import contrib  # noqa: F401
from . import hooks  # noqa: F401
from . import trainers  # noqa: F401
from . import metrics  # noqa: F401
