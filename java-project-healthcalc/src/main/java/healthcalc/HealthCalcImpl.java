package healthcalc;

import healthcalc.exceptions.InvalidHealthDataException;

public class HealthCalcImpl implements HealthCalc {

    @Override
    public String bmiClassification(double bmi) throws InvalidHealthDataException {
        if (bmi < 0) {
            throw new InvalidHealthDataException("BMI cannot be negative.");
        }
        if (bmi > 150) {
            throw new InvalidHealthDataException("BMI must be within a possible biological range [0-150].");
        }
        String result = "Obesity";
        if (bmi < 16.0) {
            result = "Severe thinness";
        } else if (bmi < 17.0) {
            result = "Moderate thinness";
        } else if (bmi < 18.5) {
            result = "Mild thinness";
        } else if (bmi < 25.0) {
            result = "Normal weight";
        } else if (bmi < 30.0) {
            result = "Overweight";
        } else if (bmi < 35.0) {
            result = "Obese Class I (Moderate)";
        } else if (bmi < 40.0) {
            result = "Obese Class II (Severe)";
        } else {
            result = "Obese Class III (Morbid)";
        }
        return result;
    }

    @Override
    public double bmi(double weight, double height) throws InvalidHealthDataException {
        if (weight <= 0) {
            throw new InvalidHealthDataException("Weight must be positive.");
        }
        if (height <= 0) {
            throw new InvalidHealthDataException("Height must be positive.");
        }
        if (weight < 1 || weight > 700) {
            throw new InvalidHealthDataException("Weight must be within a possible biological range [1-700] kg.");
        }
        if (height < 0.30 || height > 3.00) {
            throw new InvalidHealthDataException("Height must be within a possible biological range [0.30-3.00] m.");
        }
        return weight / Math.pow(height, 2);
    }
    
}
