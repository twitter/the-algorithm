# nclude "tensorflow/core/fra work/op.h"
# nclude "tensorflow/core/fra work/shape_ nference.h"
# nclude "tensorflow/core/fra work/op_kernel.h"
# nclude "tensorflow/core/ut l/work_sharder.h"

# nclude <twml.h>
# nclude "tensorflow_ut ls.h"

us ng na space tensorflow;

vo d ComputeHash ngD scret zer(
  OpKernelContext*,
   nt64_t,
  const twml::Map< nt64_t,  nt64_t> &,
   nt64_t,
   nt64_t,
   nt64_t);

REG STER_OP("Hash ngD scret zer")
.Attr("T: {float, double}")
. nput(" nput_ ds:  nt64")
. nput(" nput_vals: T")
. nput("b n_vals: T")
.Attr("feature_ ds: tensor = { dtype: DT_ NT64 }")
.Attr("n_b n:  nt")
.Attr("output_b s:  nt")
.Attr("cost_per_un :  nt")
.Attr("opt ons:  nt")
.Output("new_keys:  nt64")
.Output("new_vals: T")
.SetShapeFn(
  [](::tensorflow::shape_ nference:: nferenceContext* c) {
    c->set_output(0, c-> nput(0));
    c->set_output(1, c-> nput(1));
    return Status::OK();
  }
)
.Doc(R"doc(

T  operat on d scret zes a tensor conta n ng cont nuous features ( f cal brated).
  - note - cho ce of float or double should be cons stent among  nputs/output

 nput
   nput_ ds( nt64): A tensor conta n ng  nput feature  ds (d rect from data record).
   nput_vals(float/double): A tensor conta n ng  nput values at correspond ng feature  ds.
    -  .e.  nput_ ds[ ] <->  nput_vals[ ] for each  
  b n_vals(float/double): A tensor conta n ng t  b n boundar es for values of a g ven feature.
    - float or double, match ng  nput_vals
  feature_ ds( nt64 attr): 1D TensorProto of feature  Ds seen dur ng cal brat on
    -> h nt: look up make_tensor_proto:
       proto_ n  = np.array(values, dtype=np. nt64)
       tensor_attr = tf.make_tensor_proto(proto_ n )
  n_b n( nt): T  number of b n boundary values per feature
    ->  nce, n_b n + 1 buckets for each feature
  output_b s( nt): T  max mum number of b s to use for t  output  Ds.
  cost_per_un ( nt): An est mate of t  number of CPU cycles (or nanoseconds
     f not CPU-bound) to complete a un  of work. Overest mat ng creates too
    many shards and CPU t   w ll be dom nated by per-shard over ad, such as
    Context creat on. Underest mat ng may not fully make use of t  spec f ed
    parallel sm.
  opt ons( nt): selects behav or of t  op.
    0x00  n b s{1:0} for std::lo r_bound bucket search.
    0x01  n b s{1:0} for l near bucket search
    0x02  n b s{1:0} for std::upper_bound bucket search
    0x00  n b s{4:2} for  nteger_mult pl cat ve_hash ng
    0x01  n b s{4:2} for  nteger64_mult pl cat ve_hash ng
    h g r b s/ot r values are reserved for future extens ons

Outputs
  new_keys( nt64): T  d scret zed feature  ds w h sa  shape and s ze as keys.
  new_vals(float or double): T  d scret zed values w h t  sa  shape and s ze as vals.

Operat on
  Note that t  d scret zat on operat on maps observat on vectors to h g r d  ns onal
    observat on vectors.  re,   descr be t  mapp ng.

  Let a cal brated feature observat on be g ven by (F,x), w re F  s t   D of t 
    feature, and x  s so  real value ( .e., cont nuous feature). T  k nd of
    representat on  s useful for t  representat on of sparse vectors, w re t re
    are many zeros.

  For example, for a dense feature vector [1.2, 2.4, 3.6],   m ght have
    (0, 1.2) (1, 2.4) and (2, 3.6), w h feature  Ds  nd cat ng t  0th, 1st, and 2nd
    ele nts of t  vector.

  T  d sret zer performs t  follow ng operat on:
    (F,x) -> (map(x|F),1).
   nce,   have that map(x|F)  s a new feature  D, and t  value observed for that
    feature  s 1.   m ght read map(x|F) as 't  map of x for feature F'.

  For each feature F,   assoc ate a (d screte, f n e) set of new feature  Ds, new Ds(F).
      w ll t n have that map(x|F)  s  n t  set new Ds(F) for any value of x. Each
    set  mber of new Ds(F)  s assoc ated w h a 'b n', as def ned by t  b n
    boundar es g ven  n t  b n_vals  nput array. For any two d fferent feature  Ds F
    and G,   would  deally have that  NTERSECT(new Ds(F),new Ds(G))  s t  empty set.
    Ho ver, t   s not guaranteed for t  d scret zer.

   n t  case of t  hash ng d scret zer, map(x|F) can actually be wr ten as follows:
    let bucket = bucket(x|F) be t  t  bucket  ndex for x, accord ng to t 
    cal brat on on F. (T   s an  nteger value  n [0,n_b n],  nclus ve)
    F  s an  nteger  D.  re,   have that map(x|F) = hash_fn(F,bucket). T  has
    t  des rable property that t  new  D depends only on t  cal brat on data
    suppl ed for feature F, and not on any ot r features  n t  dataset (e.g.,
    number of ot r features present  n t  cal brat on data, or order of features
     n t  dataset). Note that Percent leD scret zer does NOT have t  property.
    T  co s at t  expense of t  poss b l y of output  D coll s ons, wh ch
      try to m n m ze through t  des gn of hash_fn.

  Example - cons der  nput vector w h a s ngle ele nt,  .e. [x].
    Let's D scret ze to one of 2 values, as follows:
    Let F=0 for t   D of t  s ngle feature  n t  vector.
    Let t  b n boundary of feature F=0 be BNDRY(F) = BNDRY(0) s nce F=0
    bucket = bucket(x|F=0) = 0  f x<=BNDRY(0) else 1
    Let map(x|F) = hash_fn(F=0,bucket=0)  f x<=BNDRY(0) else hash_fn(F=0,bucket=1)
   f   had anot r ele nt y  n t  vector,  .e. [x, y], t n   m ght add  onally
    Let F=1 for ele nt y.
    Let t  b n boundary be BNDRY(F) = BNDRY(1) s nce F=1
    bucket = bucket(x|F=1) = 0  f x<=BNDRY(1) else 1
    Let map(x|F) = hash_fn(F=1,bucket=0)  f x<=BNDRY(1) else hash_fn(F=1,bucket=1)
  Note how t  construct on of map(x|F=1) does not depend on w t r map(x|F=0)
    was constructed.
)doc");

template<typena  T>
class Hash ngD scret zer : publ c OpKernel {
 publ c:
  expl c  Hash ngD scret zer(OpKernelConstruct on* context) : OpKernel(context) {
    OP_REQU RES_OK(context,
                   context->GetAttr("n_b n", &n_b n_));
    OP_REQU RES(context,
                n_b n_ > 0,
                errors:: nval dArgu nt("Must have n_b n_ > 0."));

    OP_REQU RES_OK(context,
                   context->GetAttr("output_b s", &output_b s_));
    OP_REQU RES(context,
                output_b s_ > 0,
                errors:: nval dArgu nt("Must have output_b s_ > 0."));

    OP_REQU RES_OK(context,
                   context->GetAttr("cost_per_un ", &cost_per_un _));
    OP_REQU RES(context,
                cost_per_un _ >= 0,
                errors:: nval dArgu nt("Must have cost_per_un  >= 0."));

    OP_REQU RES_OK(context,
                   context->GetAttr("opt ons", &opt ons_));

    // construct t   D_to_ ndex hash map
    Tensor feature_ Ds;

    // extract t  tensors
    OP_REQU RES_OK(context,
                   context->GetAttr("feature_ ds", &feature_ Ds));

    // for access to t  data
    //  nt64_t data type  s set  n to_layer funct on of t  cal brator objects  n Python
    auto feature_ Ds_flat = feature_ Ds.flat< nt64>();

    // ver fy proper d  ns on constra nts
    OP_REQU RES(context,
                feature_ Ds.shape().d ms() == 1,
                errors:: nval dArgu nt("feature_ ds must be 1D."));

    // reserve space  n t  hash map and f ll  n t  values
     nt64_t num_features = feature_ Ds.shape().d m_s ze(0);
# fdef USE_DENSE_HASH
     D_to_ ndex_.set_empty_key(0);
     D_to_ ndex_.res ze(num_features);
#else
     D_to_ ndex_.reserve(num_features);
#end f  // USE_DENSE_HASH
    for ( nt64_t   = 0 ;   < num_features ;  ++) {
       D_to_ ndex_[feature_ Ds_flat( )] =  ;
    }
  }

  vo d Compute(OpKernelContext* context) overr de {
    ComputeHash ngD scret zer(
      context,
      output_b s_,
       D_to_ ndex_,
      n_b n_,
      cost_per_un _,
      opt ons_);
  }

 pr vate:
  twml::Map< nt64_t,  nt64_t>  D_to_ ndex_;
   nt n_b n_;
   nt output_b s_;
   nt cost_per_un _;
   nt opt ons_;
};

#def ne REG STER(Type)              \
  REG STER_KERNEL_BU LDER(          \
    Na ("Hash ngD scret zer")      \
    .Dev ce(DEV CE_CPU)             \
    .TypeConstra nt<Type>("T"),     \
    Hash ngD scret zer<Type>);      \

REG STER(float);
REG STER(double);

vo d ComputeHash ngD scret zer(
    OpKernelContext* context,
     nt64_t output_b s,
    const twml::Map< nt64_t,  nt64_t> & D_to_ ndex,
     nt64_t n_b n,
     nt64_t cost_per_un ,
     nt64_t opt ons) {
  const Tensor& keys = context-> nput(0);
  const Tensor& vals = context-> nput(1);
  const Tensor& b n_vals = context-> nput(2);

  const  nt64 output_s ze = keys.d m_s ze(0);

  TensorShape output_shape;
  OP_REQU RES_OK(context, TensorShapeUt ls::MakeShape(&output_s ze, 1, &output_shape));

  Tensor* new_keys = nullptr;
  OP_REQU RES_OK(context, context->allocate_output(0, output_shape, &new_keys));
  Tensor* new_vals = nullptr;
  OP_REQU RES_OK(context, context->allocate_output(1, output_shape, &new_vals));

  try {
    twml::Tensor out_keys_ = TFTensor_to_twml_tensor(*new_keys);
    twml::Tensor out_vals_ = TFTensor_to_twml_tensor(*new_vals);

    const twml::Tensor  n_keys_ = TFTensor_to_twml_tensor(keys);
    const twml::Tensor  n_vals_ = TFTensor_to_twml_tensor(vals);
    const twml::Tensor b n_vals_ = TFTensor_to_twml_tensor(b n_vals);

    // retr eve t  thread pool from t  op context
    auto worker_threads = *(context->dev ce()->tensorflow_cpu_worker_threads());

    // Def n  on of t  computat on thread
    auto task = [&]( nt64 start,  nt64 l m ) {
      twml::hashD scret zer nfer(out_keys_, out_vals_,
                              n_keys_,  n_vals_,
                             n_b n,
                             b n_vals_,
                             output_b s,
                              D_to_ ndex,
                             start, l m ,
                             opt ons);
    };

    // let Tensorflow spl  up t  work as   sees f 
    Shard(worker_threads.num_threads,
          worker_threads.workers,
          output_s ze,
          stat c_cast< nt64>(cost_per_un ),
          task);
  } catch (const std::except on &e) {
    context->CtxFa lureW hWarn ng(errors:: nval dArgu nt(e.what()));
  }
}

