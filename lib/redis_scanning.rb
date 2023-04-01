module RedisScanning
  def scan_set_in_batches(redis, key, batch_size:, &block)
    redis.sscan_each(key, count: batch_size).each_slice(batch_size, &block)
  end
end
