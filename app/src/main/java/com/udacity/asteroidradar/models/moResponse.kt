package com.udacity.asteroidradar.models

import java.io.IOException
import java.time.LocalDate
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

class moResponse {

        var links: Welcome1Links? = null
        var elementCount: Long = 0
        var nearEarthObjects: Map<String, NearEarthObject?>? =null


    // Welcome1Links.java
    inner class Welcome1Links {
        var next: String? = null
        var prev: String? = null
        var self: String? = null
    }

    // NearEarthObject.java
    inner class NearEarthObject {
        var links: NearEarthObjectLinks? = null
        var iD: String? = null
        var neoReferenceID: String? = null
        var name: String? = null
        var nasaJplURL: String? = null
        var absoluteMagnitudeH = 0.0
        var estimatedDiameter: EstimatedDiameter? = null
        var isPotentiallyHazardousAsteroid = false
        var closeApproachData: Array<CloseApproachDatum> = TODO()
        var isSentryObject = false
    }

    // CloseApproachDatum.java
    inner class CloseApproachDatum {
        var closeApproachDate: LocalDate? = null
        var closeApproachDateFull: String? = null
        var epochDateCloseApproach: Long = 0
        var relativeVelocity: RelativeVelocity? = null
        var missDistance: MissDistance? = null
        var orbitingBody: OrbitingBody? = null
    }

    // MissDistance.java
    inner class MissDistance {
        var astronomical: String? = null
        var lunar: String? = null
        var kilometers: String? = null
        var miles: String? = null
    }

    // OrbitingBody.java
    enum class OrbitingBody {
        EARTH;

        fun toValue(): String? {
            return when (this) {
                EARTH -> "Earth"
            }
            return null
        }

        companion object {
            @kotlin.jvm.Throws(IOException::class)
            fun forValue(value: String): OrbitingBody {
                if (value == "Earth") return EARTH
                throw IOException("Cannot deserialize OrbitingBody")
            }
        }
    }

    // RelativeVelocity.java
    inner class RelativeVelocity {
        var kilometersPerSecond: String? = null
        var kilometersPerHour: String? = null
        var milesPerHour: String? = null
    }

    // EstimatedDiameter.java
    inner class EstimatedDiameter {
        var kilometers: Feet? = null
        var meters: Feet? = null
        var miles: Feet? = null
        var feet: Feet? = null
    }

    // Feet.java
    inner class Feet {
        var estimatedDiameterMin = 0.0
        var estimatedDiameterMax = 0.0
    }

    // NearEarthObjectLinks.java
    inner class NearEarthObjectLinks {
        var self: String? = null
    }
}