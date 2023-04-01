require 'spec_helper'

describe IndexQueue do
  it "should build correct keys" do
    expect(IndexQueue.get_key('StatCounter', :stats)).to eq("index:stat_counter:stats")
  end

  it "should enqueue objects" do
    work = Work.new
    work.id = 34
    IndexQueue.enqueue(work, :background)
    expect(IndexQueue.new("index:work:background").ids).to eq(['34'])
  end

  it "should enqueue ids" do
    IndexQueue.enqueue_id('Bookmark', 12, :background)
    expect(IndexQueue.new("index:bookmark:background").ids).to eq(['12'])
  end

  it "should have ids added to it" do
    iq = IndexQueue.new("index:work:main")
    iq.add_id(1)
    iq.add_id(2)
    expect(iq.ids).to eq(['1', '2'])
  end

  it "should create subqueues when run" do
    iq = IndexQueue.new("index:work:main")
    iq.add_id(1)
    iq.run

    expect(IndexQueue::REDIS.exists("index:work:main")).to be_falsey
  end

  describe "#run" do
    it "should call the work indexer" do
      work = create(:work)
      expect(WorkIndexer).to receive(:new).with(
        array_including(work.id.to_s)
      ).and_call_original
      IndexQueue.new("index:work:main").run
    end

    it "should call the bookmark indexer" do
      bookmark = create(:bookmark)
      expect(BookmarkIndexer).to receive(:new).with(
        array_including(bookmark.id.to_s)
      ).and_call_original
      IndexQueue.new("index:bookmark:main").run
    end

    it "should call the tag indexer" do
      tag = create(:freeform)
      expect(TagIndexer).to receive(:new).with(
        array_including(tag.id.to_s)
      ).and_call_original
      IndexQueue.new("index:tag:main").run
    end

    it "should call the pseud indexer" do
      pseud = create(:user).default_pseud
      expect(PseudIndexer).to receive(:new).with(
        array_including(pseud.id.to_s)
      ).and_call_original
      IndexQueue.new("index:pseud:main").run
    end

    it "should call the stat counter indexer" do
      stats = create(:work).stat_counter
      stats.update(hit_count: 10_000)
      expect(StatCounterIndexer).to receive(:new).with(
        array_including(stats.id.to_s)
      ).at_least(:once).and_call_original
      IndexQueue.new("index:stat_counter:stats").run
    end
  end
end
