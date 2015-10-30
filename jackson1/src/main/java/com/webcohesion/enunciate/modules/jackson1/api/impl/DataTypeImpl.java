package com.webcohesion.enunciate.modules.jackson1.api.impl;

import com.webcohesion.enunciate.api.datatype.*;
import com.webcohesion.enunciate.javac.decorations.element.ElementUtils;
import com.webcohesion.enunciate.javac.javadoc.JavaDoc;
import com.webcohesion.enunciate.metadata.Label;
import com.webcohesion.enunciate.modules.jackson1.model.TypeDefinition;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Ryan Heaton
 */
public abstract class DataTypeImpl implements DataType {

  private final TypeDefinition typeDefinition;

  protected DataTypeImpl(TypeDefinition typeDefinition) {
    this.typeDefinition = typeDefinition;
  }

  @Override
  public String getLabel() {
    Label label = this.typeDefinition.getAnnotation(Label.class);
    return label == null ? this.typeDefinition.getSimpleName().toString() : label.value();
  }

  @Override
  public String getSlug() {
    return "json_" + getLabel().toLowerCase();
  }

  @Override
  public String getDescription() {
    return this.typeDefinition.getJavaDoc().toString();
  }

  @Override
  public String getDeprecated() {
    return ElementUtils.findDeprecationMessage(this.typeDefinition);
  }

  @Override
  public Namespace getNamespace() {
    return this.typeDefinition.getContext().getNamespace();
  }

  @Override
  public Syntax getSyntax() {
    return this.typeDefinition.getContext();
  }

  @Override
  public List<DataTypeReference> getSupertypes() {
    return null;
  }

  @Override
  public String getSince() {
    JavaDoc.JavaDocTagList tags = this.typeDefinition.getJavaDoc().get("since");
    return tags == null ? null : tags.toString();
  }

  @Override
  public String getVersion() {
    JavaDoc.JavaDocTagList tags = this.typeDefinition.getJavaDoc().get("version");
    return tags == null ? null : tags.toString();
  }

  @Override
  public Example getExample() {
    return null;
  }

  @Override
  public Map<String, String> getPropertyMetadata() {
    return Collections.emptyMap();
  }
}
