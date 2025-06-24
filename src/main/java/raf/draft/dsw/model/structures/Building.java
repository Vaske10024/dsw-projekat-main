package raf.draft.dsw.model.structures;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import raf.draft.dsw.controller.Serialization.ColorDeserializer;
import raf.draft.dsw.controller.Serialization.ColorSerializer;
import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.nodes.DraftNodeComposite;

import java.awt.*;
import java.util.Random;
import java.util.UUID;

@Getter
@Setter
@JsonTypeName("building")
public class Building extends DraftNodeComposite {
  @JsonSerialize(using = ColorSerializer.class)
  @JsonDeserialize(using = ColorDeserializer.class)
  private Color color;

  public Building() {
  }

  public Building(String naziv, DraftNode parent) {
    super(naziv, parent);
    this.color = new Color(new Random().nextInt(256), new Random().nextInt(256), new Random().nextInt(256));
  }

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }
}
