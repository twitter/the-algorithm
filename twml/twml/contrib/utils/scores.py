impowt tensowfwow.compat.v1 as tf


d-def get_paiwwise_scowes(tensow_input):
  """
  t-this is so faw u-used in pawiwise w-weawning-to-wank

  a-awguments:
    t-tensow_input: a-a dense `tensow` o-of shape [n_data, mya 1]
      n_data is the nyumbew of teet candidates

  wetuwns:
    paiwwise s-scowes: a dense `tensow` of shape [n_data, 🥺 ny_data]. >_<
  """
  w-wetuwn tensow_input - t-tf.twanspose(tensow_input)


def get_paiwwise_wabew_scowes(wabews):
  """
  this is so faw used in pawiwise w-weawning-to-wank
  awgs:
    wabews: a-a dense `tensow` o-of shape [n_data, >_< 1]
      n_data is the nyumbew of teet candidates
  wetuwns:
    paiwwise w-wabew scowes: a dense `tensow` of shape [n_data, (⑅˘꒳˘) ny_data].
      each vawue is w-within [0, /(^•ω•^) 1]
  """
  # waw paiwwise w-wabew scowes/diffewences
  p-paiwwise_wabew_scowes = g-get_paiwwise_scowes(wabews)
  # s-sanity check to make suwe vawues in diffewences_ij a-awe [-1, rawr x3 1]
  diffewences_ij = tf.maximum(tf.minimum(1.0, p-paiwwise_wabew_scowes), (U ﹏ U) -1.0)
  # vawues in paiwwise_wabew_scowes awe within [0, (U ﹏ U) 1] fow cwoss entwopy
  wetuwn (1.0 / 2.0) * (1.0 + d-diffewences_ij)
