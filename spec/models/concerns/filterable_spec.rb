# frozen_string_literal: true

require 'spec_helper'

shared_examples "a filterable" do
  describe "adding a tag" do
    context "when the tag is canonical" do
      let(:freeform) { create(:canonical_freeform) }

      it "adds a direct filter" do
        filterable.update!(freeform_string: freeform.name)
        expect(filterable.filters.reload).to include(freeform)
        expect(filterable.direct_filters.reload).to include(freeform)
      end

      it "adds the filterable to the main reindexing queue" do
        expect do
          filterable.update!(freeform_string: freeform.name)
        end.to add_to_reindex_queue(filterable, :main)
      end

      context "when the tag has a metatag" do
        let(:meta) { create(:canonical_freeform) }
        before { freeform.meta_tags << meta }

        it "adds the inherited meta filter" do
          filterable.update!(freeform_string: freeform.name)
          expect(filterable.filters.reload).to include(meta)
          expect(filterable.direct_filters.reload).not_to include(meta)
        end
      end

      context "when the filterable is already tagged with a subtag" do
        let(:sub) { create(:canonical_freeform) }

        before do
          sub.meta_tags << freeform
          filterable.update!(freeform_string: sub.name)
          filterable.reload
        end

        it "marks the existing inherited filter as direct" do
          filterable.update!(freeform_string: "#{sub.name}, #{freeform.name}")
          expect(filterable.filters.reload).to include(freeform)
          expect(filterable.direct_filters.reload).to include(freeform)
        end
      end
    end

    context "when the tag is a synonym" do
      let(:canonical) { create(:canonical_freeform) }
      let(:freeform) { create(:freeform, merger: canonical) }

      it "adds a direct filter for the canonical" do
        filterable.update!(freeform_string: freeform.name)
        expect(filterable.filters.reload).to include(canonical)
        expect(filterable.direct_filters.reload).to include(canonical)
      end

      it "adds the filterable to the main reindexing queue" do
        expect do
          filterable.update!(freeform_string: freeform.name)
        end.to add_to_reindex_queue(filterable, :main)
      end
    end
  end

  describe "removing a tag" do
    context "when the tag is canonical" do
      let(:freeform) { create(:canonical_freeform) }

      before do
        filterable.update!(freeform_string: freeform.name)
        filterable.reload
      end

      it "removes the filter" do
        filterable.update!(freeform_string: "")
        expect(filterable.filters.reload).not_to include(freeform)
      end

      it "adds the filterable to the main reindexing queue" do
        expect do
          filterable.update!(freeform_string: "")
        end.to add_to_reindex_queue(filterable, :main)
      end

      context "when the tag has a metatag" do
        let(:meta) { create(:canonical_freeform) }
        before { freeform.meta_tags << meta }

        it "removes the meta filter" do
          filterable.update!(freeform_string: "")
          expect(filterable.filters.reload).not_to include(meta)
        end

        context "when the filterable is also tagged with that metatag" do
          before do
            filterable.update!(
              freeform_string: "#{freeform.name}, #{meta.name}"
            )
            filterable.reload
          end

          it "doesn't delete the meta filter" do
            filterable.update!(freeform_string: meta.name)
            expect(filterable.filters.reload).to include(meta)
            expect(filterable.direct_filters.reload).to include(meta)
          end
        end
      end

      context "when the filterable is also tagged with a subtag" do
        let(:sub) { create(:canonical_freeform) }

        before do
          sub.meta_tags << freeform
          filterable.update!(
            freeform_string: "#{freeform.name}, #{sub.name}"
          )
          filterable.reload
        end

        it "marks the filter as indirect" do
          filterable.update!(freeform_string: sub.name)
          expect(filterable.filters.reload).to include(freeform)
          expect(filterable.direct_filters.reload).not_to include(freeform)
        end
      end

      context "when the filterable is also tagged with a synonym" do
        let(:synonym) { create(:freeform, merger: freeform) }

        before do
          filterable.update!(
            freeform_string: "#{freeform.name}, #{synonym.name}"
          )
          filterable.reload
        end

        it "doesn't delete the filter" do
          filterable.update!(freeform_string: synonym.name)
          expect(filterable.filters.reload).to include(freeform)
          expect(filterable.direct_filters.reload).to include(freeform)
        end
      end
    end

    context "when the tag is a synonym" do
      let(:canonical) { create(:canonical_freeform) }
      let(:freeform) { create(:freeform, merger: canonical) }

      before do
        filterable.update!(freeform_string: freeform.name)
        filterable.reload
      end

      it "removes the direct filter for the canonical" do
        filterable.update!(freeform_string: "")
        expect(filterable.filters.reload).not_to include(canonical)
      end

      it "adds the filterable to the main reindexing queue" do
        expect do
          filterable.update!(freeform_string: "")
        end.to add_to_reindex_queue(filterable, :main)
      end
    end
  end

  describe "removing a tag and adding another at the same time" do
    let(:old_freeform) { create(:canonical_freeform) }
    let(:new_freeform) { create(:canonical_freeform) }

    before do
      filterable.update!(freeform_string: old_freeform.name)
      filterable.reload
    end

    it "deletes the old filters and adds the new filters" do
      filterable.update!(freeform_string: new_freeform.name)
      filterable.reload

      expect(filterable.filters).not_to include(old_freeform)
      expect(filterable.filters).to include(new_freeform)
      expect(filterable.direct_filters).to include(new_freeform)
    end

    it "adds the filterable to the main reindexing queue" do
      expect do
        filterable.update!(freeform_string: new_freeform.name)
      end.to add_to_reindex_queue(filterable, :main)
    end
  end
end

describe Work do
  let(:filterable) { create(:work).reload }
  it_behaves_like "a filterable"
end

describe ExternalWork do
  let(:filterable) { create(:external_work).reload }
  it_behaves_like "a filterable"
end

describe Collection do 
  let(:filterable) { create(:collection).reload }

  it_behaves_like "a filterable"
end 
