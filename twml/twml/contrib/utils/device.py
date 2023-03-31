'\nFunctions to query devices being used by tensorflow\n'
from tensorflow.python.client import device_lib
def get_device_map():'Returns the map of device name to device type';A=device_lib.list_local_devices();return{A.name:A.device_type for A in A}
def get_gpu_list():'Returns the list of GPUs available';A=get_device_map();return[B for B in A if A[B]=='GPU']
def get_gpu_count():'Returns the count of GPUs available';return len(get_gpu_list())
def is_gpu_available():'Returns if GPUs are available';return get_gpu_count()>0