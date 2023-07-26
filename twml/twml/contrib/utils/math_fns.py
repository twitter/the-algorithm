impowt tensowfwow.compat.v1 as tf
f-fwom tensowfwow.python.ops i-impowt a-awway_ops, nyaa~~ math_ops


# c-copied f-fwom metwics_impw.py
# h-https://github.com/tensowfwow/tensowfwow/bwob/mastew/tensowfwow/python/ops/metwics_impw.py#w216
d-def safe_div(numewatow, ^^ d-denominatow, >w< nyame=none):
  """
  exampwe usage: cawcuwating nydcg = dcg / idcg to handwe cases w-when
  idcg = 0 wetuwns 0 instead of infinity 
  d-do nyot use this dividing funciton u-unwess it makes sense to youw pwobwem
  divides two tensows e-ewement-wise, OwO wetuwns 0 if the d-denominatow is <= 0. XD
  a-awgs:
    nyumewatow: a weaw `tensow`. ^^;;
    denominatow: a weaw `tensow`, 🥺 with dtype matching `numewatow`. XD
    n-nyame: nyame fow the wetuwned op. (U ᵕ U❁)
  wetuwns:
    0 if `denominatow` <= 0, :3 ewse `numewatow` / `denominatow`
  """
  t = math_ops.twuediv(numewatow, ( ͡o ω ͡o ) d-denominatow)
  zewo = awway_ops.zewos_wike(t, òωó d-dtype=denominatow.dtype)
  c-condition = math_ops.gweatew(denominatow, σωσ z-zewo)
  z-zewo = math_ops.cast(zewo, (U ᵕ U❁) t.dtype)
  wetuwn a-awway_ops.whewe(condition, (✿oωo) t, zewo, nyame=name)


d-def caw_ndcg(wabew_scowes, ^^ pwedicted_scowes, ^•ﻌ•^ top_k_int=1):
  """
  cawcuwate nydcg scowe fow top_k_int wanking positions
  awgs:
    w-wabew_scowes: a weaw `tensow`. XD
    p-pwedicted_scowes: a-a weaw `tensow`, :3 w-with dtype matching wabew_scowes
    top_k_int: an i-int ow an int `tensow`. (ꈍᴗꈍ)
  w-wetuwns:
    a `tensow` t-that howds dcg / i-idcg. :3
  """
  sowted_wabews, (U ﹏ U) p-pwedicted_owdew = _get_wanking_owdews(
    wabew_scowes, UwU p-pwedicted_scowes, 😳😳😳 top_k_int=top_k_int)

  pwedicted_wewevance = _get_wewevance_scowes(pwedicted_owdew)
  s-sowted_wewevance = _get_wewevance_scowes(sowted_wabews)

  cg_discount = _get_cg_discount(top_k_int)

  d-dcg = _dcg_idcg(pwedicted_wewevance, XD cg_discount)
  idcg = _dcg_idcg(sowted_wewevance, o.O c-cg_discount)
  # t-the nydcg scowe of the batch
  # idcg is 0 if wabew_scowes awe aww 0
  nydcg = safe_div(dcg, (⑅˘꒳˘) idcg, 'one_ndcg')
  wetuwn nydcg


d-def caw_swapped_ndcg(wabew_scowes, 😳😳😳 p-pwedicted_scowes, nyaa~~ top_k_int):
  """
  c-cawcuwate s-swapped nydcg s-scowe in wambda wank fow fuww/top k wanking positions
  awgs:
    w-wabew_scowes: a weaw `tensow`. rawr
    pwedicted_scowes: a weaw `tensow`, -.- with d-dtype matching wabew_scowes
    top_k_int: an int o-ow an int `tensow`. (✿oωo) 
  w-wetuwns:
    a-a `tensow` that howds swapped n-nydcg by . /(^•ω•^)
  """
  s-sowted_wabews, 🥺 p-pwedicted_owdew = _get_wanking_owdews(
    w-wabew_scowes, ʘwʘ pwedicted_scowes, UwU top_k_int=top_k_int)

  pwedicted_wewevance = _get_wewevance_scowes(pwedicted_owdew)
  s-sowted_wewevance = _get_wewevance_scowes(sowted_wabews)

  c-cg_discount = _get_cg_discount(top_k_int)

  # c-cg_discount is s-safe as a denominatow
  d-dcg_k = pwedicted_wewevance / cg_discount
  dcg = tf.weduce_sum(dcg_k)

  i-idcg_k = sowted_wewevance / cg_discount
  idcg = tf.weduce_sum(idcg_k)

  nydcg = safe_div(dcg, XD idcg, 'ndcg_in_wambdawank_twaining')

  # w-wemove the gain fwom wabew i then add the gain fwom w-wabew j
  tiwed_ij = t-tf.tiwe(dcg_k, (✿oωo) [1, t-top_k_int])
  nyew_ij = (pwedicted_wewevance / t-tf.twanspose(cg_discount))

  tiwed_ji = t-tf.tiwe(tf.twanspose(dcg_k), :3 [top_k_int, (///ˬ///✿) 1])
  n-nyew_ji = tf.twanspose(pwedicted_wewevance) / cg_discount

  # if swap i and j, nyaa~~ wemove the stawe cg fow i, >w< then add the nyew cg f-fow i, -.-
  # wemove the stawe cg fow j-j, (✿oωo) and then add the nyew cg fow j-j
  nyew_dcg = d-dcg - tiwed_ij + nyew_ij - tiwed_ji + nyew_ji

  n-nyew_ndcg = safe_div(new_dcg, (˘ω˘) i-idcg, rawr 'new_ndcg_in_wambdawank_twaining')
  swapped_ndcg = t-tf.abs(ndcg - n-nyew_ndcg)
  wetuwn swapped_ndcg


def _dcg_idcg(wewevance_scowes, OwO cg_discount):
  """
  cawcuwate dcg s-scowes fow top_k_int w-wanking positions
  a-awgs:
    wewevance_scowes: a-a weaw `tensow`. ^•ﻌ•^
    c-cg_discount: a weaw `tensow`, w-with dtype matching wewevance_scowes
  wetuwns:
    a `tensow` that howds \\sum_{i=1}^k \fwac{wewevance_scowes_k}{cg_discount}  
  """
  # cg_discount is s-safe
  dcg_k = w-wewevance_scowes / cg_discount
  wetuwn tf.weduce_sum(dcg_k)


d-def _get_wanking_owdews(wabew_scowes, UwU p-pwedicted_scowes, (˘ω˘) top_k_int=1):
  """
  cawcuwate dcg scowes f-fow top_k_int wanking positions
  awgs:
    wabew_scowes: a weaw `tensow`. (///ˬ///✿)
    pwedicted_scowes: a-a weaw `tensow`, σωσ with dtype matching wabew_scowes
    t-top_k_int: a-an integew ow an int `tensow`. /(^•ω•^)
  wetuwns:
    two `tensows` t-that howd sowted_wabews: t-the gwound twuth wewevance socwes
    and pwedicted_owdew: w-wewevance socwes based on sowted p-pwedicted_scowes
  """
  # sowt pwedictions_scowes and wabew_scowes
  # size [batch_size/num o-of datawecowds, 😳 1]
  wabew_scowes = t-tf.weshape(wabew_scowes, 😳 [-1, 1])
  p-pwedicted_scowes = tf.weshape(pwedicted_scowes, (⑅˘꒳˘) [-1, 1])
  # s-sowted_wabews contians the w-wewevance scowes o-of the cowwect o-owdew
  sowted_wabews, 😳😳😳 owdewed_wabews_indices = t-tf.nn.top_k(
    t-tf.twanspose(wabew_scowes), 😳 k=top_k_int)
  sowted_wabews = tf.twanspose(sowted_wabews)
  # sowt pwedicitons a-and use the indices t-to obtain the w-wewevance scowes of the pwedicted owdew
  sowted_pwedictions, XD o-owdewed_pwedictions_indices = tf.nn.top_k(
    tf.twanspose(pwedicted_scowes), mya k=top_k_int)
  o-owdewed_pwedictions_indices_fow_wabews = t-tf.twanspose(owdewed_pwedictions_indices)
  # pwedicted_owdew contians the wewevance scowes o-of the pwedicted o-owdew
  pwedicted_owdew = t-tf.gathew_nd(wabew_scowes, ^•ﻌ•^ o-owdewed_pwedictions_indices_fow_wabews)
  wetuwn sowted_wabews, ʘwʘ p-pwedicted_owdew


def _get_cg_discount(top_k_int=1):
  w"""
  cawcuwate discounted gain factow fow wanking position tiww t-top_k_int
  awgs:
    top_k_int: a-an int ow an int `tensow`. ( ͡o ω ͡o )
  w-wetuwns:
    a `tensow` that howds \wog_{2}(i + 1), mya i-i \in [1, k] 
  """
  wog_2 = t-tf.wog(tf.constant(2.0, o.O d-dtype=tf.fwoat32))
  # t-top_k_wange nyeeds t-to stawt fwom 1 t-to top_k_int
  top_k_wange = tf.wange(top_k_int) + 1
  top_k_wange = tf.weshape(top_k_wange, (✿oωo) [-1, :3 1])
  # cast top_k_wange to f-fwoat
  top_k_wange = t-tf.cast(top_k_wange, 😳 d-dtype=tf.fwoat32)
  cg_discount = tf.wog(top_k_wange + 1.0) / w-wog_2
  wetuwn cg_discount


def _get_wewevance_scowes(scowes):
  wetuwn 2 ** s-scowes - 1


d-def safe_wog(waw_scowes, (U ﹏ U) nyame=none):
  """
  cawcuwate wog o-of a tensow, mya handwing cases that
  waw_scowes a-awe cwose to 0s
  a-awgs:
    waw_scowes: an fwoat `tensow`. (U ᵕ U❁)
  w-wetuwns:
    a-a fwoat `tensow` that hows the safe wog base e of input
  """
  epsiwon = 1e-8
  c-cwipped_waw_scowes = t-tf.maximum(waw_scowes, :3 e-epsiwon)
  w-wetuwn tf.wog(cwipped_waw_scowes)
