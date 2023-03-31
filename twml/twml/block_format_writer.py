'Module containing wrapper class to write block format data'
_A='Error from libtwml'
import ctypes as ct
from libtwml import CLIB
class BlockFormatWriter:
	'\n  Class to write block format file.\n  '
	def __init__(A,file_name,records_per_block=100):
		B=file_name;B=B
		if not isinstance(B,str):raise ValueError('file_name has to be of type str')
		A.file_name=ct.c_char_p(B.encode());A.records_per_block=ct.c_int(int(records_per_block));C=ct.c_void_p(0);D=CLIB.block_format_writer_create(ct.pointer(C),A.file_name,A.records_per_block);A._handle=None
		if D!=1000:raise RuntimeError(_A)
		A._handle=C
	@property
	def handle(self):'\n    Return the handle\n    ';return self._handle
	def write(C,class_name,record):
		'\n    Write a record.\n\n    Note: `record` needs to be in a format that can be converted to ctypes.c_char_p.\n    ';A=record;B=class_name
		if not isinstance(B,str):raise ValueError('class_name has to be of type str')
		D=len(A);B=ct.c_char_p(B.encode());A=ct.c_char_p(A);E=CLIB.block_format_write(C._handle,B,A,D)
		if E!=1000:raise RuntimeError(_A)
	def flush(A):
		'\n    Flush records in buffer to outputfile.\n    ';B=CLIB.block_format_flush(A._handle)
		if B!=1000:raise RuntimeError(_A)
	def __del__(A):
		'\n    Delete the handle\n    '
		if A._handle:CLIB.block_format_writer_delete(A._handle)