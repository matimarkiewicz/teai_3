package pl.markiewicz.teai_3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/cars", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class CarController {

    private CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public ResponseEntity<List<CarModel>> getAllCarList() {
        return new ResponseEntity<>(carService.getCarList(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarModel> getCarById(@PathVariable long id) {
        Optional<CarModel> first = carService.getCarList().stream().filter(car -> car.getId() == id).findFirst();
        if (first.isPresent()) {
            return new ResponseEntity<>(first.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getCarListByColor/{color}")
    public ResponseEntity<List<CarModel>> getCarListByColor(@PathVariable String color) {
        List<CarModel> findedCarListByColor = carService.getCarList().stream().filter(carModel -> carModel.getColor().
                equals(color)).collect(Collectors.toList());
        if (findedCarListByColor != null && !findedCarListByColor.isEmpty()) {
            return new ResponseEntity<>(findedCarListByColor, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity addCar(@RequestBody CarModel car) {
        boolean add = carService.getCarList().add(car);
        if (add) {
            return new ResponseEntity(HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping
    public ResponseEntity updateCar(@RequestBody CarModel modCar) {
        Optional<CarModel> first = carService.getCarList().stream().filter(carModel -> carModel.getId()
                == modCar.getId()).findFirst();
        if (first.isPresent()) {
            carService.getCarList().remove(first.get());
            carService.getCarList().add(modCar);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping
    public ResponseEntity updatePartCar(@RequestBody CarModel updateCar) {
        Optional<CarModel> first = carService.getCarList().stream().filter(carModel -> carModel.getId()
                == updateCar.getId()).findFirst();
        if (first.isPresent()) {
            if (updateCar.getColor() != null)
                first.get().setColor(updateCar.getColor());
            if (updateCar.getMark() != null)
                first.get().setMark(updateCar.getMark());
            if (updateCar.getModel() != null)
                first.get().setModel(updateCar.getModel());

            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CarModel> removeCar(@PathVariable long id) {
        Optional<CarModel> removeCar = carService.getCarList().stream().filter(car -> car.getId() == id).findFirst();
        if (removeCar.isPresent()) {
            carService.getCarList().remove(removeCar.get());
            return new ResponseEntity<>(removeCar.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
