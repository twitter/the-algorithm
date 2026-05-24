#ifndef TENSORFLOW_CORE_KERNELS_BINARY_SPARSE_TENSOR_DENSE_MATMUL_IMPL_H_
#define TENSORFLOW_CORE_KERNELS_BINARY_SPARSE_TENSOR_DENSE_MATMUL_IMPL_H_

#include <atomic>

#include "tensorflow/core/framework/op_kernel.h"
#include "tensorflow/core/lib/core/blocking_counter.h"
#include "tensorflow/core/lib/core/threadpool.h"

namespace tensorflow {
namespace functor {

// `ConservativeShard` is adopted rather than `Shard` in tensorflow because the
// original `Shard` may generate number of shards more than the number of
// threads, which is not ideal for this case, as it may cause too much overhead.
static void ConservativeShard(int max_parallelism, thread::ThreadPool *workers,
                              int64 total, int64 cost_per_unit,
                              std::function<void(int64, int64)> work) {
  if (total == 0) {
    return;
  }
  max_parallelism = std::min(max_parallelism, workers->NumThreads());
  if (max_parallelism <= 1) {
    // Just inline the whole work since we only have 1 thread (core).
    work(0, total);
    return;
  }
  cost_per_unit = std::max(1LL, cost_per_unit);
  // We shard [0, total) into "num_shards" shards.
  //   1 <= num_shards <= num worker threads
  //
  // If total * cost_per_unit is small, it is not worth shard too
  // much. Let us assume each cost unit is 1ns, kMinCostPerShard=10000
  // is 10us.
  static const int64 kMinCostPerShard = 10000;
  const int num_shards =
      std::max<int>(1, std::min(static_cast<int64>(max_parallelism),
                                total * cost_per_unit / kMinCostPerShard));

  // Each shard contains up to "block_size" units. [0, total) is sharded
  // into:
  //   [0, block_size), [block_size, 2*block_size), ...
  // The 1st shard is done by the caller thread and the other shards
  // are dispatched to the worker threads. The last shard may be smaller than
  // block_size.
  const int64 block_size = (total + num_shards - 1) / num_shards;
  if (block_size >= total) {
    work(0, total);
    return;
  }
  const int num_shards_used = (total + block_size - 1) / block_size;
  BlockingCounter counter(num_shards_used - 1);
  for (int64 start = block_size; start < total; start += block_size) {
    auto limit = std::min(start + block_size, total);
    workers->Schedule([&work, &counter, start, limit]() {
      work(start, limit);        // Compute the shard.
      counter.DecrementCount();  // The shard is done.
    });
  }

  // Inline execute the 1st shard.
  work(0, std::min(block_size, total));
  counter.Wait();
}

static inline void VectorSum(float *a, const float *b, int n) {
  for (int i = 0; i < n; ++i) {
    a[i] += b[i];
  }
}

// This func is to vectorize the computation of segment sum.
template<typename Tindices>
static void LookupAndSegmentSum(const Tindices *a_indices, const float *b,
                                int nnz, int outer_right, float *output) {
  for (std::size_t i = 0; i < nnz; ++i) {
    const Tindices m = a_indices[i * 2];
    const Tindices k = a_indices[i * 2 + 1];
    auto output_row_m = output + m * outer_right;
    auto b_row_k = b + k * outer_right;
    VectorSum(output_row_m, b_row_k, outer_right);
  }
}

// This func enables sharding and multithreading, it comes with an overhead of
// duplicating output buffer to achieve lock free output. So there should not
// be too many threads.
template<typename Tindices>
static void ParallelLookupAndSegmentSum(OpKernelContext *ctx,
                                        const Tindices *a_indices,
                                        const float *b, int nnz, int outer_left,
                                        int outer_right, float *output) {
  auto worker_threads = *(ctx->device()->tensorflow_cpu_worker_threads());
  int out_size = outer_left * outer_right;
  if (worker_threads.num_threads <= 1) {
    memset(output, 0, out_size * sizeof(float));
    LookupAndSegmentSum<Tindices>(a_indices, b, 
                                  nnz, outer_right,
                                  output);
    return;
  }

  // this is to make buffer align with kAllocatorAlignment
  int padded_out_size = (out_size + (Allocator::kAllocatorAlignment - 1)) &
                        ~(Allocator::kAllocatorAlignment - 1);
  std::size_t num_bytes =
      (worker_threads.num_threads - 1) * padded_out_size * sizeof(float);
  auto buffer = std::unique_ptr<float>(reinterpret_cast<float *>(
      port::AlignedMalloc(num_bytes, Allocator::kAllocatorAlignment)));
  float *temp_out = buffer.get();

  std::atomic<int> thread_index(0);

  auto task = [&](int64 start, int64 limit) {
    int local_thread_index = thread_index++;
    float *buf_ptr = nullptr;
    if (local_thread_index == 0) {
      buf_ptr = output;
    } else {
      buf_ptr = temp_out + (local_thread_index - 1) * padded_out_size;
    }
    memset(buf_ptr, 0, out_size * sizeof(float));

    LookupAndSegmentSum<Tindices>(a_indices + start * 2, b, 
                                  limit - start, outer_right,
                                  buf_ptr);
  };

  int cost_per_unit = outer_right;

  // We don't use tensorflow shard func as tf may create more shards than
  // number of threads.
  ConservativeShard(worker_threads.num_threads, worker_threads.workers, nnz,
                    static_cast<int64>(cost_per_unit), task);

  for (int i = 1; i < thread_index; ++i) {
    VectorSum(output, temp_out + (i - 1) * padded_out_size, out_size);
  }
}

}  // namespace functor

}  // namespace tensorflow

#endif  // TENSORFLOW_CORE_KERNELS_BINARY_SPARSE_TENSOR_DENSE_MATMUL_IMPL_H_