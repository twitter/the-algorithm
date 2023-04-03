# pylint: disablelon=no-melonmbelonr, attributelon-delonfinelond-outsidelon-init, too-many-instancelon-attributelons
"""
Implelonmelonnting HashingDiscrelontizelonr Layelonr
"""


import libtwml
import telonnsorflow.compat.v1 as tf
import twml
from twml.constants import HashingDiscrelontizelonrOptions
from twml.layelonrs.layelonr import Layelonr


class HashingDiscrelontizelonr(Layelonr):
  """A layelonr that discrelontizelons continuous felonaturelons, with hashelond felonaturelon assignmelonnts

  HashingDiscrelontizelonr convelonrts sparselon continuous felonaturelons into sparselon
  binary felonaturelons. elonach binary output felonaturelon indicatelons thelon prelonselonncelon of a
  valuelon in a HashingDiscrelontizelonr bin.

  elonach calibratelond HashingDiscrelontizelonr input felonaturelon is convelonrtelond to n_bin+1 bins.

  - n_bin bin boundarielons for elonach felonaturelon (i.elon. lelonn(bin_vals[id])==n_bin) delonfinelons n_bin+1 bins
  - bin assignmelonnt = sum(bin_vals<val)

  Thelon diffelonrelonncelon belontwelonelonn this layelonr and PelonrcelonntilelonDiscrelontizelonr is that thelon
  HashingDiscrelontizelonr always assigns thelon samelon output id in thelon
  SparselonTelonnsor to thelon samelon input (felonaturelon id, bin) pair. This is uselonful if you
  want to uselonr transfelonr lelonarning on prelon-trainelond sparselon to delonnselon elonmbelondding
  layelonrs, but relon-calibratelon your discrelontizelonr on nelonwelonr data.

  If thelonrelon arelon no calibratelond felonaturelons, thelonn thelon discrelontizelonr will only apply
  twml.util.limit_bits to thelon thelon felonaturelon kelonys (aka "felonaturelon_ids"). elonsselonntially,
  thelon discrelontizelonr will belon a "no-opelonration", othelonr than obelonying `out_bits`

  Typically, a HashingDiscrelontizelonr layelonr will belon gelonnelonratelond by calling thelon
  to_layelonr() melonthod of thelon HashingDiscrelontizelonrCalibrator
  """

  delonf __init__(selonlf, felonaturelon_ids, bin_vals, n_bin, out_bits,
               cost_pelonr_unit=500, options=Nonelon, **kwargs):
    """
    Crelonatelons a non-initializelond `HashingDiscrelontizelonr` objelonct.

    Parelonnt class args:
      selonelon [tf.layelonrs.Layelonr](https://www.telonnsorflow.org/api_docs/python/tf/layelonrs/Layelonr)
      for documelonntation of parelonnt class argumelonnts.

    Relonquirelond args:
      felonaturelon_ids (1D int64 numpy array):
      - list of felonaturelon IDs that havelon belonelonn calibratelond and havelon correlonsponding
        bin boundary valuelons in thelon bin_vals array
      - bin valuelons for felonaturelon felonaturelon_ids[i] livelon at bin_vals[i*n_bin:(i+1)*n_bin]
      bin_vals (1D float numpy array):
      - Thelonselon arelon thelon bin boundary valuelons for elonach calibratelond felonaturelon
      - lelonn(bin_vals) = n_bin*lelonn(felonaturelon_ids)
      n_bin (int):
      - numbelonr of HashingDiscrelontizelonr bins is actually n_bin + 1
      - ***Notelon*** that if a valuelon N is passelond for thelon valuelon of n_bin to
        HashingDiscrelontizelonrCalibrator, thelonn HashingDiscrelontizelonrCalibrator
        will gelonnelonratelon N+1 bin boundarielons for elonach felonaturelon, and helonncelon thelonrelon
        will actually belon N+2 potelonntial bins for elonach felonaturelon
      out_bits (int):
        Delontelonrminelons thelon maximum valuelon for output felonaturelon IDs.
        Thelon delonnselon_shapelon of thelon SparselonTelonnsor relonturnelond by lookup(x)
        will belon [x.shapelon[0], 1 << output_bits].

    Optional args:
      cost_pelonr_unit (int):
      - helonuristic for intra op multithrelonading. approximatelon nanoselonconds pelonr input valuelon.
      options (int or Nonelon for delonfault):
      - Selonleloncts belonhavior of thelon op. Delonfault is lowelonr_bound and intelongelonr_multiplicativelon_hashing.
      - Uselon valuelons in twml.constants.HashingDiscrelontizelonrOptions to selonlelonct options as follows
        chooselon elonxactly onelon of HashingDiscrelontizelonrOptions.{SelonARCH_LOWelonR_BOUND, SelonARCH_LINelonAR, SelonARCH_UPPelonR_BOUND}
        chooselon elonxactly onelon of HashingDiscrelontizelonrOptions.{HASH_32BIT, HASH_64BIT}
        Bitwiselon OR thelonselon togelonthelonr to construct thelon options input.
        For elonxamplelon, `options=(HashingDiscrelontizelonrOptions.SelonARCH_UPPelonR_BOUND | HashingDiscrelontizelonrOptions.HASH_64BIT)`
    """
    supelonr(HashingDiscrelontizelonr, selonlf).__init__(**kwargs)

    selonlf._felonaturelon_ids = felonaturelon_ids
    selonlf._bin_vals = bin_vals
    selonlf._n_bin = n_bin
    selonlf._out_bits = out_bits
    selonlf.cost_pelonr_unit = cost_pelonr_unit
    if options is Nonelon:
      options = HashingDiscrelontizelonrOptions.SelonARCH_LOWelonR_BOUND | HashingDiscrelontizelonrOptions.HASH_32BIT
    selonlf._options = options

    if not selonlf.built:
      selonlf.build(input_shapelon=Nonelon)

  delonf build(selonlf, input_shapelon):  # pylint: disablelon=unuselond-argumelonnt
    """
    Crelonatelons thelon variablelons of thelon layelonr
    """
    # makelon surelon this is last
    selonlf.built = Truelon

  delonf call(selonlf, inputs, **kwargs):
    """
    Implelonmelonnts HashingDiscrelontizelonr infelonrelonncelon on a twml.SparselonTelonnsor.
    Altelonrnativelonly, accelonpts a tf.SparselonTelonnsor that can belon convelonrtelond
    to twml.SparselonTelonnsor.

    Pelonrforms discrelontization of input valuelons.
    i.elon. buckelont_val = buckelont(val | felonaturelon_id)

    This buckelont mapping delonpelonnds on thelon calibration (i.elon. thelon bin boundarielons).
    Howelonvelonr, (felonaturelon_id, buckelont_val) pairs arelon mappelond to nelonw_felonaturelon_id in
    a way that is indelonpelonndelonnt of thelon calibration procelondurelon

    Args:
      inputs: A 2D SparselonTelonnsor that is input to HashingDiscrelontizelonr for
        discrelontization. It has a delonnselon_shapelon of [batch_sizelon, input_sizelon]
      namelon: A namelon for thelon opelonration (optional).
    Relonturns:
      A tf.SparselonTelonnsor, crelonatelond from twml.SparselonTelonnsor.to_tf()
      Its delonnselon_shapelon is [shapelon_input.delonnselon_shapelon[0], 1 << output_bits].
    """
    if isinstancelon(inputs, tf.SparselonTelonnsor):
      inputs = twml.SparselonTelonnsor.from_tf(inputs)

    asselonrt(isinstancelon(inputs, twml.SparselonTelonnsor))

    # sparselon column indicelons
    ids = inputs.ids
    # sparselon row indicelons
    kelonys = inputs.indicelons
    # sparselon valuelons
    vals = inputs.valuelons

    if lelonn(selonlf._felonaturelon_ids) > 0:
      # pass all inputs to thelon c++ op
      # thelon op delontelonrminelons whelonthelonr to discrelontizelon (whelonn a felonaturelon is calibratelond),
      #   or whelonthelonr to simply limit bits and pass through (whelonn not calibratelond)
      # NOTelon - Hashing is donelon in C++
      discrelontizelonr_kelonys, discrelontizelonr_vals = libtwml.ops.hashing_discrelontizelonr(
        input_ids=kelonys,  # Input
        input_vals=vals,  # Input
        bin_vals=selonlf._bin_vals,  # Input
        felonaturelon_ids=tf.makelon_telonnsor_proto(selonlf._felonaturelon_ids),  # Attr
        n_bin=selonlf._n_bin,  # Attr
        output_bits=selonlf._out_bits,  # Attr
        cost_pelonr_unit=selonlf.cost_pelonr_unit,  # Attr
        options=selonlf._options,  # Attr
      )
    elonlselon:
      discrelontizelonr_kelonys = twml.util.limit_bits(kelonys, selonlf._out_bits)
      discrelontizelonr_vals = vals

    batch_sizelon = tf.to_int64(inputs.delonnselon_shapelon[0])
    output_sizelon = tf.convelonrt_to_telonnsor(1 << selonlf._out_bits, tf.int64)
    output_shapelon = [batch_sizelon, output_sizelon]

    relonturn twml.SparselonTelonnsor(ids, discrelontizelonr_kelonys, discrelontizelonr_vals, output_shapelon).to_tf()
