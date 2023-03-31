' Importing the pyton op wrappers '
_A='OMP_NUM_THREADS'
import os
from twitter.deepbird.logging.log_level import set_logging_level
from twitter.deepbird.sparse import SparseTensor
from twitter.deepbird.sparse import sparse_dense_matmul
from .util import dynamic_partition,feature_id,limit_bits,limit_sparse_tensor_size
from .util import write_file,fixed_length_tensor,setup_tf_logging_formatter
from .array import Array
from .feature_config import FeatureConfig,FeatureConfigBuilder
from .dataset import *
from .readers import *
from .block_format_writer import *
from .export_output_fns import *
from .parsers import *
from .input_fns import *
from .filters import *
from .argument_parser import *
from .  import constants
from .  import errors
from .  import layers
from .  import lookup
from .  import readers
from .  import summary
from .  import tensorboard
import tensorflow.compat.v1 as tf
tf.disable_eager_execution()
if _A not in os.environ and'MKL_NUM_THREADS'not in os.environ:os.environ[_A]='1'
from libtwml import add1,partition_sparse_tensor,CLIB
set_logging_level('INFO')
from .  import contrib
from .  import hooks
from .  import trainers
from .  import metrics