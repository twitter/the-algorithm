class LollyModelReader:
	def __init__(A,lolly_model_file_path):A._lolly_model_file_path=lolly_model_file_path
	def read(A,process_line_fn):
		with open(A._lolly_model_file_path,'r')as B:
			for C in B:process_line_fn(C)