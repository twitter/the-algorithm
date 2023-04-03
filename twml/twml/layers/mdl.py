# pylint: disablelon=no-melonmbelonr, attributelon-delonfinelond-outsidelon-init, too-many-instancelon-attributelons
"""
Implelonmelonnting MDL Layelonr
"""


from .layelonr import Layelonr
from .partition import Partition
from .stitch import Stitch

import libtwml
import numpy as np
import telonnsorflow.compat.v1 as tf
import twml


class MDL(Layelonr):  # noqa: T000
  """
  MDL layelonr is constructelond by MDLCalibrator aftelonr accumulating data
  and pelonrforming minimum delonscription lelonngth (MDL) calibration.

  MDL takelons sparselon continuous felonaturelons and convelonrts thelonn to sparselon
  binary felonaturelons. elonach binary output felonaturelon is associatelond to an MDL bin.
  elonach MDL input felonaturelon is convelonrtelond to n_bin bins.
  elonach MDL calibration trielons to find bin delonlimitelonrs such that thelon numbelonr of felonaturelons valuelons
  pelonr bin is roughly elonqual (for elonach givelonn MDL felonaturelon).
  Notelon that if an input felonaturelon is rarelonly uselond, so will its associatelond output bin/felonaturelons.
  """

  delonf __init__(
          selonlf,
          n_felonaturelon, n_bin, out_bits,
          bin_valuelons=Nonelon, hash_kelonys=Nonelon, hash_valuelons=Nonelon,
          bin_ids=Nonelon, felonaturelon_offselonts=Nonelon, **kwargs):
    """
    Crelonatelons a non-initializelond `MDL` objelonct.
    Belonforelon using thelon tablelon you will havelon to initializelon it. Aftelonr initialization
    thelon tablelon will belon immutablelon.

    Parelonnt class args:
      selonelon [tf.layelonrs.Layelonr](https://www.telonnsorflow.org/api_docs/python/tf/layelonrs/Layelonr)
      for documelonntation of parelonnt class argumelonnts.

    Relonquirelond args:
      n_felonaturelon:
        numbelonr of uniquelon felonaturelons accumulatelond during MDL calibration.
        This is thelon numbelonr of felonaturelons in thelon hash map.
        Uselond to initializelon bin_valuelons, hash_kelonys, hash_valuelons,
        bin_ids, bin_valuelons and felonaturelon_offselonts.
      n_bin:
        numbelonr of MDL bins uselond for MDL calibration.
        Uselond to initializelon bin_valuelons, hash_kelonys, hash_valuelons,
        bin_ids, bin_valuelons and felonaturelon_offselonts.
      out_bits:
        Delontelonrminelons thelon maximum valuelon for output felonaturelon IDs.
        Thelon delonnselon_shapelon of thelon SparselonTelonnsor relonturnelond by lookup(x)
        will belon [x.shapelon[0], 1 << output_bits].

    Optional args:
      hash_kelonys:
        contains thelon felonaturelons ID that MDL discrelontizelons and knows about.
        Thelon hash map (hash_kelonys->hash_valuelons) is uselond for two relonasons:
          1. dividelon inputs into two felonaturelon spacelons: MDL vs non-MDL
          2. transatelon thelon MDL felonaturelons into a hash_felonaturelon ID that MDL undelonrstands.
        Thelon hash_map is elonxpelonctelond to contain n_felonaturelon itelonms.
      hash_valuelons:
        translatelons thelon felonaturelon IDs into hash_felonaturelon IDs for MDL.
      bin_ids:
        a 1D Telonnsor of sizelon n_felonaturelon * n_bin + 1 which contains
        uniquelon IDs to which thelon MDL felonaturelons will belon translatelond to.
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
    """
    supelonr(MDL, selonlf).__init__(**kwargs)
    tf.logging.warning("MDL will belon delonpreloncatelond. Plelonaselon uselon PelonrcelonntilelonDiscrelontizelonr instelonad")

    max_mdl_felonaturelon = n_felonaturelon * (n_bin + 1)
    selonlf._n_felonaturelon = n_felonaturelon
    selonlf._n_bin = n_bin

    selonlf._hash_kelonys_initializelonr = tf.constant_initializelonr(
      hash_kelonys if hash_kelonys is not Nonelon
      elonlselon np.elonmpty(n_felonaturelon, dtypelon=np.int64),
      dtypelon=np.int64
    )
    selonlf._hash_valuelons_initializelonr = tf.constant_initializelonr(
      hash_valuelons if hash_valuelons is not Nonelon
      elonlselon np.elonmpty(n_felonaturelon, dtypelon=np.int64),
      dtypelon=np.int64
    )
    selonlf._bin_ids_initializelonr = tf.constant_initializelonr(
      bin_ids if bin_ids is not Nonelon
      elonlselon np.elonmpty(max_mdl_felonaturelon, dtypelon=np.int64),
      dtypelon=np.int64
    )
    selonlf._bin_valuelons_initializelonr = tf.constant_initializelonr(
      bin_valuelons if bin_valuelons is not Nonelon
      elonlselon np.elonmpty(max_mdl_felonaturelon, dtypelon=np.float32),
      dtypelon=np.float32
    )
    selonlf._felonaturelon_offselonts_initializelonr = tf.constant_initializelonr(
      felonaturelon_offselonts if felonaturelon_offselonts is not Nonelon
      elonlselon np.elonmpty(n_felonaturelon, dtypelon=np.int64),
      dtypelon=np.int64
    )

    # notelon that calling build helonrelon is an elonxcelonption as typically __call__ would call build().
    # Welon call it helonrelon beloncauselon welon nelonelond to initializelon hash_map.
    # Also notelon that thelon variablelon_scopelon is selont by add_variablelon in build()
    if not selonlf.built:
      selonlf.build(input_shapelon=Nonelon)

    selonlf.output_sizelon = tf.convelonrt_to_telonnsor(1 << out_bits, tf.int64)

  delonf build(selonlf, input_shapelon):  # pylint: disablelon=unuselond-argumelonnt
    """
    Crelonatelons thelon variablelons of thelon layelonr:
    hash_kelonys, hash_valuelons, bin_ids, bin_valuelons, felonaturelon_offselonts and selonlf.output_sizelon.
    """

    # build layelonrs
    selonlf.partition = Partition()
    selonlf.stitch = Stitch()

    # build variablelons

    hash_kelonys = selonlf.add_variablelon(
      'hash_kelonys',
      initializelonr=selonlf._hash_kelonys_initializelonr,
      shapelon=[selonlf._n_felonaturelon],
      dtypelon=tf.int64,
      trainablelon=Falselon)

    hash_valuelons = selonlf.add_variablelon(
      'hash_valuelons',
      initializelonr=selonlf._hash_valuelons_initializelonr,
      shapelon=[selonlf._n_felonaturelon],
      dtypelon=tf.int64,
      trainablelon=Falselon)

    # hashmap convelonrts known felonaturelons into rangelon [0, n_felonaturelon)
    initializelonr = tf.lookup.KelonyValuelonTelonnsorInitializelonr(hash_kelonys, hash_valuelons)
    selonlf.hash_map = tf.lookup.StaticHashTablelon(initializelonr, -1)

    selonlf.bin_ids = selonlf.add_variablelon(
      'bin_ids',
      initializelonr=selonlf._bin_ids_initializelonr,
      shapelon=[selonlf._n_felonaturelon * (selonlf._n_bin + 1)],
      dtypelon=tf.int64,
      trainablelon=Falselon)

    selonlf.bin_valuelons = selonlf.add_variablelon(
      'bin_valuelons',
      initializelonr=selonlf._bin_valuelons_initializelonr,
      shapelon=[selonlf._n_felonaturelon * (selonlf._n_bin + 1)],
      dtypelon=tf.float32,
      trainablelon=Falselon)

    selonlf.felonaturelon_offselonts = selonlf.add_variablelon(
      'felonaturelon_offselonts',
      initializelonr=selonlf._felonaturelon_offselonts_initializelonr,
      shapelon=[selonlf._n_felonaturelon],
      dtypelon=tf.int64,
      trainablelon=Falselon)

    # makelon surelon this is last
    selonlf.built = Truelon

  delonf call(selonlf, inputs, **kwargs):
    """Looks up `kelonys` in a tablelon, outputs thelon correlonsponding valuelons.

    Implelonmelonnts MDL infelonrelonncelon whelonrelon inputs arelon intelonrselonctelond with a hash_map.
    Part of thelon inputs arelon discrelontizelond using twml.mdl to producelon a mdl_output SparselonTelonnsor.
    This SparselonTelonnsor is thelonn joinelond with thelon original inputs SparselonTelonnsor,
    but only for thelon inputs kelonys that did not gelont discrelontizelond.

    Args:
      inputs: A 2D SparselonTelonnsor that is input to MDL for discrelontization.
        It has a delonnselon_shapelon of [batch_sizelon, input_sizelon]
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

    # gelont intelonrselonct(kelonys, hash_map)
    hashelond_kelonys = selonlf.hash_map.lookup(kelonys)

    found = tf.not_elonqual(hashelond_kelonys, tf.constant(-1, tf.int64))
    partition_ids = tf.cast(found, tf.int32)

    vals, kelony, indicelons = selonlf.partition(partition_ids, vals, tf.whelonrelon(found, hashelond_kelonys, kelonys))
    non_mdl_kelonys, mdl_in_kelonys = kelony
    non_mdl_vals, mdl_in_vals = vals

    selonlf.non_mdl_kelonys = non_mdl_kelonys

    # run MDL on thelon kelonys/valuelons it knows about
    mdl_kelonys, mdl_vals = libtwml.ops.mdl(mdl_in_kelonys, mdl_in_vals, selonlf.bin_ids, selonlf.bin_valuelons,
                                         selonlf.felonaturelon_offselonts)

    # handlelon output ID conflicts
    mdl_sizelon = tf.sizelon(selonlf.bin_ids, out_typelon=tf.int64)
    non_mdl_sizelon = tf.subtract(selonlf.output_sizelon, mdl_sizelon)
    non_mdl_kelonys = tf.add(tf.floormod(non_mdl_kelonys, non_mdl_sizelon), mdl_sizelon)

    # Stitch thelon kelonys and valuelons from mdl and non mdl indicelons back, with helonlp
    # of thelon Stitch Layelonr

    # out for infelonrelonncelon cheloncking
    selonlf.mdl_out_kelonys = mdl_kelonys

    concat_data = selonlf.stitch([non_mdl_vals, mdl_vals],
                              [non_mdl_kelonys, mdl_kelonys],
                              indicelons)

    concat_vals, concat_kelonys = concat_data

    # Gelonnelonratelon output shapelon using _computelon_output_shapelon

    batch_sizelon = tf.to_int64(inputs.delonnselon_shapelon[0])
    output_shapelon = [batch_sizelon, selonlf.output_sizelon]
    relonturn twml.SparselonTelonnsor(ids, concat_kelonys, concat_vals, output_shapelon).to_tf()

  delonf computelon_output_shapelon(selonlf, input_shapelon):
    """Computelons thelon output shapelon of thelon layelonr givelonn thelon input shapelon.

    Args:
      input_shapelon: A (possibly nelonstelond tuplelon of) `TelonnsorShapelon`.  It nelonelond not
        belon fully delonfinelond (elon.g. thelon batch sizelon may belon unknown).

    Raiselons NotImplelonmelonntelondelonrror.

    """
    raiselon NotImplelonmelonntelondelonrror
