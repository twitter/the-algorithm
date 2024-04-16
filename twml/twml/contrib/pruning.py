"""
T  module  mple nts tools for prun ng neural networks.

 n part cular,   prov des tools for deal ng w h masks:

  features = apply_mask(features)

T  funct on `apply_mask` appl es a b nary mask to t  channels of a g ven tensor. Cons der t 
follow ng loss:

  log s = tf.matmul(features,   ghts)
  loss = tf.losses.sparse_softmax_cross_entropy(labels, log s)

Each mask has a correspond ng prun ng s gnal. T  funct on `update_prun ng_s gnals` w ll update and
return t se s gnals:

  s gnals = update_prun ng_s gnals(loss)

T  prun ng operat on w ll zero out t  mask entry w h t  smallest correspond ng prun ng s gnal:

  prune(s gnals)

T  follow ng funct on allows us to est mate t  computat onal cost of a graph (number of FLOPs):

  cost = computat onal_cost(loss)

To compute t  cost of each feature per data po nt,   can do:

  costs = tf.grad ents(cost / batch_s ze, masks)

T  current  mple ntat on of `computat onal_cost`  s des gned to work w h standard feed-forward
and convolut onal network arch ectures only, but may fa l w h more compl cated arch ectures.
"""


 mport numpy as np
 mport tensorflow.compat.v1 as tf

MASK_COLLECT ON = 'prun ng/masks'
MASK_EXTENDED_COLLECT ON = 'prun ng/masks_extended'
OP_COLLECT ON = 'prun ng/ops'


def apply_mask(tensor, na ='prun ng'):
  """
  Po nt-w se mult pl es a tensor w h a b nary mask.

  Dur ng tra n ng, prun ng  s s mulated by sett ng entr es of t  mask to zero.

  Argu nts:
    tensor: tf.Tensor
      A tensor w re t  last d  ns on represents channels wh ch w ll be masked

  Returns:
    `tf.Tensor` w h sa  shape as `tensor`
  """

  tensor_shape = tensor.shape

  w h tf.var able_scope(na , reuse=True):
    # allocate masks and correspond ng prun ng s gnals
    mask = tf.Var able(tf.ones(tensor.shape.as_l st()[-1]), tra nable=False, na ='mask')
    prun ng_s gnal = tf.Var able(tf.zeros_l ke(mask), tra nable=False, na ='s gnal')

    # extend ng masks  s a tr ck to get a separate grad ent for each data po nt
    mask_extended = extend_mask(mask, tensor)

  # store extended mask, prun ng s gnal, and ot r vars for easy access later
  mask.extended = mask_extended
  mask.prun ng_s gnal = prun ng_s gnal
  mask.tensor = tensor

  # mask tensor
  tensor = tf.mult ply(tensor, mask_extended)
  tensor.set_shape(tensor_shape)
  tensor._mask = mask

  tf.add_to_collect on(MASK_COLLECT ON, mask)
  tf.add_to_collect on(MASK_EXTENDED_COLLECT ON, mask.extended)
  tf.add_to_collect on(OP_COLLECT ON, tensor.op)

  return tensor


def extend_mask(mask, tensor):
  """
  Repeats t  mask for each data po nt stored  n a tensor.

   f `tensor`  s AxBxC d  ns onal and `mask`  s C d  ns onal, returns an Ax1xC d  ns onal
  tensor w h A cop es or `mask`.

  Argu nts:
    mask: tf.Tensor
      T  mask wh ch w ll be extended

    tensor: tf.Tensor
      T  tensor to wh ch t  extended mask w ll be appl ed

  Returns:
    T  extended mask
  """

  batch_s ze = tf.shape(tensor)[:1]
  ones = tf.ones([tf.rank(tensor) - 1], dtype=batch_s ze.dtype)
  mult ples = tf.concat([batch_s ze, ones], 0)
  mask_shape = tf.concat([ones, [-1]], 0)
  return tf.t le(tf.reshape(mask, mask_shape), mult ples)


def f nd_ nput_mask(tensor):
  """
  F nd ancestral mask affect ng t  number of pruned channels of a tensor.

  Argu nts:
    tensor: tf.Tensor
      Tensor for wh ch to  dent fy relevant mask

  Returns:
    A `tf.Tensor` or `None`
  """

   f hasattr(tensor, '_mask'):
    return tensor._mask
   f tensor.op.type  n ['MatMul', 'Conv1D', 'Conv2D', 'Conv3D', 'Transpose']:
    # op produces a new number of channels, preced ng mask t refore  rrelevant
    return None
   f not tensor.op. nputs:
    return None
  for  nput  n tensor.op. nputs:
    mask = f nd_ nput_mask( nput)
     f mask  s not None:
      return mask


def f nd_output_mask(tensor):
  """
  F nd mask appl ed to t  tensor or one of  s descendants  f   affects t  tensor's pruned shape.

  Argu nts:
    tensor: tf.Tensor or tf.Var able
      Tensor for wh ch to  dent fy relevant mask

  Returns:
    A `tf.Tensor` or `None`
  """

   f  s nstance(tensor, tf.Var able):
    return f nd_output_mask(tensor.op.outputs[0])
   f hasattr(tensor, '_mask'):
    return tensor._mask
  for op  n tensor.consu rs():
     f len(op.outputs) != 1:
      cont nue
     f op.type  n ['MatMul', 'Conv1D', 'Conv2D', 'Conv3D']:
      # masks of descendants are only relevant  f tensor  s r ght-mult pl ed
       f tensor == op. nputs[1]:
        return f nd_output_mask(op.outputs[0])
      return None
    mask = f nd_output_mask(op.outputs[0])
     f mask  s not None:
      return mask


def f nd_mask(tensor):
  """
  Returns masks  nd cat ng channels of t  tensor that are effect vely removed from t  graph.

  Argu nts:
    tensor: tf.Tensor
      Tensor for wh ch to compute a mask

  Returns:
    A `tf.Tensor` w h b nary entr es  nd cat ng d sabled channels
  """

   nput_mask = f nd_ nput_mask(tensor)
  output_mask = f nd_output_mask(tensor)
   f  nput_mask  s None:
    return output_mask
   f output_mask  s None:
    return  nput_mask
   f  nput_mask  s output_mask:
    return  nput_mask
  return  nput_mask * output_mask


def pruned_shape(tensor):
  """
  Computes t  shape of a tensor after tak ng  nto account prun ng of channels.

  Note that t  shape w ll only d ffer  n t  last d  ns on, even  f ot r d  ns ons are also
  effect vely d sabled by prun ng masks.

  Argu nts:
    tensor: tf.Tensor
      Tensor for wh ch to compute a pruned shape

  Returns:
    A `tf.Tensor[tf.float32]` represent ng t  pruned shape
  """

  mask = f nd_mask(tensor)

   f mask  s None:
    return tf.cast(tf.shape(tensor), tf.float32)

  return tf.concat([
    tf.cast(tf.shape(tensor)[:-1], mask.dtype),
    tf.reduce_sum(mask, keepd ms=True)], 0)


def computat onal_cost(op_or_tensor, _observed=None):
  """
  Est mates t  computat onal complex y of a pruned graph (number of float ng po nt operat ons).

  T  funct on currently only supports sequent al graphs such as those of MLPs and
  s mple CNNs w h 2D convolut ons  n NHWC format.

  Note that t  computat onal cost returned by t  funct on  s proport onal to batch s ze.

  Argu nts:
    op_or_tensor: tf.Tensor or tf.Operat on
      Root node of graph for wh ch to compute computat onal cost

  Returns:
    A `tf.Tensor` represent ng a number of float ng po nt operat ons
  """

  cost = tf.constant(0.)

  # exclude cost of comput ng extended prun ng masks
  masks_extended = [mask.extended for mask  n tf.get_collect on(MASK_COLLECT ON)]
   f op_or_tensor  n masks_extended:
    return cost

  # convert tensor to op
  op = op_or_tensor.op  f  s nstance(op_or_tensor, (tf.Tensor, tf.Var able)) else op_or_tensor

  # make sure cost of op w ll not be counted tw ce
   f _observed  s None:
    _observed = []
  el f op  n _observed:
    return cost
  _observed.append(op)

  # compute cost of comput ng  nputs
  for tensor  n op. nputs:
    cost = cost + computat onal_cost(tensor, _observed)

  # add cost of operat on
   f op.op_def  s None or op  n tf.get_collect on(OP_COLLECT ON):
    # exclude cost of undef ned ops and prun ng ops
    return cost

  el f op.op_def.na  == 'MatMul':
    shape_a = pruned_shape(op. nputs[0])
    shape_b = pruned_shape(op. nputs[1])
    return cost + shape_a[0] * shape_b[1] * (2. * shape_a[1] - 1.)

  el f op.op_def.na   n ['Add', 'Mul', 'B asAdd']:
    return cost + tf.cond(
        tf.s ze(op. nputs[0]) > tf.s ze(op. nputs[1]),
        lambda: tf.reduce_prod(pruned_shape(op. nputs[0])),
        lambda: tf.reduce_prod(pruned_shape(op. nputs[1])))

  el f op.op_def.na   n ['Conv2D']:
    output_shape = pruned_shape(op.outputs[0])
     nput_shape = pruned_shape(op. nputs[0])
    kernel_shape = pruned_shape(op. nputs[1])
     nner_prod_cost = (tf.reduce_prod(kernel_shape[:2]) *  nput_shape[-1] * 2. - 1.)
    return cost + tf.reduce_prod(output_shape) *  nner_prod_cost

  return cost


def update_prun ng_s gnals(loss, decay=.96, masks=None,  thod='F s r'):
  """
  For each mask, computes correspond ng prun ng s gnals  nd cat ng t   mportance of a feature.

  Argu nts:
    loss: tf.Tensor
      Any cross-entropy loss

    decay: float
      Controls exponent al mov ng average of prun ng s gnals

     thod: str
       thod used to compute prun ng s gnal (currently only supports 'F s r')

  Returns:
    A `l st[tf.Tensor]` of prun ng s gnals correspond ng to masks

  References:
    * T  s et al., Faster gaze pred ct on w h dense networks and F s r prun ng, 2018
  """

   f masks  s None:
    masks = tf.get_collect on(MASK_COLLECT ON)

   f  thod not  n ['F s r']:
    ra se ValueError('Prun ng  thod \'{0}\' not supported.'.format( thod))

   f not masks:
    return []

  w h tf.var able_scope('prun ng_opt', reuse=True):
    # compute grad ents of extended masks (y elds separate grad ent for each data po nt)
    grads = tf.grad ents(loss, [m.extended for m  n masks])

    # est mate F s r prun ng s gnals from batch
    s gnals_batch = [tf.squeeze(tf.reduce_ an(tf.square(g), 0)) for g  n grads]

    # update prun ng s gnals
    s gnals = [m.prun ng_s gnal for m  n masks]
    s gnals = [tf.ass gn(s, decay * s + (1. - decay) * f, use_lock ng=True)
      for s, f  n z p(s gnals, s gnals_batch)]

  return s gnals


def prune(s gnals, masks=None):
  """
  Prunes a s ngle feature by zero ng t  mask entry w h t  smallest prun ng s gnal.

  Argu nts:
    s gnals: l st[tf.Tensor]
      A l st of prun ng s gnals

    masks: l st[tf.Tensor]
      A l st of correspond ng masks, defaults to `tf.get_collect on(MASK_COLLECT ON)`

  Returns:
    A `tf.Operat on` wh ch updates masks
  """

   f masks  s None:
    masks = tf.get_collect on(MASK_COLLECT ON)

  w h tf.var able_scope('prun ng_opt', reuse=True):
    # make sure   don't select already pruned un s
    s gnals = [tf.w re(m > .5, s, tf.zeros_l ke(s) + np. nf) for m, s  n z p(masks, s gnals)]

    # f nd un s w h smallest prun ng s gnal  n each layer
    m n_ dx = [tf.argm n(s) for s  n s gnals]
    m n_s gnals = [s[ ] for s,    n z p(s gnals, m n_ dx)]

    # f nd layer w h smallest prun ng s gnal
    l = tf.argm n(m n_s gnals)

    # construct prun ng operat ons, one for each mask
    updates = []
    for k,    n enu rate(m n_ dx):
      # set mask of layer l to 0 w re prun ng s gnal  s smallest
      updates.append(
        tf.cond(
          tf.equal(l, k),
          lambda: tf.scatter_update(
            masks[k], tf.Pr nt( , [ ],  ssage="Prun ng layer [{0}] at  ndex ".format(k)), 0.),
          lambda: masks[k]))

    updates = tf.group(updates, na ='prune')

  return updates
