# frozen_string_literal: true

require "spec_helper"

describe ApplicationHelper do
  describe "#creation_id_for_css_classes" do
    context "when creation is ExternalWork" do
      let(:external_work) { create(:external_work) }

      it "returns string for exteral work" do
        result = helper.creation_id_for_css_classes(external_work)
        expect(result).to eq("external-work-#{external_work.id}")
      end
    end

    context "when creation is Series" do
      let(:series) { create(:series) }

      it "returns string for series" do
        result = helper.creation_id_for_css_classes(series)
        expect(result).to eq("series-#{series.id}")
      end
    end

    context "when creation is Work" do
      let(:work) { create(:work) }

      it "returns string for work" do
        result = helper.creation_id_for_css_classes(work)
        expect(result).to eq("work-#{work.id}")
      end
    end
  end

  describe "#creator_ids_for_css_classes" do
    context "when creation is ExternalWork" do
      let(:external_work) { create(:external_work) }

      it "returns empty array for exteral work" do
        result = helper.creator_ids_for_css_classes(external_work)
        expect(result).to be_empty
      end
    end

    context "when creation is Series" do
      let(:series) { create(:series_with_a_work) }
      let(:user1) { series.users.first }
      let(:work) { series.works.first }

      it "returns array of strings for series" do
        result = helper.creator_ids_for_css_classes(series)
        expect(result).to eq(["user-#{user1.id}"])
      end

      context "with multiple pseuds from same user" do
        let(:user1_pseud2) { create(:pseud, user: user1) }

        before do
          series.creatorships.find_or_create_by(pseud_id: user1_pseud2.id)
        end

        it "returns array of strings with one user" do
          result = helper.creator_ids_for_css_classes(series)
          expect(result).to eq(["user-#{user1.id}"])
        end
      end

      context "with pseuds from multiple users" do
        let(:user2) { create(:user) }

        before do
          series.creatorships.find_or_create_by(pseud_id: user2.default_pseud_id)
        end

        it "returns array of strings with all users" do
          result = helper.creator_ids_for_css_classes(series)
          expect(result).to eq(["user-#{user1.id}", "user-#{user2.id}"])
        end
      end

      context "when series is anonymous" do
        let(:collection) { create(:anonymous_collection) }

        before { work.collections << collection }

        it "returns empty array" do
          result = helper.creator_ids_for_css_classes(series)
          expect(result).to be_empty
        end
      end

      context "when work is unrevealed" do
        let(:collection) { create(:unrevealed_collection) }

        before { work.collections << collection }

        it "returns array of strings" do
          result = helper.creator_ids_for_css_classes(series)
          expect(result).to eq(["user-#{user1.id}"])
        end
      end
    end

    context "when creation is Work" do
      let(:work) { create(:work) }
      let(:user1) { work.users.first }

      it "returns array of strings for work" do
        result = helper.creator_ids_for_css_classes(work)
        expect(result).to eq(["user-#{user1.id}"])
      end

      context "with multiple pseuds from same user" do
        let(:user1_pseud2) { create(:pseud, user: user1) }

        before do
          work.creatorships.find_or_create_by(pseud_id: user1_pseud2.id)
        end

        it "returns array of strings with one user" do
          result = helper.creator_ids_for_css_classes(work)
          expect(result).to eq(["user-#{user1.id}"])
        end
      end

      context "with pseuds from multiple users" do
        let(:user2) { create(:user) }

        before do
          work.creatorships.find_or_create_by(pseud_id: user2.default_pseud_id)
        end

        it "returns array of strings with all users" do
          result = helper.creator_ids_for_css_classes(work)
          expect(result).to eq(["user-#{user1.id}", "user-#{user2.id}"])
        end
      end

      context "when work is anonymous" do
        let(:collection) { create(:anonymous_collection) }

        before { work.collections << collection }

        it "returns empty array" do
          result = helper.creator_ids_for_css_classes(work)
          expect(result).to be_empty
        end
      end

      context "when work is unrevealed" do
        let(:collection) { create(:unrevealed_collection) }

        before { work.collections << collection }

        it "returns empty array" do
          result = helper.creator_ids_for_css_classes(work)
          expect(result).to be_empty
        end
      end

      context "when work has external author" do
        let(:external_creatorship) { create(:external_creatorship, work: work) }

        it "returns array of strings with user" do
          result = helper.creator_ids_for_css_classes(work)
          expect(result).to eq(["user-#{user1.id}"])
        end
      end

      context "when work has no user" do
        before { work.creatorships.delete_all }

        it "returns empty array" do
          result = helper.creator_ids_for_css_classes(work)
          expect(result).to be_empty
        end
      end
    end
  end

  describe "#css_classes_for_creation_blurb" do
    let(:default_classes) { "blurb group" }

    context "when creation is ExternalWork" do
      let(:external_work) { create(:external_work) }

      it "returns string with default classes and creation info" do
        result = helper.css_classes_for_creation_blurb(external_work)
        expect(result).to eq("#{default_classes} external-work-#{external_work.id}")
      end
    end

    context "when creation is Series" do
      let(:series) { create(:series_with_a_work) }
      let(:work) { series.works.first }
      let(:user1) { series.users.first }
      let(:user2) { create(:user) }

      it "returns string with default classes and creation and creator info" do
        result = helper.css_classes_for_creation_blurb(series)
        expect(result).to eq("#{default_classes} series-#{series.id} user-#{user1.id}")
      end

      context "when series is updated" do
        context "when new user is added" do
          it "returns updated string" do
            original_cache_key = "#{series.cache_key_with_version}/blurb_css_classes-v2"
            expect(helper.css_classes_for_creation_blurb(series)).to eq("#{default_classes} series-#{series.id} user-#{user1.id}")

            travel(1.day)
            series.creatorships.find_or_create_by(pseud_id: user2.default_pseud_id)
            expect(helper.css_classes_for_creation_blurb(series.reload)).to eq("#{default_classes} series-#{series.id} user-#{user1.id} user-#{user2.id}")
            expect(original_cache_key).not_to eq("#{series.cache_key_with_version}/blurb_css_classes-v2")
            travel_back
          end
        end

        context "when user is removed from series" do
          before { series.creatorships.find_or_create_by(pseud_id: user2.default_pseud_id) }

          it "returns updated string" do
            original_cache_key = "#{series.cache_key_with_version}/blurb_css_classes-v2"
            expect(helper.css_classes_for_creation_blurb(series)).to eq("#{default_classes} series-#{series.id} user-#{user1.id} user-#{user2.id}")

            travel(1.day)
            series.creatorships.find_by(pseud_id: user2.default_pseud_id).destroy
            expect(helper.css_classes_for_creation_blurb(series.reload)).to eq("#{default_classes} series-#{series.id} user-#{user1.id}")
            expect(original_cache_key).not_to eq("#{series.cache_key_with_version}/blurb_css_classes-v2")
            travel_back
          end
        end
      end

      context "when series' work is updated" do
        context "when new user is added to series' work" do
          it "returns updated string" do
            original_cache_key = "#{series.cache_key_with_version}/blurb_css_classes-v2"
            expect(helper.css_classes_for_creation_blurb(series)).to eq("#{default_classes} series-#{series.id} user-#{user1.id}")

            travel(1.day)
            work.creatorships.find_or_create_by(pseud_id: user2.default_pseud_id)
            expect(helper.css_classes_for_creation_blurb(series.reload)).to eq("#{default_classes} series-#{series.id} user-#{user1.id} user-#{user2.id}")
            expect(original_cache_key).not_to eq("#{series.cache_key_with_version}/blurb_css_classes-v2")
            travel_back
          end
        end

        context "when user is removed from series' work" do
          before do
            work.creatorships.find_or_create_by(pseud_id: user2.default_pseud_id)
            series.reload # make sure the series has the right value for updated_at
          end

          # TODO: AO3-5739 Co-creators removed from all works in a series are not removed from series
          it "returns same string" do
            original_cache_key = "#{series.cache_key_with_version}/blurb_css_classes-v2"
            expect(helper.css_classes_for_creation_blurb(series)).to eq("#{default_classes} series-#{series.id} user-#{user1.id} user-#{user2.id}")

            travel(1.day)
            work.creatorships.find_by(pseud_id: user2.default_pseud_id).destroy
            expect(helper.css_classes_for_creation_blurb(series.reload)).to eq("#{default_classes} series-#{series.id} user-#{user1.id} user-#{user2.id}")
            expect(original_cache_key).to eq("#{series.cache_key_with_version}/blurb_css_classes-v2")
            travel_back
          end
        end

        context "when work becomes anonymous" do
          let(:collection) { create(:anonymous_collection) }

          it "returns updated string" do
            original_cache_key = "#{series.cache_key_with_version}/blurb_css_classes-v2"
            expect(helper.css_classes_for_creation_blurb(series)).to eq("#{default_classes} series-#{series.id} user-#{user1.id}")

            travel(1.day)
            work.collections << collection
            expect(helper.css_classes_for_creation_blurb(series.reload)).to eq("#{default_classes} series-#{series.id}")
            expect(original_cache_key).not_to eq("#{series.cache_key_with_version}/blurb_css_classes-v2")
            travel_back
          end
        end

        context "when work becomes unrevealed" do
          let(:collection) { create(:unrevealed_collection) }

          it "returns same string" do
            original_cache_key = "#{series.cache_key_with_version}/blurb_css_classes-v2"
            expect(helper.css_classes_for_creation_blurb(series)).to eq("#{default_classes} series-#{series.id} user-#{user1.id}")

            travel(1.day)
            work.collections << collection
            expect(helper.css_classes_for_creation_blurb(series.reload)).to eq("#{default_classes} series-#{series.id} user-#{user1.id}")
            expect(original_cache_key).to eq("#{series.cache_key_with_version}/blurb_css_classes-v2")
            travel_back
          end
        end
      end
    end

    context "when creation is Work" do
      let(:work) { create(:work) }
      let(:user1) { work.users.first }
      let(:user2) { create(:user) }

      it "returns string with default classes and creation and creator info" do
        result = helper.css_classes_for_creation_blurb(work)
        expect(result).to eq("#{default_classes} work-#{work.id} user-#{user1.id}")
      end

      context "when new user is added" do
        it "returns updated string" do
          travel_to(1.day.ago)
          original_cache_key = "#{work.cache_key_with_version}/blurb_css_classes-v2"
          expect(helper.css_classes_for_creation_blurb(work)).to eq("#{default_classes} work-#{work.id} user-#{user1.id}")

          travel_back
          work.creatorships.find_or_create_by(pseud_id: user2.default_pseud_id)
          expect(helper.css_classes_for_creation_blurb(work)).to eq("#{default_classes} work-#{work.id} user-#{user1.id} user-#{user2.id}")
          expect(original_cache_key).not_to eq("#{work.cache_key_with_version}/blurb_css_classes-v2")
        end
      end

      context "when user is removed" do
        it "returns updated string" do
          travel_to(1.day.ago)
          work.creatorships.find_or_create_by(pseud_id: user2.default_pseud_id)
          original_cache_key = "#{work.cache_key_with_version}/blurb_css_classes-v2"
          expect(helper.css_classes_for_creation_blurb(work)).to eq("#{default_classes} work-#{work.id} user-#{user1.id} user-#{user2.id}")

          travel_back
          work.creatorships.find_by(pseud_id: user2.default_pseud_id).destroy
          expect(helper.css_classes_for_creation_blurb(work)).to eq("#{default_classes} work-#{work.id} user-#{user1.id}")
          expect(original_cache_key).not_to eq("#{work.cache_key_with_version}/blurb_css_classes-v2")
        end
      end

      context "when work becomes anonymous" do
        let(:collection) { create(:anonymous_collection) }

        it "returns updated string" do
          travel_to(1.day.ago)
          original_cache_key = "#{work.cache_key_with_version}/blurb_css_classes-v2"
          expect(helper.css_classes_for_creation_blurb(work)).to eq("#{default_classes} work-#{work.id} user-#{user1.id}")

          travel_back
          work.collections << collection
          expect(helper.css_classes_for_creation_blurb(work)).to eq("#{default_classes} work-#{work.id}")
          expect(original_cache_key).not_to eq("#{work.cache_key_with_version}/blurb_css_classes-v2")
        end
      end

      context "when work becomes unrevealed" do
        let(:collection) { create(:unrevealed_collection) }

        it "returns updated string" do
          travel_to(1.day.ago)
          original_cache_key = "#{work.cache_key_with_version}/blurb_css_classes-v2"
          expect(helper.css_classes_for_creation_blurb(work)).to eq("#{default_classes} work-#{work.id} user-#{user1.id}")

          travel_back
          work.collections << collection
          expect(helper.css_classes_for_creation_blurb(work)).to eq("#{default_classes} work-#{work.id}")
          expect(original_cache_key).not_to eq("#{work.cache_key_with_version}/blurb_css_classes-v2")
        end
      end
    end
  end
end
