package pl.markiewicz.teai_3;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarService {

    public List<CarModel> carList = new ArrayList<>();

    CarService() {
        carList.add(new CarModel(1L, "Opel", "Astra", "blue"));
        carList.add(new CarModel(2L, "Mazda", "2", "red"));
        carList.add(new CarModel(3L, "BMW", "i5", "black"));
    }

    public CarService(List<CarModel> carList) {
        this.carList = carList;
    }

    public List<CarModel> getCarList() {
        return carList;
    }

    public void setCarList(List<CarModel> carList) {
        this.carList = carList;
    }
}
