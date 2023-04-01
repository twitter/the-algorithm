require "spec_helper"

describe BookmarkIndexer, bookmark_search: true do
  describe "#index_documents" do
    let(:bookmark) { create(:bookmark) }
    let(:indexer) { BookmarkIndexer.new([bookmark.id]) }

    context "when the bookmarker of one of the bookmarks has no user" do
      before { bookmark.pseud.user.delete }

      it "doesn't error" do
        expect { indexer.index_documents }.not_to raise_exception
      end
    end
  end
end
