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
import com.example.sensorsproject.databinding.FragmentAcelerometroBinding
import com.example.sensorsproject.databinding.FragmentProximidadeBinding

class AcelerometroFragment : Fragment(), SensorEventListener {
    lateinit var binding: FragmentAcelerometroBinding
    private lateinit var sensorManager: SensorManager
    private var sensor: Sensor? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_acelerometro, container, false)
        setHasOptionsMenu(true)

        sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST)
    }

    override fun onStop() {
        super.onStop()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            val alpha: Float = 0.8f

            var gravity = arrayOf(0.0f, 0.0f, 0.0f);

            gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0]
            gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1]
            gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2]

            binding.acelerometroX.text = gravity[0].toString()
            binding.acelerometroY.text = gravity[1].toString()
            binding.acelerometroZ.text = gravity[2].toString()

        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }

}