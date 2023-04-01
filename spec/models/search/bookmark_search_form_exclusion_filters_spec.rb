require "spec_helper"

describe BookmarkSearchForm, bookmark_search: true do
  describe "tag exclusion behavior" do
    let!(:user) do
      FactoryBot.create(:user)
    end

    let!(:included_work) do
      FactoryBot.create(:work)
    end

    let!(:excluded_work) do
      FactoryBot.create(:work)
    end

    let!(:included_bookmark) do
      FactoryBot.create(
        :bookmark,
        bookmarkable_id: included_work.id,
        pseud_id: user.default_pseud.id
      )
    end

    let!(:excluded_bookmark) do
      FactoryBot.create(
        :bookmark,
        bookmarkable_id: excluded_work.id,
        pseud_id: user.default_pseud.id
      )
    end

    describe "mergers" do
      let!(:canonical_work_tag) do
        FactoryBot.create(:canonical_freeform, name: "Exclude Work Tag")
      end

      let!(:work_tag_synonym) do
        FactoryBot.create(:freeform, name: "Tagged Work Exclusion", merger: canonical_work_tag)
      end

      let!(:canonical_bookmark_tag) do
        FactoryBot.create(:canonical_freeform, name: "Complete")
      end

      it "should exclude bookmarks for works with a given canonical tag name" do
        excluded_work.update(freeform_string: "Exclude Work Tag")
        run_all_indexing_jobs

        options = {
          excluded_tag_names: "Exclude Work Tag"
        }

        search = BookmarkSearchForm.new(options)

        expect(search.search_results).to include(included_bookmark)
        expect(search.search_results).not_to include(excluded_bookmark)
      end

      it "should exclude bookmarks tagged with a given canonical tag name" do
        excluded_bookmark.update(tag_string: "Complete")
        run_all_indexing_jobs

        options = {
          excluded_bookmark_tag_names: "Complete"
        }

        search = BookmarkSearchForm.new(options)

        expect(search.search_results).to include(included_bookmark)
        expect(search.search_results).not_to include(excluded_bookmark)
      end

      it "should exclude bookmarks for works tagged with a synonym to a given canonical tag name" do
        excluded_work.update(freeform_string: "Tagged Work Exclusion")
        run_all_indexing_jobs

        options = {
          excluded_tag_names: "Exclude Work Tag"
        }

        search = BookmarkSearchForm.new(options)

        expect(search.search_results).to include(included_bookmark)
        expect(search.search_results).not_to include(excluded_bookmark)
      end

      it "should only exclude bookmarks for works tagged with a synonym (not the merger) when given that synonym as a tag to exclude" do
        included_work.update(freeform_string: "Exclude Work Tag")
        excluded_work.update(freeform_string: "Tagged Work Exclusion")
        run_all_indexing_jobs

        options = {
          excluded_tag_names: "Tagged Work Exclusion"
        }

        search = BookmarkSearchForm.new(options)

        expect(search.search_results).to include(included_bookmark)
        expect(search.search_results).not_to include(excluded_bookmark)
      end
    end
  end
end
