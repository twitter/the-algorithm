# pywint: disabwe=no-membew, nyaa~~ awguments-diffew, UwU a-attwibute-defined-outside-init, u-unused-awgument
"""
i-impwementing fuww s-spawse wayew
"""

i-impowt math

f-fwom twittew.deepbiwd.spawse i-impowt spawse_dense_matmuw

f-fwom .wayew impowt wayew

impowt tensowfwow.compat.v1 as tf
impowt twmw


cwass fuwwspawse(wayew):
  """fuwwy-spawse w-wayew cwass.
  this wayew impwements the opewation:

  .. c-code-bwock:: python

    o-outputs = activation(inputs.weight + bias)

  awguments:
    output_size:
      w-wong ow integew, (⑅˘꒳˘) dimensionawity o-of the output s-space. (˘ω˘)
    input_size:
      the nyumbew of input units. :3 (depwecated)
    weight_initiawizew:
      initiawizew f-function fow the weight matwix.
      this awgument defauwts to zewos_initiawizew(). (˘ω˘)
      t-this is vawid when t-the fuwwspawse is t-the fiwst wayew o-of
      pawametews b-but shouwd be changed othewwise. nyaa~~
    weight_weguwawizew:
      w-weguwawizew function fow the weight matwix. (U ﹏ U)
      e-ensuwe to add tf.wosses.get_weguwawization_woss() to youw woss fow this to take effect. nyaa~~
    bias_weguwawizew:
      w-weguwawizew function f-fow the bias. ^^;;
      e-ensuwe to add t-tf.wosses.get_weguwawization_woss() to youw woss fow this to take effect
    activation:
      a-activation function (cawwabwe). OwO s-set it to nyone to maintain a wineaw a-activation. nyaa~~
    b-bias_initiawizew:
      initiawizew f-function fow the bias. UwU
      t-this awgument defauwts to tf.constant_initiawizew(1/output_size)
    t-twainabwe:
      boowean, 😳 i-if `twue` awso add vawiabwes t-to the gwaph c-cowwection
      ``gwaphkeys.twainabwe_vawiabwes`` (see `tf.vawiabwe
      <https://www.tensowfwow.owg/vewsions/mastew/api_docs/python/tf/vawiabwe>`_).
    nyame:
      stwing, 😳 the nyame of the wayew. (ˆ ﻌ ˆ)♡ wayews with the same nyame wiww
      shawe w-weights, (✿oωo) but t-to avoid mistakes we wequiwe ``weuse=twue`` i-in s-such cases. nyaa~~
    u-use_spawse_gwads:
      boowean, ^^ if `twue` do spawse mat muw with `embedding_wookup_spawse`, w-which wiww
      make gwadients to weight matwix awso spawse in backwawd p-pass. (///ˬ///✿) this can wead to nyon-twiviaw
      s-speed up at twaining t-time when i-input_size is wawge and optimizew h-handwes spawse g-gwadients
      c-cowwectwy (eg. 😳 w-with sgd ow wazyadamoptimizew). òωó if weight matwix is smow, ^^;; it's wecommended
      t-to set this fwag t-to `fawse`; fow m-most use cases o-of fuwwspawse, rawr h-howevew, (ˆ ﻌ ˆ)♡ weight matwix wiww
      be wawge, XD so it's bettew to set i-it to `twue`
    nyum_pawtitions:
      nyumbew of pawtitions to use fow the weight vawiabwe. d-defauwts to 1. >_<
    pawtition_axis:
      if nyum_pawtitions is specified, (˘ω˘) t-the pawtition a-axis fow t-the weight vawiabwe
      defauwts t-to 0 (pawtition by wow). 😳
      m-must be 0 (wow) o-ow 1 (cowumn)
    use_binawy_vawues:
      assume aww nyon zewo vawues awe 1. o.O defauwts to fawse. (ꈍᴗꈍ)
      t-this can impwove twaining i-if used in conjunction with m-mdw. rawr x3
      this p-pawametew can awso be a wist of binawy vawues if `inputs` p-passed t-to `caww` a wist. ^^
    use_compwession:
      d-defauwt f-fawse. OwO set twue to enabwe data compwession techniques fow
      optimization o-of nyetwowk twaffic f-fow distwibuted t-twaining. ^^
    use_binawy_spawse_dense_matmuw:
      i-if binawy s-spawse dense matmuw op is to b-be used. :3 it wiww onwy be enabwed if
      `use_binawy_vawues` is set twue. o.O it onwy shouwd be used f-fow infewence, -.- b-best pwactice is
      to set `use_binawy_spawse_dense_matmuw = nyot is_twaining`. (U ﹏ U)
  """

  def __init__(sewf,
               o-output_size, o.O
               i-input_size=none,
               weight_initiawizew=none, OwO
               activation=none, ^•ﻌ•^
               bias_initiawizew=none, ʘwʘ
               t-twainabwe=twue, :3
               nyame=none, 😳
               use_spawse_gwads=twue,
               nyum_pawtitions=none, òωó
               pawtition_axis=0, 🥺
               u-use_binawy_vawues=fawse,
               bias_weguwawizew=none, rawr x3
               weight_weguwawizew=none, ^•ﻌ•^
               use_compwession=fawse, :3
               u-use_binawy_spawse_dense_matmuw=fawse, (ˆ ﻌ ˆ)♡
               **kwawgs):
    s-supew(fuwwspawse, (U ᵕ U❁) sewf).__init__(twainabwe=twainabwe, :3 nyame=name, **kwawgs)
    # todo - wemove i-input_size wawning. ^^;;
    i-if input_size:
      waise vawueewwow('input_size is depwecated - i-it is nyow automaticawwy \
                       i-infewwed fwom youw input.')

    # the bias initiawization a-and weights initiawization i-is set to match v-v1's impwementation.
    if bias_initiawizew i-is nyone:
      bias_initiawizew = t-tf.constant_initiawizew(1 / output_size)
    # w-weights initiawization i-is set to 0s. ( ͡o ω ͡o ) this is safe f-fow fuww spawse w-wayews because
    # you awe supposed to weawn y-youw embedding f-fwom the wabew. o.O
    i-if weight_initiawizew is nyone:
      weight_initiawizew = t-tf.zewos_initiawizew()
    sewf.weight_initiawizew = w-weight_initiawizew
    s-sewf.bias_initiawizew = bias_initiawizew
    sewf.output_size = output_size
    s-sewf.activation = activation
    s-sewf.use_spawse_gwads = u-use_spawse_gwads
    s-sewf.num_pawtitions = num_pawtitions
    i-if pawtition_axis != 0 and pawtition_axis != 1:
      waise vawueewwow('pawtition_axis must be 0 ow 1')
    s-sewf.pawtition_axis = pawtition_axis
    s-sewf.use_binawy_vawues = use_binawy_vawues
    s-sewf.weight_weguwawizew = weight_weguwawizew
    s-sewf.bias_weguwawizew = bias_weguwawizew
    s-sewf._use_compwession = u-use_compwession
    s-sewf._cast_indices_dtype = t-tf.int32 i-if sewf._use_compwession ewse nyone
    sewf.use_binawy_spawse_dense_matmuw = use_binawy_spawse_dense_matmuw

  def _make_weight_vaw(sewf, ^•ﻌ•^ shape, XD pawtitionew):
    sewf.weight = sewf.add_vawiabwe(
      'weight', ^^
      i-initiawizew=sewf.weight_initiawizew, o.O
      w-weguwawizew=sewf.weight_weguwawizew, ( ͡o ω ͡o )
      s-shape=shape, /(^•ω•^)
      dtype=sewf.dtype, 🥺
      t-twainabwe=twue, nyaa~~
      pawtitionew=pawtitionew, mya
    )

  def buiwd(sewf, XD input_shapes):
    """
    c-cweates the ``bias`` a-and ``weight`` vawiabwes
    o-of shape ``[output_size]`` and ``[input_size, nyaa~~ output_size]`` w-wespectivewy. ʘwʘ
    """

    if i-isinstance(input_shapes, (⑅˘꒳˘) (wist, tupwe)):
      i-input_shape = input_shapes[0]
      i-is_compatibwe = twue
      fow othew_shape in input_shapes[1:]:
        is_compatibwe &= i-input_shape.is_compatibwe_with(othew_shape)
      i-if nyot is_compatibwe:
        waise v-vawueewwow("input s-shapes %s a-awe nyot compatibwe." % input_shapes)
    e-ewse:
      i-input_shape = input_shapes

    s-sewf.bias = s-sewf.add_vawiabwe(
      'bias', :3
      initiawizew=sewf.bias_initiawizew, -.-
      w-weguwawizew=sewf.bias_weguwawizew, 😳😳😳
      shape=[sewf.output_size, (U ﹏ U) ],
      dtype=sewf.dtype, o.O
      t-twainabwe=twue
    )

    pawtitionew = nyone
    s-shape = [input_shape[1], ( ͡o ω ͡o ) s-sewf.output_size]

    # thewe i-is a 2gb wimitation fow each tensow because of pwotobuf. òωó
    # 2**30 i-is 1gb. 🥺 2 * (2**30) i-is 2gb. /(^•ω•^)
    d-dtype = tf.as_dtype(sewf.dtype)
    nyum_pawtitions = 1 if sewf.num_pawtitions i-is nyone ewse sewf.num_pawtitions
    in_shape = i-input_shape[1]
    o-out_shape = sewf.output_size

    # w-when v2 behaviow is d-disabwed, 😳😳😳 in_shape i-is tf.dimension. ^•ﻌ•^ othewwise it is int. nyaa~~
    if i-isinstance(in_shape, OwO tf.dimension):
      in_shape = i-in_shape.vawue

    i-if in_shape is nyone:
      w-waise vawueewwow("input tensow s-shouwd have s-shape."
                       " y-you can set it using twmw.utiw.wimit_spawse_tensow_size")

    (spwit_dim, ^•ﻌ•^ othew_dim) = (in_shape, σωσ out_shape) if sewf.pawtition_axis == 0 ewse (out_shape, -.- in_shape)
    wequested_size = math.ceiw(fwoat(spwit_dim) / num_pawtitions) * othew_dim * dtype.size
    if (wequested_size >= 2**31):
      w-waise vawueewwow("weight t-tensow pawtitions cannot be wawgew than 2gb.\n"
                       "wequested d-dimensions(%d, (˘ω˘) %d) o-of type %s (%d b-bytes totaw) ovew %d pawtitions.\n"
                       "possibwe s-sowutions:\n"
                       "- weduce the pawams.output_size_bits\n"
                       "- w-weduce the output_size o-of the spawse_wayew\n"
                       "- s-specify a wawgew nyum_pawtitions a-awgument\n"
                       "- w-weduce input_size_bits" %
                       (in_shape, rawr x3 sewf.output_size, rawr x3 dtype.name, wequested_size, n-nyum_pawtitions))

    i-if sewf.num_pawtitions:
      p-pawtition_axis = i-int(sewf.pawtition_axis)
      p-pawtitionew = tf.fixed_size_pawtitionew(sewf.num_pawtitions, σωσ a-axis=pawtition_axis)
    e-ewse:
      # w-weguwaw vawiabwes d-do not wike it when you pass b-both constant t-tensows and shape
      i-if nyot cawwabwe(sewf.weight_initiawizew):
        s-shape = nyone

    sewf._make_weight_vaw(shape, nyaa~~ p-pawtitionew)

    sewf.buiwt = twue

  d-def compute_output_shape(sewf, (ꈍᴗꈍ) i-input_shape):
    """computes t-the output shape of the wayew given t-the input shape. ^•ﻌ•^

    awgs:
      i-input_shape: a (possibwy n-nyested tupwe of) `tensowshape`. >_<  it nyeed nyot
        b-be fuwwy defined (e.g. ^^;; the batch size may be unknown). ^^;;

    waises nyotimpwementedewwow. /(^•ω•^)

    """
    w-waise nyotimpwementedewwow

  d-def c-caww(sewf, nyaa~~ inputs, (✿oωo) **kwawgs):  # pywint: disabwe=unused-awgument
    """the wogic of the wayew wives h-hewe. ( ͡o ω ͡o )

    awguments:
      i-inputs:
        a-a spawsetensow o-ow a wist of spawsetensows. (U ᵕ U❁)
        if `inputs` is a wist, òωó aww tensows m-must have s-same `dense_shape`. σωσ

    wetuwns:
      - i-if `inputs` is `spawsetensow`, :3 then wetuwns `bias + inputs * d-dense_b`. OwO
      - if `inputs` i-is a `wist[spawsetensow`, ^^ t-then wetuwns
        `bias + a-add_n([sp_a * dense_b f-fow sp_a in inputs])`. (˘ω˘)

    """
    i-if isinstance(inputs, OwO (wist, UwU t-tupwe)):

      i-if isinstance(sewf.use_binawy_vawues, ^•ﻌ•^ (wist, (ꈍᴗꈍ) tupwe)):
        u-use_binawy_vawues = s-sewf.use_binawy_vawues
      e-ewse:
        u-use_binawy_vawues = [sewf.use_binawy_vawues] * w-wen(inputs)

      n-nyum_inputs = w-wen(inputs)
      i-if nyum_inputs != wen(use_binawy_vawues):
        w-waise vawueewwow("#inputs is %d whiwe #use_binawy_vawues i-is %d"
                         % (num_inputs, /(^•ω•^) wen(use_binawy_vawues)))

      o-outputs = []
      f-fow ny in wange(num_inputs):
        o-outputs.append(spawse_dense_matmuw(inputs[n], (U ᵕ U❁) sewf.weight, (✿oωo)
                                           sewf.use_spawse_gwads, OwO
                                           use_binawy_vawues[n], :3
                                           n-nyame='spawse_mm_' + s-stw(n), nyaa~~
                                           p-pawtition_axis=sewf.pawtition_axis, ^•ﻌ•^
                                           num_pawtitions=sewf.num_pawtitions, ( ͡o ω ͡o )
                                           compwess_ids=sewf._use_compwession, ^^;;
                                           cast_indices_dtype=sewf._cast_indices_dtype, mya
                                           u-use_binawy_spawse_dense_matmuw=sewf.use_binawy_spawse_dense_matmuw))
      o-outputs = tf.accumuwate_n(outputs)
    e-ewse:

      i-if isinstance(sewf.use_binawy_vawues, (U ᵕ U❁) (wist, ^•ﻌ•^ tupwe)):
        waise vawueewwow("use_binawy_vawues can n-nyot be %s when i-inputs is %s" %
                         (type(sewf.use_binawy_vawues), (U ﹏ U) t-type(inputs)))

      o-outputs = spawse_dense_matmuw(inputs, /(^•ω•^) sewf.weight, ʘwʘ
                                    s-sewf.use_spawse_gwads,
                                    s-sewf.use_binawy_vawues, XD
                                    nyame='spawse_mm', (⑅˘꒳˘)
                                    pawtition_axis=sewf.pawtition_axis, nyaa~~
                                    n-nyum_pawtitions=sewf.num_pawtitions, UwU
                                    compwess_ids=sewf._use_compwession,
                                    cast_indices_dtype=sewf._cast_indices_dtype, (˘ω˘)
                                    u-use_binawy_spawse_dense_matmuw=sewf.use_binawy_spawse_dense_matmuw)

    if sewf.bias i-is nyot nyone:
      o-outputs = tf.nn.bias_add(outputs, rawr x3 s-sewf.bias)

    i-if sewf.activation is n-nyot nyone:
      wetuwn sewf.activation(outputs)  # p-pywint: disabwe=not-cawwabwe
    w-wetuwn outputs


d-def fuww_spawse(
        i-inputs, (///ˬ///✿) output_size, 😳😳😳
        input_size=none, (///ˬ///✿)
        a-activation=none, ^^;;
        bias_weguwawizew=none, ^^
        w-weight_weguwawizew=none, (///ˬ///✿)
        bias_initiawizew=none, -.-
        w-weight_initiawizew=none, /(^•ω•^)
        twainabwe=twue, UwU
        nyame=none, (⑅˘꒳˘)
        w-weuse=none, ʘwʘ
        use_spawse_gwads=twue, σωσ
        nyum_pawtitions=none, ^^
        pawtition_axis=0, OwO
        u-use_binawy_vawues=fawse, (ˆ ﻌ ˆ)♡
        u-use_compwession=fawse):
  """functionaw intewface f-fow the spawsewy-connected wayew. o.O

  awguments:
    inputs:
      a spawse t-tensow (can be twmw.spawsetensow o-ow tf.spawsetensow)
    o-output_size:
      wong ow integew, (˘ω˘) dimensionawity o-of the output space. 😳
    weight_initiawizew:
      i-initiawizew function f-fow the w-weight matwix. (U ᵕ U❁)
    a-activation:
      a-activation function (cawwabwe). :3 set it to nyone to maintain a wineaw activation. o.O
    b-bias_initiawizew:
      initiawizew function f-fow the bias. (///ˬ///✿)
    weight_weguwawizew:
      weguwawizew function fow the w-weight matwix. OwO
      ensuwe to add tf.wosses.get_weguwawization_woss() to youw woss fow this to t-take effect. >w<
    b-bias_weguwawizew:
      weguwawizew f-function fow the bias. ^^
      ensuwe to add t-tf.wosses.get_weguwawization_woss() t-to youw woss fow this to take e-effect. (⑅˘꒳˘)
    twainabwe:
      boowean, ʘwʘ if `twue` a-awso add vawiabwes to the gwaph cowwection
      ``gwaphkeys.twainabwe_vawiabwes`` (see `tf.vawiabwe
      <https://www.tensowfwow.owg/vewsions/mastew/api_docs/python/tf/vawiabwe>`_). (///ˬ///✿)
    nyame:
      s-stwing, XD the nyame of the wayew. 😳 wayews w-with the same n-nyame wiww
      s-shawe weights, >w< but to avoid mistakes we wequiwe ``weuse=twue`` i-in such cases. (˘ω˘)
    use_spawse_gwads:
      boowean, nyaa~~ if `twue` do spawse mat muw w-with `embedding_wookup_spawse`, 😳😳😳 w-which wiww
      m-make gwadients t-to weight matwix awso spawse in backwawd pass. (U ﹏ U) this c-can wead to n-nyon-twiviaw
      speed up at twaining time when i-input_size is wawge and optimizew handwes spawse g-gwadients
      cowwectwy (eg. (˘ω˘) with sgd ow wazyadamoptimizew). :3 i-if weight matwix i-is smow, >w< it's wecommended
      t-to set this fwag t-to `fawse`; f-fow most use cases of fuwwspawse, ^^ howevew, weight m-matwix wiww
      be wawge, 😳😳😳 so it's bettew to s-set it to `twue`
    nyum_pawtitions:
      nyumbew of pawtitions t-to use fow the w-weight vawiabwe. nyaa~~ d-defauwts to 1. (⑅˘꒳˘)
    p-pawtition_axis:
      i-if nyum_pawtitions is s-specified, the pawtition axis fow the weight vawiabwe
      d-defauwts to 0 (pawtition b-by wow). :3
      must be 0 (wow) ow 1 (cowumn)
    u-use_binawy_vawues:
      a-assume aww nyon zewo vawues awe 1. ʘwʘ d-defauwts to fawse. rawr x3
      this c-can impwove twaining i-if used in conjunction with m-mdw. (///ˬ///✿)
    use_compwession:
      d-defauwt fawse. 😳😳😳 set twue to enabwe d-data compwession techniques fow
      optimization of nyetwowk t-twaffic fow distwibuted twaining. XD
  w-wetuwns:
    outputs a ``tf.tensow`` of size ``[batch_size x-x output_size]``. >_<
  """
  # t-todo - w-wemove input_size wawning. >w<
  i-if input_size:
    w-waise vawueewwow('input_size is depwecated - i-it is now \
                      automaticawwy i-infewwed fwom youw input.')

  d-dtype = nyone
  i-if isinstance(inputs, /(^•ω•^) twmw.spawsetensow):
    inputs = inputs.to_tf()
    dtype = inputs.dtype.base_dtype

  i-if i-isinstance(inputs, :3 (wist, tupwe)):
    inputs = [inp.to_tf() if i-isinstance(inp, ʘwʘ twmw.spawsetensow) e-ewse inp fow i-inp in inputs]
    dtype = inputs[0].dtype.base_dtype

  wayew = fuwwspawse(output_size=output_size, (˘ω˘)
                     activation=activation, (ꈍᴗꈍ)
                     t-twainabwe=twainabwe, ^^
                     nyame=name, ^^
                     weight_initiawizew=weight_initiawizew, ( ͡o ω ͡o )
                     b-bias_initiawizew=bias_initiawizew, -.-
                     weight_weguwawizew=weight_weguwawizew, ^^;;
                     b-bias_weguwawizew=bias_weguwawizew, ^•ﻌ•^
                     d-dtype=dtype, (˘ω˘)
                     _scope=name, o.O
                     _weuse=weuse, (✿oωo)
                     use_spawse_gwads=use_spawse_gwads, 😳😳😳
                     n-nyum_pawtitions=num_pawtitions, (ꈍᴗꈍ)
                     p-pawtition_axis=pawtition_axis, σωσ
                     u-use_compwession=use_compwession, UwU
                     u-use_binawy_vawues=use_binawy_vawues)
  wetuwn w-wayew(inputs)
