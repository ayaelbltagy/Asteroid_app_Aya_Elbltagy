package com.udacity.asteroidradar.models

data class ModelResponse(
    val element_count: Int,
    val links: Links,
    val near_earth_objects: NearEarthObjects
) {
    data class Links(
        val next: String,
        val prev: String,
        val self: String
    )

    data class NearEarthObjects(
        val `2022-08-23`: List<X20220823>,
        val `2022-08-24`: List<X20220824>,
        val `2022-08-25`: List<X20220825>,
        val `2022-08-26`: List<X20220826>,
        val `2022-08-27`: List<X20220827>,
        val `2022-08-28`: List<X20220828>,
        val `2022-08-29`: List<X20220829>,
        val `2022-08-30`: List<X20220830>
    ) {
        data class X20220823(
            val absolute_magnitude_h: Double,
            val close_approach_data: List<CloseApproachData>,
            val estimated_diameter: EstimatedDiameter,
            val id: String,
            val is_potentially_hazardous_asteroid: Boolean,
            val is_sentry_object: Boolean,
            val links: Links,
            val name: String,
            val nasa_jpl_url: String,
            val neo_reference_id: String
        ) {
            data class CloseApproachData(
                val close_approach_date: String,
                val close_approach_date_full: String,
                val epoch_date_close_approach: Long,
                val miss_distance: MissDistance,
                val orbiting_body: String,
                val relative_velocity: RelativeVelocity
            ) {
                data class MissDistance(
                    val astronomical: String,
                    val kilometers: String,
                    val lunar: String,
                    val miles: String
                )

                data class RelativeVelocity(
                    val kilometers_per_hour: String,
                    val kilometers_per_second: String,
                    val miles_per_hour: String
                )
            }

            data class EstimatedDiameter(
                val feet: Feet,
                val kilometers: Kilometers,
                val meters: Meters,
                val miles: Miles
            ) {
                data class Feet(
                    val estimated_diameter_max: Double,
                    val estimated_diameter_min: Double
                )

                data class Kilometers(
                    val estimated_diameter_max: Double,
                    val estimated_diameter_min: Double
                )

                data class Meters(
                    val estimated_diameter_max: Double,
                    val estimated_diameter_min: Double
                )

                data class Miles(
                    val estimated_diameter_max: Double,
                    val estimated_diameter_min: Double
                )
            }

            data class Links(
                val self: String
            )
        }

        data class X20220824(
            val absolute_magnitude_h: Double,
            val close_approach_data: List<CloseApproachData>,
            val estimated_diameter: EstimatedDiameter,
            val id: String,
            val is_potentially_hazardous_asteroid: Boolean,
            val is_sentry_object: Boolean,
            val links: Links,
            val name: String,
            val nasa_jpl_url: String,
            val neo_reference_id: String
        ) {
            data class CloseApproachData(
                val close_approach_date: String,
                val close_approach_date_full: String,
                val epoch_date_close_approach: Long,
                val miss_distance: MissDistance,
                val orbiting_body: String,
                val relative_velocity: RelativeVelocity
            ) {
                data class MissDistance(
                    val astronomical: String,
                    val kilometers: String,
                    val lunar: String,
                    val miles: String
                )

                data class RelativeVelocity(
                    val kilometers_per_hour: String,
                    val kilometers_per_second: String,
                    val miles_per_hour: String
                )
            }

            data class EstimatedDiameter(
                val feet: Feet,
                val kilometers: Kilometers,
                val meters: Meters,
                val miles: Miles
            ) {
                data class Feet(
                    val estimated_diameter_max: Double,
                    val estimated_diameter_min: Double
                )

                data class Kilometers(
                    val estimated_diameter_max: Double,
                    val estimated_diameter_min: Double
                )

                data class Meters(
                    val estimated_diameter_max: Double,
                    val estimated_diameter_min: Double
                )

                data class Miles(
                    val estimated_diameter_max: Double,
                    val estimated_diameter_min: Double
                )
            }

            data class Links(
                val self: String
            )
        }

        data class X20220825(
            val absolute_magnitude_h: Double,
            val close_approach_data: List<CloseApproachData>,
            val estimated_diameter: EstimatedDiameter,
            val id: String,
            val is_potentially_hazardous_asteroid: Boolean,
            val is_sentry_object: Boolean,
            val links: Links,
            val name: String,
            val nasa_jpl_url: String,
            val neo_reference_id: String
        ) {
            data class CloseApproachData(
                val close_approach_date: String,
                val close_approach_date_full: String,
                val epoch_date_close_approach: Long,
                val miss_distance: MissDistance,
                val orbiting_body: String,
                val relative_velocity: RelativeVelocity
            ) {
                data class MissDistance(
                    val astronomical: String,
                    val kilometers: String,
                    val lunar: String,
                    val miles: String
                )

                data class RelativeVelocity(
                    val kilometers_per_hour: String,
                    val kilometers_per_second: String,
                    val miles_per_hour: String
                )
            }

            data class EstimatedDiameter(
                val feet: Feet,
                val kilometers: Kilometers,
                val meters: Meters,
                val miles: Miles
            ) {
                data class Feet(
                    val estimated_diameter_max: Double,
                    val estimated_diameter_min: Double
                )

                data class Kilometers(
                    val estimated_diameter_max: Double,
                    val estimated_diameter_min: Double
                )

                data class Meters(
                    val estimated_diameter_max: Double,
                    val estimated_diameter_min: Double
                )

                data class Miles(
                    val estimated_diameter_max: Double,
                    val estimated_diameter_min: Double
                )
            }

            data class Links(
                val self: String
            )
        }

        data class X20220826(
            val absolute_magnitude_h: Double,
            val close_approach_data: List<CloseApproachData>,
            val estimated_diameter: EstimatedDiameter,
            val id: String,
            val is_potentially_hazardous_asteroid: Boolean,
            val is_sentry_object: Boolean,
            val links: Links,
            val name: String,
            val nasa_jpl_url: String,
            val neo_reference_id: String
        ) {
            data class CloseApproachData(
                val close_approach_date: String,
                val close_approach_date_full: String,
                val epoch_date_close_approach: Long,
                val miss_distance: MissDistance,
                val orbiting_body: String,
                val relative_velocity: RelativeVelocity
            ) {
                data class MissDistance(
                    val astronomical: String,
                    val kilometers: String,
                    val lunar: String,
                    val miles: String
                )

                data class RelativeVelocity(
                    val kilometers_per_hour: String,
                    val kilometers_per_second: String,
                    val miles_per_hour: String
                )
            }

            data class EstimatedDiameter(
                val feet: Feet,
                val kilometers: Kilometers,
                val meters: Meters,
                val miles: Miles
            ) {
                data class Feet(
                    val estimated_diameter_max: Double,
                    val estimated_diameter_min: Double
                )

                data class Kilometers(
                    val estimated_diameter_max: Double,
                    val estimated_diameter_min: Double
                )

                data class Meters(
                    val estimated_diameter_max: Double,
                    val estimated_diameter_min: Double
                )

                data class Miles(
                    val estimated_diameter_max: Double,
                    val estimated_diameter_min: Double
                )
            }

            data class Links(
                val self: String
            )
        }

        data class X20220827(
            val absolute_magnitude_h: Double,
            val close_approach_data: List<CloseApproachData>,
            val estimated_diameter: EstimatedDiameter,
            val id: String,
            val is_potentially_hazardous_asteroid: Boolean,
            val is_sentry_object: Boolean,
            val links: Links,
            val name: String,
            val nasa_jpl_url: String,
            val neo_reference_id: String
        ) {
            data class CloseApproachData(
                val close_approach_date: String,
                val close_approach_date_full: String,
                val epoch_date_close_approach: Long,
                val miss_distance: MissDistance,
                val orbiting_body: String,
                val relative_velocity: RelativeVelocity
            ) {
                data class MissDistance(
                    val astronomical: String,
                    val kilometers: String,
                    val lunar: String,
                    val miles: String
                )

                data class RelativeVelocity(
                    val kilometers_per_hour: String,
                    val kilometers_per_second: String,
                    val miles_per_hour: String
                )
            }

            data class EstimatedDiameter(
                val feet: Feet,
                val kilometers: Kilometers,
                val meters: Meters,
                val miles: Miles
            ) {
                data class Feet(
                    val estimated_diameter_max: Double,
                    val estimated_diameter_min: Double
                )

                data class Kilometers(
                    val estimated_diameter_max: Double,
                    val estimated_diameter_min: Double
                )

                data class Meters(
                    val estimated_diameter_max: Double,
                    val estimated_diameter_min: Double
                )

                data class Miles(
                    val estimated_diameter_max: Double,
                    val estimated_diameter_min: Double
                )
            }

            data class Links(
                val self: String
            )
        }

        data class X20220828(
            val absolute_magnitude_h: Double,
            val close_approach_data: List<CloseApproachData>,
            val estimated_diameter: EstimatedDiameter,
            val id: String,
            val is_potentially_hazardous_asteroid: Boolean,
            val is_sentry_object: Boolean,
            val links: Links,
            val name: String,
            val nasa_jpl_url: String,
            val neo_reference_id: String
        ) {
            data class CloseApproachData(
                val close_approach_date: String,
                val close_approach_date_full: String,
                val epoch_date_close_approach: Long,
                val miss_distance: MissDistance,
                val orbiting_body: String,
                val relative_velocity: RelativeVelocity
            ) {
                data class MissDistance(
                    val astronomical: String,
                    val kilometers: String,
                    val lunar: String,
                    val miles: String
                )

                data class RelativeVelocity(
                    val kilometers_per_hour: String,
                    val kilometers_per_second: String,
                    val miles_per_hour: String
                )
            }

            data class EstimatedDiameter(
                val feet: Feet,
                val kilometers: Kilometers,
                val meters: Meters,
                val miles: Miles
            ) {
                data class Feet(
                    val estimated_diameter_max: Double,
                    val estimated_diameter_min: Double
                )

                data class Kilometers(
                    val estimated_diameter_max: Double,
                    val estimated_diameter_min: Double
                )

                data class Meters(
                    val estimated_diameter_max: Double,
                    val estimated_diameter_min: Double
                )

                data class Miles(
                    val estimated_diameter_max: Double,
                    val estimated_diameter_min: Double
                )
            }

            data class Links(
                val self: String
            )
        }

        data class X20220829(
            val absolute_magnitude_h: Double,
            val close_approach_data: List<CloseApproachData>,
            val estimated_diameter: EstimatedDiameter,
            val id: String,
            val is_potentially_hazardous_asteroid: Boolean,
            val is_sentry_object: Boolean,
            val links: Links,
            val name: String,
            val nasa_jpl_url: String,
            val neo_reference_id: String
        ) {
            data class CloseApproachData(
                val close_approach_date: String,
                val close_approach_date_full: String,
                val epoch_date_close_approach: Long,
                val miss_distance: MissDistance,
                val orbiting_body: String,
                val relative_velocity: RelativeVelocity
            ) {
                data class MissDistance(
                    val astronomical: String,
                    val kilometers: String,
                    val lunar: String,
                    val miles: String
                )

                data class RelativeVelocity(
                    val kilometers_per_hour: String,
                    val kilometers_per_second: String,
                    val miles_per_hour: String
                )
            }

            data class EstimatedDiameter(
                val feet: Feet,
                val kilometers: Kilometers,
                val meters: Meters,
                val miles: Miles
            ) {
                data class Feet(
                    val estimated_diameter_max: Double,
                    val estimated_diameter_min: Double
                )

                data class Kilometers(
                    val estimated_diameter_max: Double,
                    val estimated_diameter_min: Double
                )

                data class Meters(
                    val estimated_diameter_max: Double,
                    val estimated_diameter_min: Double
                )

                data class Miles(
                    val estimated_diameter_max: Double,
                    val estimated_diameter_min: Double
                )
            }

            data class Links(
                val self: String
            )
        }

        data class X20220830(
            val absolute_magnitude_h: Double,
            val close_approach_data: List<CloseApproachData>,
            val estimated_diameter: EstimatedDiameter,
            val id: String,
            val is_potentially_hazardous_asteroid: Boolean,
            val is_sentry_object: Boolean,
            val links: Links,
            val name: String,
            val nasa_jpl_url: String,
            val neo_reference_id: String
        ) {
            data class CloseApproachData(
                val close_approach_date: String,
                val close_approach_date_full: String,
                val epoch_date_close_approach: Long,
                val miss_distance: MissDistance,
                val orbiting_body: String,
                val relative_velocity: RelativeVelocity
            ) {
                data class MissDistance(
                    val astronomical: String,
                    val kilometers: String,
                    val lunar: String,
                    val miles: String
                )

                data class RelativeVelocity(
                    val kilometers_per_hour: String,
                    val kilometers_per_second: String,
                    val miles_per_hour: String
                )
            }

            data class EstimatedDiameter(
                val feet: Feet,
                val kilometers: Kilometers,
                val meters: Meters,
                val miles: Miles
            ) {
                data class Feet(
                    val estimated_diameter_max: Double,
                    val estimated_diameter_min: Double
                )

                data class Kilometers(
                    val estimated_diameter_max: Double,
                    val estimated_diameter_min: Double
                )

                data class Meters(
                    val estimated_diameter_max: Double,
                    val estimated_diameter_min: Double
                )

                data class Miles(
                    val estimated_diameter_max: Double,
                    val estimated_diameter_min: Double
                )
            }

            data class Links(
                val self: String
            )
        }
    }
}