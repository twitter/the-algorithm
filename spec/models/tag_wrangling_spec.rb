require 'spec_helper'

describe Tag do
  describe "canonical" do
    context "when canonical becomes true" do
      let!(:fandom) { create(:fandom) }

      it "adds filters to tagged works" do
        work = create(:work, fandom_string: fandom.name)
        fandom.update!(canonical: true)
        expect(fandom.filtered_works.reload).to include(work)
      end

      it "reindexes tagged works" do
        work = create(:work, fandom_string: fandom.name)
        expect do
          fandom.update!(canonical: true)
        end.to(add_to_reindex_queue(work, :background) &
               not_add_to_reindex_queue(work, :main))
      end
    end

    context "when canonical becomes false" do
      let!(:fandom) { create(:canonical_fandom) }

      it "removes filters from tagged works" do
        work = create(:work, fandom_string: fandom.name)
        fandom.update!(canonical: false)
        expect(fandom.filtered_works.reload).not_to include(work)
      end

      it "reindexes tagged works" do
        work = create(:work, fandom_string: fandom.name)
        expect do
          fandom.update!(canonical: false)
        end.to(add_to_reindex_queue(work, :background) &
               not_add_to_reindex_queue(work, :main))
      end

      it "removes favorite tags" do
        user = create(:user)
        user.favorite_tags.create(tag: fandom)
        fandom.update!(canonical: false)
        expect(user.favorite_tags.count).to eq 0
      end

      context "when the tag has a synonym" do
        let!(:synonym) { create(:fandom, merger: fandom) }

        it "removes the synonym's merger" do
          fandom.update!(canonical: false)
          expect(synonym.reload.merger).to eq(nil)
          expect(fandom.reload.mergers).to eq([])
        end

        it "removes filters from works tagged with the synonym" do
          work = create(:work, fandom_string: synonym.name)
          fandom.update!(canonical: false)
          expect(fandom.filtered_works.reload).not_to include(work)
        end
      end

      context "when the tag has a subtag" do
        let!(:sub) { create(:canonical_fandom) }

        before do
          sub.meta_tags << fandom
          [sub, fandom].each(&:reload)
        end

        it "removes the metatag relationship" do
          fandom.update!(canonical: false)
          expect(sub.reload.meta_tags).to eq([])
          expect(fandom.reload.sub_tags).to eq([])
        end

        it "removes filters from works tagged with the subtag" do
          work = create(:work, fandom_string: sub.name)
          fandom.update!(canonical: false)
          expect(fandom.filtered_works.reload).not_to include(work)
        end

        it "removes filters from works tagged with the subtag's synonym" do
          synonym = create(:fandom, merger: sub)
          work = create(:work, fandom_string: synonym.name)
          fandom.update!(canonical: false)
          expect(fandom.filtered_works.reload).not_to include(work)
        end
      end

      context "when the tag has a metatag" do
        let!(:meta) { create(:canonical_fandom) }

        before do
          fandom.meta_tags << meta
          [meta, fandom].each(&:reload)
        end

        it "removes the metatag relationship" do
          fandom.update!(canonical: false)
          expect(meta.reload.sub_tags).to eq([])
          expect(fandom.reload.meta_tags).to eq([])
        end

        it "removes meta filters from works tagged with the fandom" do
          work = create(:work, fandom_string: fandom.name)
          fandom.update!(canonical: false)
          expect(meta.filtered_works.reload).not_to include(work)
        end

        it "removes meta filters from works tagged with the tag's synonym" do
          synonym = create(:fandom, merger: fandom)
          work = create(:work, fandom_string: synonym.name)
          fandom.update!(canonical: false)
          expect(meta.filtered_works.reload).not_to include(work)
        end
      end
    end
  end

  describe "syn_string" do
    let!(:work) { create(:work, fandom_string: synonym.name) }

    let(:fandom) { create(:canonical_fandom) }
    let(:synonym) { create(:fandom) }

    context "adding a synonym" do
      it "adds filters to works tagged with the synonym" do
        synonym.update!(syn_string: fandom.name)
        expect(fandom.filtered_works.reload).to include(work)
      end

      it "reindexes works tagged with the synonym" do
        expect do
          synonym.update!(syn_string: fandom.name)
        end.to(add_to_reindex_queue(work, :background) &
               not_add_to_reindex_queue(work, :main))
      end

      it "copies its parent associations" do
        parent = create(:media)
        synonym.add_association(parent)
        synonym.reload

        synonym.update!(syn_string: fandom.name)

        expect(fandom.parents.reload).to contain_exactly(parent)
        expect(synonym.parents.reload).to contain_exactly(parent)
      end

      context "when the synonym used to be canonical" do
        # Only admins can make canonical tags into synonyms:
        before { User.current_user = create(:admin) }

        let(:synonym) { create(:canonical_fandom) }

        it "changes the tag to non-canonical" do
          synonym.update!(syn_string: fandom.name)
          expect(synonym.reload.canonical).to be_falsey
        end

        it "transfers its metatags and subtags" do
          sub = create(:canonical_fandom)
          meta = create(:canonical_fandom)
          synonym.sub_tags << sub
          synonym.meta_tags << meta
          synonym.reload

          synonym.update!(syn_string: fandom.name)

          expect(fandom.sub_tags.reload).to contain_exactly(sub)
          expect(fandom.meta_tags.reload).to contain_exactly(meta)
          expect(synonym.sub_tags.reload).to contain_exactly
          expect(synonym.meta_tags.reload).to contain_exactly
        end

        it "transfers its child associations" do
          child = create(:character)
          synonym.add_association(child)
          synonym.reload

          synonym.update!(syn_string: fandom.name)

          expect(fandom.children.reload).to contain_exactly(child)
          expect(synonym.children.reload).to contain_exactly
        end

        it "transfers favorite tags" do
          user = create(:user)
          user.favorite_tags.create(tag: synonym)
          synonym.update!(syn_string: fandom.name)
          expect(user.favorite_tags.count).to eq 1
          expect(user.favorite_tags.reload.first.tag).to eq(fandom)
        end

        it "handles duplicate favorite tags" do
          user = create(:user)
          user.favorite_tags.create(tag: fandom)
          user.favorite_tags.create(tag: synonym)
          synonym.update!(syn_string: fandom.name)
          expect(user.favorite_tags.count).to eq 1
          expect(user.favorite_tags.reload.first.tag).to eq(fandom)
        end
      end
    end

    context "changing the synonym" do
      let(:synonym) { create(:fandom, merger: fandom) }
      let(:new_fandom) { create(:canonical_fandom) }

      it "adds filters and meta filters for the new canonical" do
        meta = create(:canonical_fandom)
        meta.sub_tags << new_fandom
        synonym.update!(syn_string: new_fandom.name)
        expect(new_fandom.filtered_works.reload).to include(work)
        expect(meta.filtered_works.reload).to include(work)
      end

      it "removes filters and meta filters for the old canonical" do
        meta = create(:canonical_fandom)
        meta.sub_tags << fandom
        synonym.update!(syn_string: new_fandom.name)
        expect(fandom.filtered_works.reload).not_to include(work)
        expect(meta.filtered_works.reload).not_to include(work)
      end

      it "reindexes works tagged with the synonym" do
        expect do
          synonym.update!(syn_string: new_fandom.name)
        end.to(add_to_reindex_queue(work, :background) &
               not_add_to_reindex_queue(work, :main))
      end
    end

    context "removing the synonym" do
      let(:synonym) { create(:fandom, merger: fandom) }

      it "removes filters and meta filters for the old canonical" do
        meta = create(:canonical_fandom)
        meta.sub_tags << fandom
        synonym.update!(syn_string: "")
        expect(fandom.filtered_works.reload).not_to include(work)
        expect(meta.filtered_works.reload).not_to include(work)
      end

      it "reindexes works tagged with the synonym" do
        expect do
          synonym.update!(syn_string: "")
        end.to(add_to_reindex_queue(work, :background) &
               not_add_to_reindex_queue(work, :main))
      end
    end
  end

  describe "meta_tags" do
    let!(:fandom) { create(:canonical_fandom) }

    context "adding a metatag" do
      let!(:meta) { create(:canonical_fandom) }

      it "adds inherited filters to works tagged with the subtag" do
        work = create(:work, fandom_string: fandom.name)

        fandom.update!(meta_tag_string: meta.name)
        expect(work.filters.reload).to include(meta)
        expect(work.direct_filters.reload).not_to include(meta)
      end

      it "reindexes works tagged with the subtag" do
        work = create(:work, fandom_string: fandom.name)
        expect do
          fandom.update!(meta_tag_string: meta.name)
        end.to(add_to_reindex_queue(work, :background) &
               not_add_to_reindex_queue(work, :main))
      end

      context "when the metatag has a metatag" do
        let!(:meta_meta) { create(:canonical_fandom) }

        before do
          meta.meta_tags << meta_meta
          [meta, meta_meta].each(&:reload)
        end

        it "adds an inherited meta tagging" do
          fandom.update!(meta_tag_string: meta.name)
          expect(fandom.meta_tags.reload).to include(meta_meta)
          expect(fandom.direct_meta_tags.reload).not_to include(meta_meta)
        end

        it "adds inherited filters for the inherited metatag" do
          work = create(:work, fandom_string: fandom.name)
          fandom.update!(meta_tag_string: meta.name)
          expect(work.filters.reload).to include(meta_meta)
          expect(work.direct_filters.reload).not_to include(meta_meta)
        end
      end

      context "when the subtag has a subtag" do
        let(:sub) { create(:canonical_fandom) }

        before do
          sub.meta_tags << fandom
          [sub, fandom, meta].each(&:reload)
        end

        it "adds an inherited meta tagging" do
          fandom.update!(meta_tag_string: meta.name)
          expect(sub.meta_tags.reload).to include(meta)
          expect(sub.direct_meta_tags.reload).not_to include(meta)
        end

        it "reindexes works tagged with the subtag's subtag" do
          work = create(:work, fandom_string: sub.name)
          expect do
            fandom.update!(meta_tag_string: meta.name)
          end.to(add_to_reindex_queue(work, :background) &
                 not_add_to_reindex_queue(work, :main))
        end
      end

      context "when the subtag has a synonym" do
        let!(:synonym) { create(:fandom, merger: fandom) }

        it "adds inherited filters to the synonym's works" do
          work = create(:work, fandom_string: synonym.name)

          fandom.update!(meta_tag_string: meta.name)
          expect(work.filters.reload).to include(meta)
          expect(work.direct_filters.reload).not_to include(meta)
        end

        it "reindexes works tagged with the subtag's synonym" do
          work = create(:work, fandom_string: synonym.name)
          expect do
            fandom.update!(meta_tag_string: meta.name)
          end.to(add_to_reindex_queue(work, :background) &
                 not_add_to_reindex_queue(work, :main))
        end
      end
    end

    context "removing a metatag" do
      let!(:meta) { create(:canonical_fandom) }

      before do
        fandom.meta_tags << meta
        [fandom, meta].each(&:reload)
      end

      it "removes filters from works tagged with the subtag" do
        work = create(:work, fandom_string: fandom.name)
        fandom.update!(associations_to_remove: [meta.id])
        expect(work.filters.reload).not_to include(meta)
      end

      it "reindexes works tagged with the subtag" do
        work = create(:work, fandom_string: fandom.name)
        expect do
          fandom.update!(associations_to_remove: [meta.id])
        end.to(add_to_reindex_queue(work, :background) &
               not_add_to_reindex_queue(work, :main))
      end

      context "when the metatag has a metatag" do
        let!(:meta_meta) { create(:canonical_fandom) }

        before do
          meta.meta_tags << meta_meta
          [meta, meta_meta].each(&:reload)
        end

        it "removes the inherited meta tagging" do
          fandom.update!(associations_to_remove: [meta.id])
          expect(fandom.meta_tags.reload).not_to include(meta_meta)
        end

        it "removes filters for the inherited metatag" do
          work = create(:work, fandom_string: fandom.name)
          fandom.update!(associations_to_remove: [meta.id])
          expect(work.filters.reload).not_to include(meta_meta)
        end

        context "when there exists another path to the inherited metatag" do
          before do
            alt = create(:canonical_fandom)
            alt.meta_tags << meta_meta
            alt.sub_tags << fandom
            [alt, fandom, meta_meta].each(&:reload)
          end

          it "doesn't remove the inherited meta tagging" do
            fandom.update!(associations_to_remove: [meta.id])
            expect(fandom.meta_tags.reload).to include(meta_meta)
            expect(fandom.direct_meta_tags.reload).not_to include(meta_meta)
          end
        end
      end

      context "when the subtag has a subtag" do
        let(:sub) { create(:canonical_fandom) }

        before do
          sub.meta_tags << fandom
          [sub, fandom, meta].each(&:reload)
        end

        it "removes the inherited meta tagging" do
          fandom.update!(associations_to_remove: [meta.id])
          expect(meta.sub_tags.reload).not_to include(sub)
        end

        it "reindexes works tagged with the subtag's subtag" do
          work = create(:work, fandom_string: sub.name)
          expect do
            fandom.update!(associations_to_remove: [meta.id])
          end.to(add_to_reindex_queue(work, :background) &
                 not_add_to_reindex_queue(work, :main))
        end
      end

      context "when the subtag has a synonym" do
        let!(:synonym) { create(:fandom, merger: fandom) }

        it "removes filters from the synonym's works" do
          work = create(:work, fandom_string: synonym.name)
          fandom.update!(associations_to_remove: [meta.id])
          expect(work.filters.reload).not_to include(meta)
        end

        it "reindexes works tagged with the subtag's synonym" do
          work = create(:work, fandom_string: synonym.name)
          expect do
            fandom.update!(associations_to_remove: [meta.id])
          end.to(add_to_reindex_queue(work, :background) &
                 not_add_to_reindex_queue(work, :main))
        end
      end

      context "when there's also an indirect path to the direct metatag" do
        let!(:middle) { create(:canonical_fandom) }

        before do
          middle.reload.meta_tags << meta
          fandom.reload.meta_tags << middle
          [fandom, middle, meta].each(&:reload)
        end

        it "marks the metatag as inherited" do
          fandom.update!(associations_to_remove: [meta.id])
          expect(fandom.meta_tags.reload).to include(meta)
          expect(fandom.direct_meta_tags.reload).not_to include(meta)
        end
      end
    end

    describe "adding multiple metatags" do
      let(:fandom) { create(:canonical_fandom) }
      let(:meta1) { create(:canonical_fandom) }
      let(:meta2) { create(:canonical_fandom) }

      it "adds both direct metatags" do
        fandom.update!(meta_tag_string: "#{meta1.name}, #{meta2.name}")
        expect(fandom.meta_tags.reload).to include(meta1)
        expect(fandom.meta_tags.reload).to include(meta2)
        expect(fandom.direct_meta_tags.reload).to include(meta1)
        expect(fandom.direct_meta_tags.reload).to include(meta2)
      end

      it "updates inheritance for both metatags" do
        meta_meta1 = create(:canonical_fandom)
        meta_meta1.sub_tags << meta1
        meta_meta2 = create(:canonical_fandom)
        meta_meta2.sub_tags << meta2

        fandom.update!(meta_tag_string: "#{meta1.name}, #{meta2.name}")
        expect(fandom.meta_tags.reload).to include(meta_meta1)
        expect(fandom.meta_tags.reload).to include(meta_meta2)
      end
    end

    describe "adding a metatag and a subtag simultaneously" do
      let(:fandom) { create(:canonical_fandom) }
      let(:meta) { create(:canonical_fandom) }
      let(:sub) { create(:canonical_fandom) }

      it "adds both direct meta taggings" do
        fandom.update!(meta_tag_string: meta.name, sub_tag_string: sub.name)
        expect(fandom.meta_tags.reload).to include(meta)
        expect(fandom.direct_meta_tags.reload).to include(meta)
        expect(fandom.sub_tags.reload).to include(sub)
        expect(fandom.direct_sub_tags.reload).to include(sub)
      end

      it "adds the inherited meta tagging" do
        fandom.update!(meta_tag_string: meta.name, sub_tag_string: sub.name)
        expect(sub.meta_tags.reload).to include(meta)
        expect(sub.direct_meta_tags.reload).not_to include(meta)
      end
    end
  end

  describe "when the resque queue is long" do
    # Disable immediate job processing. Jobs will only be processed when we
    # call "perform_enqueued_jobs", which simulates a delay in the queue.
    include ActiveJob::TestHelper

    # https://otwarchive.atlassian.net/browse/AO3-4077
    it "deletes the inherited metatag when both direct ones are removed" do
      lowest = create(:canonical_fandom)
      middle = create(:canonical_fandom)
      highest = create(:canonical_fandom)
      middle.meta_tags << highest
      middle.sub_tags << lowest

      perform_enqueued_jobs

      middle.reload
      middle.update!(associations_to_remove: [lowest.id, highest.id])
      expect(enqueued_jobs).not_to be_empty

      perform_enqueued_jobs

      expect(lowest.meta_tags.reload).not_to include(highest)
    end

    it "deletes meta filters when a syn and a metatag are deleted" do
      canonical = create(:canonical_fandom)
      meta = create(:canonical_fandom)
      canonical.meta_tags << meta
      synonym = create(:fandom, merger: canonical)

      work = create(:work, fandom_string: synonym.name)

      perform_enqueued_jobs

      canonical.reload
      canonical.update!(associations_to_remove: [synonym.id, meta.id])
      expect(enqueued_jobs).not_to be_empty

      perform_enqueued_jobs

      expect(work.filters.reload).not_to include(meta)
    end
  end
end
