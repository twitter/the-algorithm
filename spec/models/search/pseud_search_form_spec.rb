require "spec_helper"

describe PseudSearchForm, pseud_search: true do
  context "searching pseuds in a fandom" do
    let(:fandom_kp) { create(:canonical_fandom) }
    let(:fandom_mlaatr) { create(:canonical_fandom) }
    let!(:work_1) { create(:work, fandoms: [fandom_kp]) }
    let!(:work_2) { create(:work, fandoms: [fandom_kp], restricted: true) }
    let!(:work_3) { create(:work, fandoms: [fandom_mlaatr]) }
    let!(:work_4) { create(:work, fandoms: [fandom_mlaatr], restricted: true) }

    before { run_all_indexing_jobs }

    it "returns all pseuds writing in the fandom when logged in" do
      User.current_user = User.new
      results = PseudSearchForm.new(fandom: fandom_kp.name).search_results
      expect(results).to include work_1.pseuds.first
      expect(results).to include work_2.pseuds.first
      expect(results).not_to include work_3.pseuds.first
      expect(results).not_to include work_4.pseuds.first
    end

    it "returns pseuds writing public works in the fandom" do
      results = PseudSearchForm.new(fandom: fandom_kp.name).search_results
      expect(results).to include work_1.pseuds.first
      expect(results).not_to include work_2.pseuds.first
      expect(results).not_to include work_3.pseuds.first
      expect(results).not_to include work_4.pseuds.first
    end
  end

  context "searching pseuds in multiple fandoms" do
    let(:fandom_kp) { create(:canonical_fandom) }
    let(:fandom_mlaatr) { create(:canonical_fandom) }
    let(:user) { create(:user) }

    let!(:work_1) { create(:work, fandoms: [fandom_kp, fandom_mlaatr]) }
    let!(:work_2) { create(:work, fandoms: [fandom_kp], authors: [user.default_pseud]) }
    let!(:work_3) { create(:work, fandoms: [fandom_mlaatr], authors: [user.default_pseud], restricted: true) }

    before { run_all_indexing_jobs }

    it "returns all pseuds writing in all fandoms" do
      User.current_user = User.new
      results = PseudSearchForm.new(fandom: "#{fandom_kp.name},#{fandom_mlaatr.name}").search_results
      expect(results).to include work_1.pseuds.first
      expect(results).to include user.default_pseud
    end

    it "returns pseuds writing public works in all fandoms" do
      results = PseudSearchForm.new(fandom: "#{fandom_kp.name},#{fandom_mlaatr.name}").search_results
      expect(results).to include work_1.pseuds.first
      # This author posts in both fandoms, but their only work for fandom_mlaatr is restricted.
      # To logged out users, this author does not post in both specified fandoms.
      expect(results).not_to include user.default_pseud
    end
  end

  context "a user with multiple pseuds" do
    let!(:user) { create(:user, login: "avatar") }
    let!(:second_pseud) { create(:pseud, name: "kyoshi", user: user) }

    before { run_all_indexing_jobs }

    it "reindexes all pseuds when changing username" do
      results = PseudSearchForm.new(name: "avatar").search_results
      expect(results).to include(user.default_pseud)
      expect(results).to include(second_pseud)

      user.reload
      user.login = "aang"
      user.save
      run_all_indexing_jobs

      results = PseudSearchForm.new(name: "avatar").search_results
      expect(results).to be_empty

      results = PseudSearchForm.new(name: "aang").search_results
      expect(results).to include(user.default_pseud)
      expect(results).to include(second_pseud)
    end
  end

  context "pseud index of bookmarkers" do
    let(:bookmarker) { create(:pseud, name: "bookmarkermit") }

    it "updates when bookmarked work changes restricted status" do
      work = create(:work)
      expect(work.restricted).to be_falsy

      bookmark = create(:bookmark, bookmarkable_id: work.id, pseud: bookmarker)
      run_all_indexing_jobs
      result = PseudSearchForm.new(name: bookmark.pseud.name).search_results.first
      expect(result).to eq bookmark.pseud

      # Bookmark of public work is counted for logged in and logged out searches
      User.current_user = User.new
      expect(result.bookmarks_count).to eq 1
      User.current_user = nil
      expect(result.bookmarks_count).to eq 1

      # Work becomes restricted
      work.update_attribute(:restricted, true)
      expect(work.restricted).to be_truthy
      run_all_indexing_jobs
      result = PseudSearchForm.new(name: bookmark.pseud.name).search_results.first
      expect(result).to eq bookmark.pseud

      # Bookmark of restricted work is only counted for logged in searches
      User.current_user = User.new
      expect(result.bookmarks_count).to eq 1
      User.current_user = nil
      expect(result.bookmarks_count).to eq 0
    end

    it "updates when bookmarked series changes restricted status" do
      series = create(:series)
      serial_work = create(:serial_work, series: series)
      expect(series.restricted).to be_falsy

      bookmark = create(:bookmark, bookmarkable_id: series.id, bookmarkable_type: "Series", pseud: bookmarker)
      run_all_indexing_jobs
      result = PseudSearchForm.new(name: bookmark.pseud.name).search_results.first
      expect(result).to eq bookmark.pseud

      # Bookmark of public series is counted for logged in and logged out searches
      User.current_user = User.new
      expect(result.bookmarks_count).to eq 1
      User.current_user = nil
      expect(result.bookmarks_count).to eq 1

      # Series becomes restricted
      serial_work.work.update_attribute(:restricted, true)
      series.reload
      expect(series.restricted).to be_truthy
      run_all_indexing_jobs
      result = PseudSearchForm.new(name: bookmark.pseud.name).search_results.first
      expect(result).to eq bookmark.pseud

      # Bookmark of restricted series is only counted for logged in searches
      User.current_user = User.new
      expect(result.bookmarks_count).to eq 1
      User.current_user = nil
      expect(result.bookmarks_count).to eq 0
    end

    {
      Work: :work,
      Series: :series_with_a_work,
      ExternalWork: :external_work
    }.each_pair do |type, factory|
      it "updates when bookmarked #{type} changes hidden by admin status" do
        bookmarkable = create(factory)
        expect(bookmarkable.restricted).to be_falsy
        expect(bookmarkable.hidden_by_admin).to be_falsy

        bookmark = create(:bookmark, bookmarkable_id: bookmarkable.id, bookmarkable_type: type, pseud: bookmarker)
        run_all_indexing_jobs
        result = PseudSearchForm.new(name: bookmark.pseud.name).search_results.first
        expect(result).to eq bookmark.pseud

        # Bookmark of public bookmarkable is counted for logged in and logged out searches
        User.current_user = User.new
        expect(result.bookmarks_count).to eq 1
        User.current_user = nil
        expect(result.bookmarks_count).to eq 1

        # When a series and its work are first created, the series loads
        # an empty collection of bookmarks, which stays unupdated when we pluck
        # the bookmark IDs to reindex bookmarker pseuds, so no pseuds get reindexed.
        # We need to reload the series.
        bookmarkable.reload
        bookmarkable.update_attribute(:hidden_by_admin, true)
        run_all_indexing_jobs
        result = PseudSearchForm.new(name: bookmark.pseud.name).search_results.first
        expect(result).to eq bookmark.pseud

        # Bookmark of bookmarkable hidden by admin is counted for no one
        User.current_user = User.new
        expect(result.bookmarks_count).to eq 0
        User.current_user = nil
        expect(result.bookmarks_count).to eq 0
      end
    end
  end
end
