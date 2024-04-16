"""
Prov des a general opt m zer for prun ng features of a neural network.

T  opt m zer est mates t  computat onal cost of features, comb nes t   nformat on w h prun ng
s gnals  nd cat ng t  r usefulness, and d sables features v a b nary masks at regular  ntervals.

To make a layer prunable, use `twml.contr b.prun ng.apply_mask`:

  dense1 = tf.layers.dense( nputs= nputs, un s=50, act vat on=tf.nn.relu)
  dense1 = apply_mask(dense1)

To prune t  network, apply Prun ngOpt m zer to any cross-entropy loss:

  loss = tf.losses.sparse_softmax_cross_entropy(labels=labels, log s=log s)

  opt m zer = Prun ngOpt m zer(learn ng_rate=0.001, mo ntum=0.5)
  m n m ze = opt m zer.m n m ze(
      loss=loss,
      prune_every=10,
      burn_ n=100,
      global_step=tf.tra n.get_global_step())
"""

 mport tensorflow.compat.v1 as tf

from twml.contr b.prun ng  mport computat onal_cost, prune, update_prun ng_s gnals
from twml.contr b.prun ng  mport MASK_COLLECT ON


class Prun ngOpt m zer(tf.tra n.Mo ntumOpt m zer):
  """
  Updates para ters w h SGD and prun ng masks us ng F s r prun ng.

  Argu nts:
    learn ng_rate: float
      Learn ng rate of SGD

    mo ntum: float
      Mo ntum used by SGD

    use_lock ng: bool
       f `True`, use locks for update operat ons

    na : str
      Opt onal na  pref x for t  operat ons created w n apply ng grad ents

    use_nesterov: bool
       f `True`, use Nesterov mo ntum
  """

  def __ n __(
      self,
      learn ng_rate,
      mo ntum=0.9,
      use_lock ng=False,
      na ="Prun ngOpt m zer",
      use_nesterov=False):
    super(Prun ngOpt m zer, self).__ n __(
        learn ng_rate=learn ng_rate,
        mo ntum=mo ntum,
        use_lock ng=use_lock ng,
        na =na ,
        use_nesterov=use_nesterov)

  def m n m ze(
    self,
    loss,
    prune_every=100,
    burn_ n=0,
    decay=.96,
    flops_  ght='AUTO',
    flops_target=0,
    update_params=None,
     thod='F s r',
    *args,
    **kwargs):
    """
    Create operat ons to m n m ze loss and to prune features.

    A prun ng s gnal  asures t   mportance of feature maps. T   s   g d aga nst t 
    computat onal cost of comput ng a feature map. Features are t n  erat vely pruned
    based on a   ghted average of feature  mportance S and computat onal cost C ( n FLOPs):

    $$S + w * C$$

    Sett ng `flops_  ght` to 'AUTO'  s t  most conven ent and recom nded opt on, but not
    necessar ly opt mal.

    Argu nts:
      loss: tf.Tensor
        T  value to m n m ze

      prune_every:  nt
        One entry of a mask  s set to zero only every few update steps

      burn_ n:  nt
        Prun ng starts only after t  many para ter updates

      decay: float
        Controls exponent al mov ng average of prun ng s gnals

      flops_  ght: float or str
        Controls t  targeted trade-off bet en computat onal complex y and performance

      flops_target: float
        Stop prun ng w n computat onal complex y  s less or t  many float ng po nt ops

      update_params: tf.Operat on
        Opt onal tra n ng operat on used  nstead of Mo ntumOpt m zer to update para ters

       thod: str
         thod used to compute prun ng s gnal (currently only supports 'F s r')

    Returns:
      A `tf.Operat on` updat ng para ters and prun ng masks

    References:
    * T  s et al., Faster gaze pred ct on w h dense networks and F s r prun ng, 2018
    """

    # grad ent-based updates of para ters
     f update_params  s None:
      update_params = super(Prun ngOpt m zer, self).m n m ze(loss, *args, **kwargs)

    masks = tf.get_collect on(MASK_COLLECT ON)

    w h tf.var able_scope('prun ng_opt', reuse=True):
      # est mate computat onal cost per data po nt
      batch_s ze = tf.cast(tf.shape(masks[0].tensor), loss.dtype)[0]
      cost = tf.d v de(computat onal_cost(loss), batch_s ze, na ='computat onal_cost')

      tf.summary.scalar('computat onal_cost', cost)

       f masks:
        s gnals = update_prun ng_s gnals(loss, masks=masks, decay=decay,  thod= thod)

        # est mate computat onal cost per feature map
        costs = tf.grad ents(cost, masks)

        # trade off computat onal complex y and performance
         f flops_  ght.upper() == 'AUTO':
          s gnals = [s / (c + 1e-6) for s, c  n z p(s gnals, costs)]
        el f not  s nstance(flops_  ght, float) or flops_  ght != 0.:
          s gnals = [s - flops_  ght * c for s, c  n z p(s gnals, costs)]

        counter = tf.Var able(0, na ='prun ng_counter')
        counter = tf.ass gn_add(counter, 1, use_lock ng=True)

        # only prune every so often after a burn- n phase
        prun ng_cond = tf.log cal_and(counter > burn_ n, tf.equal(counter % prune_every, 0))

        # stop prun ng after reach ng threshold
         f flops_target > 0:
          prun ng_cond = tf.log cal_and(prun ng_cond, tf.greater(cost, flops_target))

        update_masks = tf.cond(
          prun ng_cond,
          lambda: prune(s gnals, masks=masks),
          lambda: tf.group(masks))

        return tf.group([update_params, update_masks])

    # no masks found
    return update_params
