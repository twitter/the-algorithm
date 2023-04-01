require 'spec_helper'

describe AsyncIndexer do
  it "should enqueue ids" do
    tag = Tag.new
    tag.id = 34

    indexer = AsyncIndexer.new(TagIndexer, :background)

    expect(AsyncIndexer).to receive(:new).with(TagIndexer, :background).and_return(indexer)
    expect(indexer).to receive(:enqueue_ids).with([34]).and_return(true)

    AsyncIndexer.index('Tag', [34], :background)
  end

  it "should retry batch errors" do
    work = Work.new
    work.id = 34

    indexer = WorkIndexer.new([34])
    batch = {
      "errors" => true,
      "items" => [{
        "update" => {
          "_id" => 34,
          "error" => {
            "problem" => "description"
          }
        }
      }]
    }

    async_indexer = AsyncIndexer.new(WorkIndexer, "failures")

    expect(AsyncIndexer::REDIS).to receive(:smembers).and_return([34])
    expect(WorkIndexer).to receive(:new).with([34]).and_return(indexer)
    expect(indexer).to receive(:index_documents).and_return(batch)
    expect(AsyncIndexer).to receive(:new).with(WorkIndexer, "failures").and_return(async_indexer)
    expect(async_indexer).to receive(:enqueue_ids).with([34]).and_return(true)

    AsyncIndexer.perform("WorkIndexer:34:#{Time.now.to_i}")
  end

  context "when persistent failures occur" do
    before do
      # Make elasticsearch always fail.
      allow($elasticsearch).to receive(:bulk) do |options|
        {
          "errors" => true,
          "items" => options[:body].map do |line|
            action = line.keys.first
            next unless (id = line[action]["_id"])

            {
              action.to_s => {
                "_id" => id,
                "error" => { "an error" => "with a message" }
              }
            }
          end.compact
        }
      end
    end

    it "should call the BookmarkedWorkIndexer three times with the same ID" do
      expect(BookmarkedWorkIndexer).to receive(:new).with(["99999"]).
        exactly(3).times.
        and_call_original
      AsyncIndexer.index(BookmarkedWorkIndexer, [99_999], "main")
    end

    it "should add the ID to the BookmarkedWorkIndexer's permanent failures" do
      AsyncIndexer.index(BookmarkedWorkIndexer, [99_999], "main")

      permanent_store = IndexSweeper.permanent_failures(BookmarkedWorkIndexer)

      expect(permanent_store).to include(
        "99999-work" => { "an error" => "with a message" }
      )
    end
  end

  context "when there are no IDs to index" do
    before do
      allow(AsyncIndexer::REDIS).to receive(:smembers).and_return([])
    end

    it "doesn't call the indexer" do
      expect(WorkIndexer).not_to receive(:new)

      AsyncIndexer.perform("WorkIndexer:34:#{Time.now.to_i}")
    end
  end
end
