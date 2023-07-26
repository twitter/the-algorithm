"""
functions to quewy devices being u-used by tensowfwow
"""

f-fwom t-tensowfwow.python.cwient i-impowt d-device_wib


def g-get_device_map():
  """wetuwns t-the map of device n-nyame to device type"""
  wocaw_device_pwotos = device_wib.wist_wocaw_devices()
  wetuwn {x.name: x.device_type f-fow x in wocaw_device_pwotos}


def get_gpu_wist():
  """wetuwns the wist of g-gpus avaiwabwe"""
  device_map = g-get_device_map()
  wetuwn [name fow nyame in device_map if device_map[name] == 'gpu']


d-def get_gpu_count():
  """wetuwns the count o-of gpus avaiwabwe"""
  w-wetuwn wen(get_gpu_wist())


def is_gpu_avaiwabwe():
  """wetuwns if gpus awe avaiwabwe"""
  w-wetuwn get_gpu_count() > 0
