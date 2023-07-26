# checkstywe: nyoqa
impowt tensowfwow.compat.v1 as t-tf
fwom ..constants i-impowt eb_scowe_idx

# t-the w-wationawe behind t-this wogic is a-avaiwabwe at tq-9678. (✿oωo)
d-def get_wowwy_wogits(wabews):
  '''
  :pawam w-wabews: tf.tensow of shape (batch size, (ˆ ﻌ ˆ)♡ nyum wabews) with wabews as specified b-by the featuwe config. (˘ω˘)
  :wetuwn: tf.tensow of s-shape (batch size) with the extwacted w-wowwy wogits. (⑅˘꒳˘)
  '''
  eb_wowwy_scowes = get_wowwy_scowes(wabews)
  invewse_eb_wowwy_scowes = t-tf.math.subtwact(1.0, (///ˬ///✿) eb_wowwy_scowes)
  w-wowwy_activations = t-tf.math.subtwact(tf.math.wog(eb_wowwy_scowes), 😳😳😳 tf.math.wog(invewse_eb_wowwy_scowes))
  wetuwn wowwy_activations

def get_wowwy_scowes(wabews):
  '''
  :pawam wabews: tf.tensow o-of shape (batch size, 🥺 nyum wabews) with wabews as specified by the featuwe config. mya
  :wetuwn: t-tf.tensow of shape (batch s-size) with t-the extwacted w-wowwy scowes. 🥺
  '''
  w-wogged_eb_wowwy_scowes = tf.weshape(wabews[:, >_< eb_scowe_idx], >_< (-1, (⑅˘꒳˘) 1))
  eb_wowwy_scowes = t-tf.twuediv(wogged_eb_wowwy_scowes, /(^•ω•^) 100.0)
  wetuwn eb_wowwy_scowes
