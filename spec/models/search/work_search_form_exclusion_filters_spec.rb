require "spec_helper"

describe WorkSearchForm, work_search: true do
  describe "tag exclusion behavior" do
    let!(:included_work) do
      FactoryBot.create(:work)
    end

    let!(:excluded_work) do
      FactoryBot.create(:work)
    end

    describe "mergers" do

      let!(:canonical_tag) do
        FactoryBot.create(:tag, type: "Freeform", name: "Exclude Me", canonical: true)
      end

      let!(:synonym) do
        FactoryBot.create(:tag, type: "Freeform", name: "Excluded", canonical: false, merger: canonical_tag)
      end

      it "should exclude works with a given canonical tag name" do
        excluded_work.update(freeform_string: "Exclude Me")
        run_all_indexing_jobs

        options = {
          excluded_tag_names: "Exclude Me"
        }

        search = WorkSearchForm.new(options)

        expect(search.search_results).to include(included_work)
        expect(search.search_results).not_to include(excluded_work)
      end

      it "should exclude works tagged with a synonym to a given canonical tag name" do
        excluded_work.update(freeform_string: "Excluded")
        run_all_indexing_jobs

        options = {
          excluded_tag_names: "Exclude Me"
        }

        search = WorkSearchForm.new(options)

        expect(search.search_results).to include(included_work)
        expect(search.search_results).not_to include(excluded_work)
      end

      it "should only exclude works tagged with a synonym (not its merger) when given that synonym as a tag to exclude" do
        included_work.update(freeform_string: "Exclude Me")
        excluded_work.update(freeform_string: "Excluded")

        run_all_indexing_jobs

        options = {
          excluded_tag_names: "Excluded"
        }

        search = WorkSearchForm.new(options)

        expect(search.search_results).to include(included_work)
        expect(search.search_results).not_to include(excluded_work)
      end
    end

    describe "meta tagging" do
      let!(:grand_parent_tag) do
        create(:canonical_character, name: "Sam")
      end

      let!(:parent_tag) do
        create(:canonical_character, name: "Sam Winchester")
      end

      let!(:child_tag) do
        create(:canonical_character, name: "Endverse Sam Winchester")
      end

      let!(:grand_parent_parent_meta_tagging) do
        FactoryBot.create(
          :meta_tagging,
          meta_tag_id: grand_parent_tag.id,
          sub_tag_id: parent_tag.id,
          direct: true
        )
      end

      let!(:grand_parent_child_meta_tagging) do
        FactoryBot.create(
          :meta_tagging,
          meta_tag_id: grand_parent_tag.id,
          sub_tag_id: child_tag.id,
          direct: false
        )
      end

      let!(:parent_child_meta_tagging) do
        FactoryBot.create(
          :meta_tagging,
          meta_tag_id: parent_tag.id,
          sub_tag_id: child_tag.id,
          direct: true
        )
      end

      it "should exclude works tagged with direct sub tags of the given superset tag name" do
        excluded_work.update(character_string: "Sam Winchester")
        run_all_indexing_jobs

        options = {
          excluded_tag_names: "Sam"
        }

        search = WorkSearchForm.new(options)

        expect(search.search_results).to include(included_work)
        expect(search.search_results).not_to include(excluded_work)
      end

      it "should not exclude works tagged with the direct superset of the given sub tag name" do
        included_work.update(character_string: "Sam")
        excluded_work.update(character_string: "Sam Winchester")
        run_all_indexing_jobs

        options = {
          excluded_tag_names: "Sam Winchester"
        }

        search = WorkSearchForm.new(options)

        expect(search.search_results).to include(included_work)
        expect(search.search_results).not_to include(excluded_work)
      end

      it "should exclude works tagged with indirect sub tags of the given superset tag name" do
        excluded_work.update(character_string: "Endverse Sam Winchester")
        run_all_indexing_jobs

        options = {
          excluded_tag_names: "Sam"
        }

        search = WorkSearchForm.new(options)

        expect(search.search_results).to include(included_work)
        expect(search.search_results).not_to include(excluded_work)
      end

      it "should not exclude works tagged with the indirect superset of the given sub tag name" do
        included_work.update(character_string: "Sam")
        excluded_work.update(character_string: "Endverse Sam Winchester")
        run_all_indexing_jobs

        options = {
          excluded_tag_names: "Endverse Sam Winchester"
        }

        search = WorkSearchForm.new(options)

        expect(search.search_results).to include(included_work)
        expect(search.search_results).not_to include(excluded_work)
      end
    end
  end
end
