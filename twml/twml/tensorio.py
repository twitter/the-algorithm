# pylint: disable=missing-docstring, bare-except, pointless-statement,
# pointless-string-statement, redundant-unittest-assert, no-else-return,
# no-member, old-style-class, dangerous-default-value, protected-access,
# too-few-public-methods

import os

import numpy as np
import yaml


"""
Utility to load tensors serialized by Deepbird V1.

Note that Deepbird V1 serialize tensor names as \"weight\".\'1\'.
For user-friendliness, the quotes are removed from the tensor names.
"""


# helper class used to assist hierarchical key access by remembering intermediate keys.
class _KeyRecorder(object):
  def __init__(self, tensorio, keys=[]):
    self.tensorio = tensorio
    self.keys = keys

  def __getitem__(self, k):
    new_keys = self.keys + [str(k)]
    prefix = ".".join(new_keys)

    key_list = self.tensorio.list_tensors()

    # if we have a complete key, load the tensor.
    if prefix in key_list:
      return self.tensorio._load(prefix)

    # we don't have a complete key yet, but at least one tensor should start with this prefix.
    for k_value in key_list:
      if k_value.startswith(prefix):
        return _KeyRecorder(self.tensorio, new_keys)

    # if no key starts with the prefix, this _key_recorder is not valid.
    raise ValueError("Key not found: " + prefix)


# convert tensorio tensor type to numpy data type.
# also returns element size in bytes.
def _get_data_type(data_type):
  if data_type == 'Double':
    return (np.float64, 8)

  if data_type == 'Float':
    return (np.float32, 4)

  if data_type == 'Int':
    return (np.int32, 4)

  if data_type == 'Long':
    return (np.int64, 8)

  if data_type == 'Byte':
    return (np.int8, 1)

  raise ValueError('Unexpected tensorio data type: ' + data_type)


class TensorIO(object):
  """
  Construct a TensorIO class.
  tensorio_path: a directory containing tensors serialized using tensorio. tar file not supported.
  mmap_tensor:
    By default, loaded tensors use mmap storage.
    Set this to false to not use mmap. Useful when loading multiple tensors.
  """

  def __init__(self, tensorio_path, mmap_tensor=True):
    self._tensorio_path = tensorio_path
    self._mmap_tensor = mmap_tensor

    # Make sure we can locate spec.yaml.
    yaml_file = os.path.join(tensorio_path, 'spec.yaml')
    if not os.path.exists(yaml_file):
      raise ValueError('Invalid tensorio path: no spec.yaml found.')

    # load spec.yaml.
    with open(yaml_file, 'r') as file_open:
      # Note that tensor names in the yaml are like this: \"weight\".\'1\'
      # For user-friendliness, we remove the quotes.
      _spec = yaml.safe_load(file_open)
      self._spec = {k.replace("'", '').replace('"', ''): v for (k, v) in _spec.items()}

  def list_tensors(self):
    """
    Returns a list of tensors saved in the given path.
    """
    return self._spec.keys()

  def _load_tensor(self, name):
    """
    Load Tensor with the given name.
    Raise value error if the named tensor is not found.
    Returns a numpy array if the named tensor is found.
    """
    tensor_info = self._spec[name]
    if tensor_info['type'] != 'tensor':
      raise ValueError('Trying to load a tensor of unknown type: ' + tensor_info['type'])

    filename = os.path.join(self._tensorio_path, tensor_info['filename'])
    (data_type, element_size) = _get_data_type(tensor_info['tensorType'])

    np_array = np.memmap(
      filename,
      dtype=data_type,
      mode='r',
      # -1 because lua offset is 1 based.
      offset=(tensor_info['offset'] - 1) * element_size,
      shape=tuple(tensor_info['size']),
      order='C',
    )

    return np_array if self._mmap_tensor else np_array[:].copy()

  def _load_nontensor_data(self, name):
    """
    Load non-tensor data with the given name.
    Returns a python string.
    """
    tensor_info = self._spec[name]
    return tensor_info['data']

  def _load(self, name):
    """
    Load data serialized under the given name, it could be a tensor or regular data.
    """
    if name not in self._spec:
      raise ValueError('The specified key {} is not found in {}'.format(name, self._tensorio_path))

    data_type = self._spec[name]['type']
    if data_type == 'tensor':
      return self._load_tensor(name)
    else:
      return self._load_nontensor_data(name)

  def load_all(self):
    """
    Load all tensors stored in the tensorio directory.
    Returns a dictionary from tensor name to numpy arrays.
    """
    return {k: self._load(k) for k in self._spec}

  ###########################################
  # The below are utilities for convenience #
  ###########################################
  def __getitem__(self, k):
    """
    Shorthand for _load_tensor, but also supports hierarchical access like: tensorio['a']['b']['1']
    """
    if k in self._spec:
      # We have a full tensor name, directly load it.
      return self._load_tensor(k)
    else:
      return _KeyRecorder(self)[k]
