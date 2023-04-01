# frozen_string_literal: true

require "spec_helper"

describe TagQuery, tag_search: true do
  context "searching tags by draft status" do
    let!(:work) do
      create(:work,
             fandom_string: "jjba,imas",
             character_string: "bruno,koume",
             relationship_string: "bruabba,koume/ryo",
             freeform_string: "slice of life,horror")
    end

    let!(:draft) do
      create(:draft,
             fandom_string: "zombie land saga,imas",
             character_string: "saki,koume",
             relationship_string: "saki/ai,koume/ryo",
             freeform_string: "action,horror")
    end

    before { run_all_indexing_jobs }

    it "returns tags without posted works" do
      results = TagQuery.new(type: "Fandom", has_posted_works: false).search_results.map(&:name)
      expect(results).to contain_exactly("zombie land saga")

      results = TagQuery.new(type: "Character", has_posted_works: false).search_results.map(&:name)
      expect(results).to contain_exactly("saki")

      results = TagQuery.new(type: "Relationship", has_posted_works: false).search_results.map(&:name)
      expect(results).to contain_exactly("saki/ai")

      results = TagQuery.new(type: "Freeform", has_posted_works: false).search_results.map(&:name)
      expect(results).to contain_exactly("action")

      # draft-only tags appear on another posted work
      create(:work,
             fandom_string: "zombie land saga",
             character_string: "saki",
             relationship_string: "saki/ai",
             freeform_string: "action")
      run_all_indexing_jobs

      expect(TagQuery.new(type: "Fandom", has_posted_works: false).search_results).to be_empty
      expect(TagQuery.new(type: "Character", has_posted_works: false).search_results).to be_empty
      expect(TagQuery.new(type: "Relationship", has_posted_works: false).search_results).to be_empty
      expect(TagQuery.new(type: "Freeform", has_posted_works: false).search_results).to be_empty
    end

    it "returns tags with posted works" do
      results = TagQuery.new(type: "Fandom", has_posted_works: true).search_results.map(&:name)
      expect(results).to contain_exactly("jjba", "imas")

      results = TagQuery.new(type: "Character", has_posted_works: true).search_results.map(&:name)
      expect(results).to contain_exactly("bruno", "koume")

      results = TagQuery.new(type: "Relationship", has_posted_works: true).search_results.map(&:name)
      expect(results).to contain_exactly("bruabba", "koume/ryo")

      results = TagQuery.new(type: "Freeform", has_posted_works: true).search_results.map(&:name)
      expect(results).to contain_exactly("slice of life", "horror")

      # draft gets posted
      draft.update(posted: true)
      run_all_indexing_jobs

      results = TagQuery.new(type: "Fandom", has_posted_works: true).search_results.map(&:name)
      expect(results).to contain_exactly("zombie land saga", "jjba", "imas")

      results = TagQuery.new(type: "Character", has_posted_works: true).search_results.map(&:name)
      expect(results).to contain_exactly("saki", "bruno", "koume")

      results = TagQuery.new(type: "Relationship", has_posted_works: true).search_results.map(&:name)
      expect(results).to contain_exactly("saki/ai", "bruabba", "koume/ryo")

      results = TagQuery.new(type: "Freeform", has_posted_works: true).search_results.map(&:name)
      expect(results).to contain_exactly("action", "slice of life", "horror")
    end

    it "returns all tags, with or without posted works" do
      results = TagQuery.new(type: "Fandom").search_results.map(&:name)
      expect(results).to contain_exactly("zombie land saga", "jjba", "imas")

      results = TagQuery.new(type: "Character").search_results.map(&:name)
      expect(results).to contain_exactly("saki", "bruno", "koume")

      results = TagQuery.new(type: "Relationship").search_results.map(&:name)
      expect(results).to contain_exactly("saki/ai", "bruabba", "koume/ryo")

      results = TagQuery.new(type: "Freeform").search_results.map(&:name)
      expect(results).to contain_exactly("action", "slice of life", "horror")
    end
  end

  context "searching for wrangled tags" do
    let(:wrangled_tag) { create(:freeform, name: "wrangled ff") }

    # Make tag wrangled; it should be reindexed without using queues
    before do
      create(:common_tagging, common_tag_id: wrangled_tag.id)
      TagIndexer.refresh_index
    end

    it "matches wrangled tags" do
      results = TagQuery.new(name: "wrangled ff").search_results
      expect(results).to include(wrangled_tag)
    end
  end
end
