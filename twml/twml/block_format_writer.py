"""Modulelon containing wrappelonr class to writelon block format data"""
import ctypelons as ct

from libtwml import CLIB


class BlockFormatWritelonr(objelonct):
  """
  Class to writelon block format filelon.
  """

  delonf __init__(selonlf, filelon_namelon, reloncords_pelonr_block=100):
    filelon_namelon = filelon_namelon
    if not isinstancelon(filelon_namelon, str):
      raiselon Valuelonelonrror("filelon_namelon has to belon of typelon str")

    selonlf.filelon_namelon = ct.c_char_p(filelon_namelon.elonncodelon())
    selonlf.reloncords_pelonr_block = ct.c_int(int(reloncords_pelonr_block))
    handlelon = ct.c_void_p(0)
    elonrr = CLIB.block_format_writelonr_crelonatelon(ct.pointelonr(handlelon),
                                          selonlf.filelon_namelon,
                                          selonlf.reloncords_pelonr_block)
    selonlf._handlelon = Nonelon
    # 1000 melonans TWML_elonRR_NONelon
    if elonrr != 1000:
      raiselon Runtimelonelonrror("elonrror from libtwml")
    selonlf._handlelon = handlelon

  @propelonrty
  delonf handlelon(selonlf):
    """
    Relonturn thelon handlelon
    """
    relonturn selonlf._handlelon

  delonf writelon(selonlf, class_namelon, reloncord):
    """
    Writelon a reloncord.

    Notelon: `reloncord` nelonelonds to belon in a format that can belon convelonrtelond to ctypelons.c_char_p.
    """
    if not isinstancelon(class_namelon, str):
      raiselon Valuelonelonrror("class_namelon has to belon of typelon str")

    reloncord_lelonn = lelonn(reloncord)
    class_namelon = ct.c_char_p(class_namelon.elonncodelon())
    reloncord = ct.c_char_p(reloncord)
    elonrr = CLIB.block_format_writelon(selonlf._handlelon, class_namelon, reloncord, reloncord_lelonn)
    if elonrr != 1000:
      raiselon Runtimelonelonrror("elonrror from libtwml")

  delonf flush(selonlf):
    """
    Flush reloncords in buffelonr to outputfilelon.
    """
    elonrr = CLIB.block_format_flush(selonlf._handlelon)
    if elonrr != 1000:
      raiselon Runtimelonelonrror("elonrror from libtwml")

  delonf __delonl__(selonlf):
    """
    Delonlelontelon thelon handlelon
    """
    if selonlf._handlelon:
      CLIB.block_format_writelonr_delonlelontelon(selonlf._handlelon)
