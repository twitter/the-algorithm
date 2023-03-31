_B='full_mask'
_A=None
import tensorflow.compat.v1 as tf
from twml.contrib.utils import masks,math_fns
def get_pair_loss(pairwise_label_scores,pairwise_predicted_scores,params):
	'\n  Paiwise learning-to-rank ranknet loss\n  Check paper https://www.microsoft.com/en-us/research/publication/\n  learning-to-rank-using-gradient-descent/\n  for more information\n  Args:\n    pairwise_label_scores: a dense tensor of shape [n_data, n_data]\n    pairwise_predicted_scores: a dense tensor of shape [n_data, n_data]\n    n_data is the number of tweet candidates in a BatchPredictionRequest\n    params: network parameters\n  mask options: full_mask and diag_mask\n  Returns:\n    average loss over pairs defined by the masks\n  ';A=pairwise_label_scores;C=tf.shape(A)[0]
	if params.mask==_B:D,B=masks.full_mask(C,A)
	else:D,B=masks.diag_mask(C,A)
	E=tf.cond(tf.equal(B,0),lambda:0.0,lambda:_get_average_cross_entropy_loss(A,pairwise_predicted_scores,D,B));return E
def get_lambda_pair_loss(pairwise_label_scores,pairwise_predicted_scores,params,swapped_ndcg):
	'\n  Paiwise learning-to-rank lambdarank loss\n  faster than the previous gradient method\n  Note: this loss depends on ranknet cross-entropy\n  delta NDCG is applied to ranknet cross-entropy\n  Hence, it is still a gradient descent method\n  Check paper http://citeseerx.ist.psu.edu/viewdoc/\n  download?doi=10.1.1.180.634&rep=rep1&type=pdf for more information\n  for more information\n  Args:\n    pairwise_label_scores: a dense tensor of shape [n_data, n_data]\n    pairwise_predicted_scores: a dense tensor of shape [n_data, n_data]\n    n_data is the number of tweet candidates in a BatchPredictionRequest\n    params: network parameters\n    swapped_ndcg: swapped ndcg of shape [n_data, n_data]\n    ndcg values when swapping each pair in the prediction ranking order\n  mask options: full_mask and diag_mask\n  Returns:\n    average loss over pairs defined by the masks\n  ';A=pairwise_label_scores;C=tf.shape(A)[0]
	if params.mask==_B:D,B=masks.full_mask(C,A)
	else:D,B=masks.diag_mask(C,A)
	E=tf.cond(tf.equal(B,0),lambda:0.0,lambda:_get_average_cross_entropy_loss(A,pairwise_predicted_scores,D,B,swapped_ndcg));return E
def _get_average_cross_entropy_loss(pairwise_label_scores,pairwise_predicted_scores,mask,pair_count,swapped_ndcg=_A):
	'\n  Average the loss for a batchPredictionRequest based on a desired number of pairs\n  ';B=swapped_ndcg;A=tf.nn.sigmoid_cross_entropy_with_logits(labels=pairwise_label_scores,logits=pairwise_predicted_scores);A=mask*A
	if B is not _A:A=A*B
	A=tf.reduce_sum(A)/pair_count;return A
def get_listmle_loss(labels,predicted_scores):
	'\n  listwise learning-to-rank listMLE loss\n  Note: Simplified MLE formula is used in here (omit the proof in here)\n  \\sum_{s=1}^{n-1} (-predicted_scores + ln(\\sum_{i=s}^n exp(predicted_scores)))\n  n is tf.shape(predicted_scores)[0]\n  Check paper http://icml2008.cs.helsinki.fi/papers/167.pdf for more information\n  Args:\n    labels: a dense tensor of shape [n_data, 1]\n    n_data is the number of tweet candidates in a BatchPredictionRequest\n    predicted_scores: a dense tensor of same shape and type as labels\n  Returns:\n    average loss\n  ';B=predicted_scores;C=labels;C=tf.reshape(C,[-1,1]);D=tf.shape(C)[0];B=tf.reshape(B,[-1,1]);H=_get_ordered_predicted_scores(C,B,D);A=-1*tf.reduce_sum(B);E=tf.gather(H,[D-1]);E=tf.reshape(E,[]);A=tf.add(A,E);F=tf.exp(H);G=tf.reduce_sum(F);A=tf.add(A,math_fns.safe_log(G));I=tf.constant(0)
	def J(iteration,loss,exp_sum,exp):return tf.less(iteration,D-2)
	def K():
		def A(iteration,loss,exp_sum,exps):D=iteration;B=loss;A=exp_sum;C=tf.gather(exps,[D]);C=tf.reshape(C,[]);A=tf.subtract(A,C);B=tf.add(B,math_fns.safe_log(A));return tf.add(D,1),B,A,exps
		return A
	I,A,G,F=tf.while_loop(J,K(),(I,A,G,F));A=A/tf.cast(D,dtype=tf.float32);return A
def _get_ordered_predicted_scores(labels,predicted_scores,n_data):'\n  Order predicted_scores based on sorted labels\n  ';C,A=tf.nn.top_k(tf.transpose(labels),k=n_data);A=tf.transpose(A);B=tf.gather_nd(predicted_scores,A);return B
def get_attrank_loss(labels,predicted_scores,weights=_A):'\n  Modified listwise learning-to-rank AttRank loss\n  Check paper https://arxiv.org/abs/1804.05936 for more information\n  Note: there is an inconsistency between the paper statement and\n  their public code\n  Args:\n    labels: a dense tensor of shape [n_data, 1]\n    n_data is the number of tweet candidates in a BatchPredictionRequest\n    predicted_scores: a dense tensor of same shape and type as labels\n    weights: a dense tensor of the same shape as labels\n  Returns:\n    average loss\n  ';A=tf.reshape(labels,[1,-1]);B=_get_attentions(A);C=tf.reshape(predicted_scores,[1,-1]);D=tf.nn.softmax(C);E=_get_attrank_cross_entropy(B,D);return E
def _get_attentions(raw_scores):'\n  Used in attention weights in AttRank loss\n  for a query/batch/batchPreidictionRequest\n  (a rectified softmax function)\n  ';A=raw_scores;D=tf.less_equal(A,0);B=tf.ones(tf.shape(A))-tf.cast(D,dtype=tf.float32);B=tf.cast(B,dtype=tf.float32);C=B*tf.exp(A);E=tf.reduce_sum(C);F=math_fns.safe_div(C,E);return F
def _get_attrank_cross_entropy(labels,logits):B=logits;C=labels;A=C*math_fns.safe_log(B)+(1-C)*math_fns.safe_log(1-B);A=-1*A;A=tf.reduce_mean(A);return A
def get_listnet_loss(labels,predicted_scores,weights=_A):
	'\n  Listwise learning-to-rank listet loss\n  Check paper https://www.microsoft.com/en-us/research/\n  wp-content/uploads/2016/02/tr-2007-40.pdf\n  for more information\n  Args:\n    labels: a dense tensor of shape [n_data, 1]\n    n_data is the number of tweet candidates in a BatchPredictionRequest\n    predicted_scores: a dense tensor of same shape and type as labels\n    weights: a dense tensor of the same shape as labels\n  Returns:\n    average loss\n  ';A=weights;C=_get_top_one_probs(labels);D=_get_top_one_probs(predicted_scores)
	if A is _A:B=tf.reduce_mean(_get_listnet_cross_entropy(labels=C,logits=D));return B
	B=tf.reduce_mean(_get_listnet_cross_entropy(labels=C,logits=D)*A)/tf.reduce_mean(A);return B
def _get_top_one_probs(labels):'\n  Used in listnet top-one probabilities\n  for a query/batch/batchPreidictionRequest\n  (essentially a softmax function)\n  ';A=tf.exp(labels);B=tf.reduce_sum(A);C=A/B;return C
def _get_listnet_cross_entropy(labels,logits):'\n  Used in listnet\n  cross entropy on top-one probabilities\n  between ideal/label top-one probabilities\n  and predicted/logits top-one probabilities\n  for a query/batch/batchPreidictionRequest\n  ';A=-1*labels*math_fns.safe_log(logits);return A
def get_pointwise_loss(labels,predicted_scores,weights=_A):
	'\n  Pointwise learning-to-rank pointwise loss\n  Args:\n    labels: a dense tensor of shape [n_data, 1]\n    n_data is the number of tweet candidates in a BatchPredictionRequest\n    predicted_scores: a dense tensor of same shape and type as labels\n    weights: a dense tensor of the same shape as labels\n  Returns:\n    average loss\n  ';C=predicted_scores;D=labels;A=weights
	if A is _A:B=tf.reduce_mean(tf.nn.sigmoid_cross_entropy_with_logits(labels=D,logits=C));return B
	B=tf.reduce_mean(tf.nn.sigmoid_cross_entropy_with_logits(labels=D,logits=C)*A)/tf.reduce_mean(A);return B