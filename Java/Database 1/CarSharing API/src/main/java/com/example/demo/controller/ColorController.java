package com.example.demo.controller;

import com.example.demo.model.Color;
import com.example.demo.repository.ColorRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/colors")
public class ColorController {

  @Autowired
  private ColorRepository colorRepository;

  @GetMapping
  public List<Color> getAllColors() {
    return colorRepository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Color> getColorById(@PathVariable Byte id) {
    Optional<Color> color = colorRepository.findById(id);
    return color.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public Color createColor(@RequestBody Color color) {
    return colorRepository.save(color);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Color> updateColor(@PathVariable Byte id, @RequestBody Color colorDetails) {
    Optional<Color> color = colorRepository.findById(id);
    if (color.isPresent()) {
      Color updatedColor = color.get();
      updatedColor.setColor(colorDetails.getColor());
      return ResponseEntity.ok(colorRepository.save(updatedColor));
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteColor(@PathVariable Byte id) {
    if (colorRepository.existsById(id)) {
      colorRepository.deleteById(id);
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}