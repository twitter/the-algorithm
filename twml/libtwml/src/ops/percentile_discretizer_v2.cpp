#include "tensorflow/core/framework/op.h"
#include "tensorflow/core/framework/shape_inference.h"
#include "tensorflow/core/framework/op_kernel.h"
#include "tensorflow/core/util/work_sharder.h"

#include <twml.h>
#include "tensorflow_utils.h"


using namespace tensorflow;

void CombinedComputeDiscretizers(
  OpKernelContext*,
  int64_t,
  const twml::Map<int64_t, int64_t>&,
  int64_t);

REGISTER_OP("PercentileDiscretizerV2")
.Attr("T: {float, double}")
.Input("input_ids: int64")
.Input("input_vals: T")
.Input("bin_ids: int64")
.Input("bin_vals: T")
.Input("feature_offsets: int64")
.Input("start_compute: int64")
.Input("end_compute: int64")
.Attr("output_bits: int")
.Attr("feature_ids: tensor = { dtype: DT_INT64 }")
.Attr("feature_indices: tensor = { dtype: DT_INT64 }")
.Attr("cost_per_unit: int")
.Output("new_keys: int64")
.Output("new_vals: T")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
    // TODO: check sizes
    c->set_output(0, c->input(0));
    c->set_output(1, c->input(0));
    return Status::OK();
}).Doc(R"doc(

This operation discretizes a tensor containing continuous features (if calibrated).
  - note - choice of float or double should be consistent among inputs/output

Input
  input_ids(int64): A tensor containing input feature ids (direct from data record).
  input_vals: A tensor containing input values at corresponding feature ids.
    - i.e. input_ids[i] <-> input_vals[i] for each i
    - float or double
  bin_ids(int64): A tensor containing the discretized feature id for each bin.
  bin_vals: A tensor containing the bin boundaries for values of a given feature.
    - float or double
  feature_offsets(int64): Specifies the starting location of bins for a given feature id.
  start_compute(int64 scalar tensor): which index to start the computation at
  end_compute(int64 scalar tensor): which index to end the computation right before
    -> for example, (start_compute,end_compute)=(0,10) would compute on 0 thru 9
  output_bits(int): The maximum number of bits to use for the output IDs.
    -> 2**out_bits must be greater than bin_ids.size
  feature_ids(int64): 1D TensorProto of feature IDs seen during calibration
  feature_indices(int64): 1D TensorProto of feature indices corresponding with feature_IDs
    -> hint: look up make_tensor_proto:
       proto_init = np.array(values, dtype=np.int64)
       tensor_attr = tf.make_tensor_proto(my_proto_init)
  cost_per_unit(int): An estimate of the number of CPU cycles (or nanoseconds
    if not CPU-bound) to complete a unit of work. Overestimating creates too
    many shards and CPU time will be dominated by per-shard overhead, such as
    Context creation. Underestimating may not fully make use of the specified
    parallelism.

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
    elements of the vector

  The disretizer performs the following operation:
    (F,x) -> (map(x|F),1).
  Hence, we have that map(x|F) is a new feature ID, and the value observed for that
    feature is 1. We might read map(x|F) as 'the map of x for feature F'.

  For each feature F, we associate a (discrete, finite) set of new feature IDs, newIDs(F).
    We will then have that F~(x) is in the set newIDs(F) for any value of x. Each set member
    of newIDs(F) is associated with a 'bin', as defined by the bin boundaries given in
    the bin_vals input array. For any two different feature IDs F and G, we have that
    INTERSECT(newIDs(F),newIDs(G)) is the empty set

  Example - consider input vector with a single element, i.e. [x].
    Let's Discretize to one of 2 values, as follows:
    Let F=0 for the ID of the single feature in the vector.
    Let the bin boundary of feature F=0 be BNDRY(F) = BNDRY(0) since F=0
    Let newIDs(F) = newIDs(0) = {0,1}
    Let map(x|F) = map(x|0) = 0 if x<=BNDRY else 1
  If we had another element y in the vector, i.e. [x, y], then we might additionally
    Let F=1 for element y.
    Let the bin boundary be BNDRY(F) = BNDRY(1) since F=1
    Let newIDs(F) = newIDs(1) = {2,3} (so as to have empty intersect with newIDs(0))
    Let map(x|F) = map(x|1) = 2 if x<=BNDRY else 3
  Consider vector observation [-0.1, 0.2]. We then represent this as [(0, -0.1), (1, 0.2)]
    Let BNDRY(0) = BNDRY(1) = 0. When we discretize the vector, we get:
    (0, -0.1) -> (map(-0.1|0), 1) = (0, 1)
    (1,  0.2) -> (map( 0.2|1), 1) = (3, 1)
    Our output vector is then represented sparsely as [(0, 1), (3, 1)], and the dense
    representation of this could be [1, 0, 0, 1]

)doc");

template<typename T>
class PercentileDiscretizerV2 : public OpKernel {
 public:
  explicit PercentileDiscretizerV2(OpKernelConstruction* context) : OpKernel(context) {
    // get the number of output bits
    // for use with features that have not been calibrated
    OP_REQUIRES_OK(context,
                   context->GetAttr("output_bits", &output_bits_));
    OP_REQUIRES_OK(context,
                   context->GetAttr("cost_per_unit", &cost_per_unit_));
    OP_REQUIRES(context, cost_per_unit_ >= 0,
                errors::InvalidArgument("Must have cost_per_unit >= 0."));

    // construct the ID_to_index hash map
    Tensor feature_IDs;
    Tensor feature_indices;

    // extract the tensors
    OP_REQUIRES_OK(context,
                   context->GetAttr("feature_ids", &feature_IDs));
    OP_REQUIRES_OK(context,
                   context->GetAttr("feature_indices", &feature_indices));

    // for access to the data
    // int64_t data type is set in to_layer function of the calibrator objects in Python
    auto feature_IDs_flat = feature_IDs.flat<int64>();
    auto feature_indices_flat = feature_indices.flat<int64>();

    // verify proper dimension constraints
    OP_REQUIRES(context, feature_IDs.shape() == feature_indices.shape(),
                errors::InvalidArgument("feature_ids and feature_indices must be identical shape."));
    OP_REQUIRES(context, feature_IDs.shape().dims() == 1,
                errors::InvalidArgument("feature_ids and feature_indices must be 1D."));

    // reserve space in the hash map and fill in the values
    int num_features = feature_IDs.shape().dim_size(0);

#ifdef USE_DENSE_HASH
    ID_to_index_.set_empty_key(0);
    ID_to_index_.resize(num_features);
#else
    ID_to_index_.reserve(num_features);
#endif  // USE_DENSE_HASH
    for (int i = 0 ; i < num_features ; i++) {
      ID_to_index_[feature_IDs_flat(i)] = feature_indices_flat(i);
    }
  }

  void Compute(OpKernelContext* context) override {
    CombinedComputeDiscretizers(
      context,
      output_bits_,
      ID_to_index_,
      cost_per_unit_);
  }

 private:
  twml::Map<int64_t, int64_t> ID_to_index_;
  int output_bits_;
  int cost_per_unit_;
};

#define REGISTER(Type)              \
  REGISTER_KERNEL_BUILDER(          \
    Name("PercentileDiscretizerV2")         \
    .Device(DEVICE_CPU)             \
    .TypeConstraint<Type>("T"),     \
    PercentileDiscretizerV2<Type>);         \

REGISTER(float);
REGISTER(double);

void CombinedComputeDiscretizers(
    OpKernelContext* context,
    int64_t output_bits,
    const twml::Map<int64_t, int64_t> &ID_to_index,
    int64_t cost_per_unit) {
  const Tensor& keys = context->input(0);
  const Tensor& vals = context->input(1);
  const Tensor& bin_ids = context->input(2);
  const Tensor& bin_vals = context->input(3);
  const Tensor& feature_offsets = context->input(4);

  uint64 full_size = keys.dim_size(0);
  const int total_size = static_cast<int64>(full_size);
  TensorShape output_shape = {total_size};

  Tensor* new_keys = nullptr;
  OP_REQUIRES_OK(context, context->allocate_output(0, output_shape, &new_keys));
  Tensor* new_vals = nullptr;
  OP_REQUIRES_OK(context, context->allocate_output(1, output_shape, &new_vals));

  try {
    twml::Tensor out_keys_ = TFTensor_to_twml_tensor(*new_keys);
    twml::Tensor out_vals_ = TFTensor_to_twml_tensor(*new_vals);

    const twml::Tensor in_keys_ = TFTensor_to_twml_tensor(keys);
    const twml::Tensor in_vals_ = TFTensor_to_twml_tensor(vals);
    const twml::Tensor bin_ids_ = TFTensor_to_twml_tensor(bin_ids);
    const twml::Tensor bin_vals_ = TFTensor_to_twml_tensor(bin_vals);
    const twml::Tensor feature_offsets_ = TFTensor_to_twml_tensor(feature_offsets);

    // retrieve the thread pool from the op context
    auto worker_threads = *(context->device()->tensorflow_cpu_worker_threads());

    // Definition of the computation thread
    auto task = [&](int64 start, int64 limit) {
      twml::discretizerInfer(out_keys_, out_vals_,
                             in_keys_, in_vals_,
                             bin_ids_, bin_vals_,
                             feature_offsets_, output_bits,
                             ID_to_index,
                             start, limit,
                             start);
    };

    // let Tensorflow split up the work as it sees fit
    Shard(worker_threads.num_threads,
          worker_threads.workers,
          full_size,
          static_cast<int64>(cost_per_unit),
          task);
  }  catch (const std::exception &e) {
    context->CtxFailureWithWarning(errors::InvalidArgument(e.what()));
  }
}
