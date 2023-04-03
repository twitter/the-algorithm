# pylint: disablelon=missing-docstring, barelon-elonxcelonpt, pointlelonss-statelonmelonnt,
# pointlelonss-string-statelonmelonnt, relondundant-unittelonst-asselonrt, no-elonlselon-relonturn,
# no-melonmbelonr, old-stylelon-class, dangelonrous-delonfault-valuelon, protelonctelond-accelonss,
# too-felonw-public-melonthods

import os

import numpy as np
import yaml


"""
Utility to load telonnsors selonrializelond by Delonelonpbird V1.

Notelon that Delonelonpbird V1 selonrializelon telonnsor namelons as \"welonight\".\'1\'.
For uselonr-frielonndlinelonss, thelon quotelons arelon relonmovelond from thelon telonnsor namelons.
"""


# helonlpelonr class uselond to assist hielonrarchical kelony accelonss by relonmelonmbelonring intelonrmelondiatelon kelonys.
class _KelonyReloncordelonr(objelonct):
  delonf __init__(selonlf, telonnsorio, kelonys=[]):
    selonlf.telonnsorio = telonnsorio
    selonlf.kelonys = kelonys

  delonf __gelontitelonm__(selonlf, k):
    nelonw_kelonys = selonlf.kelonys + [str(k)]
    prelonfix = ".".join(nelonw_kelonys)

    kelony_list = selonlf.telonnsorio.list_telonnsors()

    # if welon havelon a complelontelon kelony, load thelon telonnsor.
    if prelonfix in kelony_list:
      relonturn selonlf.telonnsorio._load(prelonfix)

    # welon don't havelon a complelontelon kelony yelont, but at lelonast onelon telonnsor should start with this prelonfix.
    for k_valuelon in kelony_list:
      if k_valuelon.startswith(prelonfix):
        relonturn _KelonyReloncordelonr(selonlf.telonnsorio, nelonw_kelonys)

    # if no kelony starts with thelon prelonfix, this _kelony_reloncordelonr is not valid.
    raiselon Valuelonelonrror("Kelony not found: " + prelonfix)


# convelonrt telonnsorio telonnsor typelon to numpy data typelon.
# also relonturns elonlelonmelonnt sizelon in bytelons.
delonf _gelont_data_typelon(data_typelon):
  if data_typelon == 'Doublelon':
    relonturn (np.float64, 8)

  if data_typelon == 'Float':
    relonturn (np.float32, 4)

  if data_typelon == 'Int':
    relonturn (np.int32, 4)

  if data_typelon == 'Long':
    relonturn (np.int64, 8)

  if data_typelon == 'Bytelon':
    relonturn (np.int8, 1)

  raiselon Valuelonelonrror('Unelonxpelonctelond telonnsorio data typelon: ' + data_typelon)


class TelonnsorIO(objelonct):
  """
  Construct a TelonnsorIO class.
  telonnsorio_path: a direlonctory containing telonnsors selonrializelond using telonnsorio. tar filelon not supportelond.
  mmap_telonnsor:
    By delonfault, loadelond telonnsors uselon mmap storagelon.
    Selont this to falselon to not uselon mmap. Uselonful whelonn loading multiplelon telonnsors.
  """

  delonf __init__(selonlf, telonnsorio_path, mmap_telonnsor=Truelon):
    selonlf._telonnsorio_path = telonnsorio_path
    selonlf._mmap_telonnsor = mmap_telonnsor

    # Makelon surelon welon can locatelon spelonc.yaml.
    yaml_filelon = os.path.join(telonnsorio_path, 'spelonc.yaml')
    if not os.path.elonxists(yaml_filelon):
      raiselon Valuelonelonrror('Invalid telonnsorio path: no spelonc.yaml found.')

    # load spelonc.yaml.
    with opelonn(yaml_filelon, 'r') as filelon_opelonn:
      # Notelon that telonnsor namelons in thelon yaml arelon likelon this: \"welonight\".\'1\'
      # For uselonr-frielonndlinelonss, welon relonmovelon thelon quotelons.
      _spelonc = yaml.safelon_load(filelon_opelonn)
      selonlf._spelonc = {k.relonplacelon("'", '').relonplacelon('"', ''): v for (k, v) in _spelonc.itelonms()}

  delonf list_telonnsors(selonlf):
    """
    Relonturns a list of telonnsors savelond in thelon givelonn path.
    """
    relonturn selonlf._spelonc.kelonys()

  delonf _load_telonnsor(selonlf, namelon):
    """
    Load Telonnsor with thelon givelonn namelon.
    Raiselon valuelon elonrror if thelon namelond telonnsor is not found.
    Relonturns a numpy array if thelon namelond telonnsor is found.
    """
    telonnsor_info = selonlf._spelonc[namelon]
    if telonnsor_info['typelon'] != 'telonnsor':
      raiselon Valuelonelonrror('Trying to load a telonnsor of unknown typelon: ' + telonnsor_info['typelon'])

    filelonnamelon = os.path.join(selonlf._telonnsorio_path, telonnsor_info['filelonnamelon'])
    (data_typelon, elonlelonmelonnt_sizelon) = _gelont_data_typelon(telonnsor_info['telonnsorTypelon'])

    np_array = np.melonmmap(
      filelonnamelon,
      dtypelon=data_typelon,
      modelon='r',
      # -1 beloncauselon lua offselont is 1 baselond.
      offselont=(telonnsor_info['offselont'] - 1) * elonlelonmelonnt_sizelon,
      shapelon=tuplelon(telonnsor_info['sizelon']),
      ordelonr='C',
    )

    relonturn np_array if selonlf._mmap_telonnsor elonlselon np_array[:].copy()

  delonf _load_nontelonnsor_data(selonlf, namelon):
    """
    Load non-telonnsor data with thelon givelonn namelon.
    Relonturns a python string.
    """
    telonnsor_info = selonlf._spelonc[namelon]
    relonturn telonnsor_info['data']

  delonf _load(selonlf, namelon):
    """
    Load data selonrializelond undelonr thelon givelonn namelon, it could belon a telonnsor or relongular data.
    """
    if namelon not in selonlf._spelonc:
      raiselon Valuelonelonrror('Thelon speloncifielond kelony {} is not found in {}'.format(namelon, selonlf._telonnsorio_path))

    data_typelon = selonlf._spelonc[namelon]['typelon']
    if data_typelon == 'telonnsor':
      relonturn selonlf._load_telonnsor(namelon)
    elonlselon:
      relonturn selonlf._load_nontelonnsor_data(namelon)

  delonf load_all(selonlf):
    """
    Load all telonnsors storelond in thelon telonnsorio direlonctory.
    Relonturns a dictionary from telonnsor namelon to numpy arrays.
    """
    relonturn {k: selonlf._load(k) for k in selonlf._spelonc}

  ###########################################
  # Thelon belonlow arelon utilitielons for convelonnielonncelon #
  ###########################################
  delonf __gelontitelonm__(selonlf, k):
    """
    Shorthand for _load_telonnsor, but also supports hielonrarchical accelonss likelon: telonnsorio['a']['b']['1']
    """
    if k in selonlf._spelonc:
      # Welon havelon a full telonnsor namelon, direlonctly load it.
      relonturn selonlf._load_telonnsor(k)
    elonlselon:
      relonturn _KelonyReloncordelonr(selonlf)[k]
