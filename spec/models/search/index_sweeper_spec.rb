require 'spec_helper'

describe IndexSweeper do

  describe "#async_cleanup" do
    it "should index items that were expected but not found" do
      expect(AsyncIndexer).to receive(:index).with(Work, [2], "cleanup")

      IndexSweeper.async_cleanup(Work, [1,2], [1])
    end
  end

  it "should record the first failure with a count of 1, and retry" do
    sweeper = IndexSweeper.new(batch, WorkIndexer)
    indexer = AsyncIndexer.new(WorkIndexer, "failures")

    expect(AsyncIndexer).to receive(:new).with(WorkIndexer, "failures").and_return(indexer)
    expect(indexer).to receive(:enqueue_ids).with([53])

    sweeper.process_batch

    expect(
      JSON.parse(IndexSweeper::REDIS.hget("WorkIndexer:failures", 53))
    ).to include("error" => { "an error" => "with a message" }, "count" => 1)
  end

  it "record the second failure with a count of 2, and retry" do
    sweeper = IndexSweeper.new(batch, WorkIndexer)
    indexer = AsyncIndexer.new(WorkIndexer, "failures")

    IndexSweeper::REDIS.hset(
      "WorkIndexer:failures",
      53,
      [{ "error" => { "an error" => "with a message" }, "count" => 1 }].to_json
    )

    expect(AsyncIndexer).to receive(:new).with(WorkIndexer, "failures").and_return(indexer)
    expect(indexer).to receive(:enqueue_ids).with([53])

    sweeper.process_batch

    expect(
      JSON.parse(IndexSweeper::REDIS.hget("WorkIndexer:failures", 53))
    ).to include("error" => { "an error" => "with a message" }, "count" => 2)
  end

  it "should record the third failure with a count of 3, but not retry" do
    sweeper = IndexSweeper.new(batch, WorkIndexer)

    IndexSweeper::REDIS.hset(
      "WorkIndexer:failures",
      53,
      [{ "error" => { "an error" => "with a message" }, "count" => 2 }].to_json
    )

    expect(AsyncIndexer).not_to receive(:new).with(WorkIndexer, "failures")

    sweeper.process_batch

    expect(
      JSON.parse(IndexSweeper::REDIS.hget("WorkIndexer:failures", 53))
    ).to include("error" => { "an error" => "with a message" }, "count" => 3)

    # In addition to having a count of 3, it should show up on the list of
    # permanent failures.
    expect(
      IndexSweeper.permanent_failures(WorkIndexer)
    ).to include("53" => { "an error" => "with a message" })
  end

  context "when documents succeed" do
    let(:batch) {
      {
        "errors" => false,
        "items" => [
          { "update" => { "_id" => "11" } },
          { "delete" => { "_id" => "12" } },
          { "index" => { "_id" => "13" } }
        ]
      }
    }

    let(:sweeper) { IndexSweeper.new(batch, WorkIndexer) }

    it "should remove documents from stores if they succeed" do
      IndexSweeper::REDIS.hset(
        "WorkIndexer:failures",
        11,
        [{ "error" => { "an error" => "a message" }, "count" => 1 }].to_json
      )

      IndexSweeper::REDIS.hset(
        "WorkIndexer:failures:second",
        12,
        [{ "error" => { "an error" => "a message" }, "count" => 2 }].to_json
      )

      IndexSweeper::REDIS.hset(
        "WorkIndexer:failures:permanent",
        13,
        [{ "error" => { "an error" => "a message" }, "count" => 3 }].to_json
      )

      expect(AsyncIndexer).not_to receive(:new)

      sweeper.process_batch

      # There should be no recorded errors:
      expect(IndexSweeper::REDIS.hvals("WorkIndexer:failures")).to eq([])
    end

    it "should perform success callbacks with the successful ids" do
      expect(WorkIndexer).to receive(:handle_success).with(%w[11 12 13])
                                                     .and_call_original
      expect(Work).to receive(:successful_reindex).with(%w[11 12 13])
      sweeper.process_batch
    end
  end

  it "should grab the elasticsearch ids expected by the indexer for retries" do
    work = create(:work)
    work.stat_counter.update(id: 3)

    sweeper = IndexSweeper.new(batch(work.id), StatCounterIndexer)
    indexer = AsyncIndexer.new(StatCounterIndexer, "failures")

    expect(AsyncIndexer).to receive(:new).with(StatCounterIndexer, "failures").at_least(:once).and_return(indexer)
    expect(indexer).to receive(:enqueue_ids).with([work.stat_counter.id]).at_least(:once).and_call_original

    expect(sweeper.process_batch).to be(true)
  end

  it "doesn't trigger an error if the batch results are empty" do
    sweeper = IndexSweeper.new(nil, WorkIndexer)
    expect { sweeper.process_batch }.not_to raise_exception
  end

  private

  def batch(id = 53)
    {
      "errors" => true,
      "items" => [
        {
          "update" => {
            "_id" => id,
            "error" => {
              "an error" => "with a message"
            }
          }
        }
      ]
    }
  end

end
