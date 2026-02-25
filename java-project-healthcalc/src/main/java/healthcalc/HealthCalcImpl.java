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
        // if (bmi < 18.5) {
        //     result = "Underweight";
        // } else if (bmi >= 18.5 && bmi < 25) {
        //     result = "Normal weight";
        // } else if (bmi >= 25 && bmi < 30) {
        //     result = "Overweight";
        // }
        if (bmi < 18.5) {
            result = "Underweight";
        } else if (bmi < 25) {
            result = "Normal weight";
        } else if (bmi < 30) {
            result = "Overweight";
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
    
    @Override
    public double ibw(int height, String gender) throws InvalidHealthDataException {
        if (height <= 0) {
            throw new InvalidHealthDataException("Height must be positive.");
        }
        if (height < 150 || height > 300) {
            throw new InvalidHealthDataException("Height must be within a range between 150cm and 300cm.");
        }
        if (!"Hombre".equals(gender) && !"Mujer".equals(gender)) {
            throw new InvalidHealthDataException("Gender must be 'Hombre' or 'Mujer'.");
        }
        double weight = 0.0;
        if ("Hombre".equals(gender)) {
	        weight = (height - 100) - ((height - 150) / 4.0);
        } else {
	        weight = (height - 100) - ((height - 150) / 2.5);
        }
        return weight;
    }
}
