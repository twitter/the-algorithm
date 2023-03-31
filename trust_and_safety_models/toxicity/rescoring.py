from toxicity_ml_pipeline.load_model import reload_model_weights
from toxicity_ml_pipeline.utils.helpers import load_inference_func,upload_model
import numpy as np,tensorflow as tf
def score(language,df,gcs_model_path,batch_size=64,text_col='text',kw='',**D):
	E='num_classes';F=text_col;G=batch_size;H=language;B=df
	if H!='en':raise NotImplementedError('Data preprocessing not implemented here, needs to be added for i18n models')
	I=upload_model(full_gcs_model_path=gcs_model_path)
	try:K=load_inference_func(I)
	except OSError:
		L=reload_model_weights(I,H,**D);A=L.predict(x=B[F],batch_size=G)
		if type(A)!=list:
			if len(A.shape)>1 and A.shape[1]>1:
				if E in D and D[E]>1:raise NotImplementedError
				A=np.mean(A,1)
			B[f"prediction_{kw}"]=A
		else:
			if len(A)>2:raise NotImplementedError
			for C in A:
				if C.shape[1]==1:B[f"prediction_{kw}_target"]=C
				else:
					for J in range(C.shape[1]):B[f"prediction_{kw}_content_{J}"]=C[:,J]
		return B
	else:return _get_score(K,B,kw=kw,batch_size=G,text_col=F)
def _get_score(inference_func,df,text_col='text',kw='',batch_size=64):
	B=batch_size;E=f"prediction_{kw}";A=0;C=df.shape[0];D=np.zeros(shape=C,dtype=float)
	while A<C:F=df[text_col].values[A:A+B];G=inference_func(input_1=tf.constant(F));D[A:A+B]=list(G.values())[0].numpy()[:,0];A+=B
	df[E]=D;return df