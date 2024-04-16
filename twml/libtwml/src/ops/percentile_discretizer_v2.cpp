# nclude "tensorflow/core/fra work/op.h"
# nclude "tensorflow/core/fra work/shape_ nference.h"
# nclude "tensorflow/core/fra work/op_kernel.h"
# nclude "tensorflow/core/ut l/work_sharder.h"

# nclude <twml.h>
# nclude "tensorflow_ut ls.h"


us ng na space tensorflow;

vo d Comb nedComputeD scret zers(
  OpKernelContext*,
   nt64_t,
  const twml::Map< nt64_t,  nt64_t>&,
   nt64_t);

REG STER_OP("Percent leD scret zerV2")
.Attr("T: {float, double}")
. nput(" nput_ ds:  nt64")
. nput(" nput_vals: T")
. nput("b n_ ds:  nt64")
. nput("b n_vals: T")
. nput("feature_offsets:  nt64")
. nput("start_compute:  nt64")
. nput("end_compute:  nt64")
.Attr("output_b s:  nt")
.Attr("feature_ ds: tensor = { dtype: DT_ NT64 }")
.Attr("feature_ nd ces: tensor = { dtype: DT_ NT64 }")
.Attr("cost_per_un :  nt")
.Output("new_keys:  nt64")
.Output("new_vals: T")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
    // TODO: c ck s zes
    c->set_output(0, c-> nput(0));
    c->set_output(1, c-> nput(0));
    return Status::OK();
}).Doc(R"doc(

T  operat on d scret zes a tensor conta n ng cont nuous features ( f cal brated).
  - note - cho ce of float or double should be cons stent among  nputs/output

 nput
   nput_ ds( nt64): A tensor conta n ng  nput feature  ds (d rect from data record).
   nput_vals: A tensor conta n ng  nput values at correspond ng feature  ds.
    -  .e.  nput_ ds[ ] <->  nput_vals[ ] for each  
    - float or double
  b n_ ds( nt64): A tensor conta n ng t  d scret zed feature  d for each b n.
  b n_vals: A tensor conta n ng t  b n boundar es for values of a g ven feature.
    - float or double
  feature_offsets( nt64): Spec f es t  start ng locat on of b ns for a g ven feature  d.
  start_compute( nt64 scalar tensor): wh ch  ndex to start t  computat on at
  end_compute( nt64 scalar tensor): wh ch  ndex to end t  computat on r ght before
    -> for example, (start_compute,end_compute)=(0,10) would compute on 0 thru 9
  output_b s( nt): T  max mum number of b s to use for t  output  Ds.
    -> 2**out_b s must be greater than b n_ ds.s ze
  feature_ ds( nt64): 1D TensorProto of feature  Ds seen dur ng cal brat on
  feature_ nd ces( nt64): 1D TensorProto of feature  nd ces correspond ng w h feature_ Ds
    -> h nt: look up make_tensor_proto:
       proto_ n  = np.array(values, dtype=np. nt64)
       tensor_attr = tf.make_tensor_proto( _proto_ n )
  cost_per_un ( nt): An est mate of t  number of CPU cycles (or nanoseconds
     f not CPU-bound) to complete a un  of work. Overest mat ng creates too
    many shards and CPU t   w ll be dom nated by per-shard over ad, such as
    Context creat on. Underest mat ng may not fully make use of t  spec f ed
    parallel sm.

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
    ele nts of t  vector

  T  d sret zer performs t  follow ng operat on:
    (F,x) -> (map(x|F),1).
   nce,   have that map(x|F)  s a new feature  D, and t  value observed for that
    feature  s 1.   m ght read map(x|F) as 't  map of x for feature F'.

  For each feature F,   assoc ate a (d screte, f n e) set of new feature  Ds, new Ds(F).
      w ll t n have that F~(x)  s  n t  set new Ds(F) for any value of x. Each set  mber
    of new Ds(F)  s assoc ated w h a 'b n', as def ned by t  b n boundar es g ven  n
    t  b n_vals  nput array. For any two d fferent feature  Ds F and G,   have that
     NTERSECT(new Ds(F),new Ds(G))  s t  empty set

  Example - cons der  nput vector w h a s ngle ele nt,  .e. [x].
    Let's D scret ze to one of 2 values, as follows:
    Let F=0 for t   D of t  s ngle feature  n t  vector.
    Let t  b n boundary of feature F=0 be BNDRY(F) = BNDRY(0) s nce F=0
    Let new Ds(F) = new Ds(0) = {0,1}
    Let map(x|F) = map(x|0) = 0  f x<=BNDRY else 1
   f   had anot r ele nt y  n t  vector,  .e. [x, y], t n   m ght add  onally
    Let F=1 for ele nt y.
    Let t  b n boundary be BNDRY(F) = BNDRY(1) s nce F=1
    Let new Ds(F) = new Ds(1) = {2,3} (so as to have empty  ntersect w h new Ds(0))
    Let map(x|F) = map(x|1) = 2  f x<=BNDRY else 3
  Cons der vector observat on [-0.1, 0.2].   t n represent t  as [(0, -0.1), (1, 0.2)]
    Let BNDRY(0) = BNDRY(1) = 0. W n   d scret ze t  vector,   get:
    (0, -0.1) -> (map(-0.1|0), 1) = (0, 1)
    (1,  0.2) -> (map( 0.2|1), 1) = (3, 1)
      output vector  s t n represented sparsely as [(0, 1), (3, 1)], and t  dense
    representat on of t  could be [1, 0, 0, 1]

)doc");

template<typena  T>
class Percent leD scret zerV2 : publ c OpKernel {
 publ c:
  expl c  Percent leD scret zerV2(OpKernelConstruct on* context) : OpKernel(context) {
    // get t  number of output b s
    // for use w h features that have not been cal brated
    OP_REQU RES_OK(context,
                   context->GetAttr("output_b s", &output_b s_));
    OP_REQU RES_OK(context,
                   context->GetAttr("cost_per_un ", &cost_per_un _));
    OP_REQU RES(context, cost_per_un _ >= 0,
                errors:: nval dArgu nt("Must have cost_per_un  >= 0."));

    // construct t   D_to_ ndex hash map
    Tensor feature_ Ds;
    Tensor feature_ nd ces;

    // extract t  tensors
    OP_REQU RES_OK(context,
                   context->GetAttr("feature_ ds", &feature_ Ds));
    OP_REQU RES_OK(context,
                   context->GetAttr("feature_ nd ces", &feature_ nd ces));

    // for access to t  data
    //  nt64_t data type  s set  n to_layer funct on of t  cal brator objects  n Python
    auto feature_ Ds_flat = feature_ Ds.flat< nt64>();
    auto feature_ nd ces_flat = feature_ nd ces.flat< nt64>();

    // ver fy proper d  ns on constra nts
    OP_REQU RES(context, feature_ Ds.shape() == feature_ nd ces.shape(),
                errors:: nval dArgu nt("feature_ ds and feature_ nd ces must be  dent cal shape."));
    OP_REQU RES(context, feature_ Ds.shape().d ms() == 1,
                errors:: nval dArgu nt("feature_ ds and feature_ nd ces must be 1D."));

    // reserve space  n t  hash map and f ll  n t  values
     nt num_features = feature_ Ds.shape().d m_s ze(0);

# fdef USE_DENSE_HASH
     D_to_ ndex_.set_empty_key(0);
     D_to_ ndex_.res ze(num_features);
#else
     D_to_ ndex_.reserve(num_features);
#end f  // USE_DENSE_HASH
    for ( nt   = 0 ;   < num_features ;  ++) {
       D_to_ ndex_[feature_ Ds_flat( )] = feature_ nd ces_flat( );
    }
  }

  vo d Compute(OpKernelContext* context) overr de {
    Comb nedComputeD scret zers(
      context,
      output_b s_,
       D_to_ ndex_,
      cost_per_un _);
  }

 pr vate:
  twml::Map< nt64_t,  nt64_t>  D_to_ ndex_;
   nt output_b s_;
   nt cost_per_un _;
};

#def ne REG STER(Type)              \
  REG STER_KERNEL_BU LDER(          \
    Na ("Percent leD scret zerV2")         \
    .Dev ce(DEV CE_CPU)             \
    .TypeConstra nt<Type>("T"),     \
    Percent leD scret zerV2<Type>);         \

REG STER(float);
REG STER(double);

vo d Comb nedComputeD scret zers(
    OpKernelContext* context,
     nt64_t output_b s,
    const twml::Map< nt64_t,  nt64_t> & D_to_ ndex,
     nt64_t cost_per_un ) {
  const Tensor& keys = context-> nput(0);
  const Tensor& vals = context-> nput(1);
  const Tensor& b n_ ds = context-> nput(2);
  const Tensor& b n_vals = context-> nput(3);
  const Tensor& feature_offsets = context-> nput(4);

  u nt64 full_s ze = keys.d m_s ze(0);
  const  nt total_s ze = stat c_cast< nt64>(full_s ze);
  TensorShape output_shape = {total_s ze};

  Tensor* new_keys = nullptr;
  OP_REQU RES_OK(context, context->allocate_output(0, output_shape, &new_keys));
  Tensor* new_vals = nullptr;
  OP_REQU RES_OK(context, context->allocate_output(1, output_shape, &new_vals));

  try {
    twml::Tensor out_keys_ = TFTensor_to_twml_tensor(*new_keys);
    twml::Tensor out_vals_ = TFTensor_to_twml_tensor(*new_vals);

    const twml::Tensor  n_keys_ = TFTensor_to_twml_tensor(keys);
    const twml::Tensor  n_vals_ = TFTensor_to_twml_tensor(vals);
    const twml::Tensor b n_ ds_ = TFTensor_to_twml_tensor(b n_ ds);
    const twml::Tensor b n_vals_ = TFTensor_to_twml_tensor(b n_vals);
    const twml::Tensor feature_offsets_ = TFTensor_to_twml_tensor(feature_offsets);

    // retr eve t  thread pool from t  op context
    auto worker_threads = *(context->dev ce()->tensorflow_cpu_worker_threads());

    // Def n  on of t  computat on thread
    auto task = [&]( nt64 start,  nt64 l m ) {
      twml::d scret zer nfer(out_keys_, out_vals_,
                              n_keys_,  n_vals_,
                             b n_ ds_, b n_vals_,
                             feature_offsets_, output_b s,
                              D_to_ ndex,
                             start, l m ,
                             start);
    };

    // let Tensorflow spl  up t  work as   sees f 
    Shard(worker_threads.num_threads,
          worker_threads.workers,
          full_s ze,
          stat c_cast< nt64>(cost_per_un ),
          task);
  }  catch (const std::except on &e) {
    context->CtxFa lureW hWarn ng(errors:: nval dArgu nt(e.what()));
  }
}
