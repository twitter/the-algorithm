require "spec_helper"

describe "n+1 queries in the CommentsController" do
  include LoginMacros

  shared_examples "a hierarchical view with constant queries" do
    context "when displaying multiple comments", n_plus_one: true do
      context "when the comments are flat" do
        populate { |n| create_list(:comment, n, commentable: commentable) }

        warmup { subject.call }

        it "produces a constant number of queries when logged out" do
          expect do
            subject.call
          end.to perform_constant_number_of_queries
        end

        it "produces a constant number of queries when logged in" do
          fake_login

          expect do
            subject.call
          end.to perform_constant_number_of_queries
        end
      end

      context "when the comments are nested" do
        populate do |n|
          n.times.inject(commentable) do |parent|
            create(:comment, commentable: parent)
          end
        end

        warmup { subject.call }

        it "produces a constant number of queries when logged out" do
          expect do
            subject.call
          end.to perform_constant_number_of_queries
        end

        it "produces a constant number of queries when logged in" do
          fake_login

          expect do
            subject.call
          end.to perform_constant_number_of_queries
        end
      end
    end
  end

  describe "#show_comments" do
    subject do
      proc do
        get show_comments_comments_path(work_id: work, format: :js), xhr: true
      end
    end

    let!(:work) { create(:work) }
    let!(:commentable) { work.first_chapter }

    it_behaves_like "a hierarchical view with constant queries"
  end

  describe "#show" do
    subject do
      proc do
        get comment_path(comment)
      end
    end

    let!(:comment) { create(:comment) }
    let!(:commentable) { comment }

    it_behaves_like "a hierarchical view with constant queries"
  end

  describe "#index" do
    subject do
      proc do
        get work_comments_path(work, show_comments: true)
      end
    end

    let!(:work) { create(:work) }
    let!(:commentable) { work.first_chapter }

    it_behaves_like "a hierarchical view with constant queries"
  end

  describe "#unreviewed" do
    let!(:work) { create(:work, moderated_commenting_enabled: true) }

    context "when displaying multiple comments", n_plus_one: true do
      before { fake_login_known_user(work.users.first) }

      populate do |n|
        User.current_user = nil # log out to prevent commenting as work creator
        create_list(:comment, n, commentable: work.first_chapter)
      end

      warmup { get unreviewed_work_comments_path(work) }

      it "produces a constant number of queries" do
        expect do
          get unreviewed_work_comments_path(work)
        end.to perform_constant_number_of_queries
      end
    end
  end
end
