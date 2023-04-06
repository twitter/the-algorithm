using UnityEngine;
using System.Collections;



public struct GoSmoothedVector3
{
	public GoSmoothingType smoothingType;
	public float duration;
	
	private Vector3 _currentValue;
	private Vector3 _target;
	private Vector3 _start;
	private float _startTime;
	
	
	public GoSmoothedVector3( Vector3 vector )
	{
		_currentValue = vector;
		_start = vector;		
		_target = vector;
		_startTime = Time.time;
		
		// set sensible defaults
		duration = 0.2f;
		smoothingType = GoSmoothingType.Lerp;
	}
	
	
	public Vector3 smoothValue
	{
		get
		{
			// how far along are we?
			var t = ( Time.time - _startTime ) / duration;
			
			switch( smoothingType )
			{
				case GoSmoothingType.Lerp:
					_currentValue = Vector3.Lerp( _start, _target, t );
					break;
				case GoSmoothingType.Slerp:
					_currentValue = Vector3.Slerp( _start, _target, t );
					break;
			}
			
			return _currentValue;
		}
		private set
		{
			_start = smoothValue;
			_startTime = Time.time;
			_target = value;
		}
	}
	
	
	public float x
	{
		get
		{
			return _currentValue.x;
		}
		set
		{
			smoothValue = new Vector3( value, _target.y, _target.z );
		}
	}
	
	public float y
	{
		get
		{
			return _currentValue.y;
		}
		set
		{
			smoothValue = new Vector3( _target.x, value, _target.y );
		}
	}
	
	public float z
	{
		get
		{
			return _currentValue.z;
		}
		set
		{
			smoothValue = new Vector3( _target.x, _target.y, value );
		}
			
	}
	
	
	public static implicit operator GoSmoothedVector3( Vector3 v )
	{
		return new GoSmoothedVector3( v );
	}
	
}
