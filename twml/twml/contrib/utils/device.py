"""
Functions to query devices being used by tensorflow
"""

from tensorflow.python.client import device_lib


def get_device_map():
  """Returns the map of device name to device type"""
  local_device_protos = device_lib.list_local_devices()
  return {x.name: x.device_type for x in local_device_protos}


def get_gpu_list():
  """Returns the list of GPUs available"""
  device_map = get_device_map()
  return [name for name in device_map if device_map[name] == 'GPU']


def get_gpu_count():
  """Returns the count of GPUs available"""
  return len(get_gpu_list())


def is_gpu_available():
  """Returns if GPUs are available"""
  return get_gpu_count() > 0
