using System;
using UnityEngine;


public class GoEaseElastic
{
	public static float EaseIn( float t, float b, float c, float d )
	{
		if( t == 0 )
		{
			return b;
		}
		if( ( t /= d ) == 1 )
		{
			return b + c;
		}
		float p = d * .3f;
		float s = p / 4;
		return -(float)( c * Math.Pow( 2, 10 * ( t -= 1 ) ) * Math.Sin( ( t * d - s ) * ( 2 * Math.PI ) / p ) ) + b;
	}

	public static float EaseOut( float t, float b, float c, float d )
	{
		if( t == 0 )
		{
			return b;
		}
		if( ( t /= d ) == 1 )
		{
			return b + c;
		}
		float p = d * .3f;
		float s = p / 4;
		return (float)( c * Math.Pow( 2, -10 * t ) * Math.Sin( ( t * d - s ) * ( 2 * Math.PI ) / p ) + c + b );
	}

	public static float EaseInOut( float t, float b, float c, float d )
	{
		if( t == 0 )
		{
			return b;
		}
		if( ( t /= d / 2 ) == 2 )
		{
			return b + c;
		}
		float p = d * ( .3f * 1.5f );
		float a = c;
		float s = p / 4;
		if( t < 1 )
		{
			return -.5f * (float)( a * Math.Pow( 2, 10 * ( t -= 1 ) ) * Math.Sin( ( t * d - s ) * ( 2 * Math.PI ) / p ) ) + b;
		}
		return (float)( a * Math.Pow( 2, -10 * ( t -= 1 ) ) * Math.Sin( ( t * d - s ) * ( 2 * Math.PI ) / p ) * .5 + c + b );
	}

	public static float Punch( float t, float b, float c, float d )
	{
		if( t == 0 )
			return 0;

		if( ( t /= d ) == 1 )
			return 0;

		const float p = 0.3f;
		return ( c * Mathf.Pow( 2, -10 * t ) * Mathf.Sin( t * ( 2 * Mathf.PI ) / p ) );
	}
}