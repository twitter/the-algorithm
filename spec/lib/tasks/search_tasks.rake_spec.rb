require "spec_helper"

describe "rake search:run_world_index_queue" do
  it "reindexes works" do
    work = create(:work)
    IndexQueue.enqueue(work, :world)
    expect(WorkIndexer).to receive(:new)
      .with([work.id.to_s]).and_call_original
    expect(BookmarkedWorkIndexer).to receive(:new)
      .with([work.id.to_s]).and_call_original

    subject.invoke
  end

  it "reindexes pseuds" do
    pseud = create(:pseud)
    IndexQueue.enqueue(pseud, :world)
    expect(PseudIndexer).to receive(:new)
      .with([pseud.id.to_s]).and_call_original

    subject.invoke
  end

  it "reindexes tags" do
    tag = create(:tag)
    IndexQueue.enqueue(tag, :world)
    expect(TagIndexer).to receive(:new)
      .with([tag.id.to_s]).and_call_original

    subject.invoke
  end

  it "reindexes bookmarks" do
    bookmark = create(:bookmark)
    IndexQueue.enqueue(bookmark, :world)
    expect(BookmarkIndexer).to receive(:new)
      .with([bookmark.id.to_s]).and_call_original

    subject.invoke
  end

  it "reindexes series" do
    series = create(:series)
    IndexQueue.enqueue(series, :world)
    expect(BookmarkedSeriesIndexer).to receive(:new)
      .with([series.id.to_s]).and_call_original

    subject.invoke
  end

  it "reindexes external works" do
    external_work = create(:external_work)
    IndexQueue.enqueue(external_work, :world)
    expect(BookmarkedExternalWorkIndexer).to receive(:new)
      .with([external_work.id.to_s]).and_call_original

    subject.invoke
  end
end
