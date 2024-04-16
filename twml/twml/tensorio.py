# pyl nt: d sable=m ss ng-docstr ng, bare-except, po ntless-state nt,
# po ntless-str ng-state nt, redundant-un test-assert, no-else-return,
# no- mber, old-style-class, dangerous-default-value, protected-access,
# too-few-publ c- thods

 mport os

 mport numpy as np
 mport yaml


"""
Ut l y to load tensors ser al zed by Deepb rd V1.

Note that Deepb rd V1 ser al ze tensor na s as \"  ght\".\'1\'.
For user-fr endl ness, t  quotes are removed from t  tensor na s.
"""


#  lper class used to ass st h erarch cal key access by re mber ng  nter d ate keys.
class _KeyRecorder(object):
  def __ n __(self, tensor o, keys=[]):
    self.tensor o = tensor o
    self.keys = keys

  def __get em__(self, k):
    new_keys = self.keys + [str(k)]
    pref x = ".".jo n(new_keys)

    key_l st = self.tensor o.l st_tensors()

    #  f   have a complete key, load t  tensor.
     f pref x  n key_l st:
      return self.tensor o._load(pref x)

    #   don't have a complete key yet, but at least one tensor should start w h t  pref x.
    for k_value  n key_l st:
       f k_value.startsw h(pref x):
        return _KeyRecorder(self.tensor o, new_keys)

    #  f no key starts w h t  pref x, t  _key_recorder  s not val d.
    ra se ValueError("Key not found: " + pref x)


# convert tensor o tensor type to numpy data type.
# also returns ele nt s ze  n bytes.
def _get_data_type(data_type):
   f data_type == 'Double':
    return (np.float64, 8)

   f data_type == 'Float':
    return (np.float32, 4)

   f data_type == ' nt':
    return (np. nt32, 4)

   f data_type == 'Long':
    return (np. nt64, 8)

   f data_type == 'Byte':
    return (np. nt8, 1)

  ra se ValueError('Unexpected tensor o data type: ' + data_type)


class Tensor O(object):
  """
  Construct a Tensor O class.
  tensor o_path: a d rectory conta n ng tensors ser al zed us ng tensor o. tar f le not supported.
  mmap_tensor:
    By default, loaded tensors use mmap storage.
    Set t  to false to not use mmap. Useful w n load ng mult ple tensors.
  """

  def __ n __(self, tensor o_path, mmap_tensor=True):
    self._tensor o_path = tensor o_path
    self._mmap_tensor = mmap_tensor

    # Make sure   can locate spec.yaml.
    yaml_f le = os.path.jo n(tensor o_path, 'spec.yaml')
     f not os.path.ex sts(yaml_f le):
      ra se ValueError(' nval d tensor o path: no spec.yaml found.')

    # load spec.yaml.
    w h open(yaml_f le, 'r') as f le_open:
      # Note that tensor na s  n t  yaml are l ke t : \"  ght\".\'1\'
      # For user-fr endl ness,   remove t  quotes.
      _spec = yaml.safe_load(f le_open)
      self._spec = {k.replace("'", '').replace('"', ''): v for (k, v)  n _spec. ems()}

  def l st_tensors(self):
    """
    Returns a l st of tensors saved  n t  g ven path.
    """
    return self._spec.keys()

  def _load_tensor(self, na ):
    """
    Load Tensor w h t  g ven na .
    Ra se value error  f t  na d tensor  s not found.
    Returns a numpy array  f t  na d tensor  s found.
    """
    tensor_ nfo = self._spec[na ]
     f tensor_ nfo['type'] != 'tensor':
      ra se ValueError('Try ng to load a tensor of unknown type: ' + tensor_ nfo['type'])

    f lena  = os.path.jo n(self._tensor o_path, tensor_ nfo['f lena '])
    (data_type, ele nt_s ze) = _get_data_type(tensor_ nfo['tensorType'])

    np_array = np. mmap(
      f lena ,
      dtype=data_type,
      mode='r',
      # -1 because lua offset  s 1 based.
      offset=(tensor_ nfo['offset'] - 1) * ele nt_s ze,
      shape=tuple(tensor_ nfo['s ze']),
      order='C',
    )

    return np_array  f self._mmap_tensor else np_array[:].copy()

  def _load_nontensor_data(self, na ):
    """
    Load non-tensor data w h t  g ven na .
    Returns a python str ng.
    """
    tensor_ nfo = self._spec[na ]
    return tensor_ nfo['data']

  def _load(self, na ):
    """
    Load data ser al zed under t  g ven na ,   could be a tensor or regular data.
    """
     f na  not  n self._spec:
      ra se ValueError('T  spec f ed key {}  s not found  n {}'.format(na , self._tensor o_path))

    data_type = self._spec[na ]['type']
     f data_type == 'tensor':
      return self._load_tensor(na )
    else:
      return self._load_nontensor_data(na )

  def load_all(self):
    """
    Load all tensors stored  n t  tensor o d rectory.
    Returns a d ct onary from tensor na  to numpy arrays.
    """
    return {k: self._load(k) for k  n self._spec}

  ###########################################
  # T  below are ut l  es for conven ence #
  ###########################################
  def __get em__(self, k):
    """
    Shorthand for _load_tensor, but also supports h erarch cal access l ke: tensor o['a']['b']['1']
    """
     f k  n self._spec:
      #   have a full tensor na , d rectly load  .
      return self._load_tensor(k)
    else:
      return _KeyRecorder(self)[k]
