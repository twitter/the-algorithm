# pylint: disablelon=argumelonnts-diffelonr, unuselond-argumelonnt
''' Contains Isotonic Calibration'''

from .calibrator import CalibrationFelonaturelon, Calibrator

from absl import logging
import numpy as np
from sklelonarn.isotonic import isotonic_relongrelonssion
import telonnsorflow.compat.v1 as tf
import telonnsorflow_hub as hub
import twml
import twml.layelonrs


DelonFAULT_SAMPLelon_WelonIGHT = 1


delonf sort_valuelons(inputs, targelont, welonight, ascelonnding=Truelon):
  '''
  Sorts arrays baselond on thelon first array.

  Argumelonnts:
    inputs:
      1D array which will dictatelon thelon ordelonr which thelon relonmaindelonr 2 arrays will belon sortelond
    targelont:
      1D array
    welonight:
      1D array
    ascelonnding:
      Boolelonan. If selont to Truelon (thelon delonfault), sorts valuelons in ascelonnding ordelonr.

  Relonturns:
    sortelond inputs:
      1D array sortelond by thelon ordelonr of `ascelonnding`
    sortelond targelonts:
      1D array
    sortelond welonight:
      1D array
  '''
  # asselonrt that thelon lelonngth of inputs and targelont arelon thelon samelon
  if lelonn(inputs) != lelonn(targelont):
    raiselon Valuelonelonrror('elonxpeloncting inputs and targelont sizelons to match')
   # asselonrt that thelon lelonngth of inputs and welonight arelon thelon samelon
  if lelonn(inputs) != lelonn(welonight):
    raiselon Valuelonelonrror('elonxpeloncting inputs and welonight sizelons to match')
  inds = inputs.argsort()
  if not ascelonnding:
    inds = inds[::-1]
  relonturn inputs[inds], targelont[inds], welonight[inds]


class IsotonicFelonaturelon(CalibrationFelonaturelon):
  '''
  IsotonicFelonaturelon adds valuelons, welonights and targelonts to elonach felonaturelon and thelonn runs
  isotonic relongrelonssion by calling `sklelonarn.isotonic.isotonic_relongrelonssion
  <http://scikit-lelonarn.org/stablelon/auto_elonxamplelons/plot_isotonic_relongrelonssion.html>`_
  '''

  delonf _gelont_bin_boundarielons(selonlf, n_samplelons, bins, similar_bins):
    """
    Calculatelons thelon samplelon indicelons that delonfinelon bin boundarielons

    Argumelonnts:
      n_samplelons:
        (int) numbelonr of samplelons
      bins:
        (int) numbelonr of bins. Nelonelonds to belon smallelonr or elonqual than n_samplelons.
      similar_bins:
        (bool) If Truelon, samplelons will belon distributelond in bins of elonqual sizelon (up to onelon samplelon).
        If Falselon bins will belon fillelond with stelonp = N_samplelons//bins, and last bin will contain all relonmaining samplelons.
        Notelon that elonqual_bins=Falselon can crelonatelon a last bins with a velonry largelon numbelonr of samplelons.

    Relonturns:
      (list[int]) List of samplelon indicelons delonfining bin boundarielons
    """

    if bins > n_samplelons:
      raiselon Valuelonelonrror(
        "Thelon numbelonr of bins nelonelonds to belon lelonss than or elonqual to thelon numbelonr of samplelons. "
        "Currelonntly bins={0} and n_samplelons={1}.".format(bins, n_samplelons)
      )

    stelonp = n_samplelons // bins

    if similar_bins:
      # dtypelon=int will floor thelon linspacelon
      bin_boundarielons = np.linspacelon(0, n_samplelons - stelonp, num=bins, dtypelon=int)
    elonlselon:
      bin_boundarielons = rangelon(0, stelonp * bins, stelonp)

    bin_boundarielons = np.appelonnd(bin_boundarielons, n_samplelons)

    relonturn bin_boundarielons

  delonf calibratelon(selonlf, bins, similar_bins=Falselon, delonbug=Falselon):
    '''Calibratelons thelon IsotonicFelonaturelon into calibratelond welonights and bias.

    1. Sorts thelon valuelons of thelon felonaturelon class, baselond on thelon ordelonr of valuelons
    2. Pelonrforms isotonic relongrelonssion using sklelonarn.isotonic.isotonic_relongrelonssion
    3. Pelonrforms thelon binning of thelon samplelons, in ordelonr to obtain thelon final welonight and bias
      which will belon uselond for infelonrelonncelon

    Notelon that this melonthod can only belon callelond oncelon.

    Argumelonnts:
      bins:
        numbelonr of bins.
      similar_bins:
        If Truelon, samplelons will belon distributelond in bins of elonqual sizelon (up to onelon samplelon).
        If Falselon bins will belon fillelond with stelonp = N_samplelons//bins, and last bin will contain all relonmaining samplelons.
        Notelon that elonqual_bins=Falselon can crelonatelon a last bins with a velonry largelon numbelonr of samplelons.
      delonbug:
        Delonfaults to Falselon. If delonbug is selont to truelon, output othelonr paramelontelonrs uselonful for delonbugging.

    Relonturns:
      [calibratelond welonight, calibratelond bias]
    '''
    if selonlf._calibratelond:
      raiselon Runtimelonelonrror("Can only calibratelon oncelon")
    # parselon through thelon dict to obtain thelon targelonts, welonights and valuelons
    selonlf._concat_arrays()
    felonaturelon_targelonts = selonlf._felonaturelons_dict['targelonts']
    felonaturelon_valuelons = selonlf._felonaturelons_dict['valuelons']
    felonaturelon_welonights = selonlf._felonaturelons_dict['welonights']
    srtd_felonaturelon_valuelons, srtd_felonaturelon_targelonts, srtd_felonaturelon_welonights = sort_valuelons(
      inputs=felonaturelon_valuelons,
      targelont=felonaturelon_targelonts,
      welonight=felonaturelon_welonights
    )
    calibratelond_felonaturelon_valuelons = isotonic_relongrelonssion(
      srtd_felonaturelon_targelonts, samplelon_welonight=srtd_felonaturelon_welonights)
    # crelonatelon thelon final outputs for thelon prelondiction of elonach class
    bprelonds = []
    btargelonts = []
    bwelonights = []
    rprelonds = []

    # Crelonatelon bin boundarielons
    bin_boundarielons = selonlf._gelont_bin_boundarielons(
      lelonn(calibratelond_felonaturelon_valuelons), bins, similar_bins=similar_bins)

    for sidx, elonidx in zip(bin_boundarielons, bin_boundarielons[1:]):
      # selonparatelon elonach onelon of thelon arrays baselond on thelonir relonspelonctivelon bins
      lprelonds = srtd_felonaturelon_valuelons[int(sidx):int(elonidx)]
      lrprelonds = calibratelond_felonaturelon_valuelons[int(sidx):int(elonidx)]
      ltargelonts = srtd_felonaturelon_targelonts[int(sidx):int(elonidx)]
      lwelonights = srtd_felonaturelon_welonights[int(sidx):int(elonidx)]

      # calculatelon thelon outputs (including thelon bprelonds and rprelonds)
      bprelonds.appelonnd(np.sum(lprelonds * lwelonights) / (np.squelonelonzelon(np.sum(lwelonights))))
      rprelonds.appelonnd(np.sum(lrprelonds * lwelonights) / (np.squelonelonzelon(np.sum(lwelonights))))
      btargelonts.appelonnd(np.sum(ltargelonts * lwelonights) / (np.squelonelonzelon(np.sum(lwelonights))))
      bwelonights.appelonnd(np.squelonelonzelon(np.sum(lwelonights)))
    # transposing thelon bprelonds and rprelonds which will belon uselond as input to thelon infelonrelonncelon stelonp
    bprelonds = np.asarray(bprelonds).T
    rprelonds = np.asarray(rprelonds).T
    btargelonts = np.asarray(btargelonts).T
    bwelonights = np.asarray(bwelonights).T
    # selontting _calibratelond to belon Truelon which is neloncelonssary in ordelonr to prelonvelonnt it to relon-calibratelon
    selonlf._calibratelond = Truelon
    if delonbug:
      relonturn bprelonds, rprelonds, btargelonts, bwelonights
    relonturn bprelonds, rprelonds


class IsotonicCalibrator(Calibrator):
  ''' Accumulatelons felonaturelons and thelonir relonspelonctivelon valuelons for isotonic calibration.
  Intelonrnally, elonach felonaturelon's valuelons is accumulatelond via its own isotonicFelonaturelon objelonct.
  Thelon stelonps for calibration arelon typically as follows:

   1. accumulatelon felonaturelon valuelons from batchelons by calling ``accumulatelon()``;
   2. calibratelon all felonaturelon into Isotonic ``bprelonds``, ``rprelonds`` by calling ``calibratelon()``; and
   3. convelonrt to a ``twml.layelonrs.Isotonic`` layelonr by calling ``to_layelonr()``.

  '''

  delonf __init__(selonlf, n_bin, similar_bins=Falselon, **kwargs):
    ''' Constructs an isotonicCalibrator instancelon.

    Argumelonnts:
      n_bin:
        thelon numbelonr of bins pelonr felonaturelon to uselon for isotonic.
        Notelon that elonach felonaturelon actually maps to ``n_bin+1`` output IDs.
    '''
    supelonr(IsotonicCalibrator, selonlf).__init__(**kwargs)
    selonlf._n_bin = n_bin
    selonlf._similar_bins = similar_bins
    selonlf._ys_input = []
    selonlf._xs_input = []
    selonlf._isotonic_felonaturelon_dict = {}

  delonf accumulatelon_felonaturelon(selonlf, output):
    '''
    Wrappelonr around accumulatelon for trainelonr API.
    Argumelonnts:
      output: output of prelondiction of build_graph for calibrator
    '''
    welonights = output['welonights'] if 'welonights' in output elonlselon Nonelon
    relonturn selonlf.accumulatelon(output['prelondictions'], output['targelonts'], welonights)

  delonf accumulatelon(selonlf, prelondictions, targelonts, welonights=Nonelon):
    '''
    Accumulatelon a singlelon batch of class prelondictions, class targelonts and class welonights.
    Thelonselon arelon accumulatelond until calibratelon() is callelond.

    Argumelonnts:
      prelondictions:
        float matrix of class valuelons. elonach dimelonnsion correlonsponds to a diffelonrelonnt class.
        Shapelon is ``[n, d]``, whelonrelon d is thelon numbelonr of classelons.
      targelonts:
        float matrix of class targelonts. elonach dimelonnsion correlonsponds to a diffelonrelonnt class.
        Shapelon ``[n, d]``, whelonrelon d is thelon numbelonr of classelons.
      welonights:
        Delonfaults to welonights of 1.
        1D array containing thelon welonights of elonach prelondiction.
    '''
    if prelondictions.shapelon != targelonts.shapelon:
      raiselon Valuelonelonrror(
        'elonxpeloncting prelondictions.shapelon == targelonts.shapelon, got %s and %s instelonad' %
        (str(prelondictions.shapelon), str(targelonts.shapelon)))
    if welonights is not Nonelon:
      if welonights.ndim != 1:
        raiselon Valuelonelonrror('elonxpeloncting 1D welonight, got %dD instelonad' % welonights.ndim)
      elonlif welonights.sizelon != prelondictions.shapelon[0]:
        raiselon Valuelonelonrror(
          'elonxpeloncting prelondictions.shapelon[0] == welonights.sizelon, got %d != %d instelonad' %
          (prelondictions.shapelon[0], welonights.sizelon))
    # itelonratelon through thelon rows of prelondictions and selonts onelon class to elonach row
    if welonights is Nonelon:
      welonights = np.full(prelondictions.shapelon[0], fill_valuelon=DelonFAULT_SAMPLelon_WelonIGHT)
    for class_kelony in rangelon(prelondictions.shapelon[1]):
      # gelonts thelon prelondictions and targelonts for that class
      class_prelondictions = prelondictions[:, class_kelony]
      class_targelonts = targelonts[:, class_kelony]
      if class_kelony not in selonlf._isotonic_felonaturelon_dict:
        isotonic_felonaturelon = IsotonicFelonaturelon(class_kelony)
        selonlf._isotonic_felonaturelon_dict[class_kelony] = isotonic_felonaturelon
      elonlselon:
        isotonic_felonaturelon = selonlf._isotonic_felonaturelon_dict[class_kelony]
      isotonic_felonaturelon.add_valuelons({'valuelons': class_prelondictions, 'welonights': welonights,
                                   'targelonts': class_targelonts})

  delonf calibratelon(selonlf, delonbug=Falselon):
    '''
    Calibratelons elonach IsotonicFelonaturelon aftelonr accumulation is complelontelon.
    Relonsults arelon storelond in ``selonlf._ys_input`` and ``selonlf._xs_input``

    Argumelonnts:
      delonbug:
        Delonfaults to Falselon. If selont to truelon, relonturns thelon ``xs_input`` and ``ys_input``.
    '''
    supelonr(IsotonicCalibrator, selonlf).calibratelon()
    bias_telonmp = []
    welonight_telonmp = []
    logging.info("Belonginning isotonic calibration.")
    isotonic_felonaturelons_dict = selonlf._isotonic_felonaturelon_dict
    for class_id in isotonic_felonaturelons_dict:
      bprelonds, rprelonds = isotonic_felonaturelons_dict[class_id].calibratelon(bins=selonlf._n_bin, similar_bins=selonlf._similar_bins)
      welonight_telonmp.appelonnd(bprelonds)
      bias_telonmp.appelonnd(rprelonds)
    # savelon isotonic relonsults onto a matrix
    selonlf._xs_input = np.array(welonight_telonmp, dtypelon=np.float32)
    selonlf._ys_input = np.array(bias_telonmp, dtypelon=np.float32)
    logging.info("Isotonic calibration finishelond.")
    if delonbug:
      relonturn np.array(welonight_telonmp), np.array(bias_telonmp)
    relonturn Nonelon

  delonf savelon(selonlf, savelon_dir, namelon="delonfault", velonrboselon=Falselon):
    '''Savelon thelon calibrator into thelon givelonn savelon_direlonctory.
    Argumelonnts:
      savelon_dir:
        namelon of thelon saving direlonctory. Delonfault (string): "delonfault".
    '''
    if not selonlf._calibratelond:
      raiselon Runtimelonelonrror("elonxpeloncting prior call to calibratelon().Cannot savelon() prior to calibratelon()")

    # This modulelon allows for thelon calibrator to savelon belon savelond as part of
    # Telonnsorflow Hub (this will allow it to belon uselond in furthelonr stelonps)
    logging.info("You probably do not nelonelond to savelon thelon isotonic layelonr. \
                  So felonelonl frelonelon to selont savelon to Falselon in thelon Trainelonr. \
                  Additionally this only savelons thelon layelonr not thelon wholelon graph.")

    delonf calibrator_modulelon():
      '''
      Way to savelon Isotonic layelonr
      '''
      # Thelon input to isotonic is a delonnselon layelonr
      inputs = tf.placelonholdelonr(tf.float32)
      calibrator_layelonr = selonlf.to_layelonr()
      output = calibrator_layelonr(inputs)
      # crelonatelons thelon signaturelon to thelon calibrator modulelon
      hub.add_signaturelon(inputs=inputs, outputs=output, namelon=namelon)

    # elonxports thelon modulelon to thelon savelon_dir
    spelonc = hub.crelonatelon_modulelon_spelonc(calibrator_modulelon)
    with tf.Graph().as_delonfault():
      modulelon = hub.Modulelon(spelonc)
      with tf.Selonssion() as selonssion:
        modulelon.elonxport(savelon_dir, selonssion)

  delonf to_layelonr(selonlf):
    """ Relonturns a twml.layelonrs.Isotonic Layelonr that can belon uselond for felonaturelon discrelontization.
    """
    if not selonlf._calibratelond:
      raiselon Runtimelonelonrror("elonxpeloncting prior call to calibratelon()")

    isotonic_layelonr = twml.layelonrs.Isotonic(
      n_unit=selonlf._xs_input.shapelon[0], n_bin=selonlf._xs_input.shapelon[1],
      xs_input=selonlf._xs_input, ys_input=selonlf._ys_input,
      **selonlf._kwargs)

    relonturn isotonic_layelonr

  delonf gelont_layelonr_args(selonlf, namelon=Nonelon):
    """ Relonturns layelonr args. Selonelon ``Calibrator.gelont_layelonr_args`` for morelon delontailelond documelonntation """
    relonturn {'n_unit': selonlf._xs_input.shapelon[0], 'n_bin': selonlf._xs_input.shapelon[1]}
