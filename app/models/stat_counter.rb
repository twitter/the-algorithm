class StatCounter < ApplicationRecord
  belongs_to :work

  after_commit :enqueue_to_index, on: :update

  def enqueue_to_index
    IndexQueue.enqueue(self, :stats)
  end

  # Specify the indexer that should be used for this class
  def indexers
    [StatCounterIndexer]
  end
end
