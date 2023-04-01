class ApplicationPolicy
  attr_reader :user, :record

  def initialize(user, record)
    @user = user
    @record = record
  end

  def index?
    false
  end

  def show?
    false
  end

  def create?
    false
  end

  def new?
    create?
  end

  def update?
    false
  end

  def edit?
    update?
  end

  def destroy?
    false
  end

  def confirm_delete?
    destroy?
  end

  # Explicitly check that the user is an admin because regular users can have
  # roles (e.g. archivist) as well, but we don't handle those with pundit.
  def user_has_roles?(roles)
    user&.is_a?(Admin) && user.respond_to?(:roles) && (user.roles & roles).present?
  end

  class Scope
    attr_reader :user, :scope

    def initialize(user, scope)
      @user = user
      @scope = scope
    end

    def resolve
      scope.all
    end
  end
end
