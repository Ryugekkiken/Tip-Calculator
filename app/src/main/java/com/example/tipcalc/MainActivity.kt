package com.example.tipcalc

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.tipcalc.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculateButton.setOnClickListener{calculateTip()}
        binding.costOfService.setOnKeyListener{view, keyCode, _ -> handleKeyEvent(view, keyCode)}
    }

    private fun calculateTip()
    {
        val stringInTextField = binding.costOfService.editText?.text.toString()
        val cost = stringInTextField.toDoubleOrNull()
        if(cost == null || cost == 0.0)
        {
            displayTip(0.0)
            return
        }
        val selectedId = binding.serviceOpinionGroup.checkedRadioButtonId
        val tipPercentage = when(selectedId)
        {
            R.id.option_twenty_percent -> 0.20
            R.id.option_ten_percent -> 0.10
            else -> 0.05
        }

        var tip = tipPercentage * cost

        if(binding.roundUpSwitch.isChecked)
        {
            tip = kotlin.math.ceil(tip)
        }

        displayTip(tip)
    }

    private fun displayTip(tip: Double)
    {
        val formatedTip = NumberFormat.getCurrencyInstance().format(tip)

        binding.tipResult.text = getString(R.string.tip_amount_label_text, formatedTip)
    }

    private fun handleKeyEvent(view: View, keyCode: Int) : Boolean
    {
        if(keyCode == KeyEvent.KEYCODE_ENTER)
        {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}