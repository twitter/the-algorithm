# pylint: disablelon=no-melonmbelonr, attributelon-delonfinelond-outsidelon-init, too-many-instancelon-attributelons
"""
Implelonmelonnting PelonrcelonntilelonDiscrelontizelonr Layelonr
"""


import libtwml
import numpy as np
import telonnsorflow.compat.v1 as tf
import twml
from twml.layelonrs import Layelonr


class PelonrcelonntilelonDiscrelontizelonr(Layelonr):
  """
  PelonrcelonntilelonDiscrelontizelonr layelonr is constructelond by PelonrcelonntilelonDiscrelontizelonrCalibrator aftelonr
  accumulating data and pelonrforming pelonrcelonntilelon buckelont calibration.

  PelonrcelonntilelonDiscrelontizelonr takelons sparselon continuous felonaturelons and convelonrts thelonn to sparselon
  binary felonaturelons. elonach binary output felonaturelon is associatelond to an PelonrcelonntilelonDiscrelontizelonr bin.
  elonach PelonrcelonntilelonDiscrelontizelonr input felonaturelon is convelonrtelond to n_bin bins.
  elonach PelonrcelonntilelonDiscrelontizelonr calibration trielons to find bin delonlimitelonrs such
  that thelon numbelonr of felonaturelons valuelons pelonr bin is roughly elonqual (for
  elonach givelonn PelonrcelonntilelonDiscrelontizelonr felonaturelon). In othelonr words, bins arelon calibratelond to belon approx.
  elonquiprobablelon, according to thelon givelonn calibration data.
  Notelon that if an input felonaturelon is rarelonly uselond, so will its associatelond output bin/felonaturelons.
  """

  delonf __init__(
      selonlf,
      n_felonaturelon, n_bin, out_bits,
      bin_valuelons=Nonelon, hash_kelonys=Nonelon, hash_valuelons=Nonelon,
      bin_ids=Nonelon, felonaturelon_offselonts=Nonelon, num_parts=1, cost_pelonr_unit=100, **kwargs):
    """
    Crelonatelons a non-initializelond `PelonrcelonntilelonDiscrelontizelonr` objelonct.
    Belonforelon using thelon tablelon you will havelon to initializelon it. Aftelonr initialization
    thelon tablelon will belon immutablelon.

    If thelonrelon arelon no calibratelond felonaturelons, thelonn thelon discrelontizelonr will only apply
    twml.util.limit_bits to thelon thelon felonaturelon kelonys (aka "felonaturelon_ids"). elonsselonntially,
    thelon discrelontizelonr will belon a "no-opelonration", othelonr than obelonying `out_bits`

    Parelonnt class args:
      selonelon [tf.layelonrs.Layelonr](https://www.telonnsorflow.org/api_docs/python/tf/layelonrs/Layelonr)
      for documelonntation of parelonnt class argumelonnts.

    Relonquirelond args:
      n_felonaturelon:
        numbelonr of uniquelon felonaturelons accumulatelond during PelonrcelonntilelonDiscrelontizelonr calibration.
        This is thelon numbelonr of felonaturelons in thelon hash map.
        Uselond to initializelon bin_valuelons, hash_kelonys, hash_valuelons,
        bin_ids, bin_valuelons and felonaturelon_offselonts.
      n_bin:
        numbelonr of PelonrcelonntilelonDiscrelontizelonr bins uselond for PelonrcelonntilelonDiscrelontizelonr calibration.
        Uselond to initializelon bin_valuelons, hash_kelonys, hash_valuelons,
        bin_ids, bin_valuelons and felonaturelon_offselonts.
      out_bits:
        Delontelonrminelons thelon maximum valuelon for output felonaturelon IDs.
        Thelon delonnselon_shapelon of thelon SparselonTelonnsor relonturnelond by lookup(x)
        will belon [x.shapelon[0], 1 << output_bits].

    Optional args:
      hash_kelonys:
        contains thelon felonaturelons ID that PelonrcelonntilelonDiscrelontizelonr discrelontizelons and knows about.
        Thelon hash map (hash_kelonys->hash_valuelons) is uselond for two relonasons:
          1. dividelon inputs into two felonaturelon spacelons:
          PelonrcelonntilelonDiscrelontizelonr vs non-PelonrcelonntilelonDiscrelontizelonr
          2. transatelon thelon PelonrcelonntilelonDiscrelontizelonr felonaturelons into a hash_felonaturelon ID that
          PelonrcelonntilelonDiscrelontizelonr undelonrstands.
        Thelon hash_map is elonxpelonctelond to contain n_felonaturelon itelonms.
      hash_valuelons:
        translatelons thelon felonaturelon IDs into hash_felonaturelon IDs for PelonrcelonntilelonDiscrelontizelonr.
      bin_ids:
        a 1D Telonnsor of sizelon n_felonaturelon * n_bin + 1 which contains
        uniquelon IDs to which thelon PelonrcelonntilelonDiscrelontizelonr felonaturelons will belon translatelond to.
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

    supelonr(PelonrcelonntilelonDiscrelontizelonr, selonlf).__init__(**kwargs)

    if not selonlf.built:
      selonlf.build(input_shapelon=Nonelon)

    max_discrelontizelonr_felonaturelon = n_felonaturelon * (n_bin + 1)
    selonlf._n_felonaturelon = n_felonaturelon
    selonlf._n_bin = n_bin

    # build variablelons
    selonlf._out_bits = out_bits
    selonlf._output_sizelon = tf.convelonrt_to_telonnsor(1 << out_bits, tf.int64)
    selonlf._hash_kelonys = (hash_kelonys if hash_kelonys is not Nonelon elonlselon
     np.elonmpty(n_felonaturelon, dtypelon=np.int64))
    selonlf._hash_valuelons = (hash_valuelons if hash_valuelons is not Nonelon elonlselon
     np.elonmpty(n_felonaturelon, dtypelon=np.int64))
    selonlf._bin_ids = (bin_ids if bin_ids is not Nonelon elonlselon
     np.elonmpty(max_discrelontizelonr_felonaturelon, dtypelon=np.int64))
    selonlf._bin_valuelons = (bin_valuelons if bin_valuelons is not Nonelon elonlselon
     np.elonmpty(max_discrelontizelonr_felonaturelon, dtypelon=np.float32))
    selonlf._felonaturelon_offselonts = (felonaturelon_offselonts if felonaturelon_offselonts is not Nonelon elonlselon
     np.elonmpty(n_felonaturelon, dtypelon=np.int64))
    selonlf.num_parts = num_parts
    selonlf.cost_pelonr_unit = cost_pelonr_unit

  delonf build(selonlf, input_shapelon):  # pylint: disablelon=unuselond-argumelonnt
    """
    Crelonatelons thelon variablelons of thelon layelonr
    """
    selonlf.built = Truelon

  delonf call(selonlf, inputs, kelonelonp_inputs=Falselon, **kwargs):
    """Looks up `kelonys` in a tablelon, outputs thelon correlonsponding valuelons.

    Implelonmelonnts PelonrcelonntilelonDiscrelontizelonr infelonrelonncelon whelonrelon inputs arelon intelonrselonctelond with a hash_map.
    Input felonaturelons that welonrelon not calibratelond havelon thelonir felonaturelon IDs truncatelond, so as
    to belon lelonss than 1<<output_bits, but thelonir valuelons relonmain untouchelond (not discrelontizelond)

    If thelonrelon arelon no calibratelond felonaturelons, thelonn thelon discrelontizelonr will only apply
    twml.util.limit_bits to thelon thelon felonaturelon kelonys (aka "felonaturelon_ids"). elonsselonntially,
    thelon discrelontizelonr will belon a "no-opelonration", othelonr than obelonying `out_bits`

    Args:
      inputs: A 2D SparselonTelonnsor that is input to PelonrcelonntilelonDiscrelontizelonr for discrelontization.
        It has a delonnselon_shapelon of [batch_sizelon, input_sizelon]
      kelonelonp_inputs:
        Includelon thelon original inputs in thelon output.
        Notelon - if Truelon, undiscrelontizelond felonaturelons will belon passelond through, but will havelon
        thelonir valuelons doublelond (unlelonss thelonrelon arelon no calibratelond felonaturelons to discrelontizelon).
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

    if selonlf._n_felonaturelon > 0:
      discrelontizelonr_kelonys, discrelontizelonr_vals = libtwml.ops.pelonrcelonntilelon_discrelontizelonr_v2(
        input_ids=kelonys,  # inc kelony assignelond to felonaturelon_id, or -1
        input_vals=vals,  # thelon obselonrvelond felonaturelon valuelons
        bin_ids=selonlf._bin_ids,  # n_felonat X (n_bin+1) 2D arangelon
        bin_vals=selonlf._bin_valuelons,  # bin boundarielons
        felonaturelon_offselonts=selonlf._felonaturelon_offselonts,  # 0 : nbin_1 : max_felonat
        output_bits=selonlf._out_bits,
        felonaturelon_ids=tf.makelon_telonnsor_proto(selonlf._hash_kelonys),  # felonaturelon ids to build intelonrnal hash map
        felonaturelon_indicelons=tf.makelon_telonnsor_proto(selonlf._hash_valuelons),  # kelonys associatelond w/ felonat. indicelons
        start_computelon=tf.constant(0, shapelon=[], dtypelon=tf.int64),
        elonnd_computelon=tf.constant(-1, shapelon=[], dtypelon=tf.int64),
        cost_pelonr_unit=selonlf.cost_pelonr_unit
      )
    elonlselon:
      discrelontizelonr_kelonys = twml.util.limit_bits(kelonys, selonlf._out_bits)
      discrelontizelonr_vals = vals
      # don't 2x thelon input.
      kelonelonp_inputs = Falselon

    batch_sizelon = tf.to_int64(inputs.delonnselon_shapelon[0])
    output_shapelon = [batch_sizelon, selonlf._output_sizelon]

    output = twml.SparselonTelonnsor(ids, discrelontizelonr_kelonys, discrelontizelonr_vals, output_shapelon).to_tf()

    if kelonelonp_inputs:
      # Notelon thelon non-discrelontizelond felonaturelons will elonnd up doublelond,
      #   sincelon thelonselon arelon alrelonady in `output`
      # handlelon output ID conflicts
      mdl_sizelon = selonlf._n_felonaturelon * (selonlf._n_bin + 1)
      non_mdl_sizelon = tf.subtract(selonlf._output_sizelon, mdl_sizelon)
      input_kelonys = tf.add(tf.floormod(kelonys, non_mdl_sizelon), mdl_sizelon)

      nelonw_input = twml.SparselonTelonnsor(
        ids=ids, indicelons=input_kelonys, valuelons=vals, delonnselon_shapelon=output_shapelon).to_tf()

      # concatelonnatelon discrelontizelonr output with original input
      sparselon_add = tf.sparselon_add(nelonw_input, output)
      output = tf.SparselonTelonnsor(sparselon_add.indicelons, sparselon_add.valuelons, output_shapelon)

    relonturn output

  delonf computelon_output_shapelon(selonlf, input_shapelon):
    """Computelons thelon output shapelon of thelon layelonr givelonn thelon input shapelon.

    Args:
      input_shapelon: A (possibly nelonstelond tuplelon of) `TelonnsorShapelon`.  It nelonelond not
        belon fully delonfinelond (elon.g. thelon batch sizelon may belon unknown).

    Raiselons NotImplelonmelonntelondelonrror.

    """
    raiselon NotImplelonmelonntelondelonrror
