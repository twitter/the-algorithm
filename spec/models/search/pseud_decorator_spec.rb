require 'spec_helper'

describe PseudDecorator do
  before(:all) do
    @pseud = create(:pseud)
    @search_results = [{
      "_id"=>"#{@pseud.id}",
      "_source"=>{
        "id"=>@pseud.id,
        "user_id"=>@pseud.user_id,
        "name"=>@pseud.name,
        "description"=>nil,
        "user_login"=>@pseud.user_login,
        "byline"=>@pseud.byline,
        "collection_ids"=>[1],
        "sortable_name"=>@pseud.name.downcase,
        "fandoms" => [
          { "id" => 13, "name" => "Stargate SG-1", "count" => 7 },
          { "id_for_public" => 13, "name" => "Stargate SG-1", "count" => 2 }
        ],
        "general_bookmarks_count"=>7,
        "public_bookmarks_count"=>5,
        "general_works_count"=>10,
        "public_works_count"=>7
      }
    }]
  end

  describe ".decorate_from_search" do
    it "initializes decorators" do
      decs = PseudDecorator.decorate_from_search([@pseud], @search_results)
      expect(decs.length).to eq(1)
      expect(decs.first.name).to eq(@pseud.name)
    end
  end

  context "with search data" do
    before(:all) do
      @decorator = PseudDecorator.decorate_from_search([@pseud], @search_results).first
    end

    describe "#works_count" do
      it "returns the public count if there's no current user" do
        expect(@decorator.works_count).to eq(7)
      end
      it "returns the general count if there is a current user" do
        User.current_user = User.new
        expect(@decorator.works_count).to eq(10)
      end
      it "returns 0 if a count is nil" do
        data = @search_results.first.dup
        data["_source"]["public_works_count"] = nil
        dec = PseudDecorator.decorate_from_search([@pseud], [data]).first
        expect(dec.works_count).to eq(0)
      end
    end

    describe "#bookmarks_count" do
      it "returns the public count if there's no current user" do
        expect(@decorator.bookmarks_count).to eq(5)
      end
      it "returns the general count if there is a current user" do
        User.current_user = User.new
        expect(@decorator.bookmarks_count).to eq(7)
      end
    end

    describe "#byline" do
      it "matches the pseud byline" do
        expect(@decorator.byline).to eq(@pseud.byline)
      end
    end

    describe "#user_login" do
      it "matches the user login" do
        expect(@decorator.user_login).to eq(@pseud.user.login)
      end
    end

    describe "#pseud_path" do
      it "is the path to the pseud" do
        expect(@decorator.pseud_path).to eq("/users/#{@pseud.user.to_param}/pseuds/#{@pseud.to_param}")
      end
    end

    describe "#works_path" do
      it "is the path to the pseud works page" do
        expect(@decorator.works_path).to eq("/users/#{@pseud.user.to_param}/pseuds/#{@pseud.to_param}/works")
      end
    end

    describe "#works_link" do
      it "is an html link to the pseud works page" do
        expect(@decorator.works_link).to eq("<a href='/users/#{@pseud.user.to_param}/pseuds/#{@pseud.to_param}/works'>7 works</a>")
      end
    end

    describe "#bookmarks_path" do
      it "is the path to the pseud bookmarks page" do
        expect(@decorator.bookmarks_path).to eq("/users/#{@pseud.user.to_param}/pseuds/#{@pseud.to_param}/bookmarks")
      end
    end

    describe "#bookmarks_link" do
      it "is an html link to the pseud bookmarks page" do
        expect(@decorator.bookmarks_link).to eq("<a href='/users/#{@pseud.user.to_param}/pseuds/#{@pseud.to_param}/bookmarks'>5 bookmarks</a>")
      end
    end

    describe "#fandom_path" do
      it "is the path to the pseud works page with the fandom id" do
        expect(@decorator.fandom_path(13)).to eq("/users/#{@pseud.user.to_param}/pseuds/#{@pseud.to_param}/works?fandom_id=13")
      end
    end

    describe "#fandom_link" do
      it "is an html link to the pseud works page with the fandom id, showing the public count" do
        expect(@decorator.fandom_link(13)).to eq("<a href='/users/#{@pseud.user.to_param}/pseuds/#{@pseud.to_param}/works?fandom_id=13'>2 works in Stargate SG-1</a>")
      end
      it "is an html link to the pseud works page with the fandom id, showing the general count if there is a current user" do
        User.current_user = User.new
        expect(@decorator.fandom_link(13)).to eq("<a href='/users/#{@pseud.user.to_param}/pseuds/#{@pseud.to_param}/works?fandom_id=13'>7 works in Stargate SG-1</a>")
      end
      it "returns nil if there are no public works" do
        data = @search_results.first.dup
        data["_source"]["fandoms"] = [{ "id" => 13, "name" => "Stargate SG-1", "count" => 7 }]
        dec = PseudDecorator.decorate_from_search([@pseud], [data]).first
        expect(dec.fandom_link(13)).to be_nil
      end
    end

    describe "#authored_items_links" do
      it "combines the work and bookmark links" do
        str = "<a href='/users/#{@pseud.user.to_param}/pseuds/#{@pseud.to_param}/works'>7 works</a>, <a href='/users/#{@pseud.user.to_param}/pseuds/#{@pseud.to_param}/bookmarks'>5 bookmarks</a>"
        expect(@decorator.authored_items_links).to eq(str)
      end
    end

    describe "#constructed_byline" do
      it "matches the pseud byline" do
        expect(@decorator.constructed_byline).to eq(@pseud.byline)
      end
    end
  end
end
