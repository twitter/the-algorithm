#include "tensorflow/core/framework/op.h"
#include "tensorflow/core/framework/shape_inference.h"
#include "tensorflow/core/framework/op_kernel.h"
#include "tensorflow/core/util/work_sharder.h"

#include <twml.h>
#include "tensorflow_utils.h"

using namespace tensorflow;

void ComputeHashingDiscretizer(
  OpKernelContext*,
  int64_t,
  const twml::Map<int64_t, int64_t> &,
  int64_t,
  int64_t,
  int64_t);

REGISTER_OP("HashingDiscretizer")
.Attr("T: {float, double}")
.Input("input_ids: int64")
.Input("input_vals: T")
.Input("bin_vals: T")
.Attr("feature_ids: tensor = { dtype: DT_INT64 }")
.Attr("n_bin: int")
.Attr("output_bits: int")
.Attr("cost_per_unit: int")
.Attr("options: int")
.Output("new_keys: int64")
.Output("new_vals: T")
.SetShapeFn(
  [](::tensorflow::shape_inference::InferenceContext* c) {
    c->set_output(0, c->input(0));
    c->set_output(1, c->input(1));
    return Status::OK();
  }
)
.Doc(R"doc(

This operation discretizes a tensor containing continuous features (if calibrated).
  - note - choice of float or double should be consistent among inputs/output

Input
  input_ids(int64): A tensor containing input feature ids (direct from data record).
  input_vals(float/double): A tensor containing input values at corresponding feature ids.
    - i.e. input_ids[i] <-> input_vals[i] for each i
  bin_vals(float/double): A tensor containing the bin boundaries for values of a given feature.
    - float or double, matching input_vals
  feature_ids(int64 attr): 1D TensorProto of feature IDs seen during calibration
    -> hint: look up make_tensor_proto:
       proto_init = np.array(values, dtype=np.int64)
       tensor_attr = tf.make_tensor_proto(proto_init)
  n_bin(int): The number of bin boundary values per feature
    -> hence, n_bin + 1 buckets for each feature
  output_bits(int): The maximum number of bits to use for the output IDs.
  cost_per_unit(int): An estimate of the number of CPU cycles (or nanoseconds
    if not CPU-bound) to complete a unit of work. Overestimating creates too
    many shards and CPU time will be dominated by per-shard overhead, such as
    Context creation. Underestimating may not fully make use of the specified
    parallelism.
  options(int): selects behavior of the op.
    0x00 in bits{1:0} for std::lower_bound bucket search.
    0x01 in bits{1:0} for linear bucket search
    0x02 in bits{1:0} for std::upper_bound bucket search
    0x00 in bits{4:2} for integer_multiplicative_hashing
    0x01 in bits{4:2} for integer64_multiplicative_hashing
    higher bits/other values are reserved for future extensions

Outputs
  new_keys(int64): The discretized feature ids with same shape and size as keys.
  new_vals(float or double): The discretized values with the same shape and size as vals.

Operation
  Note that the discretization operation maps observation vectors to higher dimensional
    observation vectors. Here, we describe this mapping.

  Let a calibrated feature observation be given by (F,x), where F is the ID of the
    feature, and x is some real value (i.e., continuous feature). This kind of
    representation is useful for the representation of sparse vectors, where there
    are many zeros.

  For example, for a dense feature vector [1.2, 2.4, 3.6], we might have
    (0, 1.2) (1, 2.4) and (2, 3.6), with feature IDs indicating the 0th, 1st, and 2nd
    elements of the vector.

  The disretizer performs the following operation:
    (F,x) -> (map(x|F),1).
  Hence, we have that map(x|F) is a new feature ID, and the value observed for that
    feature is 1. We might read map(x|F) as 'the map of x for feature F'.

  For each feature F, we associate a (discrete, finite) set of new feature IDs, newIDs(F).
    We will then have that map(x|F) is in the set newIDs(F) for any value of x. Each
    set member of newIDs(F) is associated with a 'bin', as defined by the bin
    boundaries given in the bin_vals input array. For any two different feature IDs F
    and G, we would ideally have that INTERSECT(newIDs(F),newIDs(G)) is the empty set.
    However, this is not guaranteed for this discretizer.

  In the case of this hashing discretizer, map(x|F) can actually be written as follows:
    let bucket = bucket(x|F) be the the bucket index for x, according to the
    calibration on F. (This is an integer value in [0,n_bin], inclusive)
    F is an integer ID. Here, we have that map(x|F) = hash_fn(F,bucket). This has
    the desirable property that the new ID depends only on the calibration data
    supplied for feature F, and not on any other features in the dataset (e.g.,
    number of other features present in the calibration data, or order of features
    in the dataset). Note that PercentileDiscretizer does NOT have this property.
    This comes at the expense of the possibility of output ID collisions, which
    we try to minimize through the design of hash_fn.

  Example - consider input vector with a single element, i.e. [x].
    Let's Discretize to one of 2 values, as follows:
    Let F=0 for the ID of the single feature in the vector.
    Let the bin boundary of feature F=0 be BNDRY(F) = BNDRY(0) since F=0
    bucket = bucket(x|F=0) = 0 if x<=BNDRY(0) else 1
    Let map(x|F) = hash_fn(F=0,bucket=0) if x<=BNDRY(0) else hash_fn(F=0,bucket=1)
  If we had another element y in the vector, i.e. [x, y], then we might additionally
    Let F=1 for element y.
    Let the bin boundary be BNDRY(F) = BNDRY(1) since F=1
    bucket = bucket(x|F=1) = 0 if x<=BNDRY(1) else 1
    Let map(x|F) = hash_fn(F=1,bucket=0) if x<=BNDRY(1) else hash_fn(F=1,bucket=1)
  Note how the construction of map(x|F=1) does not depend on whether map(x|F=0)
    was constructed.
)doc");

template<typename T>
class HashingDiscretizer : public OpKernel {
 public:
  explicit HashingDiscretizer(OpKernelConstruction* context) : OpKernel(context) {
    OP_REQUIRES_OK(context,
                   context->GetAttr("n_bin", &n_bin_));
    OP_REQUIRES(context,
                n_bin_ > 0,
                errors::InvalidArgument("Must have n_bin_ > 0."));

    OP_REQUIRES_OK(context,
                   context->GetAttr("output_bits", &output_bits_));
    OP_REQUIRES(context,
                output_bits_ > 0,
                errors::InvalidArgument("Must have output_bits_ > 0."));

    OP_REQUIRES_OK(context,
                   context->GetAttr("cost_per_unit", &cost_per_unit_));
    OP_REQUIRES(context,
                cost_per_unit_ >= 0,
                errors::InvalidArgument("Must have cost_per_unit >= 0."));

    OP_REQUIRES_OK(context,
                   context->GetAttr("options", &options_));

    // construct the ID_to_index hash map
    Tensor feature_IDs;

    // extract the tensors
    OP_REQUIRES_OK(context,
                   context->GetAttr("feature_ids", &feature_IDs));

    // for access to the data
    // int64_t data type is set in to_layer function of the calibrator objects in Python
    auto feature_IDs_flat = feature_IDs.flat<int64>();

    // verify proper dimension constraints
    OP_REQUIRES(context,
                feature_IDs.shape().dims() == 1,
                errors::InvalidArgument("feature_ids must be 1D."));

    // reserve space in the hash map and fill in the values
    int64_t num_features = feature_IDs.shape().dim_size(0);
#ifdef USE_DENSE_HASH
    ID_to_index_.set_empty_key(0);
    ID_to_index_.resize(num_features);
#else
    ID_to_index_.reserve(num_features);
#endif  // USE_DENSE_HASH
    for (int64_t i = 0 ; i < num_features ; i++) {
      ID_to_index_[feature_IDs_flat(i)] = i;
    }
  }

  void Compute(OpKernelContext* context) override {
    ComputeHashingDiscretizer(
      context,
      output_bits_,
      ID_to_index_,
      n_bin_,
      cost_per_unit_,
      options_);
  }

 private:
  twml::Map<int64_t, int64_t> ID_to_index_;
  int n_bin_;
  int output_bits_;
  int cost_per_unit_;
  int options_;
};

#define REGISTER(Type)              \
  REGISTER_KERNEL_BUILDER(          \
    Name("HashingDiscretizer")      \
    .Device(DEVICE_CPU)             \
    .TypeConstraint<Type>("T"),     \
    HashingDiscretizer<Type>);      \

REGISTER(float);
REGISTER(double);

void ComputeHashingDiscretizer(
    OpKernelContext* context,
    int64_t output_bits,
    const twml::Map<int64_t, int64_t> &ID_to_index,
    int64_t n_bin,
    int64_t cost_per_unit,
    int64_t options) {
  const Tensor& keys = context->input(0);
  const Tensor& vals = context->input(1);
  const Tensor& bin_vals = context->input(2);

  const int64 output_size = keys.dim_size(0);

  TensorShape output_shape;
  OP_REQUIRES_OK(context, TensorShapeUtils::MakeShape(&output_size, 1, &output_shape));

  Tensor* new_keys = nullptr;
  OP_REQUIRES_OK(context, context->allocate_output(0, output_shape, &new_keys));
  Tensor* new_vals = nullptr;
  OP_REQUIRES_OK(context, context->allocate_output(1, output_shape, &new_vals));

  try {
    twml::Tensor out_keys_ = TFTensor_to_twml_tensor(*new_keys);
    twml::Tensor out_vals_ = TFTensor_to_twml_tensor(*new_vals);

    const twml::Tensor in_keys_ = TFTensor_to_twml_tensor(keys);
    const twml::Tensor in_vals_ = TFTensor_to_twml_tensor(vals);
    const twml::Tensor bin_vals_ = TFTensor_to_twml_tensor(bin_vals);

    // retrieve the thread pool from the op context
    auto worker_threads = *(context->device()->tensorflow_cpu_worker_threads());

    // Definition of the computation thread
    auto task = [&](int64 start, int64 limit) {
      twml::hashDiscretizerInfer(out_keys_, out_vals_,
                             in_keys_, in_vals_,
                             n_bin,
                             bin_vals_,
                             output_bits,
                             ID_to_index,
                             start, limit,
                             options);
    };

    // let Tensorflow split up the work as it sees fit
    Shard(worker_threads.num_threads,
          worker_threads.workers,
          output_size,
          static_cast<int64>(cost_per_unit),
          task);
  } catch (const std::exception &e) {
    context->CtxFailureWithWarning(errors::InvalidArgument(e.what()));
  }
}

