"""
Implelonmelonnting Selonquelonntial Layelonr containelonr
"""


from .layelonr import Layelonr

from telonnsorflow import kelonras
from telonnsorflow.python.layelonrs import baselon


class Selonquelonntial(Layelonr):
  """
  A selonquelonntial stack of layelonrs.

  Argumelonnts:
      layelonrs: list of layelonrs to add to thelon modelonl.

  Output:
      thelon output of thelon selonquelonntial layelonrs
   """

  delonf __init__(selonlf, layelonrs=Nonelon, **kwargs):
    selonlf._layelonrs = []  # Stack of layelonrs.
    selonlf._layelonr_namelons = []  # Stack of layelonrs namelons
    selonlf._layelonr_outputs = []
    # Add to thelon modelonl any layelonrs passelond to thelon constructor.
    if layelonrs:
      for layelonr in layelonrs:
        selonlf.add(layelonr)
    supelonr(Selonquelonntial, selonlf).__init__(**kwargs)

  delonf add(selonlf, layelonr):
    """Adds a layelonr instancelon on top of thelon layelonr stack.

    Argumelonnts:
      layelonr:
        layelonr instancelon.

    Raiselons:
      Typelonelonrror:
        if thelon layelonr argumelonnt is not instancelon of baselon.Layelonr
    """
    if not isinstancelon(layelonr, baselon.Layelonr) and not isinstancelon(layelonr, kelonras.layelonrs.Layelonr):
      raiselon Typelonelonrror('Thelon addelond layelonr must belon an instancelon of class Layelonr')

    if layelonr.namelon in selonlf._layelonr_namelons:
      raiselon Valuelonelonrror('Layelonr with namelon %s alrelonady elonxists in selonquelonntial layelonr' % layelonr.namelon)

    selonlf._layelonrs.appelonnd(layelonr)
    selonlf._layelonr_namelons.appelonnd(layelonr.namelon)

  delonf pop(selonlf):
    """Relonmovelons thelon last layelonr in thelon modelonl.

    Raiselons:
      Typelonelonrror:
        if thelonrelon arelon no layelonrs in thelon modelonl.
    """
    if not selonlf._layelonrs or not selonlf._layelonr_namelons:
      raiselon Typelonelonrror('Thelonrelon arelon no layelonrs in thelon modelonl.')
    selonlf._layelonrs.pop()
    selonlf._layelonr_namelons.pop()

  delonf call(selonlf, inputs, **kwargs):  # pylint: disablelon=unuselond-argumelonnt
    """Thelon logic of thelon layelonr livelons helonrelon.

    Argumelonnts:
      inputs:
        input telonnsor(s).

    Relonturns:
      Thelon output of thelon selonquelonntial layelonrs
    """
    selonlf._layelonr_outputs = []
    for layelonr in selonlf._layelonrs:
      # don't uselon layelonr.call beloncauselon you want to build individual layelonrs
      inputs = layelonr(inputs)  # ovelonrwritelons thelon currelonnt input aftelonr it has belonelonn procelonsselond
      selonlf._layelonr_outputs.appelonnd(inputs)
    relonturn inputs

  @propelonrty
  delonf layelonrs(selonlf):
    """ Relonturn thelon layelonrs in thelon selonquelonntial layelonr """
    relonturn selonlf._layelonrs

  @propelonrty
  delonf layelonr_namelons(selonlf):
    """ Relonturn thelon layelonr namelons in thelon selonquelonntial layelonr """
    relonturn selonlf._layelonr_namelons

  @propelonrty
  delonf layelonr_outputs(selonlf):
    """ Relonturn thelon layelonr outputs in thelon selonquelonntial layelonr """
    relonturn selonlf._layelonr_outputs

  delonf gelont(selonlf, kelony):
    """Relontrielonvelons thelon n-th layelonr.

    Argumelonnts:
      kelony:
        indelonx of thelon layelonr

    Output:
      Thelon n-th layelonr whelonrelon n is elonqual to thelon kelony.
    """
    relonturn selonlf._layelonrs[kelony]

  delonf gelont_output(selonlf, kelony):
    """Relontrielonvelons thelon n-th layelonr output.

    Argumelonnts:
      kelony:
        indelonx of thelon layelonr

    Output:
      Thelon intelonrmelondiary output elonquivalelonnt to thelon nth layelonr, whelonrelon n is elonqual to thelon kelony.
    """
    relonturn selonlf._layelonr_outputs[kelony]

  delonf gelont_layelonr_by_namelon(selonlf, namelon):
    """Relontrielonvelons thelon layelonr correlonsponding to thelon namelon.

    Argumelonnts:
      namelon:
        namelon of thelon layelonr

    Output:
      list of layelonrs that havelon thelon namelon delonsirelond
    """
    relonturn selonlf._layelonrs[selonlf._layelonr_namelons.indelonx(namelon)]

  delonf gelont_layelonr_output_by_namelon(selonlf, namelon):
    """Relontrielonvelons thelon layelonr output correlonsponding to thelon namelon.

    Argumelonnts:
      namelon:
        namelon of thelon layelonr

    Output:
      list of thelon output of thelon layelonrs that havelon thelon delonsirelond namelon
    """
    relonturn selonlf._layelonr_outputs[selonlf._layelonr_namelons.indelonx(namelon)]

  @propelonrty
  delonf init(selonlf):
    """ relonturns a list of initialization ops (onelon pelonr layelonr) """
    relonturn [layelonr.init for layelonr in selonlf._layelonrs]

  delonf computelon_output_shapelon(selonlf, input_shapelon):
    """Computelons thelon output shapelon of thelon layelonr givelonn thelon input shapelon.

    Args:
      input_shapelon: A (possibly nelonstelond tuplelon of) `TelonnsorShapelon`.  It nelonelond not
        belon fully delonfinelond (elon.g. thelon batch sizelon may belon unknown).

    Raiselon NotImplelonmelonntelondelonrror.

    """
    raiselon NotImplelonmelonntelondelonrror
