package com.example.sensorsproject

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.sensorsproject.databinding.FragmentHomeBinding
import com.example.sensorsproject.databinding.FragmentProximidadeBinding

class ProximidadeFragment : Fragment(), SensorEventListener {
    lateinit var binding: FragmentProximidadeBinding
    private lateinit var sensorManager: SensorManager
    private var sensor: Sensor? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_proximidade, container, false)
        setHasOptionsMenu(true)

        sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        sensor?.also {
            proximity -> sensorManager.registerListener(this, proximity, SensorManager.SENSOR_DELAY_FASTEST)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_PROXIMITY) {
            val distancia = event.values[0]
            binding.valorProximidade.text = distancia.toString()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }
}