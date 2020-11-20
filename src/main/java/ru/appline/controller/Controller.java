package ru.appline.controller;

import org.springframework.web.bind.annotation.*;
import ru.appline.logic.CompassModel;
import ru.appline.logic.Pet;
import ru.appline.logic.PetModel;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class Controller {
    private static PetModel petModel = PetModel.getInstance();
    private static CompassModel compassModel = CompassModel.getInstance();
    private static AtomicInteger newId = new AtomicInteger(1);

    @PostMapping(value = "/createPet", consumes = "application/json", produces = "text/html")
    public String createPet(@RequestBody Pet pet) {
        petModel.add(pet, newId.getAndIncrement());
        return "Питомец " + pet.getName() + " успешно создан!";
    }

    @GetMapping(value = "/getAll", produces = "application/json")
    public Map<Integer, Pet> getAll() {
        return petModel.getAll();
    }

    @GetMapping(value = "/getPet", consumes = "application/json", produces = "application/json")
    public Pet getPet(@RequestBody Map<String, Integer> id) {
        return petModel.getFromList(id.get("id"));
    }

    @PutMapping(value = "/editPet", consumes = "application/json", produces = "application/json")
    public Pet editPet(@RequestBody Map<String, String> id) {
        petModel.getFromList(Integer.parseInt(id.get("id"))).setName(id.get("name"));
        petModel.getFromList(Integer.parseInt(id.get("id"))).setType(id.get("type"));
        petModel.getFromList(Integer.parseInt(id.get("id"))).setAge(Integer.parseInt(id.get("age")));

        return petModel.getFromList(Integer.parseInt(id.get("id")));
    }

    @DeleteMapping(value = "/deletePet", consumes = "application/json", produces = "application/json")
    public Map<Integer, Pet> deletePet(@RequestBody Map<String, Integer> id) {
        petModel.getAll().remove(id.get("id"));
        return petModel.getAll();
    }

    @PostMapping(value = "/addSides", consumes = "application/json", produces = "application/json")
    public Map<String, String> addSides(@RequestBody Map<String, String> sides) {
        compassModel.addSides(sides);
        return compassModel.getAllSides();
    }

    @GetMapping(value = "/getSide", consumes = "application/json", produces = "application/json")
    public Map<String, String> getSide(@RequestBody Map<String, Integer> degree) {
        Map<String, String> resultSide = new HashMap<String, String>();
        String str = "";
        int currentDegree = degree.get("Degree");
        int lowBound = 0;
        int highBound = 0;
        for (Map.Entry<String, String> side: compassModel.getAllSides().entrySet()) {
            lowBound = Integer.parseInt(side.getValue().split("-")[0]);
            highBound = Integer.parseInt(side.getValue().split("-")[1]);
            if (lowBound > highBound) {
                if ((currentDegree >= lowBound && currentDegree < 360) || (currentDegree >= 0 && currentDegree <= highBound)) {
                    str = side.getKey();
                    break;
                }
            } else {
                if (currentDegree >= lowBound && currentDegree <= highBound) {
                    str = side.getKey();
                    break;
                }
            }
        }
        if (str.equals("")) {
            str = "Not Found!!! Wrong digit!";
        }
        resultSide.put("Side", str);
        return resultSide;
    }
}
