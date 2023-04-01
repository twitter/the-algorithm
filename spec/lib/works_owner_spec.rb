require 'spec_helper'

describe WorksOwner do

  before do
    @tag = Tag.new
    @tag.id = 666
    @time = "2013-09-27 19:14:18 -0400".to_datetime
    @time_string = @time.to_i.to_s
    Delorean.time_travel_to @time
    @tag.update_works_index_timestamp!
    Delorean.back_to_the_present
  end

  describe "works_index_timestamp" do
    it "should retrieve the owner's timestamp" do
      expect(@tag.works_index_timestamp).to eq(@time_string)
    end
  end

  describe "works_index_cache_key" do
    it "should return the full cache key" do
      expect(@tag.works_index_cache_key).to eq("works_index_for_tag_666_#{@time_string}")
    end

    it "should accept a tag argument and return the tag's timestamp" do
      collection = Collection.new
      collection.id = 42
      expect(collection.works_index_cache_key(@tag)).to eq("works_index_for_collection_42_tag_666_#{@time_string}")
    end
  end

  describe "update_works_index_timestamp!" do
    it "should update the timestamp for the owner" do
      @tag.update_works_index_timestamp!
      expect(@tag.works_index_timestamp).not_to eq(@time_string)
    end
  end

  describe "index_cache_key" do

    shared_examples_for "an owner" do
      it "should change after a work is updated" do
        @work.save
        expect(@original_cache_key).not_to eq(@owner.works_index_cache_key)
      end

      it "should change after a work is deleted" do
        if @owner.class.name == "Collection"
          Delorean.time_travel_to "10 minutes ago"
          @work.add_to_collection(@owner)
          @original_cache_key = @owner.works_index_cache_key
          Delorean.back_to_the_present
        end
        @work.destroy
        expect(@original_cache_key).not_to eq(@owner.works_index_cache_key)
      end
    end

    shared_examples_for "an owner tag" do
      it "should change after a new work is created" do
        FactoryBot.create(:work, fandom_string: @owner.name)
        expect(@original_cache_key).not_to eq(@owner.works_index_cache_key)
      end
    end

    shared_examples_for "an owner collection" do
      it "should change after a new work is created" do
        FactoryBot.create(:work, collection_names: @owner.name)
        @owner.collection_items.each {|ci| ci.approve(nil); ci.save}
        @child.collection_items.each {|ci| ci.approve(nil); ci.save} if @child
        expect(@original_cache_key).not_to eq(@owner.works_index_cache_key)
      end
    end

    shared_examples_for "an owner user" do
      it "should change after a new work is created" do
        author = @owner.is_a?(Pseud) ? @owner : @owner.default_pseud
        FactoryBot.create(:work, authors: [author])
        expect(@original_cache_key).not_to eq(@owner.works_index_cache_key)
      end

      it "should change after a work is orphaned" do
        # Ensure that the orphan account exists:
        build(:user, login: "orphan_account").save
        author = @owner.is_a?(Pseud) ? @owner : @owner.default_pseud
        Creatorship.orphan([author], [@work])
        expect(@original_cache_key).not_to eq(@owner.works_index_cache_key)
      end
    end

    describe "for a canonical tag" do
      before do
        Delorean.time_travel_to "10 minutes ago"
        @owner = FactoryBot.create(:fandom, canonical: true)
        @work = FactoryBot.create(:work, fandom_string: @owner.name)
        @original_cache_key = @owner.works_index_cache_key
        Delorean.back_to_the_present
      end
      it_should_behave_like "an owner"
      it_should_behave_like "an owner tag"

      describe "with a synonym" do
        before do
          Delorean.time_travel_to "10 minutes ago"
          @syn_tag = FactoryBot.create(:fandom, canonical: false)
          @syn_tag.syn_string = @owner.name
          @syn_tag.save
          @work2 = @work
          @work = FactoryBot.create(:work, fandom_string: @syn_tag.name)
          @original_cache_key = @owner.works_index_cache_key
          Delorean.back_to_the_present
        end
        it_should_behave_like "an owner"
        it_should_behave_like "an owner tag"

        it "should change after a new work is created in the synonym" do
          FactoryBot.create(:work, fandom_string: @syn_tag.name)
          expect(@original_cache_key).not_to eq(@owner.works_index_cache_key)
        end
      end
    end

    describe "for a collection" do
      before do
        Delorean.time_travel_to "10 minutes ago"
        @owner = FactoryBot.create(:collection)
        @work = FactoryBot.create(:work, collection_names: @owner.name)

        # we have to approve the collection items before we get a change in
        # the cache key, since it uses approved works
        @owner.collection_items.each {|ci| ci.approve(nil); ci.save}

        @original_cache_key = @owner.works_index_cache_key
        Delorean.back_to_the_present
      end
      it_should_behave_like "an owner"
      it_should_behave_like "an owner collection"

      describe "with a child" do
        before do
          Delorean.time_travel_to "10 minutes ago"
          @owner = FactoryBot.create(:collection)
          # Temporarily set User.current_user to get past the collection
          # needing to be owned by same person as parent:
          User.current_user = @owner.owners.first.user
          @child = FactoryBot.create(:collection, parent_name: @owner.name)
          User.current_user = nil
          # reload the parent collection
          @owner.reload
          @work1 = @work
          @work = FactoryBot.create(:work, collection_names: @child.name)
          @child.collection_items.each {|ci| ci.approve(nil); ci.save}
          @original_cache_key = @owner.works_index_cache_key
          Delorean.back_to_the_present
        end
        it_should_behave_like "an owner"
        it_should_behave_like "an owner collection"
      end

      describe "with a subtag" do
        before do
          @fandom = FactoryBot.create(:fandom)
          @work.fandom_string = @fandom.name
          @work.save
          @original_cache_key = @owner.works_index_cache_key(@fandom)
          @original_cache_key_without_subtag = @owner.works_index_cache_key
        end

        it "should have a different key than without the subtag" do
          expect(@original_cache_key).not_to eq(@original_cache_key_without_subtag)
        end

        describe "when a new work is added with that tag" do
          before do
            Delorean.time_travel_to "1 second from now"
            @work2 = FactoryBot.create(:work, fandom_string: @fandom.name, collection_names: @owner.name)
            @owner.collection_items.each {|ci| ci.approve(nil); ci.save}
            Delorean.back_to_the_present
          end

          it "should update both the cache keys" do
            expect(@original_cache_key_without_subtag).not_to eq(@owner.works_index_cache_key)
            # @original_cache_key.should_not eq(@owner.works_index_cache_key(@fandom))
          end
        end

        describe "when a new work is added without that tag" do
          before do
            @fandom2 = FactoryBot.create(:fandom)
            Delorean.time_travel_to "1 second from now"
            @work2 = FactoryBot.create(:work, fandom_string: @fandom2.name, collection_names: @owner.name)
            @owner.collection_items.each { |ci| ci.approve(nil); ci.save }
            Delorean.back_to_the_present
          end

          it "should update the main cache key without the tag" do
            expect(@original_cache_key_without_subtag).not_to eq(@owner.works_index_cache_key)
          end

          it "should not update the cache key with the tag" do
            expect(@owner.works_index_cache_key(@fandom)).to eq(@original_cache_key)
          end
        end

      end

    end

    describe "for a user" do
      before do
        @owner = FactoryBot.create(:user)
        @work = FactoryBot.create(:work, authors: [@owner.default_pseud])
      end
      it_should_behave_like "an owner"
      it_should_behave_like "an owner user"
    end

    describe "for a pseud" do
      before do
        user = FactoryBot.create(:user)
        @owner = FactoryBot.create(:pseud, user: user)
        @work = FactoryBot.create(:work, authors: [@owner])
      end
      it_should_behave_like "an owner"
      it_should_behave_like "an owner user"
    end

  end
end
