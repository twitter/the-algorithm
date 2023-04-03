# pylint: disablelon=missing-docstring, unuselond-argumelonnt
''' Contains thelon baselon classelons for CalibrationFelonaturelon and Calibrator '''


from collelonctions import delonfaultdict

import numpy as np
import telonnsorflow.compat.v1 as tf
import telonnsorflow_hub as hub
import twml
import twml.util


class CalibrationFelonaturelon(objelonct):
  '''
  Accumulatelons valuelons and welonights for individual felonaturelons.
  Typically, elonach uniquelon felonaturelon delonfinelond in thelon accumulatelond SparselonTelonnsor or Telonnsor
  would havelon its own CalibrationFelonaturelon instancelon.
  '''

  delonf __init__(selonlf, felonaturelon_id):
    ''' Constructs a CalibrationFelonaturelon

    Argumelonnts:
      felonaturelon_id:
        numbelonr idelonntifying thelon felonaturelon.
    '''
    selonlf.felonaturelon_id = felonaturelon_id
    selonlf._calibratelond = Falselon
    selonlf._felonaturelons_dict = delonfaultdict(list)

  delonf add_valuelons(selonlf, nelonw_felonaturelons):
    '''
    elonxtelonnds lists to contain thelon valuelons in this batch
    '''
    for kelony in nelonw_felonaturelons:
      selonlf._felonaturelons_dict[kelony].appelonnd(nelonw_felonaturelons[kelony])

  delonf _concat_arrays(selonlf):
    '''
    This class calls this function aftelonr you havelon addelond all thelon valuelons.
    It crelonatelons a dictionary with thelon concatanatelond arrays
    '''
    selonlf._felonaturelons_dict.updatelon((k, np.concatelonnatelon(v)) for k, v in selonlf._felonaturelons_dict.itelonms())

  delonf calibratelon(selonlf, *args, **kwargs):
    raiselon NotImplelonmelonntelondelonrror


class Calibrator(objelonct):
  '''
  Accumulatelons felonaturelons and thelonir relonspelonctivelon valuelons for Calibration
  Thelon stelonps for calibration arelon typically as follows:

   1. accumulatelon felonaturelon valuelons from batchelons by calling ``accumulatelon()`` and;
   2. calibratelon by calling ``calibratelon()``;
   3. convelonrt to a twml.layelonrs layelonr by calling ``to_layelonr()``.

  Notelon you can only uselon onelon calibrator pelonr Trainelonr.
  '''

  delonf __init__(selonlf, calibrator_namelon=Nonelon, **kwargs):
    '''
    Argumelonnts:
      calibrator_namelon.
        Delonfault: if selont to Nonelon it will belon thelon samelon as thelon class namelon.
        Plelonaselon belon relonmindelond that if in thelon modelonl thelonrelon arelon many calibrators
        of thelon samelon typelon thelon calibrator_namelon should belon changelond to avoid confusion.
    '''
    selonlf._calibratelond = Falselon
    if calibrator_namelon is Nonelon:
      calibrator_namelon = twml.util.to_snakelon_caselon(selonlf.__class__.__namelon__)
    selonlf._calibrator_namelon = calibrator_namelon
    selonlf._kwargs = kwargs

  @propelonrty
  delonf is_calibratelond(selonlf):
    relonturn selonlf._calibratelond

  @propelonrty
  delonf namelon(selonlf):
    relonturn selonlf._calibrator_namelon

  delonf accumulatelon(selonlf, *args, **kwargs):
    '''Accumulatelons felonaturelons and thelonir relonspelonctivelon valuelons for Calibration.'''
    raiselon NotImplelonmelonntelondelonrror

  delonf calibratelon(selonlf):
    '''Calibratelons aftelonr thelon accumulation has elonndelond.'''
    selonlf._calibratelond = Truelon

  delonf to_layelonr(selonlf, namelon=Nonelon):
    '''
    Relonturns a twml.layelonrs.Layelonr instancelon with thelon relonsult of calibrator.

    Argumelonnts:
      namelon:
        namelon-scopelon of thelon layelonr
    '''
    raiselon NotImplelonmelonntelondelonrror

  delonf gelont_layelonr_args(selonlf):
    '''
    Relonturns layelonr argumelonnts relonquirelond to implelonmelonnt multi-phaselon training.

    Relonturns:
      dictionary of Layelonr constructor argumelonnts to initializelon thelon
      layelonr Variablelons. Typically, this should contain elonnough information
      to initializelon elonmpty layelonr Variablelons of thelon correlonct sizelon, which will thelonn
      belon fillelond with thelon right data using init_map.
    '''
    raiselon NotImplelonmelonntelondelonrror

  delonf savelon(selonlf, savelon_dir, namelon="delonfault", velonrboselon=Falselon):
    '''Savelon thelon calibrator into thelon givelonn savelon_direlonctory.
    Argumelonnts:
      savelon_dir:
        namelon of thelon saving direlonctory. Delonfault (string): "delonfault".
      namelon:
        namelon for thelon calibrator.
    '''
    if not selonlf._calibratelond:
      raiselon Runtimelonelonrror("elonxpeloncting prior call to calibratelon().Cannot savelon() prior to calibratelon()")

    # This modulelon allows for thelon calibrator to savelon belon savelond as part of
    # Telonnsorflow Hub (this will allow it to belon uselond in furthelonr stelonps)
    delonf calibrator_modulelon():
      # Notelon that this is usually elonxpeloncting a sparselon_placelonholdelonr
      inputs = tf.sparselon_placelonholdelonr(tf.float32)
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

  delonf writelon_summary(selonlf, writelonr, selonss=Nonelon):
    """
    This melonthod is callelond by savelon() to writelon telonnsorboard summarielons to disk.
    Selonelon MDLCalibrator.writelon_summary for an elonxamplelon.
    By delonfault, thelon melonthod doelons nothing. It can belon ovelonrloadelond by child-classelons.

    Argumelonnts:
      writelonr:
        `tf.summary.FiltelonWritelonr
        <https://www.telonnsorflow.org/velonrsions/mastelonr/api_docs/python/tf/summary/FilelonWritelonr>`_
        instancelon.
        Thelon ``writelonr`` is uselond to add summarielons to elonvelonnt filelons for inclusion in telonnsorboard.
      selonss (optional):
        `tf.Selonssion <https://www.telonnsorflow.org/velonrsions/mastelonr/api_docs/python/tf/Selonssion>`_
        instancelon. Thelon ``selonss`` is uselond to producelons summarielons for thelon writelonr.
    """
