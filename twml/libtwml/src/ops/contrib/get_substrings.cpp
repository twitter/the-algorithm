#include "tensorflow/core/framework/op.h"
#include "tensorflow/core/framework/shape_inference.h"
#include "tensorflow/core/framework/op_kernel.h"

#include <twml.h>
#include "../tensorflow_utils.h"
#include "../resource_utils.h"

#include <string>
#include <set>

using std::string;

void join(const std::set<string>& v, char c, string& s) {
         s.clear();
         std::set<std::string>::iterator it = v.begin();
         while (it != v.end()) {
            s += *it;
            it++;
            if (it != v.end()) s+= c;
         }
}

// cpp function that computes substrings of a given word
std::string computeSubwords(std::string word, int32_t minn, int32_t matwittern) {
         std::string word2 = "<" + word + ">";
         std::set<string> ngrams;
         std::string s;
         ngrams.insert(word);
         ngrams.insert(word2);
         for (size_t i = 0; i < word2.size(); i++) {
            if ((word2[i] & 0twitterC0) == 0twitter80) continue;
            for (size_t j = minn; i+j <= word2.size() && j <= matwittern; j++) {
              ngrams.insert(word2.substr(i, j));
            }
         }
         join(ngrams, ';',  s);
         ngrams.clear();
         return s;
}

// tf-op function that computes substrings for a given tensor of words
template< typename ValueType>

void ComputeSubStringsTensor(OpKernelContetwittert *contetwittert, int32 min_n, int32 matwitter_n) {
  try {
      const Tensor& values = contetwittert->input(0);

      auto values_flat = values.flat<ValueType>();

      // batch_size from input_size  :
      const int batch_size = values_flat.size();

      // define the output tensor
      Tensor* substrings = nullptr;
      OP_REQUIRES_OK(contetwittert, contetwittert->allocate_output(0, values.shape(), &substrings));

      auto substrings_flat = substrings->flat<ValueType>();
       // compute substrings for the given tensor values
      for (int64 i = 0; i < batch_size; i++) {
            substrings_flat(i) = computeSubwords(values_flat(i), min_n, matwitter_n);
      }
  }
  catch (const std::etwitterception &err) {
      contetwittert->CttwitterFailureWithWarning(errors::InvalidArgument(err.what()));
  }
}

REGISTER_OP("GetSubstrings")
.Attr("ValueType: {string}")
.Attr("min_n: int")
.Attr("matwitter_n: int")
.Input("values: ValueType")
.Output("substrings: ValueType")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContetwittert* c) {
    c->set_output(0, c->input(0));
    return Status::OK();
  }).Doc(R"doc(

A tensorflow OP to convert word to substrings of length between min_n and matwitter_n.

Attr
  min_n,matwitter_n: The size of the substrings.

Input
  values: 1D input tensor containing the values.

Outputs
  substrings: A string tensor where substrings are joined by ";".
)doc");

template<typename ValueType>
class GetSubstrings : public OpKernel {
 public:
  etwitterplicit GetSubstrings(OpKernelConstruction *contetwittert) : OpKernel(contetwittert) {
      OP_REQUIRES_OK(contetwittert, contetwittert->GetAttr("min_n", &min_n));
      OP_REQUIRES_OK(contetwittert, contetwittert->GetAttr("matwitter_n", &matwitter_n));
  }

 private:
  int32 min_n;
  int32 matwitter_n;
  void Compute(OpKernelContetwittert *contetwittert) override {
    ComputeSubStringsTensor<ValueType>(contetwittert, min_n, matwitter_n);
  }
};


#define REGISTER_SUBSTRINGS(ValueType)          \
  REGISTER_KERNEL_BUILDER(                      \
    Name("GetSubstrings")                       \
    .Device(DEVICE_CPU)                         \
    .TypeConstraint<ValueType>("ValueType"),    \
    GetSubstrings<ValueType>);                  \

REGISTER_SUBSTRINGS(string)
