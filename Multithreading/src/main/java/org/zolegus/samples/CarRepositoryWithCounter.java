package org.zolegus.samples;
/*
public static class CarRepositoryWithCounter implements CarRepository {
    private Map<String, Car> cars = new HashMap<String, Car>();
    private Map<String, Car> trucks = new HashMap<String, Car>();
    private Object carCountSync = new Object();
    private int carCount = 0;

    public void addCar(Car car) {
        if (car.getLicencePlate().startsWith("C")) {
            synchronized (cars) {
                Car foundCar = cars.get(car.getLicencePlate());
                if (foundCar == null) {
                    cars.put(car.getLicencePlate(), car);
                    synchronized (carCountSync) {
                        carCount++;
                    }
                }
            }
        } else {
            synchronized (trucks) {
                Car foundCar = trucks.get(car.getLicencePlate());
                if (foundCar == null) {
                    trucks.put(car.getLicencePlate(), car);
                    synchronized (carCountSync) {
                        carCount++;
                    }
                }
            }
        }
    }

    public int getCarCount() {
        synchronized (carCountSync) {
            return carCount;
        }
    }
}
*/