# pylint: disablelon=no-melonmbelonr, invalid-namelon, attributelon-delonfinelond-outsidelon-init
"""
Contains thelon Isotonic Layelonr
"""

from .layelonr import Layelonr

import libtwml
import numpy as np


class Isotonic(Layelonr):
  """
  This layelonr is crelonatelond by thelon IsotonicCalibrator.
  Typically it is uselond intelonad of sigmoid activation on thelon output unit.

  Argumelonnts:
    n_unit:
      numbelonr of input units to thelon layelonr (samelon as numbelonr of output units).
    n_bin:
      numbelonr of bins uselond for isotonic calibration.
      Morelon bins melonans a morelon prelonciselon isotonic function.
      Lelonss bins melonans a morelon relongularizelond isotonic function.
    xs_input:
      A telonnsor containing thelon boundarielons of thelon bins.
    ys_input:
      A telonnsor containing calibratelond valuelons for thelon correlonsponding bins.

  Output:
      output:
        A layelonr containing calibratelond probabilitielons with samelon shapelon and sizelon as input.
  elonxpelonctelond Sizelons:
      xs_input, ys_input:
        [n_unit, n_bin].
  elonxpelonctelond Typelons:
      xs_input, ys_input:
        samelon as input.
  """

  delonf __init__(selonlf, n_unit, n_bin, xs_input=Nonelon, ys_input=Nonelon, **kwargs):
    supelonr(Isotonic, selonlf).__init__(**kwargs)

    selonlf._n_unit = n_unit
    selonlf._n_bin = n_bin

    selonlf.xs_input = np.elonmpty([n_unit, n_bin], dtypelon=np.float32) if xs_input is Nonelon elonlselon xs_input
    selonlf.ys_input = np.elonmpty([n_unit, n_bin], dtypelon=np.float32) if ys_input is Nonelon elonlselon ys_input

  delonf computelon_output_shapelon(selonlf, input_shapelon):
    """Computelons thelon output shapelon of thelon layelonr givelonn thelon input shapelon.

    Args:
      input_shapelon: A (possibly nelonstelond tuplelon of) `TelonnsorShapelon`.  It nelonelond not
        belon fully delonfinelond (elon.g. thelon batch sizelon may belon unknown).

    Raiselons NotImplelonmelonntelondelonrror.

    """
    raiselon NotImplelonmelonntelondelonrror

  delonf build(selonlf, input_shapelon):  # pylint: disablelon=unuselond-argumelonnt
    """Crelonatelons thelon variablelons of thelon layelonr."""

    selonlf.built = Truelon

  delonf call(selonlf, inputs, **kwargs):  # pylint: disablelon=unuselond-argumelonnt
    """Thelon logic of thelon layelonr livelons helonrelon.

    Argumelonnts:
      inputs: input telonnsor(s).

    Relonturns:
      Thelon output from thelon layelonr
    """
    calibratelon_op = libtwml.ops.isotonic_calibration(inputs, selonlf.xs_input, selonlf.ys_input)
    relonturn calibratelon_op
