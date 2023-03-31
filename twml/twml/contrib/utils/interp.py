'\nInterpolation functions\n'
import libtwml,tensorflow.compat.v1 as tf,twml
def linear_interp1(inputs,ref_inputs,ref_outputs):
	'\n  Perform 1D linear interpolation.\n  Arguments:\n    inputs:\n      The query input values.\n    ref_inputs:\n      Reference grid points used for interpolation.\n    ref_outputs:\n      Reference output values used for interpolation.\n\n  Returns:\n    The interpolated outputs for the requested input values.\n  ';D=ref_outputs;A=ref_inputs;B=inputs;B=tf.convert_to_tensor(B);A=tf.convert_to_tensor(A);D=tf.convert_to_tensor(D);C=B.shape.ndims;E=A.shape.ndims;F=A.shape.ndims
	if E!=C:raise ValueError('Dimension mismatch. inputs: %d, ref_inputs: %d'%(C,E))
	if F!=C:raise ValueError('Dimension mismatch. inputs: %d, ref_outputs: %d'%(C,F))
	if C>2:raise ValueError('Input dimensions should be < 2D. But got %d.'%C)
	G=tf.shape(B);B=tf.reshape(B,[-1,1]);A=tf.reshape(A,[1,-1]);D=tf.reshape(D,[1,-1]);H=libtwml.ops.isotonic_calibration(B,A,D);return tf.reshape(H,G)
def linear_interp1_by_class(inputs,input_classes,ref_inputs,ref_outputs):
	'\n  Perform 1D linear interpolation.\n  Arguments:\n    inputs:\n      The query input values.\n    input_classes:\n      The class index to use from the reference grid.\n    ref_inputs:\n      Reference 2D grid points used for interpolation.\n      Each row denotes the grid from a different class.\n    ref_outputs:\n      Reference 2D output values used for interpolation.\n      Each row denotes the grid from a different class.\n\n  Returns:\n    The interpolated outputs for the requested input values.\n  ';B=ref_outputs;C=ref_inputs;D=input_classes;A=inputs;A=tf.convert_to_tensor(A);D=tf.convert_to_tensor(D);C=tf.convert_to_tensor(C);B=tf.convert_to_tensor(B);E=tf.shape(A)
	def F(x):return x
	def G(i,fn):A=D[i];E=tf.expand_dims(fn(),axis=0);return linear_interp1(E,C[A],B[A])
	H=twml.util.batch_apply(F,A,cond_func=G);return tf.reshape(H,E)