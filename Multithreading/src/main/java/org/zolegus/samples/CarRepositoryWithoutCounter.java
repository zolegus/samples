package org.zolegus.samples;

public static class CarRepositoryWithoutCounter implements CarRepository {
    private Map<String, Car> cars = new HashMap<String, Car>();
    private Map<String, Car> trucks = new HashMap<String, Car>();

    public void addCar(Car car) {
        if (car.getLicencePlate().startsWith("C")) {
            synchronized (cars) {
                Car foundCar = cars.get(car.getLicencePlate());
                if (foundCar == null) {
                    cars.put(car.getLicencePlate(), car);
                }
            }
        } else {
            synchronized (trucks) {
                Car foundCar = trucks.get(car.getLicencePlate());
                if (foundCar == null) {
                    trucks.put(car.getLicencePlate(), car);
                }
            }
        }
    }

    public int getCarCount() {
        synchronized (cars) {
            synchronized (trucks) {
                return cars.size() + trucks.size();
            }
        }
    }
}
