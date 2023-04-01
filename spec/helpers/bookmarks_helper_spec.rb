require "spec_helper"

describe BookmarksHelper do
  let(:bookmarker) { create(:user) }
  let(:external_work_bookmark) { create(:external_work_bookmark, pseud: bookmarker.default_pseud) }
  let(:series_bookmark) { create(:series_bookmark, pseud: bookmarker.default_pseud) }
  let(:work_bookmark) { create(:bookmark, pseud: bookmarker.default_pseud) }
  let(:external_work) { external_work_bookmark.bookmarkable }
  let(:series) { series_bookmark.bookmarkable }
  let(:series_creator) { series.users.first }
  let(:work) { work_bookmark.bookmarkable }
  let(:work_creator) { work.users.first }

  describe "#css_classes_for_bookmark_blurb" do
    let(:default_classes) { "bookmark blurb group" }

    context "when bookmarkable is nil" do
      it "returns string with default classes and bookmarker info" do
        work.destroy!
        result = helper.css_classes_for_bookmark_blurb(work_bookmark.reload)
        expect(result).to eq("#{default_classes} user-#{bookmarker.id}")
      end
    end

    context "when bookmarkable is ExternalWork" do
      it "returns string with default classes, creation info, and bookmarker info" do
        result = helper.css_classes_for_bookmark_blurb(external_work_bookmark)
        expect(result).to eq("#{default_classes} external-work-#{external_work.id} user-#{bookmarker.id}")
      end

      context "when bookmark is updated" do
        it "returns same string with different cache key" do
          original_cache_key = "#{external_work.cache_key_with_version}_#{external_work_bookmark.cache_key}/blurb_css_classes"
          expected_classes = "#{default_classes} external-work-#{external_work.id} user-#{bookmarker.id}"
          expect(helper.css_classes_for_bookmark_blurb(external_work_bookmark)).to eq(expected_classes)

          travel(1.day)
          external_work_bookmark.update(bookmarker_notes: "New note")
          expect(helper.css_classes_for_bookmark_blurb(external_work_bookmark.reload)).to eq(expected_classes)
          expect(original_cache_key).not_to eq("#{external_work.cache_key_with_version}_#{external_work_bookmark.cache_key}/blurb_css_classes")
          travel_back
        end
      end
    end

    context "when bookmarkable is Series" do
      it "returns string with default classes, creation and creator info, and bookmarker info" do
        result = helper.css_classes_for_bookmark_blurb(series_bookmark)
        expect(result).to eq("#{default_classes} series-#{series.id} user-#{series_creator.id} user-#{bookmarker.id}")
      end

      context "when bookmarker is also series creator" do
        before do
          series.creatorships.first.update(pseud_id: bookmarker.default_pseud_id)
        end

        it "only includes the user id once" do
          result = helper.css_classes_for_bookmark_blurb(series_bookmark)
          expect(result).to eq("#{default_classes} series-#{series.id} user-#{bookmarker.id}")
        end
      end

      context "when series is updated" do
        context "when new creator is added" do
          let(:series_creator2) { create(:user) }

          it "returns updated string" do
            original_cache_key = "#{series.cache_key_with_version}_#{series_bookmark.cache_key}/blurb_css_classes"
            original_classes = "#{default_classes} series-#{series.id} user-#{series_creator.id} user-#{bookmarker.id}"
            expect(helper.css_classes_for_bookmark_blurb(series_bookmark)).to eq(original_classes)

            travel(1.day)
            new_classes = "#{default_classes} series-#{series.id} user-#{series_creator.id} user-#{series_creator2.id} user-#{bookmarker.id}"
            series.creatorships.find_or_create_by(pseud_id: series_creator2.default_pseud_id)
            expect(helper.css_classes_for_bookmark_blurb(series_bookmark.reload)).to eq(new_classes)
            expect(original_cache_key).not_to eq("#{series.cache_key_with_version}_#{series_bookmark.cache_key}/blurb_css_classes")
            travel_back
          end
        end
      end

      context "when bookmark is updated" do
        it "returns same string with different cache key" do
          original_cache_key = "#{series.cache_key_with_version}_#{series_bookmark.cache_key}/blurb_css_classes"
          expected_classes = "#{default_classes} series-#{series.id} user-#{series_creator.id} user-#{bookmarker.id}"
          expect(helper.css_classes_for_bookmark_blurb(series_bookmark)).to eq(expected_classes)

          travel(1.day)
          series_bookmark.update(bookmarker_notes: "New note")
          expect(helper.css_classes_for_bookmark_blurb(series_bookmark.reload)).to eq(expected_classes)
          expect(original_cache_key).not_to eq("#{series.cache_key_with_version}_#{series_bookmark.cache_key}/blurb_css_classes")
          travel_back
        end
      end
    end

    context "when bookmarkable is Work" do
      it "returns string with default classes, creation and creator info, and bookmarker info" do
        result = helper.css_classes_for_bookmark_blurb(work_bookmark)
        expect(result).to eq("#{default_classes} work-#{work.id} user-#{work_creator.id} user-#{bookmarker.id}")
      end

      context "when bookmarker is also work creator" do
        before do
          work.creatorships.first.update(pseud_id: bookmarker.default_pseud_id)
        end

        it "only includes the user id once" do
          result = helper.css_classes_for_bookmark_blurb(work_bookmark)
          expect(result).to eq("#{default_classes} work-#{work.id} user-#{bookmarker.id}")
        end
      end

      context "when work is updated" do
        context "when new creator is added" do
          let(:work_creator2) { create(:user) }

          it "returns updated string" do
            original_cache_key = "#{work.cache_key_with_version}_#{work_bookmark.cache_key}/blurb_css_classes"
            original_classes = "#{default_classes} work-#{work.id} user-#{work_creator.id} user-#{bookmarker.id}"
            expect(helper.css_classes_for_bookmark_blurb(work_bookmark)).to eq(original_classes)

            travel(1.day)
            new_classes = "#{default_classes} work-#{work.id} user-#{work_creator.id} user-#{work_creator2.id} user-#{bookmarker.id}"
            work.creatorships.find_or_create_by(pseud_id: work_creator2.default_pseud_id)
            expect(helper.css_classes_for_bookmark_blurb(work_bookmark.reload)).to eq(new_classes)
            expect(original_cache_key).not_to eq("#{work.cache_key_with_version}_#{work_bookmark.cache_key}/blurb_css_classes")
            travel_back
          end
        end
      end

      context "when bookmark is updated" do
        it "returns same string with different cache key" do
          original_cache_key = "#{work.cache_key_with_version}_#{work_bookmark.cache_key}/blurb_css_classes"
          expected_classes = "#{default_classes} work-#{work.id} user-#{work_creator.id} user-#{bookmarker.id}"
          expect(helper.css_classes_for_bookmark_blurb(work_bookmark)).to eq(expected_classes)

          travel(1.day)
          work_bookmark.update(bookmarker_notes: "New note")
          expect(helper.css_classes_for_bookmark_blurb(work_bookmark.reload)).to eq(expected_classes)
          expect(original_cache_key).not_to eq("#{work.cache_key_with_version}_#{work_bookmark.cache_key}/blurb_css_classes")
          travel_back
        end
      end
    end
  end

  describe "#css_classes_for_bookmarkable_blurb" do
    let(:default_classes) { "bookmark blurb group" }

    context "when bookmarkable is nil" do
      it "returns a string with default classes" do
        result = helper.css_classes_for_bookmarkable_blurb(nil)
        expect(result).to eq(default_classes)
      end
    end

    context "when bookmarkable is ExternalWork" do
      it "returns string with default classes and creation info" do
        result = helper.css_classes_for_bookmarkable_blurb(external_work)
        expect(result).to eq("#{default_classes} external-work-#{external_work.id}")
      end
    end

    context "when bookmarkable is Series" do
      it "returns string with default classes and creation and creator info" do
        result = helper.css_classes_for_bookmarkable_blurb(series)
        expect(result).to eq("#{default_classes} series-#{series.id} user-#{series_creator.id}")
      end
    end

    context "when bookmarkable is Work" do
      it "returns string with default classes and creation and creator info" do
        result = helper.css_classes_for_bookmarkable_blurb(work)
        expect(result).to eq("#{default_classes} work-#{work.id} user-#{work_creator.id}")
      end
    end
  end

  describe "#css_classes_for_bookmark_blurb_short" do 
    let(:default_classes) { "user short blurb group" }

    context "when logged in as bookmarker" do
      before do
        allow(helper).to receive(:current_user).and_return(bookmarker)
      end

      context "when bookmarkable is ExternalWork" do
        it "returns string with default classes, bookmarker info, and ownership indicator" do
          result = helper.css_classes_for_bookmark_blurb_short(external_work_bookmark)
          expect(result).to eq("own #{default_classes} user-#{bookmarker.id}")
        end
      end

      context "when bookmarkable is Series" do
        it "returns string with default classes, bookmarker info, and ownership indicator" do
          result = helper.css_classes_for_bookmark_blurb_short(series_bookmark)
          expect(result).to eq("own #{default_classes} user-#{bookmarker.id}")
        end
      end

      context "when bookmarkable is Work" do
        it "returns string with default classes, bookmarker info, and ownership indicator" do
          result = helper.css_classes_for_bookmark_blurb_short(work_bookmark)
          expect(result).to eq("own #{default_classes} user-#{bookmarker.id}")
        end
      end
    end

    context "when not logged in as bookmarker" do
      before do
        allow(helper).to receive(:current_user)
      end

      context "when bookmarkable is ExternalWork" do
        it "returns string with default classes and bookmarker info" do
          result = helper.css_classes_for_bookmark_blurb_short(external_work_bookmark)
          expect(result).to eq("#{default_classes} user-#{bookmarker.id}")
        end
      end

      context "when bookmarkable is Series" do
        it "returns string with default classes and bookmarker info" do
          result = helper.css_classes_for_bookmark_blurb_short(series_bookmark)
          expect(result).to eq("#{default_classes} user-#{bookmarker.id}")
        end
      end

      context "when bookmarkable is Work" do
        it "returns string with default classes and bookmarker info" do
          result = helper.css_classes_for_bookmark_blurb_short(work_bookmark)
          expect(result).to eq("#{default_classes} user-#{bookmarker.id}")
        end
      end
    end
  end
  
  describe "#bookmark_if_exists" do
    before do
      allow(helper).to receive(:current_user) { user.reload }
      allow(helper).to receive(:logged_in?) { user.present? }
    end

    let(:work) { create(:work) }

    context "when logged out" do
      let(:user) { nil }

      it "returns nil" do
        expect(helper.bookmark_if_exists(work)).to eq(nil)
      end
    end

    context "when logged in" do
      let(:user) { create(:user) }
      let(:default) { user.default_pseud }
      let(:pseud) { create(:pseud, user: user) }

      context "when the user has no bookmarks" do
        it "returns nil" do
          expect(helper.bookmark_if_exists(work)).to eq(nil)
        end
      end

      context "when the user has a bookmark for another work" do
        let!(:bookmark) { create(:bookmark, pseud: default) }

        it "returns nil" do
          expect(helper.bookmark_if_exists(work)).to eq(nil)
        end
      end

      context "when the user has one bookmark for the work" do
        let!(:bookmark) do
          create(:bookmark, bookmarkable: work, pseud: default)
        end

        it "returns the bookmark" do
          expect(helper.bookmark_if_exists(work)).to eq(bookmark)
        end
      end

      context "when the user has multiple bookmarks for the same work" do
        context "when one of the bookmarks is by the user's default pseud" do
          let!(:default_bookmark) do
            create(:bookmark, bookmarkable: work, pseud: default)
          end

          let!(:other_bookmark) do
            create_invalid(:bookmark, bookmarkable: work, pseud: pseud)
          end

          it "returns the bookmark by the default pseud" do
            expect(helper.bookmark_if_exists(work)).to eq(default_bookmark)
          end
        end

        context "when all of the bookmarks are by the user's default pseud" do
          let!(:older_bookmark) do
            create(:bookmark, bookmarkable: work, pseud: default)
          end

          let!(:newer_bookmark) do
            create_invalid(:bookmark, bookmarkable: work, pseud: default)
          end

          it "returns the most recent bookmark" do
            expect(helper.bookmark_if_exists(work)).to eq(newer_bookmark)
          end
        end

        context "when none of the bookmarks are by the user's default pseud" do
          let!(:older_bookmark) do
            create(:bookmark, bookmarkable: work, pseud: pseud)
          end

          let!(:newer_bookmark) do
            create_invalid(:bookmark, bookmarkable: work, pseud: pseud)
          end

          it "returns the most recent bookmark" do
            expect(helper.bookmark_if_exists(work)).to eq(newer_bookmark)
          end
        end
      end
    end
  end
end
