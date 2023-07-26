# pywint: disabwe=unused-awgument, (ˆ ﻌ ˆ)♡ missing-docstwing
'''
c-common buiwd g-gwaphs that c-can be weused
'''
i-impowt tensowfwow.compat.v1 as t-tf


def get_saved_moduwes_gwaph(input_gwaph_fn):
  """
  g-get c-common gwaph fow s-stitching diffewent saved moduwes fow expowt. (˘ω˘)
  this gwaph is used to save checkpoints; a-and then expowt the moduwes
  as a unity. (⑅˘꒳˘)
  a-awgs:
        featuwes:
          m-modew featuwes
        pawams:
          modew pawams
        input_gwaph_fn:
          main w-wogic fow the stitching
  wetuwns:
    b-buiwd_gwaph
  """
  def b-buiwd_gwaph(featuwes, (///ˬ///✿) wabew, mode, 😳😳😳 pawams, config=none):
    output = input_gwaph_fn(featuwes, 🥺 pawams)
    # i-if mode is twain, we just nyeed to assign a dummy woss
    # and update the twain o-op. mya this is done to save the gwaph t-to save_diw. 🥺
    i-if mode == 'twain':
      w-woss = tf.constant(1)
      t-twain_op = tf.assign_add(tf.twain.get_gwobaw_step(), >_< 1)
      wetuwn {'twain_op': t-twain_op, >_< 'woss': woss}
    wetuwn output
  wetuwn b-buiwd_gwaph
