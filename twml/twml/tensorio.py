_B='tensor'
_A='type'
import os,numpy as np,yaml
'\nUtility to load tensors serialized by Deepbird V1.\n\nNote that Deepbird V1 serialize tensor names as "weight".\'1\'.\nFor user-friendliness, the quotes are removed from the tensor names.\n'
class _KeyRecorder:
	def __init__(A,tensorio,keys=[]):A.tensorio=tensorio;A.keys=keys
	def __getitem__(A,k):
		C=A.keys+[str(k)];B='.'.join(C);D=A.tensorio.list_tensors()
		if B in D:return A.tensorio._load(B)
		for E in D:
			if E.startswith(B):return _KeyRecorder(A.tensorio,C)
		raise ValueError('Key not found: '+B)
def _get_data_type(data_type):
	A=data_type
	if A=='Double':return np.float64,8
	if A=='Float':return np.float32,4
	if A=='Int':return np.int32,4
	if A=='Long':return np.int64,8
	if A=='Byte':return np.int8,1
	raise ValueError('Unexpected tensorio data type: '+A)
class TensorIO:
	'\n  Construct a TensorIO class.\n  tensorio_path: a directory containing tensors serialized using tensorio. tar file not supported.\n  mmap_tensor:\n    By default, loaded tensors use mmap storage.\n    Set this to false to not use mmap. Useful when loading multiple tensors.\n  '
	def __init__(A,tensorio_path,mmap_tensor=True):
		B=tensorio_path;A._tensorio_path=B;A._mmap_tensor=mmap_tensor;C=os.path.join(B,'spec.yaml')
		if not os.path.exists(C):raise ValueError('Invalid tensorio path: no spec.yaml found.')
		with open(C,'r')as D:E=yaml.safe_load(D);A._spec={A.replace("'",'').replace('"',''):B for(A,B)in E.items()}
	def list_tensors(A):'\n    Returns a list of tensors saved in the given path.\n    ';return A._spec.keys()
	def _load_tensor(B,name):
		'\n    Load Tensor with the given name.\n    Raise value error if the named tensor is not found.\n    Returns a numpy array if the named tensor is found.\n    ';A=B._spec[name]
		if A[_A]!=_B:raise ValueError('Trying to load a tensor of unknown type: '+A[_A])
		D=os.path.join(B._tensorio_path,A['filename']);E,F=_get_data_type(A['tensorType']);C=np.memmap(D,dtype=E,mode='r',offset=(A['offset']-1)*F,shape=tuple(A['size']),order='C');return C if B._mmap_tensor else C[:].copy()
	def _load_nontensor_data(A,name):'\n    Load non-tensor data with the given name.\n    Returns a python string.\n    ';B=A._spec[name];return B['data']
	def _load(A,name):
		'\n    Load data serialized under the given name, it could be a tensor or regular data.\n    ';B=name
		if B not in A._spec:raise ValueError('The specified key {} is not found in {}'.format(B,A._tensorio_path))
		C=A._spec[B][_A]
		if C==_B:return A._load_tensor(B)
		else:return A._load_nontensor_data(B)
	def load_all(A):'\n    Load all tensors stored in the tensorio directory.\n    Returns a dictionary from tensor name to numpy arrays.\n    ';return{B:A._load(B)for B in A._spec}
	def __getitem__(A,k):
		"\n    Shorthand for _load_tensor, but also supports hierarchical access like: tensorio['a']['b']['1']\n    "
		if k in A._spec:return A._load_tensor(k)
		else:return _KeyRecorder(A)[k]