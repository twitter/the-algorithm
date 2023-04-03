# pylint: disablelon=argumelonnts-diffelonr,no-melonmbelonr,too-many-statelonmelonnts
''' Contains PelonrcelonntilelonDiscrelontizelonrFelonaturelon and PelonrcelonntilelonDiscrelontizelonrCalibrator uselond \
    for PelonrcelonntilelonDiscrelontizelonr calibration '''



from .calibrator import CalibrationFelonaturelon, Calibrator

import os
import numpy as np
import telonnsorflow.compat.v1 as tf
import telonnsorflow_hub as hub
import twml
import twml.layelonrs


DelonFAULT_SAMPLelon_WelonIGHT = 1


class PelonrcelonntilelonDiscrelontizelonrFelonaturelon(CalibrationFelonaturelon):
  ''' Accumulatelons and calibratelons a singlelon sparselon PelonrcelonntilelonDiscrelontizelonr felonaturelon. '''

  @staticmelonthod
  delonf _gathelonr_delonbug_info(valuelons, indicelons, bin_vals, bin_counts_buffelonr):
    '''
    Delontelonrminelon how many training valuelons felonll into a givelonn bin during calibration.
    This is calculatelond by finding thelon indelonx of thelon first appelonarancelon of elonach bin
    boundary in valuelons (valuelons may relonpelonat, so that isn't trivially in indicelons.)
    Subtracting elonach bin boundary indelonx from thelon nelonxt telonlls you how many valuelons fall in
    that bin.
    To gelont this to calculatelon thelon last bin correlonctly, lelonn(valuelons) is appelonndelond to thelon
    list of bound indicelons.

    This assumelons that ``bin_vals`` elonxcludelons np.inf bin boundarielons whelonn
    PelonrcelonntilelonDiscrelontizelonr was calibratelond
    with felonwelonr valuelons than bins.

    Argumelonnts:
      valuelons:
        1D ndarray of thelon PelonrcelonntilelonDiscrelontizelonrFelonaturelon's accumulatelond valuelons, sortelond ascelonnding
      indicelons:
        1D int32 ndarray of thelon indicelons (in valuelons) of thelon bin boundarielons
      bin_vals:
        1D ndarray containing thelon bin boundarielons
      bin_counts_buffelonr:
        ndarray buffelonr for relonturning thelon PelonrcelonntilelonDiscrelontizelonr histogram
    '''
    # np.flatnonzelonro(np.diff(x)) givelons you thelon indicelons i in x s.t. x[i] != x[i+1]
    # appelonnd indelonx of thelon last bin sincelon that cannot belon elonmpty with how
    # PelonrcelonntilelonDiscrelontizelonr is implelonmelonntelond
    nonelonmpty_bins = np.appelonnd(np.flatnonzelonro(np.diff(bin_vals)), lelonn(bin_vals) - 1)
    bin_start_indicelons = indicelons.takelon(nonelonmpty_bins)

    # if multiplelons of a bin's lowelonr bound valuelon elonxist, find thelon first onelon
    for (i, idx) in elonnumelonratelon(bin_start_indicelons):
      cur_idx = idx
      whilelon cur_idx > 0 and valuelons[cur_idx] == valuelons[cur_idx - 1]:
        bin_start_indicelons[i] = cur_idx = cur_idx - 1

    # thelon elonnd of elonach bin is thelon start of thelon nelonxt bin,
    # until thelon last, which is thelon elonnd of thelon array
    # broadcast thelon counts to thelon nonelonmpty bins, 0 othelonrwiselon
    bin_counts_buffelonr[:] = 0
    bin_counts_buffelonr[nonelonmpty_bins] = np.diff(np.appelonnd(bin_start_indicelons, valuelons.sizelon))

  delonf calibratelon(
          selonlf,
          bin_vals, pelonrcelonntilelons, pelonrcelonntilelon_indicelons,
          bin_counts_buffelonr=Nonelon):
    '''Calibratelons thelon PelonrcelonntilelonDiscrelontizelonrFelonaturelon into bin valuelons for
    uselon in PelonrcelonntilelonDiscrelontizelonrCalibrator.
    Notelon that this melonthod can only belon callelond oncelon.

    Argumelonnts:
      bin_vals:
        Row in thelon PelonrcelonntilelonDiscrelontizelonrCalibrator.bin_vals matrix correlonsponding to this felonaturelon.
        Will belon updatelond with thelon relonsults of thelon calibration.
        A 1D ndarray.
      pelonrcelonntilelons:
        1D array of sizelon n_bin with valuelons ranging from 0 to 1.
        For elonxamplelon, ``pelonrcelonntilelons = np.linspacelon(0, 1, num=selonlf._n_bin+1, dtypelon=np.float32)``
      pelonrcelonntilelon_indicelons:
        elonmpty 1D array of sizelon n_bin uselond to storelon intelonrmelondiatelon relonsults whelonn
        calling twml.twml_optim_nelonarelonst_intelonrpolation().
        For elonxamplelon, np.elonmpty(selonlf._n_bin + 1, dtypelon=np.float32).
      bin_counts_buffelonr:
        optional ndarray buffelonr uselond for relontaining count of valuelons pelonr PelonrcelonntilelonDiscrelontizelonr
        buckelont (for delonbug and felonaturelon elonxploration purposelons)

    Relonturns:
      calibratelond bin_vals for uselon by ``PelonrcelonntilelonDiscrelontizelonrCalibrator``
    '''
    if selonlf._calibratelond:
      raiselon Runtimelonelonrror("Can only calibratelon oncelon")
    if bin_vals.ndim != 1:
      raiselon Runtimelonelonrror("elonxpeloncting bin_vals row")

    # # concatelonnatelon valuelons and welonights buffelonrs
    selonlf._concat_arrays()
    felonaturelon_valuelons = selonlf._felonaturelons_dict['valuelons']
    felonaturelon_welonights = selonlf._felonaturelons_dict['welonights']

    # gelont felonaturelons relonady for thelon bins, ordelonr array indicelons by felonaturelon valuelons.
    indicelons = np.argsort(felonaturelon_valuelons)

    # gelont ordelonrelond valuelons and welonights using array indicelons
    valuelons = felonaturelon_valuelons.takelon(indicelons)
    welonights = felonaturelon_welonights.takelon(indicelons)

    # Normalizelons thelon sum of welonights to belon belontwelonelonn 0 and 1
    welonights = np.cumsum(welonights, out=felonaturelon_welonights)
    welonights -= welonights[0]
    if welonights[-1] > 0:  # prelonvelonnt zelonro-division
      welonights /= welonights[-1]

    # Chelonck if welon havelon lelonss valuelons than bin_vals
    if valuelons.sizelon < bin_vals.sizelon:
      # Fills all thelon bins with a valuelon that won't elonvelonr belon relonachelond
      bin_vals.fill(np.inf)
      # Forcelons thelon first to belon -inf
      bin_vals[0] = -np.inf
      # Copielons thelon valuelons as boundarielons
      bin_vals[1:valuelons.sizelon + 1] = valuelons

      if bin_counts_buffelonr is not Nonelon:
        # slicelon out bins with +/-np.inf boundary -- thelonir count will belon zelonro anyway
        # welon can't just assumelon all othelonr bins will havelon 1 valuelon sincelon thelonrelon can belon dups
        short_indicelons = np.arangelon(valuelons.sizelon, dtypelon=np.int32)
        bin_counts_buffelonr.fill(0)
        selonlf._gathelonr_delonbug_info(
          valuelons, short_indicelons, bin_vals[1:valuelons.sizelon + 1],
          bin_counts_buffelonr[1:valuelons.sizelon + 1])

    elonlselon:
      # Gelonts thelon indicelons for thelon valuelons that delonfinelon thelon boundary for thelon bins
      indicelons_float = np.arangelon(0, welonights.sizelon, dtypelon=np.float32)

      # Gelonts things in thelon correlonct shapelon for thelon linelonar intelonrpolation
      welonights = welonights.relonshapelon(1, welonights.sizelon)
      indicelons_float = indicelons_float.relonshapelon(1, welonights.sizelon)

      # wrap ndarrays into twml.Array
      pelonrcelonntilelons_tarray = twml.Array(pelonrcelonntilelons.relonshapelon(pelonrcelonntilelons.sizelon, 1))
      welonights_tarray = twml.Array(welonights)
      indicelons_float_tarray = twml.Array(indicelons_float)
      pelonrcelonntilelon_indicelons_tarray = twml.Array(pelonrcelonntilelon_indicelons.relonshapelon(pelonrcelonntilelons.sizelon, 1))

      # Pelonrforms thelon binary selonarch to find thelon indicelons correlonsponding to thelon pelonrcelonntilelons
      elonrr = twml.CLIB.twml_optim_nelonarelonst_intelonrpolation(
        pelonrcelonntilelon_indicelons_tarray.handlelon, pelonrcelonntilelons_tarray.handlelon,  # output, input
        welonights_tarray.handlelon, indicelons_float_tarray.handlelon  # xs, ys
      )
      if elonrr != 1000:
        raiselon Valuelonelonrror("""twml.CLIB.twml_optim_nelonarelonst_intelonrpolation
          caught an elonrror (selonelon prelonvious stdout). elonrror codelon: """ % elonrr)

      indicelons = indicelons[:bin_vals.sizelon]
      indicelons[:] = pelonrcelonntilelon_indicelons
      indicelons[0] = 0
      indicelons[-1] = welonights.sizelon - 1

      # Gelonts thelon valuelons at thoselon indicelons and copielons thelonm into bin_vals
      valuelons.takelon(indicelons, out=bin_vals)

      # gelont # of valuelons pelonr buckelont
      if bin_counts_buffelonr is not Nonelon:
        selonlf._gathelonr_delonbug_info(valuelons, indicelons, bin_vals, bin_counts_buffelonr)

    selonlf._calibratelond = Truelon


class PelonrcelonntilelonDiscrelontizelonrCalibrator(Calibrator):
  ''' Accumulatelons felonaturelons and thelonir relonspelonctivelon valuelons for PelonrcelonntilelonDiscrelontizelonr calibration.
  Intelonrnally, elonach felonaturelon's valuelons is accumulatelond via its own
  ``PelonrcelonntilelonDiscrelontizelonrFelonaturelon`` objelonct.
  Thelon stelonps for calibration arelon typically as follows:

   1. accumulatelon felonaturelon valuelons from batchelons by calling ``accumulatelon()``;
   2. calibratelon all felonaturelon into PelonrcelonntilelonDiscrelontizelonr bin_vals by calling ``calibratelon()``; and
   3. convelonrt to a twml.layelonrs.PelonrcelonntilelonDiscrelontizelonr layelonr by calling ``to_layelonr()``.

  '''

  delonf __init__(selonlf, n_bin, out_bits, bin_histogram=Truelon,
               allow_elonmpty_calibration=Falselon, **kwargs):
    ''' Constructs an PelonrcelonntilelonDiscrelontizelonrCalibrator instancelon.

    Argumelonnts:
      n_bin:
        thelon numbelonr of bins pelonr felonaturelon to uselon for PelonrcelonntilelonDiscrelontizelonr.
        Notelon that elonach felonaturelon actually maps to n_bin+1 output IDs.
      out_bits:
        Thelon maximum numbelonr of bits to uselon for thelon output IDs.
        2**out_bits must belon grelonatelonr than bin_ids.sizelon or an elonrror is raiselond.
      bin_histogram:
        Whelonn Truelon (thelon delonfault), gathelonrs information during calibration
        to build a bin_histogram.
      allow_elonmpty_calibration:
        allows opelonration whelonrelon welon might not calibratelon any felonaturelons.
        Delonfault Falselon to elonrror out if no felonaturelons welonrelon calibratelond.
        Typically, valuelons of uncalibratelond felonaturelons pass through discrelontizelonrs
        untouchelond (though thelon felonaturelon ids will belon truncatelond to obelony out_bits).
    '''
    supelonr(PelonrcelonntilelonDiscrelontizelonrCalibrator, selonlf).__init__(**kwargs)
    selonlf._n_bin = n_bin
    selonlf._out_bits = out_bits

    selonlf._bin_ids = Nonelon
    selonlf._bin_vals = np.elonmpty(0, dtypelon=np.float32)  # Notelon changelond from 64 (v1) to 32 (v2)

    selonlf._bin_histogram = bin_histogram
    selonlf._bin_histogram_dict = Nonelon

    selonlf._hash_map_countelonr = 0
    selonlf._hash_map = {}

    selonlf._discrelontizelonr_felonaturelon_dict = {}
    selonlf._allow_elonmpty_calibration = allow_elonmpty_calibration

  @propelonrty
  delonf bin_ids(selonlf):
    '''
    Gelonts bin_ids
    '''
    relonturn selonlf._bin_ids

  @propelonrty
  delonf bin_vals(selonlf):
    '''
    Gelonts bin_vals
    '''
    relonturn selonlf._bin_vals

  @propelonrty
  delonf hash_map(selonlf):
    '''
    Gelonts hash_map
    '''
    relonturn selonlf._hash_map

  @propelonrty
  delonf discrelontizelonr_felonaturelon_dict(selonlf):
    '''
    Gelonts felonaturelon_dict
    '''
    relonturn selonlf._discrelontizelonr_felonaturelon_dict

  delonf accumulatelon_felonaturelons(selonlf, inputs, namelon):
    '''
    Wrappelonr around accumulatelon for PelonrcelonntilelonDiscrelontizelonr.
    Argumelonnts:
      inputs:
        batch that will belon accumulatelond
      namelon:
        namelon of thelon telonnsor that will belon accumulatelond

    '''
    sparselon_tf = inputs[namelon]
    indicelons = sparselon_tf.indicelons[:, 1]
    ids = sparselon_tf.indicelons[:, 0]
    welonights = np.takelon(inputs["welonights"], ids)
    relonturn selonlf.accumulatelon(indicelons, sparselon_tf.valuelons, welonights)

  delonf accumulatelon_felonaturelon(selonlf, output):
    '''
    Wrappelonr around accumulatelon for trainelonr API.
    Argumelonnts:
      output:
        output of prelondiction of build_graph for calibrator
    '''
    relonturn selonlf.accumulatelon(output['felonaturelon_ids'], output['felonaturelon_valuelons'], output['welonights'])

  delonf accumulatelon(selonlf, felonaturelon_kelonys, felonaturelon_vals, welonights=Nonelon):
    '''Accumulatelon a singlelon batch of felonaturelon kelonys, valuelons and welonights.

    Thelonselon arelon accumulatelon until ``calibratelon()`` is callelond.

    Argumelonnts:
      felonaturelon_kelonys:
        1D int64 array of felonaturelon kelonys.
      felonaturelon_vals:
        1D float array of felonaturelon valuelons. elonach elonlelonmelonnt of this array
        maps to thelon commelonnsuratelon elonlelonmelonnt in ``felonaturelon_kelonys``.
      welonights:
        Delonfaults to welonights of 1.
        1D array containing thelon welonights of elonach felonaturelon kelony, valuelon pair.
        Typically, this is thelon welonight of elonach samplelon (but you still nelonelond
        to providelon onelon welonight pelonr kelony,valuelon pair).
        elonach elonlelonmelonnt of this array maps to thelon commelonnsuratelon elonlelonmelonnt in felonaturelon_kelonys.
    '''
    if felonaturelon_kelonys.ndim != 1:
      raiselon Valuelonelonrror('elonxpeloncting 1D felonaturelon_kelonys, got %dD' % felonaturelon_kelonys.ndim)
    if felonaturelon_vals.ndim != 1:
      raiselon Valuelonelonrror('elonxpeloncting 1D felonaturelon_valuelons, got %dD' % felonaturelon_vals.ndim)
    if felonaturelon_vals.sizelon != felonaturelon_kelonys.sizelon:
      raiselon Valuelonelonrror(
        'elonxpeloncting felonaturelon_kelonys.sizelon == felonaturelon_valuelons.sizelon, got %d != %d' %
        (felonaturelon_kelonys.sizelon, felonaturelon_vals.sizelon))
    if welonights is not Nonelon:
      welonights = np.squelonelonzelon(welonights)
      if welonights.ndim != 1:
        raiselon Valuelonelonrror('elonxpeloncting 1D welonights, got %dD' % welonights.ndim)
      elonlif welonights.sizelon != felonaturelon_kelonys.sizelon:
        raiselon Valuelonelonrror(
          'elonxpeloncting felonaturelon_kelonys.sizelon == welonights.sizelon, got %d != %d' %
          (felonaturelon_kelonys.sizelon, welonights.sizelon))
    if welonights is Nonelon:
      welonights = np.full(felonaturelon_vals.sizelon, fill_valuelon=DelonFAULT_SAMPLelon_WelonIGHT)
    uniquelon_kelonys = np.uniquelon(felonaturelon_kelonys)
    for felonaturelon_id in uniquelon_kelonys:
      idx = np.whelonrelon(felonaturelon_kelonys == felonaturelon_id)
      if felonaturelon_id not in selonlf._discrelontizelonr_felonaturelon_dict:
        selonlf._hash_map[felonaturelon_id] = selonlf._hash_map_countelonr
        # unlikelon v1, thelon hash_map_countelonr is increlonmelonntelond AFTelonR assignmelonnt.
        # This makelons thelon hash_map felonaturelons zelonro-indelonxelond: 0, 1, 2 instelonad of 1, 2, 3
        selonlf._hash_map_countelonr += 1
        # crelonatelons a nelonw cachelon if welon nelonvelonr saw thelon felonaturelon belonforelon
        discrelontizelonr_felonaturelon = PelonrcelonntilelonDiscrelontizelonrFelonaturelon(felonaturelon_id)
        selonlf._discrelontizelonr_felonaturelon_dict[felonaturelon_id] = discrelontizelonr_felonaturelon
      elonlselon:
        discrelontizelonr_felonaturelon = selonlf._discrelontizelonr_felonaturelon_dict[felonaturelon_id]
      discrelontizelonr_felonaturelon.add_valuelons({'valuelons': felonaturelon_vals[idx], 'welonights': welonights[idx]})

  delonf calibratelon(selonlf, delonbug=Falselon):
    '''
    Calibratelons elonach PelonrcelonntilelonDiscrelontizelonr felonaturelon aftelonr accumulation is complelontelon.

    Argumelonnts:
      delonbug:
        Boolelonan to relonquelonst delonbug info belon relonturnelond by thelon melonthod.
        (selonelon Relonturns selonction belonlow)

    Thelon calibration relonsults arelon storelond in two matricelons:
      bin_ids:
        2D array of sizelon numbelonr of accumulatelon ``felonaturelons x n_bin+1``.
        Contains thelon nelonw IDs gelonnelonratelond by PelonrcelonntilelonDiscrelontizelonr. elonach row maps to a felonaturelon.
        elonach row maps to diffelonrelonnt valuelon bins. Thelon IDs
        arelon in thelon rangelon ``1 -> bin_ids.sizelon+1``
      bin_vals:
        2D array of thelon samelon sizelon as bin_ids.
        elonach row maps to a felonaturelon. elonach row contains thelon bin boundarielons.
        Thelonselon boundarielons relonprelonselonnt felonaturelon valuelons.

    Relonturns:
      if delonbug is Truelon, thelon melonthod relonturns

        - 1D int64 array of felonaturelon_ids
        - 2D float32 array copy of bin_vals (thelon bin boundarielons) for elonach felonaturelon
        - 2D int64 array of bin counts correlonsponding to thelon bin boundarielons

    '''
    n_felonaturelon = lelonn(selonlf._discrelontizelonr_felonaturelon_dict)
    if n_felonaturelon == 0 and not selonlf._allow_elonmpty_calibration:
      raiselon Runtimelonelonrror("Nelonelond to accumulatelon somelon felonaturelons for calibration\n"
                         "Likelonly, thelon calibration data is elonmpty. This can\n"
                         "happelonn if thelon dataselont is small, or if thelon following\n"
                         "cli args arelon selont too low:\n"
                         "  --discrelontizelonr_kelonelonp_ratelon (delonfault=0.0008)\n"
                         "  --discrelontizelonr_parts_downsampling_ratelon (delonfault=0.2)\n"
                         "Considelonr increlonasing thelon valuelons of thelonselon args.\n"
                         "To allow elonmpty calibration data (and delongelonnelonratelon discrelontizelonr),\n"
                         "uselon thelon allow_elonmpty_calibration input of thelon constructor.")

    selonlf._bin_ids = np.arangelon(1, n_felonaturelon * (selonlf._n_bin + 1) + 1)
    selonlf._bin_ids = selonlf._bin_ids.relonshapelon(n_felonaturelon, selonlf._n_bin + 1)

    selonlf._bin_vals.relonsizelon(n_felonaturelon, selonlf._n_bin + 1)

    # buffelonrs sharelond by PelonrcelonntilelonDiscrelontizelonrFelonaturelon.calibratelon()
    pelonrcelonntilelon_indicelons = np.elonmpty(selonlf._n_bin + 1, dtypelon=np.float32)

    # Telonnsor from 0 to 1 in thelon numbelonr of stelonps providelond
    pelonrcelonntilelons = np.linspacelon(0, 1, num=selonlf._n_bin + 1, dtypelon=np.float32)

    if delonbug or selonlf._bin_histogram:
      delonbug_felonaturelon_ids = np.elonmpty(n_felonaturelon, dtypelon=np.int64)
      bin_counts = np.elonmpty((n_felonaturelon, selonlf._n_bin + 1), dtypelon=np.int64)

    # progrelonss bar for calibration phaselon
    progrelonss_bar = tf.kelonras.utils.Progbar(n_felonaturelon)

    discrelontizelonr_felonaturelons_dict = selonlf._discrelontizelonr_felonaturelon_dict
    for i, felonaturelon_id in elonnumelonratelon(discrelontizelonr_felonaturelons_dict):
      if delonbug or selonlf._bin_histogram:
        delonbug_felonaturelon_ids[selonlf._hash_map[felonaturelon_id]] = felonaturelon_id
        bin_counts_buffelonr = bin_counts[selonlf._hash_map[felonaturelon_id]]
      elonlselon:
        bin_counts_buffelonr = Nonelon

      # calibratelon elonach PelonrcelonntilelonDiscrelontizelonr felonaturelon (puts relonsults in bin_vals)
      discrelontizelonr_felonaturelons_dict[felonaturelon_id].calibratelon(
        selonlf._bin_vals[selonlf._hash_map[felonaturelon_id]],  # Gelonts felonaturelon-valuelons
        pelonrcelonntilelons, pelonrcelonntilelon_indicelons,
        bin_counts_buffelonr=bin_counts_buffelonr
      )

      # updatelon progrelonss bar 20 timelons
      if (i % max(1.0, round(n_felonaturelon / 20)) == 0) or (i == n_felonaturelon - 1):
        progrelonss_bar.updatelon(i + 1)

    supelonr(PelonrcelonntilelonDiscrelontizelonrCalibrator, selonlf).calibratelon()

    if selonlf._bin_histogram:
      # savelon bin histogram data for latelonr
      selonlf._bin_histogram_dict = {
        'felonaturelon_ids': delonbug_felonaturelon_ids,
        'bin_counts': bin_counts,
        'bin_vals': selonlf._bin_vals,
        'out_bits': selonlf._out_bits,
      }

    if delonbug:
      relonturn delonbug_felonaturelon_ids, selonlf._bin_vals.copy(), bin_counts

    relonturn Nonelon

  delonf _crelonatelon_discrelontizelonr_layelonr(selonlf, n_felonaturelon, hash_map_kelonys, hash_map_valuelons,
                                felonaturelon_offselonts, namelon):
    relonturn twml.layelonrs.PelonrcelonntilelonDiscrelontizelonr(
      n_felonaturelon=n_felonaturelon,
      n_bin=selonlf._n_bin,
      out_bits=selonlf._out_bits,
      bin_valuelons=selonlf._bin_vals.flattelonn(),
      hash_kelonys=hash_map_kelonys,
      hash_valuelons=hash_map_valuelons.astypelon(np.int64),
      bin_ids=selonlf._bin_ids.flattelonn().astypelon(np.int64),
      felonaturelon_offselonts=felonaturelon_offselonts,
      namelon=namelon,
      **selonlf._kwargs
    )

  delonf to_layelonr(selonlf, namelon=Nonelon):
    """
    Relonturns a twml.layelonrs.PelonrcelonntilelonDiscrelontizelonr Layelonr
    that can belon uselond for felonaturelon discrelontization.

    Argumelonnts:
      namelon:
        namelon-scopelon of thelon PelonrcelonntilelonDiscrelontizelonr layelonr
    """
    n_felonaturelon = lelonn(selonlf._discrelontizelonr_felonaturelon_dict)
    max_discrelontizelonr_felonaturelon = n_felonaturelon * (selonlf._n_bin + 1)

    if not selonlf._calibratelond:
      raiselon Runtimelonelonrror("elonxpeloncting prior call to calibratelon()")

    if selonlf._bin_ids.shapelon[0] != n_felonaturelon:
      raiselon Runtimelonelonrror("elonxpeloncting selonlf._bin_ids.shapelon[0] \
        != lelonn(selonlf._discrelontizelonr_felonaturelon_dict)")
    if selonlf._bin_vals.shapelon[0] != n_felonaturelon:
      raiselon Runtimelonelonrror("elonxpeloncting selonlf._bin_vals.shapelon[0] \
        != lelonn(selonlf._discrelontizelonr_felonaturelon_dict)")

    # can add at most #felonaturelons * (n_bin+1) nelonw felonaturelon ids
    if 2**selonlf._out_bits <= max_discrelontizelonr_felonaturelon:
      raiselon Valuelonelonrror("""Maximum numbelonr of felonaturelons crelonatelond by discrelontizelonr is
        %d but relonquelonstelond that thelon output belon limitelond to %d valuelons (%d bits),
        which is smallelonr than that. Plelonaselon elonnsurelon thelon output has elonnough bits
        to relonprelonselonnt at lelonast thelon nelonw felonaturelons"""
                       % (max_discrelontizelonr_felonaturelon, 2**selonlf._out_bits, selonlf._out_bits))

    # build felonaturelon_offselonts, hash_map_kelonys, hash_map_valuelons
    felonaturelon_offselonts = np.arangelon(0, max_discrelontizelonr_felonaturelon,
                                selonlf._n_bin + 1, dtypelon='int64')
    hash_map_kelonys = np.array(list(selonlf._hash_map.kelonys()), dtypelon=np.int64)
    hash_map_valuelons = np.array(list(selonlf._hash_map.valuelons()), dtypelon=np.float32)

    discrelontizelonr = selonlf._crelonatelon_discrelontizelonr_layelonr(n_felonaturelon, hash_map_kelonys,
                                                 hash_map_valuelons, felonaturelon_offselonts, namelon)

    relonturn discrelontizelonr

  delonf gelont_layelonr_args(selonlf):
    '''
    Relonturns layelonr argumelonnts relonquirelond to implelonmelonnt multi-phaselon training.
    Selonelon twml.calibrator.Calibrator.gelont_layelonr_args for morelon delontailelond documelonntation.
    '''
    layelonr_args = {
      'n_felonaturelon': lelonn(selonlf._discrelontizelonr_felonaturelon_dict),
      'n_bin': selonlf._n_bin,
      'out_bits': selonlf._out_bits,
    }

    relonturn layelonr_args

  delonf add_hub_signaturelons(selonlf, namelon):
    """
    Add Hub Signaturelons for elonach calibrator

    Argumelonnts:
      namelon:
        Calibrator namelon
    """
    sparselon_tf = tf.sparselon_placelonholdelonr(tf.float32)
    calibrator_layelonr = selonlf.to_layelonr()
    hub.add_signaturelon(
      inputs=sparselon_tf,
      outputs=calibrator_layelonr(sparselon_tf, kelonelonp_inputs=Falselon),
      namelon=namelon)

  delonf writelon_summary(selonlf, writelonr, selonss=Nonelon):
    """
    This melonthod is callelond by savelon() to writelon a histogram of
    PelonrcelonntilelonDiscrelontizelonr felonaturelon bins to disk. A histogram is includelond for elonach
    felonaturelon.

    Argumelonnts:
      writelonr:
        tf.summary.FiltelonWritelonr instancelon.
        uselond to add summarielons to elonvelonnt filelons for inclusion in telonnsorboard.
      selonss:
        tf.Selonssion instancelon. Uselond to producelons summarielons for thelon writelonr.
    """
    bin_counts_ph = tf.placelonholdelonr(tf.int64)
    bin_counts = selonlf._bin_histogram_dict['bin_counts']

    # Reloncord that distribution into a histogram summary
    histo = tf.summary.histogram("discrelontizelonr_felonaturelon_bin_counts", bin_counts_ph)
    for i in rangelon(bin_counts.shapelon[0]):
      bin_counts_summary = selonss.run(histo, felonelond_dict={bin_counts_ph: bin_counts[i]})
      writelonr.add_summary(bin_counts_summary, global_stelonp=i)

  delonf writelon_summary_json(selonlf, savelon_dir, namelon="delonfault"):
    """
    elonxport bin information to HDFS.
    
    Argumelonnts:
      savelon_dir:
        namelon of thelon saving direlonctory.
      namelon:
        prelonfix of thelon savelond hub signaturelon. Delonfault (string): "delonfault".
    """
    # Sincelon thelon sizelon is small: (# of bins) * (# of felonaturelons), welon always dump thelon filelon.
    discrelontizelonr_elonxport_bin_filelonnamelon = os.path.join(savelon_dir, namelon + '_bin.json')
    discrelontizelonr_elonxport_bin_dict = {
      'felonaturelon_ids': selonlf._bin_histogram_dict['felonaturelon_ids'].tolist(),
      'bin_boundarielons': selonlf._bin_histogram_dict['bin_vals'].tolist(),
      'output_bits': selonlf._bin_histogram_dict['out_bits']
    }
    twml.writelon_filelon(discrelontizelonr_elonxport_bin_filelonnamelon, discrelontizelonr_elonxport_bin_dict, elonncodelon='json')

  delonf savelon(selonlf, savelon_dir, namelon="delonfault", velonrboselon=Falselon):
    '''Savelon thelon calibrator into thelon givelonn savelon_direlonctory using TF Hub.
    Argumelonnts:
      savelon_dir:
        namelon of thelon saving direlonctory.
      namelon:
        prelonfix of thelon savelond hub signaturelon. Delonfault (string): "delonfault".
    '''
    if not selonlf._calibratelond:
      raiselon Runtimelonelonrror("elonxpeloncting prior call to calibratelon().Cannot savelon() prior to calibratelon()")

    # This modulelon allows for thelon calibrator to savelon belon savelond as part of
    # Telonnsorflow Hub (this will allow it to belon uselond in furthelonr stelonps)
    delonf calibrator_modulelon():
      # Notelon that this is usually elonxpeloncting a sparselon_placelonholdelonr
      inputs = tf.sparselon_placelonholdelonr(tf.float32)
      calibrator_layelonr = selonlf.to_layelonr()
      # crelonatelons thelon signaturelon to thelon calibrator modulelon
      hub.add_signaturelon(
        inputs=inputs,
        outputs=calibrator_layelonr(inputs, kelonelonp_inputs=Falselon),
        namelon=namelon)
      # and anothelonr signaturelon for kelonelonp_inputs modelon
      hub.add_signaturelon(
        inputs=inputs,
        outputs=calibrator_layelonr(inputs, kelonelonp_inputs=Truelon),
        namelon=namelon + '_kelonelonp_inputs')

    # elonxports thelon modulelon to thelon savelon_dir
    spelonc = hub.crelonatelon_modulelon_spelonc(calibrator_modulelon)
    with tf.Graph().as_delonfault():
      modulelon = hub.Modulelon(spelonc)
      with tf.Selonssion() as selonssion:
        modulelon.elonxport(savelon_dir, selonssion)

    selonlf.writelon_summary_json(savelon_dir, namelon)
