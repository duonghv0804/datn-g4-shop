package com.example.websitebanquanao.type;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Body {
  private int orderCode;
  private int amount;
  private String description;
  private List<ObjectNode> items;
  private String cancelUrl;
  private String returnUrl;
  private String signature;
}
