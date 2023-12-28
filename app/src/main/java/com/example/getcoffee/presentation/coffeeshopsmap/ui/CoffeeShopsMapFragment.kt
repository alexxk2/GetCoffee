package com.example.getcoffee.presentation.coffeeshopsmap.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.getcoffee.R
import com.example.getcoffee.databinding.FragmentCoffeeshopsmapBinding
import com.example.getcoffee.domain.models.Location
import com.example.getcoffee.presentation.coffeeshopsmap.view_model.CoffeeShopsMapViewModel
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.ScreenPoint
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.TextStyle
import com.yandex.runtime.image.ImageProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoffeeShopsMapFragment : Fragment() {
    private var _binding: FragmentCoffeeshopsmapBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CoffeeShopsMapViewModel by viewModels()
    private lateinit var token: String
    private var currentZoomValue = INITIAL_ZOOM_VALUE


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            token = it.getString("token","")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoffeeshopsmapBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MapKitFactory.initialize(requireContext().applicationContext)

        viewModel.getLocations(token)

        viewModel.locations.observe(viewLifecycleOwner){
            manageSpotsDrawing(it)
            initialMapCameraMovement(it)
        }

        binding.btnBack.setOnClickListener { findNavController().navigateUp() }
        binding.zoomInButton.setOnClickListener { zoomIn() }
        binding.zoomOutButton.setOnClickListener { zoomOut() }
    }



    private fun manageSpotsDrawing(listOfPlaces: List<Location>) {
        if (listOfPlaces.isEmpty()) {
            clearMapFromAllObjects()
            return
        }

        listOfPlaces.forEach { location ->
            val tempPoint = Point(location.point.latitude, location.point.longitude)
            addPlaceMarkOnMap(tempPoint, location)
        }
    }

    private fun addPlaceMarkOnMap(worldPoint: Point, location: Location) {
        val placemark = binding.yandexMapsView.map.mapObjects.addPlacemark(
            worldPoint,
            ImageProvider.fromResource(requireContext(), R.drawable.ic_coffee_shop)
        )
        placemark.setText(location.name)
        placemark.setTextStyle(TextStyle( 10f, R.color.brown,null,TextStyle.Placement.BOTTOM, 1f,true,true))

        placemark.addTapListener { _, _ ->
            val action = CoffeeShopsMapFragmentDirections.actionCoffeeShopsMapFragmentToMenuFragment(location.id, token)
            findNavController().navigate(action)
            true
        }
    }

    private fun clearMapFromAllObjects() {
        binding.yandexMapsView.map.mapObjects.clear()
    }

    private fun initialMapCameraMovement(listOfPlaces: List<Location>) {
         if (listOfPlaces.isEmpty()) {
            val defaultPoint = Point(59.945933, 30.320045)
            moveMapCamera(defaultPoint)

        } else {
            val lastCreatedPoint = Point(listOfPlaces.last().point.latitude, listOfPlaces.last().point.longitude)
            moveMapCamera(lastCreatedPoint)
        }
    }

    private fun moveMapCamera(point: Point) {
        binding.yandexMapsView.map.move(
            CameraPosition(
                point, INITIAL_ZOOM_VALUE, 0.0f, 0.0f
            ), Animation(Animation.Type.SMOOTH, MAP_MOVEMENT_DURATION_LONG), null
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun createWorldPointInCenter(): Point {
        with(binding) {
            val centerX = yandexMapsView.width / 2f
            val centerY = yandexMapsView.height / 2f
            val centerPoint = ScreenPoint(centerX, centerY)
            return yandexMapsView.screenToWorld(centerPoint)
        }
    }

    private fun zoomMapCamera(zoomValue: Float) {
        binding.yandexMapsView.map.move(
            CameraPosition(
                createWorldPointInCenter(), zoomValue, 0.0f, 0.0f
            ), Animation(Animation.Type.SMOOTH, MAP_MOVEMENT_DURATION_SHORT), null
        )
    }


    private fun zoomIn() {
        if (currentZoomValue >= binding.yandexMapsView.map.maxZoom) return

        currentZoomValue += 1.0f
        zoomMapCamera(currentZoomValue)
    }

    private fun zoomOut() {
        if (currentZoomValue <= binding.yandexMapsView.map.minZoom) return

        currentZoomValue -= 1.0f
        zoomMapCamera(currentZoomValue)
    }

    override fun onStop() {
        binding.yandexMapsView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        binding.yandexMapsView.onStart()
    }
    companion object {
        const val MAP_MOVEMENT_DURATION_LONG = 3f
        const val MAP_MOVEMENT_DURATION_SHORT = 0.5f
        const val INITIAL_ZOOM_VALUE = 14.0f
        const val PLACE_ID = "placeId"
        const val TOAST_DURATION = 8000
    }
}