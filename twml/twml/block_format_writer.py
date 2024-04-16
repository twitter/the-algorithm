"""Module conta n ng wrapper class to wr e block format data"""
 mport ctypes as ct

from l btwml  mport CL B


class BlockFormatWr er(object):
  """
  Class to wr e block format f le.
  """

  def __ n __(self, f le_na , records_per_block=100):
    f le_na  = f le_na 
     f not  s nstance(f le_na , str):
      ra se ValueError("f le_na  has to be of type str")

    self.f le_na  = ct.c_char_p(f le_na .encode())
    self.records_per_block = ct.c_ nt( nt(records_per_block))
    handle = ct.c_vo d_p(0)
    err = CL B.block_format_wr er_create(ct.po nter(handle),
                                          self.f le_na ,
                                          self.records_per_block)
    self._handle = None
    # 1000  ans TWML_ERR_NONE
     f err != 1000:
      ra se Runt  Error("Error from l btwml")
    self._handle = handle

  @property
  def handle(self):
    """
    Return t  handle
    """
    return self._handle

  def wr e(self, class_na , record):
    """
    Wr e a record.

    Note: `record` needs to be  n a format that can be converted to ctypes.c_char_p.
    """
     f not  s nstance(class_na , str):
      ra se ValueError("class_na  has to be of type str")

    record_len = len(record)
    class_na  = ct.c_char_p(class_na .encode())
    record = ct.c_char_p(record)
    err = CL B.block_format_wr e(self._handle, class_na , record, record_len)
     f err != 1000:
      ra se Runt  Error("Error from l btwml")

  def flush(self):
    """
    Flush records  n buffer to outputf le.
    """
    err = CL B.block_format_flush(self._handle)
     f err != 1000:
      ra se Runt  Error("Error from l btwml")

  def __del__(self):
    """
    Delete t  handle
    """
     f self._handle:
      CL B.block_format_wr er_delete(self._handle)
