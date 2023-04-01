# frozen_string_literal: true
require 'spec_helper'

describe OwnedTagSetsController do
  include LoginMacros
  include RedirectExpectationHelper

  describe "index" do
    let(:params) { nil }

    context "where the user_id param is set" do
      let(:user) { create(:user) }
      let!(:owned_tag_sets) { Array.new(3) { create(:owned_tag_set, owner: user.default_pseud) } }

      it "displays the tag sets owned by the user" do
        get :index, params: { user_id: user.login }
        expect(assigns(:tag_sets)).to match_array owned_tag_sets
      end
    end

    context "where the restriction param is set" do
      let(:restriction) { create(:prompt_restriction) }
      let(:owned_set_taggings) { [create(:owned_set_tagging, set_taggable_type: restriction.class.to_s, set_taggable_id: restriction.id)] }
      let!(:owned_tag_sets) { create(:owned_tag_set, owned_set_taggings: owned_set_taggings) }

      it "displays the tag sets associated with the restriction" do
        get :index, params: { restriction: restriction.id }
        expect(assigns(:tag_sets)).to match_array owned_tag_sets
      end
    end

    context "where the query param is set" do
      let!(:matching_tag_set) { create(:owned_tag_set, title: "correct_title") }
      let!(:non_matching_tag_set) { create(:owned_tag_set, title: "asdf") }

      it "displays the tag set matching the query" do
        get :index, params: { query: "correct_title" }
        expect(assigns(:tag_sets)).to include matching_tag_set
        expect(assigns(:tag_sets)).to_not include non_matching_tag_set
      end
    end

    context "where no param is set" do
      let!(:tag_sets) { Array.new(3) { create(:owned_tag_set) } }

      it "displays the available tag sets" do
        get :index
        expect(assigns(:tag_sets)).to match_array tag_sets
      end
    end
  end

  describe "show_options" do
    context "where the restriction is found" do
      let(:restriction) { create(:prompt_restriction) }
      let!(:fandom_tag_sets) do
        Array.new(3) do
          owned_set_tagging = create(:owned_set_tagging, set_taggable_type: restriction.class.to_s, set_taggable_id: restriction.id)
          create(:owned_tag_set, owned_set_taggings: [owned_set_tagging])
        end
      end
      let(:character_tag) { create(:character) }
      let!(:character_tag_set) do
        owned_set_tagging = create(:owned_set_tagging, set_taggable_type: restriction.class.to_s, set_taggable_id: restriction.id)
        create(:owned_tag_set, owned_set_taggings: [owned_set_tagging], tags: [character_tag])
      end
      let(:params) { { restriction: restriction.id } }

      before do
        get :show_options, params: params
      end

      context "where the restriction isn't found" do
        it "displays an error and redirects" do
          get :show_options
          it_redirects_to_with_error(tag_sets_path, "Which Tag Set did you want to look at?")
        end
      end

      context "where tag_type isn't specified" do
        it "then sets the correct tags with the type fandom" do
          expect(assigns(:tag_sets)).to include(*fandom_tag_sets)
          expect(assigns(:tag_sets)).to include(character_tag_set)
          expect(assigns(:tag_set_ids)).to include(*fandom_tag_sets.map(&:tag_set_id))
          expect(assigns(:tag_set_ids)).to include character_tag_set.tag_set_id
          expect(assigns(:tag_type)).to eq "fandom"
          fandom_tag_sets.each do |tag_set|
            expect(assigns(:tags)).to include(*tag_set.tags)
          end
          expect(assigns(:tags)).to_not include character_tag
        end
      end

      context "where tag_type is specified" do
        let(:tag_type) { "character" }
        let(:params) { { restriction: restriction.id, tag_type: "character" } }

        it "sets the correct tags with the type character" do
          expect(assigns(:tag_sets)).to include(*fandom_tag_sets)
          expect(assigns(:tag_sets)).to include(character_tag_set)
          expect(assigns(:tag_set_ids)).to include(*fandom_tag_sets.map(&:tag_set_id))
          expect(assigns(:tag_set_ids)).to include character_tag_set.tag_set_id
          expect(assigns(:tag_type)).to eq "character"
          fandom_tag_sets.each do |tag_set|
            expect(assigns(:tags)).to_not include(*tag_set.tags)
          end
          expect(assigns(:tags)).to include character_tag
        end
      end
    end
  end

  describe "show" do
    context "where tag set is not found" do
      it "redirects and displays an error" do
        get :show, params: { id: 12345 }
        it_redirects_to_with_error(tag_sets_path, "What Tag Set did you want to look at?")
      end
    end

    context "where tag set is found" do
      let(:visible) { false }
      let(:tag) { create(:character, common_taggings: [create(:common_tagging)]) }
      let(:owned_tag_set) do
        create(
          :owned_tag_set,
          visible: visible,
          tags: [tag]
        )
      end

      context "where tag set is not visible" do
        it "doesn't set the tag hash" do
          get :show, params: { id: owned_tag_set.id }
          expect(assigns(:tag_hash)).to_not be_present
        end
      end

      context "where tag set is visible" do
        let(:visible) { true }

        context "where tag set has type character" do
          it "sets the correct data" do
            get :show, params: { id: owned_tag_set.id }
            tag_hash = assigns(:tag_hash)
            expect(tag_hash).to be_present
            filterable = Tag.find(tag.common_taggings.first.filterable_id)
            expect(tag_hash["character"][filterable.name].first).to eq tag.name
          end
        end
      end
    end
  end

  describe "create" do
    context "where user is not logged in" do
      it "shows the access denied path" do
        post :create
        it_redirects_to_with_error(new_user_session_path, "Sorry, you don't have permission to access the page you were trying to reach. Please log in.")
      end
    end

    context "where user is logged in" do
      let(:user) { create(:user) }
      let(:owned_tag_set_params) do
        { owned_tag_set: { title: generate(:tag_title), nominated: true } }
      end

      before do
        fake_login_known_user(user)
      end

      context "where tag set is successfully saved" do
        it "saves the owned tag set and redirects to the tag set path" do
          post :create, params: owned_tag_set_params
          tag_set = OwnedTagSet.find(assigns(:tag_set).id)
          expect(tag_set.tag_set_ownerships.map(&:pseud_id)).to include user.default_pseud.id
          it_redirects_to_with_notice(tag_set_path(tag_set), "Tag Set was successfully created.")
        end
      end

      context "where tag set is not successfully saved" do
        before do
          allow_any_instance_of(OwnedTagSet).to receive(:save).and_return(false)
        end

        it "renders new page" do
          post :create, params: owned_tag_set_params
          assert_template :new
        end
      end
    end
  end

  describe "update" do
    let(:tag_set) { create(:owned_tag_set) }
    let(:user) { create(:user) }

    before do
      fake_login_known_user(user)
    end

    context "where the user isn't a moderator" do
      it "redirects and displays an error" do
        put :update, params: { id: tag_set.id }
        it_redirects_to_with_error(user_path(user), "Sorry, you don't have permission to access the page you were trying to reach.")
      end
    end

    context "where the user is a moderator" do
      let(:tag_set) { create(:owned_tag_set, owner: user.default_pseud) }
      let(:params) { { id: tag_set.id, owned_tag_set: { nominated: false } } }

      context "where the tag set is successfully updated" do
        it "updates the tag set and redirects" do
          put :update, params: params
          expect(OwnedTagSet.find(tag_set.id).nominated).to eq false
          it_redirects_to_with_notice(tag_set_path(tag_set), "Tag Set was successfully updated.")
        end
      end

      context "where the tag set has not been successfully updated" do
        let(:fandom_tag) { create(:fandom) }
        let(:character_tag) { create(:character) }
        let(:tag_set) do
          create(:owned_tag_set, owner: user.default_pseud, tags: [fandom_tag, character_tag])
        end

        before do
          allow_any_instance_of(OwnedTagSet).to receive(:save).and_return(false)
        end

        it "sets the parent and child tags, then renders the edit view" do
          put :update, params: params
          expect(assigns(:tags_in_set)).to include(fandom_tag, character_tag)
          expect(assigns(:parent_tags_in_set)).to include [fandom_tag.name, fandom_tag.id]
          expect(assigns(:child_tags_in_set)).to include [character_tag.name, character_tag.id]
          assert_template :edit
        end
      end
    end
  end

  describe "destroy" do
    let(:user) { create(:user) }
    let(:tag_set) { create(:owned_tag_set, owner: user.default_pseud) }

    before do
      fake_login_known_user(user)
    end

    context "where the tag is successfully destroyed" do
      it "redirects with a notice" do
        post :destroy, params: { id: tag_set.id }
        expect(OwnedTagSet.find_by(id: tag_set.id)).to_not be_present
        it_redirects_to_with_notice(tag_sets_path, "Your Tag Set #{tag_set.title} was deleted.")
      end
    end

    context "where the tag is not successfully destroyed" do
      before do
        allow_any_instance_of(OwnedTagSet).to receive(:destroy) { raise ActiveRecord::RecordNotDestroyed }
      end

      it "redirects with a flash error" do
        post :destroy, params: { id: tag_set.id }
        it_redirects_to_with_error(tag_sets_path, "We couldn't delete that right now, sorry! Please try again later.")
      end
    end
  end

  describe "do_batch_load" do
    let(:user) { create(:user) }
    let(:tag_set) { create(:owned_tag_set, owner: user.default_pseud) }

    before do
      fake_login_known_user(user)
    end

    context "where the batch_association param is set" do
      context "where tags and associations load successfully" do
        before do
          allow_any_instance_of(OwnedTagSet).to receive(:load_batch_associations!).and_return []
        end

        it "redirects to tag set path and displays a notice" do
          put :do_batch_load, params: { id: tag_set.id, batch_associations: true }
          it_redirects_to_with_notice(tag_set_path(tag_set), "Tags and associations loaded!")
        end
      end

      context "where some tags and associations don't load successfully" do
        before do
          allow_any_instance_of(OwnedTagSet).to receive(:load_batch_associations!).and_return ["something"]
        end

        it "redirects to batch load path and displays a notice" do
          put :do_batch_load, params: { id: tag_set.id, batch_associations: true }
          expect(flash[:notice]).to eq "We couldn't add all the tags and associations you wanted -- the ones left below didn't work. See the help for suggestions!"
          assert_template :batch_load
        end
      end
    end

    context "where the batch_association param is not set" do
      it "redirects and displays an error" do
        put :do_batch_load, params: { id: tag_set.id }
        it_redirects_to_with_error(batch_load_tag_set_path, "What did you want to load?")
      end
    end
  end
end
