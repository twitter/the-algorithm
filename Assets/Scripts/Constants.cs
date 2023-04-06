using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Assets.Scripts
{
    public static class Constants
    {
        public static readonly float MinVelocity = 0.05f;

        /// <summary>
        /// The collider of the bird is bigger when on idle state
        /// This will make it easier for the user to tap on it
        /// </summary>
        public static readonly float BirdColliderRadiusNormal = 0.235f;
        public static readonly float BirdColliderRadiusBig = 0.5f;
    }
}
