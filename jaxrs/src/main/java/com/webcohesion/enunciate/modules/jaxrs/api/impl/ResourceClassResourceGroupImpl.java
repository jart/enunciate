package com.webcohesion.enunciate.modules.jaxrs.api.impl;

import com.webcohesion.enunciate.api.PathSummary;
import com.webcohesion.enunciate.api.resources.Method;
import com.webcohesion.enunciate.api.resources.Resource;
import com.webcohesion.enunciate.api.resources.ResourceGroup;
import com.webcohesion.enunciate.facets.FacetFilter;
import com.webcohesion.enunciate.javac.decorations.element.ElementUtils;
import com.webcohesion.enunciate.metadata.Label;
import com.webcohesion.enunciate.metadata.rs.ResourceLabel;
import com.webcohesion.enunciate.modules.jaxrs.model.ResourceMethod;

import javax.lang.model.element.AnnotationMirror;
import java.util.*;

/**
 * @author Ryan Heaton
 */
public class ResourceClassResourceGroupImpl implements ResourceGroup {

  private final com.webcohesion.enunciate.modules.jaxrs.model.Resource resourceClass;
  private final List<Resource> resources = new ArrayList<Resource>();
  private final String contextPath;

  public ResourceClassResourceGroupImpl(com.webcohesion.enunciate.modules.jaxrs.model.Resource resourceClass, String contextPath) {
    this.resourceClass = resourceClass;
    this.contextPath = contextPath;
    FacetFilter facetFilter = resourceClass.getContext().getContext().getConfiguration().getFacetFilter();
    for (ResourceMethod resourceMethod : resourceClass.getResourceMethods(true)) {
      if (!facetFilter.accept(resourceMethod)) {
        continue;
      }

      this.resources.add(new ResourceImpl(resourceMethod, this));
    }
  }

  @Override
  public String getSlug() {
    return "resource_" + resourceClass.getSimpleName().toString();
  }

  @Override
  public String getLabel() {
    String label = resourceClass.getSimpleName().toString();
    ResourceLabel resourceLabel = resourceClass.getAnnotation(ResourceLabel.class);
    if (resourceLabel != null && !"##default".equals(resourceLabel.value())) {
      label = resourceLabel.value();
    }
    Label generic = resourceClass.getAnnotation(Label.class);
    if (generic != null) {
      label = generic.value();
    }

    return label;
  }

  @Override
  public String getSortKey() {
    String sortKey = getLabel();
    ResourceLabel resourceLabel = resourceClass.getAnnotation(ResourceLabel.class);
    if (resourceLabel != null && !"##default".equals(resourceLabel.sortKey())) {
      sortKey = resourceLabel.sortKey();
    }
    return sortKey;
  }

  @Override
  public String getRelativeContextPath() {
    return this.contextPath;
  }

  @Override
  public String getDescription() {
    return resourceClass.getJavaDoc().toString();
  }

  @Override
  public String getDeprecated() {
    return ElementUtils.findDeprecationMessage(this.resourceClass);
  }

  @Override
  public List<PathSummary> getPaths() {
    HashMap<String, PathSummary> summaries = new HashMap<String, PathSummary>();
    for (Resource resource : this.resources) {
      Set<String> methods = new TreeSet<String>();
      for (Method method : resource.getMethods()) {
        methods.add(method.getHttpMethod());
      }

      PathSummary summary = summaries.get(resource.getPath());
      if (summary == null) {
        summary = new PathSummaryImpl(resource.getPath(), methods);
        summaries.put(resource.getPath(), summary);
      }
      else {
        summary.getMethods().addAll(methods);
      }
    }
    return new ArrayList<PathSummary>(summaries.values());
  }

  @Override
  public List<Resource> getResources() {
    return this.resources;
  }

  @Override
  public Map<String, AnnotationMirror> getAnnotations() {
    return this.resourceClass.getAnnotations();
  }
}
