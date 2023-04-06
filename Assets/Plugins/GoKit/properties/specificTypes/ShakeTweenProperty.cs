using UnityEngine;
using System.Collections;


public class ShakeTweenProperty : AbstractTweenProperty
{
	private Transform _target;
	private Vector3 _shakeMagnitude;
	
	private Vector3 _originalEndValue;
	private Vector3 _startPosition;
	private Vector3 _startScale;
	private Vector3 _startEulers;
	
	private GoShakeType _shakeType;
	private int _frameCount;
	private int _frameMod;
	private bool _useLocalProperties;
	public bool useLocalProperties { get { return _useLocalProperties; } }
	
	
	/// <summary>
	/// you can shake any combination of position, scale and eulers by passing in a bitmask of the types you want to shake. frameMod
	/// allows you to specify what frame count the shakes should occur on. for example, a frameMod of 3 would mean that only when
	/// frameCount % 3 == 0 will the shake occur
	/// </summary>
	public ShakeTweenProperty( Vector3 shakeMagnitude, GoShakeType shakeType, int frameMod = 1, bool useLocalProperties = false ) : base( true )
	{
		_shakeMagnitude = shakeMagnitude;
		_shakeType = shakeType;
		_frameMod = frameMod;
		_useLocalProperties = useLocalProperties;
	}
	
	
	#region Object overrides
	
	public override int GetHashCode()
	{
		return base.GetHashCode();
	}
	
	
	public override bool Equals( object obj )
	{
		// start with a base check and then compare our material names
		if( base.Equals( obj ) )
			return this._shakeType == ((ShakeTweenProperty)obj)._shakeType;
		
		return false;
	}
	
	#endregion
	
	
	public override bool validateTarget( object target )
	{
		return target is Transform;
	}
	
	
	public override void prepareForUse()
	{
		_target = _ownerTween.target as Transform;
		_frameCount = 0;

		// store off any properties we will be shaking
		if( ( _shakeType & GoShakeType.Position ) != 0 )
		{
			if( _useLocalProperties )
				_startPosition = _target.localPosition;
			else
				_startPosition = _target.position;
		}

		if( ( _shakeType & GoShakeType.Eulers ) != 0 )
		{
			if( _useLocalProperties )
				_startEulers = _target.eulerAngles;
			else
				_startEulers = _target.eulerAngles;
		}
		
		if( ( _shakeType & GoShakeType.Scale ) != 0 )
			_startScale = _target.localScale;
	}
	
	
	private Vector3 randomDiminishingTarget( float falloffValue )
	{
		return new Vector3
		(
			Random.Range( -_shakeMagnitude.x, _shakeMagnitude.x ) * falloffValue,
			Random.Range( -_shakeMagnitude.y, _shakeMagnitude.y ) * falloffValue,
			Random.Range( -_shakeMagnitude.z, _shakeMagnitude.z ) * falloffValue
		);
	}
	

	public override void tick( float totalElapsedTime )
	{
		// should we skip any frames?
		if( _frameMod > 1 && ++_frameCount % _frameMod != 0 )
			return;
		
		// we want 1 minus the eased time so that we go from 1 - 0 for a shake
		var easedTime = 1 - _easeFunction( totalElapsedTime, 0, 1, _ownerTween.duration );
		
		
		// shake any properties required
		if( ( _shakeType & GoShakeType.Position ) != 0 )
		{
			var val = _startPosition + randomDiminishingTarget( easedTime );
			if( _useLocalProperties )
				_target.localPosition = val;
			else
				_target.position = val;
		}

		if( ( _shakeType & GoShakeType.Eulers ) != 0 )
		{
			var val = _startEulers + randomDiminishingTarget( easedTime );
			if( _useLocalProperties )
				_target.localEulerAngles = val;
			else
				_target.eulerAngles = val;
		}
		
		if( ( _shakeType & GoShakeType.Scale ) != 0 )
		{
			_target.localScale = _startScale + randomDiminishingTarget( easedTime );
		}
	}

}
