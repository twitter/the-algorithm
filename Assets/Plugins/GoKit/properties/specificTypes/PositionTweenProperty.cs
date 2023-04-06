using UnityEngine;
using System.Collections;


public class PositionTweenProperty : AbstractVector3TweenProperty
{
	protected bool _useLocalPosition;
	public bool useLocalPosition { get { return _useLocalPosition; } }
	
	public PositionTweenProperty( Vector3 endValue, bool isRelative = false, bool useLocalPosition = false ) : base( endValue, isRelative )
	{
		_useLocalPosition = useLocalPosition;
	}
	
	
	#region Object overrides
	
	public override int GetHashCode()
	{
		return base.GetHashCode();
	}
	
	
	public override bool Equals( object obj )
	{
		// start with a base check and then compare if we are both using local values
		if( base.Equals( obj ) )
			return this._useLocalPosition == ((PositionTweenProperty)obj)._useLocalPosition;
		
		// if we get here, we need to see if the other object is a position path tween of the same kind
		var otherAsPositionPath = obj as PositionPathTweenProperty;
		if( otherAsPositionPath != null )
			return this._useLocalPosition == otherAsPositionPath.useLocalPosition;
		
		return false;
	}
	
	#endregion

	
	public override void prepareForUse()
	{
		_target = _ownerTween.target as Transform;
		
		_endValue = _originalEndValue;
		
		// if this is a from tween we need to swap the start and end values
		if( _ownerTween.isFrom )
		{
			if( _useLocalPosition )
			{
				if (_isRelative)
					_startValue = _target.localPosition + _endValue;
				else
					_startValue = _endValue;

				_endValue = _target.localPosition;
			}
			else
			{
				if (_isRelative)
					_startValue = _target.position + _endValue;
				else
					_startValue = _endValue;

				_endValue = _target.position;
			}
		}
		else
		{
			if( _useLocalPosition )
				_startValue = _target.localPosition;
			else
				_startValue = _target.position;
		}
		
		base.prepareForUse();
	}
	

	public override void tick( float totalElapsedTime )
	{
		var easedTime = _easeFunction( totalElapsedTime, 0, 1, _ownerTween.duration );
		var vec = GoTweenUtils.unclampedVector3Lerp( _startValue, _diffValue, easedTime );
		
		if( _useLocalPosition )
			_target.localPosition = vec;
		else
			_target.position = vec;
	}

}
