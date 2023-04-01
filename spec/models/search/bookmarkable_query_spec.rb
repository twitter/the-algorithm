require 'spec_helper'

describe BookmarkableQuery do
  describe "#add_bookmark_filters" do
    it "should take a default has_parent query and flip it around" do
      bookmark_query = BookmarkQuery.new
      q = bookmark_query.bookmarkable_query
      excluded = q.generated_query.dig(:query, :bool, :must_not)
      expect(excluded).to include(term: { restricted: "true" })
      expect(excluded).to include(term: { hidden_by_admin: "true" })
      expect(excluded).to include(term: { posted: "false" })
    end

    it "should take bookmark filters and combine them into one child query" do
      bookmark_query = BookmarkQuery.new(user_ids: [5], excluded_bookmark_tag_ids: [666])
      q = bookmark_query.bookmarkable_query
      child_filter = q.bookmark_filter
      expect(child_filter.dig(:has_child, :query, :bool, :filter)).to include(term: { private: "false" })
      expect(child_filter.dig(:has_child, :query, :bool, :filter)).to include(term: { user_id: 5 })
      expect(child_filter.dig(:has_child, :query, :bool, :must_not)).to include(terms: { tag_ids: [666] })
    end
  end
end
