# frozen_string_literal: true

require 'rspec/expectations'

# A matcher for checking whether a given item has been added to a particular
# indexing queue.
RSpec::Matchers.define :add_to_reindex_queue do |item, queue|
  match do |given_proc|
    return false unless given_proc.is_a?(Proc)

    queue_key = IndexQueue.get_key(item.class, queue)

    # Stub sadd so that we can track whether it was ever called with the right
    # arguments:
    allow(IndexQueue::REDIS).to receive(:sadd).and_call_original
    given_proc.call
    expect(IndexQueue::REDIS).to have_received(:sadd).with(
      queue_key, eq(item.id).or(include(item.id))
    ).at_least(:once)
  end

  failure_message do |given_proc|
    if given_proc.is_a?(Proc)
      "expected #{queue} reindexing queue to include #{item.inspect}"
    else
      "expected #{given_proc} to be a block"
    end
  end

  failure_message_when_negated do |given_proc|
    if given_proc.is_a?(Proc)
      "expected #{queue} reindexing queue not to include #{item.inspect}"
    else
      "expected #{given_proc} to be a block"
    end
  end

  supports_block_expectations do
    true
  end
end

# Define a negated matcher to allow for composition.
RSpec::Matchers.define_negated_matcher :not_add_to_reindex_queue, :add_to_reindex_queue
