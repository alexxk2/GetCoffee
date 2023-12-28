package com.example.getcoffee.data.converters

import com.example.getcoffee.data.network.dto.LocationDto
import com.example.getcoffee.data.network.dto.MenuItemDto
import com.example.getcoffee.domain.models.Location
import com.example.getcoffee.domain.models.MenuItem
import com.example.getcoffee.domain.models.Point
import javax.inject.Inject

class NetworkConverter @Inject constructor() {

    fun convertLocationToDomain(locationDto: LocationDto): Location {
        with(locationDto) {
            return Location(
                id = id,
                name = name,
                point = Point(
                    latitude = point.latitude,
                    longitude = point.longitude
                )
            )
        }
    }

    fun convertMenuItemToDomain(menuItemDto: MenuItemDto): MenuItem {
        with(menuItemDto) {
            return MenuItem(
                id = id,
                name = name,
                imageURL = imageURL,
                price = price
            )
        }
    }
}