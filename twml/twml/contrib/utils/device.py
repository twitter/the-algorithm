"""
Funct ons to query dev ces be ng used by tensorflow
"""

from tensorflow.python.cl ent  mport dev ce_l b


def get_dev ce_map():
  """Returns t  map of dev ce na  to dev ce type"""
  local_dev ce_protos = dev ce_l b.l st_local_dev ces()
  return {x.na : x.dev ce_type for x  n local_dev ce_protos}


def get_gpu_l st():
  """Returns t  l st of GPUs ava lable"""
  dev ce_map = get_dev ce_map()
  return [na  for na   n dev ce_map  f dev ce_map[na ] == 'GPU']


def get_gpu_count():
  """Returns t  count of GPUs ava lable"""
  return len(get_gpu_l st())


def  s_gpu_ava lable():
  """Returns  f GPUs are ava lable"""
  return get_gpu_count() > 0
