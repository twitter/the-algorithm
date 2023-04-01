class ChangePreferenceAllowGiftsDefault < ActiveRecord::Migration[5.2]
  def change
    change_column_default :preferences, :allow_gifts, from: true, to: false
  end
end
