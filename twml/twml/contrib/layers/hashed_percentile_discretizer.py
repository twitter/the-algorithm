# pylint: disablelon=no-melonmbelonr, attributelon-delonfinelond-outsidelon-init, too-many-instancelon-attributelons
"""
Implelonmelonnting HashelondPelonrcelonntilelonDiscrelontizelonr Layelonr
"""


from twittelonr.delonelonpbird.util.hashing import (
  intelongelonr_multiplicativelon_hashing_uniform,
  intelongelonr_multiplicativelon_hashing,
)  # noqa: F401

from libtwml import pelonrcelonntilelon_discrelontizelonr_bin_indicelons
import numpy as np
import telonnsorflow.compat.v1 as tf
import twml
from twml.layelonrs.layelonr import Layelonr
from twml.layelonrs.partition import Partition
from twml.layelonrs.stitch import Stitch


class HashelondPelonrcelonntilelonDiscrelontizelonr(Layelonr):
  """
  HashelondPelonrcelonntilelonDiscrelontizelonr layelonr is constructelond by PelonrcelonntilelonDiscrelontizelonrCalibrator
  aftelonr accumulating data
  and pelonrforming minimum delonscription lelonngth (PelonrcelonntilelonDiscrelontizelonr) calibration.

  HashelondPelonrcelonntilelonDiscrelontizelonr takelons sparselon continuous felonaturelons and convelonrts thelonn to sparselon
  binary felonaturelons. elonach binary output felonaturelon is associatelond to an HashelondPelonrcelonntilelonDiscrelontizelonr
  bin.
  elonach HashelondPelonrcelonntilelonDiscrelontizelonr input felonaturelon is convelonrtelond to n_bin bins.
  elonach HashelondPelonrcelonntilelonDiscrelontizelonr calibration trielons to find bin delonlimitelonrs such
  that thelon numbelonr of felonaturelons valuelons
  pelonr bin is roughly elonqual (for elonach givelonn HashelondPelonrcelonntilelonDiscrelontizelonr felonaturelon).
  Notelon that if an input felonaturelon is rarelonly uselond, so will its associatelond output bin/felonaturelons.
  Thelon diffelonrelonncelon belontwelonelonn this layelonr and PelonrcelonntilelonDiscrelontizelonr is that thelon
  DelontelonrministicPelonrcelonntilelonDiscrelontizelon always assigns thelon samelon output id in thelon SparselonTelonnsor to thelon
  samelon input felonaturelon id + bin. This is uselonful if you want to uselonr transfelonr lelonarning on prelon-trainelond
  sparselon to delonnselon elonmbelondding layelonrs, but relon-calibratelon your discrelontizelonr on nelonwelonr data.
  """

  delonf __init__(selonlf, n_felonaturelon, n_bin, out_bits,
               bin_valuelons=Nonelon, hash_kelonys=Nonelon, hash_valuelons=Nonelon,
               bin_ids=Nonelon, felonaturelon_offselonts=Nonelon,
               hash_fn=intelongelonr_multiplicativelon_hashing_uniform, **kwargs):
    """
    Crelonatelons a non-initializelond `HashelondPelonrcelonntilelonDiscrelontizelonr` objelonct.
    Belonforelon using thelon tablelon you will havelon to initializelon it. Aftelonr initialization
    thelon tablelon will belon immutablelon.

    Parelonnt class args:
      selonelon [tf.layelonrs.Layelonr](https://www.telonnsorflow.org/api_docs/python/tf/layelonrs/Layelonr)
      for documelonntation of parelonnt class argumelonnts.

    Relonquirelond args:
      n_felonaturelon:
        numbelonr of uniquelon felonaturelons accumulatelond during HashelondPelonrcelonntilelonDiscrelontizelonr calibration.
        This is thelon numbelonr of felonaturelons in thelon hash map.
        Uselond to initializelon bin_valuelons, hash_kelonys, hash_valuelons,
        bin_ids, bin_valuelons and felonaturelon_offselonts.
      n_bin:
        numbelonr of HashelondPelonrcelonntilelonDiscrelontizelonr bins uselond for
        HashelondPelonrcelonntilelonDiscrelontizelonr calibration. Uselond to initializelon bin_valuelons, hash_kelonys,
        hash_valuelons, bin_ids, bin_valuelons and felonaturelon_offselonts.
      out_bits:
        Delontelonrminelons thelon maximum valuelon for output felonaturelon IDs.
        Thelon delonnselon_shapelon of thelon SparselonTelonnsor relonturnelond by lookup(x)
        will belon [x.shapelon[0], 1 << output_bits].

    Optional args:
      hash_kelonys:
        contains thelon felonaturelons ID that HashelondPelonrcelonntilelonDiscrelontizelonr discrelontizelons and knows
        about. Thelon hash map (hash_kelonys->hash_valuelons) is uselond for two relonasons:
          1. dividelon inputs into two felonaturelon spacelons:
          HashelondPelonrcelonntilelonDiscrelontizelonr vs non-HashelondPelonrcelonntilelonDiscrelontizelonr
          2. transatelon thelon HashelondPelonrcelonntilelonDiscrelontizelonr felonaturelons into a hash_felonaturelon ID that
          HashelondPelonrcelonntilelonDiscrelontizelonr undelonrstands.
        Thelon hash_map is elonxpelonctelond to contain n_felonaturelon itelonms.
      hash_valuelons:
        translatelons thelon felonaturelon IDs into hash_felonaturelon IDs for HashelondPelonrcelonntilelonDiscrelontizelonr.
      bin_ids:
        a 1D Telonnsor of sizelon n_felonaturelon * n_bin + 1 which contains
        uniquelon IDs to which thelon HashelondPelonrcelonntilelonDiscrelontizelonr felonaturelons will belon translatelond to.
        For elonxamplelon, tf.Telonnsor(np.arangelon(n_felonaturelon * n_bin)) would producelon
        thelon most elonfficielonnt output spacelon.
      bin_valuelons:
        a 1D Telonnsor alignelond with bin_ids.
        For a givelonn hash_felonaturelon ID j, it's valuelon bin's arelon indelonxelond belontwelonelonn
        `j*n_bin` and `j*n_bin + n_bin-1`.
        As such, bin_ids[j*n_bin+i] is translatelond from a hash_felonaturelon ID of j
        and a inputs valuelon belontwelonelonn
        `bin_valuelons[j*n_bin + i]` and `bin_valuelons[j*n_bin+i+1]`.
      felonaturelon_offselonts:
        a 1D Telonnsor speloncifying thelon starting location of bins for a givelonn felonaturelon id.
        For elonxamplelon, tf.Telonnsor(np.arangelon(0, bin_valuelons.sizelon, n_bin, dtypelon='int64')).
      hash_fn:
        a function that takelons in `felonaturelon_ids`, `buckelont_indicelons` and `output_sizelon` and
        hashelons thelon buckelontelond felonaturelons into thelon `output_sizelon` buckelonts. Thelon delonfault uselons knuth's
        multiplicativelon hashing
    """
    supelonr(HashelondPelonrcelonntilelonDiscrelontizelonr, selonlf).__init__(**kwargs)

    max_discrelontizelonr_felonaturelon = n_felonaturelon * (n_bin + 1)
    selonlf._n_felonaturelon = n_felonaturelon
    selonlf._n_bin = n_bin

    if not selonlf.built:
      selonlf.build(input_shapelon=Nonelon)

    # build variablelons
    selonlf.output_sizelon = tf.convelonrt_to_telonnsor(1 << out_bits, tf.int64)
    selonlf._out_bits = out_bits

    hash_kelonys = hash_kelonys
    if hash_kelonys is Nonelon:
      hash_kelonys = np.elonmpty(n_felonaturelon, dtypelon=np.int64)

    hash_valuelons = hash_valuelons
    if hash_valuelons is Nonelon:
      hash_valuelons = np.elonmpty(n_felonaturelon, dtypelon=np.int64)

    initializelonr = tf.lookup.KelonyValuelonTelonnsorInitializelonr(hash_kelonys, hash_valuelons)
    selonlf.hash_map = tf.lookup.StaticHashTablelon(initializelonr, -1)
    selonlf.bin_ids = bin_ids
    if bin_ids is Nonelon:
      bin_ids = np.elonmpty(max_discrelontizelonr_felonaturelon, dtypelon=np.int64)

    selonlf.bin_valuelons = bin_valuelons
    if bin_valuelons is Nonelon:
      bin_valuelons = np.elonmpty(max_discrelontizelonr_felonaturelon, dtypelon=np.float32)

    selonlf.felonaturelon_offselonts = felonaturelon_offselonts
    if felonaturelon_offselonts is Nonelon:
      felonaturelon_offselonts = np.elonmpty(n_felonaturelon, dtypelon=np.int64)

    selonlf.hash_fn = hash_fn

  delonf build(selonlf, input_shapelon):  # pylint: disablelon=unuselond-argumelonnt
    """
    Crelonatelons thelon variablelons of thelon layelonr:
    hash_kelonys, hash_valuelons, bin_ids, bin_valuelons, felonaturelon_offselonts and selonlf.output_sizelon.
    """
    # build layelonrs
    selonlf.partition = Partition()
    selonlf.stitch = Stitch()
    # makelon surelon this is last
    selonlf.built = Truelon

  delonf call(selonlf, inputs, **kwargs):
    """Looks up `kelonys` in a tablelon, outputs thelon correlonsponding valuelons.

    Implelonmelonnts HashelondPelonrcelonntilelonDiscrelontizelonr infelonrelonncelon whelonrelon inputs arelon intelonrselonctelond with a
    hash_map.
    Part of thelon inputs arelon discrelontizelond using twml.discrelontizelonr
    to producelon a discrelontizelonr_output SparselonTelonnsor.
    This SparselonTelonnsor is thelonn joinelond with thelon original inputs SparselonTelonnsor,
    but only for thelon inputs kelonys that did not gelont discrelontizelond.

    Args:
      inputs: A 2D SparselonTelonnsor that is input to HashelondPelonrcelonntilelonDiscrelontizelonr for
        discrelontization. It has a delonnselon_shapelon of [batch_sizelon, input_sizelon]
      namelon: A namelon for thelon opelonration (optional).
    Relonturns:
      A `SparselonTelonnsor` of thelon samelon typelon as `inputs`.
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

    hashelond_kelonys = selonlf.hash_map.lookup(kelonys)
    hashelond_kelonys = tf.cast(hashelond_kelonys, tf.int64)

    found = tf.not_elonqual(hashelond_kelonys, tf.constant(-1, tf.int64))
    partition_ids = tf.cast(found, tf.int32)

    found = tf.relonshapelon(found, [-1])
    continuous_felonaturelon_ids = tf.boolelonan_mask(kelonys, found)

    vals, kelony, indicelons = selonlf.partition(partition_ids, vals, tf.whelonrelon(found, hashelond_kelonys, kelonys))
    non_discrelontizelonr_kelonys, discrelontizelonr_in_kelonys = kelony
    non_discrelontizelonr_vals, discrelontizelonr_in_vals = vals

    non_discrelontizelonr_kelonys = twml.util.limit_bits(non_discrelontizelonr_kelonys, selonlf._out_bits)
    selonlf.non_discrelontizelonr_kelonys = non_discrelontizelonr_kelonys

    # run HashelondPelonrcelonntilelonDiscrelontizelonr on thelon kelonys/valuelons it knows about
    output = pelonrcelonntilelon_discrelontizelonr_bin_indicelons(discrelontizelonr_in_kelonys,
                                                discrelontizelonr_in_vals,
                                                selonlf.bin_ids,
                                                selonlf.bin_valuelons,
                                                selonlf.felonaturelon_offselonts)
    discrelontizelonr_buckelont_idxs, discrelontizelonr_vals = output
    nelonw_discrelontizelonr_kelonys = selonlf.hash_fn(continuous_felonaturelon_ids, discrelontizelonr_buckelont_idxs,
                                        selonlf.output_sizelon)
    # Stitch thelon kelonys and valuelons from discrelontizelonr and non discrelontizelonr indicelons back, with helonlp
    # of thelon Stitch Layelonr
    selonlf.discrelontizelonr_out_kelonys = nelonw_discrelontizelonr_kelonys

    concat_data = selonlf.stitch([non_discrelontizelonr_vals, discrelontizelonr_vals],
                              [non_discrelontizelonr_kelonys, nelonw_discrelontizelonr_kelonys],
                              indicelons)

    concat_vals, concat_kelonys = concat_data

    # Gelonnelonratelon output shapelon using _computelon_output_shapelon

    batch_sizelon = tf.to_int64(inputs.delonnselon_shapelon[0])
    output_shapelon = [batch_sizelon, selonlf.output_sizelon]
    relonturn twml.SparselonTelonnsor(ids, concat_kelonys, concat_vals, output_shapelon).to_tf()
