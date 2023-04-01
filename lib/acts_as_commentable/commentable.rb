module ActsAsCommentable
  module Commentable
    def self.included(base)
      base.extend ClassMethods
    end

    module ClassMethods
      def acts_as_commentable
        send :include, CommentableEntity
      end

      def has_comment_methods
        send :include, CommentMethods
      end
    end
  end
end
