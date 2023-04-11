"""Module containing wrapper class to write block format data"""
import ctypes as ct

from libtwml import CLIB


class BlockFormatWriter(object):
  """
  Class to write block format file.
  """

  def __init__(self, file_name, records_per_block=100):
    file_name = file_name
    if not isinstance(file_name, str):
      raise ValueError("file_name has to be of type str")

    self.file_name = ct.c_char_p(file_name.encode())
    self.records_per_block = ct.c_int(int(records_per_block))
    handle = ct.c_void_p(0)
    err = CLIB.block_format_writer_create(ct.pointer(handle),
                                          self.file_name,
                                          self.records_per_block)
    self._handle = None
    # 1000 means TWML_ERR_NONE
    if err != 1000:
      raise RuntimeError("Error from libtwml")
    self._handle = handle

  @property
  def handle(self):
    """
    Return the handle
    """
    return self._handle

  def write(self, class_name, record):
    """
    Write a record.

    Note: `record` needs to be in a format that can be converted to ctypes.c_char_p.
    """
    if not isinstance(class_name, str):
      raise ValueError("class_name has to be of type str")

    record_len = len(record)
    class_name = ct.c_char_p(class_name.encode())
    record = ct.c_char_p(record)
    err = CLIB.block_format_write(self._handle, class_name, record, record_len)
    if err != 1000:
      raise RuntimeError("Error from libtwml")

  def flush(self):
    """
    Flush records in buffer to outputfile.
    """
    err = CLIB.block_format_flush(self._handle)
    if err != 1000:
      raise RuntimeError("Error from libtwml")

  def __del__(self):
    """
    Delete the handle
    """
    if self._handle:
      CLIB.block_format_writer_delete(self._handle)
