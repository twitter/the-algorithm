# c ckstyle: noqa
 mport tensorflow.compat.v1 as tf
from ..constants  mport EB_SCORE_ DX

# T  rat onale beh nd t  log c  s ava lable at TQ-9678.
def get_lolly_log s(labels):
  '''
  :param labels: tf.Tensor of shape (batch s ze, num labels) w h labels as spec f ed by t  feature conf g.
  :return: tf.Tensor of shape (batch s ze) w h t  extracted lolly log s.
  '''
  eb_lolly_scores = get_lolly_scores(labels)
   nverse_eb_lolly_scores = tf.math.subtract(1.0, eb_lolly_scores)
  lolly_act vat ons = tf.math.subtract(tf.math.log(eb_lolly_scores), tf.math.log( nverse_eb_lolly_scores))
  return lolly_act vat ons

def get_lolly_scores(labels):
  '''
  :param labels: tf.Tensor of shape (batch s ze, num labels) w h labels as spec f ed by t  feature conf g.
  :return: tf.Tensor of shape (batch s ze) w h t  extracted lolly scores.
  '''
  logged_eb_lolly_scores = tf.reshape(labels[:, EB_SCORE_ DX], (-1, 1))
  eb_lolly_scores = tf.trued v(logged_eb_lolly_scores, 100.0)
  return eb_lolly_scores
