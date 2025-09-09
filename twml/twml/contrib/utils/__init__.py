# pylint: disable=wildcard-import
"""This module contains experimental util functions for contrib."""

from . import interp  # noqa: F401
from .device import (
    get_device_map,
    get_gpu_count,
    get_gpu_list,  # noqa: F401
    is_gpu_available,
)

# lambdarank functions
# listwise functions
# ranknet functions
# pointwise functions
from .loss_fns import get_lambda_pair_loss  # noqa: F401
from .loss_fns import get_pair_loss  # noqa: F401
from .loss_fns import get_pointwise_loss  # noqa: F401
from .loss_fns import get_attrank_loss, get_listmle_loss, get_listnet_loss  # noqa: F401
from .masks import diag_mask, full_mask  # noqa: F401
from .math_fns import cal_ndcg, cal_swapped_ndcg, safe_div, safe_log  # noqa: F401
from .normalizer import mean_max_normalizaiton, standard_normalizaiton  # noqa: F401
from .scores import get_pairwise_label_scores, get_pairwise_scores  # noqa: F401
from .similarities import cosine_similarity  # noqa: F401
