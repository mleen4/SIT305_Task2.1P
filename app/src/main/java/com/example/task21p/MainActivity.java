package com.example.task21p;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    Spinner newSpinner;

    Button clickMeButton;
    Button weightButton;
    Button temperatureButton;
    TextView valueOne;
    TextView valueTwo;
    TextView valueThree;
    EditText enterValue;

    TextView valueOneConverted;
    TextView valueTwoConverted;
    TextView valueThreeConverted;

    boolean measurementClicked = true;
    boolean weightClicked = false;
    boolean temperatureClicked = false;

//    TextView myTextView;
//    EditText myEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        clickMeButton = findViewById(R.id.clickMeButton);
        weightButton = findViewById(R.id.weightButton);
        temperatureButton = findViewById(R.id.temperatureButton);

        valueOne = findViewById(R.id.valueOneText);
        valueTwo = findViewById(R.id.valueTwoText);
        valueThree = findViewById(R.id.valueThreeText);
        enterValue = findViewById(R.id.enterValueBox);
        valueOneConverted = findViewById(R.id.valueOneConverted);
        valueTwoConverted = findViewById(R.id.valueTwoConverted);
        valueThreeConverted = findViewById(R.id.valueThreeConverted);


        final List<String> metricList = Arrays.asList("Centimeters", "Meters", "Feet", "Inches");
        final List<String> weightList = Arrays.asList("Kilograms", "Grams", "Pounds", "Ounces");
        final List<String> temperatureList = Arrays.asList("Celsius", "Fahrenheit", "Kelvin");

        //Spinner stuff // Trying to figure out why the adapter is necessary // Taken
        newSpinner = findViewById(R.id.newSpinner);

        //Instead of creating the array from the resources, I decided to do it programmatically and I found my inspiration from here:
        // https://stackoverflow.com/questions/2784081/android-create-spinner-programmatically-from-array from user Brandon O'Rourke

        ArrayAdapter measurementAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, metricList);
        measurementAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter weightAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, weightList);
        weightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter temperatureAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, temperatureList);
        temperatureAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        newSpinner.setAdapter(measurementAdapter);


        newSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                weightButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // checking if field is empty was inspired from here - https://www.codegrepper.com/code-examples/java/check+if+textfield+is+empty+java
                        if(TextUtils.isEmpty(enterValue.getText().toString()))
                        {
                            measurementClicked = false;
                            temperatureClicked = false;
                            weightClicked = true;
                            newSpinner.setAdapter(weightAdapter);
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this , "Please select the right conversion value", Toast.LENGTH_LONG).show();
                        }

                    }
                });
                clickMeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(TextUtils.isEmpty(enterValue.getText().toString()))
                        {
                            weightClicked = false;
                            temperatureClicked = false;
                            measurementClicked = true;
                            newSpinner.setAdapter(measurementAdapter);
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this , "Please select the right conversion value", Toast.LENGTH_LONG).show();
                        }
                    }

                });
                temperatureButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(TextUtils.isEmpty(enterValue.getText().toString()))
                        {
                            weightClicked = false;
                            temperatureClicked = true;
                            measurementClicked = false;
                            newSpinner.setAdapter(temperatureAdapter);
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this , "Please select the right conversion value", Toast.LENGTH_LONG).show();
                        }
                    }

                });

                String result = newSpinner.getSelectedItem().toString();
                Toast.makeText(getApplicationContext(), "You Selected: " + result, Toast.LENGTH_LONG).show();
                if (result.equals("Meters")) {
                    SetConvertedTextBlank();
                    valueOne.setText("Centimeters");
                    valueTwo.setText("Feet");
                    valueThree.setText("Inches");
                    clickMeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(measurementClicked)
                            {
                                try
                                {
                                    String testString = enterValue.getText().toString();
                                    double testDbl = Double.parseDouble(testString);
                                    double conversionResultCm = ToCentimeters(testDbl, result);
                                    double conversionResultFt = ToFeet(testDbl, result);
                                    double conversionResultIn = ToInches(testDbl, result);
                                    SetConvertedText(conversionResultCm,conversionResultFt, conversionResultIn);
                                }
                                catch (NumberFormatException ex)
                                {
                                    Toast.makeText(MainActivity.this , "Please Enter a Valid Value ie. Number/Integer", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    });
                } else if (result.equals("Centimeters")) {
                    SetConvertedTextBlank();
                    valueOne.setText("Meters");
                    valueTwo.setText("Feet");
                    valueThree.setText("Inches");
                    clickMeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(measurementClicked)
                            {
                                try {
                                    String testString = enterValue.getText().toString();
                                    double testDbl = Double.parseDouble(testString);
                                    double conversionResultMeters = ToMeters(testDbl, result);
                                    double conversionResultFt = ToFeet(testDbl, result);
                                    double conversionResultIn = ToInches(testDbl, result);
                                    // Decimal Formatting found here https://stackoverflow.com/questions/10959424/show-only-two-digit-after-decimal as string formatting seemed a little messy
                                    // ToString isn't required as DecimalFormat will convert it to a string for us, rather the .format()
                                    SetConvertedText(conversionResultMeters, conversionResultFt, conversionResultIn);
                                } catch (Exception e) {
                                    Toast.makeText(MainActivity.this, "Please Enter a Valid Value ie. Number/Integer", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    });
                } else if (result.equals("Feet")) {
                    SetConvertedTextBlank();
                    valueOne.setText("Meters");
                    valueTwo.setText("Centimeters");
                    valueThree.setText("Inches");
                    clickMeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(measurementClicked)
                            {
                                try {
                                    String testString = enterValue.getText().toString();
                                    double testDbl = Double.parseDouble(testString);
                                    double conversionResultMeters = ToMeters(testDbl, result);
                                    double conversionResultCm = ToCentimeters(testDbl, result);
                                    double conversionResultIn = ToInches(testDbl, result);
                                    // Decimal Formatting found here https://stackoverflow.com/questions/10959424/show-only-two-digit-after-decimal as string formatting seemed a little messy
                                    // ToString isn't required as DecimalFormat will convert it to a string for us, rather the .format()
                                    SetConvertedText(conversionResultMeters, conversionResultCm, conversionResultIn);
                                } catch (Exception e) {
                                    Toast.makeText(MainActivity.this, "Please Enter a Valid Value ie. 50/Number/Integer", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    });
                }
                else if(result.equals("Inches"))
                {
                    SetConvertedTextBlank();
                    valueOne.setText("Meters");
                    valueTwo.setText("Centimeters");
                    valueThree.setText("Feet");
                    clickMeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(measurementClicked) {
                                try {
                                    String testString = enterValue.getText().toString();
                                    double testDbl = Double.parseDouble(testString);
                                    double conversionResultMeters = ToMeters(testDbl, result);
                                    double conversionResultCm = ToCentimeters(testDbl, result);
                                    double conversionResultFt = ToFeet(testDbl, result);
                                    // Decimal Formatting found here https://stackoverflow.com/questions/10959424/show-only-two-digit-after-decimal as string formatting seemed a little messy
                                    // ToString isn't required as DecimalFormat will convert it to a string for us, rather the .format()
                                    SetConvertedText(conversionResultMeters, conversionResultCm, conversionResultFt);
                                } catch (Exception e) {
                                    Toast.makeText(MainActivity.this, "Please Enter a Valid Value ie. 50/Number/Integer", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    });

                }
                else if(result.equals("Kilograms"))
                {
                    SetConvertedTextBlank();
                    valueOne.setText("Grams");
                    valueTwo.setText("Ounces");
                    valueThree.setText("Pounds");
                    weightButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(weightClicked) {
                                try {
                                    String testString = enterValue.getText().toString();
                                    double testDbl = Double.parseDouble(testString);
                                    double conversionResultGrams = ToGrams(testDbl, result);
                                    double conversionResultOunces = ToOunces(testDbl, result);
                                    double conversionResultPounds = ToPounds(testDbl, result);
                                    SetConvertedText(conversionResultGrams, conversionResultOunces, conversionResultPounds);
                                } catch (Exception e) {
                                    Toast.makeText(MainActivity.this, "Please Enter a Valid Value ie. 50/Number/Integer", Toast.LENGTH_LONG).show();
                                }

                            }
                        }
                    });
                }
                else if(result.equals("Grams"))
                {
                    SetConvertedTextBlank();
                    valueOne.setText("Kilograms");
                    valueTwo.setText("Ounces");
                    valueThree.setText("Pounds");
                    weightButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(weightClicked)
                            {
                                try
                                {
                                    String testString = enterValue.getText().toString();
                                    double testDbl = Double.parseDouble(testString);
                                    double conversionResultKilograms = ToKilograms(testDbl, result);
                                    double conversionResultOunces = ToOunces(testDbl, result);
                                    double conversionResultPounds = ToPounds(testDbl, result);
                                    SetConvertedText(conversionResultKilograms, conversionResultOunces, conversionResultPounds);
                                }
                                catch(Exception e)
                                {
                                    Toast.makeText(MainActivity.this, "Please Enter a Valid Value ie. 50/Number/Integer", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    });
                }
                else if(result.equals("Ounces"))
                {
                    SetConvertedTextBlank();
                    valueOne.setText("Kilograms");
                    valueTwo.setText("Grams");
                    valueThree.setText("Pounds");
                    weightButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try
                            {
                                String testString = enterValue.getText().toString();
                                double testDbl = Double.parseDouble(testString);
                                double conversionResultKilograms = ToKilograms(testDbl, result);
                                double conversionResultGrams = ToGrams(testDbl, result);
                                double conversionResultPounds = ToPounds(testDbl, result);
                                SetConvertedText(conversionResultKilograms, conversionResultGrams, conversionResultPounds);
                            }
                            catch(Exception e)
                            {
                                Toast.makeText(MainActivity.this, "Please Enter a Valid Value ie. 50/Number/Integer", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                else if(result.equals("Pounds"))
                {
                    SetConvertedTextBlank();
                    valueOne.setText("Kilograms");
                    valueTwo.setText("Grams");
                    valueThree.setText("Ounces");
                    weightButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                String testString = enterValue.getText().toString();
                                double testDbl = Double.parseDouble(testString);
                                double conversionResultKilograms = ToKilograms(testDbl, result);
                                double conversionResultGrams = ToGrams(testDbl, result);
                                double conversionResultOunces = ToOunces(testDbl, result);
                                SetConvertedText(conversionResultKilograms, conversionResultGrams, conversionResultOunces);
                            }
                            catch(Exception e)
                            {
                                Toast.makeText(MainActivity.this, "Please Enter a Valid Value ie. 50/Number/Integer", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                else if(result.equals("Celsius"))
                {
                    SetConvertedTextBlank();
                    valueOne.setText("Fahrenheit");
                    valueTwo.setText("Kelvin");
                    valueThree.setText("");
                    temperatureButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try
                            {
                                String testString = enterValue.getText().toString();
                                double testDbl = Double.parseDouble(testString);
                                double conversionResultFahrenheit = ToFahrenheit(testDbl, result);
                                double conversionResultKelvin = ToKelvin(testDbl, result);
                                valueOneConverted.setText(new DecimalFormat("##.##").format(conversionResultFahrenheit));
                                valueTwoConverted.setText(new DecimalFormat("##.##").format(conversionResultKelvin));
                            }
                            catch(Exception e)
                            {
                                Toast.makeText(MainActivity.this, "Please Enter a Valid Value ie. 50/Number/Integer", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                else if(result.equals("Fahrenheit"))
                {
                    SetConvertedTextBlank();
                    valueOne.setText("Celsius");
                    valueTwo.setText("Kelvin");
                    valueThree.setText("");
                    temperatureButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try
                            {
                                String testString = enterValue.getText().toString();
                                double testDbl = Double.parseDouble(testString);
                                double conversionResultCelsius = ToCelsius(testDbl, result);
                                double conversionResultKelvin = ToKelvin(testDbl, result);
                                valueOneConverted.setText(new DecimalFormat("##.##").format(conversionResultCelsius));
                                valueTwoConverted.setText(new DecimalFormat("##.##").format(conversionResultKelvin));
                            }
                            catch(Exception e)
                            {
                                Toast.makeText(MainActivity.this, "Please Enter a Valid Value ie. 50/Number/Integer", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                else if(result.equals("Kelvin"))
                {
                    SetConvertedTextBlank();
                    valueOne.setText("Celsius");
                    valueTwo.setText("Fahrenheit");
                    valueThree.setText("");
                    temperatureButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try
                            {
                                String testString = enterValue.getText().toString();
                                double testDbl = Double.parseDouble(testString);
                                double conversionResultCelsius = ToCelsius(testDbl, result);
                                double conversionResultFahrenheit = ToFahrenheit(testDbl, result);
                                valueOneConverted.setText(new DecimalFormat("##.##").format(conversionResultCelsius));
                                valueTwoConverted.setText(new DecimalFormat("##.##").format(conversionResultFahrenheit));
                            }
                            catch(Exception e)
                            {
                                Toast.makeText(MainActivity.this, "Please Enter a Valid Value ie. 50/Number/Integer", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public double ToMeters(double input, String spinnerInput) {
        switch (spinnerInput) {
            case "Centimeters":
                return input / 100;
            case "Inches":
                //Inches Conversion
                return input / 39.37;
            case "Feet":
                //Feet Conversion
                return input / 3.281;
            default:
                return 0;
        }
    }

    public double ToCentimeters(double input, String spinnerInput) {
        switch (spinnerInput) {
            case "Meters":
                return input * 100;
            case "Inches":
                //Inches Conversion
                return input * 2.54;
            case "Feet":
                //Feet Conversion
                return input * 30.48;
            default:
                return 0;
        }
    }
    public double ToFeet(double input, String spinnerInput) {
        switch (spinnerInput) {
            case "Meters":
                return input * 3.281;
            case "Inches":
                //Inches Conversion
                return input / 12;
            case "Centimeters":
                //Feet Conversion
                return input / 30.48;
            default:
                return 0;
        }
    }
    public double ToInches(double input, String spinnerInput) {
        switch (spinnerInput) {
            case "Meters":
                return input * 39.37;
            case "Feet":
                //Inches Conversion
                return input * 12;
            case "Centimeters":
                //Feet Conversion
                return input / 2.54;
            default:
                return 0;
        }
    }

    public double ToKilograms(double input, String spinnerInput) {
        switch (spinnerInput) {
            case "Grams":
                return input / 1000;
            case "Ounces":
                return input / 35.274;
            case "Pounds":
                return input / 2.205;
            default:
                return 0;
        }
    }

    public double ToGrams(double input, String spinnerInput) {
        switch (spinnerInput) {
            case "Kilograms":
                return input * 1000;
            case "Ounces":
                return input * 28.35;
            case "Pounds":
                return input * 454;
            default:
                return 0;
        }
    }

    public double ToOunces(double input, String spinnerInput) {
        switch (spinnerInput) {
            case "Grams":
                return input / 28.35;
            case "Kilograms":
                return input * 35.274;
            case "Pounds":
                return input * 16;
            default:
                return 0;
        }
    }

    public double ToPounds(double input, String spinnerInput) {
        switch (spinnerInput) {
            case "Grams":
                return input / 454;
            case "Ounces":
                return input / 16;
            case "Kilograms":
                return input * 2.205;
            default:
                return 0;
        }
    }
    public double ToFahrenheit(double input, String spinnerInput) {
        switch (spinnerInput) {
            case "Kelvin":
                return (input - 273) * 9 / 5 + 32;
            case "Celsius":
                return (input * 9) / 5 + 32;
            default:
                return 0;
        }
    }
    public double ToCelsius(double input, String spinnerInput) {
        switch (spinnerInput) {
            case "Fahrenheit":
                return (input - 32) / 1.8;
            case "Kelvin":
                return input - 273.15;
            default:
                return 0;
        }
    }
    public double ToKelvin(double input, String spinnerInput) {
        switch (spinnerInput) {
            case "Fahrenheit":
                return input / 454;
            case "Celsius":
                return input + 273.15;
            default:
                return 0;
        }
    }

    public void SetConvertedText(double firstValue, double secondValue, double thirdValue)
    {
        // Decimal Formatting found here https://stackoverflow.com/questions/10959424/show-only-two-digit-after-decimal as string formatting seemed a little messy
        // ToString isn't required as DecimalFormat will convert it to a string for us, rather the .format()
        valueOneConverted.setText(new DecimalFormat("##.##").format(firstValue));
        valueTwoConverted.setText(new DecimalFormat("##.##").format(secondValue));
        valueThreeConverted.setText(new DecimalFormat("##.##").format(thirdValue));
    }

    public void SetConvertedTextBlank()
    {
        valueOneConverted.setText("");
        valueTwoConverted.setText("");
        valueThreeConverted.setText("");

    }
}
