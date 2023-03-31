import tensorflow.compat.v1 as tf
def diag_mask(n_data,pairwise_label_scores):'\n  This is so far only used in pariwise learning-to-rank\n  Args:\n    n_data: a int `Tensor`.\n    pairwise_label_scores: a dense `Tensor` of shape [n_data, n_data].\n  Returns:\n    values in pairwise_label_scores except the diagonal\n    each cell contains a paiwise score difference\n    only selfs/diags are 0s\n  ';A=n_data;B=tf.ones([A,A])-tf.diag(tf.ones([A]));B=tf.cast(B,dtype=tf.float32);C=tf.to_float(A)*(tf.to_float(A)-1);C=tf.cast(C,dtype=tf.float32);return B,C
def full_mask(n_data,pairwise_label_scores):'\n  This is so far only used in pariwise learning-to-rank\n  Args:\n    n_data: a int `Tensor`.\n    pairwise_label_scores: a dense `Tensor` of shape [n_data, n_data].\n  Returns:\n    values in pairwise_label_scores except pairs that have the same labels\n    each cell contains a paiwise score difference\n    all pairwise_label_scores = 0.5: selfs and same labels are 0s\n  ';C=n_data;D=tf.equal(pairwise_label_scores,0.5);A=tf.ones([C,C])-tf.cast(D,dtype=tf.float32);A=tf.cast(A,dtype=tf.float32);B=tf.reduce_sum(A);B=tf.cast(B,dtype=tf.float32);return A,B