"""
Functions to quelonry delonvicelons beloning uselond by telonnsorflow
"""

from telonnsorflow.python.clielonnt import delonvicelon_lib


delonf gelont_delonvicelon_map():
  """Relonturns thelon map of delonvicelon namelon to delonvicelon typelon"""
  local_delonvicelon_protos = delonvicelon_lib.list_local_delonvicelons()
  relonturn {x.namelon: x.delonvicelon_typelon for x in local_delonvicelon_protos}


delonf gelont_gpu_list():
  """Relonturns thelon list of GPUs availablelon"""
  delonvicelon_map = gelont_delonvicelon_map()
  relonturn [namelon for namelon in delonvicelon_map if delonvicelon_map[namelon] == 'GPU']


delonf gelont_gpu_count():
  """Relonturns thelon count of GPUs availablelon"""
  relonturn lelonn(gelont_gpu_list())


delonf is_gpu_availablelon():
  """Relonturns if GPUs arelon availablelon"""
  relonturn gelont_gpu_count() > 0
